/*
 Copyright (C) 2009 Q.Boiler, Ueli Hofstetter

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

package org.jquantlib.samples.util;



import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.math.distributions.InverseCumulativeNormal;
import org.jquantlib.math.randomnumbers.InverseCumulativeRsg;
import org.jquantlib.math.randomnumbers.MersenneTwisterUniformRng;
import org.jquantlib.math.randomnumbers.PseudoRandom;
import org.jquantlib.math.randomnumbers.RandomNumberGenerator;
import org.jquantlib.math.randomnumbers.RandomSequenceGenerator;
import org.jquantlib.math.statistics.Statistics;
import org.jquantlib.methods.montecarlo.MonteCarloModel;
import org.jquantlib.methods.montecarlo.SingleVariate;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Target;

public class ReplicationError {

    private final /* @Time */Number maturity_;
    private final PlainVanillaPayoff payoff_;
    private final /* @Real */Number s0_;
    private final /* @Volatility */Number sigma_;
    private final /* @Rate */Number r_;
    private final /* @Real */Number vega_;

    public ReplicationError(final Option.Type type, final/* @Time */Number maturity,
            final/* @Real */Number strike, final/* @Real */Number s0,
            final/* @Volatility */Number sigma, final/* @Rate */Number r)
    {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");


        this.maturity_ = maturity;
        payoff_ = new PlainVanillaPayoff(type, strike.doubleValue());
        this.s0_ = s0;
        this.sigma_ = sigma;
        this.r_ = r;

        final double rDiscount = Math.exp(- (r.doubleValue()) * maturity_.doubleValue());
        final double qDiscount = 1.0;
        final double forward = s0_.doubleValue() * qDiscount/rDiscount;
        final double stdDev = Math.sqrt(sigma_.doubleValue() * sigma_.doubleValue() * maturity_.doubleValue());
        //TODO:boost::shared_ptr<StrikedTypePayoff> payoff(new PlainVanillaPayoff(payoff_));
        final BlackCalculator black = new BlackCalculator(payoff_,forward,stdDev,rDiscount);

        System.out.println("Option value: " + black.value());
        // store option's vega, since Derman and Kamal's formula needs it
        vega_ = black.vega(maturity.doubleValue());
        System.out.println("Vega: " + vega_);
    }



    public void compute(final int nTimeSteps, final int nSamples) {

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        final Calendar calendar = new Target();
        final Date today = Date.todaysDate();
        final DayCounter dayCount = new Actual365Fixed();
        final Handle<Quote> stateVariable = new Handle(new SimpleQuote(s0_.doubleValue()));
        final Handle<YieldTermStructure> riskFreeRate = new Handle(new FlatForward(today, r_.doubleValue(), dayCount));

        final Handle<YieldTermStructure> dividendYield = new Handle(new FlatForward(today, 0.0, dayCount));

        final Handle<BlackVolTermStructure> volatility = new Handle(new BlackConstantVol(today, calendar, sigma_.doubleValue(),dayCount));

        final StochasticProcess1D diffusion = new BlackScholesMertonProcess(
                stateVariable, dividendYield, riskFreeRate, volatility);


        // Black Scholes equation rules the path generator:
        // at each step the log of the stock
        // will have drift and sigma^2 variance
        final InverseCumulativeRsg<RandomSequenceGenerator<MersenneTwisterUniformRng>, InverseCumulativeNormal> rsg = 
        	new PseudoRandom(RandomSequenceGenerator.class, InverseCumulativeNormal.class).makeSequenceGenerator(nTimeSteps, 0L);

        final boolean brownianBridge = false;

        final MonteCarloModel<SingleVariate, RandomNumberGenerator, Statistics> MCSimulation = new MonteCarloModel<SingleVariate, RandomNumberGenerator, Statistics>();

        // the model simulates nSamples paths
        MCSimulation.addSamples(nSamples);

        // the sampleAccumulator method
        // gives access to all the methods of statisticsAccumulator
        final Statistics s = MCSimulation.sampleAccumulator();

        /* @Real */final double PLMean = s.mean();
        /* @Real */final double PLStDev = MCSimulation.sampleAccumulator()
        .standardDeviation();
        /* @Real */final double PLSkew = MCSimulation.sampleAccumulator().skewness();
        /* @Real */final double PLKurt = MCSimulation.sampleAccumulator().kurtosis();

        // Derman and Kamal's formula
        /* @Real */final double theorStD = Math.sqrt((Math.PI / 4 / nTimeSteps)
                * vega_.doubleValue() * sigma_.doubleValue());

        final StringBuffer sb = new StringBuffer();
        sb.append(nSamples).append(" | ");
        sb.append(nTimeSteps).append(" | ");
        sb.append(PLMean).append(" | ");
        sb.append(PLStDev).append(" | ");
        sb.append(theorStD).append(" | ");
        sb.append(PLSkew).append(" | ");
        sb.append(PLKurt).append(" \n");

        System.out.println(sb.toString());
    }
}
