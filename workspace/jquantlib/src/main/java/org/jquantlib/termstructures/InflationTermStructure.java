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

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.annotation.Natural;
import org.jquantlib.lang.annotation.Rate;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.quotes.Handle;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.calendars.NullCalendar;
import org.jquantlib.util.Pair;

/**
 * 
 * Base Class for inflation term structures
 * 
 * @author Tim Blackler
 *
 */

public abstract class InflationTermStructure extends AbstractTermStructure {
	
    protected Handle<YieldTermStructure> nominalTermStructure;

    // connection with base index:
    //  lag to base date
    //  index
    //  whether or not to connect with the index at the short end
    //  (don't if you have no index set up)
    protected Period lag;
    protected Frequency frequency;
    protected @Rate double  baseRate;
    
    public InflationTermStructure(final Period lag,
    							  final Frequency frequency,
    							  final @Rate double baseRate,
    							  final Handle<YieldTermStructure> yTS) {

    	this(lag, frequency, baseRate, yTS, new Actual365Fixed());
 	
    }

    public InflationTermStructure(final Period lag,
			  final Frequency frequency,
			  final @Rate double baseRate,
			  final Handle<YieldTermStructure> yTS,
			  final DayCounter dayCounter) {
    	
    	super(dayCounter);
    	this.nominalTermStructure = yTS;
    	this.lag = lag;
    	this.frequency = frequency;
    	this.baseRate = baseRate;
    	
    	this.nominalTermStructure.addObserver(this); 	
    }

    public InflationTermStructure(final Date referenceDate,
    		  final Period lag,
			  final Frequency frequency,
			  final @Rate double baseRate,
			  final Handle<YieldTermStructure> yTS) {

    	this(referenceDate, lag, frequency, baseRate, yTS, new NullCalendar() ,new Actual365Fixed());
    }

    public InflationTermStructure(final Date referenceDate,
  		  	  final Period lag,
			  final Frequency frequency,
			  final @Rate double baseRate,
			  final Handle<YieldTermStructure> yTS,
			  final Calendar calendar,
			  final DayCounter dayCounter) {

    	super(referenceDate, calendar, dayCounter);
    	this.nominalTermStructure = yTS;
    	this.lag = lag;
    	this.frequency = frequency;
    	this.baseRate = baseRate;
    	
    	this.nominalTermStructure.addObserver(this);
    }

    public InflationTermStructure( final @Natural int settlementDays,
			  final Calendar calendar,
			  final Period lag,
			  final Frequency frequency,
			  final @Rate double baseRate,
			  final Handle<YieldTermStructure> yTS) {

    	this(settlementDays, calendar, lag, frequency, baseRate, yTS, new Actual365Fixed());

    }

    
    public InflationTermStructure( final @Natural int settlementDays,
    							   final Calendar calendar,
    							   final Period lag,
    							   final Frequency frequency,
    							   final @Rate double baseRate,
    							   final Handle<YieldTermStructure> yTS,
    							   final DayCounter dayCounter) {

    	super(settlementDays, calendar, dayCounter);
    	this.nominalTermStructure = yTS;
    	this.lag = lag;
    	this.frequency = frequency;
    	this.baseRate = baseRate;
    	
    	this.nominalTermStructure.addObserver(this);
    }

    public Period lag() {
    	return lag;
    }

    public Frequency frequency() {
    	return frequency;
    }

    public Handle<YieldTermStructure> nominalTermStructure() {
    	return nominalTermStructure;
    }

    public /*@Rate*/ double baseRate() {
    	return baseRate;
    }
    
    //! minimum (base) date
    /*! Important in inflation since it starts before nominal
        reference date.
    */
    public Date baseDate() {
    	return new Date(0);
    }

    @Override
    public Date maxDate() {
    	return new Date(0);
    }
    
	// This next part is required for piecewise- constructors
	// because, for inflation, they need more than just the
	// instruments to build the term structure, since the rate at
	// time 0-lag is non-zero, since we deal (effectively) with
	// "forwards".
    protected void setBaseRate (final @Rate double r) {
    	baseRate = r;
    
    }
 
    //! utility function giving the inflation period for a given date   
    public static Pair<Date,Date> inflationPeriod(final Date date,
    									   final Frequency frequency) {
    	
        Month month = date.month();
        int year = date.year();

        Month startMonth, endMonth;
        switch (frequency) {
          case Annual:
            startMonth = Month.January;
            endMonth = Month.December;
            break;
          case Semiannual:       	  
            startMonth = Month.valueOf(6*(month.value()-1)/6 + 1);
            endMonth = Month.valueOf(startMonth.value() + 5);
            break;
          case Quarterly:
            startMonth = Month.valueOf(3*(month.value()-1)/3 + 1);
            endMonth = Month.valueOf(startMonth.value() + 2);
            break;
          case Monthly:
            startMonth = endMonth = month;
            break;
          default:
        	  throw new LibraryException("Frequency not handled: " + frequency);
          
        };

        Date startDate = new Date(1, startMonth, year);
        Date endDate = Date.endOfMonth(new Date(1, endMonth, year));   	
        
        return new Pair<Date,Date>(startDate, endDate);
    }
    

}
