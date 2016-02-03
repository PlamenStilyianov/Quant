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
package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.EndCriteria.Type;

/**
 * Levenberg-Marquardt optimization method
 * <p>
 * This implementation is based on MINPACK
 * @see http://www.netlib.org/minpack
 * @see http://www.netlib.org/cephes/linalg.tgz
 *
 * @author Ueli Hofstetter
 */
public class LevenbergMarquardt extends OptimizationMethod {

    private final double epsfcn_, xtol_, gtol_;
    private Integer info_;

    public LevenbergMarquardt() {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.epsfcn_ = 1.0e-8;
        this.xtol_ = 1.0e-8;
        this.gtol_ = 1.0e-8;
    }

    public LevenbergMarquardt(final double epsfcn, final double xtol, final double gtol){
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.epsfcn_ = epsfcn;
        this.xtol_ = xtol;
        this.gtol_ = gtol;

    }

    @Override
    public Type minimize(final Problem P, final EndCriteria endCriteria) {
        final EndCriteria.Type ecType = EndCriteria.Type.None;
        P.reset();
        final Array x_ = P.currentValue();
        // TODO: this is probably incorrect, check the consequences
        ProblemData.getProblemData().setProblem(P);
        ProblemData.getProblemData().setInitCostValues(P.costFunction().values(x_));

        final int m = ProblemData.getProblemData().initCostValues_.size();
        final int n = x_.size();

        final Array  xx = new Array(0);
        //TODO: correct?
        xx.addAssign(x_);

        final Array fvec;
        final Array diag;

        final int mode = 1;
        final double factor = 1;
        final int nprint = 0;
        final int info = 0;
        final int nfev = 0;

        final Array fjac = new Array(0);//ZH Just removed compilation error, code not as per QL097
        final int ldfjac = m;

        //TODO: to be completed....
        /*
        boost::scoped_array<int> ipvt(new int[n]);
        boost::scoped_array<double> qtf(new double[n]);
        boost::scoped_array<double> wa1(new double[n]);
        boost::scoped_array<double> wa2(new double[n]);
        boost::scoped_array<double> wa3(new double[n]);
        boost::scoped_array<double> wa4(new double[m]);
        // call lmdif to minimize the sum of the squares of m functions
        // in n variables by the Levenberg-Marquardt algorithm.
        QuantLib::MINPACK::lmdif(m, n, xx.get(), fvec.get(),
                                 static_cast<double>(endCriteria.functionEpsilon()),
                                 static_cast<double>(xtol_),
                                 static_cast<double>(gtol_),
                                 static_cast<int>(endCriteria.maxIterations()),
                                 static_cast<double>(epsfcn_),
                                 diag.get(), mode, factor,
                                 nprint, &info, &nfev, fjac.get(),
                                 ldfjac, ipvt.get(), qtf.get(),
                                 wa1.get(), wa2.get(), wa3.get(), wa4.get());
        info_ = info;
        // check requirements & endCriteria evaluation
        QL_REQUIRE(info != 0, "MINPACK: improper input parameters");
        //QL_REQUIRE(info != 6, "MINPACK: ftol is too small. no further "
        //                               "reduction in the sum of squares "
        //                               "is possible.");
        if (info != 6) ecType = QuantLib::EndCriteria::StationaryFunctionValue;
        //QL_REQUIRE(info != 5, "MINPACK: number of calls to fcn has "
        //                               "reached or exceeded maxfev.");
        endCriteria.checkMaxIterations(nfev, ecType);
        QL_REQUIRE(info != 7, "MINPACK: xtol is too small. no further "
                                       "improvement in the approximate "
                                       "solution x is possible.");
        QL_REQUIRE(info != 8, "MINPACK: gtol is too small. fvec is "
                                       "orthogonal to the columns of the "
                                       "jacobian to machine precision.");
        // set problem
        std::copy(xx.get(), xx.get()+n, x_.begin());
        P.setCurrentValue(x_);

        return ecType;
        */
        return ecType;
    }

    public void fcn(final int x1, final int n, final double x2, final double fvec, final int x3) {
        /*
        void LevenbergMarquardt::fcn(int, int n, double* x, double* fvec, int*) {
        Array xt(n);
        std::copy(x, x+n, xt.begin());
        // constraint handling needs some improvement in the future:
        // starting point should not be close to a constraint violation
        if (ProblemData::instance().problem()->constraint().test(xt)) {
            const Array& tmp = ProblemData::instance().problem()->values(xt);
            std::copy(tmp.begin(), tmp.end(), fvec);
        } else {
            std::copy(ProblemData::instance().initCostValues().begin(),
                      ProblemData::instance().initCostValues().end(), fvec);
        }
        */
    }

    // TODO: this class is no longer used in newer releases, it seems there's a
    // better approach to do this... to be investigated...
    // class is needed to make the Levenberg-Marquardt
    // algorithm sessionId() safe (or multi threading safe).
    /*
    class ProblemData : public Singleton<ProblemData> {
      public:
        Problem* & problem() { return thisP_; }
        Array& initCostValues()    { return initCostValues_; }
      private:
        Problem* thisP_;
        Array initCostValues_;
    };
    */
    private static class ProblemData{

        static ProblemData p = null;
        public static ProblemData getProblemData(){
            if(p == null){
                p = new ProblemData();
            }
            return p;
        }

        public Problem problem(){
            return thisP_;
        }
        public Array initCostValues(){
            return initCostValues_;
        }
        public void setProblem(final Problem problem){
             this.thisP_ = problem;
        }
        public void setInitCostValues(final Array initCostValues){
            this.initCostValues_ = initCostValues;
        }


        private Problem thisP_;
        private Array initCostValues_;

    }
}
