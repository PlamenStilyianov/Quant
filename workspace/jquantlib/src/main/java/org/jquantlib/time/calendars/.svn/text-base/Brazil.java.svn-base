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

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Weekday.Friday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

//  Brazilian calendar
/**
 * Banking holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Tiradentes's Day, April 21th</li>
 * <li>Labour Day, May 1st</li>
 * <li>Independence Day, September 21th</li>
 * <li>Nossa Sra. Aparecida Day, October 12th</li>
 * <li>All Souls Day, November 2nd</li>
 * <li>Republic Day, November 15th</li>
 * <li>Black Awareness Day, November 20th</li>
 * <li>Christmas, December 25th</li>
 * <li>Passion of Christ</li>
 * <li>Carnival</li>
 * <li>Corpus Christi</li>
 * </ul>
 *
 * Holidays for the Bovespa stock exchange
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Sao Paulo City Day, JANUARY 25th</li>
 * <li>Tiradentes's Day, April 21th</li>
 * <li>Labour Day, May 1st</li>
 * <li>Revolution Day, July 9th</li>
 * <li>Independence Day, September 21th</li>
 * <li>Nossa Sra. Aparecida Day, October 12th</li>
 * <li>All Souls Day, November 2nd</li>
 * <li>Republic Day, November 15th</li>
 * <li>Black Awareness Day, November 20th (since 2007)</li>
 * <li>Christmas, December 25th</li>
 * <li>Passion of Christ</li>
 * <li>Carnival</li>
 * <li>Corpus Christi</li>
 * <li>the last business day of the year</li>
 * </ul>
 *
 * @author Srinivas Hasti
 * @author Dominik Holenstein
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Richard Gomes" })
public class Brazil extends Calendar {

    /**
     * Brazil calendars
     */
    public static enum Market {
        /**
         * Generic settlement calendar
         */
        SETTLEMENT,

        /**
         * Bolsa de Valores de Sao Paulo
         */
        BOVESPA
    }


    //
    // public constructors
    //

    public Brazil() {
        this(Market.SETTLEMENT);
    }

    public Brazil(final Market market) {
        switch (market) {
        case SETTLEMENT:
            impl = new SettlementImpl();
            break;
        case BOVESPA:
            impl = new ExchangeImpl();
            break;
        default:
            throw new LibraryException(UNKNOWN_MARKET);
        }
    }


    //
    // private final inner classes
    //

    private final class SettlementImpl extends Calendar.WesternImpl {

        @Override
        public String name() {
            return "Brazil";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth();
            final Month m = date.month();
            final int y = date.year();
            final int dd = date.dayOfYear();
            final int em = easterMonday(y);

            if (isWeekend(w)
                    // New Year's Day
                    || (d == 1 && m == Month.January)
                    // Tiradentes Day
                    || (d == 21 && m == Month.April)
                    // Labor Day
                    || (d == 1 && m == Month.May)
                    // Independence Day
                    || (d == 7 && m == Month.September)
                    // Nossa Sra. Aparecida Day
                    || (d == 12 && m == Month.October)
                    // All Souls Day
                    || (d == 2 && m == Month.November)
                    // Republic Day
                    || (d == 15 && m == Month.November)
                    // Christmas
                    || (d == 25 && m == Month.December)
                    // Passion of Christ
                    || (dd == em - 3)
                    // Carnival
                    || (dd == em - 49 || dd == em - 48)
                    // Corpus Christi
                    || (dd == em + 59)) {
                return false;
            }
            return true;
        }

    }

    private final class ExchangeImpl extends Calendar.WesternImpl {

        @Override
        public String name() {
            return "BOVESPA";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth();
            final Month m = date.month();
            final int y = date.year();
            final int dd = date.dayOfYear();
            final int em = easterMonday(y);

            if (isWeekend(w)
                    // New Year's Day
                    || (d == 1 && m == January)
                    // Sao Paulo City Day
                    || (d == 25 && m == January)
                    // Tiradentes Day
                    || (d == 21 && m == April)
                    // Labor Day
                    || (d == 1 && m == May)
                    // Revolution Day
                    || (d == 9 && m == July)
                    // Nossa Sra. Aparecida Day
                    // || (d == 12 && m == OCTOBER)-> not closed at the 12th October
                    // All Souls Day
                    // || (d == 2 && m == NOVEMBER) -> not closed at the 2nd
                    // November
                    // Republic Day
                    // || (d == 15 && m == NOVEMBER) -> not closed at the 15th
                    // November
                    // Black Awareness Day
                    || (d == 20 && m == November && y >= 2007)
                    // Christmas Eve
                    || (d == 24 && m == December)
                    // Christmas
                    || (d == 25 && m == December)
                    // Passion of Christ / Good Friday
                    || (dd == em - 3)
                    // Carnival
                    || (dd == em - 49 || dd == em - 48)
                    // Corpus Christi
                    || (dd == em + 59)
                    // last business day of the year
                    || (m == December && (d == 31 || (d >= 29 && w == Friday)))) {
                return false;
            }
            return true;
        }
    }

}
