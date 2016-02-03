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

@QualityAssurance(quality = Quality.Q4_UNIT, reviewers = { "Richard Gomes" }, version = Version.V097)
public class DiscrepancyStatistics extends SequenceStatistics {

	private static final String DIMENSION_NOT_ALLOWED = "dimension==1 not allowed";

	private /*@Real*/ double bdiscr_;
	private /*@Real*/ double ddiscr_;

	private /*@Real*/ double adiscr_;
	private /*@Real*/ double cdiscr_;


	// constructor
	public DiscrepancyStatistics(final /*@Size*/ int dimension) {
		super(dimension);
		reset(dimension);
	}

	//--- name 1-dimensional inspectors

	public /*@Real*/ double discrepancy() /*@ReadOnly*/ {
		/*@Size*/ int N = samples();
		
		/*
		 * THIS BLOCK IS COMMENTED OUT AT ORIGINAL QuantLib/C++ SOURCES 
		 * 
		Size i;
		Real double r_ik, r_jk, cdiscr = adiscr = 0.0, temp = 1.0;

		for (i=0; i<N; i++) {
			Real temp = 1.0;
			for (Size k=0; k<dimension_; k++) {
				r_ik = stats_[k].sampleData()[i].first;
				temp *= (1.0 - r_ik*r_ik);
			}
			cdiscr += temp;
		}

		for (i=0; i<N; i++) {
			for (Size j=0; j<N; j++) {
				Real temp = 1.0;
				for (Size k=0; k<dimension_; k++) {
					r_jk = stats_[k].sampleData()[j].first;
					r_ik = stats_[k].sampleData()[i].first;
					temp *= (1.0 - Math.max(r_ik, r_jk));
				}
				adiscr += temp;
			}
		}
		*/
		return Math.sqrt(adiscr_/(N*N)-bdiscr_/N*cdiscr_+ddiscr_);
	}

	public void add(final double[] datum) {
		add(datum, 1.0);
	}

	public void add(final Array datum) {
		add(datum, 1.0);
	}

	public void add(final double[] datum, final /*@Real*/ double weight) {
		super.add(datum, weight);

		/*@Size*/ int k, m, N = samples();
		/*@Real*/ double r_ik, r_jk, temp = 1.0;

		for (k=0; k<dimension_; k++) {
			r_ik = datum[k]; // i=N
			temp *= (1.0 - r_ik*r_ik);
		}
		cdiscr_ += temp;

		
		for (m=0; m<N-1; m++) {
			temp = 1.0;
			for (k=0; k<dimension_; k++) {
				// running i=1..(N-1)
				r_ik = stats[k].data().get(m).first();
				// fixed j=N
				r_jk = datum[k];
				temp *= (1.0 - Math.max(r_ik, r_jk));
			}
			adiscr_ += temp;

			temp = 1.0;
			for (k=0; k<dimension_; k++) {
				// fixed i=N
				r_ik = datum[k];
				// running j=1..(N-1)
				r_jk = stats[k].data().get(m).first();
				temp *= (1.0 - Math.max(r_ik, r_jk));
			}
			adiscr_ += temp;
		}
		temp = 1.0;
		for (k=0; k<dimension_; k++) {
			// fixed i=N, j=N
			r_ik = r_jk = datum[k];
			temp *= (1.0 - Math.max(r_ik, r_jk));
		}
		adiscr_ += temp;
	}

	public void add(final Array datum, final /*@Real*/ double weight) {
		super.add(datum, weight);

		/*@Size*/ int k, m, N = samples();
		/*@Real*/ double r_ik, r_jk, temp = 1.0;

		for (k=0; k<dimension_; k++) {
			r_ik = datum.$[datum._(k)]; // i=N
			temp *= (1.0 - r_ik*r_ik);
		}
		cdiscr_ += temp;

		
		for (m=0; m<N-1; m++) {
			temp = 1.0;
			for (k=0; k<dimension_; k++) {
				// running i=1..(N-1)
				r_ik = stats[k].data().get(m).first();
				// fixed j=N
				r_jk = datum.$[datum._(k)];
				temp *= (1.0 - Math.max(r_ik, r_jk));
			}
			adiscr_ += temp;

			temp = 1.0;
			for (k=0; k<dimension_; k++) {
				// fixed i=N
				r_ik = datum.$[datum._(k)];
				// running j=1..(N-1)
				r_jk = stats[k].data().get(m).first();
				temp *= (1.0 - Math.max(r_ik, r_jk));
			}
			adiscr_ += temp;
		}
		temp = 1.0;
		for (k=0; k<dimension_; k++) {
			// fixed i=N, j=N
			r_ik = r_jk = datum.$[datum._(k)];
			temp *= (1.0 - Math.max(r_ik, r_jk));
		}
		adiscr_ += temp;
	}

	public void reset() {
		reset(0);
	}
	
	public void reset(/*@Size*/ int dimension) {
		if (dimension == 0)		   // if no size given,
			dimension = dimension_;   // keep the current one
		QL.require(dimension != 1, DIMENSION_NOT_ALLOWED);

		super.reset(dimension);

		adiscr_ = 0.0;
		bdiscr_ = 1.0/Math.pow(2.0, dimension-1);
		cdiscr_ = 0.0;
		ddiscr_ = 1.0/Math.pow(3.0, dimension);
	}

}
