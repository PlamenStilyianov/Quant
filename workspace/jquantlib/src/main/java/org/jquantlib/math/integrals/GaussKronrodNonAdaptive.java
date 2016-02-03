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

/*
 Copyright (C) 2007 Francois du Vignaud
 Copyright (C) 2003 Niels Elken Sonderby

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

package org.jquantlib.math.integrals;

import org.jquantlib.QL;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;

/**
 * Integral of a 1-dimensional function using the Gauss-Kronrod methods
 * <p>
 * This class provide a non-adaptive integration procedure which
 uses fixed Gauss-Kronrod abscissae to sample the integrand at
 a maximum of 87 points.  It is provided for fast integration
 of smooth functions.
<p>
 This function applies the Gauss-Kronrod 10-point, 21-point, 43-point
 and 87-point integration rules in succession until an estimate of the
 integral of f over (a, b) is achieved within the desired absolute and
 relative error limits, epsabs and epsrel. The function returns the
 final approximation, result, an estimate of the absolute error,
 abserr and the number of function evaluations used, neval. The
 Gauss-Kronrod rules are designed in such a way that each rule uses
 all the results of its predecessors, in order to minimize the total
 number of function evaluations.

 * @author Ueli Hofstetter
 */
public class GaussKronrodNonAdaptive extends KronrodIntegral {

    private double relativeAccuracy_;

    public double relativeAccuracy() {
        return relativeAccuracy_;
    }

    public void setRelativeAccuracy(final double relativeAccuracy) {
        this.relativeAccuracy_ = relativeAccuracy;
    }

    public GaussKronrodNonAdaptive(final double absoluteAccuracy, final int maxEvaluations, final double relativeAccuracy) {
        super(absoluteAccuracy, maxEvaluations);
        this.relativeAccuracy_ = relativeAccuracy;
    }

    @Override
    public double integrate(final Ops.DoubleOp f, final double a, final double b) {
        final double fv1[] = new double[5];
        final double fv2[] = new double[5];
        final double fv3[] = new double[5];
        final double fv4[] = new double[5];
        final double savfun[] = new double[21]; /* array of function values which have been computed */
        double res10, res21, res43, res87; /* 10, 21, 43 and 87 point results */
        double err;
        double resAbs; /* approximation to the integral of abs(f) */
        double resasc; /* approximation to the integral of abs(f-i/(b-a)) */
        double result;
        int k;

        QL.require(a < b , "b must be greater than a"); // TODO: message

        final double halfLength = 0.5 * (b - a);
        final double center = 0.5 * (b + a);
        final double fCenter = f.op(center);

        // Compute the integral using the 10- and 21-point formula.

        res10 = 0;
        res21 = w21b[5] * fCenter;
        resAbs = w21b[5] * Math.abs(fCenter);

        for (k = 0; k < 5; k++) {
            final double abscissa = halfLength * x1[k];
            final double fval1 = f.op(center + abscissa);
            final double fval2 = f.op(center - abscissa);
            final double fval = fval1 + fval2;
            res10 += w10[k] * fval;
            res21 += w21a[k] * fval;
            resAbs += w21a[k] * (Math.abs(fval1) + Math.abs(fval2));
            savfun[k] = fval;
            fv1[k] = fval1;
            fv2[k] = fval2;
        }

        for (k = 0; k < 5; k++) {
            final double abscissa = halfLength * x2[k];
            final double fval1 = f.op(center + abscissa);
            final double fval2 = f.op(center - abscissa);
            final double fval = fval1 + fval2;
            res21 += w21b[k] * fval;
            resAbs += w21b[k] * (Math.abs(fval1) + Math.abs(fval2));
            savfun[k + 5] = fval;
            fv3[k] = fval1;
            fv4[k] = fval2;
        }

        result = res21 * halfLength;
        resAbs *= halfLength;
        final double mean = 0.5 * res21;
        resasc = w21b[5] * Math.abs(fCenter - mean);

        for (k = 0; k < 5; k++) {
            resasc += w21a[k] * (Math.abs(fv1[k] - mean) + Math.abs(fv2[k] - mean))
            + w21b[k] * (Math.abs(fv3[k] - mean) + Math.abs(fv4[k] - mean));
        }

        err = rescaleError((res21 - res10) * halfLength, resAbs, resasc);
        resasc *= halfLength;

        // test for convergence.
        if (err < absoluteAccuracy() || err < relativeAccuracy() * Math.abs(result)) {
            setAbsoluteError(err);
            setNumberOfEvaluations(21);
            return result;
        }

        /* compute the integral using the 43-point formula. */

        res43 = w43b[11] * fCenter;

        for (k = 0; k < 10; k++) {
            res43 += savfun[k] * w43a[k];
        }

        for (k = 0; k < 11; k++) {
            final double abscissa = halfLength * x3[k];
            final double fval = (f.op(center + abscissa) + f.op(center - abscissa));
            res43 += fval * w43b[k];
            savfun[k + 10] = fval;
        }

        // test for convergence.

        result = res43 * halfLength;
        err = rescaleError((res43 - res21) * halfLength, resAbs, resasc);

        if (err < absoluteAccuracy() || err < relativeAccuracy() * Math.abs(result)) {
            setAbsoluteError(err);
            setNumberOfEvaluations(43);
            return result;
        }

        /* compute the integral using the 87-point formula. */

        res87 = w87b[22] * fCenter;

        for (k = 0; k < 21; k++) {
            res87 += savfun[k] * w87a[k];
        }

        for (k = 0; k < 22; k++) {
            final double abscissa = halfLength * x4[k];
            res87 += w87b[k] * (f.op(center + abscissa) + f.op(center - abscissa));
        }

        // test for convergence.
        result = res87 * halfLength;
        err = rescaleError((res87 - res43) * halfLength, resAbs, resasc);

        setAbsoluteError(err);
        setNumberOfEvaluations(87);
        return result;
    }

    static double rescaleError(double err, final double resultAbs, final double resultAsc) {
        err = Math.abs(err);
        if (resultAsc != 0 && err != 0) {
            final double scale = Math.pow((200 * err / resultAsc), 1.5);
            if (scale < 1) {
                err = resultAsc * scale;
            } else {
                err = resultAsc;
            }
        }
        if (resultAbs > Constants.QL_MIN_POSITIVE_REAL / (50 * Constants.QL_EPSILON)) {
            final double min_err = 50 * Constants.QL_EPSILON * resultAbs;
            if (min_err > err) {
                err = min_err;
            }
        }
        return err;
    }

}
