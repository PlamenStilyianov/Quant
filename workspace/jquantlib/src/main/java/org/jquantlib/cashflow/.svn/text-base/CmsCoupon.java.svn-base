/*
 Copyright (C) 2009 John Martin

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
 Copyright (C) 2006 Giorgio Facchinetti
 Copyright (C) 2006 Mario Pucci
 Copyright (C) 2006, 2007 StatPro Italia srl

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.


 This program is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 or FITNESS FOR A PARTICULAR PURPOSE. See the license for more details. */


package org.jquantlib.cashflow;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.SwapIndex;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;


/**
 * CMS coupon class
 * @author Richard Gomes
 *
 * @warning This class does not perform any date adjustment,
 *          i.e., the start and end date passed upon finalruction
 *          should be already rolled to a business day.
 */
public class CmsCoupon extends FloatingRateCoupon {

    protected SwapIndex swapIndex_;

    public CmsCoupon(final Date paymentDate,
                     final double nominal,
                     final Date startDate,
                     final Date endDate,
                     final int fixingDays,
                     final SwapIndex index) {
        // gearing default
        this (paymentDate, nominal, startDate, endDate, fixingDays, index, 1.0);
    }

    public CmsCoupon(final Date paymentDate,
                     final double nominal,
                     final Date startDate,
                     final Date endDate,
                     final int fixingDays,
                     final SwapIndex index,
                     final double gearing) {
        // spread default
        this (paymentDate, nominal, startDate, endDate, fixingDays, index, gearing, 0.0);
    }

    public CmsCoupon(final Date paymentDate,
                     final double nominal,
                     final Date startDate,
                     final Date endDate,
                     final int fixingDays,
                     final SwapIndex index,
                     final double gearing,
                     final double spread) {
        // reference dates defaults
        this (paymentDate, nominal, startDate, endDate, fixingDays,
              index, gearing, spread, new Date(), new Date());
    }


    public CmsCoupon(final Date paymentDate,
                     final double nominal,
                     final Date startDate,
                     final Date endDate,
                     final int fixingDays,
                     final SwapIndex index,
                     final double gearing,
                     final double spread,
                     final Date refPeriodStart,
                     final Date refPeriodEnd) {
        // daycounter default
        this (paymentDate, nominal, startDate, endDate, fixingDays,
              index, gearing, spread, refPeriodStart, refPeriodEnd,
              new DayCounter());
    }


    public CmsCoupon(final Date paymentDate,
                     final double nominal,
                     final Date startDate,
                     final Date endDate,
                     final int fixingDays,
                     final SwapIndex index,
                     final double gearing,
                     final double spread,
                     final Date refPeriodStart,
                     final Date refPeriodEnd,
                     final DayCounter dayCounter) {
        // inArrears default
        this (paymentDate, nominal, startDate, endDate, fixingDays,
              index, gearing, spread, refPeriodStart, refPeriodEnd,
              dayCounter, false);
    }


    public CmsCoupon(final Date paymentDate,
                     final double nominal,
                     final Date startDate,
                     final Date endDate,
                     final int fixingDays,
                     final SwapIndex index,
                     final double gearing,
                     final double spread,
                     final Date refPeriodStart,
                     final Date refPeriodEnd,
                     final DayCounter dayCounter,
                     final boolean isInArrears) {
        super (paymentDate, nominal, startDate, endDate, fixingDays,
               index, gearing, spread, refPeriodStart, refPeriodEnd,
               dayCounter, isInArrears);
    }

    public SwapIndex swapIndex() {
        return swapIndex_;
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<CmsCoupon> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
