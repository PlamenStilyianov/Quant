/*
 Copyright (C) 2009 Ueli Hofstetter

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

package org.jquantlib.processes;

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Date;

// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class HestonProcess extends StochasticProcess {

    private final Handle<YieldTermStructure> riskFreeRate_, dividendYield_;
    private final Handle<Quote> s0_;
    private final RelinkableHandle<Quote> v0_, kappa_, theta_, sigma_, rho_;

    private enum Discretization {
        PartialTruncation, FullTruncation, Reflection, ExactVariance
    };

    private final Discretization discretization_;

    private double s0v_, v0v_, kappav_, thetav_, sigmav_, rhov_, sqrhov_;

    public HestonProcess(
            final Handle<YieldTermStructure> riskFreeRate,
            final Handle<YieldTermStructure> dividendYield,
            final Handle<Quote> s0,
            final double v0,
            final double kappa,
            final double theta,
            final double sigma,
            final double rho) {
        this(riskFreeRate, dividendYield, s0, v0, kappa, theta, sigma, rho, Discretization.FullTruncation);
    }

    public HestonProcess(
            final Handle<YieldTermStructure> riskFreeRate,
            final Handle<YieldTermStructure> dividendYield,
            final Handle<Quote> s0,
            final double v0,
            final double kappa,
            final double theta,
            final double sigma,
            final double rho,
            final Discretization d) {

        // TODO: code review :: please verify against QL/C++ code
        // TODO: code review :: super(new EulerDiscretization());
        // Seems like constructor which takes a Discretization must belong to
        // StochasticProcess and not StochasticProcess1D

        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }

        this.riskFreeRate_ = (riskFreeRate);
        this.dividendYield_ = (dividendYield);
        this.s0_ = (s0); // TODO: code review
        this.v0_ = new RelinkableHandle<Quote>(new SimpleQuote(v0));
        this.kappa_ = new RelinkableHandle<Quote>(new SimpleQuote(kappa));
        this.theta_ = new RelinkableHandle<Quote>(new SimpleQuote(theta));
        this.sigma_ = new RelinkableHandle<Quote>(new SimpleQuote(sigma));
        this.rho_ = new RelinkableHandle<Quote>(new SimpleQuote(rho));
        this.discretization_ = (d);

        // TODO: code review :: please verify against QL/C++ code

        this.riskFreeRate_.addObserver(this);
        this.dividendYield_.addObserver(this);
        this.s0_.addObserver(this);
    }

    // TODO: code review :: please verify against QL/C++ code
    public void update() {
        // helper variables to improve performance
        s0v_ = s0_.currentLink().value();
        v0v_ = v0_.currentLink().value();
        kappav_ = kappa_.currentLink().value();
        thetav_ = theta_.currentLink().value();
        sigmav_ = sigma_.currentLink().value();
        rhov_ = rho_.currentLink().value();
        sqrhov_ = Math.sqrt(1.0 - rhov_ * rhov_);

        // this->StochasticProcess::update();
    }

    public final RelinkableHandle<Quote> v0() {
        return v0_;
    }

    public final RelinkableHandle<Quote> rho() {
        return rho_;
    }

    public final RelinkableHandle<Quote> kappa() {
        return kappa_;
    }

    public final RelinkableHandle<Quote> theta() {
        return theta_;
    }

    public final RelinkableHandle<Quote> sigma() {
        return sigma_;
    }

    public final Handle<Quote> s0() {
        return s0_;
    }

    public final Handle<YieldTermStructure> dividendYield() {
        return dividendYield_;
    }

    public Handle<YieldTermStructure> riskFreeRate() {
        return riskFreeRate_;
    }


    //
    // Overrides StochasticProcess
    //

    @Override
    public Array initialValues() {
        return new Array( new double[] { s0v_, v0v_ } );
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public final/* @Time */double time(final Date d) {
        return riskFreeRate_.currentLink().dayCounter().yearFraction(riskFreeRate_.currentLink().referenceDate(), d);
    }

    @Override
    public Array drift(/* Time */final double t, final Array x) {
        final double x1 = x.get(1);
        final double vol = (x1 > 0.0)
        ? Math.sqrt(x1)
                : (discretization_ == Discretization.PartialTruncation)
                ? -Math.sqrt(-x1)
                        : 0.0;

                final double[] result = new double[2];
                result[0] = riskFreeRate_.currentLink().forwardRate(t, t, Compounding.Continuous).rate()
                - dividendYield_.currentLink().forwardRate(t, t, Compounding.Continuous).rate() - 0.5 * vol * vol;

                result[1] = kappav_ * (thetav_ - ((discretization_ == Discretization.PartialTruncation) ? x1 : vol * vol));
                return new Array(result);
    }

    @Override
    public Matrix diffusion(/* @Time */final double time, final Array x) {
        /*
         * the correlation matrix is | 1 rho | | rho 1 | whose square root (which is used here) is | 1 0 | | rho sqrt(1-rho^2) |
         */
        final double x1 = x.get(1);
        final double vol = (x1 > 0.0)
        ? Math.sqrt(x1)
                : (discretization_ == Discretization.Reflection)
                ? -Math.sqrt(-x1)
                        : 0.0;
                final double sigma2 = sigmav_ * vol;

                final Matrix result = new Matrix(2, 2);
                result.set(0, 0, vol);
                result.set(0, 1, 0.0);
                result.set(1, 0, rhov_ * sigma2);
                result.set(1, 1, sqrhov_ * sigma2);
                return result;
    }

    @Override
    public Array apply(final Array x0, final Array dx) {
        final double[] tmp = new double[2];
        tmp[0] = x0.get(0) * Math.exp(dx.get(0));
        tmp[1] = x0.get(1) + dx.get(1);
        return new Array(tmp);
    }

    @Override
    public Array evolve(/* @Time */final double t0, final Array x0, /* @Time */final double dt, final Array dw) {
        final double[] retVal = new double[2];
        double ncp, df, p, dy;
        double vol, vol2, mu, nu;

        final double sdt = Math.sqrt(dt);

        final double x00 = x0.get(0);
        final double x01 = x0.get(1);
        final double dw0 = dw.get(0);
        final double dw1 = dw.get(1);

        switch (discretization_) {
            // For the definition of PartialTruncation, FullTruncation
            // and Reflection see Lord, R., R. Koekkoek and D. van Dijk (2006),
            // "A Comparison of biased simulation schemes for
            // stochastic volatility models",
            // Working Paper, Tinbergen Institute
            case PartialTruncation:
                vol = (x01 > 0.0) ? Math.sqrt(x01) : 0.0;
                vol2 = sigmav_ * vol;
                mu = riskFreeRate_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate()
                - dividendYield_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate() - 0.5 * vol * vol;
                nu = kappav_ * (thetav_ - x01);

                retVal[0] = x00 * Math.exp(mu * dt + vol * dw0 * sdt);
                retVal[1] = x01 + nu * dt + vol2 * sdt * (rhov_ * dw0 + sqrhov_ * dw1);
                break;
            case FullTruncation:
                vol = (x01 > 0.0) ? Math.sqrt(x01) : 0.0;
                vol2 = sigmav_ * vol;
                mu = riskFreeRate_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate()
                - dividendYield_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate() - 0.5 * vol * vol;
                nu = kappav_ * (thetav_ - vol * vol);

                retVal[0] = x00 * Math.exp(mu * dt + vol * dw0 * sdt);
                retVal[1] = x01 + nu * dt + vol2 * sdt * (rhov_ * dw0 + sqrhov_ * dw1);
                break;
            case Reflection:
                vol = Math.sqrt(Math.abs(x01));
                vol2 = sigmav_ * vol;
                mu = riskFreeRate_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate()
                - dividendYield_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate() - 0.5 * vol * vol;
                nu = kappav_ * (thetav_ - vol * vol);

                retVal[0] = x00 * Math.exp(mu * dt + vol * dw0 * sdt);
                retVal[1] = vol * vol + nu * dt + vol2 * sdt * (rhov_ * dw0 + sqrhov_ * dw1);
                break;
            case ExactVariance:
                // use Alan Lewis trick to decorrelate the equity and the variance
                // process by using y(t)=x(t)-\frac{rho}{sigma}\nu(t)
                // and Ito's Lemma. Then use exact sampling for the variance
                // process. For further details please read the wilmott thread
                // "QuantLib code is very high quatlity"
                vol = (x01 > 0.0) ? Math.sqrt(x01) : 0.0;
                mu = riskFreeRate_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate()
                - dividendYield_.currentLink().forwardRate(t0, t0, Compounding.Continuous).rate() - 0.5 * vol * vol;

                df = 4 * thetav_ * kappav_ / (sigmav_ * sigmav_);
                ncp = 4 * kappav_ * Math.exp(-kappav_ * dt) / (sigmav_ * sigmav_ * (1 - Math.exp(-kappav_ * dt))) * x01;

                p = new CumulativeNormalDistribution().op(dw1);
                if (p < 0.0) {
                    p = 0.0;
                } else if (p >= 1.0) {
                    p = 1.0 - Constants.QL_EPSILON;
                }

                retVal[1] = sigmav_ * sigmav_ * (1 - Math.exp(-kappav_ * dt)) / (4 * kappav_);
                if (true) {
                    throw new UnsupportedOperationException("Work in progress");
                }

                dy = (mu - rhov_ / sigmav_ * kappav_ * (thetav_ - vol * vol)) * dt + vol * sqrhov_ * dw0 * sdt;

                retVal[0] = x00 * Math.exp(dy + rhov_ / sigmav_ * (retVal[1] - x01));
                break;
            default:
                throw new LibraryException("unknown discretization schema"); // TODO: message
        }

        return new Array( retVal );
    }

}
