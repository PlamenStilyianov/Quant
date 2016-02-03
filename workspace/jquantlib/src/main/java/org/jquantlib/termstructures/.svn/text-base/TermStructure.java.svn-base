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
import org.jquantlib.math.interpolations.Extrapolator;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * Interface for term structures
 *
 * @author Richard Gomes
 */
public abstract interface TermStructure extends Extrapolator, Observer, Observable {

    /**
     * @return the latest date for which the curve can return values
     */
    public abstract Date maxDate() /* @ReadOnly */;

    /**
     * Return the calendar used for reference date calculation
     *
     * @category Dates and Time
     * @return the calendar used for reference date calculation
     */
    public abstract Calendar calendar() /* @ReadOnly */;


    /**
     * Returns the settlementDays used for reference date calculation
     *
     * @category Dates and Time
     * @return the settlementDays used for reference date calculation
     */
    public /*@Natural*/ int settlementDays() /* @ReadOnly */;


    /**
     * This method performs a date to double conversion which represents
     * the fraction of the year between the reference date and the date passed as parameter.
     *
     * @category Dates and Time
     * @param date
     * @return the fraction of the year as a double
     */
    public abstract /*@Time*/ double timeFromReference(final Date date) /* @ReadOnly */;

    /**
     * Return the day counter used for date/double conversion
     *
     * @category Dates and Time
     * @return the day counter used for date/double conversion
     */
    public abstract DayCounter dayCounter() /* @ReadOnly */;

    /**
     * Returns the latest double for which the curve can return values
     *
     * @category Dates and Time
     * @return the latest double for which the curve can return values
     */
    public abstract /*@Time*/ double maxTime() /* @ReadOnly */;

    /**
     * Returns the Date at which discount = 1.0 and/or variance = 0.0
     *
     * @note Term structures initialized by means of this
     * constructor must manage their own reference date
     * by overriding the getReferenceDate() method.
     *
     * @category Dates and Time
     * @returns the Date at which discount = 1.0 and/or variance = 0.0
     */
    public abstract Date referenceDate() /* @ReadOnly */;

}