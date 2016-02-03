/*
 Copyright (C) 2008 Richard Gomes

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

package org.jquantlib.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This interface is intended to be used as a <i>tagging annotation</i> which is helpful when
 * we add <i>semantic meaning</i> to Java types.
 * <p>
 * As an example, consider an <code>int</code> variable which represents an amount of apples and another
 * <code>int</code> variable which represents an amount of pears. In spite the two variables are <code>int</code> and we
 * surely are able to perform mathematical operations with them, <i>semantically</i> they represent different <i>concepts</i> in
 * the real world and we should never mix them.
 * 
 * @see <a href="http://groups.csail.mit.edu/pag/jsr308/">JSR 308: Annotations on Java Types</a>
 * @see <a href="http://www.jquantlib.org/index.php/Strong_Type_Checking">Strong Type Checking</a>  
 * 
 * @author Richard Gomes
 */
@Typedef
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER })
public @interface Drift {
	// No methods - Tagging annotation
}
