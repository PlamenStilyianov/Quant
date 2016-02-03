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

import org.jquantlib.QL;
import org.jquantlib.lang.iterators.Iterables;
import org.jquantlib.time.Date;
import org.jquantlib.time.TimeSeries;

/**
 * GARCH Volatility Model
 * <p>
 * Volatilities are assumed to be expressed on an annual basis.
 *
 * @author Rajiv Chauhan
 */
//TODO : Test cases
public class Garch11 implements VolatilityCompositor {

	private /* @Real */ double alpha ;
	private /* @Real */ double beta ;
	private /* @Real */ double gamma ;
	private /* @Real */ double v ;

	public Garch11 (final double alpha, final double beta, final double v) {
		this.alpha = alpha ;
		this.beta = beta ;
		this.v = v ;
		this.gamma = (1 - alpha - beta) ;
	}

	public Garch11(final TimeSeries<Double> qs) {
        calibrate(qs);
    }

	@Override
	public TimeSeries<Double> calculate(final TimeSeries<Double> vs) {
		return calculate(vs, alpha, beta, gamma* v);
	}

	@Override
	public void calibrate(final TimeSeries<Double> timeSeries) {
	    // nothing
	}

	protected double costFunction (final TimeSeries<Double> vs, final double alpha, final double beta, final double omega) {
		final TimeSeries<Double> test = calculate(vs, alpha, beta, omega);
        QL.require(test.size() == vs.size(), "quote and test values do not match"); // TODO: message
        double retval = 0.0;
        for (final Date date : Iterables.unmodifiableIterable(test.navigableKeySet())) {
            double v = test.get(date);
            double u = vs.get(date);
            v *= v;
            u *= u;
            retval += 2.0 * Math.log(v) + u/(v*v) ;
        }
		return retval ;
	}

	private TimeSeries<Double> calculate(final TimeSeries<Double> vs, final double alpha, final double beta, final double omega) {
		final TimeSeries<Double> retValue = new TimeSeries<Double>(Double.class);
        final Iterator<Date> dates = vs.navigableKeySet().iterator();
		Date date = dates.next();

		final double zerothDayValue = vs.get(date);
		retValue.put(date, zerothDayValue) ;
		double u = 0;
        double sigma2 = zerothDayValue * zerothDayValue ;
        while (dates.hasNext()) {
            date = dates.next();
            u = vs.get(date);
            sigma2 = (omega * u * u) + (beta * sigma2) ;
            retValue.put(date, Math.sqrt(sigma2)) ;
        }
		return retValue ;
	}
}
