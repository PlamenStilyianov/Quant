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
/*
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2005 StatPro Italia srl

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

package org.jquantlib.methods.lattices;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.time.TimeGrid;

/**
 * Simple binomial lattice approximating the Black-Scholes model
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 */
public class BlackScholesLattice<T extends Tree> extends TreeLattice1D {

	private final T tree;
	private final double discount;
	private final double pd;
	private final double pu;

	public BlackScholesLattice(
	        final T tree,
	        final double riskFreeRate,
	        final /*@Time*/ double end,
			final int steps) {
		super(new TimeGrid(end, steps), 2);
		this.tree = tree;
		this.discount = Math.exp(-riskFreeRate * (end / steps));
		this.pd = tree.probability(0, 0, 0);
		this.pu = tree.probability(0, 0, 1);
	}

	@Override
    public int size(final int i) {
		return tree.size(i);
	}

	@Override
    public double discount(final int a, final int b) {
		return discount;
	}

	@Override
    public double underlying(final int i, final int index) {
		return tree.underlying(i, index);
	}

	@Override
    public int descendant(final int i, final int index, final int branch) {
		return tree.descendant(i, index, branch);
	}

	@Override
    public double probability(final int i, final int index, final int branch) {
		return tree.probability(i, index, branch);
	}

	@Override
    public void stepback(final int i, final Array values, final Array newValues) {
		for (int j = 0; j < size(i); j++)
			newValues.set(j, (pd * values.get(j) + pu * values.get(j + 1)) * discount);
	}
}
