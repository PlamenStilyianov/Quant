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
 Copyright (C) 2003, 2006 Ferdinando Ametrano
 Copyright (C) 2006 Warren Chou
 Copyright (C) 2006 StatPro Italia srl
 Copyright (C) 2006 Chiara Fornarola

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

/**
 * Intermediate class for typed payoffs (CALL/PUT) with a fixed strike price
 *
 * @author Richard Gomes
 */
public abstract class StrikedTypePayoff extends TypePayoff {

	//
    // protected fields
    //

    /**
     * This field represents the {@link Option}'s strike price
     */
    protected /*@Real*/ double strike;

    //
    // public constructors
    //

    /**
	 * Constructs a typed {@link Payoff} with a fixed strike price
	 *
	 * @param type is an {@link Option.Type}
	 * @param strike is the strike price
	 */
    public StrikedTypePayoff(final Option.Type type, final /*@Real*/ double strike) {
		super(type);
		this.strike = strike;
	}

	//
    // public final methods
    //

    /**
     * @return the strike value
     */
    public final /*@Real*/ double strike() {
		return strike;
	}

    //
    // overrides Payoff
    //

    @Override
    public String description() /* @ReadOnly */ {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.description()).append(", ").append(strike).append(" strike");
        return sb.toString();
    }

}
