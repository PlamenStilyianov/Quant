/*
 Copyright (C) 2009 Ueli Hofstetter
 Copyright (C) 2010 Richard Gomes

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
 Copyright (C) 2003 RiskMap srl

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

package org.jquantlib.math.statistics;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Ops;
import org.jquantlib.math.functions.Bind1st;
import org.jquantlib.math.functions.Bind1stPredicate;
import org.jquantlib.math.functions.Bind2nd;
import org.jquantlib.math.functions.Bind2ndPredicate;
import org.jquantlib.math.functions.Clipped;
import org.jquantlib.math.functions.Constant;
import org.jquantlib.math.functions.Expression;
import org.jquantlib.math.functions.Identity;
import org.jquantlib.math.functions.LessThanPredicate;
import org.jquantlib.math.functions.Minus;
import org.jquantlib.math.functions.Square;
import org.jquantlib.math.functions.TruePredicate;
import org.jquantlib.util.Pair;

/**
 * empirical-distribution risk measures
 * <p>
 * This class wraps a somewhat generic statistic tool and adds
 * a number of risk measures (e.g.: value-at-risk, expected
 * shortfall, etc.) based on the data distribution as reported by
 * the underlying statistic tool.
 * <p>
 * @todo add historical annualized volatility
 * 
 * @author Ueli Hofstetter
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q4_UNIT, reviewers = { "Richard Gomes" }, version = Version.V097)
public class GenericRiskStatistics extends GaussianStatistics {

	private static final String NO_DATA_BELOW_THE_TARGET = "no data below the target";
	private static final String EMPTY_SAMPLE_SET = "empty sample set";
	private static final String UNSUFFICIENT_SAMPLES_UNDER_TARGET = "samples under target <=1, unsufficient";


	public GenericRiskStatistics() {
		super();
	}


	//
	// public methods
	//

	/**
	 * returns the variance of observations below the mean,
	 * {@latex[ \frac{N}{N-1}
	 *	 \mathrm{E}\left[ (x-\langle x \rangle)^2 \;|\;
	 *					   x < \langle x \rangle \right]. }
	 *
	 * @see Markowitz (1959).
	 */
	public /*@Real*/ double semiVariance() /*@ReadOnly*/ {
		return regret(mean());
	}

	/**
	 * returns the semi deviation, defined as the
	 * square root of the semi variance.
	 */
	public /*@Real*/ double semiDeviation() /*@ReadOnly*/ {
		return Math.sqrt(semiVariance());
	}

	/**
	 * returns the variance of observations below 0.0,
	 * {@latex[ \frac{N}{N-1}
	 *	 \mathrm{E}\left[ x^2 \;|\; x < 0\right]. }
	 */
	public /*@Real*/ double downsideVariance() /*@ReadOnly*/ {
		return regret(0.0);
	}

	/**
	 * returns the downside deviation, defined as the
	 * square root of the downside variance.
	 */
	public /*@Real*/ double downsideDeviation() /*@ReadOnly*/ {
		return Math.sqrt(downsideVariance());
	}

	/**
	 * returns the variance of observations below target,
	 * {@latex[ \frac{N}{N-1}
	 *	  \mathrm{E}\left[ (x-t)^2 \;|\;
	 *						x < t \right]. }
	 *
	 * @see Dembo and Freeman, "The Rules Of Risk", Wiley (2001).
	 */
	public /*@Real*/ double regret(final /*@Real*/ double target) /*@ReadOnly*/ {
		// average over the range below the target

		final List<Ops.DoubleOp> functions = new ArrayList<Ops.DoubleOp>();
		functions.add(new Square());
		functions.add(new Bind2nd(new Minus(), target));
		final Expression comp = new Expression(functions);
		final Ops.DoublePredicate less = new Bind2ndPredicate(new LessThanPredicate(), target);

		final Pair<Double, Integer> result = expectationValue(comp, less);
		final double x = result.first();

		final int n = result.second().intValue();
		QL.require(n >= 2 , UNSUFFICIENT_SAMPLES_UNDER_TARGET);
		return (n/(n-1.0))*x;
	}

	/**
	 * potential upside (the reciprocal of VaR) at a given percentile
	 */
	public /*@Real*/ double potentialUpside(final /*@Real*/ double centile) /*@ReadOnly*/ {
		QL.require(centile >= 0.9 && centile < 1.0 , "percentile out of range [0.9, 1.0)");
		// potential upside must be a gain, i.e., floored at 0.0
		return Math.max(percentile(centile), 0.0);
	}

	/**
	 * value-at-risk at a given percentile
	 */
	public /*@Real*/ double valueAtRisk(final /*@Real*/ double centile) /*@ReadOnly*/ {
		QL.require(centile >= 0.9 && centile < 1.0 , "percentile out of range [0.9, 1.0)");
		return - Math.min(percentile(1.0-centile), 0.0);
	}

	/**
	 * expected shortfall at a given percentile
	 * <p>
	 * returns the expected loss in case that the loss exceeded
	 * a VaR threshold,
	 * <p>
	 * {@latex[ \mathrm{E}\left[ x \;|\; x < \mathrm{VaR}(p) \right], }
	 * <p>
	 * that is the average of observations below the given percentile \f$ p \f$.
	 * Also know as conditional value-at-risk.
	 * 
	 * @see Artzner, Delbaen, Eber and Heath, "Coherent measures of risk", Mathematical Finance 9 (1999)
	 */
	public /*@Real*/ double expectedShortfall(final /*@Real*/ double centile) /*@ReadOnly*/ {
		QL.require(centile>=0.9 && centile<1.0, "percentile out of range [0.9, 1.0)");
		QL.ensure(samples() != 0, EMPTY_SAMPLE_SET);
		
		final double target = -valueAtRisk(centile);

		final Ops.DoublePredicate less = new Bind2ndPredicate(new LessThanPredicate(), target);
		final Pair<Double, Integer> result = expectationValue(new Identity(), less);

		final double x = result.first();
		final Integer N = result.second();

		QL.ensure(N != 0, NO_DATA_BELOW_THE_TARGET);
		// must be a loss, i.e., capped at 0.0 and negated
		return -Math.min(x, 0.0);

	}

	/**
	 * probability of missing the given target, defined as
	 * {@latex[ \mathrm{E}\left[ \Theta \;|\; (-\infty,\infty) \right] }
	 * where
	 * {@latex[ \Theta(x) = \left\{
	 *	  \begin{array}{ll}
	 *	  1 & x < t \\
	 *	  0 & x \geq t
	 *	  \end{array}
	 *	  \right. }
	 */
	public /*@Real*/ double shortfall(final /*@Real*/ double target) /*@ReadOnly*/ {
		QL.ensure(samples() != 0, EMPTY_SAMPLE_SET);

		final Ops.DoublePredicate less = new Bind2ndPredicate(new LessThanPredicate(), target);
		return expectationValue(new Clipped(less, new Constant(1.0)), new TruePredicate()).first();
	}

	/**
	 * averaged shortfallness, defined as
	 * {@latex[ \mathrm{E}\left[ t-x \;|\; x<t \right] }
	 */
	public /*@Real*/ double averageShortfall(final /*@Real*/ double target) /*@ReadOnly*/ {

		final Ops.DoubleOp minus = new Bind1st(target, new Minus());
		final Ops.DoublePredicate less = new Bind1stPredicate(target, new LessThanPredicate());
		final Pair<Double, Integer> result = expectationValue(minus, less);

		final double x = result.first();
		final Integer N = result.second();
		QL.ensure(N != 0, NO_DATA_BELOW_THE_TARGET);
		return x;
	}

}
