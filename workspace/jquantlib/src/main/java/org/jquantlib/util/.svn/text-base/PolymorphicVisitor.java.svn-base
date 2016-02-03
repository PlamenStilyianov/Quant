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
 * This interface provides the functionality of obtaining a specific {@link Visitor}
 * <p>
 * This functionality is needed every time a class acts as a {@link Visitor} of more than one data structure or when a class acting
 * as a {@link Visitable} requires a specific kind of {@link Visitor}.
 * <p>
 * A class which implements {@link PolymorphicVisitor} is potentially able to provide multiple implementations of {@link Visitor}.
 * 
 * @see Visitor
 * @see Visitable
 * @see PolymorphicVisitable
 * @see <a href="http://www.exciton.cs.rice.edu/JavaResources/DesignPatterns/VisitorPattern.htm">The Visitor Design Pattern</a>
 * 
 * @author Richard Gomes
 */
public interface PolymorphicVisitor {

	/**
	 * This method returns a {@link Visitor} responsible for visiting a specific data structure
	 * 
	 * @param <T> needed for wildcard capture trick which allows type inference at compile time. More information at
	 * <a href="http://download.oracle.com/javase/tutorial/extra/generics/morefun.html">More Fun with Wildcards</a>
	 * 
	 * @param klass specifies the data structure type
	 * @return a Visitor responsible for visiting a certain data structure type
	 * 
	 * @see PolymorphicVisitable#accept(PolymorphicVisitor)
	 */
	public <T> Visitor<T> visitor(Class<? extends T> element);
	
}
