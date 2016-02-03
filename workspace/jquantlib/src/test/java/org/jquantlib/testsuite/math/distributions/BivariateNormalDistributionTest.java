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
import org.jquantlib.math.distributions.BivariateNormalDistribution;
import org.junit.Test;

/**
 * @author <Richard Gomes>
 */
public class BivariateNormalDistributionTest {

	public BivariateNormalDistributionTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testBivariateAtZero() {

	    /*
	    BVN(0.0,0.0,rho) = 1/4 + arcsin(rho)/(2*M_PI)
	    "Handbook of the Normal Distribution", J.K. Patel & C.B.Read, 2nd Ed, 1996
	    */
		final double rho[] = { 0.0, 0.1, 0.2, 0.3, 0.4, 0.5,
	                         0.6, 0.7, 0.8, 0.9, 0.99999 };
	    final double x = 0.0;
	    final double y = 0.0;

	    final double tolerance = 1.0e-15;
	    for (final double element : rho) {
	        for (Integer sgn=-1; sgn < 2; sgn+=2) {
	            final BivariateNormalDistribution bvn= new BivariateNormalDistribution(sgn*element);
	            final double expected = 0.25 + Math.asin(sgn*element) / (2*Math.PI) ;
	            final double realised = bvn.op(x,y);

	            if (Math.abs(realised-expected)>=tolerance)
	            	fail(" bivariate cumulative distribution\n"
                            + "    rho: " + sgn*element + "\n"
                            + "    expected:  " + expected + "\n"
                            + "    realised:  " + realised + "\n"
                            + "    tolerance: " + tolerance);
	        }
	    }
	}


	@Test
	public void testHaugValues() {

	    final double[][] values = {
	        /* The data below are from
	           "Option pricing formulas", E.G. Haug, McGraw-Hill 1998
	           pag 193
	        */
	        {  0.0,  0.0,  0.0, 0.250000 },
	        {  0.0,  0.0, -0.5, 0.166667 },
	        {  0.0,  0.0,  0.5, 1.0/3    },
	        {  0.0, -0.5,  0.0, 0.154269 },
	        {  0.0, -0.5, -0.5, 0.081660 },
	        {  0.0, -0.5,  0.5, 0.226878 },
	        {  0.0,  0.5,  0.0, 0.345731 },
	        {  0.0,  0.5, -0.5, 0.273122 },
	        {  0.0,  0.5,  0.5, 0.418340 },

	        { -0.5,  0.0,  0.0, 0.154269 },
	        { -0.5,  0.0, -0.5, 0.081660 },
	        { -0.5,  0.0,  0.5, 0.226878 },
	        { -0.5, -0.5,  0.0, 0.095195 },
	        { -0.5, -0.5, -0.5, 0.036298 },
	        { -0.5, -0.5,  0.5, 0.163319 },
	        { -0.5,  0.5,  0.0, 0.213342 },
	        { -0.5,  0.5, -0.5, 0.145218 },
	        { -0.5,  0.5,  0.5, 0.272239 },

	        {  0.5,  0.0,  0.0, 0.345731 },
	        {  0.5,  0.0, -0.5, 0.273122 },
	        {  0.5,  0.0,  0.5, 0.418340 },
	        {  0.5, -0.5,  0.0, 0.213342 },
	        {  0.5, -0.5, -0.5, 0.145218 },
	        {  0.5, -0.5,  0.5, 0.272239 },
	        {  0.5,  0.5,  0.0, 0.478120 },
	        {  0.5,  0.5, -0.5, 0.419223 },
	        {  0.5,  0.5,  0.5, 0.546244 },

	        // known analytical values
	        {  0.0, 0.0, Math.sqrt(1/2.0), 3.0/8},

//	      {  0.0,  big,  any, 0.500000 },
	        {  0.0,   30, -1.0, 0.500000 },
	        {  0.0,   30,  0.0, 0.500000 },
	        {  0.0,   30,  1.0, 0.500000 },

//	      { big,  big,   any, 1.000000 },
	        {  30,   30,  -1.0, 1.000000 },
	        {  30,   30,   0.0, 1.000000 },
	        {  30,   30,   1.0, 1.000000 },

//	      {-big,  any,   any, 0.000000 }
	        { -30, -1.0,  -1.0, 0.000000 },
	        { -30,  0.0,  -1.0, 0.000000 },
	        { -30,  1.0,  -1.0, 0.000000 },
	        { -30, -1.0,   0.0, 0.000000 },
	        { -30,  0.0,   0.0, 0.000000 },
	        { -30,  1.0,   0.0, 0.000000 },
	        { -30, -1.0,   1.0, 0.000000 },
	        { -30,  0.0,   1.0, 0.000000 },
	        { -30,  1.0,   1.0, 0.000000 }
	    };

	    for (int i=0; i<values.length; i++) {
	    	final double a = values[i][0];
	    	final double b = values[i][1];
	    	final double rho = values[i][2];
	    	final double result = values[i][3];

	        final BivariateNormalDistribution bcd = new BivariateNormalDistribution(rho);
	        final double value = bcd.op(a, b);

	        final double tolerance = 1.0e-6;
	        if (Math.abs(value-result) >= tolerance)
	        	fail(" bivariate cumulative distribution\n"
                      + "    case: " + i+1 + "\n"
                      + "    a:    " + a + "\n"
                      + "    b:    " + b + "\n"
                      + "    rho:  " + rho + "\n"
                      + "    tabulated value:  " + result + "\n"
                      + "    result:           " + value);
	    }
	}
}
