/*
 Copyright (C) 2009 John Nichol

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
 Copyright (C) 2005, 2008 StatPro Italia srl
 Copyright (C) 2007 Ferdinando Ametrano

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

package org.jquantlib.instruments.bonds;
import org.jquantlib.instruments.Bond;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;

/**
 * ZeroCouponBond class
 *
 * @category instruments
 *
 * @author John Nichol
 * 
 *
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class ZeroCouponBond extends Bond {

	public ZeroCouponBond(
			final /* @Natural */ int settlementDays,
            final Calendar calendar,
            final double faceAmount,
            final Date maturityDate,
            final BusinessDayConvention paymentConvention,
            final double redemption,
            final Date issueDate) {
		super(settlementDays, calendar, issueDate);
        maturityDate_ = maturityDate.clone();
        final Date redemptionDate = calendar_.adjust(maturityDate,
                                               paymentConvention);
        setSingleRedemption(faceAmount, redemption, redemptionDate);
	}

	public ZeroCouponBond(
	        final /* @Natural */ int settlementDays,
            final Calendar calendar,
            final double faceAmount,
            final Date maturityDate) {
		this(settlementDays, calendar, faceAmount, maturityDate, BusinessDayConvention.Following, 100.0, new Date());
	}

	public ZeroCouponBond(
	        final /* @Natural */ int settlementDays,
            final Calendar calendar,
            final double faceAmount,
            final Date maturityDate,
            final BusinessDayConvention paymentConvention,
            final double redemption) {
		this(settlementDays, calendar, faceAmount, maturityDate, paymentConvention, redemption, new Date());
	}

	public ZeroCouponBond(
	        final /* @Natural */ int settlementDays,
            final Calendar calendar,
            final double faceAmount,
            final Date maturityDate,
            final BusinessDayConvention paymentConvention) {
		this(settlementDays, calendar, faceAmount, maturityDate, paymentConvention, 100.0, new Date());
	}
}
