/*
Copyright (C) 2009 Richard Gomes

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
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb

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

package org.jquantlib.instruments;

import org.jquantlib.math.solvers1D.Brent;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;


/**
 * Helper class for one-asset implied-volatility calculation
 * <p>
 * The passed engine must be linked to the passed quote
 *
 * @see VanillaOption
 *
 * @author Richard Gomes
 */
public class ImpliedVolatilityHelper {

    //
    // public static methods
    //

    public static /* @Volatility */ double calculate(
            final Instrument  instrument,
            final PricingEngine engine,
            final SimpleQuote volQuote,
            final double targetValue,
            final double accuracy,
            final int maxEvaluations,
            /* @Volatility */ final double minVol,
            /* @Volatility */ final double maxVol) {

        instrument.setupArguments(engine.getArguments());
        engine.getArguments().validate();

        final PriceError f = new PriceError(engine, volQuote, targetValue);

        final Brent solver = new Brent();
        solver.setMaxEvaluations(maxEvaluations);
        final /*@Volatility*/ double guess = (minVol+maxVol)/2.0;
        final /*@Volatility*/ double result = solver.solve(f, accuracy, guess, minVol, maxVol);
        return result;
    }

    /**
     * The returned process is equal to the passed one, except for the volatility which
     * is flat and whose value is driven by the passed quote.
     */
    public static GeneralizedBlackScholesProcess clone(
            final GeneralizedBlackScholesProcess process,
            final SimpleQuote volQuote) {
        final Handle<? extends Quote> stateVariable = process.stateVariable();
        final Handle<YieldTermStructure> dividendYield = process.dividendYield();
        final Handle<YieldTermStructure> riskFreeRate = process.riskFreeRate();

        final Handle<BlackVolTermStructure> blackVol = process.blackVolatility();
        final Handle<BlackVolTermStructure> volatility = new Handle<BlackVolTermStructure>(
                new BlackConstantVol(
                        blackVol.currentLink().referenceDate(),
                        blackVol.currentLink().calendar(),
                        new Handle<Quote>(volQuote),
                        blackVol.currentLink().dayCounter()));

        return new GeneralizedBlackScholesProcess(stateVariable, dividendYield, riskFreeRate, volatility);
    }

}
