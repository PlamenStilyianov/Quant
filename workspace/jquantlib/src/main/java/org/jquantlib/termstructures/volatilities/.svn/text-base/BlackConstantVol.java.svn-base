/*
 Copyright (C) 2008 Richard Gomes

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
 Copyright (C) 2002, 2003, 2004 Ferdinando Ametrano
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

package org.jquantlib.termstructures.volatilities;


import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolatilityTermStructure;
import org.jquantlib.termstructures.TermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Constant Black volatility, no time-strike dependence
 * <p>
 * This class implements the BlackVolatilityTermStructure
 * interface for a constant Black volatility (no time/strike
 * dependence).
 *
 * @author Richard Gomes
 */
public class BlackConstantVol extends BlackVolatilityTermStructure {

    private final Handle<? extends Quote> volatility;

    public BlackConstantVol(
            final Date referenceDate,
            final Calendar cal,
            /*@Volatility*/ final double volatility,
            final DayCounter dc) {
        super(referenceDate, cal, BusinessDayConvention.Following, dc);
        this.volatility = new Handle<Quote>(new SimpleQuote(volatility));
    }

    public BlackConstantVol(
                final Date referenceDate,
                final Calendar cal,
                final Handle<? extends Quote> volatility,
                final DayCounter dc) {
        super(referenceDate, cal, BusinessDayConvention.Following, dc);
        this.volatility = volatility;
        this.volatility.addObserver(this);
    }

    public BlackConstantVol(
                /*@Natural*/ final int settlementDays,
                final Calendar cal,
                /*@Volatility*/ final double volatility,
                final DayCounter dc) {
        super(settlementDays, cal, BusinessDayConvention.Following, dc);
        this.volatility = new Handle<Quote>(new SimpleQuote(volatility));
    }

    public BlackConstantVol(
                /*@Natural*/ final int settlementDays,
                final Calendar cal,
                final Handle<? extends Quote> volatility,
                final DayCounter dc) {
        super(settlementDays, cal, BusinessDayConvention.Following, dc);
        this.volatility = volatility;
        this.volatility.addObserver(this);
    }


    //
    // Overrides TermStructure
    //

    @Override
    public final Date maxDate() {
        return Date.maxDate();
    }


    //
    // Override BlackVolTermStructure
    //

    @Override
    public final /*@Real*/ double minStrike() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public final /*@Real*/ double maxStrike() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    protected final /*@Volatility*/ double blackVolImpl(final /*@Time*/ double maturity, final /*@Real*/ double strike) {
        return volatility.currentLink().value();
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<BlackConstantVol> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
