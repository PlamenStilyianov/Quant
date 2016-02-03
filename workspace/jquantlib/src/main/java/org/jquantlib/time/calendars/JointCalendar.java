/*
 Copyright (C) 2008 Srinivas Hasti
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

package org.jquantlib.time.calendars;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Weekday;

/**
 * Depending on the chosen rule, this calendar has a set of business days given
 * by either the union or the intersection of the sets of business days of the
 * given calendars.
 *
 * @category calendars
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 * @author Zahid Hussain
 *
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Richard Gomes" })
public class JointCalendar extends Calendar {

    /**
     * Rules for joining calendars
     */
    public static enum JointCalendarRule {
        /**
         * A date is a holiday for the joint calendar if it
         * is a holiday for any of the given calendars
         */
        JoinHolidays,

        /**
         * A date is a business day for the joint calendar if it is a
         * business day for any of the given calendars
         */
        JoinBusinessDays
    };

    public JointCalendar(final Calendar c1, final Calendar c2, JointCalendarRule rule) {
        this(rule, c1, c2);
    }
    public JointCalendar(final Calendar c1, final Calendar c2) {
        this(JointCalendarRule.JoinHolidays, c1, c2);
    }

    public JointCalendar(final Calendar c1, final Calendar c2, final Calendar c3, JointCalendarRule rule) {
        this(rule, c1, c2, c3);
    }
    public JointCalendar(final Calendar c1, final Calendar c2, final Calendar c3) {
        this(JointCalendarRule.JoinHolidays, c1, c2, c3);
    }

    public JointCalendar(final Calendar c1, final Calendar c2, final Calendar c3, final Calendar c4, JointCalendarRule rule) {
        this(rule, c1, c2, c3, c4);
    }
    public JointCalendar(final Calendar c1, final Calendar c2, final Calendar c3, final Calendar c4) {
        this(JointCalendarRule.JoinHolidays, c1, c2, c3, c4);
    }

    //internal
    private JointCalendar(JointCalendarRule rule, final Calendar ...calendars) {
        this.impl = new Impl(rule, calendars);
    }

    // private final inner classes
    private final class Impl extends Calendar.Impl {

    	private JointCalendarRule rule_;
        private List<Calendar> calendars_;
                
        protected Impl(final JointCalendarRule rule, final Calendar ...calendars) {
            this.calendars_ = new ArrayList<Calendar>(calendars.length);
            for (int i=0; i<calendars.length; i++) {
                this.calendars_.add(calendars[i]);
            }
            this.rule_ = rule;
        }

        @Override
        public String name() /* @ReadOnly */{
            final StringBuilder sb = new StringBuilder();

            switch (rule_) {
            case JoinHolidays:
                sb.append("JoinHolidays(");
                break;
            case JoinBusinessDays:
                sb.append("JoinBusinessDays(");
                break;
            default:
                throw new LibraryException("unknown joint calendar rule");
            }

            int count = 0;
            for (final Calendar calendar : calendars_) {
                if (count > 0) {
                    sb.append(", ");
                }
                sb.append(calendar.name());
                count++;
            }
            sb.append(')');
            return sb.toString();
        }

        @Override
        public boolean isWeekend(final Weekday w) /* @ReadOnly */{
            switch (rule_) {
            case JoinHolidays:
                for (final Calendar calendar : calendars_) {
                    if (calendar.isWeekend(w)) {
                        return true;
                    }
                }
                return false;
            case JoinBusinessDays:
                for (final Calendar calendar : calendars_) {
                    if (!calendar.isWeekend(w)) {
                        return false;
                    }
                }
                return true;
            default:
                throw new LibraryException(UNKNOWN_MARKET);
            }
        }

        @Override
        public boolean isBusinessDay(final Date date) /* @ReadOnly */{
            switch (rule_) {
            case JoinHolidays:
                for (final Calendar calendar : calendars_) {
                    if (calendar.isHoliday(date)) {
                        return false;
                    }
                }
                return true;
            case JoinBusinessDays:
                for (final Calendar calendar : calendars_) {
                    if (calendar.isBusinessDay(date)) {
                        return true;
                    }
                }
                return false;
            default:
                throw new LibraryException(UNKNOWN_MARKET);
            }
        }
    }

}
