/*
 Copyright (C) 2008 Joon Tiang

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
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.June;
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.November;
import static org.jquantlib.time.Month.October;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * Singaporean calendar
 * <p>
 * Holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's day, JANUARY 1st</li>
 * <li>Good Friday</li>
 * <li>Labour Day, May 1st</li>
 * <li>National Day, August 9th</li>
 * <li>Christmas, December 25th</li>
 * <p>
 * Other holidays for which no rule is given (data available for 2004-2008
 * only:)
 * </p>
 * <li>Chinese New Year</li>
 * <li>Hari Raya Haji</li>
 * <li>Vesak Day</li>
 * <li>Deepavali</li>
 * <li>Diwali</li>
 * <li>Hari Raya Puasa</li>
 * </ul>
 *
 * @category calendars
 *
 * @see <a href="http://www.ses.com.sg/">Stock Exchange of Singapore</a>
 *
 * @author Joon Tiang
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Singapore extends Calendar {
    public enum Market {
        /**
         * Singapore Exchange
         */
        SGX
    };

    //
    // public constructors
    //

    public Singapore(){
    	this(Market.SGX);
    }

    public Singapore(final Market m){
    	impl = new SgxImpl();
    }

    //
    // private final inner classes
    //

    private final class SgxImpl extends WesternImpl {
    	@Override
    	public String name() { return "Singapore exchange"; }

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
                  // Good Friday
                  || (dd == em-3)
                  // Labor Day
                  || (d == 1 && m == May)
                  // National Day
                  || (d == 9 && m == August)
                  // Christmas Day
                  || (d == 25 && m == December)

                  // Chinese New Year
                  || ((d == 22 || d == 23) && m == January && y == 2004)
                  || ((d == 9 || d == 10) && m == February && y == 2005)
                  || ((d == 30 || d == 31) && m == January && y == 2006)
                  || ((d == 19 || d == 20) && m == February && y == 2007)
                  || ((d == 7 || d == 8) && m == February && y == 2008)
                  || ((d == 26 || d == 27) && m == January && y == 2009) //Zahid

                  // Hari Raya Haji
                  || ((d == 1 || d == 2) && m == February && y == 2004)
                  || (d == 21 && m == January && y == 2005)
                  || (d == 10 && m == January && y == 2006)
                  || (d == 2 && m == January && y == 2007)
                  || (d == 20 && m == December && y == 2007)
                  || (d == 8 && m == December && y == 2008)

                  // Vesak Poya Day
                  || (d == 2 && m == June && y == 2004)
                  || (d == 22 && m == May && y == 2005)
                  || (d == 12 && m == May && y == 2006)
                  || (d == 31 && m == May && y == 2007)
                  || (d == 18 && m == May && y == 2008)

                  // Deepavali
                  || (d == 11 && m == November && y == 2004)
                  || (d == 8 && m == November && y == 2007)
                  || (d == 28 && m == October && y == 2008)
                  || (d == 27 && m == November && y == 2009) //Zahid

                  // Diwali
                  || (d == 1 && m == November && y == 2005)

                  // Hari Raya Puasa
                  || ((d == 14 || d == 15) && m == November && y == 2004)
                  || (d == 3 && m == November && y == 2005)
                  || (d == 24 && m == October && y == 2006)
                  || (d == 13 && m == October && y == 2007)
                  || (d == 1 && m == October && y == 2008)
                  ) {
                return false;
            }
              return true;
          }
    }
}