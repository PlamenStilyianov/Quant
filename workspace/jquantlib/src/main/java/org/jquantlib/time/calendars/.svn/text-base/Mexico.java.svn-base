/*
 Copyright (c)  Q Boiler

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
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.March;
import static org.jquantlib.time.Month.May;
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
 * Mexican calendars Holidays for the Mexican stock exchange (data from <http://www.bmv.com.mx/>):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st</li>
 * <li>Constitution Day, February 5th</li>
 * <li>Birthday of Benito Juarez, March 21st</li>
 * <li>Holy Thursday</li>
 * <li>Good Friday</li>
 * <li>Labour Day, May 1st</li>
 * <li>National Day, September 16th</li>
 * <li>Our Lady of Guadalupe, December 12th</li>
 * <li>Christmas, December 25th</li>
 * </ul>
 *
 * @category calendars
 * @see <a href="http://www.bmv.com.mx/">Bolsa Mexicana de Valores</a>
 *
 * @author Q Boiler
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })
public class Mexico extends Calendar {

    public enum Market {
        /**
         * Mexican stock exchange
         */
        BMV
    };

    //
    // public constructors
    //

    public Mexico() {
        this(Market.BMV);
    }

    public Mexico(final Market m) {
        switch (m) {
        case BMV:
            impl = new BmvImpl();
            break;
        default:
            throw new LibraryException(UNKNOWN_MARKET);
        }
    }

    //
    // private final inner classes
    //

    private final class BmvImpl extends WesternImpl {

        @Override
        public String name() {
            return "Mexican stock exchange";
        }

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
                    // Constitution Day
                    || (d == 5 && m == February)
                    // Birthday of Benito Juarez
                    || (d == 21 && m == March)
                    // Holy Thursday
                    || (dd == em - 4)
                    // Good Friday
                    || (dd == em - 3)
                    // Labour Day
                    || (d == 1 && m == May)
                    // National Day
                    || (d == 16 && m == September)
                    // Our Lady of Guadalupe
                    || (d == 12 && m == December)
                    // Christmas
                    || (d == 25 && m == December)) {
                return false;
            }
            return true;
        }
    }
}
