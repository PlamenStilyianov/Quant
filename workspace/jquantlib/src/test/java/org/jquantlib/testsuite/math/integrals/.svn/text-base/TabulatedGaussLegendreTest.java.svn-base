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
import org.jquantlib.math.Ops;
import org.jquantlib.math.functions.Constant;
import org.jquantlib.math.functions.Cube;
import org.jquantlib.math.functions.Fourth;
import org.jquantlib.math.functions.Identity;
import org.jquantlib.math.functions.Square;
import org.jquantlib.math.integrals.TabulatedGaussLegendre;
import org.junit.Test;

/**
 * @author <Richard Gomes>
 */
public class TabulatedGaussLegendreTest {

	public TabulatedGaussLegendreTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testPolynomials() {
		checkSingleTabulated(new Constant(1.0), "f(x)=1", 2.0, 1.0e-13);
		checkSingleTabulated(new Identity(), "f(x)=x", 0.0, 1.0e-13);
		checkSingleTabulated(new Square(), "f(x)=x^2", 2.0/3.0, 1.0e-13);
		checkSingleTabulated(new Cube(), "f(x)=x^3", 0.0, 1.0e-13);
		checkSingleTabulated(new Fourth(), "f(x)=x^4", 2.0/5.0, 1.0e-13);
	}

	public void checkSingleTabulated(final Ops.DoubleOp f, final String tag, final double expected, final double tolerance) {
	    final int order[] = { 6, 7, 12, 20 };
	    final TabulatedGaussLegendre quad = new TabulatedGaussLegendre();
	    for (final int element : order) {
	        quad.setOrder(element);
	        final double realised = quad.evaluate(f);
	        if (Math.abs(realised-expected) > tolerance)
            fail(" integrating " + tag + "\n"
                        + "    order " + element + "\n"
                        + "    realised: " + realised + "\n"
                        + "    expected: " + expected);
	    }
	}
}
