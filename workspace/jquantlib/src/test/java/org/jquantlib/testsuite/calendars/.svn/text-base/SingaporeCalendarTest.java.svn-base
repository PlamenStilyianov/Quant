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
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Singapore;
import org.junit.Test;

/**
 * @author Joon Tiang
 * @author Renjith Nair
 *
 */


public class SingaporeCalendarTest {

	public SingaporeCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	// 2004

	@Test
	public void testSingaporeYear2004() {
      	final int year = 2004;
      	QL.info("Testing Singapore's holiday list for the year " + year + "...");

        
      	final Calendar c = new Singapore(Singapore.Market.SGX);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	//New year's day
    	expectedHol.add(new Date(1,January,year));

		//Chinese New Year
		expectedHol.add(new Date(22,January,year));
		expectedHol.add(new Date(23,January,year));

		// Hari Raya Haji
		expectedHol.add(new Date(2,February,year));

		//good friday
		expectedHol.add(new Date(9,April,year));

    	//labour day -- weekend in yr 2004
    	//expectedHol.add(new Date(1,MAY,year));

		//Vesak day
		expectedHol.add(new Date(2,June,year));

    	// National Day
    	expectedHol.add(new Date(9,August,year));

		//Deepavali
		expectedHol.add(new Date(11,November,year));

    	// Hari Raya Puasa -- 14 is in the weekends of 2004
		expectedHol.add(new Date(15,November,year));

		//christmas  -- weekend in yr 2004
    	//expectedHol.add(new Date(25,DECEMBER,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }

	// 2005

	@Test
	public void testSingaporeYear2005() {
      	final int year = 2005;
      	QL.info("Testing Singapore's holiday list for the year " + year + "...");

        
      	final Calendar c = new Singapore(Singapore.Market.SGX);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	//New year's day -- weekend in yr 2005
    	//expectedHol.add(new Date(1,JANUARY,year));
		// Hari Raya Haji
		expectedHol.add(new Date(21,January,year));

		//Chinese New Year
    	expectedHol.add(new Date(9,February,year));
		expectedHol.add(new Date(10,February,year));

		//good friday
		expectedHol.add(new Date(25,March,year));

    	//labour day -- weekend in yr 2005
    	//expectedHol.add(new Date(1,MAY,year));

		//Vesak day -- weekend in yr 2005
		//expectedHol.add(new Date(22,MAY,year));

    	// National Day
    	expectedHol.add(new Date(9,August,year));
		//Diwali
		expectedHol.add(new Date(1,November,year));
    	// Hari Raya Puasa -- weekend in 2005
		expectedHol.add(new Date(3,November,year));

		//christmas  -- weekend in yr 2005
    	//expectedHol.add(new Date(25,DECEMBER,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }

	// 2006

	@Test
	public void testSingaporeYear2006() {
      	final int year = 2006;
      	QL.info("Testing Singapore's holiday list for the year " + year + "...");

        
      	final Calendar c = new Singapore(Singapore.Market.SGX);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	//New year's day -- weekend in 2006
    	//expectedHol.add(new Date(1,JANUARY,year));
		// Hari Raya Haji
		expectedHol.add(new Date(10,January,year));

		//Chinese New Year
    	expectedHol.add(new Date(30,January,year));
		expectedHol.add(new Date(31,January,year));

		//good friday
		expectedHol.add(new Date(14,April,year));

    	//labour day
    	expectedHol.add(new Date(1,May,year));

		//Vesak day
		expectedHol.add(new Date(12,May,year));

    	// National Day
    	expectedHol.add(new Date(9,August,year));
    	// Hari Raya Puasa -- weekend in 2007
		expectedHol.add(new Date(24,October,year));



		//christmas
    	expectedHol.add(new Date(25,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }


	//	2007 - SGX Trading Holidays
	//
	//	01 Jan    Mon    New Year's Day
	//	02 Jan    Tue    Hari Raya Haji*
	//	19 Feb    Mon    Lunar New Year 2
	//	20 Feb    Tue    Lunar New Year 1
	//	06 Apr    Fri    Good Friday
	//	01 May    Tue    Labour Day
	//	31 May    Thu    Vesak Day*
	//	09 Aug    Thu    National Day
	//	13 Oct    Sat    Hari Raya Puasa*
	//	08 Nov    Thu    Deepavali*
	//	20 Dec    Thu    Hari Raya Haji*
	//	25 Dec    Tue    Christmas Day
	//
	//	*Date observed may vary

	@Test
	public void testSingaporeYear2007() {
      	final int year = 2007;
      	QL.info("Testing Singapore's holiday list for the year " + year + "...");

        
      	final Calendar c = new Singapore(Singapore.Market.SGX);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	//New year's day
    	expectedHol.add(new Date(1,January,year));
		// Hari Raya Haji
		expectedHol.add(new Date(2,January,year));

		//Chinese New Year
    	expectedHol.add(new Date(19,February,year));
		expectedHol.add(new Date(20,February,year));

		//good friday
		expectedHol.add(new Date(6,April,year));

    	//labour day
    	expectedHol.add(new Date(1,May,year));

		//Vesak day
		expectedHol.add(new Date(31,May,year));

    	// National Day
    	expectedHol.add(new Date(9,August,year));
    	// Hari Raya Puasa -- weekend in 2007
		//expectedHol.add(new Date(13,OCTOBER,year));
		// Deepavali
		expectedHol.add(new Date(8,November,year));


		// Hari Raya Haji
		expectedHol.add(new Date(20,December,year));

		//christmas
    	expectedHol.add(new Date(25,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }


	//	2008 - SGX Trading Holidays
	//
	//	01 Jan   Tue    New Year's Day
	//	07 Feb   Thu    Lunar New Year 1
	//	08 Feb   Fri    Lunar New Year 2
	//	21 Mar   Fri    Good Friday
	//	01 May   Thu    Labour Day
	//	19 May   Mon    Vesak Day*
	//	09 Aug   Sat    National Day
	//	01 Oct   Wed    Hari Raya Puasa*
	//	27 Oct   Mon    Deepavali*
	//	08 Dec   Mon    Hari Raya Haji*
	//	25 Dec   Thu    Christmas Day
	//
	//	*Date observed may vary

	@Test
	public void testSingaporeYear2008() {
      	final int year = 2008;
      	QL.info("Testing Singapore's holiday list for the year " + year + "...");

        
      	final Calendar c = new Singapore(Singapore.Market.SGX);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	//New year's day
    	expectedHol.add(new Date(1,January,year));


		//Chinese New Year
    	expectedHol.add(new Date(7,February,year));
		expectedHol.add(new Date(8,February,year));

		//good friday
		expectedHol.add(new Date(21,March,year));

    	//labour day
    	expectedHol.add(new Date(1,May,year));

    	//Vesak day
//    	expectedHol.add(new Date(18,MAY,year)); //Zahid 18 from 19, Sunday

    	// National Day -- weekend in yr 2008
    	// expectedHol.add(new Date(9,AUGUST,year));

    	// Hari Raya Puasa -- weekend in 2007
		expectedHol.add(new Date(1,October,year));

		// Deepavali
		expectedHol.add(new Date(28,October,year));//Zahid 28 from 27

		// Hari Raya Haji
		expectedHol.add(new Date(8,December,year));

		//christmas
    	expectedHol.add(new Date(25,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }


	//	2009 - SGX Trading Holidays
	//
	//	01 Jan Thu    New Year's Day
	//	26 Jan Mon    Chinese New Year
	//	27 Jan Tue    Chinese New Year
	//	10 Apr Fri    Good Friday
	//	01 May Fri    Labour Day
	//	09 May Sat    Vesak Day
	//	09 Aug Sun    National Day
	//	20 Sep Sun    Hari Raya Puasa
	//	15 Nov Sun    Deepavali
	//	27 Nov Fri    Hari Raya Haji
	//	25 Dec Fri    Christmas Day


	@Test
	public void testSingaporeYear2009() {
      	final int year = 2009;
      	QL.info("Testing Singapore's holiday list for the year " + year + "...");

        
      	final Calendar c = new Singapore(Singapore.Market.SGX);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	//New year's day
    	expectedHol.add(new Date(1,January,year));

    	// Chinese New Year
    	expectedHol.add(new Date(26,January,year));
    	expectedHol.add(new Date(27,January,year));

    	// Good Friday
    	expectedHol.add(new Date(10,April,year));

		//labour day
    	expectedHol.add(new Date(1,May,year));

    	// Vesak Day
    	//expectedHol.add(new Date(9,MAY,year));

    	// National Day
    	//expectedHol.add(new Date(9,AUGUST,year));

    	// Hari Raya Puasa
    	//expectedHol.add(new Date(20,SEPTEMBER,year));

    	// Deepavali
    	//expectedHol.add(new Date(15,NOVEMBER,year));

    	// Hari Raya Haji
    	expectedHol.add(new Date(27,November,year));

		//christmas
    	expectedHol.add(new Date(25,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }


//	@Test
//	public void testSingaporeYear2010() {
//      	final int year = 2010;
//      	QL.info("Testing Singapore's holiday list for the year " + year + "...");
//
//        
//      	final Calendar c = Singapore.getCalendar(Singapore.Market.SGX);
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	//New year's day
//    	expectedHol.add(new Date(1,JANUARY,year));
//		expectedHol.add(new Date(2,APRIL,year));
//    	// National Day --
//    	expectedHol.add(new Date(9,AUGUST,year));
//
//        // Call the Holiday Check
//        CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, c, year);
//    }
//
//
//	// 2011 - Year in Future
//	@Test
//	public void testSingaporeYear2011() {
//      	final int year = 2011;
//      	QL.info("Testing Singapore's holiday list for the year " + year + "...");
//
//        
//      	final Calendar c = Singapore.getCalendar(Singapore.Market.SGX);
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	expectedHol.add(new Date(22,APRIL,year));
//    	// National Day --
//    	expectedHol.add(new Date(9,AUGUST,year));
//
//        // Call the Holiday Check
//        CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, c, year);
//    }
//
//	// 2012 - Year in Future
//	@Test
//	public void testSingaporeYear2012() {
//      	final int year = 2012;
//      	QL.info("Testing Singapore's holiday list for the year " + year + "...");
//
//        
//      	final Calendar c = Singapore.getCalendar(Singapore.Market.SGX);
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	expectedHol.add(new Date(6,APRIL,year));
//    	//labour day
//    	expectedHol.add(new Date(1,MAY,year));
//    	// National Day -- weekend in yr 2008
//    	expectedHol.add(new Date(9,AUGUST,year));
//		//christmas
//    	expectedHol.add(new Date(25,DECEMBER,year));
//
//
//        // Call the Holiday Check
//        CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, c, year);
//    }

}

