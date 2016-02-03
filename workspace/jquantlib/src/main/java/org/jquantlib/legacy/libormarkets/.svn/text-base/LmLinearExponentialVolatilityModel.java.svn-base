/*
 Copyright
 (C) 2009 Ueli Hofstetter

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

package org.jquantlib.legacy.libormarkets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jquantlib.math.optimization.PositiveConstraint;
import org.jquantlib.model.ConstantParameter;

//! %linear exponential volatility model
/*! This class describes a linear-exponential volatility model

 \f[
 \sigma_i(t)=(a*(T_{i}-t)+d)*e^{-b(T_{i}-t)}+c
 \f]

 References:

 Damiano Brigo, Fabio Mercurio, Massimo Morini, 2003,
 Different Covariance Parameterizations of Libor Market Model and Joint
 Caps/Swaptions Calibration,
 (<http://www.business.uts.edu.au/qfrc/conferences/qmf2001/Brigo_D.pdf>)
 */

public abstract class LmLinearExponentialVolatilityModel extends LmVolatilityModel {

    private final List<Double> fixingTimes_;

    public LmLinearExponentialVolatilityModel(final List<Double> fixingTimes, final double a, final double b, final double c, final double d) {
        super(fixingTimes.size(), 4);

        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }

        this.fixingTimes_ = fixingTimes;
        arguments_.set(0, new ConstantParameter(a, new PositiveConstraint()));
        arguments_.set(1, new ConstantParameter(b, new PositiveConstraint()));
        arguments_.set(2, new ConstantParameter(c, new PositiveConstraint()));
        arguments_.set(3, new ConstantParameter(d, new PositiveConstraint()));
    }

    public List<Double> volatility(final double t, final List list) {
        final double a = arguments_.get(0).get(0.0);
        final double b = arguments_.get(1).get(0.0);
        final double c = arguments_.get(2).get(0.0);
        final double d = arguments_.get(3).get(0.0);

        final List<Double> tmp = new ArrayList<Double>(size_);
        Collections.fill(tmp, 0.0);

        for (int i = 0; i < size_; ++i) {
            final double T = fixingTimes_.get(i);
            if (T > t) {
                tmp.set(i, (a * (T - t) + d) * Math.exp(-b * (T - t)) + c);
            }
        }
        return tmp;
    }

    public double volatility(final int i, final double t, final List list) {
        final double a = arguments_.get(0).get(0.0);
        final double b = arguments_.get(1).get(0.0);
        final double c = arguments_.get(2).get(0.0);
        final double d = arguments_.get(3).get(0.0);

        final double T = fixingTimes_.get(i);

        return (T > t) ? (a * (T - t) + d) * Math.exp(-b * (T - t)) + c : 0.0;
    }

    public double integratedVariance(final int i, final int j, final double u, final List list) {
        final double a = arguments_.get(0).get(0.0);
        final double b = arguments_.get(1).get(0.0);
        final double c = arguments_.get(2).get(0.0);
        final double d = arguments_.get(3).get(0.0);

        final double T = fixingTimes_.get(i);
        final double S = fixingTimes_.get(j);

        final double k1 = Math.exp(b * u);
        final double k2 = Math.exp(b * S);
        final double k3 = Math.exp(b * T);

        return (a * a
                * (-1 - 2 * b * b * S * T - b * (S + T) + k1 * k1 * (1 + b * (S + T - 2 * u) + 2 * b * b * (S - u) * (T - u))) + 2
                * b * b * (2 * c * d * (k2 + k3) * (k1 - 1) + d * d * (k1 * k1 - 1) + 2 * b * c * c * k2 * k3 * u) + 2
                * a
                * b
                * (d * (-1 - b * (S + T) + k1 * k1 * (1 + b * (S + T - 2 * u))) - 2 * c
                        * (k3 * (1 + b * S) + k2 * (1 + b * T) - k1 * k3 * (1 + b * (S - u)) - k1 * k2 * (1 + b * (T - u)))))
                        / (4 * b * b * b * k2 * k3);
    }

    @Override
    public void generateArguments() {
    }

}
