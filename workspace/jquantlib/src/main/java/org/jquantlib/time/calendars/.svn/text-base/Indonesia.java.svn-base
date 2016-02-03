/*
 Copyright (C) 2008

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
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Month.September;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Indonesian calendars Holidays for the Jakarta stock exchange (data from <http://www.jsx.co.id/>):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Good Friday</li>
 * <li>Ascension of Jesus Christ</li>
 * <li>Independence Day, August 17th</li>
 * <li>Christmas, December 25th</li>
 * </ul>
 *
 * Other holidays for which no rule is given (data available for 2005-2008 only:)
 * <ul>
 * <li>Idul Adha</li>
 * <li>Ied Adha</li>
 * <li>Imlek</li>
 * <li>Moslem's New Year Day</li>
 * <li>Chinese New Year</li>
 * <li>Nyepi (Saka's New Year)</li>
 * <li>Birthday of Prophet Muhammad SAW</li>
 * <li>Waisak</li>
 * <li>Ascension of Prophet Muhammad SAW</li>
 * <li>Idul Fitri</li>
 * <li>Ied Fitri</li>
 * <li>Other national leaves</li>
 * </ul>
 *
 * @category calendars
 * @see <a href="http://www.idx.co.id/">Indonesia Stock Exchange</a>
 *
 * @author Joon Tiang
 * @author Jia Jia
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class Indonesia extends Calendar {

    public static enum Market {
        /**
         * Jakarta stock exchange
         */
        BEJ,

        /**
         * Jakarta stock exchange
         */
        JSX
    }

    //
    // public constructors
    //

    public Indonesia() {
        this(Market.BEJ);
    }

    public Indonesia(final Market market) {
        switch (market) {
        case BEJ:
        case JSX:
            impl = new BejImpl();
            break;
        default:
            throw new LibraryException(UNKNOWN_MARKET);
        }
    }


    //
    // private final inner classes
    //

    private final class BejImpl extends WesternImpl {

        @Override
        public String name() {
            return "Jakarta stock exchange";
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
                    // Good Friday
                    || (dd == em - 3)
                    // Ascension Thursday
                    || (dd == em + 38)
                    // Independence Day
                    || (d == 17 && m == August)
                    // Christmas
                    || (d == 25 && m == December)) {
                return false;
            }
            if (y == 2005) {
                if (// Idul Adha
                (d == 21 && m == January)
                // Imlek
                        || (d == 9 && m == February)
                        // Moslem's New Year Day
                        || (d == 10 && m == February)
                        // Nyepi
                        || (d == 11 && m == March)
                        // Birthday of Prophet Muhammad SAW
                        || (d == 22 && m == April)
                        // Waisak
                        || (d == 24 && m == May)
                        // Ascension of Prophet Muhammad SAW
                        || (d == 2 && m == September)
                        // Idul Fitri
                        || ((d == 3 || d == 4) && m == November)
                        // National leaves
                        || ((d == 2 || d == 7 || d == 8) && m == November) || (d == 26 && m == December)) {
                    return false;
                }
            }
            if (y == 2006) {
                if (// Idul Adha
                (d == 10 && m == January)
                // Moslem's New Year Day
                        || (d == 31 && m == January)
                        // Nyepi
                        || (d == 30 && m == March)
                        // Birthday of Prophet Muhammad SAW
                        || (d == 10 && m == April)
                        // Ascension of Prophet Muhammad SAW
                        || (d == 21 && m == August)
                        // Idul Fitri
                        || ((d == 24 || d == 25) && m == October)
                        // National leaves
                        || ((d == 23 || d == 26 || d == 27) && m == October)) {
                    return false;
                }
            }
            if (y == 2007) {
                if (// Nyepi
                (d == 19 && m == March)
                        // Waisak
                        || (d == 1 && m == June)
                        // Ied Adha
                        || (d == 20 && m == December)
                        // National leaves
                        || (d == 18 && m == May) || ((d == 12 || d == 15 || d == 16) && m == October)
                        || ((d == 21 || d == 24) && m == October)) {
                    return false;
                }
            }
            if (y == 2007) {
                if (// Islamic New Year
                ((d == 10 || d == 11) && m == January)
                // Chinese New Year
                        || ((d == 7 || d == 8) && m == February)
                        // Saka's New Year
                        || (d == 7 && m == March)
                        // Birthday of the prophet Muhammad SAW
                        || (d == 20 && m == March)
                        // Vesak Day
                        || (d == 20 && m == May)
                        // Isra' Mi'raj of the prophet Muhammad SAW
                        || (d == 30 && m == July)
                        // Ied Fitr
                        || (d == 30 && m == September) || ((d == 1 || d == 2 || d == 3) && m == October)
                        // Ied Adha
                        || (d == 8 && m == December) // Zahid: it is Saturday, should not be here
                        // Islamic New Year
                        || (d == 29 && m == December) // Zahid: it is Saturday, should not be here
                        // New Year's Eve
                        || (d == 31 && m == December)
                        // National leave
                        || (d == 18 && m == August)) {
                    return false;
                }
            }

            // New holidays: QL97 has only upto year 2007 ////

            if (y == 2008) {
                if (
                // Islamic New Year 1429 H
                (d == 10 && m == January)
                // National Leave
                        || (d == 11 && m == January)
                        // Chinese New Year
                        || (d == 7 && m == February)
                        // Trading Holiday
                        || (d == 8 && m == February)
                        // Saka's New Year
                        || (d == 7 && m == March)
                        // Birthday of the prophet Muhammad
                        || (d == 20 && m == March)
                        // Vesak Day
                        || (d == 20 && m == May)
                        // Isra' Mi'raj of the prophet Muhammad
                        || (d == 30 && m == July)
                        // National Leave
                        || (d == 18 && m == August) || (d == 30 && m == September)
                        // Ied Fitr 1 Syawal
                        || (d == 1 && m == October) || (d == 2 && m == October)
                        // National Leave
                        || (d == 3 && m == October)
                        // Ied Adha
                        || (d == 8 && m == December)
                        // Islamic New Year
                        || (d == 29 && m == December)
                        // New Year's Eve
                        || (d == 31 && m == December)) {
                    return false;
                }
            }
            if (y == 2009) {
                if (
                // Public Holiday
                (d == 2 && m == January)
                // Chinese New Year
                        || (d == 26 && m == January)
                        // Saka's New Year
                        || (d == 26 && m == March)
                        // Birthday of the prophet Muhammad
                        || (d == 9 && m == March)
                        // Isra' Mi'raj of the prophet Muhammad
                        || (d == 20 && m == July)
                        // Public Holiday
                        || (d == 18 && m == September) || (d == 23 && m == September)
                        // Ied Fitr 1 Syawal
                        || (d == 21 && m == September) || (d == 22 && m == September)
                        // Ied Adha
                        || (d == 27 && m == November)
                        // Islamic New Year
                        || (d == 18 && m == December)
                        // Public Holiday
                        || (d == 24 && m == December)
                        // Trading Holiday
                        || (d == 31 && m == December)) {
                    return false;
                }
            }
            return true;
        }
    }
}
