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

package org.jquantlib.testsuite.math.integrals;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.math.distributions.GammaFunction;
import org.junit.Test;

/**
 * @author <Richard Gomes>
 */
public class GammaFunctionTest {

	public GammaFunctionTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testGammaFunction() {

	    final GammaFunction gfn = new GammaFunction();

	    double expected = 0.0;
	    double calculated = gfn.logValue(1);
	    if (Math.abs(calculated) > 1.0e-15)
	        fail("GammaFunction(1)\n"
	                    + "    calculated: " + calculated + "\n"
	                    + "    expected:   " + expected);

	    for (int i=2; i<9000; i++) {
	        expected  += Math.log(i);
	        calculated = gfn.logValue(i+1);
	        if (Math.abs(calculated-expected)/expected > 1.0e-9)
	            fail("GammaFunction(" + i + ")\n"
	                        + "    calculated: " + calculated + "\n"
	                        + "    expected:   " + expected + "\n"
	                        + "    rel. error: " + Math.abs(calculated-expected)/expected);
	    }
	}


	@Test
	public void testKnownValuesAbramStegun() {
		final double[][] values =	{	{1.075, -0.0388257395},
								{1.225, -0.0922078291},
								{1.5,   -0.1207822376},
								{1.975, -0.0103670060} };

		final GammaFunction gammaFunction = new GammaFunction();
		for (final double[] value : values) {
			final double x = value[0];
			final double expected = value[1];
			final double realised = gammaFunction.logValue(x);
			final double tolerance = 1.0e-10;
			if (Math.abs(expected-realised)>tolerance)
			    fail("x: " + x + " expected: " + expected + " realised: " + realised);
		}
	}
}
