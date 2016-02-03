/*
Copyright (C)
2008 Praneet Tiwari
2009 Ueli Hofstetter

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
package org.jquantlib.model.shortrate.onefactormodels;

import org.jquantlib.QL;
import org.jquantlib.instruments.Option;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.math.distributions.NonCentralChiSquaredDistribution;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.Constraint;
import org.jquantlib.math.optimization.PositiveConstraint;
import org.jquantlib.methods.lattices.Lattice;
import org.jquantlib.methods.lattices.TrinomialTree;
import org.jquantlib.model.ConstantParameter;
import org.jquantlib.model.Parameter;
import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.time.TimeGrid;

/**
 * Cox-Ingersoll-Ross model class.
 * <p>
 * This class implements the Cox-Ingersoll-Ross model defined by
 * <p>{@latex[ dr_t = k(\theta - r_t)dt + \sqrt{r_t}\sigma dW_t}
 * 
 * @bug this class was not tested enough to guarantee its functionality.
 * 
 * @category shortrate
 * 
 * @author Praneet Tiwari
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class CoxIngersollRoss extends OneFactorAffineModel {
    // private double /*@Real*/ y0_, theta_, k_, sigma_;
    // check this value, arbitrary for now

    private static final String strike_must_be_positive = "strike must be positive";
    private static final String unsupported_option_type = "unsupported option type";

    private Parameter theta_;
    private Parameter k_;
    private Parameter sigma_;
    private Parameter r0_;

    public CoxIngersollRoss() {
        this(0.05, 0.1, 0.1, 0.1);
    }

    public CoxIngersollRoss(final double /* @Rate */r0, final double /* @Real */theta, final double /* @Real */k, final double /* @Real */sigma) {
        super(4);
        theta_ = (arguments_.get(0));
        k_ = arguments_.get(1);
        sigma_ = arguments_.get(2);
        r0_ = arguments_.get(3);
        theta_ = new ConstantParameter(theta, new PositiveConstraint());
        k_ = new ConstantParameter(k, new PositiveConstraint());
        sigma_ = new ConstantParameter(sigma, new VolatilityConstraint(k, theta));
        r0_ = new ConstantParameter(r0, new PositiveConstraint());
    }

    protected double /* @Real */theta() {
        return theta_.get(0.0);
    }

    protected double /* @Real */k() {
        return k_.get(0.0);
    }

    protected double /* @Real */sigma() {
        return sigma_.get(0.0);
    }

    protected double /* @Real */x0() {
        return r0_.get(0.0);
    }


    //
    // overrides OneFactorModel
    //

    @Override
    public ShortRateDynamics dynamics() {
        return new Dynamics(theta(), k(), sigma(), x0());
    }

    @Override
    public double /* @Real */discountBondOption(
            final Option.Type type,
            final double /* @Real */strike,
            final double /* @Time */t,
            final double /* @Time */s) {
        QL.require(strike > 0.0 , strike_must_be_positive); // TODO: message
        final double /* @DiscountFactor */discountT = discountBond(0.0, t, x0());
        final double /* @DiscountFactor */discountS = discountBond(0.0, s, x0());

        if (t < Constants.QL_EPSILON) {
            switch(type) {
                case Call:
                    return Math.max(discountS - strike, 0.0);
                case Put:
                    return Math.max(strike - discountS, 0.0);
                default:
                    throw new LibraryException(unsupported_option_type); // QA:[RG]::verified
            }
        }

        final double /* @Real */sigma2 = sigma() * sigma();
        final double /* @Real */h = Math.sqrt(k() * k() + 2.0 * sigma2);
        final double /* @Real */b = B(t, s);

        final double /* @Real */rho = 2.0 * h / (sigma2 * (Math.exp(h * t) - 1.0));
        final double /* @Real */psi = (k() + h) / sigma2;

        final double /* @Real */df = 4.0 * k() * theta() / sigma2;
        final double /* @Real */ncps = 2.0 * rho * rho * x0() * Math.exp(h * t) / (rho + psi + b);
        final double /* @Real */ncpt = 2.0 * rho * rho * x0() * Math.exp(h * t) / (rho + psi);

        final NonCentralChiSquaredDistribution chis = new NonCentralChiSquaredDistribution(df, ncps);
        final NonCentralChiSquaredDistribution chit = new NonCentralChiSquaredDistribution(df, ncpt);

        final double /* @Real */z = Math.log(A(t, s) / strike) / b;
        final double /*@Real*/ call = discountS*chis.op(2.0*z*(rho+psi+b)) -
        strike*discountT*chit.op(2.0*z*(rho+psi));

        if (type == Option.Type.Call) {
            return 0.0;
        } else {
            return 1.0;
        }
    }

    @Override
    public Lattice tree(final TimeGrid grid) {
        final TrinomialTree trinomial = new TrinomialTree(dynamics().process(), grid, true);
        return new OneFactorModel.ShortRateTree(trinomial, dynamics(), grid);
    }

    @Override
    protected double /* @Real */A(final double /* @Time */t, final double /* @Time */T) {
        final double /* @Real */sigma2 = sigma() * sigma();
        final double /* @Real */h = Math.sqrt(k() * k() + 2.0 * sigma2);
        final double /* @Real */numerator = 2.0 * h * Math.exp(0.5 * (k() + h) * (T - t));
        final double /* @Real */denominator = 2.0 * h + (k() + h) * (Math.exp((T - t) * h) - 1.0);
        final double /* @Real */value = Math.log(numerator / denominator) * 2.0 * k() * theta() / sigma2;
        return Math.exp(value);
    }

    @Override
    protected double /* @Real */B(final double /* @Time */t, final double /* @Time */T) {
        final double /* @Real */h = Math.sqrt(k() * k() + 2.0 * sigma() * sigma());
        final double /* @Real */temp = Math.exp((T - t) * h) - 1.0;
        final double /* @Real */numerator = 2.0 * temp;
        final double /* @Real */denominator = 2.0 * h + (k() + h) * temp;
        final double /* @Real */value = numerator / denominator;
        return value;
    }


    //
    // protected inner classes
    //

    /**
     * Dynamics of the short-rate under the Cox-Ingersoll-Ross model
     * <p>
     * The state variable {@latex$ y_t } will here be the square-root of the short-rate.
     * It satisfies the following stochastic equation
     * {@latex[dy_t = \left[ (\frac{k\theta }{2}+\frac{\sigma^2}{8})\frac{1}{y_t}-\frac{k}{2}y_t \right]d_t+\frac{\sigma}{2}dW_{t}}.
     */
    protected class Dynamics extends ShortRateDynamics {

        public Dynamics(final double /* @Real */theta, final double /* @Real */k, final double /* @Real */sigma, final double /* @Real */x0) {
            super(new HelperProcess(theta, k, sigma, Math.sqrt(x0)));
        }

        @Override
        public double /* @Real */variable(final double /* @Time */t, final double /* @Real */r) {
            return Math.sqrt(r);
        }

        @Override
        public double /* @Real */shortRate(final double /* @Time */t, final double /* @Real */y) {
            return y * y;
        }
    }


    //
    // private inner classes
    //

    private class VolatilityConstraint extends Constraint {

        double k, theta;

        public VolatilityConstraint(final double k, final double theta) {
            this.k = k;
            this.theta = theta;
        }

        @Override
        public boolean test(final Array array) /*@ReadOnly*/ {
            final double sigma = array.first();
            if (sigma <= 0.0) {
                return false;
            }
            if (sigma * sigma >= 2.0 * k * theta) {
                return false;
            }
            return true;
        }
    }

    private class HelperProcess extends StochasticProcess1D {

        public HelperProcess(final double /* @Real */theta, final double /* @Real */k, final double /* @Real */sigma, final double /* @Real */y0) {
            this.y0_ = y0;
            this.theta_ = theta;
            this.k_ = k;
            this.sigma_ = sigma;
        }

        @Override
        public double /* @Real */x0() {
            return y0_;
        }

        @Override
        public double /* @Real */drift(final double /* @Time */t, final double /* @Real */y) {
            return (0.5 * theta_ * k_ - 0.125 * sigma_ * sigma_) / y - 0.5 * k_ * y;
        }

        @Override
        public double /* @Real */diffusion(final double /* @Time */t, final double /* @Real */y) {
            return 0.5 * sigma_;
        }

        private final double /* @Real */y0_, theta_, k_, sigma_;
    }

}
