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

/*
 Copyright (C) 2003 Roman Gitlin
 Copyright (C) 2003 StatPro Italia srl

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

package org.jquantlib.math.integrals;

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.math.Ops.DoubleOp;

/**
 * Integral of a one-dimensional function
 *
 * <p>
 * Given a target accuracy {@latex$ \epsilon }, the integral of a function {@latex$ f } between {@latex$ a } and {@latex$ b }
 * is calculated by means of the trapezoid formula
 *
 * {@latex[
 *    \int_{a}^{b} f \mathrm{d}x = \frac{1}{2} f(x_{0}) + f(x_{1}) + f(x_{2}) + \dots + f(x_{N-1}) + \frac{1}{2} f(x_{N})
 * }
 *
 * where {@latex$ x_0 = a }, {@latex$ x_N = b }, and {@latex$ x_i = a+i \Delta x } with {@latex$ \Delta x = (b-a)/N }.
 * The number {@latex$ N } of intervals is repeatedly increased until the target accuracy is reached. \test the correctness of the
 * result is tested by checking it against known good values.
 *
 * @author Dominik Holenstein
 * @author Richard Gomes
 */
public class TrapezoidIntegral<T extends TrapezoidIntegral.IntegrationPolicy> extends Integrator {

    //
    // final protected fields
    //

    final protected IntegrationPolicy policy;


    //
    // public constructors
    //

    public TrapezoidIntegral(
            final Class<? extends TrapezoidIntegral.IntegrationPolicy> klass,
            final double accuracy,
            final int maxEvaluations) {
        super(accuracy, maxEvaluations);
        if (klass==Default.class) {
            this.policy = new Default();
        } else if (klass==MidPoint.class) {
            this.policy = new MidPoint();
        } else {
            throw new LibraryException(ReflectConstants.WRONG_ARGUMENT_TYPE);
        }
    }


    //
    // overrides Integrator
    //

    @Override
    protected double integrate(final DoubleOp f, final double a, final double b) /* @ReadOnly */ {
        // start from the coarsest trapezoid...
        int N = 1;
        double I = (f.op(a) + f.op(b)) * (b-a) / 2.0;

        // ...and refine it
        int i = 1;
        do {
            final double newI = policy.integrate(f, a, b, I, N);
            N *= policy.nbEvalutions();
            // good enough? Also, don't run away immediately
            if (Math.abs(I-newI) <= super.absoluteAccuracy() && i > 5) {
                // ok, exit
                return newI;
            }
            // oh well. Another step.
            I = newI;
            i++;
        } while (i < super.maxEvaluations());
        throw new ArithmeticException("max number of iterations reached"); // TODO: message
    }



    //
    // protected inner interfaces
    //

    protected interface IntegrationPolicy {
        public double integrate(final DoubleOp f, final double a, final double b, final double I, final int N) /* @ReadOnly */;
        public int nbEvalutions() /* @ReadOnly */;
    }


    //
    // protected static inner classes
    //

    public static class Default implements IntegrationPolicy {

        //
        // implements IntegrationPolicy
        //

        @Override
        public double integrate(final DoubleOp f, final double a, final double b, final double I, final int N) {
            double sum = 0.0;
            final double dx = (b-a)/N;
            double x = a + dx/2.0;
            for (int i=0; i<N; x += dx, ++i) {
                sum += f.op(x);
            }
            return (I + dx*sum)/2.0;
        }

        @Override
        public int nbEvalutions() {
            return 2;
        }

    }


    public static class MidPoint implements IntegrationPolicy {

        //
        // implements IntegrationPolicy
        //

        @Override
        public double integrate(final DoubleOp f, final double a, final double b, final double I, final int N) {
            double sum = 0.0;
            final double dx = (b-a)/N;
            double x = a + dx/6.0;
            final double D = 2.0*dx/3.0;
            for (int i=0; i<N; x += dx, ++i) {
                sum += f.op(x) + f.op(x+D);
            }
            return (I + dx*sum)/3.0;
        }

        @Override
        public int nbEvalutions() {
            return 3;
        }

    }

}