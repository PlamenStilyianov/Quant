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
package org.jquantlib.math.functions;

import org.jquantlib.QL;
import org.jquantlib.math.Ops;
import org.jquantlib.math.matrixutilities.Array;

/**
 * This class verifies a condition and if true, returns the evaluation of
 * a function, otherwise returns Double.NaN.
 *
 * @author Richard Gomes
 */
public final class FindIf {

    private final Array array;
    private final Ops.DoublePredicate predicate;

    public FindIf(final Array array, final Ops.DoublePredicate predicate) {
        this.array = array;
        this.predicate = predicate;
        QL.validateExperimentalMode();
    }


    public Array op() {
        // find first element which satisfies predicate and insert it, if found
        int pos = 0;
        double item = Double.NaN;
        for (; pos<array.size(); pos++) {
            final double a = array.get(pos);
            if ( predicate.op(a) ) {
                item = a;
                break;
            }
        }
        // allocate return array
        final Array result = new Array(array.size() - pos);
        result.set(0, item);
        // copy remaining elements
        int j=1;
        for (++pos; pos<array.size(); pos++, j++) {
        // while (iterator.hasNext()) {
            result.set(j, array.get(pos) );
        }
        return result;
    }

}


