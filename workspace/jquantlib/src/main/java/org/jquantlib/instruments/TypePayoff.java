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

import org.jquantlib.instruments.Option.Type;

/**
 * Intermediate class for typed payoffs (CALL/PUT)
 *
 * @see Option.Type
 *
 * @author Richard Gomes
 */
public abstract class TypePayoff extends Payoff {

	//
    // protected fields
    //

    /**
     * This protected field represents the {@link Type} backing this {@link Payoff}
     */
    protected Option.Type type;

	//
    // public constructors
    //

    /**
     * Constructs a {@link Payoff} backed on a {@link Type}
     *
     * @param type is the {@link Type} backing this {@link Payoff}
     *
     * @see Option.Type
     */
    public TypePayoff(final Option.Type type) {
		this.type = type;
	}


	//
    // public final methods
    //

    /**
     * @return the {@link Type} backing this {@link Payoff}
     *
     * @see Option.Type
     */
    public final Option.Type optionType() {
		return this.type;
	}


    //
    // overrides Payoff
    //

    @Override
    public String description() /* @ReadOnly */ {
        final StringBuilder sb = new StringBuilder();
        sb.append(name()).append(' ').append(optionType());
        return sb.toString();
    }

}