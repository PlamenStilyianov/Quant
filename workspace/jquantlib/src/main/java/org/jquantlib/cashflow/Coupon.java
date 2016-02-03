/*
 Copyright (C) 2009 Ueli Hofstetter
 Copyright (C) 2009 Daniel Kong

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

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Coupon accruing over a fixed period
 * <p>
 * This class implements part of the CashFlow interface but it is still abstract and provides derived classes with methods for
 * accrual period calculations.
 *
 * @author Ueli Hofstetter
 * @author Daniel Kong
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class Coupon extends CashFlow {

    //
    // protected fields
    //

    protected double nominal;
    protected Date paymentDate_;
    protected Date accrualStartDate_;
    protected Date accrualEndDate_;
    protected Date refPeriodStart_;
    protected Date refPeriodEnd_;


    //
    // public constructors
    //

    public Coupon(final double nominal,
            final Date paymentDate,
            final Date accrualStartDate,
            final Date accrualEndDate){
        this(nominal, paymentDate, accrualStartDate, accrualEndDate, new Date(), new Date());
    }

    public Coupon(final double nominal,
            final Date paymentDate,
            final Date accrualStartDate,
            final Date accrualEndDate,
            final Date refPeriodStart,
            final Date refPeriodEnd){
        this.nominal = nominal;
        this.paymentDate_ = paymentDate.clone();
        this.accrualStartDate_ = accrualStartDate.clone();
        this.accrualEndDate_ = accrualEndDate.clone();
        this.refPeriodStart_ = refPeriodStart.clone();
        this.refPeriodEnd_ = refPeriodEnd.clone();
    }


    //
    // public abstract methods
    //

    public abstract /*Rate*/ double rate();

    public abstract DayCounter dayCounter();

    public abstract double accruedAmount(final Date date);


    //
    // public methods
    //

    public double nominal(){
        return nominal;
    }

    public Date accrualStartDate(){
        return accrualStartDate_;
    }

    public Date accrualEndDate(){
        return accrualEndDate_;
    }

    public Date referencePeriodStart() {
        return refPeriodStart_;
    }

    public Date referencePeriodEnd() {
        return refPeriodEnd_;
    }

    public double accrualPeriod() {
        return dayCounter().yearFraction(accrualStartDate_,
                accrualEndDate_,
                refPeriodStart_,
                refPeriodEnd_);
    }

    public long accrualDays() {
        return dayCounter().dayCount(accrualStartDate_,
                accrualEndDate_);
    }


    //
    // implements Event
    //

    @Override
    public Date date() {
        return paymentDate_.clone();
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<Coupon> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
