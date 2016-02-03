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

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Weekday.Monday;
import static org.jquantlib.time.Weekday.Tuesday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 *
 * New Zealand calendar
 * Holidays:
 *       <ul>
 *       <li>Saturdays</li>
 *       <li>Sundays</li>
 *       <li>New Year's Day, JANUARY 1st (possibly moved to Monday or
 *           Tuesday)</li>
 *       <li>Day after New Year's Day, JANUARY 2st (possibly moved to
 *           Monday or Tuesday)</li>
 *       <li>Anniversary Day, Monday nearest JANUARY 22nd</li>
 *       <li>Waitangi Day. February 6th</li>
 *       <li>Good Friday</li>
 *       <li>Easter Monday</li>
 *       <li>ANZAC Day. April 25th</li>
 *       <li>Queen's Birthday, first Monday in June</li>
 *       <li>Labour Day, fourth Monday in October</li>
 *       <li>Christmas, December 25th (possibly moved to Monday or Tuesday)</li>
 *       <li>Boxing Day, December 26th (possibly moved to Monday or
 *           Tuesday)</li>
 *       </ul>
 *@note    note The holiday rules for New Zealand were documented by
 *             David Gilbert for IDB (http://www.jrefinery.com/ibd/)
 *
 * @category calendars
 *
 * @see <a href="http://www.nzx.com">New Zealand Stock Exchange</a>
 *
 * @author Anand Mani
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class NewZealand extends Calendar {


    //
    // public constructors
    //

    public NewZealand() {
        impl = new Impl();
    }


    //
    // private final inner classees
    //

    private final class Impl extends WesternImpl {
	    @Override
	    public String name() { return "New Zealand"; }

	    @Override
	    public boolean isBusinessDay(final Date date) {
	        final Weekday w = date.weekday();
	        final int d = date.dayOfMonth(), dd = date.dayOfYear();
	        final Month m = date.month();
	        final int y = date.year();
	        final int em = easterMonday(y);
	        if (isWeekend(w)
	            // New Year's Day (possibly moved to Monday or Tuesday)
	            || ((d == 1 || (d == 3 && (w == Monday || w == Tuesday))) &&
	                m == January)
	            // Day after New Year's Day (possibly moved to Mon or TUESDAY)
	            || ((d == 2 || (d == 4 && (w == Monday || w == Tuesday))) &&
	                m == January)
	            // Anniversary Day, MONDAY nearest JANUARY 22nd
	            || ((d >= 19 && d <= 25) && w == Monday && m == January)
	            // Waitangi Day. February 6th
	            || (d == 6 && m == February)
	            // Good Friday
	            || (dd == em-3)
	            // Easter MONDAY
	            || (dd == em)
	            // ANZAC Day. April 25th
	            || (d == 25 && m == April)
	            // Queen's Birthday, first MONDAY in June
	            || (d <= 7 && w == Monday && m == June)
	            // Labour Day, fourth MONDAY in October
	            || ((d >= 22 && d <= 28) && w == Monday && m == October)
	            // Christmas, December 25th (possibly MONDAY or TUESDAY)
	            || ((d == 25 || (d == 27 && (w == Monday || w == Tuesday)))
	                && m == December)
	            // Boxing Day, DECEMBER 26th (possibly MONDAY or TUESDAY)
	            || ((d == 26 || (d == 28 && (w == Monday || w == Tuesday)))
	                && m == December)) {
                return false;
            }
	        return true;
	    }
	}
}
