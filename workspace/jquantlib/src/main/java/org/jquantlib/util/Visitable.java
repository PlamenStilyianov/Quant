/*
 Copyright (C) 2007 Richard Gomes

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

package org.jquantlib.util;

/**
 * This interface defines the {@link Visitable} side of the Visitor design pattern 
 *
 * @author Richard Gomes
 * 
 * @see Visitor
 * @see PolymorphicVisitor
 * @see PolymorphicVisitable
 * 
 * @see <a href="http://www.exciton.cs.rice.edu/JavaResources/DesignPatterns/VisitorPattern.htm">The Visitor Design Pattern</a>
 *
 * @param <T> defines de data structure to be visited
 */
public interface Visitable<T> {

	/**
	 * This method is responsible for determining if a Visitor passed as argument is eligible for handling the data structures
	 * kept by <code>this</code> class. In the affirmative case, <code>accept</code> is responsible for passing <code>this</code>
	 * data structures to the Visitor. 
	 * 
	 * @param v is the Visitor<T>
	 * 
	 * @see Visitor
	 * @see PolymorphicVisitor
	 * @see PolymorphicVisitable
	 */
    public void accept(Visitor<T> v);
	
}
