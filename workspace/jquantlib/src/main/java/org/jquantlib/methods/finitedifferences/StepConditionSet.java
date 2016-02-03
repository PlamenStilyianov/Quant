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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Hasti
 * 
 */
public class StepConditionSet<T> {

	private List<StepCondition<T>> stepConditions = new ArrayList<StepCondition<T>>();

	public void applyTo(List<T> a, /* Time */double t) {
		for (int i = 0; i < stepConditions.size(); i++) {
			stepConditions.get(i).applyTo(a.get(i), t);
		}
	}

	public void push_back(StepCondition<T> a) {
		stepConditions.add(a);
	}
}
