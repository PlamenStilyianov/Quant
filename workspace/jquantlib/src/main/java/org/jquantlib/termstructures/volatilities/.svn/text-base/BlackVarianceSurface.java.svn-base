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
 Copyright (C) 2003, 2004 StatPro Italia srl

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
import org.jquantlib.math.interpolations.Interpolation2D;
import org.jquantlib.math.interpolations.Interpolation.Interpolator;
import org.jquantlib.math.interpolations.factories.Bilinear;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.termstructures.BlackVarianceTermStructure;
import org.jquantlib.termstructures.TermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * This class calculates time/strike dependent Black volatilities using as input
 * a matrix of Black volatilities observed in the market.
 *
 * The calculation is performed interpolating on the variance surface. Bilinear
 * interpolation is used as default; this can be changed by the
 * setInterpolation() method.
 *
 * @author Richard Gomes
 */
// TODO: check time extrapolation
public class BlackVarianceSurface extends BlackVarianceTermStructure {

    public enum Extrapolation {
        ConstantExtrapolation, InterpolatorDefaultExtrapolation
    };


    //
    // private fields
    //

    private final DayCounter dayCounter;
    private final Date maxDate;
    private final /* @Time */ Array times;
    private /* @Real */ Array strikes;
    private final /* @Variance */ Matrix variances;
    private Interpolation2D varianceSurface;
    private final Extrapolation lowerExtrapolation;
    private final Extrapolation upperExtrapolation;
    private final Interpolation2D.Interpolator2D factory;


    //
    // public constructors
    //

    public BlackVarianceSurface(final Date referenceDate, final Date[] dates,
            final/* @Real */ Array strikes, final/* @Volatility */ Matrix blackVolMatrix, final DayCounter dayCounter) {

        this(referenceDate, dates, strikes, blackVolMatrix, dayCounter,
                Extrapolation.InterpolatorDefaultExtrapolation,
                Extrapolation.InterpolatorDefaultExtrapolation);
    }

    public BlackVarianceSurface(
            final Date referenceDate,
            final Date[] dates,
            final/* @Real */ Array strikes,
            final/* @Volatility */ Matrix blackVolMatrix,
            final DayCounter dayCounter,
            final Extrapolation lowerExtrapolation,
            final Extrapolation upperExtrapolation) {

        super(referenceDate);
        QL.require(dates.length == blackVolMatrix.columns() , "mismatch between date vector and vol matrix colums"); // TODO: message
        QL.require(strikes.size() == blackVolMatrix.rows() , "mismatch between money-strike vector and vol matrix rows"); // TODO: message
        QL.require(dates[0].gt(referenceDate) , "cannot have dates[0] <= referenceDate"); // TODO: message

        this.dayCounter = dayCounter;
        this.maxDate = dates[dates.length-1]; // TODO: code review: index seems to be wrong
        // TODO: code review :: use of clone()
        this.strikes = strikes.clone();
        this.lowerExtrapolation = lowerExtrapolation;
        this.upperExtrapolation = upperExtrapolation;


        this.times = new Array(dates.length+1); // TODO: verify if length is correct
        this.variances = new Matrix(strikes.size(), dates.length+1); // TODO: verify if length is correct
        this.strikes = new Array(strikes.size()+1); // TODO: verify if length is correct

        for(int i = 1; i < strikes.size()+1; i++) {
            this.strikes.set(i, strikes.get(i-1));
        }

        for (int j = 1; j <= blackVolMatrix.columns(); j++) {
            times.set(j, timeFromReference(dates[j-1]));
            QL.require(times.get(j) > times.get(j-1) , "dates must be sorted unique!"); // TODO: message
            for (int i = 0; i < blackVolMatrix.rows(); i++) {
                final double elem = blackVolMatrix.get(i, j-1);
                final double ijvar = times.get(j) * elem * elem;
                variances.set(i, j, ijvar);
                QL.require(ijvar >= variances.get(i, j-1) , "variance must be non-decreasing"); // TODO: message
            }
        }
        // default: bilinear interpolation
        factory = new Bilinear();
    }


    //
    // public methods
    //

    public void setInterpolation(final Interpolator i) {
        varianceSurface = factory.interpolate(times, strikes, variances);
        varianceSurface.enableExtrapolation();
        varianceSurface.update();
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
    // Overrides BlackVolTermStructure
    //

    @Override
    public final /* @Real */ double minStrike() {
        return strikes.first();
    }

    @Override
    public final /* @Real */ double maxStrike() {
        return strikes.last();
    }

    @Override
    protected final/* @Variance */double blackVarianceImpl(/* @Time */final double t, /* @Real */double strike) /* @ReadOnly */{

        if (t == 0.0) {
            return 0.0;
        }

        // enforce constant extrapolation when required
        if (strike < strikes.first() && lowerExtrapolation == Extrapolation.ConstantExtrapolation) {
            strike = strikes.first();
        }
        if (strike > strikes.last()  && upperExtrapolation == Extrapolation.ConstantExtrapolation) {
            strike = strikes.last();
        }

        if (t <= times.last()) {
            return varianceSurface.op(t, strike);
        } else {
            // TODO: code review :: please verify against QL/C++ code
            // t>times_.back() || extrapolate
            /* @Time */final double lastTime = times.last();
            return varianceSurface.op(lastTime, strike) * t / lastTime;
        }
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<BlackVarianceSurface> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
