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
 Copyright (C) 2005, 2007 StatPro Italia srl

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.
 */

package org.jquantlib.time.calendars;

import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Weekday.Monday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * ! Holidays for the Ukrainian stock exchange (data from <http://www.ukrse.kiev.ua/eng/>):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Orthodox Christmas, JANUARY 7th</li>
 * <li>International Women's Day, March 8th</li>
 * <li>Easter Monday</li>
 * <li>Holy Trinity Day, 50 days after Easter</li>
 * <li>International Workers Solidarity Days, May 1st and 2nd</li>
 * <li>Victory Day, May 9th</li>
 * <li>Constitution Day, June 28th</li>
 * <li>Independence Day, August 24th</li>
 * </ul>
 * Holidays falling on a Saturday or Sunday are moved to the following Monday.
 *
 * @author Renjith Nair
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class Ukraine extends Calendar {
    public static enum Market {
        /**
         * Ukrainian Stock Exchange
         */
        USE
    }

    //
    // public constructors
    //

    public Ukraine() {
        this(Market.USE);
    }

    public Ukraine(final Market m) {
        impl = new UseImpl();
    }

    //
    // private final inner classes
    //

    private final class UseImpl extends OrthodoxImpl {

        @Override
        public String name() {
            return "Ukrainian stock exchange";
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
                    // Orthodox Christmas
                    || ((d == 7 || ((d == 8 || d == 9) && w == Monday)) && m == January)
                    // Women's Day
                    || ((d == 8 || ((d == 9 || d == 10) && w == Monday)) && m == March)
                    // Orthodox Easter MONDAY
                    || (dd == em)
                    // Holy Trinity Day
                    || (dd == em + 49)
                    // Workers' Solidarity Days
                    || ((d == 1 || d == 2 || (d == 3 && w == Monday)) && m == May)
                    // Victory Day
                    || ((d == 9 || ((d == 10 || d == 11) && w == Monday)) && m == May)
                    // Constitution Day
                    || (d == 28 && m == June)
                    // Independence Day
                    || (d == 24 && m == August)) {
                return false;
            }
            return true;
        }
    }
}