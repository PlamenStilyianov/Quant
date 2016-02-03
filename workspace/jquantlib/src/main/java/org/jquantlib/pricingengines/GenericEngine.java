/*
 Copyright (C) 2008 Richard Gomes

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

package org.jquantlib.pricingengines;

import java.util.List;

import org.jquantlib.instruments.Instrument;
import org.jquantlib.util.DefaultObservable;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * This is a generic definition of a PriceEngine which takes its arguments from an {@link Arguments} structure and returns its
 * results in a {@link Results} structure.
 *
 * @param <A> is an parameterized Arguments object
 * @param <R> is an parameterized Results object
 *
 * @author Richard Gomes
 */
public abstract class GenericEngine
            <A extends Instrument.Arguments, R extends Instrument.Results>
            implements PricingEngine, Observer {

    //
    // protected fields
    //

    protected A arguments_;
    protected R results_;

    //
    // protected constructors
    //

    protected GenericEngine(final A arguments, final R results) {
        this.arguments_ = arguments;
        this.results_ = results;
    }

    //
    // implements PricingEngine
    //

    @Override
    public final A getArguments() {
        return arguments_;
    }

    @Override
    public final R getResults() {
        return results_;
    }

    @Override
    public void reset() {
        results_.reset();
    }

    //
    // implements Observer
    //

    @Override
    //XXX::OBS public void update(final Observable o, final Object arg) {
    public void update() {
        //XXX:OBS update();
        notifyObservers();
    }

    //
    // implements Observable
    //

    /**
     * Implements multiple inheritance via delegate pattern to an inner class
     *
     * @see Observable
     * @see DefaultObservable
     */
    private final Observable delegatedObservable = new DefaultObservable(this);

    @Override
    public final void addObserver(final Observer observer) {
        delegatedObservable.addObserver(observer);
    }

    @Override
    public final int countObservers() {
        return delegatedObservable.countObservers();
    }

    @Override
    public final void deleteObserver(final Observer observer) {
        delegatedObservable.deleteObserver(observer);
    }

    @Override
    public final void notifyObservers() {
        delegatedObservable.notifyObservers();
    }

    @Override
    public final void notifyObservers(final Object arg) {
        delegatedObservable.notifyObservers(arg);
    }

    @Override
    public final void deleteObservers() {
        delegatedObservable.deleteObservers();
    }

    @Override
    public final List<Observer> getObservers() {
        return delegatedObservable.getObservers();
    }

}
