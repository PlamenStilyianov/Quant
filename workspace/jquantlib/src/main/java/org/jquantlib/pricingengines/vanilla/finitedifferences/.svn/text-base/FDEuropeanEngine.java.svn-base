/*
 Copyright (C) 2008 Srinivas Hasti

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */
package org.jquantlib.pricingengines.vanilla.finitedifferences;

import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.math.SampledCurve;
import org.jquantlib.methods.finitedifferences.StandardFiniteDifferenceModel;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Pricing engine for European options using finite-differences
 *
 * @category vanillaengines
 *
 * @author Srinivas Hasti
 */
//TODO: class comments
//TODO: work in progress
public class FDEuropeanEngine extends OneAssetOption.EngineImpl {

    //
    // private final fields
    //

    private final FDVanillaEngine fdVanillaEngine;


    //
    // private fields
    //

    private SampledCurve prices;


    //
    // public constructors
    //

    public FDEuropeanEngine(final GeneralizedBlackScholesProcess process, final int timeSteps, final int gridPoints, final boolean timeDependent) {
        fdVanillaEngine = new FDVanillaEngine(process, timeSteps, gridPoints, timeDependent);
        prices = new SampledCurve(gridPoints);
        process.addObserver(this);
    }

    public FDEuropeanEngine(final GeneralizedBlackScholesProcess stochProcess, final int binomialSteps, final int samples) {
        this(stochProcess,binomialSteps,samples,false);
    }


    //
    // implements PricingEngine
    //

    @Override
    public void calculate() {
        fdVanillaEngine.setupArguments(arguments_);
        fdVanillaEngine.setGridLimits();
        fdVanillaEngine.initializeInitialCondition();
        fdVanillaEngine.initializeOperator();
        fdVanillaEngine.initializeBoundaryConditions();

        final StandardFiniteDifferenceModel model = new StandardFiniteDifferenceModel(fdVanillaEngine.finiteDifferenceOperator, fdVanillaEngine.bcS);

        prices = new SampledCurve(fdVanillaEngine.intrinsicValues);
        prices.setValues( model.rollback(prices.values(), fdVanillaEngine.getResidualTime(), 0, fdVanillaEngine.timeSteps) );

        final OneAssetOption.ResultsImpl r = (OneAssetOption.ResultsImpl)results_;
        r.value = prices.valueAtCenter();
        final Option.GreeksImpl greeks = r.greeks();
        greeks.delta = prices.firstDerivativeAtCenter();
        greeks.gamma = prices.secondDerivativeAtCenter();
        greeks.theta = greeks.blackScholesTheta(fdVanillaEngine.process, r.value, greeks.delta, greeks.gamma);
        // final Option.MoreGreeksImpl moreGreeks = r.moreGreeks();
        r.additionalResults().put("priceCurve", prices);
    }

}
