/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2008 Dominik Holenstein

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
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.October;
import static org.jquantlib.time.Weekday.Friday;
import static org.jquantlib.time.Weekday.Monday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 *  Argentinian calendars
 *  Holidays for the Buenos Aires stock exchange
 *       (data from <http://www.merval.sba.com.ar/>):
 *       <ul>
 *      <li>Saturdays</li>
 *       <li>Sundays</li>
 *       <li>New Year's Day, JANUARY 1st</li>
 *       <li>Holy Thursday</li>
 *       <li>Good Friday</li>
 *       <li>Labour Day, May 1st</li>
 *       <li>May Revolution, May 25th</li>
 *       <li>Death of General Manuel Belgrano, third Monday of June</li>
 *       <li>Independence Day, July 9th</li>
 *       <li>Death of General Jos� de San Mart�n, third Monday of August</li>
 *       <li>Columbus Day, October 12th (moved to preceding Monday if
 *           on Tuesday or Wednesday and to following if on Thursday
 *           or Friday)</li>
 *       <li>Immaculate Conception, December 8th</li>
 *       <li>Christmas Eve, December 24th</li>
 *       <li>New Year's Eve, December 31th</li>
 *       </ul>
 *      \ingroup calendars
 *
 * @author Srinivas Hasti
 * @author Dominik Holenstein
 * @author Richard Gomes
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Argentina extends Calendar {

    public static enum Market {
        /**
         * Buenos Aires stock exchange calendar
         */
        MERVAL
    };


    //
    // public constructors
    //

    public Argentina() {
		this(Market.MERVAL);
	}

	public Argentina(final Market m) {
		impl = new MervalImpl();
    }


    //
    // private final inner classes
    //

	private final class MervalImpl extends WesternImpl {

		@Override
		public String name() { return "Buenos Aires stock exchange"; }

		@Override
        public boolean  isBusinessDay(final Date date) {
        	final Weekday w = date.weekday();
			final int d = date.dayOfMonth(), dd = date.dayOfYear();
			final Month m = date.month();
			final int y = date.year();
			final int em = easterMonday(y);

			if (isWeekend(w)
			// New Year's Day
					|| (d == 1 && m == January)
					// Holy Thursday
					|| (dd == em - 4)
					// Good Friday
					|| (dd == em - 3)
					// Labour Day
					|| (d == 1 && m == May)
					// May Revolution
					|| (d == 25 && m == May)
					// Death of General Manuel Belgrano
					|| (d >= 15 && d <= 21 && w == Monday && m == June)
					// Independence Day
					|| (d == 9 && m == July)
					// Death of General Jos� de San Mart�n
					|| (d >= 15 && d <= 21 && w == Monday && m == August)
					// Columbus Day
					|| ((d == 10 || d == 11 || d == 12 || d == 15 || d == 16)
							&& w == Monday && m == October)
					// Immaculate Conception
					|| (d == 8 && m == December)
					// Christmas Eve
					|| (d == 24 && m == December)
					// New Year's Eve
					|| ((d == 31 || (d == 30 && w == Friday)) && m == December)) {
                return false;
            }
			return true;
		}
	}
}