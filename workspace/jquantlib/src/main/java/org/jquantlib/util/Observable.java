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

import java.util.List;

/**
 * This interface is intended to provide more flexibility to complex object
 * models when multiple inheritance is needed.
 * 
 * <p>
 * By providing Observable as an interface, instead of the JDK'a
 * {@link java.util.Observable} class, client classes have the option to mimic
 * multiple inheritance by means of a delegate pattern which uses an inner
 * private class which implements this interface.
 * 
 * <p>
 * This class is based on the work done by Martin Fischer, with only minor changes.
 * See references below.
 * 
 * @see <a
 *      href="http://www.jroller.com/martin_fischer/entry/a_generic_java_observer_pattern">Martin
 *      Fischer: Observer and Observable interfaces</a>
 * @see <a href="http://jdj.sys-con.com/read/35878.htm">Improved
 *      Observer/Observable</a>
 * 
 * @author Martin Fischer (original author)
 * @author Richard Gomes
 */
public interface Observable {
	
	/**
	 * Attaches a observer to the Observable. After attachment the observer gets
	 * informed about changes in the Observable.
	 * 
	 * @param observer
	 *            The observer to attach to the observable
	 */
	public void addObserver(final Observer observer);
	

	/**
	 * Counts how many Observers were attached to this class.
	 * 
	 * @return the number of Observers
	 * @see Observer
	 */
	public int countObservers();
	
	
    /**
     * Returns list of observers registered with the Observable. List returned is
     * unmodifiable list. 
     * 
     * @return list of observers
     */
    public List<Observer> getObservers();

	
	/**
	 * Detaches a previously attached observer to the observable. After
	 * detachment the observer does no longer receive change notifications from
	 * the observable.
	 * 
	 * @param observer
	 *            The observer to detach from the observable
	 */
	public void deleteObserver(final Observer observer);
	
	
	/**
	 * Detaches all previously attached observer to the observable. After
	 * detachment observers do not longer receive change notifications from
	 * the observable.
	 */
	public void deleteObservers();
	

	/**
	 * Notifies all attached observers about changes in the observable.
	 */
	public void notifyObservers();

	/**
	 * Notifies all attached observers about changes in the observable.
	 * 
	 * @param arg
	 *            an arbitrary Object to be passed to the Observer
	 */
	public void notifyObservers(Object arg);
}