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

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.termstructures.volatilities.VolatilityTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitable;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Local volatility term structure base class
 *
 * @author Richard Gomes
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class LocalVolTermStructure extends VolatilityTermStructure implements PolymorphicVisitable {

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
    public LocalVolTermStructure() {
        this(new Calendar(), BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public LocalVolTermStructure(final Calendar cal) {
        this(cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public LocalVolTermStructure(
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
    public LocalVolTermStructure(
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(cal, bdc, dc);
    }

    /**
     *  initialize with a fixed reference date
     */
    public LocalVolTermStructure(final Date referenceDate) {
        this(referenceDate, new Calendar(), BusinessDayConvention.Following, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public LocalVolTermStructure(
            final Date referenceDate,
            final Calendar cal) {
        this(referenceDate, cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public LocalVolTermStructure(
            final Date referenceDate,
            final Calendar cal,
            final BusinessDayConvention bdc) {
        this(referenceDate, cal, bdc, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public LocalVolTermStructure(
            final Date referenceDate,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(referenceDate, cal, bdc, dc);
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public LocalVolTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal) {
        this(settlementDays, cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public LocalVolTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal,
            final BusinessDayConvention bdc) {
        this(settlementDays, cal, bdc, new DayCounter());
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public LocalVolTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(settlementDays, cal, bdc, dc);
    }


    //! \name Local Volatility

    public final /*@Volatility*/ double localVol(final Date d, final /*@Real*/ double underlyingLevel, final boolean extrapolate) {
        /*@Time*/ final double t = timeFromReference(d);
        checkRange(t, underlyingLevel, extrapolate);
        return localVolImpl(t, underlyingLevel);
    }

    public final /*@Volatility*/ double localVol(final /*@Time*/ double t, final /*@Real*/ double underlyingLevel) {
        return localVol(t, underlyingLevel, false);
    }

    public final /*@Volatility*/ double localVol(final /*@Time*/ double t, final /*@Real*/ double underlyingLevel, final boolean extrapolate) {
        checkRange(t, underlyingLevel, extrapolate);
        return localVolImpl(t, underlyingLevel);
    }


    /**
     * @return the minimum strike for which the term structure can return vols
     */
    @Override
    public abstract /*@Real*/ double minStrike();

    /**
     * @return the maximum strike for which the term structure can return vols
     */
    @Override
    public abstract /*@Real*/ double maxStrike();





    //
    // Calculations
    //
    //        These methods must be implemented in derived classes to perform
    //        the actual volatility calculations. When they are called,
    //        range check has already been performed; therefore, they must
    //        assume that extrapolation is required.
    //
    
    /**
     * Local Vol calculation
     */
    protected abstract /*@Volatility*/ double localVolImpl(final /*@Time*/ double t, final /*@Real*/ double strike);



    private final void checkRange(final /*@Time*/ double t, final /*@Real*/ double strike, final boolean extrapolate) {
        super.checkRange(t, extrapolate);
        /*@Real*/ final double minStrike = minStrike();
        /*@Real*/ final double maxStrike = maxStrike();
        QL.require(extrapolate||allowsExtrapolation()||(strike>=minStrike&&strike<=maxStrike) , "strike is outside curve domain"); // TODO: message
    }

    
    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<LocalVolTermStructure> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            throw new LibraryException("not a local-volatility term structure visitor"); // TODO: message
        }
    }

}
