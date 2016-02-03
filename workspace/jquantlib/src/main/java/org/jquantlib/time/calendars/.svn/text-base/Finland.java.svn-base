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

import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Weekday.Friday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Finnish calendar
 * Holidays:
 *      <ul>
 *       <li>Saturdays</li>
 *       <li>Sundays</li>
 *       <li>New Year's Day, JANUARY 1st</li>
 *       <li>Epiphany, JANUARY 6th</li>
 *       <li>Good Friday</li>
 *       <li>Easter Monday</li>
 *       <li>Ascension Thursday</li>
 *       <li>Labour Day, May 1st</li>
 *       <li>Midsummer Eve (Friday between June 18-24)</li>
 *       <li>Independence Day, December 6th</li>
 *       <li>Christmas Eve, December 24th</li>
 *       <li>Christmas, December 25th</li>
 *       <li>Boxing Day, December 26th</li>
 *       </ul>
 *       in group calendars
 *
 * @author Heng Joon Tiang
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Finland extends Calendar {

    //
    // public constructors
    //

	public Finland() {
		impl = new Impl();
	}


    //
    // private final inner classes
    //

	private final class Impl extends WesternImpl {

		@Override
		public String name() { return "Finland";  }

		@Override
		public boolean isBusinessDay(final Date date) {
	        final Weekday w = date.weekday();
	        final int d = date.dayOfMonth();
	        final int dd = date.dayOfYear();
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
	            // Ascension Thursday
	            || (dd == em+38)
	            // Labour Day
	            || (d == 1 && m == May)
	            // Midsummer Eve (Friday between June 18-24)
	            || (w == Friday && (d >= 18 && d <= 24) && m == June)
	            // Independence Day
	            || (d == 6 && m == December)
	            // Christmas Eve
	            || (d == 24 && m == December)
	            // Christmas
	            || (d == 25 && m == December)
	            // Boxing Day
	            || (d == 26 && m == December)) {
                return false;
            }
	        return true;
		}
    }
}
