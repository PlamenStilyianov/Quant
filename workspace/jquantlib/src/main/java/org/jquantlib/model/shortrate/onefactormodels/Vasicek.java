/*
Copyright (C) 2009 Praneet Tiwari

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

import static org.jquantlib.pricingengines.BlackFormula.blackFormula;

import org.jquantlib.instruments.Option;
import org.jquantlib.math.Constants;
import org.jquantlib.math.optimization.NoConstraint;
import org.jquantlib.math.optimization.PositiveConstraint;
import org.jquantlib.model.ConstantParameter;
import org.jquantlib.model.Parameter;
import org.jquantlib.processes.OrnsteinUhlenbeckProcess;

/**
 * Vasicek model class
 * <p>
 * This class implements the Vasicek model defined by \f[ dr_t = a(b - r_t)dt + \sigma dW_t , \f] where \f$ a \f$, \f$ b \f$ and
 * \f$ \sigma \f$ are constants; a risk premium \f$ \lambda \f$ can also be specified.
 *
 * @category shortrate
 *
 * @author Praneet Tiwari
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class Vasicek extends OneFactorAffineModel {

    //
    // protected fields
    //

    protected double r0_;
    protected Parameter  a_;
    protected Parameter  b_;
    protected Parameter  sigma_;
    protected Parameter  lambda_;


    //
    // public methods
    //

    public Vasicek(/* @Rate */ final double r0, final double a, final double b, final double sigma, final double lambda) {
        super(4);
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.r0_ = r0;


        // TODO: code review :: please verify against QL/C++ code :: Seems to be non-sense!

        this.a_ = arguments_.get(0);
        this.b_ = arguments_.get(1);
        this.sigma_ = arguments_.get(2);
        this.lambda_ = arguments_.get(3);

        // TODO: code review :: please verify against QL/C++ code :: Seems to be non-sense!

        this.a_ = new ConstantParameter(a, new PositiveConstraint());
        this.b_ = new ConstantParameter(b, new NoConstraint());
        this.sigma_ = new ConstantParameter(sigma, new PositiveConstraint());
        this.lambda_ = new ConstantParameter(lambda, new NoConstraint());
    }


    //
    // protected methods
    //

    protected double a() /* @ReadOnly */ {
        return a_.get(0.0);
    }

    protected double b() /* @ReadOnly */ {
        return b_.get(0.0);
    }

    protected double lambda() /* @ReadOnly */ {
        return lambda_.get(0.0);
    }

    protected double sigma() /* @ReadOnly */ {
        return sigma_.get(0.0);
    }


    //
    // implements AffineModel
    //

    @Override
    public double discountBondOption(
            final Option.Type type,
            final double strike,
            /* @Time */ final double maturity,
            /* @Time */ final double bondMaturity) /* @ReadOnly */ {
        double v;
        final double _a = a();
        if (Math.abs(maturity) < Constants.QL_EPSILON)
            v = 0.0;
        else if (_a < Math.sqrt(Constants.QL_EPSILON))
            v = sigma() * B(maturity, bondMaturity) * Math.sqrt(maturity);
        else
            v = sigma() * B(maturity, bondMaturity) * Math.sqrt(0.5 * (1.0 - Math.exp(-2.0 * _a * maturity)) / _a);

        final double /* @Real */f = discountBond(0.0, bondMaturity, r0_);
        final double /* @Real */k = discountBond(0.0, maturity, r0_) * strike;

        return blackFormula(type, k, f, v);
    }


    //
    // implements OneFactorAffineModel
    //

    @Override
    public  ShortRateDynamics dynamics() /* @ReadOnly */ {
        return new Dynamics(a(), b(), sigma(), r0_);
    }


    @Override
    protected double A(/* @Time */ final double t, /* @Time */ final double T) /* @ReadOnly */ {
        final double /* @Real */_a = a();
        if (_a < Math.sqrt(Constants.QL_EPSILON))
            return 0.0;
        else {
            final double /* @Real */sigma2 = sigma() * sigma();
            final double /* @Real */bt = B(t, T);
            return Math.exp((b() + lambda() * sigma() / _a - 0.5 * sigma2 / (_a * _a)) * (bt - (T - t)) - 0.25 * sigma2 * bt * bt
                    / _a);
        }
    }

    @Override
    protected double B(/* @Time */ final double t, /* @Time */ final double T) /* @ReadOnly */ {
        final double /* @Real */_a = a();
        if (_a < Math.sqrt(Constants.QL_EPSILON))
            return (T - t);
        else
            return (1.0 - Math.exp(-_a * (T - t))) / _a;
    }


    //
    // private inner classes
    //

    /**
     * Short-rate dynamics in the %Vasicek model
     * <p>
     * The short-rate follows an Ornstein-Uhlenbeck process with mean \f$ b \f$.
     */
    private final class Dynamics extends ShortRateDynamics {

        //
        // private fields
        //

        private final double a_;
        private final double b_;
        private final double r0_;


        //
        // public methods
        //

        public Dynamics(final double a, final double b, final double sigma, final double r0) {
            super(new OrnsteinUhlenbeckProcess(a, sigma, r0 - b, 0.0));
            this.a_  = a;
            this.b_  = b;
            this.r0_ = r0;
        }

        @Override
        public double variable(/* @Time */ final double t, /* @Rate */ final double r) /* @ReadOnly */ {
            return r - b_;
        }

        @Override
        public double shortRate(/* @Time */ final double t, final double x) /* @ReadOnly */ {
            return x + b_;
        }

    }

}
