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
import org.jquantlib.math.distributions.BinomialDistribution;
import org.junit.Test;

/**
 * @author Dominik Holenstein
 **/

public class BinomialDistributionTest {

    public BinomialDistributionTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    @Test
    public void testBinomialDistribution() {

        // Expected values with n = 16 trials and p = 0.5
        final double[]testvalues = {	1.52587890625E-5,
                2.44140625E-4,
                0.0018310546875,
                0.008544921875,
                0.02777099609375,
                0.066650390625,
                0.1221923828125,
                0.174560546875,
                0.19638061523438,
                0.174560546875,
                0.1221923828125,
                0.066650390625,
                0.02777099609375,
                0.008544921875,
                0.0018310546875,
                2.44140625E-4,
        };

        final double p = 0.5; 		   // p = probability
        final int n = testvalues.length; // n = number of trials

        final BinomialDistribution binomdist = new BinomialDistribution(p,n);

        for(int i=0;i<n;i++){
            final int z = i;
            final double expected = testvalues[i];
            final double computed = binomdist.op(z);

            // double tolerance = (z<6 ) ? 1.0e-15: 1.0e-10;
            final double tolerance = 1.0e-15; // try to to get 1.0e-15 accuracy whenever possible

            //assertEquals(expected, computed, tolerance);
            if(computed - expected > tolerance){
                fail("expected: " +  expected + " but was: " + computed);
            }
        }
    }
}
