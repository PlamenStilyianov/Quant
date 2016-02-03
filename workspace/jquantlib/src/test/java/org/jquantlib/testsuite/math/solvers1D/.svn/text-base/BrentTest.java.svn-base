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

package org.jquantlib.testsuite.math.solvers1D;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.math.Ops;
import org.jquantlib.math.solvers1D.Brent;
import org.junit.Test;

/**
 * @author Richard Gomes
 */
public class BrentTest {

    public BrentTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
    }

    @Test
    public void testInvertSquare() {

        final Ops.DoubleOp square = new Ops.DoubleOp() {

            public double op(final double x) {
                return x * x - 1;
            }

        };

        final double accuracy = 1.0e-15;
        final Brent brent = new Brent();

        double soln = brent.solve(square, accuracy, 0.01, 0, 2);

        // assertEquals(1.0, soln, accuracy);
        if (Math.abs(1.0 - soln) > accuracy) {
            fail("expected: 1.0 but was: " + (soln - accuracy));
        }

        // assertEquals(10, brent.getNumEvaluations());
        if (brent.getNumEvaluations() != 10) {
            fail("expected: 10" + " but was: " + brent.getNumEvaluations());
        }

        soln = brent.solve(square, accuracy, 0.01, 0.1);

        // assertEquals(1.0, soln,accuracy);
        if (Math.abs(1.0 - soln) > accuracy) {
            fail("expected: 1.0 but was: " + (soln - accuracy));
        }

        // assertEquals(13, brent.getNumEvaluations());
        if (brent.getNumEvaluations() != 13) {
            fail("expected: 13" + " but was: " + brent.getNumEvaluations());
        }

    }

}
