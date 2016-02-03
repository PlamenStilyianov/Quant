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
 Copyright (C) 2004, 2005, 2006, 2007 Ferdinando Ametrano

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

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.annotation.Natural;
import org.jquantlib.lang.annotation.Rate;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.quotes.Handle;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;

/**
 * 
 * Base class for year-on-year inflation term structures.
 * 
 * @author Tim Blackler
 *
 */

public abstract class YoYInflationTermStructure extends InflationTermStructure {

	
    public YoYInflationTermStructure(final DayCounter dayCounter,
						    		  final Period lag,
						    		  final Frequency frequency,
						    		  final @Rate double baseYoYRate,
						    		  final Handle<YieldTermStructure> yTS) {
    	super(lag, frequency, baseYoYRate, yTS, dayCounter);
    	
    }


    public YoYInflationTermStructure(final Date referenceDate,
									  final Calendar calendar,
									  final DayCounter dayCounter,
						  		  	  final Period lag,
									  final Frequency frequency,
									  final @Rate double baseYoYRate,
									  final Handle<YieldTermStructure> yTS) {

    	super(referenceDate, lag, frequency, baseYoYRate, yTS, calendar, dayCounter);
    }
    
    
    public YoYInflationTermStructure(final @Natural int settlementDays,
	    							  final Calendar calendar,
	    							  final DayCounter dayCounter,
	    							  final Period lag,
	    							  final Frequency frequency,
	    							  final @Rate double baseYoYRate,
	    							  final Handle<YieldTermStructure> yTS) {
    	
    	super(settlementDays, calendar, lag, frequency,baseYoYRate, yTS, dayCounter);
    }  
    
    
    public /*@Rate*/ double yoyRate(final Date date) {
    	return this.yoyRate(date, false);
    }
    
    public /*@Rate*/ double yoyRate(final Date date,
            						 final boolean extrapolate) {
    	this.checkRange(date, extrapolate);
    	return this.yoyRate(timeFromReference(date));
    }

    public /*@Rate*/ double yoyRate(final @Time double time) {
    	return this.yoyRate(time, false);
    }
    
    public /*@Rate*/ double yoyRate(final @Time double time,
            						 final boolean extrapolate) {
    	this.checkRange(time, extrapolate);
    	return this.yoyRateImpl(time);
    }

    protected abstract /*@Rate*/ double yoyRateImpl(@Time double time);
}
