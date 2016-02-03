/*
 Copyright (C) 2008

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
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;

import java.util.List;
import java.util.Vector;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Indonesia;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jia Jia
 */

public class IndonesiaCalendarTest {

    private Calendar c = null;

    public IndonesiaCalendarTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
    }

    @Before
    public void setUp() {
        c = new Indonesia(Indonesia.Market.BEJ);
    }

    // Holiday figures taken from http://www.idx.co.id/MainMenu/Trading/TradingHoliday/tabid/85/language/en-US/Default.aspx
    @Test
    public void testIndonesiaYear2009() {
        final int year = 2009;
        QL.info("Testing Indonesia's holiday list for the year " + year + "...");
        final List<Date> expectedHol = new Vector<Date>();
        // New Year
        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(26, January, year));
        expectedHol.add(new Date(9, March, year));
        expectedHol.add(new Date(26, March, year));
        expectedHol.add(new Date(10, April, year));
        // Ascension Thursday
        expectedHol.add(new Date(21, May, year));

        // Waisak
        expectedHol.add(new Date(20, July, year));

        // Independence Day
        expectedHol.add(new Date(17, August, year));
        expectedHol.add(new Date(18, September, year));
        expectedHol.add(new Date(21, September, year));
        expectedHol.add(new Date(22, September, year));
        expectedHol.add(new Date(23, September, year));

        // Ied Adha
        expectedHol.add(new Date(27, November, year));

        // Christmas
        expectedHol.add(new Date(18, December, year));
        expectedHol.add(new Date(24, December, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(31, December, year));
        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);
    }

    @Test
    public void testIndonesiaYear2008() {
        final int year = 2008;
        QL.info("Testing Indonesia's holiday list for the year " + year + "...");
        final List<Date> expectedHol = new Vector<Date>();
        // New Year
        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(10, January, year));
        expectedHol.add(new Date(11, January, year));
        expectedHol.add(new Date(7, February, year));
        expectedHol.add(new Date(8, February, year));
        expectedHol.add(new Date(7, March, year));
        expectedHol.add(new Date(20, March, year));
        expectedHol.add(new Date(21, March, year));
        // Ascension Thursday
        expectedHol.add(new Date(1, May, year));

        // National leaves
        expectedHol.add(new Date(20, May, year));

        // Waisak
        expectedHol.add(new Date(30, July, year));

        // Independence Day
        expectedHol.add(new Date(18, August, year));
        expectedHol.add(new Date(30, September, year));

        // National leaves
        expectedHol.add(new Date(1, October, year));
        expectedHol.add(new Date(2, October, year));
        expectedHol.add(new Date(3, October, year));


        // Ied Adha
        expectedHol.add(new Date(8, December, year));

        // Christmas
        expectedHol.add(new Date(25, December, year));

        expectedHol.add(new Date(29, December, year));
        expectedHol.add(new Date(31, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);
    }

 // 2007
    @Test
    public void testIndonesiaYear2007() {
        final int year = 2007;
        QL.info("Testing Indonesia's holiday list for the year " + year + "...");
        final List<Date> expectedHol = new Vector<Date>();
        // New Year
        expectedHol.add(new Date(1, January, year));

        // Nyepi
        expectedHol.add(new Date(19, March, year));

        // Good Friday
        expectedHol.add(new Date(6, April, year));

        // Ascension Thursday
        expectedHol.add(new Date(17, May, year));

        // National leaves
        expectedHol.add(new Date(18, May, year));

        // Waisak
        expectedHol.add(new Date(1, June, year));

        // Independence Day
        expectedHol.add(new Date(17, August, year));

        // National leaves
        expectedHol.add(new Date(12, October, year));
        expectedHol.add(new Date(15, October, year));
        expectedHol.add(new Date(16, October, year));
        expectedHol.add(new Date(24, October, year));

        // Ied Adha
        expectedHol.add(new Date(20, December, year));

        // Christmas
        expectedHol.add(new Date(25, December, year));

        //New holidays as per QL0.9.7
        //Islamic New year
        expectedHol.add(new Date(10, January, year));
        expectedHol.add(new Date(11, January, year));
        //Chinese  New year
        expectedHol.add(new Date(7, February, year));
        expectedHol.add(new Date(8, February, year));

        // Saka's New Year
        expectedHol.add(new Date(7, March, year));
        // Birthday of the prophet Muhammad SAW
        expectedHol.add(new Date(20, March, year));
        // Isra' Mi'raj of the prophet Muhammad SAW, Sunday
        expectedHol.add(new Date(30, July, year));

        // National leaves
        expectedHol.add(new Date(1, October, year));
        expectedHol.add(new Date(2,  October, year));
        expectedHol.add(new Date(3,  October, year));

        // New Year's Eve
        expectedHol.add(new Date(31, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);
    }

    // 2006
    @Test
    public void testIndonesiaYear2006() {
        final int year = 2006;
        QL.info("Testing Indonesia's holiday list for the year " + year + "...");
        final List<Date> expectedHol = new Vector<Date>();
        // New Year -- weekend in yr 2006
        // expectedHol.add(new Date(1,JANUARY,year));

        // Idul Adha
        expectedHol.add(new Date(10, January, year));

        // Moslem's New Year Day
        expectedHol.add(new Date(31, January, year));

        // Nyepi
        expectedHol.add(new Date(30, March, year));

        // Birthday of Prophet Muhammad SAW
        expectedHol.add(new Date(10, April, year));

        // Good Friday
        expectedHol.add(new Date(14, April, year));

        // Ascension Thursday
        expectedHol.add(new Date(25, May, year));

        // Independence Day
        expectedHol.add(new Date(17, August, year));

        // Ascension of Prophet Muhammad SAW
        expectedHol.add(new Date(21, August, year));

        // National leaves
        expectedHol.add(new Date(23, October, year));

        // Idul Fitri
        expectedHol.add(new Date(24, October, year));
        expectedHol.add(new Date(25, October, year));

        // National Leaves
        expectedHol.add(new Date(26, October, year));
        expectedHol.add(new Date(27, October, year));

        // Christmas
        expectedHol.add(new Date(25, December, year));
        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);
    }

    // 2005
    @Test
    public void testIndonesiaYear2005() {
        final int year = 2005;
        QL.info("Testing Indonesia's holiday list for the year " + year + "...");
        final List<Date> expectedHol = new Vector<Date>();
        // New Year -- weekend in yr 2005
        // expectedHol.add(new Date(1,JANUARY,year));

        // Idul Adha
        expectedHol.add(new Date(21, January, year));

        // Imlek
        expectedHol.add(new Date(9, February, year));

        // Moslem's New Year Day
        expectedHol.add(new Date(10, February, year));

        // Nyepi
        expectedHol.add(new Date(11, March, year));

        // Good Friday
        expectedHol.add(new Date(25, March, year));

        // Birthday of Prophet Muhammad SAW
        expectedHol.add(new Date(22, April, year));

        // Ascension Thursday
        expectedHol.add(new Date(5, May, year));

        // Waisak
        expectedHol.add(new Date(24, May, year));

        // Independence Day
        expectedHol.add(new Date(17, August, year));

        // Ascension of Prophet Muhammad SAW
        expectedHol.add(new Date(2, September, year));

        // National Leaves
        expectedHol.add(new Date(2, November, year));

        // Idul Fitri
        expectedHol.add(new Date(3, November, year));
        expectedHol.add(new Date(4, November, year));

        // National Leaves
        expectedHol.add(new Date(7, November, year));
        expectedHol.add(new Date(8, November, year));

        // Christmas -- weekend in yr 2005
        // expectedHol.add(new Date(25,DECEMBER,year));

        // National Leaves
        expectedHol.add(new Date(26, December, year));
        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, c, year);
    }

    @After
    public void destroy() {
        c = null;
    }
}
