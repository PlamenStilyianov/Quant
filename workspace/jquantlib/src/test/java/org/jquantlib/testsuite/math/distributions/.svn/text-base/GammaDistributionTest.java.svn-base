/*
 Copyright (C) 2008 Dominik Holenstein

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
import org.jquantlib.math.distributions.GammaDistribution;
import org.junit.Test;

/**
 * @author Dominik Holenstein
 **/


public class GammaDistributionTest {

	public GammaDistributionTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	//FIXME Compare the test values to QuantLib
	@Test
	public void testGammaDisribution() {

		// for a = 1.0
		final double[][] testvalues = {	{1.0, 0.6321205487807914},
									{2.0, 0.1353352832366127},
									{3.0, 0.04978706836786395},
									{4.0, 0.018315638888734182},
									{5.0, 0.006737946999085468},
									{6.0, 0.002478752176666357},
									{7.0, 9.118819655545164E-4},
									{8.0, 3.354626279025119E-4},
									{9.0, 1.2340980408667962E-4},
									{10.0, 4.539992976248486E-5},
									{11.0, 1.6701700790245663E-5},
									{12.0, 6.144212353328207E-6},
									{13.0, 2.2603294069810534E-6},
									{14.0, 8.315287191035681E-7},
									{15.0, 3.0590232050182594E-7}};

		// for a = 0.1
		final double[][] testvalues2= {	{1.0, 0.9758726484126121},
									{2.0, 0.005673823979811235},
									{3.0, 0.001565271747114275},
									{4.0, 4.6461128723220273E-4},
									{5.0, 1.439389658467268E-4},
									{6.0, 4.587266119320674E-5},
									{7.0, 1.4917168148427797E-5},
									{8.0, 4.924807948019276E-6},
									{9.0, 1.645169609700308E-6},
									{10.0, 5.547985717901634E-7},
									{11.0, 1.8854922924246192E-7},
									{12.0, 6.449477029349438E-8},
									{13.0, 2.2182342798583913E-8},
									{14.0, 7.665444025768806E-9},
									{15.0, 2.659774281674882E-9}};

		// Test for a = 1.0 (alpha)
		double a = 1.0;
		final GammaDistribution gammDistribution = new GammaDistribution(a);
		for (final double[] testvalue : testvalues) {
			final double expected = testvalue[1];
			final double x = testvalue[0];
			final double computed = gammDistribution.op(x);
			// QL.info(computed); // for testing
			final double tolerance = 1.0e-15;
			if (Math.abs(expected-computed)>tolerance) {
				fail("x: " + x + " expected: " + expected + " realised: " + computed);
			}
		}

		// Test for a = 0.1 (alpha)
		a = 0.1;
		final GammaDistribution gammDist = new GammaDistribution(a);
		for (final double[] element : testvalues2) {
			final double expected = element[1];
			final double x = element[0];
			final double computed = gammDist.op(x);
			// QL.info(computed); // for testing
			final double tolerance = 1.0e-15;
			if (Math.abs(expected-computed)>tolerance) {
				fail("x: " + x + " expected: " + expected + " realised: " + computed);
			}
		}
	}
}

