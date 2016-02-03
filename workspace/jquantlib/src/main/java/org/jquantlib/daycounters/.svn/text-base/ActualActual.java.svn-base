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

package org.jquantlib.daycounters;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;

/**
 * Implementation of ActualActual day counters.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Day_count_convention">Day count convention</a>
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 *
 */
@QualityAssurance(quality=Quality.Q4_UNIT, version=Version.V097, reviewers="Richard Gomes")
public class ActualActual extends DayCounter {

    //
    // public enums
    //

    /**
     * Actual/Actual Calendar Conventions
     */
    public static enum Convention {
        ISMA, Bond,
        ISDA, Historical, Actual365,
        AFB, Euro
    }


    //
    // public constructors
    //

    public ActualActual() {
        this(Convention.ISDA);
    }

    public ActualActual(final ActualActual.Convention c) {
        switch (c) {
            case ISMA:
            case Bond:
                super.impl = new ImplISMA();
                break;
            case ISDA:
            case Historical:
            case Actual365:
                super.impl = new ImplISDA();
                break;
            case AFB:
            case Euro:
                super.impl = new ImplAFB();
                break;
            default:
                throw new LibraryException("unknown act/act convention"); // TODO: message
        }
    }


    //
    // inner classes
    //

    final private class ImplISMA extends DayCounter.Impl {

        @Override
        public final String name() /* @ReadOnly */{
            return "Actual/Actual (ISMA)";
        }

        @Override
        public final double yearFraction(
                final Date d1, final Date d2,
                final Date d3, final Date d4) /* @ReadOnly */{

            if (d1.equals(d2))
                return 0.0;

            if (d1.gt(d2))
                return -yearFraction(d2, d1, d3, d4);

            // when the reference period is not specified, try taking
            // it equal to (d1,d2)
            Date refPeriodStart = (!d3.isNull() ? d3 : d1);
            Date refPeriodEnd   = (!d4.isNull() ? d4 : d2);

            QL.ensure(refPeriodEnd.gt(refPeriodStart) && refPeriodEnd.gt(d1) , "invalid reference period");  // TODO: message

            // estimate roughly the length in months of a period
            int months = (int) (0.5 + 12 * (refPeriodEnd.sub(refPeriodStart)) / 365.0);

            // for short periods...
            if (months == 0) {
                // ...take the reference period as 1 year from d1
                refPeriodStart = d1;
                refPeriodEnd = d1.add(Period.ONE_YEAR_FORWARD);
                months = 12;
            }

            final double period = months / 12.0;

            if (d2.le(refPeriodEnd)) {
                // here refPeriodEnd is a future (notional?) payment date
                if (d1.ge(refPeriodStart))
                    // here refPeriodStart is the last (maybe notional)
                    // payment date.
                    // refPeriodStart <= d1 <= d2 <= refPeriodEnd
                    // [maybe the equality should be enforced, since
                    // refPeriodStart < d1 <= d2 < refPeriodEnd
                    // could give wrong results] ???
                    return period * dayCount(d1, d2)
                    / dayCount(refPeriodStart, refPeriodEnd);
                else {
                    // here refPeriodStart is the next (maybe notional)
                    // payment date and refPeriodEnd is the second next
                    // (maybe notional) payment date.
                    // d1 < refPeriodStart < refPeriodEnd
                    // AND d2 <= refPeriodEnd
                    // this case is long first coupon

                    // the last notional payment date
                    final Date previousRef = refPeriodStart.add(new Period(
                            -months, TimeUnit.Months));
                    if (d2.gt(refPeriodStart))
                        return yearFraction(d1, refPeriodStart, previousRef,
                                refPeriodStart)
                                + yearFraction(refPeriodStart, d2,
                                        refPeriodStart, refPeriodEnd);
                    else
                        return yearFraction(d1, d2, previousRef,
                                refPeriodStart);
                }
            } else {
                // TODO: code review :: please verify against QL/C++ code

                // here refPeriodEnd is the last notional payment date
                // d1 < refPeriodEnd < d2 AND refPeriodStart < refPeriodEnd
                QL.require(refPeriodStart.le(d1) , "invalid dates"); // TODO: message

                // now it is: refPeriodStart <= d1 < refPeriodEnd < d2

                // the part from d1 to refPeriodEnd
                double sum = yearFraction(d1, refPeriodEnd, refPeriodStart, refPeriodEnd);

                // the part from refPeriodEnd to d2
                // count how many regular periods are in [refPeriodEnd, d2],
                // then add the remaining time
                int i = 0;
                Date newRefStart, newRefEnd;
                do {
                    newRefStart = refPeriodEnd.add(new Period(months * i, TimeUnit.Months));
                    newRefEnd = refPeriodEnd.add(new Period(months * (i + 1), TimeUnit.Months));
                    if (d2.lt(newRefEnd))
                        break;
                    else {
                        sum += period;
                        i++;
                    }
                } while (true);
                sum += yearFraction(newRefStart, d2, newRefStart, newRefEnd);
                return sum;
            }
        }
    }


    final private class ImplISDA extends DayCounter.Impl {

        @Override
        public final String name() /* @ReadOnly */{
            return "Actual/Actual (ISDA)";
        }

        @Override
        public final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            if (dateStart.equals(dateEnd))
                return 0.0;
            if (dateStart.gt(dateEnd))
                return -yearFraction(dateEnd, dateStart, new Date(), new Date());

            final int y1 = dateStart.year();
            final int y2 = dateEnd.year();
            final double dib1 = Date.isLeap(dateStart.year()) ? 366.0 : 365.0;
            final double dib2 = Date.isLeap(dateEnd.year())   ? 366.0 : 365.0;

            double sum = y2 - y1 - 1;

            // Days from start to starting of following year
            sum += (dib1 - dateStart.dayOfYear() + 1) / dib1;
            // Days from beginning of year to the endDate
            sum += (dateEnd.dayOfYear() - 1) / dib2;
            return sum;
        }

    }


    final private class ImplAFB extends DayCounter.Impl {

        @Override
        public final String name() /* @ReadOnly */ {
            return "Actual/Actual (AFB)";
        }

        @Override
        public final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
            if (dateStart.equals(dateEnd))
                return 0.0;
            if (dateStart.gt(dateEnd))
                return -1.0 * yearFraction(dateEnd, dateStart, new Date(), new Date());

            Date newD2 = dateEnd;
            Date temp = dateEnd;
            double sum = 0.0;
            while (temp.gt(dateStart)) {
                temp = newD2.add(Period.ONE_YEAR_BACKWARD);
                if (temp.dayOfMonth() == 28 && temp.month().value() == 2 && Date.isLeap(temp.year()))
                    temp.inc();
                if (temp.ge(dateStart)) {
                    sum += 1.0;
                    newD2 = temp;
                }
            }

            double den = 365.0;

            if (Date.isLeap(newD2.year())) {
                if (newD2.gt(new Date(29, Month.February, newD2.year())) &&
                        dateStart.le(new Date(29, Month.February, newD2.year())))
                    den += 1.0;
            } else if (Date.isLeap(dateStart.year()))
                if (newD2.gt(new Date(29, Month.February, dateStart.year())) &&
                        dateStart.le(new Date(29, Month.February, dateStart.year())))
                    den += 1.0;
            return sum + dayCount(dateStart, newD2) / den;
        }

    }

}
