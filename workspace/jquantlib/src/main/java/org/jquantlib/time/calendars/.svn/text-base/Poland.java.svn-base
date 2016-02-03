/*
 Copyright (C) 2008 Anand Mani

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
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Polish calendar
 * <p>
 * Holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>Easter Monday</li>
 * <li>Corpus Christi</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>May Day, May 1st</li>
 * <li>Constitution Day, May 3rd</li>
 * <li>Assumption of the Blessed Virgin Mary, August 15th</li>
 * <li>All Saints Day, November 1st</li>
 * <li>Independence Day, November 11th</li>
 * <li>Christmas, December 25th</li>
 * <li>2nd Day of Christmas, December 26th</li>
 * </ul>
 *
 * @category calendars
 *
 * @see <a href="http://www.gpw.pl/">Warsaw Stock Exchange</a>
 *
 * @author Anand Mani
 * @author Renjith Nair
 * @author Richard Gomes
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Poland extends Calendar {

    //
    // public constructors
    //

	public Poland() {
		impl = new Impl();
	}


    //
    // private final inner classes
    //

	private final class Impl extends WesternImpl {
        @Override
		public String name() { return "Poland"; }

        @Override
        public boolean isBusinessDay(final Date date) {
	        final Weekday w = date.weekday();
	        final int d = date.dayOfMonth(), dd = date.dayOfYear();
	        final Month m = date.month();
	        final int y = date.year();
	        final int em = easterMonday(y);
	        if (isWeekend(w)
	            // Easter Monday
	            || (dd == em)
	            // Corpus Christi
	            || (dd == em+59)
	            // New Year's Day
	            || (d == 1  && m == January)
	            // May Day
	            || (d == 1  && m == May)
	            // Constitution Day
	            || (d == 3  && m == May)
	            // Assumption of the Blessed Virgin Mary
	            || (d == 15  && m == August)
	            // All Saints Day
	            || (d == 1  && m == November)
	            // Independence Day
	            || (d ==11  && m == November)
	            // Christmas
	            || (d == 25 && m == December)
	            // 2nd Day of Christmas
	            || (d == 26 && m == December)) {
                return false;
            }
	        return true;
        }
	}
}
