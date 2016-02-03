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
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl
 Copyright (C) 2004 Jeff Yu

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

package org.jquantlib.time;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;

/**
 * This class provides methods for determining whether a date is a business day
 * or a holiday for a given market, and for incrementing/decrementing a date of
 * a given number of business days.
 * <p>
 * A calendar should be defined for specific exchange holiday schedule or for
 * general country holiday schedule. Legacy city holiday schedule calendars will
 * be moved to the exchange/country convention.
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Zahid Hussain", "Richard Gomes" })
public class Calendar {

    //
    // public static fields
    //

    public static final String UNKNOWN_MARKET = "Unknown market";
    public static final String UKNOWN_BUSINESS_DAY_CONVENTION = "Unknown business day convention";


    //
    // protected fields
    //

    /**
     * All extended classes *must* initialize this field.
     *
     * @see <a href="http://www.boost.org/doc/libs/1_40_0/libs/smart_ptr/sp_techniques.html#pimpl">"Pimpl" idiom</a>
     */
    protected Impl impl;


    //
    // public constructors
    //

    public Calendar() {
        // empty!
    }


    //
    // public methods
    //

    /**
     * Returns whether or not the calendar is initialized
     */
    public boolean empty() /* @ReadOnly */{
        return impl == null;
    }

    /**
     * Returns the name of the calendar. warning This method is used for output
     * and comparison between calendars. It is <b>not</b> meant to be used for
     * writing switch-on-type code.
     *
     * @return String - name of calendar
     */
    public String name() /* @ReadOnly */{
        return impl.name();
    }

    /**
     * Returns <tt>true</tt> if the date is a business day for the given market.
     */
    public boolean isBusinessDay(final Date d) /* @ReadOnly */{
        if (impl.addedHolidays.contains(d))
            return false;
        if (impl.removedHolidays.contains(d))
            return true;
        return impl.isBusinessDay(d);
    }

    /**
     * Returns <tt>true</tt> if the date is a holiday for the given market.
     */
    public boolean isHoliday(final Date d) /* @ReadOnly */{
        return !isBusinessDay(d);
    }

    /**
     * Returns <tt>true</tt> iff the weekday is part of the weekend for the
     * given market.
     */
    public boolean isWeekend(final Weekday w) /* @ReadOnly */{
        return impl.isWeekend(w);
    }

    /**
     * Returns <tt>true</tt> if the date is last business day for the month in
     * given market.
     */
    public boolean isEndOfMonth(final Date d) /* @ReadOnly */{
        return (d.month() != adjust(d.add(1)).month());
    }

    /**
     * Returns last business day of the month to which the given date belongs
     *
     * @param d
     * @return last business Date based on passed date
     */
    public Date endOfMonth(final Date d) /* @ReadOnly */{
        return adjust(Date.endOfMonth(d), BusinessDayConvention.Preceding);
    }

    /**
     * Adds a date to the set of holidays for the given calendar.
     * <p>
     * <b>NOTE</b>: This method does not affect <i>pre-defined</i> calendars, returning
     * silently.
     * */
    public void addHoliday(final Date d) {
        // if d was a genuine holiday previously removed, revert the change
        impl.removedHolidays.remove(d);
        // if it's already a holiday, leave the calendar alone.
        // Otherwise, add it.
        if (impl.isBusinessDay(d)) {
            impl.addedHolidays.add(d);
        }
    }

    /**
     * Removes a date from the set of holidays for the given calendar.
     * <p>
     * <b>NOTE</b>: This method does not affect <i>pre-defined</i> calendars, returning
     * silently.
     */
    public void removeHoliday(final Date d) {
        // if d was an artificially-added holiday, revert the change
        impl.addedHolidays.remove(d);
        // if it's already a business day, leave the calendar alone.
        // Otherwise, add it.
        if (!impl.isBusinessDay(d)) {
            impl.removedHolidays.add(d);
        }
    }

    /**
     * Returns the holidays between two dates
     */
    public static List<Date> holidayList(final Calendar c, final Date from, final Date to, final boolean includeWeekEnds) {

        QL.require(to.gt(from), "'from' date (" + from.toString()
                + ") must be earlier than 'to' date (" + to.toString() + ")");

        final List<Date> result = new ArrayList<Date>();
        for (Date d = from.clone(); d.le(to); d=d.add(1)) {
            if (c.isHoliday(d)
                    && (includeWeekEnds || !c.isWeekend(d.weekday()))) {
                result.add(d);
            }
        }
        return result;
    }

    /**
     * Adjusts a non-business day to the appropriate near business day
     * with respect to the given convention.
     * <p>
     * Assumes convention BusinessDayConvention.Following
     *
     * @note The input date is not modified
     */
    public Date adjust(final Date date) /* @ReadOnly */ {
        return adjust(date, BusinessDayConvention.Following);
    }


    /**
     * Adjusts a non-business day to the appropriate near business day
     * with respect to the given convention.
     *
     * @note The input date is not modified
     */
    public Date adjust(final Date d, final BusinessDayConvention c) /* @ReadOnly */ {
        if (c == BusinessDayConvention.Unadjusted)
            return d.clone();
        final Date d1 = d.clone();
        if (c == BusinessDayConvention.Following || c == BusinessDayConvention.ModifiedFollowing) {
            while (isHoliday(d1)) {
                d1.inc();
            }
            if (c == BusinessDayConvention.ModifiedFollowing) {
                if (d1.month() != d.month())
                    return adjust(d, BusinessDayConvention.Preceding);
            }
        } else if (c == BusinessDayConvention.Preceding || c == BusinessDayConvention.ModifiedPreceding) {
            while (isHoliday(d1)) {
                d1.dec();
            }
            if (c == BusinessDayConvention.ModifiedPreceding && d1.month() != d.month())
                return adjust(d, BusinessDayConvention.Following);
        } else
            throw new LibraryException(UKNOWN_BUSINESS_DAY_CONVENTION);
        return d1;
    }

    /**
     * Advances the given date as specified by the given period and
     * returns the result.
     *
     * @note The input date is not modified.
     */

    public Date advance(final Date date, final Period period, final BusinessDayConvention convention) /* @ReadOnly */ {
        return advance(date, period, convention, false);
    }


    /**
     * Advances the given date as specified by the given period and
     * returns the result.
     *
     * @note The input date is not modified.
     */
    public Date advance(
            final Date date,
            final Period period,
            final BusinessDayConvention convention,
            final boolean endOfMonth) /* @ReadOnly */{
        return advance(date, period.length(), period.units(), convention, endOfMonth);
    }




    /**
     * Advances the given date of the given number of business days and
     * returns the result.
     * @note The input date is not modified.
     */
    public Date advance(
            final Date date,
            final int n,
            final TimeUnit unit) /* @ReadOnly */ {
        return advance(date, n, unit, BusinessDayConvention.Following, false);
    }

    /**
     * Advances the given date as specified by the given period and
     * returns the result.
     *
     * @note The input date is not modified.
     */
    public Date advance(final Date date, final Period period) /* @ReadOnly */ {
        return advance(date, period, BusinessDayConvention.Following, false);
    }

    /**
     * Advances the given date of the given number of business days and
     * returns the result.
     * @note The input date is not modified.
     */
    public Date advance(
            final Date d,
            int n,
            final TimeUnit unit,
            final BusinessDayConvention c,
            final boolean endOfMonth) /* @ReadOnly */{
        QL.require(d != null && !d.isNull(), "null date");
        if (n == 0)
            return adjust(d, c);
        else if (unit == TimeUnit.Days) {
            final Date d1 = d.clone();
            if (n > 0) {
                while (n > 0) {
                    d1.inc();
                    while (isHoliday(d1)) {
                        d1.inc();
                    }
                    n--;
                }
            } else {
                while (n < 0) {
                    d1.dec();
                    while (isHoliday(d1)) {
                        d1.dec();
                    }
                    n++;
                }
            }
            return d1;
        } else if (unit == TimeUnit.Weeks) {
            final Date d1 = d.add(new Period(n, unit));
            return adjust(d1, c);
        } else {
            final Date d1 = d.add(new Period(n, unit));

            // we are sure the unit is Months or Years
            if (endOfMonth && isEndOfMonth(d))
                return endOfMonth(d1);

            return adjust(d1, c);
        }
    }

    /**
     * Calculates the number of business days between two given dates and
     * returns the result.
     */
    public int businessDaysBetween(final Date from, final Date to) /* @ReadOnly */{
        return businessDaysBetween(from, to, true, false);
    }

    public int businessDaysBetween(final Date from, final Date to,
            final boolean includeFirst, final boolean includeLast) /* @ReadOnly */{
        int wd = 0;
        if (from.ne(to)) {
            if (from.lt(to)) {
                // the last one is treated separately to avoid
                // incrementing Date::maxDate()
                for (Date d = from.clone(); d.lt(to); d=d.add(1)) {
                    if (isBusinessDay(d)) {
                        ++wd;
                    }
                }
                if (isBusinessDay(to)) {
                    ++wd;
                }
            } else if (from.gt(to)) {
                for (Date d = to.clone(); d.lt(from); d=d.add(1)) {
                    if (isBusinessDay(d)) {
                        ++wd;
                    }
                }
                if (isBusinessDay(from)) {
                    ++wd;
                }
            }

            if (isBusinessDay(from) && !includeFirst) {
                wd--;
            }
            if (isBusinessDay(to) && !includeLast) {
                wd--;
            }

            if (from.gt(to)) {
                wd = -wd;
            }
        }

        return wd;
    }


    //
    // public static methods
    //

    public static boolean eq(final Calendar c1, final Calendar c2) {
        return (c1.empty() && c2.empty()) || (!c1.empty() && !c2.empty() && c1.name().equals(c2.name()));
    }

    public static boolean ne(final Calendar c1, final Calendar c2) {
        return !eq(c1, c2);
    }


    //
    // protected inner classes
    //

    protected abstract class Impl {

        private final Set<Date> addedHolidays = new HashSet<Date>();
        private final Set<Date> removedHolidays = new HashSet<Date>();

        protected Impl() {
            // only extended classes can instantiate
        }

        public abstract String name();
        public abstract boolean isBusinessDay(final Date d);
        public abstract boolean isWeekend(Weekday w);

    }


    protected abstract class WesternImpl extends Impl {

        private final short easterMonday[] = {
                     98,  90, 103,  95, 114, 106,  91, 111, 102,   // 1901-1909
                87, 107,  99,  83, 103,  95, 115,  99,  91, 111,   // 1910-1919
                96,  87, 107,  92, 112, 103,  95, 108, 100,  91,   // 1920-1929
               111,  96,  88, 107,  92, 112, 104,  88, 108, 100,   // 1930-1939
                85, 104,  96, 116, 101,  92, 112,  97,  89, 108,   // 1940-1949
               100,  85, 105,  96, 109, 101,  93, 112,  97,  89,   // 1950-1959
               109,  93, 113, 105,  90, 109, 101,  86, 106,  97,   // 1960-1969
                89, 102,  94, 113, 105,  90, 110, 101,  86, 106,   // 1970-1979
                98, 110, 102,  94, 114,  98,  90, 110,  95,  86,   // 1980-1989
               106,  91, 111, 102,  94, 107,  99,  90, 103,  95,   // 1990-1999
               115, 106,  91, 111, 103,  87, 107,  99,  84, 103,   // 2000-2009
                95, 115, 100,  91, 111,  96,  88, 107,  92, 112,   // 2010-2019
               104,  95, 108, 100,  92, 111,  96,  88, 108,  92,   // 2020-2029
               112, 104,  89, 108, 100,  85, 105,  96, 116, 101,   // 2030-2039
                93, 112,  97,  89, 109, 100,  85, 105,  97, 109,   // 2040-2049
               101,  93, 113,  97,  89, 109,  94, 113, 105,  90,   // 2050-2059
               110, 101,  86, 106,  98,  89, 102,  94, 114, 105,   // 2060-2069
                90, 110, 102,  86, 106,  98, 111, 102,  94, 114,   // 2070-2079
                99,  90, 110,  95,  87, 106,  91, 111, 103,  94,   // 2080-2089
               107,  99,  91, 103,  95, 115, 107,  91, 111, 103,   // 2090-2099
                88, 108, 100,  85, 105,  96, 109, 101,  93, 112,   // 2100-2109
                97,  89, 109,  93, 113, 105,  90, 109, 101,  86,   // 2110-2119
               106,  97,  89, 102,  94, 113, 105,  90, 110, 101,   // 2120-2129
                86, 106,  98, 110, 102,  94, 114,  98,  90, 110,   // 2130-2139
                95,  86, 106,  91, 111, 102,  94, 107,  99,  90,   // 2140-2149
               103,  95, 115, 106,  91, 111, 103,  87, 107,  99,   // 2150-2159
                84, 103,  95, 115, 100,  91, 111,  96,  88, 107,   // 2160-2169
                92, 112, 104,  95, 108, 100,  92, 111,  96,  88,   // 2170-2179
               108,  92, 112, 104,  89, 108, 100,  85, 105,  96,   // 2180-2189
               116, 101,  93, 112,  97,  89, 109, 100,  85, 105    // 2190-2199
        };

        @Override
        public boolean isWeekend(final Weekday w) {
            return w == Weekday.Saturday || w == Weekday.Sunday;
        }

        /**
         *  expressed relative to first day of year
         * @param y - year
         * @return
         */
        protected int easterMonday(final int y) {
            return easterMonday[y-1901];
        }
    }


    protected abstract class OrthodoxImpl extends Impl {

        private final short easterMonday[] = {
                     105, 118, 110, 102, 121, 106, 126, 118, 102,   // 1901-1909
                122, 114,  99, 118, 110,  95, 115, 106, 126, 111,   // 1910-1919
                103, 122, 107,  99, 119, 110, 123, 115, 107, 126,   // 1920-1929
                111, 103, 123, 107,  99, 119, 104, 123, 115, 100,   // 1930-1939
                120, 111,  96, 116, 108, 127, 112, 104, 124, 115,   // 1940-1949
                100, 120, 112,  96, 116, 108, 128, 112, 104, 124,   // 1950-1959
                109, 100, 120, 105, 125, 116, 101, 121, 113, 104,   // 1960-1969
                117, 109, 101, 120, 105, 125, 117, 101, 121, 113,   // 1970-1979
                 98, 117, 109, 129, 114, 105, 125, 110, 102, 121,   // 1980-1989
                106,  98, 118, 109, 122, 114, 106, 118, 110, 102,   // 1990-1999
                122, 106, 126, 118, 103, 122, 114,  99, 119, 110,   // 2000-2009
                 95, 115, 107, 126, 111, 103, 123, 107,  99, 119,   // 2010-2019
                111, 123, 115, 107, 127, 111, 103, 123, 108,  99,   // 2020-2029
                119, 104, 124, 115, 100, 120, 112,  96, 116, 108,   // 2030-2039
                128, 112, 104, 124, 116, 100, 120, 112,  97, 116,   // 2040-2049
                108, 128, 113, 104, 124, 109, 101, 120, 105, 125,   // 2050-2059
                117, 101, 121, 113, 105, 117, 109, 101, 121, 105,   // 2060-2069
                125, 110, 102, 121, 113,  98, 118, 109, 129, 114,   // 2070-2079
                106, 125, 110, 102, 122, 106,  98, 118, 110, 122,   // 2080-2089
                114,  99, 119, 110, 102, 115, 107, 126, 118, 103,   // 2090-2099
                123, 115, 100, 120, 112,  96, 116, 108, 128, 112,   // 2100-2109
                104, 124, 109, 100, 120, 105, 125, 116, 108, 121,   // 2110-2119
                113, 104, 124, 109, 101, 120, 105, 125, 117, 101,   // 2120-2129
                121, 113,  98, 117, 109, 129, 114, 105, 125, 110,   // 2130-2139
                102, 121, 113,  98, 118, 109, 129, 114, 106, 125,   // 2140-2149
                110, 102, 122, 106, 126, 118, 103, 122, 114,  99,   // 2150-2159
                119, 110, 102, 115, 107, 126, 111, 103, 123, 114,   // 2160-2169
                 99, 119, 111, 130, 115, 107, 127, 111, 103, 123,   // 2170-2179
                108,  99, 119, 104, 124, 115, 100, 120, 112, 103,   // 2180-2189
                116, 108, 128, 119, 104, 124, 116, 100, 120, 112    // 2190-2199
        };

        @Override
        public boolean isWeekend(final Weekday w) {
            return w == Weekday.Saturday || w == Weekday.Sunday;
        }

        /**
         * @return the offset of the Easter Monday relative to the
         * first day of the year
         */
        protected final int easterMonday(final int year) {
            return easterMonday[year-1901];
        }

    }

}
