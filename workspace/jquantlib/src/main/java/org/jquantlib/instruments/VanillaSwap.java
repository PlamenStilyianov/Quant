/*
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
 Copyright (C) 2006, 2008 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006, 2007 StatPro Italia srl

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

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.cashflow.CashFlow;
import org.jquantlib.cashflow.FixedRateCoupon;
import org.jquantlib.cashflow.FixedRateLeg;
import org.jquantlib.cashflow.IborCoupon;
import org.jquantlib.cashflow.IborLeg;
import org.jquantlib.cashflow.Leg;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Date;
import org.jquantlib.time.Schedule;

/**
 * Plain-vanilla swap
 *
 * @note if you define TodaysPayments like this
 * <pre>
 * new Settings().setTodaysPayments(true);
 * </pre>
 * payments occurring at the settlement date of
 * the swap are included in the NPV, and therefore
 * affect the fair-rate and fair-spread
 * calculation. This might not be what you want.
 *
 * @category instruments
 *
 * @author Richard Gomes
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class VanillaSwap extends Swap {

    static final /*@Spread*/ double  basisPoint = 1.0e-4;

    private final Type type;
    private final /*@Real*/ double nominal;
    private final Schedule fixedSchedule;
    private final /*@Rate*/ double  fixedRate;
    private final DayCounter fixedDayCount;
    private final Schedule floatingSchedule;
    private final IborIndex iborIndex;
    private final /*@Spread*/ double spread;
    private final DayCounter floatingDayCount;
    private final BusinessDayConvention paymentConvention;
    // private final Array fixingDays;

    // results
    private /*@Rate*/ double fairRate;
    private /*@Spread*/ double fairSpread;


    public VanillaSwap(
            final Type type,
            final /*@Real*/ double nominal,
            final Schedule fixedSchedule,
            final /*@Rate*/ double fixedRate,
            final DayCounter fixedDayCount,
            final Schedule floatSchedule,
            final IborIndex iborIndex,
            final /*@Spread*/ double spread,
            final DayCounter floatingDayCount) {
        this(type, nominal, fixedSchedule, fixedRate, fixedDayCount, floatSchedule,
                iborIndex, spread, floatingDayCount, BusinessDayConvention.Following);
    }

    //FIXME: remove parameter "fixingDays"
    public VanillaSwap(
            final Type type,
            final /*@Real*/ double nominal,
            final Schedule fixedSchedule,
            final /*@Rate*/ double fixedRate,
            final DayCounter fixedDayCount,
            final Schedule floatSchedule,
            final IborIndex iborIndex,
            final /*@Spread*/ double spread,
            final DayCounter floatingDayCount,
            final BusinessDayConvention paymentConvention
    /*, final Array fixingDays*/) {
        super(2);
        this.type = type;
        this.nominal = nominal;
        this.fixedSchedule = fixedSchedule;
        this.fixedRate = fixedRate;
        this.fixedDayCount = fixedDayCount;
        this.floatingSchedule = floatSchedule;
        this.iborIndex = iborIndex;
        this.spread = spread;
        this.floatingDayCount = floatingDayCount;
        this.paymentConvention = paymentConvention;
        //FIXME this.fixingDays = fixingDays;

        final Leg fixedLeg = new FixedRateLeg(fixedSchedule, fixedDayCount)
        .withNotionals(nominal)
        .withCouponRates(fixedRate)
        .withPaymentAdjustment(paymentConvention)
        .Leg();

        // JM where are gearings set they cannot be null for the floating leg.
        final Leg floatingLeg = new IborLeg(floatingSchedule, iborIndex)
        .withNotionals(nominal)
        .withPaymentDayCounter(floatingDayCount)
        .withPaymentAdjustment(paymentConvention)

        //FIXME:: .withFixingDays (fixingDays) // FIXME: slight deviation from quantlib, need to expose fixing days up the stack

        .withSpreads(spread)

        // FIXME: JM quantlib does not assign this, it is currently required for construction
        // .withGearings(1.0)

        .Leg();

        for (final CashFlow item : floatingLeg) {
            item.addObserver(this);
        }

        super.legs.add(fixedLeg);
        super.legs.add(floatingLeg);
        if (type==Type.Payer) {
            super.payer[0] = -1.0;
            super.payer[1] = +1.0;
        } else {
            super.payer[0] = +1.0;
            super.payer[1] = -1.0;
        }
    }

    public /*@Rate*/ double  fairRate() /* @ReadOnly */ {
        calculate();
        QL.require(!Double.isNaN(fairRate) , "result not available"); // TODO: message
        return fairRate;
    }

    public /*@Spread*/ double fairSpread() /* @ReadOnly */ {
        calculate();
        QL.require(!Double.isNaN(fairSpread) , "result not available"); // TODO: message
        return fairSpread;
    }


    public final Leg fixedLeg() /* @ReadOnly */ {
        return legs.get(0);
    }

    public final Leg floatingLeg() /* @ReadOnly */ {
        return legs.get(1);
    }


    public /*@Real*/ double fixedLegBPS() /* @ReadOnly */ {
        calculate();
        QL.require(!Double.isNaN(legBPS[0]) , "result not available"); // TODO: message
        return legBPS[0];
    }

    public /*@Real*/ double floatingLegBPS() /* @ReadOnly */ {
        calculate();
        QL.require(!Double.isNaN(legBPS[1]) , "result not available");
        return legBPS[1];
    }

    public /*@Real*/ double fixedLegNPV() /* @ReadOnly */ {
        calculate();
        QL.require(!Double.isNaN(legNPV[0]) , "result not available"); // TODO: message
        return legNPV[0];
    }

    public /*@Real*/ double floatingLegNPV() /* @ReadOnly */ {
        calculate();
        QL.require(!Double.isNaN(legNPV[1]) , "result not available"); // TODO: message
        return legNPV[1];
    }

    @Override
    public void setupExpired() /* @ReadOnly */ {
        super.setupExpired();
        legBPS[0] = 0.0;
        legBPS[1] = 0.0;
        fairRate   = Constants.NULL_REAL;
        fairSpread = Constants.NULL_REAL;
    }

    @Override
    public void setupArguments(final PricingEngine.Arguments arguments) /* @ReadOnly */ {
        super.setupArguments(arguments);
        if (arguments.getClass().isAssignableFrom(VanillaSwap.Arguments.class)) {
            final VanillaSwap.ArgumentsImpl a = (VanillaSwap.ArgumentsImpl) arguments;

            a.type = type;
            a.nominal = nominal;

            final Leg fixedCoupons = fixedLeg();

            a.fixedResetDates = new ArrayList<Date>(fixedCoupons.size());
            a.fixedPayDates = new ArrayList<Date>(fixedCoupons.size());
            a.fixedCoupons = new ArrayList</*@Real*/ Double>(fixedCoupons.size());

            for (int i=0; i<fixedCoupons.size(); i++) {
                final FixedRateCoupon coupon = (FixedRateCoupon) fixedCoupons.get(i);
                a.fixedPayDates.set(i, coupon.date());
                a.fixedResetDates.set(i, coupon.accrualStartDate());
                a.fixedCoupons.set(i, coupon.amount());
            }

            final Leg floatingCoupons = floatingLeg();

            a.floatingResetDates = new ArrayList<Date>(floatingCoupons.size());
            a.floatingPayDates = new ArrayList<Date>(floatingCoupons.size());
            a.floatingFixingDates = new ArrayList<Date>(floatingCoupons.size());

            a.floatingAccrualTimes = new ArrayList</*@Time*/ Double>(floatingCoupons.size());
            a.floatingSpreads = new ArrayList</*@Spread*/ Double>(floatingCoupons.size());
            a.floatingCoupons = new ArrayList</*@Real*/ Double>(floatingCoupons.size());
            for (int i=0; i<floatingCoupons.size(); ++i) {
                final IborCoupon coupon = (IborCoupon) floatingCoupons.get(i);

                a.floatingResetDates.set(i, coupon.accrualStartDate());
                a.floatingPayDates.set(i, coupon.date());

                a.floatingFixingDates.set(i, coupon.fixingDate());
                a.floatingAccrualTimes.set(i, coupon.accrualPeriod());
                a.floatingSpreads.set(i, coupon.spread());
                try {
                    a.floatingCoupons.set(i, coupon.amount());
                } catch (final Exception e) {
                    a.floatingCoupons.set(i, Constants.NULL_REAL);
                }
            }
        }
    }


    @Override
    public void fetchResults(final PricingEngine.Results results) /* @ReadOnly */ {
        super.fetchResults(results);

        if (results.getClass().isAssignableFrom(VanillaSwap.Results.class)) {
            final VanillaSwap.ResultsImpl r = (VanillaSwap.ResultsImpl)results;
            fairRate = r.fairRate;
            fairSpread = r.fairSpread;
        } else {
            fairRate   = Constants.NULL_REAL;
            fairSpread = Constants.NULL_REAL;
        }

        if (Double.isNaN(fairRate)) {
            // calculate it from other results
            if (!Double.isNaN(legBPS[0])) {
                fairRate = fixedRate- NPV/(legBPS[0]/basisPoint);
            }
        }
        if (Double.isNaN(fairSpread)) {
            // ditto
            if (!Double.isNaN(legBPS[1])) {
                fairSpread = spread - NPV/(legBPS[1]/basisPoint);
            }
        }
    }


    @Override
    public String toString() {
        return type.toString();
    }


    //
    // inner public enums
    //

    public enum Type {
        Receiver (-1),
        Payer (1);

        private final int enumValue;

        private Type(final int frequency) {
            this.enumValue = frequency;
        }

        static public Type valueOf(final int value) {
            switch (value) {
            case -1:
                return Type.Receiver;
            case 1:
                return Type.Payer;
            default:
                throw new LibraryException("value must be one of -1, 1"); // TODO: message
            }
        }

        public int toInteger() {
            return this.enumValue;
        }
    }






    //
    // inner interfaces
    //

    public interface Arguments extends Swap.Arguments { /* marking interface */ }


    public interface Results extends Swap.Results { /* marking interface */ }


    //
    // ???? inner classes
    //


    /**
     * Arguments for simple swap calculation
     *
     * @author Richard Gomes
     */
    // TODO: code review :: object model needs to be validated and eventually refactored
    public class ArgumentsImpl extends Swap.ArgumentsImpl implements VanillaSwap.Arguments {

        public Type type;
        public /*@Real*/ double nominal;

        public List<Date> fixedResetDates;
        public List<Date> fixedPayDates;
        public List</*@Time*/ Double> floatingAccrualTimes;
        public List<Date> floatingResetDates;
        public List<Date> floatingFixingDates;
        public List<Date> floatingPayDates;

        public List</*@Real*/ Double> fixedCoupons;
        public List</*@Spread*/ Double> floatingSpreads;
        public List</*@Real*/ Double> floatingCoupons;


        @Override
        public void validate() /* @ReadOnly */ {
            super.validate();
            QL.require(!Double.isNaN(nominal) , "nominal null or not set"); // TODO: message
            QL.require(fixedResetDates.size() == fixedPayDates.size() , "number of fixed start dates different from number of fixed payment dates");
            QL.require(fixedPayDates.size() == fixedCoupons.size() , "number of fixed payment dates different from number of fixed coupon amounts");
            QL.require(floatingResetDates.size() == floatingPayDates.size() , "number of floating start dates different from number of floating payment dates");
            QL.require(floatingFixingDates.size() == floatingPayDates.size() , "number of floating fixing dates different from number of floating payment dates");
            QL.require(floatingAccrualTimes.size() == floatingPayDates.size() , "number of floating accrual Times different from number of floating payment dates");
            QL.require(floatingSpreads.size() == floatingPayDates.size() , "number of floating spreads different from number of floating payment dates");
            QL.require(floatingPayDates.size() == floatingCoupons.size() , "number of floating payment dates different from number of floating coupon amounts");
        }

    }




    /**
     * Results from simple swap calculation
     *
     * @author Richard Gomes
     */
    // TODO: code review :: object model needs to be validated and eventually refactored
    public class ResultsImpl extends Swap.ResultsImpl implements VanillaSwap.Results {

        public /*@Rate*/ double  fairRate;
        public /*@Spread*/ double  fairSpread;

        @Override
        public void reset() {
            super.reset();
            fairRate   = Constants.NULL_REAL;
            fairSpread = Constants.NULL_REAL;
        }


    }




}
