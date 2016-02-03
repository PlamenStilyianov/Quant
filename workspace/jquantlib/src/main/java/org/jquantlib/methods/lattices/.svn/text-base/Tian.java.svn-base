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
package org.jquantlib.methods.lattices;

import org.jquantlib.lang.annotation.NonNegative;
import org.jquantlib.lang.annotation.Real;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.lang.annotation.Unused;
import org.jquantlib.processes.StochasticProcess1D;

/**
 * Tian tree: third moment matching, multiplicative approach
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 */
public class Tian extends BinomialTree {

	protected double up;
	protected double down;
	protected double pu;
	protected double pd;

	public Tian(
            final StochasticProcess1D process,
            final @Time double end,
            final @NonNegative int steps,
            final @Unused @Real double strike) {
		super(process, end, steps);

		final double q = Math.exp(process.variance(0.0, x0, dt));
		final double r = Math.exp(driftPerStep) * Math.sqrt(q);

		up = 0.5 * r * q * (q + 1 + Math.sqrt(q * q + 2 * q - 3));
		down = 0.5 * r * q * (q + 1 - Math.sqrt(q * q + 2 * q - 3));

		pu = (r - down) / (up - down);
		pd = 1.0 - pu;

		// doesn't work
		// treeCentering_ = (up_+down_)/2.0;
		// up_ = up_-treeCentering_;

		if (pu < 0.0 || pu > 1.0)
            throw new IllegalStateException("negative probablity");
	}

	@Override
	public double probability(final int i, final int index, final int branch) {
		return (branch == 1 ? pu : pd);
	}

	@Override
	public double underlying(final int i, final int index) {
		return x0 * Math.pow(down, i - index) * Math.pow(up, index);
	}

}
