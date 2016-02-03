/*
 Copyright (C) 2008 Srinivas Hasti

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

/**
 *
 * @author Srinivas Hasti
 */
package org.jquantlib.testsuite.pricers;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.Option.Type;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Date;

//TODO:  Import all the testcase when MC is available
//FIXME: Rename to OldPricerTest
public class OldPricer {

    public OldPricer() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    private static class BatchData {
        Option.Type type;
        double underlying;
        double strike;
        double dividendYield;
        double riskFreeRate;
        double first;
        double length;
        int fixings;
        double volatility;
        boolean controlVariate;
        double result;
        public BatchData(final Type type, final double underlying, final double strike,
                final double dividendYield, final double riskFreeRate, final double first,
                final double length, final int fixings, final double volatility,
                final boolean controlVariate, final double result) {
            super();
            this.type = type;
            this.underlying = underlying;
            this.strike = strike;
            this.dividendYield = dividendYield;
            this.riskFreeRate = riskFreeRate;
            this.first = first;
            this.length = length;
            this.fixings = fixings;
            this.volatility = volatility;
            this.controlVariate = controlVariate;
            this.result = result;
        }
    };

    /* @Test public*/ void testMcSingleFactorPricers() {

        QL.info("Testing old-style Monte Carlo single-factor pricers...");

        final DayCounter dc = new Actual360();
        final long seed = 3456789;

        // cannot be too low, or one cannot compare numbers when
        // switching to a new default generator
        final long fixedSamples = 1023;
        final double minimumTol = 1.0e-2;

        // batch 5
        //
        // data from "Asian Option", Levy, 1997
        // in "Exotic Options: The State of the Art",
        // edited by Clewlow, Strickland

        final BatchData cases5[] = {
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,    2, 0.13, true, 1.51917595129 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,    4, 0.13, true, 1.67940165674 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,    8, 0.13, true, 1.75371215251 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,   12, 0.13, true, 1.77595318693 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,   26, 0.13, true, 1.81430536630 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,   52, 0.13, true, 1.82269246898 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,  100, 0.13, true, 1.83822402464 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,  250, 0.13, true, 1.83875059026 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0,  500, 0.13, true, 1.83750703638 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 0.0,      11.0/12.0, 1000, 0.13, true, 1.83887181884 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,    2, 0.13, true, 1.51154400089 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,    4, 0.13, true, 1.67103508506 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,    8, 0.13, true, 1.74529684070 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,   12, 0.13, true, 1.76667074564 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,   26, 0.13, true, 1.80528400613 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,   52, 0.13, true, 1.81400883891 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,  100, 0.13, true, 1.82922901451 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,  250, 0.13, true, 1.82937111773 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0,  500, 0.13, true, 1.82826193186 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 1.0/12.0, 11.0/12.0, 1000, 0.13, true, 1.82967846654 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,    2, 0.13, true, 1.49648170891 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,    4, 0.13, true, 1.65443100462 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,    8, 0.13, true, 1.72817806731 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,   12, 0.13, true, 1.74877367895 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,   26, 0.13, true, 1.78733801988 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,   52, 0.13, true, 1.79624826757 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,  100, 0.13, true, 1.81114186876 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,  250, 0.13, true, 1.81101152587 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0,  500, 0.13, true, 1.81002311939 ),
                new BatchData( Option.Type.Call, 90.0, 87.0, 0.06, 0.025, 3.0/12.0, 11.0/12.0, 1000, 0.13, true, 1.81145760308 )
        };


        for (final BatchData element : cases5) {
            final int dt = (int) element.length/(element.fixings-1);
            final double[] timeIncrements = new double[element.fixings];
            for (int i=0; i<element.fixings; i++) {
                timeIncrements[i] = i*dt + element.first;
            }

            final Date today = Date.todaysDate();
            final YieldTermStructure yeildStructureRiskFree =  org.jquantlib.testsuite.util.Utilities.flatRate(today,element.riskFreeRate, dc);
            final YieldTermStructure yeildStructureDividentYield =  org.jquantlib.testsuite.util.Utilities.flatRate(today,element.dividendYield, dc);
            final YieldTermStructure yeildStructureVolatility =  org.jquantlib.testsuite.util.Utilities.flatRate(today,element.volatility, dc);

            // TODO: Complete the test case when we have MonteCarlo

        }
    }

}
