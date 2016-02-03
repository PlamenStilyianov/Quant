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

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Binary gap payoff which is equivalent of being:
 * <li>long a {@link PlainVanillaPayoff} at the first strike (same CALL/PUT type) and</li>
 * <li>short a {@link CashOrNothingPayoff} at the first strike (same CALL/PUT type) with cash payoff equal to the difference between
 * the second and the first strike.</li>
 * <p>
 * <b>WARNING:</b> this payoff can be negative depending on the strikes
 * <p>
 * Definitions of Binary path-independent payoffs can be found in
 * <i>M. Rubinstein, E. Reiner:"Unscrambling The Binary Code", Risk, Vol.4 no.9,1991</i>.
 *
 * @see <a href="http://www.in-the-money.com/artandpap/Binary%20Options.doc">Binary Options</a>
 *
 * @author Richard Gomes
 */
public class GapPayoff extends StrikedTypePayoff {

	//
    // protected fields
    //

    protected /*@Real*/ double secondStrike;

	//
	// public constructors
	//

	public GapPayoff(final Option.Type type, final /*@Real*/ double strike, final /*@Real*/ double secondStrike) {
		super(type, strike);
		this.secondStrike = secondStrike;
	}

    /**
     * @return the second strike value
     */
	public /*@Real*/ double getSecondStrike() /* @ReadOnly */ {
		return secondStrike;
	}


	//
	// Overrides Payoff
	//

    @Override
    public String name() /* @ReadOnly */ {
        return "Gap";
    }

    @Override
    public String description() /* @ReadOnly */ {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.description()).append(", ").append(secondStrike).append(" second strike");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Pays off nothing if the underlying asset price {@latex$ S_{T}} finishes below/above the first strike price {@latex$ K_{1}},
     * or pays out the difference between the asset price {@latex$ S_{T}} and the second strike price {@latex$ K_{2}} if the
     * underlying asset finishes above/below the first strike price {@latex$ K_{1}}.
     * <li>CALL Option: if {@latex$ S_{T}>K_{1} \rightarrow S_{T}-K_{2}}, otherwise zero</li>
     * <li>PUT Option: if {@latex$ K_{1}>S_{T} \rightarrow K_{2}-S_{T}}, otherwise zero</li>
     * where {@latex$ S_{T}} is the asset price at maturity, {@latex$ K_{1}} is the first strike and {@latex$ K_{2}} is the
     * second strike.
     */
	@Override
    public final double get(final double price) /* @ReadOnly */ {
    	if (type==Option.Type.Call) {
            return (price-strike >= 0.0 ? price-secondStrike : 0.0);
        } else if (type==Option.Type.Put) {
            return (strike-price >= 0.0 ? secondStrike-price : 0.0);
        } else {
            throw new LibraryException(UNKNOWN_OPTION_TYPE); // QA:[RG]::verified
        }
    }


	//
	// implements PolymorphicVisitable
	//

	@Override
	public void accept(final PolymorphicVisitor pv) {
		final Visitor<GapPayoff> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
	}

}
