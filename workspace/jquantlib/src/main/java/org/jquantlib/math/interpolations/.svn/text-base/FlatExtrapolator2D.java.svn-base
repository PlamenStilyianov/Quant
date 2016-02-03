/*
 Copyright (C) 2008 Anand Mani

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

package org.jquantlib.math.interpolations;

/**
 * This class performs a flat extrapolation backed by an existing {@link Interpolation2D}.
 * <p>
 * Unlike other classes extended from Interpolation and Interpolation2D interfaces, this class is not self enclosed but
 * it depends on another interpolation class in order to perform the extrapolation.
 *
 * @author Richard Gomes
 */
public class FlatExtrapolator2D extends AbstractInterpolation2D {

    public FlatExtrapolator2D(final Interpolation2D decoratedInterpolation) {
        super.impl_ = new FlatExtrapolator2DImpl(decoratedInterpolation);
    }

    private class FlatExtrapolator2DImpl extends AbstractInterpolation2D.Impl {

        private final Interpolation2D decoratedInterp_;

        private FlatExtrapolator2DImpl(final Interpolation2D decoratedInterpolation) {
            // intentionally calls "super()"
            this.decoratedInterp_ = decoratedInterpolation;
            calculate();
        }

//XXX
//        //
//        // public methods
//        //
//
//        public void update() {
//            decoratedInterp_.update();
//        }

        //
        // overrides AbstractInterpolation2D.Impl
        //

        @Override
        public double xMin() /* @ReadOnly */{
            return decoratedInterp_.xMin();
        }

        @Override
        public double xMax() /* @ReadOnly */{
            return decoratedInterp_.xMax();
        }

        @Override
        public double yMin() /* @ReadOnly */{
            return decoratedInterp_.yMin();
        }

        @Override
        public double yMax() /* @ReadOnly */{
            return decoratedInterp_.yMax();
        }

        @Override
        public boolean isInRange(final double x, final double y) /* @ReadOnly */{
            return decoratedInterp_.isInRange(x, y);
        }

        @Override
        public void calculate() {
            // nothing
        }

        @Override
        public double op(double x, double y) /* @ReadOnly */{
            x = bindX(x);
            y = bindY(y);
            return decoratedInterp_.op(x, y);
        }

        //
        // protected methods
        //

        @Override
        protected int locateX(final double x) /* @ReadOnly */{
            return decoratedInterp_.locateX(x);
        }

        @Override
        protected int locateY(final double y) /* @ReadOnly */{
            return decoratedInterp_.locateY(y);
        }


        //
        // private methods
        //

        private double bindX(final double x) /* @ReadOnly */{
            if (x < xMin()) {
                return xMin();
            }
            if (x > xMax()) {
                return xMax();
            }
            return x;
        }

        private double bindY(final double y) /* @ReadOnly */{
            if (y < yMin()) {
                return yMin();
            }
            if (y > yMax()) {
                return yMax();
            }
            return y;
        }

    }
}
