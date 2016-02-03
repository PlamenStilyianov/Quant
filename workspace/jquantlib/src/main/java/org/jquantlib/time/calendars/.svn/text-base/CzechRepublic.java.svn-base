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
 Copyright (C) 2005, 2007 StatPro Italia srl

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.
 */

package org.jquantlib.time.calendars;

import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.July;
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
 * Czech calendars Holidays for the Prague stock exchange (see
 * http://www.pse.cz/):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Easter Monday</li>
 * <li>Labour Day, May 1st</li>
 * <li>Liberation Day, May 8th</li>
 * <li>SS. Cyril and Methodius, July 5th</li>
 * <li>Jan Hus Day, July 6th</li>
 * <li>Czech Statehood Day, September 28th</li>
 * <li>Independence Day, October 28th</li>
 * <li>Struggle for Freedom and Democracy Day, November 17th</li>
 * <li>Christmas Eve, December 24th</li>
 * <li>Christmas, December 25th</li>
 * <li>St. Stephen, December 26th</li>
 * </ul>
 *  \in group calendars
 *
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class CzechRepublic extends Calendar {

	public static enum Market {
	    /**
	     * Prague stock exchange
	     */
		PSE
	}


	//
	// public constructors
	//

	public CzechRepublic() {
		this(Market.PSE);
	}

	public CzechRepublic(final Market m) {
		switch (m) {
		case PSE:
			impl = new PseImpl();
			break;
		default:
			throw new LibraryException(UNKNOWN_MARKET);
		}
	}


    //
    // private final inner classes
    //

	private final class PseImpl extends WesternImpl {

		@Override
		public String name() {
			return "Prague stock exchange of CzechRepublic";
		}

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
					// Easter Monday
					|| (dd == em)
					// Labour Day
					|| (d == 1 && m == May)
					// Liberation Day
					|| (d == 8 && m == May)
					// SS. Cyril and Methodius
					|| (d == 5 && m == July)
					// Jan Hus Day
					|| (d == 6 && m == July)
					// Czech Statehood Day
					|| (d == 28 && m == September)
					// Independence Day
					|| (d == 28 && m == October)
					// Struggle for Freedom and Democracy Day
					|| (d == 17 && m == November)
					// Christmas Eve
					|| (d == 24 && m == December)
					// Christmas
					|| (d == 25 && m == December)
					// St. Stephen
					|| (d == 26 && m == December)
					// unidentified closing days for stock exchange
					|| (d == 2 && m == January && y == 2004)
					|| (d == 31 && m == December && y == 2004)) {
                return false;
            }
			return true;
		}
	}
}
