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
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;

/**
 * Actual/365 Euribor index
 * <p>
 * Euribor rate adjusted for the mismatch between the actual/360
 * convention used for Euribor and the actual/365 convention
 * previously used by a few pre-EUR currencies.
 *
 * @author Srinivas Hasti
 */
public class Euribor365 extends IborIndex {

    public Euribor365(final Period tenor) {
    	this(tenor, new Handle<YieldTermStructure>());
    }
    
    public Euribor365(final Period tenor, final Handle<YieldTermStructure> h) {
        super("Euribor365", tenor,
                2, // settlement days
                new EURCurrency(),
                new Target(),
                euriborConvention(tenor),
                euriborEOM(tenor),
                new Actual365Fixed(),
                h);
        QL.require(this.tenor().units() != TimeUnit.Days , "for daily tenors dedicated DailyTenor constructor must be used"); // TODO: message
    }

}