package org.jquantlib.legacy.libormarkets;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.math.matrixutilities.PseudoSqrt;
import org.jquantlib.math.matrixutilities.PseudoSqrt.SalvagingAlgorithm;
import org.jquantlib.math.optimization.BoundaryConstraint;
import org.jquantlib.math.optimization.PositiveConstraint;
import org.jquantlib.model.ConstantParameter;

//! %linear exponential correlation model
/*! This class describes a exponential correlation model

 \f[
 \rho_{i,j}=rho + (1-rho)*e^{(-\beta \|i-j\|)}
 \f]

 References:

 Damiano Brigo, Fabio Mercurio, Massimo Morini, 2003,
 Different Covariance Parameterizations of Libor Market Model and Joint
 Caps/Swaptions Calibration,
 (<http://www.business.uts.edu.au/qfrc/conferences/qmf2001/Brigo_D.pdf>)
 */

public class LmLinearExponentialCorrelationModel extends LmCorrelationModel {
    private Matrix corrMatrix_, pseudoSqrt_;
    private int factors_;

    public LmLinearExponentialCorrelationModel(final int size, final double rho, final double beta, int factors) {
        super(size, 2);

        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }

        corrMatrix_ = new Matrix(size, size);
        factors = factors != 0 ? 0 : size;
        arguments_.set(0, new ConstantParameter(rho, new BoundaryConstraint(-1.0, 1.0)));
        arguments_.set(1, new ConstantParameter(beta, new PositiveConstraint()));
        generateArguments();
    }

    public LmLinearExponentialCorrelationModel(final int size, final double rho, final double beta) {
        this(size, rho, beta, 0);
    }

    @Override
    public Matrix correlation(final /* @ Time */double time, final Array x) {
        // TODO: code review :: use of clone()
        return corrMatrix_;
    }

    @Override
    public double correlation(final int i, final int j, /* @ Time */final double time, final Array x) {
        return corrMatrix_.get(i, j);
    }

    @Override
    public boolean isTimeIndependent() {
        return true;
    }

    @Override
    public int factors() {
        return factors_;
    }

    @Override
    public Matrix pseudoSqrt(final /* @ Time */double time, final Array x) {
        // TODO: code review :: use of clone()
        return pseudoSqrt_;
    }

    @Override
    public void generateArguments() {
        final double rho = arguments_.get(0).get(0.0);
        final double beta = arguments_.get(1).get(0.0);

        for (int i = 0; i < size_; ++i) {
            for (int j = i; j < size_; ++j) {
                final double value = rho + (1 - rho) * Math.exp(-beta * Math.abs(i - j));
                corrMatrix_.set(i, j, value);
                corrMatrix_.set(j, i, value);
            }
        }

        pseudoSqrt_ = PseudoSqrt.rankReducedSqrt(corrMatrix_, factors_, 1, SalvagingAlgorithm.None);
        corrMatrix_ = pseudoSqrt_.mul(pseudoSqrt_).mul(pseudoSqrt_.transpose());
    }
}
