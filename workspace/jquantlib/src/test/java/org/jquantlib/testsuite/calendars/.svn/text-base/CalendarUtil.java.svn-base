/*
 Copyright (C) 2008 Dominik Holenstein

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

import java.util.List;

import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.junit.Assert;

/**
 * This is the general test base class for Calendars including generic methods.
 *
 * @author Dominik Holenstein
 */

public class CalendarUtil {

    protected void checkHolidayList(final List<Date> expected, final Calendar calendar, final int year) {

        final List<Date> calculated = Calendar.holidayList(
                calendar,
                new Date(1, Month.January, year),
                new Date(31, Month.December, year),
                false);

        int error = 0;

        final StringBuilder sb = new StringBuilder();
        sb.append("Holidays do not match\n");

        for (final Date date : expected)
            if (!calculated.contains(date)) {
                sb.append("  >> Holiday expected but not calculated: ").append(date.weekday()).append(", ").append(date).append('\n');
                error++;
            }

        for (final Date date : calculated)
            if (!expected.contains(date)) {
                sb.append("  >> Holiday calculated but not expected: ").append(date.weekday()).append(", ").append(date).append('\n');
                error++;
            }

        if (error>0)
            Assert.fail(sb.toString());
    }

}
