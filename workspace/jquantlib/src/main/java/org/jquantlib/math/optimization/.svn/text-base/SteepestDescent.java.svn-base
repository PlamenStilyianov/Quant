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
package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.EndCriteria.Type;

//TODO: code review: license, class comments, compare against C++ sources
public class SteepestDescent extends LineSearchBasedMethod {

    public SteepestDescent(LineSearch lineSearch){
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        if(lineSearch == null)
            lineSearch = new LineSearch();

    }

    @Override
    public Type minimize(final Problem P, final EndCriteria endCriteria) {
        final EndCriteria.Type ecType = EndCriteria.Type.None;
        P.reset();
        Array x_ = P.currentValue();
        int iterationNumber = 0;
        final int stationaryStateIterationNumber_ = 0;
        lineSearch_.setSearchDirection(new Array(x_.size()));
        boolean end = false;

        // function and squared norm of gradient values
        double normdiff; // TODO: code review :: variable never read
        // classical initial value for line-search step
        double t = 1.0;
        // set gold at the size of the optimization problem search direction
        Array gold  = new Array(lineSearch_.searchDirection().size());
        Array gdiff = new Array(lineSearch_.searchDirection().size());

        P.setFunctionValue(P.valueAndGradient(gold, x_));
        lineSearch_.searchDirection_ = gold.mul(-1);
        P.setGradientNormValue(gold.dotProduct(gold));
        normdiff = Math.sqrt(P.gradientNormValue());

        do {
            // Linesearch
            t = lineSearch_.evaluate(P, ecType, endCriteria, t);
            if (lineSearch_.succeed_ == false) throw new ArithmeticException("line search failed");
            // End
            end = endCriteria.get(iterationNumber,
                    stationaryStateIterationNumber_,
                    true, //FIXME: it should be in the problem
                    P.functionValue(),
                    Math.sqrt(P.gradientNormValue()),
                    lineSearch_.lastFunctionValue(),
                    Math.sqrt(lineSearch_.lastGradientNormNorm2()),
                    ecType
                    //FIXME: it's never been used! ???
                    // , normdiff
            );

            // Updates
            // New point
            x_ = lineSearch_.lastX();
            // New function value
            P.setFunctionValue(lineSearch_.lastFunctionValue());
            // New gradient and search direction vectors
            gdiff = gold.sub(lineSearch_.lastGradient());
            normdiff = Math.sqrt(gdiff.dotProduct(gdiff));
            gold = lineSearch_.lastGradient();
            lineSearch_.setSearchDirection(gold.mul(-1));
            // New gradient squared norm
            P.setGradientNormValue(lineSearch_.lastGradientNormNorm2());

            // Increase iteration number
            ++iterationNumber;
        } while (end == false);

        P.setCurrentValue(x_);
        return ecType;
    }
}
