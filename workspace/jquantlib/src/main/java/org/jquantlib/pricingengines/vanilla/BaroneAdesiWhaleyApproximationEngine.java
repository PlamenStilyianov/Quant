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
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.lang.annotation.PackagePrivate;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.pricingengines.BlackFormula;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Barone-Adesi and Whaley pricing engine for American options
 *
 * @author <Richard Gomes>
 */
public class BaroneAdesiWhaleyApproximationEngine extends VanillaOption.EngineImpl {

    // TODO: refactor messages
    private static final String NOT_AN_AMERICAN_OPTION = "not an American Option";
    private static final String NON_AMERICAN_EXERCISE_GIVEN = "non-American exercise given";
    private static final String PAYOFF_AT_EXPIRY_NOT_HANDLED = "payoff at expiry not handled";
    private static final String NON_STRIKE_PAYOFF_GIVEN = "non-striked payoff given";
    private static final String BLACK_SCHOLES_PROCESS_REQUIRED = "Black-Scholes process required";
    private static final String UNKNOWN_OPTION_TYPE = "unknown Option type";


    //
    // private final fields
    //

    final private GeneralizedBlackScholesProcess process;
    final private OneAssetOption.ArgumentsImpl a;
    final private OneAssetOption.ResultsImpl   r;
    private final Option.GreeksImpl greeks;
    private final Option.MoreGreeksImpl moreGreeks;


    //
    // public constructors
    //

    public BaroneAdesiWhaleyApproximationEngine(final GeneralizedBlackScholesProcess process) {
        this.a = (OneAssetOption.ArgumentsImpl)arguments_;
        this.r = (OneAssetOption.ResultsImpl)results_;
        this.greeks = r.greeks();
        this.moreGreeks = r.moreGreeks();
        this.process = process;
        this.process.addObserver(this);
    }


    //
    // implements PricingEngine
    //

    @Override
    public void calculate() {
        QL.require(a.exercise.type()==Exercise.Type.American , NOT_AN_AMERICAN_OPTION); // QA:[RG]::verified
        QL.require(a.exercise instanceof AmericanExercise , NON_AMERICAN_EXERCISE_GIVEN); // QA:[RG]::verified
        final AmericanExercise ex = (AmericanExercise)a.exercise;
        QL.require(!ex.payoffAtExpiry() , PAYOFF_AT_EXPIRY_NOT_HANDLED); // QA:[RG]::verified
        QL.require(a.payoff instanceof StrikedTypePayoff , NON_STRIKE_PAYOFF_GIVEN); // QA:[RG]::verified
        final StrikedTypePayoff payoff = (StrikedTypePayoff)a.payoff;

        final double /*@Real*/ variance = process.blackVolatility().currentLink().blackVariance(ex.lastDate(), payoff.strike());
        final double /*@DiscountFactor*/ dividendDiscount = process.dividendYield().currentLink().discount(ex.lastDate());
        final double /*@DiscountFactor*/ riskFreeDiscount = process.riskFreeRate().currentLink().discount(ex.lastDate());
        final double /*@Real*/ spot = process.stateVariable().currentLink().value();
        QL.require(spot > 0.0, "negative or null underlying given"); // TODO: message
        final double /*@Real*/ forwardPrice = spot * dividendDiscount / riskFreeDiscount;
        final BlackCalculator black = new BlackCalculator(payoff, forwardPrice, Math.sqrt(variance), riskFreeDiscount);

        if (dividendDiscount>=1.0 && payoff.optionType()==Option.Type.Call) {
            // early exercise never optimal
            r.value                     = black.value();
            greeks.delta            = black.delta(spot);
            moreGreeks.deltaForward = black.deltaForward();
            moreGreeks.elasticity   = black.elasticity(spot);
            greeks.gamma            = black.gamma(spot);

            final DayCounter rfdc  = process.riskFreeRate().currentLink().dayCounter();
            final DayCounter divdc = process.dividendYield().currentLink().dayCounter();
            final DayCounter voldc = process.blackVolatility().currentLink().dayCounter();

            double /*@Time*/ t = rfdc.yearFraction(process.riskFreeRate().currentLink().referenceDate(), a.exercise.lastDate());
            greeks.rho = black.rho(t);

            t = divdc.yearFraction(process.dividendYield().currentLink().referenceDate(), a.exercise.lastDate());
            greeks.dividendRho = black.dividendRho(t);

            t = voldc.yearFraction(process.blackVolatility().currentLink().referenceDate(), a.exercise.lastDate());
            greeks.vega        = black.vega(t);
            greeks.theta       = black.theta(spot, t);

            moreGreeks.thetaPerDay        = black.thetaPerDay(spot, t);
            moreGreeks.strikeSensitivity  = black.strikeSensitivity();
            moreGreeks.itmCashProbability = black.itmCashProbability();
        } else {
            // early exercise can be optimal
            final CumulativeNormalDistribution cumNormalDist = new CumulativeNormalDistribution();
            final double /*@Real*/ tolerance = 1e-6;
            final double /*@Real*/ Sk = criticalPrice(payoff, riskFreeDiscount, dividendDiscount, variance, tolerance);
            final double /*@Real*/ forwardSk = Sk * dividendDiscount / riskFreeDiscount;
            final double /*@Real*/ d1 = (Math.log(forwardSk/payoff.strike()) + 0.5*variance)/Math.sqrt(variance);
            final double /*@Real*/ n = 2.0*Math.log(dividendDiscount/riskFreeDiscount)/variance;
            final double /*@Real*/ K = -2.0*Math.log(riskFreeDiscount)/(variance*(1.0-riskFreeDiscount));
            double /*@Real*/ Q, a;
            switch (payoff.optionType()) {
            case Call:
                Q = (-(n-1.0) + Math.sqrt(((n-1.0)*(n-1.0))+4.0*K))/2.0;
                a =  (Sk/Q) * (1.0 - dividendDiscount * cumNormalDist.op(d1));
                if (spot<Sk)
                    r.value = black.value() + a * Math.pow((spot/Sk), Q);
                else
                    r.value = spot - payoff.strike();
                break;
            case Put:
                Q = (-(n-1.0) - Math.sqrt(((n-1.0)*(n-1.0))+4.0*K))/2.0;
                a = -(Sk/Q) * (1.0 - dividendDiscount * cumNormalDist.op(-d1));
                if (spot>Sk)
                    r.value = black.value() + a * Math.pow((spot/Sk), Q);
                else
                    r.value = payoff.strike() - spot;
                break;
            default:
                throw new LibraryException(UNKNOWN_OPTION_TYPE); // QA:[RG]::verified
            }
        } // end of "early exercise can be optimal"

    }


    //
    // private methods
    //

    //TODO: code review :: unused method?
    @Deprecated
    private /* @Usused */ double  criticalPrice(
            final StrikedTypePayoff payoff,
            final double /*@DiscountFactor*/ riskFreeDiscount,
            final double /*@DiscountFactor*/ dividendDiscount,
            final double variance) {
        return criticalPrice(payoff, riskFreeDiscount, dividendDiscount, variance, 1.0e-6);
    }


    //
    // package protected methods
    //
    // TODO: study if a refactoring is a good idea, in order to remove the package private access modifier
    //

    @PackagePrivate static double  criticalPrice(
            final StrikedTypePayoff payoff,
            final double /*@DiscountFactor*/ riskFreeDiscount,
            final double /*@DiscountFactor*/ dividendDiscount,
            final double variance,
            final double tolerance) {

        // Calculation of seed value, Si
        final double /*@Real*/ n= 2.0*Math.log(dividendDiscount/riskFreeDiscount)/(variance);
        final double /*@Real*/ m=-2.0*Math.log(riskFreeDiscount)/(variance);
        final double /*@Real*/ bT = Math.log(dividendDiscount/riskFreeDiscount);

        double /*@Real*/ qu, Su, h, Si;
        switch (payoff.optionType()) {
        case Call:
            qu = (-(n-1.0) + Math.sqrt(((n-1.0)*(n-1.0)) + 4.0*m))/2.0;
            Su = payoff.strike() / (1.0 - 1.0/qu);
            h = -(bT + 2.0*Math.sqrt(variance)) * payoff.strike() / (Su - payoff.strike());
            Si = payoff.strike() + (Su - payoff.strike()) * (1.0 - Math.exp(h));
            break;
        case Put:
            qu = (-(n-1.0) - Math.sqrt(((n-1.0)*(n-1.0)) + 4.0*m))/2.0;
            Su = payoff.strike() / (1.0 - 1.0/qu);
            h = (bT - 2.0*Math.sqrt(variance)) * payoff.strike() / (payoff.strike() - Su);
            Si = Su + (payoff.strike() - Su) * Math.exp(h);
            break;
        default:
            throw new LibraryException(UNKNOWN_OPTION_TYPE); // QA:[RG]::verified
        }


        // Newton Raphson algorithm for finding critical price Si
        double /*@Real*/ Q, LHS, RHS, bi;
        double /*@Real*/ forwardSi = Si * dividendDiscount / riskFreeDiscount;
        double /*@Real*/ d1 = (Math.log(forwardSi/payoff.strike()) + 0.5*variance) / Math.sqrt(variance);
        final CumulativeNormalDistribution cumNormalDist = new CumulativeNormalDistribution();
        final double /*@Real*/ K = (riskFreeDiscount!=1.0 ? -2.0*Math.log(riskFreeDiscount)/ (variance*(1.0-riskFreeDiscount)) : 0.0);
        final double /*@Real*/ temp = BlackFormula.blackFormula(payoff.optionType(), payoff.strike(), forwardSi, Math.sqrt(variance))*riskFreeDiscount;
        switch (payoff.optionType()) {
        case Call:
            Q = (-(n-1.0) + Math.sqrt(((n-1.0)*(n-1.0)) + 4 * K)) / 2;
            LHS = Si - payoff.strike();
            RHS = temp + (1 - dividendDiscount * cumNormalDist.op(d1)) * Si / Q;
            bi =  dividendDiscount * cumNormalDist.op(d1) * (1 - 1/Q)
            + (1 - dividendDiscount * cumNormalDist.derivative(d1) / Math.sqrt(variance)) / Q;
            while (Math.abs(LHS - RHS)/payoff.strike() > tolerance) {
                Si = (payoff.strike() + RHS - bi * Si) / (1 - bi);
                forwardSi = Si * dividendDiscount / riskFreeDiscount;
                d1 = (Math.log(forwardSi/payoff.strike())+0.5*variance)/Math.sqrt(variance);
                LHS = Si - payoff.strike();
                final double /*@Real*/ temp2 = BlackFormula.blackFormula(payoff.optionType(), payoff.strike(), forwardSi, Math.sqrt(variance))*riskFreeDiscount;
                RHS = temp2 + (1 - dividendDiscount * cumNormalDist.op(d1)) * Si / Q;
                bi = dividendDiscount * cumNormalDist.op(d1) * (1 - 1 / Q)
                + (1 - dividendDiscount * cumNormalDist.derivative(d1) / Math.sqrt(variance)) / Q;
            }
            break;
        case Put:
            Q = (-(n-1.0) - Math.sqrt(((n-1.0)*(n-1.0)) + 4 * K)) / 2;
            LHS = payoff.strike() - Si;
            RHS = temp - (1 - dividendDiscount * cumNormalDist.op(-d1)) * Si / Q;
            bi = -dividendDiscount * cumNormalDist.op(-d1) * (1 - 1/Q)
            - (1 + dividendDiscount * cumNormalDist.derivative(-d1) / Math.sqrt(variance)) / Q;
            while (Math.abs(LHS - RHS)/payoff.strike() > tolerance) {
                Si = (payoff.strike() - RHS + bi * Si) / (1 + bi);
                forwardSi = Si * dividendDiscount / riskFreeDiscount;
                d1 = (Math.log(forwardSi/payoff.strike())+0.5*variance)/Math.sqrt(variance);
                LHS = payoff.strike() - Si;
                final double /*@Real*/ temp2 = BlackFormula.blackFormula(payoff.optionType(), payoff.strike(), forwardSi, Math.sqrt(variance))*riskFreeDiscount;
                RHS = temp2 - (1 - dividendDiscount * cumNormalDist.op(-d1)) * Si / Q;
                bi = -dividendDiscount * cumNormalDist.op(-d1) * (1 - 1 / Q)
                - (1 + dividendDiscount * cumNormalDist.derivative(-d1) / Math.sqrt(variance)) / Q;
            }
            break;
        default:
            throw new LibraryException(UNKNOWN_OPTION_TYPE); // QA:[RG]::verified
        }

        return Si;
    }

}
