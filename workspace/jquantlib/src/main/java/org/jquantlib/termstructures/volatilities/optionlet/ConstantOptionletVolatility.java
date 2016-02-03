/*
 Copyright (C) 2010 Zahid Hussain

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
 Copyright (C) 2008 Ferdinando Ametrano
 Copyright (C) 2004, 2005, 2007 StatPro Italia srl

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
package org.jquantlib.termstructures.volatilities.optionlet;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.math.Constants;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.volatilities.FlatSmileSection;
import org.jquantlib.termstructures.volatilities.SmileSection;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;

/**
 * Constant caplet volatility, no time-strike dependence
 * 
 * @author Zahid Hussain
 */
public class ConstantOptionletVolatility extends OptionletVolatilityStructure {

	private final Handle<Quote> volatility_;

	/**
	 * floating reference date, floating market data
	 */
	public ConstantOptionletVolatility(final int settlementDays,
			final Calendar cal, final BusinessDayConvention bdc,
			final Handle<Quote> vol, final DayCounter dc) {
		super(settlementDays, cal, bdc, dc);
		this.volatility_ = vol;
		volatility_.addObserver(this);
	}

	/**
	 * fixed reference date, floating market data
	 */
	public ConstantOptionletVolatility(final Date referenceDate,
			final Calendar cal, final BusinessDayConvention bdc,
			final Handle<Quote> vol, final DayCounter dc) {
		super(referenceDate, cal, bdc, dc);
		this.volatility_ = vol;
		volatility_.addObserver(this);
	}

	/**
	 * floating reference date, fixed market data
	 */
	public ConstantOptionletVolatility(final int settlementDays,
			final Calendar cal, final BusinessDayConvention bdc,
			final double vol, final DayCounter dc) {
		super(settlementDays, cal, bdc, dc);
		this.volatility_ = new Handle<Quote>(new SimpleQuote(vol));
	}

	/**
	 * fixed reference date, fixed market data
	 */
	public ConstantOptionletVolatility(final Date referenceDate,
			final Calendar cal, final BusinessDayConvention bdc, final double vol,
			final DayCounter dc) {
		super(referenceDate, cal, bdc, dc);
		volatility_ = new Handle<Quote>(new SimpleQuote(vol));
	}


	//
	// overrides TermStructure
	//

	@Override
    public Date maxDate() {
		return Date.maxDate();
	}


    //
    // overrides VolatilityTermStructure
    //

	@Override
    public double minStrike() {
		return Constants.QL_MIN_REAL;
	}

	@Override
    public double maxStrike() {
		return Constants.QL_MAX_REAL;
	}


    //
    // overrides OptionletVolatilityStructure
    //

    @Override
    protected SmileSection smileSectionImpl(final Date d) {

        final double /* Volatility */atmVol = volatility_.currentLink().value();
        return new FlatSmileSection(d, atmVol, dayCounter(), referenceDate());
    }

    @Override
    protected SmileSection smileSectionImpl(final double optionTime) {
        final double /* Volatility */atmVol = volatility_.currentLink().value();
        return new FlatSmileSection(optionTime, atmVol, dayCounter());
    }

	@Override
    protected double /* Volatility */volatilityImpl(final double time, final double rate) {
		return volatility_.currentLink().value();
	}
}
