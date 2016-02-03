/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.time.calendars;

import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;
import static org.jquantlib.time.Weekday.Monday;
import static org.jquantlib.time.Weekday.Tuesday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/** Canadian calendar
 * Banking holidays:
 *   <ul>
 *   <li>Saturdays</li>
 *   <li>Sundays</li>
 *   <li>New Year's Day, JANUARY 1st (possibly moved to Monday)</li>
 *   <li>Family Day, third Monday of February (since 2008)</li>
 *   <li>Good Friday</li>
 *   <li>Easter Monday</li>
 *   <li>Victoria Day, The Monday on or preceding 24 May</li>
 *   <li>Canada Day, July 1st (possibly moved to Monday)</li>
 *   <li>Provincial Holiday, first Monday of August</li>
 *   <li>Labour Day, first Monday of September</li>
 *   <li>Thanksgiving Day, second Monday of October</li>
 *   <li>Remembrance Day, November 11th (possibly moved to Monday)</li>
 *   <li>Christmas, December 25th (possibly moved to Monday or Tuesday)</li>
 *   <li>Boxing Day, December 26th (possibly moved to Monday or
 *       Tuesday)</li>
 *   </ul>

 *   Holidays for the Toronto stock exchange (TSX):
 *   <ul>
 *   <li>Saturdays</li>
 *   <li>Sundays</li>
 *   <li>New Year's Day, JANUARY 1st (possibly moved to Monday)</li>
 *   <li>Family Day, third Monday of February (since 2008)</li>
 *   <li>Good Friday</li>
 *   <li>Easter Monday</li>
 *   <li>Victoria Day, The Monday on or preceding 24 May</li>
 *   <li>Canada Day, July 1st (possibly moved to Monday)</li>
 *   <li>Provincial Holiday, first Monday of August</li>
 *   <li>Labour Day, first Monday of September</li>
 *   <li>Thanksgiving Day, second Monday of October</li>
 *   <li>Christmas, December 25th (possibly moved to Monday or Tuesday)</li>
 *   <li>Boxing Day, December 26th (possibly moved to Monday or
 *       Tuesday)</li>
 *   </ul>
 *
 *   \ingroup calendars
 *
 * @author Srinivas Hasti
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Canada extends Calendar {

    public static enum Market {
        /**
         * Generic settlement calendar
         */
        SETTLEMENT,

        /**
         * Toronto stock exchange calendar
         */
        TSX
    };


    //
    // public constructors
    //

    public Canada() {
        this(Market.SETTLEMENT);
    }

    public Canada(final Market market) {
        switch (market) {
        case SETTLEMENT:
            impl = new SettlementImpl();
            ;
            break;
        case TSX:
            impl = new TsxImpl();
            break;
        default:
            throw new LibraryException(UNKNOWN_MARKET);
        }
    }


    //
    // private final inner classes
    //

    private final class SettlementImpl extends WesternImpl {

    	@Override
    	public String name()  { return "Canada"; }

    	@Override
    	public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                // New Year's Day (possibly moved to Monday)
                || ((d == 1 || (d == 2 && w == Monday)) && m == January)
                // Family Day (third MONDAY in February, since 2008)
                || ((d >= 15 && d <= 21) && w == Monday && m == February
                    && y >= 2008)
                // Good Friday
                || (dd == em-3)
                // Easter MONDAY
                || (dd == em)
                // The MONDAY on or preceding 24 MAY (Victoria Day)
                || (d > 17 && d <= 24 && w == Monday && m == May)
                // JULY 1st, possibly moved to MONDAY (Canada Day)
                || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m==July)
                // first MONDAY of AUGUST (Provincial Holiday)
                || (d <= 7 && w == Monday && m == August)
                // first MONDAY of September (Labor Day)
                || (d <= 7 && w == Monday && m == September)
                // second MONDAY of October (Thanksgiving Day)
                || (d > 7 && d <= 14 && w == Monday && m == October)
                // November 11th (possibly moved to MONDAY)
                || ((d == 11 || ((d == 12 || d == 13) && w == Monday))
                    && m == November)
                // Christmas (possibly moved to MONDAY or Tuesday)
                || ((d == 25 || (d == 27 && (w == Monday || w == Tuesday)))
                    && m == December)
                // Boxing Day (possibly moved to MONDAY or TUESDAY)
                || ((d == 26 || (d == 28 && (w == Monday || w == Tuesday)))
                    && m == December)
                ) {
                return false;
            }
            return true;
    	}
    }

    private final class TsxImpl extends WesternImpl {

    	@Override
    	public String name()  { return "TSX"; }

    	@Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                // New Year's Day (possibly moved to MONDAY)
                || ((d == 1 || (d == 2 && w == Monday)) && m == January)
                // Family Day (third MONDAY in FEBRUARY, since 2008)
                || ((d >= 15 && d <= 21) && w == Monday && m == February
                    && y >= 2008)
                // Good Friday
                || (dd == em-3)
                // Easter MONDAY
                || (dd == em)
                // The MONDAY on or preceding 24 MAY (Victoria Day)
                || (d > 17 && d <= 24 && w == Monday && m == May)
                // JULY 1st, possibly moved to MONDAY (Canada Day)
                || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m==July)
                // first MONDAY of AUGUST (Provincial Holiday)
                || (d <= 7 && w == Monday && m == August)
                // first MONDAY of SEPTEMBER (Labor Day)
                || (d <= 7 && w == Monday && m == September)
                // second MONDAY of OCTOBER (Thanksgiving Day)
                || (d > 7 && d <= 14 && w == Monday && m == October)
                // Christmas (possibly moved to MONDAY or TUESDAY)
                || ((d == 25 || (d == 27 && (w == Monday || w == Tuesday)))
                    && m == December)
                // Boxing Day (possibly moved to MONDAY or TUESDAY)
                || ((d == 26 || (d == 28 && (w == Monday || w == Tuesday)))
                    && m == December)
                ) {
                return false;
            }
            return true;
        }
    }


}