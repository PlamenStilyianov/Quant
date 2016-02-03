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
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.math.distributions.InverseCumulativeNormal;
import org.jquantlib.math.distributions.NormalDistribution;

/**
 * Statistics tool for gaussian-assumption risk measures
 * <p>
 * This class wraps a somewhat generic statistic tool and adds
 * a number of gaussian risk measures (e.g.: value-at-risk, expected
 * shortfall, etc.) based on the mean and variance provided by
 * the underlying statistic tool.
 * 
 * @author Ueli Hofstetter
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q4_UNIT, reviewers = { "Richard Gomes" }, version = Version.V097)
public abstract class GenericGaussianStatistics extends GeneralStatistics {

    public GenericGaussianStatistics() {
    	super();
    }

    
    //
    // public methods
    //
    

    //
	// Gaussian risk measures
	//
	
    /**
     * returns the downside variance, defined as
     * <p>
     * {@latex[ \frac{N}{N-1} \times \frac{ \sum_{i=1}^{N}
     *      \theta \times x_i^{2}}{ \sum_{i=1}^{N} w_i} },
     * <p>
     * where {@latex$ \theta } = 0 if x > 0 and
     *   {@latex$ \theta } = 1 if x <0
     */
    public /*@Real*/ double gaussianDownsideVariance() /* @ReadOnly */ {
        return gaussianRegret(0.0);
    }

    /**
     * returns the downside deviation, defined as the
     * square root of the downside variance.
	 */
	public /*@Real*/ double gaussianDownsideDeviation() /* @ReadOnly */ {
	    return Math.sqrt(gaussianDownsideVariance());
	}
	
	/**
	 * returns the variance of observations below target
	 * <p>
	 * {@latex[ \frac{\sum w_i (min(0, x_i-target))^2 }{\sum w_i}. }
	 * <p>
	 * @see Dembo, Freeman "The Rules Of Risk", Wiley (2001)
	 */
	public /*@Real*/ double gaussianRegret(/*@Real*/ double target) /* @ReadOnly */ {
        /*@Real*/ double m = this.mean();
        /*@Real*/ double std = this.standardDeviation();
        /*@Real*/ double variance = std*std;
        final CumulativeNormalDistribution gIntegral = new CumulativeNormalDistribution(m, std);
        final NormalDistribution g = new NormalDistribution(m, std);
        /*@Real*/ double firstTerm = variance + m*m - 2.0*target*m + target*target;
        /*@Real*/ double alfa = gIntegral.op(target);
        /*@Real*/ double secondTerm = m - target;
        /*@Real*/ double beta = variance*g.op(target);
        /*@Real*/ double result = alfa*firstTerm - beta*secondTerm;
        return result/alfa;
    }

    
    public /*@Real*/ double gaussianPercentile(/*@Real*/ double percentile) /* @ReadOnly */ {
		QL.require(percentile>0.0, "percentile must be > 0.0");
		QL.require(percentile<1.0, "percentile must be < 1.0");
		final InverseCumulativeNormal gInverse = new InverseCumulativeNormal(mean(), standardDeviation());
		return gInverse.op(percentile);
	}

    
    /**
     * percentile must be in range (0%-100%) extremes excluded
     */
    public /*@Real*/ double gaussianTopPercentile(/*@Real*/ double percentile) /* @ReadOnly */ {
        return gaussianPercentile(1.0-percentile);
    }


    /**
     * percentile must be in range [90%-100%)
     */
    public /*@Real*/ double gaussianPotentialUpside(/*@Real*/ double percentile) /* @ReadOnly */ {
        QL.require(percentile<1.0 && percentile>=0.9, "percentile is out of range [0.9, 1)");
        /*@Real*/ double result = gaussianPercentile(percentile);
        // potential upside must be a gain, i.e., floored at 0.0
        return Math.max(result, 0.0);
    }

    
    /**
     * percentile must be in range [90%-100%)
     */
    public /*@Real*/ double gaussianValueAtRisk(/*@Real*/ double percentile) /* @ReadOnly */ {
        QL.require(percentile<1.0 && percentile>=0.9, "percentile is out of range [0.9, 1)");
        /*@Real*/ double result = gaussianPercentile(1.0-percentile);
        // VaR must be a loss: this means that it has to be MIN(dist(1.0-percentile), 0.0)
        // VaR must also be a positive quantity, so -MIN(*)
        return -Math.min(result, 0.0);
    }

    /**
     * gaussian-assumption Expected Shortfall at a given percentile
     * <p>
     * Assuming a gaussian distribution it
     * returns the expected loss in case that the loss exceeded
     * a VaR threshold,
     * <p>
     * {@latex[ \mathrm{E}\left[ x \;|\; x < \mathrm{VaR}(p) \right], }
     * <p>
     * that is the average of observations below the
     * given percentile {@latex$ p }.
     * Also know as conditional value-at-risk.
     *
     * @see Artzner, Delbaen, Eber and Heath, "Coherent measures of risk", Mathematical Finance 9 (1999)
     */
    public /*@Real*/ double gaussianExpectedShortfall(/*@Real*/ double percentile) /* @ReadOnly */ {
        QL.require(percentile<1.0 && percentile>=0.9, "percentile is out of range [0.9, 1)");
        /*@Real*/ double m = this.mean();
        /*@Real*/ double std = this.standardDeviation();
        final InverseCumulativeNormal gInverse = new InverseCumulativeNormal(m, std);
        /*@Real*/ double var = gInverse.op(1.0-percentile);
        final NormalDistribution g = new NormalDistribution(m, std);
        /*@Real*/ double result = m - std*std*g.op(var)/(1.0-percentile);
        // expectedShortfall must be a loss: this means that it has to be MIN(result, 0.0)
        // expectedShortfall must also be a positive quantity, so -MIN(*)
        return -Math.min(result, 0.0);
    }


    /**
     * gaussian-assumption Shortfall (observations below target)        
     */
    public /*@Real*/ double gaussianShortfall(/*@Real*/ double target) /* @ReadOnly */ {
        final CumulativeNormalDistribution gIntegral = new CumulativeNormalDistribution(mean(), standardDeviation());
        return gIntegral.op(target);
    }


    /**
     * gaussian-assumption {@link Average} Shortfall (averaged shortfallness)        
     */
    public /*@Real*/ double gaussianAverageShortfall(/*@Real*/ double target) /* @ReadOnly */ {
    	/*@Real*/ double m = mean();
    	/*@Real*/ double std = standardDeviation();
        final CumulativeNormalDistribution gIntegral = new CumulativeNormalDistribution(m, std);
        final NormalDistribution g = new NormalDistribution(m, std);
        return ( (target-m) + std*std*g.op(target)/gIntegral.op(target) );
    }

}
