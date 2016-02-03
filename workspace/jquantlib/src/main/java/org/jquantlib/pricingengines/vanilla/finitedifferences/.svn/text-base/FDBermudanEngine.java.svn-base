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

import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.instruments.Option;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.finitedifferences.NullCondition;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Finite-difference Bermudan engine
 *
 * @author Richard Gomes
 */
public class FDBermudanEngine extends OneAssetOption.EngineImpl {

    //
    // private fields
    //

    private double extraTermInBermuda; // TODO: code review
    private final FDMultiPeriodEngine fdVanillaEngine;


    //
    // public constructors
    //

    public FDBermudanEngine(
            final GeneralizedBlackScholesProcess process) {
        this(process, 100,100, false);
    }

    public FDBermudanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps) {
        this(process, timeSteps, 100, false);
    }

    public FDBermudanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints) {
        this(process, timeSteps, gridPoints, false);
    }

    public FDBermudanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints,
            final boolean timeDependent) {
        fdVanillaEngine = new FDBermudianMPEngine(process, timeSteps, gridPoints, timeDependent);
    }


    //
    // private methods
    //

    // TODO: verify how this method is called
    private void initializeStepCondition() {
        fdVanillaEngine.stepCondition = new NullCondition<Array>();
    }

    // TODO: verify how this method is called
    private void executeIntermediateStep(final int step) {
        final int size = fdVanillaEngine.intrinsicValues.size();
        for (int j = 0; j < size; j++) {
            fdVanillaEngine.prices.values().set(j, Math.max(fdVanillaEngine.prices.value(j), fdVanillaEngine.intrinsicValues.value(j)));
        }
    }


    //
    // implements PricingEngine
    //

    @Override
    public void calculate() {
        final Option.ArgumentsImpl a = (Option.ArgumentsImpl)arguments_;
        fdVanillaEngine.setupArguments(a);
        fdVanillaEngine.calculate(results_);
    }


    //
    // private inner classes
    //

    private static class FDBermudianMPEngine extends FDMultiPeriodEngine {

        public FDBermudianMPEngine(final GeneralizedBlackScholesProcess process, final int timeSteps, final int gridPoints, final boolean timeDependent) {
            super(process, timeSteps, gridPoints, timeDependent);
        }


        //
        // overrides FDMultiPeriodEngine
        //

        @Override
        protected void executeIntermediateStep(final int step) {
            final int size = intrinsicValues.size();
            for (int j = 0; j < size; j++) {
                prices.values().set(j, Math.max(prices.value(j), intrinsicValues.value(j)));
            }
        }
    }

}