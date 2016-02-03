/*
 Copyright (C) 2009 John Nichol

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
 Copyright (C) 2007 Ferdinando Ametrano
 Copyright (C) 2007 Chiara Fornarola

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

package org.jquantlib.instruments.bonds;

import org.jquantlib.QL;
import org.jquantlib.cashflow.IborLeg;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.instruments.Bond;
import org.jquantlib.math.Constants;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.quotes.Handle;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;


/**
 * floating-rate bond (possibly capped and/or floored)
 *
 * @category instruments
 *
 * @author John Nichol
 * @author Zahid Hussain
 */
//TEST: calculations are tested by checking results against cached values.
public class FloatingRateBond extends Bond {
	public FloatingRateBond(
	        final int settlementDays,
			final double faceAmount,
			final Schedule schedule,
			final IborIndex index,
			final DayCounter paymentDayCounter,
			final BusinessDayConvention paymentConvention,
			final int fixingDays,
			final Array gearings,
			final Array spreads,
			final Array caps,
			final Array floors,
			final boolean inArrears,
			final double redemption,
			final Date issueDate) {

		super(settlementDays, schedule.calendar(), issueDate);
		maturityDate_ = schedule.endDate().clone();

		cashflows_ = new IborLeg(schedule, index)
						.withNotionals(faceAmount)
						.withPaymentDayCounter(paymentDayCounter)
						.withPaymentAdjustment(paymentConvention)
						.withFixingDays(fixingDays)
						.withGearings(gearings)
						.withSpreads(spreads)
						.withCaps(caps)
						.withFloors(floors)
						.inArrears(inArrears).Leg();

		addRedemptionsToCashflows(new double[]{redemption});

		QL.ensure(!cashflows().isEmpty(), "bond with no cashflows!");
		QL.ensure(redemptions_.size() == 1, "multiple redemptions created");
		index.addObserver(this);
	}

	public FloatingRateBond(
	        final int settlementDays,
			final double faceAmount,
			final Schedule schedule,
			final IborIndex index,
			final DayCounter accrualDayCounter) {
		this(settlementDays, faceAmount, schedule,index, accrualDayCounter, 
				BusinessDayConvention.Following, Constants.NULL_INTEGER, 
				new Array(new double[] { 1.0 }), new Array(new double[] { 0.0 }), 
				new Array(0), new Array(0), false, 100.0, new Date());

	}

	public FloatingRateBond(final int settlementDays,
							final double faceAmount,
							final Date startDate,
							final Date maturityDate,
							final Frequency couponFrequency,
							final Calendar calendar,
							final Handle<IborIndex> index,
							final DayCounter accrualDayCounter,
							final BusinessDayConvention accrualConvention,
							final BusinessDayConvention paymentConvention,
							final int fixingDays,
							final Array gearings,
							final Array spreads,
							final Array caps,
							final Array floors,
							final boolean inArrears,
							final double redemption,
							final Date issueDate,
							final Date stubDate,
							final DateGeneration.Rule rule,
							final boolean endOfMonth) {
		super(settlementDays, calendar, issueDate);

		maturityDate_ = maturityDate.clone();

		Date firstDate = null, nextToLastDate = null;
		switch (rule) {
			case Backward:
				firstDate = new Date();
				nextToLastDate = stubDate;
				break;
			case Forward:
				firstDate = stubDate;
				nextToLastDate = new Date();
				break;
			case Zero:
			case ThirdWednesday:
			case Twentieth:
			case TwentiethIMM:
				QL.error("stub date (" + stubDate + ") not allowed with " +
					rule + " DateGeneration::Rule");
			default:
			QL.error("unknown DateGeneration::Rule (" + rule + ")");
		}

		Schedule schedule = new Schedule(startDate, maturityDate_, new Period(couponFrequency),
								calendar_, accrualConvention, accrualConvention,
								rule, endOfMonth, firstDate, nextToLastDate);

		cashflows_ = new IborLeg(schedule, index.currentLink())
						.withNotionals(faceAmount)
						.withPaymentDayCounter(accrualDayCounter)
						.withPaymentAdjustment(paymentConvention)
						.withFixingDays(fixingDays)
						.withGearings(gearings)
						.withSpreads(spreads)
						.withCaps(caps)
						.withFloors(floors)
						.inArrears(inArrears).Leg();

		addRedemptionsToCashflows(new double[] {redemption });

		QL.ensure(!cashflows().isEmpty(), "bond with no cashflows!");
		QL.ensure(redemptions_.size() == 1, "multiple redemptions created");

		index.addObserver(this);
	}

	public FloatingRateBond(
	        final int settlementDays,
			final double faceAmount,
			final Date startDate,
			final Date maturityDate,
			final Frequency couponFrequency,
			final Calendar calendar,
			final Handle<IborIndex> index,
			final DayCounter accrualDayCounter) {
		this(settlementDays, faceAmount, startDate, maturityDate,
				couponFrequency, calendar, index,
				accrualDayCounter, BusinessDayConvention.Following, BusinessDayConvention.Following,
				Constants.NULL_INTEGER, new Array(new double[] { 1.0 }),
				new Array(new double[] { 0.0 }), new Array(0), new Array(0),
				false, 100.0, new Date(), new Date(),
				DateGeneration.Rule.Backward, false);
	}
}
