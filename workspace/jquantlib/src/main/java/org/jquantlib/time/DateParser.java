/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.time;

import org.jquantlib.QL;


/**
 * Helper class to parse Strings to Date
 *
 * @author Srinivas Hasti
 * @author Zahid Hussain
 *
 * @Changes: Sep 2009: Used correct method to parse date and format strings in parse method.
 *
 */
// TODO: OSGi :: remove statics
public class DateParser {

    /**
     * Convert ISO format strings to Date. Ex: 2008-03-31
     *
     * @param str
     * @return Date
     */
    public static Date parseISO(final String str) {
        QL.require(str.length() == 10 && str.charAt(4) == '-' && str.charAt(7) == '-', "invalid format"); // TODO: message

        final int year = Integer.parseInt(str.substring(0, 4));
        final int month = Integer.parseInt(str.substring(5, 7));
        final int day = Integer.parseInt(str.substring(8, 10));

        final Date date = new Date(day, Month.valueOf(month), year);
        // QL.debug(date.isoDate().toString());
        return date;
    }

    /**
     * Convert the String with separator '/' to Date using the format specified.
     *
     * For example: "2008/03/31", "yyyy/MM/dd"
     *
     * @param str
     * @param fmt
     * @return Date
     */
    public static Date parse(final String str, final String fmt) {
        String[] slist = null;
        String[] flist = null;
        int d = 0, m = 0, y = 0;

        slist = str.split("/");
        flist = fmt.split("/");

        Date date;

        if (slist.length != flist.length) {
            date = new Date();
        } else {
            for (int i = 0; i < flist.length; i++) {
                final String sub = flist[i];
                if (sub.equalsIgnoreCase("dd")) {
                    d = Integer.parseInt(slist[i]);
                } else if (sub.equalsIgnoreCase("mm")) {
                    m = Integer.parseInt(slist[i]);
                } else if (sub.equalsIgnoreCase("yyyy")) {
                    y = Integer.parseInt(slist[i]);
                    if (y < 100) {
                        y += 2000;
                    }
                }
            }
            date = new Date(d, m, y);
        }

        // QL.debug(date.isoDate().toString());
        return date;
    }

}
