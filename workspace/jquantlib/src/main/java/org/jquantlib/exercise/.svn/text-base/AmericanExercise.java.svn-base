/*
 Copyright (C) 2007 Richard Gomes

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
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
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

package org.jquantlib.exercise;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Date;

/**
 * American exercise
 * <p>
 * An American option can be exercised at any time between two
 * predefined dates. In case the first date is omitted, the
 * option can be exercised at any time before the expiry date.
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Richard Gomes" })
public class AmericanExercise extends EarlyExercise {

	/**
	 * Constructs an AmericanExercise with two limiting dates define and a default payoff
	 * equals <code>false</code>, which means there's no payoff at exercise Date.
	 *
	 * @param earliestDate
	 * @param latestDate
	 */
	public AmericanExercise(final Date earliestDate, final Date latestDate) {
		this(earliestDate, latestDate, false);
	}

	/**
	 * Constructs an AmericanExercise with two limiting dates and a defined payoff.
	 *
	 * @param earliestDate is the earliest Date of exercise
	 * @param latestDate is the latest Date of exercise
	 * @param payoffAtExpiry is <code>true</code> if a payoff is expected to happen on exercise date
	 */
	public AmericanExercise(final Date earliestDate, final Date latestDate, final boolean payoffAtExpiry) {
		super(Exercise.Type.American, payoffAtExpiry);
		QL.require(earliestDate.le(latestDate) , "earliest > latest exercise date");  // TODO: message
		super.dates.add(earliestDate.clone());
		super.dates.add(latestDate.clone());
    }

}
