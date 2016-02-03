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

/*
 Copyright (C) 2007 Ferdinando Ametrano
 Copyright (C) 2007 Fran�ois du Vignaud
 Copyright (C) 2001, 2002, 2003 Nicolas Di C�sar�

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 */

package org.jquantlib.math.optimization;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Constants;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Constrained optimization problem
 *
 * @author Ueli Hofstetter
 */
@QualityAssurance(quality=Quality.Q3_DOCUMENTATION, version=Version.V097, reviewers="Richard Gomes")
public class Problem {

    //
    // protected fields
    //

    /**
     * Unconstrained cost function.
     */
    protected CostFunction  costFunction_;

    /**
     * Constraint
     */
    protected Constraint  constraint_;

    /**
     * current value of the local minimum
     */
    protected Array currentValue_;

    /**
     * function and gradient norm values at the curentValue_ (i.e. the last step)
     */
    protected double functionValue_;

    protected double squaredNorm_;

    /**
     * number of evaluation of cost function and its gradient
     */
    protected int functionEvaluation_;

    protected int gradientEvaluation_;


    //
    // public constructors
    //

    public Problem(final CostFunction  costFunction, final Constraint  constraint) {
        this(costFunction, constraint, new Array(0));//ZH: Verified QL097
    }

    public Problem(final CostFunction  costFunction, final Constraint  constraint, final Array  initialValue) {
        this.costFunction_ = costFunction;
        this.constraint_ = constraint;
        this.currentValue_ = initialValue.clone();
    }


    //
    // public methods
    //

    /**
     * @warning it does not reset the current minimum to any initial value
     */
    public void reset() {
        this.functionEvaluation_ = 0;
        this.gradientEvaluation_ = 0;
        functionValue_ = Constants.NULL_REAL;
        squaredNorm_ = Constants.NULL_REAL;
    }

    /**
     * Call cost function computation and increment evaluation counter
     */
    public double value(final Array  x) {
        functionEvaluation_++;
        return costFunction_.value(x);
    }

    /**
     * Call cost values computation and increment evaluation counter
     */
    public Array values(final Array  x) {
        functionEvaluation_++;
        return costFunction_.values(x);
    }

    /**
     * Call cost function gradient computation and increment evaluation counter
     */
    public void gradient(final Array  grad_f, final Array  x) {
        gradientEvaluation_++;
        costFunction_.gradient(grad_f, x);
    }

    /**
     * Call cost function computation and it gradient
     */
    public double valueAndGradient(final Array  grad_f, final Array  x) {
        functionEvaluation_++;
        gradientEvaluation_++;
        return costFunction_.valueAndGradient(grad_f, x);
    }

    /**
     * Constraint
     */
    public Constraint  constraint() /* @ReadOnly */ {
        return this.constraint_;
    }

    /**
     * Cost function
     */
    public CostFunction  costFunction() /* @ReadOnly */ {
        return this.costFunction_;
    }

    public void setCurrentValue(final Array  currentValue) {
        this.currentValue_ = currentValue;
    }

    /**
     * current value of the local minimum
     */
    public final Array  currentValue() /* @ReadOnly */ {
        return this.currentValue_;
    }

    public void setFunctionValue(final double functionValue) {
        this.functionValue_ = functionValue;
    }

    /**
     * value of cost function
     */
    public double functionValue() /* @ReadOnly */ {
        return this.functionValue_;
    }

    public void setGradientNormValue(final double squaredNorm) {
        this.squaredNorm_ = squaredNorm;
    }

    /**
     * value of cost function gradient norm
     */
    public double gradientNormValue() /* @ReadOnly */ {
        return this.squaredNorm_;
    }

    /**
     * number of evaluation of cost function
     */
    public int functionEvaluation() /* @ReadOnly */ {
        return this.functionEvaluation_;
    }

    /**
     * number of evaluation of cost function gradient
     */
    public int gradientEvaluation() /* @ReadOnly */ {
        return this.gradientEvaluation_;
    }

}
