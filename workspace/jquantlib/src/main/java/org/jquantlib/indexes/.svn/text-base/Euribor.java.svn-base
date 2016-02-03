/*
 Copyright (C) 2008 Srinivas Hasti

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
import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;

/**
 * Euribor index
 * <p>
 * Euribor rate fixed by the ECB.
 *
 * @note This is the rate fixed by the ECB. Use EurLibor if you're interested in the London fixing by BBA.
 *
 * @author Srinivas Hasti
 */
public class Euribor extends IborIndex {

    public Euribor(final Period tenor) {
    	this(tenor, new Handle<YieldTermStructure>());
    }
	
    public Euribor(final Period tenor, final Handle<YieldTermStructure> h) {
        super("Euribor",
                tenor,
                2, // settlement days
                new EURCurrency(),
                new Target(),
                euriborConvention(tenor),
                euriborEOM(tenor),
                new Actual360(),
                h);
        QL.require(tenor().units() != TimeUnit.Days , "for daily tenors dedicated DailyTenor constructor must be used");
    }

}
