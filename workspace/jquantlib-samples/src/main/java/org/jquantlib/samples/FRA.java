/*
 Copyright (C) 2009 Ueli Hofstetter

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


/*!
 Copyright (C) 2006 Allen Kuo

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

/*  This example shows how to set up a term structure and price a simple
    forward-rate agreement.
 */


package org.jquantlib.samples;


import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.ActualActual;
import org.jquantlib.daycounters.ActualActual.Convention;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.Euribor365_3M;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.RateHelper;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.yieldcurves.FraRateHelper;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.TimeUnit;

public class FRA implements Runnable {

    public static void main(final String[] args) {
        new FRA().run();
    }

    @Override
    public void run() {

        QL.validateExperimentalMode();

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        /*********************
         ***  MARKET DATA  ***
         *********************/

        final RelinkableHandle<YieldTermStructure> euriborTermStructure = new RelinkableHandle<YieldTermStructure>();
        final Handle<IborIndex> euribor3m = new Handle<IborIndex>(new Euribor365_3M(euriborTermStructure));

        final Date todaysDate = new Date(23, Month.May, 2006);
        new Settings().setEvaluationDate(todaysDate);

        final Calendar calendar = euribor3m.currentLink().fixingCalendar();
        final int fixingDays = euribor3m.currentLink().fixingDays();
        final Date settlementDate = calendar.advance(todaysDate, fixingDays, TimeUnit.Months );

        System.out.println("Today: "+ todaysDate.weekday() + "," + todaysDate);

        System.out.println("Settlement date: " + settlementDate.weekday() + "," + settlementDate);

        // 3 month term FRA quotes (index refers to monthsToStart)
        final double threeMonthFraQuote [] = new double[10];
        threeMonthFraQuote[1]=0.030;
        threeMonthFraQuote[2]=0.031;
        threeMonthFraQuote[3]=0.032;
        threeMonthFraQuote[6]=0.033;
        threeMonthFraQuote[9]=0.034;

        /********************
         ***    QUOTES    ***
         ********************/

        // SimpleQuote stores a value which can be manually changed;
        // other Quote subclasses could read the value from a database
        // or some kind of data feed.

        final Handle<SimpleQuote> fra1x4Rate = new Handle<SimpleQuote>(new SimpleQuote(threeMonthFraQuote[1]));
        final Handle<SimpleQuote> fra2x5Rate = new Handle<SimpleQuote>(new SimpleQuote(threeMonthFraQuote[2]));
        final Handle<SimpleQuote> fra3x6Rate = new Handle<SimpleQuote>(new SimpleQuote(threeMonthFraQuote[3]));
        final Handle<SimpleQuote> fra6x9Rate = new Handle<SimpleQuote>(new SimpleQuote(threeMonthFraQuote[6]));
        final Handle<SimpleQuote> fra9x12Rate = new Handle<SimpleQuote>(new SimpleQuote(threeMonthFraQuote[9]));

        final RelinkableHandle<Quote> h1x4 = null ;       h1x4.linkTo(fra1x4Rate.currentLink());
        final RelinkableHandle<Quote> h2x5 = null;        h2x5.linkTo(fra2x5Rate.currentLink());
        final RelinkableHandle<Quote> h3x6 = null;        h3x6.linkTo(fra3x6Rate.currentLink());
        final RelinkableHandle<Quote> h6x9 = null;        h6x9.linkTo(fra6x9Rate.currentLink());
        final RelinkableHandle<Quote> h9x12 = null;       h9x12.linkTo(fra9x12Rate.currentLink());

        /*********************
         ***  RATE HELPERS ***
         *********************/

        // RateHelpers are built from the above quotes together with
        // other instrument dependant infos.  Quotes are passed in
        // relinkable handles which could be relinked to some other
        // data source later.

        final DayCounter fraDayCounter = euribor3m.currentLink().dayCounter();
        final BusinessDayConvention convention = euribor3m.currentLink().businessDayConvention();
        final boolean endOfMonth = euribor3m.currentLink().endOfMonth();

        final RateHelper fra1x4  = new FraRateHelper(h1x4,  1,  4, fixingDays, calendar, convention, endOfMonth, fraDayCounter);
        final RateHelper fra2x5  = new FraRateHelper(h2x5,  2,  5, fixingDays, calendar, convention, endOfMonth, fraDayCounter);
        final RateHelper fra3x6  = new FraRateHelper(h3x6,  3,  6, fixingDays, calendar, convention, endOfMonth, fraDayCounter);
        final RateHelper fra6x9  = new FraRateHelper(h6x9,  6,  9, fixingDays, calendar, convention, endOfMonth, fraDayCounter);
        final RateHelper fra9x12 = new FraRateHelper(h9x12, 9, 12, fixingDays, calendar, convention, endOfMonth, fraDayCounter);

        /*********************
         **  CURVE BUILDING **
         *********************/

        // Any DayCounter would be fine.
        // ActualActual::ISDA ensures that 30 years is 30.0
        final DayCounter termStructureDayCounter = new ActualActual(Convention.ISDA);

        final double tolerance = 1.0e-15;

        //A FRA curve


    }

}
