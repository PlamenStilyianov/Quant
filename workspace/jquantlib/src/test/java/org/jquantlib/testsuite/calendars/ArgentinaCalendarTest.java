/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2008 Dominik Holenstein

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
import org.jquantlib.time.calendars.Argentina;
import org.junit.Test;

/**
 * @author Srinivas Hasti
 * @author Dominik Holenstein
 * @author Jia Jia
 */

public class ArgentinaCalendarTest {

	private final Calendar merval;
//	private final Calendar settlement;

	public ArgentinaCalendarTest() {
		QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

//		settlement = new Argentina(Argentina.Market.SETTLEMENT);
		merval = new Argentina(Argentina.Market.MERVAL);
	}

	@Test
	public void testArgentinaMervalYear2004() {
		final int year = 2004;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
//		expectedHol.add(new Date(24, MARCH, year));
//		expectedHol.add(new Date(2, APRIL, year));
		expectedHol.add(new Date(8, April, year));
		expectedHol.add(new Date(9, April, year));
		expectedHol.add(new Date(25, May, year));
		// expectedHol.add(new Date(1,MAY,year)); --> Sunday
		expectedHol.add(new Date(21, June, year));
		expectedHol.add(new Date(9, July, year));
		expectedHol.add(new Date(16, August, year));
		expectedHol.add(new Date(11, October, year));
		expectedHol.add(new Date(8, December, year));
		expectedHol.add(new Date(24, December, year));
		// expectedHol.add(new Date(25,DECEMBER,year));
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);

	}

	@Test
	public void testArgentinaMervalYear2005() {
		final int year = 2005;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(24, March, year));
		expectedHol.add(new Date(25, March, year));
		expectedHol.add(new Date(25, May, year));
		expectedHol.add(new Date(20, June, year));
		expectedHol.add(new Date(15, August, year));
		expectedHol.add(new Date(10, October, year));
		expectedHol.add(new Date(8, December, year));

//		expectedHol.add(new Date(25, DECEMBER, year));
		expectedHol.add(new Date(30, December, year));
		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);
	}

	@Test
	public void testArgentinaMervalYear2006() {

		final int year = 2006;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

//		expectedHol.add(new Date(24, MARCH, year));
		expectedHol.add(new Date(13, April, year));
		expectedHol.add(new Date(14, April, year));
		expectedHol.add(new Date(1, May, year));
		expectedHol.add(new Date(25, May, year));
		expectedHol.add(new Date(19, June, year));
		expectedHol.add(new Date(21, August, year));
		expectedHol.add(new Date(16, October, year));
//		expectedHol.add(new Date(6, NOVEMBER, year));
		expectedHol.add(new Date(8, December, year));
//		expectedHol.add(new Date(25, DECEMBER, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);
	}

	// 2007 - MERVAL Trading Holidays
	//
	// 01 Jan Mon New Year's Day
	// 24 Mar Sat Truth and Justice Day
	// 02 Apr Mon Malvinas Islands Memorial
	// 05 Apr Thu Holy Thursday
	// 06 Apr Fri Good Friday
	// 01 May Tue Workers' Day
	// 25 May Fri National Holiday
	// 18 Jun Mon Flag Day
	// 09 Jul Mon Independence Day
	// 20 Aug Mon Anniversary of the Death of General San Martin
	// 15 Oct Mon Columbus Day (OBS)
	// 06 Nov Tue Bank Holiday
	// 08 Dec Sat Immaculate Conception
	// 24 Dec Mon Christmas Eve
	// 25 Dec Tue Christmas Day
	// 31 Dec Mon Last business day of year

	@Test
	public void testArgentinaMervalYear2007() {

		final int year = 2007;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
		// expectedHol.add(new Date(24,MARCH,year));
//		expectedHol.add(new Date(2, APRIL, year));
		expectedHol.add(new Date(5, April, year));
		expectedHol.add(new Date(6, April, year));
		expectedHol.add(new Date(1, May, year));
		expectedHol.add(new Date(25, May, year));
		expectedHol.add(new Date(18, June, year));
		expectedHol.add(new Date(9, July, year));
		expectedHol.add(new Date(20, August, year));
		expectedHol.add(new Date(15, October, year));
		expectedHol.add(new Date(20, August, year));
//		expectedHol.add(new Date(6, NOVEMBER, year));
		// expectedHol.add(new Date( 8,DECEMBER,year));
		expectedHol.add(new Date(24, December, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);
	}

	@Test
	public void testArgentinaMervalYear2008() {
		final int year = 2008;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
		expectedHol.add(new Date(20, March, year));
		expectedHol.add(new Date(21, March, year));
//		expectedHol.add(new Date(24, MARCH, year));
//		expectedHol.add(new Date(2, APRIL, year));
		expectedHol.add(new Date(1, May, year));
		expectedHol.add(new Date(16, June, year));
		expectedHol.add(new Date(9, July, year));
		expectedHol.add(new Date(18, August, year));
//		expectedHol.add(new Date(6, NOVEMBER, year));
		expectedHol.add(new Date(8, December, year));
		expectedHol.add(new Date(24, December, year));
//		expectedHol.add(new Date(25, DECEMBER, year));

		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);
	}

	@Test
	public void testArgentinaMervalYear2009() {

		final int year = 2009;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
//		expectedHol.add(new Date(24, MARCH, year));
//		expectedHol.add(new Date(2, APRIL, year));
		expectedHol.add(new Date(9, April, year));
		expectedHol.add(new Date(10, April, year));
		expectedHol.add(new Date(1, May, year));
		expectedHol.add(new Date(25, May, year));
		expectedHol.add(new Date(15, June, year));
		expectedHol.add(new Date(9, July, year));
		expectedHol.add(new Date(17, August, year));
		expectedHol.add(new Date(12, October, year));
//		expectedHol.add(new Date(6, NOVEMBER, year));
		expectedHol.add(new Date(8, December, year));
		expectedHol.add(new Date(24, December, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);

	}

	@Test
	public void testArgentinaMervalYear2010() {

		final int year = 2010;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
//		expectedHol.add(new Date(24, MARCH, year));
		expectedHol.add(new Date(1, April, year));
		expectedHol.add(new Date(2, April, year));
		expectedHol.add(new Date(25, May, year));
		expectedHol.add(new Date(21, June, year));
		expectedHol.add(new Date(9, July, year));
		expectedHol.add(new Date(16, August, year));
		expectedHol.add(new Date(11, October, year));
		expectedHol.add(new Date(8, December, year));
		expectedHol.add(new Date(24, December, year));
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);
	}

	@Test
	public void testArgentinaMervalYear2011() {
		final int year = 2011;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

//		expectedHol.add(new Date(24, MARCH, year));
		expectedHol.add(new Date(21, April, year));
		expectedHol.add(new Date(22, April, year));
		expectedHol.add(new Date(25, May, year));
		expectedHol.add(new Date(20, June, year));
		expectedHol.add(new Date(15, August, year));
		expectedHol.add(new Date(10, October, year));
		expectedHol.add(new Date(8, December, year));
		expectedHol.add(new Date(30, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);
	}

	@Test
	public void testArgentinaMervalYear2012() {

		final int year = 2012;

		QL.info("Testing " + Argentina.Market.MERVAL
				+ " holiday list for the year " + year + "...");
		
		final List<Date> expectedHol = new ArrayList<Date>();

		// expectedHol.add(DateFactory.getDateUtil().getDate(1,JANUARY,year));
		// --> Sunday
//		expectedHol.add(new Date(2, APRIL, year));
		expectedHol.add(new Date(5, April, year));
		expectedHol.add(new Date(6, April, year));
		expectedHol.add(new Date(1, May, year));
		expectedHol.add(new Date(25, May, year));
		expectedHol.add(new Date(18, June, year));
		expectedHol.add(new Date(9, July, year));
		expectedHol.add(new Date(20, August, year));
		expectedHol.add(new Date(15, October, year));
//		expectedHol.add(new Date(6, NOVEMBER, year));
		expectedHol.add(new Date(24, December, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, merval, year);
	}

//	@Test
//	public void testArgentinaSettlementYear2004() {
//		final int year = 2004;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(1, JANUARY, year));
//		expectedHol.add(new Date(2, APRIL, year));
//		expectedHol.add(new Date(8, APRIL, year));
//		expectedHol.add(new Date(9, APRIL, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(21, JUNE, year));
//		expectedHol.add(new Date(9, JULY, year));
//		expectedHol.add(new Date(16, AUGUST, year));
//		expectedHol.add(new Date(11, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(8, DECEMBER, year));
//		expectedHol.add(new Date(24, DECEMBER, year));
//		expectedHol.add(new Date(31, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2005() {
//		final int year = 2005;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(24, MARCH, year));
//		expectedHol.add(new Date(25, MARCH, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(20, JUNE, year));
//		expectedHol.add(new Date(15, AUGUST, year));
//		expectedHol.add(new Date(10, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(8, DECEMBER, year));
//		expectedHol.add(new Date(30, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2006() {
//		final int year = 2006;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(13, APRIL, year));
//		expectedHol.add(new Date(14, APRIL, year));
//		expectedHol.add(new Date(1, MAY, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(19, JUNE, year));
//		expectedHol.add(new Date(21, AUGUST, year));
//		expectedHol.add(new Date(16, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(8, DECEMBER, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2007() {
//		final int year = 2007;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(1, JANUARY, year));
//		expectedHol.add(new Date(2, APRIL, year));
//		expectedHol.add(new Date(5, APRIL, year));
//		expectedHol.add(new Date(6, APRIL, year));
//		expectedHol.add(new Date(1, MAY, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(18, JUNE, year));
//		expectedHol.add(new Date(9, JULY, year));
//		expectedHol.add(new Date(20, AUGUST, year));
//		expectedHol.add(new Date(15, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(24, DECEMBER, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
//		expectedHol.add(new Date(31, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2008() {
//		final int year = 2008;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(1, JANUARY, year));
//		expectedHol.add(new Date(20, MARCH, year));
//		expectedHol.add(new Date(21, MARCH, year));
//		expectedHol.add(new Date(2, APRIL, year));
//		expectedHol.add(new Date(1, MAY, year));
//		expectedHol.add(new Date(16, JUNE, year));
//		expectedHol.add(new Date(9, JULY, year));
//		expectedHol.add(new Date(18, AUGUST, year));
//		expectedHol.add(new Date(8, DECEMBER, year));
//		expectedHol.add(new Date(24, DECEMBER, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
//		expectedHol.add(new Date(31, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2009() {
//		final int year = 2009;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(1, JANUARY, year));
//		expectedHol.add(new Date(2, APRIL, year));
//		expectedHol.add(new Date(9, APRIL, year));
//		expectedHol.add(new Date(10, APRIL, year));
//		expectedHol.add(new Date(1, MAY, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(15, JUNE, year));
//		expectedHol.add(new Date(9, JULY, year));
//		expectedHol.add(new Date(17, AUGUST, year));
//		expectedHol.add(new Date(12, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(8, DECEMBER, year));
//		expectedHol.add(new Date(24, DECEMBER, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
//		expectedHol.add(new Date(31, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2010() {
//		final int year = 2010;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(1, JANUARY, year));
//		expectedHol.add(new Date(1, APRIL, year));
//		expectedHol.add(new Date(2, APRIL, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(21, JUNE, year));
//		expectedHol.add(new Date(9, JULY, year));
//		expectedHol.add(new Date(16, AUGUST, year));
//		expectedHol.add(new Date(11, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(8, DECEMBER, year));
//		expectedHol.add(new Date(24, DECEMBER, year));
//		expectedHol.add(new Date(31, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2011() {
//		final int year = 2011;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		expectedHol.add(new Date(21, APRIL, year));
//		expectedHol.add(new Date(22, APRIL, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(20, JUNE, year));
//		expectedHol.add(new Date(15, AUGUST, year));
//		expectedHol.add(new Date(10, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(8, DECEMBER, year));
//		expectedHol.add(new Date(30, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}
//
//	@Test
//	public void testArgentinaSettlementYear2012() {
//		final int year = 2012;
//		QL.info("Testing " + Argentina.Market.SETTLEMENT
//				+ " holiday list for the year " + year + "...");
//		
//		final List<Date> expectedHol = new ArrayList<Date>();
//
//		// expectedHol.add(DateFactory.getDateUtil().getDate(1,JANUARY,year));
//		// --> Sunday
//		expectedHol.add(new Date(2, APRIL, year));
//		expectedHol.add(new Date(5, APRIL, year));
//		expectedHol.add(new Date(6, APRIL, year));
//		expectedHol.add(new Date(1, MAY, year));
//		expectedHol.add(new Date(25, MAY, year));
//		expectedHol.add(new Date(18, JUNE, year));
//		expectedHol.add(new Date(9, JULY, year));
//		expectedHol.add(new Date(20, AUGUST, year));
//		expectedHol.add(new Date(15, OCTOBER, year));
//		expectedHol.add(new Date(2, NOVEMBER, year));
//		expectedHol.add(new Date(24, DECEMBER, year));
//		expectedHol.add(new Date(25, DECEMBER, year));
//		expectedHol.add(new Date(31, DECEMBER, year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, settlement, year);
//	}

}
