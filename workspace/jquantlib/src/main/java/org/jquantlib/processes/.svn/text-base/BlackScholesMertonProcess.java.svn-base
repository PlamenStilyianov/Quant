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

package org.jquantlib.processes;

import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;

/**
 * Merton (1973) extension to the Black-Scholes stochastic process
 * <p>
 * This class describes the stochastic process for a stock or
 * stock index paying a continuous dividend yield given by
 * {@latex[ dS(t, S) = (r(t) - q(t) - \frac{\sigma(t, S)^2}{2}) dt }
 *
 * @author Richard Gomes
 */
public class BlackScholesMertonProcess extends GeneralizedBlackScholesProcess {

    public BlackScholesMertonProcess(
            final Handle<? extends Quote> x0,
            final Handle<YieldTermStructure> dividendTS,
            final Handle<YieldTermStructure> riskFreeTS,
            final Handle<BlackVolTermStructure> blackVolTS) {
    	this(x0, dividendTS, riskFreeTS, blackVolTS, new EulerDiscretization());
    }

	public BlackScholesMertonProcess(
            final Handle<? extends Quote> x0,
            final Handle<YieldTermStructure> dividendTS,
            final Handle<YieldTermStructure> riskFreeTS,
            final Handle<BlackVolTermStructure> blackVolTS,
            final StochasticProcess1D.Discretization1D discretization) {
    	super(x0, dividendTS, riskFreeTS, blackVolTS, discretization);
    }

}
