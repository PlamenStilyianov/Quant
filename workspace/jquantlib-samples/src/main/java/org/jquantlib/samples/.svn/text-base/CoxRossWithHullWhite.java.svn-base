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
 Quantlib license

 QuantLib is
 Copyright (C) 2002, 2003, 2004, 2005 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003, 2004, 2005 StatPro Italia srl

 Copyright (C) 2002, 2003, 2004 Decillion Pty(Ltd)
 Copyright (C) 2001, 2002, 2003 Nicolas Di C???sar???
 Copyright (C) 2003, 2004 Neil Firth
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2003 Niels Elken S???nderby

 Copyright (C) 2004 FIMAT Group
 Copyright (C) 2003, 2004 Roman Gitlin
 Copyright (C) 2004 M-Dimension Consulting Inc.
 Copyright (C) 2004 Mike Parker
 Copyright (C) 2004 Walter Penschke
 Copyright (C) 2004 Gianni Piolanti
 Copyright (C) 2003 Kawanishi Tomoya
 Copyright (C) 2004 Jeff Yu

 Copyright (C) 2005 Serkan Atalik
 Copyright (C) 2005 Gary Kennedy
 Copyright (C) 2005 Klaus Spanderen
 Copyright (C) 2005 Joseph Wang
 Copyright (C) 2005 Charles Whitmore

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.

 Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 Neither the names of the copyright holders nor the names of the QuantLib
 Group and its contributors may be used to endorse or promote products
 derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 **/

package org.jquantlib.samples;


import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.AmericanExercise;
import org.jquantlib.exercise.BermudanExercise;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.EuropeanOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.Payoff;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.processes.HullWhiteProcess;
import org.jquantlib.processes.StochasticProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;

/**
 * Calculates equity option values using HullWhite process and Cox-Ross-Rubinstein engine
 *
 * @see http://quantlib.org/reference/_equity_option_8cpp-example.html
 *
 * @author Richard Gomes
 */
public class CoxRossWithHullWhite implements Runnable {

    public static void main(final String[] args) {
        new CoxRossWithHullWhite().run();
    }

    @Override
    public void run() {

        QL.validateExperimentalMode();

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();

        // set up dates
        final Calendar calendar = new Target();
        final Date todaysDate = new Date(15, Month.May, 1998);
        final Date settlementDate = new Date(17, Month.May, 1998);
        new Settings().setEvaluationDate(todaysDate);

        // our options
        final Option.Type type = Option.Type.Put;
        final double strike = 40.0;
        final double underlying = 36.0;
        /*@Rate*/final double riskFreeRate = 0.06;
        final double volatility = 0.2;
        final double dividendYield = 0.00;

        final Date maturity = new Date(17, Month.May, 1999);
        final DayCounter dayCounter = new Actual365Fixed();

        // write column headings
        //                 "         1         2         3         4         5         6         7         8"
        //                 "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
        System.out.println("                            Method      European      Bermudan      American");
        System.out.println("================================== ============= ============= =============");

        // Define exercise for European Options
        final Exercise europeanExercise = new EuropeanExercise(maturity);

        // Define exercise for Bermudan Options
        final int bermudanForwards = 4;
        final Date[] exerciseDates = new Date[bermudanForwards];
        for (int i = 1; i <= bermudanForwards; i++) {
            exerciseDates[i-1] = settlementDate.add(new Period(3 * i, TimeUnit.Months));
        }
        final Exercise bermudanExercise = new BermudanExercise(exerciseDates);

        // Define exercise for American Options
        final Exercise americanExercise = new AmericanExercise(settlementDate, maturity);

        // bootstrap the yield/dividend/volatility curves
        final Handle<Quote> underlyingH = new Handle<Quote>(new SimpleQuote(underlying));
        final Handle<YieldTermStructure> flatDividendTS = new Handle<YieldTermStructure>(new FlatForward(settlementDate, dividendYield, dayCounter));
        final Handle<YieldTermStructure> flatTermStructure = new Handle<YieldTermStructure>(new FlatForward(settlementDate, riskFreeRate, dayCounter));
        final Handle<BlackVolTermStructure> flatVolTS = new Handle<BlackVolTermStructure>(new BlackConstantVol(settlementDate, calendar, volatility, dayCounter));
        final Payoff payoff = new PlainVanillaPayoff(type, strike);

        // European Options
        final VanillaOption europeanOption = new EuropeanOption(payoff, europeanExercise);

        // Bermundan options (can be thought as a collection of European Options)
        final VanillaOption bermudanOption = new VanillaOption(payoff, bermudanExercise);

        // American Options
        final VanillaOption americanOption = new VanillaOption(payoff, americanExercise);

        // define line formatting
        //              "         0         1         2         3         4         5         6         7         8"
        //              "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
        //              "Method                                       European      Bermudan      American      ";
        //              "12345678901234567890123456789012345678901234 123.567890123 123.567890123 123.567890123";
        final String fmt    = "%34s %13.9f %13.9f %13.9f\n";
        final String fmttbd = "%34s %13.9f %13.9f %13.9f  (TO BE DONE)\n";

        final StochasticProcess hwProcess = new HullWhiteProcess(flatDividendTS, 0, 0);
        //XXX final BlackScholesMertonProcess bsmProcess = new BlackScholesMertonProcess(underlyingH, flatDividendTS, flatTermStructure, flatVolTS);

        final String method = "CoxRossRubinstein with HullWhite";
        final int timeSteps = 801;

//        europeanOption.setPricingEngine(new BinomialVanillaEngine<ExtendedCoxRossRubinstein>(hwProcess, timeSteps) { /* anpnymous */ } );
//        bermudanOption.setPricingEngine(new BinomialVanillaEngine<ExtendedCoxRossRubinstein>(hwProcess, timeSteps) { /* anpnymous */ } );
//        americanOption.setPricingEngine(new BinomialVanillaEngine<ExtendedCoxRossRubinstein>(hwProcess, timeSteps) { /* anpnymous */ } );
//        System.out.printf(fmt, method, europeanOption.NPV(), bermudanOption.NPV(), americanOption.NPV() );
//
//        clock.stopClock();
//        clock.log();

        throw new UnsupportedOperationException();

    }

}
