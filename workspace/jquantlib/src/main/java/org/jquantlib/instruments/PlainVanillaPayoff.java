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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2007 StatPro Italia srl

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
 * Plain-vanilla payoff
 * <p>
 * Pays off nothing if the underlying asset price {@latex$ S_{T}} finishes below/above the strike price {@latex$ K}, or pays
 * out a the difference between the asset price {@latex$ S_{T}} and the strike price {@latex$ K} if the underlying asset finishes
 * above/below the strike price.
 *
 * @see <a href="http://www.in-the-money.com/artandpap/Binary%20Options.doc">Binary Options</a>
 *
 * @author Richard Gomes
 */
public class PlainVanillaPayoff extends StrikedTypePayoff {

    //
    // public constructors
    //

	public PlainVanillaPayoff(final Option.Type type, final /*@Real*/ double strike) {
		super(type, strike);
	}

    //
    // Overrides Payoff
    //

    @Override
    public String name() /* @ReadOnly */ {
        return "Vanilla";
    }

	/**
     * Pays off nothing if the underlying asset price {@latex$ S_{T}} finishes below/above the strike price {@latex$ K}, or pays
     * out a the difference between the asset price {@latex$ S_{T}} and the strike price {@latex$ K} if the underlying asset finishes
     * above/below the strike price.
     * <li>CALL Option: {@latex$ \max(S_{T}-K,0)}</li>
     * <li>PUT Option:  {@latex$ \max(K-S_{T},0)}</li>
     * where {@latex$ S_{T}} is the asset price at maturity and {@latex$ K} is the strike price.
	 */
	@Override
    public final double get(final double price) /* @ReadOnly */ {
    	if (type==Option.Type.Call) {
            return Math.max(price - strike, 0.0);
        } else if (type==Option.Type.Put) {
            return Math.max(strike - price, 0.0);
        } else {
            throw new LibraryException(UNKNOWN_OPTION_TYPE); // QA:[RG]::verified
        }
    }


	//
    // implements PolymorphicVisitable
    //

    @Override
    public void accept(final PolymorphicVisitor pv) {
        final Visitor<PlainVanillaPayoff> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
    }

}
