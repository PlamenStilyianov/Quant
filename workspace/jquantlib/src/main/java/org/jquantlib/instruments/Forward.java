/*
 Copyright (C) 2008 John Martin

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
 Copyright (C) 2006 Allen Kuo

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

package org.jquantlib.instruments;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;

/**
 * Abstract base forward class
 * <p>
 * Derived classes must implement the virtual functions
 * spotValue() (NPV or spot price) and spotIncome() associated
 * with the specific relevant underlying (e.g. bond, stock,
 * commodity, loan/deposit). These functions must be used to set the
 * protected member variables underlyingSpotValue_ and
 * underlyingIncome_ within performCalculations() in the derived
 * class before the base-class implementation is called.
 * <p>
 * spotIncome() refers generically to the present value of
 * coupons, dividends or storage costs.
 * <p>
 * discountCurve_ is the curve used to discount forward contract
 * cash flows back to the evaluation day, as well as to obtain
 * forward values for spot values/prices.
 * <p>
 * incomeDiscountCurve_, which for generality is not
 * automatically set to the discountCurve_, is the curve used to
 * discount future income/dividends/storage-costs etc back to the
 * evaluation date.
 *
 * @todo Add preconditions and tests
 *
 * @warning This class still needs to be rigorously tested
 *
 * @category instruments
 *
 * @author John Martin
 */
public abstract class Forward extends Instrument {

    //
    // protected member variables
    //

    protected int settlementDays;
    protected Date maturityDate;
    protected Date valueDate;
    protected Calendar calendar;
    protected DayCounter dayCounter;
    protected BusinessDayConvention businessDayConvention;
    protected Handle <YieldTermStructure> discountCurve;
    protected Handle <YieldTermStructure> incomeDiscountCurve;
    protected ForwardTypePayoff payoff;
    protected double underlyingSpotValue;
    protected double underlyingIncome;


    //
    // protected constructors
    //

    protected Forward(
            final DayCounter dc,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final int /* @Natural */settlementDays,
            final Payoff payoff,
            final Date valueDate,
            final Date maturityDate) {
        this (dc, cal, bdc, settlementDays, payoff, valueDate, maturityDate, new Handle <YieldTermStructure>());
    }

    protected Forward(
            final DayCounter dc,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final int /* @Natural */settlementDays,
            final Payoff payoff,
            final Date valueDate,
            final Date maturityDate,
            final Handle <YieldTermStructure> discountCurve) {
        this.dayCounter = dc;
        this.calendar = cal;
        this.businessDayConvention = bdc;
        this.settlementDays = settlementDays;
        this.maturityDate = calendar.adjust (maturityDate, businessDayConvention);
        this.discountCurve = discountCurve;
        this.payoff = (ForwardTypePayoff) payoff;
        this.valueDate = valueDate;

        //XXX
        //registerWith(Settings::instance().evaluationDate());
        //registerWith(discountCurve_);

        new Settings().evaluationDate().addObserver(this);
        discountCurve.addObserver(this);
    }


    //
    // public abstract methods
    //

    public abstract double spotValue ();

    public abstract double spotIncome (Handle <YieldTermStructure> incomeDiscountCurve);


    //
    // public methods
    //

    public double forwardValue () {
        calculate ();

        return (underlyingSpotValue - underlyingIncome)
                / (discountCurve.currentLink ().discount (maturityDate));
    }

    /**
     * Simple yield calculation based on underlying spot and
     * forward values, taking into account underlying income.
     * <p>
     * When \f$ t>0 \f$, call with:
     * <pre>
     * underlyingSpotValue=spotValue(t);
     * forwardValue=strikePrice;
     * </pre>
     * to get current yield.
     * For a repo, if {@latex$ t=0 }, impliedYield should reproduce the spot repo rate.
     * For FRA's, this should reproduce the relevant zero rate at the FRA's maturity date;
     */
    public InterestRate impliedYield(
            final double underlyingSpotValue,
            final double forwardValue,
            final Date settlementDate,
            final Compounding compoundingConvention,
            final DayCounter dayCounter) {
        final double tenor = dayCounter.yearFraction (settlementDate, maturityDate);
        final double compoundingFactor = forwardValue / (underlyingSpotValue - spotIncome (incomeDiscountCurve));
        return InterestRate.impliedRate (compoundingFactor, tenor, dayCounter, compoundingConvention);
    }

    public BusinessDayConvention businessDayConvention() {
        return this.businessDayConvention;
    }

    public Calendar calendar() {
        return this.calendar;
    }

    public Date settlementDate() {
        final Period advance = new Period (settlementDays, TimeUnit.Days);
        final Date settle = calendar.advance (new Settings ().evaluationDate (), advance);
        if (settle.gt (valueDate)) {
            return settle;
        }
        return valueDate;
    }

    public DayCounter dayCounter() {
        return this.dayCounter;
    }

    public Handle <YieldTermStructure> discountCurve() {
        return this.discountCurve;
    }

    public Handle <YieldTermStructure> incomeDiscountCurve() {
        return this.incomeDiscountCurve;
    }


    //
    // overrides Instrument
    //

    @Override
    public void performCalculations () {
        QL.require (discountCurve != null, " Discount Curve must be set for Forward");
        NPV = payoff.get (forwardValue ()) * discountCurve.currentLink ().discount (maturityDate);
    }

    @Override
    public boolean isExpired() {
        return valueDate.gt (maturityDate);
    }

}