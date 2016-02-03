/*
Copyright (C) 2008 Richard Gomes
Copyright (C) 2009 John Martin

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
Copyright (C) 2005, 2006, 2007 StatPro Italia srl

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

package org.jquantlib.termstructures.yieldcurves;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.math.Constants;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;

/**
 * Zero-curve traits
 *
 * @author Richard Gomes
 * @author John Martin
 */
public class ZeroYield implements Traits {

    private static final double avgRate = .05;

    //TODO: think how constructor must look like
    public ZeroYield() {
        QL.validateExperimentalMode();
    }

    @Override
    public double initialValue(final YieldTermStructure curve) {
        return avgRate;
    }

    @Override
    public double initialGuess() {
        return avgRate;
    }

    @Override
    public double guess(final YieldTermStructure c, final Date d) {
        return c.zeroRate(d, c.dayCounter(), Compounding.Continuous, Frequency.Annual, true).rate();
    }

    @Override
    public double minValueAfter(final int i, final double[] data) {
        if (new Settings().isNegativeRates()) {
            return -3.0;
        }
        return Constants.QL_EPSILON;
    }

    @Override
    public double maxValueAfter(final int i, final double[] data) {
        // no constraints.
        // We choose as max a value very unlikely to be exceeded.
        return 3.0;
    }

    @Override
    public void updateGuess(final double[] data, final double value, final int i) {
        data[i] = value;
        if (i == 1) {
            data[0] = value; // first point is updated as well
        }
    }

    @Override
    public boolean dummyInitialValue() {
        return true;
    }

    @Override
    public Date initialDate(final YieldTermStructure curve) {
        return curve.referenceDate();
    }

    @Override
    public int maxIterations() {
        return 30;
    }

}