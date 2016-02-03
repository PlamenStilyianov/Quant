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

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Danish calendar
 * Holidays:
 *       <ul>
 *       <li>Saturdays</li>
 *       <li>Sundays</li>
 *      <li>Maunday Thursday</li>
 *       <li>Good Friday</li>
 *       <li>Easter Monday</li>
 *       <li>General Prayer Day, 25 days after Easter Monday</li>
 *       <li>Ascension</li>
 *       <li>Whit (Pentecost) Monday </li>
 *       <li>New Year's Day, JANUARY 1st</li>
 *       <li>Constitution Day, June 5th</li>
 *       <li>Christmas, December 25th</li>
 *       <li>Boxing Day, December 26th</li>
 *       </ul>
 *
 *       in group calendars
 *
 * @author Jia Jia
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Denmark extends Calendar {

    //
    // public constructors
    //

	public Denmark() {
		impl = new Impl();
	}


    //
    // private final inner classes
    //

    private final class Impl extends WesternImpl {

    	@Override
	    public String name() { return "Denmark"; }

    	@Override
	    public boolean isBusinessDay(final Date date)  {
	        final Weekday w = date.weekday();
	        final int d = date.dayOfMonth(), dd = date.dayOfYear();
	        final Month m = date.month();
	        final int y = date.year();
	        final int em = easterMonday(y);
	        if (isWeekend(w)
	            // Maunday Thursday
	            || (dd == em-4)
	            // Good Friday
	            || (dd == em-3)
	            // Easter Monday
	            || (dd == em)
	            // General Prayer Day
	            || (dd == em+25)
	            // Ascension
	            || (dd == em+38)
	            // Whit Monday
	            || (dd == em+49)
	            // New Year's Day
	            || (d == 1  && m == January)
	            // Constitution Day, June 5th
	            || (d == 5  && m == June)
	            // Christmas
					|| (d == 25 && m == December)
	            // Boxing Day
	            || (d == 26 && m == December)

	            // below added according to http://nordic.nasdaqomxtrader.com/trading/tradinghours/
                // Christmas eve
                || (d == 24 && m == December && (y == 2008 || y == 2009 || y == 2007))
                // new year eve
                || (d == 31 && m ==December && (y == 2008 || y == 2009 || y == 2007))

                || (d == 22 && m == May && y == 2009)) {
                return false;
            }
	        return true;
	    }
    }
}
