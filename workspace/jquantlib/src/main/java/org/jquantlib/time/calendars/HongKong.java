/*
 Copyright (C) 2008 Richard Gomes

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
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;
import static org.jquantlib.time.Weekday.Monday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Hong Kong calendars Holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday)</li>
 * <li>Ching Ming Festival, April 5th</li>
 * <li>Good Friday</li>
 * <li>Easter Monday</li>
 * <li>Labor Day, May 1st</li>
 * <li>SAR Establishment Day, July 1st (possibly moved to Monday)</li>
 * <li>National Day, October 1st (possibly moved to Monday)</li>
 * <li>Christmas, December 25th</li>
 * <li>Boxing Day, December 26th (possibly moved to Monday)</li>
 * </ul>
 *
 * Other holidays for which no rule is given (data available for 2004-2007 only:)
 * <ul>
 * <li>Lunar New Year</li>
 * <li>Chinese New Year</li>
 * <li>Buddha's birthday</li>
 * <li>Tuen NG Festival</li>
 * <li>Mid-autumn Festival</li>
 * <li>Chung Yeung Festival</li>
 * </ul>
 *
 * Data from <http://www.hkex.com.hk>
 *
 * ingroup calendars
 *
 * @author Richard Gomes
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class HongKong extends Calendar {

    public static enum Market {
        /**
         * Hong Kong stock exchange
         */
        HKEx
    }

    //
    // public constructor
    //

    public HongKong() {
        this(Market.HKEx);
    }

    public HongKong(final Market m) {
        switch (m) {
        case HKEx:
            impl = new HkexImpl();
            break;

        default:
            throw new LibraryException(UNKNOWN_MARKET);
        }
    }


    //
    // private final inner classes
    //

    private final class HkexImpl extends WesternImpl {
        @Override
        public String name() {
            return "Hong Kong stock exchange";
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
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == January)
                    // Ching Ming Festival
                    || (d == 5 && m == April)
                    // Good Friday
                    || (dd == em - 3)
                    // Easter MONDAY
                    || (dd == em)
                    // Labor Day
                    || (d == 1 && m == May)
                    // SAR Establishment Day
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == July)
                    // National Day
                    || ((d == 1 || ((d == 2 || d == 3) && w == Monday)) && m == October)
                    // Christmas Day
                    || (d == 25 && m == December)
                    // Boxing Day
                    || ((d == 26 || ((d == 27 || d == 28) && w == Monday)) && m == December)) {
                return false;
            }

            if (y == 2004) {
                if (// Lunar New Year
                ((d == 22 || d == 23 || d == 24) && m == January)
                // Buddha's birthday
                        || (d == 26 && m == May)
                        // Tuen NG festival
                        || (d == 22 && m == June)
                        // Mid-autumn festival
                        || (d == 29 && m == September)
                        // Chung Yeung
                        || (d == 29 && m == September)) {
                    return false;
                }
            }

            if (y == 2005) {
                if (// Lunar New Year
                ((d == 9 || d == 10 || d == 11) && m == February)
                // Buddha's birthday
                        || (d == 16 && m == May)
                        // Tuen NG festival
                        || (d == 11 && m == June)
                        // Mid-autumn festival
                        || (d == 19 && m == September)
                        // Chung Yeung festival
                        || (d == 11 && m == October)) {
                    return false;
                }
            }

            if (y == 2006) {
                if (// Lunar New Year
                ((d >= 28 && d <= 31) && m == January)
                // Buddha's birthday
                        || (d == 5 && m == May)
                        // Tuen NG festival
                        || (d == 31 && m == May)
                        // Mid-autumn festival
                        || (d == 7 && m == October)
                        // Chung Yeung festival
                        || (d == 30 && m == October)) {
                    return false;
                }
            }

            if (y == 2007) {
                if (// Lunar New Year
                ((d >= 17 && d <= 20) && m == February)
                // Buddha's birthday
                        || (d == 24 && m == May)
                        // Tuen NG festival
                        || (d == 19 && m == June)
                        // Mid-autumn festival
                        || (d == 26 && m == September)
                        // Chung Yeung festival
                        || (d == 19 && m == October)) {
                    return false;
                }
            }

            if (y == 2008) {
                if (// Lunar New Year
                ((d >= 7 && d <= 9) && m == February)
                // Ching Ming Festival
                        || (d == 4 && m == April)
                        // Buddha's birthday
                        || (d == 12 && m == May)
                        // Tuen NG festival
                        || (d == 9 && m == June)
                        // Mid-autumn festival
                        || (d == 15 && m == September)
                        // Chung Yeung festival
                        || (d == 7 && m == October)) {
                    return false;
                }
            }

            return true;
        }
    }
}
