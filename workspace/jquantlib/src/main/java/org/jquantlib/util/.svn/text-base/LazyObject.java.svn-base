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
 Copyright (C) 2003 RiskMap srl

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

package org.jquantlib.util;

import java.util.List;


/**
 * Framework for calculation on demand and result caching.
 *
 * @see <a href="http://c2.com/cgi/wiki?LazyObject">Lazy Object Design Pattern</a>
 * @see Observer
 * @see Observable
 *
 * @author Richard Gomes
 */
public abstract class LazyObject implements Observer, Observable {

    //
    // protected fields
    //

    protected boolean calculated;
    protected boolean frozen;

    //
    // protected abstract methods
    //

    /**
     * This method must implement any calculations which must be (re)done in order to calculate the desired results.
     *
     * @throws ArithmeticException
     *
     */
    protected abstract void performCalculations() throws ArithmeticException;

    //
    // public constructors
    //

    /**
     * Creates a new LazyObject instance which is potentially able to perform calculations on demand every time it observes a change
     * in a {@link Observable} object. A LazyObject is an {@link Observer} and an {@link Observable} at the same time.
     */
    public LazyObject() {
        this.calculated = false;
        this.frozen = false;
    }

    //
    // public final methods
    //

    /**
     * This method force the recalculation of any results which would otherwise be cached.
     *
     * @note Explicit invocation of this method is <b>not</b> necessary if the object registered itself as observer with the
     *       structures on which such results depend. It is strongly advised to follow this policy when possible.
     */
    public final void recalculate() {
        final boolean wasFrozen = frozen;
        calculated = frozen = false;
        try {
            calculate();
        } finally {
            frozen = wasFrozen;
            notifyObservers();
        }
    }

    /**
     * This method constrains the object to return the presently cached results on successive invocations, even if arguments upon
     * which they depend should change.
     */
    public final void freeze() {
        frozen = true;
    }

    /**
     * This method reverts the effect of the <i><b>freeze</b></i> method, thus re-enabling recalculations.
     */
    public final void unfreeze() {
        frozen = false;
        // send notification, just in case we lost any
        notifyObservers();
    }

    //
    // protected methods
    //

    /**
     * This method performs all needed calculations by calling the <i><b>performCalculations</b></i> method.
     * <p>
     *
     * @note Objects cache the results of the previous calculation. Such results will be returned upon later invocations of <i><b>calculate</b></i>.
     *       When the results depend on arguments which could change between invocations, the lazy object must register itself as
     *       observer of such objects for the calculations to be performed again when they change.
     */
    protected void calculate() {
        if (!calculated && !frozen) {
            // prevent infinite recursion in case of bootstrapping
            calculated = true;
            try {
                performCalculations();
            } catch (final ArithmeticException e) {
                calculated = false;
                throw e;
            }
        }
    }

    //
    // implements Observer
    //

    // XXX:registerWith
    //    @Override
    //    public void registerWith(final Observable o) {
    //        o.addObserver(this);
    //    }
    //
    //    @Override
    //    public void unregisterWith(final Observable o) {
    //        o.deleteObserver(this);
    //    }

    @Override
    //XXX::OBS public void update(final Observable o, final Object arg) {
    public void update() {
        // observers don't expect notifications from frozen objects
        // LazyObject forwards notifications only once until it has been
        // recalculated
        if (!frozen && calculated)
            //XXX::OBS notifyObservers(arg);
            notifyObservers();
        calculated = false;
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
