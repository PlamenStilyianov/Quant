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
import org.jquantlib.math.distributions.GammaFunction;

/**
 * @author <Richard Gomes>
 */
public class RegularisedIncompleteBeta {

    //
    // private fields
    //

    private final double tolerance_;
    private final int maxIterations_;

    private static final GammaFunction _gammaFunction = new GammaFunction();

    public RegularisedIncompleteBeta(final double tolerance, final int maxIterations){

        checkMaxIterations(maxIterations);

        tolerance_ = tolerance;
        maxIterations_ = maxIterations;
    }

    public RegularisedIncompleteBeta(){
        tolerance_ = 1.0e-16;
        maxIterations_ = 100;
    }

    /*
    The implementation of the algorithm was inspired by
    "Numerical Recipes in C", 2nd edition,
    Press, Teukolsky, Vetterling, Flannery, chapter 6
     */
    private double betaContinuedFraction(final double a, final double  b, final double  x,
            final double accuracy, final int maxIteration) {

        checkMaxIterations(maxIteration);
        double aa, del;
        final double qab = a+b;
        final double qap = a+1.0;
        final double qam = a-1.0;
        double c = 1.0;
        double d = 1.0-qab*x/qap;
        if (Math.abs(d) < Constants.QL_EPSILON)
            d = Constants.QL_EPSILON;
        d = 1.0/d;
        double result = d;

        int m, m2;
        for (m=1; m<=maxIteration; m++) {
            m2=2*m;
            aa=m*(b-m)*x/((qam+m2)*(a+m2));
            d=1.0+aa*d;
            if (Math.abs(d) < Constants.QL_EPSILON) d=Constants.QL_EPSILON;
            c=1.0+aa/c;
            if (Math.abs(c) < Constants.QL_EPSILON) c=Constants.QL_EPSILON;
            d=1.0/d;
            result *= d*c;
            aa = -(a+m)*(qab+m)*x/((a+m2)*(qap+m2));
            d=1.0+aa*d;
            if (Math.abs(d) < Constants.QL_EPSILON) d=Constants.QL_EPSILON;
            c=1.0+aa/c;
            if (Math.abs(c) < Constants.QL_EPSILON) c=Constants.QL_EPSILON;
            d=1.0/d;
            del=d*c;
            result *= del;
            if (Math.abs(del-1.0) < accuracy)
                return result;
        }

        throw new ArithmeticException("a or b too big, or maxIteration too small in betacf"); // TODO: message

    }

    /**
     * Regularised incomplete beta function
     * @param a
     * @param b
     * @param x
     * @param accuracy
     * @param maxIteration
     * @return regularised incomplete beta
     */
    public double evaluate(final double x,final double a, final double b) {



        checkMaxIterations(maxIterations_);

        QL.require(a > 0.0 , "a must be greater than zero"); // TODO: message
        QL.require(b > 0.0 , "b must be greater than zero"); // TODO: message

        if (x == 0.0)
            return 0.0;

        if (x == 1.0)
            return 1.0;

        QL.require(x>0.0 && x<1.0 , "x must be in [0,1]"); // TODO: message

        final double result = Math.exp(_gammaFunction.logValue(a+b) -
                _gammaFunction.logValue(a) - _gammaFunction.logValue(b) +
                a*Math.log(x) + b*Math.log(1.0-x));

        if (x < (a+1.0)/(a+b+2.0))
            return result * betaContinuedFraction(a, b, x, tolerance_, maxIterations_)/a;

        return 1.0 - result * betaContinuedFraction(b, a, 1.0-x, tolerance_, maxIterations_)/b;
    }

    /**
     * Checks if <code>maxIterations</code> is >= 1.
     * @param maxIterations
     * @throws IllegalArgumentException if <code>maxIterations<1</code>
     */
    private void checkMaxIterations(final int maxIterations){
        QL.require(maxIterations>0 , "Expected maxIterations must be greater 0"); // TODO: message
    }

}
