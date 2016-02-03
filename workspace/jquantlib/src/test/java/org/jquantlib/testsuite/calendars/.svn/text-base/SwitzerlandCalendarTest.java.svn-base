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
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;

import java.util.List;
import java.util.Vector;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Switzerland;
import org.junit.Test;

/**
 * @author Srinivas Hasti
 * @author Dominik Holenstein
 * @author Renjith Nair
 * <p>
 * <strong>Description</strong><br>
 * Switzerland Calendar Test.
 */

public class SwitzerlandCalendarTest {

	public SwitzerlandCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}


	@Test
	public void testSwitzerlandSWXCalendar() {

		QL.info("\n\n=== Switzerland SWX Calendar ===");

		final Calendar c = new Switzerland();

		testSwitzerlandCalendar(c);
	}

//	@Test
//	public void testSwitzerlandSettlementCalendar() {
//
//		QL.info("\n\n=== Switzerland Settlement Calendar ===");
//
//		final Calendar c = new Switzerland();
//
//		testSwitzerlandCalendar( c);
//	}

	private void testSwitzerlandCalendar( final Calendar c) {
		// 2004 - leap-year in the past
		testSwitzerland2004(c);

		// 2005 - year in the past
		testSwitzerland2005(c);

		// 2006 - year in the past
		testSwitzerland2006(c);

		// 2007 - regular year in the past
		testSwitzerland2007(c);

		// 2008 - current year
		testSwitzerland2008(c);

		// 2009 - regular year in the future
		testSwitzerland2009(c);

		// 2010 -  year in the future
		testSwitzerland2010(c);

		// 2011 -  year in the future
		testSwitzerland2011(c);

		// 2012 - leap-year in the future
		testSwitzerland2012(c);
	}

	public void testSwitzerland2004(final Calendar c){

		final int year = 2004;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

		expectedHol.add(new Date(1,January,year));
		expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(12,April,year));
    	// expectedHol.add(DateFactory.getDateUtil().getDate(1,MAY,year)); --> Saturday
    	expectedHol.add(new Date(20,May,year));
    	expectedHol.add(new Date(31,May,year));

//    	if(market == Switzerland.Market.Settlement)
//    	{
//	    	expectedHol.add(new Date(24,DECEMBER,year));
//	    	expectedHol.add(new Date(31,DECEMBER,year));
//    	}
    	// expectedHol.add(DateFactory.getDateUtil().getDate(26,DECEMBER,year)); --> Sunday

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2005(final Calendar c){

		final int year = 2005;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

    	expectedHol.add(new Date(25,March,year));
    	expectedHol.add(new Date(28,March,year));
    	expectedHol.add(new Date(5,May,year));
    	expectedHol.add(new Date(16,May,year));
    	expectedHol.add(new Date(1,August,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2006(final Calendar c ){

		final int year = 2006;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

		expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(14,April,year));
    	expectedHol.add(new Date(17,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(25,May,year));
    	expectedHol.add(new Date(5,June,year));
    	expectedHol.add(new Date(1,August,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2007(final Calendar c) {

		final int year = 2007;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

		expectedHol.add(new Date(1,January,year));
		expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(6,April,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(17,May,year));
    	expectedHol.add(new Date(28,May,year));
    	expectedHol.add(new Date(1,August,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

//    	if(market == Switzerland.Market.Settlement)
//    	{
//	    	expectedHol.add(new Date(24,DECEMBER,year));
//	    	expectedHol.add(new Date(31,DECEMBER,year));
//    	}

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2008(final Calendar c){

		final int year = 2008;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

		expectedHol.add(new Date(1,January,year));
		expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(21,March,year));
    	expectedHol.add(new Date(24,March,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(12,May,year));
    	expectedHol.add(new Date(1,August,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

//    	if(market == Switzerland.Market.Settlement)
//    	{
//	    	expectedHol.add(new Date(24,DECEMBER,year));
//	    	expectedHol.add(new Date(31,DECEMBER,year));
//    	}

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2009(final Calendar c) {

		final int year = 2009;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

		expectedHol.add(new Date(1,January,year));
		expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(10,April,year));
    	expectedHol.add(new Date(13,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(21,May,year));
    	expectedHol.add(new Date(01,June,year));
    	// expectedHol.add(DateFactory.getDateUtil().getDate(1,AUGUST,year)); --> Saturday
    	expectedHol.add(new Date(25,December,year));
    	// expectedHol.add(DateFactory.getDateUtil().getDate(26,DECEMBER,year)); --> Saturday

//    	if(market == Switzerland.Market.Settlement)
//    	{
//	    	expectedHol.add(new Date(24,DECEMBER,year));
//	    	expectedHol.add(new Date(31,DECEMBER,year));
//    	}

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2010(final Calendar c){

		final int year = 2010;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

		expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(2,April,year));
    	expectedHol.add(new Date(5,April,year));
    	expectedHol.add(new Date(13,May,year));
    	expectedHol.add(new Date(24,May,year));

//    	if(market == Switzerland.Market.Settlement)
//    	{
//	    	expectedHol.add(new Date(24,DECEMBER,year));
//	    	expectedHol.add(new Date(31,DECEMBER,year));
//    	}

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2011(final Calendar c){

		final int year = 2011;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

    	expectedHol.add(new Date(22,April,year));
    	expectedHol.add(new Date(25,April,year));
    	expectedHol.add(new Date(2,June,year));
    	expectedHol.add(new Date(13,June,year));
    	expectedHol.add(new Date(1,August,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}

	public void testSwitzerland2012(final Calendar c) {
		final int year = 2012;
		QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");

		final List<Date> expectedHol = new Vector<Date>();

		// expectedHol.add(DateFactory.getDateUtil().getDate(1,JANUARY,year)); --> Sunday
		expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(6,April,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(17,May,year));
    	expectedHol.add(new Date(28,May,year));
    	expectedHol.add(new Date(1,August,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

//    	if(market == Switzerland.Market.Settlement)
//    	{
//	    	expectedHol.add(new Date(24,DECEMBER,year));
//	    	expectedHol.add(new Date(31,DECEMBER,year));
//    	}

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, c, year);
	}
}
