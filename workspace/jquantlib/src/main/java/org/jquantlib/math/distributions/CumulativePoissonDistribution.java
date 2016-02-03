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

import org.jquantlib.math.Ops;


/**
 * Cumulative Poisson distribution function
 * <p>
 * This function provides an approximation of the integral of the Poisson
 * distribution.
 * <p>
 * In probability theory and statistics, the Poisson distribution is a discrete
 * probability distribution that expresses the probability of a number of events
 * occurring in a fixed period of time if these events occur with a known
 * average rate and independently of the time since the last event. The Poisson
 * distribution can also be used for the number of events in other specified
 * intervals such as distance, area or volume.
 *
 * @see Wikipedia: <a href="http://en.wikipedia.org/wiki/Poisson_distribution">Poisson Distribution</a>
 * @see Book: <i>"Numerical Recipes in C", 2nd edition, Teukolsky, Vetterling, Flannery, chapter 6.</i>
 *
 * @author Dominik Holenstein
 */
//TODO Test the correctness of the returned value against known good results.
//TODO CumulativePoissonDistribution: Write a test case.
public class CumulativePoissonDistribution implements Ops.IntToDouble {

    //
    // private static fields
    //

    private static final double accuracy = 1.0e-15;
    private static final int maxIteration = 100;

    //
    // static fields
    //

    private final double mu;

    //
    // public constructors
    //

    public CumulativePoissonDistribution(final double mu) {
        this.mu = mu;
    }


    //
    // implements UnaryFunctionInteger
    //

    /**
     * {@inheritDoc}
     * <p>
     * Computes the cumulative Poisson distribution by using the incomplete gamma function
     * .
     * @param k is the number of occurrences of an event
     * @return the cumulative Poisson distribution by using the incomplete gamma function
     */
    @Override
    public double op(final int k) /* @Read-only */ {
        final IncompleteGamma incompleteGamma = new IncompleteGamma();
        return 1.0 - incompleteGamma.incompleteGammaFunction((double)k +1, mu, accuracy, maxIteration);
    }

}
