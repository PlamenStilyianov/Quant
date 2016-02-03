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
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.processes.StochasticProcess1D;

/**
 * Base class for equal probabilities binomial tree
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 * @author Tim Swetonic
 */
public abstract class EqualProbabilitiesBinomialTree extends BinomialTree {

	protected double up;

	public EqualProbabilitiesBinomialTree(final StochasticProcess1D process, @Time final double end, @NonNegative final int steps) {
		super(process, end, steps);
	}

	@Override
	public double probability(final int i, final int index, final int branch) {
		return 0.5;
	}

	@Override
	public double underlying(final int i, final int index) {
		final int j = index * 2 - i;

		// exploiting the forward value tree centering
		return this.x0 * Math.exp(i * this.driftPerStep + j * this.up);
	}

}
