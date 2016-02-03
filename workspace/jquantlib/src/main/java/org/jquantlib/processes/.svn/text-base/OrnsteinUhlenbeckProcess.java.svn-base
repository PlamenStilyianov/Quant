/*
Copyright (C) 2008 Praneet Tiwari
Copyright (C) 2009 Richard Gomes

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

import org.jquantlib.QL;
import org.jquantlib.math.Constants;


/**
 * Ornstein-Uhlenbeck process class
 * <p>
 * This class describes the Ornstein-Uhlenbeck process governed by
 * {@latex[ dx = a (r - x_t) dt + \sigma dW_t }
 *
 * @category processes
 *
 * @author Praneet Tiwari
 * @author Richard Gomes
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class OrnsteinUhlenbeckProcess extends StochasticProcess1D {

    static private final String NEGATIVE_SPEED_GIVEN = "negative speed given";
    static private final String NEGATIVE_VOLATILITY_GIVEN = "negative volatilty given";

    private final double /* @Real */x0_, speed_, level_;
    private final double /* @Volatility */volatility_;

    public OrnsteinUhlenbeckProcess(
            final double /* @Real */speed,
            final double /* @Volatility */ vol) {
        this(speed, vol, 0.0, 0.0);
    }

    public OrnsteinUhlenbeckProcess(
            final double /* @Real */speed,
            final double /* @Volatility */vol,
            final double /* @Real */x0) {
        this(speed, vol, x0, 0.0);
    }

    public OrnsteinUhlenbeckProcess(
            final double /* @Real */speed,
            final double /* @Volatility */vol,
            final double /* @Real */x0,
            final double /* @Real */level) {
        super();

        QL.require(speed >= 0.0, NEGATIVE_SPEED_GIVEN); // TODO: message
        QL.require(vol >= 0.0, NEGATIVE_VOLATILITY_GIVEN); // TODO: message

        x0_ = (x0);
        speed_ = (speed);
        level_ = (level);
        volatility_ = (vol);
    }


    //
    // public methods
    //

    public double /* @Real */speed() {
        return speed_;
    }

    public double /* @Real */volatility() {
        return volatility_;
    }

    public double /* @Real */level() {
        return level_;
    }

    //
    // extends StochasticProcess1D
    //

    @Override
    public double /* @Real */x0() {
        return x0_;
    }

    @Override
    public double /* @Real */drift(
            final double /* @Time */t,
            final double /* @Real */x) {
        return speed_ * (level_ - x);
    }

    @Override
    public double /* @Real */diffusion(
            final double /* @Time */t,
            final double /* @Real */x) {
        return volatility_;
    }

    @Override
    public double /* @Real */expectation(
            final double /* @Time */t0,
            final double /* @Real */x0,
            final double /* @Time */dt) {
        return level_ + (x0 - level_) * Math.exp(-speed_ * dt);
    }

    @Override
    public double /* @Real */stdDeviation(
            final double /* @Time */t0,
            final double /* @Real */x0,
            final double /* @Time */dt) {
        return Math.sqrt(variance(t0, x0, dt));
    }

    @Override
    public double /* @Real */variance(
            final double /* @Time */t0,
            final double /* @Real */x0,
            final double /* @Time */dt) {
        if (speed_ < Math.sqrt(Constants.QL_EPSILON))
            // algebraic limit for small speed
            return volatility_ * volatility_ * dt;
        else
            return 0.5 * volatility_ * volatility_ / speed_ * (1.0 - Math.exp(-2.0 * speed_ * dt));
    }

}