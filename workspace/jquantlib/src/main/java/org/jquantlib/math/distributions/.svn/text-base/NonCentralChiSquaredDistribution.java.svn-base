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

package org.jquantlib.math.distributions;

import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;


/**
 * @author Richard Gomes
 */
public class NonCentralChiSquaredDistribution implements Ops.DoubleOp {

    private static final String FAILED_TO_CONVERGE = "failed to converge";

	//
	// private fields
	//

	/** degrees of freedom */
	private final double df;

	/** non-centrality parameter */
	private final double ncp;

	private final GammaFunction gammaFunction_ = new GammaFunction();


	//
	// public constructor
	//

	public NonCentralChiSquaredDistribution(final double df, final double ncp){
		//TODO check on valid parameters
		this.df = df;
		this.ncp = ncp;
	}


	//
	// implements Ops.DoubleOp
	//

	@Override
	public double op(final double x) /* @Read-only */ {
		//C++ appears to be based on Algorithm AS 275 with perhaps one addition, see below
        if (x <= 0.0) return 0.0;

        final double errmax = 1e-12;
        final int itrmax = 10000;
        final double lam = 0.5 * ncp;

        double u = Math.exp(-lam);
        double v = u;
        final double x2 = 0.5 * x;
        final double f2 = 0.5 * df;

        double t = 0.0;
        if (f2 * Constants.QL_EPSILON > 0.125 && Math.abs(x2 - f2) < Math.sqrt(Constants.QL_EPSILON) * f2) {
            // TODO check if this part is AS 275?? or a known asymptotic
            t = Math.exp((1 - t) * (2 - t / (f2 + 1))) / Math.sqrt(2.0 * Math.PI * (f2 + 1.0));
        } else {
            t = Math.exp(f2 * Math.log(x2) - x2 - gammaFunction_.logValue(f2 + 1));
        }

        double ans = v*t;
        int n = 1;
        double f_2n = df + 2.0;
        double f_x_2n = df + 2.0 - x;

        // restructure C++ algo to avoid goto...
        while (f_x_2n <= 0.0) {
            u *= lam / n;
            v += u;
            t *= x / f_2n;
            ans += v * t;
            n++;
            f_2n += 2.0;
            f_x_2n += 2.0;
        }

        while (n <= itrmax) {
            final double bound = t * x / f_x_2n;
            if (bound > errmax) {
                u *= lam / n;
                v += u;
                t *= x / f_2n;
                ans += v * t;
                n++;
                f_2n += 2.0;
                f_x_2n += 2.0;
            } else {
                return ans;
            }
        }

        throw new ArithmeticException(FAILED_TO_CONVERGE); // TODO: message
	}

}
