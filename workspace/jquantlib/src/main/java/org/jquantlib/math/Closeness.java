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

package org.jquantlib.math;

import org.jquantlib.lang.annotation.NonNegative;

/**
 *  Follows somewhat the advice of Knuth on checking for floating-point
 * equality. The closeness relationship is:
 * <p>{@latex[
 * \mathrm{close}(x,y,n) \equiv |x-y| \leq \varepsilon |x|
 *                       \wedge |x-y| \leq \varepsilon |y|
 * }
 * <p>where {@latex$ \varepsilon} is {@latex$ n} times the machine accuracy;
 * <p>{@latex$ n} equals 42 if not given.
 *
 * @author Richard Gomes
 */
//FIXME: code review
final public class Closeness {

	//
	// Static public final methods
	//

	static public final boolean isClose(final double x, final double y) {
	    return isClose(x, y, 42);
	}

	static public final boolean isClose(final double x, final double y, @NonNegative final int n) {
        final double diff = Math.abs(x-y);
        final double tolerance = n * Constants.QL_EPSILON;
        return diff <= tolerance*Math.abs(x) &&
               diff <= tolerance*Math.abs(y);
	}

	static public final boolean isCloseEnough(final double x, final double y) {
	    return isCloseEnough(x, y, 42);
	}

	static public final boolean isCloseEnough(final double x, final double y, @NonNegative final int n) {
        final double diff = Math.abs(x-y);
        final double tolerance = n * Constants.QL_EPSILON;
        return diff <= tolerance*Math.abs(x) ||
               diff <= tolerance*Math.abs(y);
	}

}
