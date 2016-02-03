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

package org.jquantlib.testsuite.termstructures.volatilities;

import static org.junit.Assert.assertEquals;

import org.jquantlib.QL;
import org.jquantlib.termstructures.volatilities.Sabr;
import org.junit.Test;


/**
 * @author <Richard Gomes>
 */
public class SabrTest {

	public SabrTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testAgainstKnownValues() {

		double strike = 0.0398;
        final double forward = 0.0398;
        final double expiryTime = 5.0;
        final double alpha = 0.0305473;
        final double beta = 0.5;
        final double nu = 0.34;
        final double rho = -0.11;

        final Sabr sabr = new Sabr();
        double sabrVol = sabr.sabrVolatility(strike, forward, expiryTime, alpha, beta, nu, rho);
        assertEquals(0.16,sabrVol, 1.0e-6);

        strike = 0.0598;
        sabrVol = sabr.sabrVolatility(strike, forward, expiryTime, alpha, beta, nu, rho);
        assertEquals(0.15755519,sabrVol, 1.0e-6);

        strike = 0.0198;
        sabrVol = sabr.sabrVolatility(strike, forward, expiryTime, alpha, beta, nu, rho);
        assertEquals(0.2373848,sabrVol, 1.0e-6);

	}
}
