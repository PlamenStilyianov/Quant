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

package org.jquantlib.math.distributions;


import org.jquantlib.QL;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Factorial;
import org.jquantlib.math.Ops;

/**
 * Inverse cumulative Poisson distribution function.
 *
 * @author Dominik Holenstein
 */
// TEST the correctness of the returned value is tested by checking it against known good results.
public class InverseCumulativePoisson implements Ops.DoubleOp {

    //
    // private fields
    //

    private final double lambda;


    //
    // public constructors
    //

	public InverseCumulativePoisson() {
    	this(1.0);
    }


    public InverseCumulativePoisson(final double lambda) {
        QL.require(lambda>0.0, "lambda must be positive");
        this.lambda = lambda;
    }


    //
    // public methods
    //

    private double calcSummand(final int index) {
        final Factorial fact = new Factorial();
        return Math.exp(-lambda) * Math.pow(lambda, index) / fact.get(index);
    }


    //
    // implements Ops.op
    //

    /**
     * Computes the inverse cumulative poisson distribution.
     *
     * @param x
     * @returns the inverse of the cumulative poisson distribution of input <code>x</code>
     */
    @Override
    public double op (final double x) /* @Read-only */ {
        QL.require(x >= 0.0 && x <= 1.0 , "undefined outside interval [0,1]"); // TODO: message

        if (x == 1.0) {
            return Constants.QL_MAX_REAL;
        }

        double sum = 0.0;
        int index = 0;
        while (x > sum) {
            sum += calcSummand(index);
            index++;
        }
        return (index-1);
    }

}
