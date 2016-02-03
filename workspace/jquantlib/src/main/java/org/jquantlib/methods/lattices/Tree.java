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
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
package org.jquantlib.methods.lattices;

/**
 * Tree approximating a single-factor diffusion
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 * @author Tim Swetonic
 */
public abstract class Tree {

	private final int columns;

	abstract public double underlying(int i, int index);
	abstract public int size(int i);
	abstract public int descendant(int i, int index, int branch);
	abstract public double probability(int i, int index, int branch);

	protected Tree(final int columns) {
		this.columns = columns;
	}

	protected final int columns() {
		return columns;
	}


	//
	// protected enums
	//

    protected enum Branches {
        BINOMIAL(2),
        TRINOMIAL(3);

        private int value;

        private Branches(final int i) {
            this.value = i;
        }

        // TODO: possibly remove this method? (Could ordinal() be used?)
        public int getValue() {
            return value;
        }
    }

}
