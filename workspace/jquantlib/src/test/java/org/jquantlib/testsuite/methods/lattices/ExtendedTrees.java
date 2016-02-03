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
 Copyright (C) 2003, 2007 Ferdinando Ametrano
 Copyright (C) 2003, 2007, 2008 StatPro Italia srl

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

package org.jquantlib.testsuite.methods.lattices;

import java.util.HashMap;
import java.util.Map;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.experimental.lattices.ExtendedAdditiveEQPBinomialTree;
import org.jquantlib.experimental.lattices.ExtendedCoxRossRubinstein;
import org.jquantlib.experimental.lattices.ExtendedJarrowRudd;
import org.jquantlib.experimental.lattices.ExtendedJoshi4;
import org.jquantlib.experimental.lattices.ExtendedLeisenReimer;
import org.jquantlib.experimental.lattices.ExtendedTian;
import org.jquantlib.experimental.lattices.ExtendedTrigeorgis;
import org.jquantlib.instruments.EuropeanOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.Option.Type;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.AnalyticEuropeanEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.vanilla.BinomialVanillaEngine;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.Date;
import org.junit.Test;

public class ExtendedTrees {

    private enum EngineType {
        Analytic, JR, CRR, EQP, TGEO, TIAN, LR, JOSHI
    };

    public ExtendedTrees() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
    }

    @Test
    public void testJRBinomialEngines() {

        QL.info("Testing time-dependent JR binomial European engines against analytic results...");

        final EngineType engine = EngineType.JR;
        /* @Size */final int steps = 251;
        /* @Size */final int samples = Constants.NULL_INTEGER;
        final Map<String, /* @Real */Double> relativeTol = new HashMap<String, Double>();
        relativeTol.put("value", 0.002);
        relativeTol.put("delta", 1.0e-3);
        relativeTol.put("gamma", 1.0e-4);
        relativeTol.put("theta", 0.03);
        testEngineConsistency(engine, steps, samples, relativeTol);
    }

    @Test
    public void testCRRBinomialEngines() {

        QL.info("Testing time-dependent CRR binomial European engines against analytic results...");

        final EngineType engine = EngineType.CRR;
        /* @Size */final int steps = 501;
        /* @Size */final int samples = Constants.NULL_INTEGER;
        final Map<String, /* @Real */Double> relativeTol = new HashMap<String, Double>();
        relativeTol.put("value", 0.02);
        relativeTol.put("delta", 1.0e-3);
        relativeTol.put("gamma", 1.0e-4);
        relativeTol.put("theta", 0.03);
        testEngineConsistency(engine, steps, samples, relativeTol);
    }

    @Test
    public void testEQPBinomialEngines() {

        QL.info("Testing time-dependent EQP binomial European engines against analytic results...");

        final EngineType engine = EngineType.EQP;
        /* @Size */final int steps = 501;
        /* @Size */final int samples = Constants.NULL_INTEGER;
        final Map<String, /* @Real */Double> relativeTol = new HashMap<String, Double>();
        relativeTol.put("value", 0.02);
        relativeTol.put("delta", 1.0e-3);
        relativeTol.put("gamma", 1.0e-4);
        relativeTol.put("theta", 0.03);
        testEngineConsistency(engine, steps, samples, relativeTol);
    }

    @Test
    public void testTGEOBinomialEngines() {

        QL.info("Testing time-dependent TGEO binomial European engines against analytic results...");

        final EngineType engine = EngineType.TGEO;
        /* @Size */final int steps = 251;
        /* @Size */final int samples = Constants.NULL_INTEGER;
        final Map<String, /* @Real */Double> relativeTol = new HashMap<String, Double>();
        relativeTol.put("value", 0.002);
        relativeTol.put("delta", 1.0e-3);
        relativeTol.put("gamma", 1.0e-4);
        relativeTol.put("theta", 0.03);
        testEngineConsistency(engine, steps, samples, relativeTol);
    }

    @Test
    public void testTIANBinomialEngines() {

        QL.info("Testing time-dependent TIAN binomial European engines against analytic results...");

        final EngineType engine = EngineType.TIAN;
        /* @Size */final int steps = 251;
        /* @Size */final int samples = Constants.NULL_INTEGER;
        final Map<String, /* @Real */Double> relativeTol = new HashMap<String, Double>();
        relativeTol.put("value", 0.002);
        relativeTol.put("delta", 1.0e-3);
        relativeTol.put("gamma", 1.0e-4);
        relativeTol.put("theta", 0.03);
        testEngineConsistency(engine, steps, samples, relativeTol);
    }

    @Test
    public void testLRBinomialEngines() {

        QL.info("Testing time-dependent LR binomial European engines against analytic results...");

        final EngineType engine = EngineType.LR;
        /* @Size */final int steps = 251;
        /* @Size */final int samples = Constants.NULL_INTEGER;
        final Map<String, /* @Real */Double> relativeTol = new HashMap<String, Double>();
        relativeTol.put("value", 1.0e-6);
        relativeTol.put("delta", 1.0e-3);
        relativeTol.put("gamma", 1.0e-4);
        relativeTol.put("theta", 0.03);
        testEngineConsistency(engine, steps, samples, relativeTol);
    }

    @Test
    public void testJOSHIBinomialEngines() {

        QL.info("Testing time-dependent Joshi binomial European engines against analytic results...");

        final EngineType engine = EngineType.JOSHI;
        /* @Size */final int steps = 251;
        /* @Size */final int samples = Constants.NULL_INTEGER;
        final Map<String, /* @Real */Double> relativeTol = new HashMap<String, Double>();
        relativeTol.put("value", 1.0e-7);
        relativeTol.put("delta", 1.0e-3);
        relativeTol.put("gamma", 1.0e-4);
        relativeTol.put("theta", 0.03);
        testEngineConsistency(engine, steps, samples, relativeTol);
    }

    private void testEngineConsistency(final EngineType engine, final/* @Size */int binomialSteps, final/* @Size */int samples,
            final Map<String, /* @Real */Double> tolerance) {

        final Map<String, /* @Real */Double> calculated = new HashMap<String, Double>();
        final Map<String, /* @Real */Double> expected = new HashMap<String, Double>();

        // test options
        final Option.Type types[] = { Option.Type.Call, Option.Type.Put };
        /* @Real */final double strikes[] = { 75.0, 100.0, 125.0 };
        /* @Integer */final int lengths[] = { 1 };

        // test data
        /* @Real */final double underlyings[] = { 100.0 };
        /* @Rate */final double qRates[] = { 0.00, 0.05 };
        /* @Rate */final double rRates[] = { 0.01, 0.05, 0.15 };
        /* @Volatility */final double vols[] = { 0.11, 0.50, 1.20 };

        final DayCounter dc = new Actual360();
        final Date today = Date.todaysDate();

        final SimpleQuote spot = new SimpleQuote(0.0);
        final SimpleQuote vol = new SimpleQuote(0.0);
        final BlackVolTermStructure volTS = Utilities.flatVol(today, vol, dc);
        final SimpleQuote qRate = new SimpleQuote(0.0);
        final YieldTermStructure qTS = Utilities.flatRate(today, qRate, dc);
        final SimpleQuote rRate = new SimpleQuote(0.0);
        final YieldTermStructure rTS = Utilities.flatRate(today, rRate, dc);

        for (final Type type : types)
            for (final double strike : strikes)
                for (final int length : lengths) {
                    final Date exDate = today.add(length * 360);
                    final Exercise exercise = new EuropeanExercise(exDate);
                    final StrikedTypePayoff payoff = new PlainVanillaPayoff(type, strike);

                    // reference option
                    final VanillaOption refOption = makeOption(payoff, exercise, spot, qTS, rTS, volTS, EngineType.Analytic,
                            Constants.NULL_INTEGER, Constants.NULL_INTEGER);

                    // option to check
                    final VanillaOption option = makeOption(payoff, exercise, spot, qTS, rTS, volTS, engine, binomialSteps, samples);

                    for (final double u : underlyings)
                        for (final double q : qRates)
                            for (final double r : rRates)
                                for (final double v : vols) {
                                    spot.setValue(u);
                                    qRate.setValue(q);
                                    rRate.setValue(r);
                                    vol.setValue(v);

                                    expected.clear();
                                    calculated.clear();

                                    // FLOATING_POINT_EXCEPTION
                                    expected.put("value", refOption.NPV());
                                    calculated.put("value", option.NPV());

                                    if (option.NPV() > spot.value() * 1.0e-5) {
                                        expected.put("delta", refOption.delta());
                                        expected.put("gamma", refOption.gamma());
                                        expected.put("theta", refOption.theta());
                                        calculated.put("delta", option.delta());
                                        calculated.put("gamma", option.gamma());
                                        calculated.put("theta", option.theta());
                                    }
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

    private GeneralizedBlackScholesProcess makeProcess(final Quote u, final YieldTermStructure q, final YieldTermStructure r,
            final BlackVolTermStructure vol) {
        return new BlackScholesMertonProcess(new Handle<Quote>(u), new Handle<YieldTermStructure>(q),
                new Handle<YieldTermStructure>(r), new Handle<BlackVolTermStructure>(vol));
    }

    private VanillaOption makeOption(
            final StrikedTypePayoff payoff,
            final Exercise exercise,
            final Quote u,
            final YieldTermStructure q,
            final YieldTermStructure r,
            final BlackVolTermStructure vol,
            final EngineType engineType,
            /* @Size */final int binomialSteps,
            /* @Size */final int samples) {

        final GeneralizedBlackScholesProcess stochProcess = makeProcess(u, q, r, vol);

        PricingEngine engine;
        switch (engineType) {
        case Analytic:
            engine = new AnalyticEuropeanEngine(stochProcess);
            break;
        case JR:
            engine = new BinomialVanillaEngine<ExtendedJarrowRudd>(ExtendedJarrowRudd.class, stochProcess, binomialSteps);
            break;
        case CRR:
            engine = new BinomialVanillaEngine<ExtendedCoxRossRubinstein>(ExtendedCoxRossRubinstein.class, stochProcess, binomialSteps);
            break;
        case EQP:
            engine = new BinomialVanillaEngine<ExtendedAdditiveEQPBinomialTree>(ExtendedAdditiveEQPBinomialTree.class, stochProcess, binomialSteps);
            break;
        case TGEO:
            engine = new BinomialVanillaEngine<ExtendedTrigeorgis>(ExtendedTrigeorgis.class, stochProcess, binomialSteps);
            break;
        case TIAN:
            engine = new BinomialVanillaEngine<ExtendedTian>(ExtendedTian.class, stochProcess, binomialSteps);
            break;
        case LR:
            engine = new BinomialVanillaEngine<ExtendedLeisenReimer>(ExtendedLeisenReimer.class, stochProcess, binomialSteps);
            break;
        case JOSHI:
            engine = new BinomialVanillaEngine<ExtendedJoshi4>(ExtendedJoshi4.class, stochProcess, binomialSteps);
            break;
        default:
            throw new IllegalStateException("unknown engine type");
        }

        final VanillaOption option = new EuropeanOption(payoff, exercise);
        option.setPricingEngine(engine);
        return option;
    }

    private String engineTypeToString(final EngineType type) {
        switch (type) {
        case Analytic:
            return "analytic";
        case JR:
            return "Jarrow-Rudd";
        case CRR:
            return "Cox-Ross-Rubinstein";
        case EQP:
            return "EQP";
        case TGEO:
            return "Trigeorgis";
        case TIAN:
            return "Tian";
        case LR:
            return "LeisenReimer";
        case JOSHI:
            return "Joshi";
        default:
            throw new IllegalStateException("unknown engine type");
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
