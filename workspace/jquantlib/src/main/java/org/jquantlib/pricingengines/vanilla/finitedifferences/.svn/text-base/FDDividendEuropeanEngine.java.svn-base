/*
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

import org.jquantlib.instruments.DividendVanillaOption;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Finite-differences pricing engine for dividend European options
 *
 * @test the correctness of the returned greeks is tested by reproducing numerical derivatives.
 * @test the invariance of the results upon addition of null dividends is tested.
 *
 * @category vanillaengines
 *
 * @author Richard Gomes
 */

/*
    this:: typedef FDEngineAdapter<FDDividendEngine, DividendVanillaOption::engine> FDDividendEuropeanEngine;

    TODO:: typedef FDEngineAdapter<FDDividendEngineMerton73, DividendVanillaOption::engine> FDDividendEuropeanEngineMerton73;

    TODO:: typedef FDEngineAdapter<FDDividendEngineShiftScale, DividendVanillaOption::engine> FDDividendEuropeanEngineShiftScale;
*/

//typedef FDEngineAdapter<FDDividendEngine, DividendVanillaOption::engine> FDDividendEuropeanEngine;
public class FDDividendEuropeanEngine
        extends FDEngineAdapter<FDDividendEngine, DividendVanillaOption.Engine>
        implements DividendVanillaOption.Engine {

    //
    // public constructors
    //

    public FDDividendEuropeanEngine(final GeneralizedBlackScholesProcess process) {
        this(process, 100, 100, false);
    }

    public FDDividendEuropeanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps) {
        this(process, timeSteps, 100, false);
    }

    public FDDividendEuropeanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints) {
        this(process, timeSteps, gridPoints, false);
    }

    public FDDividendEuropeanEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints,
            final boolean timeDependent) {
        super(FDDividendEngine.class, DividendVanillaOption.Engine.class, process, timeSteps, gridPoints, timeDependent);
        super.impl = new Impl(this);
    }


    //
    // private inner classes
    //

    private class Impl extends DividendVanillaOption.EngineImpl {

        private final FDDividendEuropeanEngine engine;

        private Impl(final FDDividendEuropeanEngine engine) {
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


//    @Override
//XXX::OBS    public void update(final Observable o, final Object arg) {
//        super.impl.update(o, arg);
//    }


//    //
//    // implements Observer
//    //
//
//    @Override
//    public void update() {
//      if (impl==null)
//          throw new LibraryException(PRICING_ENGINE_NOT_SET);
//      impl.update();
//      //XXX  super.impl.update();
//    }
//
//
//    //
//    // implements Observable
//    //
//
//    @Override
//    public void addObserver(final Observer observer) {
//        super.impl.addObserver(observer);
//    }
//
//    @Override
//    public int countObservers() {
//        return super.impl.countObservers();
//    }
//
//    @Override
//    public void deleteObserver(final Observer observer) {
//        super.impl.deleteObserver(observer);
//    }
//
//    @Override
//    public void deleteObservers() {
//        super.impl.deleteObservers();
//    }
//
//    @Override
//    public List<Observer> getObservers() {
//        return super.impl.getObservers();
//    }
//
//    @Override
//    public void notifyObservers() {
//        super.impl.notifyObservers();
//    }
//
//    @Override
//    public void notifyObservers(final Object arg) {
//        super.impl.notifyObservers(arg);
//    }

}
