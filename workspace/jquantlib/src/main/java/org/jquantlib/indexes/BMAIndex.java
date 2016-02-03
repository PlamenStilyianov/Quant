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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006, 2007, 2008 StatPro Italia srl

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

package org.jquantlib.indexes;


import org.jquantlib.QL;
import org.jquantlib.currencies.America.USDCurrency;
import org.jquantlib.daycounters.ActualActual;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Date;
import org.jquantlib.time.MakeSchedule;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.Weekday;
import org.jquantlib.time.calendars.UnitedStates;

/**
 * Bond Market Association index
 *
 * The BMA index is the short-term tax-exempt reference index of
 * the Bond Market Association.  It has tenor one week, is fixed
 * weekly on Wednesdays and is applied with a one-day's fixing
 * gap from Thursdays on for one week.  It is the tax-exempt
 * correspondent of the 1M USD-Libor.
 * 
 * @author Tim Blackler
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class BMAIndex extends InterestRateIndex {

    private final Handle<YieldTermStructure> termStructure;
    
    public BMAIndex() {
    	this(new Handle<YieldTermStructure>());
    }
    public BMAIndex(final Handle<YieldTermStructure> h) {
        super("BMA", new Period(1,TimeUnit.Weeks), 1, new USDCurrency(), new UnitedStates(UnitedStates.Market.NYSE), new ActualActual(ActualActual.Convention.ISDA));

        this.termStructure = h;
        if (termStructure != null) {
        	termStructure.addObserver(this);
        }
    }

    public BMAIndex clone(final Handle<YieldTermStructure> h) {
        final BMAIndex clone = new BMAIndex(h);
        return clone;
    }
    
    @Override
    public String name() {
        return "BMA";
    }
            
    public Schedule fixingSchedule(final Date start, final Date end) {
    	return (new MakeSchedule(previousWednesday(start),
    							nextWednesday(end),
    							new Period (1,TimeUnit.Weeks),
    							fixingCalendar,  							
    							BusinessDayConvention.Following)).forwards().schedule();
    }
    
    @Override
    protected double forecastFixing(final Date fixingDate) {
        QL.require(! termStructure.empty() , "no forecasting term structure set to " + name());  // TODO: message
        final Date start = fixingCalendar().advance(fixingDate, 1, TimeUnit.Days);
        final Date end  = maturityDate(start);
        return termStructure.currentLink().forwardRate(start, 
        											   end,
        											   dayCounter,
        											   Compounding.Simple).rate();
    }
    
    @Override
    public boolean isValidFixingDate(final Date fixingDate) {
        // either the fixing date is last Wednesday, or all days
        // between last Wednesday included and the fixing date are
        // holidays
    	for (Date d = previousWednesday(fixingDate); d.lt(fixingDate); d.inc()) {
    		if (fixingCalendar.isBusinessDay(d)) {
    			return false;
    		}
    	}
        // also, the fixing date itself must be a business day
        return fixingCalendar.isBusinessDay(fixingDate);
    }    
    
    @Override
    public Handle<YieldTermStructure> termStructure() {
        return termStructure;
    }

    @Override
    public Date maturityDate(final Date valueDate) {
    	Date fixingDate = fixingCalendar().advance(valueDate, -1, TimeUnit.Days);
    	Date nextWednesday = nextWednesday(fixingDate);
        return fixingCalendar().advance(nextWednesday, 1, TimeUnit.Days);
    }
 
    private Date previousWednesday(final Date date) {
        Weekday w = date.weekday();
        if (w.value() >= 4) // roll back w-4 days
            return date.subAssign((w.value() - 4));
        else // roll forward 4-w days and back one week
            return date.addAssign(4 - w.value() - 7);
    }

    private Date nextWednesday(final Date date) {
        return previousWednesday(date.addAssign(7));
    }
    
    
    
}
