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

import org.jquantlib.Settings;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.TimeUnit;

/**
 * Forward rate agreement (FRA) class
 * <p>
 *  1. Unlike the forward contract conventions on carryable
 *     financial assets (stocks, bonds, commodities), the
 *     valueDate for a FRA is taken to be the day when the forward
 *     loan or deposit begins and when full settlement takes place
 *     (based on the NPV of the contract on that date).
 *     maturityDate is the date when the forward loan or deposit
 *     ends. In fact, the FRA settles and expires on the
 *     valueDate, not on the (later) maturityDate. It follows that
 *     (maturityDate - valueDate) is the tenor/term of the
 *     underlying loan or deposit
 * <p>
 *  2. Choose position type = Long for an "FRA purchase" (future
 *     long loan, short deposit [borrower])
 * <p>
 *  3. Choose position type = Short for an "FRA sale" (future short
 *     loan, long deposit [lender])
 * <p>
 *  4. If strike is given in the constructor, can calculate the NPV
 *     of the contract via NPV().
 * <p>
 *  5. If forward rate is desired/unknown, it can be obtained via
 *     forwardRate(). In this case, the strike variable in the
 *     constructor is irrelevant and will be ignored.
 *
 *  @todo Add preconditions and tests
 *
 *  @todo Should put an instance of ForwardRateAgreement in the
 *        FraRateHelper to ensure consistency with the piecewise
 *        yield curve.
 *
 *  @todo Differentiate between BBA (British)/AFB (French)
 *        [assumed here] and ABA (Australian) banker conventions
 *        in the calculations.
 *
 *  @warning This class still needs to be rigorously tested
 *
 *  @category instruments
 *
 * @author John Martin
 */
public class ForwardRateAgreement extends Forward {

    protected InterestRate forwardRate;
    protected InterestRate strikeForwardRate;
    protected Position fraType;
    protected double notional;
    protected IborIndex index;


    //
    // public constructor
    //

    public ForwardRateAgreement(
            final Date valueDate,
            final Date maturityDate,
            final Position type,
            final double strikeForwardRate,
            final double notionalAmount,
            final IborIndex index) {
        this (valueDate, maturityDate, type, strikeForwardRate, notionalAmount, index, new Handle<YieldTermStructure>());
    }


    public ForwardRateAgreement(
            final Date valueDate,
            final Date maturityDate,
            final Position type,
            final double strikeForwardRate,
            final double notionalAmount,
            final IborIndex index,
            final Handle<YieldTermStructure> discountCurve) {
        super(index.dayCounter(), index.fixingCalendar(), index.businessDayConvention(), index.fixingDays(),
                null, valueDate, maturityDate, discountCurve);
        this.fraType = type;
        this.notional = notionalAmount;
        this.index = index;

        // do I adjust this ?
        // valueDate_ = calendar_.adjust(valueDate_,businessDayConvention_);
        final Date fixingDate = calendar.advance (valueDate, -1 * settlementDays, TimeUnit.Days);

        forwardRate = new InterestRate(index.fixing(fixingDate), index.dayCounter(), Compounding.Simple, Frequency.Once);
        this.strikeForwardRate = new InterestRate(strikeForwardRate, index.dayCounter(), Compounding.Simple, Frequency.Once);
        final double strike = notional * this.strikeForwardRate.compoundFactor(valueDate, maturityDate);
        payoff = new ForwardTypePayoff(fraType, strike);

        // income discount curve is irrelevant to a FRA
        incomeDiscountCurve = discountCurve;
        underlyingIncome = 0.0;

        index.addObserver (this);
    }

    @Override
    public Date settlementDate() {
        return calendar.advance(new Settings().evaluationDate(), settlementDays, TimeUnit.Days);
    }

    @Override
    public boolean isExpired() {
        if (new Settings().isTodaysPayments()) {
            return valueDate.lt(settlementDate());
        }
        return valueDate.le(settlementDate());
    }

    @Override
    public double spotIncome(final Handle<YieldTermStructure> incomeDiscountCurve) {
        // irrelevant for FRA
        return 0;
    }

    // In theory, no need to implement this for a FRA (could directly
    // supply a forwardValue). For the sake of keeping a consistent
    // framework, we adhere to the concept of the forward contract as
    // defined in the base class, with an underlying having a
    // spotPrice (in this case, a loan or deposit with an NPV). Thus,
    // spotValue() is defined here.

    @Override
    public double spotValue() {
        calculate();
        final double compoundFactor = forwardRate.compoundFactor(valueDate, maturityDate);
        final double discount = discountCurve.currentLink().discount(maturityDate);
        return notional * compoundFactor * discount;
    }

    public InterestRate forwardRate() {
        calculate();
        return forwardRate;
    }

    @Override
    public void performCalculations() {
        final Date fixingDate = calendar.advance(valueDate, -1 * settlementDays, TimeUnit.Days);
        forwardRate = new InterestRate(index.fixing(fixingDate), index.dayCounter(), Compounding.Simple, Frequency.Once);
        underlyingSpotValue = spotValue();
        underlyingIncome = 0.0;
        super.performCalculations();
    }

}