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
 Copyright (C) 2004, 2005, 2006 StatPro Italia srl

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

package org.jquantlib.termstructures;

import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.interpolations.DefaultExtrapolator;
import org.jquantlib.math.interpolations.Extrapolator;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.DefaultObservable;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;



/**
 * Basic term-structure functionality.
 *
 * <p><b>More Details about constructors:</b>
 * <p>There are three ways in which a term structure can keep
 * track of its reference date:
 * <li>such date is fixed;</li>
 * <li>such date is determined by advancing the current date of a given number of business days;</li>
 * <li>such date is based on the reference date of some other structure.</li>
 *
 * <p>Case 1: The constructor taking a date is to be used.
 * The default implementation of {@link TermStructure#referenceDate()} will
 * then return such date.
 *
 * <p>Case 2: The constructor taking a number of days and a calendar is to be used
 * so that {@link TermStructure#referenceDate()} will return a date calculated based on the
 * current evaluation date and the term structure and observers will be notified when the
 * evaluation date changes.
 *
 * <p>Case 3: The {@link TermStructure#referenceDate()} method must
 * be overridden in derived classes so that it fetches and
 * return the appropriate date.
 *
 * @author Richard Gomes
 */
public abstract class AbstractTermStructure implements TermStructure {

    static private final String THIS_METHOD_MUST_BE_OVERRIDDEN = "This method must be overridden";


    //
    // private fields
    //

    /**
     * <p>Case 1: The constructor taking a date is to be used.
     * The default implementation of {@link TermStructure#referenceDate()} will
     * then return such date.
     *
     * <p>Case 2: The constructor taking a number of days and a calendar is to be used
     * so that {@link TermStructure#referenceDate()} will return a date calculated based on the
     * current evaluation date and the term structure and observers will be notified when the
     * evaluation date changes.
     *
     * <p>Case 3: The {@link TermStructure#referenceDate()} method must
     * be overridden in derived classes so that it fetches and
     * return the appropriate date.
     */
    private Date referenceDate;

    /**
     * Beware that this variable must always be accessed via {@link #dayCounter()} method.
     * Extended classes have the option to redefine semantics of a day counter by keeping their own private
     * dayCounter variable and providing their own version of {@link #dayCounter()} method. When extended
     * classes fail to provide their version of {@link #dayCounter()} method, <i><b>this</b>.getDayCounter</i>
     * must throw an {@link IllegalStateException} because the private variable dayCounter was never initialised.
     *
     * @see #getDayCounter
     */
    private final DayCounter dayCounter;

    /**
     * This variable must be <i>false</i> when Case 2; <i>true</i> otherwise
     */
    private boolean updated;


    //
    // private final fields
    //

    private final int settlementDays;

    /**
     * This variable must be <i>true</i> when Case 2; <i>false</i> otherwise
     */
    private final boolean moving;


    //
    // protected fields
    //

    /**
     * Beware that this variable must always be accessed via {@link #calendar()} method.
     * Extended classes have the option to redefine semantics of a calendar by keeping their own private
     * calendar variable and providing their own version of {@link #calendar()} method. When extended
     * classes fail to provide their version of {@link #calendar()} method, <i><b>this</b>.getCalendar</i>
     * must throw an {@link IllegalStateException} because the private variable calendar was never initialised.
     *
     * @see #calendar
     */
    protected Calendar calendar;


    //
    // public constructors
    //

    /**
     * <p>This constructor requires an override of method {@link TermStructure#referenceDate()} in
     * derived classes so that it fetches and return the appropriate reference date.
     * This is the <i>Case 3</i> described on the top of this class.
     *
     * @see TermStructure documentation for more details about constructors.
     */
    public AbstractTermStructure() {
        this(new Actual365Fixed());
    }

    /**
     * <p>This constructor requires an override of method {@link TermStructure#referenceDate()} in
     * derived classes so that it fetches and return the appropriate reference date.
     * This is the <i>Case 3</i> described on the top of this class.
     *
     * @see TermStructure documentation for more details about constructors.
     */
    //TODO : What's the calendar in this case?
    public AbstractTermStructure(final DayCounter dc) {
        QL.require(dc!=null , "day counter must be informed"); // TODO: message
        this.calendar = null;
        this.settlementDays = 0;
        this.dayCounter = dc;

        // When Case 1 or Case 3
        this.moving = false;
        this.updated = true;

        // initialize reference date without any observers
        this.referenceDate = null;
    }

    /**
     * Initialize with a fixed reference date
     *
     * <p>This constructor takes a date to be used.
     * The default implementation of {@link TermStructure#referenceDate()} will
     * then return such date.
     * This is the <i>Case 1</i> described on the top of this class.
     *
     * @see TermStructure documentation for more details about constructors.
     */
    public AbstractTermStructure(final Date referenceDate, final Calendar calendar) {
        this(referenceDate, calendar, new Actual365Fixed());
    }

    /**
     * Initialize with a fixed reference date
     *
     * <p>This constructor takes a date to be used.
     * The default implementation of {@link TermStructure#referenceDate()} will
     * then return such date.
     * This is the <i>Case 1</i> described on the top of this class.
     *
     * @see TermStructure documentation for more details about constructors.
     */
    public AbstractTermStructure(final Date referenceDate, final Calendar calendar, final DayCounter dc) {
        QL.require(referenceDate!=null , "reference date must be informed"); // TODO: message
        QL.require(calendar!=null , "calendar must be informed"); // TODO: message
        QL.require(dc!=null , "day counter must be informed"); // TODO: message

        this.settlementDays = 0;
        this.calendar = calendar;
        this.dayCounter = dc;

        // When Case 1 or Case 3
        this.moving = false;
        this.updated = true;

        // initialize reference date with this class as observer
        this.referenceDate = referenceDate;
    }

    /**
     * Calculate the reference date based on the global evaluation date
     *
     * <p>This constructor takes a number of days and a calendar to be used
     * so that {@link TermStructure#referenceDate()} will return a date calculated based on the
     * current evaluation date and the term structure. This class will be notified when the
     * evaluation date changes.
     * This is the <i>Case 2</i> described on the top of this class.
     *
     * @see TermStructure documentation for more details about constructors.
     */
    public AbstractTermStructure(final int settlementDays, final Calendar calendar) {
        this(settlementDays, calendar, new Actual365Fixed());
    }


    /**
     * Calculate the reference date based on the global evaluation date
     *
     * <p>This constructor takes a number of days and a calendar to be used
     * so that {@link TermStructure#referenceDate()} will return a date calculated based on the
     * current evaluation date and the term structure. This class will be notified when the
     * evaluation date changes.
     * This is the <i>Case 2</i> described on the top of this class.
     *
     * @see TermStructure documentation for more details about constructors.
     */
    public AbstractTermStructure(final int settlementDays, final Calendar calendar, final DayCounter dc) {
        this.settlementDays = settlementDays;
        this.calendar = calendar;
        this.dayCounter = dc;

        // When Case 2
        this.moving = true;
        this.updated = false;

        // observes date changes
        final Date today = new Settings().evaluationDate();
        today.addObserver(this);

        this.referenceDate = calendar.advance(today, settlementDays, TimeUnit.Days);
    }


    //
    // protected methods
    //

    /**
     * This method performs date-range check
     */
    protected void checkRange(final Date d, final boolean extrapolate) /* @ReadOnly */ {
        QL.require(d.ge(referenceDate()) , "date before reference date"); // TODO: message
        QL.require(extrapolate || allowsExtrapolation() || d.le(maxDate()) , "date is past max curve"); // TODO: message
    }

    /**
     * This method performs date-range check
     */
    protected void checkRange(/*@Time*/ final double t, final boolean extrapolate) /* @ReadOnly */ {
        QL.require(t >= 0.0 , "negative time given"); // TODO: message
        QL.require(extrapolate||allowsExtrapolation()||t<=maxTime()||Closeness.isCloseEnough(t, maxTime()) , "time is past max curve"); // TODO: message
    }


    //
    // implements TermStructure
    //

    /* (non-Javadoc)
     * @see org.jquantlib.termstructures.TermStructure#calendar()
     */
    @Override
    public Calendar calendar() /* @ReadOnly */ {
        QL.require(this.calendar != null , THIS_METHOD_MUST_BE_OVERRIDDEN); // TODO: message
        return calendar;
    }

    /* (non-Javadoc)
     * @see org.jquantlib.termstructures.TermStructure#settlementDays()
     */
    public /*@Natural*/ int settlementDays() /* @ReadOnly */ {
        return settlementDays;
    }

    /* (non-Javadoc)
     * @see org.jquantlib.termstructures.TermStructure#timeFromReference(org.jquantlib.util.Date)
     */
    @Override
    public final /*@Time*/ double timeFromReference(final Date date) /* @ReadOnly */ {
        return dayCounter().yearFraction(referenceDate(), date);
    }

    /* (non-Javadoc)
     * @see org.jquantlib.termstructures.TermStructure#dayCounter()
     */
    @Override
    public DayCounter dayCounter() /* @ReadOnly */ {
        QL.require(this.dayCounter != null , THIS_METHOD_MUST_BE_OVERRIDDEN); // TODO: message
        return dayCounter;
    }

    /* (non-Javadoc)
     * @see org.jquantlib.termstructures.TermStructure#maxTime()
     */
    @Override
    public /*@Time*/ double maxTime() /* @ReadOnly */ {
        return timeFromReference(maxDate());
    }

    /* (non-Javadoc)
     * @see org.jquantlib.termstructures.TermStructure#referenceDate()
     */
    @Override
    public Date referenceDate() /* @ReadOnly */ {
        if (!updated) {
        	final Date today = new Settings().evaluationDate();
        	referenceDate = calendar().advance(today, settlementDays, TimeUnit.Days);
            updated = true;
        }
        return referenceDate;
    }


    //
    // implements Extrapolator
    //

    /**
     * Implements multiple inheritance via delegate pattern to a inner class
     *
     * @see Extrapolator
     */
    private final DefaultExtrapolator delegatedExtrapolator = new DefaultExtrapolator();

    /**
     * @return
     */
    @Override
    public final boolean allowsExtrapolation() {
        return delegatedExtrapolator.allowsExtrapolation();
    }

    @Override
    public void disableExtrapolation() {
        delegatedExtrapolator.disableExtrapolation();
    }

    @Override
    public void enableExtrapolation() {
        delegatedExtrapolator.enableExtrapolation();
    }


    //
    // implements Observer
    //

    //XXX:registerWith
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
        if (moving) {
            updated = false;
        }
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
