/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.cashflow;

import org.jquantlib.util.PolymorphicVisitable;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;


/**
 * @author Srinivas Hasti
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class CashFlow extends Event implements Comparable<CashFlow> {

    //
    // public abstract methods
    //

    /**
	 * @return amount of the cash flow. The amount is not discounted, i.e., it is the actual amount paid at the cash flow date.
	 */
	public abstract double amount();


    //
    // implements Comparable
    //

	@Override
    public int compareTo(final CashFlow c2) {
        if (date().lt(c2.date())) {
            return -1;
        }

        if (date().equals(c2.date())) {
            try {
                if (amount() < c2.amount()) {
                    return -1;
                }
            } catch (final Exception e) {
                return -1;
            }
            return 0;
        }

        return 1;
    }


	//
	// implements PolymorphicVisitable
	//

	@Override
	public void accept(final PolymorphicVisitor pv) {
		final Visitor<CashFlow> v = (pv!=null) ? pv.visitor(this.getClass()) : null;
        if (v != null) {
            v.visit(this);
        } else {
            super.accept(pv);
        }
	}

}
