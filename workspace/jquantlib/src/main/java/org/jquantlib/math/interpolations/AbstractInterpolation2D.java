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
 Copyright (C) 2002, 2003, 2006 Ferdinando Ametrano
 Copyright (C) 2004, 2005, 2006, 2007 StatPro Italia srl

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
import org.jquantlib.math.matrixutilities.Matrix;

/**
 * Base class for 2-D interpolations.
 * <p>
 * Classes derived from this class will provide interpolated
 * values from two sequences of length {@latex$ N } and {@latex$ M },
 * representing the discretized values of the {@latex$ x } and {@latex$ y}
 * variables, and a {@latex$ N \times M } matrix representing
 * the tabulated function values.
 *
 * @author Richard Gomes
 */
public class AbstractInterpolation2D implements Interpolation2D {

    protected Impl impl_;

    @Override
    public boolean empty() /* @ReadOnly */ { return impl_==null; }

    @Override
    public /*@Real*/ double op(final /*@Real*/ double x, final /*@Real*/ double y) /* @ReadOnly */ {
        return op(x, y, false);
    }
    @Override
    public /*@Real*/ double op(final /*@Real*/ double x, final /*@Real*/ double y, final boolean allowExtrapolation) /* @ReadOnly */ {
        checkRange(x, y, allowExtrapolation);
        return impl_.op(x, y);
    }

    @Override
    public /*@Real*/ double xMin() /* @ReadOnly */ {
        return impl_.xMin();
    }

    @Override
    public /*@Real*/ double xMax() /* @ReadOnly */ {
        return impl_.xMax();
    }

    @Override
    public /*@Real*/ double yMin() /* @ReadOnly */ {
        return impl_.yMin();
    }

    @Override
    public /*@Real*/ double yMax() /* @ReadOnly */ {
        return impl_.yMax();
    }

    @Override
    public int locateX(final double x) {
        return impl_.locateX(x);
    }

    @Override
    public int locateY(final double y) {
        return impl_.locateY(y);
    }

    @Override
    public boolean isInRange(final double x, final double y) /*@ReadOnly*/ {
        return impl_.isInRange(x, y);
    }

    @Override
    public void update() {
        impl_.calculate();
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
    protected final void checkRange(final double x, final double y, final boolean extrapolate) /* @ReadOnly */{
        if (!(extrapolate || allowsExtrapolation() || isInRange(x, y))) {
            final StringBuilder sb = new StringBuilder();
            sb.append("interpolation range is [");
            sb.append(xMin()).append(", ").append(xMax());
            sb.append("] x [");
            sb.append(yMin()).append(", ").append(yMax());
            sb.append("]: extrapolation at (");
            sb.append(x).append(",").append(y);
            sb.append(") not allowed");
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
         * @note Derived classes are responsible for initializing <i>vx</i>, <i>vy</i> and eventually <i>mz</i>
         */
        protected Array vx;

        /**
         * @note Derived classes are responsible for initializing <i>vx</i>, <i>vy</i> and eventually <i>mz</i>
         */
        protected Array vy;

        /**
         * @note Derived classes are responsible for initializing <i>vx</i>, <i>vy</i> and eventually <i>mz</i>
         */
        protected Matrix mz;


        //
        // protected constructors
        //

        protected Impl() {
            // nothing
        }

        protected Impl(final Array vx, final Array vy, final Matrix mz) {
            this.vx = vx; // TODO: clone?
            this.vy = vy; // TODO: clone?
            this.mz = mz; // TODO: clone?

            QL.require(vx.size() >= 2 && vy.size() >= 2, "not enough points to interpolate"); // TODO: message
            for (int i = 0; i < vx.size() - 1; i++) {
                QL.require(vx.get(i) <= vx.get(i + 1), "unsorted values on array X"); // TODO: message
                QL.require(vy.get(i) <= vy.get(i + 1), "unsorted values on array Y"); // TODO: message
            }
        }

        //
        // final public methods
        //

        public double xMin() /* @ReadOnly */ {
            return  vx.first();
        }

        public double xMax() /* @ReadOnly */ {
            return vx.last();
        }

        public double yMin() /* @ReadOnly */ {
            return  vy.first();
        }

        public double yMax() /* @ReadOnly */ {
            return vy.last();
        }

        public double op(final double x, final double y, final boolean allowExtrapolation) {
            checkRange(x, y, allowExtrapolation);
            return op(x, y);
        }

        public boolean isInRange(final double x, final double y) /*@ReadOnly*/ {
            QL.require(extraSafetyChecksX(), "unsorted values on array X"); // TODO: message
            final double x1 = xMin(), x2 = xMax();
            final boolean xIsInrange = (x >= x1 && x <= x2) || isClose(x, x1) || isClose(x, x2);
            if (!xIsInrange)
                return false;

            QL.require(extraSafetyChecksY(), "unsorted values on array Y"); // TODO: message
            final double y1 = yMin(), y2 = yMax();
            return (y >= y1 && y <= y2) || isClose(y, y1) || isClose(y, y2);
        }


        //
        // virtual public methods
        //

        public abstract double op(final double x, final double y) /* @ReadOnly */;
        public abstract void calculate();


        //
        // protected methods
        //

        protected int locateX(final double x) /* @ReadOnly */{
            QL.require(extraSafetyChecksX(), "unsorted values on array X"); // TODO: message
            if (x <= vx.first())
                return 0;
            else if (x > vx.last())
                return vx.size() - 2;
            else
                return vx.upperBound(x) - 1;
        }

        protected int locateY(final double y) /* @ReadOnly */{
            QL.require(extraSafetyChecksY(), "unsorted values on array Y"); // TODO: message
            if (y <= vy.first())
                return 0;
            else if (y > vy.last())
                return vy.size() - 2;
            else
                return vy.upperBound(y) - 1;
        }


        //
        // private methods
        //

        private boolean extraSafetyChecksX() {
            if (new Settings().isExtraSafetyChecks()) {
                for (int i = 0; i < vx.size() - 1; i++) {
                    if (vx.get(i) > vx.get(i + 1))
                        return false;
                }
            }
            return true;
        }

        private boolean extraSafetyChecksY() {
            if (new Settings().isExtraSafetyChecks()) {
                for (int i = 0; i < vy.size() - 1; i++) {
                    if (vy.get(i) > vy.get(i + 1))
                        return false;
                }
            }
            return true;
        }

    }

}
