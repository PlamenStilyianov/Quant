/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2008 Dominik Holenstein

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

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Brazil;
import org.junit.Test;

/**
 * @author Srinivas Hasti
 * @author Dominik Holenstein
 * @author Jia Jia
 *
 */

public class BrazilCalendarTest {

	private final Calendar exchange;
	private final Calendar settlement;

	public BrazilCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	    exchange = new Brazil(Brazil.Market.BOVESPA);
	    settlement = new Brazil(Brazil.Market.SETTLEMENT);
	}

    // 2004 - leap-year in the past
	@Test
    public void testBrazilBovespaYear2004()
    {
       	final int year = 2004;
    	QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(23,February,year));
    	expectedHol.add(new Date(24,February,year));
    	expectedHol.add(new Date(9,April,year));
    	expectedHol.add(new Date(21,April,year));
    	expectedHol.add(new Date(10,June,year));
    	expectedHol.add(new Date(9,July,year));
    	expectedHol.add(new Date(24,December,year));
    	expectedHol.add(new Date(31,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

    // 2004 - leap-year in the past
    @Test
    public void testBrazilBovespaYear2005()
    {
        final int year = 2005;
        QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(25,January,year));
        expectedHol.add(new Date(7,February,year));
        expectedHol.add(new Date(8,February,year));
        expectedHol.add(new Date(25,March,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(26,May,year));
        expectedHol.add(new Date(30,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    // 2004 - leap-year in the past
    @Test
    public void testBrazilBovespaYear2006()
    {
        final int year = 2006;
        QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(25,January,year));
        expectedHol.add(new Date(27,February,year));
        expectedHol.add(new Date(28,February,year));
        expectedHol.add(new Date(14,April,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(15,June,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(29,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    // 2007 - regular year in the past
    @Test
    public void testBrazilBovespaYear2007() {

    	final int year = 2007;
    	QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(25,January,year));
    	expectedHol.add(new Date(19,February,year));
        expectedHol.add(new Date(20,February,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(7,June,year));
        expectedHol.add(new Date(9,July,year));
        expectedHol.add(new Date(20,November,year));
        expectedHol.add(new Date(24,December,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(31,December,year));

        // Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    // 2008 - current year
    @Test
    public void testBrazilBovespaYear2008(){
      	final int year = 2008;
      	QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(25,January,year));
    	expectedHol.add(new Date(4,February,year));
    	expectedHol.add(new Date(5,February,year));
        expectedHol.add(new Date(21,March,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(22,May,year));
        expectedHol.add(new Date(9,July,year));
        expectedHol.add(new Date(20,November,year));
        expectedHol.add(new Date(24,December,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(31,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }


    // 2009 - current year in the future
    @Test
    public void testBrazilBovespaYear2009() {

    	final int year = 2009;
    	QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(23,February,year));
    	expectedHol.add(new Date(24,February,year));
        expectedHol.add(new Date(10,April,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(11,June,year));
        expectedHol.add(new Date(9,July,year));
        expectedHol.add(new Date(20,November,year));
        expectedHol.add(new Date(24,December,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(31,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);

    }

    // 2004 - leap-year in the past
    @Test
    public void testBrazilBovespaYear2010()
    {
        final int year = 2010;
        QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(25,January,year));
        expectedHol.add(new Date(15,February,year));
        expectedHol.add(new Date(16,February,year));
        expectedHol.add(new Date(2,April,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(3,June,year));
        expectedHol.add(new Date(9,July,year));
        expectedHol.add(new Date(24,December,year));
        expectedHol.add(new Date(31,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    // 2004 - leap-year in the past
    @Test
    public void testBrazilBovespaYear2011()
    {
        final int year = 2011;
        QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(25,January,year));
        expectedHol.add(new Date(7,March,year));
        expectedHol.add(new Date(8,March,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(22,April,year));
        expectedHol.add(new Date(23,June,year));
        expectedHol.add(new Date(30,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }
    // 2012 - next leap-year in the future
    @Test
    public void testBrazilBovespaYear2012() {
    	final int year = 2012;
    	QL.info("Testing " + Brazil.Market.BOVESPA + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(25,January,year));
    	expectedHol.add(new Date(20,February,year));
        expectedHol.add(new Date(21,February,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(7,June,year));
        expectedHol.add(new Date(9,July,year));
        expectedHol.add(new Date(20,November,year));
        expectedHol.add(new Date(24,December,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(31,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testBrazilSettlementYear2004() {
        final int year = 2004;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(23,February,year));
        expectedHol.add(new Date(24,February,year));
        expectedHol.add(new Date(9,April,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(10,June,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(15,November,year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2005() {
        final int year = 2005;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(7,February,year));
        expectedHol.add(new Date(8,February,year));
        expectedHol.add(new Date(25,March,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(26,May,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(15,November,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2006() {
        final int year = 2006;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(27,February,year));
        expectedHol.add(new Date(28,February,year));
        expectedHol.add(new Date(14,April,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(15,June,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(15,November,year));
        expectedHol.add(new Date(25,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2007() {
        final int year = 2007;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(19,February,year));
        expectedHol.add(new Date(20,February,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(7,June,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(15,November,year));
        expectedHol.add(new Date(25,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2008() {
        final int year = 2008;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(4,February,year));
        expectedHol.add(new Date(5,February,year));
        expectedHol.add(new Date(21,March,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(22,May,year));
        expectedHol.add(new Date(25,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2009() {
        final int year = 2009;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(23,February,year));
        expectedHol.add(new Date(24,February,year));
        expectedHol.add(new Date(10,April,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(11,June,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(25,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2010() {
        final int year = 2010;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(15,February,year));
        expectedHol.add(new Date(16,February,year));
        expectedHol.add(new Date(2,April,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(3,June,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(15,November,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2011() {
        final int year = 2011;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(7,March,year));
        expectedHol.add(new Date(8,March,year));
        expectedHol.add(new Date(21,April,year));
        expectedHol.add(new Date(22,April,year));
        expectedHol.add(new Date(23,June,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(15,November,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testBrazilSettlementYear2012() {
        final int year = 2012;
        QL.info("Testing " + Brazil.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(20,February,year));
        expectedHol.add(new Date(21,February,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(1,May,year));
        expectedHol.add(new Date(7,June,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(2,November,year));
        expectedHol.add(new Date(15,November,year));
        expectedHol.add(new Date(25,December,year));
        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }


}
