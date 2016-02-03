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
Copyright (C) 2003 RiskMap srl

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

package org.jquantlib.testsuite.termstructures;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.math.Closeness;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.AbstractYieldTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.termstructures.yieldcurves.ImpliedTermStructure;
import org.jquantlib.testsuite.util.Flag;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.NullCalendar;
import org.jquantlib.time.calendars.Target;
import org.junit.Ignore;
import org.junit.Test;


public class TermStructuresTest {

    private final Calendar calendar;
    private final int settlementDays;
    private final YieldTermStructure termStructure;
    private final YieldTermStructure dummyTermStructure;


    public TermStructuresTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");

        this.calendar = new Target();
        this.settlementDays = 2;
        this.termStructure = null;
        this.dummyTermStructure = null;

        //FIXME: remove comments when PiecewiseYieldCurve becomes available

        //		calendar = org.jquantlib.time.calendars.Target.getCalendar();
        //        settlementDays = 2;
        //        org.jquantlib.util.Date today = calendar.advance(org.jquantlib.util.DateFactory.getFactory().getTodaysDate());
        //        org.jquantlib.Configuration.getSystemConfiguration(null).getGlobalSettings().setEvaluationDate(today);
        //        org.jquantlib.util.Date settlement = calendar.advance(today,settlementDays,TimeUnit.DAYS);
        //
        //        Datum depositData[] = new Datum[] {
        //        		new Datum( 1, TimeUnit.MONTHS, 4.581 ),
        //        		new Datum( 2, TimeUnit.MONTHS, 4.573 ),
        //        		new Datum( 3, TimeUnit.MONTHS, 4.557 ),
        //        		new Datum( 6, TimeUnit.MONTHS, 4.496 ),
        //        		new Datum( 9, TimeUnit.MONTHS, 4.490 )
        //        };
        //
        //        Datum swapData[] =  new Datum[] {
        //        		  new Datum(  1, TimeUnit.YEARS, 4.54 ),
        //                new Datum(  5, TimeUnit.YEARS, 4.99 ),
        //                new Datum( 10, TimeUnit.YEARS, 5.47 ),
        //                new Datum( 20, TimeUnit.YEARS, 5.89 ),
        //                new Datum( 30, TimeUnit.YEARS, 5.96 )
        //        };
        //
        //        int deposits = depositData.length;
        //        int swaps = swapData.length;
        //
        //        RateHelper<YieldTermStructure> instruments[] = new RateHelper[deposits+swaps];
        //
        //
        //        for (int i=0; i<deposits; i++) {
        //            instruments[i] = new DepositRateHelper<YieldTermStructure>(
        //            						depositData[i].rate/100,
        //            						new Period(depositData[i].n, depositData[i].units),
        //                                    settlementDays, calendar,
        //                                    BusinessDayConvention.MODIFIED_FOLLOWING, true,
        //                                    Actual360.getDayCounter());
        //        }
        //
        //
        //       IborIndex index = new IborIndex(
        //       							"dummy",
        //       							new Period(6,TimeUnit.MONTHS),
        //       							settlementDays,
        //       							calendar,
        //								null,
        //								BusinessDayConvention.MODIFIED_FOLLOWING,
        //								false,
        //								Actual360.getDayCounter());
        //
        //        for (int i=0; i<swaps; ++i) {
        //            instruments[i+deposits] = new SwapRateHelper(
        //            						swapData[i].rate/100,
        //            						new Period(swapData[i].n, swapData[i].units),
        //                                    calendar, Frequency.ANNUAL,
        //                                    BusinessDayConvention.UNADJUSTED, Thirty360.getDayCounter(),
        //                                    index);
        //        }


        //TODO: remove comments
        //        termStructure = new PiecewiseYieldDiscountCurve<LogLinear>(settlement, instruments, Actual360.getDayCounter());
        //
        //        dummyTermStructure = new PiecewiseYieldDiscountCurve<LogLinear>(settlement, instruments, Actual360.getDayCounter());
    }


    @Test
    public void testReferenceChange() {
        QL.info("Testing term structure against evaluation date change...");

        final YieldTermStructure localTermStructure = new FlatForward(settlementDays, new NullCalendar(), 0.03, new Actual360());

        final int days[] = { 10, 30, 60, 120, 360, 720 };
        /*@DiscountFactor*/ final double[] expected = new /*@DiscountFactor*/ double[days.length];

        final Date today = new Settings().evaluationDate();

        for (int i=0; i<days.length; i++) {
            final Date anotherDay = today.add(days[i]);
            expected[i] = localTermStructure.discount(anotherDay);
        }

        final Date nextMonth = today.add(30);
        new Settings().setEvaluationDate(nextMonth);
        /*@DiscountFactor*/ final double[] calculated = new /*@DiscountFactor*/ double[days.length];

        for (int i=0; i<days.length; i++) {
            final Date anotherDay = nextMonth.add(days[i]);
            calculated[i] = localTermStructure.discount(anotherDay);
        }

        for (int i=0; i<days.length; i++) {
            if (!Closeness.isClose(expected[i],calculated[i])) {
                fail("\n  Discount at " + days[i] + " days:\n"
                        + "    before date change: " + expected[i] + "\n"
                        + "    after date change:  " + calculated[i]);
            }
        }
    }


    @Ignore
    @Test
    public void testImplied() {
        QL.info("Testing consistency of implied term structure...");
        fail("***** TEST FAILED :: waiting for implementation of PiecewiseYieldTermStructure *****");

        //	    final double tolerance = 1.0e-10;
        //	    final Date today = settings.getEvaluationDate();
        //	    final Date newToday = today.plus(Period.ONE_YEAR_FORWARD.times(3));
        //	    final Date newSettlement = Target.getCalendar().advance(newToday, settlementDays, TimeUnit.DAYS);
        //	    final Date testDate = newSettlement.plus(Period.ONE_YEAR_FORWARD.times(5));
        //
        //	    final YieldTermStructure implied = new ImpliedTermStructure<YieldTermStructure>(
        //	    		new Handle<YieldTermStructure>(termStructure), newSettlement);
        //
        //	    final /*@DiscountFactor*/ double baseDiscount = termStructure.discount(newSettlement);
        //	    final /*@DiscountFactor*/ double discount = termStructure.discount(testDate);
        //	    final /*@DiscountFactor*/ double impliedDiscount = implied.discount(testDate);
        //
        //        if (Math.abs(discount - baseDiscount*impliedDiscount) > tolerance)
        //        	fail("unable to reproduce discount from implied curve\n"
        //	            + "    calculated: " + baseDiscount*impliedDiscount + "\n"
        //	            + "    expected:   " + discount);
    }


    /**
     * FIXME:
     * This test should be using PiecewiseYieldCurve but was changed to use another TermStructure for the time being whilst
     * PiecewiseYieldCurve is not available.
     */
    @Test
    public void testImpliedObs() {
        QL.info("Testing observability of implied term structure...");

        final Date today = new Settings().evaluationDate();
        final Date newToday = today.add(Period.ONE_YEAR_FORWARD.mul(3));
        final Date newSettlement = new Target().advance(newToday, settlementDays, TimeUnit.Days);

        //FIXME:: Fix RelinkableHandle. The initialization of "h" should be:
        //
        //        final RelinkableHandle<YieldTermStructure> h = new RelinkableHandle<YieldTermStructure>(YieldTermStructure.class);
        //
        // see: http://bugs.jquantlib.org/view.php?id=465
        //
        final RelinkableHandle<YieldTermStructure> h = new RelinkableHandle<YieldTermStructure>(
                new AbstractYieldTermStructure() {
                    @Override
                    protected double discountImpl(final double t) {
                        throw new UnsupportedOperationException();
                    }
                    @Override
                    public Date maxDate() {
                        throw new UnsupportedOperationException();
                    }
                } );

        final YieldTermStructure implied = new ImpliedTermStructure<YieldTermStructure>(h, newSettlement);

        final Flag flag = new Flag();
        implied.addObserver(flag);

        // TODO: initialization needed (dependent of PiecewiseYieldCurve)
        // h.setLink(termStructure);

        // -------------------------------------------------------------------------------------------------------------
        // FIXME: This code was added instead. Please remove when PiecewiseYieldCurve becomes ready
        //
        final Quote quote = new SimpleQuote(100.0);
        final Flag anotherFlag = new Flag();
        quote.addObserver(anotherFlag);
        h.linkTo(new FlatForward(today, new Handle<Quote>(quote), new Actual360()));
        if (!anotherFlag.isUp()) {
            fail("Observer was not notified of term structure change");
        }

        if (!flag.isUp()) {
            fail("Observer was not notified of term structure change");
        }
    }
//    Date today = Settings::instance().evaluationDate();
//    Date newToday = today + 3*Years;
//    Date newSettlement = vars.calendar.advance(newToday,
//                                               vars.settlementDays,Days);
//    RelinkableHandle<YieldTermStructure> h;
//    boost::shared_ptr<YieldTermStructure> implied(
//                                  new ImpliedTermStructure(h, newSettlement));
//    Flag flag;
//    flag.registerWith(implied);
//    h.linkTo(vars.termStructure);
//    if (!flag.isUp())
//        BOOST_ERROR("Observer was not notified of term structure change");


    @Ignore
    @Test
    public void testFSpreaded() {
        QL.info("Testing consistency of forward-spreaded term structure...");
        fail("***** TEST FAILED :: waiting for translation of ForwardSpreadedTermStructure *****");

        //	    final double tolerance = 1.0e-10;
        //	    final Quote me = new SimpleQuote(0.01);
        //	    final Handle<Quote> mh = new Handle(me);
        //
        //	    YieldTermStructure spreaded = new ForwardSpreadedTermStructure( new Handle<YieldTermStructure>(termStructure), mh);
        //	    Date testDate = termStructure.referenceDate().increment(5 * Period.ONE_YEAR_FORWARD.length());
        //	    DayCounter tsdc  = termStructure.dayCounter();
        //	    DayCounter sprdc = spreaded.dayCounter();
        //
        //	    // FIXME :: code review:: could be:: /*@Rate*/ double forward = ... ?????
        //	    InterestRate forward = termStructure.forwardRate(testDate, testDate, tsdc, Compounding.CONTINUOUS, Frequency.NO_FREQUENCY);
        //
        //	    // FIXME :: code review:: could be:: /*@Rate*/ double spreadedForward = ... ?????
        //	    InterestRate spreadedForward = spreaded.forwardRate(testDate, testDate, sprdc, Compounding.CONTINUOUS, Frequency.NO_FREQUENCY);
        //
        //        if (Math.abs(forward.rate() - (spreadedForward.rate() - me.evaluate())) > tolerance) {
        //            fail("unable to reproduce forward from spreaded curve\n"
        //                    + "    calculated: " + (spreadedForward.rate() - me.evaluate()) + "\n"
        //                    + "    expected:   " + forward.rate()
        //                );
        //        }
    }


    @Ignore
    @Test
    public void testFSpreadedObs() {
        QL.info("Testing observability of forward-spreaded term structure...");
        fail("***** TEST FAILED :: waiting for translation of ForwardSpreadedTermStructure *****");

        //	    SimpleQuote me = new SimpleQuote(0.01);
        //	    Handle<Quote> mh = new Handle<Quote>(me);
        //	    RelinkableHandle<YieldTermStructure> h = new RelinkableHandle<YieldTermStructure>(); //(dummyTermStructure_);
        //	    YieldTermStructure spreaded = new ForwardSpreadedTermStructure(h, mh);
        //
        //	    Flag flag = new Flag();
        //	    spreaded.addObserver(flag);
        //	    h.setLink(termStructure);
        //	    if (!flag.isUp()) {
        //	        fail("Observer was not notified of term structure change");
        //	    }
        //
        //	    flag.lower();
        //	    me.setValue(0.005);
        //	    if (!flag.isUp()) {
        //	        fail("Observer was not notified of spread change");
        //	    }
    }


    @Ignore
    @Test
    public void testZSpreaded() {
        QL.info("Testing consistency of zero-spreaded term structure...");
        fail("***** TEST FAILED :: waiting for translation of ZeroSpreadedTermStructure *****");

        //	    double tolerance = 1.0e-10;
        //	    Quote me = new SimpleQuote(0.01);
        //	    Handle<Quote> mh = new Handle(me);
        //	    YieldTermStructure spreaded = new ZeroSpreadedTermStructure(new Handle<YieldTermStructure>(termStructure), mh);
        //	    Date testDate = termStructure.referenceDate().increment(5 * Period.ONE_YEAR_FORWARD.length());
        //	    DayCounter rfdc  = termStructure.dayCounter();
        //
        //	    // FIXME :: code review:: could be:: /*@Rate*/ double zero = ... ?????
        //	    InterestRate zero = termStructure.zeroRate(testDate, rfdc, Compounding.CONTINUOUS, Frequency.NO_FREQUENCY);
        //
        //	    // FIXME :: code review:: could be:: /*@Rate*/ double spreadedZero = ... ?????
        //	    InterestRate spreadedZero = spreaded.zeroRate(testDate, rfdc, Compounding.CONTINUOUS, Frequency.NO_FREQUENCY);
        //
        //	    if (Math.abs(zero.rate() - (spreadedZero.rate() - me.evaluate())) > tolerance) {
        //	        fail(
        //	                "unable to reproduce zero yield from spreaded curve\n"
        //	                + "    calculated: " + (spreadedZero.rate() - me.evaluate()) + "\n"
        //	                + "    expected:   " + zero.rate());
        //	    }
    }


    @Ignore
    @Test
    public void testZSpreadedObs() {
        QL.info("Testing observability of zero-spreaded term structure...");
        fail("***** TEST FAILED :: waiting for translation of ZeroSpreadedTermStructure *****");

        //	    SimpleQuote me = new SimpleQuote(0.01);
        //	    Handle<Quote> mh = new Handle<Quote>(me);
        //
        //	    RelinkableHandle<YieldTermStructure> h = new RelinkableHandle<YieldTermStructure>(dummyTermStructure);
        //	    YieldTermStructure spreaded = new ZeroSpreadedTermStructure(h, mh);
        //
        //	    Flag flag = new Flag();
        //	    spreaded.addObserver(flag);
        //	    h.setLink(termStructure);
        //
        //        if (!flag.isUp()) {
        //            fail("Observer was not notified of term structure change");
        //	    }
        //
        //	    flag.lower();
        //	    me.setValue(0.005);
        //        if (!flag.isUp()) {
        //            fail("Observer was not notified of spread change");
        //	    }
    }



    //
    // private inner classes
    //

    private static class Datum {
        public int n;
        public TimeUnit units;
        public double rate;

        public Datum(final int n, final TimeUnit units, final double rate) {
            this.n = n;
            this.units = units;
            this.rate = rate;
        }
    }

}
