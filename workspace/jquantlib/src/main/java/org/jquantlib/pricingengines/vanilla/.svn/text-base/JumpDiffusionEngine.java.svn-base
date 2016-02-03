/*
 Copyright (C) 2009 Richard Gomes

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
 Copyright (C) 2004 Ferdinando Ametrano

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

/*
 Copyright (C) 2004 Ferdinando Ametrano
 Copyright (C) 2007 StatPro Italia srl

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

package org.jquantlib.pricingengines.vanilla;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.math.Constants;
import org.jquantlib.math.distributions.PoissonDistribution;
import org.jquantlib.pricingengines.AnalyticEuropeanEngine;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.processes.Merton76Process;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;

/**
 * Jump-diffusion engine for vanilla options
 *
 * @author <Richard Gomes>
 */
public class JumpDiffusionEngine extends VanillaOption.EngineImpl {

    // TODO: refactor messages
    private static final double DEFAULT_RELATIVE_ACCURACY = 1e-4;
    private static final int DEFAULT_MAX_ITERATIONS = 100;


    //
    // private final fields
    //

    final private Merton76Process process;
    final private VanillaOption.ArgumentsImpl A;
    final private VanillaOption.ResultsImpl   R;

    private final Option.GreeksImpl greeks;
    private final Option.MoreGreeksImpl moreGreeks;

    private final double relativeAccuracy;
    private final int maxIterations;


    public JumpDiffusionEngine(
            final Merton76Process process) {
        this(process, DEFAULT_RELATIVE_ACCURACY, DEFAULT_MAX_ITERATIONS);
    }

    public JumpDiffusionEngine(
            final Merton76Process process,
            final double relativeAccuracy) {
        this(process, relativeAccuracy, DEFAULT_MAX_ITERATIONS);
    }

    public JumpDiffusionEngine(
            final Merton76Process process,
            final double relativeAccuracy,
            final int maxIterations) {
        this.maxIterations = maxIterations;
        this.relativeAccuracy = relativeAccuracy;
        this.A = (VanillaOption.ArgumentsImpl)arguments_;
        this.R = (VanillaOption.ResultsImpl)results_;
        this.greeks = R.greeks();
        this.moreGreeks = R.moreGreeks();
        this.process = process;
        this.process.addObserver(this);
    }


    @Override
    public void calculate() {
        final double /* @Real */jumpSquareVol =
            process.logJumpVolatility().currentLink().value() * process.logJumpVolatility().currentLink().value();
        final double /* @Real */muPlusHalfSquareVol = process.logMeanJump().currentLink().value() + 0.5 * jumpSquareVol;

        // mean jump size
        final double /* @Real */k = Math.exp(muPlusHalfSquareVol) - 1.0;
        final double /* @Real */lambda = (k + 1.0) * process.jumpIntensity().currentLink().value();

        // dummy strike
        final double /* @Real */variance = process.blackVolatility().currentLink().blackVariance(A.exercise.lastDate(), 1.0);

        final DayCounter voldc = process.blackVolatility().currentLink().dayCounter();
        final Calendar volcal = process.blackVolatility().currentLink().calendar();
        final Date volRefDate = process.blackVolatility().currentLink().referenceDate();
        final double /* @Time */t = voldc.yearFraction(volRefDate, A.exercise.lastDate());
        final double /* @Rate */riskFreeRate = -Math.log(process.riskFreeRate().currentLink().discount(A.exercise.lastDate())) / t;
        final Date rateRefDate = process.riskFreeRate().currentLink().referenceDate();

        final PoissonDistribution p = new PoissonDistribution(lambda * t);

        final Handle<? extends Quote> stateVariable = process.stateVariable();
        final Handle<YieldTermStructure> dividendTS = process.dividendYield();
        final RelinkableHandle<YieldTermStructure> riskFreeTS =
            new RelinkableHandle<YieldTermStructure>(process.riskFreeRate().currentLink());
        final RelinkableHandle<BlackVolTermStructure> volTS =
            new RelinkableHandle<BlackVolTermStructure>(process.blackVolatility().currentLink());

        final GeneralizedBlackScholesProcess bsProcess =
            new GeneralizedBlackScholesProcess(stateVariable, dividendTS, riskFreeTS, volTS);

        final AnalyticEuropeanEngine baseEngine = new AnalyticEuropeanEngine(bsProcess);

        final VanillaOption.ArgumentsImpl baseArguments = (VanillaOption.ArgumentsImpl) baseEngine.getArguments();

        baseArguments.payoff = A.payoff;
        baseArguments.exercise = A.exercise;

        baseArguments.validate();

        final VanillaOption.ResultsImpl baseResults = (VanillaOption.ResultsImpl) baseEngine.getResults();

        R.value = 0.0;
        greeks.delta = 0.0;
        greeks.gamma = 0.0;
        greeks.theta = 0.0;
        greeks.vega = 0.0;
        greeks.rho = 0.0;
        greeks.dividendRho = 0.0;


        double /* @Real */ r, v, weight, lastContribution = 1.0;
        double /* @Real */ theta_correction;

        int i;
        for (i = 0; lastContribution > relativeAccuracy && i < maxIterations || i < (int)(lambda*t); i++) {

            // constant vol/rate assumption. It should be relaxed
            v = Math.sqrt((variance + i * jumpSquareVol) / t);
            r = riskFreeRate - process.jumpIntensity().currentLink().value() * k + i * muPlusHalfSquareVol / t;
            riskFreeTS.linkTo(new FlatForward(rateRefDate, r, voldc));
            volTS.linkTo(new BlackConstantVol(rateRefDate, volcal, v, voldc));

            baseArguments.validate();
            baseEngine.calculate();

            weight = p.op(i);
            R.value += weight * baseResults.value;
            greeks.delta += weight * baseResults.greeks().delta;
            greeks.gamma += weight * baseResults.greeks().gamma;
            greeks.vega += weight * (Math.sqrt(variance / t) / v) * baseResults.greeks().vega;
            // theta modified
            theta_correction = baseResults.greeks().vega * ((i * jumpSquareVol) / (2.0 * v * t * t)) + baseResults.greeks().rho * i
            * muPlusHalfSquareVol / (t * t);
            greeks.theta += weight * (baseResults.greeks().theta + theta_correction + lambda * baseResults.value);
            if (i != 0) {
                greeks.theta -= (p.op(i-1) * lambda * baseResults.value);
            }
            // end theta calculation
            greeks.rho += weight * baseResults.greeks().rho;
            greeks.dividendRho += weight * baseResults.greeks().dividendRho;

            lastContribution = Math.abs(baseResults.value / (Math.abs(R.value) > Constants.QL_EPSILON ? R.value : 1.0));

            lastContribution = Math.max(lastContribution,
                    Math.abs(baseResults.greeks().delta / (Math.abs(greeks.delta) > Constants.QL_EPSILON ? greeks.delta : 1.0)));

            lastContribution = Math.max(lastContribution,
                    Math.abs(baseResults.greeks().gamma / (Math.abs(greeks.gamma) > Constants.QL_EPSILON ? greeks.gamma : 1.0)));

            lastContribution = Math.max(lastContribution,
                    Math.abs(baseResults.greeks().theta / (Math.abs(greeks.theta) > Constants.QL_EPSILON ? greeks.theta : 1.0)));

            lastContribution = Math.max(lastContribution,
                    Math.abs(baseResults.greeks().vega / (Math.abs(greeks.vega) > Constants.QL_EPSILON ? greeks.vega : 1.0)));

            lastContribution = Math.max(lastContribution,
                    Math.abs(baseResults.greeks().rho / (Math.abs(greeks.rho) > Constants.QL_EPSILON ? greeks.rho : 1.0)));

            lastContribution = Math.max(lastContribution,
                    Math.abs(baseResults.greeks().dividendRho / (Math.abs(greeks.dividendRho) > Constants.QL_EPSILON ? greeks.dividendRho : 1.0)));

            lastContribution *= weight;
        }

        QL.ensure(i < maxIterations , "accuracy not reached"); // TODO: message
    }

}
