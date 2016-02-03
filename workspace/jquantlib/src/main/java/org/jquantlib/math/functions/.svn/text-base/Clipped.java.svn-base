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

import org.jquantlib.math.Ops;

/**
 * This class verifies a condition and if true, returns the evaluation of
 * a function, otherwise returns Double.NaN.
 *
 * @author Richard Gomes
 */
public final class Clipped implements Ops.DoubleOp {

    private final Ops.DoublePredicate checker;
    private final Ops.DoubleOp function;

    public Clipped(final Ops.DoublePredicate checker, final Ops.DoubleOp function){
        this.checker = checker;
        this.function = function;
    }


    //
    // implements Ops.DoubleOp
    //

	@Override
	public double op(final double a) {
        return checker.op(a) ? function.op(a) : Double.NaN;
	}

}


