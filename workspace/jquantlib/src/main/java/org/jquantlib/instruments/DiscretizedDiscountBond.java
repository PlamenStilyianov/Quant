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
 Copyright (C) 2006, 2007 StatPro Italia srl

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

package org.jquantlib.instruments;

import java.util.List;

import org.jquantlib.methods.lattices.Lattice;

/**
 * Useful discretized discount bond asset
 *
 * @author Srinivas Hasti
 */
// TODO: complete this class
public class DiscretizedDiscountBond extends DiscretizedAsset {


    public DiscretizedDiscountBond() {
        throw new UnsupportedOperationException("work in progress");
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#initialize(org.jquantlib.methods.lattices.Lattice,
	 *      double)
	 */
	@Override
	public void initialize(final Lattice lt, final double t) {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#isOnTime(double)
	 */
	@Override
	protected boolean isOnTime(final double t) {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#mandatoryTimes()
	 */
	@Override
	public List<Double> mandatoryTimes() {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#partialRollback(double)
	 */
	@Override
	public void partialRollback(final double to) {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#postAdjustValues()
	 */
	@Override
	public void postAdjustValues() {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#preAdjustValues()
	 */
	@Override
	public void preAdjustValues() {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#presentValue()
	 */
	@Override
	public double presentValue() {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#reset(int)
	 */
	@Override
	public void reset(final int size) {
        throw new UnsupportedOperationException("work in progress");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jquantlib.methods.lattices.DiscretizedAsset#rollback(double)
	 */
	@Override
	public void rollback(final double to) {
        throw new UnsupportedOperationException("work in progress");
	}

}
