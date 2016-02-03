/*
Copyright (C) 2009 Richard Gomes

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
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb

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

package org.jquantlib.model;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.NoConstraint;

/**
 * Piecewise-constant parameter.
 * <p>
 * {@latex$ a(t) = a_i} if {@latex$ t_{i-1} \geq t < t_i }.
 * <p>
 * This kind of parameter is usually used to enhance the fitting of a model.
 *
 * @author Richard Gomes
 */
public class PiecewiseConstantParameter extends Parameter {


    //
    // public methods
    //

    public PiecewiseConstantParameter(final /* @Time */ double[] times) {
        super(times.length+1, new Impl(times), new NoConstraint());
    }


    //
    // private inner classes
    //

    static private class Impl implements Parameter.Impl {

        //
        // private fields
        //

        private final double[] times_;

        //
        // public methods
        //

        public Impl(final double[] times) {
            this.times_ = times;
        }

        @Override
        public double value(final Array  params, /* @Time */ final double t) /* @ReadOnly */ {
            /*@NonNegative*/ final int size = times_.length;
            for (/*@NonNegative*/ int i=0; i<size; i++)
                if (t<times_[i])
                    return params.get(i);
            return params.get(size);
        }
    }

}