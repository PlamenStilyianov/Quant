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
 Copyright (C) 2002, 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl

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

package org.jquantlib.math.interpolations;

import static org.jquantlib.math.Closeness.isClose;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.math.matrixutilities.Array;


/** Abstract base class for 1-D interpolations.
 * <p>
 * Classes derived from this class will provide interpolated
 * values from two sequences of equal length, representing
 * discretized values of a variable and a function of the former,
 * respectively.
 *
 * @author Richard Gomes
*/
public abstract class AbstractInterpolation implements Interpolation {

    protected Impl impl;

    @Override
    public boolean empty() /* @ReadOnly */ { return impl==null; }

    @Override
    public /*@Real*/ double op(final /*@Real*/ double x) /* @ReadOnly */ {
        return op(x, false);
    }
    @Override
    public /*@Real*/ double op(final /*@Real*/ double x, final boolean allowExtrapolation) /* @ReadOnly */ {
        checkRange(x, allowExtrapolation);
        return impl.op(x);
    }

    @Override
    public /*@Real*/ double primitive(final /*@Real*/ double x) /* @ReadOnly */ {
        return primitive(x, false);
    }
    @Override
    public /*@Real*/ double primitive(final /*@Real*/ double x, final boolean allowExtrapolation) /* @ReadOnly */ {
        checkRange(x, allowExtrapolation);
        return impl.primitive(x);
    }

    @Override
    public /*@Real*/ double derivative(final /*@Real*/ double x) /* @ReadOnly */ {
        return derivative(x, false);
    }
    @Override
    public /*@Real*/ double derivative(final /*@Real*/ double x, final boolean allowExtrapolation) /* @ReadOnly */ {
        checkRange(x, allowExtrapolation);
        return impl.derivative(x);
    }

    @Override
    public /*@Real*/ double secondDerivative(final /*@Real*/ double x) /* @ReadOnly */ {
        return secondDerivative(x, false);
    }
    @Override
    public /*@Real*/ double secondDerivative(final /*@Real*/ double x, final boolean allowExtrapolation) /* @ReadOnly */ {
        checkRange(x, allowExtrapolation);
        return impl.secondDerivative(x);
    }

    @Override
    public /*@Real*/ double xMin() /* @ReadOnly */ {
        return impl.xMin();
    }

    @Override
    public /*@Real*/ double xMax() /* @ReadOnly */ {
        return impl.xMax();
    }

    @Override
    public boolean isInRange(final /*@Real*/ double x) /* @ReadOnly */ {
        return impl.isInRange(x);
    }

    @Override
    public void update() {
        impl.update();
    }


    //
    // protected methods
    //

    /**
     * This method verifies if
     * <li> extrapolation is enabled;</li>
     * <li> requested <i>x</i> is valid</li>
     *
     * @param x
     * @param extrapolate
     *
     * @throws IllegalStateException if extrapolation is not enabled.
     * @throws IllegalArgumentException if <i>x</i> is out of range
     */
    protected final void checkRange(final double x, final boolean extrapolate) /* @ReadOnly */ {
        if (! (extrapolate || allowsExtrapolation() || impl.isInRange(x)) ) {
            final StringBuilder sb = new StringBuilder();
            sb.append("interpolation range is [");
            sb.append(xMin()).append(", ").append(xMax());
            sb.append("]: extrapolation at ");
            sb.append(x);
            sb.append(" not allowed");
            throw new IllegalArgumentException(sb.toString());
        }
    }


    //
    // implements Extrapolator
    //

    /**
     * Implements multiple inheritance via delegate pattern to an inner class
     *
     * @see Extrapolator
     */
    private final DefaultExtrapolator delegatedExtrapolator = new DefaultExtrapolator();

    @Override
    public final boolean allowsExtrapolation() {
        return delegatedExtrapolator.allowsExtrapolation();
    }

    @Override
    public void disableExtrapolation() {
        delegatedExtrapolator.disableExtrapolation();
    }

    @Override
    public void enableExtrapolation() {
        delegatedExtrapolator.enableExtrapolation();
    }


    //
    // protected inner classes
    //

    protected abstract class Impl {

        /**
         * @note Derived classes are responsible for initializing <i>vx</i> and <i>vy</i>
         */
        protected Array vx;

        /**
         * @note Derived classes are responsible for initializing <i>vx</i> and <i>vy</i>
         */
        protected Array vy;


        protected Impl(final Array vx, final Array vy) {
            this.vx = vx; // TODO: clone?
            this.vy = vy; // TODO: clone?

            QL.require(vx.size() >= 2 , "not enough points to interpolate"); // TODO: message
            QL.require(extraSafetyChecks(), "unsorted values on array X");   // TODO: message
        }

        //
        // final public methods
        //

        public final double xMin() /* @ReadOnly */ {
            return  vx.first();
        }

        public final double xMax() /* @ReadOnly */ {
            return vx.last();
        }

        public final boolean isInRange(final double x) {
            QL.require(extraSafetyChecks(), "unsorted values on array X"); // TODO: message
            final double x1 = xMin(), x2 = xMax();
            return (x >= x1 && x <= x2) || isClose(x,x1) || isClose(x,x2);
        }

        public final double op(final double x, final boolean allowExtrapolation) {
            checkRange(x, allowExtrapolation);
            return op(x);
        }

        public final double primitive(final double x, final boolean allowExtrapolation) {
            checkRange(x, allowExtrapolation);
            return primitive(x);
        }

        public final double derivative(final double x, final boolean allowExtrapolation) {
            checkRange(x, allowExtrapolation);
            return derivative(x);
        }

        public final double secondDerivative(final double x, final boolean allowExtrapolation) {
            checkRange(x, allowExtrapolation);
            return secondDerivative(x);
        }


        //
        // virtual public methods
        //

        public abstract void update();
        public abstract double op(final double x) /* @ReadOnly */;
        public abstract double primitive(final double x) /* @ReadOnly */;
        public abstract double derivative(final double x) /* @ReadOnly */;
        public abstract double secondDerivative(final double x) /* @ReadOnly */;


        //
        // protected methods
        //

        protected int locate(final double x) /* @ReadOnly */ {
            QL.require(extraSafetyChecks(), "unsorted values on array X"); // TODO: message
            if (x < vx.first())
                return 0;
            else if (x > vx.last())
                return vx.size()-2;
            else {
                //return vx.upperBound(x)-1;
                final int ub = vx.upperBound(vx.begin(), vx.end()-1, x)-1;
                return ub;
            }
        }


        //
        // private methods
        //

        private boolean extraSafetyChecks() {
            if (new Settings().isExtraSafetyChecks()) {
                for (int i=0; i<vx.size()-1; i++) {
                    if (vx.get(i) > vx.get(i+1))
                        return false;
                }
            }
            return true;
        }

    }


    protected class LogInterpolationImpl extends Impl {

        private final Array logY_;
        private final Interpolation interpolation_;

        public LogInterpolationImpl(final Array vx, final Array vy, final Interpolation.Interpolator factory) {
            super(vx, vy);
            this.logY_ = new Array(vx.size());
            this.interpolation_ = factory.interpolate(vx, logY_);
        }

        @Override
        public void update() {
            for (int i=0; i<logY_.size(); i++) {
                QL.require(vy.get(i)>0.0, "invalid value"); // TODO: message
                logY_.set(i, Math.log(vy.get(i)));
            }
            interpolation_.update();
        }

        @Override
        public double op(final double x) /* @ReadOnly */ {
            return Math.exp(interpolation_.op(x, true));
        }

        @Override
        public double primitive(final double x) /* @ReadOnly */ {
            throw new UnsupportedOperationException("LogInterpolation primitive not implemented"); // TODO: message
        }

        @Override
        public double derivative(final double x) /* @ReadOnly */ {
            return op(x)*interpolation_.derivative(x, true);
        }

        @Override
        public double secondDerivative(final double x) /* @ReadOnly */ {
            return derivative(x)*interpolation_.derivative(x, true) + op(x)*interpolation_.secondDerivative(x, true);
        }
    }

}
