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
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Turkey;
import org.junit.Test;

/**
 * @author Renjith Nair
 *
 *
 */

public class TurkeyCalendarTest {

    private final Calendar exchange;

	public TurkeyCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
        this.exchange = new Turkey();
	}

    // 2004 - year in the past
	@Test
    public void testTurkeyISEHolidaysYear2004()
    {
       	final int year = 2004;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(2,February,year));
    	expectedHol.add(new Date(3,February,year));
    	expectedHol.add(new Date(4,February,year));
    	expectedHol.add(new Date(23,April,year));
    	expectedHol.add(new Date(19,May,year));
    	expectedHol.add(new Date(30,August,year));
    	expectedHol.add(new Date(29,October,year));
    	expectedHol.add(new Date(15,November,year));
    	expectedHol.add(new Date(16,November,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2005 - year in the past
	@Test
    public void testTurkeyISEHolidaysYear2005()
    {
       	final int year = 2005;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(19,January,year));
    	expectedHol.add(new Date(20,January,year));
    	expectedHol.add(new Date(21,January,year));
    	expectedHol.add(new Date(19,May,year));
    	expectedHol.add(new Date(30,August,year));
    	expectedHol.add(new Date(2,November,year));
    	expectedHol.add(new Date(3,November,year));
    	expectedHol.add(new Date(4,November,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2006 - year in the past
	@Test
    public void testTurkeyISEHolidaysYear2006()
    {
       	final int year = 2006;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(9,January,year));
    	expectedHol.add(new Date(10,January,year));
    	expectedHol.add(new Date(11,January,year));
    	expectedHol.add(new Date(12,January,year));
    	expectedHol.add(new Date(13,January,year));
    	expectedHol.add(new Date(19,May,year));
    	expectedHol.add(new Date(30,August,year));
    	expectedHol.add(new Date(23,October,year));
    	expectedHol.add(new Date(24,October,year));
    	expectedHol.add(new Date(25,October,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2007 - year in the past
	@Test
    public void testTurkeyISEHolidaysYear2007()
    {
       	final int year = 2007;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(3,January,year));
    	expectedHol.add(new Date(4,January,year));
    	expectedHol.add(new Date(23,April,year));
    	expectedHol.add(new Date(30,August,year));
    	expectedHol.add(new Date(11,October,year));
    	expectedHol.add(new Date(12,October,year));
    	expectedHol.add(new Date(29,October,year));
    	expectedHol.add(new Date(19,December,year));
    	expectedHol.add(new Date(20,December,year));
    	expectedHol.add(new Date(21,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2008 - Current Year
	@Test
    public void testTurkeyISEHolidaysYear2008()
    {
       	final int year = 2008;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(23,April,year));
    	expectedHol.add(new Date(19,May,year));
    	expectedHol.add(new Date(29,September,year));
    	expectedHol.add(new Date(30,September,year));
    	expectedHol.add(new Date(1,October,year));
    	expectedHol.add(new Date(2,October,year));
    	expectedHol.add(new Date(29,October,year));
    	expectedHol.add(new Date(8,December,year));
    	expectedHol.add(new Date(9,December,year));
    	expectedHol.add(new Date(10,December,year));
    	expectedHol.add(new Date(11,December,year));


    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2009 - Future Year
	@Test
    public void testTurkeyISEHolidaysYear2009()
    {
       	final int year = 2009;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(23,April,year));
    	expectedHol.add(new Date(19,May,year));
    	expectedHol.add(new Date(29,October,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2010 - Future Year
	@Test
    public void testTurkeyISEHolidaysYear2010()
    {
       	final int year = 2010;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(23,April,year));
    	expectedHol.add(new Date(19,May,year));
    	expectedHol.add(new Date(30,August,year));
    	expectedHol.add(new Date(29,October,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2011 - Future Year
	@Test
    public void testTurkeyISEHolidaysYear2011()
    {
       	final int year = 2011;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(19,May,year));
    	expectedHol.add(new Date(30,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2012 - Future Year
	@Test
    public void testTurkeyISEHolidaysYear2012()
    {
       	final int year = 2012;
    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(23,April,year));
    	expectedHol.add(new Date(30,August,year));
    	expectedHol.add(new Date(29,October,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

}
