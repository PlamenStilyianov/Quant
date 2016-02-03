/*
Copyright (C) 2009 Ueli Hofstetter
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
 or FITNESS FOR A PARTICULAR PURPOSE. See the license for more details.
*/

package org.jquantlib.cashflow;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.SwapIndex;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Schedule;

/**
 * Helper class building a sequence of capped/floored cms-rate coupons
 *
 * @author Ueli Hofstetter
 * @author John Martin
 * @author Zahid Hussain
 */
public class CmsLeg {

    private final Schedule schedule_;
    private final SwapIndex swapIndex_;
    private/* @Real */Array notionals_;
    private DayCounter paymentDayCounter_;
    private BusinessDayConvention paymentAdjustment_;
    private Array fixingDays_;
    private Array gearings_;
    private/* @Spread */Array spreads_;
    private/* @Rate */Array caps_;
    private Array floors_;
    private boolean inArrears_;
    private boolean zeroPayments_;


    public CmsLeg(final Schedule schedule, final SwapIndex swapIndex) {
        schedule_ = schedule;
        swapIndex_ = swapIndex;
        paymentAdjustment_ = BusinessDayConvention.Following;
        inArrears_ = false;
        zeroPayments_ = false;
        
        fixingDays_ = new Array(0);
        gearings_ = new Array(0);
        spreads_ = new Array(0);
        caps_ = new Array(0);
        floors_ = new Array(0);
    }


    public CmsLeg withNotionals(/* Real */final double notional) {
        notionals_ = new Array(1).fill(notional);
        return this;
    }

    public CmsLeg withNotionals(final Array notionals) {
        notionals_ = notionals;
        return this;
    }

    
    public CmsLeg withPaymentDayCounter(final DayCounter dayCounter) {
        paymentDayCounter_ = dayCounter;
        return this;
    }

    public CmsLeg withPaymentAdjustment(final BusinessDayConvention convention) {
        paymentAdjustment_ = convention;
        return this;
    }
    
    public CmsLeg withFixingDays(/* Natural */final int fixingDays) {
        fixingDays_ = new Array(1).fill(fixingDays);
        return this;
    }

    public CmsLeg withFixingDays(final Array fixingDays) {
        fixingDays_ = fixingDays.clone();
        return this;
    }

    public CmsLeg withGearings(/* Real */final double gearing) {
        gearings_ = new Array(1).fill(gearing);
        return this;
    }

    public CmsLeg withGearings(final Array gearings) {
        gearings_ = gearings.clone();
        return this;
    }

    public CmsLeg withSpreads(/* Spread */final double spread) {
        spreads_ = new Array(1).fill(spread);
        return this;
    }

    public CmsLeg withSpreads(final Array spreads) {
        spreads_ = spreads.clone();
        return this;
    }

    public CmsLeg withCaps(/* @Rate */final double cap) {
        caps_ = new Array(1).fill(cap);
        return this;
    }

    public CmsLeg withCaps(final Array caps) {
        caps_ = caps.clone();
        return this;
    }

    public CmsLeg withFloors(/* @Rate */final double floor) {
        floors_ = new Array(1).fill(floor);
        return this;
    }

    public CmsLeg withFloors(final Array floors) {
        floors_ = floors.clone();
        return this;
    }

    public CmsLeg inArrears(final boolean flag) {
        inArrears_ = flag;
        return this;
    }

    public CmsLeg withZeroPayments(final boolean flag) {
        zeroPayments_ = flag;
        return this;
    }

    public Leg Leg() {
        return new FloatingLeg<SwapIndex, CmsCoupon, CappedFlooredCmsCoupon> (
        		SwapIndex.class, CmsCoupon.class, CappedFlooredCmsCoupon.class,
        		notionals_, schedule_, swapIndex_, paymentDayCounter_,
       			paymentAdjustment_, fixingDays_, gearings_, spreads_,
       			caps_, floors_, inArrears_, zeroPayments_);
    }

}
