/*
 Copyright (C) 2007 Richard Gomes

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
 Copyright (C) 2003, 2004 StatPro Italia srl

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

package org.jquantlib.testsuite.util;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.NullCalendar;

/**
 * This class contains utility methods
 *
 * @author Richard Gomes
 */
public class Utilities {

    //
    // TODO: reimplement norm() using Iterators, aiming to identify if our API is complete
    //
    //

//    Real norm(const Iterator& begin, const Iterator& end, Real h) {
//        // squared values
//        std::vector<Real> f2(end-begin);
//        std::transform(begin,end,begin,f2.begin(),
//                       std::multiplies<Real>());
//        // numeric integral of f^2
//        Real I = h * (std::accumulate(f2.begin(),f2.end(),0.0)
//                      - 0.5*f2.front() - 0.5*f2.back());
//        return std::sqrt(I);
//    }


    public static double norm(final Array arr, final double h) {

        //copy arr into f2, and square each value
        final Array f2 = new Array(arr.size());
        for(int i = 0; i < arr.size(); i++)
        {
            final double d = arr.get(i);
            f2.set(i, d*d);
        }
        // squared values
        //std::vector<Real> f2(end-begin);
        //std::transform(begin,end,begin,f2.begin(),
        //               std::multiplies<Real>());

        // numeric integral of f^2
        //double I = h * (std::accumulate(f2.begin(),f2.end(),0.0)
        //              - 0.5*f2.front() - 0.5*f2.back());
        //I believe this code is adding together the values in f2 (initialized to 0.0)
        //then subtracting 0.5 * front() and also subtracting 0.5 * back()
        double I = 0;
        for(int i = 0; i < f2.size(); i++)
            I += f2.get(i);

        //not sure about this...
        I -= 0.5 * f2.first();
        I -= 0.5 * f2.last();
        I *= h;

        return Math.sqrt(I);
    }

    static public double relativeError(final double x1, final double x2, final double reference) {
        if (reference != 0.0)
            return Math.abs(x1 - x2) / reference;
        else
            // fall back to absolute error
            return Math.abs(x1 - x2);
    }

    static public YieldTermStructure flatRate(final Date today, final Quote forward, final DayCounter dc) {
        return new FlatForward(today, new Handle<Quote>(forward), dc);
    }

    static public YieldTermStructure flatRate(final Date today, final/* @Rate */double forward, final DayCounter dc) {
        return flatRate(today, new SimpleQuote(forward), dc);
    }

    static public YieldTermStructure flatRate(final Quote forward, final DayCounter dc) {
        return new FlatForward(0, new NullCalendar(), new Handle<Quote>(forward), dc);
    }

    static public YieldTermStructure flatRate(final/* @Rate */double forward, final DayCounter dc) {
        return flatRate(new SimpleQuote(forward), dc);
    }

    static public BlackVolTermStructure flatVol(final Date today, final Quote vol, final DayCounter dc) {
        return new BlackConstantVol(today, new NullCalendar(), new Handle<Quote>(vol), dc);
    }

    static public BlackVolTermStructure flatVol(final Date today, final/* @Volatility */double vol, final DayCounter dc) {
        return flatVol(today, new SimpleQuote(vol), dc);
    }

    static public BlackVolTermStructure flatVol(final Quote vol, final DayCounter dc) {
        return new BlackConstantVol(0, new NullCalendar(), new Handle<Quote>(vol), dc);
    }

    static public BlackVolTermStructure flatVol(final/* @Volatility */double vol, final DayCounter dc) {
        return flatVol(new SimpleQuote(vol), dc);
    }

}
