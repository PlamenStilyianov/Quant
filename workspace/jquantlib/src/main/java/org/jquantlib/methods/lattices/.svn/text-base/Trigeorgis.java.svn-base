/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2008 Tim Swetonic

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
 * Trigeorgis (additive equal jumps) binomial tree
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 * @author Tim Swetonic
 */
public class Trigeorgis extends EqualJumpsBinomialTree {

	public Trigeorgis(
	        final StochasticProcess1D process,
	        final @Time double end,
	        final @NonNegative int steps,
	        final @Unused @Real double strike) {
        super(process, end, steps);

        dx = Math.sqrt(process.variance(0.0, x0, dt) + driftPerStep * driftPerStep);
        pu = 0.5 + 0.5 * driftPerStep / dx;
        pd = 1.0 - pu;

        if (pu < 0.0 || pu > 1.0)
            throw new IllegalStateException("negative probability");
    }

}
