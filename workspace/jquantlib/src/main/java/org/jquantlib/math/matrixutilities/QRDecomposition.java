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

/*
 Copyright (C) 2008 Klaus Spanderen

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

import java.util.Arrays;

import org.jquantlib.QL;
import org.jquantlib.math.optimization.Minpack;


/**
 * QR decompoisition
 * <p>
 * This implementation is based on <a href="http://www1.fpl.fs.fed.us/optimization.html">MINPACK/J</a>
 * <p>
 * This subroutine uses householder transformations with <i>optional</i> column pivoting to compute a QR factorization of
 * the {@latex$ m} by {@latex$ n} matrix {@latex$ A}.
 * <p>
 * That is, Minpack_f77#qrfac determines an orthogonal matrix {@latex$ Q}, a permutation matrix {@latex$ P}, and
 * an upper trapezoidal matrix {@latex$ R} with diagonal elements of nonincreasing magnitude, such that {@latex$ A*P = Q*R}.
 * <p>
 * Return value <code>ipvt</code> is an integer array of length {@latex$ n}, which defines the permutation matrix {@latex$ P}
 * such that {@latex$ A*P = Q*R}.
 * <p>
 * Column {@latex$ j} of {@latex$ P} is column <code>ipvt[j]</code> of the identity matrix.
 * <p>
 * See lmdiff.cpp for further details.
 *
 * @see <a href="http://mathworld.wolfram.com/QRDecomposition.html">MathWorld</a>
 * @see <a href="http://en.wikipedia.org/wiki/QR_decomposition">Wikipedia</a>
 * @see <a href="http://www1.fpl.fs.fed.us/optimization.html">MINPACK/J</a>
 * @see <a href="http://www.netlib.org/minpack">MINPACK</a>
 *
 * @author Richard Gomes
 */
public class QRDecomposition {

    private final int m;
    private final int n;
    private final Matrix A;
    private final Matrix Q;
    private final Matrix R;
    private final Matrix P;
    private final int ipvt[];
    private final boolean isNonSingular;


    /**
     * Calculates the QR-decomposition of the given matrix.
     * @param A The matrix to decompose.
     */
    public QRDecomposition(final Matrix matrix) {
        this(matrix, false);
    }


    /**
     * Calculates the QR-decomposition of the given matrix.
     * @param M The matrix to decompose.
     */
    public QRDecomposition(final Matrix A, final boolean pivot) {
        this.A = A;
        this.m = A.rows();
        this.n = A.cols();
        this.ipvt = new int[n];

        final Matrix mT = A.clone().toJava().transpose();
        final Array rdiag = new Array(n);
        final Array wa = new Array(n);

        System.out.println("mT (BEFORE) = " + mT);
        Minpack.qrfac(
                m, n,
                mT,                      // input/output parameter (sorry for that :~ )
                pivot,
                ipvt, rdiag, rdiag, wa  // output parameters (sorry for that :~ )
                );

        System.out.println("mT (AFTER)  = " + mT);
        System.out.println("Array ipvt = " + Arrays.toString(ipvt));
        System.out.println("Array rdiag = " + rdiag);
        System.out.println("Array wa = " + wa);

        // obtain R
        final double[][] r = new double[n][n];
        for (int i=0; i < n; i++) {
            // r[i][i] = rdiag[i];
            r[i][i] = rdiag.get(i);
            if (i < m) {
                // std::copy(mT.column_begin(i)+i+1, mT.column_end(i), r.row_begin(i)+i+1);
                for (int k = i + 1; k < n; k++) {
                    // r[i][k] = mT[k][i];
                    r[i][k] = mT.get(k, i);
                }
            }
        }

        // obtain Q
        final double[][] q = new double[m][n];
        final double w[] = new double[m];
        for (int k=0; k < m; k++) {
            Arrays.fill(w, 0.0);
            w[k] = 1.0;
            for (int j=0; j < Math.min(n, m); j++) {
                final double t3 = mT.get(j, j);
                if (t3 != 0.0) {
                    // final double t = std::inner_product(mT.row_begin(j)+j, mT.row_end(j), w.begin()+j, 0.0)/t3;
                    double t = 0.0;
                    for (int p = j; p < m; p++) {
                        t += mT.get(j, p) * w[p];
                    }
                    t /= t3;

                    for (int i=j; i<m; i++) {
                        w[i] -= mT.get(j, i) * t;
                    }
                }
                q[k][j] = w[j];
            }
        }

        // reverse column pivoting
        final double[][] p = new double[n][n];
        if (pivot) {
            for (int i=0; i < n; ++i) {
                p[ipvt[i]][i] = 1.0;
            }
        } else {
            for (int i=0; i < n; ++i) {
                p[i][i] = 1.0;
            }
        }

        this.isNonSingular = isNonSingular(rdiag.$);

        final boolean fortran = this.A.addr.isFortran();
        this.R = fortran ? new Matrix(r).toFortran() : new Matrix(r);
        this.Q = fortran ? new Matrix(q).toFortran() : new Matrix(q);
        this.P = fortran ? new Matrix(p).toFortran() : new Matrix(p);

        System.out.println("Matrix Q = "+Q.toString());
        System.out.println("Matrix R = "+R.toString());
        System.out.println("Matrix P = "+P.toString());
        System.out.println("Matrix mT = "+mT.toString());
    }

    public Matrix Q() {
        return Q;
    }

    public Matrix R() {
        return R;
    }

    public Matrix P() {
        return P;
    }

    public boolean isNonSingular() {
        return isNonSingular;
    }


    public Array solve (
            final Array b,
            final boolean pivot,
            final Array d) {

        QL.require(b.size() == m, "dimensions of A and b don't match");
        QL.require(d != null && !d.empty() && d.size() == n, "dimensions of A and d don't match");

        final Matrix aT = A.transpose();
        final Matrix rT = R.transpose();

        final Array sdiag = new Array(n);
        final Array wa = new Array(n);

        final Array ld = new Array(n);
        if (d!=null && !d.empty()) {
            ld.fill(d);
        }
        final Array x = new Array(n);
        final Array qtb = Q.transpose().mul(b);

        throw new UnsupportedOperationException();
        //TODO:: Minpack.qrsolv(n, rT, n, ipvt, ld, qtb, x, sdiag, wa);
        //TODO:: return x;
    }


    //
    // private methods
    //

    private boolean isNonSingular(final double[] rdiag) {
        for (final double diag : rdiag) {
            if (diag == 0)
                return false;
        }
        return true;
    }

}
