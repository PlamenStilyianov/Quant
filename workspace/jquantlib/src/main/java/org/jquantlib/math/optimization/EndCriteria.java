/*
 Copyright (C) 2008 Joon Tiang Heng

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

package org.jquantlib.math.optimization;

import org.jquantlib.QL;

/**
 * Criteria to end optimization process
 * <p>
 * <ul>
 * <li>maximum number of iterations AND minimum number of iterations around
 * stationary point</li>
 * <li>x (independent variable) stationary point</li>
 * <li>y=f(x) (dependent variable) stationary point</li>
 * <li>stationary gradient</li>
 * </ul>
 *
 * @author Joon Tiang Heng
 */
// FIXME: needs code review and better documentation
public class EndCriteria {

    public enum Type {
        None,
        MaxIterations,
        StationaryPoint,
        StationaryFunctionValue,
        StationaryFunctionAccuracy,
        ZeroGradientNorm,
        Unknown;

//XXX not needed?
//
//        public final String toString(final Type ec) /*@ReadOnly*/ {
//            switch (ec) {
//            case None:
//                return "None";
//            case MaxIterations:
//                return "MaxIterations";
//            case StationaryPoint:
//                return "StationaryPoint";
//            case StationaryFunctionValue:
//                return "StationaryFunctionValue";
//            case StationaryFunctionAccuracy:
//                return "StationaryFunctionAccuracy";
//            case ZeroGradientNorm:
//                return "ZeroGradientNorm";
//            case Unknown:
//                return "Unknown";
//            default:
//                throw new LibraryException("unknown EndCriteria"); // TODO: message
//            }
//        }

    };

        //! Maximum number of iterations
        protected final int maxIterations_;

        //! Maximum number of iterations in stationary state
        protected final int maxStationaryStateIterations_;//mutable

        //! root, function and gradient epsilons
        protected final double rootEpsilon_, functionEpsilon_, gradientNormEpsilon_;


        private Type ecType;
        private int statStateIterations;


        //
        // public constructors
        //

        public EndCriteria(final int maxIterations,
                final int maxStationaryStateIterations,
                final double rootEpsilon,
                final double functionEpsilon,
                final double gradientNormEpsilon) {

            if (System.getProperty("EXPERIMENTAL") == null)
                throw new UnsupportedOperationException("Work in progress");

            this.maxIterations_ = maxIterations;
            this.rootEpsilon_ = rootEpsilon;
            this.functionEpsilon_ = functionEpsilon;
            this.maxStationaryStateIterations_ = (maxStationaryStateIterations != 0) ? maxStationaryStateIterations : Math.min(maxIterations/2, 100);
            this.gradientNormEpsilon_ = (Double.isNaN(gradientNormEpsilon)) ? functionEpsilon_ : gradientNormEpsilon;

            QL.require(this.maxStationaryStateIterations_ >= 1 , "maxStationaryStateIterations must be greater than one"); // TODO: message
            QL.require(this.maxStationaryStateIterations_ <= this.maxIterations_ , "maxStationaryStateIterations_ must be less than maxIterations_"); // TODO: message
        }


        //
        // public methods
        //

        /**
         * Test if the number of iteration is below MaxIterations
         */
        /* FIXME: The intention was to modify the passed reference ecType and not this.ecType.
         * However, the question is how this should be done without changing the method signature?
         */
        public boolean checkMaxIterations(
                final int iteration,
                final Type ecType) {
            if (iteration < maxIterations_)
                return false;
            //this is wrong!!!!!!!!!!!!!!!
            this.ecType = Type.MaxIterations;
            throw new UnsupportedOperationException("work in progress");
            //-- return true;
        }


        /**
         * Test if the root variation is below rootEpsilon
         */
        public boolean checkStationaryPoint(
                final double xOld,
                final double xNew,
                final int statStateIterations,
                final Type ecType) {
            if (Math.abs(xNew-xOld) >= rootEpsilon_) {
                this.statStateIterations = 0;
                return false;
            }
            this.statStateIterations++;
            if (statStateIterations <= maxStationaryStateIterations_)
                return false;
            this.ecType = Type.StationaryPoint;
            return true;
        }

        /**
         * Test if the function variation is below functionEpsilon
         */
        public boolean checkStationaryFunctionValue(
                final double fxOld,
                final double fxNew,
                final int statStateIterations,
                final Type ecType) {
            if (Math.abs(fxNew-fxOld) >= functionEpsilon_) {
                this.statStateIterations = 0;

                return false;
            }
            this.statStateIterations++;
            if (statStateIterations <= maxStationaryStateIterations_)
                return false;
            this.ecType = Type.StationaryFunctionValue;
            return true;
        }

        /**
         * Test if the function value is below functionEpsilon
         */
        public boolean checkStationaryFunctionAccuracy(
                final double f,
                final boolean positiveOptimization,
                final Type ecType) {
            if (!positiveOptimization)
                return false;
            if (f >= functionEpsilon_)
                return false;
            this.ecType = Type.StationaryFunctionAccuracy;
            return true;
        }


        /**
         * Test if the gradient norm value is below gradientNormEpsilon
         * */
        public boolean checkZeroGradientNorm(
                final double gradientNorm,
                final Type ecType) {
            if (gradientNorm >= gradientNormEpsilon_)
                return false;
            this.ecType = Type.ZeroGradientNorm;
            return true;
        }

        /**
         *  Test if the number of iterations is not too big and if a minimum point is not reached
         */
        public boolean get(
                final int iteration,
                final int statStateIterations,
                final boolean positiveOptimization,
                final double fold,
                final double normgold,
                final double fnew,
                final double normgnew,
                final Type ecType) {
            return
            checkMaxIterations(iteration, ecType) ||
            checkStationaryFunctionValue(fold, fnew, statStateIterations, ecType) ||
            checkStationaryFunctionAccuracy(fnew, positiveOptimization, ecType) ||
            checkZeroGradientNorm(normgnew, ecType);
        }

        // Inspectors

        public final int getMaxIterations() /*@ReadOnly*/ {
            return maxIterations_;
        }

        public final int getMaxStationaryStateIterations() /*@ReadOnly*/ {
            return maxStationaryStateIterations_;
        }

        public final double getRootEpsilon() /*@ReadOnly*/ {
            return rootEpsilon_;
        }

        public final double getFunctionEpsilon() /*@ReadOnly*/ {
            return functionEpsilon_;
        }

        public final double getGradientNormEpsilon() /*@ReadOnly*/ {
            return gradientNormEpsilon_;
        }

}
