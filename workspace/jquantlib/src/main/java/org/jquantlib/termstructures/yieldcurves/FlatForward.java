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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2007 StatPro Italia srl

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

package org.jquantlib.termstructures.yieldcurves;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.AbstractYieldTermStructure;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.calendars.NullCalendar;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * Flat interest-rate curve
 *
 * @category yieldtermstructures
 *
 * @author Richard Gomes
 */
public class FlatForward extends AbstractYieldTermStructure {

    private final Handle<? extends Quote> forward;
    private final Compounding compounding;
    private final Frequency frequency;
    private InterestRate rate;


    //
    // public constructors
    //

    public FlatForward(
            final Date referenceDate,
            final Handle<? extends Quote> forward,
            final DayCounter dayCounter,
            final Compounding compounding,
            final Frequency frequency) {
        super(referenceDate, new NullCalendar(), dayCounter);
        this.compounding = compounding;
        this.frequency = frequency;
        this.forward = forward;
        this.forward.addObserver(this);
        updateRate();
    }

    public FlatForward(
            final Date referenceDate,
            final Handle<? extends Quote> forward,
            final DayCounter dayCounter,
            final Compounding compounding) {
        this(referenceDate, forward, dayCounter, compounding, Frequency.Annual);
    }

    public FlatForward(
            final Date referenceDate,
            final Handle<? extends Quote> forward,
            final DayCounter dayCounter) {
        this(referenceDate, forward, dayCounter, Compounding.Continuous);
    }

    // --------------------------------------------

    public FlatForward(
            final Date referenceDate,
            final /*@Rate*/ double forward,
            final DayCounter dayCounter,
            final Compounding compounding,
            final Frequency frequency) {
        super(referenceDate, new NullCalendar(), dayCounter);
        this.forward = new Handle<SimpleQuote>(new SimpleQuote(forward));
        this.compounding = compounding;
        this.frequency = frequency;
        updateRate();
    }

    public FlatForward(
            final Date referenceDate,
            final /*@Rate*/ double forward,
            final DayCounter dayCounter,
            final Compounding compounding) {
        this(referenceDate, forward, dayCounter, compounding, Frequency.Annual);
    }

    public FlatForward(
            final Date referenceDate,
            final /*@Rate*/ double forward,
            final DayCounter dayCounter) {
        this(referenceDate, forward, dayCounter, Compounding.Continuous);
    }

    // --------------------------------------------

    public FlatForward(
            final int settlementDays,
            final Calendar calendar,
            final Handle<? extends Quote> forward,
            final DayCounter dayCounter,
            final Compounding compounding,
            final Frequency frequency) {
        super(settlementDays, calendar, dayCounter);
        this.compounding = compounding;
        this.frequency = frequency;
        this.forward = forward;
        this.forward.addObserver(this);
        updateRate();
    }

    public FlatForward(
            final int settlementDays,
            final Calendar calendar,
            final Handle<? extends Quote> forward,
            final DayCounter dayCounter) {
        this(settlementDays, calendar, forward, dayCounter, Compounding.Continuous);
    }

    public FlatForward(
            final int settlementDays,
            final Calendar calendar,
            final Handle<? extends Quote> forward,
            final DayCounter dayCounter,
            final Compounding compounding) {
        this(settlementDays, calendar, forward, dayCounter, compounding, Frequency.Annual);
    }

    // --------------------------------------------

    public FlatForward(
            final int settlementDays,
            final Calendar calendar,
            final /*@Rate*/ double forward,
            final DayCounter dayCounter,
            final Compounding compounding,
            final Frequency frequency) {
        super(settlementDays, calendar, dayCounter);
        this.forward = new Handle<Quote>(new SimpleQuote(forward));
        this.compounding = compounding;
        this.frequency = frequency;
        updateRate();
    }

    public FlatForward(
            final int settlementDays,
            final Calendar calendar,
            final /*@Rate*/ double forward,
            final DayCounter dayCounter) {
        this(settlementDays, calendar, forward, dayCounter, Compounding.Continuous);
    }

    public FlatForward(
            final int settlementDays,
            final Calendar calendar,
            final /*@Rate*/ double forward,
            final DayCounter dayCounter,
            final Compounding compounding) {
        this(settlementDays, calendar, forward, dayCounter, compounding, Frequency.Annual);
    }


    //
    // public methods
    //

    public final Compounding compounding() {
        return compounding;
    }

    public final Frequency compoundingFrequency() {
        return frequency;
    }


    //
    // private methods
    //

    private void updateRate() {
        rate = new InterestRate(forward.currentLink().value(), this.dayCounter(), this.compounding, this.frequency);
    }


    //
    // overrides YieldTermStructure
    //

    @Override
    protected final /*@DiscountFactor*/ double discountImpl(final /*@Time*/ double t) {
        return rate.discountFactor(t);
    }

    //
    // overrides TermStructure
    //

    @Override
    public final Date maxDate() {
        return Date.maxDate();
    }


    //
    // implements Observer interface
    //

    /**
     * This method implements {@link Observer#update(Observable, Object)}
     *
     * @see Observer#update(Observable, Object)
     */
    @Override
    //XXX::OBS public void update(final Observable o, final Object arg) {
    public void update() {
        updateRate();
        //XX::OBS super.update(o, arg);
        super.update();
    }

}
