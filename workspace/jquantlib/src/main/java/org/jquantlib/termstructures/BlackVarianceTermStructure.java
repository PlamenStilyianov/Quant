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
 Copyright (C) 2002, 2003 Ferdinando Ametrano
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl

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

package org.jquantlib.termstructures;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.termstructures.yieldcurves.FixedRateBondHelper;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Black variance term structure
 * <p>
 * This abstract class acts as an adapter to VolTermStructure allowing the programmer to implement only the
 * <tt>blackVarianceImpl(Time, Real, bool)</tt> method in derived classes.
 * <p>
 * Volatility is assumed to be expressed on an annual basis.
 *
 * @note Term structures initialized by means of this constructor must manage their own reference date by overriding the
 *       referenceDate() method.
 * @note See the TermStructure documentation for issues regarding constructors.
 *
 * @author Richard Gomes
 */
public abstract class BlackVarianceTermStructure extends BlackVolTermStructure {


    //
    // public constructors
    //
    // See the TermStructure documentation for issues regarding constructors.
    //

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public BlackVarianceTermStructure() {
        this(new Calendar(), BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public BlackVarianceTermStructure(final Calendar cal) {
        this(cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public BlackVarianceTermStructure(
            final Calendar cal,
            final BusinessDayConvention bdc) {
        this(cal, bdc, new DayCounter());
    }

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public BlackVarianceTermStructure(
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(cal, bdc, dc);
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVarianceTermStructure(final Date referenceDate) {
        this(referenceDate, new Calendar(), BusinessDayConvention.Following, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVarianceTermStructure(
            final Date referenceDate,
            final Calendar cal) {
        this(referenceDate, cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVarianceTermStructure(
            final Date referenceDate,
            final Calendar cal,
            final BusinessDayConvention bdc) {
        this(referenceDate, cal, bdc, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVarianceTermStructure(
            final Date referenceDate,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(referenceDate, cal, bdc, dc);
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public BlackVarianceTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal) {
        this(settlementDays, cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public BlackVarianceTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal,
            final BusinessDayConvention bdc) {
        this(settlementDays, cal, bdc, new DayCounter());
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public BlackVarianceTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(settlementDays, cal, bdc, dc);
    }


    //
    // Overrides BlackVolTermStructure
    //

    /**
     * Returns the volatility for the given strike and date calculating it from the variance.
     */
    @Override
    protected final/* @Volatility */double blackVolImpl(final/* @Time */double maturity, final/* @Real */double strike) {
        /* @Time */double nonZeroMaturity;
        /* @Time */final double m = maturity;
        if (m == 0.0) {
            nonZeroMaturity = 0.00001;
        } else {
            nonZeroMaturity = m;
        }
        /* @Variance */final double var = blackVarianceImpl(/* Time */nonZeroMaturity, strike);
        return Math.sqrt(var / nonZeroMaturity);
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
		final Visitor<BlackVarianceTermStructure> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
