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
import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * @author Srinivas Hasti
 * 
 */
public class OperatorFactory {
	
	public static TridiagonalOperator getOperator(
			GeneralizedBlackScholesProcess process, Array grid,
			double residualTime, boolean timeDependent) {
		if (timeDependent)
			return new BSMTermOperator(grid, process, residualTime);
		else
			return new BSMOperator(grid, process, residualTime);
	}
	//TODO: finish; This is not required for Mar 1st release.
	/*
	 * public static TridiagonalOperator getOperator( const
	 * boost::shared_ptr<OneFactorModel::ShortRateDynamics> &process, const
	 * Array &grid) { return OneFactorOperator(grid, process); }
	 */
}
