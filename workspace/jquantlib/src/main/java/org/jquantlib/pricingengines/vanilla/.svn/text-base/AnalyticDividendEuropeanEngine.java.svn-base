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
 Copyright (C) 2004 StatPro Italia srl

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
import org.jquantlib.cashflow.CashFlow;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.DividendVanillaOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;

/**
 * Analytic pricing engine for European options with discrete dividends
 *
 * @category vanillaengines
 *
 * @author <Richard Gomes>
 */
public class AnalyticDividendEuropeanEngine extends DividendVanillaOption.EngineImpl {

    //
    // private final fields
    //

    private final GeneralizedBlackScholesProcess process;
    private final DividendVanillaOption.ArgumentsImpl a;
    private final DividendVanillaOption.ResultsImpl   r;
    private final Option.GreeksImpl greeks;
    private final Option.MoreGreeksImpl moreGreeks;


    //
    // public constructors
    //

    public AnalyticDividendEuropeanEngine(final GeneralizedBlackScholesProcess process) {
        this.a = (DividendVanillaOption.ArgumentsImpl)arguments_;
        this.r = (DividendVanillaOption.ResultsImpl)results_;
        this.greeks = r.greeks();
        this.moreGreeks = r.moreGreeks();
        this.process = process;
        this.process.addObserver(this);
    }

    /* (non-Javadoc)
     * @see org.jquantlib.pricingengines.PricingEngine#calculate()
     */
    @Override
    public void calculate() /* @ReadOnly */ {

        QL.require(a.exercise.type() == Exercise.Type.European, "not an European option"); // TODO: message

        final StrikedTypePayoff payoff = (StrikedTypePayoff) a.payoff;
        QL.require(payoff!=null, "non-striked payoff given"); // TODO: message

        final Date settlementDate = process.riskFreeRate().currentLink().referenceDate();
        double riskless = 0.0;
        for (int i=0; i<a.cashFlow.size(); i++) {
            final CashFlow cashflow = a.cashFlow.get(i);
            if (cashflow.date().gt(settlementDate)) {
                riskless += cashflow.amount() * process.riskFreeRate().currentLink().discount(cashflow.date());
            }
        }

        final double spot = process.stateVariable().currentLink().value() - riskless;
        QL.require(spot > 0.0, "negative or null underlying after subtracting dividends"); // TODO: message

        final /*@DiscountFactor*/ double dividendDiscount = process.dividendYield().currentLink().discount(a.exercise.lastDate());
        final /*@DiscountFactor*/ double riskFreeDiscount = process.riskFreeRate().currentLink().discount(a.exercise.lastDate());
        final double forwardPrice = spot * dividendDiscount / riskFreeDiscount;

        final double variance = process.blackVolatility().currentLink().blackVariance(a.exercise.lastDate(), payoff.strike());
        final BlackCalculator black = new BlackCalculator(payoff, forwardPrice, Math.sqrt(variance), riskFreeDiscount);

        r.value = black.value();
        greeks.delta = black.delta(spot);
        greeks.gamma = black.gamma(spot);

        final DayCounter rfdc  = process.riskFreeRate().currentLink().dayCounter();
        final DayCounter voldc = process.blackVolatility().currentLink().dayCounter();
        /*@Time*/ double t = voldc.yearFraction(process.blackVolatility().currentLink().referenceDate(), a.exercise.lastDate());
        greeks.vega = black.vega(t);

        double delta_theta = 0.0, delta_rho = 0.0;
        for (int i = 0; i < a.cashFlow.size(); i++) {
            final CashFlow cashflow = a.cashFlow.get(i);
            final Date d = cashflow.date();
            if (d.gt(settlementDate)) {
                delta_theta -= cashflow.amount()
                * process.riskFreeRate().currentLink().zeroRate(d, rfdc, Compounding.Continuous, Frequency.Annual).rate()
                * process.riskFreeRate().currentLink().discount(d);
                delta_rho += cashflow.amount() * process.time(d) * process.riskFreeRate().currentLink().discount(t);
            }
        }
        t = process.time(a.exercise.lastDate());
        try {
            greeks.theta = black.theta(spot, t) + delta_theta * black.delta(spot);
        } catch (final ArithmeticException e) {
            greeks.theta = Constants.NULL_REAL;
        }

        greeks.rho = black.rho(t) + delta_rho * black.delta(spot);
    }

}
