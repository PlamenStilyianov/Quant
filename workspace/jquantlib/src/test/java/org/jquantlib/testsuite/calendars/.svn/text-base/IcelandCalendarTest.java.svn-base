package org.jquantlib.testsuite.calendars;

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Iceland;
import org.junit.Test;

/**
 * @author Bo Conroy
 *
 */
public class IcelandCalendarTest {

    //TODO: private final Calendar settlement;
    private final Calendar exchange;

	public IcelandCalendarTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	    //TODO: this.settlement = Iceland.getCalendar(Iceland.Market.Settlement);
	    this.exchange   = new Iceland(Iceland.Market.ICEX);
	}


    @Test
    public void testIcelandYear2004() {
        final int year = 2004;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

		expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(8, April, year));
        expectedHol.add(new Date(9, April, year));
        expectedHol.add(new Date(12, April, year));
        expectedHol.add(new Date(22, April, year));
        expectedHol.add(new Date(20, May, year));
        expectedHol.add(new Date(31, May, year));
        expectedHol.add(new Date(17, June, year));
        expectedHol.add(new Date(2, August, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testIcelandYear2005() {
        final int year = 2005;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(3, January, year));
        expectedHol.add(new Date(24, March, year));
        expectedHol.add(new Date(25, March, year));
        expectedHol.add(new Date(28, March, year));
        expectedHol.add(new Date(21, April, year));
        expectedHol.add(new Date(5, May, year));
        expectedHol.add(new Date(16, May, year));
        expectedHol.add(new Date(17, June, year));
        expectedHol.add(new Date(1, August, year));
        expectedHol.add(new Date(26, December, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testIcelandYear2006() {
        final int year = 2006;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(13, April, year));
        expectedHol.add(new Date(14, April, year));
        expectedHol.add(new Date(17, April, year));
        expectedHol.add(new Date(20, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(25, May, year));
        expectedHol.add(new Date(5, June, year));
        expectedHol.add(new Date(7, August, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testIcelandYear2007() {
        final int year = 2007;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(5, April, year));
        expectedHol.add(new Date(6, April, year));
        expectedHol.add(new Date(9, April, year));
        expectedHol.add(new Date(19, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(17, May, year));
        expectedHol.add(new Date(28, May, year));
        expectedHol.add(new Date(6, August, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    // 2008 - current year
    @Test
    public void testIcelandYear2008() {
        final int year = 2008;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(20, March, year));
        expectedHol.add(new Date(21, March, year));
        expectedHol.add(new Date(24, March, year));
        expectedHol.add(new Date(24, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(12, May, year));
        expectedHol.add(new Date(17, June, year));
        expectedHol.add(new Date(4, August, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testIcelandYear2009() {
        final int year = 2009;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(9, April, year));
        expectedHol.add(new Date(10, April, year));
        expectedHol.add(new Date(13, April, year));
        expectedHol.add(new Date(23, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(21, May, year));
        expectedHol.add(new Date(1, June, year));
        expectedHol.add(new Date(17, June, year));
        expectedHol.add(new Date(3, August, year));
        expectedHol.add(new Date(25, December, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testIcelandYear2010() {
        final int year = 2010;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(1, January, year));
        expectedHol.add(new Date(1, April, year));
        expectedHol.add(new Date(2, April, year));
        expectedHol.add(new Date(5, April, year));
        expectedHol.add(new Date(22, April, year));
        expectedHol.add(new Date(13, May, year));
        expectedHol.add(new Date(24, May, year));
        expectedHol.add(new Date(17, June, year));
        expectedHol.add(new Date(2, August, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testIcelandYear2011() {
        final int year = 2011;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(3, January, year));
        expectedHol.add(new Date(21, April, year));
        expectedHol.add(new Date(22, April, year));
        expectedHol.add(new Date(25, April, year));
        expectedHol.add(new Date(2, June, year));
        expectedHol.add(new Date(13, June, year));
        expectedHol.add(new Date(17, June, year));
        expectedHol.add(new Date(1, August, year));
        expectedHol.add(new Date(26, December, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

    @Test
    public void testIcelandYear2012() {
        final int year = 2012;
    	QL.info("Testing " + Iceland.Market.ICEX + " holidays list for the year " + year + "...");
        
    	final List<Date> expectedHol = new ArrayList<Date>();

        expectedHol.add(new Date(2, January, year));
        expectedHol.add(new Date(5, April, year));
        expectedHol.add(new Date(6, April, year));
        expectedHol.add(new Date(9, April, year));
        expectedHol.add(new Date(19, April, year));
        expectedHol.add(new Date(1, May, year));
        expectedHol.add(new Date(17, May, year));
        expectedHol.add(new Date(28, May, year));
        expectedHol.add(new Date(6, August, year));
        expectedHol.add(new Date(25, December, year));
        expectedHol.add(new Date(26, December, year));

    	// Call the Holiday Check
    	final CalendarUtil cbt = new CalendarUtil();
    	cbt.checkHolidayList(expectedHol, exchange, year);
    }

}
