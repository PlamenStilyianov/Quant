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

package org.jquantlib.quotes;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Constants;


/**
 * Market element returning a stored value
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Richard Gomes" })
public class SimpleQuote extends Quote {

    //
    // private fields
    //

    private double value;


    //
    // public constructors
    //

    public SimpleQuote() {
        this(Constants.NULL_REAL);
    }

    public SimpleQuote(final SimpleQuote o) {
        this.value = o.value;
    }

    public SimpleQuote(final double d) {
        this.value = d;
    }


    //
    // public methods
    //

    public double setValue() {
        return setValue(Constants.NULL_REAL);
    }

    /**
     * @return the difference between the new value and the old value
     */
    public double setValue(final double value) {
        final double diff = this.value - value;
        if (diff != 0.0) {
            this.value = value;
            notifyObservers();
        }
        return diff;
    }

    public void reset() {
        setValue(Constants.NULL_REAL);
    }


    //
    // overrides Object
    //

    @Override
    public String toString() {
        return String.valueOf(value);
    }


    //
    // implements Quote
    //

    @Override
    public final double value() /* @ReadOnly */ {
        QL.require(isValid() , "invalid simple quote: no value available"); // TODO: message
        return value;
    }

    @Override
    public boolean isValid() /* @ReadOnly */ {
        return !Double.isNaN(value);
    }

}
