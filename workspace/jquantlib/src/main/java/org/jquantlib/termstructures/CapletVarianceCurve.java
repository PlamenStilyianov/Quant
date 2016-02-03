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
 Copyright (C) 2005 Klaus Spanderen

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
package org.jquantlib.termstructures;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.termstructures.volatilities.BlackVarianceCurve;
import org.jquantlib.termstructures.volatilities.FlatSmileSection;
import org.jquantlib.termstructures.volatilities.SmileSection;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;

/**
 * caplet variance curve
 * <p>
 * @deprecated use the StrippedOptionletAdapter of a StrippedOptionlet instance
*/
@Deprecated
public class CapletVarianceCurve extends OptionletVolatilityStructure {

	private final BlackVarianceCurve blackCurve;

	//
	// public constructor
	//

	public CapletVarianceCurve(
	        final Date referenceDate,
            final Date [] dates,
            final double [] capletVolCurve,
            final DayCounter dayCounter) {
		super(referenceDate, new Calendar(), BusinessDayConvention.Following);
		blackCurve = new BlackVarianceCurve(referenceDate, dates, capletVolCurve, dayCounter, false);
	}


    //
    // overrides TermStructure
    //

    public Date maxDate() {
        return blackCurve.maxDate();
    }

	//
	// overrides AbstractTermStructure
	//

	@Override
    public DayCounter dayCounter() {
		return blackCurve.dayCounter();
	}

	//
	// overrides VolatilityTermStructure
	//

	@Override
    public /* @Real */ double minStrike() {
		return blackCurve.minStrike();
	}

	@Override
    public /* @Real */ double maxStrike() {
		return blackCurve.maxStrike();
	}

	//
	// override OptionletVolatilityStructure
	//

	@Override
    protected SmileSection smileSectionImpl(/* @Time */final double t) {
		// dummy strike
		final double atmVol = blackCurve.blackVol(t, 0.05, true);
		return new FlatSmileSection(t, atmVol, dayCounter());
	}

	@Override
    protected /* @Volatility */ double volatilityImpl(/* @Time */ final double t, /* @Rate */ final double r) {
		return blackCurve.blackVol(t, r, true);
	}

}
