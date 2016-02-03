package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;

public class NonLinearLeastSquare {
    
  //! Non-linear least-square method.
    /*! Using a given optimization algorithm (default is conjugate
        gradient),

        \f[ min \{ r(x) : x in R^n \} \f]

        where \f$ r(x) = |f(x)|^2 \f$ is the Euclidean norm of \f$
        f(x) \f$ for some vector-valued function \f$ f \f$ from
        \f$ R^n \f$ to \f$ R^m \f$,
        \f[ f = (f_1, ..., f_m) \f]
        with \f$ f_i(x) = b_i - \phi(x,t_i) \f$ where \f$ b \f$ is the
        vector of target data and \f$ phi \f$ is a scalar function.

        Assuming the differentiability of \f$ f \f$, the gradient of
        \f$ r \f$ is defined by
        \f[ grad r(x) = f'(x)^t.f(x) \f]
    */
        //! Default constructor
    
        //! solution vector
        private Array results_, initialValue_;
        //! least square residual norm
        private double resnorm_;
        //! Exit flag of the optimization process
        private int exitFlag_;
        //! required accuracy of the solver
        private double accuracy_, bestAccuracy_;
        //! maximum and real number of iterations
        private int maxIterations_, nbIterations_;
        //! Optimization method
        private OptimizationMethod om_;
        //constraint
        private Constraint c_;
        
        
        public NonLinearLeastSquare(Constraint c,
                             double accuracy,
                             int maxiter ){
            this.exitFlag_ = -1;
            this.accuracy_ = accuracy;
            this.maxIterations_ = maxiter;
            this.om_ = new ConjugateGradient(null);
            this.c_ = c;
            if (System.getProperty("EXPERIMENTAL") == null) {
                throw new UnsupportedOperationException("Work in progress");
            }
        }
        public NonLinearLeastSquare(Constraint c){
            this(c, 1e-4, 100);

        }   
        
        //! Default constructor
        public NonLinearLeastSquare(Constraint c,
                             double accuracy,
                             int maxiter,
                             OptimizationMethod om){
            this.exitFlag_ = -1;
            this.accuracy_ = accuracy;
            this.maxIterations_ = maxiter;
            this.om_ = om;
            this.c_ = c;
            if (System.getProperty("EXPERIMENTAL") == null) {
                throw new UnsupportedOperationException("Work in progress");
            }
        }

        //! Solve least square problem using numerix solver
        public Array perform(LeastSquareProblem lsProblem){
            double eps = accuracy_;

            // wrap the least square problem in an optimization function
            LeastSquareFunction lsf = new LeastSquareFunction(lsProblem);

            // define optimization problem
            Problem P = new Problem(lsf, c_, initialValue_);

            // minimize
            EndCriteria ec = new EndCriteria(maxIterations_, 
                Math.min(maxIterations_/2, 100), 
                eps, eps, eps);
            exitFlag_ = om_.minimize(P, ec).ordinal();

            // summarize results of minimization
            //        nbIterations_ = om_->iterationNumber();

            results_ = P.currentValue();
            resnorm_ = P.functionValue();
            bestAccuracy_ = P.functionValue();

            return results_;
        }

        public void setInitialValue(final Array initialValue) {
            initialValue_ = initialValue;
        }

        //! return the results
        Array results() { return results_; }

        //! return the least square residual norm
        double residualNorm() { return resnorm_; }

        //! return last function value
        double lastValue() { return bestAccuracy_; }

        //! return exit flag
        int exitFlag() { return exitFlag_; }

        //! return the performed number of iterations
        Integer iterationsNumber() { return nbIterations_; }


}
