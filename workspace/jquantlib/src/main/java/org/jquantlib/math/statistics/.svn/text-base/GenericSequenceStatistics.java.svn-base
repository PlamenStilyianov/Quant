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
 Copyright (C) 2003, 2004, 2005, 2006, 2007 Ferdinando Ametrano

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

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;

/**
 * Statistics analysis of N-dimensional (sequence) data
 * <p>
 * It provides 1-dimensional statistics as discrepancy plus
 * N-dimensional (sequence) statistics (e.g. mean,
 * variance, skewness, kurtosis, etc.) with one component for each
 * dimension of the sample space.
 * 
 * @author Ueli Hofstetter
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q4_UNIT, reviewers = { "Richard Gomes" }, version = Version.V097)
public class GenericSequenceStatistics {

	private static final String UNSUFFICIENT_SAMPLE_WEIGHT = "sampleWeight=0, unsufficient";
	private static final String UNSUFFICIENT_SAMPLE_NUMBER = "sample number <=1, unsufficient";
	private static final String NULL_DIMENSION			 = "sample error: null dimension";
	private static final String SAMPLE_SIZE_MISMATCH	   = "sample size mismatch";

	protected /*@Size*/ int dimension_;
	protected Statistics[] stats;
	protected Matrix quadraticSum;
	private /*@Real*/ double[] results;
	
	
	public GenericSequenceStatistics() {
		this(0);
	}

	public GenericSequenceStatistics(final int dimension) {
		this.dimension_ = 0;
		reset(dimension);
	}


	//
	// public methods
	//

	public /*@Size*/ int size() /*@ReadOnly*/ {
		return dimension_;
	}

	//---- covariance and correlation

	/**
	 * returns the covariance Matrix
	 */
	public Matrix covariance() /*@ReadOnly*/ {
		final /*@Real*/ double sampleWeight = weightSum();
		QL.require(sampleWeight > 0.0, UNSUFFICIENT_SAMPLE_WEIGHT);

		final /*@Real*/ double sampleNumber = samples();
		QL.require(sampleNumber > 1.0, UNSUFFICIENT_SAMPLE_NUMBER);

		final Array m = mean();
		final /*@Real*/ double inv = 1.0/sampleWeight;

		final Matrix result = quadraticSum.mul(inv);
		result.subAssign(m.outerProduct(m));

		result.mulAssign( sampleNumber/(sampleNumber-1.0) );
		return result;
	}

	/**
	 * returns the correlation Matrix
	 */
	public Matrix correlation() /*@ReadOnly*/ {
		final Matrix corr = covariance();
		final Array v = corr.diagonal();
		for (/*@Size*/ int i=0; i<dimension_; i++){
			for (/*@Size*/ int j=0; j<dimension_; j++){
				if (i==j) {
					if (v.$[v._(i)]==0.0) {
						corr.$[corr._(i,j)] = 1.0;
					} else {
						corr.$[corr._(i,j)] *= 1.0/Math.sqrt(v.$[v._(i)] * v.$[v._(j)]);
					}
				} else {
					if (v.$[v._(i)]==0.0 && v.$[v._(j)]==0) {
						corr.$[corr._(i,j)] = 1.0;
					} else if (v.$[v._(i)]==0.0 || v.$[v._(j)]==0.0) {
						corr.$[corr._(i,j)] = 0.0;
					} else {
						corr.$[corr._(i,j)] *= 1.0/Math.sqrt(v.$[v._(i)]*v.$[v._(j)]);
					}
				}
			} // j for
		} // i for
		return corr;
	}
	
	//---- 1-D inspectors lifted from underlying statistics class

	public /*@Size*/ int samples() /*@ReadOnly*/ {
		return (stats.length == 0) ? 0 : stats[0].samples();
	}

	public /*@Real*/ double weightSum() /*@ReadOnly*/ {
		return (stats.length == 0) ? 0.0 : stats[0].weightSum();
	}

	//---- N-D inspectors lifted from underlying statistics class

	//-- void argument list

	public Array mean() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].mean();
		}
		return new Array(results);
	}

	public Array variance() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].variance();
		}
		return new Array(results);
	}

	public Array standardDeviation() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].standardDeviation();
		}
		return new Array(results);
	}

	public Array downsideVariance() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].downsideVariance();
		}
		return new Array(results);
	}

	public Array downsideDeviation() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].downsideDeviation();
		}
		return new Array(results);
	}

	public Array semiVariance() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].semiVariance();
		}
		return new Array(results);
	}

	public Array semiDeviation() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].semiDeviation();
		}
		return new Array(results);
	}

	public Array errorEstimate() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].errorEstimate();
		}
		return new Array(results);
	}

	public Array skewness() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].skewness();
		}
		return new Array(results);
	}

	public Array kurtosis() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].kurtosis();
		}
		return new Array(results);
	}

	public Array min() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].min();
		}
		return new Array(results);
	}

	public Array max() /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++) {
			results[i] = stats[i].max();
		}
		return new Array(results);
	}
	
	//-- single argument list

	public Array gaussianPercentile(/*@Real*/ final double y) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].gaussianPercentile(y);
		return new Array(results);
	}

	public Array gaussianPotentialUpside(/*@Real*/ final double percentile) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].gaussianPotentialUpside(percentile);
		return new Array(results);
	}

	public Array gaussianValueAtRisk(/*@Real*/ final double percentile) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].gaussianValueAtRisk(percentile);
		return new Array(results);
	}

	public Array gaussianExpectedShortfall(/*@Real*/ final double percentile) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].gaussianExpectedShortfall(percentile);
		return new Array(results);
	}

	public Array gaussianShortfall(/*@Real*/ final double target) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].gaussianShortfall(target);
		return new Array(results);
	}

	public Array gaussianAverageShortfall(/*@Real*/ final double target) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].gaussianAverageShortfall(target);
		return new Array(results);
	}

	public Array percentile(/*@Real*/ final double y) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].percentile(y);
		return new Array(results);
	}

	public Array potentialUpside(/*@Real*/ final double percentile) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].potentialUpside(percentile);
		return new Array(results);
	}

	public Array valueAtRisk(/*@Real*/ final double percentile) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].valueAtRisk(percentile);
		return new Array(results);
	}

	public Array expectedShortfall(/*@Real*/ final double percentile) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].expectedShortfall(percentile);
		return new Array(results);
	}

	public Array regret(/*@Real*/ final double target) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].regret(target);
		return new Array(results);
	}

	public Array shortfall(/*@Real*/ final double target) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].shortfall(target);
		return new Array(results);
	}

	public Array averageShortfall(/*@Real*/ final double target) /*@ReadOnly*/ {
		for (/*@Size*/ int i=0; i<dimension_; i++)
			results[i] = stats[i].averageShortfall(target);
		return new Array(results);
	}
	

	//---- Modifiers
	
	public void reset() {
		reset(0);
	}

	public void reset(final/* @Size */int dimension) {
		// (re-)initialize
		if (dimension > 0) {
			if (dimension == dimension_) {
				for (/* @Size */int i = 0; i < dimension_; ++i)
					stats[i].reset();
			} else {
				this.dimension_ = dimension;
				stats = new Statistics[dimension];
				for (int i=0; i<dimension; i++) {
					stats[i] = new Statistics();
				}
				results = new double[dimension];
			}
			quadraticSum = new Matrix(dimension_, dimension_);
		} else {
			dimension_ = dimension;
		}
	}

    /**
     * adds a sequence of data to the set, with default weight
     */
	public void add(final double[] datum) {
		add(datum, 1.0);
	}

    /**
     * adds a sequence of data to the set, each with its weight
     */
	public void add(final double[] datum, final/* @Real */double weight) {
		if (dimension_ == 0) {
			// stat wasn't initialized yet
			final /*@Integer*/ int dimension = datum.length;
			QL.require(dimension > 0, NULL_DIMENSION);
			reset(/* @Size */dimension);
		}
		QL.require(datum.length == dimension_, SAMPLE_SIZE_MISMATCH);
		final Array array = new Array(datum);
		quadraticSum.addAssign(array.outerProduct(array).mulAssign(weight));
		for (/* @Size */int i = 0; i < dimension_; i++) {
			stats[i].add(datum[i], weight);
		}
	}

    /**
     * adds a sequence of data to the set, with default weight
     */
	public void add(final Array datum) {
		add(datum, 1.0);
	}

    /**
     * adds a sequence of data to the set, each with its weight
     */
	public void add(final Array datum, final/* @Real */double weight) {
		if (dimension_ == 0) {
			// stat wasn't initialized yet
			final /*@Integer*/ int dimension = datum.size();
			QL.require(dimension > 0, NULL_DIMENSION);
			reset(/* @Size */dimension);
		}
		QL.require(datum.size() == dimension_, SAMPLE_SIZE_MISMATCH);
		quadraticSum.addAssign(datum.outerProduct(datum).mulAssign(weight));
		for (/* @Size */int i = 0; i < dimension_; i++) {
			stats[i].add(datum.$[datum._(i)], weight);
		}
	}

}
