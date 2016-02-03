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
package org.jquantlib.math.matrixutilities;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;

/**
 * LU Decomposition.
 * <P>
 * For an m-by-n matrix A with m >= n, the LU decomposition is an m-by-n unit lower triangular matrix L, an n-by-n upper triangular
 * matrix U, and a permutation vector piv of length m so that A(piv,:) = L*U. If m < n, then L is m-by-m and U is m-by-n.
 * <P>
 * The LU decomposition with pivoting always exists, even if the matrix is singular, so the constructor will never fail. The primary
 * use of the LU decomposition is in the solution of square systems of simultaneous linear equations. This will fail if
 * isNonsingular() returns false.
 *
 * @note  This class is adapted from JAMA
 * @see <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a>
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q1_TRANSLATION, version = Version.OTHER, reviewers = { "Richard Gomes" })
public class LUDecomposition {

    private final static String MATRIX_IS_SINGULAR = "Matrix is singular";

    //
    // private fields
    //

    private final int m;
    private final int n;
    private final Matrix LU;
    private final int piv[];

    private int pivsign;


    //
    // public constructors
    //

    /**
     * LU Decomposition
     *
     * @param A is a rectangular matrix
     * @return Structure to access L, U and piv.
     */
    public LUDecomposition(final Matrix A) {
        this.LU = A.clone().toJava();
        this.m = LU.rows();
        this.n = LU.cols();

        // initialize pivots
        this.piv = new int[m];
        for (int i = 0; i < m; i++) {
            piv[i] = i;
        }
        this.pivsign = 1;

        final double[] LUcolj = new double[m];

        // Outer loop.

        for (int j = 0; j < n; j++) {

            // Make a copy of the j-th column to localize references.

            for (int i = 0; i < m; i++) {
                LUcolj[i] = LU.$[LU.addr.op(i, j)];
            }

            // Apply previous transformations.

            for (int i = 0; i < m; i++) {
                // Most of the time is spent in the following dot product.

                final int kmax = Math.min(i, j);
                double s = 0.0;
                for (int k = 0; k < kmax; k++) {
                    s += LU.$[LU.addr.op(i, k)] * LUcolj[k];
                }

                LU.$[LU.addr.op(i, j)] = LUcolj[i] -= s;
            }

            // Find pivot and exchange if necessary.

            int p = j;
            for (int i = j + 1; i < m; i++) {
                if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
                    p = i;
                }
            }
            if (p != j) {
                for (int k = 0; k < n; k++) {
                    final double t = LU.$[LU.addr.op(p, k)];
                    LU.$[LU.addr.op(p, k)] = LU.$[LU.addr.op(j, k)];
                    LU.$[LU.addr.op(j, k)] = t;
                }
                final int k = piv[p];
                piv[p] = piv[j];
                piv[j] = k;
                pivsign = -pivsign;
            }

            // Compute multipliers.

            if (j < m && LU.$[LU.addr.op(j, j)] != 0.0) { //FINDBUGS:: NS_DANGEROUS_NON_SHORT_CIRCUIT (solved)
                for (int i = j + 1; i < m; i++) {
                    LU.$[LU.addr.op(i, j)] /= LU.$[LU.addr.op(j, j)];
                }
            }
        }
    }

    //
    // public methods
    //

    /**
     * Is the matrix nonsingular?
     *
     * @return true if U, and hence A, is nonsingular.
     */
    public boolean isNonSingular() {
        for (int j = 0; j < n; j++) {
            if (LU.$[LU.addr.op(j, j)] == 0)
                return false;
        }
        return true;
    }

    /**
     * Return lower triangular factor
     *
     * @return L
     */
    public Matrix L() {
        final Matrix L = new Matrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) {
                    L.$[L.addr.op(i, j)] = LU.$[LU.addr.op(i, j)];
                } else if (i == j) {
                    L.$[L.addr.op(i, j)] = 1.0;
//XXX - not needed
//                } else {
//                    L.data[L.addr.op(i, j)] = 0.0;
                }
            }
        }
        return L;
    }

    /**
     * Return upper triangular factor
     *
     * @return U
     */
    public Matrix U() {
        final Matrix U = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i <= j) {
                    U.$[U.addr.op(i, j)] = LU.$[LU.addr.op(i, j)];
//XXX - not needed
//                } else {
//                    U.data[U.addr.op(i, j)] = 0.0;
                }
            }
        }
        return U;
    }

    /**
     * Return pivot permutation vector
     *
     * @return piv
     */
    //FIXME: code review
    //FIXME: should return ArrayInt (array of integers) instead
    public int[] getPivot() {
        final int[] p = new int[m];
        for (int i = 0; i < m; i++) {
            p[i] = piv[i];
        }
        return p;
    }

//XXX
//    /**
//     * Return pivot permutation vector as a one-dimensional double array
//     *
//     * @return (double) piv
//     */
//    public double[] getDoublePivot() {
//        final double[] vals = new double[m];
//        for (int i = 0; i < m; i++) {
//            vals[i] = piv[i];
//        }
//        return vals;
//    }

    /**
     * Determinant
     *
     * @return det(A)
     * @exception IllegalArgumentException Matrix must be square
     */
    public double det() {
        QL.require(m == n, Matrix.MATRIX_MUST_BE_SQUARE); // QA:[RG]::verified

        double d = pivsign;
        for (int j = 0; j < n; j++) {
            d *= LU.$[LU.addr.op(j, j)];
        }
        return d;
    }

    /**
     * Solve A*X = B
     *
     * @param B a Matrix with as many m as A and any number of columns.
     * @return X so that L*U*X = B(piv,:)
     * @exception IllegalArgumentException Matrix row dimensions must agree.
     * @exception LibraryException Matrix is singular.
     */
    public Matrix solve(final Matrix B) {
        QL.require(B.rows() == this.m, Matrix.MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        if (!this.isNonSingular())
            throw new LibraryException(MATRIX_IS_SINGULAR);

        // Copy right hand side with pivoting
        final Matrix X = B.range(piv, 0, B.cols());

        // Solve L*Y = B(piv,:)
        for (int k = 0; k < n; k++) {
            for (int i = k + 1; i < n; i++) {
                for (int j = 0; j < B.cols(); j++) {
                    X.$[X.addr.op(i, j)] -= X.$[X.addr.op(k, j)] * LU.$[LU.addr.op(i, k)];
                }
            }
        }
        // Solve U*X = Y;
        for (int k = n - 1; k >= 0; k--) {
            for (int j = 0; j < B.cols(); j++) {
                X.$[X.addr.op(k, j)] /= LU.$[LU.addr.op(k, k)];
            }
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < B.cols(); j++) {
                    X.$[X.addr.op(i, j)] -= X.$[X.addr.op(k, j)] * LU.$[LU.addr.op(i, k)];
                }
            }
        }
        return X;
    }

}