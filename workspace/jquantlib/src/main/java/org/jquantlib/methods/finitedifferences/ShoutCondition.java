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

package org.jquantlib.methods.finitedifferences;

import org.jquantlib.instruments.Option;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Shout option condition
 * <p>
 * A shout option is an option where the holder has the right to
 * lock in a minimum value for the payoff at one (shout) time
 * during the option's life. The minimum value is the option's
 * intrinsic value at the shout time.
 *
 * @author Srinivas Hasti
 */
public class ShoutCondition extends CurveDependentStepCondition {
	/* @Time */private final double resTime;
	/* @Rate */private final double rate;
	/* @DiscountFactor */private double disc;

	public ShoutCondition(final Option.Type type,
	        final /*@Real*/ double strike, final /*@Time*/ double resTime, final /*@Rate*/ double rate) {
		super(type, strike);
		this.resTime = resTime;
		this.rate = rate;
	}

	public ShoutCondition(final Array intrinsicValues,
	        final /*@Time*/double resTime, final /*@Rate*/ double rate) {
		super(intrinsicValues);
		this.resTime = resTime;
		this.rate = rate;
	}

	@Override
    public void applyTo(final Array a, final /*@Time*/ double t) {
		disc = Math.exp(-rate * (t - resTime));
		super.applyTo(a, t);
	}

	@Override
    protected /*@Real*/double applyToValue(final /*@Real*/double current, final /*@Real*/double intrinsic) {
		return Math.max(current, disc * intrinsic);
	}
}
