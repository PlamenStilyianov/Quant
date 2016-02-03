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

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.math.SampledCurve;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.finitedifferences.BoundaryCondition;
import org.jquantlib.methods.finitedifferences.BoundaryConditionSet;
import org.jquantlib.methods.finitedifferences.NullCondition;
import org.jquantlib.methods.finitedifferences.StandardSystemFiniteDifferenceModel;
import org.jquantlib.methods.finitedifferences.StepCondition;
import org.jquantlib.methods.finitedifferences.StepConditionSet;
import org.jquantlib.methods.finitedifferences.TridiagonalOperator;
import org.jquantlib.pricingengines.BlackCalculator;
import org.jquantlib.pricingengines.PricingEngine.Results;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Finite-difference template to generate engines
 *
 * @author Srinivas Hasti
 */
public abstract class FDStepConditionEngine extends FDVanillaEngine {

    //
    // protected fields
    //

    protected StepCondition<Array> stepCondition;
    protected SampledCurve prices;
    protected TridiagonalOperator controlOperator;
    protected List<BoundaryCondition<TridiagonalOperator>> controlBCs;
    protected SampledCurve controlPrices;

    public FDStepConditionEngine(final GeneralizedBlackScholesProcess process,
            final int timeSteps, final int gridPoints, final boolean timeDependent) {
        super(process, timeSteps, gridPoints, timeDependent);
        this.controlBCs = new ArrayList<BoundaryCondition<TridiagonalOperator>>();
        this.controlPrices = new SampledCurve(gridPoints);
    }

    protected abstract void initializeStepCondition();

    @Override
    protected void calculate(final Results results) {
        final OneAssetOption.ResultsImpl r = (OneAssetOption.ResultsImpl)results;
        final Option.GreeksImpl greeks = r.greeks();
        // final Option.MoreGreeksImpl moreGreeks = r.moreGreeks();
        setGridLimits();
        initializeInitialCondition();
        initializeOperator();
        initializeBoundaryConditions();
        initializeStepCondition();

        final List<TridiagonalOperator> operatorSet = new ArrayList<TridiagonalOperator>();
        List<Array> arraySet = new ArrayList<Array>();
        final BoundaryConditionSet<BoundaryCondition<TridiagonalOperator>> bcSet = new BoundaryConditionSet<BoundaryCondition<TridiagonalOperator>>();
        final StepConditionSet<Array> conditionSet = new StepConditionSet<Array>();

        prices =  new SampledCurve(intrinsicValues);
        controlPrices = new SampledCurve(intrinsicValues);
        controlOperator =  new TridiagonalOperator(finiteDifferenceOperator);
        controlBCs.add(bcS.get(0));
        controlBCs.add(bcS.get(1));

        operatorSet.add(finiteDifferenceOperator);
        operatorSet.add(controlOperator);

        arraySet.add(prices.values());
        arraySet.add(controlPrices.values());

        bcSet.push_back(bcS);
        bcSet.push_back(controlBCs);

        conditionSet.push_back(stepCondition);
        conditionSet.push_back(new NullCondition<Array>());

        final StandardSystemFiniteDifferenceModel model = new StandardSystemFiniteDifferenceModel(operatorSet, bcSet);
        arraySet = model.rollback(arraySet, getResidualTime(),0.0, timeSteps, conditionSet);

        //TODO: code review: Verify use clone()
        prices.setValues(arraySet.get(0).clone());
        controlPrices.setValues(arraySet.get(1).clone());

        final StrikedTypePayoff striked_payoff = (StrikedTypePayoff) (payoff);
        QL.require(striked_payoff != null , "non-striked payoff given"); // TODO: message

        final double variance = process.blackVolatility().currentLink().blackVariance(exerciseDate, striked_payoff.strike());
        final double dividendDiscount = process.dividendYield().currentLink().discount(exerciseDate);
        final double riskFreeDiscount = process.riskFreeRate().currentLink().discount(exerciseDate);
        final double spot = process.stateVariable().currentLink().value();
        final double forwardPrice = spot * dividendDiscount / riskFreeDiscount;

        final BlackCalculator black = new BlackCalculator(striked_payoff, forwardPrice, Math.sqrt(variance), riskFreeDiscount);

        r.value = prices.valueAtCenter() - controlPrices.valueAtCenter() + black.value();
        greeks.delta = prices.firstDerivativeAtCenter() - controlPrices.firstDerivativeAtCenter() + black.delta(spot);
        greeks.gamma = prices.secondDerivativeAtCenter() - controlPrices.secondDerivativeAtCenter() + black.gamma(spot);
        r.additionalResults().put("priceCurve", prices);
    }
}
