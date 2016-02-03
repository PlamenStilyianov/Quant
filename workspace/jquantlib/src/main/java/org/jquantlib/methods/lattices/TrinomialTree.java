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

import java.util.Vector;

import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.time.TimeGrid;

/**
 * Recombining trinomial tree class
 * <p>
 * This class defines a recombining trinomial tree approximating a 1-D stochastic process.
 *
 * @warning The diffusion term of the SDE must be independent of the underlying process.
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 * @author Tim Swetonic
*/
//TODO: http://bugs.jquantlib.org/view.php?id=394
public class TrinomialTree extends Tree {

	public static final Branches branches = Branches.TRINOMIAL;

	protected Vector<Branching> branchings_ = new Vector<Branching>();
	protected double x0_;
	protected Vector<Double> dx_ = new Vector<Double>();
	protected TimeGrid timeGrid_;

	public TrinomialTree(final StochasticProcess1D process, final TimeGrid timeGrid) {
	    this(process, timeGrid, false);
	}

	public TrinomialTree(final StochasticProcess1D process, final TimeGrid timeGrid, final boolean isPositive) {
		super(timeGrid.size());
		dx_.add(new Double(1));
		dx_.add(new Double(0.0));
		timeGrid_ = timeGrid;
		x0_ = process.x0();

		final int nTimeSteps = timeGrid.size() - 1;
		Integer jMin = 0;
		Integer jMax = 0;

		for (int i = 0; i < nTimeSteps; i++) {
			final double t = timeGrid.at(i);
			final double dt = timeGrid.dt(i);

			// Variance must be independent of x
			final double v2 = process.variance(t, 0.0, dt);
			/* Volatility */final double v = Math.sqrt(v2);
			dx_.add(v * Math.sqrt(3.0));

			final Branching branching = new Branching();
			for (int j = jMin; j <= jMax; j++) {
				final double x = x0_ + j * dx_.get(i);
				final double m = process.expectation(t, x, dt);
				int temp = (int) Math.floor((m - x0_) / dx_.get(i + 1) + 0.5);

				if (isPositive) {
                    while (x0_ + (temp - 1) * dx_.get(i + 1) <= 0) {
                        temp++;
                    }
                }

				final double e = m - (x0_ + temp * dx_.get(i + 1));
				final double e2 = e * e;
				final double e3 = e * Math.sqrt(3.0);

				final double p1 = (1.0 + e2 / v2 - e3 / v) / 6.0;
				final double p2 = (2.0 - e2 / v2) / 3.0;
				final double p3 = (1.0 + e2 / v2 + e3 / v) / 6.0;

				branching.add(temp, p1, p2, p3);
			}
			branchings_.add(branching);

			jMin = branching.jMin();
			jMax = branching.jMax();
		}

	}

	public double dx(final int i) {
		return dx_.get(i).doubleValue();
	}

	public TimeGrid timeGrid() {
		return timeGrid_;
	}

	@Override
	public int size(final int i) {
		return i == 0 ? 1 : branchings_.get(i - 1).size();
	}

	@Override
	public double underlying(final int i, final int index) {
		if (i == 0)
            return x0_;
        else
            return x0_ + (branchings_.get(i - 1).jMin() + (double) (index)) * dx(i);
	}

	@Override
	public int descendant(final int i, final int index, final int branch) {
		return branchings_.get(i).descendant(index, branch);
	}

	@Override
	public double probability(final int i, final int index, final int branch) {
		return branchings_.get(i).probability(index, branch);
	}

	private static class Branching {

		private final Vector<Integer> k_ = new Vector<Integer>();
		private final Vector<Vector<Double>> probs_ = new Vector<Vector<Double>>(3);
		private int kMin_, jMin_, kMax_, jMax_;

		public Branching() {
			kMin_ = Integer.MAX_VALUE;
			jMin_ = Integer.MAX_VALUE;
			kMax_ = Integer.MIN_VALUE;
			jMax_ = Integer.MIN_VALUE;
		}

		public int descendant(final int index, final int branch) {
			return k_.elementAt(index) - jMin_ - 1 + branch;
		}

		public double probability(final int index, final int branch) {
			return probs_.elementAt(branch).elementAt(index);
		}

		public int size() {
			return jMax_ - jMin_ + 1;
		}

		public int jMin() {
			return jMin_;
		}

		public int jMax() {
			return jMax_;
		}

		public void add(final int k, final double p1, final double p2, final double p3) {
			// store
			k_.add(k);
			final Vector<Double> v1 = new Vector<Double>();
			final Vector<Double> v2 = new Vector<Double>();
			final Vector<Double> v3 = new Vector<Double>();
			v1.add(new Double(p1));
			v2.add(new Double(p2));
			v3.add(new Double(p3));

			probs_.add(v1);
			probs_.add(v2);
			probs_.add(v3);

			// maintain invariants
			kMin_ = Math.min(kMin_, k);
			jMin_ = kMin_ - 1;
			kMax_ = Math.max(kMax_, k);
			jMax_ = kMax_ + 1;

		}

	}

}
