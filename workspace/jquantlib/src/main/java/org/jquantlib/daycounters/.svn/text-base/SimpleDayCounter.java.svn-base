/*
 Copyright (C) 2007 Richard Gomes

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
import org.jquantlib.time.Date;

/**
 * Simple day counter for reproducing theoretical calculations.
 * <p>
 * This day counter tries to ensure that whole-month distances are returned as a
 * simple fraction, i.e., 1 year = 1.0, 6 months = 0.5, 3 months = 0.25 and so
 * forth.
 *
 * @note This day counter should be used together with NullCalendar, which
 *       ensures that dates at whole-month distances share the same day of
 *       month. It is <b>not</b> guaranteed to work with any other calendar.
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
@QualityAssurance(quality=Quality.Q4_UNIT, version=Version.V097, reviewers="Richard Gomes")
public class SimpleDayCounter extends DayCounter {


    public SimpleDayCounter() {
        super.impl = new Impl();
    }


    //
    // private inner classes
    //

    final private class Impl extends DayCounter.Impl {

        private final DayCounter fallback = new Thirty360();

        //
        // implements DayCounter
        //

        @Override
        protected final String name() /* @ReadOnly */{
            return "Simple";
        }

        @Override
        protected long dayCount(final Date dateStart, final Date dateEnd) /* @ReadOnly */ {
            return fallback.dayCount(dateStart, dateEnd);
        }

        @Override
        protected /*@Time*/ final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            final int dm1 = dateStart.dayOfMonth();
            final int dm2 = dateEnd.dayOfMonth();
            final int mm1 = dateStart.month().value();
            final int mm2 = dateEnd.month().value();
            final int yy1 = dateStart.year();
            final int yy2 = dateEnd.year();

            if (dm1 == dm2 ||
                    // e.g., Aug 30 -> Feb 28 ?
                    (dm1 > dm2 && Date.isEndOfMonth(dateEnd)) ||
                    // e.g., Feb 28 -> Aug 30 ?
                    (dm1 < dm2 && Date.isEndOfMonth(dateStart)))
                return (yy2 - yy1) + (mm2 - mm1) / 12.0;
            else
                return fallback.yearFraction(dateStart, dateEnd, refPeriodStart, refPeriodEnd);
        }

    }

}
