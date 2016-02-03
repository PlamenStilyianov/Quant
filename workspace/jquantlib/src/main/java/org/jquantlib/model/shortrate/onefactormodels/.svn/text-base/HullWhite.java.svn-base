/*
Copyright (C) 2008 Praneet Tiwari
Copyright (C) 2009 Ueli Hofstetter
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

package org.jquantlib.model.shortrate.onefactormodels;

import static org.jquantlib.pricingengines.BlackFormula.blackFormula;

import org.jquantlib.QL;
import org.jquantlib.instruments.Option;
import org.jquantlib.math.Constants;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.lattices.Lattice;
import org.jquantlib.methods.lattices.TrinomialTree;
import org.jquantlib.model.NullParameter;
import org.jquantlib.model.Parameter;
import org.jquantlib.model.TermStructureFittingParameter;
import org.jquantlib.processes.OrnsteinUhlenbeckProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.TimeGrid;

/**
 * Single-factor Hull-White (extended Vasicek) model class.
 * <p>
 * This class implements the standard single-factor Hull-White model defined by
 * <p>
 * {@latex[ dr_t = (\theta(t) - \alpha r_t)dt + \sigma dW_t }
 * <p>
 * where {@latex$ \alpha } and {@latex$ \sigma } are constants.
 *
 * @note When the term structure is relinked, the r0 parameter of the underlying Vasicek model is not updated.
 *
 * @category shortrate
 *
 * @author Praneet Tiwari
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class HullWhite extends Vasicek implements TermStructureConsistentModel {

    private final TermStructureConsistentModelClass termStructureConsistentModelClass;

    private Parameter phi_;

    //
    // public constructors
    //

    public HullWhite(final Handle<YieldTermStructure> termStructure) {
        this(termStructure, 0.1, 0.01);
    }

    public HullWhite(
            final Handle<YieldTermStructure> termStructure,
            final double a) {
        this(termStructure, a, 0.01);
    }

    public HullWhite(
            final Handle<YieldTermStructure> termStructure,
            final double a,
            final double sigma) {

        super(termStructure.currentLink().forwardRate(0.0, 0.0, Compounding.Continuous, Frequency.NoFrequency).rate(),
                a, 0.0, sigma, 0.0);

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        termStructureConsistentModelClass = new TermStructureConsistentModelClass(termStructure);
        b_ = new NullParameter();
        lambda_ = new NullParameter();
        generateArguments();

        termStructureConsistentModelClass.termStructure().addObserver(this);
    }

    //
    // protected methods
    //

    @Override
    protected void generateArguments() {
        phi_ = new FittingParameter(termStructureConsistentModelClass.termStructure(), a(), sigma());
    }

    //
    // public methods
    //

    @Override
    public double discountBondOption(
            final Option.Type type,
            final double strike,
            final double /* @Time */ maturity,
            final double /* @Time */ bondMaturity) /* @ReadOnly */ {

        final double /* @Real */_a = a();
        double /* @Real */v;
        if (_a < Math.sqrt(Constants.QL_EPSILON))
            v = sigma() * B(maturity, bondMaturity) * Math.sqrt(maturity);
        else
            v = sigma() * B(maturity, bondMaturity) * Math.sqrt(0.5 * (1.0 - Math.exp(-2.0 * _a * maturity)) / _a);
        final double /* @Real */f = termStructureConsistentModelClass.termStructure().currentLink().discount(bondMaturity);
        final double /* @Real */k = termStructureConsistentModelClass.termStructure().currentLink().discount(maturity) * strike;

        return blackFormula(type, k, f, v);
    }

    /**
     *  Futures convexity bias (i.e., the difference between
     *  futures implied rate and forward rate) calculated as in
     *  <p>
     *  G. Kirikos, D. Novak, "Convexity Conundrums", Risk Magazine, March 1997.
     *
     *  @note t and T should be expressed in yearfraction using deposit day counter, F_quoted is futures' market price.
     */
    public /* @Rate */ double convexityBias(
            final double futurePrice,
            final /* @Time */ double t,
            final /* @Time */ double T,
            final double sigma,
            final double a) {

        QL.require(futurePrice >= 0.0 , "negative futures price not allowed"); // TODO: message
        QL.require(t >= 0.0 , "negative t not allowed"); // TODO: message
        QL.require(T >= t , "T must not be less than t"); // TODO: message
        QL.require(a >= 0.0 , "negative a not allowed"); // TODO: message

        final double /* @Time */deltaT = (T - t);
        final double /* @Real */tempDeltaT = (1. - Math.exp(-a * deltaT)) / a;
        final double /* @Real */halfSigmaSquare = sigma * sigma / 2.0;

        // lambda adjusts for the fact that the underlying is an interest rate
        final double /* @Real */lambda = halfSigmaSquare * (1. - Math.exp(-2.0 * a * t)) / a * tempDeltaT * tempDeltaT;

        final double /* @Real */tempT = (1.0 - Math.exp(-a * t)) / a;

        // phi is the MtM adjustment
        final double /* @Real */phi = halfSigmaSquare * tempDeltaT * tempT * tempT;

        // the adjustment
        final double /* @Real */z = lambda + phi;

        final double /* @Rate */futureRate = (100.0 - futurePrice) / 100.0;
        return (1.0 - Math.exp(-z)) * (futureRate + 1.0 / (T - t));
    }


    //
    // overrides OneFactorModel
    //

    @Override
    public Lattice tree(final TimeGrid grid) {
        final TermStructureFittingParameter phi = new TermStructureFittingParameter(termStructureConsistentModelClass.termStructure());
        // needed to activate the above constructor
        final ShortRateDynamics numericDynamics = (new Dynamics(phi, a(), sigma()));
        final TrinomialTree trinomial = new TrinomialTree(numericDynamics.process(), grid, true);
        final ShortRateTree numericTree = new OneFactorModel.ShortRateTree(trinomial, numericDynamics, grid);

        // typedef TermStructureFittingParameter::NumericalImpl NumericalImpl;
        final TermStructureFittingParameter.NumericalImpl impl = (TermStructureFittingParameter.NumericalImpl) phi.implementation();
        impl.reset();
        for (int /* @Size */i = 0; i < (grid.size() - 1); i++) {
            final double /* @Real */discountBond = termStructureConsistentModelClass.termStructure().currentLink().discount(grid.at(i + 1));
            final Array statePrices = numericTree.statePrices(i);
            final int /* @Size */size = numericTree.size(i);
            final double /* @Time */dt = numericTree.timeGrid().dt(i);
            final double /* @Real */dx = trinomial.dx(i);
            double /* @Real */x = trinomial.underlying(i, 0);
            double /* @Real */value = 0.0;
            for (int /* @Size */j = 0; j < size; j++) {
                value += statePrices.get(j) * Math.exp(-x * dt);
                x += dx;
            }
            value = Math.log(value / discountBond) / dt;
            // impl->set(grid[i], value);
            impl.set(grid.index(i), value); // ???????????????
        }
        return numericTree;
    }


    //
    // overrides Vasicek
    //

    @Override
    protected double A(/* @Time */ final double t, /* @Time */ final double T) /* @ReadOnly */ {
        final double /* @DiscountFactor */discount1 = termStructureConsistentModelClass.termStructure().currentLink().discount(t);
        final double /* @DiscountFactor */discount2 = termStructureConsistentModelClass.termStructure().currentLink().discount(T);
        final double /* @Rate */forward = termStructureConsistentModelClass.termStructure().currentLink().forwardRate(t, t,
                Compounding.Continuous, Frequency.NoFrequency).rate();
        final double /* @Real */temp = sigma() * B(t, T);
        final double /* @Real */value = B(t, T) * forward - 0.25 * temp * temp * B(0.0, 2.0 * t);
        return Math.exp(value) * discount2 / discount1;
    }


    @Override
    public ShortRateDynamics dynamics() {
        return (new Dynamics(phi_, a(), sigma()));
    }

    //
    // implements TermStructureConsistentModel
    //

    @Override
    public Handle<YieldTermStructure> termStructure() {
        return termStructureConsistentModelClass.termStructure();
    }


    //
    // static inner classes
    //

    /**
     * Analytical term-structure fitting parameter \f$ \varphi(t) \f$.
     *
     * {@latex$ \varphi(t) } is analytically defined by
     * <p>
     * {@latex[ \varphi(t) = f(t) + \frac{1}{2}[\frac{\sigma(1-e^{-at})}{a}]^2 }
     * <p>
     * where {@latex$ f(t) } is the instantaneous forward rate at {@latex$ t }.
     */
    static private class FittingParameter extends TermStructureFittingParameter {

        public FittingParameter(final Handle<YieldTermStructure> termStructure, final double a, final double sigma) {
            super(new Impl(termStructure, a, sigma));
        }


        //
        // private inner classes
        //

        static private class Impl implements Parameter.Impl {

            private final Handle<YieldTermStructure> termStructure;
            private final double a;
            private final double sigma;

            public Impl(
                    final Handle<YieldTermStructure> termStructure,
                    final double a,
                    final double sigma) {
                this.termStructure = termStructure;
                this.a = a;
                this.sigma = sigma;
            }

            @Override
            public double value(final Array params, final double t) {
                final double forwardRate = termStructure.currentLink().forwardRate(
                        t, t, Compounding.Continuous, Frequency.NoFrequency).rate();
                final double temp = sigma*(1.0 - Math.exp(-a*t))/a;
                return (forwardRate + 0.5*temp*temp);
            }
        }

    }


    /**
     * Short-rate dynamics in the Hull-White model
     * <p>
     * The short-rate is here
     * <p>
     * {@latex[ r_t = \varphi(t) + x_t }
     * <p>
     * where {@latex$ \varphi(t) } is the deterministic time-dependent parameter used for
     * term-structure fitting and {@latex$ x_t } is the state variable following an Ornstein-Uhlenbeck process.
     */
    public class Dynamics extends ShortRateDynamics {

        private final Parameter fitting_;

        public Dynamics(final Parameter  fitting, final double a, final double sigma) {
            super(new OrnsteinUhlenbeckProcess(a, sigma, /* default */0.0, /* default */0.0));
            fitting_ = (fitting);
        }

        @Override
        public double variable(/* @Time */ final double t, /* @Rate */ final double r) /* @ReadOnly */ {
            return r - fitting_.get(t);
        }

        @Override
        public double shortRate(/* @Time */ final double t, final double x) /* @ReadOnly */ {
            return x + fitting_.get(t);
        }

    }

}
