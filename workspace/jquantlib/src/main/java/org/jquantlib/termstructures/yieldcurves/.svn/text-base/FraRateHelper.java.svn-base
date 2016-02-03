/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2010 Neel Sheyal  

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

package org.jquantlib.termstructures.yieldcurves;

import org.jquantlib.QL;
import org.jquantlib.currencies.Currency;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.termstructures.BootstrapHelper;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 *  Rate helper for bootstrapping over IborIndex futures prices
 *
 * @author Srinivas Hasti
 * @author Neel Sheyal
 */

public class FraRateHelper extends RelativeDateRateHelper {

	
	//
	// private fields
	//
	private Date fixingDate;
	private final Period periodToStart;
	private final IborIndex iborIndex;
	private final RelinkableHandle<YieldTermStructure> termStructureHandle = new RelinkableHandle<YieldTermStructure>(null);

	
	//
	// public constructors
	//

	public FraRateHelper(
			final Handle<Quote> rate,
			final/* @Natural */int monthsToStart,
			final/* @Natural */int monthsToEnd,
			final/* @Natural */int fixingDays, 
			final Calendar calendar,
			final BusinessDayConvention convention, 
			final boolean endOfMonth,
			final DayCounter dayCounter) {

		super(rate);
		this.periodToStart = new Period(monthsToStart, TimeUnit.Months);
		
		QL.require(monthsToEnd > monthsToStart,
				"monthsToEnd must be greater than monthsToStart");
		QL.validateExperimentalMode();
	
		this.iborIndex = new IborIndex(
				"no-fix", // never take fixing into account
				new Period(monthsToEnd - monthsToStart, TimeUnit.Months),
				fixingDays, new Currency(), calendar, convention, endOfMonth,
				dayCounter, this.termStructureHandle);

		initializeDates();
	}

	public FraRateHelper(
			final/* @Rate */double rate,
			final/* @Natural */int monthsToStart,
			final/* @Natural */int monthsToEnd,
			final/* @Natural */int fixingDays, 
			final Calendar calendar,
			final BusinessDayConvention convention, 
			final boolean endOfMonth,
			final DayCounter dayCounter) {
		
		super(rate);
		this.periodToStart = new Period(monthsToStart, TimeUnit.Months);
		
		QL.require(monthsToEnd > monthsToStart,
				"monthsToEnd must be greater than monthsToStart");
		QL.validateExperimentalMode();

		iborIndex = new IborIndex(
				"no-fix", // never take fixing into account
				new Period(monthsToEnd - monthsToStart, TimeUnit.Months),
				fixingDays, new Currency(), calendar, convention, endOfMonth,
				dayCounter, this.termStructureHandle);

		initializeDates();
	}

	public FraRateHelper(
			final Handle<Quote> rate,
			final/* @Natural */int monthsToStart, 
			final IborIndex i) {
		
		super(rate);
		this.periodToStart = new Period(monthsToStart, TimeUnit.Months);

		QL.validateExperimentalMode();
		
		iborIndex = new IborIndex(
				"no-fix", // never take fixing into account
				i.tenor(), i.fixingDays(), new Currency(), i.fixingCalendar(),
				i.businessDayConvention(), i.endOfMonth(), i.dayCounter(),
				this.termStructureHandle);

		initializeDates();
	}

	public FraRateHelper(
			final/* @Rate */double rate,
			final/* @Natural */int monthsToStart, 
			final IborIndex i) {

		super(rate);
		this.periodToStart = new Period(monthsToStart, TimeUnit.Months);

		QL.validateExperimentalMode();
		
		iborIndex = new IborIndex(
				"no-fix", // never take fixing into account
				i.tenor(), i.fixingDays(), new Currency(), i.fixingCalendar(),
				i.businessDayConvention(), i.endOfMonth(), i.dayCounter(),
				this.termStructureHandle);

		initializeDates();
	}

	public FraRateHelper(
			final Handle<Quote> rate, 
			final Period periodToStart,
			final/* @Natural */int lengthInMonths,
			final/* @Natural */int fixingDays, 
			final Calendar calendar,
			final BusinessDayConvention convention, 
			final boolean endOfMonth,
			final DayCounter dayCounter) {

		super(rate);
		this.periodToStart = periodToStart;
	
		QL.validateExperimentalMode();

		iborIndex = new IborIndex(
				"no-fix", // never take fixing into account
				new Period(lengthInMonths, TimeUnit.Months), fixingDays,
				new Currency(), calendar, convention, endOfMonth, dayCounter,
				this.termStructureHandle);

		initializeDates();

	}

	public FraRateHelper(
			final/* @Rate */double rate, 
			final Period periodToStart,
			final/* @Natural */int lengthInMonths,
			final/* @Natural */int fixingDays, 
			final Calendar calendar,
			final BusinessDayConvention convention, 
			final boolean endOfMonth,
			final DayCounter dayCounter) {

		super(rate);
		this.periodToStart = periodToStart;
		
		QL.validateExperimentalMode();

		iborIndex = new IborIndex(
				"no-fix", // never take fixing into account
				new Period(lengthInMonths, TimeUnit.Months), fixingDays,
				new Currency(), calendar, convention, endOfMonth, dayCounter,
				this.termStructureHandle);
		initializeDates();

	}

	public FraRateHelper(
			final Handle<Quote> rate, 
			final Period periodToStart,
			final IborIndex i) {
		
		super(rate);
		this.periodToStart = periodToStart;
		
		QL.validateExperimentalMode();

		iborIndex = new IborIndex(
				"no-fix",// never take fixing into account
				i.tenor(), i.fixingDays(), new Currency(), i.fixingCalendar(),
				i.businessDayConvention(), i.endOfMonth(), i.dayCounter(),
				this.termStructureHandle);

		initializeDates();

	}

	public FraRateHelper(
			final/* @Rate */double rate, 
			final Period periodToStart,
			final IborIndex i) {

		super(rate);
		this.periodToStart = periodToStart;
		
		QL.validateExperimentalMode();

		iborIndex = new IborIndex(
				"no-fix",// never take fixing into account
				i.tenor(), i.fixingDays(), new Currency(), i.fixingCalendar(),
				i.businessDayConvention(), i.endOfMonth(), i.dayCounter(),
				this.termStructureHandle);

		initializeDates();

	}

    
    //
    // implements BootstrapHelper
    //

    @Override
    public double impliedQuote()  {
        QL.require(termStructure != null , "term structure not set");
        return iborIndex.fixing(this.fixingDate, true);
    }

    @Override
    public void setTermStructure(final YieldTermStructure t) {
        // no need to register---the index is not lazy
        termStructureHandle.linkTo(t, false);
        super.setTermStructure(t);
    }

    //
    // implements RelativeDateRateHelper
    //

    @Override
    protected void initializeDates() {

		final Date settlement = iborIndex.fixingCalendar().advance(this.evaluationDate,
				                                                  new Period(iborIndex.fixingDays(), TimeUnit.Days));
			                                                  

		this.earliestDate = iborIndex.fixingCalendar().advance( settlement,
				                                                this.periodToStart,
				                                                iborIndex.businessDayConvention(), 
				                                                iborIndex.endOfMonth());
		
		this.latestDate = iborIndex.maturityDate(this.earliestDate);
		this.fixingDate = iborIndex.fixingDate(this.earliestDate);
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<FraRateHelper> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
