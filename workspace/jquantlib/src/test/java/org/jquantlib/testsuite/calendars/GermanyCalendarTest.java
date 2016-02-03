package org.jquantlib.testsuite.calendars;

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.October;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Germany;
import org.junit.Test;

/**
 * @author Sangaran Sampanthan
 */
public class GermanyCalendarTest {

    private final Calendar cFrankfurt;
	private final Calendar cXetra;
	private final Calendar cEurex;
	private final Calendar cSettlement;

	public GermanyCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
		cSettlement	= new Germany(Germany.Market.Settlement);
		cFrankfurt 	= new Germany(Germany.Market.FrankfurtStockExchange);
		cXetra 		= new Germany(Germany.Market.Xetra);
		cEurex 		= new Germany(Germany.Market.Eurex);
	}


	@Test
	public void testGermanyYear2004() {
		final int year = 2004;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		expectedHol.add(new Date(1,January,year));
		// Labour Day
		//expectedHol.add(new Date(1,MAY,year));
		//christmas eve
		expectedHol.add(new Date(24,December,year));
		//christmas
		//expectedHol.add(new Date(25,DECEMBER,year));
		//boxing day
		//expectedHol.add(new Date(26,DECEMBER,year));
		//new years eve
		expectedHol.add(new Date(31,December,year));

		//good friday
		expectedHol.add(new Date(9,April,year));
		//easter monday
		expectedHol.add(new Date(12,April,year));


		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		//expectedHol.add(new Date(3,OCTOBER,year));

		//ascension
		expectedHol.add(new Date(20,May,year));
		//whit monday
		expectedHol.add(new Date(31,May,year));
		//corpus christi
		expectedHol.add(new Date(10,June,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testGermanyYear2005() {
		final int year = 2005;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		//expectedHol.add(new Date(1,JANUARY,year));
		// Labour Day
		//expectedHol.add(new Date(1,MAY,year));
		//christmas eve
		//expectedHol.add(new Date(24,DECEMBER,year));
		//christmas
		//expectedHol.add(new Date(25,DECEMBER,year));
		//boxing day
		expectedHol.add(new Date(26,December,year));
		//new years eve
		//expectedHol.add(new Date(31,DECEMBER,year));

		//good friday
		expectedHol.add(new Date(25,March,year));
		//easter monday
		expectedHol.add(new Date(28,March,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		expectedHol.add(new Date(3,October,year));

		//ascension
		expectedHol.add(new Date(5,May,year));
		//whit monday
		expectedHol.add(new Date(16,May,year));
		//corpus christi
		expectedHol.add(new Date(26,May,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testGermanyYear2006() {
		final int year = 2006;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		//expectedHol.add(new Date(1,JANUARY,year));
		// Labour Day
		expectedHol.add(new Date(1,May,year));
		//christmas eve
		//expectedHol.add(new Date(24,DECEMBER,year));
		//christmas
		expectedHol.add(new Date(25,December,year));
		//boxing day
		expectedHol.add(new Date(26,December,year));
		//new years eve
		//expectedHol.add(new Date(31,DECEMBER,year));

		//good friday
		expectedHol.add(new Date(14,April,year));
		//easter monday
		expectedHol.add(new Date(17,April,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		expectedHol.add(new Date(3,October,year));

		//ascension
		expectedHol.add(new Date(25,May,year));
		//whit monday
		expectedHol.add(new Date(5,June,year));
		//corpus christi
		expectedHol.add(new Date(15,June,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testGermanyYear2007() {
		final int year = 2007;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		expectedHol.add(new Date(1,January,year));
		// Labour Day
		expectedHol.add(new Date(1,May,year));
		//christmas eve
		expectedHol.add(new Date(24,December,year));
		//christmas
		expectedHol.add(new Date(25,December,year));
		//boxing day
		expectedHol.add(new Date(26,December,year));
		//new years eve
		expectedHol.add(new Date(31,December,year));

		//good friday
		expectedHol.add(new Date(6,April,year));
		//easter monday
		expectedHol.add(new Date(9,April,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		expectedHol.add(new Date(3,October,year));

		//ascension
		expectedHol.add(new Date(17,May,year));
		//whit monday
		expectedHol.add(new Date(28,May,year));
		//corpus christi
		expectedHol.add(new Date(7,June,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}


	// 2008 - current year
	@Test
	public void testGermanyYear2008() {
		final int year = 2008;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		expectedHol.add(new Date(1,January,year));
		// Labour Day
		expectedHol.add(new Date(1,May,year));
		//christmas eve
		expectedHol.add(new Date(24,December,year));
		//christmas
		expectedHol.add(new Date(25,December,year));
		//boxing day
		expectedHol.add(new Date(26,December,year));
		//new years eve
		expectedHol.add(new Date(31,December,year));

		//good friday
		expectedHol.add(new Date(21,March,year));
		//easter monday
		expectedHol.add(new Date(24,March,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		expectedHol.add(new Date(3,October,year));

		//ascension -- same day as Labor day
		//expectedHol.add(new Date(1,MAY,year));
		//whit monday
		expectedHol.add(new Date(12,May,year));
		//corpus christi
		expectedHol.add(new Date(22,May,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testGermanyYear2009() {
		final int year = 2009;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		expectedHol.add(new Date(1,January,year));
		// Labour Day
		expectedHol.add(new Date(1,May,year));
		//christmas eve
		expectedHol.add(new Date(24,December,year));
		//christmas
		expectedHol.add(new Date(25,December,year));
		//boxing day
		//expectedHol.add(new Date(26,DECEMBER,year));
		//new years eve
		expectedHol.add(new Date(31,December,year));

		//good friday
		expectedHol.add(new Date(10,April,year));
		//easter monday
		expectedHol.add(new Date(13,April,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		//expectedHol.add(new Date(3,OCTOBER,year));

		//ascension
		expectedHol.add(new Date(21,May,year));
		//whit monday
		expectedHol.add(new Date(1,June,year));
		//corpus christi
		expectedHol.add(new Date(11,June,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testGermanyYear2010() {
		final int year = 2010;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		expectedHol.add(new Date(1,January,year));
		// Labour Day
		//expectedHol.add(new Date(1,MAY,year));
		//christmas eve
		expectedHol.add(new Date(24,December,year));
		//christmas
		//expectedHol.add(new Date(25,DECEMBER,year));
		//boxing day
		//expectedHol.add(new Date(26,DECEMBER,year));
		//new years eve
		expectedHol.add(new Date(31,December,year));

		//good friday
		expectedHol.add(new Date(2,April,year));
		//easter monday
		expectedHol.add(new Date(5,April,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		//expectedHol.add(new Date(3,OCTOBER,year));

		//ascension
		expectedHol.add(new Date(13,May,year));
		//whit monday
		expectedHol.add(new Date(24,May,year));
		//corpus christi
		expectedHol.add(new Date(3,June,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testGermanyYear2011() {
		final int year = 2011;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		//expectedHol.add(new Date(1,JANUARY,year));
		// Labour Day
		//expectedHol.add(new Date(1,MAY,year));
		//christmas eve
		//expectedHol.add(new Date(24,DECEMBER,year));
		//christmas
		//expectedHol.add(new Date(25,DECEMBER,year));
		//boxing day
		expectedHol.add(new Date(26,December,year));
		//new years eve
		//expectedHol.add(new Date(31,DECEMBER,year));

		//good friday
		expectedHol.add(new Date(22,April,year));
		//easter monday
		expectedHol.add(new Date(25,April,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		expectedHol.add(new Date(3,October,year));

		//ascension
		expectedHol.add(new Date(2,June,year));
		//whit monday
		expectedHol.add(new Date(13,June,year));
		//corpus christi
		expectedHol.add(new Date(23,June,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

	@Test
	public void testGermanyYear2012() {
		final int year = 2012;
		QL.info("Testing Germany holiday list for the year " + year + " as recognized by markets Frankfurt Stock Exchange, Xetra, Eurex ...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		//new years day
		//expectedHol.add(new Date(1,JANUARY,year));
		// Labour Day
		expectedHol.add(new Date(1,May,year));
		//christmas eve
		expectedHol.add(new Date(24,December,year));
		//christmas
		expectedHol.add(new Date(25,December,year));
		//boxing day
		expectedHol.add(new Date(26,December,year));
		//new years eve
		expectedHol.add(new Date(31,December,year));

		//good friday
		expectedHol.add(new Date(6,April,year));
		//easter monday
		expectedHol.add(new Date(9,April,year));

		// Call the Holiday Check
		final CalendarUtil cbt = new CalendarUtil();
		cbt.checkHolidayList(expectedHol, cFrankfurt, year);
		cbt.checkHolidayList(expectedHol, cXetra, year);
		cbt.checkHolidayList(expectedHol, cEurex, year);

		QL.info("Testing Germany holiday list for the year " + year + " as recognized by Settlement ...");
		//National Day
		expectedHol.add(new Date(3,October,year));

		//ascension
		expectedHol.add(new Date(17,May,year));
		//whit monday
		expectedHol.add(new Date(28,May,year));
		//corpus christi
		expectedHol.add(new Date(7,June,year));

		cbt.checkHolidayList(expectedHol, cSettlement, year);
	}

}
