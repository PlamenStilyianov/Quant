package org.jquantlib.legacy.libormarkets;

import org.jquantlib.QL;
import org.jquantlib.math.Ops;
import org.jquantlib.math.integrals.GaussKronrodAdaptive;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.processes.LfmCovarianceParameterization;

public class LfmCovarianceProxy extends LfmCovarianceParameterization {

    protected LmVolatilityModel  volaModel_;
    protected LmCorrelationModel corrModel_;


    public LfmCovarianceProxy(final LmVolatilityModel volaModel, final LmCorrelationModel corrModel){
        super(corrModel.size(), corrModel.factors());

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        this.volaModel_ = volaModel;
        this.corrModel_ = corrModel;
    }

    public LmVolatilityModel volatilityModel(){
        return volaModel_;
    }

    public LmCorrelationModel correlationModel(){
        return corrModel_;
    }

    @Override
    // TODO: review iterators
    public Matrix diffusion(/*@Time*/ final double t, final Array x){
        final Matrix pca = corrModel_.pseudoSqrt(t, x);
        // TODO: code review :: use of clone()
        final Array  vol = volaModel_.volatility(t, x);
        for (int i=0; i<size_; ++i) {
            pca.rangeRow(i).mulAssign(vol.get(i));
        }
        return pca;
    }

    @Override
    public Matrix covariance(/* @Time */final double t, final Array x) {
        // TODO: code review :: use of clone()
        final Array volatility = volaModel_.volatility(t, x);
        final Matrix correlation = corrModel_.correlation(t, x);

        final Matrix tmp = new Matrix(size_, size_);
        for (int i = 0; i < size_; ++i) {
            for (int j = 0; j < size_; ++j) {
                tmp.set(i, j, volatility.get(i) * correlation.get(i, j) * volatility.get(j));
            }
        }

        return tmp;
    }

    static class Var_Helper implements Ops.DoubleOp {

        private final  int i_, j_;
        private final LmVolatilityModel/* * */   volaModel_;
        private final LmCorrelationModel/* * */  corrModel_;

      public Var_Helper(final LfmCovarianceProxy proxy,
                                                 final int i, final int j){
      this.i_ = i;
        this.j_ = j;
        this.volaModel_ = proxy.volaModel_;
        this.corrModel_ = proxy.corrModel_;
      }

      public double op(final double t)  {
          /*@Volatility*/ double v1, v2;

          if (i_ == j_) {
            v1 = v2 = volaModel_.volatility(i_, t);
        } else {
              v1 = volaModel_.volatility(i_, t);
              v2 = volaModel_.volatility(j_, t);
          }

          return  v1 * corrModel_.correlation(i_, j_, t) * v2;
      }
    }

     public  double integratedCovariance(final int i, final int j, /*@Time*/final double t, final Array x)  {

          if (corrModel_.isTimeIndependent()) {
            try {
                  // if all objects support these methods
                  // thats by far the fastest way to get the
                  // integrated covariance
                  return corrModel_.correlation(i, j, 0.0, x) * volaModel_.integratedVariance(j, i, t, x);
              }
              catch (final Exception ex) {
                  // okay proceed with the
                  // slow numerical integration routine
              }
        }

          QL.require(!x.empty() , "can not handle given x here"); // TODO: message

          double tmp=0.0;
          final Var_Helper helper = new Var_Helper(this, i, j);

          final GaussKronrodAdaptive integrator = new GaussKronrodAdaptive(1e-10, 10000);
          for (int k=0; k<64; ++k) {
            tmp+=integrator.op(helper, k*t/64., (k+1)*t/64.);
        }
          return tmp;
      }

}
