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
 * Newton Safe 1d solver.
 *
 * @see Book: <i>Press, Teukolsky, Vetterling, and Flannery, "Numerical Recipes in C", 2nd edition, Cambridge University Press</i>
 * 
 * @author Dominik Holenstein
 */
public class NewtonSafe extends AbstractSolver1D<Derivative> {

    /**
     * Computes the roots of a function by using the Newton Safe method.
     * @param f the function
     * @param xAccuracy the provided accuracy
     * @returns <code>root_</code>
     */
    @Override
    protected double solveImpl(final Derivative f, final double xAccuracy) {

        double froot, dfroot, dx, dxold;
        double xh, xl;

        // Orient the search so that f(xl) < 0
        if (fxMin < 0.0) {
            xl=xMin;
            xh=xMax;
        } else {
            xh=xMin;
            xl=xMax;
        }

        // the "stepsize before last"
        dxold=xMax-xMin;
        // it was dxold=std::fabs(xMax_-xMin_); in Numerical Recipes
        // here (xMax_-xMin_ > 0) is verified in the constructor

        // and the last step
        dx=dxold;

        froot = f.op(root);
        dfroot = f.derivative(root);
        evaluationNumber++;

        while (evaluationNumber<= getMaxEvaluations()) {
            // Bisect if (out of range || not decreasing fast enough)
            if ((((root-xh)*dfroot-froot)*
                    ((root-xl)*dfroot-froot) > 0.0)
                    || (Math.abs(2.0*froot) > Math.abs(dxold*dfroot))) {

                dxold = dx;
                dx = (xh-xl)/2.0;
                root=xl+dx;
            } else {
                dxold=dx;
                dx=froot/dfroot;
                root -= dx;
            }
            // Convergence criterion
            if (Math.abs(dx) < xAccuracy)
                return root;

            froot = f.op(root);
            dfroot = f.derivative(root);
            evaluationNumber++;

            if (froot < 0.0)
                xl=root;
            else
                xh=root;
        }
        throw new ArithmeticException("maximum number of function evaluations exceeded"); // TODO: message
    }
}
