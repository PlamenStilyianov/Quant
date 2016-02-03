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
Copyright (C) 2003, 2004 StatPro Italia srl

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

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.termstructures.AbstractYieldTermStructure;
import org.jquantlib.termstructures.TermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.Target;

/**
 * Zero-yield term structure
 * <p>
 * This abstract class acts as an adapter to YieldTermStructure allowing the programmer to implement only the
 * <tt>zeroYieldImpl(Time, bool)</tt> method in derived classes. Discount and forward are calculated from zero yields.
 * <p>
 * Rates are assumed to be annual continuous compounding.
 *
 * @see TermStructure documentation for issues regarding constructors.
 *
 * @author Richard Gomes
 */
public abstract class ZeroYieldStructure extends AbstractYieldTermStructure {

	//
	// public constructors
	//


	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param dc
	 */
	public ZeroYieldStructure() {
		this(new Actual365Fixed());
        QL.validateExperimentalMode();
	}

	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param dc
	 */
	public ZeroYieldStructure(final DayCounter dc) {
		super(dc);
        QL.validateExperimentalMode();
	}

	// ---

	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param refDate
	 * @param cal
	 * @param dc
	 */
	public ZeroYieldStructure(final Date refDate, final Calendar cal) {
		this(refDate, cal, new Actual365Fixed());
        QL.validateExperimentalMode();
	}

	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param refDate
	 * @param cal
	 * @param dc
	 */
	public ZeroYieldStructure(final Date refDate, final DayCounter dc) {
		this(refDate, new Target(), dc); // FIXME: code review : default calendar
        QL.validateExperimentalMode();
	}

	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param refDate
	 * @param cal
	 * @param dc
	 */
	public ZeroYieldStructure(final Date refDate) {
		this(refDate, new Target(), new Actual365Fixed()); // FIXME: code review : default calendar
        QL.validateExperimentalMode();
	}


	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param refDate
	 * @param cal
	 * @param dc
	 */
	public ZeroYieldStructure(final Date refDate, final Calendar cal, final DayCounter dc) {
		super(refDate, cal, dc);
        QL.validateExperimentalMode();
	}

	// ---


	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param settlementDays
	 * @param cal
	 * @param dc
	 */
	public ZeroYieldStructure(final int settlementDays, final Calendar cal) {
		this(settlementDays, cal, new Actual365Fixed());
        QL.validateExperimentalMode();
	}

	/**
	 * @see TermStructure documentation for issues regarding constructors.
	 *
	 * @param settlementDays
	 * @param cal
	 * @param dc
	 */
	public ZeroYieldStructure(final int settlementDays, final Calendar cal, final DayCounter dc) {
		super(settlementDays, cal, dc);
        QL.validateExperimentalMode();
	}


	//
	// protected methods
	//

	/**
	 * Returns the discount factor for the given date calculating it from the zero yield.
	 */
	@Override
    protected /*@DiscountFactor*/ double discountImpl(/*@Time*/ final double t) /* @ReadOnly */ {
        /*@Rate*/ final double r = zeroYieldImpl(t);
        return Math.exp(-r*t);
    }


	//
	// abstract methods
	//

	protected abstract /*@Rate*/ double zeroYieldImpl(/*@Time*/ double t) /* @ReadOnly */;

}
