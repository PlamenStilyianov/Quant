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
 Copyright (C) 2004 StatPro Italia srl

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

import org.jquantlib.math.interpolations.CubicInterpolation.BoundaryCondition;
import org.jquantlib.math.interpolations.CubicInterpolation.DerivativeApprox;
import org.jquantlib.math.interpolations.factories.Bilinear;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;

/**
 * Bicubic spline interpolation between discrete points
 *
 * @see Bicubic
 *
 * @author Richard Gomes
 */
// TODO: rename to BicubicSpline
public class BicubicSplineInterpolation extends AbstractInterpolation2D {

    //
    // private constructors
    //

    /**
     * Constructor for a bilinear interpolation between discrete points
     *
     * @see Bilinear
     */
    public BicubicSplineInterpolation(final Array vx, final Array vy, final Matrix mz) {
        super.impl_ = new BicubicSplineImpl(vx, vy, mz);
    }

    //
    // private inner classes
    //

    /**
     * This class is a default implementation for {@link BilinearInterpolation} instances.
     *
     * @author Richard Gomes
     */
    private class BicubicSplineImpl extends AbstractInterpolation2D.Impl {

        private Interpolation[] splines_;

        public BicubicSplineImpl(final Array vx, final Array vy, final Matrix mz) {
            super(vx, vy, mz);
            calculate();
        }

        @Override
        public void calculate() {
            splines_ = new Interpolation[mz.rows()];
            for (int i=0; i<mz.rows(); i++) {
                splines_[i] = new CubicInterpolation(
                                vx, mz.rangeRow(i),
                                DerivativeApprox.Spline, false,
                                BoundaryCondition.SecondDerivative, 0.0,
                                BoundaryCondition.SecondDerivative, 0.0);
            }
        }

        @Override
        public double op(final double x, final double y) /* @ReadOnly */ {
            final double[] section = new double[splines_.length];
            for (int i=0; i<splines_.length; i++) {
                section[i] = splines_[i].op(x, true);
            }

            final CubicInterpolation spline = new CubicInterpolation(vy, new Array(section),
                                      DerivativeApprox.Spline, false,
                                      BoundaryCondition.SecondDerivative, 0.0,
                                      BoundaryCondition.SecondDerivative, 0.0);
            return spline.op(y, true);
        }

    }

}
