/*
 Copyright (C) 2008 Anand Mani

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

package org.jquantlib.experimental;

import java.util.Collection;

import org.joda.primitives.list.impl.ArrayDoubleList;
import org.joda.primitives.list.impl.ArrayFloatList;

public class PrimitiveCollectionAddVisitorImpl implements PrimitiveCollectionAddVisitor {

	@Override
	public boolean visitAddDoubleCollection(Collection collection, double value) {
		return collection.add(value);
	}

	@Override
	public boolean visitAddFloatCollection(Collection collection, float value) {
		return collection.add(value);
	}

	public static void main(String[] args) {
		PrimitiveCollectionAddVisitor v = PrimitiveCollectionAddVisitor.impl;
		
		ArrayDoubleList dal = new ArrayDoubleList();
		v.visitAddDoubleCollection(dal, 6.66);
		
		ArrayFloatList fal = new ArrayFloatList();
		v.visitAddFloatCollection(fal, 6.66f);
	}
}
