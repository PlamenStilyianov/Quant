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

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.LocalVolTermStructure;
import org.jquantlib.termstructures.TermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Local volatility surface derived from a Black vol surface
 * <p>
 * For details about this implementation refer to "Stochastic Volatility and
 * Local Volatility" in "Case Studies and Financial Modelling Course Notes," by
 * Jim Gatheral, Fall Term, 2003
 *
 * @see <a href="http://www.math.nyu.edu/fellows_fin_math/gatheral/Lecture1_Fall02.pdf">This article</a>
 *
 * @author Richard Gomes
 */
// TODO: this class is untested, probably unreliable.
public class LocalVolSurface extends LocalVolTermStructure {

    private final Handle<BlackVolTermStructure> blackTS_;
    private final Handle<YieldTermStructure> riskFreeTS_;
    private final Handle<YieldTermStructure> dividendTS_;
    private final Handle<? extends Quote> underlying_;

    public LocalVolSurface(
            final Handle<BlackVolTermStructure> blackTS,
            final Handle<YieldTermStructure> riskFreeTS,
            final Handle<YieldTermStructure> dividendTS,
            final Handle<? extends Quote> underlying) {

        super(blackTS.currentLink().calendar(),
              blackTS.currentLink().businessDayConvention(),
              blackTS.currentLink().dayCounter());

        this.blackTS_ = blackTS;
        this.riskFreeTS_ = riskFreeTS;
        this.dividendTS_ = dividendTS;
        this.underlying_ = underlying;

        this.blackTS_.addObserver(this);
        this.riskFreeTS_.addObserver(this);
        this.dividendTS_.addObserver(this);
        this.underlying_.addObserver(this);
    }

    public LocalVolSurface(
            final Handle<BlackVolTermStructure> blackTS,
            final Handle<YieldTermStructure> riskFreeTS,
            final Handle<YieldTermStructure> dividendTS,
            final /*@Real*/ double underlying) {

        super(blackTS.currentLink().calendar(),
              blackTS.currentLink().businessDayConvention(),
              blackTS.currentLink().dayCounter());

        this.blackTS_ = blackTS;
        this.riskFreeTS_ = riskFreeTS;
        this.dividendTS_ = dividendTS;
        this.underlying_ = new Handle<Quote>(new SimpleQuote(underlying));

        this.blackTS_.addObserver(this);
        this.riskFreeTS_.addObserver(this);
        this.dividendTS_.addObserver(this);
    }


    //
    // Overrides LocalVolTermStructure
    //

    @Override
    public final Date referenceDate() {
        return this.blackTS_.currentLink().referenceDate();
    }

    @Override
    public final DayCounter dayCounter() {
        return this.blackTS_.currentLink().dayCounter();
    }

    @Override
    public final Date maxDate() {
        return blackTS_.currentLink().maxDate();
    }

    @Override
    public final /*@Real*/ double minStrike() {
        return blackTS_.currentLink().minStrike();
    }

    @Override
    public final /*@Real*/ double maxStrike() {
        return blackTS_.currentLink().maxStrike();
    }

    @Override
    protected final /*@Volatility*/ double localVolImpl(
            final /*@Time*/ double time,
            final /*@Real*/ double underlyingLevel) {

        // obtain local copies of objects
        final Quote u = underlying_.currentLink();
        final YieldTermStructure dTS = dividendTS_.currentLink();
        final YieldTermStructure rTS = riskFreeTS_.currentLink();
        final BlackVolTermStructure bTS = blackTS_.currentLink();

        final double forwardValue = u.value() * ( dTS.discount(time, true) / rTS.discount(time, true) );

        // strike derivatives
        /*@Real*/ double strike;
        /*@Real*/ double strikem;
        /*@Real*/ double strikep;
        double y, dy;
        double w, wp, wm, dwdy, d2wdy2;
        strike = underlyingLevel;
        y = Math.log(strike / forwardValue);
        dy = ((y != 0.0) ? y * 0.000001 : 0.000001);
        strikep = strike * Math.exp(dy);
        strikem = strike / Math.exp(dy);
        w = bTS.blackVariance(time,  strike, true);
        wp = bTS.blackVariance(time, strikep, true);
        wm = bTS.blackVariance(time, strikem, true);
        dwdy = (wp - wm) / (2.0 * dy);
        d2wdy2 = (wp - 2.0 * w + wm) / (dy * dy);

        // time derivative
        /*@Time*/ final double t = time;
        /*@Time*/ double dt;
        double wpt, wmt, dwdt;
        if (t == 0.0) {
            dt = 0.0001;
            wpt = bTS.blackVariance(/*@Time*/ (t + dt), strike, true);
            QL.require(wpt >= w , "decreasing variance at strike"); // TODO: message
            dwdt = (wpt - w) / dt;
        } else {
            dt = Math.min(0.0001, t / 2.0);
            wpt = bTS.blackVariance(/*@Time*/ (t + dt), strike, true);
            wmt = bTS.blackVariance(/*@Time*/ (t - dt), strike, true);
            QL.ensure(wpt >= w , "decreasing variance at strike"); // TODO: message
            QL.ensure(w >= wmt , "decreasing variance at strike"); // TODO: message
            dwdt = (wpt - wmt) / (2.0 * dt);
        }

        if (dwdy == 0.0 && d2wdy2 == 0.0)
            return Math.sqrt(dwdt);
        else {
            final double den1 = 1.0 - y / w * dwdy;
            final double den2 = 0.25 * (-0.25 - 1.0 / w + y * y / w / w) * dwdy * dwdy;
            final double den3 = 0.5 * d2wdy2;
            final double den = den1 + den2 + den3;
            final double result = dwdt / den;
            QL.ensure(result >= 0.0 , "negative local vol^2 at strike); the black vol surface is not smooth enough"); // TODO: message
            return Math.sqrt(result);

            // TODO: code review :: please verify against QL/C++ code

            // commented out at original source QuantLib
            // return std::sqrt(dwdt / (1.0 - y/w*dwdy +
            // 0.25*(-0.25 - 1.0/w + y*y/w/w)*dwdy*dwdy + 0.5*d2wdy2));
        }
    }


    //
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<LocalVolSurface> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
