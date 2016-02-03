/*

Copyright (C) 2008 Q.Boiler

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
Copyright (C) 2007 Mark Joshi

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
package org.jquantlib.math.matrixutilities;



/**
 * @author M. Do
 * @author Q. Boiler
 */

//
// TODO: code review
//
// This class belongs to QuantLib0.9.x and should not be translated whilst we still work on QuantLib 0.8.1.
// This note can be removed when JQuantLib evolves and the original (C++) class BasisIncompleteOrdered
// becomes completely translated an integrated to all dependencies.
//
//

// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class BasisIncompleteOrdered {

    public BasisIncompleteOrdered() {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }

//	private final int euclideanDimension;
//	private final List<Array> currentBasis;
//
//	public BasisIncompleteOrdered(final int euclideanDimension) {
//
//	    if (System.getProperty("EXPERIMENTAL")==null) throw new UnsupportedOperationException("not implemented");
//	    this.euclideanDimension = euclideanDimension;
//	    this.currentBasis = new ArrayList<Array>();
//	}
//
//	public boolean addVector(final Array newVector1) {
//		if (newVector1.length != euclideanDimension) {
//			throw new LibraryException("BasisIncompleteOrdered : missized vector passed");
//		}
//
//		if (currentBasis.size() == euclideanDimension) {
//			return false;
//		}
//
//		for (int j = 0; j < currentBasis.size(); ++j) {
//			double innerProd = newVector1.dotProduct(currentBasis.get(j));
//			Array data = newVector1;
//
//			// TODO:: code review against C++ sources
//
//			Array currentBasisAtJ = currentBasis.get(j);
//			for (int k = 0; k < euclideanDimension; ++k) {
//			    double value = data.get(k) - currentBasis.get(j) * innerProd;
//			    data.set(k, value);
//			}
//		}
//
//		double norm = Math.sqrt(newVector1.dotProduct(newVector1));
//
//		if (norm < 1e-12) // maybe this should be a tolerance
//		{
//			return false;
//		}
//        // TODO: code review :: use of clone()
//		Array data = newVector1.clone();
//		data.divAssign(norm);
//		currentBasis.add(data);
//
//		return true;
//	}
//
//	public int basisSize() {
//		return currentBasis.size();
//	}
//
//	public int euclideanDimension() {
//		return euclideanDimension;
//	}
//
//	public Matrix getBasisAsRowsInMatrix() {
//	    //TODO: consider a convenience method in class Matrix intended to copy a rectangular region from another Matrix
//		Matrix basis = new Matrix(currentBasis.size(), euclideanDimension);
//		for (int row = 0; row < basis.rows; row++) {
//			basis.setRow(row, currentBasis.get(row));
//		}
//		return basis;
//	}

}
