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
 * @author Srinivas Hasti
 * 
 */
public interface BoundaryCondition<T extends Operator> {
	
	public static enum Side {
		None, Upper, Lower;
	}

	/*
	 * ! This method modifies an operator \f$ L \f$ before it is applied to an
	 * array \f$ u \f$ so that \f$ v = Lu \f$ will satisfy the given condition.
	 */
	public void applyBeforeApplying(T operator);

	/*
	 * ! This method modifies an array \f$ u \f$ so that it satisfies the given
	 * condition.
	 */
	public void applyAfterApplying(Array array);

	/*
	 * ! This method modifies an operator \f$ L \f$ before the linear system \f$
	 * Lu' = u \f$ is solved so that \f$ u' \f$ will satisfy the given
	 * condition.
	 */
	public void applyBeforeSolving(T operator, Array array);

	/*
	 * ! This method modifies an array \f$ u \f$ so that it satisfies the given
	 * condition.
	 */
	public void applyAfterSolving(Array array);

	/*
	 * ! This method sets the current time for time-dependent boundary
	 * conditions.
	 */
	public void setTime(/*@Time*/double t);
}
