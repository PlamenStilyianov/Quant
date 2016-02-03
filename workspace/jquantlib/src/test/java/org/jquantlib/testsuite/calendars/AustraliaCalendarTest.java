/*
 Copyright (C) 2008 Tim Swetonic

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
import static org.jquantlib.time.Month.October;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Australia;
import org.junit.Test;

/**
 * @author Tim Swetonic
 * @author Jia Jia
 *
 */


public class AustraliaCalendarTest {

	private final Calendar exchange;

	public AustraliaCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	    exchange = new Australia();//.getCalendar(Australia.Market.SETTLEMENT);
	}

    // 2008 - current year
    @Test
	public void testAustraliaYear2008() {
      	final int year = 2008;
      	QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(28,January,year));

    	//good friday
    	expectedHol.add(new Date(21,March,year));
    	//easter monday
    	expectedHol.add(new Date(24,March,year));

    	expectedHol.add(new Date(25,April,year));
    	expectedHol.add(new Date(9,June,year));
    	expectedHol.add(new Date(4,August,year));
        expectedHol.add(new Date(6,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2004() {
        final int year = 2004;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(26,January,year));
        expectedHol.add(new Date(9,April,year));
        expectedHol.add(new Date(12,April,year));
        expectedHol.add(new Date(26,April,year));
        expectedHol.add(new Date(14,June,year));
        expectedHol.add(new Date(2,August,year));
        expectedHol.add(new Date(4,October,year));
        expectedHol.add(new Date(27,December,year));
        expectedHol.add(new Date(28,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2005() {
        final int year = 2005;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(26,January,year));
        expectedHol.add(new Date(25,March,year));

        //good friday
        expectedHol.add(new Date(28,March,year));

        expectedHol.add(new Date(25,April,year));
        expectedHol.add(new Date(13,June,year));
        expectedHol.add(new Date(1,August,year));
        expectedHol.add(new Date(3,October,year));
        expectedHol.add(new Date(26,December,year));
        expectedHol.add(new Date(27,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2006() {
        final int year = 2006;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(26,January,year));
        expectedHol.add(new Date(14,April,year));
        expectedHol.add(new Date(17,April,year));
        expectedHol.add(new Date(25,April,year));
        expectedHol.add(new Date(12,June,year));
        expectedHol.add(new Date(7,August,year));
        expectedHol.add(new Date(2,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2007() {
        final int year = 2007;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(26,January,year));

        //good friday
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(9,April,year));
        expectedHol.add(new Date(25,April,year));
        expectedHol.add(new Date(11,June,year));
        expectedHol.add(new Date(6,August,year));
        expectedHol.add(new Date(1,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2009() {
        final int year = 2009;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(26,January,year));
        expectedHol.add(new Date(10,April,year));
        expectedHol.add(new Date(13,April,year));
        expectedHol.add(new Date(8,June,year));
        expectedHol.add(new Date(3,August,year));
        expectedHol.add(new Date(5,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(28,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2010() {
        final int year = 2010;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(26,January,year));
        expectedHol.add(new Date(2,April,year));
        expectedHol.add(new Date(5,April,year));
        expectedHol.add(new Date(26,April,year));
        expectedHol.add(new Date(14,June,year));
        expectedHol.add(new Date(2,August,year));
        expectedHol.add(new Date(4,October,year));
        expectedHol.add(new Date(27,December,year));
        expectedHol.add(new Date(28,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2011() {
        final int year = 2011;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(26,January,year));
        expectedHol.add(new Date(22,April,year));
        expectedHol.add(new Date(25,April,year));
        expectedHol.add(new Date(13,June,year));
        expectedHol.add(new Date(1,August,year));
        expectedHol.add(new Date(3,October,year));
        expectedHol.add(new Date(26,December,year));
        expectedHol.add(new Date(27,December,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testAustraliaYear2012() {
        final int year = 2012;
        QL.info("Testing Australia holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(26,January,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(9,April,year));
        expectedHol.add(new Date(25,April,year));
        expectedHol.add(new Date(11,June,year));
        expectedHol.add(new Date(6,August,year));
        expectedHol.add(new Date(1,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

}

