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
 Copyright (C) 2007 Ferdinando Ametrano
 Copyright (C) 2007 Giorgio Facchinetti
 Copyright (C) 2007 Cristina Duminuco
 Copyright (C) 2007 StatPro Italia srl

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

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Schedule;

/**
 * Helper class building a sequence of capped/floored ibor-rate coupons
 *
 * @author Ueli Hofstetter
 * @author John Martin
 */
public class IborLeg {

    private final Schedule schedule_;
    private final IborIndex index_;
    private Array notionals_;
    private DayCounter paymentDayCounter_;
    private BusinessDayConvention paymentAdjustment_;
    private Array fixingDays_;
    private Array gearings_;
    private Array spreads_;
    private Array caps_, floors_;
    private boolean inArrears_, zeroPayments_;

    public IborLeg(final Schedule schedule, final IborIndex index) {
        schedule_ = (schedule);
        index_ = (index);
        paymentAdjustment_ = BusinessDayConvention.Following;

        // TODO : review initialization
        // these are vectors in quantlib, therfor they must be initalized to default
        // values or nullable. since we have decided to write the code without null checks
        // all over the place we are going to initialze them for now to be consistent with
        // quantlib behavoir
        fixingDays_ = new Array(0);
        gearings_ = new Array(0);
        spreads_ = new Array(0);
        caps_ = new Array(0);
        floors_ = new Array(0);
        inArrears_ = false;
        zeroPayments_ = false;
    }

    public final IborLeg withNotionals(/* @Real */final double notional) {
        notionals_ = new Array(new double[] { notional });// std::vector<Real>(1,notional);
        return this;
    }

    public final IborLeg withNotionals(final Array notionals) {
        notionals_ = notionals;
        return this;
    }

    public final IborLeg withPaymentDayCounter(final DayCounter dayCounter) {
        paymentDayCounter_ = dayCounter;
        return this;
    }

    public final IborLeg withPaymentAdjustment(final BusinessDayConvention convention) {
        paymentAdjustment_ = convention;
        return this;
    }

    public final IborLeg withFixingDays(/* @Natural */final double fixingDays) {
        fixingDays_ = new Array(new double[] { fixingDays });// std::vector<Natural>(1,fixingDays);
        return this;
    }

    public final IborLeg withFixingDays(final Array fixingDays) {
        fixingDays_ = fixingDays;
        return this;
    }

    public IborLeg withGearings(/* @Real */final double gearing) {
        gearings_ = new Array(new double[] { gearing });
        return this;
    }

    public IborLeg withGearings(final Array gearings) {
        gearings_ = gearings;
        return this;
    }

    public IborLeg withSpreads(/* @Spread */final double spread) {
        spreads_ = new Array(new double[] { spread });
        return this;
    }

    public IborLeg withSpreads(final Array spreads) {
        spreads_ = spreads;
        return this;
    }

    public IborLeg withCaps(/* @Rate */final double cap) {
        caps_ = new Array(1).fill(cap);
        return this;
    }

    public IborLeg withCaps(final Array caps) {
        caps_ = caps;
        return this;
    }

    public IborLeg withFloors(/* @Rate */final double floor) {
        floors_ = new Array(1).fill(floor);
        return this;
    }

    public IborLeg withFloors(final Array floors) {
        floors_ = floors;
        return this;
    }

    public IborLeg inArrears(final boolean flag) {
        inArrears_ = flag;
        return this;
    }

    public IborLeg withZeroPayments(final boolean flag) {
        zeroPayments_ = flag;
        return this;
    }

    public Leg Leg() /* @ReadOnly */{

        final Leg cashflows = new FloatingLeg(
        		IborIndex.class, IborCoupon.class, CappedFlooredIborCoupon.class,
                notionals_, schedule_, index_,
                paymentDayCounter_, paymentAdjustment_, fixingDays_,
                gearings_, spreads_, caps_, floors_, inArrears_, zeroPayments_);

        if (caps_.empty() && floors_.empty() && !inArrears_) {
            PricerSetter.setCouponPricer(cashflows, new BlackIborCouponPricer(new Handle <OptionletVolatilityStructure>()));
        }
        return cashflows;
    }

}