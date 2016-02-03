

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
 Copyright (C) 2005 Gary Kennedy

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

package org.jquantlib.pricingengines.asian;


import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.AverageType;
import org.jquantlib.instruments.ContinuousAveragingAsianOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;


/**
 * @author <Richard Gomes>
 */
//TODO class comments
//TODO add reference to original paper, clewlow strickland
public class AnalyticContinuousGeometricAveragePriceAsianEngine extends ContinuousAveragingAsianOption.EngineImpl {

    //
    // private final fields
    //

    private final GeneralizedBlackScholesProcess process;
    private final ContinuousAveragingAsianOption.ArgumentsImpl a;
    private final ContinuousAveragingAsianOption.ResultsImpl   r;
    private final Option.GreeksImpl greeks;
    private final Option.MoreGreeksImpl moreGreeks;


    //
    // public constructors
    //

    public AnalyticContinuousGeometricAveragePriceAsianEngine(final GeneralizedBlackScholesProcess process) {
        this.process = process;
        this.a = arguments_;
        this.r = results_;
        this.greeks = r.greeks();
        this.moreGreeks = r.moreGreeks();
        process.addObserver(this);
    }


    //
    // implements PricingEngine
    //

    @Override
    public void calculate() /*@ReadOnly*/ {
        QL.require(a.averageType==AverageType.Geometric , "not a geometric average option"); // TODO: message
        QL.require(a.exercise.type()==Exercise.Type.European , "not an European Option"); // TODO: message
        final Date exercise = a.exercise.lastDate();

        QL.require(a.payoff instanceof PlainVanillaPayoff , "non-plain payoff given"); // TODO: message
        final PlainVanillaPayoff payoff = (PlainVanillaPayoff)arguments_.payoff;

        /*@Volatility*/ final double volatility = process.blackVolatility().currentLink().blackVol(exercise, payoff.strike());
        /*@Real*/ final double variance = process.blackVolatility().currentLink().blackVariance(exercise, payoff.strike());
        /*@DiscountFactor*/ final double  riskFreeDiscount = process.riskFreeRate().currentLink().discount(exercise);
        final DayCounter rfdc  = process.riskFreeRate().currentLink().dayCounter();
        final DayCounter divdc = process.dividendYield().currentLink().dayCounter();
        final DayCounter voldc = process.blackVolatility().currentLink().dayCounter();

        /*@Spread*/ final double dividendYield = 0.5 * (
                process.riskFreeRate().currentLink().zeroRate(
                        exercise,
                        rfdc,
                        Compounding.Continuous,
                        Frequency.NoFrequency).rate() + process.dividendYield().currentLink().zeroRate(
                                exercise,
                                divdc,
                                Compounding.Continuous,
                                Frequency.NoFrequency).rate() + volatility*volatility/6.0);

        /*@Time*/ final double t_q = divdc.yearFraction(
                process.dividendYield().currentLink().referenceDate(), exercise);
        /*@DiscountFactor*/ final double dividendDiscount = Math.exp(-dividendYield*t_q);
        /*@Real*/ final double spot = process.stateVariable().currentLink().value();
        QL.require(spot > 0.0, "negative or null underlying given"); // TODO: message
        /*@Real*/ final double forward = spot * dividendDiscount / riskFreeDiscount;

        final BlackCalculator black = new BlackCalculator(payoff, forward, Math.sqrt(variance/3.0),riskFreeDiscount);
        r.value = black.value();
        greeks.delta = black.delta(spot);
        greeks.gamma = black.gamma(spot);
        greeks.dividendRho = black.dividendRho(t_q)/2.0;

        /*@Time*/ final double t_r = rfdc.yearFraction(process.riskFreeRate().currentLink().referenceDate(),
                a.exercise.lastDate());
        greeks.rho = black.rho(t_r) + 0.5 * black.dividendRho(t_q);

        /*@Time*/ final double t_v = voldc.yearFraction(
                process.blackVolatility().currentLink().referenceDate(),
                a.exercise.lastDate());
        greeks.vega = black.vega(t_v)/Math.sqrt(3.0) +
        black.dividendRho(t_q)*volatility/6.0;

        try {
            greeks.theta = black.theta(spot, t_v);
        } catch (final ArithmeticException e) {
            greeks.theta = Constants.NULL_REAL;
        }
    }

}
