/*
Copyright (C) 2009 Ueli Hofstetter
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
 Copyright (C) 2007 Ferdinando Ametrano
 Copyright (C) 2007 Giorgio Facchinetti
 Copyright (C) 2007 Cristina Duminuco
 Copyright (C) 2007 StatPro Italia srl

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
package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.BMAIndex;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Schedule;

/**
 * Helper class building a sequence of average BMA coupons
 *
 * @author Ueli Hofstetter
 * @author John Martin
 */
public class AverageBMALeg {
	
    private final Schedule schedule;
    private final BMAIndex index;
    private Array notionals;
    private DayCounter paymentDayCounter;
    private BusinessDayConvention paymentAdjustment;
    private Array gearings;
    private Array spreads;

    public AverageBMALeg(final Schedule schedule, final BMAIndex index) {
        this.schedule = schedule;
        this.index = index;
        this.paymentAdjustment = BusinessDayConvention.Following;

        // TODO : review initialization
        // these are vectors in quantlib, therfor they must be initalized to default
        // values or nullable. since we have decided to write the code without null checks
        // all over the place we are going to initialze them for now to be consistent with
        // quantlib behavoir
        gearings = new Array(0);
        spreads = new Array(0);
  
    }

    public final AverageBMALeg withNotionals(/* @Real */final double notional) {
        notionals = new Array(new double[] { notional });// std::vector<Real>(1,notional);
        return this;
    }

    public final AverageBMALeg withNotionals(final Array notionals) {
        this.notionals = notionals;
        return this;
    }

    public final AverageBMALeg withPaymentDayCounter(final DayCounter dayCounter) {
        paymentDayCounter = dayCounter;
        return this;
    }

    public final AverageBMALeg withPaymentAdjustment(final BusinessDayConvention convention) {
        paymentAdjustment = convention;
        return this;
    }

    public AverageBMALeg withGearings(/* @Real */final double gearing) {
        gearings = new Array(new double[] { gearing });
        return this;
    }

    public AverageBMALeg withGearings(final Array gearings) {
        this.gearings = gearings;
        return this;
    }

    public AverageBMALeg withSpreads(/* @Spread */final double spread) {
        spreads = new Array(new double[] { spread });
        return this;
    }

    public AverageBMALeg withSpreads(final Array spreads) {
        this.spreads = spreads;
        return this;
    }

    public Leg Leg() /* @ReadOnly */{

        QL.require(!this.notionals.empty(), "no notional given");

        Leg cashflows = new Leg();

        // the following is not always correct
        Calendar calendar = schedule.calendar();

        Date refStart, start, refEnd, end;
        Date paymentDate;

        int n = schedule.size()-1;
        	
        for (int i=0; i<n ; i++) {
        	refStart = schedule.date(i);
        	start = schedule.date(i);
        	
        	refEnd = schedule.date(i+1);
        	end = schedule.date(i+1);
        	paymentDate = calendar.adjust(end, paymentAdjustment);
        	
        	if (i == 0 && !schedule.isRegular(i+1)) {
        		refStart = calendar.adjust(end.sub(schedule.tenor()),
                        				   paymentAdjustment);
        	}

        	if (i == n-1 && !schedule.isRegular(i+1))
        		refEnd = calendar.adjust(start.add(schedule.tenor()),
                                   		 paymentAdjustment);
        	
        	AverageBMACoupon coupon = new AverageBMACoupon(paymentDate,
        													notionals.get(i) != 0.0 ? notionals.get(i):notionals.last(),
        													start,
        													end,
        													index,
        													gearings.get(i) != 0.0 ? gearings.get(i) : 1.0,
        													spreads.get(i) != 0.0 ? spreads.get(i) : 0.0,
        													refStart,
        													refEnd,
        													paymentDayCounter);
        	
        	cashflows.add(coupon);       	
        	
        }
    	
        

        return cashflows;
    }

}