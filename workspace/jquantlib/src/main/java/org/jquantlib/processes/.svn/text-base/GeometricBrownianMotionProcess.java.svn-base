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

/**
 * Geometric brownian-motion process
 * <p>
 * This class describes the stochastic process governed by
 * <p>
 * <p>{@latex[ dS(t, S) = \mu S dt + \sigma S dW_t }
 * 
 * @category processes
 * 
 * @author Ueli Hofstetter
 */
public class GeometricBrownianMotionProcess extends StochasticProcess1D {

    protected double initialValue_;
    protected double mue_;
    protected double sigma_;

    public GeometricBrownianMotionProcess(
            final double initialValue,
            final double mue,
            final double sigma) {
        this.sigma_ = sigma;
        this.initialValue_ = initialValue;
        this.mue_ = mue;
    }

    @Override
    public double diffusion(
            final double t,
            final double x) {
        return sigma_ * x;
    }

    @Override
    public double drift(
            final double t,
            final double x) {
        return mue_ * x;
    }

    @Override
    public double x0() {
        return initialValue_;
    }

}
