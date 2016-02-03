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

import java.util.List;

import org.jquantlib.math.matrixutilities.Array;

/**
 * @author Srinivas Hasti
 * 
 */
// ! Mixed (explicit/implicit) scheme for finite difference methods
/*
 * ! In this implementation, the passed operator must be derived from either
 * TimeConstantOperator or TimeDependentOperator. Also, it must implement at
 * least the following interface:
 * 
 * \code typedef ... array_type;
 * 
 * // copy constructor/assignment // (these will be provided by the compiler if
 * none is defined) Operator(const Operator&); Operator& operator=(const
 * Operator&);
 * 
 * // inspectors Size size();
 * 
 * // modifiers void setTime(Time t);
 * 
 * // operator interface array_type applyTo(const array_type&); array_type
 * solveFor(const array_type&); static Operator identity(Size size);
 * 
 * // operator algebra Operator operator(Real, const Operator&); Operator
 * operator+(const Operator&, const Operator&); Operator operator+(const
 * Operator&, const Operator&); \endcode
 * 
 * \warning The differential operator must be linear for this evolver to work.
 * 
 * \todo - derive variable theta schemes - introduce multi time-level schemes.
 * 
 * \ingroup findiff
 */
public class MixedScheme<T extends Operator> {
	private T L, I, explicitPart, implicitPart;
	/* Time */private double dt;
	/* Real */private double theta;
	private List<BoundaryCondition<T>> bcs;

	public MixedScheme(T op,
	/* Real */double theta, List<BoundaryCondition<T>> bcs2) {
		L = op;
		I = (T)op.identity(op.size());
		this.theta = theta;
		this.bcs = bcs2;
	}

	public Array step(Array a, /* Time */double t) {
		int i;
		for (i = 0; i < bcs.size(); i++)
			bcs.get(i).setTime(t);
		if (theta != 1.0) { // there is an explicit part
			if (L.isTimeDependent()) {
				L.setTime(t);
				explicitPart = (T) I.subtract(L.multiply((1.0 - theta) * dt)); // I-((1.0-theta) * dt)*L_;
			}
			for (i = 0; i < bcs.size(); i++)
				bcs.get(i).applyBeforeApplying(explicitPart);
			a = explicitPart.applyTo(a);
			for (i = 0; i < bcs.size(); i++)
				bcs.get(i).applyAfterApplying(a);
		}
		if (theta != 0.0) { // there is an implicit part
			if (L.isTimeDependent()) {
				L.setTime(t - dt);
				implicitPart = (T) I.add(L.multiply(theta * dt)); // I_+(theta_ * dt_)*L_;
			}
			for (i = 0; i < bcs.size(); i++)
				bcs.get(i).applyBeforeSolving(implicitPart, a);
			a = implicitPart.solveFor(a);
			for (i = 0; i < bcs.size(); i++)
				bcs.get(i).applyAfterSolving(a);
		}
		
		return a;
	}

	public void setStep(/* Time */double dt) {
		this.dt = dt;
		if (theta != 1.0) // there is an explicit part
			explicitPart = (T) I.subtract(L.multiply((1.0 - theta) * dt)); // I - ((1.0 - theta) * dt) * L
		if (theta != 0.0) // there is an implicit part
			implicitPart = (T) I.add(L.multiply(theta * dt)); // I + (theta * dt) * L
	}
}
