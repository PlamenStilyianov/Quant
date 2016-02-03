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

import java.util.List;

import org.jquantlib.math.Ops;
import org.jquantlib.math.Ops.DoubleOp;

/**
 * Processes a sequence of functions
 *
 * @author Richard Gomes
 */
public class Expression implements Ops.DoubleOp {

    private final List<DoubleOp> list;

    public Expression(final List<DoubleOp> list) {
        this.list = list;
    }


	//
    // implements Ops.DoubleOp
    //

	@Override
	public double op(final double a) {
        double result = a;
        for (int i = 0; i<list.size(); i++)
            result = list.get(i).op(result);
        return result;
	}

}
