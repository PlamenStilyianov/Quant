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

import org.jquantlib.math.matrixutilities.Array;

/**
 *
 * @author Srinivas Hasti
 *
 */
public class NeumannBC implements BoundaryCondition<TridiagonalOperator> {
	private final /* @Real */double value;
	private final Side side;

	public NeumannBC(final double value, final Side side) {
		this.value = value;
		this.side = side;
	}

	@Override
	public void applyAfterApplying(final Array u) {
		switch (side) {
		case Lower:
			u.set(0, u.get(1) - value);
			break;
		case Upper:
			u.set(u.size() - 1, u.get(u.size() - 2) + value);
			break;
		default:
			throw new IllegalStateException(
					"unknown side for Neumann boundary condition");
		}
	}

	@Override
	public void applyBeforeApplying(final TridiagonalOperator operator) {
		switch (side) {
		case Lower:
			operator.setFirstRow(-1.0, 1.0);
			break;
		case Upper:
			operator.setLastRow(-1.0, 1.0);
			break;
		default:
			throw new IllegalStateException(
					"unknown side for Neumann boundary condition");
		}
	}

	@Override
	public void applyBeforeSolving(final TridiagonalOperator operator,
		 final Array rhs) {
		switch (side) {
		case Lower:
			operator.setFirstRow(-1.0, 1.0);
			rhs.set(0, value);
			break;
		case Upper:
			operator.setLastRow(-1.0, 1.0);
			rhs.set(rhs.size() - 1, value);
			break;
		default:
			throw new IllegalStateException(
					"unknown side for Neumann boundary condition");
		}

	}

	@Override
	public void applyAfterSolving(final Array arrayType) {

	}

	@Override
	public void setTime(final double t) {

	}

}
