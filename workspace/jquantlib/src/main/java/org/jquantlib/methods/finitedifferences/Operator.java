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
// CODE REVIEW: Do we really need this interface. Helps to easily replaces Tridiaginal implementations
public interface Operator {

	public int size();

	public boolean isTimeDependent();

	public void setTime(double t);

	public <T extends Operator> T identity(int size);

	public Array applyTo(Array a);

	public Array solveFor(Array a);
    public double[] solveFor(double[] a);

    public <T extends Operator> void swap(T from);

    public <T extends Operator> T add(T d);

    public <T extends Operator> T subtract(T d);

    public <T extends Operator> T multiply(double a);


//
// methods not called, not tested
//
//	Array SOR(Array rhs, int tol);
//	<T extends Operator> void swap(T op1, T op2);
//	<T extends Operator> T add(T a, T b);
//	<T extends Operator> T subtract(T a, T b);
//	<T extends Operator> T multiply(double a, final T d);
//	<T extends Operator> T multiply(T d, double a);
//	<T extends Operator> T divide(T d, double a);

}
