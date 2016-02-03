/*
 Copyright (C) 2008 Daniel Kong

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
 Copyright (C) 2005 Joseph Wang
 Copyright (C) 2005, 2006 Theo Boafo

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

package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.time.Date;

/**
 * @author Daniel Kong
 */
public class Callability extends Event {

	public enum Type { Call, Put }

	private final Price price;
	private final Type type;
	private final Date date;

	public Callability(final Price price, final Type type, final Date date){
        this.price=price;
        this.type=type;
        this.date=date;
	}

	@Override
	public Date date() {
		return date;
	}

	public Price price(){
		return price;
	}

	public Type type(){
		return type;
	}

	public static class Price {

		public enum Type{ Dirty, Clean }

		private final double amount;
		private Type type;

        public Price() {
            amount = 0.0;
        }

        public Price(final double amount, final Type type) {
            this.amount = amount;
            this.type = type;
        }

        public double amount() {
            QL.require(!Double.isNaN(amount) , "no amount given"); // TODO: message
            return amount;
        }

        public Type type() {
            return type;
        }

	}

}
