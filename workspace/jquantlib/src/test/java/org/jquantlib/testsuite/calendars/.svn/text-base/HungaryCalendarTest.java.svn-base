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
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
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
import org.jquantlib.time.calendars.Hungary;
import org.junit.Test;

/**
 * @author Jia Jia
 *
 */
public class HungaryCalendarTest {

    private final Calendar c;

    public HungaryCalendarTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
		c	= new Hungary();
    }

    // 2004 - leap-year in the past
    @Test
    public void testHungaryYear2004() {
        final int year = 2004;
        QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(15, March, year));
        expectedHol.add(new Date(12, April, year));
        expectedHol.add(new Date(31, May, year));
        expectedHol.add(new Date(20, August, year));
        expectedHol.add(new Date(1, November, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);

    }

    @Test
    public void testHungaryYear2007() {

        final int year = 2007;
        QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(15, March, year));
        expectedHol.add(new Date(9, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(28, May, year));
        expectedHol.add(new Date(20, August, year));
        expectedHol.add(new Date(23, October, year));
        expectedHol.add(new Date(1, November, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);
    }

    @Test
    public void testHungaryYear2008() {
        final int year = 2008;
        QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(24, March, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(12, May, year));
        expectedHol.add(new Date(20, August, year));
        expectedHol.add(new Date(23, October, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);
    }

    @Test
    public void testHungaryYear2009() {

        final int year = 2009;
        QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(13, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(1, June, year));
        expectedHol.add(new Date(20, August, year));
        expectedHol.add(new Date(23, October, year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);

    }

    @Test
    public void testHungaryYear2012() {

        final int year = 2012;
        QL.info("Testing " + c.name() + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(15, March, year));
        expectedHol.add(new Date(9, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(28, May, year));
        expectedHol.add(new Date(20, August, year));
        expectedHol.add(new Date(23, October, year));
        expectedHol.add(new Date(1, November, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);

    }

}
