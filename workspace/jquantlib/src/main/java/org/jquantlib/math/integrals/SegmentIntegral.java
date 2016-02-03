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

/*
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl

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
import org.jquantlib.math.Ops;

/**
 * Integral of a one-dimensional function
 * <p>
 * Given a number {@latex$ N } of intervals, the integral of a function {@latex$ f } between {@latex$ a } and {@latex$ b }
 * is calculated by means of the trapezoid formula
 *
 * {@latex[
 *  \int_{a}^{b} f \mathrm{d}x = \frac{1}{2} f(x_{0}) + f(x_{1}) + f(x_{2}) + \dots + f(x_{N-1}) + \frac{1}{2} f(x_{N})
 * } where {@latex$ x_0 = a }, {@latex$ x_N = b }, and {@latex$ x_i = a+i \Delta x } with
 * {@latex$ \Delta x = (b-a)/N }.
 *
 * @author Richard Gomes
 */
//TODO: Add test case.
//TEST the correctness of the result is tested by checking it against known good values.
public class SegmentIntegral extends Integrator {

    private final int intervals;

    //
    // public constructors
    //

    public SegmentIntegral(final int intervals) {
        super(1, 1);
        QL.require(intervals >= 1 , "at least 1 interval needed"); // TODO: message
        this.intervals = intervals;
    }


    //
    // public final methods
    //

    @Override
    public final double integrate(final Ops.DoubleOp f, final double a, final double b) {
        final double dx = (b-a)/numberOfEvaluations(); // getNumberOfEvaluations() returns intervals_
        double sum = 0.5*(f.op(a)+f.op(b));
        final double end = b - 0.5*dx;
        for (double x = a+dx; x < end; x += dx) {
            sum += f.op(x);
        }
        return sum*dx;
    }

    @Override
    public final int numberOfEvaluations() {
        return intervals;
    }

}
