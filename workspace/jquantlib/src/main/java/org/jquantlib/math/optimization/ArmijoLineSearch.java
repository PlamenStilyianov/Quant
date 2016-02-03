/*
 Copyright (C) 2009 Ueli Hofstetter, Philippe Hungerbuehler

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

    //! Armijo line search.
    /*! Let \f$ \alpha \f$ and \f$ \beta \f$ be 2 scalars in \f$ [0,1]
        \f$.  Let \f$ x \f$ be the current value of the unknown, \f$ d
        \f$ the search direction and \f$ t \f$ the step. Let \f$ f \f$
        be the function to minimize.  The line search stops when \f$ t
        \f$ verifies
        \f[ f(x + t \cdot d) - f(x) \leq -\alpha t f'(x+t \cdot d) \f]
        and
        \f[ f(x+\frac{t}{\beta} \cdot d) - f(x) > -\frac{\alpha}{\beta}
            t f'(x+t \cdot d) \f]

        (see Polak, Algorithms and consistent approximations, Optimization,
        volume 124 of Applied Mathematical Sciences, Springer-Verlag, NY,
        1997)
    */
package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.EndCriteria.Type;


public class ArmijoLineSearch extends LineSearch {

    public ArmijoLineSearch(){
        this(1e-8, 0.05, 0.65);
    }

    public ArmijoLineSearch(final double eps, final double alpha, final double beta){
        super(eps);
        alpha_ = alpha;
        beta_ = beta;
    }

    @Override
    public double evaluate(final Problem P, final Type ecType, final EndCriteria endCriteria, final double t_ini) {
        final Constraint constraint = P.constraint();
        succeed_ = true;
        boolean maxIter = false;
        //FIXME: check this initialization
        double qtold = t_ini;
        double t = t_ini;
        int loopNumber = 0;

        final double q0 = P.functionValue();
        final double qpO = P.gradientNormValue();

        qt_ = q0;
        qpt_ = (gradient_.empty()? qpO : - gradient_.dotProduct(searchDirection_));

        // Initialize gradient
        gradient_ = new Array(P.currentValue().size());
        // Compute new point
        xtd_ = P.currentValue();
        t = update(xtd_, searchDirection_, t, constraint);
        // Compute fucntion value at the new point
        qt_ = P.value(xtd_);

        //Enter in the loop if the criterion is not satisfied
        if((qt_ - q0) > -alpha_*t*qpt_){
            do{
                loopNumber++;
                // Decrease the step
                t *= beta_;
                // Store old value of the function
                qtold = qt_;
                // New point value
                xtd_ = P.currentValue();
                t = update(xtd_, searchDirection_, t, constraint);

                // Compute function value at the new point
                qt_ = P.value(xtd_);
                P.gradient(gradient_, xtd_);
                // and it squared norm
                maxIter = endCriteria.checkMaxIterations(loopNumber, ecType);
            }
            while((((qt_ - q0) > (-alpha_ * t * qpt_)) ||
                   ((qtold - q0) <= (-alpha_ * t * qpt_ / beta_))) &&
                   (!maxIter));
        }

        if(maxIter){
            succeed_ = false;
        }

        // Compute new Gradient
        P.gradient(gradient_, xtd_);
        // and it squared norm
        qpt_ = gradient_.dotProduct(gradient_);

        // Return new step value
        return t;
    }

    private final double alpha_;
    private final double beta_;
}
