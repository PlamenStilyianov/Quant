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
 Copyright (C) 2003 Ferdinando Ametrano

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
import org.jquantlib.exercise.AmericanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Bjerksund and Stensland approximation engine
 *
 * @author <Richard Gomes>
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// review JSR-308 annotations too
public class BjerksundStenslandApproximationEngine extends VanillaOption.EngineImpl {

    // TODO: refactor messages
    private static final String NOT_AN_AMERICAN_OPTION = "not an American Option";
    private static final String NON_AMERICAN_EXERCISE_GIVEN = "non-American exercise given";
    private static final String PAYOFF_AT_EXPIRY_NOT_HANDLED = "payoff at expiry not handled";
    private static final String NON_PLAIN_PAYOFF_GIVEN = "non-plain payoff given";
    private static final String BLACK_SCHOLES_PROCESS_REQUIRED = "Black-Scholes process required";
    private static final String BJERKSUND_NOT_APPLICABLE = "Bjerksund-Stensland approximation not applicable to this set of parameters";

    //
    // private final fields
    //

    private final GeneralizedBlackScholesProcess process;
    private final OneAssetOption.ArgumentsImpl a;
    private final OneAssetOption.ResultsImpl   r;
    private final Option.GreeksImpl greeks;
    private final Option.MoreGreeksImpl moreGreeks;

    //
    // private fields
    //

    private final CumulativeNormalDistribution cumNormalDist = new CumulativeNormalDistribution();


    //
    // public constructors
    //

    public BjerksundStenslandApproximationEngine(final GeneralizedBlackScholesProcess process) {
        this.a = (OneAssetOption.ArgumentsImpl)arguments_;
        this.r = (OneAssetOption.ResultsImpl)results_;
        this.greeks = r.greeks();
        this.moreGreeks = r.moreGreeks();
        this.process = process;
        this.process.addObserver(this);
    }


    //
    // private methods
    //

    private double /*@Real*/ phi(
            final double /*@Real*/ S,
            final double /*@Real*/ gamma,
            final double /*@Real*/ H,
            final double /*@Real*/ I,
            final double /*@Real*/ rT,
            final double /*Real*/ bT,
            final double /*@Real*/ variance) {

        final double /* @Real */lambda = (-rT + gamma * bT + 0.5 * gamma * (gamma - 1.0) * variance);
        final double /* @Real */d = -(Math.log(S / H) + (bT + (gamma - 0.5) * variance)) / Math.sqrt(variance);
        final double /* @Real */kappa = 2.0 * bT / variance + (2.0 * gamma - 1.0);
        return Math.exp(lambda) * Math.pow(S, gamma) * (cumNormalDist.op(d)
                - Math.pow((I / S), kappa) * cumNormalDist.op(d - 2.0 * Math.log(I / S) / Math.sqrt(variance)));
    }

    private double /*@Real*/ americanCallApproximation(
            final double /*@Real*/ s,
            final double /*@Real*/ x,
            final double /*@Real*/ rfD,
            final double /*@Real*/ dD,
            final double /*@Real*/ variance) {

        final double /* @Real */bT = Math.log(dD / rfD);
        final double /* @Real */rT = Math.log(1.0 / rfD);

        final double /* @Real */beta = (0.5 - bT / variance) + Math.sqrt(Math.pow((bT / variance - 0.5), (2.0)) + 2.0 * rT / variance);
        final double /* @Real */BInfinity = beta / (beta - 1.0) * x;
        // Real B0 = std::max(X, std::log(rfD) / std::log(dD) * X);
        final double /* @Real */B0 = Math.max(x, rT / (rT - bT) * x);
        final double /* @Real */ht = -(bT + 2.0 * Math.sqrt(variance)) * B0 / (BInfinity - B0);

        // investigate what happen to I for dD->0.0
        final double /*@Real*/ i = B0 + (BInfinity - B0) * (1 - Math.exp(ht));

        QL.require(i>=x , BJERKSUND_NOT_APPLICABLE); // QA:[RG]::verified

        if (s >= i) {
            return s - x;
        } else {
            // investigate what happen to alpha for dD->0.0
            final double /*@Real*/ alpha = (i - x) * Math.pow(i, (-beta));
            return alpha * Math.pow(s, beta)
            - alpha * phi(s, beta, i, i, rT, bT, variance)
            +         phi(s,  1.0, i, i, rT, bT, variance)
            -         phi(s,  1.0, x, i, rT, bT, variance)
            -    x *  phi(s,  0.0, i, i, rT, bT, variance)
            +    x *  phi(s,  0.0, x, i, rT, bT, variance);
        }
    }


    //
    // implements PricingEngine
    //

    @Override
    public void calculate() /*@ReadOnly*/{
        QL.require(a.exercise.type()==Exercise.Type.American , NOT_AN_AMERICAN_OPTION); // QA:[RG]::verified
        QL.require(a.exercise instanceof AmericanExercise , NON_AMERICAN_EXERCISE_GIVEN); // QA:[RG]::verified
        final AmericanExercise ex = (AmericanExercise)a.exercise;
        QL.require(!ex.payoffAtExpiry() , PAYOFF_AT_EXPIRY_NOT_HANDLED); // QA:[RG]::verified
        QL.require(a.payoff instanceof PlainVanillaPayoff , NON_PLAIN_PAYOFF_GIVEN); // QA:[RG]::verified
        PlainVanillaPayoff payoff = (PlainVanillaPayoff)a.payoff;

        final double /* @Real */variance = process.blackVolatility().currentLink().blackVariance(ex.lastDate(), payoff.strike());
        double /* @DiscountFactor */dividendDiscount = process.dividendYield().currentLink().discount(ex.lastDate());
        double /* @DiscountFactor */riskFreeDiscount = process.riskFreeRate().currentLink().discount(ex.lastDate());
        double /* @Real */spot = process.stateVariable().currentLink().value();
        QL.require(spot > 0.0, "negative or null underlying given"); // TODO: message
        double /* @Real */strike = payoff.strike();

        if (payoff.optionType()==Option.Type.Put) {
            // use put-call symmetry
            // swap spot and strike, has to be done inline
            double tmp = spot; spot = strike; strike = tmp;

            // swap riskFreeDiscount and dividenDiscount, has to be done inline
            tmp = riskFreeDiscount; riskFreeDiscount = dividendDiscount; dividendDiscount = tmp;

            payoff = new PlainVanillaPayoff(Option.Type.Call, strike);
        }

        if (dividendDiscount>=1.0) {
            // early exercise is never optimal - use Black formula
            final double /*@Real*/ forwardPrice = spot * dividendDiscount / riskFreeDiscount;
            final BlackCalculator black = new BlackCalculator(payoff, forwardPrice, Math.sqrt(variance), riskFreeDiscount);

            r.value           = black.value();
            greeks.delta            = black.delta(spot);
            moreGreeks.deltaForward = black.deltaForward();
            moreGreeks.elasticity   = black.elasticity(spot);
            greeks.gamma            = black.gamma(spot);

            final DayCounter rfdc  = process.riskFreeRate().currentLink().dayCounter();
            final DayCounter divdc = process.dividendYield().currentLink().dayCounter();
            final DayCounter voldc = process.blackVolatility().currentLink().dayCounter();

            double /* @Time */t = rfdc.yearFraction(process.riskFreeRate().currentLink().referenceDate(), a.exercise.lastDate());
            greeks.rho = black.rho(t);

            t = divdc.yearFraction(process.dividendYield().currentLink().referenceDate(), a.exercise.lastDate());
            greeks.dividendRho = black.dividendRho(t);

            t = voldc.yearFraction(process.blackVolatility().currentLink().referenceDate(), a.exercise.lastDate());
            greeks.vega            = black.vega(t);
            greeks.theta           = black.theta(spot, t);
            moreGreeks.thetaPerDay = black.thetaPerDay(spot, t);

            moreGreeks.strikeSensitivity  = black.strikeSensitivity();
            moreGreeks.itmCashProbability = black.itmCashProbability();
        } else {
            // early exercise can be optimal - use approximation
            r.value = americanCallApproximation(spot,
                    strike,
                    riskFreeDiscount,
                    dividendDiscount,
                    variance);
        }

    }

}
