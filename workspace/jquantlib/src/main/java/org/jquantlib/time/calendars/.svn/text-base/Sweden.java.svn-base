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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.
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

/** Holidays for Sweden
 *  <ul>
 *  <li>Saturdays</li>
 *  <li>Sundays</li>
 *  <li>New Year's Day, JANUARY 1st</li>
 *  <li>Epiphany, JANUARY 6th</li>
 *  <li>Good Friday</li>
 *  <li>Easter Monday</li>
 *  <li>Ascension</li>
 *  <li>Whit(Pentecost) Monday </li>
 *  <li>May Day, May 1st</li>
 *  <li>National Day, June 6th</li>
 *  <li>Midsummer Eve (Friday between June 18-24)</li>
 *  <li>Christmas Eve, December 24th</li>
 *  <li>Christmas Day, December 25th</li>
 *  <li>Boxing Day, December 26th</li>
 *  <li>New Year's Eve, December 31th</li>
 *  </ul>
 *  @author Renjith Nair
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Sweden extends Calendar {

    //
    // public constructors
    //

    public Sweden() {
        impl = new Impl();
    }

    //
    // private final inner classes
    //

	private final class Impl extends WesternImpl {
	       @Override
	        public String name() { return "Sweden"; }

	        @Override
	        public boolean isBusinessDay(final Date date) {
	            final Weekday w = date.weekday();
	            final int d = date.dayOfMonth(), dd = date.dayOfYear();
	            final Month m = date.month();
	            final int y = date.year();
	            final int em = easterMonday(y);
	            if (isWeekend(w)
	                // Good Friday
	                || (dd == em-3)
	                // Easter Monday
	                || (dd == em)
	                // Ascension Thursday
	                || (dd == em+38)
	                // Whit Monday
	                || (dd == em+49)
	                // New Year's Day
	                || (d == 1  && m == January)
	                // Epiphany
	                || (d == 6  && m == January)
	                // May Day
	                || (d == 1  && m == May)
	                // June 6 id National Day but is not a holiday.
	                // It has been debated wheter or not this day should be
	                // declared as a holiday.
	                // As of 2002 the Stockholmborsen is open that day
	                // || (d == 6  && m == June)
	                // Midsummer Eve (Friday between June 18-24)
	                || (w == Friday && (d >= 18 && d <= 24) && m == June)
	                // Christmas Eve
	                || (d == 24 && m == December)
	                // Christmas Day
	                || (d == 25 && m == December)
	                // Boxing Day
	                || (d == 26 && m == December)
	                // New Year's Eve
	                || (d == 31 && m == December)) {
                    return false;
                }
	            return true;
	        }
	}
}