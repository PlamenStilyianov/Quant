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
import org.jquantlib.math.Closeness;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;

/**
 * Brent 1-D solver
 * <p>
 * The implementation of the algorithm was inspired by <br/>
 * <i>Press, Teukolsky, Vetterling, and Flannery, "Numerical Recipes in C", 2nd Edition, Cambridge University Press</i>
 *	
 * @author Richard Gomes
 **/
public class Brent extends AbstractSolver1D<Ops.DoubleOp> {

    /**
     * Computes the roots of a function by using the Brent method.
     * 
     * @param f the function
     * @param xAccuracy the provided accuracy
     * @returns <code>root</code>
     */
    @Override
    protected double solveImpl(final Ops.DoubleOp f, final double xAccuracy) {

        double min1, min2;
        double froot, p, q, r, s, xAcc1, xMid;
        double d = 0.0, e = 0.0;

        root = xMax;
        froot = fxMax;
        while (evaluationNumber <= getMaxEvaluations()) {
            if ((froot > 0.0 && fxMax > 0.0) || (froot < 0.0 && fxMax < 0.0)) {
                // Rename xMin, root, xMax and adjust bounds
                xMax = xMin;
                fxMax = fxMin;
                e = d = root - xMin;
            }

            if (Math.abs(fxMax) < Math.abs(froot)) {
                xMin = root;
                root = xMax;
                xMax = xMin;
                fxMin = froot;
                froot = fxMax;
                fxMax = fxMin;
            }

            // Convergence check
            xAcc1 = 2.0 * Constants.QL_EPSILON * Math.abs(root) + 0.5 * xAccuracy;
            xMid = (xMax - root) / 2.0;
            if (Math.abs(xMid) <= xAcc1 || froot == 0.0)
                return root;

            if (Math.abs(e) >= xAcc1 && Math.abs(fxMin) > Math.abs(froot)) {
                // Attempt inverse quadratic interpolation
                s = froot / fxMin;

                if (Closeness.isClose(xMin, xMax)) {
                    p = 2.0 * xMid * s;
                    q = 1.0 - s;
                } else {
                    q = fxMin / fxMax;
                    r = froot / fxMax;
                    p = s * (2.0 * xMid * q * (q - r) - (root - xMin) * (r - 1.0));
                    q = (q - 1.0) * (r - 1.0) * (s - 1.0);
                }
                if (p > 0.0)
                    q = -q; // Check whether in bounds
                p = Math.abs(p);
                min1 = 3.0 * xMid * q - Math.abs(xAcc1 * q);
                min2 = Math.abs(e * q);
                if (2.0 * p < (min1 < min2 ? min1 : min2)) {
                    e = d; // Accept interpolation
                    d = p / q;
                } else {
                    d = xMid; // Interpolation failed, use bisection
                    e = d;
                }
            } else {
                d = xMid; // Bounds decreasing too slowly, use bisection
                e = d;
            }

            xMin = root;
            fxMin = froot;
            if (Math.abs(d) > xAcc1)
                root += d;
            else
                root += sign(xAcc1, xMid);
            froot = f.op(root);
            evaluationNumber++;
        }
        throw new ArithmeticException("maximum number of function evaluations exceeded"); // TODO: message
    }

    private double sign(final double a, final double b) {
        return b >= 0.0 ? Math.abs(a) : -Math.abs(a);
    }

}
