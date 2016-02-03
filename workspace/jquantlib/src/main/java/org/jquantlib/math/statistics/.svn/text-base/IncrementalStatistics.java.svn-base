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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl

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
import org.jquantlib.math.Constants;
import org.jquantlib.math.matrixutilities.Array;


/**
 * Statistics tool based on incremental accumulation
 * <p>
 * It can accumulate a set of data and return statistics (e.g: mean,
 * variance, skewness, kurtosis, error estimation, etc.)
 * <p>
 * @warning high moments are numerically unstable for high
 *          average/standardDeviation ratios.
 *          
 * @author Ueli Hofstetter
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q4_UNIT, reviewers = { "Richard Gomes" }, version = Version.V097)
public class IncrementalStatistics extends GenericRiskStatistics {

    private static final String UNSUFFICIENT_SAMPLE_WEIGHT    = "sampleWeight_=0, unsufficient";
    private static final String UNSUFFICIENT_SAMPLE_NUMBER    = "sample number <=1, unsufficient";
    private static final String UNSUFFICIENT_SAMPLE_NUMBER_2  = "sample number <=2, unsufficient";
    private static final String UNSUFFICIENT_SAMPLE_NUMBER_3  = "sample number <=3, unsufficient";
    private static final String NEGATIVE_VARIANCE             = "negative variance";
    private static final String EMPTY_SAMPLE_SET              = "empty sample set";
    private static final String MAX_NUMBER_OF_SAMPLES_REACHED = "maximum number of samples reached";
    private static final String INCOMPATIBLE_ARRAY_SIZES      = "incompatible array sizes";

    
    protected /*@Size*/ int sampleNumber_;
    protected /*@Size*/ int downsideSampleNumber_;
    protected /*@Real*/ double sampleWeight_, downsideSampleWeight_;
    protected /*@Real*/ double sum_, quadraticSum_, downsideQuadraticSum_;
    protected /*@Real*/ double cubicSum_, fourthPowerSum_;
    protected /*@Real*/ double min_, max_;


    public IncrementalStatistics() {
    	super();
        reset();
    }

    
    //
    // public methods
    //
    
    /**
     * number of samples collected
     */
    @Override
    public /*@Size*/ int samples() /*@ReadOnly*/ {
        return sampleNumber_;
    }

    /**
     * sum of data weights
     */
    @Override
    public /*@Real*/ double weightSum() /*@ReadOnly*/ {
        return sampleWeight_;
    }

    /**
     * returns the mean, defined as
     * {@latex[ \langle x \rangle = \frac{\sum w_i x_i}{\sum w_i}. }
     */
    @Override
    public /*@Real*/ double mean() /*@ReadOnly*/ {
        QL.require(sampleWeight_>0.0, UNSUFFICIENT_SAMPLE_WEIGHT);
        return sum_/sampleWeight_;
    }

    /**
     * returns the variance, defined as
     * {@latex[ \frac{N}{N-1} \left\langle \left(
     *      x-\langle x \rangle \right)^2 \right\rangle. }
     */
    @Override
    public /*@Real*/ double variance() /*@ReadOnly*/ {
        QL.require(sampleWeight_>0.0, UNSUFFICIENT_SAMPLE_WEIGHT);
        QL.require(sampleNumber_>1, UNSUFFICIENT_SAMPLE_NUMBER);

        /*@Real*/ double m = mean();
        /*@Real*/ double v = quadraticSum_/sampleWeight_;
        v -= m*m;
        v *= sampleNumber_/(sampleNumber_-1.0);

        QL.ensure(v >= 0.0, NEGATIVE_VARIANCE);
        return v;
    }

    
    /**
     * returns the standard deviation {@latex$ \sigma }, defined as the
     * square root of the variance.
     */
    @Override
    public /*@Real*/ double standardDeviation() /*@ReadOnly*/ {
        return Math.sqrt(variance());
    }


    /**
     * returns the error estimate {@latex$ \epsilon }, defined as the
     * square root of the ratio of the variance to the number of
     * samples.
     */
    @Override
    public /*@Real*/ double errorEstimate() /*@ReadOnly*/ {
        /*@Real*/ double var = variance();
        QL.require(samples() > 0, EMPTY_SAMPLE_SET);
        return Math.sqrt(var/samples());
    }

    /**
     * returns the downside deviation, defined as the
     * square root of the downside variance.
     */
    @Override
    public /*@Real*/ double downsideDeviation() /*@ReadOnly*/ {
        return Math.sqrt(downsideVariance());
    }

    /**
     * returns the downside variance, defined as
     * {@latex[ \frac{N}{N-1} \times \frac{ \sum_{i=1}^{N}
     *      \theta \times x_i^{2}}{ \sum_{i=1}^{N} w_i} },
     *  where {@latex$ \theta } = 0 if x > 0 and
     *  {@latex$ \theta } =1 if x <0
     */
    @Override
    public /*@Real*/ double downsideVariance() /*@ReadOnly*/ {
        if (downsideSampleWeight_==0.0) {
            QL.require(sampleWeight_>0.0, UNSUFFICIENT_SAMPLE_WEIGHT);
            return 0.0;
        }

        QL.require(downsideSampleNumber_>1, "sample number below zero <=1, unsufficient");

        return (downsideSampleNumber_/(downsideSampleNumber_-1.0))*
            (downsideQuadraticSum_ /downsideSampleWeight_);
    }

    /**
     * returns the skewness, defined as
     * {@latex[ \frac{N^2}{(N-1)(N-2)} \frac{\left\langle \left(
     *    x-\langle x \rangle \right)^3 \right\rangle}{\sigma^3}. }
     *  The above evaluates to 0 for a Gaussian distribution.
     */
    @Override
    public /*@Real*/ double skewness() /*@ReadOnly*/ {
        QL.require(sampleNumber_>2, UNSUFFICIENT_SAMPLE_NUMBER_2);
        /*@Real*/ double s = standardDeviation();

        if (s==0.0) return 0.0;

        /*@Real*/ double m = mean();
        /*@Real*/ double result = cubicSum_/sampleWeight_;
        result -= 3.0*m*(quadraticSum_/sampleWeight_);
        result += 2.0*m*m*m;
        result /= s*s*s;
        result *= sampleNumber_/(sampleNumber_-1.0);
        result *= sampleNumber_/(sampleNumber_-2.0);
        return result;
    }

    
    /**
     * returns the excess kurtosis, defined as
     * {@latex[ \frac{N^2(N+1)}{(N-1)(N-2)(N-3)}
     *      \frac{\left\langle \left(x-\langle x \rangle \right)^4
     *      \right\rangle}{\sigma^4} - \frac{3(N-1)^2}{(N-2)(N-3)}. }
     *  The above evaluates to 0 for a Gaussian distribution.
     */
    @Override
    public /*@Real*/ double kurtosis() /*@ReadOnly*/ {
        QL.require(sampleNumber_>3, UNSUFFICIENT_SAMPLE_NUMBER_3);

        /*@Real*/ double m = mean();
        /*@Real*/ double v = variance();

        /*@Real*/ double c = (sampleNumber_-1.0)/(sampleNumber_-2.0);
        c *= (sampleNumber_-1.0)/(sampleNumber_-3.0);
        c *= 3.0;

        if (v==0) return c;

        /*@Real*/ double result = fourthPowerSum_/sampleWeight_;
        result -= 4.0*m*(cubicSum_/sampleWeight_);
        result += 6.0*m*m*(quadraticSum_/sampleWeight_);
        result -= 3.0*m*m*m*m;
        result /= v*v;
        result *= sampleNumber_/(sampleNumber_-1.0);
        result *= sampleNumber_/(sampleNumber_-2.0);
        result *= (sampleNumber_+1.0)/(sampleNumber_-3.0);


        return result-c;
    }

    /**
     * returns the minimum sample value
     */
    @Override
    public /*@Real*/ double min() /*@ReadOnly*/ {
        QL.require(samples() > 0, EMPTY_SAMPLE_SET);
        return min_;
    }


    /**
     * returns the maximum sample value
     */
    @Override
    public /*@Real*/ double max() /*@ReadOnly*/ {
        QL.require(samples() > 0, EMPTY_SAMPLE_SET);
        return max_;
    }

    /**
     * adds a sequence of data to the set, with default weight
     */
    @Override
    public void addSequence(final double[] datum) {
	    for (int i=0; i<datum.length; i++) {
	    	add(datum[i]);
	    }
    }
    
    /**
     * adds a sequence of data to the set, each with its weight
     * <p>
     * weights must be positive or null
     */
    @Override
    public void addSequence(final double[] datum, final double[] weights) {
        QL.require(datum.length==weights.length, INCOMPATIBLE_ARRAY_SIZES);
        for (int i=0; i<datum.length; i++) {
        	add(datum[i], weights[i]);
        }
    }

    /**
     * adds a sequence of data to the set, with default weight
     */
    @Override
    public void addSequence(final Array datum) {
	    for (int i=0; i<datum.size(); i++) {
	    	add(datum.get(i));
	    }
    }
    
    /**
     * adds a sequence of data to the set, each with its weight
     * <p>
     * weights must be positive or null
     */
    @Override
    public void addSequence(final Array datum, final Array weights) {
        QL.require(datum.size()==weights.size(), INCOMPATIBLE_ARRAY_SIZES);
        for (int i=0; i<datum.size(); i++) {
        	add(datum.get(i), weights.get(i));
        }
    }


    /**
     * adds a datum to the set, possibly with a weight
     * <p>
     * weight must be positive or null
     */
    @Override
    public void add(final /*@Real*/ double value) {
    	add(value, 1.0);
    }
    
    @Override
    public void add(final /*@Real*/ double value, final /*@Real*/ double weight) {
        QL.require(weight>=0.0, "negative weight not allowed");

        /*@Size*/ int oldSamples = sampleNumber_;
        sampleNumber_++;
        QL.ensure(sampleNumber_ > oldSamples, MAX_NUMBER_OF_SAMPLES_REACHED);

        sampleWeight_ += weight;

        /*@Real*/ double temp = weight*value;
        sum_ += temp;
        temp *= value;
        quadraticSum_ += temp;
        if (value<0.0) {
            downsideQuadraticSum_ += temp;
            downsideSampleNumber_++;
            downsideSampleWeight_ += weight;
        }
        temp *= value;
        cubicSum_ += temp;
        temp *= value;
        fourthPowerSum_ += temp;
        if (oldSamples == 0) {
            min_ = max_ = value;
        } else {
            min_ = Math.min(value, min_);
            max_ = Math.max(value, max_);
        }
    }
        
        
        
    /**
     * resets the data to a null set
     */
    @Override
    public void reset() {
        min_ = Constants.DBL_MAX;
        max_ = Constants.DBL_MIN;
        sampleNumber_ = 0;
        downsideSampleNumber_ = 0;
        sampleWeight_ = 0.0;
        downsideSampleWeight_ = 0.0;
        sum_ = 0.0;
        quadraticSum_ = 0.0;
        downsideQuadraticSum_ = 0.0;
        cubicSum_ = 0.0;
        fourthPowerSum_ = 0.0;
    }

}
