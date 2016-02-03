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
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Poland;
import org.junit.Test;

/**
 * @author Renjith Nair
 * @author Richard Gomes
 */

public class PolandCalendarTest {

    private final Calendar settlement;
//    private final Calendar exchange;

	public PolandCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	    this.settlement = new Poland();
//	    this.exchange   = new Poland();
	}


	// 2004 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Apr 11	Easter Day
	//	Apr 12	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	May 30	Whit Sunday
	//	Jun 10	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2004()
    {
       	final int year = 2004;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date( 1,January,year));
    	// expectedHol.add(new Date(11,APRIL,year));
    	expectedHol.add(new Date(12,April,year));
    	// expectedHol.add(new Date( 1,MAY,year));
    	expectedHol.add(new Date( 3,May,year));
    	// expectedHol.add(new Date(30,MAY,year));
    	expectedHol.add(new Date(10,June,year));
    	// expectedHol.add(new Date(15,AUGUST,year));
    	expectedHol.add(new Date( 1,November,year));
    	expectedHol.add(new Date(11,November,year));
    	// expectedHol.add(new Date(25,DECEMBER,year));
    	// expectedHol.add(new Date(26,DECEMBER,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2005 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Mar 27	Easter Day
	//	Mar 28	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	May 15	Whit Sunday
	//	May 26	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2005()
    {
       	final int year = 2005;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	// expectedHol.add(new Date( 1,JANUARY,year));
    	// expectedHol.add(new Date(27,MARCH,year));
    	expectedHol.add(new Date(28,March,year));
    	// expectedHol.add(new Date( 1,MAY,year));
    	expectedHol.add(new Date( 3,May,year));
    	// expectedHol.add(new Date(15,MAY,year));
    	expectedHol.add(new Date(26,May,year));
    	expectedHol.add(new Date(15,August,year));
    	expectedHol.add(new Date( 1,November,year));
    	expectedHol.add(new Date(11,November,year));
    	// expectedHol.add(new Date(25,DECEMBER,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2006 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Apr 16	Easter Day
	//	Apr 17	testPolandWSEHolidaysYear2006Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	Jun  4	Whit Sunday
	//	Jun 15	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2006()
    {
       	final int year = 2006;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	// expectedHol.add(new Date( 1,JANUARY,year));
    	// expectedHol.add(new Date(16,APRIL,year));
    	expectedHol.add(new Date(17,April,year));
    	expectedHol.add(new Date( 1,May,year));
    	expectedHol.add(new Date( 3,May,year));
    	// expectedHol.add(new Date( 4,JUNE,year));
    	expectedHol.add(new Date(15,June,year));
    	expectedHol.add(new Date(15,August,year));
    	expectedHol.add(new Date( 1,November,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2007 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Apr  8	Easter Day
	//	Apr  9	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	May 27	Whit Sunday
	//	Jun  7	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2007()
    {
       	final int year = 2007;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date( 1,January,year));
    	expectedHol.add(new Date( 9,April,year));
    	expectedHol.add(new Date( 1,May,year));
    	expectedHol.add(new Date( 3,May,year));
    	// expectedHol.add(new Date(27,MAY,year));
    	expectedHol.add(new Date( 7,June,year));
    	expectedHol.add(new Date(15,August,year));
    	expectedHol.add(new Date( 1,November,year));
    	// expectedHol.add(new Date(11,NOVEMBER,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2008 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Mar 23	Easter Day
	//	Mar 24	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	May 11	Whit Sunday
	//	May 22	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2008()
    {
       	final int year = 2008;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,January,year));
    	expectedHol.add(new Date(24,March,year));
    	expectedHol.add(new Date(1,May,year));
    	// expectedHol.add(new Date(11,MAY,year));
    	expectedHol.add(new Date(22,May,year));
    	expectedHol.add(new Date(15,August,year));
    	expectedHol.add(new Date(11,November,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2009 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Apr 12	Easter Day
	//	Apr 13	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	May 31	Whit Sunday
	//	Jun 11	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2009()
    {
       	final int year = 2009;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date( 1,January,year));
    	// expectedHol.add(new Date(12,APRIL,year));
    	expectedHol.add(new Date(13,April,year));
    	expectedHol.add(new Date( 1,May,year));
    	// expectedHol.add(new Date( 3,MAY,year));
    	// expectedHol.add(new Date(31,MAY,year));
    	expectedHol.add(new Date(11,June,year));
    	// expectedHol.add(new Date(15,AUGUST,year));
    	// expectedHol.add(new Date( 1,NOVEMBER,year));
    	expectedHol.add(new Date(11,November,year));
    	expectedHol.add(new Date(25,December,year));
    	// expectedHol.add(new Date(26,DECEMBER,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2010 - Settlement trading holidays
	//
	//	Jan 1	New Year's Day
	//	Apr 4	Easter Day
	//	Apr 5	Easter Monday
	//	May 1	State Holiday
	//	May 3	Constitution Day
	//	May 23	Whit Sunday
	//	Jun 3	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov 1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2010()
    {
       	final int year = 2010;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date( 1,January,year));
    	// expectedHol.add(new Date( 4,APRIL,year));
    	expectedHol.add(new Date( 5,April,year));
    	// expectedHol.add(new Date( 1,MAY,year));
    	expectedHol.add(new Date( 3,May,year));
    	// expectedHol.add(new Date(23,MAY,year));
    	expectedHol.add(new Date( 3,June,year));
    	// expectedHol.add(new Date(15,AUGUST,year));
    	expectedHol.add(new Date( 1,November,year));
    	expectedHol.add(new Date(11,November,year));
    	// expectedHol.add(new Date(25,DECEMBER,year));
    	// expectedHol.add(new Date(26,DECEMBER,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2011 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Apr 24	Easter Day
	//	Apr 25	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	Jun 12	Whit Sunday
	//	Jun 23	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2011()
    {
       	final int year = 2011;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	// expectedHol.add(new Date( 1,JANUARY,year));
    	// expectedHol.add(new Date(24,APRIL,year));
    	expectedHol.add(new Date(25,April,year));
    	// expectedHol.add(new Date( 1,MAY,year));
    	expectedHol.add(new Date( 3,May,year));
    	// expectedHol.add(new Date(12,JUNE,year));
    	expectedHol.add(new Date(23,June,year));
    	expectedHol.add(new Date(15,August,year));
    	expectedHol.add(new Date( 1,November,year));
    	expectedHol.add(new Date(11,November,year));
    	// expectedHol.add(new Date(25,DECEMBER,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);

    }


	// 2012 - Settlement trading holidays
	//
	//	Jan  1	New Year's Day
	//	Apr  8	Easter Day
	//	Apr  9	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	May 27	Whit Sunday
	//	Jun  7	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

	@Test
    public void testPolandSettlementHolidaysYear2012()
    {
       	final int year = 2012;
    	QL.info("Testing " + settlement.name() + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

    	// expectedHol.add(new Date( 1,JANUARY,year));
    	// expectedHol.add(new Date( 8,APRIL,year));
    	expectedHol.add(new Date( 9,April,year));
    	expectedHol.add(new Date( 1,May,year));
    	expectedHol.add(new Date( 3,May,year));
    	// expectedHol.add(new Date(27,MAY,year));
    	expectedHol.add(new Date( 7,June,year));
    	expectedHol.add(new Date(15,August,year));
    	expectedHol.add(new Date( 1,November,year));
    	// expectedHol.add(new Date(11,NOVEMBER,year));
    	expectedHol.add(new Date(25,December,year));
    	expectedHol.add(new Date(26,December,year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, settlement, year);
    }






	// 2004 - WSE trading holidays
	//
	//	Jan  1	New Year's Day
	//	Jan  2	New Year's Day (Friday gap)
	//	Apr  9	Good Friday
	//	Apr 11	Easter Day
	//	Apr 12	Easter Monday
	//	May  1	State Holiday
	//	May  3	Constitution Day
	//	May 30	Whit Sunday
	//	Jun 10	Corpus Christi
	//	Aug 15	Assumption of Mary
	//	Nov  1	All Saints
	//	Nov 11	Independence Day
	//	Dec 24	Christmas Eve
	//	Dec 25	Christmas Day
	//	Dec 26	Boxing Day

//	@Test
//    public void testPolandWSEHolidaysYear2004()
//    {
//       	final int year = 2004;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	expectedHol.add(new Date( 1,JANUARY,year));
////    	expectedHol.add(new Date( 2,JANUARY,year));
//    	expectedHol.add(new Date( 9,APRIL,year));
//    	// expectedHol.add(new Date(11,APRIL,year));
//    	expectedHol.add(new Date(12,APRIL,year));
//    	// expectedHol.add(new Date( 1,MAY,year));
//    	expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date(30,MAY,year));
//    	expectedHol.add(new Date(10,JUNE,year));
//    	// expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date( 1,NOVEMBER,year));
//    	expectedHol.add(new Date(11,NOVEMBER,year));
//    	expectedHol.add(new Date(24,DECEMBER,year));
//    	// expectedHol.add(new Date(25,DECEMBER,year));
//    	// expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2005 - WSE trading holidays
//	//
//	//	Jan  1	New Year's Day
//	//	Mar 25	Good Friday
//	//	Mar 27	Easter Day
//	//	Mar 28	Easter Monday
//	//	May  1	State Holiday
//	//	May  3	Constitution Day
//	//	May 15	Whit Sunday
//	//	May 26	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov  1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2005()
//    {
//       	final int year = 2005;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	// expectedHol.add(new Date( 1,JANUARY,year));
//    	expectedHol.add(new Date(25,MARCH,year));
//    	// expectedHol.add(new Date(27,MARCH,year));
//    	expectedHol.add(new Date(28,MARCH,year));
//    	// expectedHol.add(new Date( 1,MAY,year));
//    	expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date(15,MAY,year));
//    	expectedHol.add(new Date(26,MAY,year));
//    	expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date( 1,NOVEMBER,year));
//    	expectedHol.add(new Date(11,NOVEMBER,year));
//    	// expectedHol.add(new Date(25,DECEMBER,year));
//    	expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2006 - WSE trading holidays
//	//
//	//	Jan  1	New Year's Day
//	//	Apr 14	Good Friday
//	//	Apr 16	Easter Day
//	//	Apr 17	Easter Monday
//	//	May  1	State Holiday
//	//	May  3	Constitution Day
//	//	Jun  4	Whit Sunday
//	//	Jun 15	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov  1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2006()
//    {
//       	final int year = 2006;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	// expectedHol.add(new Date( 1,JANUARY,year));
//    	expectedHol.add(new Date(14,APRIL,year));
//    	// expectedHol.add(new Date(16,APRIL,year));
//    	expectedHol.add(new Date(17,APRIL,year));
//    	expectedHol.add(new Date( 1,MAY,year));
//    	expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date( 4,JUNE,year));
//    	expectedHol.add(new Date(15,JUNE,year));
//    	expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date( 1,NOVEMBER,year));
//    	// expectedHol.add(new Date(24,DECEMBER,year));
//    	expectedHol.add(new Date(25,DECEMBER,year));
//    	expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2007 - WSE trading holidays
//	//
//	//	Jan  1	New Year's Day
//	//	Apr  6	Good Friday
//	//	Apr  8	Easter Day
//	//	Apr  9	Easter Monday
//	//	May  1	State Holiday
//	//	May  3	Constitution Day
//	//	May 27	Whit Sunday
//	//	Jun  7	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov  1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2007()
//    {
//       	final int year = 2007;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	expectedHol.add(new Date( 1,JANUARY,year));
//    	expectedHol.add(new Date( 6,APRIL,year));
//    	expectedHol.add(new Date( 9,APRIL,year));
//    	expectedHol.add(new Date( 1,MAY,year));
//    	expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date(27,MAY,year));
//    	expectedHol.add(new Date( 7,JUNE,year));
//    	expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date( 1,NOVEMBER,year));
//    	// expectedHol.add(new Date(11,NOVEMBER,year));
//    	expectedHol.add(new Date(24,DECEMBER,year));
//    	expectedHol.add(new Date(25,DECEMBER,year));
//    	expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2008 - WSE trading holidays
//	//
//	//	Jan  1	New Year's Day
//	//	Mar 21	Good Friday
//	//	Mar 23	Easter Day
//	//	Mar 24	Easter Monday
//	//	May  1	State Holiday
//	//	May  3	Constitution Day
//	//	May 11	Whit Sunday
//	//	May 22	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov  1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2008()
//    {
//       	final int year = 2008;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	expectedHol.add(new Date(1,JANUARY,year));
//    	expectedHol.add(new Date(21,MARCH,year));
//    	expectedHol.add(new Date(24,MARCH,year));
//    	expectedHol.add(new Date(1,MAY,year));
//    	// expectedHol.add(new Date(11,MAY,year));
//    	expectedHol.add(new Date(22,MAY,year));
//    	expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date(11,NOVEMBER,year));
//    	expectedHol.add(new Date(24,DECEMBER,year));
//    	expectedHol.add(new Date(25,DECEMBER,year));
//    	expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2009 - WSE trading holidays
//	//
//	//	Jan  1	New Year's Day
//	//	Jan  2	New Year's Day // Friday gap
//	//	Apr 10	Good Friday
//	//	Apr 12	Easter Day
//	//	Apr 13	Easter Monday
//	//	May  1	State Holiday
//	//	May  3	Constitution Day
//	//	May 31	Whit Sunday
//	//	Jun 11	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov  1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2009()
//    {
//       	final int year = 2009;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	expectedHol.add(new Date( 1,JANUARY,year));
//    	expectedHol.add(new Date( 2,JANUARY,year));
//    	expectedHol.add(new Date(10,APRIL,year));
//    	// expectedHol.add(new Date(12,APRIL,year));
//    	expectedHol.add(new Date(13,APRIL,year));
//    	expectedHol.add(new Date( 1,MAY,year));
//    	// expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date(31,MAY,year));
//    	expectedHol.add(new Date(11,JUNE,year));
//    	// expectedHol.add(new Date(15,AUGUST,year));
//    	// expectedHol.add(new Date( 1,NOVEMBER,year));
//    	expectedHol.add(new Date(11,NOVEMBER,year));
//    	expectedHol.add(new Date(24,DECEMBER,year));
//    	expectedHol.add(new Date(25,DECEMBER,year));
//    	// expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2010 - WSE trading holidays
//	//
//	//	Jan 1	New Year's Day
//	//	Apr 2	Good Friday
//	//	Apr 4	Easter Day
//	//	Apr 5	Easter Monday
//	//	May 1	State Holiday
//	//	May 3	Constitution Day
//	//	May 23	Whit Sunday
//	//	Jun 3	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov 1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2010()
//    {
//       	final int year = 2010;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	expectedHol.add(new Date(1,JANUARY,year));
//    	expectedHol.add(new Date( 2,APRIL,year));
//    	// expectedHol.add(new Date( 4,APRIL,year));
//    	expectedHol.add(new Date( 5,APRIL,year));
//    	// expectedHol.add(new Date( 1,MAY,year));
//    	expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date(23,MAY,year));
//    	expectedHol.add(new Date( 3,JUNE,year));
//    	// expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date( 1,NOVEMBER,year));
//    	expectedHol.add(new Date(11,NOVEMBER,year));
//    	expectedHol.add(new Date(24,DECEMBER,year));
//    	// expectedHol.add(new Date(25,DECEMBER,year));
//    	// expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2011 - WSE trading holidays
//	//
//	//	Jan  1	New Year's Day
//	//	Apr 22	Good Friday
//	//	Apr 24	Easter Day
//	//	Apr 25	Easter Monday
//	//	May  1	State Holiday
//	//	May  3	Constitution Day
//	//	Jun 12	Whit Sunday
//	//	Jun 23	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov  1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2011()
//    {
//       	final int year = 2011;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	// expectedHol.add(new Date( 1,JANUARY,year));
//    	expectedHol.add(new Date(22,APRIL,year));
//    	// expectedHol.add(new Date(24,APRIL,year));
//    	expectedHol.add(new Date(25,APRIL,year));
//    	// expectedHol.add(new Date( 1,MAY,year));
//    	expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date(12,JUNE,year));
//    	expectedHol.add(new Date(23,JUNE,year));
//    	expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date( 1,NOVEMBER,year));
//    	expectedHol.add(new Date(11,NOVEMBER,year));
//    	// expectedHol.add(new Date(24,DECEMBER,year));
//    	// expectedHol.add(new Date(25,DECEMBER,year));
//    	expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//
//    }
//
//
//	// 2012 - WSE trading holidays
//	//
//	//	Jan  1	New Year's Day
//	//	Apr  6	Good Friday
//	//	Apr  8	Easter Day
//	//	Apr  9	Easter Monday
//	//	May  1	State Holiday
//	//	May  3	Constitution Day
//	//	May 27	Whit Sunday
//	//	Jun  7	Corpus Christi
//	//	Aug 15	Assumption of Mary
//	//	Nov  1	All Saints
//	//	Nov 11	Independence Day
//	//	Dec 24	Christmas Eve
//	//	Dec 25	Christmas Day
//	//	Dec 26	Boxing Day
//
//	@Test
//    public void testPolandWSEHolidaysYear2012()
//    {
//       	final int year = 2012;
//    	QL.info("Testing " + exchange.name() + " holidays list for the year " + year + "...");
//        
//    	final List<Date> expectedHol = new ArrayList<Date>();
//
//    	// expectedHol.add(new Date( 1,JANUARY,year));
//    	expectedHol.add(new Date( 6,APRIL,year));
//    	// expectedHol.add(new Date( 8,APRIL,year));
//    	expectedHol.add(new Date( 9,APRIL,year));
//    	expectedHol.add(new Date( 1,MAY,year));
//    	expectedHol.add(new Date( 3,MAY,year));
//    	// expectedHol.add(new Date(27,MAY,year));
//    	expectedHol.add(new Date( 7,JUNE,year));
//    	expectedHol.add(new Date(15,AUGUST,year));
//    	expectedHol.add(new Date( 1,NOVEMBER,year));
//    	// expectedHol.add(new Date(11,NOVEMBER,year));
//    	expectedHol.add(new Date(24,DECEMBER,year));
//    	expectedHol.add(new Date(25,DECEMBER,year));
//    	expectedHol.add(new Date(26,DECEMBER,year));
//
//    	// Call the Holiday Check
//    	final CalendarUtil cbt = new CalendarUtil();
//    	cbt.checkHolidayList(expectedHol, exchange, year);
//    }


}
