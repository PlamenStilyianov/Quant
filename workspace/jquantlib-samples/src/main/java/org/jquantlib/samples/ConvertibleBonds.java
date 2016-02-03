/*
 Copyright (C) 2009 Daniel Kong

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
import org.jquantlib.Settings;
import org.jquantlib.cashflow.Callability;
import org.jquantlib.cashflow.FixedDividend;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.daycounters.Thirty360;
import org.jquantlib.exercise.AmericanExercise;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.CallabilitySchedule;
import org.jquantlib.instruments.DividendSchedule;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.bonds.ConvertibleFixedCouponBond;
import org.jquantlib.instruments.bonds.SoftCallability;
import org.jquantlib.methods.lattices.AdditiveEQPBinomialTree;
import org.jquantlib.methods.lattices.CoxRossRubinstein;
import org.jquantlib.methods.lattices.JarrowRudd;
import org.jquantlib.methods.lattices.Joshi4;
import org.jquantlib.methods.lattices.LeisenReimer;
import org.jquantlib.methods.lattices.Tian;
import org.jquantlib.methods.lattices.Trigeorgis;
import org.jquantlib.pricingengines.BinomialConvertibleEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;

/**
 * This example evaluates convertible bond prices.
 *
 * @see http://quantlib.org/reference/_convertible_bonds_8cpp-example.html
 *
 * @author Daniel Kong
 */
public class ConvertibleBonds implements Runnable {

    public static void main(final String[] args) {
        new ConvertibleBonds().run();
    }

    @Override
    public void run() {

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();
        QL.info("Started calculation at: " + clock.getElapsedTime());

        // actually never used.....
        final Option.Type type = Option.Type.Put;

        final double underlying = 36.0;
        final double spreadRate = 0.005;

        final double dividendYield = 0.02;
        final double riskFreeRate = 0.06;
        final double volatility = 0.20;

        final int settlementDays = 3;
        final int length = 5;
        final double redemption = 100.0;
        final double conversionRatio = redemption/underlying;

        final Calendar calendar = new Target();
        //adjust today to the next business day...
        final Date today = calendar.adjust(Date.todaysDate());
        QL.info("Today's date is adjusted by the default business day convention is: " + today.shortDate());
        // set the evaluation date to the adjusted today's date
        new Settings().setEvaluationDate(today);
        QL.info("Set the global evaluation date to the adjusted today's date: " + today.shortDate());

        //Set up settlement, exercise and issue dates
        final Date settlementDate = calendar.advance(today, settlementDays, TimeUnit.Days);
        QL.info("SettlementDate is: " + settlementDate.shortDate());
        QL.info("Check that we haven't messed up with references --> today's date is still: " + today.shortDate());
        final Date exerciseDate = calendar.advance(settlementDate, length, TimeUnit.Years);
        QL.info("Excercise date is: " + exerciseDate.shortDate());
        final Date issueDate = calendar.advance(exerciseDate, -length, TimeUnit.Years);
        QL.info("Issue date is: " + issueDate.shortDate());

        //Fix business day convention and compounding?? frequency
        final BusinessDayConvention convention = BusinessDayConvention.ModifiedFollowing;
        final Frequency frequency = Frequency.Annual;

        final Schedule schedule = new Schedule(
                issueDate, exerciseDate,
                new Period(frequency), calendar,
                convention, convention,
                DateGeneration.Rule.Backward, false);

        final DividendSchedule dividends = new DividendSchedule();
        final CallabilitySchedule callability = new CallabilitySchedule();

        final double[] coupons = new double[] { 1.0, 0.05 };

        final DayCounter bondDayCount = new Thirty360();

        // Call dates, years 2, 4
        final int[] callLength = { 2, 4 };
        // Put dates year 3
        final int[] putLength = { 3 };

        final double[] callPrices = {101.5, 100.85};
        final double[] putPrices = { 105.0 };

        for(int i=0; i<callLength.length; i++){
            callability.add(
                    new SoftCallability(
                        new Callability.Price(callPrices[i], Callability.Price.Type.Clean),
                        schedule.date(callLength[i]),
                        1.20));
        }

        for (int j=0; j<putLength.length; j++) {
            callability.add(
                    new Callability(
                            new Callability.Price(putPrices[j],Callability.Price.Type.Clean),
                            Callability.Type.Put,
                            schedule.date(putLength[j])));
        }

        // Assume dividends are paid every 6 months.
        for (final Date d = today.add(new Period(6, TimeUnit.Months)); d.lt(exerciseDate); d.addAssign(new Period(6, TimeUnit.Months))) {
            dividends.add(new FixedDividend(1.0, d));
        }

        final DayCounter dayCounter = new Actual365Fixed();
        /*@Time*/ final double maturity = dayCounter.yearFraction(settlementDate,exerciseDate);

        System.out.println("option type = "+type);
        System.out.println("Time to maturity = "+maturity);
        System.out.println("Underlying price = "+underlying);
        System.out.println("Risk-free interest rate = "+riskFreeRate);
        System.out.println("Dividend yield = "+dividendYield);
        System.out.println("Volatility = "+volatility);
        System.out.println("");
        System.out.println("");


        // define line formatting
        //              "         1         2         3         4         5         6         7         8         9"
        //              "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
        //              "                         Tree type      European      American";
        //              "================================== ============= ============="
        //              "1234567890123456789012345678901234 123.567890123 123.567890123";
        final String fmt    = "%34s %13.9f %13.9f\n";
        final String fmttbd = "%34s %13.9f %13.9f (TO BE DONE)\n";


        // write column headings
        //                 "         1         2         3         4         5         6         7         8"
        //                 "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
        System.out.println("Tsiveriotis-Fernandes method");
        System.out.println("                         Tree type      European      American");
        System.out.println("================================== ============= =============");


        final Exercise europeanExercise = new EuropeanExercise(exerciseDate);
        final Exercise americanExercise = new AmericanExercise(settlementDate,exerciseDate);

        final Handle<Quote> underlyingH = new Handle<Quote>(new SimpleQuote(underlying));
        final Handle<YieldTermStructure> flatTermStructure = new Handle<YieldTermStructure>(
                new FlatForward(settlementDate, riskFreeRate, dayCounter));
        final Handle<YieldTermStructure> flatDividendTS = new Handle<YieldTermStructure>(
                new FlatForward(settlementDate, dividendYield, dayCounter));
        final Handle<BlackVolTermStructure> flatVolTS = new Handle<BlackVolTermStructure>(
                new BlackConstantVol(settlementDate, calendar, volatility, dayCounter));

        final BlackScholesMertonProcess stochasticProcess = new BlackScholesMertonProcess(
                underlyingH, flatDividendTS, flatTermStructure, flatVolTS);

        final int timeSteps = 801;

        final Handle<Quote> creditSpread = new Handle<Quote>(new SimpleQuote(spreadRate));

        //XXX rate and discountCurve not being used in original QuantLib/C++ sources
        // final Quote rate = new SimpleQuote(riskFreeRate);
        //
        // final Handle<YieldTermStructure> discountCurve = new Handle<YieldTermStructure>(
        //         new FlatForward(today, new Handle<Quote>(rate), dayCounter));

        final ConvertibleFixedCouponBond europeanBond = new ConvertibleFixedCouponBond(
                europeanExercise, conversionRatio, dividends, callability,
                creditSpread, issueDate, settlementDays,
                coupons, bondDayCount, schedule, redemption);

        final ConvertibleFixedCouponBond americanBond = new ConvertibleFixedCouponBond(
                americanExercise, conversionRatio, dividends, callability,
                creditSpread, issueDate, settlementDays,
                coupons, bondDayCount, schedule, redemption);

        String method;
        PricingEngine engine;

        method = "Jarrow-Rudd";
        engine = new BinomialConvertibleEngine<JarrowRudd>(JarrowRudd.class, stochasticProcess, timeSteps);
        europeanBond.setPricingEngine(engine);
        americanBond.setPricingEngine(engine);
        System.out.printf(fmt, method, europeanBond.NPV(), americanBond.NPV() );

        method = "Cox-Ross-Rubinstein";
        engine = new BinomialConvertibleEngine<CoxRossRubinstein>(CoxRossRubinstein.class, stochasticProcess, timeSteps);
        europeanBond.setPricingEngine(engine);
        americanBond.setPricingEngine(engine);
        System.out.printf(fmt, method, europeanBond.NPV(), americanBond.NPV() );

        method = "Additive equiprobabilities";
        engine = new BinomialConvertibleEngine<AdditiveEQPBinomialTree>(AdditiveEQPBinomialTree.class, stochasticProcess, timeSteps);
        europeanBond.setPricingEngine(engine);
        americanBond.setPricingEngine(engine);
        System.out.printf(fmt, method, europeanBond.NPV(), americanBond.NPV() );

        method = "Trigeorgis";
        engine = new BinomialConvertibleEngine<Trigeorgis>(Trigeorgis.class, stochasticProcess, timeSteps);
        europeanBond.setPricingEngine(engine);
        americanBond.setPricingEngine(engine);
        System.out.printf(fmt, method, europeanBond.NPV(), americanBond.NPV() );

        method = "Tian";
        engine = new BinomialConvertibleEngine<Tian>(Tian.class, stochasticProcess, timeSteps);
        europeanBond.setPricingEngine(engine);
        americanBond.setPricingEngine(engine);
        System.out.printf(fmt, method, europeanBond.NPV(), americanBond.NPV() );

        method = "Leisen-Reimer";
        engine = new BinomialConvertibleEngine<LeisenReimer>(LeisenReimer.class, stochasticProcess, timeSteps);
        europeanBond.setPricingEngine(engine);
        americanBond.setPricingEngine(engine);
        System.out.printf(fmt, method, europeanBond.NPV(), americanBond.NPV() );

        method = "Joshi";
        engine = new BinomialConvertibleEngine<Joshi4>(Joshi4.class, stochasticProcess, timeSteps);
        europeanBond.setPricingEngine(engine);
        americanBond.setPricingEngine(engine);
        System.out.printf(fmt, method, europeanBond.NPV(), americanBond.NPV() );


        clock.stopClock();
        clock.log();
    }

}
