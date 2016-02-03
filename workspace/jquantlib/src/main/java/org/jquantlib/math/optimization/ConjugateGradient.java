/*
 Copyright (C) 2009 Ueli Hofstetter

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

/* -*- mode: c++; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*- */

/*
 Copyright (C) 2006 Ferdinando Ametrano
 Copyright (C) 2001, 2002, 2003 Nicolas Di Cesare

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

/*! \file conjugategradient.hpp
    \brief Conjugate gradient optimization method
*/
package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.EndCriteria.Type;

//! Multi-dimensional Conjugate Gradient class.
/*! User has to provide line-search method and
    optimization end criteria.
    Search direction \f$ d_i = - f'(x_i) + c_i*d_{i-1} \f$
    where \f$ c_i = ||f'(x_i)||^2/||f'(x_{i-1})||^2 \f$
    and \f$ d_1 = - f'(x_1) \f$
*/

public class ConjugateGradient extends LineSearchBasedMethod {

    public ConjugateGradient(){
        throw new UnsupportedOperationException("Work in progress");
    }


    public ConjugateGradient(final LineSearch lineSearch) {
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
        if(lineSearch == null){

        }
    }

    /*! Multi-dimensional Conjugate Gradient
    (Fletcher-Reeves-Polak-Ribiere algorithm
    adapted from Numerical Recipes in C, 2nd edition).
     */
    @Override
    public Type minimize(final Problem P, final EndCriteria endCriteria) {
        final EndCriteria.Type ecType = EndCriteria.Type.None;
        P.reset();
        Array x_ = P.currentValue();
        int iterationNumber_ = 0;
        final int stationaryStateIterationNumber_ = 0;
        lineSearch_.setSearchDirection(new Array(x_.size()));

        boolean done = false;

        // function and squared norm of gradient values;
        double fold, gold2;
        final double c;
        final double normdiff;
        // clssical inital value for line-search step
        final double t = 1.0;
        // Set gradient g at the size of the optimization problem search direction.
        final int sz = lineSearch_.searchDirection().size();
        Array g = new Array(sz); Array d = new Array(sz); final Array sddiff = new Array(sz);
        // Initialize cost function and gradient g
        P.setFunctionValue(P.valueAndGradient(g, x_));
        lineSearch_.setSearchDirection(g.mul(-1));
        P.setGradientNormValue(g.dotProduct(g));
        // Loop over iterations
        do{
            // Linesearch
            // FIXME: what are we doing here?
            //t = (*lineSearch_)(P, ecType, endCriteria, t);
            // don't throw: it can fail just because maxIterations exceeded
            //QL_REQUIRE(lineSearch_->succeed(), "line-search failed!");
            if (lineSearch_.succeed_)
            {
                // Updates
                d = lineSearch_.searchDirection();
                // New point
                x_ = lineSearch_.lastX();
                // New function value
                fold = P.functionValue();
                P.setFunctionValue(lineSearch_.lastFunctionValue());
                // New gradient and search direction vectors
                g = lineSearch_.lastGradient();
                // orthogonalization coef
                gold2 = P.gradientNormValue();
                P.setGradientNormValue(lineSearch_.lastGradientNormNorm2());
                //c = P.gradientNormValue() / gold2;
                // conjugate gradient search direction
                //sddiff = (-g + c * d) - lineSearch_->searchDirection();
                //normdiff = std::sqrt(DotProduct(sddiff, sddiff));
                //lineSearch_->searchDirection() = -g + c * d;
                // End criteria
                /* FIXME: This part will be remove in later versions of Quantlib --> to be reviewd!!
                done = endCriteria(iterationNumber_,
                                   stationaryStateIterationNumber_,
                                   true,  //FIXME: it should be in the problem
                                   fold,
                                   Math.sqrt(gold2),
                                   P.functionValue(),
                                   Math.sqrt(P.gradientNormValue()),
                                   ecType
                                   // FIXME: it's never been used! ???
                                   // , normdiff
                                   );*/
                // Increase interation number"
                ++iterationNumber_;
            } else {
                done=true;
            }
        } while (!done);
        P.setCurrentValue(x_);
        return ecType;
        }
    }

