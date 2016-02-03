/*
 Copyright (C) 2008 Srinivas Hasti

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

/*
 Copyright (C) 2005 Joseph Wang

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

package org.jquantlib.methods.finitedifferences;

import org.jquantlib.math.LogGrid;
import org.jquantlib.math.TransformedGrid;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.time.Frequency;

/**
 * Black Scholes Merton PDE
 *
 * @author Srinivas Hasti
 */
public class PdeBSM extends PdeSecondOrderParabolic {

	private final GeneralizedBlackScholesProcess process;

	public PdeBSM(final GeneralizedBlackScholesProcess process) {
		this.process = process;
	}

	@Override
	public double diffusion(final double t, final double x) {
		  return process.diffusion(t, x);
	}

	@Override
	public double discount(double t, final double x) {
		if (Math.abs(t) < 1e-8) {
            t = 0;
        }
        return process.riskFreeRate().currentLink().forwardRate(t,t,Compounding.Continuous,Frequency.NoFrequency,true).rate();
	}

	@Override
	public double drift(final double t, final double x) {
		return process.drift(t, x);
	}

    @Override
    public TransformedGrid applyGridType(final Array grid) {
        return new LogGrid(grid);
    }



}
