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

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.AverageType;
import org.jquantlib.instruments.DiscreteAveragingAsianOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.math.Constants;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.math.distributions.NormalDistribution;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;


/**
 * Pricing engine for European discrete geometric average price Asian
 * <p>
 * This class implements a discrete geometric average price Asian option, with European exercise. The formula is from "Asian
 * Option", E. Levy (1997) in "Exotic Options: The State of the Art", edited by L. Clewlow, C. Strickland, pag 65-97
 *
 * @author <Richard Gomes>
 */
public class AnalyticDiscreteGeometricAveragePriceAsianEngine extends DiscreteAveragingAsianOption.EngineImpl {

    // TODO: refactor messages
    private static final String NOT_AN_EUROPEAN_OPTION = "not an European Option";
    private static final String NON_STRIKED_PAYOFF_GIVEN = "non-striked payoff given";
    private static final String BLACK_SCHOLES_PROCESS_REQUIRED = "Black-Scholes process required";

    //
    // private final fields
    //

    private final GeneralizedBlackScholesProcess process;
    private final DiscreteAveragingAsianOption.ArgumentsImpl a;
    private final DiscreteAveragingAsianOption.ResultsImpl   r;
    private final Option.GreeksImpl greeks;
    private final Option.MoreGreeksImpl moreGreeks;


    //
    // public constructors
    //

    public AnalyticDiscreteGeometricAveragePriceAsianEngine(final GeneralizedBlackScholesProcess process) {
        this.process = process;
        this.a = (DiscreteAveragingAsianOption.ArgumentsImpl)arguments_;
        this.r = (DiscreteAveragingAsianOption.ResultsImpl)results_;
        this.greeks = r.greeks();
        this.moreGreeks = r.moreGreeks();
        process.addObserver(this);
    }



    //
    // implements PricingEngine
    //

    @Override
    public void calculate() /*@ReadOnly*/{
        QL.require(a.exercise.type()==Exercise.Type.European , NOT_AN_EUROPEAN_OPTION); // TODO: message
        final StrikedTypePayoff payoff = (StrikedTypePayoff) a.payoff;
        QL.require(a.payoff instanceof StrikedTypePayoff , NON_STRIKED_PAYOFF_GIVEN); // TODO: message

        /*
         * This engine cannot really check for the averageType==Geometric
         * since it can be used as control variate for the Arithmetic version
         *
         * QL_REQUIRE(arguments_.averageType == Average::Geometric, "not a geometric average option");
         */

        double runningLog;
        int pastFixings;
        if (a.averageType == AverageType.Geometric) {
            if (!(a.runningAccumulator>0.0))
                throw new IllegalArgumentException(
                        "positive running product required: "
                        + a.runningAccumulator + " not allowed");
            runningLog = Math.log(a.runningAccumulator);
            pastFixings = a.pastFixings;
        } else {  // it is being used as control variate
            runningLog = 1.0;
            pastFixings = 0;
        }

        final Date referenceDate = process.riskFreeRate().currentLink().referenceDate();
        final DayCounter rfdc  = process.riskFreeRate().currentLink().dayCounter();
        final DayCounter divdc = process.dividendYield().currentLink().dayCounter();
        final DayCounter voldc = process.blackVolatility().currentLink().dayCounter();

        // TODO: consider double[] instead
        final List<Double> fixingTimes = new ArrayList<Double>();
        /*@Size*/ int i;
        for (i=0; i<a.fixingDates.size(); i++) {
            if (a.fixingDates.get(i).ge(referenceDate)) {
                /*@Time*/ final double t = voldc.yearFraction(referenceDate,
                        a.fixingDates.get(i));
                fixingTimes.add(Double.valueOf(t));
            }
        }

        /*@Size*/ final int remainingFixings = fixingTimes.size();
        /*@Size*/ final int numberOfFixings = pastFixings + remainingFixings;
        /*@Real*/ final double N = numberOfFixings;

        /*@Real*/ final double pastWeight = pastFixings/N;
        /*@Real*/ final double futureWeight = 1.0-pastWeight;

        double timeSum = 0.0;
        for (int k=0; k<fixingTimes.size(); k++) {
            timeSum += fixingTimes.get(k);
        }

        /*@Volatility*/ final double vola = process.blackVolatility().currentLink().blackVol(a.exercise.lastDate(), payoff.strike());

        /*@Real*/ double temp = 0.0;
        for (i=pastFixings+1; i<numberOfFixings; i++) {
            temp += fixingTimes.get(i-pastFixings-1)*(N-i);
        }

        /*@Real*/ final double variance = vola*vola /N/N * (timeSum+ 2.0*temp);
        /*@Real*/ final double dsigG_dsig = Math.sqrt((timeSum + 2.0*temp))/N;
        /*@Real*/ final double sigG = vola * dsigG_dsig;
        /*@Real*/ final double dmuG_dsig = -(vola * timeSum)/N;

        final Date exDate = a.exercise.lastDate();
        /*@Rate*/ final double dividendRate = process.dividendYield().currentLink().
        zeroRate(exDate, divdc, Compounding.Continuous, Frequency.NoFrequency).rate();
        /*@Rate*/ final double riskFreeRate = process.riskFreeRate().currentLink().
        zeroRate(exDate, rfdc, Compounding.Continuous, Frequency.NoFrequency).rate();
        /*@Rate*/ final double nu = riskFreeRate - dividendRate - 0.5*vola*vola;

        /*@Real*/ final double  s = process.stateVariable().currentLink().value();

        /*@Real*/ final double muG = pastWeight * runningLog +
        futureWeight * Math.log(s) + nu*timeSum/N;
        /*@Real*/ final double forwardPrice = Math.exp(muG + variance / 2.0);

        /*@DiscountFactor*/ final double riskFreeDiscount = process.riskFreeRate().currentLink().
        discount(a.exercise.lastDate());

        final BlackCalculator black = new BlackCalculator(payoff, forwardPrice, Math.sqrt(variance), riskFreeDiscount);

        r.value = black.value();
        greeks.delta = futureWeight*black.delta(forwardPrice)*forwardPrice/s;
        greeks.gamma = forwardPrice*futureWeight/(s*s)*(black.gamma(forwardPrice)*futureWeight*forwardPrice
                - pastWeight*black.delta(forwardPrice) );

        /*@Real*/ double Nx_1, nx_1;
        final CumulativeNormalDistribution CND = new CumulativeNormalDistribution();
        final NormalDistribution ND = new NormalDistribution();

        if (sigG > Constants.QL_EPSILON) {
            /*@Real*/ final double x_1  = (muG-Math.log(payoff.strike())+variance)/sigG;
            Nx_1 = CND.op(x_1);
            nx_1 = ND.op(x_1);
        } else {
            Nx_1 = (muG > Math.log(payoff.strike()) ? 1.0 : 0.0);
            nx_1 = 0.0;
        }
        greeks.vega = forwardPrice * riskFreeDiscount * ( (dmuG_dsig + sigG * dsigG_dsig)*Nx_1 + nx_1*dsigG_dsig );

        if (payoff.optionType() == Option.Type.Put) {
            greeks.vega -= riskFreeDiscount * forwardPrice * (dmuG_dsig + sigG * dsigG_dsig);
        }

        /*@Time*/ final double tRho = rfdc.yearFraction(process.riskFreeRate().currentLink().referenceDate(), a.exercise.lastDate());
        greeks.rho = black.rho(tRho)*timeSum/(N*tRho) - (tRho-timeSum/N) * r.value;

        /*@Time*/ final double tDiv = divdc.yearFraction(
                process.dividendYield().currentLink().referenceDate(),
                a.exercise.lastDate());

        greeks.dividendRho = black.dividendRho(tDiv)*timeSum/(N*tDiv);
        moreGreeks.strikeSensitivity = black.strikeSensitivity();
        greeks.theta = greeks.blackScholesTheta(process, r.value, greeks.delta, greeks.gamma);
    }

}
