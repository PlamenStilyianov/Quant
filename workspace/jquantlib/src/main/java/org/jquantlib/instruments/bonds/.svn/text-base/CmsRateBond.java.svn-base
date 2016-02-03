/*
 Copyright (C) 2010 Zahid Hussain

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

import org.jquantlib.QL;
import org.jquantlib.cashflow.CmsLeg;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.SwapIndex;
import org.jquantlib.instruments.Bond;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Date;
import org.jquantlib.time.Schedule;

/**
*
* @author Zahid Hussain
*/
//TODO: Write test case

public class CmsRateBond extends Bond {

	public CmsRateBond(
            final /*Natural */ int settlementDays,
            final /*Real*/ double faceAmount,
            final Schedule schedule,
            final SwapIndex index,
            final DayCounter paymentDayCounter,
            final BusinessDayConvention paymentConvention,
            final /*Natural*/ int fixingDays,
            final Array gearings,
            final Array spreads,
            final Array caps,
            final Array floors,
            final boolean inArrears,
            final /*Real*/ double  redemption,
            final Date issueDate) {
		
		super(settlementDays, schedule.calendar(), issueDate);
		maturityDate_ = schedule.endDate().clone();
		cashflows_ = new CmsLeg(schedule, index)
				.withNotionals(faceAmount)
				.withPaymentDayCounter(paymentDayCounter)
				.withPaymentAdjustment(paymentConvention)
				.withFixingDays(fixingDays)
				.withGearings(gearings)
				.withSpreads(spreads)
				.withCaps(caps)
				.withFloors(floors)
				.inArrears(inArrears).Leg();

		addRedemptionsToCashflows(new double[] { redemption });

		QL.ensure(!cashflows().isEmpty(), "bond with no cashflows!");
		QL.ensure(redemptions_.size() == 1, "multiple redemptions created");
		index.addObserver(this);		
	}
	
	public CmsRateBond(
            final /*Natural */ int settlementDays,
            final /*Real*/ double faceAmount,
            final Schedule schedule,
            final SwapIndex index,
            final DayCounter paymentDayCounter) {
		
		this(settlementDays, faceAmount, schedule, index, paymentDayCounter,
				BusinessDayConvention.Following, //default
				0,								//default fixing days
				new Array(1).fill(1.0),			//default gearings
				new Array(1).fill(0.0), 		//default spread
				new Array(0), 					//default caps
				new Array(0), 					//default floor
				false, 							//defaul in Arrears
				100.0,							// default redemption
				new Date() 						// default issue date
				);
	}

}
