/*
 Copyright (C) 2009 John Nichol

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
 Copyright (C) 2007 Ferdinando Ametrano
 Copyright (C) 2007 Chiara Fornarola
 Copyright (C) 2005, 2006, 2008 StatPro Italia srl

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
package org.jquantlib.indexes.ibor;

import org.jquantlib.QL;
import org.jquantlib.currencies.Currency;
import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.JointCalendar;
import org.jquantlib.time.calendars.JointCalendar.JointCalendarRule;
import org.jquantlib.time.calendars.UnitedKingdom;

/**
 * base class for all BBA LIBOR indexes but the EUR, O/N, and S/N ones
 *
 * LIBOR fixed by BBA.
 * 
 *
 * @see <a href="http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1414">http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1414</a>
 * @author John Nichol
 * @author Zahid Hussain
 */
public class Libor extends IborIndex {

    //
    // private final fields
    //

    private final Calendar financialCenterCalendar;
    private final Calendar jointCalendar;


    //
    // public constructors
    //

	public Libor(
	        final String familyName,
			final Period tenor,
			final int settlementDays,
			final Currency currency,
			final Calendar financialCenterCalendar,
			final DayCounter dayCounter,
			final Handle<YieldTermStructure> h) {
		// http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412 :
		// UnitedKingdom::Exchange is the fixing calendar for
		// a) all currencies but EUR
		// b) all indexes but o/n and s/n
		super(familyName, 
			  tenor, 
			  settlementDays, 
			  currency, 
			  new UnitedKingdom(UnitedKingdom.Market.Exchange),
			  liborConvention(tenor), 
			  liborEOM(tenor), 
			  dayCounter, 
			  h);
		this.financialCenterCalendar = financialCenterCalendar;
		this.jointCalendar = new JointCalendar(new UnitedKingdom(UnitedKingdom.Market.Exchange),
				financialCenterCalendar,
				JointCalendarRule.JoinHolidays);
		QL.require(this.tenor().units()!= TimeUnit.Days,
				"for daily tenors (" + this.tenor() + ") dedicated DailyTenor constructor must be used");

		QL.require(!currency.eq(new EURCurrency()), "for EUR Libor dedicated EurLibor constructor must be used");
	}

	public Libor(
	        final String familyName,
			final Period tenor,
			final int settlementDays,
			final Currency currency,
			final Calendar financialCenterCalendar,
			final DayCounter dayCounter) {
	        	this(familyName,
	        		tenor,
	        		settlementDays,
	        		currency,
	        		financialCenterCalendar,
	        		dayCounter,	
	        		new Handle<YieldTermStructure>());
	        }

	/**
     * Date calculations
     *
     * @see <a href="http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412">http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412</a>
	 */
	@Override
    public Date valueDate(final Date fixingDate) {

		QL.require(isValidFixingDate(fixingDate),
				"Fixing date " + fixingDate + " is not valid");

		// http://www.bba.org.uk/bba/jsp/polopoly.jsp?d=225&a=1412 :
		// For all currencies other than EUR and GBP the period between
		// Fixing Date and Value Date will be two London business days
		// after the Fixing Date, or if that day is not both a London
		// business day and a business day in the principal financial centre
		// of the currency concerned, the next following day which is a
		// business day in both centres shall be the Value Date.
		final Date d = fixingCalendar().advance(fixingDate, fixingDays(), TimeUnit.Days);
		return jointCalendar.adjust(d);
	}

	@Override
    public Date maturityDate(final Date valueDate) {
		// Where a deposit is made on the final business day of a
		// particular calendar month, the maturity of the deposit shall
		// be on the final business day of the month in which it matures
		// (not the corresponding date in the month of maturity). Or in
		// other words, in line with market convention, BBA LIBOR rates
		// are dealt on an end-end basis. For instance a one month
		// deposit for value 28th February would mature on 31st March,
		// not the 28th of March.
		return jointCalendar.advance(valueDate, tenor(), businessDayConvention(),
				endOfMonth());
	}

	@Override
    public Handle<IborIndex> clone(final Handle<YieldTermStructure> h) {
		return new Handle<IborIndex>(
		        new Libor(familyName(),
				tenor(),
				fixingDays(),
				currency(),
				financialCenterCalendar,
				dayCounter(),
				h));

	}
}
