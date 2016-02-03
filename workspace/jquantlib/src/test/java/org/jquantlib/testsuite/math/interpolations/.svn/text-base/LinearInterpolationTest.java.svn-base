/*
 Copyright (C) 2008 Daniel Kong, Richard Gomes

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
import org.jquantlib.math.interpolations.Interpolation;
import org.jquantlib.math.interpolations.factories.Linear;
import org.jquantlib.math.matrixutilities.Array;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Daniel Kong
 * @author Richard Gomes
 **/

public class LinearInterpolationTest {

	private static final Array x  = new Array( new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 });
	private static final Array y  = new Array( new double[] { 5.0, 4.0, 3.0, 2.0, 1.0 });
	private static final Array x2 = new Array( new double[] { -2.0, -1.0, 0.0, 1.0, 3.0, 4.0, 5.0, 6.0, 7.0 });
	private static double y2[];
	private static Interpolation interpolation;
	private static int length;
	private static double tolerance;

	public LinearInterpolationTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@BeforeClass
	public static void setUpLinearInterpolation(){
		QL.info("::::: Testing use of interpolations as functors... :::::");

		interpolation = new Linear().interpolate(x, y);
		interpolation.update();
	    length = x2.size();
	    y2 = new double[length];
	    tolerance = 1.0e-12;
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWithoutEnableExtrapolation(){
		for (int i=0; i<length; i++) {
	    	y2[i] = interpolation.op(x2.get(i));
    	}
	}

	@Test
	public void testEnableExtrapolation(){
		interpolation.enableExtrapolation();
    	for (int i=0; i<length; i++) {
    		y2[i] = interpolation.op(x2.get(i));
    	}
	    for (int i=0; i<length; i++) {
	        final double expected = 5.0-x2.get(i);
	        if (abs(y2[i]-expected) > tolerance) {
	            final StringBuilder sb = new StringBuilder();
	            sb.append("failed to reproduce ").append(i+1).append("o. expected datum");
	            sb.append("\n    expected:   ").append(expected);
	            sb.append("\n    calculated: ").append(y2[i]);
	            sb.append("\n    error:      ").append(abs(y2[i]-expected));

	            if (abs(y2[i]-expected) > tolerance) {
                    fail(sb.toString());
                }
	        }
	    }
	}

}
