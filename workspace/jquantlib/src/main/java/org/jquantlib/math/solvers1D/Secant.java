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
import org.jquantlib.math.Ops.DoubleOp;


/**
 * Secant 1d solver.
 *
 * @see Book: <i>Press, Teukolsky, Vetterling, and Flannery, "Numerical Recipes in C", 2nd edition, Cambridge University Press</i>
 * 
 * @author Dominik Holenstein
 */
public class Secant extends AbstractSolver1D<Ops.DoubleOp> {

    /**
     * Computes the roots of a function by using the Secant method.
     * @param f the function
     * @param xAccuracy the provided accuracy
     * @returns <code>root_</code>
     */
    @Override
    protected double solveImpl(final DoubleOp f, final double xAccuracy)  {

        double fl, froot, dx, xl;

        // Pick the bound with the smaller function value
        // as the most recent guess
        if (Math.abs(fxMin) < Math.abs(fxMax)) {
            root = xMin;
            froot = fxMin;
            xl = xMax;
            fl = fxMax;
        } else {
            root = xMax;
            froot = fxMax;
            xl = xMin;
            fl = fxMin;
        }
        while (evaluationNumber <= getMaxEvaluations()) {
            dx = (xl - root) * froot / (froot - fl);
            xl = root;
            fl = froot;
            root += dx;
            froot = f.op(root);
            evaluationNumber++;
            if (Math.abs(dx) < xAccuracy || froot == 0.0)
                return root;
        }
        throw new ArithmeticException("maximum number of function evaluations exceeded"); // TODO: message
    }
}
