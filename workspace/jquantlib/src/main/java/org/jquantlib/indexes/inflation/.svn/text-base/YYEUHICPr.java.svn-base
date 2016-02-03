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
package org.jquantlib.indexes.inflation;

import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.indexes.EURegion;
import org.jquantlib.indexes.YoYInflationIndex;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YoYInflationTermStructure;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;

/**
 * 
 * Fake year-on-year EU HICP (i.e. a ratio of EU HICP)
 * 
 * @author Tim Blackler
 *
 */

public class YYEUHICPr extends YoYInflationIndex {

	public YYEUHICPr(final Frequency frequency,
   	      final boolean revised,
   	      final boolean interpolated) {
			
	this(frequency, revised, interpolated, new Handle<YoYInflationTermStructure>());
	   
 }
	
    public YYEUHICPr(final Frequency frequency,
            	  final boolean revised,
            	  final boolean interpolated,
            	  final Handle<YoYInflationTermStructure> termStructure) {
    	
    	super("YYR_HICP",
              new EURegion(),
              revised,
              interpolated,
              true,
              frequency,
              new Period(3, TimeUnit.Months),
              new EURCurrency(),
              termStructure);
    	
    }    
}
