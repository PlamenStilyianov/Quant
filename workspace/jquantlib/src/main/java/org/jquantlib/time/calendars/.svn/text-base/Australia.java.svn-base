/*
 Copyright (C) 2008 Tim Swetonic

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
 * Australian calendar
 * <p>
 * Holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Australia Day, JANUARY 26th (possibly moved to MONDAY)</li>
 * <li>Good Friday</li>
 * <li>Easter MONDAY</li>
 * <li>ANZAC Day. April 25th (possibly moved to MONDAY)</li>
 * <li>Queen's Birthday, second MONDAY in June</li>
 * <li>Bank Holiday, first MONDAY in August</li>
 * <li>Labour Day, first MONDAY in October</li>
 * <li>Christmas, December 25th (possibly moved to MONDAY or TUESDAY)</li>
 * <li>Boxing Day, December 26th (possibly moved to MONDAY or TUESDAY)</li>
 * </ul>
 * @author Tim Swetonic
 * @author Richard Gomes
 *
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Australia extends Calendar {

    //
    // public constructors
    //

	public Australia() {
        impl =  new AustraliaImpl();
    }


    //
    // private final inner classes
    //

	private final class AustraliaImpl extends WesternImpl {

	  @Override
	  public String name() { return "Australia"; }

	  @Override
	  public boolean isBusinessDay(final Date date)  {
        final Weekday w = date.weekday();
        final int d = date.dayOfMonth(), dd = date.dayOfYear();
        final Month m = date.month();
        final int y = date.year();
        final int em = easterMonday(y);
        if (isWeekend(w)
            // New Year's Day (possibly moved to Monday)
            || (d == 1  && m == January)
            // Australia Day, JANUARY 26th (possibly moved to Monday)
            || ((d == 26 || ((d == 27 || d == 28) && w == Monday)) &&
                m == January)
            // Good Friday
            || (dd == em-3)
            // Easter Monday
            || (dd == em)
            // ANZAC Day, April 25th (possibly moved to Monday)
            || ((d == 25 || (d == 26 && w == Monday)) && m == April)
            // Queen's Birthday, second Monday in June
            || ((d > 7 && d <= 14) && w == Monday && m == June)
            // Bank Holiday, first Monday in August
            || (d <= 7 && w == Monday && m == August)
            // Labour Day, first Monday in October
            || (d <= 7 && w == Monday && m == October)
            // Christmas, December 25th (possibly Monday or Tuesday)
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
