/*
 Copyright (C) 2009 Ueli Hofstetter

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
 Copyright (C) 2007 Giorgio Facchinetti
 Copyright (C) 2007 Cristina Duminuco

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.indexes.IndexManager;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;
/**
 * 
 * @author Zahid Hussain
 *
 */
// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class IborCoupon extends FloatingRateCoupon {

    private final static String NULL_TERM_STRUCTURE = "null term structure set to par coupon";

    public IborCoupon(final Date paymentDate,
                      final double nominal,
                      final Date startDate,
                      final Date endDate,
                      final int fixingDays,
                      final IborIndex index) {
        // gearing default constructor
        this (paymentDate, nominal, startDate, endDate, fixingDays, index, 1.0);
    }

    public IborCoupon(final Date paymentDate,
                      final double nominal,
                      final Date startDate,
                      final Date endDate,
                      final int fixingDays,
                      final IborIndex index,
                      final double gearing) {
        // spread default constructor
        this (paymentDate, nominal, startDate, endDate, fixingDays, index, gearing, 0.0);
    }

    public IborCoupon(final Date paymentDate,
                      final double nominal,
                      final Date startDate,
                      final Date endDate,
                      final int fixingDays,
                      final IborIndex index,
                      final double gearing,
                      final double spread) {
        // refperiodStart, refperiod end default constructor
        this (paymentDate, nominal, startDate, endDate, fixingDays,
              index, gearing, spread, new Date(), new Date());
    }

    public IborCoupon(final Date paymentDate,
                      final double nominal,
                      final Date startDate,
                      final Date endDate,
                      final int fixingDays,
                      final IborIndex index,
                      final double gearing,
                      final double spread,
                      final Date refPeriodStart,
                      final Date refPeriodEnd) {
        // default daycounter constructor
        this (paymentDate, nominal, startDate, endDate, fixingDays,
              index, gearing, spread, refPeriodStart, refPeriodEnd, new DayCounter());
    }

    public IborCoupon(final Date paymentDate,
                      final double nominal,
                      final Date startDate,
                      final Date endDate,
                      final int fixingDays,
                      final IborIndex index,
                      final double gearing,
                      final double spread,
                      final Date refPeriodStart,
                      final Date refPeriodEnd,
                      final DayCounter dayCounter) {
        // default inArrears constructor
        this (paymentDate, nominal, startDate, endDate, fixingDays,
              index, gearing, spread, refPeriodStart, refPeriodEnd, dayCounter, false);
    }

    public IborCoupon(
            final Date paymentDate,
            final double nominal,
            final Date startDate,
            final Date endDate,
            final int fixingDays,
            final IborIndex index,
            final double gearing,
            final double spread,
            final Date refPeriodStart,
            final Date refPeriodEnd,
            final DayCounter dayCounter,
            final boolean isInArrears) {
        super(paymentDate, nominal, startDate, endDate, fixingDays, index,
                gearing, spread, refPeriodStart, refPeriodEnd,
                dayCounter, isInArrears);
    }

    @Override
    public double indexFixing() {
        final Settings settings = new Settings();
        if (settings.isUseIndexedCoupon())
            return index_.fixing(fixingDate());
        if (isInArrears())
            return index_.fixing(fixingDate());
        else {
            final Handle<YieldTermStructure> termStructure = index_.termStructure();
            QL.require(termStructure != null , NULL_TERM_STRUCTURE);  // QA:[RG]::verified
            final Date today = settings.evaluationDate();
            final Date fixing_date = fixingDate();
            final IndexManager indexManager = IndexManager.getInstance();
            if (fixing_date.lt(today)) {
                final double pastFixing = indexManager.getHistory(index_.name()).get(fixing_date);
                QL.require(!Double.isNaN(pastFixing), "Missing fixing"); // TODO: message
                return pastFixing;
            }
            if (fixing_date.equals(today)) {
                try {
                    final double pastFixing = indexManager.getHistory(index_.name()).get(fixing_date);
                    if (! Double.isNaN (pastFixing))
                        return pastFixing;
                } catch (final Exception e) {
                    ; // fall through and forecast
                }
            }

            // start discount
            final Date fixingValueDate = index_.fixingCalendar()
                .advance(fixing_date, index_.fixingDays(), TimeUnit.Days);
            final double startDiscount = termStructure.currentLink().discount(fixingValueDate);

            // end discount
            final Date nextFixingDate = index_.fixingCalendar()
                .advance(accrualEndDate_, -(fixingDays()), TimeUnit.Days);
            final Date nextFixingValueDate = index_.fixingCalendar()
                .advance (nextFixingDate, index_.fixingDays(), TimeUnit.Days);
            final double endDiscount = termStructure.currentLink().discount(nextFixingValueDate);

            // spanning time
            final double spanningTime = index_.dayCounter()
                .yearFraction (fixingValueDate, nextFixingValueDate);
            // implied fixing
            return (startDiscount / endDiscount - 1.0) / spanningTime;
        }
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<IborCoupon> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
