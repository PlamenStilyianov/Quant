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
import org.jquantlib.math.distributions.Derivative;


/**
 * Newton's 1d solver.
 * 
 * @see Book: <i>Press, Teukolsky, Vetterling, and Flannery, "Numerical Recipes in C", 2nd edition, Cambridge University Press</i>
 * 
 * @author Dominik Holenstein
 */
public class Newton extends AbstractSolver1D<Derivative> {

    /**
     * Computes the roots of a function by using Newton's method.
     * @param f the function
     * @param xAccuracy the provided accuracy
     * @returns <code>root_</code>
     */
    @Override
    protected double solveImpl(final Derivative f, final double xAccuracy) {

        double froot, dfroot, dx;

        froot = f.op(root);
        dfroot = f.derivative(root);
        evaluationNumber++;

        while (evaluationNumber<= getMaxEvaluations()) {
            dx=froot/dfroot;
            root -= dx;
            // jumped out of brackets, switch to NewtonSafe
            if ((xMin-root)*(root-xMax) < 0.0) {
                final NewtonSafe s = new NewtonSafe();
                s.setMaxEvaluations(getMaxEvaluations()-evaluationNumber);
                return s.solve(f, xAccuracy, root+dx, xMin, xMax);
            }
            if (Math.abs(dx) < xAccuracy)
                return root;
            froot = f.op(root);
            dfroot = f.derivative(root);
            evaluationNumber++;
        }
        throw new ArithmeticException("maximum number of function evaluations exceeded"); // TODO: message
    }
}
