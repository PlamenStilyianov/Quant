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

import org.jquantlib.math.LogGrid;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 *
 * @author Srinivas Hasti
 *
 */
public class BSMOperator extends TridiagonalOperator {

	public BSMOperator(final int size, final double dx, final double r, final double q, final double sigma) {
		super(size);
		final double sigma2 = sigma * sigma;
		final double nu = r - q - sigma2 / 2;
		final double pd = -(sigma2 / dx - nu) / (2 * dx);
		final double pu = -(sigma2 / dx + nu) / (2 * dx);
		final double pm = sigma2 / (dx * dx) + r;
		setMidRows(pd, pm, pu);
	}

	public BSMOperator(final Array grid, final GeneralizedBlackScholesProcess process, final double residualTime) {
		super(grid.size());
		final LogGrid logGrid = new LogGrid(grid);
		final PdeConstantCoeff<PdeBSM> cc = new PdeConstantCoeff<PdeBSM>(
				PdeBSM.class, process, residualTime, process.stateVariable().currentLink().value());
		cc.generateOperator(residualTime, logGrid, this);
	}
}
