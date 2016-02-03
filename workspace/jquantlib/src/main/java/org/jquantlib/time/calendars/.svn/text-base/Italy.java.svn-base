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

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;

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
 * Italian calendars Public holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Epiphany, JANUARY 6th</li>
 * <li>Easter Monday</li>
 * <li>Liberation Day, April 25th</li>
 * <li>Labour Day, May 1st</li>
 * <li>Republic Day, June 2nd (since 2000)</li>
 * <li>Assumption, August 15th</li>
 * <li>All Saint's Day, November 1st</li>
 * <li>Immaculate Conception Day, December 8th</li>
 * <li>Christmas Day, December 25th</li>
 * <li>St. Stephen's Day, December 26th</li>
 * </ul>
 *
 * Holidays for the stock exchange (data from http://www.borsaitalia.it):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Good Friday</li>
 * <li>Easter Monday</li>
 * <li>Labour Day, May 1st</li>
 * <li>Assumption, August 15th</li>
 * <li>Christmas' Eve, December 24th</li>
 * <li>Christmas, December 25th</li>
 * <li>St. Stephen, December 26th</li>
 * <li>New Year's Eve, December 31st</li>
 * </ul>
 *
 * @test the correctness of the returned results is tested against a list of known holidays.
 *
 * @category calendars
 * @see <a href="http://www.borsaitalia.it">Borsa Italiana</a>
 *
 * @author Srinivas Hasti
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class Italy extends Calendar {

    public static enum Market {
        /**
         * Generic settlement calendar
         */
        Settlement,

        /**
         * Milan stock-exchange calendar
         */
        Exchange
    }


    //
    // public constructors
    //

    public Italy() {
        this(Market.Settlement);
    }

    public Italy(final Market market) {
        switch (market) {
        case Settlement:
            impl = new SettlementImpl();
            break;
        case Exchange:
            impl = new ExchangeImpl();
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
            return "Italian settlement";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
            // New Year's Day
                    || (d == 1 && m == January)
                    // Epiphany
                    || (d == 6 && m == January)
                    // Easter Monday
                    || (dd == em)
                    // Liberation Day
                    || (d == 25 && m == April)
                    // Labour Day
                    || (d == 1 && m == May)
                    // Republic Day
                    || (d == 2 && m == June && y >= 2000)
                    // Assumption
                    || (d == 15 && m == August)
                    // All Saints' Day
                    || (d == 1 && m == November)
                    // Immaculate Conception
                    || (d == 8 && m == December)
                    // Christmas
                    || (d == 25 && m == December)
                    // St. Stephen
                    || (d == 26 && m == December)
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
            return "Milan stock exchange";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
            // New Year's Day
                    || (d == 1 && m == January)
                    // Good Friday
                    || (dd == em - 3)
                    // Easter Monday
                    || (dd == em)
                    // Labour Day
                    || (d == 1 && m == May)
                    // Assumption
                    || (d == 15 && m == August)
                    // Christmas' Eve
                    || (d == 24 && m == December)
                    // Christmas
                    || (d == 25 && m == December)
                    // St. Stephen
                    || (d == 26 && m == December)
                    // New Year's Eve
                    || (d == 31 && m == December)) {
                return false;
            }
            return true;
        }
    }
}
