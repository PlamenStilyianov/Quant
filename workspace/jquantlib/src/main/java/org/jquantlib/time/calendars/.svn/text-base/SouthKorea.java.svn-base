/*
 Copyright (C) 2008 Jia Jia

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
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;
import static org.jquantlib.time.Weekday.Saturday;
import static org.jquantlib.time.Weekday.Sunday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * South Korean calendars Public holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Independence Day, March 1st</li>
 * <li>Arbour Day, April 5th (until 2005)</li>
 * <li>Labour Day, May 1st</li>
 * <li>Children's Day, May 5th</li>
 * <li>Memorial Day, June 6th</li>
 * <li>Constitution Day, July 17th (until 2007)</li>
 * <li>Liberation Day, August 15th</li>
 * <li>National Fondation Day, October 3th</li>
 * <li>Christmas Day, December 25th</li>
 * </ul>
 *
 * Other holidays for which no rule is given (data available for 2004-2010 only:)
 * <ul>
 * <li>Lunar New Year, the last day of the previous lunar year, JANUARY 1st, 2nd in lunar calendar</li>
 * <li>Election Days</li>
 * <li>National Assemblies</li>
 * <li>Presidency</li>
 * <li>Regional Election Days</li>
 * <li>Buddha's birthday, April 8th in lunar calendar</li>
 * <li>Harvest Moon Day, August 14th, 15th, 16th in lunar calendar</li>
 * </ul>
 *
 * Holidays for the Korea exchange (data from <http://www.krx.co.kr> or <http://www.dooriworld.com/daishin/holiday/holiday.html>):
 * <ul>
 * <li>Public holidays as listed above</li>
 * <li>Year-end closing</li>
 * </ul>
 *
 * @category Calendars
 *
 * @author Jia Jia
 * @author Zahid Hussain
 *
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class SouthKorea extends Calendar {

    public enum Market {
        /**
         * Public holidays
         */
        Settlement,

        /**
         * Korea Exchange
         */
        KRX
    }

    //
    // public constructors
    //

    public SouthKorea() {
        this(Market.KRX);
    }

    public SouthKorea(final Market m) {
        switch (m) {
        case Settlement:
            impl = new SettlementImpl();
            break;
        case KRX:
            impl = new KrxImpl();
            break;
        default:
            throw new LibraryException(UNKNOWN_MARKET);
        }
    }

    //
    // private inner classes
    //

    private class SettlementImpl extends Impl {

        @Override
        public String name() {
            return "South-Korean settlement";
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
                    // Independence Day
                    || (d == 1 && m == March)
                    // Arbour Day
                    || (d == 5 && m == April && y <= 2005)
                    // Labour Day
                    || (d == 1 && m == May)
                    // Children's Day
                    || (d == 5 && m == May)
                    // Memorial Day
                    || (d == 6 && m == June)
                    // Constitution Day
                    || (d == 17 && m == July && y <= 2007)
                    // Liberation Day
                    || (d == 15 && m == August)
                    // National Foundation Day
                    || (d == 3 && m == October)
                    // Christmas Day
                    || (d == 25 && m == December)

                    // Lunar New Year
                    || ((d == 21 || d == 22 || d == 23) && m == January && y == 2004)
                    || ((d == 8 || d == 9 || d == 10) && m == February && y == 2005)
                    || ((d == 28 || d == 29 || d == 30) && m == January && y == 2006)
                    || (d == 19 && m == February && y == 2007)
                    || ((d == 6 || d == 7 || d == 8) && m == February && y == 2008)
                    || ((d == 25 || d == 26 || d == 27) && m == January && y == 2009)
                    || ((d == 13 || d == 14 || d == 15) && m == February && y == 2010)
                    // Election Day 2004
                    || (d == 15 && m == April && y == 2004) // National Assembly
                    || (d == 31 && m == May && y == 2006) // Regional election
                    || (d == 19 && m == December && y == 2007) // Presidency
                    || (d == 9 && m == April && y == 2008)
                    // Buddha's birthday
                    || (d == 26 && m == May && y == 2004)
                    || (d == 15 && m == May && y == 2005)
                    || (d == 5 && m == May && y == 2006)
                    || (d == 24 && m == May && y == 2007)
                    || (d == 12 && m == May && y == 2008)
                    || (d == 2 && m == May && y == 2009)
                    || (d == 21 && m == May && y == 2010)
                    // Harvest Moon Day
                    || ((d == 27 || d == 28 || d == 29) && m == September && y == 2004)
                    || ((d == 17 || d == 18 || d == 19) && m == September && y == 2005)
                    || ((d == 5 || d == 6 || d == 7) && m == October && y == 2006)
                    || ((d == 24 || d == 25 || d == 26) && m == September && y == 2007)
                    || ((d == 13 || d == 14 || d == 15) && m == September && y == 2008)
                    || ((d == 2 || d == 3 || d == 4) && m == October && y == 2009)
                    || ((d == 21 || d == 22 || d == 23) && m == September && y == 2010)) {
                return false;
            }
            return true;
        }
    }

    private class KrxImpl extends SettlementImpl {
        @Override
        public String name() {
            return "South-Korea exchange";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            // public holidays
            if (!super.isBusinessDay(date)) {
                return false;
            }

            final int d = date.dayOfMonth();
            final Month m = date.month();
            final int y = date.year();

            if (// Year-end closing
            (d == 31 && m == December && y == 2004) || (d == 30 && m == December && y == 2005)
                    || (d == 29 && m == December && y == 2006) || (d == 31 && m == December && y == 2007)) {
                return false;
            }

            return true;
        }
    }
}
