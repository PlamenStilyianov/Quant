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

import java.lang.reflect.Constructor;
import java.util.List;

import org.jquantlib.instruments.OneAssetOption;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.util.Observer;

/**
 * Finite-differences pricing engine for American-style vanilla options
 *
 * @category vanillaengines
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
//FIXME: http://bugs.jquantlib.org/view.php?id=405
public abstract class FDEngineAdapter<
            Base extends FDVanillaEngine, 
            Engine extends OneAssetOption.Engine>
implements OneAssetOption.Engine {

    //
    // private fields
    //

    private final FDVanillaEngine baseInstance;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE ARE COMMENTS REGARDING THE USE OF THE 2nd TYPE PARAMETER
    //
    // Whilst in C++ the template engine at compile time allocates the second type parameter as one of the base classes
    // of "this" class, in Java we decided employing the p-impl idiom to allocate the second parameter because the
    // type of this parameter is known at compile time anyway.
    // In order to keep resemblance with original QuantLib/C++ code, we define the second parameter but we never use it
    // because extended classes are responsible for initialize the p-impl reference, which also implies that it belongs
    // to the expect type, i.e.: the type the extend class expects for it.
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final Class<? extends FDVanillaEngine>	classBase;       // first generic type parameter
    private final Class<? extends Engine>			classEngine;     // second generic type parameter

    protected OneAssetOption.Engine impl;


    //
    // public constructors
    //

    public FDEngineAdapter(
    	    final Class<? extends FDVanillaEngine>   classBase,
    	    final Class<? extends Engine> classEngine,
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints,
            final boolean timeDependent) {
        // obtain generic type parameters
        this.classBase   = classBase;
        this.classEngine = classEngine;
        
        try {
            // instantiate 1st generic parameter : a base FD engine
            final Constructor<Base> baseConstructor = (Constructor<Base>) classBase.getConstructor(GeneralizedBlackScholesProcess.class, int.class, int.class, boolean.class);
            baseInstance = baseConstructor.newInstance(process, timeSteps, gridPoints, timeDependent);
        } catch (final Exception e) {
            throw new LibraryException(e);
        }
        process.addObserver(this);
    }
    

    //
    // implements PricingEngine
    //

    @Override
    public void calculate() /* @ReadOnly */ {
        // minimum sanity check on p-impl idiom
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        if (!this.classEngine.isAssignableFrom(impl.getClass())) {
            throw new LibraryException(ReflectConstants.ILLEGAL_TYPE_PARAMETER);
        }
        baseInstance.setupArguments(impl.getArguments());
        baseInstance.calculate(impl.getResults());
    }


    //
    // implements Observer
    //

    @Override
    public void update() {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        impl.update();
    }


    //
    // implements Observable
    //

    @Override
    public void addObserver(final Observer observer) {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        impl.addObserver(observer);
    }

    @Override
    public int countObservers() {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        return impl.countObservers();
    }

    @Override
    public void deleteObserver(final Observer observer) {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        impl.deleteObserver(observer);
    }

    @Override
    public void deleteObservers() {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        impl.deleteObservers();
    }

    @Override
    public List<Observer> getObservers() {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        return impl.getObservers();
    }

    @Override
    public void notifyObservers() {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        impl.notifyObservers();
    }

    @Override
    public void notifyObservers(final Object arg) {
        if (impl==null) {
            throw new LibraryException(PRICING_ENGINE_NOT_SET);
        }
        impl.notifyObservers(arg);
    }

}
