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

import org.jquantlib.math.Constants;
import org.jquantlib.math.ErrorFunction;
import org.jquantlib.math.Ops;

/**
 * Cumulative normal distribution function (CDF).
 * <p>
 * Given x it provides an approximation to the integral of the Gaussian Normal Distribution.
 * 
 * {@latex[
 * 	\frac12 \left(1+\mathrm{erf}\left( \frac{x-\mu}{\sigma\sqrt2}\right) \right)
 * }
 * <p>
 * @see cite: <i>M. Abramowitz and I. Stegun, Handbook of Mathematical Functions, Dover Publications, New York (1972)</i>
 * @see <a href="http://en.wikipedia.org/wiki/Normal_distribution">Cumulative Normal Distribution</a>
 * 
 * @author Richard Gomes
 */
public class CumulativeNormalDistribution extends NormalDistribution implements Ops.DoubleOp {

    //
    // static private fields
    //

    static private final ErrorFunction errorFunction = new ErrorFunction();
    static private final NormalDistribution gaussian = new NormalDistribution();

    //
    // public constructors
    //

    public CumulativeNormalDistribution() {
        super();
    }

    public CumulativeNormalDistribution(final double average, final double sigma) {
        super(average, sigma);
    }


    //
    // Implements Ops.DoubleOp
    //

    /**
     * Computes the cumulative normal distribution.
     * <p>
     * Asymptotic expansion for very negative z as references on M. Abramowitz book.
     * 
     * @see cite: "Monte Carlo Methods in Finance", ISBN-13: 978-0471497417
     * @see cite: M. Abramowitz and A. Stegun, Pocketbook of Mathematical Functions, ISBN 3-87144818-4, p.408, item 26.2.12
     * 
     * @param z
     * @return result
     */
    @Override
    public double op(double z) /* @Read-only */ {
        // QL.require(!(z >= average && 2.0*average-z > average) , "not a real number");
        z = (z - average) / sigma;
        double result = 0.5 * ( 1.0 + errorFunction.op( z*Constants.M_SQRT_2 ) );
        if (result<=1e-8) { //TODO: investigate the threshold level
            // See:Asymptotic expansion for very negative z following (26.2.12) on page 408 in M. Abramowitz and A. Stegun,
            // Pocketbook of Mathematical Functions, ISBN 3-87144818-4.
            // See also: Jaeckels book "Monte Carlo Methods in Finance", ISBN-13: 978-0471497417
            double sum=1.0;
            final double zsqr=z*z;
            double i=1.0, g=1.0, x, y, a=Constants.QL_MAX_REAL, lasta;
            do {
                lasta=a;
                x = (4.0*i-3.0)/zsqr;
                y = x*((4.0*i-1)/zsqr);
                a = g*(x-y);
                sum -= a;
                g *= y;
                ++i;
                a = Math.abs(a);
            } while (lasta>a && a>=Math.abs(sum*Constants.QL_EPSILON));
            result = -gaussian.op(z)/z*sum;
        }
        return result;
    }


    //
    // implements Derivative
    //

    /**
     * Computes the derivative.
     * @param x
     * @return <code>gaussian.evaluate(xn) / sigma</code>
     */
    @Override
    public double derivative(final double x) /* @ReadOnly */ {
        final double xn = (x - average) / sigma;
        return gaussian.op(xn) / sigma;
    }

}



