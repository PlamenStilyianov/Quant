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
import org.jquantlib.Settings;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.bonds.FixedRateBond;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.bond.DiscountingBondEngine;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.termstructures.RateHelper;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Date;
import org.jquantlib.time.Schedule;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Fixed-coupon bond helper
 * <p>
 * WARNING: This class assumes that the reference date does not change
 * between calls of setTermStructure()
 * 
 * @category instruments
 * 
 * @author Srinivas Hasti
 * @author Neel Sheyal
 */
public class FixedRateBondHelper extends RateHelper {

	//
	// private fields
	//
     private FixedRateBond bond;
     private final RelinkableHandle<YieldTermStructure> termStructureHandle = new RelinkableHandle<YieldTermStructure>(null);
    
	/**
	 * 
	 * @param cleanPrice
	 * @param settlementDays
	 * @param faceAmount
	 * @param schedule
	 * @param coupons
	 * @param dayCounter
	 * @param paymentConvention: default value = BusinessDayConvention.Following
	 * @param redemption: default value = 100 
	 * @param issueDate: default: new Date()
	 */
	public FixedRateBondHelper(final Handle<Quote> cleanPrice,
                                final /* Natural */ int settlementDays,
                                final /* Real */ double faceAmount,
                                final Schedule schedule,
                                final /* Rate */ double[] coupons,
                                final DayCounter dayCounter) {
		this(cleanPrice, settlementDays, faceAmount, schedule, coupons,
				dayCounter, BusinessDayConvention.Following, 100.0, new Date());
	}
	
	/**
	 * 
	 * @param cleanPrice
	 * @param settlementDays
	 * @param faceAmount
	 * @param schedule
	 * @param coupons
	 * @param dayCounter
	 * @param paymentConvention
	 * @param redemption
	 * @param issueDate
	 */
	public FixedRateBondHelper(final Handle<Quote> cleanPrice,
                                final /* Natural */ int settlementDays,
                                final /* Real */ double faceAmount,
                                final Schedule schedule,
                                final /* Rate */ double[] coupons, 
                                final DayCounter dayCounter,
                                final BusinessDayConvention paymentConvention,
                                final /* Real */ double redemption,
                                final Date issueDate) {
		super(cleanPrice);
		QL.validateExperimentalMode();  
		
		this.bond = new FixedRateBond(settlementDays, faceAmount, schedule,
				coupons, dayCounter, paymentConvention, redemption, issueDate);
		
		this.latestDate = this.bond.maturityDate();
		new Settings().evaluationDate().addObserver(this);
		
		final PricingEngine bondEngine = new DiscountingBondEngine(this.termStructureHandle);
		this.bond.setPricingEngine(bondEngine);
			 		
}
	
	/**
	 *  {@literal WARNING: Setting a pricing engine to the passed bond from
                     external code will cause the bootstrap to fail or
                     to give wrong results. It is advised to discard
                     the bond after creating the helper, so that the
                     helper has sole ownership of it.}
     *                
	 * @param cleanPrice
	 * @param bond
	 */
	public FixedRateBondHelper(final Handle<Quote> cleanPrice,
			                    final FixedRateBond bond) {
		super(cleanPrice);
		QL.validateExperimentalMode();  
		
		this.latestDate = bond.maturityDate();
		new Settings().evaluationDate().addObserver(this);
		
		final PricingEngine bondEngine = new DiscountingBondEngine(this.termStructureHandle);
		this.bond.setPricingEngine(bondEngine);
			
	}
	
	
	//
	// public methods
	//
	
	public FixedRateBond  bond() {
    	return this.bond;
    }

	
	//
	// overrides BootstrapHelper
	//
	
	@Override
	public void setTermStructure(final YieldTermStructure t) {
		// do not set the relinkable handle as an observer -
        // force recalculation when needed
		this.termStructureHandle.linkTo(t,false);
		super.setTermStructure(t);
    }
    
	@Override
	public/* Real */double impliedQuote() {
		QL.require(this.termStructure != null, "term structure not set");
		this.bond.recalculate();
		return this.bond.cleanPrice();
	}

	
	//
	// implements PolymorphicVisitable
	//
	
	@Override
	public void accept(final PolymorphicVisitor pv) {
		final Visitor<FixedRateBondHelper> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
	}

}
