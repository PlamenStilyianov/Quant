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

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.AbstractSolver1D;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;

/**
 * Method by 1d solver.
 *
 * @see Book: <i>Press, Teukolsky, Vetterling, and Flannery, "Numerical Recipes in C", 2nd edition, Cambridge University Press</i>
 *
 * @author Dominik Holenstein
 */
public class Ridder extends AbstractSolver1D<Ops.DoubleOp> {

    /**
     * Computes the roots of a function by using the method due to Ridder.
     * @param f the function
     * @param xAccuracy the provided accuracy
     * @returns <code>root_</code>
     */
    @Override
    protected double solveImpl(final Ops.DoubleOp f, final double xAccuracy){

        double fxMid, froot, s, xMid, nextRoot;

        // test on Black-Scholes implied volatility show that
        // Ridder solver algorithm actually provides an
        // accuracy 100 times below promised
        final double xAccuracy_ = xAccuracy/100.0;

        // Any highly unlikely value, to simplify logic below
        root = Constants.QL_MIN_POSITIVE_REAL;

        while (evaluationNumber<= getMaxEvaluations()) {
            xMid=0.5*(xMin+xMax);
            // First of two function evaluations per iteraton
            fxMid=f.op(xMid);
            evaluationNumber++;
            s = Math.sqrt(fxMid*fxMid-fxMin*fxMax);
            if (s == 0.0)
                return root;
            // Updating formula
            nextRoot = xMid + (xMid - xMin) * ((fxMin >= fxMax ? 1.0 : -1.0) * fxMid / s);
            if (Math.abs(nextRoot-root) <= xAccuracy_)
                return root;

            root=nextRoot;
            // Second of two function evaluations per iteration
            froot=f.op(root);
            evaluationNumber++;
            if (froot == 0.0)
                return root;

            // Bookkeeping to keep the root bracketed on next iteration
            if (sign(fxMid,froot) != fxMid) {
                xMin=xMid;
                fxMin=fxMid;
                xMax=root;
                fxMax=froot;
            } else if (sign(fxMin,froot) != fxMin) {
                xMax=root;
                fxMax=froot;
            } else if (sign(fxMax,froot) != fxMax) {
                xMin=root;
                fxMin=froot;
            } else
                throw new LibraryException("internal error"); // TODO: message

            if (Math.abs(xMax-xMin) <= xAccuracy_)
                return root;
        }
        throw new ArithmeticException("maximum number of function evaluations exceeded"); // TODO: message
    }

    private double sign(final double a, final double b) {
        return b >= 0.0 ? Math.abs(a) : -Math.abs(a);
    }
}
