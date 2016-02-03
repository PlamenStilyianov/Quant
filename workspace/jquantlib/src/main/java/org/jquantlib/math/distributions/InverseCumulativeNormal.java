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
import org.jquantlib.Settings;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.Constants;
import org.jquantlib.math.randomnumbers.InverseCumulative;


/**
 * The inverse normal cumulative distribution is a non-linear function for which
 * no closed-form solution exists. The function is continuous, monotonically increasing,
 * infinitely differentiable, and maps the open interval (0,1) to the whole real line.
 *
 * @see <a href="http://home.online.no/~pjacklam/notes/invnorm/">
 * 		An algorithm for computing the inverse normal cumulative distribution function</a>
 *
 * @author Dominik Holenstein
 */

public class InverseCumulativeNormal implements InverseCumulative {

    static final private String SIGMA_MUST_BE_POSITIVE = "sigma must be greater than 0.0";

    //
    // static final private fields
    //

    static final private double a1 = -3.969683028665376e+01;
    static final private double a2 =  2.209460984245205e+02;
    static final private double a3 = -2.759285104469687e+02;
    static final private double a4 =  1.383577518672690e+02;
    static final private double a5 = -3.066479806614716e+01;
    static final private double a6 =  2.506628277459239e+00;

    static final private double b1 = -5.447609879822406e+01;
    static final private double b2 =  1.615858368580409e+02;
    static final private double b3 = -1.556989798598866e+02;
    static final private double b4 =  6.680131188771972e+01;
    static final private double b5 = -1.328068155288572e+01;

    static final private double c1 = -7.784894002430293e-03;
    static final private double c2 = -3.223964580411365e-01;
    static final private double c3 = -2.400758277161838e+00;
    static final private double c4 = -2.549732539343734e+00;
    static final private double c5 =  4.374664141464968e+00;
    static final private double c6 =  2.938163982698783e+00;

    static final private double d1 =  7.784695709041462e-03;
    static final private double d2 =  3.224671290700398e-01;
    static final private double d3 =  2.445134137142996e+00;
    static final private double d4 =  3.754408661907416e+00;

    //
    // Limits of the approximation regions (break-points)
    //
    static final private double xlow = 0.02425;
    static final private double xhigh = 1.0 - xlow;


    //
    // protected fields
    //

    protected double average;
    protected double sigma;


    //
    // private fields
    //

    // refinement for higher precision
    private final boolean highPrecision;


    //
    // public constructors
    //

    public InverseCumulativeNormal() {
        this(0.0);
    }

    public InverseCumulativeNormal(final double average) {
        this(average, 1.0);
    }

    public InverseCumulativeNormal(final double average, final double sigma) {
        QL.require(sigma > 0.0 , SIGMA_MUST_BE_POSITIVE); // TODO: message
        this.average = average;
        this.sigma = sigma;
        this.highPrecision = new Settings().isRefineHighPrecisionUsingHalleysMethod();
    }


    //
    // implements UnaryFunction
    //

    /**
     * Computes the inverse cumulative normal distribution.
     * @param x
     * @returns <code>average + z * sigma</code>
     */
    @Override
    public double op(double x)/* @ReadOnly */{
        QL.require(sigma > 0.0 , SIGMA_MUST_BE_POSITIVE); // TODO: message

        double z;
        double r;

        if (x < 0.0 || x > 1.0) {
            // try to recover if due to numerical error
            if (Closeness.isCloseEnough(x, 1.0)) {
                x = 1.0;
            } else if (Math.abs(x) < Constants.QL_EPSILON) {
                x = 0.0;
            } else {
                throw new LibraryException(SIGMA_MUST_BE_POSITIVE); // QA:[RG]::verified
            }
        }

        if (x < xlow) {
            // Rational approximation for the lower region 0<x<u_low
            z = Math.sqrt(-2.0 * Math.log(x));
            z = (((((c1*z+c2)*z+c3)*z+c4)*z+c5)*z+c6) / ((((d1*z+d2)*z+d3)*z+d4)*z+1.0);
        } else if (x <= xhigh) {
            // Rational approximation for the central region u_low<=x<=u_high
            z = x-0.5;
            r = z*z;
            z = (((((a1*r+a2)*r+a3)*r+a4)*r+a5)*r+a6)*z / (((((b1*r+b2)*r+b3)*r+b4)*r+b5)*r+1.0);
        } else {
            // Rational approximation for the upper region u_high<x<1
            z = Math.sqrt(-2.0 * Math.log(1.0 - x));
            z = -(((((c1*z+c2)*z+c3)*z+c4)*z+c5)*z+c6) / ((((d1*z+d2)*z+d3)*z+d4)*z+1.0);
        }

        // The relative error of the approximation has absolute value less than 1.15e-9.
        // One iteration of Halley's rational method (third order) gives full machine precision.
        // #define REFINE_TO_FULL_MACHINE_PRECISION_USING_HALLEYS_METHOD
        // error (f_(z) - x) divided by the cumulative's derivative
        // r = (f_(z) - x) * M_SQRT2 *y M_SQRTPI * exp(0.5 * z*z);
        if (highPrecision) {
            final CumulativeNormalDistribution f_ = new CumulativeNormalDistribution();
            // error
            r = (f_.op(z)-x) * Constants.M_SQRT2 * Constants.M_SQRTPI * Math.exp(0.5*z*z);
            //  Halley's method
            z -= r/(1+0.5*z*r);
        }
        return average + z * sigma;
    }
}
