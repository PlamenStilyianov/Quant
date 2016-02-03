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
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Ukraine;
import org.junit.Test;

/**
 * @author Renjith Nair
 *
 *
 */

public class UkraineCalendarTest {

    private final Calendar exchange;

	public UkraineCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
        this.exchange = new Ukraine(Ukraine.Market.USE);
	}

    // 2004 - year in the past and leap year
	@Test
    public void testUkraineUSEHolidaysYear2004()
    {
       	final int year = 2004;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(7,January,year));
    	expectedHol.add(new Date(8,March,year));
    	expectedHol.add(new Date(12,April,year));
    	expectedHol.add(new Date(3,May,year));
    	expectedHol.add(new Date(10,May,year));
    	expectedHol.add(new Date(31,May,year));
    	expectedHol.add(new Date(28,June,year));
    	expectedHol.add(new Date(24,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2005 - year in the past and leap year
	@Test
    public void testUkraineUSEHolidaysYear2005()
    {
       	final int year = 2005;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(3,January,year));// New Year's Day 
    	expectedHol.add(new Date(7,January,year));//Orthodox Christmas 
    	expectedHol.add(new Date(8,March,year)); //Internation Women's day
    	expectedHol.add(new Date(2,May,year));  // Labor days 
    	expectedHol.add(new Date(9,May,year));  // Victory Day
    	expectedHol.add(new Date(20,June,year));//Zahid: Changed from 28 to 20
    	expectedHol.add(new Date(28,June,year)); //Zahid: Constitution day
    	expectedHol.add(new Date(24,August,year)); //Independence day

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2006 - year in the past and leap year
	@Test
    public void testUkraineUSEHolidaysYear2006()
    {
       	final int year = 2006;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(9,January,year));
    	expectedHol.add(new Date(8,March,year));
    	expectedHol.add(new Date(24,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(2,May,year));
    	expectedHol.add(new Date(9,May,year));
    	expectedHol.add(new Date(12,June,year));
    	expectedHol.add(new Date(28,June,year));
    	expectedHol.add(new Date(24,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2007 - year in the past and leap year
	@Test
    public void testUkraineUSEHolidaysYear2007()
    {
       	final int year = 2007;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(8,January,year));
    	expectedHol.add(new Date(8,March,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(2,May,year));
    	expectedHol.add(new Date(9,May,year));
    	expectedHol.add(new Date(28,May,year));
    	expectedHol.add(new Date(28,June,year));
    	expectedHol.add(new Date(24,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }


	 // 2008 - Current Year
	@Test
    public void testUkraineUSEHolidaysYear2008()
    {
       	final int year = 2008;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(7,January,year));
    	expectedHol.add(new Date(10,March,year));
//    	expectedHol.add(new Date(8,MARCH,year));
    	expectedHol.add(new Date(28,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(2,May,year));
    	expectedHol.add(new Date(9,May,year));
    	expectedHol.add(new Date(16,June,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	 // 2009 - Future Year
	@Test
    public void testUkraineUSEHolidaysYear2009()
    {
       	final int year = 2009;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(7,January,year));
    	expectedHol.add(new Date(9,March,year));
    	expectedHol.add(new Date(20,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(11,May,year));
    	expectedHol.add(new Date(8,June,year));
    	expectedHol.add(new Date(24,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2010 - Future Year
	@Test
    public void testUkraineUSEHolidaysYear2010()
    {
       	final int year = 2010;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(7,January,year));
    	expectedHol.add(new Date(8,March,year));
    	expectedHol.add(new Date(5,April,year));
    	expectedHol.add(new Date(3,May,year));
    	expectedHol.add(new Date(10,May,year));
    	expectedHol.add(new Date(24,May,year));
    	expectedHol.add(new Date(28,June,year));
    	expectedHol.add(new Date(24,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2011 - Future Year
	@Test
    public void testUkraineUSEHolidaysYear2011()
    {
       	final int year = 2011;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(3,January,year));
    	expectedHol.add(new Date(7,January,year));
    	expectedHol.add(new Date(8,March,year));
    	expectedHol.add(new Date(25,April,year));
    	expectedHol.add(new Date(2,May,year));
    	expectedHol.add(new Date(9,May,year));
    	expectedHol.add(new Date(13,June,year));
    	expectedHol.add(new Date(28,June,year));
    	expectedHol.add(new Date(24,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

	// 2012 - Future Year
	@Test
    public void testUkraineUSEHolidaysYear2012()
    {
       	final int year = 2012;
    	QL.info("Testing " + Ukraine.Market.USE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(2,January,year));
    	expectedHol.add(new Date(9,January,year));
    	expectedHol.add(new Date(8,March,year));
    	expectedHol.add(new Date(16,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(2,May,year));
    	expectedHol.add(new Date(9,May,year));
    	expectedHol.add(new Date(4,June,year));
    	expectedHol.add(new Date(28,June,year));
    	expectedHol.add(new Date(24,August,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt= new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

}
