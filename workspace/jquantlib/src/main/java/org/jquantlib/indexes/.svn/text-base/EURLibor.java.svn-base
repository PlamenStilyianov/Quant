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
import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.JointCalendar;
import org.jquantlib.time.calendars.JointCalendar.JointCalendarRule;
import org.jquantlib.time.calendars.Target;
import org.jquantlib.time.calendars.UnitedKingdom;
import org.jquantlib.time.calendars.UnitedKingdom.Market;

/**
 * EurLibor index
 * <p>
 * EurLibor rate fixed in London fixing by BBA.
 *
 * @note This is the London fixing by BBA . Use Euribor if you're interested in the rate fixed by the ECB.
 *
 * @author Tim Blackler
 **/
public class EURLibor extends IborIndex {
    
	private Calendar target;
    
	/**
     * JoinBusinessDays is the fixing calendar for all indexes but o/n
     *
     * @see <a href="http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412">http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412</a>
     */
    public EURLibor(final Period tenor) {
    	this(tenor, new Handle<YieldTermStructure>());
    }
	
    public EURLibor(final Period tenor, final Handle<YieldTermStructure> h) {
        super("EURLibor",
                tenor,
                2, // settlement days
                new EURCurrency(),
                new JointCalendar(new UnitedKingdom(Market.Exchange), 
                				  new Target(),
                				  JointCalendarRule.JoinBusinessDays),
                eurliborConvention(tenor),
                eurliborEOM(tenor),
                new Actual360(),
                h);
        QL.require(tenor().units() != TimeUnit.Days , "for daily tenors dedicated DailyTenor constructor must be used");
        
        this.target = new Target();
    }
    
	/**
     * Date calculations
     *
     * @see <a href="http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412">http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412</a>
	 */
	@Override
    public Date valueDate (final Date fixingDate) {
        // http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412 :
        // In the case of EUR the Value Date shall be two TARGET
        // business days after the Fixing Date.
    	
    	QL.require(isValidFixingDate(fixingDate), "Fixing date " + fixingDate + " is not valid");
    	return this.target.advance(fixingDate, fixingDays,  TimeUnit.Days);
    }
    
	@Override
    public Date maturityDate (final Date valueDate) {
        // http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412 :
        // In the case of EUR only, maturity dates will be based on days in
        // which the Target system is open.
    	
     	return this.target.advance(valueDate, tenor(), businessDayConvention(),  endOfMonth());
    }
     
}
