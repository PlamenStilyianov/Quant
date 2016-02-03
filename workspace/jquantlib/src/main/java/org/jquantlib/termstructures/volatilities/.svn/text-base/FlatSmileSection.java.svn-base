/*
 Copyright (C) 2009 Ueli Hofstetter
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
 Copyright (C) 2007 Ferdinando Ametrano
 Copyright (C) 2007 Fran�ois du Vignaud
 Copyright (C) 2007 Giorgio Facchinetti

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
import org.jquantlib.math.Constants;
import org.jquantlib.time.Date;

/**
 * Flat SmileSection
 *
 * @author Ueli Hofstetter
 * @author John Nichol
 */
public class FlatSmileSection extends SmileSection {

    private final double vol_;
    private final double atmLevel_;

    //
    // public constructors
    //

    public FlatSmileSection(
            final Date d,
            final double vol,
            final DayCounter dc,
            final Date referenceDate) {
    	this(d, vol, dc, referenceDate, Constants.NULL_REAL);
    }

    public FlatSmileSection(
            final Date d,
            final double vol,
            final DayCounter dc,
            final Date referenceDate,
            final /* @Real */ double atmLevel) {

        super(d, dc, referenceDate);
        this.vol_ = vol;
        this.atmLevel_ = atmLevel;
    }

    public FlatSmileSection(
            final /* @Time */ double exerciseTime,
            final double vol,
            final DayCounter dc,
            final /* @Real */ double atmLevel) {
    	super(exerciseTime, dc);
    	vol_ = vol;
    	atmLevel_ = atmLevel;
    }

    public FlatSmileSection(
            final /* @Time */ double exerciseTime,
            final double vol, final DayCounter dc) {
    	this(exerciseTime, vol, dc, Constants.NULL_REAL);
    }


    //
    // overrides SmileSection
    //

    @Override
    public double variance() {
        return vol_ * vol_ * exerciseTime_;
    }

    @Override
    public double volatility() {
        return vol_;
    }

    @Override
    public double minStrike() {
        return Constants.DBL_MIN;
    }

    @Override
    public double maxStrike() {
        return Constants.DBL_MAX;
    }

    @Override
    public /* @Real */ double atmLevel() {
        return atmLevel_;
    }

    @Override
    public /* @Volatility */ double volatilityImpl(/* @Rate */final double strike) {
        return vol_;
    }

}
