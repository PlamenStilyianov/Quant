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

package org.jquantlib.indexes;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.currencies.Currency;
import org.jquantlib.lang.annotation.Rate;
import org.jquantlib.lang.annotation.Real;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.InflationTermStructure;
import org.jquantlib.termstructures.ZeroInflationTermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.util.Pair;

/**
 * Base class for zero inflation indices.
 *
 * @author Tim Blackler
 *
 */
// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class ZeroInflationIndex extends InflationIndex {
    
    private Handle<ZeroInflationTermStructure> zeroInflation;
    
    public ZeroInflationIndex(final String familyName,
			 final Region region,
			 final boolean revised,
			 final boolean interpolated,
			 final Frequency frequency,
			 final Period availabilityLag,
			 final Currency currency) {
    	this(familyName, region, revised, interpolated, frequency, availabilityLag, currency, new Handle<ZeroInflationTermStructure>());
    }
    
    public ZeroInflationIndex(final String familyName,
            				 final Region region,
            				 final boolean revised,
            				 final boolean interpolated,
            				 final Frequency frequency,
            				 final Period availabilityLag,
            				 final Currency currency,
            				 final Handle<ZeroInflationTermStructure> zeroInflation) {
    	super(familyName, region, revised, interpolated, frequency, availabilityLag, currency);
    	this.zeroInflation = zeroInflation;
    	this.zeroInflation.addObserver(this);	
    }
 
    @Override
    public double fixing(Date fixingDate) {
    	return this.fixing(fixingDate, false);
    }	
    
    @Override
    public double fixing(Date fixingDate, boolean forecastTodaysFixing) {
    	Date today = new Settings().evaluationDate();
    	Date todayMinusLag = today.sub(availabilityLag);
    	
    	Pair<Date,Date> lim = InflationTermStructure.inflationPeriod(todayMinusLag, frequency);
    	todayMinusLag = lim.second().inc();
    	
    	if ((fixingDate.lt(todayMinusLag)) ||
    		(fixingDate.eq(todayMinusLag) && !forecastTodaysFixing)) {
    		
    		@Real double pastFixing = IndexManager.getInstance().getHistory(name()).get(fixingDate);
    		QL.require(!(Double.isNaN(pastFixing)) , "Missing " + name() + " fixing for " + fixingDate);
    		return pastFixing;
    	} else {
    		return forecastFixing(fixingDate);
    	}
    }
    
    public Handle<ZeroInflationTermStructure> zeroInflationTermStructure() {
    	return zeroInflation;
    }
    
    private /* @Rate */ double forecastFixing(final Date fixingDate) {
    	// the term structure is relative to the fixing value at the base date.
    	Date baseDate = zeroInflation.currentLink().baseDate();
    	@Real double baseFixing = fixing(baseDate);
    	
    	// get the relevant period end
    	Pair<Date, Date> limBase = InflationTermStructure.inflationPeriod(baseDate, frequency);
    	Date trueBaseDate = limBase.second();
    	
        // if the value is not interpolated, get the value for half
        // way along the period.
    	Date d = fixingDate;
    	if (!interpolated()) {
    		Pair<Date, Date> lim = InflationTermStructure.inflationPeriod(fixingDate, frequency);
    		int n = (int)(lim.second().sub(lim.first())) / 2;
    		
    		d = lim.first().add(n);
    	}
    	
        // Assume annual compounding (we're using a zero inflation
        // term structure)
    	@Rate double zero = zeroInflation.currentLink().zeroRate(d);
    	@Time double t = zeroInflation.currentLink().dayCounter().yearFraction(trueBaseDate, d);
    	
    	return baseFixing * Math.pow((1.0 + zero), t);
    }
       
}
