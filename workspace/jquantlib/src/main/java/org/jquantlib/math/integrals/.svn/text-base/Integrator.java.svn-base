/*
 Copyright (C) 2008 Richard Gomes

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

package org.jquantlib.math.integrals;

import org.jquantlib.QL;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;

/**
 * This is the abstract base class for all integrators
 *
 * @author Richard Gomes
 */
public abstract class Integrator {

    //
    // private fields
    //

    private double absoluteAccuracy;
    private int maxEvaluations;
    private double absoluteError;
    private int numberOfEvaluations;


    //
    // public constructors
    //

    public Integrator(final double absoluteAccuracy, final int maxEvaluations) {
        QL.require(absoluteAccuracy > Constants.QL_EPSILON , "required tolerance must be > epsilon"); // TODO: message

        this.absoluteAccuracy = absoluteAccuracy;
        this.maxEvaluations = maxEvaluations;
    }


    //
    // final public methods
    //

    final public double op(final Ops.DoubleOp f, final double a, final double b) /* @ReadOnly */{
        if (a == b) {
            return 0.0;
        }
        if (b > a) {
            return integrate(f, a, b);
        } else {
            return -integrate(f, b, a);
        }
    }


    //
    // public virtual methods
    //

    public boolean isIntegrationSuccess() /* @ReadOnly */ {
        return numberOfEvaluations <= maxEvaluations && absoluteError <= absoluteAccuracy;
    }

    public final double absoluteAccuracy() /* @ReadOnly */ {
        return absoluteAccuracy;
    }

    public final void setAbsoluteAccuracy(final double accuracy) {
        absoluteAccuracy = accuracy;
    }

    public final int maxEvaluations() /* @ReadOnly */ {
        return this.maxEvaluations;
    }

    public final void setMaxEvaluations(final int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
    }

    public final double absoluteError() /* @ReadOnly */ {
        return absoluteError;
    }


    //
    // final protected methods
    //

    final protected void setAbsoluteError(final double error) /* @ReadOnly */ {
        absoluteError = error;
    }

    final protected void setNumberOfEvaluations(final int evaluations) /* @ReadOnly */ {
        this.numberOfEvaluations = evaluations;
    }

    final protected void increaseNumberOfEvaluations(final int increase) /* @ReadOnly */ {
        this.numberOfEvaluations += increase;
    }


    //
    // protected virtual methods
    //

    protected int numberOfEvaluations() /* @ReadOnly */ {
        return this.numberOfEvaluations;
    }


    //
    // protected abstract methods
    //

    protected abstract double integrate(final Ops.DoubleOp f, final double a, final double b) /* @ReadOnly */;

}
