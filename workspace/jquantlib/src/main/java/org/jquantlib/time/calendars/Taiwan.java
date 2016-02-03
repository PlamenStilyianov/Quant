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

package org.jquantlib.time.calendars;

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
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
 * Taiwanese calendars Holidays for the Taiwan stock exchange
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Peace Memorial Day, February 28</li>
 * <li>Labor Day, May 1st</li>
 * <li>Double Tenth National Day, October 10th</li>
 * </ul>
 *
 * Other holidays for which no rule is given (data available for 2002-2007
 * only:)
 * <ul>
 * <li>Chinese Lunar New Year</li>
 * <li>Tomb Sweeping Day</li>
 * <li>Dragon Boat Festival</li>
 * <li>Moon Festival</li>
 * </ul>
 *
 * @category calendars
 *
 * @see <a href="http://www.tse.com.tw/en/trading/trading_days.php">Taiwan Stock Exchange</a>
 *
 * @author Renjith Nair
 * @author Jia Jia
 *
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Taiwan extends Calendar {

    public static enum Market {
        /**
         * Taiwan Stock Exchange
         */
        TSEC
    }

    //
    // public constructors
    //

    public Taiwan() {
    	this(Market.TSEC);
    }

    public Taiwan(final Market m) {
    	impl = new TsecImpl();
    }

    //
    // private final inner classes
    //

    private final class TsecImpl extends Impl {
    	@Override
    	public String name()  { return "Taiwan stock exchange"; }

    	@Override
    	public boolean isWeekend(final Weekday w)  {
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
                // Peace Memorial Day
                || (d == 28 && m == February)
                // Labor Day
                || (d == 1 && m == May)
                // Double Tenth
                || (d == 10 && m == October)
                ) {
                return false;
            }

            if (y == 2002) {
                // Dragon Boat Festival and Moon Festival fall on Saturday
                if (// Chinese Lunar New Year
                    (d >= 9 && d <= 17 && m == February)
                    // Tomb Sweeping Day
                    || (d == 5 && m == April)
                    ) {
                    return false;
                }
            }
            if (y == 2003) {
                // Tomb Sweeping Day falls on Saturday
                if (// Chinese Lunar New Year
                    ((d >= 31 && m == January) || (d <= 5 && m == February))
                    // Dragon Boat Festival
                    || (d == 4 && m == June)
                    // Moon Festival
                    || (d == 11 && m == September)
                    ) {
                    return false;
                }
            }
            if (y == 2004) {
                // Tomb Sweeping Day falls on Sunday
                if (// Chinese Lunar New Year
                    (d >= 21 && d <= 26 && m == January)
                    // Dragon Boat Festival
                    || (d == 22 && m == June)
                    // Moon Festival
                    || (d == 28 && m == September)
                    ) {
                    return false;
                }
            }
            if (y == 2005) {
                // Dragon Boat and Moon Festival fall on Saturday or Sunday
                if (// Chinese Lunar New Year
                    (d >= 6 && d <= 13 && m == February)
                    // Tomb Sweeping Day
                    || (d == 5 && m == April)
                    // make up for Labor Day, not seen in other years
                    || (d == 2 && m == May)
                    ) {
                    return false;
                }
            }
            if (y == 2006) {
                // Dragon Boat and Moon Festival fall on Saturday or Sunday
                if (// Chinese Lunar New Year
                    ((d >= 28 && m == January) || (d <= 5 && m == February))
                    // Tomb Sweeping Day
                    || (d == 5 && m == April)
                    // Dragon Boat Festival
                    || (d == 31 && m == May)
                    // Moon Festival
                    || (d == 6 && m == October)
                    ) {
                    return false;
                }
            }
            if (y == 2007) {
                if (// Chinese Lunar New Year
                    (d >= 17 && d <= 25 && m == February)
                    // Tomb Sweeping Day
                    || (d == 5 && m == April)
                    // adjusted holidays
                    || (d == 6 && m == April)
                    || (d == 18 && m == June)
                    // Dragon Boat Festival
                    || (d == 19 && m == June)
                    // adjusted holiday
                    || (d == 24 && m == September)
                    // Moon Festival
                    || (d == 25 && m == September)
                    ) {
                    return false;
                }
            }
            if (y == 2008) {
                if (// Chinese Lunar New Year
                    (d >= 4 && d <= 11 && m == February)
                    // Tomb Sweeping Day
                    || (d == 4 && m == April)
                    ) {
                    return false;
                }
            }
            return true;
        }
    }
}
