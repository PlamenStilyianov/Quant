/*
 Copyright (C) 2009 Ueli Hofstetter

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

import java.util.ArrayList;

import org.jquantlib.QL;

/**
 * @author Ueli Hofstetter
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class Leg extends ArrayList<CashFlow> implements Cloneable {

    //
    // public constructors
    //

    public Leg() {
        super();
    }

    public Leg(final int n) {
        super(n);
    }

    public CashFlow first() {
        QL.require(this.size() > 0 , "no cashflows");  // TODO: message
        return this.get(0);
    }

    public CashFlow last() {
        QL.require(this.size() > 0 , "no cashflows");  // TODO: message
        return this.get(this.size()-1);
    }

    @Override
    public Object clone() {
    	return (Leg)super.clone();
    }

}
