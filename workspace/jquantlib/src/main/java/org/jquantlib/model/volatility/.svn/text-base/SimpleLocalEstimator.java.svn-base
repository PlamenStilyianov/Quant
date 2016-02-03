/*
 Copyright (C) 2008 Rajiv Chauhan

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
 * Simple Local Estimator volatility model
 * <p>
 * Volatilities are assumed to be expressed on an annual basis.
 *
 * @author Rajiv Chauhan
 * @author Anand Mani
 */
public class SimpleLocalEstimator {

	private final /* @Real */ double yearFraction ;

    public SimpleLocalEstimator(final /*@Real*/ double y) {
        this.yearFraction = y;
    }

    public TimeSeries<Double> calculate(final TimeSeries<Double> quotes) {
        final TimeSeries<Double> retval = new TimeSeries<Double>(Double.class);
        final Iterator<Date> dates = quotes.navigableKeySet().iterator();
    	double prev = quotes.get(dates.next());
    	while (dates.hasNext()) {
    	    final Date date = dates.next();
            final double curr = quotes.get(date) ;
            final double value = Math.abs(Math.log(curr/prev))/Math.sqrt(yearFraction) ;
            retval.put(date, value);
            prev = curr;
    	}
        return retval;
    }

}
