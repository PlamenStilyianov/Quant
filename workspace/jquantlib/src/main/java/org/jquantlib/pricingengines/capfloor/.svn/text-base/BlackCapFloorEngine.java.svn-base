
/*
Copyright (C) 2009 John Martin

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


package org.jquantlib.pricingengines.capfloor;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.annotation.Volatility;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;

// TODO implementation
//FIXME - JM

public class BlackCapFloorEngine //TODO:: extends CapFloor.Engine
{
//TODO:
//    private final Handle <YieldTermStructure> termStructure;
//
//    private Handle <OptionletVolatilityStructure> volatility;


    public BlackCapFloorEngine (final Handle <YieldTermStructure> termStructureHandle,
                                final Volatility v,
                                final DayCounter dc)

    {
        QL.validateExperimentalMode();
//
//        this.termStructure = termStructureHandle;
//
//        //this.volatility = new OptionletVolatilityStructure (new ConstantOptionletVolatility
//        //                      (0, new NullCalendar(), BusinessDayConvention.Following, v, dc));
//        this.termStructure.addObserver (this);
    }


    public BlackCapFloorEngine (final Handle <YieldTermStructure> termStructureHandle,
                                final Handle <Quote> v,
                                final DayCounter dc)
    {
        QL.validateExperimentalMode();
//        this.termStructure = termStructureHandle;
//        this.volatility = new OptionletVolatilityStructure
//            (new ConstantOptionVolatility (0, new NullCalendar(),
//                                           BusinessDayConvention.Following, v, dc));
//
//        this.termStructure.addObserver (this);
//        this.volatility.addObserver (this);
    }


    public BlackCapFloorEngine (final Handle <YieldTermStructure> termStructureHandle,
                                final Handle <OptionletVolatilityStructure> vol)

    {
        QL.validateExperimentalMode();
//        this.termStructure = termStructureHandle;
//        this.volatility = vol;
//
//        this.termStructure.addObserver (this);
//        this.volatility.addObserver (this);
    }

    public void calculate()
    {
        QL.validateExperimentalMode();
//        double value = 0.0;
//        double vega = 0.0;
//        int optionlets = arguments_.startDates.size();
//        Array values = new Array (optionlets, 0.0);
//        Array vegas = new Array (optionlets, 0.0);
//        Array stdDevs = new Array (optionletgs, 0.0);
//        CapFloor.Type type = arguments_.type;
//        Date today = volatility.referenceDate();
//        Date settlement = termStructure.referenceDate();
//
//        for (int i = 0; i < optionlets; ++ i)
//        {
//            Date paymentDate = arguments_.endDates
//        }
    }
}
