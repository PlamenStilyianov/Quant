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

package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.math.Constants;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Capped and/or floored floating-rate coupon
 * <p>
 * The payoff {@latex$ P } of a capped floating-rate coupon is:
 * {@latex[ P = N \times T \times \min(a L + b, C) }
 * <p>
 * The payoff of a floored floating-rate coupon is:
 * {@latex[ P = N \times T \times \max(a L + b, F) }
 * <p>
 * The payoff of a collared floating-rate coupon is:
 * {@latex[ P = N \times T \times \min(\max(a L + b, F), C) } where
 * <p>
 * {@latex$ N } is the notional, {@latex$ T }is the accrual time, {@latex$ L } is the floating rate, {@latex$ a } is its gearing,
 * {@latex$ b } is the spread, and {@latex$ C } and {@latex$ F } are the strikes.
 * <p>
 * They can be decomposed in the following manner. Decomposition of a capped floating rate coupon:
 * {@latex[ R = \min(a L + b, C) = (a L + b) + \min(C - b - \xi |a| L, 0) } where
 * {@latex$ \xi = sgn(a) }. Then:
 * {@latex[ R = (a L + b) + |a| \min(\frac{C - b}{|a|} - \xi L, 0) }
 *
 * @author Ueli Hofstetter
 * @author John Martin
 */

/*
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004 StatPro Italia srl
 Copyright (C) 2003 Nicolas Di C�sar�
 Copyright (C) 2006, 2007 Cristina Duminuco
 Copyright (C) 2006 Ferdinando Ametrano
 Copyright (C) 2007 Giorgio Facchinetti

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

/**
 * Coupon paying a variable index-based rate
 *
 * @author Ueli Hofstetter
 * @author John Martin
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: code review :: please verify against QL/C++ code
public class CappedFlooredCoupon extends FloatingRateCoupon {

    protected FloatingRateCoupon underlying_;
    protected boolean isCapped_, isFloored_;
    protected /* @Rate */ double cap_, floor_;


    //
    // public constructors
    //

    public CappedFlooredCoupon(final FloatingRateCoupon underlying) {
        this(underlying, Constants.NULL_REAL, Constants.NULL_REAL);
    }

    public CappedFlooredCoupon(final FloatingRateCoupon underlying, final double cap, final double floor) {
        super(underlying.date(), underlying.nominal,
                underlying.accrualStartDate(), underlying.accrualEndDate(),
                underlying.fixingDays(), underlying.index(), underlying.gearing(), underlying.spread(),
                underlying.referencePeriodStart(), underlying.referencePeriodEnd(),
                underlying.dayCounter(), underlying.isInArrears());
        this.underlying_ = underlying;
        isCapped_ = false;
        isFloored_ = false;

        if (gearing_ > 0) {
            if (!Double.isNaN(cap)) {
                isCapped_ = true;
                cap_ = cap;
            }
            if (!Double.isNaN(floor)) {
                floor_ = floor;
                isFloored_ = true;
            }
        }

        // FIXME :: this comment does not belong to C++ code :: evaluate and eventually remove
        //
        // note subtle difference, caps become floors and floors become caps
        // if gearing is < 0.
        // It maybe WRONG to do this, note how we access the floor() and cap()
        // functions defined below. if we swap the caps and floors at construction, for
        // a negative gearing, we will undo this change when we access the negative
        // gearing again through the floor and cap functions.

        else {
            if (!Double.isNaN(cap)) {
                floor_ = cap;
                isFloored_ = true;
            }
            if (!Double.isNaN(floor)) {
                isCapped_ = true;
                cap_ = floor;
            }
        }

        if (isCapped_ && isFloored_) {
            QL.require(cap >= floor , "cap level less than floor level"); // TODO: message
        }

        this.underlying_.addObserver(this);
        //XXX:registerWith
        // registerWith(underlying);
    }

    @Override
    public void setPricer(final FloatingRateCouponPricer pricer) {

        if (this.pricer_ != null) {
            this.pricer_.deleteObserver (this);
        }
        this.pricer_ = pricer;
        if (this.pricer_ != null) {
            this.pricer_.addObserver (this);
        }
        update();
        underlying_.setPricer (pricer);
    }

    @Override
    public /*@Rate*/ double rate() /* @ReadOnly */ {
        QL.require (underlying_.pricer_ != null, "pricer not set");
        final double swapletRate = underlying_.rate();
        double floorRate = 0.0;
        double capRate = 0.0;
        if (isFloored_) {
            floorRate = underlying_.pricer_.floorletRate(effectiveFloor());
        }
        if (isCapped_) {
            capRate = underlying_.pricer_.capletRate (effectiveCap());
        }
        return swapletRate + floorRate - capRate;
    }

    @Override
    public /*@Rate*/ double convexityAdjustment() /* @ReadOnly */ {
        return underlying_.convexityAdjustment();
    }

    public boolean isCapped() /* @ReadOnly */{
        return isCapped_;
    }

    public boolean isFloored() /* @ReadOnly */{
        return isFloored_;
    }

    public /*@Rate*/ double cap() /* @ReadOnly */ {
        if (gearing_ > 0 && isCapped_) {
            return cap_;
        }
        if (gearing_ < 0 && isFloored_) {
            return floor_;
        }
        return Constants.NULL_REAL;
    }

    public /*@Rate*/ double floor() /* @ReadOnly */ {
        if (gearing_ > 0 && isFloored_) {
            return floor_;
        }
        if (gearing_ < 0 && isCapped_) {
            return cap_;
        }
        return Constants.NULL_REAL;
    }

    private /*@Rate*/ double effectiveCap() /* @ReadOnly */ {
        return (cap_ - spread()) / gearing();
    }

    private /*@Rate*/ double effectiveFloor() /* @ReadOnly */ {
        return (floor_ - spread()) / gearing();
    }


    //
    // implements Observer
    //

    @Override
    public void update() {
        notifyObservers();
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<CappedFlooredCoupon> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }


}