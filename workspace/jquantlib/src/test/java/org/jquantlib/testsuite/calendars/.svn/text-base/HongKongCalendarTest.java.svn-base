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
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.HongKong;
import org.junit.Test;

/**
 * @author Richard Gomes
 */
public class HongKongCalendarTest {

    public HongKongCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

    // 2009 -- taken from Exchange website http://www.hkex.com.hk

    //	 1-Jan-09   Thursday    The first day of JANUARY
    //	26-Jan-09   Monday      Lunar New Year's Day
    //	27-Jan-09   Tuesday     The second day of Lunar New Year
    //	28-Jan-09   Wednesday   The third day of Lunar New Year
    //	10-Apr-09   Friday      Good Friday
    //	13-Apr-09   Monday      Easter Monday
    //	 1-May-09   Friday      Labour Day
    //	28-May-09   Thursday    Tuen Ng Festival
    //	 1-Jul-09   Wednesday   Hong Kong Special Administrative Region Establishment Day
    //	 1-Oct-09   Thursday    National Day
    //	26-Oct-09   Monday      Chung Yeung Festival
    //	25-Dec-09   Friday      Christmas Day

	@Test public void testHongKongYear2009() {
        final int year = 2009;
        QL.info("Testing Hong Kong's holiday list for the year " + year + "...");

        final Calendar c = new HongKong(HongKong.Market.HKEx);
        
        final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));    // The first day of JANUARY
//        expectedHol.add(new Date(26,JANUARY,year));   // Lunar New Year's Day
//        expectedHol.add(new Date(27,JANUARY,year));   // The second day of Lunar New Year
//        expectedHol.add(new Date(28,JANUARY,year));   // The third day of Lunar New Year
        expectedHol.add(new Date(10,April,year));     // Good Friday
        expectedHol.add(new Date(13,April,year));     // Easter Monday
        expectedHol.add(new Date(1,May,year));        // Labour Day
//        expectedHol.add(new Date(28,MAY,year));       // Tuen Ng Festival
        expectedHol.add(new Date(1,July,year));       // Hong Kong Special Administrative Region Establishment Day
        expectedHol.add(new Date(1,October,year));    // National Day
//        expectedHol.add(new Date(26,OCTOBER,year));   // Chung Yeung festival
        expectedHol.add(new Date(25,December,year));  // Christmas Day

        expectedHol.add(new Date(28,December,year));  

        new CalendarUtil().checkHolidayList(expectedHol, c, year);
    }


    // 2008 -- taken from Exchange website http://www.hkex.com.hk

    //     1-Jan-08   Tuesday     The first day of JANUARY
    //     7-Feb-08   Thursday    Lunar New Year's Day
    //     8-Feb-08   Friday      The second day of Lunar New Year
    //    21-Mar-08   Friday      Good Friday
    //    24-Mar-08   Monday      Easter Monday
    //     4-Apr-08   Friday      Ching Ming Festival
    //     1-May-08   Thursday    Labour Day
    //    12-May-08   Monday      The Buddha's Birthday
    //     9-Jun-08   Monday      The day following Tuen Ng Festival
    //     1-Jul-08   Tuesday     Hong Kong Special Administrative Region Establishment Day
    //    15-Sep-08   Monday      The day following Chinese Mid-Autumn Festival
    //     1-Oct-08   Wednesday   National Day
    //     7-Oct-08   Tuesday     Chung Yeung Festival
    //    25-Dec-08   Thursday    Christmas Day
    //    26-Dec-08   Friday      The first weekday after Christmas Day

	@Test public void testHongKongYear2008() {
      	final int year = 2008;
        QL.info("Testing Hong Kong's holiday list for the year " + year + "...");

        final Calendar c = new HongKong(HongKong.Market.HKEx);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1,January,year));    // The first day of JANUARY
    	expectedHol.add(new Date(7,February,year));   // Lunar New Year's Day
		expectedHol.add(new Date(8,February,year));   // The second day of Lunar New Year
		expectedHol.add(new Date(21,March,year));     // Good Friday
		expectedHol.add(new Date(24,March,year));     // Easter Monday
		expectedHol.add(new Date(4,April,year));      // Ching Ming Festival
		expectedHol.add(new Date(1,May,year));        // Labour Day
        expectedHol.add(new Date(12,May,year));       // The Buddha's Birthday
		expectedHol.add(new Date(9,June,year));       // The day following Tuen Ng Festival
        expectedHol.add(new Date(1,July,year));       // Hong Kong Special Administrative Region Establishment Day
    	expectedHol.add(new Date(15,September,year)); // The day following Chinese Mid-Autumn Festival
        expectedHol.add(new Date(1,October,year));    // National Day
        expectedHol.add(new Date(7,October,year));    // Chung Yeung festival
        expectedHol.add(new Date(25,December,year));  // Christmas Day
        expectedHol.add(new Date(26,December,year));  // The first weekday after Christmas Day

        new CalendarUtil().checkHolidayList(expectedHol, c, year);
    }


	// 2007 -- http://www.hkfastfacts.com/Hong-Kong-Festivals-2007.html

	//     1-Jan-07         The first day of JANUARY
    //    17-Feb-07         Lunar New Year's Day
    //    18-Feb-07         The second day of Lunar New Year
    //     5-Apr-07         Ching Ming Festival
    //     6-Apr-07         Good Friday
    //     9-Apr-07         Easter Monday
    //     1-May-07         Labour Day
    //    24-May-07         The Buddha's Birthday
    //    19-Jun-07         The day following Tuen Ng Festival
    //     2-Jul-07         Hong Kong Special Administrative Region Establishment Day
    //    26-Sep-07         The day following Chinese Mid-Autumn Festival
    //     1-Oct-07         National Day
    //    19-Oct-07         Chung Yeung Festival
    //    25-Dec-07         Christmas Day
    //    26-Dec-07         The first weekday after Christmas Day

	@Test public void testHongKongYear2007() {
        final int year = 2007;
        QL.info("Testing Hong Kong's holiday list for the year " + year + "...");

        final Calendar c = new HongKong(HongKong.Market.HKEx);
        
        final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));    // The first day of JANUARY
        expectedHol.add(new Date(1,May,year));        // Labour Day
        expectedHol.add(new Date(1,October,year));    // National Day
        expectedHol.add(new Date(25,December,year));  // Christmas Day
        expectedHol.add(new Date(26,December,year));  // The first weekday after Christmas Day

    	expectedHol.add(new Date(19,February,year));   // 2 Lunar New Year's Day
		expectedHol.add(new Date(20,February,year));   // The 3 day of Lunar New Year
		expectedHol.add(new Date(5,April,year));      // Ching Ming Festival
		expectedHol.add(new Date(6,April,year));     // Good Friday
		expectedHol.add(new Date(9,April,year));     // Easter Monday
        expectedHol.add(new Date(24,May,year));       // The Buddha's Birthday
		expectedHol.add(new Date(19,June,year));       // The day following Tuen Ng Festival
        expectedHol.add(new Date(2,July,year));       // day after Hong Kong Special Administrative Region Establishment Day
    	expectedHol.add(new Date(26,September,year)); // The day following Chinese Mid-Autumn Festival
        expectedHol.add(new Date(19,October,year));    // Chung Yeung festival

        new CalendarUtil().checkHolidayList(expectedHol, c, year);

    }


	// 2006 -- http://www.hkfastfacts.com/Hong-Kong-Festivals-2006.html

	//     2-Jan-06         The 2 day of JANUARY
    //    30-Jan-06         The 2 day Lunar New Year's Day
    //    31-Jan-06         The 3 day of Lunar New Year
    //     5-Apr-06         Ching Ming Festival
    //    14-Apr-06         Good Friday
    //    17-Apr-06         Easter Monday
    //     1-May-06         Labour Day
    //     5-May-06         The Buddha's Birthday
    //    31-May-06         The Tuen Ng Festival
    //     1-Jul-06   Saturday      Hong Kong Special Administrative Region Establishment Day
    //     2-Oct-06         The day after National Day
    //     7-Oct-06         The day following Chinese Mid-Autumn Festival
    //    30-Oct-06         Chung Yeung Festival
    //    25-Dec-06         Christmas Day
    //    26-Dec-06         The first weekday after Christmas Day

	@Test public void testHongKongYear2006() {
        final int year = 2006;
        QL.info("Testing Hong Kong's holiday list for the year " + year + "...");

        final Calendar c = new HongKong(HongKong.Market.HKEx);
        
        final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));    // The first day of JANUARY
        expectedHol.add(new Date(1,May,year));        // Labour Day
        expectedHol.add(new Date(1,October,year));    // National Day
        expectedHol.add(new Date(25,December,year));  // Christmas Day
        expectedHol.add(new Date(26,December,year));  // The first weekday after Christmas Day

        expectedHol.remove(new Date(1,January,year));
        expectedHol.remove(new Date(1,October,year));

    	expectedHol.add(new Date(2,January,year));   // 2 of the New Year's Day
    	expectedHol.add(new Date(30,January,year));   // 2 Lunar New Year's Day
		expectedHol.add(new Date(31,January,year));   // The 3 day of Lunar New Year
		expectedHol.add(new Date(5,April,year));      // Ching Ming Festival
		expectedHol.add(new Date(14,April,year));     // Good Friday
		expectedHol.add(new Date(17,April,year));     // Easter Monday
        expectedHol.add(new Date(5,May,year));       // The Buddha's Birthday
		expectedHol.add(new Date(31,May,year));       // The Tuen Ng Festival
        expectedHol.add(new Date(3,July,year));  //Monday     // day after Hong Kong Special Administrative Region Establishment Day
        expectedHol.add(new Date(2,October,year));    // National Day
    	//expectedHol.add(new Date(7,OCTOBER,year)); saturday // The day following Chinese Mid-Autumn Festival
        expectedHol.add(new Date(30,October,year));    // Chung Yeung festival

        new CalendarUtil().checkHolidayList(expectedHol, c, year);
    }


	// 2005 -- http://www.hkfastfacts.com/Hong-Kong-Festivals-2005.html

	//     3-Jan-05         Monday The 3 day of JANUARY
    //     9-Feb-05         Lunar New Year's Day
    //    10-Feb-05         The 2 day Lunar New Year's Day
    //    11-Feb-05         The 3 day of Lunar New Year
    //    25-Mar-05         Good Friday
    //    28-Mar-05         Easter Monday
    //     5-Apr-05         Ching Ming Festival
    //     1-May-05         Sunday Labour Day
    //    15-May-05         Sunday The Buddha's Birthday
    //    11-Jun-05         Saturday The Tuen Ng Festival
    //     1-Jul-05         Hong Kong Special Administrative Region Establishment Day
    //    19-Sep-05         The Chinese Mid-Autumn Festival
    //     1-Oct-05         Saturday The National Day
    //    11-Oct-05         Chung Yeung Festival
    //    25-Dec-05         Sunday Christmas Day
    //    26-Dec-05         The first weekday after Christmas Day

    @Test public void testHongKongYear2005() {
        final int year = 2005;
        QL.info("Testing Hong Kong's holiday list for the year " + year + "...");

        final Calendar c = new HongKong(HongKong.Market.HKEx);
        
        final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));    // The first day of JANUARY
        expectedHol.add(new Date(1,May,year));        // Labour Day
        expectedHol.add(new Date(1,October,year));    // National Day
        expectedHol.add(new Date(25,December,year));  // Christmas Day
        expectedHol.add(new Date(26,December,year));  // The first weekday after Christmas Day

        expectedHol.remove(new Date(1,January,year));
        expectedHol.remove(new Date(1,May,year));
        expectedHol.remove(new Date(1,October,year));
        expectedHol.remove(new Date(25,December,year));

    	expectedHol.add(new Date(3,January,year));   // 3 of the New Year's Day
    	expectedHol.add(new Date(9,February,year));   // Lunar New Year's Day
    	expectedHol.add(new Date(10,February,year));   // 2 Lunar New Year's Day
		expectedHol.add(new Date(11,February,year));   // The 3 day of Lunar New Year
		expectedHol.add(new Date(25,March,year));     // Good Friday
		expectedHol.add(new Date(28,March,year));     // Easter Monday
		expectedHol.add(new Date(5,April,year));      // Ching Ming Festival
        expectedHol.remove(new Date(2,May,year));     // Day after labor day
        expectedHol.add(new Date(16,May,year));       // The day after  Buddha's Birthday
		//expectedHol.add(new Date(11,JUNE,year));       // The Tuen Ng Festival
        expectedHol.add(new Date(1,July,year));       // Hong Kong Special Administrative Region Establishment Day
    	expectedHol.add(new Date(19,September,year));   // The day following Chinese Mid-Autumn Festival
        expectedHol.add(new Date(3,October,year));
        expectedHol.add(new Date(11,October,year));    // Chung Yeung festival
        expectedHol.remove(new Date(27,December,year));

        new CalendarUtil().checkHolidayList(expectedHol, c, year);
    }


	// 2005 -- http://www.hkfastfacts.com/Hong-Kong-Festivals-2004.html

	//     1-Jan-04         1 day of JANUARY
    //    22-Jan-04         Lunar New Year's Day
    //    23-Jan-04         The 2 day Lunar New Year's Day
    //    24-Jan-04         The 3 day of Lunar New Year
    //     5-Apr-04         Ching Ming Festival
    //     9-Apr-04         Good Friday
    //    12-Apr-04         Easter Monday
    //     1-May-04         Saturday Labour Day
    //    26-May-04         The Buddha's Birthday
    //    22-Jun-04         The Tuen Ng Festival
    //     1-Jul-04         Hong Kong Special Administrative Region Establishment Day
    //    28-Sep-04         The Chinese Mid-Autumn Festival
    //     1-Oct-04         The National Day
    //    22-Oct-04         Chung Yeung Festival
    //    25-Dec-04         Sunday Christmas Day
    //    27-Dec-04         The second weekday after Christmas Day

    @Test public void testHongKongYear2004() {
        final int year = 2004;
        QL.info("Testing Hong Kong's holiday list for the year " + year + "...");

        final Calendar c = new HongKong(HongKong.Market.HKEx);
        
        final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));    // The first day of JANUARY
        expectedHol.add(new Date(1,May,year));        // Labour Day
        expectedHol.add(new Date(1,October,year));    // National Day
        expectedHol.add(new Date(25,December,year));  // Christmas Day
        expectedHol.add(new Date(26,December,year));  // The first weekday after Christmas Day

        expectedHol.remove(new Date(1,May,year));        // Labour Day

    	expectedHol.add(new Date(22,January,year));   // Lunar New Year's Day
    	expectedHol.add(new Date(23,January,year));   // 2 Lunar New Year's Day
//		expectedHol.add(new Date(24,JANUARY,year));   // The 3 day of Lunar New Year
		expectedHol.add(new Date(5,April,year));      // Ching Ming Festival
		expectedHol.add(new Date(9,April,year));     // Good Friday
		expectedHol.add(new Date(12,April,year));     // Easter Monday
        expectedHol.add(new Date(26,May,year));       // The Buddha's Birthday
		expectedHol.add(new Date(22,June,year));       // The Tuen Ng Festival
        expectedHol.add(new Date(1,July,year));       // Hong Kong Special Administrative Region Establishment Day
    	expectedHol.add(new Date(29,September,year));   // The day after Chinese Mid-Autumn Festival
        //expectedHol.add(new Date(22,OCTOBER,year));    // Chung Yeung festival
        expectedHol.remove(new Date(25,December,year));
        expectedHol.remove(new Date(26,December,year));
        expectedHol.add(new Date(27,December,year));

        new CalendarUtil().checkHolidayList(expectedHol, c, year);
    }

}
