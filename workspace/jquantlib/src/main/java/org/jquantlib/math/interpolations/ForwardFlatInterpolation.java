/*
 Copyright (C) 2008 Anand Mani
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
/*
 Copyright (C) 2004, 2008 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2001, 2002, 2003 Nicolas Di C�sar�

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

import org.jquantlib.math.interpolations.factories.ForwardFlat;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Forward-flat interpolation between discrete points
 *
 * @see ForwardFlat
 *
 * @author Anand Mani
 * @author Richard Gomes
 */
public class ForwardFlatInterpolation extends AbstractInterpolation {

    //
    // public constructors
    //

    public ForwardFlatInterpolation(final Array vx, final Array vy) {
        super.impl = new ForwardFlatInterpolationImpl(vx, vy);
        super.impl.update();
    }


    //
    // protected inner classes
    //

    private class ForwardFlatInterpolationImpl extends AbstractInterpolation.Impl {

        //
        // private fields
        //

        private final Array vp;
        private final int n;


        //
        // protected constructors
        //

        protected ForwardFlatInterpolationImpl(final Array vx, final Array vy) {
            super(vx, vy);
            this.n  = vx.size();
            this.vp = new Array(n);
        }


        //
        // overrides AbstractInterpolation.Impl
        //

        @Override
        public void update() {
            vp.set(0, 0.0);
            for (int i=1; i<vx.size(); i++) {
                final double dx = vx.get(i) - vx.get(i-1);
                vp.set(i, vp.get(i-1) + dx * vy.get(i-1));
            }
        }

        @Override
        public double op(final double x) {
            if (x >= vx.get(n-1)) {
                return vy.get(n-1);
            }
            final int i = locate(x);
            return vy.get(i);
        }

        @Override
        public double primitive(final double x) {
            final int i = locate(x);
            final double dx = x - vx.get(i);
            return vp.get(i) + dx * vy.get(i);
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
