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

/*
 Copyright (C) 2006, 2008 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006, 2007 StatPro Italia srl

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
package org.jquantlib.instruments;

import org.jquantlib.QL;
import org.jquantlib.cashflow.AverageBMALeg;
import org.jquantlib.cashflow.CashFlow;
import org.jquantlib.cashflow.IborLeg;
import org.jquantlib.cashflow.Leg;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.BMAIndex;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.lang.annotation.Real;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Schedule;

/**
 * BMA Swap
 *
 * swap paying Libor against BMA coupons
 *
 * @category instruments
 *
 * @author Tim Blackler
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class BMASwap extends Swap {

    static final /*@Spread*/ double  basisPoint = 1.0e-4;

    private final Type type;
    private final /*@Real*/ double nominal;
    private final /*@Rate*/ double liborFraction;
    private final /*@Rate*/ double liborSpread;

    public BMASwap(
            final Type type,
            final /*@Real*/ double nominal,
            final Schedule liborSchedule,
            final /*@Rate*/ double liborFraction,
            final /*@Rate*/ double liborSpread, 
            final IborIndex liborIndex,
            final DayCounter liborDayCount,
            final Schedule bmaSchedule,
            final BMAIndex bmaIndex,
            final DayCounter bmaDayCount) {
        super(2);
        this.type = type;
        this.nominal = nominal;
        this.liborFraction = liborFraction;
        this.liborSpread = liborSpread;

        final BusinessDayConvention convention = liborSchedule.businessDayConvention();
        
        final Leg iborLeg = new IborLeg(liborSchedule, liborIndex)
							        .withNotionals(nominal)
							        .withPaymentDayCounter(liborDayCount)
							        .withPaymentAdjustment(convention)
							        .withFixingDays(liborIndex.fixingDays())
							        .withGearings(liborFraction)
							        .withSpreads(liborSpread)
							        .Leg();
        this.legs.add(iborLeg);

        final Leg bmaLeg = new AverageBMALeg(bmaSchedule, bmaIndex)
							        .withNotionals(nominal)
							        .withPaymentDayCounter(bmaDayCount)
							        .withPaymentAdjustment(bmaSchedule.businessDayConvention())
							        .Leg();
        
        this.legs.add(bmaLeg);        
        
        for (final Leg leg : this.legs) {
        	for (final CashFlow item : leg) {
        		item.addObserver(this);
        	}
        }
        
        switch (type) {
        case Payer:
        	payer[0] = +1.0;
        	payer[1] = -1.0;
        	break;
        case Receiver:
        	payer[0] = -1.0;
        	payer[1] = +1.0;
        	break;
        default:
        	throw new LibraryException("Unknown BMA-swap type");
        }
        							
    }

    public /*@Rate*/ double  liborFraction() /* @ReadOnly */ {
    	 return liborFraction;
    }

    public /*@Spread*/ double  liborSpread() /* @ReadOnly */ {
   	 return liborSpread;
    }
    
    public /*@Real*/ double  nominal() /* @ReadOnly */ {
      	 return nominal;
      }
    
    public Type type() /* @ReadOnly */ {
     	 return type;
     }

    public Leg liborLeg() /* @ReadOnly */ {
    	 return legs.get(0);
    }

    public Leg bmaLeg() /* @ReadOnly */ {
   	 return legs.get(1);
   }

    public /*@Real*/ double liborLegBPS() /* @ReadOnly */ {
    	calculate();
    	QL.require(!Double.isNaN(legBPS[0]) , "result not available");
    	return legBPS[0];
    }
    
    
	public /*@Real*/ double liborLegNPV() /* @ReadOnly */ {
		calculate();
		QL.require(!Double.isNaN(legNPV[0]) , "result not available");
		return legNPV[0];
	}
    
      
	public /*@Real*/ double  fairLiborFraction() /* @ReadOnly */ {
	
		@Real
		final double spreadNPV = (liborSpread/basisPoint)*liborLegBPS();
		@Real
		final double pureLiborNPV = liborLegNPV() - spreadNPV;
		
		return -liborFraction * (bmaLegNPV() + spreadNPV) / pureLiborNPV;
	}   
    
    public /*@Spread*/ double  fairLiborSpread() /* @ReadOnly */ {
      	 return liborSpread - NPV()/(liborLegBPS()/basisPoint);
    }   

    public /*@Real*/ double bmaLegBPS() /* @ReadOnly */ {
    	calculate();
    	QL.require(!Double.isNaN(legBPS[1]) , "result not available");
    	return legBPS[1];
    }
    
    public /*@Real*/ double bmaLegNPV() /* @ReadOnly */ {
    	calculate();
    	QL.require(!Double.isNaN(legNPV[1]) , "result not available");
    	return legNPV[1];
    }

    //
    // inner public enums
    //

    public static enum Type {
        Receiver (-1),
        Payer (1);

        private final int enumValue;

        private Type(final int frequency) {
            this.enumValue = frequency;
        }

        static public Type valueOf(final int value) {
            switch (value) {
            case -1:
                return Type.Receiver;
            case 1:
                return Type.Payer;
            default:
                throw new LibraryException("value must be one of -1, 1"); // TODO: message
            }
        }

        public int toInteger() {
            return this.enumValue;
        }
    }
    
}
