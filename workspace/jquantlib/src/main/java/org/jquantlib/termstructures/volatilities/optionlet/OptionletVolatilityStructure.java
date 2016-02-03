/*
 Copyright (C) 2009 John Nichol

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
 Copyright (C) 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl

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
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.termstructures.volatilities.SmileSection;
import org.jquantlib.termstructures.volatilities.VolatilityTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;

/**
 * Optionlet (caplet/floorlet) volatility structure
 * <p>
 * This class is purely abstract and defines the interface of
 * concrete structures which will be derived from this one.
 */
public abstract class OptionletVolatilityStructure extends VolatilityTermStructure {


    //
    // public constructors
    //

	public OptionletVolatilityStructure(
	        final Calendar cal,
			final BusinessDayConvention bdc) {
		this(cal, bdc, new DayCounter());
	}

	public OptionletVolatilityStructure(
	        final Calendar cal,
			final BusinessDayConvention bdc,
			final DayCounter dc) {
		super(cal, bdc, dc);
	}

	/**
	 * initialize with a fixed reference date
	 */
	public OptionletVolatilityStructure(
	        final Date referenceDate,
			final Calendar cal,
			final BusinessDayConvention bdc) {
		super(referenceDate, cal, bdc, new DayCounter());
	}

	/**
	 * initialize with a fixed reference date
	 */
	public OptionletVolatilityStructure(
	        final Date referenceDate,
			final Calendar cal,
			final BusinessDayConvention bdc,
			final DayCounter dc) {
		super(referenceDate, cal, bdc, dc);
	}

	/**
	 * calculate the reference date based on the global evaluation date
	 */
	public OptionletVolatilityStructure(
	        final int settlementDays,
			final Calendar cal,
			final BusinessDayConvention bdc,
			final DayCounter dc) {
		super(settlementDays, cal, bdc, dc);
	}

	/**
	 * calculate the reference date based on the global evaluation date
	 */
	public OptionletVolatilityStructure(
	        final int settlementDays,
			final Calendar cal,
			final BusinessDayConvention bdc) {
		this(settlementDays, cal, bdc, new DayCounter());
	}


	//
	// public methods
	//

	/**
	 * returns the volatility for a given option tenor and strike rate
	 */
	public double volatility(final Period optionTenor, final double strike) {
		return volatility(optionTenor, strike, false);
	}

	/**
	 * returns the volatility for a given option date and strike rate
	 */
	public double volatility(final Date optionDate, final double strike) {
		return volatility(optionDate, strike, false);
	}

	/**
	 * returns the volatility for a given option time and strike rate
	 */
	public double volatility(@Time final double optionTime, final double strike) {
		return volatility(optionTime, strike, false);
	}

	/**
	 * returns the Black variance for a given option tenor and strike rate
	 */
	public double blackVariance(final Period optionTenor, final double strike) {
		return blackVariance(optionTenor, strike, false);
	}

	/**
	 * returns the Black variance for a given option date and strike rate
	 */
	public double blackVariance(final Date optionDate, final double strike) {
		return blackVariance(optionDate, strike, false);
	}

	/**
	 * returns the Black variance for a given option time and strike rate
	 */
	public double blackVariance(final @Time double optionTime, final double strike) {
		return blackVariance(optionTime, strike, false);
	}


	/**
	 * returns the smile for a given option tenor
	 */
	public SmileSection smileSection(final Period optionTenor) {
		return smileSection(optionTenor, false);
	}

	/**
	 * returns the smile for a given option date
	 */
	public SmileSection smileSection(final Date optionDate) {
		return smileSection(optionDate, false);
	}

	/**
	 * returns the smile for a given option time
	 */
	public SmileSection smileSection(final @Time double optionTime) {
		return smileSection(optionTime, false);
	}


	//
	// protected abstract methods
	//

	protected abstract SmileSection smileSectionImpl(final @Time double optionTime);

	/**
	 * implements the actual volatility calculation in derived classes
	 */
	protected abstract double volatilityImpl(/* @Time */double optionTime, double strike);



	//
	// 1. Period-based methods convert Period to Date and then use the equivalent Date-based methods
	//

	public double volatility(final Period optionTenor, final double strike, final boolean extrapolate) {
		final Date optionDate = optionDateFromTenor(optionTenor);
		return volatility(optionDate, strike, extrapolate);
	}

	public double blackVariance(final Period optionTenor,
		final double strike,
		final boolean extrapolate) {
		final Date optionDate = optionDateFromTenor(optionTenor);
		return blackVariance(optionDate, strike, extrapolate);
	}

	public SmileSection smileSection(final Period optionTenor, final boolean extrapolate) {
		final Date optionDate = optionDateFromTenor(optionTenor);
		return smileSection(optionDate, extrapolate);
	}


	//
	// 2. blackVariance methods rely on volatility methods
	//

	public double blackVariance(final Date optionDate, final double strike, final boolean extrapolate) {
		final double v = volatility(optionDate, strike, extrapolate);
		/* @Time */final double t = timeFromReference(optionDate);
		return v*v*t;
	}

	public double blackVariance(/* @Time */final double optionTime, final double strike, final boolean extrapolate) {
		final double v = volatility(optionTime, strike, extrapolate);
		return v*v*optionTime;
	}

	//
	// 3. relying on xxxImpl methods
	//

	public double volatility(final Date optionDate, final double strike, final boolean extrapolate) {
		checkRange(optionDate, extrapolate);
		checkStrike(strike, extrapolate);
		return volatilityImpl(optionDate, strike);
	}

	public double volatility(@Time final double optionTime, final double strike, final boolean extrapolate) {
		checkRange(optionTime, extrapolate);
		checkStrike(strike, extrapolate);
		return volatilityImpl(optionTime, strike);
	}

	public SmileSection smileSection(final Date optionDate, final boolean extr) {
		checkRange(optionDate, extr);
		return smileSectionImpl(optionDate);
	}

	public SmileSection smileSection(final @Time double optionTime, final boolean extrapolate) {
		checkRange(optionTime, extrapolate);
		return smileSectionImpl(optionTime);
	}

	//
	// 4. default implementation of Date-based xxxImpl methods relying on the equivalent Time-based methods
	//

	protected SmileSection smileSectionImpl(final Date optionDate) {
		return smileSectionImpl(timeFromReference(optionDate));
	}

	public double volatilityImpl(final Date optionDate, final double strike) {
		return volatilityImpl(timeFromReference(optionDate), strike);
	}

}
