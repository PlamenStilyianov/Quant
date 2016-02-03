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

package org.jquantlib.termstructures.volatilities;

import static org.jquantlib.math.Closeness.isClose;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.Rate;
import org.jquantlib.lang.annotation.Real;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.math.Constants;


/**
 * Implements the Black equivalent volatility for the S.A.B.R. model.
 *
 * @author <Richard Gomes>
 *
 */
public class Sabr {

    /**
     * Computes the Black equivalent volatility without validating parameters
     *
     * @param strike
     * @param forward
     * @param expiryTime
     * @param alpha
     * @param beta
     * @param nu
     * @param rho
     *
     * @return Black equivalent volatility
     *
     * @see #validateSabrParameters(Real, Real, Real, Real)
     * @see #sabrVolatility(Rate, Rate, Time, Real, Real, Real, Real)
     */
    public double unsafeSabrVolatility(
            final double strike,
            final double forward,
            final double expiryTime,
            final double alpha,
            final double beta,
            final double nu,
            final double rho) {

        final double oneMinusBeta = 1.0-beta;
        final double A = Math.pow(forward*strike, oneMinusBeta);
        final double sqrtA= Math.sqrt(A);
        double logM;
        if (!isClose(forward, strike))
            logM = Math.log(forward/strike);
        else {
            final double epsilon = (forward-strike)/strike;
            logM = epsilon - .5 * epsilon * epsilon ;
        }
        final double z = (nu/alpha)*sqrtA*logM;
        final double B = 1.0-2.0*rho*z+z*z;
        final double C = oneMinusBeta*oneMinusBeta*logM*logM;
        final double tmp = (Math.sqrt(B)+z-rho)/(1.0-rho);
        final double xx = Math.log(tmp);
        final double D = sqrtA*(1.0+C/24.0+C*C/1920.0);
        final double d = 1.0 + expiryTime * (oneMinusBeta*oneMinusBeta*alpha*alpha/(24.0*A)
                + 0.25*rho*beta*nu*alpha/sqrtA
                +(2.0-3.0*rho*rho)*(nu*nu/24.0));

        double multiplier;
        // computations become precise enough if the square of z worth
        // slightly more than the precision machine (hence the m)
        final double m = 10;
        if (Math.abs(z*z)>Constants.QL_EPSILON * m)
            multiplier = z/xx;
        else {
            final double talpha = (0.5-rho*rho)/(1.0-rho);
            final double tbeta = alpha - .5;
            final double tgamma = rho/(1-rho);
            multiplier = 1.0 - beta*z + (tgamma - talpha + tbeta*tbeta*.5)*z*z;
        }
        return (alpha/D)*multiplier*d;

    }

    /**
     * checks that the parameters are valid; specifically,
     * <ol>
     * <li><code>alpha</code> > 0.0</li>
     * <li><code>beta</code> >= 0.0 && <=1.0</li>
     * <li><code>nu</code> >= 0.0</li>
     * <li><code>rho*rho</code> < 1.0 </li>
     * </ol>
     * @param alpha
     * @param beta
     * @param nu
     * @param rho
     */
    public void validateSabrParameters(
            final double alpha,
            final double beta,
            final double nu,
            final double rho) {
        //FIXME don't spent time constructing string until the error is real...
        // TODO: code review :: please verify against QL/C++ code
        QL.require(alpha>0.0 , "alpha must be positive"); // TODO: message
        QL.require(beta>=0.0 && beta<=1.0 , "beta must be in (0.0, 1.0)"); // TODO: message
        QL.require(nu>=0.0 , "nu must be non negative"); // TODO: message
        QL.require(rho*rho<1.0 , "rho square must be less than one"); // TODO: message
    }

    /**
     *
     * Computes the S.A.B.R. volatility
     * <p>
     * Checks S.A.B.R. model parameters using {@code #validateSabrParameters(Real, Real, Real, Real)}
     * <p>
     * Checks the terms and conditions;
     * <ol>
     * <li><code>strike</code> > 0.0</li>
     * <li><code>forward</code> > 0.0</li>
     * <li><code>expiryTime</code> >= 0.0</li>
     * </ol>
     *  @param strike
     * @param forward
     * @param expiryTime
     * @param alpha
     * @param beta
     * @param nu
     * @param rho
     * @return
     *
     * @see #unsafeSabrVolatility(Rate, Rate, Time, Real, Real, Real, Real)
     * @see #validateSabrParameters(Real, Real, Real, Real)
     */
    public double sabrVolatility(
            final double strike,
            final double forward,
            final double expiryTime,
            final double alpha,
            final double beta,
            final double nu,
            final double rho) {
        QL.require(strike>0.0 , "strike must be positive"); // TODO: message
        QL.require(forward>0.0 , "forward must be positive"); // TODO: message
        QL.require(expiryTime>=0.0 , "expiry time must be non-negative"); // TODO: message
        validateSabrParameters(alpha, beta, nu, rho);
        return unsafeSabrVolatility(strike, forward, expiryTime, alpha, beta, nu, rho);
    }

}

