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
 * Binomial tree base class
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 * @author Tim Swetonic
 */
// TODO: http://bugs.jquantlib.org/view.php?id=394
public abstract class BinomialTree extends Tree {

    public static final Branches branches = Branches.BINOMIAL;

	protected @Real final double x0;
    protected @Real final double driftPerStep;
    protected @Time final double dt;

	protected BinomialTree(
	        final StochasticProcess1D process,
	        final @Time double end,
	        final @NonNegative int steps) {
        super(steps + 1);

        x0 = process.x0();
        dt = end / steps;
        driftPerStep = process.drift(0.0, x0) * dt;
    }

	@Override
    public final int size(final int i) {
		return i + 1;
	}

	@Override
    public final int descendant(final @Unused int unused, final int index, final int branch) {
		return index + branch;
	}

}
