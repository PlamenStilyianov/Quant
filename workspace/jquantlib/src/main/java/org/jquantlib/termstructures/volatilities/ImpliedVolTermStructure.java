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
 Copyright (C) 2003 Ferdinando Ametrano

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
import org.jquantlib.termstructures.BlackVarianceTermStructure;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.TermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Implied vol term structure at a given date in the future
 * <p>
 * The given date will be the implied reference date.
 *
 * @note This term structure will remain linked to the original structure,
 *       i.e., any changes in the latter will be reflected in this structure as well.
 *
 * @note It doesn't make financial sense to have an asset-dependent implied Volatility Term Structure.
 *       This class should be used with term structures that are time dependent only.
 *
 * @author Richard Gomes
 */
public class ImpliedVolTermStructure extends BlackVarianceTermStructure {

    private final Handle<BlackVolTermStructure> originalTS;

    public ImpliedVolTermStructure(final Handle<BlackVolTermStructure> originalTS, final Date referenceDate) {
        super(referenceDate);
        this.originalTS = originalTS;

        this.originalTS.addObserver(this);
        //XXX:registerWith
        //registerWith(originalTS);
    }


    //
    // Overrides TermStructure
    //

    @Override
    public Date maxDate() {
        return originalTS.currentLink().maxDate();
    }


    @Override
    public DayCounter dayCounter() /* @ReadOnly */ {
        return originalTS.currentLink().dayCounter();
    }


    //
    // Override BlackVolTermStructure
    //

    @Override
    protected double blackVarianceImpl(/* @Time */final double t, /* @Real */final double strike) /* @ReadOnly */{
        // timeShift (and/or variance) variance at evaluation date cannot be cached since the original curve could change between
        // invocations of this method
        /* @Time */ final double timeShift = dayCounter().yearFraction(originalTS.currentLink().referenceDate(), referenceDate());

        // t is relative to the current reference date and needs to be converted to the time relative to the reference date of the
        // original curve
        return originalTS.currentLink().blackForwardVariance(timeShift, timeShift + t, strike, true);
    }

    @Override
    public /*@Real*/ double maxStrike() /* @ReadOnly */ {
        return originalTS.currentLink().maxStrike();
    }

    @Override
    public /*@Real*/ double minStrike() /* @ReadOnly */ {
        return originalTS.currentLink().minStrike();
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<ImpliedVolTermStructure> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
