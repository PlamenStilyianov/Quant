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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2004, 2005 StatPro Italia srl

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

import org.jquantlib.QL;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;


/**
 * 1-dimensional stochastic process
 * <p>
 * This class describes a stochastic process governed by
 * <p>{@latex[ dx_t = \mu(t, x_t)dt + \sigma(t, x_t)dW_t }
 *
 * @author Richard Gomes
 */
public abstract class StochasticProcess1D extends StochasticProcess {

    static private final String ARRAY_1D_REQUIRED = "1-D array required";

    //
    // protected fields
    //

    protected Discretization1D discretization1D;


    //
    // protected constructors
    //

    protected StochasticProcess1D() {
    	// only extended classes can instantiate
    }

    /**
     * @param discretization is an Object that <b>must</b> implement {@link Discretization} <b>and</b> {@link Discretization1D}.
     */
    protected StochasticProcess1D(final Discretization1D discretization) {
        super();
        this.discretization1D = discretization;
    }

    /**
     * Returns the initial value of the state variable
     */
    public abstract /*@Real*/ double x0();

    /**
     *
     * Returns the drift part of the equation
     * {@latex$ \mu(t, x_t) }
     */
    public abstract /*@Drift*/ double drift(final /*@Time*/ double t, final /*@Real*/ double x);

    /**
     * Returns the diffusion part of the equation, i.e.
     * {@latex$ \sigma(t, x_t) }
     */
    public abstract /*@Diffusion*/ double diffusion(final /*@Time*/ double t, final /*@Real*/ double x);

    /**
     * Returns the expectation
     * {@latex$ E(x_{t_0 + \Delta t} | x_{t_0} = x_0) }
     * of the process after a time interval {@latex$ \Delta t }
     * according to the given discretization. This method can be
     * overridden in derived classes which want to hard-code a
     * particular discretization.
     */
    public /*@Expectation*/ double expectation(final /*@Time*/ double t0, final /*@Real*/ double x0, final /*@Time*/ double dt) {
        return apply(x0, discretization1D.driftDiscretization(this, t0, x0, dt)); // XXX
    }

    /**
     * Returns the standard deviation
     * {@latex$ S(x_{t_0 + \Delta t} | x_{t_0} = x_0) }
     * of the process after a time interval {@latex$ \Delta t }
     * according to the given discretization. This method can be
     * overridden in derived classes which want to hard-code a
     * particular discretization.
     */
    public /*@StdDev*/ double stdDeviation(final /*@Time*/ double t0, final double x0, final /*@Time*/ double dt) {
        return discretization1D.diffusionDiscretization(this, t0, x0, dt); // XXX
    }

    /**
     * Returns the variance
     * {@latex$ V(x_{t_0 + \Delta t} | x_{t_0} = x_0) }
     * of the process after a time interval {@latex$ \Delta t }
     * according to the given discretization. This method can be
     * overridden in derived classes which want to hard-code a
     * particular discretization.
     */
    public /*@Variance*/ double variance(final /*@Time*/ double t0, final double x0, final /*@Time*/ double dt) {
        return discretization1D.varianceDiscretization(this, t0, x0, dt); // XXX
    }

    /**
     * Returns the asset value after a time interval {@latex$ \Delta t }
     * according to the given discretization. By default, it returns
     * {@latex[
     *     E(x_0,t_0,\Delta t) + S(x_0,t_0,\Delta t) \cdot \Delta w
     * }
     * where {@latex$ E } is the expectation and {@latex$ S } the
     * standard deviation.
     */
    public final /*@Real*/ double evolve(final /*@Time*/ double t0, final /*@Real*/ double x0, final /*@Time*/ double dt, final double dw) {
        return apply(expectation(t0,x0,dt), stdDeviation(t0,x0,dt) * dw);
    }

    /**
     * Applies a change to the asset value. By default, it
     * returns {@latex$ x + \Delta x }.
     */
    public /*@Real*/ double apply(final /*@Real*/ double x0, final /*@Real*/ double dx) {
        return x0 + dx;
    }


    //
    // implements StochasticProcess
    //

    @Override
    public final int size() {
        return 1;
    }

    @Override
    public final /*@Real*/ Array initialValues() {
        return new Array(1).fill( x0() );
    }

    @Override
    public final /*@Real*/ Array drift(final /*@Time*/ double t, /*@Real*/ final Array x) {
        QL.require(x.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        return new Array(1).fill( drift(t, x.first()) );//ZH:QL097, fill requires atleast one element
    }

    @Override
    public final /*@Diffusion*/ Matrix diffusion(final /*@Time*/ double t, /*@Real*/ final Array x) {
        QL.require(x.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        final double v = diffusion(t, x.first());
        return new Matrix(1, 1).fill(v);
    }

    @Override
    public final /*@Expectation*/ Array expectation(final /*@Time*/ double t0, final /*@Real*/ Array x0, final /*@Time*/ double dt) {
        QL.require(x0.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        return new Array(1).fill( expectation(t0, x0.first(), dt) );//ZH: not same code as QL097, guessed size 1
    }

    @Override
    public final /*@StdDev*/ Matrix stdDeviation(final /*@Time*/ double t0, final /*@Real*/ Array x0, final /*@Time*/ double dt) {
        QL.require(x0.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        final double v = stdDeviation(t0, x0.first(), dt);
        return new Matrix(1, 1).fill(v);
    }

    @Override
    public final /*@Covariance*/ Matrix covariance(final /*@Time*/ double t0, final /*@Real*/ Array x0, final /*@Time*/ double dt) {
        QL.require(x0.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        final double v = discretization1D.varianceDiscretization(this, t0, x0.first(), dt);
        return new Matrix(1, 1).fill(v);
    }

    @Override
    public final Array evolve(final /*@Time*/ double t0, final /*@Real*/ Array x0, final /*@Time*/ double dt, final Array dw) {
        QL.require(x0.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        QL.require(dw.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        return new Array(1).fill( evolve(t0, x0.first(), dt, dw.first()) );//ZH: Method different than QL097, set size 1
    }

    @Override
    public final /*@Real*/ Array apply(final /*@Real*/ Array x0, final Array dx) {
        QL.require(x0.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        QL.require(dx.size()==1 , ARRAY_1D_REQUIRED); // TODO: message
        return new Array(1).fill( apply(x0.first(), dx.first()) );//ZH: TBD review code with QL097
    }


    //
    // inner interfaces
    //

    /**
     * Discretization of a stochastic process over a given time interval
     *
     * @author Richard Gomes
     */
    public interface Discretization1D {

        /**
         * Returns the drift part of the equation, i.e. {@latex$ \mu(t, x_t) }
         */
        public /* @Drift */double driftDiscretization(
                    final StochasticProcess1D sp,
                    final/* @Time */double t0, final/* @Real */double x0, final/* @Time */double dt); // XXX

        /**
         * Returns the diffusion part of the equation, i.e. {@latex$ \sigma(t, x_t) }
         */
        public /* @Diffusion */double diffusionDiscretization(
                    final StochasticProcess1D sp,
                    final/* @Time */double t0, final/* @Real */double x0, final/* @Time */double dt); // XXX

        /**
         * Returns the variance {@latex$ V(x_{t_0 + \Delta t} | x_{t_0} = x_0) } of the process after a time interval
         * {@latex$ \Delta t } according to the given discretization. This method can be overridden in derived classes which want to
         * hard-code a particular discretization.
         */
        public /* @Variance */double varianceDiscretization(
                    final StochasticProcess1D sp,
                    final/* @Time */double t0, final/* @Real */double x0, final/* @Time */double dt); // XXX

    }

}
