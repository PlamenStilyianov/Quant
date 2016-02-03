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
import org.jquantlib.math.RegularisedIncompleteBeta;
import org.junit.Test;

/**
 * @author <Richard Gomes>
 */
public class RegularisedIncompleteBetaTest {

	public RegularisedIncompleteBetaTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testRegIncompleteBetaHartleyFitchExamples() {


		final RegularisedIncompleteBeta beta = new RegularisedIncompleteBeta();

		// FIXME Is 1.0e-6 accuracy ok?
		final double[][] values = { {30, 5, 0.7, 0.0116578},
							  {30, 5, 0.94, 0.94936},
							  {30, 5, 0.96, 0.989182},
							  {10, 16, 0.2, 0.0173319},
							  {16, 10, 0.8, 0.982668},
							  { 4,  2, 0.89, 0.903488},
							  { 4,  2, 0.42, 0.103308}};

		for (final double[] value : values) {
			final double a = value[0];
			final double b = value[1];
			final double x = value[2];
			final double expected = value[3];
			final double realised = beta.evaluate(x, a, b);
			if (Math.abs(expected-realised)>1.0e-6)
				fail("x: " + x + " expected: " + expected + " realised: " + realised);
		}
	}
}
