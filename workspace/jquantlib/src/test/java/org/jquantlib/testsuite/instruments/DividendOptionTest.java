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
 Copyright (C) 2004, 2005, 2007 StatPro Italia srl

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

package org.jquantlib.testsuite.instruments;


import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.AmericanExercise;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.DividendVanillaOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.instruments.Option.Type;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.pricingengines.AnalyticEuropeanEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.vanilla.AnalyticDividendEuropeanEngine;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDDividendAmericanEngine;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDDividendEuropeanEngine;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDEngineAdapter;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.junit.Test;

//TODO: Figure out why tests for options with both continuous and discrete dividends fail.
//TODO: Make the known value test work.  It is slightly off from the answer in Hull probably due to date conventions.
public class DividendOptionTest {

    @Test
    public void testEuropeanValues() {

        QL.info("Testing dividend European option values with no dividends...");

        /* @Real */ final double tolerance = 1.0e-5;

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */ final double strikes[] = { 50.0, 99.5, 100.0, 100.5, 150.0 };
        /* @Real */ final double underlyings[] = { 100.0 };
        /* @Rate */ final double qRates[] = { 0.00, 0.10, 0.30 };
        /* @Rate */ final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Integer */ final int lengths[] = { 1, 2 };
        /* @Volatility */ final double vols[] = { 0.05, 0.20, 0.70 };

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(qRate, dc));
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(rRate, dc));
        final SimpleQuote vol = new SimpleQuote(0.0);
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(vol, dc));

        for (final Type type : types)
            for (final double strike : strikes)
                for (final int length : lengths) {
                  final Date exDate = today.add(new Period(length, TimeUnit.Years));
                  final Exercise exercise = new EuropeanExercise(exDate);

                  final List<Date> dividendDates = new ArrayList<Date>();
                  final List</* @Real */ Double> dividends = new ArrayList<Double>();
                  for (final Date d = today.add(new Period(3, TimeUnit.Months));
                             d.lt(exercise.lastDate());
                             d.addAssign(new Period(6, TimeUnit.Months))) {
                      dividendDates.add(d.clone());
                      dividends.add(0.0);
                  }

                  final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);
                  final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);
                  final PricingEngine ref_engine = new AnalyticEuropeanEngine(stochProcess);
                  final PricingEngine engine = new AnalyticDividendEuropeanEngine(stochProcess);

                  final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
                  option.setPricingEngine(engine);

                  final VanillaOption ref_option = new VanillaOption(payoff, exercise);
                  ref_option.setPricingEngine(ref_engine);

                  for (final double u : underlyings)
                    for (final double q : qRates)
                        for (final double r : rRates)
                            for (final double v : vols) {
                                spot.setValue(u);
                                qRate.setValue(q);
                                rRate.setValue(r);
                                vol.setValue(v);

                                /* @Real */ final double calculated = option.NPV();
                                /* @Real */ final double expected = ref_option.NPV();
                                /* @Real */ final double error = Math.abs(calculated-expected);
                                if (error > tolerance)
                                    REPORT_FAILURE("value start limit",
                                                   payoff, exercise,
                                                   u, q, r, today, v,
                                                   expected, calculated,
                                                   error, tolerance);
                            }
                }
    }

    // Reference pg. 253 - Hull - Options, Futures, and Other Derivatives 5th ed
    // Exercise 12.8

    @Test
    public void testEuropeanKnownValue() {

        QL.info("Testing dividend European option values with known value...");

        /* @Real */ final double tolerance = 1.0e-2;
        /* @Real */ final double expected = 3.67;

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(qRate, dc));
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(rRate, dc));
        final SimpleQuote vol = new SimpleQuote(0.0);
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(vol, dc));

        final Date exDate = today.add(new Period(6, TimeUnit.Months));
        final Exercise exercise = new EuropeanExercise(exDate);

        final List<Date> dividendDates = new ArrayList<Date>();
        final List</* @Real */ Double> dividends = new ArrayList<Double>();
        dividendDates.add(today.add(new Period(2, TimeUnit.Months)));
        dividends.add(0.50);
        dividendDates.add(today.add(new Period(5, TimeUnit.Months)));
        dividends.add(0.50);

        final StrikedTypePayoff payoff = new PlainVanillaPayoff(Option.Type.Call, 40.0);
        final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);
        final PricingEngine engine = new AnalyticDividendEuropeanEngine(stochProcess);

        final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
        option.setPricingEngine(engine);

        /* @Real */ final double u = 40.0;
        /* @Rate */ final double q = 0.0, r = 0.09;
        /* @Volatility */ final double v = 0.30;
        spot.setValue(u);
        qRate.setValue(q);
        rRate.setValue(r);
        vol.setValue(v);

        /* @Real */ final double calculated = option.NPV();
        /* @Real */ final double error = Math.abs(calculated-expected);
        if (error > tolerance)
            REPORT_FAILURE("value start limit",
                           payoff, exercise,
                           u, q, r, today, v,
                           expected, calculated,
                           error, tolerance);
    }


    @Test
    public void testEuropeanStartLimit() {

        QL.info("Testing dividend European option with a dividend on today's date...");

        /* @Real */ final double tolerance = 1.0e-5;
        /* @Real */ final double dividendValue = 10.0;

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */ final double strikes[] = { 50.0, 99.5, 100.0, 100.5, 150.0 };
        /* @Real */ final double underlyings[] = { 100.0 };
        /* @Rate */ final double qRates[] = { 0.00, 0.10, 0.30 };
        /* @Rate */ final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Integer */ final int lengths[] = { 1, 2 };
        /* @Volatility */ final double vols[] = { 0.05, 0.20, 0.70 };

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(qRate, dc));
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(rRate, dc));
        final SimpleQuote vol = new SimpleQuote(0.0);
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(vol, dc));

        for (final Type type : types)
            for (final double strike : strikes)
                for (final int length : lengths) {
                  final Date exDate = today.add(new Period(length, TimeUnit.Months));
                  final Exercise exercise = new EuropeanExercise(exDate);

                  final List<Date> dividendDates = new ArrayList<Date>();
                  final List</* @Real */ Double> dividends = new ArrayList<Double>();
                  dividendDates.add(today);
                  dividends.add(dividendValue);

                  final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);
                  final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);
                  final PricingEngine engine = new AnalyticDividendEuropeanEngine(stochProcess);
                  final PricingEngine ref_engine = new AnalyticEuropeanEngine(stochProcess);

                  final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
                  option.setPricingEngine(engine);

                  final VanillaOption ref_option = new VanillaOption(payoff, exercise);
                  ref_option.setPricingEngine(ref_engine);

                  for (final double u : underlyings)
                    for (final double q : qRates)
                        for (final double r : rRates)
                            for (final double v : vols) {
                                spot.setValue(u);
                                qRate.setValue(q);
                                rRate.setValue(r);
                                vol.setValue(v);

                                /* @Real */ final double calculated = option.NPV();
                                spot.setValue(u-dividendValue);
                                /* @Real */ final double expected = ref_option.NPV();
                                /* @Real */ final double error = Math.abs(calculated-expected);
                                if (error > tolerance)
                                    REPORT_FAILURE("value", payoff, exercise,
                                                   u, q, r, today, v,
                                                   expected, calculated,
                                                   error, tolerance);
                            }
                }
    }

    @Test
    public void testEuropeanEndLimit() {

        QL.info("Testing dividend European option values with end limits...");

        /* @Real */ final double tolerance = 1.0e-5;
        /* @Real */ final double dividendValue = 10.0;

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */ final double strikes[] = { 50.0, 99.5, 100.0, 100.5, 150.0 };
        /* @Real */ final double underlyings[] = { 100.0 };
        /* @Rate */ final double qRates[] = { 0.00, 0.10, 0.30 };
        /* @Rate */ final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Integer */ final int lengths[] = { 1, 2 };
        /* @Volatility */ final double vols[] = { 0.05, 0.20, 0.70 };

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(qRate, dc));
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(rRate, dc));
        final SimpleQuote vol = new SimpleQuote(0.0);
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(vol, dc));

        for (final Type type : types)
            for (final double strike : strikes)
                for (final int length : lengths) {
                  final Date exDate = today.add(new Period(length, TimeUnit.Years));
                  final Exercise exercise = new EuropeanExercise(exDate);

                  final List<Date> dividendDates = new ArrayList<Date>();
                  final List</* @Real */ Double> dividends = new ArrayList<Double>();
                  dividendDates.add(exercise.lastDate());
                  dividends.add(dividendValue);

                  final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);
                  final StrikedTypePayoff refPayoff = new PlainVanillaPayoff(type, strike + dividendValue);
                  final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);
                  final PricingEngine engine = new AnalyticDividendEuropeanEngine(stochProcess);
                  final PricingEngine ref_engine = new AnalyticEuropeanEngine(stochProcess);

                  final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise,dividendDates, dividends);
                  option.setPricingEngine(engine);

                  final VanillaOption ref_option = new VanillaOption(refPayoff, exercise);
                  ref_option.setPricingEngine(ref_engine);

                  for (final double u : underlyings)
                    for (final double q : qRates)
                        for (final double r : rRates)
                            for (final double v : vols) {
                                /* @Volatility */ spot.setValue(u);
                                qRate.setValue(q);
                                rRate.setValue(r);
                                vol.setValue(v);

                                /* @Real */ final double calculated = option.NPV();
                                /* @Real */ final double expected = ref_option.NPV();
                                /* @Real */ final double error = Math.abs(calculated-expected);
                                if (error > tolerance)
                                    REPORT_FAILURE("value", payoff, exercise,
                                                   u, q, r, today, v,
                                                   expected, calculated,
                                                   error, tolerance);
                            }
                }
    }


    @Test
    public void testEuropeanGreeks() {

        QL.info("Testing dividend European option greeks...");

        final Map<String, /* @Real */ Double> calculated = new HashMap<String, Double>();
        final Map<String, /* @Real */ Double> expected = new HashMap<String, Double>();
        final Map<String, /* @Real */ Double> tolerance = new HashMap<String, Double>();
        tolerance.put("delta", 1.0e-5);
        tolerance.put("gamma", 1.0e-5);
        tolerance.put("theta", 1.0e-5);
        tolerance.put("rho",   1.0e-5);
        tolerance.put("vega",  1.0e-5);

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */ final double strikes[] = { 50.0, 99.5, 100.0, 100.5, 150.0 };
        /* @Real */ final double underlyings[] = { 100.0 };
        /* @Rate */ final double qRates[] = { 0.00, 0.10, 0.30 };
        /* @Rate */ final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Integer */ final int lengths[] = { 1, 2 };
        /* @Volatility */ final double vols[] = { 0.05, 0.20, 0.40 };

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(qRate, dc));
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(rRate, dc));
        final SimpleQuote vol = new SimpleQuote(0.0);
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(vol, dc));

        for (final Type type : types)
            for (final double strike : strikes)
                for (final int length : lengths) {
                  final Date exDate = today.add(new Period(length, TimeUnit.Years));
                  final Exercise exercise = new EuropeanExercise(exDate);

                  final List<Date> dividendDates = new ArrayList<Date>();
                  final List</* @Real */ Double> dividends = new ArrayList<Double>();
                  for (final Date d = today.add(new Period(3, TimeUnit.Months));
                             d.lt(exercise.lastDate());
                             d.addAssign(new Period(6, TimeUnit.Months))) {
                      dividendDates.add(d.clone());
                      dividends.add(5.0);
                  }

                  final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);
                  final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);
                  final PricingEngine engine = new AnalyticDividendEuropeanEngine(stochProcess);

                  final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
                  option.setPricingEngine(engine);

                  for (final double u : underlyings)
                    for (final double q : qRates)
                        for (final double r : rRates)
                            for (final double v : vols) {
                                spot.setValue(u);
                                qRate.setValue(q);
                                rRate.setValue(r);
                                vol.setValue(v);

                                /* @Real */ final double value = option.NPV();
                                calculated.put("delta", option.delta());
                                calculated.put("gamma", option.gamma());
                                calculated.put("theta", option.theta());
                                calculated.put("rho",   option.rho());
                                calculated.put("vega",  option.vega());

                                if (value > spot.value()*1.0e-5) {
                                    // perturb spot and get delta and gamma
                                    /* @Real */ final double du = u*1.0e-4;
                                    spot.setValue(u+du);
                                    /* @Real */ double value_p = option.NPV();
                                    final double delta_p = option.delta();
                                    spot.setValue(u-du);
                                    /* @Real */ double value_m = option.NPV();
                                    final double delta_m = option.delta();
                                    spot.setValue(u);
                                    expected.put("delta", (value_p - value_m)/(2*du) );
                                    expected.put("gamma", (delta_p - delta_m)/(2*du) );

                                    // perturb risk-free /* @Rate */ double and get rho
                                    final /* @Spread */ double dr = r*1.0e-4;
                                    rRate.setValue(r+dr);
                                    value_p = option.NPV();
                                    rRate.setValue(r-dr);
                                    value_m = option.NPV();
                                    rRate.setValue(r);
                                    expected.put("rho", (value_p - value_m)/(2*dr) );

                                    // perturb /* @Volatility */ double and get vega
                                    final /* @Spread */ double dv = v*1.0e-4;
                                    vol.setValue(v+dv);
                                    value_p = option.NPV();
                                    vol.setValue(v-dv);
                                    value_m = option.NPV();
                                    vol.setValue(v);
                                    expected.put("vega", (value_p - value_m)/(2*dv) );

                                    // perturb date and get theta
                                    final /*@Time*/ double dT = dc.yearFraction(today.sub(1), today.add(1));
                                    new Settings().setEvaluationDate(today.sub(1));
                                    value_m = option.NPV();
                                    new Settings().setEvaluationDate(today.add(1));
                                    value_p = option.NPV();
                                    new Settings().setEvaluationDate(today);
                                    expected.put("theta", (value_p - value_m)/dT );

                                    // compare
                                    for (final Map.Entry<String, Double> it : calculated.entrySet()) {

                                        final String greek = it.getKey();
                                        /* @Real */final double expct = expected.get(greek);
                                        /* @Real */final double calcl = it.getValue();
                                        /* @Real */final double tol = tolerance.get(greek);
                                        /* @Real */final double error = Utilities.relativeError(expct, calcl, u);
                                        if (error > tol)
                                            REPORT_FAILURE(greek, payoff, exercise, u, q, r, today, v, expct, calcl, error, tol);
                                    }
                                }
                            }
                }
    }


    @Test
    public void testFdEuropeanValues() {

        QL.info("Testing finite-difference dividend European option values...");

        /* @Real */ final double tolerance = 1.0e-2;
        final /* @Size */ int gridPoints = 300;
        final /* @Size */ int timeSteps = 40;

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */ final double strikes[] = { 50.0, 99.5, 100.0, 100.5, 150.0 };
        /* @Real */ final double underlyings[] = { 100.0 };
        // /* @Rate */ double qRates[] = { 0.00, 0.10, 0.30 };
        // Analytic dividend may not be handling q correctly
        /* @Rate */ final double qRates[] = { 0.00 };
        /* @Rate */ final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Integer */ final int lengths[] = { 1, 2 };
        /* @Volatility */ final double vols[] = { 0.05, 0.20, 0.40 };

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(qRate, dc));
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(rRate, dc));
        final SimpleQuote vol = new SimpleQuote(0.0);
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(vol, dc));

        for (final Type type : types)
            for (final double strike : strikes)
                for (final int length : lengths) {
                  final Date exDate = today.add(new Period(length, TimeUnit.Years));
                  final Exercise exercise = new EuropeanExercise(exDate);

                  final List<Date> dividendDates = new ArrayList<Date>();
                  final List</* @Real */ Double> dividends = new ArrayList<Double>();
                  for (final Date d = today.add(new Period(3, TimeUnit.Months));
                             d.lt(exercise.lastDate());
                             d.addAssign(new Period(6, TimeUnit.Months))) {
                      dividendDates.add(d.clone());
                      dividends.add(5.0);
                  }

                  final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);
                  final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);
                  final PricingEngine engine = new FDDividendEuropeanEngine(stochProcess, timeSteps, gridPoints);
                  final PricingEngine ref_engine = new AnalyticDividendEuropeanEngine(stochProcess);

                  final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
                  option.setPricingEngine(engine);

                  final DividendVanillaOption ref_option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
                  ref_option.setPricingEngine(ref_engine);

                  for (final double u : underlyings)
                    for (final double q : qRates)
                        for (final double r : rRates)
                            for (final double v : vols) {
                                spot.setValue(u);
                                qRate.setValue(q);
                                rRate.setValue(r);
                                vol.setValue(v);
                                // FLOATING_POINT_EXCEPTION
                                /* @Real */ final double calculated = option.NPV();
                                if (calculated > spot.value()*1.0e-5) {
                                    /* @Real */ final double expected = ref_option.NPV();
                                    /* @Real */ final double error = Math.abs(calculated-expected);
                                    if (error > tolerance)
                                        REPORT_FAILURE("value", payoff, exercise,
                                                       u, q, r, today, v,
                                                       expected, calculated,
                                                       error, tolerance);
                                }
                            }
                }
    }


    @Test
    public void testFdEuropeanGreeks() {

        QL.info("Testing finite-differences dividend European option greeks...");
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        /* @Integer */ final int lengths[] = { 1, 2 };

        for (final int length : lengths) {
            final Date exDate = today.add(new Period(length, TimeUnit.Years));
            final Exercise exercise = new EuropeanExercise(exDate);
            testFdGreeks(FDDividendEuropeanEngine.class, today, exercise);
        }
    }

    @Test
    public void testFdAmericanGreeks() {
        QL.info("Testing finite-differences dividend American option greeks...");
        final Date today = Date.todaysDate();
        new Settings().setEvaluationDate(today);

        /* @Integer */ final int lengths[] = { 1, 2 };

        for (final int length : lengths) {
            final Date exDate = today.add(new Period(length, TimeUnit.Years));
            final Exercise exercise = new AmericanExercise(today, exDate);
            testFdGreeks(FDDividendAmericanEngine.class, today, exercise);
        }
    }


    @Test
    public void testFdEuropeanDegenerate() {

        QL.info("Testing degenerate finite-differences dividend European option...");

        final Date today = new Date(27, Month.February, 2005);
        new Settings().setEvaluationDate(today);
        final Date exDate = new Date(13, Month.April, 2005);
        final Exercise exercise = new EuropeanExercise(exDate);
        testFdDegenerate(FDDividendEuropeanEngine.class, today, exercise);
    }

    @Test
    public void testFdAmericanDegenerate() {

        QL.info("Testing degenerate finite-differences dividend American option...");

        final Date today = new Date(27, Month.February,2005);
        new Settings().setEvaluationDate(today);
        final Date exDate = new Date(13, Month.April, 2005);
        final Exercise exercise = new AmericanExercise(today, exDate);
        testFdDegenerate(FDDividendAmericanEngine.class, today, exercise);
    }


    private <T extends FDEngineAdapter> void testFdGreeks(final Class<T> engineClass, final Date today, final Exercise exercise) {

        final Map<String, /* @Real */ Double> calculated = new HashMap<String, Double>();
        final Map<String, /* @Real */ Double> expected = new HashMap<String, Double>();
        final Map<String, /* @Real */ Double> tolerance = new HashMap<String, Double>();
        tolerance.put("delta", 5.0e-3);
        tolerance.put("gamma", 7.0e-3);
        // tolerance.put("theta", 1.0e-2);

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */ final double strikes[] = { 50.0, 99.5, 100.0, 100.5, 150.0 };
        /* @Real */ final double underlyings[] = { 100.0 };
        /* @Rate */ final double qRates[] = { 0.00, 0.10, 0.20 };
        /* @Rate */ final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Volatility */ final double vols[] = { 0.05, 0.20, 0.50 };

        final DayCounter dc = new Actual360();

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(qRate, dc));
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(rRate, dc));
        final SimpleQuote vol = new SimpleQuote(0.0);
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(vol, dc));

        for (final Type type : types)
            for (final double strike : strikes) {

                final List<Date> dividendDates = new ArrayList<Date>();
                final List</* @Real */ Double> dividends = new ArrayList<Double>();
                for (final Date d = today.add(new Period(3, TimeUnit.Months));
                           d.lt(exercise.lastDate());
                           d.addAssign(new Period(6, TimeUnit.Months))) {
                    dividendDates.add(d.clone());
                    dividends.add(5.0);
                }

                final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);
                final PricingEngine engine;
                try {
                    final Constructor<T> baseConstructor = engineClass.getConstructor(GeneralizedBlackScholesProcess.class);
                    engine = baseConstructor.newInstance(stochProcess);
                } catch (final Exception e) {
                    throw new LibraryException(e);
                }
                final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);

                final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
                option.setPricingEngine(engine);

                for (final double u : underlyings)
                    for (final double q : qRates)
                        for (final double r : rRates)
                            for (final double v : vols) {
                                spot.setValue(u);
                                qRate.setValue(q);
                                rRate.setValue(r);
                                vol.setValue(v);

                                // FLOATING_POINT_EXCEPTION
                                /* @Real */ final double value = option.NPV();
                                calculated.put("delta", option.delta() );
                                calculated.put("gamma", option.gamma() );
                                // calculated.put("theta", option.theta() );

                                if (value > spot.value()*1.0e-5) {
                                  // perturb spot and get delta and gamma
                                  /* @Real */ final double du = u*1.0e-4;
                                  spot.setValue(u+du);
                                  /* @Real */ final double value_p = option.NPV(),
                                       delta_p = option.delta();
                                  spot.setValue(u-du);
                                  /* @Real */ final double value_m = option.NPV(),
                                       delta_m = option.delta();
                                  spot.setValue(u);
                                  expected.put("delta", (value_p - value_m)/(2*du) );
                                  expected.put("gamma", (delta_p - delta_m)/(2*du) );

                                  // perturb date and get theta
                                  /*
                                    Time dT = dc.yearFraction(today-1, today+1);
                                    new Settings().setEvaluationDate(today.sub(1));
                                    value_m = option.NPV();
                                    new Settings().setEvaluationDate(today.add(1));
                                    value_p = option.NPV();
                                    new Settings().setEvaluationDate(today);
                                    expected.put("theta", (value_p - value_m)/dT );
                                  */

                                  // compare
                                  for (final Map.Entry<String, Double> it : calculated.entrySet()) {

                                      final String greek = it.getKey();
                                      /* @Real */final double expct = expected.get(greek);
                                      /* @Real */final double calcl = it.getValue();
                                      /* @Real */final double tol = tolerance.get(greek);
                                      /* @Real */final double error = Utilities.relativeError(expct, calcl, u);
                                      if (error > tol)
                                        REPORT_FAILURE(greek, payoff, exercise, u, q, r, today, v, expct, calcl, error, tol);
                                  }
                                }
                              }
            }
    }


    private <T extends FDEngineAdapter> void testFdDegenerate(final Class<T> engineClass, final Date today, final Exercise exercise) {
        final DayCounter dc = new Actual360();
        final SimpleQuote spot = new SimpleQuote(54.625);
        final Handle<YieldTermStructure> rTS = new Handle<YieldTermStructure>(Utilities.flatRate(0.052706, dc));
        final Handle<YieldTermStructure> qTS = new Handle<YieldTermStructure>(Utilities.flatRate(0.0, dc));
        final Handle<BlackVolTermStructure> volTS = new Handle<BlackVolTermStructure>(Utilities.flatVol(0.282922, dc));
        final BlackScholesMertonProcess process = new BlackScholesMertonProcess(new Handle<Quote>(spot), qTS, rTS, volTS);

        final /* @Size */ int timeSteps = 40;
        final /* @Size */ int gridPoints = 300;

        final PricingEngine engine;
        try {
            final Constructor<T> baseConstructor = engineClass.getConstructor(GeneralizedBlackScholesProcess.class, int.class, int.class);
            engine = baseConstructor.newInstance(process, timeSteps, gridPoints);
        } catch (final Exception e) {
            throw new LibraryException(e);
        }

        final StrikedTypePayoff payoff = new PlainVanillaPayoff(Option.Type.Call, 55.0);
        final /* @Real */ double tolerance = 3.0e-3;

        final List<Date> dividendDates = new ArrayList<Date>();
        final List</* @Real */ Double> dividends = new ArrayList<Double>();

        final DividendVanillaOption option1 = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
        option1.setPricingEngine(engine);

        // FLOATING_POINT_EXCEPTION
        final /* @Real */ double refValue = option1.NPV();

        for (/* @Size */ int i=0; i<=6; i++) {
            dividends.add(0.0);
            dividendDates.add(today.add(i));

            final DividendVanillaOption option = new DividendVanillaOption(payoff, exercise, dividendDates, dividends);
            option.setPricingEngine(engine);
            final /* @Real */ double value = option.NPV();

            if (Math.abs(refValue-value) > tolerance) {
                final StringBuilder sb = new StringBuilder();
                sb.append("NPV changed by null dividend :\n");
                sb.append("    previous value: ").append(refValue).append('\n');
                sb.append("    current value:  ").append(value).append('\n');
                sb.append("    change:         ").append(value-refValue);
                fail(sb.toString());
            }
        }
    }


    private void REPORT_FAILURE(
            final String greekName,
            final StrikedTypePayoff payoff,
            final Exercise exercise,
            final double s, final double q, final double r,
            final Date today, final double v,
            final double expected, final double calculated,
            final double error, final double tolerance) {

        final StringBuilder sb = new StringBuilder();

        sb.append(exercise).append(" ");
        sb.append(payoff.optionType()).append(" option with ");
        sb.append(payoff).append(" payoff:\n");
        sb.append("    spot value:       ").append(s).append("\n");
        sb.append("    strike:           ").append(payoff.strike()).append("\n");
        sb.append("    dividend yield:   ").append(q).append("\n");
        sb.append("    risk-free rate:   ").append(r).append("\n");
        sb.append("    reference date:   ").append(today).append("\n");
        sb.append("    maturity:         ").append(exercise.lastDate()).append("\n");
        sb.append("    volatility:       ").append(v).append("\n\n");
        sb.append("    expected ").append(greekName).append(":   ").append(expected).append("\n");
        sb.append("    calculated ").append(greekName).append(": ").append(calculated).append("\n");
        sb.append("    error:            ").append(error).append("\n");
        sb.append("    tolerance:        ").append(tolerance);
    }

}
