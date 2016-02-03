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
package org.jquantlib.math;

import org.jquantlib.math.matrixutilities.Array;


/**
 *
 * @author Srinivas Hasti
 *
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class TransformedGrid {

	protected Array grid;
	protected Array transformedGrid;
	protected Array dxm;
	protected Array dxp;
	protected Array dx;

	public TransformedGrid(final Array grid) {
	    // TODO: code review :: use of clone()
		this.grid = grid;
		this.transformedGrid = grid.clone();
		this.dxm = new Array(grid.size());
		this.dxp = new Array(grid.size());
		this.dx = new Array(grid.size());
		for (int i = 1; i < transformedGrid.size() - 1; i++) {
			dxm.set(i, transformedGrid.get(i) - transformedGrid.get(i - 1));
			dxp.set(i, transformedGrid.get(i + 1) - transformedGrid.get(i));
			dx.set(i, dxm.get(i) + dxp.get(i));
		}
	}

	public TransformedGrid(final Array grid, final Ops.DoubleOp f) {
	    // TODO: code review :: use of clone()
	    this.grid = grid;
		this.transformedGrid = grid.clone().transform(f);
		this.dxm = new Array(grid.size());
		this.dxp = new Array(grid.size());
		this.dx = new Array(grid.size());
		for (int i = 1; i < transformedGrid.size() - 1; i++) {
			dxm.set(i, transformedGrid.get(i) - transformedGrid.get(i - 1));
			dxp.set(i, transformedGrid.get(i + 1) - transformedGrid.get(i));
			dx.set(i, dxm.get(i) + dxp.get(i));
		}
	}

	public Array gridArray() {
		return grid;
	}

	public Array transformedGridArray() {
		return transformedGrid;
	}

	public Array dxmArray() {
		return dxm;
	}

	public Array dxpArray() {
		return dxp;
	}

	public Array dxArray() {
		return dx;
	}

	public double grid(final int i) {
		return grid.get(i);
	}

	public double transformedGrid(final int i) {
		return transformedGrid.get(i);
	}

	public double dxm(final int i) {
		return dxm.get(i);
	}

	public double dxp(final int i) {
		return dxp.get(i);
	}

	public double dx(final int i) {
		return dx.get(i);
	}

	public int size() {
		return grid.size();
	}
}
