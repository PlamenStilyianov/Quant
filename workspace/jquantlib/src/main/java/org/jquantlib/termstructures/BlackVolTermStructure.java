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
 * Black-volatility term structure
 * <p>
 * This abstract class defines the interface of concrete Black-volatility term
 * structures which will be derived from this one.
 * <p>
 * Volatilities are assumed to be expressed on an annual basis.
 *
 * @author Richard Gomes
 */
public abstract class BlackVolTermStructure extends VolatilityTermStructure implements PolymorphicVisitable {

    static private final double dT = 1.0/365.0;

    /**
     * The minimum strike for which the term structure can return vols
     */
    @Override
    public abstract /*@Real*/ double minStrike();

    /**
     * The maximum strike for which the term structure can return vols
     */
    @Override
    public abstract /*@Real*/ double maxStrike();

    protected abstract /*@Volatility*/ double blackVolImpl(final /*@Time*/ double maturity, final /*@Real*/ double strike);

    protected abstract /*@Variance*/ double blackVarianceImpl(final /*@Time*/ double maturity, final /*@Real*/ double strike);



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
    public BlackVolTermStructure() {
        this(new Calendar(), BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public BlackVolTermStructure(final Calendar cal) {
        this(cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * 'default' constructor
     * <p>
     * @warning term structures initialized by means of this
     *          constructor must manage their own reference date
     *          by overriding the referenceDate() method.
     */
    public BlackVolTermStructure(
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
    public BlackVolTermStructure(
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(cal, bdc, dc);
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVolTermStructure(final Date referenceDate) {
        this(referenceDate, new Calendar(), BusinessDayConvention.Following, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVolTermStructure(
            final Date referenceDate,
            final Calendar cal) {
        this(referenceDate, cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVolTermStructure(
            final Date referenceDate,
            final Calendar cal,
            final BusinessDayConvention bdc) {
        this(referenceDate, cal, bdc, new DayCounter());
    }

    /**
     *  initialize with a fixed reference date
     */
    public BlackVolTermStructure(
            final Date referenceDate,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(referenceDate, cal, bdc, dc);
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public BlackVolTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal) {
        this(settlementDays, cal, BusinessDayConvention.Following, new DayCounter());
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public BlackVolTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal,
            final BusinessDayConvention bdc) {
        this(settlementDays, cal, bdc, new DayCounter());
    }

    /**
     * calculate the reference date based on the global evaluation date
     */
    public BlackVolTermStructure(
            /*@Natural*/ final int settlementDays,
            final Calendar cal,
            final BusinessDayConvention bdc,
            final DayCounter dc) {
        super(settlementDays, cal, bdc, dc);
    }


    //
    // public methods
    //

    /**
     * Present (a.k.a spot) volatility
     */
    public final /*@Volatility*/ double blackVol(final Date maturity, final /*@Real*/ double strike) {
        return blackVol(maturity, strike, false);
    }

    /**
     * Present (a.k.a spot) volatility
     */
    public final /*@Volatility*/ double blackVol(final Date maturity, final /*@Real*/ double strike, final boolean extrapolate) {
        checkRange(maturity, extrapolate);
        checkStrike(strike, extrapolate);
        /*@Time*/ final double t = timeFromReference(maturity);
        return blackVolImpl(t, strike);
    }

    /**
     * Present (a.k.a spot) volatility
     */
    public final /*@Volatility*/ double blackVol(final /*@Time*/ double maturity, final /*@Real*/ double strike) {
        return blackVol(maturity, strike, false);
    }

    /**
     * Present (a.k.a spot) volatility
     */
    public final /*@Volatility*/ double blackVol(final /*@Time*/ double maturity, final /*@Real*/ double strike, final boolean extrapolate) {
        checkRange(maturity, extrapolate);
        checkStrike(strike, extrapolate);
        return blackVolImpl(maturity, strike);
    }

    /**
     * Present (a.k.a spot) variance
     */
    public final /*@Variance*/ double blackVariance(final Date maturity, final /*@Real*/ double strike) {
        return blackVariance(maturity, strike, false);
    }

    /**
     * Present (a.k.a spot) variance
     */
    public final /*@Variance*/ double blackVariance(final Date maturity, final /*@Real*/ double strike, final boolean extrapolate) {
        checkRange(maturity, extrapolate);
        checkStrike(strike, extrapolate);
        /*@Time*/ final double t = timeFromReference(maturity);
        return blackVarianceImpl(t, strike);
    }

    /**
     * Present (a.k.a spot) variance
     */
    public final /*@Variance*/ double blackVariance(final /*@Time*/ double maturity, final /*@Real*/ double strike) {
        return blackVariance(maturity, strike, false);
    }

    /**
     * Present (a.k.a spot) variance
     */
    public final /*@Variance*/ double blackVariance(final /*@Time*/ double maturity, final /*@Real*/ double strike, final boolean extrapolate) {
        checkRange(maturity, extrapolate);
        checkStrike(strike, extrapolate);
        return blackVarianceImpl(maturity, strike);
    }


    /**
     * Future (a.k.a. forward) volatility
     *
     * @param date1
     * @param date2
     * @param strike
     * @param extrapolate
     * @return
     */
    public final /*@Volatility*/ double blackForwardVol(final Date date1, final Date date2, final /*@Real*/ double strike, final boolean extrapolate) {
        QL.require(date1.le(date2), "date1 later than date2"); // TODO: message
        /*@Time*/ final double time1 = timeFromReference(date1);
        /*@Time*/ final double time2 = timeFromReference(date2);
        return blackForwardVol(time1, time2, strike, extrapolate);
    }

    /**
     * Future (a.k.a. forward) volatility
     *
     * @param time1
     * @param time2
     * @param strike
     * @param extrapolate
     * @return
     */
    public final /*@Volatility*/ double blackForwardVol(final /*@Time*/ double time1, final /*@Time*/ double time2, final /*@Real*/ double strike, final boolean extrapolate) {
        /*@Time*/ final double t1 = time1;
        /*@Time*/ final double t2 = time2;
        QL.require(t1 <= t2 , "t1 later than t2"); // TODO: message
        checkRange(time2, extrapolate);
        checkStrike(strike, extrapolate);
        if (t1==t2) {
            if (t1==0.0) {
                /*@Time*/ final double epsilon = 1.0e-5;
                /*@Variance*/ final double var = blackVarianceImpl(epsilon, strike);
                return Math.sqrt(var/epsilon);
            } else {
                final double epsilon = Math.min(1.0e-5, t1);
                /*@Variance*/ final double var1 = blackVarianceImpl(t1-epsilon, strike);
                /*@Variance*/ final double var2 = blackVarianceImpl(t1+epsilon, strike);
                QL.require(var2 >= var1 , "variances must be non-decreasing"); // TODO: message
                return  Math.sqrt((var2-var1) / (2*epsilon));
            }
        } else {
            /*@Variance*/ final double var1 = blackVarianceImpl(time1, strike);
            /*@Variance*/ final double var2 = blackVarianceImpl(time2, strike);
            QL.require(var2 >= var1 , "variances must be non-decreasing"); // TODO: message
            return  Math.sqrt((var2-var1)/(t2-t1));
        }
    }

    /**
     * Future (a.k.a. forward) variance
     *
     * @param date1
     * @param date2
     * @param strike
     * @param extrapolate
     * @return
     */
    public final /*@Variance*/ double blackForwardVariance(final Date date1, final Date date2, final /*@Real*/ double strike, final boolean extrapolate) {
        QL.require(date1.le(date2) , "date1 later than date2"); // TODO: message
        /*@Time*/ final double time1 = timeFromReference(date1);
        /*@Time*/ final double time2 = timeFromReference(date2);
        return blackForwardVariance(time1, time2, strike, extrapolate);
    }

    /**
     * Future (a.k.a. forward) variance
     *
     * @param time1
     * @param time2
     * @param strike
     * @param extrapolate
     * @return
     */
    public final /*@Variance*/ double blackForwardVariance(final /*@Time*/ double time1, final /*@Time*/ double time2, final /*@Real*/ double strike, final boolean extrapolate) {
        /*@Time*/ final double t1 = time1;
        /*@Time*/ final double t2 = time2;
        QL.require(t1<=t2 , "t1 later than t2"); // TODO: message
        checkRange(time2, extrapolate);
        checkStrike(strike, extrapolate);
        /*@Variance*/ final double v1 = blackVarianceImpl(time1, strike);
        /*@Variance*/ final double v2 = blackVarianceImpl(time2, strike);
        QL.require(v2 >= v1 , "variances must be non-decreasing"); // TODO: message
        return v2-v1;
    }

    
    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<BlackVolTermStructure> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            throw new LibraryException("not a Black-volatility term structure visitor"); // TODO: message
        }
    }

}
