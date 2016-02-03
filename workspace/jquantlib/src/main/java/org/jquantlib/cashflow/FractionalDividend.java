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

package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.time.Date;

/**
 * Predetermined cash flow
 * <p>
 * This cash flow pays a predetermined amount at a given date.
 *
 * @author Daniel Kong
 */
public class FractionalDividend extends Dividend {

	protected double rate;
	protected double nominal;

	public FractionalDividend(final double rate, final Date date) {
		super(date);
		this.rate=rate;
	}

	public FractionalDividend(final double rate, final double nominal, final Date date) {
		super(date);
		this.rate=rate;
		this.nominal=nominal;
	}

	@Override
	public double amount(final double underlying) {
		return rate*underlying;
	}

	@Override
	public double amount() {
		QL.require(!Double.isNaN(nominal) , "no nominal given"); // TODO: message
		return rate*nominal;
	}

}
