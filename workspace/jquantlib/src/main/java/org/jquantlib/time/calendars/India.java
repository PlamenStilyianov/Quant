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

import static org.jquantlib.time.Month.April;
import static org.jquantlib.time.Month.August;
import static org.jquantlib.time.Month.December;
import static org.jquantlib.time.Month.February;
import static org.jquantlib.time.Month.January;
import static org.jquantlib.time.Month.March;
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
 *  Indian calendars
 *  Holidays for the National Stock Exchange
 *   (data from <http://www.nse-india.com/>):
 *   <ul>
 *   <li>Saturdays</li>
 *   <li>Sundays</li>
 *   <li>Republic Day, JANUARY 26th</li>
 *   <li>Good Friday</li>
 *   <li>Ambedkar Jayanti, April 14th</li>
 *   <li>Independence Day, August 15th</li>
 *   <li>Gandhi Jayanti, October 2nd</li>
 *   <li>Christmas, December 25th</li>
 *   </ul>
 *
 *   Other holidays for which no rule is given (data available for
 *   2005-2008 only:)
 *   <ul>
 *   <li>Bakri Id</li>
 *   <li>Moharram</li>
 *   <li>Mahashivratri</li>
 *   <li>Holi</li>
 *   <li>Ram Navami</li>
 *   <li>Mahavir Jayanti</li>
 *   <li>Id-E-Milad</li>
 *   <li>Maharashtra Day</li>
 *   <li>Buddha Pournima</li>
 *   <li>Ganesh Chaturthi</li>
 *   <li>Dasara</li>
 *   <li>Laxmi Puja</li>
 *   <li>Bhaubeej</li>
 *   <li>Ramzan Id</li>
 *   <li>Guru Nanak Jayanti</li>
 *   </ul>
 *   in group calendars
 *
 *   TBD:This implementation has hoildays only up to year 2008.
 *
 * @category calendars
 * @see <a href="http://www.nse-india.com/">National Stock Exchange of India</a>
 *
 * @author Renjith Nair
 * @author Zahid Hussain
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class India extends Calendar {

	public  static enum Market {
	    /**
	     * National Stock Exchange
	     */
	    NSE
        }


	//
	// public constructor
	//

	public India() {
    	this(Market.NSE);
    }
    public India(final Market m) {
    	switch(m) {
    		case NSE:
    			impl = new NseImpl();
    			break;
    	     default:
    	        throw new LibraryException(UNKNOWN_MARKET);
    		}
    }


    //
    // private final inner classes
    //

    private final class NseImpl extends WesternImpl {

		@Override
		public String name () { return "National Stock Exchange of India"; }

		@Override
		public boolean isBusinessDay(final Date date) {
	        final Weekday w = date.weekday();
	        final int d = date.dayOfMonth();
	        final Month m = date.month();
	        final int y = date.year();
	        final int dd = date.dayOfYear();
	        final int em = easterMonday(y);

	        if (isWeekend(w)
	            // Republic Day
	            || (d == 26 && m == January)
	            // Good Friday
	            || (dd == em-3)
	            // Ambedkar Jayanti
	            || (d == 14 && m == April)
	            // Independence Day
	            || (d == 15 && m == August)
	            // Gandhi Jayanti
	            || (d == 2 && m == October)
	            // Christmas
	            || (d == 25 && m == December)
	            ) {
                return false;
            }
	        if (y == 2005) {
	            // Moharram, Holi, Maharashtra Day, and Ramzan Id fall
	            // on Saturday or Sunday in 2005
	            if (// Bakri Id
	                (d == 21 && m == January)
	                // Ganesh Chaturthi
	                || (d == 7 && m == September)
	                // Dasara
	                || (d == 12 && m == October)
	                // Laxmi Puja
	                || (d == 1 && m == November)
	                // Bhaubeej
	                || (d == 3 && m == November)
	                // Guru Nanak Jayanti
	                || (d == 15 && m == November)
	                ) {
                    return false;
                }
	        }
	        if (y == 2006) {
	            if (// Bakri Id
	                (d == 11 && m == January)
	                // Moharram
	                || (d == 9 && m == February)
	                // Holi
	                || (d == 15 && m == March)
	                // Ram Navami
	                || (d == 6 && m == April)
	                // Mahavir Jayanti
	                || (d == 11 && m == April)
	                // Maharashtra Day
	                || (d == 1 && m == May)
	                // Bhaubeej
	                || (d == 24 && m == October)
	                // Ramzan Id
	                || (d == 25 && m == October)
	                ) {
                    return false;
                }
	        }
	        if (y == 2007) {
	            if (// Bakri Id
	                (d == 1 && m == January)
	                // Moharram
	                || (d == 30 && m == January)
	                // Mahashivratri
	                || (d == 16 && m == February)
	                // Ram Navami
	                || (d == 27 && m == March)
	                // Maharashtra Day
	                || (d == 1 && m == May)
	                // Buddha Pournima
	                || (d == 2 && m == May)
	                // Laxmi Puja
	                || (d == 9 && m == November)
	                // Bakri Id (again)
	                || (d == 21 && m == December)
	                ) {
                    return false;
                }
	        }
	        if (y == 2008) {
	            if (// Mahashivratri
	                (d == 6 && m == March)
	                // Id-E-Milad
	                || (d == 20 && m == March)
	                // Mahavir Jayanti
	                || (d == 18 && m == April)
	                // Maharashtra Day
	                || (d == 1 && m == May)
	                // Buddha Pournima
	                || (d == 19 && m == May)
	                // Ganesh Chaturthi
	                || (d == 3 && m == September)
	                // Ramzan Id
	                || (d == 2 && m == October)
	                // Dasara
	                || (d == 9 && m == October)
	                // Laxmi Puja
	                || (d == 28 && m == October)
	                // Bhau bhij
	                || (d == 30 && m == October)
	                // Gurunanak Jayanti
	                || (d == 13 && m == November)
	                // Bakri Id
	                || (d == 9 && m == December)
	                ) {
                    return false;
                }
	        }
	        return true;
		}
    }
}
