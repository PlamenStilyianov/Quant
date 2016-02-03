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
import org.jquantlib.math.distributions.CumulativeBinomialDistribution;
import org.junit.Test;

/**
 * @author Dominik Holenstein
 */

public class CumulativeBinomialDistributionTest {

    public CumulativeBinomialDistributionTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    @Test
    public void testCumulativeBinomialDistribution() {

        final double[] testvalues = {		1.52587890625E-5,
                2.593994140625E-4,
                0.00209045410156,
                0.01063537597656,
                0.03840637207031,
                0.10505676269531,
                0.22724914550781,
                0.40180969238281,
                0.59819030761719,
                0.77275085449219,
                0.89494323730469,
                0.96159362792969,
                0.98936462402344,
                0.99790954589844,
                0.99974060058594,
                0.99998474121094 };

        final double p = 0.5; 					// probability that an event occurs
        final int n = testvalues.length;			// number of trials

        final CumulativeBinomialDistribution cumbinomdist = new CumulativeBinomialDistribution(p,n);
        for (int i=0;i<testvalues.length;i++) {
            final double expected = testvalues[i];
            final double realised = cumbinomdist.op(i); // i = number of successful events
            final double tolerance = 1.0e-11;
            if (Math.abs(expected-realised)>tolerance) {
                fail("x: " + i + " expected: " + expected + " realised: " + realised);
            }
        }

    }
}

