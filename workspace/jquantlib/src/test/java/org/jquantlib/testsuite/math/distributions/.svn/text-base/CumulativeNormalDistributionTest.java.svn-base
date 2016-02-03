/*
 Copyright (C) 2007 Richard Gomes

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

package org.jquantlib.testsuite.math.distributions;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.junit.Test;

/**
 * @author <Richard Gomes>
 */
public class CumulativeNormalDistributionTest {

	public CumulativeNormalDistributionTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testKnownGoodValuesFromAbramStegun() {

		final double[][] testvalues = {	{0.0, 0.5},
									{0.1, 0.539827837277029},
									{0.2, 0.579259709439103},
									{0.3, 0.617911422188953},
									{0.4, 0.655421741610324},
									{0.5, 0.691462461274013},
									{0.6, 0.725746882249927},
									{0.7, 0.758036347776927},
									{0.8, 0.788144601416604},
									{0.9, 0.815939874653241},
									{1.0, 0.841344746068543},
									{1.2, 0.884930329778292},
									{1.4, 0.919243340766229},
									{1.6, 0.945200708300442},
									{1.8, 0.964069680887074},
									{2.0, 0.977249868051821},
									{2.5, 0.993790334674224},
									{3.0, 0.998650101968370},
									{3.5, 0.9997673709},
									{4.0, 0.9999683288},
									{5.0, 0.9999997133}};


		final CumulativeNormalDistribution cnd = new CumulativeNormalDistribution();

		for (final double[] testvalue : testvalues) {
			final double z = testvalue[0];
			final double expected = testvalue[1];
			final double computed = cnd.op(z);
			final double tolerance = (Math.abs(z)<3.01) ? 1.0e-15: 1.0e-10;


			// assertEquals(expected, computed,tolerance);
			if(expected - computed > tolerance){
				fail("expected:  + " + expected + " but was " + computed);
			}

			// assertEquals(1.0, computed+ cnd.evaluate(-z),tolerance);
			if (Math.abs(1.0-(computed+cnd.op(-z)))>tolerance) {
				fail("expected: 1.0" + " but is: " + computed + cnd.op(-z));
			}
		}
	}

	@Test
	public void testExtremes(){
		double z = -40;
		// double tolerance = (Math.abs(z)<3.01) ? 1.0e-15: 1.0e-10;
		final double tolerance = 1.0e-15;

		final CumulativeNormalDistribution cnd = new CumulativeNormalDistribution();

		// assertEquals(0, cnd.evaluate(z),1.0e-15);
		if (Math.abs(0.0-(cnd.op(z)))>tolerance) {
			fail("expected: 1.0" + " but is: " + cnd.op(z));
		}

		z = -10;
		// assertEquals(0, cnd.evaluate(z),1.0e-15);
		if (Math.abs(0.0-cnd.op(z))>tolerance) {
			fail("expected: 1.0" + " but is: " + cnd.op(z));
		}

		z = 10;
		// assertEquals(1.0, cnd.evaluate(z),1.0e-15);
		if (Math.abs(1.0-(cnd.op(z)))>tolerance) {
			fail("expected: 1.0" + " but is: " + cnd.op(z));
		}

		z = 40;
		// assertEquals(1.0, cnd.evaluate(z),1.0e-15);
		if (Math.abs(1.0-(cnd.op(z)))>tolerance) {
			fail("expected: 1.0" + " but is: " + cnd.op(z));
		}
	}
}
