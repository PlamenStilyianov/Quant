/*
 Copyright (C)
 2009 Ueli Hofstetter

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

import org.jquantlib.math.optimization.CostFunction;

// TODO: code review :: please verify against QL/C++ code
//TODO: license, class comments
class HypersphereCostFunction extends CostFunction {

    private final int size_;
    private final boolean lowerDiagonal_;
    private final Matrix targetMatrix_;
    private final Array targetVariance_;
    private final  Matrix currentRoot_;
    private Matrix tempMatrix_, currentMatrix_;

    public HypersphereCostFunction(final Matrix targetMatrix, final Array targetVariance, final boolean lowerDiagonal) {
        this.size_ = targetMatrix.rows;
        this.lowerDiagonal_ = lowerDiagonal;
        this.targetMatrix_ = targetMatrix;
        this.targetVariance_ = targetVariance;
        currentRoot_ = new Matrix(size_, size_);
        tempMatrix_ = new Matrix(size_, size_);
        currentMatrix_ = new Matrix(size_, size_);
    }

    @Override
    public Array values(final Array array) {
        throw new UnsupportedOperationException("values method not implemented");
    }

    @Override
    public double value(final Array x)  {
        int i,j,k;

        currentRoot_.fill(1.0);
        if (lowerDiagonal_) {
            for (i=0; i<size_; i++)
                for (k=0; k<size_; k++)
                    if (k>i)
                        currentRoot_.set(i,k,0);
                    else
                        for (j=0; j<=k; j++)
                            if (j == k && k!=i)
                                currentRoot_.set(i,k, currentMatrix_.get(i, k) * Math.cos(x.get(i*(i-1)/2+j)));
                            else if (j!=i)
                                currentRoot_.set(i,k, currentRoot_.get(i, k)*
                                        Math.sin(x.get(i*(i-1)/2+j)));
        } else
            for (i=0; i<size_; i++)
                for (k=0; k<size_; k++)
                    for (j=0; j<=k; j++)
                        if (j == k && k!=size_-1)
                            currentRoot_.set(i, k, currentRoot_.get(i,k)
                                    *Math.cos(x.get(j*size_+i)));
                        else if (j!=size_-1)
                            currentRoot_.set(i,k,currentRoot_.get(i, j)*Math.sin(x.get(j*size_+i)));
        double temp, error=0;
        tempMatrix_ = currentRoot_.transpose();
        currentMatrix_ = currentRoot_.mul(tempMatrix_);
        for (i=0;i<size_;i++)
            for (j=0;j<size_;j++) {
                temp = currentMatrix_.get(i, j)*targetVariance_.get(i)*targetVariance_.get(j)-targetMatrix_.get(i, j);
                error += temp*temp;
            }
        return error;
    }
}
