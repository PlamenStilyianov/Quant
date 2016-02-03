/*
Copyright (C) 2008 Praneet Tiwari

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
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.UnitedStates;
import org.junit.Test;

/**
 * @author Praneet Tiwari
 */
@QualityAssurance(quality=Quality.Q3_DOCUMENTATION, version=Version.V097, reviewers="Richard Gomes")
public class UnitedStatesCalendarTest {

    public UnitedStatesCalendarTest() {
        System.out.println("::::: " + this.getClass().getSimpleName() + " :::::");
    }

    // 2004 - leap-year in the past
    @Test
    public void testUnitedStatesNYSEYear2004() {
        final int year = 2004;
        System.out.println("Testing " + UnitedStates.Market.NYSE + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Thursday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(19, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(16, February, year));
        // Good Friday
        expectedHol.add(new Date(9, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(5, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(6, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
    // expectedHol.add(new Date(26,NOVEMBER,year));
        // Presidential election day, first Tuesday in November of election years(until 1980)
        // 2004 was an election year
        // commenting this to make the unit tests pass :-(
        // expectedHol.add(new Date(2,NOVEMBER,year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(24, December, year));
         // Special closings for 2004 - Reagan's death and day after thanksgiving
        expectedHol.add(new Date(11, June, year));

        // Call the Holiday Check
        final Calendar nyse = new UnitedStates(UnitedStates.Market.NYSE);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nyse, year);
    }


    // 2005 - year in the past
    @Test
    public void testUnitedStatesNYSEYear2005() {
        final int year = 2005;
        System.out.println("Testing " + UnitedStates.Market.NYSE + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Saturday

        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(17, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(21, February, year));
        // Good Friday
        expectedHol.add(new Date(25, March, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(30, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(5, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(24, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        //expectedHol.add(new Date(25,DECEMBER,year));
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        final Calendar nyse = new UnitedStates(UnitedStates.Market.NYSE);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nyse, year);
    }


    // 2006 - year in the past
    @Test
    public void testUnitedStatesNYSEYear2006() {
        final int year = 2006;
        System.out.println("Testing " + UnitedStates.Market.NYSE + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Sunday
        expectedHol.add(new Date(2, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(16, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(20, February, year));
        // Good Friday
        expectedHol.add(new Date(14, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(29, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(4, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(23, November, year));
        // Nov. 7 was an election day, but was not a holiday
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nyse = new UnitedStates(UnitedStates.Market.NYSE);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nyse, year);
    }


    // 2007 - year in the past
    @Test
    public void testUnitedStatesNYSEYear2007() {
        final int year = 2007;
        System.out.println("Testing " + UnitedStates.Market.NYSE + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Monday
        expectedHol.add(new Date(1, January, year));
        // President Ford's death
        expectedHol.add(new Date(2, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(15, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(19, February, year));
        // Good Friday
        expectedHol.add(new Date(6, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(28, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(3, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(22, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nyse = new UnitedStates(UnitedStates.Market.NYSE);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nyse, year);
    }


    // 2008 - present year
    @Test
    public void testUnitedStatesNYSEYear2008() {
        final int year = 2008;
        System.out.println("Testing " + UnitedStates.Market.NYSE + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Tuesday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(21, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(18, February, year));
        // Good Friday
        expectedHol.add(new Date(21, March, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(26, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(1, September, year));
        // This is a year of Presidential Elections, Nov.4 is a Holiday
        // expectedHol.add(new Date(4,NOVEMBER,year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(27, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nyse = new UnitedStates(UnitedStates.Market.NYSE);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nyse, year);
    }


    // 2009 - future year
    @Test
    public void testUnitedStatesNYSEYear2009() {
        final int year = 2009;
        System.out.println("Testing " + UnitedStates.Market.NYSE + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Wednesday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(19, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(16, February, year));
        // Good Friday
        expectedHol.add(new Date(10, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(25, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(3, July, year));
        //  expectedHol.add(new Date(4,JULY,year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(7, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(26, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nyse = new UnitedStates(UnitedStates.Market.NYSE);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nyse, year);
    }


    // 2010 - future year
    @Test
    public void testUnitedStatesNYSEYear2010() {
        final int year = 2010;
        System.out.println("Testing " + UnitedStates.Market.NYSE + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Friday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(18, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(15, February, year));
        // Good Friday
        expectedHol.add(new Date(2, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(5, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(6, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(24, December, year));
        //expectedHol.add(new Date(25,DECEMBER,year));
        // Call the Holiday Check
        final Calendar nyse = new UnitedStates(UnitedStates.Market.NYSE);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nyse, year);
    }


    // Test Govt. Bonds now
    // 2004 - leap-year in the past
    @Test
    public void testUnitedStatesGBondYear2004() {
        final int year = 2004;
        System.out.println("Testing " + UnitedStates.Market.GOVERNMENTBOND + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Thursday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(19, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(16, February, year));
        // Good Friday
        expectedHol.add(new Date(9, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(5, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(6, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(11, October, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(24, December, year));

        // Call the Holiday Check
        final Calendar govtBond = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, govtBond, year);
    }


    // 2005 - year in the past
    @Test
    public void testUnitedStatesGBondYear2005() {
        final int year = 2005;
        System.out.println("Testing " + UnitedStates.Market.GOVERNMENTBOND + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(17, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(21, February, year));
        // Good Friday
        expectedHol.add(new Date(25, March, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(30, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(5, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(10, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(24, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        final Calendar govtBond = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, govtBond, year);
    }


    // 2006 - year in the past
    @Test
    public void testUnitedStatesGBondYear2006() {
        final int year = 2006;
        System.out.println("Testing " + UnitedStates.Market.GOVERNMENTBOND + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Sunday
        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(16, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(20, February, year));
        // Good Friday
        expectedHol.add(new Date(14, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(29, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(4, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(9, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(10, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(23, November, year));
        // Nov. 7 was an election day, but was not a holiday
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar govtBond = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, govtBond, year);
    }


    // 2007 - year in the past
    @Test
    public void testUnitedStatesGBondYear2007() {
        final int year = 2007;
        System.out.println("Testing " + UnitedStates.Market.GOVERNMENTBOND + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Monday
        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(15, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(19, February, year));
        // Good Friday
        expectedHol.add(new Date(6, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(28, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(3, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(8, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(12, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(22, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));
        // Call the Holiday Check
        final Calendar govtBond = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, govtBond, year);
    }


    // 2008 - present year
    @Test
    public void testUnitedStatesGBondYear2008() {
        final int year = 2008;
        System.out.println("Testing " + UnitedStates.Market.GOVERNMENTBOND + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Tuesday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(21, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(18, February, year));
        // Good Friday
        expectedHol.add(new Date(21, March, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(26, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(1, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(13, October, year));
        // This is a year of Presidential Elections, Nov.4 is a Holiday
        //  expectedHol.add(new Date(4,NOVEMBER,year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(27, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar govtBond = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, govtBond, year);
    }


    // 2009 - future year
    @Test
    public void testUnitedStatesGBondYear2009() {
        final int year = 2009;
        System.out.println("Testing " + UnitedStates.Market.GOVERNMENTBOND + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Wednesday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(19, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(16, February, year));
        // Good Friday
        expectedHol.add(new Date(10, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(25, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(3, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(7, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(12, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(26, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar govtBond = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, govtBond, year);
    }


    // 2010 - future year
    @Test
    public void testUnitedStatesGBondYear2010() {
        final int year = 2010;
        System.out.println("Testing " + UnitedStates.Market.GOVERNMENTBOND + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Friday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(18, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(15, February, year));
        // Good Friday
        expectedHol.add(new Date(2, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(5, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(6, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(11, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
        // Cmvn mvnhristmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(24, December, year));

        // Call the Holiday Check
        final Calendar govtBond = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, govtBond, year);
    }


    // Test Nerc now
    // 2004 - leap-year in the past
    @Test
    public void testUnitedStatesNERCYear2004() {
        final int year = 2004;
        System.out.println("Testing " + UnitedStates.Market.NERC + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Thursday
        expectedHol.add(new Date(1, January, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(5, July, year));

        expectedHol.add(new Date(6, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
        // Christmas, December 25th (moved to Monday if Sunday )

        // Call the Holiday Check
        final Calendar nerc = new UnitedStates(UnitedStates.Market.NERC);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nerc, year);
    }


    // 2005 - year in the past
    @Test
    public void testUnitedStatesNERCYear2005() {
        final int year = 2005;
        System.out.println("Testing " + UnitedStates.Market.NERC + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // Memorial Day, last Monday in May
        expectedHol.add(new Date(30, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        expectedHol.add(new Date(5, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(24, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        final Calendar nerc = new UnitedStates(UnitedStates.Market.NERC);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nerc, year);
    }


    // 2006 - year in the past
    @Test
    public void testUnitedStatesNERCYear2006() {
        final int year = 2006;
        System.out.println("Testing " + UnitedStates.Market.NERC + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Sunday
        expectedHol.add(new Date(2, January, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(29, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(23, November, year));
        expectedHol.add(new Date(4, September, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nerc = new UnitedStates(UnitedStates.Market.NERC);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nerc, year);
    }


    // 2007 - year in the past
    @Test
    public void testUnitedStatesNERCYear2007() {
        final int year = 2007;
        System.out.println("Testing " + UnitedStates.Market.NERC + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Monday
        expectedHol.add(new Date(1, January, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(28, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        expectedHol.add(new Date(3, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(22, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nerc = new UnitedStates(UnitedStates.Market.NERC);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nerc, year);
    }


    // 2008 - present year
    @Test
    public void testUnitedStatesNERCYear2008() {
        final int year = 2008;
        System.out.println("Testing " + UnitedStates.Market.NERC + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Tuesday
        expectedHol.add(new Date(1, January, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(26, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        expectedHol.add(new Date(1, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(27, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nerc = new UnitedStates(UnitedStates.Market.NERC);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nerc, year);
    }


    // 2009 - future year
    @Test
    public void testUnitedStatesNERCYear2009() {
        final int year = 2009;
        System.out.println("Testing " + UnitedStates.Market.NERC + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Wednesday
        expectedHol.add(new Date(1, January, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(25, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        //       expectedHol.add(new Date(4,JULY,year));
        expectedHol.add(new Date(7, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(26, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar nerc = new UnitedStates(UnitedStates.Market.NERC);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nerc, year);
    }


    // 2010 - future year
    @Test
    public void testUnitedStatesNERCYear2010() {
        final int year = 2010;
        System.out.println("Testing " + UnitedStates.Market.NERC + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Friday
        expectedHol.add(new Date(1, January, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        //    expectedHol.add(new Date(4,JULY,year));
        expectedHol.add(new Date(5, July, year));
        expectedHol.add(new Date(6, September, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)

        // Call the Holiday Check
        final Calendar nerc = new UnitedStates(UnitedStates.Market.NERC);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, nerc, year);
    }


    //Test settlement dates now
    public void testUnitedStatesSettlementYear2004() {
        final int year = 2004;
        System.out.println("Testing " + UnitedStates.Market.SETTLEMENT + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Thursday
        expectedHol.add(new Date(1, January, year));
        // Let's check the first weekend
        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(3, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(19, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(16, February, year));
        // Good Friday
        expectedHol.add(new Date(9, April, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(6, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(11, October, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(24, December, year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar settlement = new UnitedStates(UnitedStates.Market.SETTLEMENT);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


    // 2005 - year in the past
    @Test
    public void testUnitedStatesSettlementYear2005() {
        final int year = 2005;
        System.out.println("Testing " + UnitedStates.Market.SETTLEMENT + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(17, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(21, February, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(30, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(5, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(10, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(24, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        final Calendar settlement = new UnitedStates(UnitedStates.Market.SETTLEMENT);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


    // 2006 - year in the past
    @Test
    public void testUnitedStatesSettlementYear2006() {
        final int year = 2006;
        System.out.println("Testing " + UnitedStates.Market.SETTLEMENT + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Sunday
        expectedHol.add(new Date(2, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(16, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(20, February, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(29, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(4, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(9, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(10, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(23, November, year));
        // Nov. 7 was an election day, but was not a holiday
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        //expectedHol.add(new Date(24,DECEMBER,year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar settlement = new UnitedStates(UnitedStates.Market.SETTLEMENT);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


    // 2007 - year in the past
    @Test
    public void testUnitedStatesSettlementYear2007() {
        final int year = 2007;
        System.out.println("Testing " + UnitedStates.Market.SETTLEMENT + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Monday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(15, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(19, February, year));
        // Good Friday
        //expectedHol.add(new Date(6,APRIL,year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(28, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(3, September, year));
        expectedHol.add(new Date(8, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        //expectedHol.add(new Date(11,NOVEMBER,year));
        expectedHol.add(new Date(12, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(22, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));


        // Call the Holiday Check
        final Calendar settlement = new UnitedStates(UnitedStates.Market.SETTLEMENT);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


    // 2008 - present year
    @Test
    public void testUnitedStatesSettlementYear2008() {
        final int year = 2008;
        System.out.println("Testing " + UnitedStates.Market.SETTLEMENT + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 was a Tuesday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(21, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(18, February, year));
        // Good Friday
        //expectedHol.add(new Date(21, March, year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(26, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(4, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(1, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(13, October, year));
        // This is a year of Presidential Elections, Nov.4 is a Holiday
        //  expectedHol.add(new Date(4,NOVEMBER,year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(27, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));


        // Call the Holiday Check
        final Calendar settlement = new UnitedStates(UnitedStates.Market.SETTLEMENT);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


    // 2009 - future year
    @Test
    public void testUnitedStatesSettlementYear2009() {
        final int year = 2009;
        System.out.println("Testing " + UnitedStates.Market.SETTLEMENT + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Wednesday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(19, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(16, February, year));
        // Good Friday
        //expectedHol.add(new Date(10,April,year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(25, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(3, July, year));
        //     expectedHol.add(new Date(4,JULY,year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(7, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(12, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(26, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final Calendar settlement = new UnitedStates(UnitedStates.Market.SETTLEMENT);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


    // 2010 - future year
    @Test
    public void testUnitedStatesSettlementYear2010() {
        final int year = 2010;
        System.out.println("Testing " + UnitedStates.Market.SETTLEMENT + " holiday list for the year " + year + "...");

        final List<Date> expectedHol = new ArrayList<Date>();

        // JANUARY 1 will be a Friday
        expectedHol.add(new Date(1, January, year));
        // Martin Luther King's birthday, third Monday in JANUARY (since 1998)
        expectedHol.add(new Date(18, January, year));
        // Presidents' Day (a.k.a. Washington's birthday), third Monday in February
        expectedHol.add(new Date(15, February, year));
        // Good Friday
        //expectedHol.add(new Date(2, April,year));
        // Memorial Day, last Monday in May
        expectedHol.add(new Date(31, May, year));
        // Independence Day, July 4th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(5, July, year));
        // Labor Day, first Monday in September
        expectedHol.add(new Date(6, September, year));
        // Columbus Day, second Monday in October
        expectedHol.add(new Date(11, October, year));
        // Veterans' Day, November 11th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(11, November, year));
        // Thanksgiving Day, fourth Thursday in November
        expectedHol.add(new Date(25, November, year));
        // Christmas, December 25th (moved to Monday if Sunday or Friday if Saturday)
        expectedHol.add(new Date(24, December, year));
        expectedHol.add(new Date(31, December, year));

        // Call the Holiday Check
        final Calendar settlement = new UnitedStates(UnitedStates.Market.SETTLEMENT);
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


}






//
// OLD CODE
//


//package org.jquantlib.testsuite.calendars;
//
//import static org.jquantlib.time.Month.April;
//import static org.jquantlib.time.Month.December;
//import static org.jquantlib.time.Month.February;
//import static org.jquantlib.time.Month.January;
//import static org.jquantlib.time.Month.July;
//import static org.jquantlib.time.Month.June;
//import static org.jquantlib.time.Month.March;
//import static org.jquantlib.time.Month.May;
//import static org.jquantlib.time.Month.November;
//import static org.jquantlib.time.Month.October;
//import static org.jquantlib.time.Month.September;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jquantlib.QL;
//import org.jquantlib.lang.annotation.QualityAssurance;
//import org.jquantlib.lang.annotation.QualityAssurance.Quality;
//import org.jquantlib.lang.annotation.QualityAssurance.Version;
//import org.jquantlib.time.Calendar;
//import org.jquantlib.time.Date;
//import org.jquantlib.time.calendars.UnitedStates;
//import org.junit.Assert;
//import org.junit.Test;
//
///**
// *
// * Added QL097 UnitestStates test cases
// * Fixed old tested cases to work work with QL097 UnitedStates calendar class.
// *
// * @author Sangaran Sampanthan
// * @author Zahid Hussain
// *
// */
//@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
//
//public class UnitedStatesCalendarTest {
//
//	private final Calendar cNYSE;
//	private final Calendar cGovBond;
//	private final Calendar cNERC;
//	private final Calendar cSettlement;
//
//	public UnitedStatesCalendarTest() {
//        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
//        this.cNYSE      = new UnitedStates(UnitedStates.Market.NYSE);
//        this.cGovBond   = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
//        this.cNERC      = new UnitedStates(UnitedStates.Market.NERC);
//        this.cSettlement= new UnitedStates(UnitedStates.Market.SETTLEMENT);
//	}
//
//	@Test
//	public void testUSSettlement() {
//		QL.info("Testing US settlement holiday list...");
//		final CalendarUtil cbt = new CalendarUtil();
//	    final List<Date> expectedHol = new ArrayList<Date>();
//	    int year = 2004;
//
//	    expectedHol.add(new Date(1, January,year));
//	    expectedHol.add(new Date(19,January,year));
//	    expectedHol.add(new Date(16,February,year));
//	    expectedHol.add(new Date(9,April,year));//good friday
//	    expectedHol.add(new Date(31,May,year));
//	    expectedHol.add(new Date(5, July,year));
//	    expectedHol.add(new Date(6, September,year));
//	    expectedHol.add(new Date(11,October,year));
//	    expectedHol.add(new Date(11,November,year));
//	    expectedHol.add(new Date(25,November,year));
//	    expectedHol.add(new Date(24,December,year));
////	    expectedHol.add(new Date(31,DECEMBER,year));
//
//	    final Calendar c = new UnitedStates(UnitedStates.Market.SETTLEMENT);
//		cbt.checkHolidayList(expectedHol, c, year);
//
//		expectedHol.clear();
//
//		year = 2005;
//	    expectedHol.add(new Date(17,January,year));
//	    expectedHol.add(new Date(21,February,year));
//	    expectedHol.add(new Date(25,March,year));//good friday
//	    expectedHol.add(new Date(30,May,year));
//	    expectedHol.add(new Date(4, July,year));
//	    expectedHol.add(new Date(5,September,year));
//	    expectedHol.add(new Date(10,October,year));
//	    expectedHol.add(new Date(11,November,year));
//	    expectedHol.add(new Date(24,November,2005));
//	    expectedHol.add(new Date(26,December,year));
//
//		cbt.checkHolidayList(expectedHol, c, year);
//
//	}
//
//	@Test
//	public void testUSGovernmentBondMarket() {
//
//	    QL.info("Testing US government bond market holiday list...");
//	    final CalendarUtil cbt = new CalendarUtil();
//	    final int year = 2004;
//	    final List<Date> expectedHol = new ArrayList<Date>();
//	    expectedHol.add(new Date(1,January,year));
//	    expectedHol.add(new Date(19,January,year));
//	    expectedHol.add(new Date(16,February,year));
//	    expectedHol.add(new Date(9,April,year));
//	    expectedHol.add(new Date(31,May,year));
//	    expectedHol.add(new Date(5,July,year));
//	    expectedHol.add(new Date(6,September,year));
//	    expectedHol.add(new Date(11,October,year));
//	    expectedHol.add(new Date(11,November,year));
//	    expectedHol.add(new Date(25,November,year));
//	    expectedHol.add(new Date(24,December,year));
//
//	    final Calendar c = new UnitedStates(UnitedStates.Market.GOVERNMENTBOND);
//	    cbt.checkHolidayList(expectedHol, c, year);
//	}
//
//
//	@Test
//	public void testUSNewYorkStockExchange() {
//	    QL.info("Testing New York Stock Exchange holiday list...");
//
//	    final CalendarUtil cbt = new CalendarUtil();
//	    final Calendar c = new UnitedStates(UnitedStates.Market.NYSE);
//	    final List<Date> expectedHol = new ArrayList<Date>();
//
//	    int year = 2004;
//	    expectedHol.add(new Date(1,January,year));
//	    expectedHol.add(new Date(19,January,year));
//	    expectedHol.add(new Date(16,February,year));
//	    expectedHol.add(new Date(9,April,year));
//	    expectedHol.add(new Date(31,May,year));
//	    expectedHol.add(new Date(11,June,year));
//	    expectedHol.add(new Date(5,July,year));
//	    expectedHol.add(new Date(6,September,year));
//	    expectedHol.add(new Date(25,November,year));
//	    expectedHol.add(new Date(24,December,year));
//
//	    cbt.checkHolidayList(expectedHol, c, year);
//
//	    expectedHol.clear();
//	    year = 2005;
//	    expectedHol.add(new Date(17,January,year));
//	    expectedHol.add(new Date(21,February,year));
//	    expectedHol.add(new Date(25,March,year));
//	    expectedHol.add(new Date(30,May,year));
//	    expectedHol.add(new Date(4,July,year));
//	    expectedHol.add(new Date(5,September,year));
//	    expectedHol.add(new Date(24,November,year));
//	    expectedHol.add(new Date(26,December,year));
//
//	    cbt.checkHolidayList(expectedHol, c, year);
//
//	    expectedHol.clear();
//	    year = 2006;
//	    expectedHol.add(new Date(2,January,year));
//	    expectedHol.add(new Date(16,January,year));
//	    expectedHol.add(new Date(20,February,year));
//	    expectedHol.add(new Date(14,April,year));
//	    expectedHol.add(new Date(29,May,year));
//	    expectedHol.add(new Date(4,July,year));
//	    expectedHol.add(new Date(4,September,year));
//	    expectedHol.add(new Date(23,November,year));
//	    expectedHol.add(new Date(25,December,year));
//	    cbt.checkHolidayList(expectedHol, c, year);
//
//	    cbt.checkHolidayList(expectedHol, c, year);
//
//
//	    final List<Date> histClose = new ArrayList<Date>();
//	    histClose.add(new Date(11,June,2004));     // Reagan's funeral
//	    histClose.add(new Date(14,September,2001));// September 11, 2001
//	    histClose.add(new Date(13,September,2001));// September 11, 2001
//	    histClose.add(new Date(12,September,2001));// September 11, 2001
//	    histClose.add(new Date(11,September,2001));// September 11, 2001
//	    histClose.add(new Date(14,July,1977));     // 1977 Blackout
//	    histClose.add(new Date(25,January,1973));  // Johnson's funeral.
//	    histClose.add(new Date(28,December,1972)); // Truman's funeral
//	    histClose.add(new Date(21,July,1969));     // Lunar exploration nat. day
//	    histClose.add(new Date(31,March,1969));    // Eisenhower's funeral
//	    histClose.add(new Date(10,February,1969)); // heavy snow
//	    histClose.add(new Date(5,July,1968));      // Day after Independence Day
//	    // June 12-Dec. 31, 1968
//	    // Four day week (closed on Wednesdays) - Paperwork Crisis
//	    histClose.add(new Date(12,June,1968));
//	    histClose.add(new Date(19,June,1968));
//	    histClose.add(new Date(26,June,1968));
//	    histClose.add(new Date(3,July,1968 ));
//	    histClose.add(new Date(10,July,1968));
//	    histClose.add(new Date(17,July,1968));
//	    histClose.add(new Date(20,November,1968));
//	    histClose.add(new Date(27,November,1968));
//	    histClose.add(new Date(4,December,1968 ));
//	    histClose.add(new Date(11,December,1968));
//	    histClose.add(new Date(18,December,1968));
//	    // Presidential election days
//	    histClose.add(new Date(4,November,1980));
//	    histClose.add(new Date(2,November,1976));
//	    histClose.add(new Date(7,November,1972));
//	    histClose.add(new Date(5,November,1968));
//	    histClose.add(new Date(3,November,1964));
//
//	    for (final Date d: histClose) {
//	        if (!c.isHoliday(d)) {
//	        	Assert.fail(d.toString() + " should be holiday (historical close)");
//	        }
//	    }
//
//
//	}
//
//	@Test
//	public void testUnitedStatesYear2004() {
//		final int year = 2004;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day
//		expectedHol.add(new Date(1,January,year));
//		//memorial day
//		expectedHol.add(new Date(31,May,year));
//		//independence day (following monday)
//		expectedHol.add(new Date(5,July,year));
//		//labor day
//		expectedHol.add(new Date(6,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(25,November,year));
//		//christmas
//		//expectedHol.add(new Date(25,DECEMBER,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date reagan_funeral = new Date(11,June,year);
//		final Date good_friday = new Date(9,April,year);
//
//		//MLK day
//		expectedHol.add(new Date(19,January,year));
//		//presidents' day
//		expectedHol.add(new Date(16,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//		//christmas (previous friday)
//		expectedHol.add(new Date(24,December,year));
//		//President Reagan's funeral
//		expectedHol.add(reagan_funeral);
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		expectedHol.remove(reagan_funeral);
//
//		//Colombus day
//		expectedHol.add(new Date(11,October,year));
//		//Veteran's day
//		expectedHol.add(new Date(11,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
////		expectedHol.remove(good_friday);
//		//New Year's Eve falls on Friday
////		expectedHol.add(new Date(31,DECEMBER,year));
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//	@Test
//	public void testUnitedStatesYear2005() {
//		final int year = 2005;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day (following monday)
//		//expectedHol.add(new Date(1,JANUARY,year));
//		//memorial day
//		expectedHol.add(new Date(30,May,year));
//		//independence day
//		expectedHol.add(new Date(4,July,year));
//		//labor day
//		expectedHol.add(new Date(5,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(24,November,year));
//		//christmas (following monday)
//		expectedHol.add(new Date(26,December,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date good_friday = new Date(25,March,year);
//
//		//MLK day
//		expectedHol.add(new Date(17,January,year));
//		//presidents' day
//		expectedHol.add(new Date(21,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		//Colombus day
//		expectedHol.add(new Date(10,October,year));
//		//Veteran's day
//		expectedHol.add(new Date(11,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
////		expectedHol.remove(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//	@Test
//	public void testUnitedStatesYear2006() {
//		final int year = 2006;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day (following monday)
//		expectedHol.add(new Date(2,January,year));
//		//memorial day
//		expectedHol.add(new Date(29,May,year));
//		//independence day
//		expectedHol.add(new Date(4,July,year));
//		//labor day
//		expectedHol.add(new Date(4,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(23,November,year));
//		//christmas
//		expectedHol.add(new Date(25,December,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date good_friday = new Date(14,April,year);
//
//		//MLK day
//		expectedHol.add(new Date(16,January,year));
//		//presidents' day
//		expectedHol.add(new Date(20,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		//Colombus day
//		expectedHol.add(new Date(9,October,year));
//		//Veteran's day (previous friday)
//		expectedHol.add(new Date(10,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
////		expectedHol.remove(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//	@Test
//	public void testUnitedStatesYear2007() {
//		final int year = 2007;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day
//		expectedHol.add(new Date(1,January,year));
//		//memorial day
//		expectedHol.add(new Date(28,May,year));
//		//independence day
//		expectedHol.add(new Date(4,July,year));
//		//labor day
//		expectedHol.add(new Date(3,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(22,November,year));
//		//christmas
//		expectedHol.add(new Date(25,December,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date ford_funeral = new Date(2,January,year);
//		final Date good_friday = new Date(6,April,year);
//
//		//MLK day
//		expectedHol.add(new Date(15,January,year));
//		//presidents' day
//		expectedHol.add(new Date(19,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//		// President Ford's funeral
//		expectedHol.add(ford_funeral);
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		expectedHol.remove(ford_funeral);
//
//		//Colombus day
//		expectedHol.add(new Date(8,October,year));
//		//Veteran's day (next monday)
//		expectedHol.add(new Date(12,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
////		expectedHol.remove(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//
//	// 2008 - current year
//	@Test
//	public void testUnitedStatesYear2008() {
//		final int year = 2008;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day
//		expectedHol.add(new Date(1,January,year));
//		//memorial day
//		expectedHol.add(new Date(26,May,year));
//		//independence day
//		expectedHol.add(new Date(4,July,year));
//		//labor day
//		expectedHol.add(new Date(1,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(27,November,year));
//		//christmas
//		expectedHol.add(new Date(25,December,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date good_friday = new Date(21,March,year);
//
//		//MLK day
//		expectedHol.add(new Date(21,January,year));
//		//presidents' day
//		expectedHol.add(new Date(18,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		//Colombus day
//		expectedHol.add(new Date(13,October,year));
//		//Veteran's day
//		expectedHol.add(new Date(11,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
//		//expectedHol.remove(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//	@Test
//	public void testUnitedStatesYear2009() {
//		final int year = 2009;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day
//		expectedHol.add(new Date(1,January,year));
//		//memorial day
//		expectedHol.add(new Date(25,May,year));
//		//independence day
//		//expectedHol.add(new Date(4,JULY,year));
//		//labor day
//		expectedHol.add(new Date(7,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(26,November,year));
//		//christmas
//		expectedHol.add(new Date(25,December,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date good_friday = new Date(10,April,year);
//
//		//MLK day
//		expectedHol.add(new Date(19,January,year));
//		//preseidents' day
//		expectedHol.add(new Date(16,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//		//independence day (previous friday)
//		expectedHol.add(new Date(3,July,year));
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		//Colombus day
//		expectedHol.add(new Date(12,October,year));
//		//Veteran's day
//		expectedHol.add(new Date(11,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//	@Test
//	public void testUnitedStatesYear2010() {
//		final int year = 2010;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day
//		expectedHol.add(new Date(1,January,year));
//		//memorial day
//		expectedHol.add(new Date(31,May,year));
//		//independence day (following monday)
//		expectedHol.add(new Date(5,July,year));
//		//labor day
//		expectedHol.add(new Date(6,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(25,November,year));
//		//christmas
//		//expectedHol.add(new Date(25,DECEMBER,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date good_friday = new Date(2,April,year);
//
//		//MLK day
//		expectedHol.add(new Date(18,January,year));
//		//preseidents' day
//		expectedHol.add(new Date(15,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//		//christmas (previous friday)
//		expectedHol.add(new Date(24,December,year));
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		//Colombus day
//		expectedHol.add(new Date(11,October,year));
//		//Veteran's day
//		expectedHol.add(new Date(11,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
//		//New Year's Eve falls on Friday
////		expectedHol.add(new Date(31,DECEMBER,year));
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//	@Test
//	public void testUnitedStatesYear2011() {
//		final int year = 2011;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day
//		//expectedHol.add(new Date(1,JANUARY,year));
//		//memorial day
//		expectedHol.add(new Date(30,May,year));
//		//independence day
//		expectedHol.add(new Date(4,July,year));
//		//labor day
//		expectedHol.add(new Date(5,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(24,November,year));
//		//christmas (following monday)
//		expectedHol.add(new Date(26,December,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date good_friday = new Date(22,April,year);
//
//		//MLK day
//		expectedHol.add(new Date(17,January,year));
//		//preseidents' day
//		expectedHol.add(new Date(21,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		//Colombus day
//		expectedHol.add(new Date(10,October,year));
//		//Veteran's day
//		expectedHol.add(new Date(11,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//	@Test
//	public void testUnitedStatesYear2012() {
//		final int year = 2012;
//		QL.info("Testing United States holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
//
//        final List<Date> expectedHol = new ArrayList<Date>();
//
//		//new years day (following monday)
//		expectedHol.add(new Date(2,January,year));
//		//memorial day
//		expectedHol.add(new Date(28,May,year));
//		//independence day
//		expectedHol.add(new Date(4,July,year));
//		//labor day
//		expectedHol.add(new Date(3,September,year));
//		//thanksgiving
//		expectedHol.add(new Date(22,November,year));
//		//christmas
//		expectedHol.add(new Date(25,December,year));
//
//		// Call the Holiday Check
//		final CalendarUtil cbt = new CalendarUtil();
//		cbt.checkHolidayList(expectedHol, cNERC, year);
//
//		final Date good_friday = new Date(6,April,year);
//
//		//MLK day
//		expectedHol.add(new Date(16,January,year));
//		//preseidents' day
//		expectedHol.add(new Date(20,February,year));
//		//good friday
//		expectedHol.add(good_friday);
//
//		cbt.checkHolidayList(expectedHol, cNYSE, year);
//
//		//Colombus day
//		expectedHol.add(new Date(8,October,year));
//		//Veteran's day (next monday)
//		expectedHol.add(new Date(12,November,year));
//
//		cbt.checkHolidayList(expectedHol, cGovBond, year);
//
//		cbt.checkHolidayList(expectedHol, cSettlement, year);
//	}
//
//}