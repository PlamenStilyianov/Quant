/*
 Copyright (C) 2008 Anand Mani

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

package org.jquantlib.math;

import java.util.Iterator;

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.Series;

/**
 * Interval Price
 *
 * @author Anand Mani
 */
public class IntervalPrice {

    private final static String UNKNOWN_PRICE_TYPE = "unknown price type";

    //
    // private fields
    //

    private double open;
    private double close;
    private double high;
    private double low;


    //
    // public constructors
    //

    public IntervalPrice() {
    	this(Constants.NULL_REAL, Constants.NULL_REAL, Constants.NULL_REAL, Constants.NULL_REAL);
    }
    
    
    public IntervalPrice(
            final /*@Real*/ double open,
            final /*@Real*/ double close,
            final /*@Real*/ double high,
            final /*@Real*/ double low) {
        setValues(open, close, high, low);
    }


    //
    // public methods
    //

    public /*@Real*/ double open() /*@ReadOnly*/ {
        return this.open;
    }

    public /*@Real*/ double close() /*@ReadOnly*/ {
        return this.close;
    }

    public /*@Real*/ double high() /*@ReadOnly*/ {
        return this.high;
    }

    public /*@Real*/ double low() /*@ReadOnly*/ {
        return this.low;
    }

    public /*@Real*/ double value(final IntervalPrice.Type type) /*@ReadOnly*/ {
        switch (type) {
        case Open:
            return this.open;
        case Close:
            return this.close;
        case High:
            return this.high;
        case Low:
            return this.low;
        default:
            throw new LibraryException(UNKNOWN_PRICE_TYPE); // QA:[RG]::verified
        }
    }

    public void setValue(final Type type, final /*@Real*/ double value) {
        switch (type) {
        case Open:
            this.open = value;
            break;
        case Close:
            this.close = value;
            break;
        case High:
            this.high = value;
            break;
        case Low:
            this.low = value;
            break;
        default:
            throw new LibraryException(UNKNOWN_PRICE_TYPE); // QA:[RG]::verified
        }
    }

    public void setValues(
            final /*@Real*/ double open, final /*@Real*/ double close,
            final /*@Real*/ double high, final /*@Real*/ double low) {
        this.open  = open;
        this.close = close;
        this.high  = high;
        this.low   = low;
    }



    //
    // public static methods
    //

    public static <K> Series<K, IntervalPrice> makeSeries(
    		final Class<K> classK,
            final K[] date,
            final double[] open,
            final double[] close,
            final double[] high,
            final double[] low) {

        final int dsize = date.length;
        if (open.length != dsize || close.length != dsize || high.length != dsize || low.length != dsize)
            throw new LibraryException("array sizes mismatch"); // QA:[RG]::verified

        final Series<K, IntervalPrice> retval = new Series<K, IntervalPrice>(classK, IntervalPrice.class);
        for (int i=0; i< dsize; i++) {
            retval.put(date[i], new IntervalPrice(open[i], close[i], high[i], low[i]));
        }

        return retval;
    }

    public static <K> double[] extractValues(
            final Series<K, IntervalPrice> ts,
            final IntervalPrice.Type type)  {
        final double[] result = new double[ts.size()];
        final Iterator<IntervalPrice> it = ts.values().iterator();
        for (int i=0; i<ts.size(); i++) {
            result[i] = it.next().value(type);
        }
        return result;
    }

    public static <K> Series<K, Double> extractComponent(
    		final Class<K> classK,
            final Series<K, IntervalPrice> ts,
            final IntervalPrice.Type type) {
        final Series<K, Double> result = new Series<K, Double>(classK, Double.class);
        for (final K date : ts.keySet()) {
            final IntervalPrice prices = ts.get(date);
            result.put(date, prices.value(type));
        }
        return result;
    }


    //
    // public inner enums
    //

    public enum Type {
        Open, Close, High, Low
    }

}
