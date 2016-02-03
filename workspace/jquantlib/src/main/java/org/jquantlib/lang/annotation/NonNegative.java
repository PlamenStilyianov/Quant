package org.jquantlib.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This annotation is intended to mark a type as non-negative number
 * 
 * @note An annotation processor is responsible for instrumenting the bytecode as needed for
 * guarantee that a field, local variable or parameter only assumes valid values.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Natural_numbers">Natural number</a>
 * @see <a href="http://groups.csail.mit.edu/pag/jsr308/">JSR 308: Annotations on Java Types</a>
 * @see <a href="http://www.jquantlib.org/index.php/Strong_Type_Checking">Strong Type Checking</a>  
 * 
 * @author Richard Gomes
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
public @interface NonNegative {
    // tagging annotation
}
