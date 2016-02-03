/*
 Copyright (C) 2008 Jia Jia

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
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Denmark;
import org.junit.Test;

/**
 * @author Jia Jia
 *
 *
 */

public class DenmarkCalendarTest {

	private final Calendar exchange;

	public DenmarkCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
		exchange = new Denmark();
	}

	@Test
	public void testCSEYear2004() {
		final int year = 2004;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
		// maunday thursday
		expectedHol.add(new Date(8, April, year));
		// good friday
		expectedHol.add(new Date(9, April, year));
		// easter monday
		expectedHol.add(new Date(12, April, year));

		// great prayer day
		expectedHol.add(new Date(7, May, year));
		// ascension
		expectedHol.add(new Date(20, May, year));
		// whit monday
		expectedHol.add(new Date(31, May, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	@Test
	public void testCSEYear2005() {
		final int year = 2005;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(24, March, year));
		// good friday
		expectedHol.add(new Date(25, March, year));
		// easter monday
		expectedHol.add(new Date(28, March, year));

		// great prayer day
		expectedHol.add(new Date(22, April, year));
		// ascension
		expectedHol.add(new Date(5, May, year));
		// whit monday
		expectedHol.add(new Date(16, May, year));
		// boxing day
		expectedHol.add(new Date(26, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	@Test
	public void testCSEYear2006() {
		final int year = 2006;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(13, April, year));
		// maunday thursday
		expectedHol.add(new Date(14, April, year));
		// good friday
		expectedHol.add(new Date(17, April, year));
		// easter monday
		expectedHol.add(new Date(12, May, year));

		// great prayer day
		expectedHol.add(new Date(25, May, year));
		// ascension
		expectedHol.add(new Date(5, June, year));
		// christmas
		expectedHol.add(new Date(25, December, year));
		// boxing day
		expectedHol.add(new Date(26, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	@Test
	public void testCSEYear2007() {
		final int year = 2007;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
		// maunday thursday
		expectedHol.add(new Date(5, April, year));
		// good friday
		expectedHol.add(new Date(6, April, year));
		// easter monday
		expectedHol.add(new Date(9, April, year));

		// great prayer day
		expectedHol.add(new Date(4, May, year));
		// ascension
		expectedHol.add(new Date(17, May, year));
		// whit monday
		expectedHol.add(new Date(28, May, year));
		// constitution day
		expectedHol.add(new Date(5, June, year));
		// christmas eve
		expectedHol.add(new Date(24, December, year));
		// christmas
		expectedHol.add(new Date(25, December, year));
		// boxing day
		expectedHol.add(new Date(26, December, year));
		// new year's eve
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	// 2008 - current year
	@Test
	public void testCSEYear2008() {
		final int year = 2008;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
		// maunday thursday
		expectedHol.add(new Date(20, March, year));
		// good friday
		expectedHol.add(new Date(21, March, year));
		// easter monday
		expectedHol.add(new Date(24, March, year));

		// great prayer day
		expectedHol.add(new Date(18, April, year));
		// ascension
		expectedHol.add(new Date(1, May, year));
		// whit monday
		expectedHol.add(new Date(12, May, year));
		// constitution day
		expectedHol.add(new Date(5, June, year));
		// christmas eve
		expectedHol.add(new Date(24, December, year));
		// christmas
		expectedHol.add(new Date(25, December, year));
		// boxing day
		expectedHol.add(new Date(26, December, year));
		// boxing day
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	@Test
	public void testCSEYear2009() {
		final int year = 2009;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
		// maunday thursday
		expectedHol.add(new Date(9, April, year));
		// good friday
		expectedHol.add(new Date(10, April, year));
		// easter monday
		expectedHol.add(new Date(13, April, year));

		// great prayer day
		expectedHol.add(new Date(8, May, year));
		// ascension
		expectedHol.add(new Date(21, May, year));
		// ascension
		expectedHol.add(new Date(22, May, year));
		// whit monday
		expectedHol.add(new Date(1, June, year));
		// constitution day
		expectedHol.add(new Date(5, June, year));
		// christmas eve
		expectedHol.add(new Date(24, December, year));
		// christmas
		expectedHol.add(new Date(25, December, year));
		expectedHol.add(new Date(31, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	@Test
	public void testCSEYear2010() {
		final int year = 2010;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
		// maunday thursday
		expectedHol.add(new Date(1, April, year));
		// good friday
		expectedHol.add(new Date(2, April, year));
		// easter monday
		expectedHol.add(new Date(5, April, year));

		// great prayer day
		expectedHol.add(new Date(30, April, year));
		// ascension
		expectedHol.add(new Date(13, May, year));
		// whit monday
		expectedHol.add(new Date(24, May, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	@Test
	public void testCSEYear2012() {
		final int year = 2012;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(5, April, year));
		// maunday thursday
		expectedHol.add(new Date(6, April, year));
		// good friday
		expectedHol.add(new Date(9, April, year));
		// easter monday
		expectedHol.add(new Date(4, May, year));

		// great prayer day
		expectedHol.add(new Date(17, May, year));
		// ascension
		expectedHol.add(new Date(28, May, year));
		// whit monday
		expectedHol.add(new Date(5, June, year));
		// christmas
		expectedHol.add(new Date(25, December, year));
		// boxing day
		expectedHol.add(new Date(26, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

	@Test
	public void testCSEYear2011() {
		final int year = 2011;
    	QL.info("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(21, April, year));
		// maunday thursday
		expectedHol.add(new Date(22, April, year));
		// good friday
		expectedHol.add(new Date(25, April, year));
		// easter monday
		expectedHol.add(new Date(20, May, year));

		// great prayer day
		expectedHol.add(new Date(2, June, year));
		// ascension
		expectedHol.add(new Date(13, June, year));
		// boxing day
		expectedHol.add(new Date(26, December, year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, exchange, year);
	}

}
