package org.jquantlib.testsuite.calendars;

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.September;

import java.util.List;
import java.util.Vector;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Mexico;
import org.jquantlib.time.calendars.Mexico.Market;
import org.junit.Before;
import org.junit.Test;

public class MexicoCalendarTest {

//    private Calendar settlementCalendar;
    private Calendar bmvCalendar;
    private List<Date> expectedHol;

    @Before
    public void setUp() {
//    	settlementCalendar = new Mexico(Market.SETTLEMENT);
    	bmvCalendar = new Mexico(Market.BMV);
        expectedHol = new Vector<Date>();
    }

//    @Test
//    public void testMexicoSettlementYear2004() {
//        final int year = 2004;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 1, JANUARY, year));
//        expectedHol.add(new Date( 2, FEBRUARY, year));
//        expectedHol.add(new Date( 8, APRIL, year));
//        expectedHol.add(new Date( 9, APRIL, year));
//        expectedHol.add(new Date(16, SEPTEMBER, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(15, NOVEMBER, year));
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }
//
//    @Test
//    public void testMexicoSettlementYear2005() {
//        final int year = 2005;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 7, FEBRUARY, year));
//        expectedHol.add(new Date(21, MARCH, year));
//        expectedHol.add(new Date(24, MARCH, year));
//        expectedHol.add(new Date(25, MARCH, year));
//        expectedHol.add(new Date(16, SEPTEMBER, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(14, NOVEMBER, year));
//        expectedHol.add(new Date(12, DECEMBER, year));
//
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }
//
//    @Test
//    public void testMexicoSettlementYear2006() {
//        final int year = 2006;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 6, FEBRUARY, year));
//        expectedHol.add(new Date(21, MARCH, year));
//        expectedHol.add(new Date(13, APRIL, year));
//        expectedHol.add(new Date(14, APRIL, year));
//        expectedHol.add(new Date( 1, MAY, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(20, NOVEMBER, year));
//        expectedHol.add(new Date(12, DECEMBER, year));
//        expectedHol.add(new Date(25, DECEMBER, year));
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }
//
//    @Test
//    public void testMexicoSettlementYear2007() {
//        final int year = 2007;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 1, JANUARY, year));
//        expectedHol.add(new Date( 5, FEBRUARY, year));
//        expectedHol.add(new Date(21, MARCH, year));
//        expectedHol.add(new Date( 5, APRIL, year));
//        expectedHol.add(new Date( 6, APRIL, year));
//        expectedHol.add(new Date( 1, MAY, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(19, NOVEMBER, year));
//        expectedHol.add(new Date(12, DECEMBER, year));
//        expectedHol.add(new Date(25, DECEMBER, year));
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }
//
//    // 2008 - current year
//    @Test
//    public void testMexicoSettlementYear2008() {
//        final int year = 2008;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 1, JANUARY, year));
//        expectedHol.add(new Date( 4, FEBRUARY, year));
//        expectedHol.add(new Date(20, MARCH, year));
//        expectedHol.add(new Date(21, MARCH, year));
//        expectedHol.add(new Date( 1, MAY, year));
//        expectedHol.add(new Date(16, SEPTEMBER, year));
//        expectedHol.add(new Date(17, NOVEMBER, year));
//        expectedHol.add(new Date(12, DECEMBER, year));
//        expectedHol.add(new Date(25, DECEMBER, year));
//
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//    }
//
//    @Test
//    public void testMexicoSettlementYear2009() {
//        final int year = 2009;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 1, JANUARY, year));
//        expectedHol.add(new Date( 2, FEBRUARY, year));
//        expectedHol.add(new Date( 9, APRIL, year));
//        expectedHol.add(new Date(10, APRIL, year));
//        expectedHol.add(new Date( 1, MAY, year));
//        expectedHol.add(new Date(16, SEPTEMBER, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(16, NOVEMBER, year));
//        expectedHol.add(new Date(25, DECEMBER, year));
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }
//
//    @Test
//    public void testMexicoSettlementYear2010() {
//        final int year = 2010;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 1, JANUARY, year));
//        expectedHol.add(new Date( 1, FEBRUARY, year));
//        expectedHol.add(new Date( 1, APRIL, year));
//        expectedHol.add(new Date( 2, APRIL, year));
//        expectedHol.add(new Date(16, SEPTEMBER, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(15, NOVEMBER, year));
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }
//
//    @Test
//    public void testMexicoSettlementYear2011() {
//        final int year = 2011;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 7, FEBRUARY, year));
//        expectedHol.add(new Date(21, MARCH, year));
//        expectedHol.add(new Date(21, APRIL, year));
//        expectedHol.add(new Date(22, APRIL, year));
//        expectedHol.add(new Date(16, SEPTEMBER, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(14, NOVEMBER, year));
//        expectedHol.add(new Date(12, DECEMBER, year));
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }
//
//    @Test
//    public void testMexicoSettlementYear2012() {
//        final int year = 2012;
//        QL.info("Testing Mexican Settlement holiday list for the year " + year + "...");
//
//        expectedHol.add(new Date( 6, FEBRUARY, year));
//        expectedHol.add(new Date(21, MARCH, year));
//        expectedHol.add(new Date( 5, APRIL, year));
//        expectedHol.add(new Date( 6, APRIL, year));
//        expectedHol.add(new Date( 1, MAY, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(19, NOVEMBER, year));
//        expectedHol.add(new Date(12, DECEMBER, year));
//        expectedHol.add(new Date(25, DECEMBER, year));
//
//        // Call the Holiday Check
//        final CalendarUtil cbt = new CalendarUtil();
//        cbt.checkHolidayList(expectedHol, settlementCalendar, year);
//
//    }


    @Test
    public void testMexicoBVMYear2004() {
        final int year = 2004;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

		expectedHol.add(new Date( 1, January, year));
        expectedHol.add(new Date( 5, February, year));//Zahid:Changed from 2 to 5
        expectedHol.add(new Date( 8, April, year));
        expectedHol.add(new Date( 9, April, year));
        expectedHol.add(new Date(16, September, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year)); //Zahid:
//        expectedHol.add(new Date(15, NOVEMBER, year)); //Zahid

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }

    @Test
    public void testMexicoBVMYear2005() {
        final int year = 2005;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

//        expectedHol.add(new Date( 5, FEBRUARY, year)); //Changed from 7 to 5, it Sat
        expectedHol.add(new Date(21, March, year));
        expectedHol.add(new Date(24, March, year));
        expectedHol.add(new Date(25, March, year));
        expectedHol.add(new Date(16, September, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(14, NOVEMBER, year));
        expectedHol.add(new Date(12, December, year));


        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }

    @Test
    public void testMexicoBVMYear2006() {
        final int year = 2006;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

//        expectedHol.add(new Date( 5, FEBRUARY, year));//Zahid: Changed from 6 to 5, it is Sun
        expectedHol.add(new Date(21, March, year));
        expectedHol.add(new Date(13, April, year));
        expectedHol.add(new Date(14, April, year));
        expectedHol.add(new Date( 1, May, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(20, NOVEMBER, year));
        expectedHol.add(new Date(12, December, year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }


	//    2007 - BMV Trading Holidays
	//
	//    01 Jan    Mon    New Year's Day
	//    05 Feb    Mon    Constitution Day
	//    19 Mar    Mon    Juarez's Birthday
	//    05 Apr    Thu    Holy Thursday
	//    06 Apr    Fri    Good Friday
	//    07 Apr    Sat    Holy Saturday
	//    01 May    Tue    Labour Day
	//    16 Sep    Sun    Independence Day
	//    02 Nov    Fri    All Soul's Day
	//    19 Nov    Mon    Mexican Revolution
	//    12 Dec    Wed    Our Lady of Guadalupe
	//    25 Dec    Tue    Christmas Day

    @Test
    public void testMexicoBVMYear2007() {
        final int year = 2007;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

        expectedHol.add(new Date( 1, January, year));
        expectedHol.add(new Date( 5, February, year));
        expectedHol.add(new Date(21, March, year));
        expectedHol.add(new Date( 5, April, year));
        expectedHol.add(new Date( 6, April, year));
        expectedHol.add(new Date( 1, May, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(19, NOVEMBER, year));
        expectedHol.add(new Date(12, December, year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }

    // 2008 - current year
    @Test
    public void testMexicoBVMYear2008() {
        final int year = 2008;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

        expectedHol.add(new Date( 1, January, year));
        expectedHol.add(new Date( 5, February, year));// Changed from 4 to 5
        expectedHol.add(new Date(20, March, year));
        expectedHol.add(new Date(21, March, year));
        expectedHol.add(new Date( 1, May, year));
        expectedHol.add(new Date(16, September, year));
//        expectedHol.add(new Date(17, NOVEMBER, year));
        expectedHol.add(new Date(12, December, year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);
    }

    @Test
    public void testMexicoBVMYear2009() {
        final int year = 2009;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

        expectedHol.add(new Date( 1, January, year));
        expectedHol.add(new Date( 5, February, year)); //Zahid Changed from 2 to 5
        expectedHol.add(new Date( 9, April, year));
        expectedHol.add(new Date(10, April, year));
        expectedHol.add(new Date( 1, May, year));
        expectedHol.add(new Date(16, September, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(16, NOVEMBER, year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }

    @Test
    public void testMexicoBVMYear2010() {
        final int year = 2010;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

        expectedHol.add(new Date( 1, January, year));
        expectedHol.add(new Date( 5, February, year)); //Changed from 1 to 5
        expectedHol.add(new Date( 1, April, year));
        expectedHol.add(new Date( 2, April, year));
        expectedHol.add(new Date(16, September, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(15, NOVEMBER, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }

    @Test
    public void testMexicoBVMYear2011() {
        final int year = 2011;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

//        expectedHol.add(new Date( 5, FEBRUARY, year)); //Changed from 7 to 5, it is Saturday
        expectedHol.add(new Date(21, March, year));
        expectedHol.add(new Date(21, April, year));
        expectedHol.add(new Date(22, April, year));
        expectedHol.add(new Date(16, September, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(14, NOVEMBER, year));
        expectedHol.add(new Date(12, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }

    @Test
    public void testMexicoBVMYear2012() {
        final int year = 2012;
        QL.info("Testing Mexican BVM holiday list for the year " + year + "...");

//        expectedHol.add(new Date( 5, FEBRUARY, year));//Changed from 6 to 5, it is Sunday
        expectedHol.add(new Date(21, March, year));
        expectedHol.add(new Date( 5, April, year));
        expectedHol.add(new Date( 6, April, year));
        expectedHol.add(new Date( 1, May, year));
//        expectedHol.add(new Date( 2, NOVEMBER, year));
//        expectedHol.add(new Date(19, NOVEMBER, year));
        expectedHol.add(new Date(12, December, year));
        expectedHol.add(new Date(25, December, year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, bmvCalendar, year);

    }

}
