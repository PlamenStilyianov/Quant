package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;

public class LeastSquareFunction extends CostFunction  {

    //! least square problem
    protected LeastSquareProblem lsp_;

    //! Default constructor
    LeastSquareFunction(final LeastSquareProblem lsp) {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.lsp_ = lsp;
    }

    @Override
    public double value(final Array x)  {
        // size of target and function to fit vectors
        final Array target = new Array(lsp_.size());
        final Array fct2fit = new Array(lsp_.size());
        // compute its values
        lsp_.targetAndValue(x, target, fct2fit);
        // do the difference
        final Array diff = target.sub(fct2fit);
        // and compute the scalar product (square of the norm)
        return diff.dotProduct(diff);
    }

    @Override
    public void gradient(final Array grad_f, final Array x) {
        // size of target and function to fit vectors
        final Array target = new Array(lsp_.size());
        final Array fct2fit = new Array(lsp_.size());
        // size of gradient matrix
        final Matrix grad_fct2fit  = new Matrix(lsp_.size(), x.size());
        // compute its values
        lsp_.targetValueAndGradient(x, grad_fct2fit, target, fct2fit);
        // do the difference
        final Array diff = target.sub(fct2fit);
        // compute derivative
        // FIXME: transpost implementation ?!!!!!!!!!!!!!!
        // grad_f = -2.0*(transpose(grad_fct2fit)*diff);
    }

    @Override
    public double valueAndGradient(final Array grad_f, final Array x) {
        // size of target and function to fit vectors
        final Array target = new Array(lsp_.size());
        final Array fct2fit = new Array(lsp_.size());
        // size of gradient matrix
        final Matrix grad_fct2fit  = new Matrix(lsp_.size (), x.size());
        // compute its values
        lsp_.targetValueAndGradient(x, grad_fct2fit, target, fct2fit);
        // do the difference
        final Array diff = target.sub(fct2fit);
        // compute derivative
        // FIXME: transpost implementation ?!!!!!!!!!!!!!!
        // grad_f = -2.0*(transpose(grad_fct2fit)*diff);
        // and compute the scalar product (square of the norm)
        return diff.dotProduct(diff);
    }

    @Override
    public Array values(final Array x) {
        throw new UnsupportedOperationException("Work in progress");
    }

}
