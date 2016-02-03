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

package org.jquantlib.math.interpolations;

import org.jquantlib.math.interpolations.factories.BackwardFlat;
import org.jquantlib.math.matrixutilities.Array;


/**
 * Backward-flat interpolation between discrete points
 *
 * @see BackwardFlat
 *
 * @author Richard Gomes
 */
public class BackwardFlatInterpolation extends AbstractInterpolation {

    //
    // public constructors
    //

    public BackwardFlatInterpolation(final Array vx, final Array vy) {
        super.impl = new BackwardFlatInterpolationImpl(vx, vy);
        super.impl.update();
    }


    //
    // protected inner classes
    //

    private class BackwardFlatInterpolationImpl extends AbstractInterpolation.Impl {

        //
        // private fields
        //

        private final Array vp;


        //
        // protected constructors
        //

        protected BackwardFlatInterpolationImpl(final Array vx, final Array vy) {
            super(vx, vy);
            this.vp = new Array(vx.size());
        }


        //
        // overrides AbstractInterpolation.Impl
        //

        @Override
        public void update() {
            vp.set(0, 0.0);
            for (int i=1; i<vx.size(); i++) {
                final double dx = vx.get(i) - vx.get(i-1);
                vp.set(i, vp.get(i-1) + dx*vy.get(i));
            }
        }

        @Override
        public double op(final double x) {
            if (x <= vx.get(0)) {
                return vy.get(0);
            }
            final int i = locate(x);
            if (x == vx.get(i)) {
                return vy.get(i);
            } else {
                return vy.get(i+1);
            }
        }

        @Override
        public double primitive(final double x) {
            final int i = locate(x);
            final double dx = x - vx.get(i);
            return vp.get(i) + dx*vy.get(i+1);
        }

        @Override
        public double derivative(final double x) {
            return 0.0;
        }

        @Override
        public double secondDerivative(final double x) {
            return 0.0;
        }

    }

}
