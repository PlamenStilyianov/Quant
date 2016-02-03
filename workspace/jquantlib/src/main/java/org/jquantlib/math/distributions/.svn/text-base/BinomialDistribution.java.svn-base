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
 * Binomial Distribution
 * <p>
 * In probability theory and statistics, the binomial distribution is the discrete probability distribution of the number of
 * successes in a sequence of n independent yes/no experiments, each of which yields success with probability p. Such a
 * success/failure experiment is also called a Bernoulli experiment or Bernoulli trial. In fact, when n = 1, the binomial
 * distribution is a Bernoulli distribution.
 * <p>
 * The binomial distribution is the basis for the popular binomial test of statistical
 * significance. A binomial distribution should not be confused with a bimodal distribution.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Binomial_distribution">Binomial Distribution</a>
 *
 * @author Richard Gomes
 * @author Dominik Holenstein
 */
public class BinomialDistribution implements Ops.IntToDouble {

    private static final String INVALID_PROBABILITY = "probability must be 0.0 <= p <= 1.0";

    //
    // private static final methods
    //

    private static final Factorial factorial = new Factorial();

    //
    // private final fields
    //

    private final int nExp;


    //
    // private fields
    //

    private  double logP; //TODO: code review
    private  double logOneMinusP; //TODO: code review

    /**
     * Constructor of the Binomial Distribution taking two arguments for
     * initialization.
     *
     * @param p
     *            Probability of success of each trial
     * @param n
     *            Sequence of independent yes/no experiments
     */
    //TODO: code review
    public BinomialDistribution(final double p, final int n) {
        QL.require(p >= 0 && p <= 1.0 , INVALID_PROBABILITY); // QA:[RG]::verified
        this.nExp = n;
        if (p == 0.0) {
            this.logOneMinusP = 0.0;
        } else if (p == 1.0) {
            this.logP = 0.0;
        } else {
            this.logP = Math.log(p);
            this.logOneMinusP = Math.log(1.0 - p);
        }
    }

    //
    // implements UnaryFunctionInteger
    //

    /**
     * Computes the probability of <code>k</code> successful trials.
     *
     * @param k
     *            Number of successful trials
     * @return Math.exp(binomialCoefficientLn(nExp, k) + k * logP + (nExp-k) * logOneMinusP);
     */
    @Override
    public double op(final int k) {

        if (k > nExp) {
            return 0.0;
        }

        // p == 1.0
        if (logP == 0.0) {
            return (k == nExp ? 1.0 : 0.0);
        }

        // p==0.0
        if (logOneMinusP == 0.0) {
            return (k == 0 ? 1.0 : 0.0);
        }

        return Math.exp(binomialCoefficientLn(nExp, k) + k * logP + (nExp - k) * logOneMinusP);
    }

    /**
     * Computes the natural logarithm of the binomial coefficient.
     *
     * @param n
     *            Number of total trials
     * @param k
     *            Number of successful trials
     * @return Natural logarithm of the binomial coefficient
     */
    private double binomialCoefficientLn(final int n, final int k) {
        QL.require(n >= 0 , "n < 0 not allowed"); // TODO: message
        QL.require(k >= 0 , "k < 0 not allowed"); // TODO: message
        QL.require(n >= k , "n < k not allowed"); // TODO: message
        return factorial.ln(n) - factorial.ln(k) - factorial.ln(n - k);
    }

    /**
     * Computes the binomial coefficient.
     *
     * @param n
     *            Number of total trials
     * @param k
     *            Number of successful trials
     * @return Math.floor(0.5 + Math.exp(binomialCoefficientLn(n, k)))
     */
    //XXX
    private double binomialCoefficient(final int n, final int k) {
    	return Math.floor(0.5 + Math.exp(binomialCoefficientLn(n, k)));
    }


    /**
     * Given an odd integer n and a real number z it returns p such that:
     * <pre>
     * 1 - CumulativeBinomialDistribution((n-1)/2, n, p) = CumulativeNormalDistribution(z)
     * </pre>
     * Pre-condition: n must be odd
     */
    static public /*@Real*/ double PeizerPrattMethod2Inversion(final double z, final long n) {

        QL.require(n%2==1,"n must be an odd number"); // TODO: message

        /*@Real*/ double result = (z/(n+1.0/3.0+0.1/(n+1.0)));
        result *= result;
        result = Math.exp(-result*(n+1.0/6.0));
        result = 0.5 + (z>0 ? 1 : -1) * Math.sqrt((0.25 * (1.0-result)));
        return result;
    }

}
