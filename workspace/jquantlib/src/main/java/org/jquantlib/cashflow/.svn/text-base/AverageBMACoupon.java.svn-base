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

/*
 Copyright (C) 2007 Giorgio Facchinetti
 Copyright (C) 2007 Cristina Duminuco

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

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.BMAIndex;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;
/**
 * 
 * Average BMA coupon
 * Coupon paying a BMA index, where the coupon rate is a
 * weighted average of relevant fixings.
 * 
 * The weighted average is computed based on the
 * actual calendar days for which a given fixing is valid and
 * contributing to the given interest period.
 * 
 * Before weights are computed, the fixing schedule is adjusted
 * for the index's fixing day gap. See rate() method for details.
 * 
 * @author Tim Blackler
 *
 */
// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class AverageBMACoupon extends FloatingRateCoupon {

    private final Schedule fixingSchedule;
    private final static int bmaCutoffDays = 0;
    
    public AverageBMACoupon(
            final Date paymentDate,
            final double nominal,
            final Date startDate,
            final Date endDate,
            final BMAIndex index,
            final double gearing,
            final double spread,
            final Date refPeriodStart,
            final Date refPeriodEnd,
            final DayCounter dayCounter) {
        super(paymentDate, nominal, startDate, endDate, index.fixingDays(), index,
                gearing, spread, refPeriodStart, refPeriodEnd,
                dayCounter, false);
        
        this.fixingSchedule = index.fixingSchedule(index.fixingCalendar()
        		                                        .advance(startDate, 
        		                                        		 new Period((index.fixingDays() + bmaCutoffDays)*-1,TimeUnit.Days), 
        		                                        		 BusinessDayConvention.Preceding)
        		                                   ,endDate);
        this.setPricer(new AverageBMACouponPricer());
    }


    public /*@Rate*/ Date fixingDate() {
		throw new LibraryException("no single fixing for average-BMA coupon");
    }
    

    public List<Date> fixingDates() {
    	return fixingSchedule.dates();
    }   
    

        public /*@Rate*/ double indexFixing() {
		throw new LibraryException("no single fixing for average-BMA coupon");
    }    
   
    
    public List<Double> indexFixings() {
    	List<Double> fixings = new ArrayList<Double>(fixingSchedule.size());
    	for (int i = 0; i < fixingSchedule.size(); i++) {
    		fixings.add(index_.fixing(fixingSchedule.date(i)));
    	}
    	return fixings;
    }
    
    public /*@Rate*/ double convexityAdjustment() {
		throw new LibraryException("not defined for average-BMA coupon");
    }
    
    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<AverageBMACoupon> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
