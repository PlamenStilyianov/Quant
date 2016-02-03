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
 Copyright (C) 2002, 2003 Ferdinando Ametrano

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
import org.jquantlib.termstructures.LocalVolTermStructure;
import org.jquantlib.termstructures.TermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Local volatility curve derived from a Black curve
 *
 * @author Richard Gomes
 */
public class LocalVolCurve extends LocalVolTermStructure {

    private final BlackVarianceCurve blackVarianceCurve;

    public LocalVolCurve(final Handle<BlackVarianceCurve> curve) {
        super(curve.currentLink().calendar(),
              curve.currentLink().businessDayConvention(),
              curve.currentLink().dayCounter());
        blackVarianceCurve = curve.currentLink();
        this.blackVarianceCurve.addObserver(this);
    }


    //
    // Overrides TermStructure
    //

    @Override
    public final Date referenceDate() {
        return blackVarianceCurve.referenceDate();
    }

    @Override
    public final DayCounter dayCounter() {
        return blackVarianceCurve.dayCounter();
    }

    @Override
    public final Date maxDate() {
        return blackVarianceCurve.maxDate();
    }


    //
    // Overrides LocalVolTermStructure
    //

    @Override
    public final /*@Real*/ double minStrike() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public final /*@Real*/ double maxStrike() {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * The relation
     * {@latex[ \int_0^T \sigma_L^2(t)dt = \sigma_B^2 T }
     * holds, where
     * {@latex$ \sigma_L(t) }
     * is the local volatility at time {@latex$ t } and {@latex$ \sigma_B(T) }
     * is the Black volatility for maturity {@latex$ T }.
     * <p>
     * From the above, the formula
     * {@latex[ \sigma_L(t) = \sqrt{\frac{\mathrm{d}}{\mathrm{d}t}\sigma_B^2(t)t} }
     * can be deduced which is here implemented.
     */
    @Override
    protected final /*@Volatility*/ double localVolImpl(
            final /*@Time*/ double maturity,
            final /*@Real*/ double strike) {
        /*@Time*/ final double m = maturity;
        /*@Time*/ final double dt = 1.0 / 365.0;
        /*@Variance*/ final double var1 = blackVarianceCurve.blackVariance(/*@Time*/ maturity, strike, true);
        /*@Variance*/ final double var2 = blackVarianceCurve.blackVariance(/*@Time*/ m + dt, strike, true);
        final double derivative = (var2 - var1) / dt;
        return Math.sqrt(derivative);
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<LocalVolCurve> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
