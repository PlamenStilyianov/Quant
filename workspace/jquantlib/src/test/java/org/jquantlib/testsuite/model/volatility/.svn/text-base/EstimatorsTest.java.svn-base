/*
 Copyright (C) 2007 Richard Gomes

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
 Copyright (C) 2006 Joseph Wang

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

package org.jquantlib.testsuite.model.volatility;

import static org.junit.Assert.assertNotNull;

import org.jquantlib.QL;
import org.jquantlib.model.volatility.ConstantEstimator;
import org.jquantlib.model.volatility.SimpleLocalEstimator;
import org.jquantlib.model.volatility.VolatilityCompositor;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.TimeSeries;
import org.junit.Test;

public class EstimatorsTest {

    private final TimeSeries<Double> ts;

    public EstimatorsTest() {
        QL.info("Testing volatility model construction...");

        final Date[] dates = new Date[] {
                new Date(25, Month.March, 2005),
                new Date(29, Month.March, 2005),
                new Date(15, Month.March, 2005),
                new Date(21, Month.March, 2005),
                new Date(27, Month.March, 2005) };

        final double[] values = new double[] { 1.2, 2.3, 0.3, 2.0, 2.5 };

        this.ts = new TimeSeries<Double>(Double.class);
        for (int i=0; i<dates.length; i++) {
            ts.put(dates[i], values[i]);
        }
    }

    @Test
    public void testSECalculate() {
        final SimpleLocalEstimator sle = new SimpleLocalEstimator(1/360.0);
        final TimeSeries<Double> locale = sle.calculate(ts);
        assertNotNull(locale) ;
    }

    @Test
    public void testCECalculate() {
        final SimpleLocalEstimator       sle = new SimpleLocalEstimator(1/360.0);
        final TimeSeries<Double>      locale = sle.calculate(ts);
        final VolatilityCompositor        ce = new ConstantEstimator(1);
        final TimeSeries<Double>       value = ce.calculate(locale);
        assertNotNull(value) ;
    }

}
