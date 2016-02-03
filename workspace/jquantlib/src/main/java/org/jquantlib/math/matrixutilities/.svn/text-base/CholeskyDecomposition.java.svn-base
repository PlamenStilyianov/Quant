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

/*
 Copyright (C) 2003, 2004 Ferdinando Ametrano

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

package org.jquantlib.math.matrixutilities;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;

/**
 * Cholesky Decomposition.
 * <P>
 * For a symmetric, positive definite matrix A, the Cholesky decomposition is an lower triangular matrix L so that A = L*L'.
 * <P>
 * If the matrix is not symmetric or positive definite, the constructor returns a partial decomposition and sets an internal flag
 * that may be queried by the isSPD() method.
 *
 * @note  This class is adapted from JAMA
 * @see <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a>
 *
 * @author Richard Gomes
 */
// FIXME: http://bugs.jquantlib.org/view.php?id=340
@QualityAssurance(quality = Quality.Q1_TRANSLATION, version = Version.OTHER, reviewers = { "Richard Gomes" })
public class CholeskyDecomposition {

    private final static String MATRIX_IS_NOT_SIMMETRIC_POSITIVE = "Matrix is not symmetric positive definite.";


    //
    // private fields
    //

    /** Row and column dimension (square matrix). */
    private final int n;

    /** Matrix for internal storage of decomposition. */
    private final Matrix L;

    /** Symmetric and positive definite flag. */
    private boolean isspd;


    //
    // public constructors
    //

    /**
     * Cholesky algorithm for symmetric and positive definite matrix.
     *
     * @param A is a square, symmetric matrix.
     * @return Structure to access L and isspd flag.
     */
    public CholeskyDecomposition(final Matrix A) {
        QL.require(A.rows() == A.cols(), Matrix.MATRIX_MUST_BE_SQUARE); // QA:[RG]::verified

        this.n = A.rows();
        this.L = new Matrix(n, n);
        this.isspd = (A.rows() == A.cols());

        // Main loop.
        for (int j = 0; j < n; j++) {
            double d = 0.0;
            for (int k = 0; k < j; k++) {
                double s = 0.0;
                for (int i = 0; i < k; i++) {
                    s += L.$[L.addr.op(k, i)] * L.$[L.addr.op(j, i)];
                }
                L.$[L.addr.op(j, k)] = s = (A.$[A.addr.op(j, k)] - s) / L.$[L.addr.op(k, k)];
                d = d + s * s;
                isspd = isspd & (A.$[A.addr.op(k, j)] == A.$[A.addr.op(j, k)]);
            }
            d = A.$[A.addr.op(j, j)] - d;
            isspd = isspd && (d > 0.0); //FINDBUGS:: NS_DANGEROUS_NON_SHORT_CIRCUIT (solved)
            L.$[L.addr.op(j, j)] = Math.sqrt(Math.max(d, 0.0));
            for (int k = j + 1; k < n; k++) {
                L.$[L.addr.op(j, k)] = 0.0;
            }
        }
    }

    //
    // public methods
    //

    /**
     * Is the matrix symmetric and positive definite?
     *
     * @return true if A is symmetric and positive definite.
     */
    public boolean isSPD() {
        return isspd;
    }

    /**
     * Return triangular factor.
     *
     * @return L
     */
    public Matrix L() {
        return L.clone();
    }

    /**
     * Solve A*X = B
     *
     * @param m a Matrix with as many rows as A and any number of columns.
     * @return X so that L*L'*X = B
     * @exception IllegalArgumentException Matrix row dimensions must agree.
     * @exception LibraryException Matrix is not symmetric positive definite.
     */

    public Matrix solve(final Matrix B) {
        QL.require(B.rows() == this.n, Matrix.MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        if (!this.isSPD())
            throw new LibraryException(MATRIX_IS_NOT_SIMMETRIC_POSITIVE);

        // Copy right hand side.
        final int nx = B.cols();
        final Matrix X = B.clone();

        // Solve L*Y = B;
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < nx; j++) {
                for (int i = 0; i < k; i++) {
                    X.$[X.addr.op(k, j)] -= X.$[X.addr.op(i, j)] * L.$[L.addr.op(k, i)];
                }
                X.$[X.addr.op(k, j)] /= L.$[L.addr.op(k, k)];
            }
        }

        // Solve L'*X = Y;
        for (int k = n - 1; k >= 0; k--) {
            for (int j = 0; j < nx; j++) {
                for (int i = k + 1; i < n; i++) {
                    X.$[X.addr.op(k, j)] -= X.$[X.addr.op(i, j)] * L.$[L.addr.op(i, k)];
                }
                X.$[X.addr.op(k, j)] /= L.$[L.addr.op(k, k)];
            }
        }

        return X;
    }

}
