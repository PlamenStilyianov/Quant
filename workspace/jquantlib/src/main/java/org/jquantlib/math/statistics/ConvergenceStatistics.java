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
 Copyright (C) 2005 Gary Kennedy
 Copyright (C) 2006 StatPro Italia srl

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
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.util.Pair;


/**
 * Statistics class with convergence table
 * <p>
 * This class decorates another statistics class adding a convergence table
 * calculation. The table tracks the convergence of the mean.
 * <p> 
 * It is possible to specify the number of samples at which the mean should be
 * stored by mean of the second template parameter; the default is to store \f$
 * 2^{n-1} \f$ samples at the \f$ n \f$-th step. Any passed class must implement
 * the following interface:
 * <pre>
 * public int initialSamples() @ReadOnly;
 * public int nextSamples(int currentSamples) @ReadOnly;
 * </pre>
 * as well as a copy constructor.
 *  
 *  @author Ueli Hofstetter
 *  @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q4_UNIT, reviewers = { "Richard Gomes" }, version = Version.V097)
public class ConvergenceStatistics {

	private static final String INCOMPATIBLE_ARRAY_SIZES      = "incompatible array sizes";

	private final Statistics statistics;
	private final DoublingConvergenceSteps samplingRule;
	private final List<Pair<Integer, Double>> table;
	private int nextSampleSize;

	public ConvergenceStatistics() {
		this(new DoublingConvergenceSteps());
	}

	public ConvergenceStatistics(final DoublingConvergenceSteps rule) {
		this.statistics = new Statistics();
		this.samplingRule = rule;
		this.table = new ArrayList<Pair<Integer, Double>>();
		reset();
	}

	public int initialSamples() {
		return 1;
	}

	public int nextSamples(final int current) {
		return 2 * current + 1;
	}

	public void add(final double value) {
		add(value, 1.0);
	}

	public void add(final double value, final double weight) {
		this.statistics.add(value, weight);
		if (this.statistics.samples() == nextSampleSize) {
			table.add(new Pair<Integer, Double>(statistics.samples(),
					statistics.mean()));
			nextSampleSize = samplingRule.nextSamples(nextSampleSize);
		}
	}

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
	public void addSequence(final double[] datum, final double[] weights) {
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
	public void addSequence(final Array datum, final Array weights) {
        QL.require(datum.size()==weights.size(), INCOMPATIBLE_ARRAY_SIZES);
        for (int i=0; i<datum.size(); i++) {
        	add(datum.get(i), weights.get(i));
        }
	}

	public void reset() {
		statistics.reset();
		nextSampleSize = samplingRule.initialSamples();
		table.clear();
	}

	public List<Pair<Integer, Double>> convergenceTable() {
		return table;
	}

	
	//
	// private inner classes
	//

	private static class DoublingConvergenceSteps {
		public int initialSamples() /*@ReadOnly*/ {
			return 1;
		}

		public int nextSamples(final int current) /*@ReadOnly*/ {
			return 2 * current + 1;
		}
	}

}
