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

package org.jquantlib.testsuite.calendars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;
import org.junit.Test;

/**
 * @author Srinivas Hasti
 * 
 */
public class TestCalendarReferences {

    @Test
    public void testCalendarAdvance() {
        final Calendar calendar = new Target();
        final Date today = new Date(20, Month.June, 2008);
        final Period thirtyYears = new Period(30, TimeUnit.Years);

        // check advance should return different reference
        Date nextDate = calendar.advance(today, new Period(1,TimeUnit.Days));
        assertNotSame(today, nextDate);

        nextDate = calendar.advance(today, 10, TimeUnit.Days);
        assertNotSame(today, nextDate);

        nextDate = calendar.advance(today, thirtyYears, BusinessDayConvention.Following);
        assertNotSame(today, nextDate);


        nextDate = calendar.advance(today, thirtyYears, BusinessDayConvention.Following, true);
        assertNotSame(today, nextDate);

        nextDate = calendar.advance(today, 10, TimeUnit.Days, BusinessDayConvention.Following, true);
        assertNotSame(today, nextDate);
    }

    @Test
    public void testCalendarAdjust(){
        final Calendar calendar = new Target();
        final Date today = new Date(20, Month.June, 2008);

        // check advance should return same reference
        final Date nextDate = calendar.adjust(today, BusinessDayConvention.Following);
//        assertSame(today, nextDate);
        assertEquals(today, nextDate);
    }
}
