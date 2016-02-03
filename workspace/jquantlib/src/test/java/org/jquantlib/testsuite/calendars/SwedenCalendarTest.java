/*
 Copyright (C) 2008 Renjith Nair

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
import org.jquantlib.time.calendars.Sweden;
import org.junit.Test;

/**
 * @author Renjith Nair
 */

public class SwedenCalendarTest {

    //TODO: private final Calendar settlement;
    private final Calendar exchange;

	public SwedenCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	    //TODO: this.settlement = Sweden.getCalendar(Sweden.Market.Settlement);
	    this.exchange   = new Sweden();
	}


    // 2004 - year in the past
	@Test
    public void testSwedenSEHolidaysYear2004()
    {
       	final int year = 2004;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(6,January,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(12,April,year));
    	expectedHol.add(new Date(20,May,year));
    	expectedHol.add(new Date(31,May,year));
    	expectedHol.add(new Date(18,June,year));
    	expectedHol.add(new Date(24,December,year));
    	expectedHol.add(new Date(31,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2005 - year in the past
	@Test
    public void testSwedenSEHolidaysYear2005()
    {
       	final int year = 2005;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(6,January,year));
    	expectedHol.add(new Date(25,March,year));
    	expectedHol.add(new Date(28,March,year));
    	expectedHol.add(new Date(5,May,year));
    	expectedHol.add(new Date(16,May,year));
    	expectedHol.add(new Date(24,June,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2006 - year in the past
	@Test
    public void testSwedenSEHolidaysYear2006()
    {
       	final int year = 2006;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(6,January,year));
    	expectedHol.add(new Date(14,April,year));
    	expectedHol.add(new Date(17,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(25,May,year));
    	expectedHol.add(new Date(5,June,year));
    	expectedHol.add(new Date(23,June,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2007 - year in the past
	@Test
    public void testSwedenSEHolidaysYear2007()
    {
       	final int year = 2007;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(6,April,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(17,May,year));
    	expectedHol.add(new Date(28,May,year));
    	expectedHol.add(new Date(22,June,year));
    	expectedHol.add(new Date(24,December,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));
    	expectedHol.add(new Date(31,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2008 - Current Year
	@Test
    public void testSwedenSEHolidaysYear2008()
    {
       	final int year = 2008;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(21,March,year));
    	expectedHol.add(new Date(24,March,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(12,May,year));
    	expectedHol.add(new Date(20,June,year));
    	expectedHol.add(new Date(24,December,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));
    	expectedHol.add(new Date(31,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2009 - Future Year
	@Test
    public void testSwedenSEHolidaysYear2009()
    {
       	final int year = 2009;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(6,January,year));
    	expectedHol.add(new Date(10,April,year));
    	expectedHol.add(new Date(13,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(21,May,year));
    	expectedHol.add(new Date(1,June,year));
    	expectedHol.add(new Date(19,June,year));
    	expectedHol.add(new Date(24,December,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(31,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2010 - Future Year
	@Test
    public void testSwedenSEHolidaysYear2010()
    {
       	final int year = 2010;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(6,January,year));
    	expectedHol.add(new Date(2,April,year));
    	expectedHol.add(new Date(5,April,year));
    	expectedHol.add(new Date(13,May,year));
    	expectedHol.add(new Date(24,May,year));
    	expectedHol.add(new Date(18,June,year));
    	expectedHol.add(new Date(24,December,year));
    	expectedHol.add(new Date(31,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2011 - Future Year
	@Test
    public void testSwedenSEHolidaysYear2011()
    {
       	final int year = 2011;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(6,January,year));
    	expectedHol.add(new Date(22,April,year));
    	expectedHol.add(new Date(25,April,year));
    	expectedHol.add(new Date(2,June,year));
    	expectedHol.add(new Date(13,June,year));
    	expectedHol.add(new Date(24,June,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2012 - Future Year
	@Test
    public void testSwedenSEHolidaysYear2012()
    {
       	final int year = 2012;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(6,January,year));
    	expectedHol.add(new Date(6,April,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(17,May,year));
    	expectedHol.add(new Date(28,May,year));
    	expectedHol.add(new Date(22,June,year));
    	expectedHol.add(new Date(24,December,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));
    	expectedHol.add(new Date(31,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

}
