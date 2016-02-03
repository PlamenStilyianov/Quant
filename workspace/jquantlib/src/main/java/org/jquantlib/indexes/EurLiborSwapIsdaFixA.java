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
import org.jquantlib.daycounters.Thirty360;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;

/**
 * 
 * EurLiborSwapIsdaFixA index base class
 * EUR Libor Swap indexes fixed by ISDA in cooperation with
 * Reuters and Intercapital Brokers at 10am London.
 * Annual 30/360 vs 6M Libor, 1Y vs 3M Libor.
 * Reuters page ISDAFIX2 or EURSFIXLA=.
 * 
 * Further info can be found at <http://www.isda.org/fix/isdafix.html> or
 * Reuters page ISDAFIX.
 * 
 * @author Tim Blackler
 */
public class EurLiborSwapIsdaFixA extends SwapIndex {

    public EurLiborSwapIsdaFixA(final Period tenor) {
    	this(tenor, new Handle<YieldTermStructure>());
    }
	
    public EurLiborSwapIsdaFixA(final Period tenor, final Handle<YieldTermStructure> h) {
        super( "EurLiborSwapIsdaFixA",
                tenor,
                2, // settlement days
                new EURCurrency(),
                new Target(),
                new Period(1,TimeUnit.Years),
                BusinessDayConvention.ModifiedFollowing,
                new Thirty360(Thirty360.Convention.BondBasis),
                tenor.gt(new Period(1,TimeUnit.Years)) ? new EURLibor(new Period(6,TimeUnit.Months), h):
                	 									 new EURLibor(new Period(3,TimeUnit.Months), h)
                		
                );
        }
}
