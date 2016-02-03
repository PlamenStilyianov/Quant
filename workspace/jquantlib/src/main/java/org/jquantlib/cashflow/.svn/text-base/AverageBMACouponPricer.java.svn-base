/*
 Copyright (C) 2011 Tim Blackler

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

import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.indexes.InterestRateIndex;
import org.jquantlib.lang.annotation.Natural;
import org.jquantlib.lang.annotation.Rate;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Date;

public class AverageBMACouponPricer extends FloatingRateCouponPricer {

	private AverageBMACoupon coupon;
	
	@Override
	public /* @Real */ double swapletPrice() {
		throw new LibraryException("not available");
	}

	@Override
	public /* @Real */ double swapletRate() {

		List<Date> fixingDates = coupon.fixingDates();
		InterestRateIndex index = coupon.index();
		
		@Natural int cutoffDays = 0; // to be verified
		Date startDate = coupon.accrualStartDate().sub(cutoffDays);
		Date endDate = coupon.accrualEndDate().sub(cutoffDays);
		Date d1 = new Date(startDate.serialNumber());
		Date d2 = new Date(startDate.serialNumber());

		QL.require(fixingDates.size() > 0, "fixing date list empty");
		QL.require(index.valueDate(fixingDates.get(0)).le(startDate), "first fixing date valid after period start");
		QL.require(index.valueDate(fixingDates.get((fixingDates.size()-1))).ge(startDate), "last fixing date valid before period end");
		
		@Rate double avgBMA = 0.0;
		int days = 0;
		
		for (int i=0;i<(fixingDates.size()-1);i++) {
			Date valueDate = index.valueDate(fixingDates.get(i));
			Date nextValueDate = index.valueDate(fixingDates.get(i+1));
			
			if (fixingDates.get(i).ge(endDate) || valueDate.ge(valueDate)) {
				break;
			}
			
			if (fixingDates.get(i+1).lt(startDate) || nextValueDate.le(startDate)) {
				continue;
			}
			
			d2 = Date.min(nextValueDate, endDate);
			avgBMA += (index.fixing(fixingDates.get(i))) * (d2.sub(d1));
			days += d2.sub(d1);
			
			d1 = d2;
		}
		
		avgBMA /= (endDate.sub(startDate));
		
		QL.ensure(days == (endDate.sub(startDate)) , "averaging days " + days + " differ from interest days " + endDate.sub(startDate));
		
		return coupon.gearing() * avgBMA + coupon.spread();

	}

	@Override
	public /* @Real */ double capletPrice(final double effectiveCap) {
		throw new LibraryException("not available");
	}

	@Override
	public /* @Real */ double capletRate(final double effectiveCap) {
		throw new LibraryException("not available");
	}

	@Override
	public /* @Real */ double floorletPrice(final double effectiveFloor) {
		throw new LibraryException("not available");
	}

	@Override
	public /* @Real */ double floorletRate(final double effectiveFloor) {
		throw new LibraryException("not available");
	}

	@Override
	public void initialize(final FloatingRateCoupon coupon) {
			QL.ensure(coupon instanceof AverageBMACoupon, "wrong coupon type");
            this.coupon = (AverageBMACoupon)coupon;               
	}

}
