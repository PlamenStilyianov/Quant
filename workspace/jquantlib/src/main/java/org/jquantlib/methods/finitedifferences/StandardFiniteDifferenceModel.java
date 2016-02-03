/*
 Copyright (C) 2008 Srinivas Hasti

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
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

/**
 * @author Srinivas Hasti
 * 
 */
public class StandardFiniteDifferenceModel extends FiniteDifferenceModel<TridiagonalOperator, CrankNicolson<TridiagonalOperator>> {
    
	public StandardFiniteDifferenceModel(
			final TridiagonalOperator op,
			final List<BoundaryCondition<TridiagonalOperator>> bcs) {
		super(TridiagonalOperator.class, CrankNicolson.class, op, bcs);
	}
}
