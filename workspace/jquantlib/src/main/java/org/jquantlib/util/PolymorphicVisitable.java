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
 * This interface works together with {@link PolymorphicVisitor} in order to provide
 * the functionality of obtaining a specific {@link Visitor}.
 * <p>
 * This functionality is needed every time a class acts as a Visitor of more
 * than one data structure or when a class acting as a Visitable requires a
 * certain kind of Visitor.
 * 
 * @note A class which implements {@link PolymorphicVisitable} probably does not need
 * to implement {@link Visitable}
 * 
 * @see Visitor
 * @see Visitable
 * @see PolymorphicVisitor
 * @see <a href="http://www.exciton.cs.rice.edu/JavaResources/DesignPatterns/VisitorPattern.htm">The Visitor Design Pattern</a>
 *
 * @param <T> defines the data structure to be visited
 * 
 * @author Richard Gomes
 */
public interface PolymorphicVisitable {

	/**
     * This method is intended to extend the semantics of method {@link Visitable#accept(Visitor)}
     * <p>
     * In a conventional Visitor design pattern, the <code>accept</code> method is called when access to visit a data structure is
     * requested. A {@link Visitor} object is passed as argument in case permission is granted to that {@link Visitor} to access the
     * data structure. Obviously, {@link Visitor}s and {@link Visitable}s work in pairs and the class which provides the data
     * structure to be visited also implements {@link Visitable} in order to properly grant access when the expected {@link Visitor}
     * is received.
     * <p>
     * In the case of a {@link PolymorphicVisitable}, a {@link PolymorphicVisitor} is passed instead of a {@link Visitor}. A
     * {@link PolymorphicVisitor} is in fact, a composition of {@link Visitor}s and not only a single {@link Visitor}. A
     * {@link PolymorphicVisitor} is responsible for returning the correct {@link Visitor} responsible for processing a certain data
     * structure.
     * <p>
     * The initial design of pairs made of &lt;{@link Visitor},{@link Visitable}&gt; is extended to a concept of a matrix made
     * of multiple {@link Visitor}s against multiple {@link Visitable}s. Every class which implements {@link PolymorphicVisitable}
     * passes different data structures when querying {@link PolymorphicVisitor}s.
     * 
     * @see PolymorphicVisitor#getVisitor(Class)
     */
    public void accept(PolymorphicVisitor pv);

}
