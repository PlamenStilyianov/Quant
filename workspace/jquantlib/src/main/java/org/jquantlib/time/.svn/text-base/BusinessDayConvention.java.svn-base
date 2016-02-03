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

/**
 * Business Day conventions
 *
 * These conventions specify the algorithm used to adjust a date in case
 * it is not a valid business day.
 *
 * @author Richard Gomes
 */
public enum BusinessDayConvention {
	// ISDA
	/**
	 * Choose the first business day after the given holiday.
	 */
	Following,

	/**
	 * Choose the first business day after
	 * the given holiday unless it belongs
	 * to a different month, in which case
	 * choose the first business day before
	 * the holiday.
	 */
	ModifiedFollowing,

	/**
	 * Choose the first business day before
	 * the given holiday.
	 */
	Preceding,

	// NON ISDA
	/**
	 * Choose the first business day before
	 * the given holiday unless it belongs
	 * to a different month, in which case
	 * choose the first business day after
	 * the holiday.
	 */
	ModifiedPreceding,

	/**
	 * Do not adjust.
	 */
	Unadjusted;
}
