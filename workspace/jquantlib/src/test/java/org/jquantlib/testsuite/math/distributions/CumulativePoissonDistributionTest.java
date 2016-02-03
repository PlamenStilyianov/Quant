/*
 Copyright (C) 2007 Dominik Holenstein

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

package org.jquantlib.testsuite.math.distributions;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.math.distributions.CumulativePoissonDistribution;
import org.junit.Test;


/**
 *
 * @author Dominik Holenstein
 *
 */

public class CumulativePoissonDistributionTest {

    public CumulativePoissonDistributionTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
    }

    @Test
    public void testCumulativePoissonDistribution() {

        // Expected values with n = 15 trials and mean = 0.5
        final double[] testvalues = {
                0.60653065971263,
                0.90979598956895,
                0.98561232203303,
                0.99824837744371,
                0.99982788437004,
                0.99998583506268,
                0.9999989976204,
                0.99999993780309,
                0.99999999656451,
                0.99999999982903,
                0.99999999999226,
                0.99999999999968,
                0.99999999999999,
                1,
                1, };

        // Expected values with n = 15 trials and mean = 5.0
        final double[] testvalues2 = {
                0.00673794699909,
                0.040427681994512854,
                0.12465201948308113,
                0.26502591529736175,
                0.4404932850652127,
                0.61596065483306,
                0.76218346297294,
                0.86662832592999,
                0.93190636527815,
                0.9681719426938,
                0.98630473140162,
                0.99454690808699,
                0.99798114837256,
                0.99930201002086,
                0.9997737463333305, };



        for (double mean=0.0; mean<=10.0; mean+=0.5) {
            final CumulativePoissonDistribution cdf = new CumulativePoissonDistribution(mean);

            int i = 0;
            double cumCalculated = cdf.op(i);
            double logHelper = -mean;
            double cumExpected = Math.exp(logHelper);
            double error = Math.abs(cumCalculated-cumExpected);
            if (error>1.0e-13) {
                fail("expected: " + cumExpected + "  calculated: " + cumCalculated + "  error: " + error);
            }

            for (i=1; i<25; i++) {
                cumCalculated = cdf.op(i);
                if (mean == 0.0) {
                    cumExpected = 1.0;
                } else {
                    logHelper = logHelper+Math.log(mean)-Math.log(i);
                    cumExpected += Math.exp(logHelper);
                }
                error = Math.abs(cumCalculated-cumExpected);
                if (error>1.0e-12) {
                    fail("expected: " + cumExpected + "  calculated: " + cumCalculated + "  error: " + error);
                }
            }
        }

    }

}
