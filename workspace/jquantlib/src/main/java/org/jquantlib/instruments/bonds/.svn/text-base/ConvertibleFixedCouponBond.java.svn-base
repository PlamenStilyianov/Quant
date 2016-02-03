/*
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
package org.jquantlib.instruments.bonds;

import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.cashflow.Callability;
import org.jquantlib.cashflow.Dividend;
import org.jquantlib.cashflow.FixedRateLeg;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.CallabilitySchedule;
import org.jquantlib.instruments.DividendSchedule;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.time.Date;
import org.jquantlib.time.Schedule;

/**
 * Convertible fixed-coupon bond
 *
 * @warning Most methods inherited from Bond (such as yield or
 * the yield-based dirtyPrice and cleanPrice) refer to
 * the underlying plain-vanilla bond and do not take
 * convertibility and callability into account.
 *
 * @author Daniel Kong
 */
//TODO: Work in progress
public class ConvertibleFixedCouponBond extends ConvertibleBond {

	public ConvertibleFixedCouponBond(
	          final Exercise exercise,
	          final double conversionRatio,
	          final DividendSchedule dividends,
	          final CallabilitySchedule callability,
	          final Handle<Quote> creditSpread,
	          final Date issueDate,
	          final int settlementDays,
	          /*@Rate*/final double[] coupons,
	          final DayCounter dayCounter,
	          final Schedule schedule){
		this(exercise, conversionRatio, dividends, callability, creditSpread,
		     issueDate, settlementDays, coupons, dayCounter, schedule, 100);
	}

	public ConvertibleFixedCouponBond(
			final Exercise exercise,
			final double conversionRatio,
	        final DividendSchedule dividends,
	        final CallabilitySchedule callability,
			final Handle<Quote> creditSpread,
			final Date issueDate,
			final int settlementDays,
			/*Rate*/final double[] coupons,
			final DayCounter dayCounter,
			final Schedule schedule,
			final double redemption) {
		super(exercise, conversionRatio, dividends, callability, creditSpread,
		      issueDate, settlementDays, dayCounter, schedule, redemption);

		// notional forcibly set to 100
        this.cashflows_ = new FixedRateLeg(schedule,dayCounter)
            .withNotionals(100.0)
            .withCouponRates(coupons)
            .withPaymentAdjustment(schedule.businessDayConvention())
            .Leg();

        addRedemptionsToCashflows(new double[] { redemption });

        QL.ensure(redemptions_.size() == 1, "multiple redemptions created");

        this.option = new ConvertibleBondOption(this, exercise, conversionRatio,
                                      dividends, callability, creditSpread,
                                      cashflows_, dayCounter, schedule,
                                      issueDate, settlementDays, redemption);
	}

}
