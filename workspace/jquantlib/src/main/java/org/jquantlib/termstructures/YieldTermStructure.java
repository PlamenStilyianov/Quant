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

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;


/**
 * Interest rate term structure
 *
 * @author Richard Gomes
 */
public abstract interface YieldTermStructure extends TermStructure {

    /**
     * Return the implied zero-yield rate for a given date or time. In the former case, the time is calculated as a
     * fraction of year from the reference date.
     * <p>
     * The resulting interest rate has the required day-counting rule.
     *
     * @category zero yield rates
     */
    public abstract InterestRate zeroRate(
            final Date d,
            final DayCounter resultDayCounter,
            final Compounding comp);

    /**
     * Return the implied zero-yield rate for a given date or time. In the former case, the time is calculated as a
     * fraction of year from the reference date.
     * <p>
     * The resulting interest rate has the required day-counting rule.
     *
     * @category zero yield rates
     */
    public abstract InterestRate zeroRate(
            final Date d,
            final DayCounter resultDayCounter,
            final Compounding comp,
            final Frequency freq);

    /**
     * Return the implied zero-yield rate for a given date or time. In the former case, the time is calculated as a
     * fraction of year from the reference date.
     * <p>
     * The resulting interest rate has the required day-counting rule.
     *
     * @category zero yield rates
     */
    public abstract InterestRate zeroRate(
            final Date d,
            final DayCounter dayCounter,
            final Compounding comp,
            final Frequency freq,
            boolean extrapolate);

    /**
     * Return the implied zero-yield rate for a given date or time. In the former case, the time is calculated as a
     * fraction of year from the reference date.
     * <p>
     * The resulting interest rate has the same day-counting rule used by the
     * term structure. The same rule should be used for calculating the passed
     * double t.
     *
     * @category zero yield rates
     */
    public abstract InterestRate zeroRate(
            final/*@Time*/double time,
            final Compounding comp,
            final Frequency freq,
            boolean extrapolate);

    /**
     * Returns the implied forward interest rate between two dates
     * or times. In the former case, times are calculated as fractions of year
     * from the reference date. The resulting interest rate has the required
     * day-counting rule.
     * <p>
     * Dates are not adjusted for holidays
     *
     * @category forward rates
     */
    public abstract InterestRate forwardRate(
            final Date d1,
            final Date d2,
            final DayCounter resultDayCounter,
            final Compounding comp);

    /**
     * Returns the implied forward interest rate between two dates
     * or times. In the former case, times are calculated as fractions of year
     * from the reference date. The resulting interest rate has the required
     * day-counting rule.
     *
     * @category forward rates
     *
     * @see #forwardRate(Date, Date, DayCounter, Compounding)
     */
    public abstract InterestRate forwardRate(
            final Date d1,
            final Date d2,
            final DayCounter resultDayCounter,
            final Compounding comp,
            final Frequency freq);

    /**
     * Returns the implied forward interest rate between two dates
     * or times. In the former case, times are calculated as fractions of year
     * from the reference date. The resulting interest rate has the required
     * day-counting rule.
     *
     * @category forward rates
     *
     * @see #forwardRate(Date, Date, DayCounter, Compounding)
     */
    public abstract InterestRate forwardRate(
            final Date d1,
            final Date d2,
            final DayCounter dayCounter,
            final Compounding comp,
            final Frequency freq,
            boolean extrapolate);

    /**
     * Returns the implied forward interest rate between two dates
     * or times. In the former case, times are calculated as fractions of year
     * from the reference date. The resulting interest rate has the required
     * day-counting rule.
     *
     * @category forward rates
     *
     * @see YieldTermStructure#forwardRate(Date, Date, DayCounter,
     *      org.jquantlib.termstructures.InterestRate.Compounding, Frequency)
     */
    public abstract InterestRate forwardRate(
            final/*@Time*/double t1,
            final/*@Time*/double t2,
            final Compounding comp);

    /**
     * Returns the implied forward interest rate between two dates
     * or times. In the former case, times are calculated as fractions of year
     * from the reference date. The resulting interest rate has the required
     * day-counting rule.
     *
     * @category forward rates
     *
     * @see YieldTermStructure#forwardRate(Date, Date, DayCounter,
     *      org.jquantlib.termstructures.InterestRate.Compounding, Frequency)
     */
    public abstract InterestRate forwardRate(
            final/* @Time */double t1,
            final/* @Time */double t2,
            final Compounding comp,
            final Frequency freq);

    /**
     * Returns the implied forward interest rate between two dates
     * or times. In the former case, times are calculated as fractions of year
     * from the reference date. The resulting interest rate has the required
     * day-counting rule.
     * <p>
     * The resulting interest rate has the same day-counting rule used by the
     * term structure. The same rule should be used for the calculating the
     * passed times t1 and t2.
     *
     * @category forward rates
     */
    public abstract InterestRate forwardRate(
            final/* @Time */double time1,
            final/* @Time */double time2,
            final Compounding comp,
            final Frequency freq,
            boolean extrapolate);

    /**
     * Returns the implied forward interest rate between two dates
     * or times. In the former case, times are calculated as fractions of year
     * from the reference date. The resulting interest rate has the required
     * day-counting rule.
     *
     * @category forward rates
     *
     * @see #forwardRate(Date, Date, DayCounter, Compounding)
     */
    public abstract InterestRate forwardRate(
            final Date d,
            final Period p,
            final DayCounter resultDayCounter,
            Compounding comp,
            Frequency freq);

    /**
     * @category forward rates
     *
     * @see #forwardRate(Date, Date, DayCounter, Compounding)
     */
    public abstract InterestRate forwardRate(
            final Date d,
            final Period p,
            final DayCounter dayCounter,
            final Compounding comp,
            final Frequency freq,
            boolean extrapolate);

    /**
     * Returns the discount factor for a given date or time. In the
     * former case, the double is calculated as a fraction of year from the
     * reference date.
     *
     * @category discount factors
     */
    public abstract/* @DiscountFactor */double discount(final Date d);

    /**
     * Returns the discount factor for a given date or time. In the
     * former case, the double is calculated as a fraction of year from the
     * reference date.
     *
     * @category discount factors
     *
     * @see org.jquantlib.termstructures.YieldTermStructureImpl#discount(org.jquantlib.time.Date, boolean)
     */
    public abstract/* @DiscountFactor */double discount(final Date d, boolean extrapolate);

    /**
     * Returns the discount factor for a given date or time. In the
     * former case, the double is calculated as a fraction of year from the
     * reference date.
     * <p>
     * The same day-counting rule used by the term structure should be used for
     * calculating the passed double t.
     *
     * @category discount factors
     */
    public abstract/* @DiscountFactor */double discount(final/* @Time */double t);

    /**
     * Returns the discount factor for a given date or time. In the
     * former case, the double is calculated as a fraction of year from the
     * reference date.
     *
     * @category discount factors
     *
     * @see org.jquantlib.termstructures.YieldTermStructureImpl#discount(double, boolean)
     */
    public abstract/* @DiscountFactor */double discount(final/* @Time */double t, boolean extrapolate);

    /**
     * Returns the implied par rate for a given sequence of payments at the given dates or times. In the former case, times are
     * calculated as fractions of year from the reference date.
     *
     * @category par rates
     *
     * @note though somewhat related to a swap rate, this method is not to be used for the fair rate of a real swap, since it does
     *       not take into account all the market conventions' details. The correct way to evaluate such rate is to instantiate a
     *       SimpleSwap with the correct conventions, pass it the term structure and call the swap's fairRate() method.
     */
    public abstract/* @Rate */double parRate(int tenor, final Date startDate, final Frequency freq, boolean extrapolate);

    /**
     * Returns the implied par rate for a given sequence of payments at the given dates or times. In the former case, times are
     * calculated as fractions of year from the reference date.
     *
     * @category par rates
     *
     * @note though somewhat related to a swap rate, this method is not to be used for the fair rate of a real swap, since it does
     *       not take into account all the market conventions' details. The correct way to evaluate such rate is to instantiate a
     *       SimpleSwap with the correct conventions, pass it the term structure and call the swap's fairRate() method.
     *
     * @param dates
     * @param freq
     * @param extrapolate
     * @return the first date in the vector must equal the start date; the following dates must equal the payment dates.
     *
     * @see YieldTermStructure#parRate(int, Date, Frequency, boolean)
     */
    public abstract/* @Rate */double parRate(final Date[] dates, final Frequency freq, boolean extrapolate);

    /**
     * Returns the implied par rate for a given sequence of payments at the given dates or times. In the former case, times are
     * calculated as fractions of year from the reference date.
     *
     * @category par rates
     *
     * @note though somewhat related to a swap rate, this method is not to be used for the fair rate of a real swap, since it does
     *       not take into account all the market conventions' details. The correct way to evaluate such rate is to instantiate a
     *       SimpleSwap with the correct conventions, pass it the term structure and call the swap's fairRate() method.
     *
     * @return the first double in the vector must equal the start time; the
     *         following times must equal the payment times.
     *
     * @see YieldTermStructure#parRate(int, Date, Frequency, boolean)
     */
    public abstract/* @Rate */double parRate(final/* @Time */double[] times, final Frequency frequency, boolean extrapolate);

}