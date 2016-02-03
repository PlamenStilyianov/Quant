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

package org.jquantlib.time.calendars;

import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
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

/**
 *
 * United Kingdom calendars Public holidays (data from http://www.dti.gov.uk/er/bankhol.htm):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday)</li>
 * <li>Good Friday</li>
 * <li>Easter Monday</li>
 * <li>Early May Bank Holiday, first Monday of May</li>
 * <li>Spring Bank Holiday, last Monday of May</li>
 * <li>Summer Bank Holiday, last Monday of August</li>
 * <li>Christmas Day, DECEMBER 25th (possibly moved to Monday or Tuesday)</li>
 * <li>Boxing Day, DECEMBER 26th (possibly moved to Monday or Tuesday)</li>
 * </ul>
 *
 * Holidays for the stock exchange:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday)</li>
 * <li>Good Friday</li>
 * <li>Easter Monday</li>
 * <li>Early May Bank Holiday, first Monday of May</li>
 * <li>Spring Bank Holiday, last Monday of May</li>
 * <li>Summer Bank Holiday, last Monday of August</li>
 * <li>Christmas Day, DECEMBER 25th (possibly moved to Monday or Tuesday)</li>
 * <li>Boxing Day, DECEMBER 26th (possibly moved to Monday or Tuesday)</li>
 * </ul>
 *
 * Holidays for the metals exchange:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday)</li>
 * <li>Good Friday</li>
 * <li>Easter Monday</li>
 * <li>Early May Bank Holiday, first Monday of May</li>
 * <li>Spring Bank Holiday, last Monday of May</li>
 * <li>Summer Bank Holiday, last Monday of August</li>
 * <li>Christmas Day, DECEMBER 25th (possibly moved to Monday or Tuesday)</li>
 * <li>Boxing Day, DECEMBER 26th (possibly moved to Monday or Tuesday)</li>
 * </ul>
 *
 * @category calendars
 * @TODO add LIFFE
 * @test the correctness of the returned results is tested against a list of known holidays.
 *
 * @author Srinivas Hasti TODO add LIFFE
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class UnitedKingdom extends Calendar {
    /**
     * UK calendars
     *
     */
    public static enum Market {
        /**
         * Generic settlement calendar
         */
        Settlement,

        /**
         * London Stock Exchange calendar
         */
        Exchange,

        /**
         * London Metals Exchange calendar
         */
        Metals
    };

    //
    // public constructors
    //

    public UnitedKingdom() {
        this(Market.Settlement);
    }

    public UnitedKingdom(final Market market) {
        switch (market) {
        case Settlement:
            impl = new SettlementImpl();
            break;
        case Exchange:
            impl = new ExchangeImpl();
            break;
        case Metals:
            impl = new MetalsImpl();
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
        public String name() {
            return "UK settlement";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
            // New Year's Day (possibly moved to Monday)
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == January)
                    // Good Friday
                    || (dd == em - 3)
                    // Easter MONDAY
                    || (dd == em)
                    // first MONDAY of May (Early May Bank Holiday)
                    || (d <= 7 && w == Monday && m == May)
                    // last MONDAY of MAY (Spring Bank Holiday)
                    || (d >= 25 && w == Monday && m == May && y != 2002)
                    // last MONDAY of August (Summer Bank Holiday)
                    || (d >= 25 && w == Monday && m == August)
                    // Christmas (possibly moved to MONDAY or Tuesday)
                    || ((d == 25 || (d == 27 && (w == Monday || w == Tuesday))) && m == December)
                    // Boxing Day (possibly moved to MONDAY or TUESDAY)
                    || ((d == 26 || (d == 28 && (w == Monday || w == Tuesday))) && m == December)
                    // June 3rd, 2002 only (Golden Jubilee Bank Holiday)
                    // June 4rd, 2002 only (special Spring Bank Holiday)
                    || ((d == 3 || d == 4) && m == June && y == 2002)
                    // DECEMBER 31st, 1999 only
                    || (d == 31 && m == December && y == 1999)) {
                return false;
            }
            return true;
        }
    }

    private final class ExchangeImpl extends WesternImpl {
        @Override
        public String name() {
            return "London stock exchange";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
            // New Year's Day (possibly moved to MONDAY)
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == January)
                    // Good Friday
                    || (dd == em - 3)
                    // Easter MONDAY
                    || (dd == em)
                    // first MONDAY of MAY (Early MAY Bank Holiday)
                    || (d <= 7 && w == Monday && m == May)
                    // last MONDAY of MAY (Spring Bank Holiday)
                    || (d >= 25 && w == Monday && m == May && y != 2002)
                    // last MONDAY of AUGUST (Summer Bank Holiday)
                    || (d >= 25 && w == Monday && m == August)
                    // Christmas (possibly moved to MONDAY or TUESDAY)
                    || ((d == 25 || (d == 27 && (w == Monday || w == Tuesday))) && m == December)
                    // Boxing Day (possibly moved to MONDAY or TUESDAY)
                    || ((d == 26 || (d == 28 && (w == Monday || w == Tuesday))) && m == December)
                    // JUNE 3rd, 2002 only (Golden Jubilee Bank Holiday)
                    // JUNE 4rd, 2002 only (special Spring Bank Holiday)
                    || ((d == 3 || d == 4) && m == June && y == 2002)
                    // DECEMBER 31st, 1999 only
                    || (d == 31 && m == December && y == 1999)) {
                return false;
            }
            return true;
        }
    }

    private final class MetalsImpl extends WesternImpl {
        @Override
        public String name() {
            return "London metals exchange";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
            // New Year's Day (possibly moved to MONDAY)
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == January)
                    // Good Friday
                    || (dd == em - 3)
                    // Easter MONDAY
                    || (dd == em)
                    // first MONDAY of MAY (Early MAY Bank Holiday)
                    || (d <= 7 && w == Monday && m == May)
                    // last MONDAY of MAY (Spring Bank Holiday)
                    || (d >= 25 && w == Monday && m == May && y != 2002)
                    // last MONDAY of AUGUST (Summer Bank Holiday)
                    || (d >= 25 && w == Monday && m == August)
                    // Christmas (possibly moved to MONDAY or TUESDAY)
                    || ((d == 25 || (d == 27 && (w == Monday || w == Tuesday))) && m == December)
                    // Boxing Day (possibly moved to MONDAY or TUESDAY)
                    || ((d == 26 || (d == 28 && (w == Monday || w == Tuesday))) && m == December)
                    // JUNE 3rd, 2002 only (Golden Jubilee Bank Holiday)
                    // JUNE 4rd, 2002 only (special Spring Bank Holiday)
                    || ((d == 3 || d == 4) && m == June && y == 2002)
                    // DECEMBER 31st, 1999 only
                    || (d == 31 && m == December && y == 1999)) {
                return false;
            }
            return true;
        }
    }
}