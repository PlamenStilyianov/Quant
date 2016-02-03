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
 Copyright (C) 2005, 2006, 2007 StatPro Italia srl

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

package org.jquantlib.termstructures.yieldcurves;

import java.lang.reflect.Constructor;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.interpolations.Interpolation;
import org.jquantlib.math.interpolations.Interpolation.Interpolator;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.Bootstrap;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.termstructures.IterativeBootstrap;
import org.jquantlib.termstructures.RateHelper;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.util.LazyObject;
import org.jquantlib.util.Pair;

/**
 * Piecewise yield term structure
 * <p>
 * This term structure is bootstrapped on a number of interest rate instruments which are passed as a vector of handles to
 * RateHelper instances. Their maturities mark the boundaries of the interpolated segments.
 * <p>
 * Each segment is determined sequentially starting from the earliest period to the latest and is chosen so that the instrument
 * whose maturity marks the end of such segment is correctly repriced on the curve.
 *
 * @note The bootstrapping algorithm will raise an exception if any two instruments have the same maturity date.
 *
 *
 * @category yieldtermstructures
 *
 * @author Richard Gomes
 */
public class PiecewiseYieldCurve<
                T extends Traits,
                I extends Interpolator,
                B extends Bootstrap>
        extends LazyObject implements PiecewiseCurve<I> {

    //=============================================================================================
    //                                      Translation Notes                                    //
    //                                                                                           //
    // We are purposely diverging from QuantLib in regards to usage of std::vector.              //
    //                                                                                           //
    // The normal way of translating std::vector<T> would be employing List<T> but, in the       //
    // specific case of PiecewiseYieldCurve, it would impose more complexity and would require   //
    // data transformations which would directly impact performance. On the other hand, benefits //
	// provided by Lists are not necessary here because only indexed access is required, nothing //
	// else.                                                                                     // 
    //                                                                                           //
    // Richard Gomes 14-FEB-2011                                                                 // 
    //=============================================================================================
    
	
    //
    // private final fields
    //

    private final Class<T> classT;
    private final Class<I> classI;
    private final Class<B> classB;
    private final Traits.Curve baseCurve;


    private final RateHelper[] instruments;
    private final Handle<Quote>[] jumps;
    private final double accuracy;
    
    
    
    private /*@Time*/ double[] jumpTimes;
    private Date[] jumpDates;
    private Date latestReference;


    //
    // package private fields
    //
    private final Traits        traits;
    private final Interpolator  interpolator;
    private final Bootstrap     bootstrap;


    
    
    
    
    // *********************************************************
    // * Case 1 : initByReference with Formal Class Parameters *
    // *********************************************************
    
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final Date referenceDate,
            final RateHelper[] instruments,
            final DayCounter dayCounter) {
    	this(	classT, classI, classB,
                referenceDate, instruments, dayCounter,
                new Handle/*<Quote>*/[0],
                new Date[0],
                1.0e-12,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
    	);
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final Date referenceDate,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps) {
    	this(	classT, classI, classB,
                referenceDate, instruments, dayCounter,
                jumps,
                new Date[0],
                1.0e-12,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
    	);
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final Date referenceDate,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates) {
    	this(	classT, classI, classB,
                referenceDate, instruments, dayCounter,
                jumps,
                jumpDates,
                1.0e-12,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
    	);
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final Date referenceDate,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates,
            final /*@Real*/ double accuracy) {
    	this(	classT, classI, classB,
                referenceDate, instruments, dayCounter,
                jumps,
                jumpDates,
                accuracy,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
    	);
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final Date referenceDate,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates,
            final /*@Real*/ double accuracy,
            final Interpolator interpolator) {
    	this(	classT, classI, classB,
                referenceDate, instruments, dayCounter,
                jumps,
                jumpDates,
                accuracy,
                interpolator,
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
    	);
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final Date referenceDate,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates,
            final /*@Real*/ double accuracy,
            final Interpolator interpolator,
            final Bootstrap bootstrap) {
    	
        QL.validateExperimentalMode();

		QL.require(classT!=null, "Generic type for Traits is null");
		QL.require(classI!=null, "Generic type for Interpolation is null");
		QL.require(classB!=null, "Generic type for Bootstrap is null");
        this.classT = classT;
        this.classI = classI;
        this.classB = classB;
        
        this.interpolator = interpolator==null     ? constructInterpolator(classI) : interpolator;
        this.bootstrap    = bootstrap==null        ? constructBootstrap(classB)    : bootstrap;

        //TODO; validate types of interpolator and bootstrap
        
        // instantiate base class and call super constructor
        this.baseCurve = constructBaseClass(classT, classI, referenceDate, dayCounter, this.interpolator);
        this.instruments = instruments; // TODO: clone() ?
        
        this.jumps        = jumps==null            ?  new Handle /*<Quote>*/ [0]   : jumps;
        this.jumpDates    = jumpDates==null        ? new Date[0]                   : jumpDates;
        this.accuracy     = Double.isNaN(accuracy) ? 1.0e-12                       : accuracy;
        this.traits       = constructTraits(classT);

        this.jumpTimes = new double[jumpDates.length];
        setJumps();
        for (final Handle<Quote> jump : jumps) {
            jump.addObserver(this);
        }
        bootstrap.setup(this);
    }



    // ********************************************************
    // * Case 2 : initByCalendar with Formal Class Parameters *
    // ********************************************************
    
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final /*@Natural*/ int settlementDays,
            final Calendar calendar,
            final RateHelper[] instruments,
            final DayCounter dayCounter) {
        this(
        		classT, classI, classB,
                settlementDays, calendar, instruments, dayCounter,
                new Handle /*<Quote>*/ [0],
                new Date[0],
                1.0e-12,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
        );
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final /*@Natural*/ int settlementDays,
            final Calendar calendar,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps) {
        this(
        		classT, classI, classB,
                settlementDays, calendar, instruments, dayCounter,
                jumps,
                new Date[0],
                1.0e-12,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
        );
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final /*@Natural*/ int settlementDays,
            final Calendar calendar,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates) {
        this(
        		classT, classI, classB,
                settlementDays, calendar, instruments, dayCounter,
                jumps,
                jumpDates,
                1.0e-12,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
        );
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final /*@Natural*/ int settlementDays,
            final Calendar calendar,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates,
            final /*@Real*/ double accuracy) {
        this(
        		classT, classI, classB,
                settlementDays, calendar, instruments, dayCounter,
                jumps,
                jumpDates,
                accuracy,
                constructInterpolator(classI),
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
        );
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final /*@Natural*/ int settlementDays,
            final Calendar calendar,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates,
            final /*@Real*/ double accuracy,
            final Interpolator interpolator) {
        this(
        		classT, classI, classB,
                settlementDays, calendar, instruments, dayCounter,
                jumps,
                jumpDates,
                accuracy,
                interpolator,
                new IterativeBootstrap<PiecewiseYieldCurve<T,I,B>>(PiecewiseYieldCurve.class)
        );
    }
    public PiecewiseYieldCurve(
            final Class<T> classT,
            final Class<I> classI,
            final Class<B> classB,
            //--
            final /*@Natural*/ int settlementDays,
            final Calendar calendar,
            final RateHelper[] instruments,
            final DayCounter dayCounter,
            //----
            final Handle<Quote>[] jumps,
            final Date[] jumpDates,
            final /*@Real*/ double accuracy,
            final Interpolator interpolator,
            final Bootstrap bootstrap) {
    	
    	QL.validateExperimentalMode();
    	
		QL.require(classT!=null, "Generic type for Traits is null");
		QL.require(classI!=null, "Generic type for Interpolation is null");
		QL.require(classB!=null, "Generic type for Bootstrap is null");
        this.classT = classT;
        this.classI = classI;
        this.classB = classB;
        
        this.interpolator = interpolator==null     ? constructInterpolator(classI) : interpolator;
        this.bootstrap    = bootstrap==null        ? constructBootstrap(classB)    : bootstrap;

        //TODO; validate types of interpolator and bootstrap
        
        // instantiate base class and call super constructor
        this.baseCurve = constructBaseClass(classT, classI, settlementDays, calendar, dayCounter, this.interpolator);
        this.instruments = instruments;
        
        this.jumps        = jumps==null            ? new Handle /*<Quote>*/ [0]    : jumps;
        this.jumpDates    = jumpDates==null        ? new Date[0]                   : jumpDates;
        this.accuracy     = Double.isNaN(accuracy) ? 1.0e-12                       : accuracy;
        this.traits       = constructTraits(classT);

        this.jumpTimes = new double[jumpDates.length];
        setJumps();
        for (final Handle<Quote> jump : jumps) {
            jump.addObserver(this);
        }
        bootstrap.setup(this);
    }



    

    static private Traits.Curve constructBaseClass(
            final Class<?> classT,
            final Class<?> classI,
            final Date referenceDate,
            final DayCounter dayCounter,
            final Interpolator interpolator) {
        if (classT == Discount.class)
            return new InterpolatedDiscountCurve(classI, referenceDate, dayCounter, interpolator);
        else if (classT == ForwardRate.class)
            return new InterpolatedForwardCurve(classI, referenceDate, dayCounter, interpolator);
        else if (classT == ZeroYield.class)
            return new InterpolatedZeroCurve(classI, referenceDate, dayCounter, interpolator);
        else
            throw new LibraryException("only Discount, ForwardRate and ZeroYield are supported"); // TODO: message
    }

    static private Traits.Curve constructBaseClass(
            final Class<?> classT,
            final Class<?> classI,
            final /*@Natural*/ int settlementDays,
            final Calendar calendar,
            final DayCounter dayCounter,
            final Interpolator interpolator) {
        if (classT == Discount.class)
            return new InterpolatedDiscountCurve(classI, settlementDays, calendar, dayCounter, interpolator);
        else if (classT == ForwardRate.class)
            return new InterpolatedForwardCurve(classI, settlementDays, calendar, dayCounter, interpolator);
        else if (classT == ZeroYield.class)
            return new InterpolatedZeroCurve(classI, settlementDays, calendar, dayCounter, interpolator);
        else
            throw new LibraryException("only Discount, ForwardRate and ZeroYield are supported"); // TODO: message
    }

    static private Traits constructTraits(final Class<?> classT) {
        if (Traits.class.isAssignableFrom(classT)) {
            try {
                return (Traits) classT.newInstance();
            } catch (final Exception e) {
                throw new LibraryException("could not instantiate Traits", e); // TODO: message
            }
        }

        throw new LibraryException("not a Traits"); // TODO: message
    }

    static private Interpolator constructInterpolator(final Class<?> classI) {
        if (Interpolator.class.isAssignableFrom(classI)) {
            try {
                return (Interpolator) classI.newInstance();
            } catch (final Exception e) {
                throw new LibraryException("could not instantiate Interpolator", e); // TODO: message
            }
        }

        throw new LibraryException("not an Interpolator"); // TODO: message
    }

    static private Bootstrap constructBootstrap(final Class<?> classB) {
        if (Bootstrap.class.isAssignableFrom(classB)) {
            try {
                final Constructor<Bootstrap> c = (Constructor<Bootstrap>) classB.getConstructor(Class.class);
                return c.newInstance(PiecewiseCurve.class);
            } catch (final Exception e) {
                throw new LibraryException("could not instantiate Bootstrap", e); // TODO: message
            }
        }

        throw new LibraryException("not a Bootstrap"); // TODO: message
    }


    //
    // implements PiecewiseCurve
    //

    @Override
    public Traits traits() /* @ReadOnly */ {
        return traits;
    }

    @Override
    public Interpolator interpolator() /* @ReadOnly */ {
        return interpolator;
    }

    @Override
    public RateHelper[] instruments() /* @ReadOnly */ {
        return instruments;
    }

    @Override
    public double accuracy() {
        return accuracy;
    }

    @Override
    public Date maxDate() /* @ReadOnly */ {
        calculate();
        return baseCurve.maxDate();
    }

    @Override
    public double[] times() /* @ReadOnly */ {
        calculate();
        return baseCurve.times();
    }

    @Override
    public Date[] dates() /* @ReadOnly */ {
        calculate();
        return baseCurve.dates();
    }

    @Override
    public double[] data() /* @ReadOnly */ {
        calculate();
        return baseCurve.data();
    }

    @Override
    public List<Pair<Date, Double>> nodes() /* @ReadOnly */ {
        calculate();
        return baseCurve.nodes();
    }

    @Override
    public Date[] jumpDates() /* @ReadOnly */ {
        calculate();
        return baseCurve.dates();
    }

    @Override
    public double[] jumpTimes() /* @ReadOnly */ {
        calculate();
        return baseCurve.times();
    }

    @Override
    public void setData(final double[] data) {
        baseCurve.setData(data);
    }

    @Override
    public void setDates(final Date[] dates) {
        baseCurve.setDates(dates);
    }

    @Override
    public void setTimes(final double[] times) {
        baseCurve.setTimes(times);
    }

    @Override
    public Interpolation interpolation() {
        return baseCurve.interpolation();
    }

    @Override
    public void setInterpolation(final Interpolation interpolation) {
        baseCurve.setInterpolation(interpolation);
    }


    //
    // overrides LazyObject
    //

    @Override
    public void update() {
        baseCurve.update();
        super.update();
        if (baseCurve.referenceDate() != latestReference) {
            setJumps();
        }
    }


    //
    // private methods
    //

    public /*@DiscountFactor*/ double discountImpl(final /*@Time*/ double t) /* @ReadOnly */ {
        calculate();

        if (jumps.length > 0) {
            /*@DiscountFactor*/ double jumpEffect = 1.0;
            for (int i=0; i<jumps.length && jumpTimes[i]<t; ++i) {
                QL.require(jumps[i].currentLink().isValid(), "invalid jump quote");
                /*@DiscountFactor*/ final double thisJump = jumps[i].currentLink().value();
                QL.require(thisJump > 0.0 && thisJump <= 1.0, "invalid  jump value");
                jumpEffect *= thisJump;
            }
            return jumpEffect * baseCurve.discount(t);
        }

        return baseCurve.discount(t);
    }

    public void setJumps() {
        final int nJumps = jumps.length;
        final Date referenceDate = baseCurve.referenceDate();
        if (this.jumpDates.length==0 && jumps.length!=0) { // turn of year dates
            this.jumpDates = new Date[nJumps];
            this.jumpTimes = new double[nJumps];
            for (int i=0; i<jumps.length; ++i) {
                jumpDates[i] = new Date(31, Month.December, referenceDate.year()+i);
            }
        } else { // fixed dates
            QL.require(jumpDates.length==nJumps, "mismatch between number of jumps and jump dates");
        }
        for (int i=0; i<nJumps; ++i) {
            jumpTimes[i] = baseCurve.timeFromReference(jumpDates[i]);
        }
        this.latestReference = referenceDate;
    }


    // template definitions

    @Override
    public void performCalculations() /* @ReadOnly */ {
        // just delegate to the bootstrapper
        bootstrap.calculate();
    }


    //
    // implements YieldTermStructure
    //

    @Override
    public double discount(final Date d, final boolean extrapolate) {
        return baseCurve.discount(d, extrapolate);
    }

    @Override
    public double discount(final Date d) {
        return baseCurve.discount(d);
    }

    @Override
    public double discount(final double t, final boolean extrapolate) {
        return baseCurve.discount(t, extrapolate);
    }

    @Override
    public double discount(final double t) {
        return baseCurve.discount(t);
    }

    @Override
    public InterestRate forwardRate(final Date d1, final Date d2, final DayCounter dayCounter, final Compounding comp, final Frequency freq, final boolean extrapolate) {
        return baseCurve.forwardRate(d1, d2, dayCounter, comp, freq, extrapolate);
    }

    @Override
    public InterestRate forwardRate(final Date d1, final Date d2, final DayCounter resultDayCounter, final Compounding comp, final Frequency freq) {
        return baseCurve.forwardRate(d1, d2, resultDayCounter, comp, freq);
    }

    @Override
    public InterestRate forwardRate(final Date d1, final Date d2, final DayCounter resultDayCounter, final Compounding comp) {
        return baseCurve.forwardRate(d1, d2, resultDayCounter, comp);
    }

    @Override
    public InterestRate forwardRate(final Date d, final Period p, final DayCounter dayCounter, final Compounding comp, final Frequency freq, final boolean extrapolate) {
        return baseCurve.forwardRate(d, p, dayCounter, comp, freq, extrapolate);
    }

    @Override
    public InterestRate forwardRate(final Date d, final Period p, final DayCounter resultDayCounter, final Compounding comp, final Frequency freq) {
        return baseCurve.forwardRate(d, p, resultDayCounter, comp, freq);
    }

    @Override
    public InterestRate forwardRate(final double time1, final double time2, final Compounding comp, final Frequency freq, final boolean extrapolate) {
        return baseCurve.forwardRate(time1, time2, comp, freq, extrapolate);
    }

    @Override
    public InterestRate forwardRate(final double t1, final double t2, final Compounding comp, final Frequency freq) {
        return baseCurve.forwardRate(t1, t2, comp, freq);
    }

    @Override
    public InterestRate forwardRate(final double t1, final double t2, final Compounding comp) {
        return baseCurve.forwardRate(t1, t2, comp);
    }

    @Override
    public double parRate(final Date[] dates, final Frequency freq, final boolean extrapolate) {
        return baseCurve.parRate(dates, freq, extrapolate);
    }


    @Override
    public double parRate(final double[] times, final Frequency frequency, final boolean extrapolate) {
        return baseCurve.parRate(times, frequency, extrapolate);
    }

    @Override
    public double parRate(final int tenor, final Date startDate, final Frequency freq, final boolean extrapolate) {
        return baseCurve.parRate(tenor, startDate, freq, extrapolate);
    }

    @Override
    public InterestRate zeroRate(final Date d, final DayCounter dayCounter, final Compounding comp, final Frequency freq, final boolean extrapolate) {
        return baseCurve.zeroRate(d, dayCounter, comp, freq, extrapolate);
    }

    @Override
    public InterestRate zeroRate(final Date d, final DayCounter resultDayCounter, final Compounding comp, final Frequency freq) {
        return baseCurve.zeroRate(d, resultDayCounter, comp, freq);
    }

    @Override
    public InterestRate zeroRate(final Date d, final DayCounter resultDayCounter, final Compounding comp) {
        return baseCurve.zeroRate(d, resultDayCounter, comp);
    }

    @Override
    public InterestRate zeroRate(final double time, final Compounding comp, final Frequency freq, final boolean extrapolate) {
        return baseCurve.zeroRate(time, comp, freq, extrapolate);
    }


    //
    // implements TermStructure
    //

    @Override
    public Calendar calendar() {
        return baseCurve.calendar();
    }

    @Override
    public DayCounter dayCounter() {
        return baseCurve.dayCounter();
    }

    @Override
    public double maxTime() {
        return baseCurve.maxTime();
    }

    @Override
    public Date referenceDate() {
        return baseCurve.referenceDate();
    }

    @Override
    public /*@Natural*/ int settlementDays() {
        return baseCurve.settlementDays();
    }

    @Override
    public double timeFromReference(final Date date) {
        return baseCurve.timeFromReference(date);
    }


    //
    // implements Extrapolator
    //

    @Override
    public boolean allowsExtrapolation() {
        return baseCurve.allowsExtrapolation();
    }

    @Override
    public void disableExtrapolation() {
        baseCurve.disableExtrapolation();
    }

    @Override
    public void enableExtrapolation() {
        baseCurve.enableExtrapolation();
    }

}