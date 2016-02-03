/*
 Copyright (C) 2009 Apratim Rajendra

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

package org.jquantlib.samples;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.math.distributions.NormalDistribution;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.processes.EulerDiscretization;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.termstructures.BlackVarianceTermStructure;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackVarianceCurve;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.calendars.UnitedStates;
import org.jquantlib.time.calendars.UnitedStates.Market;

/**
 * This class explores StochasticProcess1D(GeneralizedBlackScholesProcess)/LinearDiscretization(EulerDiscretization)
 *
 * @author Apratim Rajendra
 *
 */
public class Processes implements Runnable {

    public static void main(final String[] args) {
        new Processes().run();
    }

    @Override
    public void run() {

        QL.validateExperimentalMode();

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();

        final Date today  = Date.todaysDate();
        final Date date10 = today.clone().addAssign(10);
        final Date date15 = today.clone().addAssign(15);
        final Date date18 = today.clone().addAssign(18);
        final Date date20 = today.clone().addAssign(20);
        final Date date25 = today.clone().addAssign(25);
        final Date date30 = today.clone().addAssign(30);
        final Date date40 = today.clone().addAssign(40);


        System.out.println("//============================StochasticProcess1D/LinearDiscretization=========================");

        //Creating stock quote handle
        final SimpleQuote stockQuote = new SimpleQuote(5.6);
        final RelinkableHandle<Quote>  handleToStockQuote = new RelinkableHandle<Quote>(stockQuote);

        //Creating black volatility term structure

        //Following is the time axis
        final Date[] dates = { date10.clone(), date15.clone(), date20.clone(), date25.clone(), date30.clone(), date40.clone() };

        //Following is the volatility axis
        final double[] volatilities = {0.1,0.2,0.3,0.4,0.5,0.6};

        //Following is the curve
        final BlackVarianceTermStructure varianceCurve = new BlackVarianceCurve(today, dates,volatilities, new Actual365Fixed(), false);
        ((BlackVarianceCurve)varianceCurve).setInterpolation();

        //Dividend termstructure
        final SimpleQuote dividendQuote = new SimpleQuote(0.3);
        final RelinkableHandle<Quote>  handleToInterestRateQuote = new RelinkableHandle<Quote>(dividendQuote);
        final YieldTermStructure dividendTermStructure = new FlatForward(2,new UnitedStates(Market.NYSE),handleToInterestRateQuote, new Actual365Fixed(), Compounding.Continuous,Frequency.Daily);

        //Risk free term structure
        final SimpleQuote riskFreeRateQuote = new SimpleQuote(0.3);
        final RelinkableHandle<Quote>  handleToRiskFreeRateQuote = new RelinkableHandle<Quote>(riskFreeRateQuote);
        final YieldTermStructure riskFreeTermStructure = new FlatForward(2,new UnitedStates(Market.NYSE),handleToRiskFreeRateQuote, new Actual365Fixed(), Compounding.Continuous,Frequency.Daily);

        //Creating the process
        final StochasticProcess1D process = new GeneralizedBlackScholesProcess(handleToStockQuote,new RelinkableHandle<YieldTermStructure>(dividendTermStructure),new RelinkableHandle<YieldTermStructure>(riskFreeTermStructure),new RelinkableHandle<BlackVolTermStructure>(varianceCurve),new EulerDiscretization());

        //Calculating the drift of the stochastic process after time = 18th day from today with value of the stock as specified from the quote
        //The drift = (riskFreeForwardRate - dividendForwardRate) - (Variance/2)
        System.out.println("The drift of the process after time = 18th day from today with value of the stock as specified from the quote = "+process.drift(process.time(date18.clone()), handleToStockQuote.currentLink().value()));

        //Calculating the diffusion of the process after time = 18th day from today with value of the stock as specified from the quote
        //The diffusion = volatiltiy of the stochastic process
        System.out.println("The diffusion of the process after time = 18th day from today with value of the stock as specified from the quote = "+process.diffusion(process.time(date18.clone()), handleToStockQuote.currentLink().value()));

        //Calulating the standard deviation of the process after time = 18th day from today with value of the stock as specified from the quote
        //The standard deviation = volatility*sqrt(dt)
        System.out.println("The stdDeviation of the process after time = 18th day from today with value of the stock as specified from the quote = "+process.stdDeviation(process.time(date18.clone()), handleToStockQuote.currentLink().value(), 0.01));

        //Calulating the variance of the process after time = 18th day from today with value of the stock as specified from the quote
        //The variance = volatility*volatility*dt
        System.out.println("The variance of the process after time = 18th day from today with value of the stock as specified from the quote = "+process.variance(process.time(date18.clone()), handleToStockQuote.currentLink().value(), 0.01));

        //Calulating the expected value of the stock quote after time = 18th day from today with the current value of the stock as specified from the quote
        //The expectedValue = intialValue*exp(drift*dt)-----can be obtained by integrating----->dx/x= drift*dt
        System.out.println("Expected value = "+process.expectation(process.time(date18.clone()), handleToStockQuote.currentLink().value(), 0.01));

        //Calulating the exact value of the stock quote after time = 18th day from today with the current value of the stock as specified from the quote
        //The exact value = intialValue*exp(drift*dt)*exp(volatility*sqrt(dt))-----can be obtained by integrating----->dx/x= drift*dt+volatility*sqrt(dt)
        System.out.println("Exact value = "+process.evolve(process.time(date18.clone()), 6.7, .001, new NormalDistribution().op(Math.random())));

        //Calculating the drift of the stochastic process after time = 18th day from today with value of the stock as specified from the quote
        //The drift = (riskFreeForwardRate - dividendForwardRate) - (Variance/2)
        final Array drift = process.drift(process.time(date18.clone()), new Array(1).fill(5.6));
        System.out.println("The drift of the process after time = 18th day from today with value of the stock as specified from the quote");

        //Calculating the diffusion of the process after time = 18th day from today with value of the stock as specified from the quote
        //The diffusion = volatiltiy of the stochastic process
        final Matrix diffusion = process.diffusion(process.time(date18.clone()), new Array(1).fill(5.6));
        System.out.println("The diffusion of the process after time = 18th day from today with value of the stock as specified from the quote");

        //Calulating the standard deviation of the process after time = 18th day from today with value of the stock as specified from the quote
        //The standard deviation = volatility*sqrt(dt)
        final Matrix stdDeviation = process.stdDeviation(process.time(date18.clone()), new Array(1).fill(5.6), 0.01);
        System.out.println("The stdDeviation of the process after time = 18th day from today with value of the stock as specified from the quote");

        //Calulating the expected value of the stock quote after time = 18th day from today with the current value of the stock as specified from the quote
        //The expectedValue = intialValue*exp(drift*dt)-----can be obtained by integrating----->dx/x= drift*dt
        final Array expectation = process.expectation(process.time(date18.clone()), new Array(1).fill(5.6), 0.01);
        System.out.println("Expected value = "+expectation.first());

        //Calulating the exact value of the stock quote after time = 18th day from today with the current value of the stock as specified from the quote
        //The exact value = intialValue*exp(drift*dt)*exp(volatility*sqrt(dt))-----can be obtained by integrating----->dx/x= drift*dt+volatility*sqrt(dt)
        final Array evolve = process.evolve(process.time(date18.clone()), new Array(1).fill(6.7), .001, new Array(1).fill(new NormalDistribution().op(Math.random()) ));
        System.out.println("Exact value = "+evolve.first());

        //Calculating covariance of the process
        final Matrix covariance = process.covariance(process.time(date18.clone()),  new Array(1).fill(5.6), 0.01);
        System.out.println("Covariance = "+covariance.get(0, 0));

        clock.stopClock();
        clock.log();
    }

}
