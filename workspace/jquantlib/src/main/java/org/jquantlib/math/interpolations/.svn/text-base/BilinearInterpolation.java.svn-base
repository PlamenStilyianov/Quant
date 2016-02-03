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

import org.jquantlib.math.interpolations.factories.Bilinear;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;


/**
 * Bilinear interpolation between discrete points
 *
 * @see Bilinear
 *
 * @author Richard Gomes
 */
public class BilinearInterpolation extends AbstractInterpolation2D {

    //
    // private constructors
    //

    /**
     * Constructor for a bilinear interpolation between discrete points
     *
     * @see Bilinear
     */
    public BilinearInterpolation(final Array vx, final Array vy, final Matrix mz) {
        super.impl_ = new BilinearInterpolationImpl(vx, vy, mz);
    }

	//
    // private inner classes
    //

    /**
	 * This class is a default implementation for {@link BilinearInterpolation} instances.
	 *
	 * @author Richard Gomes
	 */
	private class BilinearInterpolationImpl extends AbstractInterpolation2D.Impl {

		public BilinearInterpolationImpl(final Array vx, final Array vy, final Matrix mz) {
		    super(vx, vy, mz);
		    calculate();
		}

		@Override
		public void calculate() {
		    // nothing
		}

	    @Override
	    public double op(final double x, final double y) /* @ReadOnly */ {
	        final int i = locateX(x);
	        final int j = locateY(y);

	        final double z1 = mz.get(j, i);
	        final double z2 = mz.get(j, i+1);
	        final double z3 = mz.get(j+1, i);
	        final double z4 = mz.get(j+1, i+1);

	        final double t = (x - vx.get(i)) / (vx.get(i+1) - vx.get(i));
	        final double u = (y - vy.get(j)) / (vy.get(j+1) - vy.get(j));

	        return (1.0 - t) * (1.0 - u) * z1 + t * (1.0 - u) * z2 + (1.0 - t) * u * z3 + t * u * z4;
	    }

	}

}
