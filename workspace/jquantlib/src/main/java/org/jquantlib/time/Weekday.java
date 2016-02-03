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
 Copyright (C) 2004, 2005, 2006 Ferdinando Ametrano
 Copyright (C) 2006 Katiuscia Manzoni
 Copyright (C) 2006 Toyin Akin

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

import org.jquantlib.lang.exceptions.LibraryException;

/**
 * Day's serial number MOD 7
 * <p>
 * WEEKDAY Excel function is the same except for Sunday = 7.
 *
 * @author Richard Gomes
 */
public enum Weekday {
    Sunday(1), Monday(2), Tuesday(3), Wednesday(4), Thursday(5), Friday(6), Saturday(7);

    private final int enumValue;

    private Weekday(final int weekday) {
        this.enumValue = weekday;
    }

    /**
     * Returns a new Weekday object given its cardinality
     *
     * @param value
     *            is the cardinality from 1 (Sunday) till 7 (Saturday)
     * @return a new Weekday object given its cardinality
     */
    static public Weekday valueOf(final int value) {
        switch (value) {
        case 1:
            return Weekday.Sunday;
        case 2:
            return Weekday.Monday;
        case 3:
            return Weekday.Tuesday;
        case 4:
            return Weekday.Wednesday;
        case 5:
            return Weekday.Thursday;
        case 6:
            return Weekday.Friday;
        case 7:
            return Weekday.Saturday;
        default:
            throw new LibraryException("value must be [1,7]"); // TODO: message
        }
    }

    /**
     * Returns the week day as a number where Sunday (1) till Saturday (7)
     *
     * @return the week day as a number where Sunday (1) till Saturday (7)
     */
    public int value() {
        return enumValue;
    }

    /**
     * Returns the name of weekdays in long format
     *
     * @see Weekday#getLongFormat
     * @return the name of weekdays in long format
     */
    @Override
    public String toString() {
        switch (enumValue) {
        case 1:
            return "Sunday";
        case 2:
            return "Monday";
        case 3:
            return "Tuesday";
        case 4:
            return "Wednesday";
        case 5:
            return "Thursday";
        case 6:
            return "Friday";
        case 7:
            return "Saturday";
        }
        throw new LibraryException("value must be [1,7]"); // TODO: message
    }

    /**
     * Returns the name of weekdays in long format
     *
     * @see Weekday#toString
     * @return the name of weekdays in long format
     */
    public String getLongFormat() {
        return this.toString();
    }

    /**
     * Returns the name of weekdays in short format (3 letters)
     *
     * @return the name of weekdays in short format (3 letters)
     */
    public String getShortFormat() {
        return getAsShortFormat();
    }

    /**
     * Returns the name of weekdays in shortest format. (2 letters)
     *
     * @return the name of weekdays in shortest format (2 letters)
     */
    public String getShortestFormat() {
        return getAsShortestFormat();
    }

    /**
     * Returns the name of weekdays in short format (3 letters)
     */
    private String getAsShortFormat (){
        final StringBuilder sb = new StringBuilder();
        sb.append(this);
        sb.setLength(3);
        return sb.toString();
    }

    /**
     * Returns the name of weekdays in shortest format (2 letters)
     */
    private String getAsShortestFormat(){
        final StringBuilder sb = new StringBuilder();
        sb.append(this);
        sb.setLength(2);
        return sb.toString();

    }
}
