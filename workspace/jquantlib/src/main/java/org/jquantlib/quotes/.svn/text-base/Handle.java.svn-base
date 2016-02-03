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
 Copyright (C) 2003, 2004, 2005, 2006, 2007 StatPro Italia srl

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

package org.jquantlib.quotes;

import java.util.List;

import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;
import org.jquantlib.util.WeakReferenceObservable;

/**
 * Shared handle to an observable
 * <p>
 * All copies of an instance of this class refer to the same observable by means
 * of a relinkable weak reference. When such pointer is relinked to another
 * observable, the change will be propagated to all the copies.
 *
 * @author Richard Gomes
 */
public class Handle<T extends Observable> implements Observable {

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // In QL/C++, Handle has operators which return the inner Link class, performing
    // a call to currentLink() behind the scenes. This is pretty handy from the calling
    // code because you can simply dereference a Handle and (like magic!) call methods
    // from the parameterized class.
    //
    // In Java we are obliged to explicitly call currentLink() in order to do the same thing.
    //
    // The difficulty arises when we need to register Observers.
    //
    // In the current implementation, we hide class Link from outside world and we only expose
    // the Handle itself, which implements Observable. Doing this way, the calling Java code
    // does not need to decide which object is really the Observer object because class Handle
    // is responsible for hiding this implementation details from outside world and properly
    // forwarding notifications to external Observers as expected.
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////


    static private final String EMPTY_HANDLE = "empty Handle cannot be dereferenced"; // TODO: message

    //
    // final private fields
    //

    /**
     * Responsible for forwarding notifications coming from the Observable object to
     * objects registering as Observers of <code>this</code> instance
     */
    final private Link link;

    //
    // private fields
    //

    private T observable;
    private boolean isObserver = false;

    //
    // public constructors
    //


    public Handle() {
        this.link = new Link(this);
        this.observable = null; // just for verbosity
        this.isObserver = true;
    }

    public Handle(final T observable) {
        this.link = new Link(this);
        internalLinkTo(observable, true);
    }


    public Handle(final T observable, final boolean registerAsObserver) {
        this.link = new Link(this);
        internalLinkTo(observable, registerAsObserver);
    }


    //
    // final public methods
    //

    final public boolean empty() /* @ReadOnly */ {
        return (this.observable==null);
    }

    final public T currentLink() {
        return this.observable;
    }


    //
    // public methods
    //

    public void linkTo(final T observable) {
        throw new UnsupportedOperationException();
    }

    public void linkTo(final T observable, final boolean registerAsObserver) {
        throw new UnsupportedOperationException();
    }


    //
    // protected final methods
    //

    final protected void internalLinkTo(final T observable) {
        this.internalLinkTo(observable, true);
    }

    final protected void internalLinkTo(final T observable, final boolean registerAsObserver) {
        if ((this.observable!=observable) || (this.isObserver!=registerAsObserver)) {
            if (this.observable!=null && this.isObserver) {
                this.observable.deleteObserver(link);
            }
            this.observable = observable;
            this.isObserver = registerAsObserver;
            if (this.observable!=null && this.isObserver) {
                this.observable.addObserver(link);
            }
            if (this.observable!=null) {
                this.observable.notifyObservers();
            }
        }
    }


    //
    // overrides Object
    //

    @Override
    public String toString() {
        return observable==null ? "null" : observable.toString();
    }


    //
    // implements Observable
    //

    @Override
    public final void addObserver(final Observer observer) {
        //XXX QL.require(observable!=null, EMPTY_HANDLE);
        link.addObserver(observer);
    }

    @Override
    public final int countObservers() {
        //XXX QL.require(observable!=null, EMPTY_HANDLE);
        return link.countObservers();
    }

    @Override
    public final void deleteObserver(final Observer observer) {
        //XXX QL.require(observable!=null, EMPTY_HANDLE);
        link.deleteObserver(observer);
    }

    @Override
    public final void notifyObservers() {
        //XXX QL.require(observable!=null, EMPTY_HANDLE);
        link.notifyObservers();
    }

    @Override
    public final void notifyObservers(final Object arg) {
        //XXX QL.require(observable!=null, EMPTY_HANDLE);
        link.notifyObservers(arg);
    }

    @Override
    public final void deleteObservers() {
        //XXX QL.require(observable!=null, EMPTY_HANDLE);
        link.deleteObservers();
    }

    @Override
    public final List<Observer> getObservers() {
        //XXX QL.require(observable!=null, EMPTY_HANDLE);
        return link.getObservers();
    }


    //
    // private final inner classes
    //

    /**
     * A Link is responsible for observing the Observable object passed to Handle during it's construction
     * or another Observable passed to {@link Handle#linkTo(Observable)} methods.
     * <p>
     * So, the ditto Observable notifies its Observers, a Link instance is notified, which ultimately
     * is responsible for forwarding this notification to a list of external Observers.
     */
    final private class Link extends WeakReferenceObservable implements Observer {

        private Link(final Observable observable) {
            super(observable);
        }

        @Override
        public void update() {
            if (observable!=null) {
                super.notifyObservers();
            }
        }
    }

}
