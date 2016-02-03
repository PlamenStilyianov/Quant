/*
 Copyright (C) 2009 Daniel Kong

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
package org.jquantlib.instruments.bonds;

import java.util.List;

import org.jquantlib.cashflow.Callability;
import org.jquantlib.cashflow.Dividend;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.Bond;
import org.jquantlib.instruments.Option;
import org.jquantlib.math.Constants;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.time.Date;
import org.jquantlib.time.Schedule;

/**
 * Base class for convertible bonds
 *
 * @author Daniel Kong
 */
public class ConvertibleBond extends Bond {

    protected double conversionRatio;
    protected List<Dividend> dividends;
    protected List<Callability> callability;
    protected Handle<Quote> creditSpread;
    protected Option option;

    
    public ConvertibleBond(
            final Exercise exercise,
            final double conversionRatio,
            final List<Dividend> dividends,
            final List<Callability> callability,
            final Handle<Quote> creditSpread,
            final Date issueDate,
            final int settlementDays,
            final DayCounter dayCounter,
            final Schedule schedule,
            final double redemption) {

        super(settlementDays, schedule.calendar(), issueDate);
        this.conversionRatio = conversionRatio;
        this.callability = callability;
        this.dividends = dividends;
        this.creditSpread = creditSpread;
        this.maturityDate_ = schedule.endDate().clone();
        creditSpread.addObserver(this);
    }

    public double conversionRatio() /* @ReadOnly */ {
        return conversionRatio;
    }

    public List<Dividend> dividends() /* @ReadOnly */ {
        return dividends;
    }

    public List<Callability> callability() /* @ReadOnly */ {
        return callability;
    }

    public Handle<Quote> creditSpread() /* @ReadOnly */ {
        return creditSpread;
    }

    @Override
    protected void performCalculations() /* @ReadOnly */ {
        option.setPricingEngine(engine);
        NPV = settlementValue_ = option.NPV();
        errorEstimate = Constants.NULL_REAL;
    }

}
