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
 Copyright (C) 2004, 2005, 2006 Ferdinando Ametrano
 Copyright (C) 2006 Katiuscia Manzoni
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl

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
 * Frequency of events
 *
 * @author Richard Gomes
 */
// TODO: document methods
public enum Frequency {
    /** null frequency */		        NoFrequency      (-1),
    /** only once */			        Once			 (0),
    /** once a year */			        Annual			 (1),
    /** twice a year */			        Semiannual		 (2),
    /** every fourth month */	        EveryFourthMonth (3),
    /** every third month */	        Quarterly		 (4),
    /** every second month */	        Bimonthly		 (6),
    /** once a month */			        Monthly			 (12),
    /** every fourth week */            EveryFourthWeek  (13),
    /** every second week */	        Biweekly		 (26),
    /** once a week */			        Weekly			 (52),
    /** once a day */			        Daily			 (365),
    /** some other unknown frequency */ OtherFrequency   (999);

    private final int enumValue;

    private Frequency(final int frequency) {
        this.enumValue = frequency;
    }

    static public Frequency valueOf(final int value) {
        switch (value) {
        case -1:
            return Frequency.NoFrequency;
        case 0:
            return Frequency.Once;
        case 1:
            return Frequency.Annual;
        case 2:
            return Frequency.Semiannual;
        case 3:
            return Frequency.EveryFourthMonth;
        case 4:
            return Frequency.Quarterly;
        case 6:
            return Frequency.Bimonthly;
        case 12:
            return Frequency.Monthly;
        case 13:
            return Frequency.EveryFourthWeek;
        case 26:
            return Frequency.Biweekly;
        case 52:
            return Frequency.Weekly;
        case 365:
            return Frequency.Daily;
        case 999:
            return Frequency.OtherFrequency;
        default:
            throw new LibraryException("value must be one of -1,0,1,2,3,4,6,12,13,26,52,365,999"); // TODO: message
        }
    }

    public int toInteger() {
        return this.enumValue;
    }

}
