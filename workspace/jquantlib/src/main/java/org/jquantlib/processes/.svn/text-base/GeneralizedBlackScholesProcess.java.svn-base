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
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2004, 2005 StatPro Italia srl

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

package org.jquantlib.processes;

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.LocalVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.volatilities.BlackVarianceCurve;
import org.jquantlib.termstructures.volatilities.LocalConstantVol;
import org.jquantlib.termstructures.volatilities.LocalVolCurve;
import org.jquantlib.termstructures.volatilities.LocalVolSurface;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;

/**
 * Generalized Black-Scholes stochastic process
 *
 * <p>
 * This class describes the stochastic process governed by
 * <p>
 * {@latex[ dS(t, S) = (r(t) - q(t) - \frac \sigma(t, S)^2}{2}) dt + \sigma dW_t. }
 *
 * @author Richard Gomes
 */
public class GeneralizedBlackScholesProcess extends StochasticProcess1D {

    private final Handle<? extends Quote> x0;
    private final Handle<YieldTermStructure> riskFreeRate;
    private final Handle<YieldTermStructure> dividendYield;
    private final Handle<BlackVolTermStructure> blackVolatility;
    private final RelinkableHandle<LocalVolTermStructure> localVolatility;
    private boolean updated;

    /**
     * @param discretization
     *            is an Object that <b>must</b> implement {@link Discretization}
     *            <b>and</b> {@link Discretization1D}.
     */
    public GeneralizedBlackScholesProcess(
            final Handle<? extends Quote> x0,
            final Handle<YieldTermStructure> dividendTS,
            final Handle<YieldTermStructure> riskFreeTS,
            final Handle<BlackVolTermStructure> blackVolTS) {
        this(x0, dividendTS, riskFreeTS, blackVolTS, new EulerDiscretization());
    }

    /**
     * @param discretization
     *            is an Object that <b>must</b> implement {@link Discretization}
     *            <b>and</b> {@link Discretization1D}.
     */
    public GeneralizedBlackScholesProcess(
            final Handle<? extends Quote> x0,
            final Handle<YieldTermStructure> dividendTS,
            final Handle<YieldTermStructure> riskFreeTS,
            final Handle<BlackVolTermStructure> blackVolTS,
            final StochasticProcess1D.Discretization1D discretization) {
        super(discretization);
        this.localVolatility = new RelinkableHandle<LocalVolTermStructure>();
//XXX :: remove
//
//                this.localVolatility = new RelinkableHandle<LocalVolTermStructure>(
//                        new LocalVolTermStructure() {
//                            @Override
//                            protected double localVolImpl(final double t, final double strike) {
//                                throw new UnsupportedOperationException();
//                            }
//                            @Override
//                            public double maxStrike() {
//                                throw new UnsupportedOperationException();
//                            }
//                            @Override
//                            public double minStrike() {
//                                throw new UnsupportedOperationException();
//                            }
//                            @Override
//                            public Date maxDate() {
//                                throw new UnsupportedOperationException();
//                            }
//                        }
//                );

        this.x0 = x0;
        this.riskFreeRate = riskFreeTS;
        this.dividendYield = dividendTS;
        this.blackVolatility = blackVolTS;
        this.updated = false;

        this.x0.addObserver(this);
        this.riskFreeRate.addObserver(this);
        this.dividendYield.addObserver(this);
        this.blackVolatility.addObserver(this);
    }


    public final Handle<? extends Quote> stateVariable() {
        return x0;
    }

    public final Handle<YieldTermStructure> dividendYield() {
        return dividendYield;
    }

    public final Handle<YieldTermStructure> riskFreeRate() {
        return riskFreeRate;
    }

    public final Handle<BlackVolTermStructure> blackVolatility() {
        return blackVolatility;
    }

    public final Handle<LocalVolTermStructure> localVolatility() {
        if (!updated) {
            final Class<? extends BlackVolTermStructure> klass = blackVolatility.currentLink().getClass();

            // constant Black vol?
            if (BlackConstantVol.class.isAssignableFrom(klass)) {
                // ok, the local volatility is constant too.
                final BlackConstantVol constVol = (BlackConstantVol) blackVolatility.currentLink();
                localVolatility.linkTo(new LocalConstantVol(
                        constVol.referenceDate(),
                        constVol.blackVol(/*@Time*/0.0, /*@Real*/x0.currentLink().value()), constVol.dayCounter()));
                updated = true;
                return localVolatility;
            }

            // ok, so it's not constant. Maybe it's strike-independent?
            if (BlackVarianceCurve.class.isAssignableFrom(klass)) {
                final Handle<BlackVarianceCurve> volCurve = new Handle<BlackVarianceCurve>(
                        (BlackVarianceCurve) blackVolatility().currentLink());
                localVolatility.linkTo(new LocalVolCurve(volCurve));
                updated = true;
                return localVolatility;
            }

            // ok, so it's strike-dependent. Never mind.
            if (LocalVolSurface.class.isAssignableFrom(klass)) {
                localVolatility.linkTo(new LocalVolSurface(blackVolatility, riskFreeRate, dividendYield, x0));
                updated = true;
                return localVolatility;
            }

            // Note: The previous LocalVolSurface case was a catch-all condition.
            // We decided to explicitly test the interface and throw an exception if we are not able
            // to identify the correct interface to be used.
            throw new LibraryException("unrecognized volatility curve"); // QA:[RG]::verified // FIXME: message
        } else
            return localVolatility;
    }


    //
    // implements StochasticProcess1D
    //

    @Override
    public/* @Real */double x0() {
        return x0.currentLink().value();
    }

    @Override
    public /* @Drift */ double drift(
            final/* @Time */double t,
            final/* @Real */double x) {
        /* @Diffusion */final double sigma = diffusion(t, x);
        // we could be more anticipatory if we know the right dt
        // for which the drift will be used
        /* @Time */final double t1 = t + 0.0001;
        final YieldTermStructure yts = riskFreeRate.currentLink();
        /* @Rate */final double r = yts.forwardRate(t, t1, Compounding.Continuous, Frequency.NoFrequency, true).rate();

        final YieldTermStructure divTs = dividendYield.currentLink();
        final double d = divTs.forwardRate(t, t1, Compounding.Continuous, Frequency.NoFrequency, true).rate();
        return r - d - 0.5 * sigma * sigma;
    }

    @Override
    public/* @Diffusion */double diffusion(
            final/* @Time */double t,
            final/* @Real */double x) {
        /* @Volatility */final double vol = localVolatility().currentLink().localVol(t, x, true);
        return vol;
    }

    @Override
    public final/* @Real */double apply(
            final/* @Real */double x0,
            final/* @Time */double dx) {
        // result = x0 * e^dx
        final double result = x0 * Math.exp(dx);
        return result;
    }

    @Override
    public final/* @Time */double time(final Date d) {
        final YieldTermStructure yts = riskFreeRate.currentLink();
        return yts.dayCounter().yearFraction(yts.referenceDate(), d);
    }

    //
    // implements Observer
    //

    @Override
    //XXX::OBS public final void update(final Observable o, final Object arg) {
    public final void update() {
        updated = false;
        //XXX::OBS super.update(o, arg);
        super.update();
    }

}
