/*
 Copyright (C) 2008 Richard Gomes

 This source code is release under the BSD License.

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the JQuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

package org.jquantlib.math;

import org.jquantlib.QL;




/**
 * @author <Richard Gomes>
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
//FIXME: refactor package "solvers1d"
abstract public class AbstractSolver1D<F extends Ops.DoubleOp> {

    //
    // Constants
    //

    private static final int MAX_FUNCTION_EVALUATIONS = 100;


    //
    // private fields
    //

    private int maxEvaluations;
    private boolean lowerBoundEnforced;
    private boolean upperBoundEnforced;


    //
    // protected fields
    //

    protected double root, xMin, xMax, fxMin, fxMax;
    protected int evaluationNumber;
    protected double lowerBound, upperBound;


    //
    // public constructors
    //

    public AbstractSolver1D() {
        this.maxEvaluations = MAX_FUNCTION_EVALUATIONS;
        this.lowerBoundEnforced = false;
        this.upperBoundEnforced = false;
    }

    public AbstractSolver1D(final int maxEvalautions, final boolean lowerBoundEnforced, final boolean upperBoundenforeced) {
        setMaxEvaluations(maxEvalautions);
        this.lowerBoundEnforced = lowerBoundEnforced;
        this.upperBoundEnforced = upperBoundenforeced;
    }


    //
    // protected abstract methods
    //

    abstract protected double solveImpl(final F f, double accuracy);


    //
    // public methods
    //

    /**
     * This method returns the zero of the function <code>f</code>,
     * determined with the given accuracy <code>epsilon</code>;
     * depending on the particular solver, this might mean that
     * the returned <code>x</code> is such that <code>|f(x)| < epsilon f</code>,
     * or that <code>f |x-\xi| < \epsilon \f$ where \f$ \xi \f$ </code>
     * is the real zero.
     * <p>
     * This method contains a bracketing routine to which an
     * initial guess must be supplied as well as a step used to
     * scan the range of the possible bracketing values.
     */
    public double solve(final F f, double accuracy, final double guess, final double step) {

        QL.require(accuracy > 0.0 , "accuracy must be positive"); // TODO: message
        // check whether we really want to use epsilon
        accuracy = Math.max(accuracy, Constants.QL_EPSILON);

        final double growthFactor = 1.6;
        int flipflop = -1;

        root = guess;
        fxMax = f.op(root);

        // monotonically crescent bias, as in optionValue(volatility)
        if (fxMax == 0.0)
            return root;
        else if (fxMax > 0.0) {
            xMin = enforceBounds(root - step);
            fxMin = f.op(xMin);
            xMax = root;
        } else {
            xMin = root;
            fxMin = fxMax;
            xMax = enforceBounds(root + step);
            fxMax = f.op(xMax);
        }

        evaluationNumber = 2;
        while (evaluationNumber <= maxEvaluations) {
            if (fxMin * fxMax <= 0.0) {//FIXME avoid product to check signs
                if (fxMin == 0.0)
                    return xMin;
                if (fxMax == 0.0)
                    return xMax;
                root = (xMax + xMin) / 2.0;
                return solveImpl(f, accuracy);
            }
            if (Math.abs(fxMin) < Math.abs(fxMax)) {
                xMin = enforceBounds(xMin + growthFactor * (xMin - xMax));
                fxMin = f.op(xMin);
            } else if (Math.abs(fxMin) > Math.abs(fxMax)) {
                xMax = enforceBounds(xMax + growthFactor * (xMax - xMin));
                fxMax = f.op(xMax);
            } else if (flipflop == -1) {
                xMin = enforceBounds(xMin + growthFactor * (xMin - xMax));
                fxMin = f.op(xMin);
                evaluationNumber++;
                flipflop = 1;
            } else if (flipflop == 1) {
                xMax = enforceBounds(xMax + growthFactor * (xMax - xMin));
                fxMax = f.op(xMax);
                flipflop = -1;
            }
            evaluationNumber++;
        }

        //FIXME is it so exceptional, should we return s success/fail flag?
        // TODO: code review :: please verify against QL/C++ code
        throw new ArithmeticException("unable to bracket root after function evaluation"); // TODO: message
    }

    /**
     * This method returns the zero of the function {@latex$ f }, determined with the given accuracy {@latex$ \epsilon };
     * depending on the particular solver, this might mean that the returned {@latex$ x } is such that {@latex$ |f(x)| < \epsilon },
     * or that {@latex$ |x-\xi| < \epsilon } where {@latex$ \xi } is the real zero.
     * <p>
     * An initial guess must be supplied, as well as two values {@latex$ x_\mathrm{min} } and {@latex$ x_\mathrm{max} } which must
     * bracket the zero (i.e., either {@latex$ f(x_\mathrm{min}) \leq 0 \leq f(x_\mathrm{max}) }, or
     * {@latex$ f(x_\mathrm{max}) \leq 0 \leq f(x_\mathrm{min}) } must be true).
     *
     * @param f is the function we wish to find a root
     * @param accuracy is the desired accuracy
     * @param guess
     * @param xMin
     * @param xMax
     * @return a zero of a function
     */
    public double solve(final F f, double accuracy, final double guess, final double xMin, final double xMax) {
        // TODO: Design by Contract? http://bugs.jquantlib.org/view.php?id=291
        QL.require(accuracy > 0.0 , "accuracy must be positive"); // TODO: message
        QL.require(xMin < xMax , "invalid range: xMin >= xMax"); // TODO: message
        QL.require(!lowerBoundEnforced || xMin >= lowerBound , "xMin < enforced low bound"); // TODO: message
        QL.require(!upperBoundEnforced || xMax <= upperBound , "xMax > enforced hi bound"); // TODO: message

        // check whether we really want to use epsilon
        accuracy = Math.max(accuracy, Constants.QL_EPSILON);

        this.xMin = xMin;
        this.xMax = xMax;


        fxMin = f.op(this.xMin);
        if (fxMin == 0.0) return this.xMin;

        fxMax = f.op(this.xMax);
        if (fxMax == 0.0) return this.xMax;

        evaluationNumber = 2;

        // TODO: code review :: please verify against QL/C++ code
        QL.require(fxMin * fxMax < 0.0 , "root not bracketed"); // TODO: message
        QL.require(guess > this.xMin , "guess must be greather than xMin"); // TODO: message
        QL.require(guess < this.xMax , "guess must be lesser than xMax"); // TODO: message

        root = guess;

        return solveImpl(f, accuracy);
    }

    public void setMaxEvaluations(final int evaluations) {
        this.maxEvaluations = Math.max(1, evaluations);
    }

    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    public void setLowerBound(final double lowerBound) {
        this.lowerBound = lowerBound;
        this.lowerBoundEnforced = true;
    }

    public void setUpperBound(final double upperBound) {
        this.upperBound = upperBound;
        this.upperBoundEnforced = true;
    }

    public int getNumEvaluations() {
        return evaluationNumber;
    }


    //
    // private methods
    //

    private double enforceBounds(final double x) {
        if (lowerBoundEnforced && x < lowerBound) return lowerBound;
        if (upperBoundEnforced && x > upperBound) return upperBound;
        return x;
    }

}
