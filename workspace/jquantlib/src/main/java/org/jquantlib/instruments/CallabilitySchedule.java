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

package org.jquantlib.instruments;

import java.util.ArrayList;

import org.jquantlib.QL;
import org.jquantlib.cashflow.Callability;

/**
 * @author Zahid Hussain
 */
public class CallabilitySchedule extends ArrayList<Callability> implements Cloneable {
	private static final long serialVersionUID = 1L;

	public CallabilitySchedule() {
        super();
    }

    public CallabilitySchedule(final int n) {
        super(n);
    }

    public Callability first() {
        QL.require(this.size() > 0 , "no Callability"); 
        return this.get(0);
    }

    public Callability last() {
        QL.require(this.size() > 0 , "no Callability");
        return this.get(this.size()-1);
    }

    @Override
    public Object clone() {
    	return (CallabilitySchedule)super.clone();
    }

}
