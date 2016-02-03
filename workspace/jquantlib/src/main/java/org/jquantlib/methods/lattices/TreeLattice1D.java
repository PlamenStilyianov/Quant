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
 * One-dimensional tree-based lattice
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 */
public abstract class TreeLattice1D extends TreeLattice {

	//
    // public constructors
    //

    public TreeLattice1D(final TimeGrid timeGrid, final int n) {
		super(timeGrid, n);
	}

	//
    // overrides Lattice
    //

    @Override
    public Array grid(final double t) {
		final int i = timeGrid().index(t);
		final Array grid = new Array(size(i));
		for (int j = 0; j < grid.size(); j++)
            grid.set(j, underlying(i, j));
		return grid;
	}


	//
	// abstract methods
	//

	public abstract double underlying(int i, int index);
}
