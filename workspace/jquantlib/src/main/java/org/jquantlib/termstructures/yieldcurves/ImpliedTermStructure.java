/*
 Copyright (C) 2008 Richard Gomes

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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl

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

package org.jquantlib.termstructures.yieldcurves;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.AbstractYieldTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;

/**
 * Implied term structure at a given date in the future.
 *
 * @note The given date will be the implied reference date.
 * @note This term structure will remain linked to the original structure, i.e., any changes in the latter will be reflected in this
 *       structure as well.
 */
//TEST the correctness of the returned values is tested by checking them against numerical calculations.
//TEST observability against changes in the underlying term structure is checked.
public class ImpliedTermStructure<T extends YieldTermStructure> extends AbstractYieldTermStructure {

    private final Handle<T>	originalCurve;

    public ImpliedTermStructure(final Handle<T> h, final Date referenceDate) {
        super(referenceDate);
        this.originalCurve = h;
        this.originalCurve.addObserver(this);
    }

    @Override
    public DayCounter dayCounter() /* @ReadOnly */ {
        return originalCurve.currentLink().dayCounter();
    }

    @Override
    public Calendar calendar() /* @ReadOnly */ {
        return originalCurve.currentLink().calendar();
    }

    @Override
    public /*@Natural*/ int settlementDays() /* @ReadOnly */ {
        return originalCurve.currentLink().settlementDays();
    }

    public Date maxDate() /* @ReadOnly */ {
        return originalCurve.currentLink().maxDate();
    }

    @Override
    protected /*@DiscountFactor*/ double discountImpl(final /*@Time*/ double  t) /* @ReadOnly */ {
        /* t is relative to the current reference date
           and needs to be converted to the time relative
           to the reference date of the original curve */
        final Date ref = referenceDate();
        final /*@Time*/ double originalTime = t + dayCounter().yearFraction(originalCurve.currentLink().referenceDate(), ref);
        /* discount at evaluation date cannot be cached
           since the original curve could change between
           invocations of this method */
        return originalCurve.currentLink().discount(originalTime, true) / originalCurve.currentLink().discount(ref, true);
    }

}
