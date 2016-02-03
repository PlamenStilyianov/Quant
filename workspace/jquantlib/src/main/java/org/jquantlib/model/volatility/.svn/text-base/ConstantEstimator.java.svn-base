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
 Copyright (C) 2006 Joseph Wang

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

package org.jquantlib.model.volatility;

import java.util.Iterator;

import org.jquantlib.time.Date;
import org.jquantlib.time.TimeSeries;

/**
 * Constant-estimator volatility model
 * <p>
 * Volatilities are assumed to be expressed on an annual basis.
 *
 * @author Richard Gomes
 */
// TODO : Test cases
public class ConstantEstimator implements VolatilityCompositor {

	private final /* @NonNegative */int size;

	public ConstantEstimator(final/* @NonNegative */int size) {
		this.size = size;
	}

	@Override
	public void calibrate(final TimeSeries<Double> timeSeries) {
		// nothing
	}

	@Override
	public TimeSeries<Double> calculate(final TimeSeries<Double> quotes) {

	    //
	    // This method employs a different algorithm from original QuantLib/C++ sources. The reasons are:
	    // 1. The original algorithm is knowingly inefficient, fact pointed out by the original author.
	    // 2. TimeSeries as it is implemented by JQuantLib/Java does not [intentionally] allow random walk
	    //    thru its elements, which leaded us to adopt Iterators instead.
	    //

		final TimeSeries<Double> retval = new TimeSeries<Double>(Double.class);

		final Iterator<Date> it1 = quotes.navigableKeySet().iterator();
		double sumu2 = 0.0, sumu = 0.0;
		Date d1 = null;

		// advance 'size' elements and accumulate results
		for (int i=0; i<size; i++) {
		    d1 = it1.next();
		    final double u = quotes.get(d1);
            sumu  += u;
            sumu2 += u * u;
		}
		// assign first result
		retval.put(d1, Math.sqrt(sumu2/size - sumu*sumu/size/(size+1)) );

        final Iterator<Date> it2 = quotes.navigableKeySet().iterator();
        Date d2;
        while (it1.hasNext()) {
            // add a new element to calculation
            d1 = it1.next();
            final double u = quotes.get(d1);
            sumu  += u;
            sumu2 += u * u;
            // remove an old element from calculation
            d2 = it2.next();
            final double v = quotes.get(d2);
            sumu  -= v;
            sumu2 -= v * v;
            // assign next result
            retval.put(d1, Math.sqrt(sumu2/size - sumu*sumu/size/(size+1)) );
        }
        return retval;
	}

}
