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
 * Rate helper for bootstrapping over deposit rates
 * 
 * @author Srinivas Hasti
 * @author Neel Sheyal 
 */

public class DepositRateHelper extends RelativeDateRateHelper {

	//
	// private fields 
	//
	private final static String TS_NOT_SET = "term structure not set";
    private Date fixingDate;
    private final IborIndex iborIndex;
    private final RelinkableHandle<YieldTermStructure> termStructureHandle = new RelinkableHandle <YieldTermStructure> (null);

    //
	// public constructors
	//
    
    /**
     * 
     * @param rate
     * @param tenor
     * @param fixingDays
     * @param calendar
     * @param convention
     * @param endOfMonth
     * @param dayCounter
     */
    public DepositRateHelper(
                final Handle<Quote> rate,
                final Period tenor,
                final /*@Natural*/ int fixingDays,
                final Calendar calendar,
                final BusinessDayConvention convention,
                final boolean endOfMonth,
                final DayCounter dayCounter) {
        super(rate);
        this.iborIndex = new IborIndex(
                      "no-fix", // never take fixing into account
                      tenor, fixingDays,
                      new Currency(), calendar, convention,
                      endOfMonth, dayCounter, termStructureHandle);
        initializeDates();
    }

	/**
	 * 
	 * @param rate
	 * @param tenor
	 * @param fixingDays
	 * @param calendar
	 * @param convention
	 * @param endOfMonth
	 * @param dayCounter
	 */
    public DepositRateHelper(
                final /*@Rate*/ double  rate,
                final Period tenor,
                final  /*@Natural*/ int fixingDays,
                final Calendar calendar,
                final BusinessDayConvention convention,
                final boolean endOfMonth,
                final DayCounter dayCounter) {
        super(rate);
        QL.validateExperimentalMode();

        this.iborIndex = new IborIndex(
                      "no-fix", // never take fixing into account
                      tenor, fixingDays,
                      new Currency(), calendar, convention,
                      endOfMonth, dayCounter, this.termStructureHandle);
        initializeDates();
    }

	/**
	 * 
	 * @param rate
	 * @param iborIndex
	 */
	public DepositRateHelper(final Handle<Quote> rate,
                final IborIndex iborIndex) {
        super(rate);
        QL.validateExperimentalMode();

        this.iborIndex = new IborIndex(
                      "no-fix", // never take fixing into account
                      iborIndex.tenor(), iborIndex.fixingDays(), new Currency(),
                      iborIndex.fixingCalendar(), iborIndex.businessDayConvention(),
                      iborIndex.endOfMonth(), iborIndex.dayCounter(), this.termStructureHandle);
        initializeDates();
    }

    /**
     * 
     * @param rate
     * @param IborIndex
     */
    public DepositRateHelper(
                final  /*@Rate*/ double  rate,
                final IborIndex i) {
        super(rate);
        QL.validateExperimentalMode();

        this.iborIndex = new IborIndex(
                      "no-fix", // never take fixing into account
                      i.tenor(), i.fixingDays(), new Currency(),
                      i.fixingCalendar(), i.businessDayConvention(),
                      i.endOfMonth(), i.dayCounter(), this.termStructureHandle);
        initializeDates();
    }


    //
    // public methods 
    //

    /**
     * {@link RelativeDateRateHelper#impliedQuote()}
     */
	@Override
	public double impliedQuote() {
		QL.require(termStructure != null, DepositRateHelper.TS_NOT_SET);
		return this.iborIndex.fixing(fixingDate, true);
	}

	/**
	 * 
	 * @param YieldTermStructure
	 * {@link BootstrapHelper#setTermStructure(org.jquantlib.termstructures.TermStructure)}
	 */
	@Override
	public void setTermStructure(final YieldTermStructure term) {
		// no need to register---the index is not lazy
		this.termStructureHandle.linkTo(term, false);
		super.setTermStructure(term);
	}

    /**
     * Overrides the abstract method defined in the super class: RelativeDateRateHelper
     * {@link RelativeDateRateHelper#initializeDates()}
     */
	@Override
	protected void initializeDates() {
		earliestDate = this.iborIndex.fixingCalendar().advance(evaluationDate,
				this.iborIndex.fixingDays(), TimeUnit.Days);

		this.latestDate = this.iborIndex.maturityDate(earliestDate);
		this.fixingDate = this.iborIndex.fixingDate(earliestDate);

	}

	
	//
	// implements PolymorphicVisitable
	//
	
	@Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<DepositRateHelper> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }


}
