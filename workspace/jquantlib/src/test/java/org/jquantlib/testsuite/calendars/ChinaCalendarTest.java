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
import static org.jquantlib.time.Month.December;
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
import org.jquantlib.time.calendars.China;
import org.junit.Test;

/**
 * @author Tim Swetonic
 * @author Jia Jia
 * @author Renjith Nair
 *
 */

public class ChinaCalendarTest {

    private final Calendar exchange;

	public ChinaCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
        exchange = new China(China.Market.SSE);
	}

    @Test
    public void testChinaSSEYear2004() {
        final int year = 2004;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(19, January, year));
        expectedHol.add(new Date(20, January, year));
        expectedHol.add(new Date(21, January, year));
        expectedHol.add(new Date(22, January, year));
        expectedHol.add(new Date(23, January, year));
        expectedHol.add(new Date(26, January, year));
        expectedHol.add(new Date(27, January, year));
        expectedHol.add(new Date(28, January, year));
        expectedHol.add(new Date(3, May, year));
        expectedHol.add(new Date(4, May, year));
        expectedHol.add(new Date(5, May, year));
        expectedHol.add(new Date(6, May, year));
        expectedHol.add(new Date(7, May, year));
        expectedHol.add(new Date(1, October, year));
        expectedHol.add(new Date(4, October, year));
        expectedHol.add(new Date(5, October, year));
        expectedHol.add(new Date(6, October, year));
        expectedHol.add(new Date(7, October, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testChinaSSEYear2005() {
        final int year = 2005;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(3, January, year));
        expectedHol.add(new Date(7, February, year));
        expectedHol.add(new Date(8, February, year));
        expectedHol.add(new Date(9, February, year));
        expectedHol.add(new Date(10, February, year));
        expectedHol.add(new Date(11, February, year));
        expectedHol.add(new Date(14, February, year));
        expectedHol.add(new Date(15, February, year));
        expectedHol.add(new Date(2, May, year));
        expectedHol.add(new Date(3, May, year));
        expectedHol.add(new Date(4, May, year));
        expectedHol.add(new Date(5, May, year));
        expectedHol.add(new Date(6, May, year));
        expectedHol.add(new Date(3, October, year));
        expectedHol.add(new Date(4, October, year));
        expectedHol.add(new Date(5, October, year));
        expectedHol.add(new Date(6, October, year));
        expectedHol.add(new Date(7, October, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testChinaSSEYear2006() {
        final int year = 2006;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(3, January, year));
        expectedHol.add(new Date(26, January, year));
        expectedHol.add(new Date(27, January, year));
        expectedHol.add(new Date(30, January, year));
        expectedHol.add(new Date(31, January, year));
        expectedHol.add(new Date(1, February, year));
        expectedHol.add(new Date(2, February, year));
        expectedHol.add(new Date(3, February, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(2, May, year));
        expectedHol.add(new Date(3, May, year));
        expectedHol.add(new Date(4, May, year));
        expectedHol.add(new Date(5, May, year));
        expectedHol.add(new Date(2, October, year));
        expectedHol.add(new Date(3, October, year));
        expectedHol.add(new Date(4, October, year));
        expectedHol.add(new Date(5, October, year));
        expectedHol.add(new Date(6, October, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testChinaSSEYear2007() {
        final int year = 2007;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(3, January, year));
        expectedHol.add(new Date(19, February, year));
        expectedHol.add(new Date(20, February, year));
        expectedHol.add(new Date(21, February, year));
        expectedHol.add(new Date(22, February, year));
        expectedHol.add(new Date(23, February, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(2, May, year));
        expectedHol.add(new Date(3, May, year));
        expectedHol.add(new Date(4, May, year));
        expectedHol.add(new Date(7, May, year));
        expectedHol.add(new Date(1, October, year));
        expectedHol.add(new Date(2, October, year));
        expectedHol.add(new Date(3, October, year));
        expectedHol.add(new Date(4, October, year));
        expectedHol.add(new Date(5, October, year));
        // Interesting Fact
        //31 Dec 2007 is included as holiday in 2008 list of holidays :)
        expectedHol.add(new Date(31, December, year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    // 2008 - current year
    @Test
    public void testChinaSSEYear2008() {
        final int year = 2008;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(6, February, year));
        expectedHol.add(new Date(7, February, year));
        expectedHol.add(new Date(8, February, year));
        expectedHol.add(new Date(11, February, year));
        expectedHol.add(new Date(12, February, year));
        expectedHol.add(new Date(4, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(2, May, year));
        expectedHol.add(new Date(9, June, year));
        expectedHol.add(new Date(15, September, year));
        expectedHol.add(new Date(29, September, year));
        expectedHol.add(new Date(30, September, year));
        expectedHol.add(new Date(1, October, year));
        expectedHol.add(new Date(2, October, year));
        expectedHol.add(new Date(3, October, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testChinaSSEYear2009() {
        final int year = 2009;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(26, January, year));
        expectedHol.add(new Date(27, January, year));
        expectedHol.add(new Date(28, January, year));
        expectedHol.add(new Date(29, January, year));
        expectedHol.add(new Date(30, January, year));
        expectedHol.add(new Date(6, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(28, May, year));
        expectedHol.add(new Date(29, May, year));
        expectedHol.add(new Date(1, October, year));
        expectedHol.add(new Date(2, October, year));
        expectedHol.add(new Date(5, October, year));
        expectedHol.add(new Date(6, October, year));
        expectedHol.add(new Date(7, October, year));
        expectedHol.add(new Date(8, October, year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testChinaSSEYear2010() {
        final int year = 2010;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testChinaSSEYear2011() {
        final int year = 2011;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testChinaSSEYear2012() {
        final int year = 2012;
        QL.info("Testing China holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }
}
