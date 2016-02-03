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
 Copyright (C) 2005, 2006 Theo Boafo
 Copyright (C) 2006 StatPro Italia srl

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

import org.jquantlib.instruments.DiscretizedAsset;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.pricingengines.hybrid.DiscretizedConvertible;

/**
 * Binomial lattice approximating the Tsiveriotis-Fernandes model
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 */
public class TsiveriotisFernandesLattice<T extends Tree> extends BlackScholesLattice<T> {

	private final double pd;
	private final double pu;
	private final double creditSpread;
	private final double dt;
	private final double riskFreeRate;

	public TsiveriotisFernandesLattice(
	        final T tree,
	        final double riskFreeRate,
	        final /*@Time*/ double end,
			final int steps,
			final double creditSpread,
			final double sigma,
			final double divYield) {
		super(tree, riskFreeRate, end, steps);
		this.dt = end / steps;
		this.pd = tree.probability(0, 0, 0);
		this.pu = tree.probability(0, 0, 1);
		this.riskFreeRate = riskFreeRate;
		this.creditSpread = creditSpread;

		if (pu > 1.0)
            throw new IllegalStateException("negative probability");
		if (pu < 0.0)
            throw new IllegalStateException("negative probability");
	}

	public void stepback(
	        final int i,
	        final Array values,
	        final Array conversionProbability,
			final Array spreadAdjustedRate,
			final Array newValues,
			final Array newConversionProbability,
			final Array newSpreadAdjustedRate) {

		for (int j = 0; j < size(i); j++) {

			// new conversion probability is calculated via backward
			// induction using up and down probabilities on tree on
			// previous conversion probabilities, ie weighted average
			// of previous probabilities.
			newConversionProbability.set(j, pd * conversionProbability.get(j)
					+ pu * conversionProbability.get(j + 1));

			// Use blended discounting rate
			newSpreadAdjustedRate.set(j, newConversionProbability.get(j)
					* riskFreeRate + (1 - newConversionProbability.get(j))
					* (riskFreeRate + creditSpread));

			newValues.set(j, (pd * values.get(j) / (1 + (spreadAdjustedRate.get(j) * dt)))
			                 + (pu * values.get(j + 1) / (1 + (spreadAdjustedRate.get(j + 1) * dt))));
		}
	}

	@Override
    public void rollback(final DiscretizedAsset asset, final double to) {
		partialRollback(asset, to);
		asset.adjustValues();
	}

	@Override
    public void partialRollback(final DiscretizedAsset asset, final double to) {

		final double from = asset.time();

		if (Closeness.isClose(from, to))
            return;

		if (from <= to)
            throw new IllegalStateException("cannot roll the asset back to "
					+ to + " (it is already at t = " + from + ")");

		final DiscretizedConvertible convertible = (DiscretizedConvertible) (asset);

		final int iFrom = t.index(from);
		final int iTo = t.index(to);

		for (int i = iFrom - 1; i >= iTo; --i) {

			final Array newValues = new Array(size(i));
			final Array newSpreadAdjustedRate = new Array(size(i));
			final Array newConversionProbability = new Array(size(i));

			stepback(i, convertible.values(), convertible
					.conversionProbability(), convertible.spreadAdjustedRate(),
		    			newValues, newConversionProbability, newSpreadAdjustedRate);

			convertible.setTime(t.get(i));
			convertible.setValues(newValues);
			convertible.setSpreadAdjustedRate(newSpreadAdjustedRate);
			convertible.setConversionProbability(newConversionProbability);

			// skip the very last adjustment
			if (i != iTo)
                convertible.adjustValues();
		}
	}

}
