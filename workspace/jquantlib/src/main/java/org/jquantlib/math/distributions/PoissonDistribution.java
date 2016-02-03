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

package org.jquantlib.math.distributions;

import org.jquantlib.QL;
import org.jquantlib.math.Factorial;
import org.jquantlib.math.Ops;

/**
 * Normal distribution function
 * <p>
 * Given an integer {@latex$ k}, it returns its probability in a Poisson distribution.
 *
 * @author Dominik Holenstein
 */
// TEST the correctness of the returned value is tested by checking it against known good results.
// TODO PoissonDistribution: check logMu_ -> is never used
public class PoissonDistribution implements Ops.IntToDouble {

    //
    // private final fields
    //

    private final double mu;


    //
    // public constructors
    //

    /**
     * PoissonDistribution constructor
     * <p>
     * Initialize the mean value {@latex$ \mu}
     *
     * @param the mean value {@latex$ \mu}
     */
    public PoissonDistribution(final double mu) {
        QL.require(mu >= 0.0 , "mu must be non negative"); // TODO: message
        this.mu = mu;
    }

    //
    // implements UnaryFunctionInteger
    //

    /**
     * {@inheritDoc}
     * <p>
     * PoissonDistribution evaluation
     * <p>
     * Compute the Poisson Distribution with input {@latex$ \mu} and {@latex$ k}.
     *
     * @param k
     * @return Math.exp(k*Math.log(mu_) - logFactorial - mu_)
     */
    @Override
    public double op(final int k)/* @Read-only */ {
        if (mu==0.0)
            if (k==0) return 1.0;
            else      return 0.0;
        final Factorial fact = new Factorial();
        final double logFactorial = fact.ln(k);
        return Math.exp(k*Math.log(mu) - logFactorial - mu);
    }

}
