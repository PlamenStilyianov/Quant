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
import org.jquantlib.math.Constants;

/**
 * Provides the probability density function (pdf) of the (unit) normal distribution
 *
 * {@latex[
 * 	\frac{1}{\sigma \sqrt{2\pi} } \exp \left(-\frac{(x-\mu)^2}{2\sigma ^2} \right)
 * }
 *
 * @see <a href="http://en.wikipedia.org/wiki/Probability_density_function">Normal Distribution</a>
 *
 * @author Richard Gomes
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class NormalDistribution implements Derivative {

	//
	// protected fields
	//

	protected double average;
	protected double sigma;

	//
	// private fields
	//

	private final double normalizationFactor; // FIXME: code review
	private final double denominator; // FIXME: code review
	private final double denormalizationFactor;


	//
	// public constructors
	//

	/**
	 * Default constructor which assumes {@latex$ \mu \leftarrow 0.0} and {@latex \sigma \leftarrow 1.0 }.
	 */
	public NormalDistribution() {
		this(0.0, 1.0);
	}

    /**
     * Default constructor which assumes {@latex \sigma \leftarrow 1.0 }.
     * @param average
     */
    public NormalDistribution(final double average) {
        this(average, 1.0);
    }

	/**
	 * Constructor which initializes {@latex$ \mu } and {@latex \sigma }.
	 * @param average
	 * @param sigma
	 */
	public NormalDistribution(final double average, final double sigma) {
        QL.require(sigma > 0.0 , "sigma must be greater than 0.0"); // TODO: message

		this.average = average;
		this.sigma = sigma;

	    this.normalizationFactor = Constants.M_SQRT_2*Constants.M_1_SQRTPI/sigma;
	    this.denormalizationFactor = sigma*sigma;
	    this.denominator = 2.0*denormalizationFactor;
	}


    //
    // implements Ops.DoubleOp
    //

	/**
     * {@inheritDoc}
     * <p>
	 * Computes the Normal distribution at point {@latex$ x }
	 *
	 * @param x
	 * @return the Normal distribution at point {@latex$ x }
	 */
	@Override
	public double op(final double x) /* @ReadOnly */ {
		final double exponent = -0.5*x*x;
		if (exponent <= -690.0) return 0.0;
		return Constants.M_1_SQRT2PI*Math.exp(exponent);
	}

    //
    // implements Derivative
    //

	/**
     * {@inheritDoc}
     * <p>
	 * Calculates the first derivative of a Normal distribution at point {@latex$ x }
	 *
	 * @param x
	 * @return the first derivative of a Normal distribution at point {@latex$ x }
	 */
	@Override
	public double derivative(final double x) /* @ReadOnly */ {
	    return (op(x) * (average - x)) / denormalizationFactor;
	}

}
