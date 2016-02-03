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


import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;

/**
 * Base class for the one day deposit BBA EUR LIBOR indexes
 * <p>
 * Euro O/N LIBOR fixed by BBA. It can be also used for T/N and S/N
 * indexes, even if such indexes do not have BBA fixing.
 *
 * @note This is the London fixing by BBA . Use Euribor if you're interested in the rate fixed by the ECB.
 *
 * @author Tim Blackler
 **/
public class DailyTenorEURLibor extends IborIndex {
    
    
	/**
     * JoinBusinessDays is the fixing calendar for all indexes but o/n
     *
     * @see <a href="http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412">http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412</a>
     */
    public DailyTenorEURLibor(final int settlementDays) {
    	this(settlementDays, new Handle<YieldTermStructure>());
    }
	
    public DailyTenorEURLibor(final int settlementDays, final Handle<YieldTermStructure> h) {
        super(	"EURLibor",
                new Period (1,TimeUnit.Days),
                settlementDays, 
                new EURCurrency(),
                // http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412 :
                // no o/n or s/n fixings (as the case may be) will take place
                // when the principal centre of the currency concerned is
                // closed but London is open on the fixing day.
                new Target(),
                eurliborConvention(new Period (1,TimeUnit.Days)),
                eurliborEOM(new Period (1,TimeUnit.Days)),
                new Actual360(),
                h);
    }
}
