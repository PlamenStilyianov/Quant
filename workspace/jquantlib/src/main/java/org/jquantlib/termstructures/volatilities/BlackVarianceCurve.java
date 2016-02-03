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
 Copyright (C) 2003 StatPro Italia srl

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

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.math.interpolations.Interpolation;
import org.jquantlib.math.interpolations.factories.Linear;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.termstructures.BlackVarianceTermStructure;
import org.jquantlib.termstructures.TermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Black volatility curve modelled as variance curve
 * <p>
 * This class calculates time-dependent Black volatilities using as input a
 * vector of (ATM) Black volatilities observed in the market.
 * <p>
 * The calculation is performed interpolating on the variance curve. Linear
 * interpolation is used as default; this can be changed by the
 * setInterpolation() method.
 * <p>
 * For strike dependence, see BlackVarianceSurface.
 *
 * @author Richard Gomes
 */
// TODO check time extrapolation
public class BlackVarianceCurve extends BlackVarianceTermStructure {

    //
    // private fields
    //

    private final DayCounter dayCounter;
    private final Date maxDate;
    private final Date[] dates;
    private final /*@Time*/ Array times;
    private final /*@Variance*/ Array variances;
    private Interpolation varianceCurve;
    private final Interpolation.Interpolator factory;


    //
    // public constructors
    //

    public BlackVarianceCurve(
            final Date referenceDate,
            final Date[] dates,
            final /*@Volatility*/ double[] blackVolCurve,
            final DayCounter dayCounter) {
        this(referenceDate, dates, blackVolCurve, dayCounter, true);
    }

    public BlackVarianceCurve(
            final Date referenceDate,
            final Date[] dates,
            final /*@Volatility*/ double[] blackVolCurve,
            final DayCounter dayCounter,
            final boolean forceMonotoneVariance) {
        super(referenceDate);

        QL.require(dates.length==blackVolCurve.length , "mismatch between date vector and black vol vector"); // TODO: message
        // cannot have dates[0]==referenceDate, since the
        // value of the volatility at dates[0] would be lost
        // (variance at referenceDate must be zero)
        QL.require(dates[0].gt(referenceDate) , "cannot have dates[0] <= referenceDate"); // TODO: message

        this.dayCounter = dayCounter;
        this.dates = dates.clone();
        this.maxDate = dates[dates.length-1];

        variances = /*@Variance*/ new Array(this.dates.length+1);
        times     = /*@Time*/     new Array(this.dates.length+1);
        variances.set(0, 0.0);
        times.set(0, 0.0);
        for (int j=1; j<=blackVolCurve.length; j++) {
            times.set(j, timeFromReference(this.dates[j-1]));
            QL.require(times.get(j)>times.get(j-1) , "times must be sorted unique"); // TODO: message
            final double value = times.get(j) * blackVolCurve[j-1]*blackVolCurve[j-1];
            variances.set(j, value);
            QL.require(variances.get(j)>=variances.get(j-1) || !forceMonotoneVariance , "variance must be non-decreasing"); // TODO: message
        }

        // default: linear interpolation
        factory = new Linear();
    }


    //
    // public methods
    //

    public void setInterpolation() {
        this.setInterpolation(factory);
    }

    public final void setInterpolation(final Interpolation.Interpolator factory) {
        varianceCurve = factory.interpolate(times, variances);
        varianceCurve.enableExtrapolation();
        varianceCurve.update();
        notifyObservers();
    }


    //
    // Overrides TermStructure
    //

    @Override
    public final DayCounter dayCounter() {
        return dayCounter;
    }

    @Override
    public final Date maxDate() {
        return maxDate;
    }


    //
    // Overrides BlackVolTermStrucure
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
    // TODO :: compare against C++ sources
    protected final /*@Variance*/ double blackVarianceImpl(final /*@Time*/ double t, final /*@Real*/ double maturity) {
        if (t <= times.last()) {
            return varianceCurve.op(t);
        } else {
            // extrapolate with flat vol
            /*@Time*/ final double lastTime = times.last();  // TODO: probably an error here
            return varianceCurve.op(lastTime) * t / lastTime;
        }
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<BlackVarianceCurve> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
