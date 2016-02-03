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

import java.util.List;
import java.util.Vector;

import org.jquantlib.QL;
import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.instruments.Payoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.math.SampledCurve;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.finitedifferences.BoundaryCondition;
import org.jquantlib.methods.finitedifferences.NeumannBC;
import org.jquantlib.methods.finitedifferences.OperatorFactory;
import org.jquantlib.methods.finitedifferences.TridiagonalOperator;
import org.jquantlib.pricingengines.PricingEngine.Arguments;
import org.jquantlib.pricingengines.PricingEngine.Results;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.time.Date;

/**
 * Finite-differences pricing engine for BSM one asset options
 * <p>
 * The name is a misnomer as this is a base class for any finite difference scheme.  Its main job is to handle grid layout.
 *
 * @author Srinivas Hasti
 */
public class FDVanillaEngine {
    protected GeneralizedBlackScholesProcess process;
    protected /* @Size */ int timeSteps, gridPoints;
    protected boolean timeDependent;
    protected /* Real */ double requiredGridValue;
    protected Date exerciseDate;
    protected Payoff payoff;
    protected TridiagonalOperator finiteDifferenceOperator;
    protected SampledCurve intrinsicValues;
    protected List<BoundaryCondition<TridiagonalOperator>> bcS;
    // temporaries
    protected /* Real */ double sMin, center, sMax;

    //private double gridLogSpacing; //Not used
    private final static/* Real */double safetyZoneFactor = 1.1;


    //
    // public constructors
    //
    public FDVanillaEngine(final GeneralizedBlackScholesProcess process, final int timeSteps, final int gridPoints, final boolean timeDependent) {
        this.process = process;
        this.timeSteps = timeSteps;
        this.gridPoints = gridPoints;
        this.timeDependent = timeDependent;
        this.intrinsicValues = new SampledCurve(gridPoints);
        bcS = new Vector<BoundaryCondition<TridiagonalOperator>>();
    }


    //
    // public methods
    //

    public Array grid() {
        return intrinsicValues.grid();
    }


    //
    // protected methods
    //

    protected void setGridLimits() {
        setGridLimits(process.stateVariable().currentLink().value(), getResidualTime());
        ensureStrikeInGrid();
    }

    protected void setupArguments(final Arguments a) {
        final OneAssetOption.ArgumentsImpl args = (OneAssetOption.ArgumentsImpl) a;
        exerciseDate = args.exercise.lastDate();
        payoff = args.payoff;
        requiredGridValue = ((StrikedTypePayoff) (payoff)).strike();
    }

    protected void setGridLimits(/* Real */final double center, /* Time */final double t) {
        QL.require(center > 0.0 , "negative or null underlying given"); // TODO: message
        this.center = center;
        /* Size */final int newGridPoints = safeGridPoints(gridPoints, t);
        if (newGridPoints > intrinsicValues.size())
            intrinsicValues = new SampledCurve(newGridPoints);

        /* Real */final double volSqrtTime = Math.sqrt(process.blackVolatility().currentLink().blackVariance(t, center));

        // the prefactor fine tunes performance at small volatilities
        /* Real */final double prefactor = 1.0 + 0.02 / volSqrtTime;
        /* Real */final double minMaxFactor = Math.exp(4.0 * prefactor * volSqrtTime);
        sMin = center / minMaxFactor; // underlying grid min value
        sMax = center * minMaxFactor; // underlying grid max value
    }

    protected void ensureStrikeInGrid() {
        // ensure strike is included in the grid
        final StrikedTypePayoff striked_payoff = (StrikedTypePayoff) (payoff);
        if (striked_payoff == null)
            return;
        /* Real */final double requiredGridValue = striked_payoff.strike();

        if (sMin > requiredGridValue / safetyZoneFactor) {
            sMin = requiredGridValue / safetyZoneFactor;
            // enforce central placement of the underlying
            sMax = center / (sMin / center);
        }
        if (sMax < requiredGridValue * safetyZoneFactor) {
            sMax = requiredGridValue * safetyZoneFactor;
            // enforce central placement of the underlying
            sMin = center / (sMax / center);
        }
    }

    protected void initializeInitialCondition() {
        intrinsicValues.setLogGrid(sMin, sMax);
        intrinsicValues.sample(new PayoffFunction(payoff));
    }

    protected void initializeOperator() {
        finiteDifferenceOperator = OperatorFactory.getOperator(process, intrinsicValues.grid(), getResidualTime(), timeDependent);
    }

    protected void initializeBoundaryConditions() {

        bcS.add(new NeumannBC(
                intrinsicValues.value(1)- intrinsicValues.value(0),
                NeumannBC.Side.Lower));

        bcS.add(new NeumannBC(
                intrinsicValues.value(intrinsicValues.size() - 1)- intrinsicValues.value(intrinsicValues.size() - 2),
                NeumannBC.Side.Upper));
    }

    protected/* Time */double getResidualTime() {
        return process.time(exerciseDate);
    }

    // safety check to be sure we have enough grid points.
    protected/* Size */int safeGridPoints(/* Size */final int gridPoints,
            /* Time */final double residualTime) {
        final int minGridPoints = 10;
        final int minGridPointsPerYear = 2;
        return Math.max(
                gridPoints,
                residualTime > 1.0
                ? (int) ((minGridPoints + (residualTime - 1.0) * minGridPointsPerYear))
                        : minGridPoints);
    }

    protected void calculate(final Results r) {
        throw new UnsupportedOperationException();
    }

}
