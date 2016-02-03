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
 Copyright (C) 2002, 2003, 2004 Ferdinando Ametrano
 Copyright (C) 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004 StatPro Italia srl

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
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.Instrument;
import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.Payoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.math.Ops;
import org.jquantlib.math.integrals.SegmentIntegral;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Pricing engine for European vanilla options using integral approach
 *
 * @author Richard Gomes
 */
public class IntegralEngine extends OneAssetOption.EngineImpl {

    // TODO: refactor messages
    private static final String NOT_AN_AMERICAN_OPTION = "not an American Option";
    private static final String NON_STRIKED_PAYOFF_GIVEN = "non-striked payoff given";
    private static final String BLACK_SCHOLES_PROCESS_REQUIRED = "Black-Scholes process required";


    //
    // private final fields
    //

    private final GeneralizedBlackScholesProcess process;
    private final Option.ArgumentsImpl     a;
    private final Instrument.ResultsImpl   r;


    //
    // public constructors
    //

    public IntegralEngine(final GeneralizedBlackScholesProcess process) {
        this.a = (Option.ArgumentsImpl)arguments_;
        this.r = (Instrument.ResultsImpl)results_;
        this.process = process;
        this.process.addObserver(this);
    }


    //
    // implements PricingEngine
    //

    // TODO: define tolerance for calculate()
    @Override
    public void calculate() {
        QL.require(a.exercise.type()==Exercise.Type.European , NOT_AN_AMERICAN_OPTION); // TODO: message
        QL.require(a.payoff instanceof StrikedTypePayoff , NON_STRIKED_PAYOFF_GIVEN); // TODO: message
        final StrikedTypePayoff payoff = (StrikedTypePayoff) a.payoff;

        final double variance = process.blackVolatility().currentLink().blackVariance(a.exercise.lastDate(), payoff.strike());
        final double /* @DiscountFactor */dividendDiscount = process.dividendYield().currentLink().discount(a.exercise.lastDate());
        final double /* @DiscountFactor */riskFreeDiscount = process.riskFreeRate().currentLink().discount(a.exercise.lastDate());
        final double /* @Rate */drift = Math.log(dividendDiscount / riskFreeDiscount) - 0.5 * variance;

        final Integrand f = new Integrand(a.payoff, process.stateVariable().currentLink().value(), drift, variance);
        final SegmentIntegral integrator = new SegmentIntegral(5000);

        final double infinity = 10.0*Math.sqrt(variance);
        r.value =
            process.riskFreeRate().currentLink().discount(a.exercise.lastDate()) /
            Math.sqrt(2.0*Math.PI*variance) * integrator.op(f, drift-infinity, drift+infinity);
    }


    //
    // private inner classes
    //

    private static class Integrand implements Ops.DoubleOp {

        private final Payoff payoff;
        private final double s0;
        private final double /* @Rate */ drift;
        private final double variance;

        public Integrand(final Payoff payoff, final double s0, final double /* @Rate */drift, final double variance) {
            this.payoff = payoff;
            this.s0 = s0;
            this.drift = drift;
            this.variance = variance;
        }

        public double op(final double x) {
            final double temp = s0 * Math.exp(x);
            final double result = payoff.get(temp);
            return result * Math.exp(-(x - drift) * (x - drift) / (2.0 * variance));
        }

    }

}
