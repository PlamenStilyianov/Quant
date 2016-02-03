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

package org.jquantlib.termstructures;

import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;

/**
 * Base helper class for yield-curve bootstrapping
 * <p>
 * This class provides an abstraction for the instruments used to bootstrap a term structure. It is advised that a rate helper for
 * an instrument contains an instance of the actual instrument class to ensure consistency between the algorithms used during
 * bootstrapping and later instrument pricing. This is not yet fully enforced in the available rate helpers, though - only
 * SwapRateHelper and FixedCouponBondHelper contain their corresponding instrument for the time being.
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
public abstract class RateHelper extends BootstrapHelper <YieldTermStructure> {

    public RateHelper (final Handle <Quote> quote) {
        super (quote);
    }

    public RateHelper (final double quote) {
        super (quote);
    }
}
