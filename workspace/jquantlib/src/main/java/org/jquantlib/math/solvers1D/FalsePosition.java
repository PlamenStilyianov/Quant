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

package org.jquantlib.math.solvers1D;

import org.jquantlib.math.AbstractSolver1D;
import org.jquantlib.math.Ops;

/**
 * False Position method.
 * <p>
 * The implementation of the algorithm was inspired by
 * <i>Press, Teukolsky, Vetterling, and Flannery,
 * "Numerical Recipes in C", 2nd edition,
 * Cambridge University Press</i>
 * 
 * @author Dominik Holenstein
 */
//TODO FalsePosition: Add test case.
public class FalsePosition extends AbstractSolver1D<Ops.DoubleOp> {

    /**
     * Computes the roots of a function by using the False Position method.
     * @param f the function
     * @param xAccuracy the provided accuracy
     * @returns <code>root_</code>
     */
    @Override
    protected double solveImpl(final Ops.DoubleOp f, final double xAccuracy) {

        double fl, fh, xl, xh, dx, del, froot;

        // Identify the limits so that xl corresponds to the low side
        if (fxMin < 0.0) {
            xl=xMin;
            fl = fxMin;
            xh=xMax;
            fh = fxMax;
        } else {
            xl=xMax;
            fl = fxMax;
            xh=xMin;
            fh = fxMin;
        }
        dx=xh-xl;

        while (evaluationNumber<= getMaxEvaluations()) {
            // Increment with respect to latest value
            root=xl+dx*fl/(fl-fh);
            froot=f.op(root);
            evaluationNumber++;
            if (froot < 0.0) {  // Replace appropriate limit
                del=xl-root;
                xl=root;
                fl=froot;
            } else {
                del=xh-root;
                xh=root;
                fh=froot;
            }
            dx=xh-xl;

            // Convergence criterion
            if (Math.abs(del) < xAccuracy || froot == 0.0)
                return root;
        }
        throw new ArithmeticException("maximum number of function evaluations exceeded"); // TODO: message
    }
}
