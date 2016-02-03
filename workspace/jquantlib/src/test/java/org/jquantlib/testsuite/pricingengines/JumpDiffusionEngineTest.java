package org.jquantlib.testsuite.pricingengines;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.AssetOrNothingPayoff;
import org.jquantlib.instruments.CashOrNothingPayoff;
import org.jquantlib.instruments.EuropeanOption;
import org.jquantlib.instruments.GapPayoff;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.Option.Type;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.vanilla.JumpDiffusionEngine;
import org.jquantlib.processes.Merton76Process;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.Date;
import org.junit.Assert;
import org.junit.Test;

public class JumpDiffusionEngineTest {

    public JumpDiffusionEngineTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }


    @Test
    public void testMerton76() {
        QL.info("Testing Merton 76 jump-diffusion model for European options...");

        // The data below are from
        //"Option pricing formulas", E.G. Haug, McGraw-Hill 1998, pag 9
        //
        // Haug use the arbitrary truncation criterium of 11 terms in the sum,
        // which doesn't guarantee convergence up to 1e-2.
        // Using Haug's criterium Haug's values have been correctly reproduced.
        // the following values have the right 1e-2 accuracy: any value different
        // from Haug has been noted.
        final HaugMertonData values[] = {
                //        type, strike,   spot,    q,    r,    t,  vol, int, gamma, value, tol
                // gamma = 0.25, strike = 80
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.25, 20.67, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.25, 21.74, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.25, 23.63, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.25, 20.65, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.25, 21.70, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.25, 23.61, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.25, 20.64, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.25, 21.70, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.25, 23.61, 1e-2) , // Haug 23.28
                // gamma = 0.25, strike = 90
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.25, 11.00, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.25, 12.74, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.25, 15.40, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.25, 10.98, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.25, 12.75, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.25, 15.42, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.25, 10.98, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.25, 12.75, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.25, 15.42, 1e-2) , // Haug 15.20
                // gamma = 0.25, strike = 100
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.25,  3.42, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.25,  5.88, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.25,  8.95, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.25,  3.51, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.25,  5.96, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.25,  9.02, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.25,  3.53, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.25,  5.97, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.25,  9.03, 1e-2) , // Haug 8.89
                // gamma = 0.25, strike = 110
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.25,  0.55, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.25,  2.11, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.25,  4.67, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.25,  0.56, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.25,  2.16, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.25,  4.73, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.25,  0.56, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.25,  2.17, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.25,  4.74, 1e-2) , // Haug 4.66
                // gamma = 0.25, strike = 120
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.25,  0.10, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.25,  0.64, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.25,  2.23, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.25,  0.06, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.25,  0.63, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.25,  2.25, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.25,  0.05, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.25,  0.62, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.25,  2.25, 1e-2) , // Haug 2.21

                // gamma = 0.50, strike = 80
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.50, 20.72, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.50, 21.83, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.50, 23.71, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.50, 20.66, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.50, 21.73, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.50, 23.63, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.50, 20.65, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.50, 21.71, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.50, 23.61, 1e-2) , // Haug 23.28
                // gamma = 0.50, strike = 90
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.50, 11.04, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.50, 12.72, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.50, 15.34, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.50, 11.02, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.50, 12.76, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.50, 15.41, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.50, 11.00, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.50, 12.75, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.50, 15.41, 1e-2) , // Haug 15.18
                // gamma = 0.50, strike = 100
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.50,  3.14, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.50,  5.58, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.50,  8.71, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.50,  3.39, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.50,  5.87, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.50,  8.96, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.50,  3.46, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.50,  5.93, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.50,  9.00, 1e-2) , // Haug 8.85
                // gamma = 0.50, strike = 110
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.50,  0.53, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.50,  1.93, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.50,  4.42, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.50,  0.58, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.50,  2.11, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.50,  4.67, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.50,  0.57, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.50,  2.14, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.50,  4.71, 1e-2) , // Haug 4.62
                // gamma = 0.50, strike = 120
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.50,  0.19, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.50,  0.71, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.50,  2.15, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.50,  0.10, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.50,  0.66, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.50,  2.23, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.50,  0.07, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.50,  0.64, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.50,  2.24, 1e-2) , // Haug 2.19

                // gamma = 0.75, strike = 80
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.75, 20.79, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.75, 21.96, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.75, 23.86, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.75, 20.68, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.75, 21.78, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.75, 23.67, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.75, 20.66, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.75, 21.74, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  80.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.75, 23.64, 1e-2) , // Haug 23.30
                // gamma = 0.75, strike = 90
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.75, 11.11, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.75, 12.75, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.75, 15.30, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.75, 11.09, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.75, 12.78, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.75, 15.39, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.75, 11.04, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.75, 12.76, 1e-2) ,
                new HaugMertonData( Option.Type.Call,  90.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.75, 15.40, 1e-2) , // Haug 15.17
                // gamma = 0.75, strike = 100
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.75,  2.70, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.75,  5.08, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.75,  8.24, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.75,  3.16, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.75,  5.71, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.75,  8.85, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.75,  3.33, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.75,  5.85, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 100.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.75,  8.95, 1e-2) , // Haug 8.79
                // gamma = 0.75, strike = 110
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.75,  0.54, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.75,  1.69, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.75,  3.99, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.75,  0.62, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.75,  2.05, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.75,  4.57, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.75,  0.60, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.75,  2.11, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 110.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.75,  4.66, 1e-2) , // Haug 4.56
                // gamma = 0.75, strike = 120
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25, 1.0,  0.75,  0.29, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25, 1.0,  0.75,  0.84, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25, 1.0,  0.75,  2.09, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25, 5.0,  0.75,  0.15, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25, 5.0,  0.75,  0.71, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25, 5.0,  0.75,  2.21, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.10, 0.25,10.0,  0.75,  0.11, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.25, 0.25,10.0,  0.75,  0.67, 1e-2) ,
                new HaugMertonData( Option.Type.Call, 120.00, 100.00, 0.00, 0.08, 0.50, 0.25,10.0,  0.75,  2.23, 1e-2)  // Haug 2.17
        };

        final Date today = new Settings().evaluationDate();
        final DayCounter dc = new Actual360();

        final SimpleQuote           spot  = new SimpleQuote(0.0);
        final SimpleQuote           qRate = new SimpleQuote(0.0);
        final YieldTermStructure    qTS   = Utilities.flatRate(today, qRate, dc);
        final SimpleQuote           rRate = new SimpleQuote(0.0);
        final YieldTermStructure    rTS   = Utilities.flatRate(today, rRate, dc);
        final SimpleQuote           vol   = new SimpleQuote(0.0);
        final BlackVolTermStructure volTS = Utilities.flatVol(today, vol, dc);

        final SimpleQuote jumpIntensity = new SimpleQuote(0.0);
        final SimpleQuote meanLogJump   = new SimpleQuote(0.0);
        final SimpleQuote jumpVol       = new SimpleQuote(0.0);

        final Merton76Process stochProcess = new Merton76Process(
                new Handle<Quote>(spot),
                new Handle<YieldTermStructure>(qTS),
                new Handle<YieldTermStructure>(rTS),
                new Handle<BlackVolTermStructure>(volTS),
                new Handle<Quote>(jumpIntensity),
                new Handle<Quote>(meanLogJump),
                new Handle<Quote>(jumpVol));
        final PricingEngine engine = new JumpDiffusionEngine(stochProcess);

        for (final HaugMertonData value : values) {
            final StrikedTypePayoff payoff = new PlainVanillaPayoff(value.type, value.strike);

            final Date exDate = today.add((int) (value.t * 360 + 0.5));
            final Exercise exercise = new EuropeanExercise(exDate);

            spot.setValue(value.s);
            qRate.setValue(value.q);
            rRate.setValue(value.r);

            jumpIntensity.setValue(value.jumpIntensity);

            // delta in Haug's notation
            final double /* @Real */jVol = value.v * Math.sqrt(value.gamma / value.jumpIntensity);
            jumpVol.setValue(jVol);

            // z in Haug's notation
            final double /* @Real */diffusionVol = value.v * Math.sqrt(1.0 - value.gamma);
            vol.setValue(diffusionVol);

            // Haug is assuming zero meanJump
            final double /* @Real */meanJump = 0.0;
            meanLogJump.setValue(Math.log(1.0 + meanJump) - 0.5 * jVol * jVol);

            final double totalVol = Math.sqrt(value.jumpIntensity * jVol * jVol + diffusionVol * diffusionVol);
            final double volError = Math.abs(totalVol - value.v);

            if (volError >= 1.0e-13)
                throw new ArithmeticException(" mismatch");

            final EuropeanOption option = new EuropeanOption(payoff, exercise);
            option.setPricingEngine(engine);

            final double /* @Real */calculated = option.NPV();
            final double /* @Real */error = Math.abs(calculated - value.result);
            if (error > value.tol) {
                REPORT_FAILURE_2("value", payoff, exercise, value.s, value.q, value.r, today, value.v,
                        value.jumpIntensity, value.gamma, value.result, calculated, error, value.tol);
            }
        }
    }


    private void REPORT_FAILURE_2(final String greekName, final StrikedTypePayoff payoff, final Exercise exercise, final double s, final double q, final double r,
            final Date today, final double v, final double intensity, final double gamma, final double expected, final double calculated, final double error, final double tolerance) {

        Assert.fail(exercise + " " + payoff.optionType() + " option with " + payoff + " payoff:\n" + "    underlying value: " + s
                + "\n" + "    strike:           " + payoff.strike() + "\n" + "    dividend yield:   " + q + "\n"
                + "    risk-free rate:   " + r + "\n" + "    reference date:   " + today + "\n" + "    maturity:         "
                + exercise.lastDate() + "\n" + "    volatility:       " + v + "\n" + "    intensity:        " + intensity + "\n"
                + "    gamma:            " + gamma + "\n\n" + "    expected   " + greekName + ": " + expected + "\n"
                + "    calculated " + greekName + ": " + calculated + "\n" + "    error:            " + error + "\n"
                + "    tolerance:        " + tolerance);
    }


    @Test
    public void testGreeks() {
        QL.info("Testing jump-diffusion option greeks...");

        final Map<String, Double> calculated = new HashMap<String, Double>();
        final Map<String, Double> expected = new HashMap<String, Double>();
        final Map<String, Double> tolerance = new HashMap<String, Double>();
        tolerance.put("delta", 1.0e-4);
        tolerance.put("gamma", 1.0e-4);
        tolerance.put("theta", 1.1e-4);
        tolerance.put("rho", 1.0e-4);
        tolerance.put("divRho", 1.0e-4);
        tolerance.put("vega", 1.0e-4);

        final Option.Type types[] = { Option.Type.Put, Option.Type.Call };

        final double strikes[] = { 50.0, 100.0, 150.0 };
        final double underlyings[] = { 100.0 };
        final double qRates[] = { -0.05, 0.0, 0.05 };
        final double rRates[] = { 0.0, 0.01, 0.2 };

        // The testsuite check fails if a too short maturity is chosen(i.e. 1 year).
        // The problem is in the theta calculation. With the finite difference(fd) method
        // we might get values too close to the jump steps, invalidating the fd methodology
        // for calculating greeks.

        final double residualTimes[] = { 5.0 };
        final double vols[] = { 0.11 };
        final double jInt[] = { 1.0, 5.0 };
        final double mLJ[] = { -0.20, 0.0, 0.20 };
        final double jV[] = { 0.01, 0.25 };

        final DayCounter dc = new Actual360();
        new Settings().setEvaluationDate(Date.todaysDate());
        final Date today = new Settings().evaluationDate();

        final SimpleQuote           spot  = new SimpleQuote(0.0);
        final SimpleQuote           qRate = new SimpleQuote(0.0);
        final YieldTermStructure    qTS   = Utilities.flatRate(qRate, dc);
        final SimpleQuote           rRate = new SimpleQuote(0.0);
        final YieldTermStructure    rTS   = Utilities.flatRate(rRate, dc);
        final SimpleQuote           vol   = new SimpleQuote(0.0);
        final BlackVolTermStructure volTS = Utilities.flatVol(vol, dc);

        final SimpleQuote jumpIntensity = new SimpleQuote(0.0);
        final SimpleQuote meanLogJump   = new SimpleQuote(0.0);
        final SimpleQuote jumpVol       = new SimpleQuote(0.0);

        final Merton76Process stochProcess = new Merton76Process(
                new Handle<Quote>(spot),
                new Handle<YieldTermStructure>(qTS),
                new Handle<YieldTermStructure>(rTS),
                new Handle<BlackVolTermStructure>(volTS),
                new Handle<Quote>(jumpIntensity),
                new Handle<Quote>(meanLogJump),
                new Handle<Quote>(jumpVol));

        // The jumpdiffusionengine greeks are very sensitive to the convergence level.
        // A tolerance of 1.0e-08 is usually sufficient to get reasonable results
        final PricingEngine engine = new JumpDiffusionEngine(stochProcess, 1e-08);

        for (final Type type : types) {
            for (final double strike : strikes) {
                for (final double element : jInt) {
                    jumpIntensity.setValue(element);
                    for (final double element2 : mLJ) {
                        meanLogJump.setValue(element2);
                        for (final double element3 : jV) {
                            jumpVol.setValue(element3);
                            for (final double residualTime : residualTimes) {
                                final Date exDate = today.add((int) (residualTime * 360 + 0.5));
                                final Exercise exercise = new EuropeanExercise(exDate);

                                for (int kk = 0; kk < 1; kk++) {
                                    StrikedTypePayoff payoff = null;
                                    // option to check
                                    if (kk == 0) {
                                        payoff = new PlainVanillaPayoff(type, strike);
                                    } else if (kk == 1) {
                                        payoff = new CashOrNothingPayoff(type, strike, 100.0);
                                    } else if (kk == 2) {
                                        payoff = new AssetOrNothingPayoff(type, strike);
                                    } else if (kk == 3) {
                                        payoff = new GapPayoff(type, strike, 100.0);
                                    }
                                    final EuropeanOption option = new EuropeanOption(payoff, exercise);
                                    option.setPricingEngine(engine);

                                    for (final double u : underlyings) {
                                        for (final double q : qRates) {
                                            for (final double r : rRates) {
                                                for (final double v : vols) {
                                                    spot.setValue(u);
                                                    qRate.setValue(q);
                                                    rRate.setValue(r);
                                                    vol.setValue(v);

                                                    final double value = option.NPV();
                                                    calculated.put("delta", option.delta());
                                                    calculated.put("gamma", option.gamma());
                                                    calculated.put("theta", option.theta());
                                                    calculated.put("rho", option.rho());
                                                    calculated.put("divRho", option.dividendRho());
                                                    calculated.put("vega", option.vega());

                                                    if (value > spot.value() * 1.0e-5) {
                                                        // perturb spot and get delta and gamma
                                                        final double du = u * 1.0e-5;
                                                        spot.setValue(u + du);
                                                        double value_p = option.NPV();
                                                        final double delta_p = option.delta();
                                                        spot.setValue(u - du);
                                                        double value_m = option.NPV();
                                                        final double delta_m = option.delta();
                                                        spot.setValue(u);
                                                        expected.put("delta", (value_p - value_m) / (2 * du));
                                                        expected.put("gamma", (delta_p - delta_m) / (2 * du));

                                                        // perturb rates and get rho and dividend rho
                                                        final double dr = 1.0e-5;
                                                        rRate.setValue(r + dr);
                                                        value_p = option.NPV();
                                                        rRate.setValue(r - dr);
                                                        value_m = option.NPV();
                                                        rRate.setValue(r);
                                                        expected.put("rho", (value_p - value_m) / (2 * dr));

                                                        final double dq = 1.0e-5;
                                                        qRate.setValue(q + dq);
                                                        value_p = option.NPV();
                                                        qRate.setValue(q - dq);
                                                        value_m = option.NPV();
                                                        qRate.setValue(q);
                                                        expected.put("divRho", (value_p - value_m) / (2 * dq));

                                                        // perturb volatility and get vega
                                                        final double dv = v * 1.0e-4;
                                                        vol.setValue(v + dv);
                                                        value_p = option.NPV();
                                                        vol.setValue(v - dv);
                                                        value_m = option.NPV();
                                                        vol.setValue(v);
                                                        expected.put("vega", (value_p - value_m) / (2 * dv));

                                                        final Date yesterday = today.sub(1);
                                                        final Date tomorrow = today.add(1);
                                                        final double dT = dc.yearFraction(yesterday, tomorrow);
                                                        new Settings().setEvaluationDate(yesterday);
                                                        value_m = option.NPV();
                                                        new Settings().setEvaluationDate(tomorrow);
                                                        value_p = option.NPV();
                                                        new Settings().setEvaluationDate(today);
                                                        expected.put("theta", (value_p - value_m) / dT);

                                                        // compare
                                                        for (final Entry<String, Double> it : calculated.entrySet()) {
                                                            final String greek = it.getKey();
                                                            final double expct = expected.get(greek);
                                                            final double calcl = calculated.get(greek);
                                                            final double tol = tolerance.get(greek);

                                                            final double error = Math.abs(expct-calcl);
                                                            if (error > tol) {

                                                                // System.err.println("type="+type.toString());
                                                                // System.err.println("strike="+strike);
                                                                // System.err.println("element ="+element);
                                                                // System.err.println("element2="+element2);
                                                                // System.err.println("element3="+element3);
                                                                // System.err.println("residualTime="+residualTime);
                                                                // System.err.println("u="+u);
                                                                // System.err.println("q="+q);
                                                                // System.err.println("r="+r);
                                                                // System.err.println("v="+v);

                                                                // TODO improve error message
                                                                // REPORT_FAILURE(greek, payoff, exercise, u, q, r, today, v, expct,
                                                                // calcl, error, tol);
                                                                Assert.fail("Failed on greek: " + greek);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //
    // private inner classes
    //

    static private class HaugMertonData {
        public Option.Type type;
        public double strike;
        public double s;        // spot
        public double q;        // dividend
        public double r;        // risk-free rate
        public double t;        // time to maturity
        public double v;        // volatility
        public double jumpIntensity;
        public double gamma;
        public double result;   // result
        public double tol;      // tolerance

        public HaugMertonData(  final Option.Type type,
                final double strike,
                final double spot,
                final double q,
                final double r,
                final double t,
                final double vol,
                final double intensity,
                final double gamma,
                final double value,
                final double tol){
            this.type = type;
            this.strike = strike;
            this.s = spot;
            this.q = q;
            this.r = r;
            this.t = t;
            this.v = vol;
            this.jumpIntensity = intensity;
            this.gamma = gamma;
            this.result = value;
            this.tol = tol;
        }
    }

}
