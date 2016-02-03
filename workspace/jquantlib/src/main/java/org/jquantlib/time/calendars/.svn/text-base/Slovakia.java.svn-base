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

import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.September;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Slovak calendars
 * <p>
 * Holidays for the Bratislava stock exchange (data from <http://www.bsse.sk/>):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Epiphany, JANUARY 6th</li>
 * <li>Good Friday</li>
 * <li>Easter Monday</li>
 * <li>May Day, May 1st</li>
 * <li>Liberation of the Republic, May 8th</li>
 * <li>SS. Cyril and Methodius, July 5th</li>
 * <li>Slovak National Uprising, August 29th</li>
 * <li>Constitution of the Slovak Republic, September 1st</li>
 * <li>Our Lady of the Seven Sorrows, September 15th</li>
 * <li>All Saints Day, November 1st</li>
 * <li>Freedom and Democracy of the Slovak Republic, November 17th</li>
 * <li>Christmas Eve, December 24th</li>
 * <li>Christmas, December 25th</li>
 * <li>St. Stephen, December 26th</li>
 * </ul>
 * @category calendars
 * @author Richard Gomes
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Slovakia extends Calendar {
    public enum Market {
        /**
         * Bratislava stock exchange
         */
        BSSE
    }

    //
    // public constructors
    //

    public Slovakia() {
    	this(Market.BSSE);
    }
    public Slovakia(final Market m) {
    	impl = new BsseImpl();
    }


    //
    // private final inner classes
    //

    private final class BsseImpl extends WesternImpl  {
    	@Override
    	public String name() { return "Bratislava stock exchange"; }

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
                // Good Friday
                || (dd == em-3)
                // Easter Monday
                || (dd == em)
                // May Day
                || (d == 1 && m == May)
                // Liberation of the Republic
                || (d == 8 && m == May)
                // SS. Cyril and Methodius
                || (d == 5 && m == July)
                // Slovak National Uprising
                || (d == 29 && m == August)
                // Constitution of the Slovak Republic
                || (d == 1 && m == September)
                // Our Lady of the Seven Sorrows
                || (d == 15 && m == September)
                // All Saints Day
                || (d == 1 && m == November)
                // Freedom and Democracy of the Slovak Republic
                || (d == 17 && m == November)
                // Christmas Eve
                || (d == 24 && m == December)
                // Christmas
                || (d == 25 && m == December)
                // St. Stephen
                || (d == 26 && m == December)
                // unidentified closing days for stock exchange
                || (d >= 24 && d <= 31 && m == December && y == 2004)
                || (d >= 24 && d <= 31 && m == December && y == 2005)) {
                return false;
            }
            return true;
        }
    }
}
