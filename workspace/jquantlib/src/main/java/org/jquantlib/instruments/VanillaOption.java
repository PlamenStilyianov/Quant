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

/*
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl

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

import org.jquantlib.QL;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.pricingengines.AnalyticEuropeanEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDAmericanEngine;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDBermudanEngine;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.quotes.SimpleQuote;

/**
 * Vanilla option (no discrete dividends, no barriers) on a single asset
 *
 * @author Richard Gomes
 */
public class VanillaOption extends OneAssetOption {

    static private final String UNKNOWN_EXERCISE_TYPE = "unknown exercise type";


    public VanillaOption(
            final Payoff payoff,
            final Exercise exercise) {
    	super(/*process,*/ payoff, exercise/*, engine*/);
    }

    /**
     * Currently, this method returns the Black-Scholes implied volatility using analytic formulas for European options and
     * a finite-difference method for American and Bermudan options. It will give inconsistent results if the pricing was
     * performed with any other methods (such as jump-diffusion models.)
     * <p>
     * Options with a gamma that changes sign (e.g., binary options) have values that are <b>not</b> monotonic in the
     * volatility. In these cases, the calculation can fail and the result (if any) is almost meaningless. Another possible
     * source of failure is to have a target value that is not attainable with any volatility, e.g., a target value lower
     * than the intrinsic value in the case of American options.
     */
    public /*@Volatility*/ double impliedVolatility(
            final /*@Real*/ double price,
            final GeneralizedBlackScholesProcess process) /* @ReadOnly */ {
        return impliedVolatility(price, process, 1.0e-4, 100, 1.0e-7, 4.0);
    }

    /**
     * Currently, this method returns the Black-Scholes implied volatility using analytic formulas for European options and
     * a finite-difference method for American and Bermudan options. It will give inconsistent results if the pricing was
     * performed with any other methods (such as jump-diffusion models.)
     * <p>
     * Options with a gamma that changes sign (e.g., binary options) have values that are <b>not</b> monotonic in the
     * volatility. In these cases, the calculation can fail and the result (if any) is almost meaningless. Another possible
     * source of failure is to have a target value that is not attainable with any volatility, e.g., a target value lower
     * than the intrinsic value in the case of American options.
     */
    public /*@Volatility*/ double impliedVolatility(
            final /*@Real*/ double price,
            final GeneralizedBlackScholesProcess process,
            final /*@Real*/ double accuracy) /* @ReadOnly */ {
        return impliedVolatility(price, process, accuracy, 100, 1.0e-7, 4.0);
    }

    /**
     * Currently, this method returns the Black-Scholes implied volatility using analytic formulas for European options and
     * a finite-difference method for American and Bermudan options. It will give inconsistent results if the pricing was
     * performed with any other methods (such as jump-diffusion models.)
     * <p>
     * Options with a gamma that changes sign (e.g., binary options) have values that are <b>not</b> monotonic in the
     * volatility. In these cases, the calculation can fail and the result (if any) is almost meaningless. Another possible
     * source of failure is to have a target value that is not attainable with any volatility, e.g., a target value lower
     * than the intrinsic value in the case of American options.
     */
    public /*@Volatility*/ double impliedVolatility(
            final /*@Real*/ double price,
            final GeneralizedBlackScholesProcess process,
            final /*@Real*/ double accuracy,
            final /*@NonNegative*/ int maxEvaluations) /* @ReadOnly */ {
        return impliedVolatility(price, process, accuracy, maxEvaluations, 1.0e-7, 4.0);
    }

    /**
     * Currently, this method returns the Black-Scholes implied volatility using analytic formulas for European options and
     * a finite-difference method for American and Bermudan options. It will give inconsistent results if the pricing was
     * performed with any other methods (such as jump-diffusion models.)
     * <p>
     * Options with a gamma that changes sign (e.g., binary options) have values that are <b>not</b> monotonic in the
     * volatility. In these cases, the calculation can fail and the result (if any) is almost meaningless. Another possible
     * source of failure is to have a target value that is not attainable with any volatility, e.g., a target value lower
     * than the intrinsic value in the case of American options.
     */
    public /*@Volatility*/ double impliedVolatility(
            final /*@Real*/ double price,
            final GeneralizedBlackScholesProcess process,
            final /*@Real*/ double accuracy,
            final /*@NonNegative*/ int maxEvaluations,
            final /*@Volatility*/ double minVol) /* @ReadOnly */ {
        return impliedVolatility(price, process, accuracy, maxEvaluations, minVol, 4.0);
    }

    /**
     * Currently, this method returns the Black-Scholes implied volatility using analytic formulas for European options and
     * a finite-difference method for American and Bermudan options. It will give inconsistent results if the pricing was
     * performed with any other methods (such as jump-diffusion models.)
     * <p>
     * Options with a gamma that changes sign (e.g., binary options) have values that are <b>not</b> monotonic in the
     * volatility. In these cases, the calculation can fail and the result (if any) is almost meaningless. Another possible
     * source of failure is to have a target value that is not attainable with any volatility, e.g., a target value lower
     * than the intrinsic value in the case of American options.
     */
    public /*@Volatility*/ double impliedVolatility(
            final /*@Real*/ double price,
            final GeneralizedBlackScholesProcess process,
            final /*@Real*/ double accuracy,
            final /*@NonNegative*/ int maxEvaluations,
            final /*@Volatility*/ double minVol,
            final /*@Volatility*/ double maxVol) /* @ReadOnly */ {

        QL.require(!isExpired(), "option expired");
        final SimpleQuote volQuote = new SimpleQuote();
        final GeneralizedBlackScholesProcess newProcess = ImpliedVolatilityHelper.clone(process, volQuote);

        // engines are built-in for the time being
        final PricingEngine engine;
        switch (exercise.type()) {
          case European:
            engine = new AnalyticEuropeanEngine(newProcess);
            break;
          case American:
            engine = new FDAmericanEngine(newProcess);
            break;
          case Bermudan:
            engine = new FDBermudanEngine(newProcess);
            break;
          default:
            throw new LibraryException(UNKNOWN_EXERCISE_TYPE);
        }

        return ImpliedVolatilityHelper.calculate(this,
                                                 engine,
                                                 volQuote,
                                                 price,
                                                 accuracy,
                                                 maxEvaluations,
                                                 minVol, maxVol);
    }



    //
    // public interfaces
    //

    public interface Engine extends OneAssetOption.Engine { /* marking interface */ }



    /**
     * Vanilla option engine base class
     *
     * @author Richard Gomes
     */
    static public abstract class EngineImpl
            extends OneAssetOption.EngineImpl
            implements VanillaOption.Engine {

        protected EngineImpl() {
            super(new OneAssetOption.ArgumentsImpl(), new OneAssetOption.ResultsImpl());
        }

        protected EngineImpl(final VanillaOption.Arguments arguments, final VanillaOption.Results results) {
            super(arguments, results);
        }

    }


}
