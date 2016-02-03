/*
 Copyright (C) 2008 Ueli Hofstetter

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


package org.jquantlib.testsuite.math.interpolations;

import static java.lang.Math.abs;
import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.math.interpolations.Interpolation2D;
import org.jquantlib.math.interpolations.factories.Bilinear;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.junit.Test;

public class BilinearInterpolationTest {

	private final Array x = new Array(new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 });
	private final Array y = new Array(new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 });

	private final Array x_test = new Array(new double[] { -0.5 ,0, 0.5, 1.5, 2.5, 3.5, 4.5 });
	private final Array y_test = new Array(new double[] { -0.5 ,0, 0.5, 1.5, 2.5, 3.5, 4.5 });

	private final Matrix zz;
	private final Interpolation2D interpolation2d;
	private final double tolerance;

    public BilinearInterpolationTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
        QL.info("::::: Testing use of interpolations as functors... :::::");

        // fill zz using f(x,y) = x + y;
        zz = new Matrix(x.size(), y.size());
        for (int i = 0; i < x.size(); i++)
            for (int ii = 0; ii < y.size(); ii++) {
                final double value = x.get(i) + y.get(ii);
                zz.set(i, ii, value);
            }
        interpolation2d = new Bilinear().interpolate(x, y, zz);
        interpolation2d.update();
        tolerance = 1.0e-12;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWithoutEnableExtrapolation() {
        for (int i_x = 0; i_x < x_test.size(); i_x++)
            for (int i_y = 0; i_y < y_test.size(); i_y++)
                interpolation2d.op(x_test.get(i_x), y_test.get(i_y));
    }

    @Test
    public void testEnableExtrapolation() {
        interpolation2d.enableExtrapolation();
        for (int i_x = 0; i_x < x_test.size(); i_x++)
            for (int i_y = 0; i_y < y_test.size(); i_y++) {
                final double interpolated = interpolation2d.op(x_test.get(i_x), y_test.get(i_y));
                final double expected = x_test.get(i_x) + y_test.get(i_y);
                if (abs(interpolated - expected) > tolerance)
                    fail("failed to interpolate value at x = " + x_test.get(i_x) + ", y = " + y_test.get(i_y));
            }
    }

}
