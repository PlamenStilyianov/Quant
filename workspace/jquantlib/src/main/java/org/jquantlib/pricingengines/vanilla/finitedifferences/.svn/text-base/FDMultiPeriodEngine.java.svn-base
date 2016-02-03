/*
 Copyright (C) 2009 Ueli Hofstetter
 Copyright (C) 2009 Srinivas Hasti

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

/*
 Copyright (C) 2005 Joseph Wang
 Copyright (C) 2007 StatPro Italia srl

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

package org.jquantlib.pricingengines.vanilla.finitedifferences;


import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.cashflow.Event;
import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.math.SampledCurve;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.finitedifferences.NullCondition;
import org.jquantlib.methods.finitedifferences.StandardFiniteDifferenceModel;
import org.jquantlib.methods.finitedifferences.StepCondition;
import org.jquantlib.pricingengines.PricingEngine.Arguments;
import org.jquantlib.pricingengines.PricingEngine.Results;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Base engine for options with events happening at specific times
 *
 * @author Richard Gomes
 */
public abstract class FDMultiPeriodEngine extends FDVanillaEngine {


    //
    // protected fields
    //

    protected List<Event> events;
    protected List<Double> stoppingTimes;
    protected SampledCurve prices;
    protected StepCondition<Array> stepCondition;
    protected StandardFiniteDifferenceModel model;


    //
    // final protected fields
    //

    final private int timeStepPerPeriod;


    //
    // public constructors
    //

    public FDMultiPeriodEngine(final GeneralizedBlackScholesProcess process) {
        this(process, 100, 100, false);
    }

    public FDMultiPeriodEngine(final GeneralizedBlackScholesProcess process, final int gridPoints, final int timeSteps, final boolean timeDependent) {
        super(process, gridPoints, timeSteps, timeDependent);
        this.stoppingTimes = new ArrayList<Double>();
        this.timeStepPerPeriod = timeSteps;
    }


    //
    // public methods
    //

    public void setupArguments(final Arguments args, final List<Event> schedule) {
        super.setupArguments(args);
        events = schedule;
        stoppingTimes.clear();
        final int n = schedule.size();
        for (int i = 0; i<n; i++) {
            stoppingTimes.add(process.time(events.get(i).date()));
        }
    }


    //
    // abstract methods
    //

    protected abstract void executeIntermediateStep(int step);


    //
    // protected methods
    //

    protected double getDividendTime(final int i) {
        return stoppingTimes.get(i);
    }

    protected void initializeModel() {
        model = new StandardFiniteDifferenceModel(finiteDifferenceOperator, bcS);
    }

    protected void initializeStepCondition() {
        stepCondition = new NullCondition<Array>();
    }


    //
    // overrides FDVanillaEngine
    //

    @Override
    protected void setupArguments(final Arguments a) {
        super.setupArguments(a);
        final OneAssetOption.ArgumentsImpl args = (OneAssetOption.ArgumentsImpl) a;
        events.clear();
        final int n = args.exercise.size();
        for (int i=0; i<n; ++i) {
            stoppingTimes.add(process.time(args.exercise.date(i)));
        }
    }

    @Override
    public void calculate(final Results results) {
        final OneAssetOption.ResultsImpl r = (OneAssetOption.ResultsImpl) results;
        final Option.GreeksImpl greeks = r.greeks();
        // final Option.MoreGreeksImpl moreGreeks = r.moreGreeks();
        double beginDate, endDate;
        final int dateNumber = stoppingTimes.size();
        boolean lastDateIsResTime = false;
        int firstIndex = -1;
        int lastIndex = dateNumber - 1;
        boolean firstDateIsZero = false;
        double firstNonZeroDate = getResidualTime();

        final double dateTolerance = 1e-6;

        if (dateNumber > 0) {
            QL.require(getDividendTime(0) >= 0, "first date cannot be negative"); // TODO: message
            if (getDividendTime(0) < getResidualTime() * dateTolerance) {
                firstDateIsZero = true;
                firstIndex = 0;
                if (dateNumber >= 2) {
                    firstNonZeroDate = getDividendTime(1);
                }
            }
            if (Math.abs(getDividendTime(lastIndex) - getResidualTime()) < dateTolerance) {
                lastDateIsResTime = true;
                lastIndex = dateNumber - 2;
            }

            if (!firstDateIsZero) {
                firstNonZeroDate = getDividendTime(0);
            }

            if (dateNumber >= 2) {
                for (int j = 1; j < dateNumber; j++) {
                    QL.require(getDividendTime(j - 1) < getDividendTime(j) , "dates must be in strictly increasing order"); // TODO: message
                }
            }
        }

        double dt = getResidualTime() / (timeStepPerPeriod*(dateNumber+1));

        // Ensure that dt is always smaller than the first non-zero date
        if (firstNonZeroDate <= dt) {
            dt = firstNonZeroDate / 2.0;
        }

        setGridLimits();
        initializeInitialCondition();
        initializeOperator();
        initializeBoundaryConditions();
        initializeModel();
        initializeStepCondition();

        prices = intrinsicValues.clone();
        if (lastDateIsResTime) {
            executeIntermediateStep(dateNumber - 1);
        }

        int j = lastIndex;
        do {
            if (j == (dateNumber - 1)) {
                beginDate = getResidualTime();
            } else {
                beginDate = getDividendTime(j + 1);
            }

            if (j >= 0) {
                endDate = getDividendTime(j);
            } else {
                endDate = dt;
            }

            prices.setValues(model.rollback(prices.values(), beginDate, endDate, timeStepPerPeriod, stepCondition));

            if (j >= 0) {
                executeIntermediateStep(j);
            }
        } while (--j >= firstIndex);

        prices.setValues(model.rollback(prices.values(), dt, 0, 1, stepCondition));

        if (firstDateIsZero) {
            executeIntermediateStep(0);
        }

        r.value = prices.valueAtCenter();
        greeks.delta = prices.firstDerivativeAtCenter();
        greeks.gamma = prices.secondDerivativeAtCenter();
        r.additionalResults().put("priceCurve", prices);
    }

}
