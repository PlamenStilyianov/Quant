/*
Copyright (C) 2008 Praneet Tiwari

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
package org.jquantlib.model.shortrate.twofactormodels;

import java.util.ArrayList;

import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.methods.lattices.Lattice;
import org.jquantlib.methods.lattices.TreeLattice2D;
import org.jquantlib.methods.lattices.TrinomialTree;
import org.jquantlib.model.shortrate.ShortRateModel;
import org.jquantlib.model.shortrate.StochasticProcessArray;
import org.jquantlib.processes.StochasticProcess;
import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.time.TimeGrid;

/**
 *
 * @author Praneet Tiwari
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class TwoFactorModel extends ShortRateModel {

    // ! Class describing the dynamics of the two state variables
    /*
     * ! We assume here that the short-rate is a function of two state variables x and y. \f[ r_t = f(t, x_t, y_t) \f] of two state
     * variables \f$ x_t \f$ and \f$ y_t \f$. These stochastic processes satisfy \f[ x_t = \mu_x(t, x_t)dt + \sigma_x(t, x_t) dW_t^x
     * \f] and \f[ y_t = \mu_y(t,y_t)dt + \sigma_y(t, y_t) dW_t^y \f] where \f$ W^x \f$ and \f$ W^y \f$ are two brownian motions
     * satisfying \f[ dW^x_t dW^y_t = \rho dt \f].
     */
    public abstract class ShortRateDynamics {

        private final StochasticProcess1D xProcess_, yProcess_;
        double /* @Real */correlation_;

        public ShortRateDynamics(final StochasticProcess1D xProcess, final StochasticProcess1D yProcess,
                final double /* @Real */correlation) {
            xProcess_ = (xProcess);
            yProcess_ = (yProcess);
            correlation_ = (correlation);
        }

        public abstract double /* @Rate */shortRate(double /* @Time */t, double /* @Real */x, double /* @Real */y);

        // ! Risk-neutral dynamics of the first state variable x
        public StochasticProcess1D xProcess() {
            return xProcess_;
        }

        // ! Risk-neutral dynamics of the second state variable y
        public StochasticProcess1D yProcess() {
            return yProcess_;
        }

        // ! Correlation \f$ \rho \f$ between the two brownian motions.
        public double /* @Real */correlation() {
            return correlation_;
        }

        // ! Joint process of the two variables

        public StochasticProcess process() {
            final Matrix correlation = new Matrix(2, 2);
            correlation.set(0, 0, 1.0);
            correlation.set(1, 1, 1.0);
            correlation.set(0, 1, correlation_);
            correlation.set(1, 0, correlation_);
            final ArrayList<StochasticProcess1D> processes = new ArrayList<StochasticProcess1D>();
            processes.add(0, xProcess_);
            processes.add(1, xProcess_);
            return (new StochasticProcessArray(processes, correlation));
        }
    }

    // ! Recombining two-dimensional tree discretizing the state variable

    public class ShortRateTree extends TreeLattice2D<TrinomialTree> {

        private final ShortRateDynamics dynamics_;

        // ! Plain tree build-up from short-rate dynamics
        public ShortRateTree(final TrinomialTree tree1, final TrinomialTree tree2, final ShortRateDynamics dynamics) {
            super(tree1, tree2, dynamics.correlation());

            dynamics_ = dynamics;
        }

        @Override
        public double /* @DiscountFactor */discount(final int /* @Size */i, final int /* @Size */index) {
            final int /* @Size */modulo = tree1.size(i);
            final int /* @Size */index1 = index % modulo;
            final int /* @Size */index2 = index / modulo;

            final double /* @Real */x = tree1.underlying(i, index1);
            final double /* @Real */y = tree2.underlying(i, index2);

            final double /* @Real */r = dynamics_.shortRate(timeGrid().at(i), x, y);
            return Math.exp(-r * timeGrid().dt(i));
        }
    }

    public TwoFactorModel(final int /* @Size */nParams) {
        super(nParams);
    }

    // ! Returns the short-rate dynamics
    public abstract ShortRateDynamics dynamics();

    // ! Returns a two-dimensional trinomial tree
    @Override
    public Lattice tree(final TimeGrid grid) {

        final ShortRateDynamics dyn = dynamics();

        final TrinomialTree tree1 = new TrinomialTree(dyn.xProcess(), grid, true);
        final TrinomialTree tree2 = new TrinomialTree(dyn.yProcess(), grid, true);

        return new ShortRateTree(tree1, tree2, dyn);

    }
}
