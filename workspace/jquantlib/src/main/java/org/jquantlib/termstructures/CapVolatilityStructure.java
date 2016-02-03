/*
 Copyright (C) 2009 Ueli Hofstetter

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
package org.jquantlib.termstructures;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.calendars.NullCalendar;

public abstract class CapVolatilityStructure extends AbstractTermStructure {

    public CapVolatilityStructure(final DayCounter dc) {
        super(dc == null ? new Actual365Fixed() : dc);
    }

    public CapVolatilityStructure(final Date referenceDate, final Calendar cal, final DayCounter dc) {
        super(referenceDate, cal == null ? new NullCalendar() : cal, dc == null ? new Actual365Fixed() : dc);
    }

    public CapVolatilityStructure(final int settlementDays, final Calendar cal, final DayCounter dc) {
        super(settlementDays, cal == null ? new NullCalendar() : cal, dc == null ? new Actual365Fixed() : dc);
    }

    public double volatility(final Date end, final double strike) {
        return volatility(end, strike, false);
    }

    public double volatility(final Date end, final double strike, final boolean extrapolate) {
        final double t = timeFromReference(end);
        checkRange(t, strike, extrapolate);
        return volatilityImpl(t, strike);

    }

    public abstract double minStrike();

    public abstract double maxStrike();

    public abstract double volatilityImpl(double length, double strike);

    // ! returns the volatility for a given cap/floor length and strike rate
    public double volatility(final Period optionTenor, final double strike, final boolean extrapolate) {
        final Date exerciseDate = calendar().advance(referenceDate(), optionTenor, BusinessDayConvention.Following); // FIXME: Original
        // C++ comment
        return volatility(exerciseDate, strike, extrapolate);
    }

    public double volatility(final Period length, final double strike) {
        return volatility(length, strike, false);
    }

    // ! returns the volatility for a given end time and strike rate
    public double volatility(final double t, final double strike, final boolean extrapolate) {
        checkRange(t, strike, extrapolate);
        return volatilityImpl(t, strike);
    }

    public double volatility(final double t, final double strike) {
        return volatility(t, strike, false);
    }

    private void checkRange(final double t, final double k, final boolean extrapolate) {
        super.checkRange(t, extrapolate);
        QL.require(extrapolate||allowsExtrapolation()||(k >= minStrike() && k <= maxStrike()) , "strike is outside curve domain"); // TODO: message
    }

}
