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

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;

/**
 * Singular Value Decomposition
 * <P>
 * For an m-by-n matrix A with m >= n, the singular value decomposition is an m-by-n orthogonal matrix U, an n-by-n diagonal matrix
 * S, and an n-by-n orthogonal matrix V so that A = U*S*V'.
 * <P>
 * The singular values, sigma[k] = S.data[S.addr.op(k,k)], are ordered so that sigma[0] >= sigma[1] >= ... >= sigma[n-1].
 * <P>
 * The singular value decompostion always exists, so the constructor will never fail. The matrix condition number and the effective
 * numerical rank can be computed from this decomposition.
 *
 * @note  This class was adapted from JAMA
 * @see <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a>
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q1_TRANSLATION, version = Version.OTHER, reviewers = { "Richard Gomes" })
public class SVD {

    private final Matrix U;
    private final Matrix V;
    private final Matrix S;
    private final Array s;
    private final int m;
    private final int n;

    //
    // public constructors
    //

    /**
     * Construct the singular value decomposition
     *
     * @param A is a rectangular matrix
     * @return Structure to access U, S and V.
     */
    public SVD(final Matrix A) {
        this.m = A.rows();
        this.n = A.cols();

        final int nu = Math.min(m, n);

        this.U = new Matrix(m, nu);
        this.V = new Matrix(n, n);
        this.S = new Matrix(n, n);
        this.s = new Array(Math.min(m + 1, n));

        final double[] e = new double[n];
        final double[] work = new double[m];
        final boolean wantu = true;
        final boolean wantv = true;

        // Reduce A to bidiagonal form, storing the diagonal elements
        // in s and the super-diagonal elements in e.

        final int nct = Math.min(m - 1, n);
        final int nrt = Math.max(0, Math.min(n - 2, m));
        for (int k = 0; k < Math.max(nct, nrt); k++) {
            if (k < nct) {

                // Compute the transformation for the k-th column and
                // place the k-th diagonal in s.data[s.addr.op(k)].
                // Compute 2-norm of k-th column without under/overflow.
                s.$[s.addr.op(k)] = 0;
                for (int i = k; i < m; i++) {
                    s.$[s.addr.op(k)] = Math.hypot(s.$[s.addr.op(k)], A.$[A.addr.op(i, k)]);
                }
                if (s.$[s.addr.op(k)] != 0.0) {
                    if (A.$[A.addr.op(k, k)] < 0.0) {
                        s.$[s.addr.op(k)] = -s.$[s.addr.op(k)];
                    }
                    for (int i = k; i < m; i++) {
                        A.$[A.addr.op(i, k)] /= s.$[s.addr.op(k)];
                    }
                    A.$[A.addr.op(k, k)] += 1.0;
                }
                s.$[s.addr.op(k)] = -s.$[s.addr.op(k)];
            }
            for (int j = k + 1; j < n; j++) {
                if ((k < nct) & (s.$[s.addr.op(k)] != 0.0)) {

                    // Apply the transformation.

                    double t = 0;
                    for (int i = k; i < m; i++) {
                        t += A.$[A.addr.op(i, k)] * A.$[A.addr.op(i, j)];
                    }
                    t = -t / A.$[A.addr.op(k, k)];
                    for (int i = k; i < m; i++) {
                        A.$[A.addr.op(i, j)] += t * A.$[A.addr.op(i, k)];
                    }
                }

                // Place the k-th row of A into e for the
                // subsequent calculation of the row transformation.

                e[j] = A.$[A.addr.op(k, j)];
            }
            if (wantu & (k < nct)) {

                // Place the transformation in U for subsequent back
                // multiplication.

                for (int i = k; i < m; i++) {
                    U.$[U.addr.op(i, k)] = A.$[A.addr.op(i, k)];
                }
            }
            if (k < nrt) {

                // Compute the k-th row transformation and place the
                // k-th super-diagonal in e[k].
                // Compute 2-norm without under/overflow.
                e[k] = 0;
                for (int i = k + 1; i < n; i++) {
                    e[k] = Math.hypot(e[k], e[i]);
                }
                if (e[k] != 0.0) {
                    if (e[k + 1] < 0.0) {
                        e[k] = -e[k];
                    }
                    for (int i = k + 1; i < n; i++) {
                        e[i] /= e[k];
                    }
                    e[k + 1] += 1.0;
                }
                e[k] = -e[k];
                if ((k + 1 < m) & (e[k] != 0.0)) {

                    // Apply the transformation.

                    for (int i = k + 1; i < m; i++) {
                        work[i] = 0.0;
                    }
                    for (int j = k + 1; j < n; j++) {
                        for (int i = k + 1; i < m; i++) {
                            work[i] += e[j] * A.$[A.addr.op(i, j)];
                        }
                    }
                    for (int j = k + 1; j < n; j++) {
                        final double t = -e[j] / e[k + 1];
                        for (int i = k + 1; i < m; i++) {
                            A.$[A.addr.op(i, j)] += t * work[i];
                        }
                    }
                }
                if (wantv) {

                    // Place the transformation in V for subsequent
                    // back multiplication.

                    for (int i = k + 1; i < n; i++) {
                        V.$[V.addr.op(i, k)] = e[i];
                    }
                }
            }
        }

        // Set up the final bidiagonal matrix or order p.

        int p = Math.min(n, m + 1);
        if (nct < n) {
            s.$[nct] = A.$[A.addr.op(nct, nct)];
        }
        if (m < p) {
            s.$[p - 1] = 0.0;
        }
        if (nrt + 1 < p) {
            e[nrt] = A.$[A.addr.op(nrt, p - 1)];
        }
        e[p - 1] = 0.0;

        // If required, generate U.

        if (wantu) {
            for (int j = nct; j < nu; j++) {
                for (int i = 0; i < m; i++) {
                    U.$[U.addr.op(i, j)] = 0.0;
                }
                U.$[U.addr.op(j, j)] = 1.0;
            }
            for (int k = nct - 1; k >= 0; k--) {
                if (s.$[s.addr.op(k)] != 0.0) {
                    for (int j = k + 1; j < nu; j++) {
                        double t = 0;
                        for (int i = k; i < m; i++) {
                            t += U.$[U.addr.op(i, k)] * U.$[U.addr.op(i, j)];
                        }
                        t = -t / U.$[U.addr.op(k, k)];
                        for (int i = k; i < m; i++) {
                            U.$[U.addr.op(i, j)] += t * U.$[U.addr.op(i, k)];
                        }
                    }
                    for (int i = k; i < m; i++) {
                        U.$[U.addr.op(i, k)] = -U.$[U.addr.op(i, k)];
                    }
                    U.$[U.addr.op(k, k)] = 1.0 + U.$[U.addr.op(k, k)];
                    for (int i = 0; i < k - 1; i++) {
                        U.$[U.addr.op(i, k)] = 0.0;
                    }
                } else {
                    for (int i = 0; i < m; i++) {
                        U.$[U.addr.op(i, k)] = 0.0;
                    }
                    U.$[U.addr.op(k, k)] = 1.0;
                }
            }
        }

        // If required, generate V.

        if (wantv) {
            for (int k = n - 1; k >= 0; k--) {
                if ((k < nrt) && (e[k] != 0.0)) { //FINDBUGS:: NS_DANGEROUS_NON_SHORT_CIRCUIT (solved)
                    for (int j = k + 1; j < nu; j++) {
                        double t = 0;
                        for (int i = k + 1; i < n; i++) {
                            t += V.$[V.addr.op(i, k)] * V.$[V.addr.op(i, j)];
                        }
                        t = -t / V.$[V.addr.op(k + 1, k)];
                        for (int i = k + 1; i < n; i++) {
                            V.$[V.addr.op(i, j)] += t * V.$[V.addr.op(i, k)];
                        }
                    }
                }
                for (int i = 0; i < n; i++) {
                    V.$[V.addr.op(i, k)] = 0.0;
                }
                V.$[V.addr.op(k, k)] = 1.0;
            }
        }

        // Main iteration loop for the singular values.

        final int pp = p - 1;
        int iter = 0;
        final double eps = Math.pow(2.0, -52.0);
        while (p > 0) {
            int k, kase;

            // Here is where a test for too many iterations would go.

            // This section of the program inspects for
            // negligible elements in the s and e arrays. On
            // completion the variables kase and k are set as follows.

            // kase = 1 if s(p) and e[k-1] are negligible and k<p
            // kase = 2 if s(k) is negligible and k<p
            // kase = 3 if e[k-1] is negligible, k<p, and
            // s(k), ..., s(p) are not negligible (qr step).
            // kase = 4 if e(p-1) is negligible (convergence).

            for (k = p - 2; k >= -1; k--) {
                if (k == -1) {
                    break;
                }
                if (Math.abs(e[k]) <= eps * (Math.abs(s.$[s.addr.op(k)]) + Math.abs(s.$[s.addr.op(k + 1)]))) {
                    e[k] = 0.0;
                    break;
                }
            }
            if (k == p - 2) {
                kase = 4;
            } else {
                int ks;
                for (ks = p - 1; ks >= k; ks--) {
                    if (ks == k) {
                        break;
                    }
                    final double t = (ks != p ? Math.abs(e[ks]) : 0.) + (ks != k + 1 ? Math.abs(e[ks - 1]) : 0.);
                    if (Math.abs(s.$[s.addr.op(ks)]) <= eps * t) {
                        s.$[s.addr.op(ks)] = 0.0;
                        break;
                    }
                }
                if (ks == k) {
                    kase = 3;
                } else if (ks == p - 1) {
                    kase = 1;
                } else {
                    kase = 2;
                    k = ks;
                }
            }
            k++;

            // Perform the task indicated by kase.

            switch (kase) {

            // Deflate negligible s(p).

            case 1: {
                double f = e[p - 2];
                e[p - 2] = 0.0;
                for (int j = p - 2; j >= k; j--) {
                    double t = Math.hypot(s.$[j], f);
                    final double cs = s.$[j] / t;
                    final double sn = f / t;
                    s.$[j] = t;
                    if (j != k) {
                        f = -sn * e[j - 1];
                        e[j - 1] = cs * e[j - 1];
                    }
                    if (wantv) {
                        for (int i = 0; i < n; i++) {
                            t = cs * V.$[V.addr.op(i, j)] + sn * V.$[V.addr.op(i, p - 1)];
                            V.$[V.addr.op(i, p - 1)] = -sn * V.$[V.addr.op(i, j)] + cs * V.$[V.addr.op(i, p - 1)];
                            V.$[V.addr.op(i, j)] = t;
                        }
                    }
                }
            }
                break;

            // Split at negligible s(k).

            case 2: {
                double f = e[k - 1];
                e[k - 1] = 0.0;
                for (int j = k; j < p; j++) {
                    double t = Math.hypot(s.$[j], f);
                    final double cs = s.$[j] / t;
                    final double sn = f / t;
                    s.$[j] = t;
                    f = -sn * e[j];
                    e[j] = cs * e[j];
                    if (wantu) {
                        for (int i = 0; i < m; i++) {
                            t = cs * U.$[U.addr.op(i, j)] + sn * U.$[U.addr.op(i, k - 1)];
                            U.$[U.addr.op(i, k - 1)] = -sn * U.$[U.addr.op(i, j)] + cs * U.$[U.addr.op(i, k - 1)];
                            U.$[U.addr.op(i, j)] = t;
                        }
                    }
                }
            }
                break;

            // Perform one qr step.

            case 3: {

                // Calculate the shift.

                final double scale = Math.max(Math.max(Math.max(Math.max(Math.abs(s.$[p - 1]), Math.abs(s.$[p - 2])),
                        Math.abs(e[p - 2])), Math.abs(s.$[s.addr.op(k)])), Math.abs(e[k]));
                final double sp = s.$[p - 1] / scale;
                final double spm1 = s.$[p - 2] / scale;
                final double epm1 = e[p - 2] / scale;
                final double sk = s.$[s.addr.op(k)] / scale;
                final double ek = e[k] / scale;
                final double b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0;
                final double c = (sp * epm1) * (sp * epm1);
                double shift = 0.0;
                if ((b != 0.0) | (c != 0.0)) {
                    shift = Math.sqrt(b * b + c);
                    if (b < 0.0) {
                        shift = -shift;
                    }
                    shift = c / (b + shift);
                }
                double f = (sk + sp) * (sk - sp) + shift;
                double g = sk * ek;

                // Chase zeros.

                for (int j = k; j < p - 1; j++) {
                    double t = Math.hypot(f, g);
                    double cs = f / t;
                    double sn = g / t;
                    if (j != k) {
                        e[j - 1] = t;
                    }
                    f = cs * s.$[j] + sn * e[j];
                    e[j] = cs * e[j] - sn * s.$[j];
                    g = sn * s.$[j + 1];
                    s.$[j + 1] = cs * s.$[j + 1];
                    if (wantv) {
                        for (int i = 0; i < n; i++) {
                            t = cs * V.$[V.addr.op(i, j)] + sn * V.$[V.addr.op(i, j + 1)];
                            V.$[V.addr.op(i, j + 1)] = -sn * V.$[V.addr.op(i, j)] + cs * V.$[V.addr.op(i, j + 1)];
                            V.$[V.addr.op(i, j)] = t;
                        }
                    }
                    t = Math.hypot(f, g);
                    cs = f / t;
                    sn = g / t;
                    s.$[j] = t;
                    f = cs * e[j] + sn * s.$[j + 1];
                    s.$[j + 1] = -sn * e[j] + cs * s.$[j + 1];
                    g = sn * e[j + 1];
                    e[j + 1] = cs * e[j + 1];
                    if (wantu && (j < m - 1)) {
                        for (int i = 0; i < m; i++) {
                            t = cs * U.$[U.addr.op(i, j)] + sn * U.$[U.addr.op(i, j + 1)];
                            U.$[U.addr.op(i, j + 1)] = -sn * U.$[U.addr.op(i, j)] + cs * U.$[U.addr.op(i, j + 1)];
                            U.$[U.addr.op(i, j)] = t;
                        }
                    }
                }
                e[p - 2] = f;
                iter = iter + 1;
            }
                break;

            // Convergence.

            case 4: {

                // Make the singular values positive.

                if (s.$[s.addr.op(k)] <= 0.0) {
                    s.$[s.addr.op(k)] = (s.$[s.addr.op(k)] < 0.0 ? -s.$[s.addr.op(k)] : 0.0);
                    if (wantv) {
                        for (int i = 0; i <= pp; i++) {
                            V.$[V.addr.op(i, k)] = -V.$[V.addr.op(i, k)];
                        }
                    }
                }

                // Order the singular values.

                while (k < pp) {
                    if (s.$[s.addr.op(k)] >= s.$[s.addr.op(k + 1)]) {
                        break;
                    }
                    double t = s.$[s.addr.op(k)];
                    s.$[s.addr.op(k)] = s.$[s.addr.op(k + 1)];
                    s.$[s.addr.op(k + 1)] = t;
                    if (wantv && (k < n - 1)) {
                        for (int i = 0; i < n; i++) {
                            t = V.$[V.addr.op(i, k + 1)];
                            V.$[V.addr.op(i, k + 1)] = V.$[V.addr.op(i, k)];
                            V.$[V.addr.op(i, k)] = t;
                        }
                    }
                    if (wantu && (k < m - 1)) {
                        for (int i = 0; i < m; i++) {
                            t = U.$[U.addr.op(i, k + 1)];
                            U.$[U.addr.op(i, k + 1)] = U.$[U.addr.op(i, k)];
                            U.$[U.addr.op(i, k)] = t;
                        }
                    }
                    k++;
                }
                iter = 0;
                p--;
            }
                break;
            }
        }


        // Populate S matrix

        for (int i = 0; i < n; i++) {
            // XXX: not needed
            // for (int j = 0; j < n; j++) {
            // S.data[S.addr.op(i,j)] = 0.0;
            // }
            S.$[S.addr.op(i, i)] = s.$[s.addr.op(i)];
        }
    }

    //
    // public methods
    //

    /**
     * Return the left singular vectors
     *
     * @return U
     */
    public Matrix U() {
        return U.clone(); // new Matrix(U,m,Math.min(m+1,n));
    }

    /**
     * Return the right singular vectors
     *
     * @return V
     */
    public Matrix V() {
        return V.clone(); // new Matrix(V,n,n);
    }

    /**
     * Return the one-dimensional array of singular values
     *
     * @return diagonal of S.
     */
    public Array singularValues() {
        return s.clone();
    }

    /**
     * Return the diagonal matrix of singular values
     *
     * @return S
     */
    public Matrix S() {
        return S.clone();
    }

    /**
     * Two norm
     *
     * @return max(S)
     */
    public double norm2() {
        return s.$[s.addr.op(0)];
    }

    /**
     * Two norm condition number
     *
     * @return max(S)/min(S)
     */
    public double cond() {
        return s.$[s.addr.op(0)] / s.$[Math.min(m, n) - 1];
    }

    /**
     * Effective numerical matrix rank
     *
     * @return Number of nonnegligible singular values.
     */
    public int rank() {
        final double eps = Math.pow(2.0, -52.0);
        final double tol = Math.max(m, n) * s.$[s.addr.op(0)] * eps;
        int r = 0;
        for (int i = 0; i < s.size(); i++) {
            if (s.$[s.addr.op(i)] > tol) {
                r++;
            }
        }
        return r;
    }

}
