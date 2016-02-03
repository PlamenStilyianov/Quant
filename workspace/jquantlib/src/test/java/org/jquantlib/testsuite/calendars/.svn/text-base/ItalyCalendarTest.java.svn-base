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
import org.jquantlib.time.calendars.Italy;
import org.junit.Test;

/**
 * @author Minh Do
 *
 */
public class ItalyCalendarTest {

	private final Calendar cExchange;
	private final Calendar cSettlement;


	public ItalyCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
		cExchange		= new Italy(Italy.Market.Exchange);
		cSettlement	= new Italy(Italy.Market.Settlement);
	}


	@Test
	public void testItalyYear2004() {
		final int year = 2004;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1,January,year));

		final Date goodFriday = new Date(9,April,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(12,April,year));
		final Date christmasEve = new Date(24,December,year);
		expectedHol.add(christmasEve);
		final Date newYearEve = new Date(31,December,year);
		expectedHol.add(newYearEve);

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);
		expectedHol.remove(christmasEve);
		expectedHol.remove(newYearEve);

		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(2,June,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(8,December,year));
		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testItalyYear2006() {
		final int year = 2006;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		final Date goodFriday = new Date(14,April,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(17,April,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(15,August,year));
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);

		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(25,April,year));
		expectedHol.add(new Date(2,June,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(8,December,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testItalyYear2007() {
		final int year = 2007;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1,January,year));
		final Date goodFriday = new Date(6,April,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(9,April,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(15,August,year));
		final Date christmasEve = new Date(24,December,year);
		expectedHol.add(christmasEve);
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));
		final Date newYearEve = new Date(31,December,year);
		expectedHol.add(newYearEve);

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);
		expectedHol.remove(christmasEve);
		expectedHol.remove(newYearEve);

		expectedHol.add(new Date(25,April,year));
		expectedHol.add(new Date(1,November,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testItalyYear2008() {
		final int year = 2008;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1,January,year));
		final Date goodFriday = new Date(21,March,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(24,March,year));
		expectedHol.add(new Date(1,May,year));
		expectedHol.add(new Date(15,August,year));
		final Date christmasEve = new Date(24,December,year);
		expectedHol.add(christmasEve);
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));
		final Date newYearEve = new Date(31,December,year);
		expectedHol.add(newYearEve);

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);
		expectedHol.remove(christmasEve);
		expectedHol.remove(newYearEve);

		expectedHol.add(new Date(25,April,year));
		expectedHol.add(new Date(2,June,year));
		expectedHol.add(new Date(8,December,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testItalyYear2009() {
		final int year = 2009;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1,January,year));
		final Date goodFriday = new Date(10,April,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(13,April,year));
		expectedHol.add(new Date(1,May,year));
		final Date christmasEve = new Date(24,December,year);
		expectedHol.add(christmasEve);
		expectedHol.add(new Date(25,December,year));
		final Date newYearEve = new Date(31,December,year);
		expectedHol.add(newYearEve);

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);
		expectedHol.remove(christmasEve);
		expectedHol.remove(newYearEve);

		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(2,June,year));
		expectedHol.add(new Date(8,December,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testItalyYear2010() {
		final int year = 2010;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1,January,year));
		final Date goodFriday = new Date(2,April,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(5,April,year));
		final Date christmasEve = new Date(24,December,year);
		expectedHol.add(christmasEve);
		final Date newYearEve = new Date(31,December,year);
		expectedHol.add(newYearEve);

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);
		expectedHol.remove(christmasEve);
		expectedHol.remove(newYearEve);

		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(2,June,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(8,December,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}
	@Test
	public void testItalyYear2011() {

		final int year = 2011;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		final Date goodFriday = new Date(22,April,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(25,April,year));
		expectedHol.add(new Date(15,August,year));
		expectedHol.add(new Date(26,December,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);

		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(2,June,year));
		expectedHol.add(new Date(1,November,year));
		expectedHol.add(new Date(8,December,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}


	@Test
	public void testItalyYear2012() {
		final int year = 2012;
		QL.info("Testing Italy holiday list for the year " + year);
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		final Date goodFriday = new Date(6,April,year);
		expectedHol.add(goodFriday);
		expectedHol.add(new Date(9,April,year));
		expectedHol.add(new Date(1,May,year));

		expectedHol.add(new Date(15, August,year));
		final Date christmasEve = new Date(24,December,year);
		expectedHol.add(christmasEve);
		expectedHol.add(new Date(25,December,year));
		expectedHol.add(new Date(26,December,year));

		final Date newYearEve = new Date(31,December,year);
		expectedHol.add(newYearEve);

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cExchange, year);

		expectedHol.remove(goodFriday);
		expectedHol.remove(christmasEve);
		expectedHol.remove(newYearEve);

		expectedHol.add(new Date(6,January,year));
		expectedHol.add(new Date(25,April,year));
		expectedHol.add(new Date(1,November,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}
}