/*
 Copyright (C) 2008 Siju Odeyemi

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
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Weekday.Monday;
import static org.jquantlib.time.Weekday.Thursday;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Icelandic calendars
 * Holidays for the Iceland stock exchange
 *       (data from <http://www.icex.is/is/calendar?languageID=1>):
 *        <ul>
 *        <li>Saturdays</li>
 *       <li>Sundays</li>
 *       <li>New Year's Day, JANUARY 1st (possibly moved to Monday)</li>
 *       <li>Holy Thursday</li>
 *       <li>Good Friday</li>
 *       <li>Easter Monday</li>
 *       <li>First day of Summer (third or fourth Thursday in April)</li>
 *       <li>Labour Day, May 1st</li>
 *       <li>Ascension Thursday</li>
 *       <li>Pentecost Monday</li>
 *       <li>Independence Day, June 17th</li>
 *       <li>Commerce Day, first Monday in August</li>
 *       <li>Christmas, December 25th</li>
 *       <li>Boxing Day, December 26th</li>
 *       </ul>
 *
 *       \ingroup calendars

 * @author Siju Odeyemi
 * @author Zahid Hussain
 */


@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Iceland extends Calendar {

    public static enum Market {
        /**
         * Iceland stock exchange
         */
        ICEX
    }


    //
    // public constructors
    //

   public Iceland() {
     this(Market.ICEX);
    }

    public Iceland(final Market m) {
    	switch (m) {
    	case ICEX:
    		impl = new IcexImpl();
    		break;
    	 default:
             throw new LibraryException(UNKNOWN_MARKET);
    	}
    }


    //
    // private final inner classes
    //

	private final class IcexImpl extends WesternImpl {

		@Override
		public String name () { return "Iceland stock exchange"; }

		@Override
		public boolean isBusinessDay(final Date date) {
              final Weekday w = date.weekday();
              final int d = date.dayOfMonth(), dd = date.dayOfYear();
              final Month m = date.month();
              final int y = date.year();
              final int em = easterMonday(y);
              if (isWeekend(w)
                  // New Year's Day (possibly moved to Monday)
                  || ((d == 1 || ((d == 2 || d == 3) && w == Monday))
                      && m == January)
                  // Holy Thursday
                  || (dd == em-4)
                  // Good Friday
                  || (dd == em-3)
                  // Easter MONDAY
                  || (dd == em)
                  // First day of Summer
                  || (d >= 19 && d <= 25 && w == Thursday && m == April)
                  // Ascension THURSDAY
                  || (dd == em+38)
                  // Pentecost MONDAY
                  || (dd == em+49)
                  // Labour Day
                  || (d == 1 && m == May)
                  // Independence Day
                  || (d == 17 && m == June)
                  // Commerce Day
                  || (d <= 7 && w == Monday && m == August)
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
