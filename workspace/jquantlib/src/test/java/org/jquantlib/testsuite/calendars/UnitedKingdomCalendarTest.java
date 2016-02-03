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
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.UnitedKingdom;
import org.junit.Test;

/**
 * @author Praneet Tiwari
 *
 */
public class UnitedKingdomCalendarTest {

    private final Calendar metals;
    private final Calendar settlement;
    private final Calendar exchange;

    
    public UnitedKingdomCalendarTest() {
        System.out.println("::::: " + this.getClass().getSimpleName() + " :::::");
        this.metals = new UnitedKingdom(UnitedKingdom.Market.Metals);
        this.settlement = new UnitedKingdom(UnitedKingdom.Market.Settlement);
        this.exchange = new UnitedKingdom(UnitedKingdom.Market.Exchange);
    }
    

    // 2004 - leap-year in the past
    @Test
    public void testUnitedKingdomMetalsYear2004() {
        int year = 2004;
        System.out.println("Testing " + metals.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Thursday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(9, April, year));
        // Easter Monday
        expectedHol.add(new Date(12, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(3, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(31, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(30, August, year));
        // Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(27, December, year));
        expectedHol.add(new Date(28, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, metals, year);

    }
    
    
    // 2005 - year in the past
    @Test
    public void testUnitedKingdomMetalsYear2005() {
        int year = 2005;
        System.out.println("Testing " + metals.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Saturday
        expectedHol.add(new Date(3, January, year));
        // Good Friday
        expectedHol.add(new Date(25, March, year));
        // Easter Monday
        expectedHol.add(new Date(28, March, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(2, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(30, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(29, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));
        expectedHol.add(new Date(27, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, metals, year);

    }
    
    
    // 2006 - year in the past
    @Test
    public void testUnitedKingdomMetalsYear2006() {
        int year = 2006;
        System.out.println("Testing " + metals.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Sunday
        expectedHol.add(new Date(2, January, year));
        // Good Friday
        expectedHol.add(new Date(14, April, year));
        // Easter Monday
        expectedHol.add(new Date(17, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(1, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(29, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(28, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, metals, year);

    }
    
    
    // 2007 - year in the past
    @Test
    public void testUnitedKingdomMetalsYear2007() {
        int year = 2007;
        System.out.println("Testing " + metals.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Monday
        expectedHol.add(new Date(1, January, year));
        // Good Friday
        expectedHol.add(new Date(6, April, year));
        // Easter Monday
        expectedHol.add(new Date(9, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(7, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(28, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(27, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, metals, year);

    }
    
    
    // 2008 - present year 
    @Test
    public void testUnitedKingdomMetalsYear2008() {
        int year = 2008;
        System.out.println("Testing " + metals.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Tuesday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(21, March, year));
        // Easter Monday
        expectedHol.add(new Date(24, March, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(5, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(26, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(25, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, metals, year);

    }
    
    
    // 2009 - future year 
    @Test
    public void testUnitedKingdomMetalsYear2009() {
        int year = 2009;
        System.out.println("Testing " + metals.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY will be a Thursday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(10, April, year));
        // Easter Monday
        expectedHol.add(new Date(13, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(4, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(25, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(31, August, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // 28 th, a Monday should also be a holiday
        expectedHol.add(new Date(28, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, metals, year);

    }
    
    
    // 2010 - future year 
    @Test
    public void testUnitedKingdomMetalsYear2010() {
        int year = 2010;
        System.out.println("Testing " + metals.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY will be a Friday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(2, April, year));
        // Easter Monday
        expectedHol.add(new Date(5, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(3, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(31, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(30, August, year));
        // 27 th DEC., a Monday should also be a holiday
        expectedHol.add(new Date(27, December, year));
        // 28 th DEC., a Tuesday should also be a holiday
        expectedHol.add(new Date(28, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, metals, year);

    }
    
    
    // test settlement dates now...
    public void testUnitedKingdomSettlementYear2004() {
        int year = 2004;
        System.out.println("Testing " + settlement.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        // Let's check the first weekend
        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(3, January, year));
        // Check another weekend, incidentally V Day ;-)
        expectedHol.add(new Date(14, February, year));
        //Good Friday
        expectedHol.add(new Date(9, April, year));
        // Easter Monday
        expectedHol.add(new Date(12, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(3, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(31, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(30, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));
        expectedHol.add(new Date(27, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);

    }
    
    
    // 2005 - year in the past
    @Test
    public void testUnitedKingdomSettlementYear2005() {
        int year = 2005;
        System.out.println("Testing " + settlement.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Saturday
        expectedHol.add(new Date(3, January, year));
        // Good Friday
        expectedHol.add(new Date(25, March, year));
        // Easter Monday
        expectedHol.add(new Date(28, March, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(2, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(30, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(29, August, year));
        expectedHol.add(new Date(26, December, year));
        expectedHol.add(new Date(27, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);

    }
    
    
    // 2006 - year in the past
    @Test
    public void testUnitedKingdomSettlementYear2006() {
        int year = 2006;
        System.out.println("Testing " + settlement.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Sunday
        expectedHol.add(new Date(2, January, year));
        // Good Friday
        expectedHol.add(new Date(14, April, year));
        // Easter Monday
        expectedHol.add(new Date(17, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(1, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(29, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(28, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);

    }
    
    
    // 2007 - year in the past
    @Test
    public void testUnitedKingdomSettlementYear2007() {
        int year = 2007;
        System.out.println("Testing " + settlement.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Monday
        expectedHol.add(new Date(1, January, year));
        // Good Friday
        expectedHol.add(new Date(6, April, year));
        // Easter Monday
        expectedHol.add(new Date(9, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(7, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(28, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(27, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));


        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);

    }
    
    
    // 2008 - present year 
    @Test
    public void testUnitedKingdomSettlementYear2008() {
        int year = 2008;
        System.out.println("Testing " + settlement.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Tuesday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(21, March, year));
        // Easter Monday
        expectedHol.add(new Date(24, March, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(5, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(26, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(25, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);

    }
    
    
    // 2009 - future year 
    @Test
    public void testUnitedKingdomSettlementYear2009() {
        int year = 2009;
        System.out.println("Testing " + settlement.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY will be a Thursday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(10, April, year));
        // Easter Monday
        expectedHol.add(new Date(13, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(4, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(25, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(31, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // 28 th, a Monday should also be a holiday
        expectedHol.add(new Date(28, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);

    }
    
    
    // 2010 - future year 
    @Test
    public void testUnitedKingdomSettlementYear2010() {
        int year = 2010;
        System.out.println("Testing " + settlement.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY will be a Friday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(2, April, year));
        // Easter Monday
        expectedHol.add(new Date(5, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(3, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(31, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(30, August, year));
        expectedHol.add(new Date(27, December, year));
        // 28 th, a Tuesday should also be a holiday
        expectedHol.add(new Date(28, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);

    }
    
    
    //test exchange dates...
    public void testUnitedKingdomExchangeYear2004() {
        int year = 2004;
        System.out.println("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Thursday
        expectedHol.add(new Date(1, January, year));
        // Let's check the first weekend
        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(3, January, year));
        // Check another weekend, incidentally V Day ;-)
        expectedHol.add(new Date(14, February, year));
        //Good Friday
        expectedHol.add(new Date(9, April, year));
        // Easter Monday
        expectedHol.add(new Date(12, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(3, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(31, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(30, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));
        expectedHol.add(new Date(27, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }
    
    
    // 2005 - year in the past
    @Test
    public void testUnitedKingdomExchangeYear2005() {
        int year = 2005;
        System.out.println("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Saturday
        expectedHol.add(new Date(3, January, year));
        // Good Friday
        expectedHol.add(new Date(25, March, year));
        // Easter Monday
        expectedHol.add(new Date(28, March, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(2, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(30, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(29, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        //	expectedHol.add(new Date(25,DECEMBER,year));
        expectedHol.add(new Date(26, December, year));
        expectedHol.add(new Date(27, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }
    
    
    // 2006 - year in the past
    @Test
    public void testUnitedKingdomExchangeYear2006() {
        int year = 2006;
        System.out.println("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Sunday
        expectedHol.add(new Date(2, January, year));
        // Good Friday
        expectedHol.add(new Date(14, April, year));
        // Easter Monday
        expectedHol.add(new Date(17, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(1, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(29, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(28, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }
    
    
    // 2007 - year in the past
    @Test
    public void testUnitedKingdomExchangeYear2007() {
        int year = 2007;
        System.out.println("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Monday
        expectedHol.add(new Date(1, January, year));
        // Good Friday
        expectedHol.add(new Date(6, April, year));
        // Easter Monday
        expectedHol.add(new Date(9, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(7, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(28, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(27, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }
    
    
    // 2008 - present year 
    @Test
    public void testUnitedKingdomExchangeYear2008() {
        int year = 2008;
        System.out.println("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY was a Tuesday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(21, March, year));
        // Easter Monday
        expectedHol.add(new Date(24, March, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(5, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(26, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(25, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // Boxing Day, December 26th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(26, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }
    
    
    // 2009 - future year 
    @Test
    public void testUnitedKingdomExchangeYear2009() {
        int year = 2009;
        System.out.println("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY will be a Thursday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(10, April, year));
        // Easter Monday
        expectedHol.add(new Date(13, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(4, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(25, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(31, August, year));
        //Christmas Day, December 25th (possibly moved to Monday or Tuesday)
        expectedHol.add(new Date(25, December, year));
        // 28 th, a Monday should also be a holiday
        expectedHol.add(new Date(28, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }
    
    
    // 2010 - future year 
    @Test
    public void testUnitedKingdomExchangeYear2010() {
        int year = 2010;
        System.out.println("Testing " + exchange.name() + " holiday list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

        // First JANUARY will be a Friday
        expectedHol.add(new Date(1, January, year));
        //Good Friday
        expectedHol.add(new Date(2, April, year));
        // Easter Monday
        expectedHol.add(new Date(5, April, year));
        // May Bank holiday, first Monday of May
        expectedHol.add(new Date(3, May, year));
        // Spring Bank Holiday, last Monday of May
        expectedHol.add(new Date(31, May, year));
        // Summer Bank Holiday, last Monday of August
        expectedHol.add(new Date(30, August, year));
        // 27 th, a Monday should also be a holiday
        expectedHol.add(new Date(27, December, year));
        // 28 th, a Tuesday should also be a holiday
        expectedHol.add(new Date(28, December, year));

        // Call the Holiday Check
        CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

}
