/*
 Copyright (C) 2008

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

package org.jquantlib.testsuite.calendars;

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Japan;
import org.junit.Test;

public class JapanCalendarTest {

    private final Calendar settlement;

    public JapanCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	    this.settlement = new Japan();
	}

    // 2012 -- simply taken from rules
    // Comparing with http://www.timeanddate.com/calendar/index.html?year=2012&country=26
    //
	//     1 Jan	New Year's Day
	//     2 Jan	'New Year's Day' observed
	//     9 Jan	Coming of Age Day
	//    11 Feb	National Foundation Day
	//    20 Mar	Spring Equinox
	//    29 Apr	Shōwa Day
	//
	//    30 Apr	'Shōwa Day' observed
	//     3 May	Constitution Memorial Day
	//     4 May	Greenery Day
	//     5 May	Children's Day
	//    16 Jul	Sea Day
	//    17 Sep	Respect for the Aged Day
	//
	//    22 Sep	Autumn Equinox
	//     8 Oct	Sports Day
	//     3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	//    23 Dec	Emperor's Birthday
	//    24 Dec	'Emperor's Birthday' observed

    @Test public void testJapanYear2012() {
        final int year = 2012;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		// Sun: expectedHol.add(new Date( 1,JANUARY,year));   // New Year's Day
		expectedHol.add(new Date( 2,January,year));   // Bank Holiday
		expectedHol.add(new Date( 3,January,year));   // Bank Holiday
    	expectedHol.add(new Date( 9,January,year));   // Coming of Age Day
		// Sat: expectedHol.add(new Date(11,FEBRUARY,year));  // National Foundation Day
		expectedHol.add(new Date(20,March,year));     // Spring Equinox observed
		// Sun: expectedHol.add(new Date(29,APRIL,year));     // Showa Day
		expectedHol.add(new Date(30,April,year));     // Showa Day observed
		expectedHol.add(new Date( 3,May,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Greenery Day
        // Sat: expectedHol.add(new Date( 5,MAY,year));       // Children's Day
		expectedHol.add(new Date(16,July,year));      // Sea Day
        expectedHol.add(new Date(17,September,year)); // Respect for the Aged Day
    	// Sat: expectedHol.add(new Date(22,SEPTEMBER,year)); // Autumn Equinox
        expectedHol.add(new Date( 8,October,year));   // Sports Day
        // Sat: expectedHol.add(new Date( 3,NOVEMBER,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        // Sun: expectedHol.add(new Date(23,DECEMBER,year));  // Emperor's Birthday
        expectedHol.add(new Date(24,December,year));  // Emperor's Birthday observed
		expectedHol.add(new Date(31,December,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }

    // 2011 -- simply taken from rules
    // Comparing with http://www.timeanddate.com/calendar/index.html?year=2011&country=26
    //
	//     1 Jan	New Year's Day
	//     2 Jan	Bank Holiday
	//     3 Jan	Bank Holiday
	//    10 Jan	Coming of Age Day
	//    11 Feb	National Foundation Day
	//    21 Mar	Spring Equinox
	//    29 Apr	Shōwa Day
	//
	//     3 May	Constitution Memorial Day
	//     4 May	Greenery Day
	//     5 May	Children's Day
	//    18 Jul	Sea Day
	//    19 Sep	Respect for the Aged Day
	//
	//    23 Sep	Autumn Equinox
	//    10 Oct	Sports Day
	//     3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	//    23 Dec	Emperor's Birthday
	//    31 Dec	Bank Holiday

    @Test public void testJapanYear2011() {
        final int year = 2011;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		// Sat: expectedHol.add(new Date( 1,JANUARY,year));   // New Year's Day
		// Sun: expectedHol.add(new Date( 2,JANUARY,year));   // Bank Holiday
		expectedHol.add(new Date( 3,January,year));   // Bank Holiday
    	expectedHol.add(new Date(10,January,year));   // Coming of Age Day
		expectedHol.add(new Date(11,February,year));  // National Foundation Day
		expectedHol.add(new Date(21,March,year));     // Spring Equinox observed
		expectedHol.add(new Date(29,April,year));     // Showa Day
		expectedHol.add(new Date( 3,May,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Greenery Day
        expectedHol.add(new Date( 5,May,year));       // Children's Day
		expectedHol.add(new Date(18,July,year));      // Sea Day
        expectedHol.add(new Date(19,September,year)); // Respect for the Aged Day
    	expectedHol.add(new Date(23,September,year)); // Autumn Equinox
        expectedHol.add(new Date(10,October,year));   // Sports Day
        expectedHol.add(new Date( 3,November,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        expectedHol.add(new Date(23,December,year));  // Emperor's Birthday
		// Sat: expectedHol.add(new Date(31,DECEMBER,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }

    // 2010 -- simply taken from rules
    // Comparing with http://www.timeanddate.com/calendar/index.html?year=2010&country=26
    //
	//     1 Jan	New Year's Day
	//     2 Jan	Bank Holiday
	//     3 Jan	Bank Holiday
	//    11 Jan	Coming of Age Day
	//    11 Feb	National Foundation Day
	//    21 Mar	Spring Equinox
	//    22 Mar	'Spring Equinox' observed
	//    29 Apr	Shōwa Day
	//     3 May	Constitution Memorial Day
	//     4 May	Greenery Day
	//     5 May	Children's Day
	//    19 Jul	Sea Day
	//    20 Sep	Respect for the Aged Day
	//    23 Sep	Autumn Equinox
	//    11 Oct	Sports Day
	//     3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	//    23 Dec	Emperor's Birthday
	//    31 Dec	Bank Holiday


    @Test public void testJapanYear2010() {
        final int year = 2010;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date( 1,January,year));   // New Year's Day
		// Sat: expectedHol.add(new Date( 2,JANUARY,year));   // Bank Holiday
		// Sun: expectedHol.add(new Date( 3,JANUARY,year));   // Bank Holiday
    	expectedHol.add(new Date(11,January,year));   // Coming of Age Day
		expectedHol.add(new Date(11,February,year));  // National Foundation Day
		expectedHol.add(new Date(22,March,year));     // Spring Equinox observed
		expectedHol.add(new Date(29,April,year));     // Showa Day
		expectedHol.add(new Date( 3,May,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Greenery Day
        expectedHol.add(new Date( 5,May,year));       // Children's Day
		expectedHol.add(new Date(19,July,year));      // Sea Day
        expectedHol.add(new Date(20,September,year)); // Respect for the Aged Day
    	expectedHol.add(new Date(23,September,year)); // Autumn Equinox
        expectedHol.add(new Date(11,October,year));   // Sports Day
        expectedHol.add(new Date( 3,November,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        expectedHol.add(new Date(23,December,year));  // Emperor's Birthday
		expectedHol.add(new Date(31,December,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }

	// 2009 -- simply taken from rules
	// Comparing with http://www.mizuhocbk.co.jp/english/service/custody/holiday2009.html
    //
	//     1 Jan	New Year's Day
	//     2 Jan	Bank Holiday
	////   3 Jan	Bank Holiday
	//    12 Jan	Coming of Age Day
	//    11 Feb	National Foundation Day
	//    20 Mar	Spring Equinox
	//    29 Apr	Showa Day
	////   3 May	Constitution Memorial Day
	//     4 May	Greenery Day
	//     5 May	Children's Day
	//     6 May	Alternative Constitution Memorial Day
	//    20 Jul	Sea Day1 Jan	New Year's Day
	//    21 Sep	Respect for the Aged Day
    //	  22 Sep    Bank Holiday
    //    23 Sep	Autumn Equinox
	//    12 Oct	Sports Day
	//     3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	//    23 Dec	Emperor's Birthday
	//    31 Dec	Bank Holiday

	@Test public void testJapanYear2009() {
        final int year = 2009;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date( 1,January,year));   // New Year's Day
		expectedHol.add(new Date( 2,January,year));   // Bank Holiday
		//. Sat: expectedHol.add(new Date( 3,JANUARY,year));   // Bank Holiday
    	expectedHol.add(new Date(12,January,year));   // Coming of Age Day
		expectedHol.add(new Date(11,February,year));  // National Foundation Day
		expectedHol.add(new Date(20,March,year));     // Spring Equinox
		expectedHol.add(new Date(29,April,year));     // Showa Day
		// expectedHol.add(new Date( 3,MAY,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Greenery Day
        expectedHol.add(new Date( 5,May,year));       // Children's Day
//        expectedHol.add(new Date( 6,MAY,year));       // alternative Constitution Memorial Day
		expectedHol.add(new Date(20,July,year));      // Sea Day
        expectedHol.add(new Date(21,September,year)); // Respect for the Aged Day
        expectedHol.add(new Date(22,September,year)); // Bank Holiday
    	expectedHol.add(new Date(23,September,year)); // Autumn Equinox
        expectedHol.add(new Date(12,October,year));   // Sports Day
        expectedHol.add(new Date( 3,November,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        expectedHol.add(new Date(23,December,year));  // Emperor's Birthday
		expectedHol.add(new Date(31,December,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }


	// 2008 -- simply taken from rules
	// Comparing with http://www.mizuhocbk.co.jp/english/service/custody/holiday2008.html
    //
	//     1 Jan	New Year's Day
	//     2 Jan	Bank Holiday
	//     3 Jan	Bank Holiday
	//    14 Jan	Coming of Age Day
	//    11 Feb	National Foundation Day
	//    20 Mar	Spring Equinox
	//    29 Apr	Showa Day
	////   3 May	Constitution Memorial Day
	////   4 May	Greenery Day
	//     5 May	Children's Day
	//     6 May	Alternative Greenery Day
	//    21 Jul	Sea Day
	//    15 Sep	Respect for the Aged Day4
	//    23 Sep	Autumn Equinox
	//    13 Oct	Sports Day
	//     3 Nov	Culture Day
	//    24 Nov	Labor Thanksgiving Day
	//    23 Dec	Emperor's Birthday
	//    31 Dec	Bank Holiday

	@Test public void testJapanYear2008() {
        final int year = 2008;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date( 1,January,year));   // New Year's Day
		expectedHol.add(new Date( 2,January,year));   // Bank Holiday
		expectedHol.add(new Date( 3,January,year));   // Bank Holiday
    	expectedHol.add(new Date(14,January,year));   // Coming of Age Day
		expectedHol.add(new Date(11,February,year));  // National Foundation Day
		expectedHol.add(new Date(20,March,year));     // Spring Equinox
		expectedHol.add(new Date(29,April,year));     // Showa Day
		// expectedHol.add(new Date( 3,MAY,year));       // Constitution Memorial Day
		// expectedHol.add(new Date( 4,MAY,year));       // Between Day
        expectedHol.add(new Date( 5,May,year));       // Children's Day
//        expectedHol.add(new Date( 6,MAY,year));       // alternative Greenery Day
		expectedHol.add(new Date(21,July,year));      // Sea Day
        expectedHol.add(new Date(15,September,year)); // Respect for the Aged Day
    	expectedHol.add(new Date(23,September,year)); // Autumn Equinox
        expectedHol.add(new Date(13,October,year));   // Sports Day
        expectedHol.add(new Date( 3,November,year));  // Culture Day
        expectedHol.add(new Date(24,November,year));  // Labor Thanksgiving Day
        expectedHol.add(new Date(23,December,year));  // Emperor's Birthday
		expectedHol.add(new Date(31,December,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }


	// 2007 -- simply taken from rules
	// Comparing with http://www.mizuhocbk.co.jp/english/service/custody/holiday2007.html
    //
	//     1 Jan	New Year's Day
	//     2 Jan	Bank Holiday
	//     3 Jan	Bank Holiday
	//     8 Jan	Coming of Age Day
	//    12 Feb	National Foundation Day
	//    21 Mar	Spring Equinox
	//    30 Apr	Greenery Day
	//     3 May	Constitution Memorial Day
	//     4 May	Greenery Day
	////   5 May	Children's Day
	//    16 Jul	Sea Day
	//    17 Sep	Respect for the Aged Day
	//    24 Sep	Autumn Equinox
	//     8 Oct	Sports Day
	////   3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	//    24 Dec	Emperor's Birthday
	//    31 Dec	Bank Holiday

	@Test public void testJapanYear2007() {
        final int year = 2007;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date( 1,January,year));   // New Year's Day
		expectedHol.add(new Date( 2,January,year));   // Bank Holiday
		expectedHol.add(new Date( 3,January,year));   // Bank Holiday
    	expectedHol.add(new Date( 8,January,year));   // Coming of Age Day
		expectedHol.add(new Date(12,February,year));  // National Foundation Day
		expectedHol.add(new Date(21,March,year));     // Spring Equinox
		expectedHol.add(new Date(30,April,year));     // Greenery Day
		expectedHol.add(new Date( 3,May,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Between Day
        // Sat: expectedHol.add(new Date( 5,MAY,year));       // Children's Day
		expectedHol.add(new Date(16,July,year));      // Sea Day
        expectedHol.add(new Date(17,September,year)); // Respect for the Aged Day
    	expectedHol.add(new Date(24,September,year)); // Autumn Equinox
        expectedHol.add(new Date( 8,October,year));   // Sports Day
        // Sat: expectedHol.add(new Date( 3,NOVEMBER,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        expectedHol.add(new Date(24,December,year));  // Emperor's Birthday
		expectedHol.add(new Date(31,December,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }


    // 2006 -- simply taken from rules
	// Comparing with http://www.mizuhocbk.co.jp/english/service/custody/holiday2006.html
    //
	////   1 Jan	New Year's Day
	//     2 Jan	Bank Holiday
	//     3 Jan	Bank Holiday
	//     9 Jan	Coming of Age Day
	////  11 Feb	National Foundation Day
	//    21 Mar	Spring Equinox
	////  29 Apr	Greenery Day
	//     3 May	Constitution Memorial Day
	//     4 May	Between Day
	//     5 May	Children's Day
	//    17 Jul	Sea Day
	//    18 Sep	Respect for the Aged Day
	////  23 Sep	Autumn Equinox
	//     9 Oct	Sports Day
	//     3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	////  23 Dec	Emperor's Birthday
	////  31 Dec	Bank Holiday

	@Test public void testJapanYear2006() {
        final int year = 2006;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		// Sun: expectedHol.add(new Date( 1,JANUARY,year));   // New Year's Day
		expectedHol.add(new Date( 2,January,year));   // Bank Holiday
		expectedHol.add(new Date( 3,January,year));   // Bank Holiday
    	expectedHol.add(new Date( 9,January,year));   // Coming of Age Day
		// Sat: expectedHol.add(new Date(11,FEBRUARY,year));  // National Foundation Day
		expectedHol.add(new Date(21,March,year));     // Spring Equinox
		// Sat: expectedHol.add(new Date(29,APRIL,year));     // Greenery Day
		expectedHol.add(new Date( 3,May,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Between Day
        expectedHol.add(new Date( 5,May,year));       // Children's Day
		expectedHol.add(new Date(17,July,year));      // Sea Day
        expectedHol.add(new Date(18,September,year)); // Respect for the Aged Day
    	// Sat: expectedHol.add(new Date(23,SEPTEMBER,year)); // Autumn Equinox
        expectedHol.add(new Date( 9,October,year));   // Sports Day
        expectedHol.add(new Date( 3,November,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        // Sat: expectedHol.add(new Date(23,DECEMBER,year));  // Emperor's Birthday
		// Sun: expectedHol.add(new Date(31,DECEMBER,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }


    // 2005 -- simply taken from rules
	// Comparing with http://www.mizuhocbk.co.jp/english/service/custody/holiday2005.html
    //
	////   1 Jan	New Year's Day
	////   2 Jan	Bank Holiday
	//     3 Jan	Bank Holiday
	//    10 Jan	Coming of Age Day
	//    11 Feb	National Foundation Day
	//    21 Mar	Spring Equinox
	//    29 Apr	Greenery Day
	//     3 May	Constitution Memorial Day
	//     4 May	Between Day
	//     5 May	Children's Day
	//    18 Jul	Sea Day
	//    19 Sep	Respect for the Aged Day
	//    23 Sep	Autumn Equinox
	//    10 Oct	Sports Day
	//     3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	//    23 Dec	Emperor's Birthday
	////  31 Dec	Bank Holiday

    @Test public void testJapanYear2005() {
        final int year = 2005;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		// Sat: expectedHol.add(new Date( 1,JANUARY,year));   // New Year's Day
		// Sun: expectedHol.add(new Date( 2,JANUARY,year));   // Bank Holiday
		expectedHol.add(new Date( 3,January,year));   // Bank Holiday
    	expectedHol.add(new Date(10,January,year));   // Coming of Age Day
		expectedHol.add(new Date(11,February,year));  // National Foundation Day
		expectedHol.add(new Date(21,March,year));     // Spring Equinox
		expectedHol.add(new Date(29,April,year));     // Greenery Day
		expectedHol.add(new Date( 3,May,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Between Day
        expectedHol.add(new Date( 5,May,year));       // Children's Day
		expectedHol.add(new Date(18,July,year));      // Sea Day
        expectedHol.add(new Date(19,September,year)); // Respect for the Aged Day
    	expectedHol.add(new Date(23,September,year)); // Autumn Equinox
        expectedHol.add(new Date(10,October,year));   // Sports Day
        expectedHol.add(new Date( 3,November,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        expectedHol.add(new Date(23,December,year));  // Emperor's Birthday
		// Sat: expectedHol.add(new Date(31,DECEMBER,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }


    // 2004 -- simply taken from rules
    // Comparing with http://www.timeanddate.com/calendar/index.html?year=2004&country=26
    //
	//     1 Jan	New Year's Day
	//     2 Jan	Bank Holiday
	////   3 Jan	Bank Holiday
	//    12 Jan	Coming of Age Day
	//    11 Feb	National Foundation Day
	////  20 Mar	Spring Equinox
	//    29 Apr	Greenery Day
	//     3 May	Constitution Memorial Day
	//     4 May	Between Day
	//     5 May	Children's Day
	//    19 Jul	Sea Day
	//    20 Sep	Respect for the Aged Day
	//    23 Sep	Autumn Equinox
	//    11 Oct	Sports Day
	//     3 Nov	Culture Day
	//    23 Nov	Labor Thanksgiving Day
	//    23 Dec	Emperor's Birthday
	//    31 Dec	Bank Holiday

    @Test public void testJapanYear2004() {
        final int year = 2004;
        QL.info("Testing Japan's holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date( 1,January,year));   // New Year's Day
		expectedHol.add(new Date( 2,January,year));   // Bank Holiday
		// Sat: expectedHol.add(new Date( 3,JANUARY,year));   // Bank Holiday
    	expectedHol.add(new Date(12,January,year));   // Coming of Age Day
		expectedHol.add(new Date(11,February,year));  // National Foundation Day
		// Sat: expectedHol.add(new Date(20,MARCH,year));     // Spring Equinox
		expectedHol.add(new Date(29,April,year));     // Greenery Day
		expectedHol.add(new Date( 3,May,year));       // Constitution Memorial Day
		expectedHol.add(new Date( 4,May,year));       // Between Day
        expectedHol.add(new Date( 5,May,year));       // Children's Day
		expectedHol.add(new Date(19,July,year));      // Sea Day
        expectedHol.add(new Date(20,September,year)); // Respect for the Aged Day
    	expectedHol.add(new Date(23,September,year)); // Autumn Equinox
        expectedHol.add(new Date(11,October,year));   // Sports Day
        expectedHol.add(new Date( 3,November,year));  // Culture Day
        expectedHol.add(new Date(23,November,year));  // Labor Thanksgiving Day
        expectedHol.add(new Date(23,December,year));  // Emperor's Birthday
		expectedHol.add(new Date(31,December,year));  // Bank Holiday

        new CalendarUtil().checkHolidayList(expectedHol, settlement, year);
    }

}
