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
 Copyright (C) 2004 Neil Firth

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
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.math.distributions.NormalDistribution;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.pricingengines.BlackFormula;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * An Approximate Formula for Pricing American Options, Journal of Derivatives Winter 1999,  Ju, N.
 * <p>
 * Known issue, the case of zero interest rates causes a division by zero
 *
 * @author <Richard Gomes>
 */
public class JuQuadraticApproximationEngine extends VanillaOption.EngineImpl {

    // TODO: refactor messages
    private static final String NOT_AN_AMERICAN_OPTION = "not an American Option";
    private static final String NON_AMERICAN_EXERCISE_GIVEN = "non-American exercise given";
    private static final String PAYOFF_AT_EXPIRY_NOT_HANDLED = "payoff at expiry not handled";
    private static final String NON_STRIKE_PAYOFF_GIVEN = "non-striked payoff given";
    private static final String BLACK_SCHOLES_PROCESS_REQUIRED = "Black-Scholes process required";
    private static final String UNKNOWN_OPTION_TYPE = "unknown Option type";
    private static final String DIVIDING_BY_ZERO_INTEREST_RATE = "dividing by zero interst rate";


    //
    // private final fields
    //

    final private GeneralizedBlackScholesProcess process;
    final private VanillaOption.ArgumentsImpl a;
    final private VanillaOption.ResultsImpl   r;
    private final Option.GreeksImpl greeks;
    private final Option.MoreGreeksImpl moreGreeks;


    //
    // public constructors
    //

    public JuQuadraticApproximationEngine(final GeneralizedBlackScholesProcess process) {
        this.a = (VanillaOption.ArgumentsImpl)arguments_;
        this.r = (VanillaOption.ResultsImpl)results_;
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

        final double /* @Real */variance = process.blackVolatility().currentLink().blackVariance(ex.lastDate(), payoff.strike());
        final double /* @DiscountFactor */dividendDiscount = process.dividendYield().currentLink().discount(ex.lastDate());
        final double /* @DiscountFactor */riskFreeDiscount = process.riskFreeRate().currentLink().discount(ex.lastDate());
        final double /* @Real */spot = process.stateVariable().currentLink().value();
        QL.require(spot > 0.0, "negative or null underlying given"); // TODO: message
        final double /* @Real */forwardPrice = spot * dividendDiscount / riskFreeDiscount;
        final BlackCalculator black = new BlackCalculator(payoff, forwardPrice, Math.sqrt(variance), riskFreeDiscount);

        if (dividendDiscount>=1.0 && payoff.optionType()==Option.Type.Call) {
            // early exercise never optimal
            r.value           = black.value();
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
            greeks.vega            = black.vega(t);
            greeks.theta           = black.theta(spot, t);
            moreGreeks.thetaPerDay = black.thetaPerDay(spot, t);

            moreGreeks.strikeSensitivity  = black.strikeSensitivity();
            moreGreeks.itmCashProbability = black.itmCashProbability();
        } else {
            // early exercise can be optimal
            final CumulativeNormalDistribution cumNormalDist = new CumulativeNormalDistribution();
            final NormalDistribution normalDist = new NormalDistribution();

            final double /*@Real*/ tolerance = 1e-6;
            final double /*@Real*/ Sk = BaroneAdesiWhaleyApproximationEngine.criticalPrice(payoff, riskFreeDiscount, dividendDiscount, variance, tolerance);

            final double /*@Real*/ forwardSk = Sk * dividendDiscount / riskFreeDiscount;

            final double /*@Real*/ alpha = -2.0*Math.log(riskFreeDiscount)/(variance);
            final double /*@Real*/ beta = 2.0*Math.log(dividendDiscount/riskFreeDiscount)/
            (variance);
            final double /*@Real*/ h = 1 - riskFreeDiscount;
            double /*@Real*/ phi;

            switch (payoff.optionType()) {
            case Call:
                phi = 1;
                break;
            case Put:
                phi = -1;
                break;
            default:
                throw new LibraryException(UNKNOWN_OPTION_TYPE); // QA:[RG]::verified
            }

            // TODO: study how zero interest rate could be handled
            QL.ensure(h != 0.0 , DIVIDING_BY_ZERO_INTEREST_RATE); // QA:[RG]::verified

            final double /* @Real */temp_root = Math.sqrt((beta - 1) * (beta - 1) + (4 * alpha) / h);
            final double /* @Real */lambda = (-(beta - 1) + phi * temp_root) / 2;
            final double /* @Real */lambda_prime = -phi * alpha / (h * h * temp_root);

            final double /* @Real */black_Sk = BlackFormula.blackFormula(payoff.optionType(), payoff.strike(), forwardSk, Math.sqrt(variance)) * riskFreeDiscount;
            final double /* @Real */hA = phi * (Sk - payoff.strike()) - black_Sk;

            final double /* @Real */d1_Sk = (Math.log(forwardSk / payoff.strike()) + 0.5 * variance) / Math.sqrt(variance);
            final double /* @Real */d2_Sk = d1_Sk - Math.sqrt(variance);
            final double /* @Real */part1 = forwardSk * normalDist.op(d1_Sk) / (alpha * Math.sqrt(variance));
            final double /* @Real */part2 = -phi * forwardSk * cumNormalDist.op(phi * d1_Sk) * Math.log(dividendDiscount) / Math.log(riskFreeDiscount);
            final double /* @Real */part3 = +phi * payoff.strike() * cumNormalDist.op(phi * d2_Sk);
            final double /* @Real */V_E_h = part1 + part2 + part3;

            final double /* @Real */b = (1 - h) * alpha * lambda_prime / (2 * (2 * lambda + beta - 1));
            final double /* @Real */c = -((1 - h) * alpha / (2 * lambda + beta - 1)) * (V_E_h / (hA) + 1 / h + lambda_prime / (2 * lambda + beta - 1));
            final double /* @Real */temp_spot_ratio = Math.log(spot / Sk);
            final double /* @Real */chi = temp_spot_ratio * (b * temp_spot_ratio + c);

            if (phi * (Sk - spot) > 0) {
                r.value = black.value() + hA * Math.pow((spot / Sk), lambda) / (1 - chi);
            } else {
                r.value = phi * (spot - payoff.strike());
            }

            final double /* @Real */temp_chi_prime = (2 * b / spot) * Math.log(spot / Sk);
            final double /* @Real */chi_prime = temp_chi_prime + c / spot;
            final double /* @Real */chi_double_prime = 2 * b / (spot * spot) - temp_chi_prime / spot - c / (spot * spot);
            greeks.delta = phi * dividendDiscount * cumNormalDist.op(phi * d1_Sk)
            + (lambda / (spot * (1 - chi)) + chi_prime / ((1 - chi)*(1 - chi))) *
            (phi * (Sk - payoff.strike()) - black_Sk) * Math.pow((spot/Sk), lambda);

            greeks.gamma = phi * dividendDiscount * normalDist.op(phi*d1_Sk) /
            (spot * Math.sqrt(variance))
            + (2 * lambda * chi_prime / (spot * (1 - chi) * (1 - chi))
                    + 2 * chi_prime * chi_prime / ((1 - chi) * (1 - chi) * (1 - chi))
                    + chi_double_prime / ((1 - chi) * (1 - chi))
                    + lambda * (1 - lambda) / (spot * spot * (1 - chi)))
                    * (phi * (Sk - payoff.strike()) - black_Sk)
                    * Math.pow((spot/Sk), lambda);
        }

    } // end of "early exercise can be optimal"

}
