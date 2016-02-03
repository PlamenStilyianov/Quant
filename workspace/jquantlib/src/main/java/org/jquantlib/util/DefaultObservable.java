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

package org.jquantlib.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jquantlib.QL;


// --------------------------------------------------------
// This class is based on the work done by Martin Fischer.
// See references in JavaDoc
//--------------------------------------------------------

/**
 * Default implementation of an {@link Observable}.
 * <p>
 * This implementation notifies the observers in a synchronous fashion. Note that this can cause trouble if you notify the observers
 * while in a transactional context because once the notification is done it cannot be rolled back.
 *
 * @note This class is not thread safe
 *
 * @see <a href="http://www.jroller.com/martin_fischer/entry/a_generic_java_observer_pattern"> Martin Fischer: Observer and
 *      Observable interfaces</a>
 * @see <a href="http://jdj.sys-con.com/read/35878.htm">Improved Observer/Observable</a>
 *
 * @see Observable
 * @see Observer
 * @see WeakReferenceObservable
 *
 * @author Richard Gomes
 * @author Srinivas Hasti
 */
public class DefaultObservable implements Observable {

    final private static String OBSERVABLE_IS_NULL = "observable is null";
    final private static String CANNOT_NOTIFY_OBSERVERS = "could not notify one or more observers";

    //
    // private final fields
    //

    private final List<Observer> observers;
    private final Observable observable;

    //
    // public constructors
    //

    public DefaultObservable(final Observable observable) {
        QL.require(observable != null, DefaultObservable.OBSERVABLE_IS_NULL);
        this.observers = new CopyOnWriteArrayList<Observer>();
        this.observable = observable;
    }

    //
    // public methods
    //

    @Override
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }

    @Override
    public int countObservers() {
        return observers.size();
    }

    @Override
    public List<Observer> getObservers() {
        return Collections.unmodifiableList(this.observers);
    }

    @Override
    public void deleteObserver(final Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void deleteObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(final Object arg) {
        Exception exception = null;
        for (final Observer observer : observers) {
            try {
                wrappedNotify(observer, observable, arg);
            } catch (final Exception e) {
                // Quite a dilemma. If we don't catch the exception,
                // other observers will not receive the notification
                // and might be left in an incorrect state. If we do
                // catch it and continue the loop (as we do here) we
                // lose the exception. The least evil might be to try
                // and notify all observers, while raising an
                // exception if something bad happened.
                exception = e;
            }
        }
        if (exception!=null) QL.error(DefaultObservable.CANNOT_NOTIFY_OBSERVERS, exception);
    }

    //
    // protected methods
    //

    /**
     * This method is intended to encapsulate the notification semantics, in
     * order to let extended classes to implement their own version. Possible
     * implementations are:
     * <li>remote notification;</li>
     * <li>notification via SwingUtilities.invokeLater</li>
     * <li>others...</li>
     *
     * <p>
     * The default notification simply does
     * <pre>
     * observer.update(observable, arg);
     * </pre>
     *
     * @param observer
     * @param observable
     * @param arg
     */
    protected void wrappedNotify(final Observer observer, final Observable observable, final Object arg) {
        //XXX::OBS observer.update(observable, arg);
        observer.update();
    }

}