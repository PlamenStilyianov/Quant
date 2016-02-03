/*
 Copyright (C) 2009 Ueli Hofstetter
 Copyright (C) 2009 John Nichol

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
 Copyright (C) 2006 Mario Pucci

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

package org.jquantlib.termstructures.volatilities;

import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.math.Constants;
import org.jquantlib.time.Date;
import org.jquantlib.util.DefaultObservable;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * Smile section base class
 *
 * @author Ueli Hofstetter
 * @author John Nichol
 */
public abstract class SmileSection implements Observer, Observable {

    private Date exerciseDate_;
    private Date reference_;

    private final DayCounter dc_;
    private final boolean isFloating_;

    protected double exerciseTime_;


    //
    // public constructors
    //

    public SmileSection(
            final Date d,
            final DayCounter dc,
            final Date referenceDate) {
    	exerciseDate_ = d;
    	dc_ = dc;
    	isFloating_ = referenceDate.isNull();
    	if (isFloating_) {
    		final Settings settings = new Settings();
    		settings.evaluationDate().addObserver(this);
    		reference_ = settings.evaluationDate();
    	} else
            reference_ = referenceDate;
    	initializeExerciseTime();
    }

    public SmileSection(
            final /* @Time */ double exerciseTime,
            final DayCounter dc) {
    	isFloating_ = false;
    	dc_ = dc;
    	exerciseTime_ = exerciseTime;
    	QL.require(exerciseTime_>=0.0,
    			"expiry time must be positive: " +
    			exerciseTime_ + " not allowed");
    }


    //
    // abstract methods
    //

    public abstract double minStrike();

    public abstract double maxStrike();

    public abstract double atmLevel();

    protected abstract /* @Real */ double volatilityImpl(/* @Rate */ double strike);


    //
    // public methods
    //

    public double variance() {
    	return variance(Constants.NULL_REAL);
    }

    public double volatility() {
    	return volatility(Constants.NULL_REAL);
    }

    public void initializeExerciseTime() {
        QL.require(exerciseDate_.ge(reference_),
                "expiry date (" + exerciseDate_ +
                ") must be greater than reference date (" +
                reference_ + ")");
     exerciseTime_ = dc_.yearFraction(reference_, exerciseDate_);

    }

    public double variance(double strike) {
        if (Double.isNaN(strike))
            strike = atmLevel();
        return varianceImpl(strike);
    }

    public double volatility(double strike) {
        if (Double.isNaN(strike))
            strike = atmLevel();
        return volatilityImpl(strike);
    }

    public Date exerciseDate() {
        return exerciseDate_;
    }

    public double exerciseTime() {
        return exerciseTime_;
    }

    public DayCounter dayCounter() {
        return dc_;
    }

    //
    // protected methods
    //

    protected /* @Real */ double varianceImpl(/* @Rate */ final double strike) {
    	/* @Volatility */ final double v = volatilityImpl(strike);
        return v*v*exerciseTime();

    }


    //
    // implements Observer
    //

    @Override
    public void update() {
        if (isFloating_) {
            final Settings settings = new Settings();
            reference_ = settings.evaluationDate();
            initializeExerciseTime();
        }

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
    private final DefaultObservable delegatedObservable = new DefaultObservable(this);

    @Override
    public void addObserver(final Observer observer) {
        delegatedObservable.addObserver(observer);
    }

    @Override
    public int countObservers() {
        return delegatedObservable.countObservers();
    }

    @Override
    public void deleteObserver(final Observer observer) {
        delegatedObservable.deleteObserver(observer);
    }

    @Override
    public void notifyObservers() {
        delegatedObservable.notifyObservers();
    }

    @Override
    public void notifyObservers(final Object arg) {
        delegatedObservable.notifyObservers(arg);
    }

    @Override
    public void deleteObservers() {
        delegatedObservable.deleteObservers();
    }

    @Override
    public List<Observer> getObservers() {
        return delegatedObservable.getObservers();
    }

}
