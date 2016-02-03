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
 Copyright (C) 2003, 2004 Ferdinando Ametrano
 Copyright (C) 2005 StatPro Italia srl

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.AverageType;
import org.jquantlib.instruments.ContinuousAveragingAsianOption;
import org.jquantlib.instruments.DiscreteAveragingAsianOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.Option.Type;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.asian.AnalyticContinuousGeometricAveragePriceAsianEngine;
import org.jquantlib.pricingengines.asian.AnalyticDiscreteGeometricAveragePriceAsianEngine;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.junit.Test;

/**
 * @author <Richard Gomes>
 */
public class AsianOptionTest {

    public AsianOptionTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
    }


    @Test
    public void testAnalyticDiscreteGeometricAverage() {

        QL.info("Testing analytic discrete geometric average-price Asians...");

        // data from "Implementing Derivatives Model",
        // Clewlow, Strickland, p.118-123

        final Date today = new Settings().evaluationDate();

        final DayCounter dc = new Actual360();

        QL.info("Today: " + today);

        final SimpleQuote spot = new SimpleQuote(100.0);
        final SimpleQuote qRate = new SimpleQuote(0.03);
        final YieldTermStructure qTS = Utilities.flatRate(today, qRate.value(), dc);
        final SimpleQuote rRate = new SimpleQuote(0.06);
        final YieldTermStructure rTS = Utilities.flatRate(today, rRate.value(), dc);
        final SimpleQuote vol = new SimpleQuote(0.20);
        final BlackVolTermStructure volTS = Utilities.flatVol(today, vol.value(), dc);

        final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(new Handle<Quote>(spot),
                new Handle<YieldTermStructure>(qTS), new Handle<YieldTermStructure>(rTS), new Handle<BlackVolTermStructure>(volTS));

        final PricingEngine engine = new AnalyticDiscreteGeometricAveragePriceAsianEngine(stochProcess);

        final AverageType averageType = AverageType.Geometric;
        /* @Real */final double runningAccumulator = 1.0;
        /* @Size */final int pastFixings = 0;
        /* @Size */final int futureFixings = 10;
        final Option.Type type = Option.Type.Call;
        /* @Real */final double strike = 100.0;
        final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);

        final Date exerciseDate = today.clone().addAssign(360);
        final Exercise exercise = new EuropeanExercise(exerciseDate);

        QL.info("Exercise: " + exerciseDate);
        QL.info("Df: " + rTS.discount(exerciseDate));
        QL.info("DivDf: " + qTS.discount(exerciseDate));

        final List<Date> fixingDates = new ArrayList<Date>(futureFixings);
        final int dt = (int) (360.0 / (futureFixings) + 0.5);
        fixingDates.add(today.clone().addAssign(dt));
        for (int j = 1; j < futureFixings; j++) {
            final Date prevDate = fixingDates.get(j - 1);
            fixingDates.add(prevDate.clone().addAssign(dt));
        }

        QL.info("Average Dates:\n");
        for (final Date d : fixingDates) {
            QL.info(d.toString());
        }

        final DiscreteAveragingAsianOption option = new DiscreteAveragingAsianOption(
                averageType, runningAccumulator, pastFixings, fixingDates, payoff, exercise);
        option.setPricingEngine(engine);

        /* @Real */final double calculated = option.NPV();
        /* @Real */final double expected = 5.3425606635;

        /* @Real */final double tolerance = 1e-10;
        if (Math.abs(calculated - expected) > tolerance) {
            reportFailure("value", averageType, runningAccumulator, pastFixings, fixingDates, payoff, exercise, spot.value(),
                    qRate.value(), rRate.value(), today, vol.value(), expected, calculated, tolerance);
        }
    }


    @Test
    public void testAnalyticDiscreteGeometricAveragePriceGreeks() {

        QL.info("Testing discrete-averaging geometric Asian greeks...");

        final Map<String, Double> tolerance = new HashMap<String, Double>();
        tolerance.put("delta", 1.0e-5);
        tolerance.put("gamma", 1.0e-5);
        tolerance.put("theta", 1.0e-5);
        tolerance.put("rho", 1.0e-5);
        tolerance.put("divRho", 1.0e-5);
        tolerance.put("vega", 1.0e-5);

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */final double underlyings[] = { 100.0 };
        /* @Real */final double strikes[] = { 90.0, 100.0, 110.0 };
        /* @Real */final double qRates[] = { 0.04, 0.05, 0.06 };
        /* @Real */final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Integer */final int lengths[] = { 1, 2 };
        /* @Volatility */final double vols[] = { 0.11, 0.50, 1.20 };

        final DayCounter dc = new Actual360();

        final SimpleQuote           spot  = new SimpleQuote(0.0);
        final SimpleQuote           qRate = new SimpleQuote(0.0);
        final YieldTermStructure    qTS   = Utilities.flatRate(qRate, dc);
        final SimpleQuote           rRate = new SimpleQuote(0.0);
        final YieldTermStructure    rTS   = Utilities.flatRate(rRate, dc);
        final SimpleQuote           vol   = new SimpleQuote(0.0);
        final BlackVolTermStructure volTS = Utilities.flatVol(vol, dc);

        final BlackScholesMertonProcess process = new BlackScholesMertonProcess(
                new Handle<Quote>(spot),
                new Handle<YieldTermStructure>(qTS),
                new Handle<YieldTermStructure>(rTS),
                new Handle<BlackVolTermStructure>(volTS));

        final Date today = new Settings().evaluationDate();

        for (final Type type : types) {
            for (final double strike : strikes) {
                for (final int length : lengths) {

                    final Date exerciseDate = new Date(today.dayOfMonth(), today.month(), today.year() + length);
                    final EuropeanExercise maturity = new EuropeanExercise(exerciseDate);

                    final PlainVanillaPayoff payoff = new PlainVanillaPayoff(type, strike);

                    final double runningAverage = 120;
                    final int pastFixings = 1;

                    final List<Date> fixingDates = new ArrayList<Date>();

                    final Date d = today.clone();
                    final Period THREEMONTH = new Period(3, TimeUnit.Months);
                    d.addAssign(new Period(3, TimeUnit.Months));
                    for (d.addAssign(THREEMONTH); d.le(maturity.lastDate()); d.addAssign(THREEMONTH)) {
                        fixingDates.add(d.clone());
                    }

                    final PricingEngine engine = new AnalyticDiscreteGeometricAveragePriceAsianEngine(process);

                    final DiscreteAveragingAsianOption option = new DiscreteAveragingAsianOption(
                            AverageType.Geometric, runningAverage, pastFixings, fixingDates, payoff, maturity);
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
                                    final Map<String, Double> calculated = new HashMap<String, Double>();
                                    calculated.put("delta", option.delta());
                                    calculated.put("gamma", option.gamma());
                                    calculated.put("theta", option.theta());
                                    calculated.put("rho", option.rho());
                                    calculated.put("divRho", option.dividendRho());
                                    calculated.put("vega", option.vega());

                                    final Map<String, Double> expected = new HashMap<String, Double>();
                                    if (value > spot.value() * 1.0e-5) {
                                        // perturb spot and get delta and gamma
                                        final double du = u * 1.0e-4;
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
                                        final double dr = r * 1.0e-4;
                                        rRate.setValue(r + dr);
                                        value_p = option.NPV();
                                        rRate.setValue(r - dr);
                                        value_m = option.NPV();
                                        rRate.setValue(r);
                                        expected.put("rho", (value_p - value_m) / (2 * dr));

                                        final double dq = q * 1.0e-4;
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

                                        // perturb date and get theta
                                        final Date yesterday = today.sub(1);
                                        final Date tomorrow = today.add(1);
                                        final double dT = dc.yearFraction(yesterday, tomorrow);
                                        new Settings().setEvaluationDate(yesterday);
                                        value_m = option.NPV();
                                        new Settings().setEvaluationDate(tomorrow);
                                        value_p = option.NPV();
                                        expected.put("theta", (value_p - value_m) / dT);
                                        new Settings().setEvaluationDate(today);
                                        // compare
                                        for (final Entry<String, Double> greek : calculated.entrySet()) {
                                            final double expct = expected.get(greek.getKey());
                                            final double calcl = calculated.get(greek.getKey());
                                            final double tol = tolerance.get(greek.getKey());
                                            final double error = Utilities.relativeError(expct, calcl, u);
                                            if (error > tol) {
                                                reportFailure(greek.getKey(), AverageType.Geometric, runningAverage, pastFixings,
                                                        new ArrayList<Date>(), payoff, maturity, u, q, r, today, v, expct, calcl,
                                                        tol);
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

    @Test
    public void testAnalyticContinuousGeometricAveragePrice() {
        QL.info("Testing analytic continuous geometric average-price Asians...");
        final DayCounter dc = new Actual360();
        // data from "Option Pricing Formulas", Haug, pag.96-97

        final Date today = new Settings().evaluationDate();

        QL.info("Today: " + today);

        final SimpleQuote           spot  = new SimpleQuote(80.0);
        final SimpleQuote           qRate = new SimpleQuote(-0.03);
        final YieldTermStructure    qTS   = Utilities.flatRate(today, qRate, dc);
        final SimpleQuote           rRate = new SimpleQuote(0.05);
        final YieldTermStructure    rTS   = Utilities.flatRate(today, rRate, dc);
        final SimpleQuote           vol   = new SimpleQuote(0.20);
        final BlackVolTermStructure volTS = Utilities.flatVol(today, vol, dc);

        final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(
                new Handle<Quote>(spot), new Handle<YieldTermStructure>(qTS),
                new Handle<YieldTermStructure>(rTS), new Handle<BlackVolTermStructure>(volTS));
        final PricingEngine engine = new AnalyticContinuousGeometricAveragePriceAsianEngine(stochProcess);

        final AverageType averageType = AverageType.Geometric;
        final Option.Type type = Option.Type.Put;
        /* @Real */final double strike = 85.0;

        final Date exerciseDate = today.clone().addAssign(90);

        /* @Size */int pastFixings = Integer.MAX_VALUE;
        /* @Real */double runningAccumulator = Double.NaN;

        final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);
        final Exercise exercise = new EuropeanExercise(exerciseDate);

        final ContinuousAveragingAsianOption option = new ContinuousAveragingAsianOption(averageType, payoff, exercise);
        option.setPricingEngine(engine);

        /* @Real */double calculated = option.NPV();
        /* @Real */final double expected = 4.6922;
        /* @Real */double tolerance = 1.0e-4;

        if (Math.abs(calculated - expected) > tolerance) {
            reportFailure("value", averageType, runningAccumulator, pastFixings, new ArrayList<Date>(), payoff, exercise, spot
                    .value(), qRate.value(), rRate.value(), today, vol.value(), expected, calculated, tolerance);

        }
        // trying to approximate the continuous version with the discrete version
        runningAccumulator = 1.0;
        pastFixings = 0;

        final List<Date> fixingDates = new ArrayList<Date>(91);

        for (/* @Size */int i = 0; i < 91; i++) {
            fixingDates.add(today.clone().addAssign(i));
        }

        final PricingEngine engine2 = new AnalyticDiscreteGeometricAveragePriceAsianEngine(stochProcess);
        final DiscreteAveragingAsianOption option2 = new DiscreteAveragingAsianOption(
                averageType, runningAccumulator, pastFixings, fixingDates, payoff, exercise);
        option2.setPricingEngine(engine2);

        calculated = option2.NPV();
        tolerance = 3.0e-3;
        if (Math.abs(calculated - expected) > tolerance) {
            reportFailure("value", averageType, runningAccumulator, pastFixings, fixingDates, payoff, exercise, spot.value(),
                    qRate.value(), rRate.value(), today, vol.value(), expected, calculated, tolerance);
        }
    }


    @Test
    public void testAnalyticContinuousGeometricAveragePriceGreeks() {
        QL.info("Testing analytic continuous geometric average-price Asian greeks...");

        final Map<String, /* @Real */Double> tolerance = new HashMap<String, Double>();
        tolerance.put("delta", 1.0e-5);
        tolerance.put("gamma", 1.0e-5);
        tolerance.put("theta", 1.0e-5);
        tolerance.put("rho", 1.0e-5);
        tolerance.put("divRho", 1.0e-5);
        tolerance.put("vega", 1.0e-5);

        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */final double underlyings[] = { 100.0 };
        /* @Real */final double strikes[] = { 90.0, 100.0, 110.0 };
        /* @Real */final double qRates[] = { 0.04, 0.05, 0.06 };
        /* @Real */final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Integer */final int lengths[] = { 1, 2 };
        /* @Volatility */final double vols[] = { 0.11, 0.50, 1.20 };

        final DayCounter dc = new Actual360();

        final SimpleQuote           spot  = new SimpleQuote(0.0);
        final SimpleQuote           qRate = new SimpleQuote(0.0);
        final YieldTermStructure    qTS   = Utilities.flatRate(qRate, dc);
        final SimpleQuote           rRate = new SimpleQuote(0.0);
        final YieldTermStructure    rTS   = Utilities.flatRate(rRate, dc);
        final SimpleQuote           vol   = new SimpleQuote(0.0);
        final BlackVolTermStructure volTS = Utilities.flatVol(vol, dc);

        final BlackScholesMertonProcess stochProcess = new BlackScholesMertonProcess(
                new Handle<Quote>(spot),
                new Handle<YieldTermStructure>(qTS),
                new Handle<YieldTermStructure>(rTS),
                new Handle<BlackVolTermStructure>(volTS));

        final PricingEngine engine = new AnalyticContinuousGeometricAveragePriceAsianEngine(stochProcess);



        final Date today = new Settings().evaluationDate();

        for (final Type type : types) {
            for (final double strike : strikes) {
                for (final int length : lengths) {

                    final Date exerciseDate = new Date(today.dayOfMonth(), today.month(), today.year() + length);
                    final EuropeanExercise maturity = new EuropeanExercise(exerciseDate);
                    final PlainVanillaPayoff payoff = new PlainVanillaPayoff(type, strike);

                    final ContinuousAveragingAsianOption option = new ContinuousAveragingAsianOption(
                            AverageType.Geometric, payoff, maturity);
                    option.setPricingEngine(engine);

                    /* @Size */final int pastFixings = Integer.MAX_VALUE;
                    /* @Real */final double runningAverage = Double.NaN;

                    for (final double u : underlyings) {
                        for (final double q : qRates) {
                            for (final double r : rRates) {
                                for (final double v : vols) {

                                    spot.setValue(u);
                                    qRate.setValue(q);
                                    rRate.setValue(r);
                                    vol.setValue(v);

                                    /* @Real */final double value = option.NPV();
                                    final Map<String, Double> calculated = new HashMap<String, Double>();
                                    calculated.put("delta", option.delta());
                                    calculated.put("gamma", option.gamma());
                                    calculated.put("theta", option.theta());
                                    calculated.put("rho", option.rho());
                                    calculated.put("divRho", option.dividendRho());
                                    calculated.put("vega", option.vega());

                                    final Map<String, Double> expected = new HashMap<String, Double>();
                                    if (value > spot.value() * 1.0e-5) {
                                        // perturb spot and get delta and gamma
                                        /* @Real */final double du = u * 1.0e-4;
                                        spot.setValue(u + du);
                                        /* @Real */double value_p = option.NPV();
                                        /* @Real */final double delta_p = option.delta();
                                        spot.setValue(u - du);
                                        /* @Real */double value_m = option.NPV();
                                        /* @Real */final double delta_m = option.delta();
                                        spot.setValue(u);
                                        expected.put("delta", (value_p - value_m) / (2 * du));
                                        expected.put("gamma", (delta_p - delta_m) / (2 * du));

                                        // perturb rates and get rho and dividend rho
                                        /* @Spread */final double dr = r * 1.0e-4;
                                        rRate.setValue(r + dr);
                                        value_p = option.NPV();
                                        rRate.setValue(r - dr);
                                        value_m = option.NPV();
                                        rRate.setValue(r);
                                        expected.put("rho", (value_p - value_m) / (2 * dr));

                                        /* @Spread */final double dq = q * 1.0e-4;
                                        qRate.setValue(q + dq);
                                        value_p = option.NPV();
                                        qRate.setValue(q - dq);
                                        value_m = option.NPV();
                                        qRate.setValue(q);
                                        expected.put("divRho", (value_p - value_m) / (2 * dq));

                                        // perturb volatility and get vega
                                        /* @Volatility */final double dv = v * 1.0e-4;
                                        vol.setValue(v + dv);
                                        value_p = option.NPV();
                                        vol.setValue(v - dv);
                                        value_m = option.NPV();
                                        vol.setValue(v);
                                        expected.put("vega", (value_p - value_m) / (2 * dv));

                                        // perturb date and get theta
                                        final Date yesterday = today.sub(1);
                                        final Date tomorrow = today.add(1);
                                        /* @Time */final double dT = dc.yearFraction(yesterday, tomorrow);
                                        new Settings().setEvaluationDate(yesterday);
                                        value_m = option.NPV();
                                        new Settings().setEvaluationDate(tomorrow);
                                        value_p = option.NPV();
                                        expected.put("theta", (value_p - value_m) / dT);
                                        new Settings().setEvaluationDate(today);
                                        // compare
                                        for (final Entry<String, Double> greek : calculated.entrySet()) {
                                            /* @Real */final double expct = expected.get(greek.getKey());
                                            /* @Real */final double calcl = calculated.get(greek.getKey());
                                            /* @Real */final double tol = tolerance.get(greek.getKey());
                                            /* @Real */final double error = Utilities.relativeError(expct, calcl, u);
                                            if (error > tol) {
                                                reportFailure(greek.getKey(), AverageType.Geometric, runningAverage, pastFixings,
                                                        new ArrayList<Date>(), payoff, maturity, u, q, r, today, v, expct, calcl,
                                                        tol);
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


    private void reportFailure(final String greekName, final AverageType averageType, final double runningAccumulator, final int pastFixings,
            final List<Date> fixingDates, final StrikedTypePayoff payoff, final Exercise exercise, final double s, final double q, final double r, final Date today,
            final double v, final double expected, final double calculated, final double tolerance) {

        fail(exercise + " Asian option with " + averageType + " and " + payoff + " payoff:\n" + "    running variable: "
                + runningAccumulator + "\n" + "    past fixings:     " + pastFixings + "\n" + "    future fixings:   "
                + fixingDates.size() + "\n" + "    underlying value: " + s + "\n" + "    strike:           " + payoff.strike()
                + "\n" + "    dividend yield:   " + q + "\n" + "    risk-free rate:   " + r + "\n" + "    reference date:   "
                + today + "\n" + "    maturity:         " + exercise.lastDate() + "\n" + "    volatility:       " + v + "\n\n"
                + "    expected   " + greekName + ": " + expected + "\n" + "    calculated " + greekName + ": " + calculated + "\n"
                + "    error:            " + Math.abs(expected - calculated) + "\n" + "    tolerance:        " + tolerance);

    }

}
