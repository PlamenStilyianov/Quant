/*
 Copyright (C) 2008 Srinivas Hasti

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
import static org.jquantlib.time.Month.May;
import static org.jquantlib.time.Month.October;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/** German calendars
 * Public holidays:
 *   <ul>
 *   <li>Saturdays</li>
 *   <li>Sundays</li>
 *   <li>New Year's Day, JANUARY 1st</li>
 *   <li>Good Friday</li>
 *   <li>Easter Monday</li>
 *   <li>Ascension Thursday</li>
 *   <li>Whit Monday</li>
 *   <li>Corpus Christi</li>
 *   <li>Labour Day, May 1st</li>
 *   <li>National Day, October 3rd</li>
 *   <li>Christmas Eve, December 24th</li>
 *   <li>Christmas, December 25th</li>
 *   <li>Boxing Day, December 26th</li>
 *   <li>New Year's Eve, December 31st</li>
 *   </ul>
 *
 *   Holidays for the Frankfurt Stock exchange
 *   (data from http://deutsche-boerse.com/):
 *   <ul>
 *   <li>Saturdays</li>
 *   <li>Sundays</li>
 *   <li>New Year's Day, JANUARY 1st</li>
 *   <li>Good Friday</li>
 *   <li>Easter Monday</li>
 *   <li>Labour Day, May 1st</li>
 *   <li>Christmas' Eve, December 24th</li>
 *   <li>Christmas, December 25th</li>
 *   <li>Christmas Holiday, December 26th</li>
 *   <li>New Year's Eve, December 31st</li>
 *   </ul>
 *
 *   Holidays for the Xetra exchange
 *   (data from http://deutsche-boerse.com/):
 *   <ul>
 *   <li>Saturdays</li>
 *   <li>Sundays</li>
 *   <li>New Year's Day, JANUARY 1st</li>
 *   <li>Good Friday</li>
 *   <li>Easter Monday</li>
 *   <li>Labour Day, May 1st</li>
 *   <li>Christmas' Eve, December 24th</li>
 *   <li>Christmas, December 25th</li>
 *   <li>Christmas Holiday, December 26th</li>
 *   <li>New Year's Eve, December 31st</li>
 *   </ul>
 *
 *   Holidays for the Eurex exchange
 *   (data from http://www.eurexchange.com/index.html):
 *   <ul>
 *   <li>Saturdays</li>
 *   <li>Sundays</li>
 *   <li>New Year's Day, JANUARY 1st</li>
 *   <li>Good Friday</li>
 *   <li>Easter Monday</li>
 *   <li>Labour Day, May 1st</li>
 *   <li>Christmas' Eve, December 24th</li>
 *   <li>Christmas, December 25th</li>
 *   <li>Christmas Holiday, December 26th</li>
 *   <li>New Year's Eve, December 31st</li>
 *   </ul>
 *
 *   in group calendars
 *   <li>test the correctness of the returned results is tested
 *         against a list of known holidays.
 *
 * @author Srinivas Hasti
 * @author Zahid Hussain
 *
 */

@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain" })

public class Germany extends Calendar {

    /**
     * German calendars
     *
     */
    public static enum Market {
        /**
         * eneric settlement calendar
         */
        Settlement,

        /**
         * Frankfurt stock-exchange
         */
        FrankfurtStockExchange,

        /**
         * Xetra
         */
        Xetra,

        /**
         * Eurex
         */
        Eurex
    }


    //
    // public constructors
    //

    public Germany() {
    	this(Market.FrankfurtStockExchange);
    }
    public Germany(final Market market) {
            switch (market) {
              case Settlement:
                impl = new SettlementImpl();
                break;
              case FrankfurtStockExchange:
            	  impl = new FrankfurtStockExchangeImpl();
                break;
              case Xetra:
            	  impl = new XetraImpl();
                break;
              case Eurex:
            	  impl = new EurexImpl();
                break;
              default:
                  throw new LibraryException(UNKNOWN_MARKET);
            }
    }


    //
    // private final inner classes
    //

    private final class SettlementImpl extends WesternImpl {
        @Override
        public String name() { return "German settlement"; }

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
                // Easter Monday
                || (dd == em)
                // Ascension Thursday
                || (dd == em+38)
                // Whit Monday
                || (dd == em+49)
                // Corpus Christi
                || (dd == em+59)
                // Labour Day
                || (d == 1 && m == May)
                // National Day
                || (d == 3 && m == October)
                // Christmas Eve
                || (d == 24 && m == December)
                // Christmas
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

    private final class FrankfurtStockExchangeImpl extends WesternImpl {
        @Override
        public String name() { return "Frankfurt stock exchange"; }

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
					|| (dd == em - 3)
					// Easter Monday
					|| (dd == em)
					// Labour Day
					|| (d == 1 && m == May)
					// Christmas' Eve
					|| (d == 24 && m == December)
					// Christmas
					|| (d == 25 && m == December)
					// Christmas Day
					|| (d == 26 && m == December)
					// New Year's Eve
					|| (d == 31 && m == December)) {
                return false;
            }
			return true;
		}

    }

    private final class XetraImpl extends WesternImpl {

    	@Override
        public String name() { return "Xetra"; }

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
                // Easter Monday
                || (dd == em)
                // Labour Day
                || (d == 1 && m == May)
                // Christmas' Eve
                || (d == 24 && m == December)
                // Christmas
                || (d == 25 && m == December)
                // Christmas Day
                || (d == 26 && m == December)
                // New Year's Eve
                || (d == 31 && m == December)) {
                return false;
            }
            return true;
        }

    }

    private final class EurexImpl extends WesternImpl {

    	@Override
        public String name() { return "Eurex"; }

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
                // Easter Monday
                || (dd == em)
                // Labour Day
                || (d == 1 && m == May)
                // Christmas' Eve
                || (d == 24 && m == December)
                // Christmas
                || (d == 25 && m == December)
                // Christmas Day
                || (d == 26 && m == December)
                // New Year's Eve
                || (d == 31 && m == December)) {
                return false;
            }
            return true;
        }
    }
}
