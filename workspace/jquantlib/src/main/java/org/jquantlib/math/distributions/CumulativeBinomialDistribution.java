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

/**
 * @author Richard Gomes
 * @author Dominik Holenstein
 */

package org.jquantlib.math.distributions;


import org.jquantlib.QL;
import org.jquantlib.math.Beta;
import org.jquantlib.math.Ops;

/**
 * Cumulative binomial distribution function.
 * <p>
 * Given an integer k it provides the cumulative probability of observing kk<=k.
 *
 * @author Richard Gomes
 *
 */
// TODO: review comments and formulas
public class CumulativeBinomialDistribution implements Ops.IntToDouble {

    private static final String INVALID_PROBABILITY = "probability must be 0.0 <= p <= 1.0";

    //
    // static private final fields
    //

    private static final double accuracy = 1e-16;
    private static final int maxIteration = 100;

    //
    // private final fields
    //

    private final int n;
    private final double p;

    //
    // public constructors
    //

    /**
     * This constructor initializes p and n
     * @param p is the probability of success of a single trial
     * @param n is the total number of trials
     */
    public CumulativeBinomialDistribution(final double p, final int n){
        QL.require(p >= 0.0 && p <= 1.0 , INVALID_PROBABILITY); // TODO: message
        this.n = n; // total number of trials
        this.p = p; // probability of success on a single trial
    }

    //
    // implements UnaryFunctionInteger
    //

    /**
     * {@inheritDoc}
     * <p>
     * Computes the Cumulative Binomial Distribution.
     *
     * @param k
     * @return 1.0 - Beta.incompleteBetaFunction(k+1, n_-k, p_, accuracy, maxIteration)
     */
    @Override
    public double op(final int k) {
        if (k >= n)
            return 1.0;
        else
            return 1.0 - Beta.incompleteBetaFunction(k + 1, n - k, p, accuracy, maxIteration);
    }

}
