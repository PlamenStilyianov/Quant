/*
 Copyright (C) 2008 Daniel Kong
 Copyright (C) 2008 Richard Gomes
 Copyright (C) 2009 John Nichol

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

package org.jquantlib.daycounters;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Brazil;

/**
 * Business/252 day count convention
 *
 * @see <a href="http://en.wikipedia.org/wiki/Day_count_convention">Day count Convention</a>
 *
 * @author Daniel Kong
 * @author Richard Gomes
 * @author John Nichol
 */
@QualityAssurance(quality=Quality.Q4_UNIT, version=Version.V097, reviewers="Richard Gomes")
public class Business252 extends DayCounter {


    public Business252() {
        this(new Brazil());
    }

    public Business252(final Calendar calendar) {
        super.impl = new Impl(calendar);
    }


    //
    // private inner classes
    //

    final private class Impl extends DayCounter.Impl {

        private final Calendar calendar;

        //
        // implements DayCounter
        //

        private Impl(final Calendar calendar) {
            this.calendar = calendar;
        }

        @Override
        public final String name() /* @ReadOnly */{
            return "Business/252(" + calendar.name() + ")";
        }

        @Override
        public long dayCount(final Date d1, final Date d2) {
        	return calendar.businessDaysBetween(d1, d2);
        }

        @Override
        public /*@Time*/ final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            return /*@Time*/ dayCount(dateStart, dateEnd) / 252.0;
        }

    }

}
