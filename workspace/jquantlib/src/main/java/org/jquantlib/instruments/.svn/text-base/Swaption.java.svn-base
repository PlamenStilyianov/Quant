/*
Copyright (C) 2008 Praneet Tiwari

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
package org.jquantlib.instruments;

import org.jquantlib.exercise.Exercise;
import org.jquantlib.pricingengines.PricingEngine.Arguments;

/**
 *
 * @author Praneet Tiwari
 */

// ! %Swaption class
/*
 * ! \ingroup instruments
 *
 * \test - the correctness of the returned value is tested by checking that the price of a payer (resp. receiver) swaption decreases
 * (resp. increases) with the strike. - the correctness of the returned value is tested by checking that the price of a payer (resp.
 * receiver) swaption increases (resp. decreases) with the spread. - the correctness of the returned value is tested by checking it
 * against that of a swaption on a swap with no spread and a correspondingly adjusted fixed rate. - the correctness of the returned
 * value is tested by checking it against a known good value. - the correctness of the returned value of cash settled swaptions is
 * tested by checking the modified annuity against a value calculated without using the Swaption class.
 *
 *
 * \todo add greeks and explicit exercise lag
 */
//TODO: code review
public class Swaption {// extends Option {

    // ! %settlement information
    public Swaption(final VanillaSwap swap, final Exercise exercise, final Settlement.Type delivery /* = Settlement::Physical */) {
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
    }

    // @Override
    protected void setupArguments(final Arguments arguments) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /*****
     * class arguments extends VanillaSwap::arguments, public Option::arguments { public: arguments() :
     * settlementType(Settlement::Physical) {} boost::shared_ptr<VanillaSwap> swap; Settlement::Type settlementType; void validate()
     * const; }
     ********/
}
