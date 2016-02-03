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
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Taiwan;
import org.junit.Test;

/**
 * @author Renjith Nair
 *
 *
 */

public class TaiwanCalendarTest {

    private Calendar exchange = null;
	private final List<Date> expectedHol = null;

	public TaiwanCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
		this.exchange = new Taiwan(Taiwan.Market.TSEC);
	}


    // 2002 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2002()
    {
       	final int year = 2002;
        QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(11,February,year));
    	expectedHol.add(new Date(12,February,year));
    	expectedHol.add(new Date(13,February,year));
    	expectedHol.add(new Date(14,February,year));
    	expectedHol.add(new Date(15,February,year));
    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(5,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(10,October,year));

    	// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2003 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2003()
    {
       	final int year = 2003;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(31,January,year));
    	expectedHol.add(new Date(3,February,year));
    	expectedHol.add(new Date(4,February,year));
    	expectedHol.add(new Date(5,February,year));
    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(4,June,year));
    	expectedHol.add(new Date(11,September,year));
    	expectedHol.add(new Date(10,October,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }


	// 2004 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2004()
    {
       	final int year = 2004;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(21,January,year));
    	expectedHol.add(new Date(22,January,year));
    	expectedHol.add(new Date(23,January,year));
    	expectedHol.add(new Date(26,January,year));
    	expectedHol.add(new Date(22,June,year));
    	expectedHol.add(new Date(28,September,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2005 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2005()
    {
       	final int year = 2005;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

//    	expectedHol.add(new Date(4,FEBRUARY,year));
    	expectedHol.add(new Date(7,February,year));
    	expectedHol.add(new Date(8,February,year));
    	expectedHol.add(new Date(9,February,year));
    	expectedHol.add(new Date(10,February,year));
    	expectedHol.add(new Date(11,February,year));
    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(5,April,year));
    	expectedHol.add(new Date(2,May,year));
    	expectedHol.add(new Date(10,October,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2006 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2006()
    {
       	final int year = 2006;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(30,January,year));
    	expectedHol.add(new Date(31,January,year));
    	expectedHol.add(new Date(1,February,year));
    	expectedHol.add(new Date(2,February,year));
    	expectedHol.add(new Date(3,February,year));
    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(5,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(31,May,year));
    	expectedHol.add(new Date(6,October,year));
    	expectedHol.add(new Date(10,October,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2007 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2007()
    {
       	final int year = 2007;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(19,February,year));
    	expectedHol.add(new Date(20,February,year));
    	expectedHol.add(new Date(21,February,year));
    	expectedHol.add(new Date(22,February,year));
    	expectedHol.add(new Date(23,February,year));
    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(5,April,year));
    	expectedHol.add(new Date(6,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(18,June,year));
    	expectedHol.add(new Date(19,June,year));
    	expectedHol.add(new Date(24,September,year));
    	expectedHol.add(new Date(25,September,year));
    	expectedHol.add(new Date(10,October,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2008 - Current Year
	@Test
    public void testTaiwanTSEHolidaysYear2008()
    {
       	final int year = 2008;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(4,February,year));
    	expectedHol.add(new Date(5,February,year));
    	expectedHol.add(new Date(6,February,year));
    	expectedHol.add(new Date(7,February,year));
    	expectedHol.add(new Date(8,February,year));
    	expectedHol.add(new Date(11,February,year));
    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(4,April,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(10,October,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2009 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2009()
    {
       	final int year = 2009;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(1,May,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2010 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2010()
    {
       	final int year = 2010;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2011 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2011()
    {
       	final int year = 2011;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(10,October,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2012 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2012()
    {
       	final int year = 2012;
    	QL.info("Testing " + Taiwan.Market.TSEC + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(28,February,year));
    	expectedHol.add(new Date(1,May,year));
    	expectedHol.add(new Date(10,October,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

}
