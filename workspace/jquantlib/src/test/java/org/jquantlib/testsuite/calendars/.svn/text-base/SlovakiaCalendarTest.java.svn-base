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
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.September;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Slovakia;
import org.junit.Test;

/**
 * @author Heng Joon Tiang
 * @author Renjith Nair
 *
 */

public class SlovakiaCalendarTest {

	public SlovakiaCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}


	//FIXME: Needs to obtain reliable data for 2004
	@Test
	public void testSlovakiaYear2004() {
      	final int year = 2004;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1,January,year));
//		expectedHol.add(new Date(2,JANUARY,year));
		//expectedHol.add(new Date(3,JANUARY,year));
		//expectedHol.add(new Date(4,JANUARY,year));
//		expectedHol.add(new Date(5,JANUARY,year));
		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(9,April,year));
		expectedHol.add(new Date(12,April,year));
		//expectedHol.add(new Date(1,MAY,year));
		//expectedHol.add(new Date(8,MAY,year));
		expectedHol.add(new Date(5,July,year));
		//expectedHol.add(new Date(29,AUGUST,year));
    	expectedHol.add(new Date(1,September,year));
		expectedHol.add(new Date(15,September,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(17,November,year));
		expectedHol.add(new Date(24,December,year));
		//expectedHol.add(new Date(25,DECEMBER,year));
		//expectedHol.add(new Date(26,DECEMBER,year));
		expectedHol.add(new Date(27,December,year));
		expectedHol.add(new Date(28,December,year));
		expectedHol.add(new Date(29,December,year));
		expectedHol.add(new Date(30,December,year));
		expectedHol.add(new Date(31,December,year));
        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }


	//	2005 - BSSE Trading Holidays
	//
	//	01 Jan    Sat    New Year's/Founding of Slovak Republic
	//	02 Jan    Sun    New Year's Holiday 2
	//	03 Jan    Mon    New Year's Holiday 3
	//	04 Jan    Tue    New Year's Holiday 4
	//	05 Jan    Wed    New Year's Holiday 5
	//	06 Jan    Thu    Epiphany
	//	25 Mar    Fri    Good Fri
	//	28 Mar    Mon    Easter Mon
	//	01 May    Sun    Labour Day
	//	08 May    Sun    V-E Day
	//	05 Jul    Tue    Sts. Cyril and Methodius Day
	//	29 Aug    Mon    Anniversary of Slovak National Uprising
	//	01 Sep    Thu    Constitution Day
	//	15 Sep    Thu    Our Lady of Sorrows
	//	01 Nov    Tue    All Saints' Day
	//	17 Nov    Thu    Freedom and Democracy Day
	//	24 Dec    Sat    Christmas Eve
	//	25 Dec    Sun    Christmas Day
	//	26 Dec    Mon    Boxing Day
	//	27 Dec    Tue    Christmas Holiday 1
	//	28 Dec    Wed    Christmas Holiday 2
	//	29 Dec    Thu    Christmas Holiday 3
	//	30 Dec    Fri    Christmas Holiday 4
	//	31 Dec    Sat    New Year's Eve

	@Test
	public void testSlovakiaYear2005() {
      	final int year = 2005;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

		//expectedHol.add(new Date(1,JANUARY,year));
		//expectedHol.add(new Date(2,JANUARY,year));
//		expectedHol.add(new Date(3,JANUARY,year));
//		expectedHol.add(new Date(4,JANUARY,year));
//		expectedHol.add(new Date(5,JANUARY,year));
		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(25,March,year));
		expectedHol.add(new Date(28,March,year));
		//expectedHol.add(new Date(1,MAY,year));
		//expectedHol.add(new Date(8,MAY,year));
		expectedHol.add(new Date(5,July,year));
		expectedHol.add(new Date(29,August,year));
    	expectedHol.add(new Date(1,September,year));
		expectedHol.add(new Date(15,September,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(17,November,year));
		//expectedHol.add(new Date(24,DECEMBER,year));
		//expectedHol.add(new Date(25,DECEMBER,year));
		expectedHol.add(new Date(26,December,year));
		expectedHol.add(new Date(27,December,year));
		expectedHol.add(new Date(28,December,year));
		expectedHol.add(new Date(29,December,year));
		expectedHol.add(new Date(30,December,year));
		//expectedHol.add(new Date(31,DECEMBER,year));

        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }


	//FIXME: Needs to obtain reliable data for 2006
	@Test
	public void testSlovakiaYear2006() {

		final int year = 2006;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

		//expectedHol.add(new Date(1,JANUARY,year));
//		expectedHol.add(new Date(2,JANUARY,year));
//		expectedHol.add(new Date(3,JANUARY,year));
//		expectedHol.add(new Date(4,JANUARY,year));
//		expectedHol.add(new Date(5,JANUARY,year));
		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(14,April,year));
		expectedHol.add(new Date(17,April,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(8,May,year));
		expectedHol.add(new Date(5,July,year));
		expectedHol.add(new Date(29,August,year));
    	expectedHol.add(new Date(1,September,year));
		expectedHol.add(new Date(15,September,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(17,November,year));
		//expectedHol.add(new Date(24,DECEMBER,year));
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));
//		expectedHol.add(new Date(27,DECEMBER,year));
//		expectedHol.add(new Date(28,DECEMBER,year));
//		expectedHol.add(new Date(29,DECEMBER,year));
		//expectedHol.add(new Date(30,DECEMBER,year));
		//expectedHol.add(new Date(31,DECEMBER,year));

		// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }

	//	2007 - BSSE Trading Holidays
	//
	//	01 Jan    Mon    Founding of Slovak Republic
	//	02 Jan    Tue    New Year's Holiday 2
	//	03 Jan    Wed    New Year's Holiday 3
	//	04 Jan    Thu    New Year's Holiday 4
	//	05 Jan    Fri    New Year's Holiday 5
	//	06 Jan    Sat    Epiphany
	//	06 Apr    Fri    Good Friday
	//	09 Apr    Mon    Easter Monday
	//	01 May    Tue    Labour Day
	//	08 May    Tue    V-E Day
	//	05 Jul    Thu    Sts. Cyril and Methodius Day
	//	29 Aug    Wed    Anniversary of Slovak National Uprising
	//	01 Sep    Sat    Constitution Day
	//	15 Sep    Sat    Our Lady of Sorrows
	//	01 Nov    Thu    All Saints' Day
	//	17 Nov    Sat    Freedom and Democracy Day
	//	24 Dec    Mon    Christmas Eve
	//	25 Dec    Tue    Christmas Day
	//	26 Dec    Wed    Boxing Day
	//	27 Dec    Thu    Christmas Holiday 1
	//	28 Dec    Fri    Christmas Holiday 2
	//	29 Dec    Sat    Christmas Holiday 3
	//	30 Dec    Sun    Christmas Holiday 4
	//	31 Dec    Mon    New Year's Eve

	@Test
	public void testSlovakiaYear2007() {

		final int year = 2007;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
//    	expectedHol.add(new Date(2,JANUARY,year));
//    	expectedHol.add(new Date(3,JANUARY,year));
//    	expectedHol.add(new Date(4,JANUARY,year));
//    	expectedHol.add(new Date(5,JANUARY,year));
    	// expectedHol.add(new Date(6,JANUARY,year));
		expectedHol.add(new Date(6,April,year));
		expectedHol.add(new Date(9,April,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(8,May,year));
		expectedHol.add(new Date(5,July,year));
		expectedHol.add(new Date(29,August,year));
		// expectedHol.add(new Date(1,SEPTEMBER,year));
		// expectedHol.add(new Date(15,SEPTEMBER,year));
		expectedHol.add(new Date(1,November,year));
		// expectedHol.add(new Date(17,NOVEMBER,year));
		expectedHol.add(new Date(24,December,year));
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));
//		expectedHol.add(new Date(27,DECEMBER,year));
//		expectedHol.add(new Date(28,DECEMBER,year));
		// expectedHol.add(new Date(29,DECEMBER,year));
		// expectedHol.add(new Date(30,DECEMBER,year));
//		expectedHol.add(new Date(31,DECEMBER,year));

		// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }


	//	2008 - BSSE Trading Holidays
	//
	//	01 Jan   Tue    Founding of Slovak Republic
	//	02 Jan   Wed    New Year's Holiday 2
	//	03 Jan   Thu    New Year's Holiday 3
	//	04 Jan   Fri    New Year's Holiday 4
	//	05 Jan   Sat    New Year's Holiday 5
	//	06 Jan   Sun    Epiphany
	//	21 Mar   Fri    Good Friday
	//	24 Mar   Mon    Easter Monday
	//	01 May   Thu    Labour Day
	//	08 May   Thu    V-E Day
	//	05 Jul   Sat    Sts. Cyril and Methodius Day
	//	29 Aug   Fri    Anniversary of Slovak National Uprising
	//	01 Sep   Mon    Constitution Day
	//	15 Sep   Mon    Our Lady of Sorrows
	//	01 Nov   Sat    All Saints' Day
	//	17 Nov   Mon    Freedom and Democracy Day
	//	24 Dec   Wed    Christmas Eve
	//	25 Dec   Thu    Christmas Day
	//	26 Dec   Fri    Boxing Day
	//	27 Dec   Sat    Christmas Holiday 1
	//	28 Dec   Sun    Christmas Holiday 2
	//	31 Dec   Wed    New Year's Eve

	@Test
	public void testSlovakiaYear2008() {

		final int year = 2008;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
//    	expectedHol.add(new Date(2,JANUARY,year));
//    	expectedHol.add(new Date(3,JANUARY,year));
//    	expectedHol.add(new Date(4,JANUARY,year));
    	// expectedHol.add(new Date(5,JANUARY,year));
    	// expectedHol.add(new Date(6,JANUARY,year));
		expectedHol.add(new Date(21,March,year));
		expectedHol.add(new Date(24,March,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(8,May,year));
		expectedHol.add(new Date(29,August,year));
    	expectedHol.add(new Date(1,September,year));
		expectedHol.add(new Date(15,September,year));
		// expectedHol.add(new Date(1,NOVEMBER,year));
		expectedHol.add(new Date(17,November,year));
		expectedHol.add(new Date(24,December,year));
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));
		// expectedHol.add(new Date(27,DECEMBER,year));
		// expectedHol.add(new Date(28,DECEMBER,year));
//		expectedHol.add(new Date(29,DECEMBER,year));
//		expectedHol.add(new Date(30,DECEMBER,year));
//		expectedHol.add(new Date(31,DECEMBER,year));

		// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }

	//FIXME: Needs to obtain reliable data for 2009
	@Test
	public void testSlovakiaYear2009() {

		final int year = 2009;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
//    	expectedHol.add(new Date(2,JANUARY,year));
    	//expectedHol.add(new Date(3,JANUARY,year));
    	//expectedHol.add(new Date(4,JANUARY,year));
//    	expectedHol.add(new Date(5,JANUARY,year));
		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(10,April,year));
		expectedHol.add(new Date(13,April,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(8,May,year));
    	expectedHol.add(new Date(1,September,year));
		expectedHol.add(new Date(15,September,year));
		expectedHol.add(new Date(17,November,year));
		expectedHol.add(new Date(24,December,year));
		expectedHol.add(new Date(25,December,year));
		//expectedHol.add(new Date(26,DECEMBER,year));
		//expectedHol.add(new Date(27,DECEMBER,year));
//		expectedHol.add(new Date(28,DECEMBER,year));
//		expectedHol.add(new Date(29,DECEMBER,year));
//		expectedHol.add(new Date(30,DECEMBER,year));
//		expectedHol.add(new Date(31,DECEMBER,year));

		// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }

	//FIXME: Needs to obtain reliable data for 2010
	@Test
	public void testSlovakiaYear2010() {

		final int year = 2010;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	//expectedHol.add(new Date(2,JANUARY,year));
    	//expectedHol.add(new Date(3,JANUARY,year));
//    	expectedHol.add(new Date(4,JANUARY,year));
//    	expectedHol.add(new Date(5,JANUARY,year));
		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(2,April,year));
		expectedHol.add(new Date(5,April,year));
		expectedHol.add(new Date(5,July,year));
    	expectedHol.add(new Date(1,September,year));
		expectedHol.add(new Date(15,September,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(17,November,year));
		expectedHol.add(new Date(24,December,year));
		//expectedHol.add(new Date(25,DECEMBER,year));
		//expectedHol.add(new Date(26,DECEMBER,year));
//		expectedHol.add(new Date(27,DECEMBER,year));
//		expectedHol.add(new Date(28,DECEMBER,year));
//		expectedHol.add(new Date(29,DECEMBER,year));
//		expectedHol.add(new Date(30,DECEMBER,year));
//		expectedHol.add(new Date(31,DECEMBER,year));

		// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }

	//FIXME: Needs to obtain reliable data for 2011
	@Test
	public void testSlovakiaYear2011() {

		final int year = 2011;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

		//expectedHol.add(new Date(1,JANUARY,year));
		//expectedHol.add(new Date(2,JANUARY,year));
//		expectedHol.add(new Date(3,JANUARY,year));
//		expectedHol.add(new Date(4,JANUARY,year));
//		expectedHol.add(new Date(5,JANUARY,year));
		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(22,April,year));
		expectedHol.add(new Date(25,April,year));
		expectedHol.add(new Date(5,July,year));
		expectedHol.add(new Date(29,August,year));
    	expectedHol.add(new Date(1,September,year));
		expectedHol.add(new Date(15,September,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(17,November,year));
		//expectedHol.add(new Date(24,DECEMBER,year));
		//expectedHol.add(new Date(25,DECEMBER,year));
		expectedHol.add(new Date(26,December,year));
//		expectedHol.add(new Date(27,DECEMBER,year));
//		expectedHol.add(new Date(28,DECEMBER,year));
//		expectedHol.add(new Date(29,DECEMBER,year));
//		expectedHol.add(new Date(30,DECEMBER,year));
		//expectedHol.add(new Date(31,DECEMBER,year));

		// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }

	//FIXME: Needs to obtain reliable data for 2012
	@Test
	public void testSlovakiaYear2012() {

		final int year = 2012;
      	QL.info("Testing Solvakia's holiday list for the year " + year + "...");

        
      	final Calendar c = new Slovakia(Slovakia.Market.BSSE);
    	final List<Date> expectedHol = new ArrayList<Date>();

		//expectedHol.add(new Date(1,JANUARY,year));
//		expectedHol.add(new Date(2,JANUARY,year));
//		expectedHol.add(new Date(3,JANUARY,year));
//		expectedHol.add(new Date(4,JANUARY,year));
//		expectedHol.add(new Date(5,JANUARY,year));
		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(6,April,year));
		expectedHol.add(new Date(9,April,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(8,May,year));
		expectedHol.add(new Date(5,July,year));
		expectedHol.add(new Date(29,August,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(24,December,year));
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));
//		expectedHol.add(new Date(27,DECEMBER,year));
//		expectedHol.add(new Date(28,DECEMBER,year));
		//expectedHol.add(new Date(29,DECEMBER,year));
		//expectedHol.add(new Date(30,DECEMBER,year));
//		expectedHol.add(new Date(31,DECEMBER,year));

		// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, c, year);
    }
}
