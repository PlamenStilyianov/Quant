/*
 Copyright (C) 2009 Ueli Hofstetter

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

package org.jquantlib.processes;

import org.jquantlib.QL;
import org.jquantlib.math.Ops;
import org.jquantlib.math.integrals.GaussKronrodAdaptive;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;

public abstract class LfmCovarianceParameterization {
    protected int size_;
    private final int factors_;

    public LfmCovarianceParameterization(final int size, final int factors) {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        this.size_ = size;
        this.factors_ = factors;
    }

    public int size() {
        return size_;
    }

    public int factors() {
        return factors_;
    }

    public abstract Matrix diffusion(/* @Time */double t, final Array x);

    public Matrix diffusion(/* @Time */final double t) {
        return diffusion(t, new Array(0)); //ZH:QL097 using Null<Array>
    }

    public Matrix covariance(/* @Time */final double t, final Array x) {
        final Matrix sigma = this.diffusion(t, x);
        return sigma.mul(sigma.transpose());
    }

    public Matrix covariance(/* @Time */final double t) {
        return diffusion(t, new Array(0)); //ZH:QL097 using Null<Array> which is new Array(), see Null.hpp
    }

    public Matrix integratedCovariance(/* @Time */final double t, final Array x) {
        // this implementation is not intended for production.
        // because it is too slow and too inefficient.
        // This method is useful for testing and R&D.
        // Please overload the method within derived classes.
        QL.require(!x.empty() , "can not handle given x here"); // TODO: message

        final Matrix tmp = new Matrix(size_, size_);
        for (int i = 0; i < size_; ++i) {
            for (int j = 0; j <= i; ++j) {
                final Var_Helper helper = new Var_Helper(this, i, j);
                final GaussKronrodAdaptive integrator = new GaussKronrodAdaptive(1e-10, 10000);
                for(int k = 0; k<64; ++k) {
                    tmp.set(i, j, tmp.get(i, j)+integrator.op(helper, k*t/64.0,(k+1)*t/64.0));
                }
                tmp.set(j,i, tmp.get(i, j));
            }
        }

        return tmp;
    }

    public Matrix integratedCovariance(/* @Time */final double t) {
        return integratedCovariance(t, new Array(0)); //ZH QL097 using Null<Array>
    }

    private static class Var_Helper implements Ops.DoubleOp {

        private final int i_, j_;
        private final LfmCovarianceParameterization param_;

        public Var_Helper(final LfmCovarianceParameterization param, final int i, final int j) {
            this.i_ = i;
            this.j_ = j;
            this.param_ = param;
        }

        public double op(final double t) {
            final Matrix m = param_.diffusion(t);
            return m.constRangeRow(i_).innerProduct(m.constRangeRow(j_));
        }
    }

}
