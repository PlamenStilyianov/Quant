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

package org.jquantlib.testsuite.instruments;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.BarrierOption;
import org.jquantlib.instruments.BarrierType;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.barrier.AnalyticBarrierEngine;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.Date;
import org.junit.Test;

public class BarrierOptionTest {


    public BarrierOptionTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }


    @Test
    public void testHaugValues() {

        QL.info("Testing barrier options against Haug's values...");

        final NewBarrierOptionData values[] = {
                //
                // The data below are from "Option pricing formulas", E.G. Haug, McGraw-Hill 1998 pag. 72
                //
                //     barrierType, barrier, rebate,         type, strike,     s,    q,    r,    t,    v,  result, tol
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0, Option.Type.Call,     90, 100.0, 0.04, 0.08, 0.50, 0.25,  9.0246, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0, Option.Type.Call,    100, 100.0, 0.04, 0.08, 0.50, 0.25,  6.7924, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0, Option.Type.Call,    110, 100.0, 0.04, 0.08, 0.50, 0.25,  4.8759, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0, Option.Type.Call,     90, 100.0, 0.04, 0.08, 0.50, 0.25,  3.0000, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0, Option.Type.Call,    100, 100.0, 0.04, 0.08, 0.50, 0.25,  3.0000, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0, Option.Type.Call,    110, 100.0, 0.04, 0.08, 0.50, 0.25,  3.0000, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0, Option.Type.Call,     90, 100.0, 0.04, 0.08, 0.50, 0.25,  2.6789, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0, Option.Type.Call,    100, 100.0, 0.04, 0.08, 0.50, 0.25,  2.3580, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0, Option.Type.Call,    110, 100.0, 0.04, 0.08, 0.50, 0.25,  2.3453, 1.0e-4),

                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.25,  7.7627, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  4.0109, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.25,  2.0576, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.25, 13.8333, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  7.8494, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.25,  3.9795, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.25, 14.1112, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  8.4482, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.25,  4.5910, 1.0e-4),

                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  8.8334, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  7.0285, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  5.4137, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  3.0000, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  3.0000, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  3.0000, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  2.6341, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  2.4389, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  2.4315, 1.0e-4),

                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  9.0093, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  5.1370, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  2.8517, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.30, 14.8816, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  9.2045, 1.0e-4),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  5.3043, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0, Option.Type.Call,    90, 100.0, 0.04, 0.08, 0.50, 0.30, 15.2098, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0, Option.Type.Call,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  9.7278, 1.0e-4),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0, Option.Type.Call,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  5.8350, 1.0e-4),

                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.25,  2.2798, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  2.2947, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.25,  2.6252, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.25,  3.0000, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  3.0000, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.25,  3.0000, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.25,  3.7760, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  5.4932, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.25,  7.5187, 1.0e-4 ),

                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.25,  2.9586, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  6.5677, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.25, 11.9752, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.25,  2.2845, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  5.9085, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.25, 11.6465, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.25,  1.4653, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.25,  3.3721, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.25,  7.0846, 1.0e-4 ),

                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  2.4170, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  2.4258, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,    95.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  2.6246, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  3.0000, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  3.0000, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownOut,   100.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  3.0000, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  4.2293, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  5.8032, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpOut,     105.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  7.5649, 1.0e-4 ),

                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  3.8769, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  7.7989, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,     95.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.30, 13.3078, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  3.3328, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  7.2636, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.DownIn,    100.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.30, 12.9713, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0,  Option.Type.Put,    90, 100.0, 0.04, 0.08, 0.50, 0.30,  2.0658, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0,  Option.Type.Put,   100, 100.0, 0.04, 0.08, 0.50, 0.30,  4.4226, 1.0e-4 ),
                new NewBarrierOptionData( BarrierType.UpIn,      105.0,    3.0,  Option.Type.Put,   110, 100.0, 0.04, 0.08, 0.50, 0.30,  8.3686, 1.0e-4 ),

                //
                //  Data from "Going to Extreme: Correcting Simulation Bias in Exotic Option Valuation"
                //  D.R. Beaglehole, P.H. Dybvig and G. Zhou
                //  Financial Analysts Journal; Jan / Feb 1997; 53, 1
                //
                //    barrierType, barrier, rebate,         type, strike,     s,    q,    r,    t,    v,  result, tol
                //---- new NewBarrierOptionData( BarrierType.DownOut,    45.0,    0.0,  Option.Type.PUT,     50,  50.0,-0.05, 0.10, 0.25, 0.50,   4.032, 1.0e-3 ),
                //---- new NewBarrierOptionData( BarrierType.DownOut,    45.0,    0.0,  Option.Type.PUT,     50,  50.0,-0.05, 0.10, 1.00, 0.50,   5.477, 1.0e-3 )
        };

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final SimpleQuote           spot  = new SimpleQuote(0.0);
        final SimpleQuote           qRate = new SimpleQuote(0.0);
        final YieldTermStructure    qTS   = Utilities.flatRate(today, qRate, dc);
        final SimpleQuote           rRate = new SimpleQuote(0.0);
        final YieldTermStructure    rTS   = Utilities.flatRate(today, rRate, dc);
        final SimpleQuote           vol   = new SimpleQuote(0.0);
        final BlackVolTermStructure volTS = Utilities.flatVol(today, vol, dc);

        for (final NewBarrierOptionData value : values) {
            final Date exDate = today.add( timeToDays(value.t) );
            final Exercise exercise = new EuropeanExercise(exDate);

            spot.setValue(value.s);
            qRate.setValue(value.q);
            rRate.setValue(value.r);
            vol.setValue(value.v);

            final StrikedTypePayoff payoff = new PlainVanillaPayoff(value.type, value.strike);

            final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(
                    new Handle<Quote>(spot),
                    new Handle<YieldTermStructure>(qTS),
                    new Handle<YieldTermStructure>(rTS),
                    new Handle<BlackVolTermStructure>(volTS));
            final PricingEngine engine = new AnalyticBarrierEngine(stochProcess);

            final BarrierOption barrierOption = new BarrierOption(value.barrierType, value.barrier, value.rebate, payoff, exercise);
            barrierOption.setPricingEngine(engine);

            final double calculated = barrierOption.NPV();
            final double expected = value.result;
            final double error = Math.abs(calculated-expected);
            if (error>value.tol) {
                REPORT_FAILURE("value", value.barrierType, value.barrier,
                        value.rebate, payoff, exercise, value.s,
                        value.q, value.r, today, value.v,
                        expected, calculated, error, value.tol);
            }

        }
    }

    @Test
    public void testBabsiriValues() {
        QL.info("Testing barrier options against Babsiri's values...");

        /**
         * Data from
         * "Simulating Path-Dependent Options: A New Approach"
         * - M. El Babsiri and G. Noel
         * Journal of Derivatives; Winter 1998; 6, 2
         */
        final BarrierOptionData values[] = {
                new BarrierOptionData( BarrierType.DownIn,   0.10,   100,  90,   0.07187,  0.0),
                new BarrierOptionData( BarrierType.DownIn,   0.15,   100,  90,   0.60638,  0.0),
                new BarrierOptionData( BarrierType.DownIn,   0.20,   100,  90,   1.64005,  0.0),
                new BarrierOptionData( BarrierType.DownIn,   0.25,   100,  90,   2.98495,  0.0),
                new BarrierOptionData( BarrierType.DownIn,   0.30,   100,  90,   4.50952,  0.0),
                new BarrierOptionData( BarrierType.UpIn,     0.10,   100,  110,  4.79148,  0.0),
                new BarrierOptionData( BarrierType.UpIn,     0.15,   100,  110,   7.08268,  0.0 ),
                new BarrierOptionData( BarrierType.UpIn,     0.20,   100,  110,   9.11008,  0.0 ),
                new BarrierOptionData( BarrierType.UpIn,     0.25,   100,  110,  11.06148,  0.0 ),
                new BarrierOptionData( BarrierType.UpIn,     0.30,   100,  110,  12.98351,  0.0 )
        };

        final double underlyingPrice = 100.0;
        final double rebate = 0.0;
        final double r = 0.05;
        final double q = 0.02;

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final Quote                 underlying = new SimpleQuote(underlyingPrice);
        final Quote                 qH_SME     = new SimpleQuote(q);
        final YieldTermStructure    qTS        = Utilities.flatRate(today, qH_SME, dc);
        final Quote                 rH_SME     = new SimpleQuote(r);
        final YieldTermStructure    rTS        = Utilities.flatRate(today, rH_SME, dc);
        final SimpleQuote           volatility = new SimpleQuote(0.10);
        final BlackVolTermStructure volTS      = Utilities.flatVol(today, volatility, dc);

        final Date exDate = today.add(360);
        final Exercise exercise = new EuropeanExercise(exDate);

        for (final BarrierOptionData value : values) {
            volatility.setValue(value.volatility);
            final StrikedTypePayoff callPayoff = new PlainVanillaPayoff(Option.Type.Call, value.strike);

            final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(
                    new Handle<Quote>(underlying),
                    new Handle<YieldTermStructure>(qTS),
                    new Handle<YieldTermStructure>(rTS),
                    new Handle<BlackVolTermStructure>(volTS));

            final PricingEngine engine = new AnalyticBarrierEngine(stochProcess);

            final BarrierOption barrierCallOption = new BarrierOption(value.barrierType, value.barrier, rebate, callPayoff, exercise);
            barrierCallOption.setPricingEngine(engine);

            final double calculated = barrierCallOption.NPV();
            final double expected = value.callValue;
            final double error = Math.abs(calculated - expected);
            final double maxErrorAllowed = 1.0e-3;

            if (error > maxErrorAllowed) {
                REPORT_FAILURE("value", value.barrierType, value.barrier, rebate, callPayoff, exercise, underlyingPrice, q,
                        r, today, value.volatility, expected, calculated, error, maxErrorAllowed);
            }
        }
    }

    @Test
    public void testBeagleholeValues() {

        QL.info("Testing barrier options against Beaglehole's values...");

        /**
         * Data from
         * "Going to Extreme: Correcting Simulation Bias in Exotic Option Valuation"
         * - D.R. Beaglehole, P.H. Dybvig and G. Zhou
         * Financial Analysts Journal; Jan / Feb 1997; 53, 1
         */
        final BarrierOptionData values[] = {
                new BarrierOptionData(BarrierType.DownOut, 0.50,   50,      45,  5.477,  0.0)
        };

        final double underlyingPrice = 50.0;
        final double rebate = 0.0;
        final double r = Math.log(1.1);
        final double q = 0.00;

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final Quote                 underlying = new SimpleQuote(underlyingPrice);
        final Quote                 qH_SME     = new SimpleQuote(q);
        final YieldTermStructure    qTS        = Utilities.flatRate(today, qH_SME, dc);
        final Quote                 rH_SME     = new SimpleQuote(r);
        final YieldTermStructure    rTS        = Utilities.flatRate(today, rH_SME, dc);
        final SimpleQuote           volatility = new SimpleQuote(0.10);
        final BlackVolTermStructure volTS      = Utilities.flatVol(today, volatility, dc);

        final Date exDate = today.add(360);

        final Exercise exercise = new EuropeanExercise(exDate);

        for (final BarrierOptionData value : values) {
            volatility.setValue(value.volatility);
            final StrikedTypePayoff callPayoff = new PlainVanillaPayoff(Option.Type.Call, value.strike);

            final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(
                    new Handle<Quote>(underlying),
                    new Handle<YieldTermStructure>(qTS),
                    new Handle<YieldTermStructure>(rTS),
                    new Handle<BlackVolTermStructure>(volTS));
            final PricingEngine engine = new AnalyticBarrierEngine(stochProcess);


            final BarrierOption barrierCallOption = new BarrierOption(value.barrierType, value.barrier, rebate, callPayoff, exercise);
            barrierCallOption.setPricingEngine(engine);

            final double calculated = barrierCallOption.NPV();
            final double expected = value.callValue;
            final double error = Math.abs(calculated - expected);
            final double maxErrorAllowed = 1.0e-3;

            if (error > maxErrorAllowed) {
                REPORT_FAILURE("value", value.barrierType, value.barrier, rebate, callPayoff, exercise, underlyingPrice, q,
                        r, today, value.volatility, expected, calculated, error, maxErrorAllowed);
            }
        }


        //TODO: MC Barrier engine not implemented yet.
        /*

        final double maxMcRelativeErrorAllowed = 0.01;
        final int timeSteps = 1;
        final boolean brownianBridge = true;
        final boolean antitheticVariate = false;
        final boolean controlVariate = false;
        final int requiredSamples = 131071; //2^17-1
        final double requiredTolerance;
        final int maxSamples = 1048575; // 2^20-1
        final boolean isBiased = false;
        final double seed = 10;

        boost::shared_ptr<PricingEngine> mcEngine(
                new MCBarrierEngine<LowDiscrepancy>(timeSteps, brownianBridge,
                                                antitheticVariate, controlVariate,
                                                requiredSamples, requiredTolerance,
                                                maxSamples, isBiased, seed));

            barrierCallOption.setPricingEngine(mcEngine);
            calculated = barrierCallOption.NPV();
            error = std::fabs(calculated-expected)/expected;
            if (error>maxMcRelativeErrorAllowed) {
                REPORT_FAILURE("value", values[i].type, values[i].barrier,
                               rebate, callPayoff, exercise, underlyingPrice,
                               q, r, today, values[i].volatility,
                               expected, calculated, error,
                               maxMcRelativeErrorAllowed);
            }
         */
    }



// http://bugs.jquantlib.org/view.php?id=459
//
//    void BarrierOptionTest::testPerturbative() {
//        BOOST_MESSAGE("Testing perturbative engine for barrier options...");
//
//        Real S = 100.0;
//        Real rebate = 0.0;
//        Rate r = 0.03;
//        Rate q = 0.02;
//
//        DayCounter dc = Actual360();
//        Date today = Date::todaysDate();
//
                // ---- This is future Java code, to be inserted when this code were translated
                //FIXME: http://bugs.jquantlib.org/view.php?id=460
                //new Settings().setEvaluationDate(today);
//
//        boost::shared_ptr<SimpleQuote> underlying(new SimpleQuote(S));
//        boost::shared_ptr<YieldTermStructure> qTS = flatRate(today, q, dc);
//        boost::shared_ptr<YieldTermStructure> rTS = flatRate(today, r, dc);
//
//        std::vector<Date> dates(2);
//        std::vector<Volatility> vols(2);
//
//        dates[0] = today + 90;  vols[0] = 0.105;
//        dates[1] = today + 180; vols[1] = 0.11;
//
//        boost::shared_ptr<BlackVolTermStructure> volTS(
//                                  new BlackVarianceCurve(today, dates, vols, dc));
//
//        boost::shared_ptr<BlackScholesMertonProcess> stochProcess(
//            new BlackScholesMertonProcess(Handle<Quote>(underlying),
//                                          Handle<YieldTermStructure>(qTS),
//                                          Handle<YieldTermStructure>(rTS),
//                                          Handle<BlackVolTermStructure>(volTS)));
//
//        Real strike = 101.0;
//        Real barrier = 101.0;
//        Date exDate = today+180;
//
//        boost::shared_ptr<Exercise> exercise(new EuropeanExercise(exDate));
//        boost::shared_ptr<StrikedTypePayoff> payoff(
//                                     new PlainVanillaPayoff(Option::Put, strike));
//
//        BarrierOption option(Barrier::UpOut, barrier, rebate, payoff, exercise);
//
//        Natural order = 0;
//        bool zeroGamma = false;
//        boost::shared_ptr<PricingEngine> engine(
//             new PerturbativeBarrierOptionEngine(stochProcess, order, zeroGamma));
//
//        option.setPricingEngine(engine);
//
//        Real calculated = option.NPV();
//        Real expected = 0.897365;
//        Real tolerance = 1.0e-6;
//        if (std::fabs(calculated-expected) > tolerance) {
//            BOOST_ERROR("Failed to reproduce expected value"
//                        << "\n  calculated: " << std::setprecision(5) << calculated
//                        << "\n  expected:   " << std::setprecision(5) << expected);
//        }
//
//        order = 1;
//        engine = boost::shared_ptr<PricingEngine>(
//             new PerturbativeBarrierOptionEngine(stochProcess, order, zeroGamma));
//
//        option.setPricingEngine(engine);
//
//        calculated = option.NPV();
//        expected = 0.894374;
//        if (std::fabs(calculated-expected) > tolerance) {
//            BOOST_ERROR("Failed to reproduce expected value"
//                        << "\n  calculated: " << std::setprecision(5) << calculated
//                        << "\n  expected:   " << std::setprecision(5) << expected);
//        }
//
//        order = 2;
//        engine = boost::shared_ptr<PricingEngine>(
//             new PerturbativeBarrierOptionEngine(stochProcess, order, zeroGamma));
//
//        option.setPricingEngine(engine);
//
//        calculated = option.NPV();
//        expected = 0.894375;
//        if (std::fabs(calculated-expected) > tolerance) {
//            BOOST_ERROR("Failed to reproduce expected value"
//                        << "\n  calculated: " << std::setprecision(5) << calculated
//                        << "\n  expected:   " << std::setprecision(5) << expected);
//        }
//    }


    private int timeToDays(/*@Time*/ final double t) {
        return (int) (t*360+0.5);
    }


    private void REPORT_FAILURE(final String greekName, final BarrierType barrierType,
            final double barrier, final double rebate, final StrikedTypePayoff payoff,
            final Exercise exercise, final double s, final double q, final double r, final Date today,
            final double v, final double expected, final double calculated,
            final double error, final double tolerance) {
        fail("\n" + barrierType + " " + exercise
                + payoff.optionType() + " option with "
                + payoff.getClass().getSimpleName() + " payoff:\n"
                + "    underlying value: " +  s + "\n"
                + "    strike:           " + payoff.strike() + "\n"
                + "    barrier:          " + barrier + "\n"
                + "    rebate:           " + rebate + "\n"
                + "    dividend yield:   " + q + "\n"
                + "    risk-free rate:   " + r + "\n"
                + "    reference date:   " + today + "\n"
                + "    maturity:         " + exercise.lastDate() + "\n"
                + "    volatility:       " + v  + "\n\n"
                + "    expected   " + greekName + ": " + expected + "\n"
                + "    calculated " + greekName + ": " + calculated + "\n"
                + "    error:            " + error + "\n"
                + "    tolerance:        " + tolerance);
    }


    private static class NewBarrierOptionData {

        private final BarrierType barrierType;
        private final double barrier;
        private final double rebate;
        private final Option.Type type;
        private final double strike;
        private final double s;        // spot
        private final double q;        // dividend
        private final double r;        // risk-free rate
        private final double t;        // time to maturity
        private final double v;  // volatility
        private final double result;   // result
        private final double tol;      // tolerance

        public NewBarrierOptionData(
                final BarrierType barrierType,
                final double barrier,
                final double rebate,
                final Option.Type type,
                final double strike,
                final double s,        // spot
                final double q,        // dividend
                final double r,        // risk-free rate
                final double t,        // time to maturity
                final double v,  // volatility
                final double result,   // result
                final double tol      // tolerance
        ) {
            this.barrierType = barrierType;
            this.barrier = barrier;
            this.rebate = rebate;
            this.type = type;
            this.strike = strike;
            this.s = s;
            this.q = q;
            this.r = r;
            this.t = t;
            this.v = v;
            this.result = result;
            this.tol = tol;
        }
    }


    private static class BarrierOptionData {

        private final BarrierType barrierType;
        private final double volatility;
        private final double strike;
        private final double barrier;
        private final double callValue;
        private final double putValue;

        public BarrierOptionData(
                final BarrierType barrierType,
                final double volatility,
                final double strike,
                final double barrier,
                final double callValue,
                final double putValue) {
            this.barrierType = barrierType;
            this.volatility = volatility;
            this.strike = strike;
            this.barrier = barrier;
            this.callValue = callValue;
            this.putValue = putValue;
        }

    }

}
