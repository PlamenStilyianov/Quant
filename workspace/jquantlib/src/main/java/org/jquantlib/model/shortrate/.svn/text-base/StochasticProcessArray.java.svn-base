/*
Copyright (C)
2008 Praneet Tiwari
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
package org.jquantlib.model.shortrate;

import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.math.matrixutilities.PseudoSqrt;
import org.jquantlib.math.matrixutilities.PseudoSqrt.SalvagingAlgorithm;
import org.jquantlib.processes.StochasticProcess;
import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.time.Date;

/**
 *
 * @author Praneet Tiwari
 */
// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class StochasticProcessArray extends StochasticProcess {

    private static final String no_process_given = "no process given";
    private static final String mismatch_processnumber_sizecorrelationmatrix =  "mismatch between number of processes and size of correlation matrix";

    protected List<StochasticProcess1D> processes_;
    protected Matrix sqrtCorrelation_;

    public StochasticProcessArray(final List<StochasticProcess1D> processes, final Matrix correlation) {

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        QL.require(!processes.isEmpty() , no_process_given); // TODO: message
        QL.require(correlation.rows() == processes.size() , mismatch_processnumber_sizecorrelationmatrix); // TODO: message

        this.processes_ = processes;
        this.sqrtCorrelation_ = PseudoSqrt.pseudoSqrt(correlation, SalvagingAlgorithm.Spectral);
        for (int i=0; i<processes_.size(); i++) {
            processes_.get(i).addObserver(this);
            //XXX:registerWith
            //registerWith(processes_.get(i));
        }
    }

    @Override
    public Array initialValues()  {
        final double[] tmp = new double[size()];
        for (int i=0; i<size(); ++i) {
            tmp[i] = processes_.get(i).x0();
        }
        return new Array( tmp );
    }

    @Override
    public int size() {
        return processes_.size();
    }

    @Override
    public Array drift(final /* @Time */double t, final Array x) {
        final double[] tmp = new double[size()];
        for (int i=0; i<size(); i++) {
            tmp[i] = processes_.get(i).drift(t, x.get(i));
        }
        return new Array( tmp );
    }

    @Override
    public Matrix diffusion(final /*Time*/ double t, final Array x)  {
        for (int i=0; i<size(); i++) {
            final double sigma = processes_.get(i).diffusion(t, x.get(i));
            sqrtCorrelation_.rangeRow(i).mulAssign(sigma);
        }
        return sqrtCorrelation_;
    }

    @Override
    public Array expectation(final /*@Time*/double t0, final Array x0, final /*@Time*/double dt)  {
        final double [] tmp = new double[size()];
        for (int i=0; i<size(); i++) {
            tmp[i] = processes_.get(i).expectation(t0, x0.get(i), dt);
        }
        return new Array(tmp);
    }

    @Override
    public Matrix stdDeviation(final /*@Time*/ double t0, final Array x0, final /*@Time*/ double dt)  {
        for (int i=0; i<size(); i++) {
            final double sigma = processes_.get(i).stdDeviation(t0, x0.get(i), dt);
            sqrtCorrelation_.rangeRow(i).mulAssign(sigma);
        }
        return sqrtCorrelation_;
    }

    @Override
    public Matrix covariance(final /*@Time*/ double t0, final Array x0, final /*@Time*/ double dt)  {
        final Matrix tmp = stdDeviation(t0, x0, dt);
        return tmp.mul(tmp.transpose());
    }

    @Override
    public Array evolve(final /*@Time*/ double t0, final Array x0, final /*@Time*/double dt, final Array dw)  {

        final Array dz = sqrtCorrelation_.mul(dw);
        final double[] tmp = new double[size()];
        for (int i=0; i<size(); i++) {
            tmp[i] = processes_.get(i).evolve(t0, x0.get(i), dt, dz.get(i));
        }

        return new Array(tmp);
    }

    @Override
    public Array apply(final Array x0, final Array dx)  {
        final double [] tmp = new double[size()];
        for (int i=0; i<size(); i++) {
            tmp[i] = processes_.get(i).apply(x0.get(i), dx.get(i));
        }
        return new Array(tmp);
    }

    @Override
    public /*@Time*/ double time(final Date d) {
        return processes_.get(0).time(d);
    }

    public StochasticProcess1D process(final int i) {
        return processes_.get(i);
    }

    public Matrix correlation() {
        return sqrtCorrelation_.mul(sqrtCorrelation_.transpose());
    }

}
