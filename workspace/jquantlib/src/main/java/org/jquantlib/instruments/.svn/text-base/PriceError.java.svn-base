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
 Copyright (C) 2007 StatPro Italia srl

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
package org.jquantlib.instruments;

import org.jquantlib.QL;
import org.jquantlib.math.Ops.DoubleOp;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.quotes.SimpleQuote;

/**
 *
 * @author Richard Gomes
 */
public class PriceError implements DoubleOp {

    private final PricingEngine engine;
    private final Instrument.ResultsImpl results;
    private final SimpleQuote vol;
    private final double targetValue;


    public PriceError(final PricingEngine engine, final SimpleQuote vol, final double targetValue) {
        this.engine = engine;
        this.vol = vol;
        this.targetValue = targetValue;
        this.results = (Instrument.ResultsImpl) engine.getResults();
        QL.require(results != null, "pricing engine does not supply needed results"); // TODO: message
    }


    //
    // implements Ops.DoubleOp
    //

    @Override
    public double op(/*@Volatility*/ final double x) /* @ReadOnly */ {
        vol.setValue(x);
        engine.calculate();

        return results.value - targetValue;
    }

}
