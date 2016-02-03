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
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
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
import org.jquantlib.time.calendars.Canada;
import org.junit.Test;

/**
 * @author Jia Jia
 *
 */

public class CanadaCalendarTest {

    private final Calendar settlement;
    private final Calendar exchange;

	public CanadaCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
        settlement = new Canada(Canada.Market.SETTLEMENT);
        exchange = new Canada(Canada.Market.TSX);
	}

    @Test
    public void testCanadaSettlementYear2004() {
        final int year = 2004;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(9,April,year));
        expectedHol.add(new Date(12,April,year));//Zahid
        expectedHol.add(new Date(24,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(2,August,year));
        expectedHol.add(new Date(6,September,year));
        expectedHol.add(new Date(11,October,year));
        expectedHol.add(new Date(11,November,year));
        expectedHol.add(new Date(27,December,year));
        expectedHol.add(new Date(28,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2005() {
        final int year = 2005;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(25,March,year));
        expectedHol.add(new Date(28,March,year));//Zahid
        expectedHol.add(new Date(23,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(1,August,year));
        expectedHol.add(new Date(5,September,year));
        expectedHol.add(new Date(10,October,year));
        expectedHol.add(new Date(11,November,year));
        expectedHol.add(new Date(26,December,year));
        expectedHol.add(new Date(27,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2006() {
        final int year = 2006;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(2,January,year));
        expectedHol.add(new Date(14,April,year));
        expectedHol.add(new Date(17,April,year));
        expectedHol.add(new Date(22,May,year));
        expectedHol.add(new Date(3,July,year));
        expectedHol.add(new Date(7,August,year));
        expectedHol.add(new Date(4,September,year));
        expectedHol.add(new Date(9,October,year));
        expectedHol.add(new Date(13,November,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2007() {
        final int year = 2007;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(9,April,year));
        expectedHol.add(new Date(21,May,year));
        expectedHol.add(new Date(2,July,year));
        expectedHol.add(new Date(6,August,year));
        expectedHol.add(new Date(3,September,year));
        expectedHol.add(new Date(8,October,year));
        expectedHol.add(new Date(12,November,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2008() {
        final int year = 2008;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(18,February,year));
        expectedHol.add(new Date(21,March,year));
        expectedHol.add(new Date(24,March,year));//Zahid
        expectedHol.add(new Date(19,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(4,August,year));
        expectedHol.add(new Date(1,September,year));
        expectedHol.add(new Date(13,October,year));
        expectedHol.add(new Date(11,November,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2009() {
        final int year = 2009;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(16,February,year));
        expectedHol.add(new Date(10,April,year));
        expectedHol.add(new Date(13,April,year));//Zahid
        expectedHol.add(new Date(18,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(3,August,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(11,November,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(28,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2010() {
        final int year = 2010;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(15,February,year));
        expectedHol.add(new Date(2,April,year));
        expectedHol.add(new Date(5,April,year));//Zahid
        expectedHol.add(new Date(24,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(2,August,year));
        expectedHol.add(new Date(6,September,year));
        expectedHol.add(new Date(11,October,year));
        expectedHol.add(new Date(11,November,year));
        expectedHol.add(new Date(27,December,year));
        expectedHol.add(new Date(28,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2011() {
        final int year = 2011;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(21,February,year));
        expectedHol.add(new Date(22,April,year));
        expectedHol.add(new Date(25,April,year));//Zahid
        expectedHol.add(new Date(23,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(1,August,year));
        expectedHol.add(new Date(5,September,year));
        expectedHol.add(new Date(10,October,year));
        expectedHol.add(new Date(11,November,year));
        expectedHol.add(new Date(26,December,year));
        expectedHol.add(new Date(27,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaSettlementYear2012() {
        final int year = 2012;
        QL.info("Testing " + Canada.Market.SETTLEMENT + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(2,January,year));
        expectedHol.add(new Date(20,February,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(9,April,year));//Zahid
        expectedHol.add(new Date(21,May,year));
        expectedHol.add(new Date(2,July,year));
        expectedHol.add(new Date(6,August,year));
        expectedHol.add(new Date(3,September,year));
        expectedHol.add(new Date(8,October,year));
        expectedHol.add(new Date(12,November,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, settlement, year);
    }

    @Test
    public void testCanadaTSXYear2004() {
        final int year = 2004;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(9,April,year));
        expectedHol.add(new Date(12,April,year));//Zahid
        expectedHol.add(new Date(24,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(2,August,year));
        expectedHol.add(new Date(6,September,year));
        expectedHol.add(new Date(11,October,year));
        expectedHol.add(new Date(27,December,year));
        expectedHol.add(new Date(28,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2005() {
        final int year = 2005;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(25,March,year));
        expectedHol.add(new Date(28,March,year));//Zahid
        expectedHol.add(new Date(23,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(1,August,year));
        expectedHol.add(new Date(5,September,year));
        expectedHol.add(new Date(10,October,year));
        expectedHol.add(new Date(27,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2006() {
        final int year = 2006;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(2,January,year));
        expectedHol.add(new Date(14,April,year));
        expectedHol.add(new Date(17,April,year)); //Zahid
        expectedHol.add(new Date(22,May,year));
        expectedHol.add(new Date(3,July,year));
        expectedHol.add(new Date(7,August,year));
        expectedHol.add(new Date(4,September,year));
        expectedHol.add(new Date(9,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2007() {
        final int year = 2007;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(9,April,year)); //Zahid
        expectedHol.add(new Date(21,May,year));
        expectedHol.add(new Date(2,July,year));
        expectedHol.add(new Date(6,August,year));
        expectedHol.add(new Date(3,September,year));
        expectedHol.add(new Date(8,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2008() {
        final int year = 2008;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(18,February,year));
        expectedHol.add(new Date(21,March,year));
        expectedHol.add(new Date(24,March,year));//Zahid
        expectedHol.add(new Date(19,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(4,August,year));
        expectedHol.add(new Date(1,September,year));
        expectedHol.add(new Date(13,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2009() {
        final int year = 2009;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(16,February,year));
        expectedHol.add(new Date(10,April,year));
        expectedHol.add(new Date(13,April,year)); //Zahid
        expectedHol.add(new Date(18,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(3,August,year));
        expectedHol.add(new Date(7,September,year));
        expectedHol.add(new Date(12,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(28,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2010() {
        final int year = 2010;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1,January,year));
        expectedHol.add(new Date(15,February,year));
        expectedHol.add(new Date(2,April,year));
        expectedHol.add(new Date(5,April,year)); //Zahid
        expectedHol.add(new Date(24,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(2,August,year));
        expectedHol.add(new Date(6,September,year));
        expectedHol.add(new Date(11,October,year));
        expectedHol.add(new Date(27,December,year));
        expectedHol.add(new Date(28,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2011() {
        final int year = 2011;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(21,February,year));
        expectedHol.add(new Date(22,April,year));
        expectedHol.add(new Date(25,April,year)); //Zahid
        expectedHol.add(new Date(23,May,year));
        expectedHol.add(new Date(1,July,year));
        expectedHol.add(new Date(1,August,year));
        expectedHol.add(new Date(5,September,year));
        expectedHol.add(new Date(10,October,year));
        expectedHol.add(new Date(26,December,year));
        expectedHol.add(new Date(27,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

    @Test
    public void testCanadaTSXYear2012() {
        final int year = 2012;
        QL.info("Testing " + Canada.Market.TSX + " holiday list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(2,January,year));
        expectedHol.add(new Date(20,February,year));
        expectedHol.add(new Date(6,April,year));
        expectedHol.add(new Date(9,April,year)); //Zahid
        expectedHol.add(new Date(21,May,year));
        expectedHol.add(new Date(2,July,year));
        expectedHol.add(new Date(6,August,year));
        expectedHol.add(new Date(3,September,year));
        expectedHol.add(new Date(8,October,year));
        expectedHol.add(new Date(25,December,year));
        expectedHol.add(new Date(26,December,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);

    }

}
