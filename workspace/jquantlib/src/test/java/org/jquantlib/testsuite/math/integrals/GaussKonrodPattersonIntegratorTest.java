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
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;
import org.jquantlib.math.Ops.DoubleOp;
import org.jquantlib.math.functions.Constant;
import org.jquantlib.math.functions.Cube;
import org.jquantlib.math.functions.Fourth;
import org.jquantlib.math.functions.Identity;
import org.jquantlib.math.functions.Square;
import org.jquantlib.math.integrals.GaussKronrodPatterson;
import org.jquantlib.math.integrals.Integrator;
import org.junit.Test;

/**
 * @author Dominik Holenstein
 * @author Richard Gomes
 */
public class GaussKonrodPattersonIntegratorTest {

	public GaussKonrodPattersonIntegratorTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testPolynomials() {
		checkSingleTabulated(new Constant(1.0), "f(x)=1",   2.0,     1.0e-13);
		checkSingleTabulated(new Identity(),    "f(x)=x",   0.0,     1.0e-13);
		checkSingleTabulated(new Square(),         "f(x)=x^2", 2.0/3.0, 1.0e-13);
		checkSingleTabulated(new Cube(),        "f(x)=x^3", 0.0,     1.0e-13);
		checkSingleTabulated(new Fourth(),      "f(x)=x^4", 2.0/5.0, 1.0e-13);
	}

	private void checkSingleTabulated(final Ops.DoubleOp f, final String tag, final double expected, final double tolerance) {

		final Integrator quad = new GaussKronrodPatterson();
	    final double realised = quad.op(f,-1,1);

        if (Math.abs(realised-expected) > tolerance)
        	fail(" integrating " + tag + "\n"
                    + "    realised: " + realised + "\n"
                    + "    expected: " + expected);

	}

	@Test
	public void testExp() {
		final DoubleOp exp = new DoubleOp() {
			public double op(final double x) {
				return Math.exp(x);
			}
		};

		final Integrator quad = new GaussKronrodPatterson(0, 1.1*Constants.QL_EPSILON);

		final double realised = quad.op(exp, 0, 6);
		final double expected = Math.exp(6) - 1.0;
		final double tolerance = 1.0e-10;

		if (Math.abs(realised-expected)>tolerance) {
			fail("Expected: " + expected + " Realised: " + realised);
		}
	}

}
