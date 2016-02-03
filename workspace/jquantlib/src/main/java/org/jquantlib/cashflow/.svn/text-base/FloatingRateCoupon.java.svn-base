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

package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.InterestRateIndex;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.Observer;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 *
 * @author Ueli Hofstetter
 * @author Zahid Hussain
 */
public class FloatingRateCoupon extends Coupon implements Observer {

    protected InterestRateIndex index_;
    protected DayCounter dayCounter_;
    protected int fixingDays_;
    protected double gearing_;
    protected double spread_;
    protected boolean isInArrears_;
    protected FloatingRateCouponPricer pricer_;

    public FloatingRateCoupon(
            final Date paymentDate,
            final double nominal,
            final Date startDate,
            final Date endDate,
            final int fixingDays,
            final InterestRateIndex index,
            final double gearing,
            final double spread,
            final Date refPeriodStart,
            final Date refPeriodEnd,
            final DayCounter dayCounter,
            final boolean isInArrears) {
        super(nominal, paymentDate, startDate, endDate, refPeriodStart, refPeriodEnd);

        this.index_ = index;
        this.dayCounter_ = dayCounter;
        this.fixingDays_ = fixingDays == 0 ? index.fixingDays() : fixingDays;
        this.gearing_ = gearing;
        this.spread_ = spread;
        this.isInArrears_ = isInArrears;
        
        QL.require(gearing != 0 , "Null gearing not allowed");

        if (dayCounter_.empty())
            dayCounter_ = index.dayCounter();

        Date evaluationDate = new Settings().evaluationDate();
        this.index_.addObserver(this);
        evaluationDate.addObserver(this);
    }

    public void setPricer(final FloatingRateCouponPricer pricer){
        if (pricer_ != null) {
        	pricer_.deleteObserver(this);
        }
        pricer_ = pricer;
        if ( pricer_ != null ) {
        	pricer_.addObserver(this);
        }
        update();
    }
    
    public FloatingRateCouponPricer pricer() {
        return pricer_;
    }
    
    public double amount() {
        return rate() * accrualPeriod() * nominal();
    }

    public /*Real*/ double accruedAmount(final Date d) {
    	
        if (d.le(accrualStartDate_) || d.gt(paymentDate_)) {
            return 0.0;
        } else {
            return nominal() * rate() *
                dayCounter().yearFraction(accrualStartDate_,
                                          Date.min(d,accrualEndDate_),
                                          refPeriodStart_,
                                          refPeriodEnd_);
        }
    }

    public double price(final Handle<YieldTermStructure> yts) {
        return amount() * yts.currentLink().discount(date());
    }

    public DayCounter dayCounter() {
        return dayCounter_;
    }
    
    public InterestRateIndex index() {
        return index_;
    }

    public int fixingDays() {
        return fixingDays_;
    }

    public Date fixingDate() {
        // if isInArrears_ fix at the end of period
        Date refDate = isInArrears_ ? accrualEndDate_ : accrualStartDate_;
        return index_.fixingCalendar().advance(refDate, new Period(-fixingDays_, TimeUnit.Days), BusinessDayConvention.Preceding);
    }


    public double gearing() {
        return gearing_;
    }
    public double spread() {
        return spread_;
    }
    
    public /*Rate*/ double indexFixing() {
        return index_.fixing(fixingDate());
    }

    public /*Rate*/ double rate() {
        QL.require(pricer_ != null, "pricer not set");
        pricer_.initialize(this);
        return pricer_.swapletRate();
    }

    public /*Rate*/ double adjustedFixing()  {
        return (rate()-spread())/gearing();
    }

    public boolean isInArrears() {
        return isInArrears_;
    }
    
    public /*Rate*/ double convexityAdjustmentImpl(double /*Rate*/ f) {
        return (gearing() == 0.0 ? 0.0 : adjustedFixing()-f);
    }
    
    public /*Rate*/ double convexityAdjustment() {
        return convexityAdjustmentImpl(indexFixing());
    }
    
    public void update() {
        notifyObservers();
    }

    //
    // implements TypeVisitable
    //
    
    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<FloatingRateCoupon> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }
}
