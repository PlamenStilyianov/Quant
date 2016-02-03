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
/*
 Copyright (C) 2005 Sercan Atalik

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.
 */

package org.jquantlib.time.calendars;

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;
import static org.jquantlib.time.Weekday.Saturday;
import static org.jquantlib.time.Weekday.Sunday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Holidays for the National Stock Exchange
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>National Holidays (April 23rd, May 19th, August 30th, October 29th</li>
 * <li>Local Holidays (Kurban, Ramadan; 2004 to 2009 only)</li>
 * </ul>
 *
 * @category Calendars
 * @author Renjith Nair
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class Turkey extends Calendar {

    //
    // public constructors
    //

    public Turkey() {
        impl = new TurkeyImpl();
    }

    //
    // private final inner classes
    //

    private final class TurkeyImpl extends Impl {

        @Override
        public String name() {
            return "Turkey";
        }

        @Override
        public boolean isWeekend(final Weekday w) {
            return w == Saturday || w == Sunday;
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth();
            final Month m = date.month();
            final int y = date.year();

            if (isWeekend(w)
            // New Year's Day
                    || (d == 1 && m == January)
                    // 23 nisan / National Holiday
                    || (d == 23 && m == April)
                    // 19 may/ National Holiday
                    || (d == 19 && m == May)
                    // 30 aug/ National Holiday
                    || (d == 30 && m == August)
                    // /29 ekim National Holiday
                    || (d == 29 && m == October)) {
                return false;
            }

            // Local Holidays
            if (y == 2004) {
                // kurban
                if ((m == February && d <= 4)
                // ramazan
                        || (m == November && d >= 14 && d <= 16)) {
                    return false;
                }
            } else if (y == 2005) {
                // kurban
                if ((m == January && d >= 19 && d <= 21)
                // ramazan
                        || (m == November && d >= 2 && d <= 5)) {
                    return false;
                }
            } else if (y == 2006) {
                // kurban
                if ((m == January && d >= 9 && d <= 13)
                // ramazan
                        || (m == October && d >= 23 && d <= 25)
                        // kurban
                        || (m == December && d >= 30)) {
                    return false;
                }
            } else if (y == 2007) {
                // kurban
                if ((m == January && d <= 4)
                // ramazan
                        || (m == October && d >= 11 && d <= 14)
                        // kurban
                        || (m == December && d >= 19 && d <= 23)) {
                    return false;
                }
            } else if (y == 2008) {
                // ramazan
                if ((m == September && d >= 29) || (m == October && d <= 2)
                // kurban
                        || (m == December && d >= 7 && d <= 11)) {
                    return false;
                }
            }
            return true;
        }
    }
}