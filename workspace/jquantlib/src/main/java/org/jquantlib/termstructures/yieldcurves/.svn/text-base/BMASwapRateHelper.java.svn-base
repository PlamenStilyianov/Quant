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

package org.jquantlib.termstructures.yieldcurves;



import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.BMAIndex;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.instruments.BMASwap;
import org.jquantlib.lang.annotation.Natural;
import org.jquantlib.pricingengines.swap.DiscountingSwapEngine;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.MakeSchedule;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.Weekday;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Rate helper for bootstrapping over BMA swap rates
 *
 * @author Tim Blackler
 */
//
public class BMASwapRateHelper extends RelativeDateRateHelper {

    protected final Period tenor;
    protected /* @Natural */ int settlementDays;
    protected final Calendar calendar;
    protected final Period bmaPeriod;
    protected final BusinessDayConvention bmaConvention;
    protected final DayCounter bmaDayCount;
    protected final BMAIndex bmaIndex;
    protected final IborIndex iborIndex;
    protected BMASwap swap;
    protected RelinkableHandle<YieldTermStructure> termStructureHandle = new RelinkableHandle <YieldTermStructure> (null);
 
    //
    // public constructors
    //

    public BMASwapRateHelper(
            final Handle<Quote> liborFraction,
            final Period tenor,
            final @Natural int settlementDays,
            final Calendar calendar,
            // bma leg
            final Period bmaPeriod,
            final BusinessDayConvention bmaConvention,
            final DayCounter bmaDayCount,
            final BMAIndex bmaIndex,
            // libor leg
            final IborIndex iborIndex) {
        super(liborFraction);

        QL.validateExperimentalMode();

        this.tenor = tenor;
        this.settlementDays = settlementDays;
        this.calendar = calendar;

        this.bmaPeriod = bmaPeriod;
        this.bmaConvention = bmaConvention;
        this.bmaDayCount = bmaDayCount;
        this.bmaIndex = bmaIndex;
        this.iborIndex = iborIndex;

        this.iborIndex.addObserver(this);
        this.bmaIndex.addObserver(this);

        initializeDates();
    }



    //
    // protected methods
    //

	/** 
     * 
     * @see org.jquantlib.termstructures.yield.RelativeDateRateHelper#initializeDates()
     */
    @Override
    protected void initializeDates() {

    	earliestDate = calendar.advance(evaluationDate, 
    			                        new Period(settlementDays, TimeUnit.Days), 
    			                        BusinessDayConvention.Following);
    	

    	Date maturity = earliestDate.add(tenor);
    	BMAIndex clonedIndex = bmaIndex.clone(termStructureHandle);
    	Schedule bmaSchedule = new MakeSchedule(earliestDate,
    			                                maturity,
    			                                bmaPeriod,
    			                                bmaIndex.fixingCalendar(),
    			                                bmaConvention)
    								.backwards()
    								.schedule();
    	
    	Schedule liborSchedule = new MakeSchedule(earliestDate,
                								  maturity,
                								  iborIndex.tenor(),
                								  iborIndex.fixingCalendar(),
                								  iborIndex.businessDayConvention())
    								.endOfMonth(iborIndex.endOfMonth())
    								.schedule();

    	this.swap = new BMASwap(BMASwap.Type.Payer,
    							100.0,
    							liborSchedule,
    							0.75, //arbitary
    							0.0,
    							iborIndex,
    							iborIndex.dayCounter(),
    							bmaSchedule,
    							clonedIndex,
    							bmaDayCount);
    	
    	this.swap.setPricingEngine(new DiscountingSwapEngine(iborIndex.termStructure()));
    	
    	Date d = calendar.adjust(swap.maturityDate(), BusinessDayConvention.Following);
    	Weekday w = d.weekday();
    	Date nextWednesday = w.value() >=4 ? d.add(11 - w.value()) :  d.add(4 - w.value());
    	
    	latestDate = clonedIndex.valueDate(clonedIndex.fixingCalendar().adjust(nextWednesday));
    }
    
    /**
     * Do not set the relinkable handle as an observer.
     * Force recalculation when needed
     * 
     * @see org.jquantlib.termstructures.BootstrapHelper#setTermStructure
     *
     * @param t
     */
    @Override
    public void setTermStructure(final YieldTermStructure t) {
        this.termStructureHandle.linkTo(t, false);
        super.setTermStructure(t);
    }

    /** 
     * @see org.jquantlib.termstructures.BootstrapHelper#getImpliedQuote()
     */
    @Override
    public /*@Real*/ double impliedQuote() /* @ReadOnly */ {
        QL.require(termStructure != null , "term structure not set");

        // we didn't register as observers - force calculation
        swap.recalculate();
        
        return swap.fairLiborFraction();
    }

    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<BMASwapRateHelper> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
