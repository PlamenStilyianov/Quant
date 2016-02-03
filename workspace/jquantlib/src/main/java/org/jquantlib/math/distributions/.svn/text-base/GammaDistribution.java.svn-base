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
import org.jquantlib.math.Ops;

/**
 * @author Richard Gomes
 * @author Dominik Holenstein
 */

/**
 * The gamma distribution is a two-parameter family of continuous probability distributions.
 * <p>
 * A gamma distribution is a general type of statistical distribution that is related to the beta
 * distribution and arises naturally in processes for which the waiting times between Poisson
 * distributed events are relevant.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Gamma_distribution">Gamma Distribution</a>
 * @see <a href="http://mathworld.wolfram.com/GammaDistribution.html">Gamma Distribution on Wolfram MathWorld</a>
 *
 * @author Richard Gomes
 * @author Dominik Holenstein
 */
public class GammaDistribution implements Ops.DoubleOp {

    private static final String ACCURACY_NOT_REACHED = "accuracy not reached";

    //
    // private field
    //

    private final double a;


    //
    // public constructor
    //


    /**
     * Intitializes <code>a_</code> and checks that <code>a_</code> is not smaller than 0.00.
     * @param a
     * @throws ArithmeticException if <code>a_</code> is smaller than 0.00
     */
    public GammaDistribution(final double a) {
        QL.require(a >= 0.0 , "invalid parameter for gamma distribution"); // TODO: message
        this.a = a;
    }


    //
    // implements Ops.DoubleOp
    //


    /**
     * Computes the Gamma distribution.
     * @param x random variable
     * @return Gamma distribution of <code>x</code>
     */
    @Override
    public double op(final double x) /* Read-only */ {

        if (x <= 0.0) return 0.0;

        final double gln = new GammaFunction().logValue(a);

        if (x < (a + 1.0)) {
            double ap = a;
            double del = 1.0 / a;
            double sum = del;
            for (int n = 1; n <= 100; n++) {
                ap += 1.0;
                del *= x / ap;
                sum += del;
                if (Math.abs(del) < Math.abs(sum) * 3.0e-7)
                    return sum * Math.exp(-x + a * Math.log(x) - gln);
            }
        } else {
            double b = x + 1.0 - a;
            double c = Constants.QL_MAX_REAL;
            double d = 1.0 / b;
            double h = d;
            for (int n = 1; n <= 100; n++) {
                final double an = -1.0 * n * (n - a);
                b += 2.0;
                d = an * d + b;

                if (Math.abs(d) < Constants.QL_EPSILON)
                    d = Constants.QL_EPSILON;
                c = b + an / c;
                if (Math.abs(c) < Constants.QL_EPSILON)
                    c = Constants.QL_EPSILON;
                d = 1.0 / d;
                final double del = d * c;
                h *= del;
                if (Math.abs(del - 1.0) < Constants.QL_EPSILON)
                    return h * Math.exp(-x + a * Math.log(x) - gln);
            }
        }

        throw new ArithmeticException(ACCURACY_NOT_REACHED); // TODO: message
    }

}

