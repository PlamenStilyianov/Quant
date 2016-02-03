/*
 Copyright (C) 2008 Praneet Tiwari
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;
import org.jquantlib.math.functions.Bind2nd;
import org.jquantlib.math.functions.ComposedFunction;
import org.jquantlib.math.functions.Cube;
import org.jquantlib.math.functions.Everywhere;
import org.jquantlib.math.functions.Fourth;
import org.jquantlib.math.functions.Identity;
import org.jquantlib.math.functions.Minus;
import org.jquantlib.math.functions.Square;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.util.ComparablePair;
import org.jquantlib.util.Pair;

/**
 * Statistics tool
 * <p>
 * This class accumulates a set of data and returns their
 * statistics (e.g: mean, variance, skewness, kurtosis,
 * error estimation, percentile, etc.) based on the empirical
 * distribution (no gaussian assumption)
 * <p>
 * It doesn't suffer the numerical instability problem of
 * IncrementalStatistics. The downside is that it stores all
 * samples, thus increasing the memory requirements.
 * 
 * @author Praneet Tiwari
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q4_UNIT, reviewers = { "Richard Gomes" }, version = Version.V097)
public abstract class GeneralStatistics {
	
    private List<ComparablePair<Double, Double>> samples;
    private boolean sorted;
        
    private static final String EMPTY_SAMPLE_SET = "empty sample set";
    private static final String NEGATIVE_WEIGHT_NOT_ALLOWED = "negative weight not allowed";
    private static final String INCOMPATIBLE_ARRAY_SIZES = "incompatible array sizes";
    

    public GeneralStatistics() {
        reset();
    }

    
    //
    // public methods
    //
    
    /**
     * adds a sequence of data to the set, with default weight
     */
    public void addSequence(final double[] datum) {
	    for (int i=0; i<datum.length; i++) {
	    	add(datum[i]);
	    }
    }
    
    /**
     * adds a sequence of data to the set, each with its weight
     */
    public void addSequence(
    		final double[] datum, 
    		final double[] weights) {
        QL.require(datum.length==weights.length, INCOMPATIBLE_ARRAY_SIZES);
        for (int i=0; i<datum.length; i++) {
        	add(datum[i], weights[i]);
        }
    }

    /**
     * adds a sequence of data to the set, with default weight
     */
    public void addSequence(final Array datum) {
	    for (int i=0; i<datum.size(); i++) {
	    	add(datum.get(i));
	    }
    }
    
    /**
     * adds a sequence of data to the set, each with its weight
     */
    public void addSequence(
    		final Array datum, 
    		final Array weights) {
        QL.require(datum.size()==weights.size(), INCOMPATIBLE_ARRAY_SIZES);
        for (int i=0; i<datum.size(); i++) {
        	add(datum.get(i), weights.get(i));
        }
    }

    /**
     * resets the data to a null set
     */
    public void reset() {
    	samples = new ArrayList<ComparablePair<Double, Double>>();
        sorted = true;
    }

    /**
     * sort the data set in increasing order
     */
    public void sort() /*@ReadOnly*/ {
        if (!sorted) {
            Collections.sort(samples);
            sorted = true;
        }
    }
    
    public /*@Size*/ int samples() /*@ReadOnly*/ {
        return samples.size();
    }

    public List<ComparablePair<Double, Double>> data() /*@ReadOnly*/ {
    	return Collections.unmodifiableList(samples);
    }

    public /*@Real*/ double weightSum() /*@ReadOnly*/ {
        /*@Real*/ double result = 0.0;
        for (final ComparablePair<Double, Double> it : samples) {
        	result += it.second();
        }
        return result;
    }

    public /*@Real*/ double mean() /*@ReadOnly*/ {
        /*@Size*/ final int N = samples();
        QL.require(N != 0, "empty sample set");
        // eat our own dog food
        return expectationValue(new Identity(), new Everywhere()).first();
    }

    public /*@Real*/ double variance() /*@ReadOnly*/ {
        /*@Size*/ final int N = samples();
        QL.require(N > 1, "sample number <=1, unsufficient");
        // Subtract the mean and square. Repeat on the whole range.
        // Hopefully, the whole thing will be inlined in a single loop.
        /*@Real*/ final double s2 = expectationValue(
        		new ComposedFunction(
        				new Square(), new Bind2nd(
        						new Minus(), mean())),
                                   new Everywhere()).first();
        return s2*N/(N-1.0);
    }

    public /*@Real*/ double standardDeviation() /*@ReadOnly*/ {
        return Math.sqrt(variance());
    }

    public /*@Real*/ double errorEstimate() /*@ReadOnly*/ {
        return Math.sqrt(variance()/samples());
    }

    public /*@Real*/ double skewness() /*@ReadOnly*/ {
        /*@Size*/ final int N = samples();
        QL.require(N > 2, "sample number <=2, unsufficient");

        /*@Real*/ final double x = expectationValue(
        		new ComposedFunction(
        				new Cube(), new Bind2nd(
        						new Minus(), mean())),
                                  new Everywhere()).first();
        /*@Real*/ final double sigma = standardDeviation();

        return (x/(sigma*sigma*sigma))*(N/(N-1.0))*(N/(N-2.0));
    }

    public /*@Real*/ double kurtosis() /*@ReadOnly*/ {
        /*@Size*/ final int N = samples();
        QL.require(N > 3, "sample number <=3, unsufficient");

        /*@Real*/ final double x = expectationValue(
        		new ComposedFunction(
        				new Fourth(), new Bind2nd(
        						new Minus(), mean())),
                                  new Everywhere()).first();
        /*@Real*/ final double sigma2 = variance();

        /*@Real*/ final double c1 = (N/(N-1.0)) * (N/(N-2.0)) * ((N+1.0)/(N-3.0));
        /*@Real*/ final double c2 = 3.0 * ((N-1.0)/(N-2.0)) * ((N-1.0)/(N-3.0));

        return c1*(x/(sigma2*sigma2))-c2;
    }

    public /*@Real*/ double min() /*@ReadOnly*/ {
        QL.require(samples()>0, EMPTY_SAMPLE_SET);
        return Collections.min(samples).first();
    }

    public /*@Real*/ double max() /*@ReadOnly*/ {
        QL.require(samples()>0, EMPTY_SAMPLE_SET);
        return Collections.max(samples).first();
    }

    public final Pair<Double, Integer> expectationValue(final Ops.DoubleOp f, final Ops.DoublePredicate inRange) {
        double num = 0.0;
        double den = 0.0;
        int n = 0;
        for (final ComparablePair<Double, Double> element : samples) {
            final double x = element.first();
            final double w = element.second();
            if (inRange.op(x)) {
                num += f.op(x) * w;
                den += w;
                n++;
            }
        }
        if (n == 0)
            return new Pair<Double, Integer>(Constants.NULL_REAL, 0);
        else
            return new Pair<Double, Integer>(num/den, n);
    }

    public /*@Real*/ double percentile(final /*@Real*/ double percent) /*@ReadOnly*/ {
        QL.require(percent > 0.0 && percent <= 1.0, "percentile must be in (0.0, 1.0]");
        /*@Real*/ final double sampleWeight = weightSum();
        QL.require(sampleWeight>0.0, "empty sample set");

        sort();

        int k = 0; final int l = samples.size()-1;
        
        /* the sum of weight is non null, therefore there's at least one sample */
        /*@Real*/ double integral = samples.get(k).second();
        /*@Real*/ final double target = percent*sampleWeight;
        
        while (integral < target && k != l) {
        	k++;
            integral += samples.get(k).second();
        }
        return samples.get(k).first();
    }

    public /*@Real*/ double topPercentile(final /*@Real*/ double percent) /*@ReadOnly*/ {
        QL.require(percent > 0.0 && percent <= 1.0, "percentile must be in (0.0, 1.0]");
        /*@Real*/ final double sampleWeight = weightSum();
        QL.require(sampleWeight > 0.0, "empty sample set");

        sort();

        int k = samples.size()-1; final int l = 0;
        
        /* the sum of weight is non null, therefore there's at least one sample */
        /*@Real*/ double integral = samples.get(k).second();
        /*@Real*/ final double target = percent*sampleWeight;
        
        while (integral < target && k != l) {
        	k--;
            integral += samples.get(k).second();
        }
        return samples.get(k).first();
    }
    
    public void add(/*@Real*/ final double value) {
    	add(value, 1.0);
    }
    
    public void add(/*@Real*/ final double value, /*@Real*/ final double weight) {
        QL.require(weight>=0.0, NEGATIVE_WEIGHT_NOT_ALLOWED);
        samples.add(new ComparablePair<Double, Double>(value, weight));
        sorted = false;
    }
    
}
