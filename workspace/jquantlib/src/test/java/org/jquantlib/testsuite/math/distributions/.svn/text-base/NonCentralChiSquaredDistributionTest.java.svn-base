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
import org.jquantlib.math.distributions.NonCentralChiSquaredDistribution;
import org.junit.Test;

/**
 * @author <Richard Gomes>
 */
public class NonCentralChiSquaredDistributionTest {

	public NonCentralChiSquaredDistributionTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testPenevRayKovELV() {

		final double[][] values = { 	{1,3,12,0.958368},
								{2,2,6, 0.775015},
								{4,4,14, 0.883645},
								{4,16,16, 0.352378},
								{10, 16, 12, 0.039595} /*typo in PenevRayKov, agree with ELV*/,
								//a boundary case of CEV model
								{3.001, 0.8, 0.67, 0.084415/* differs from ELV, Ding is failing*/},
								//{99.99, 21802, 21843.6, 0.42291} Ding cannot compute, series too long...
								};

		for (final double[] value : values) {
			final double df = value[0];
			final double ncp = value[1];
			final double x = value[2];
			final double expected = value[3];
			final NonCentralChiSquaredDistribution nccsd = new NonCentralChiSquaredDistribution(df, ncp);
			final double realised = nccsd.op(x);
			if (Math.abs(expected-realised)>1.0e-6)
				fail("Noncentral chi squared failed: df " + df
					+ " ncp " + ncp
					+ " x " + x
					+ " expected " + expected
					+ " realised " + realised);
		}
	}
}
