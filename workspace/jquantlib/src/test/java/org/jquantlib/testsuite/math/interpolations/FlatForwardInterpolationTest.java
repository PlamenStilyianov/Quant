/*
 Copyright (C) 2008 Daniel Kong

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
import static org.junit.Assert.assertFalse;

import org.jquantlib.QL;
import org.jquantlib.math.interpolations.Interpolation;
import org.jquantlib.math.interpolations.factories.ForwardFlat;
import org.jquantlib.math.matrixutilities.Array;
import org.junit.Test;

/**
 * @author Daniel Kong
 **/

public class FlatForwardInterpolationTest  {

	private final Array x = new Array( new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 });
	private final Array y = new Array( new double[] { 5.0, 4.0, 3.0, 2.0, 1.0 });
	private final Interpolation interpolation;
	private final int length;
	private final double tolerance;

	public FlatForwardInterpolationTest () {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
		interpolation = new ForwardFlat().interpolate(x, y);
	    length = x.size();
	    tolerance = 1.0e-12;
	}

	@Test
	public void checkAtOriginalPoints(){
		for(int i=0; i<length; i++){
			final double d = x.get(i);
			final double calculated = interpolation.op(d);
			final double expected = y.get(i);
			assertFalse("failed to reproduce "+i+" datum"
						+"\n expected:     "+expected
						+"\n calculated:   "+calculated
						+"\n error:        "+abs(expected-calculated),
						abs(expected-calculated) > tolerance);
		}
	}

	@Test
	public void checkAtMiddlePoints(){
		for(int i=0; i<length-1; i++){
			final double d = (x.get(i)+x.get(i+1))/2;
			final double calculated = interpolation.op(d);
			final double expected = y.get(i);

			assertFalse("failed to interpolate correctly at "+d
						+"\n expected:     "+expected
						+"\n calculated:   "+calculated
						+"\n error:        "+abs(expected-calculated),
						abs(expected-calculated) > tolerance);
		}
	}

	@Test
	public void checkOutsideOriginalRange(){
		interpolation.enableExtrapolation();
		double d = x.first() - 0.5;
		double calculated = interpolation.op(d);
		double expected = y.first();
		assertFalse("failed to extrapolate correctly at "+d
					+"\n expected:     "+expected
					+"\n calculated:   "+calculated
					+"\n error:        "+abs(expected-calculated),
					abs(expected-calculated) > tolerance);
		d= x.last()+0.5;
		calculated = interpolation.op(d);
		expected = y.last();
		assertFalse("failed to extrapolate correctly at "+d
				    +"\n expected:     "+expected
			    	+"\n calculated:   "+calculated
			    	+"\n error:        "+abs(expected-calculated),
			    	abs(expected-calculated) > tolerance);

	}

	@Test
	public void checkPrimitiveAtOriginalPoints(){
		double calculated = interpolation.primitive(x.first());
		double expected = 0.0;
		assertFalse("failed to calculate primitive at "+x.first()
				    +"\n expected:     "+expected
			    	+"\n calculated:   "+calculated
			    	+"\n error:        "+abs(expected-calculated),
			    	abs(expected-calculated) > tolerance);
		double sum = 0.0;
		for(int i=1; i<length; i++){
			sum += (x.get(i)-x.get(i-1))*y.get(i-1);
			calculated = interpolation.primitive(x.get(i));
			expected=sum;
			assertFalse("failed to calculate primitive at "+x.get(i)
			            +"\n expected:     "+expected
			            +"\n calculated:   "+calculated
			            +"\n error:        "+abs(expected-calculated),
			            abs(expected-calculated) > tolerance);
		}
	}

	@Test
	public void checkPrimitiveAtMiddlePoints(){
		double sum = 0.0;
		for(int i=0; i<length-1; i++){
			final double d = (x.get(i)+x.get(i+1))/2;
			sum += (x.get(i+1)-x.get(i))*y.get(i)/2;
			final double calculated = interpolation.primitive(d);
			final double expected=sum;
			sum += (x.get(i+1)-x.get(i))*y.get(i)/2;
			assertFalse("failed to calculate primitive at "+d
			            +"\n expected:     "+expected
			            +"\n calculated:   "+calculated
			            +"\n error:        "+abs(expected-calculated),
			            abs(expected-calculated) > tolerance);
		}
	}

}