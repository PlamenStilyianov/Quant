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
 Copyright (C) 2006 Banca Profilo S.p.A.

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

import org.jquantlib.math.Constants;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Frequency;

/**
 * Forward Hull-White stochastic process
 *
 * @category processes
 *
 * @author Richard Gomes
 */
public class HullWhiteForwardProcess extends ForwardMeasureProcess1D {

    protected OrnsteinUhlenbeckProcess   process;
    protected Handle<YieldTermStructure> h;
    protected double                     a;
    protected double                     sigma;

    public HullWhiteForwardProcess(
            final Handle<YieldTermStructure> h,
            final double a,
            final double sigma) {
        super();
        this.process = new OrnsteinUhlenbeckProcess(
                a,
                sigma,
                h.currentLink()
                    .forwardRate(
                        0.0,
                        0.0,
                        Compounding.Continuous,Frequency.NoFrequency)
                    .rate());
        this.h = h;
        this.a = a;
        this.sigma = sigma;
    }

    //
    // public methods
    //

    public double a() /* @ReadOnly */{
        return this.a;
    }

    public double sigma() /* @ReadOnly */{
        return this.sigma;
    }

    public double alpha(
            final/* @Time */double t) /* @ReadOnly */{
        double alfa = a > Constants.QL_EPSILON ? (sigma / a) * (1 - Math.exp(-a * t)) : sigma * t;
        alfa *= 0.5 * alfa;
        alfa += h.currentLink().forwardRate(t, t, Compounding.Continuous, Frequency.NoFrequency).rate();
        return alfa;
    }

    public double M_T(
            final double s,
            final double t,
            final double T) /* @ReadOnly */{
        if (a > Constants.QL_EPSILON) {
            final double coeff = (sigma * sigma) / (a * a);
            final double exp1 = Math.exp(-a * (t - s));
            final double exp2 = Math.exp(-a * (T - t));
            final double exp3 = Math.exp(-a * (T + t - 2.0 * s));
            return coeff * (1 - exp1) - 0.5 * coeff * (exp2 - exp3);
        } else {
            // low-a algebraic limit
            final double coeff = (sigma * sigma) / 2.0;
            return coeff * (t - s) * (2.0 * T - t - s);
        }
    }

    public double B(
            final/* @Time */double t,
            final/* @Time */double T) /* @ReadOnly */{
        return a > Constants.QL_EPSILON ? 1 / a * (1 - Math.exp(-a * (T - t))) : T - t;
    }

    //
    // extends StochasticProcess1D
    //

    @Override
    public double x0() /* @ReadOnly */{
        return process.x0();
    }

    @Override
    public double drift(
            final/* @Time */double t,
            final double x) /* @ReadOnly */{
        double alpha_drift = sigma * sigma / (2 * a) * (1 - Math.exp(-2 * a * t));
        final double shift = 0.0001;
        final double f = h.currentLink().forwardRate(t, t, Compounding.Continuous, Frequency.NoFrequency).rate();
        final double fup = h.currentLink().forwardRate(t + shift, t + shift, Compounding.Continuous, Frequency.NoFrequency).rate();
        final double f_prime = (fup - f) / shift;
        alpha_drift += a * f + f_prime;
        return process.drift(t, x) + alpha_drift - B(t, T_) * sigma * sigma;
    }

    @Override
    public double diffusion(
            final/* @Time */double t,
            final double x) /* @ReadOnly */{
        return process.diffusion(t, x);
    }

    @Override
    public double expectation(
            final/* @Time */double t0,
            final double x0,
            final/* @Time */double dt) /* @ReadOnly */{
        return process.expectation(t0, x0, dt) + alpha(t0 + dt) - alpha(t0) * Math.exp(-a * dt) - M_T(t0, t0 + dt, T_);
    }

    @Override
    public double stdDeviation(
            final/* @Time */double t0,
            final double x0,
            final/* @Time */double dt) /* @ReadOnly */{
        return process.stdDeviation(t0, x0, dt);
    }

    @Override
    public double variance(
            final/* @Time */double t0,
            final double x0,
            final/* @Time */double dt) /* @ReadOnly */{
        return process.variance(t0, x0, dt);
    }

}
