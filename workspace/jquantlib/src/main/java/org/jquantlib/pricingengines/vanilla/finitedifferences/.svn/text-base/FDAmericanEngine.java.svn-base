/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2009 Richard Gomes

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

import java.util.List;

import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.util.Observer;

/**
 * Pricing engine for American options using finite-differences
 *
 * @category vanillaengines
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 */

// typedef FDEngineAdapter<FDAmericanCondition<FDStepConditionEngine>, OneAssetOption::engine> FDAmericanEngine;
public class FDAmericanEngine
        extends FDEngineAdapter<FDAmericanCondition<FDStepConditionEngine>, OneAssetOption.Engine>
        implements OneAssetOption.Engine {

	
	
    //
    // public constructors
    //

    public FDAmericanEngine(
            final GeneralizedBlackScholesProcess process) {
        super(FDAmericanCondition.class, OneAssetOption.Engine.class, process, 100,100, false);
        super.impl = new Impl(this);
    }

    public FDAmericanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps) {
        super(FDAmericanCondition.class, OneAssetOption.Engine.class, process, timeSteps, 100, false);
        super.impl = new Impl(this);
    }

    public FDAmericanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints) {
        super(FDAmericanCondition.class, OneAssetOption.Engine.class, process, timeSteps, gridPoints, false);
        super.impl = new Impl(this);
    }

    public FDAmericanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints,
            final boolean timeDependent) {
        super(FDAmericanCondition.class, OneAssetOption.Engine.class, process, timeSteps, gridPoints, timeDependent);
        super.impl = new Impl(this);
    }


    //
    // private inner classes
    //


    private class Impl extends OneAssetOption.EngineImpl {
        private final FDAmericanEngine engine;

        private Impl(final FDAmericanEngine engine) {
            this.engine = engine;
        }

        @Override
        public void calculate() {
            // calls FDEngineAdapter#calculate()
            engine.calculate();
        }
    }


    //
    // implements OneAssetOption.Engine
    //

    @Override
    public Arguments getArguments() {
        return super.impl.getArguments();
    }

    @Override
    public Results getResults() {
        return super.impl.getResults();
    }

    @Override
    public void reset() {
        super.impl.reset();
    }


    //
    // implements Observer
    //

//    @Override
//XXX::OBS    public void update(final Observable o, final Object arg) {
//        super.impl.update(o, arg);
//    }
    @Override
    public void update() {
        super.impl.update();
    }


    //
    // implements Observable
    //

    @Override
    public void addObserver(final Observer observer) {
        super.impl.addObserver(observer);
    }

    @Override
    public int countObservers() {
        return super.impl.countObservers();
    }

    @Override
    public void deleteObserver(final Observer observer) {
        super.impl.deleteObserver(observer);
    }

    @Override
    public void deleteObservers() {
        super.impl.deleteObservers();
    }

    @Override
    public List<Observer> getObservers() {
        return super.impl.getObservers();
    }

    @Override
    public void notifyObservers() {
        super.impl.notifyObservers();
    }

    @Override
    public void notifyObservers(final Object arg) {
        super.impl.notifyObservers(arg);
    }

}
