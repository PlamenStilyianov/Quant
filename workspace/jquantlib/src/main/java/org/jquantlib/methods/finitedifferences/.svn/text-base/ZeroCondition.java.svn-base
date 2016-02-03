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

/**
 * @author Srinivas Hasti
 *
 */
public class ZeroCondition implements StepCondition<List<Double>> {

	/* (non-Javadoc)
	 * @see org.jquantlib.methods.finitedifferences.StepConditon#applyTo(java.lang.Object, double)
	 */
	@Override
	public void applyTo(List<Double> a, double t) {
		for (int i = 0; i < a.size(); i++) {
			a.set(i,Math.max(a.get(i), 0.0));
		}
	}

}
