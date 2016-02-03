/*
Copyright (C) 2008 Praneet Tiwari

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
package org.jquantlib.model;

//reviewed once uh.

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.Constraint;

/**
 * Standard constant parameter {@latex$ a(t) = a }
 *
 * @author Praneet Tiwari
 */
public class ConstantParameter extends Parameter {

    public ConstantParameter(final Constraint constraint) {
        super(1, new Impl(), constraint);
    }

    public ConstantParameter(final double /* @Real */value, final Constraint constraint) {
        super(1, new Impl(), constraint);
        super.params.set(0, value);
        if (!testParams(params))
            throw new IllegalArgumentException(value + ": invalid value");

    }


    //
    // private inner classes
    //

    private static class Impl implements Parameter.Impl {

        @Override
        public double /* @Real */value(final Array params, final double /* @Time */t) {
            return params.first();
        }
    }


}
