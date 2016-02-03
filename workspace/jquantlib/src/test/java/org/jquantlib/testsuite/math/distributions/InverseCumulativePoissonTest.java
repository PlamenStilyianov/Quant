/*
 Copyright (C) 2008 Richard Gomes

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
import org.jquantlib.math.Closeness;
import org.jquantlib.math.distributions.InverseCumulativePoisson;
import org.junit.Test;

/**
 * @author Dominik Holenstein
 */

public class InverseCumulativePoissonTest {

    public InverseCumulativePoissonTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
    }

    @Test
    public void testInverseCumulativePoissonDistribution() {
        QL.info("running InverseCumulativePoissonDistribution test ....");
        final InverseCumulativePoisson icp = new InverseCumulativePoisson(1.0);
        final double data[] = { 0.2, 0.5, 0.9, 0.98, 0.99, 0.999, 0.9999, 0.99995,
                0.99999, 0.999999, 0.9999999, 0.99999999 };

        for (int i = 0; i < data.length; i++) {
            if (!Closeness.isClose(icp.op(data[i]), i)) {
                fail("failed to reproduce known value for x = " + data[i]
                                                                       + "\n" + "calculated: " + data[i] + "\n" + "expected: "
                                                                       + i);
            }
        }
        QL.info("... test finished.");
    }
}
