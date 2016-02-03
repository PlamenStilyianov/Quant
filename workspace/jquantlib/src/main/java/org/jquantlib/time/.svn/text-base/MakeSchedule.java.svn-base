/*
 Copyright (C) 2009 Zahid Hussain

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

package org.jquantlib.time;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;

/**
 * Helper class
 * <p>
 * This class provides a more comfortable interface to the argument list of Schedule's constructor. 
 * 
 * @author Zahid Hussain
 */
@QualityAssurance(quality = Quality.Q0_UNFINISHED, version = Version.V097, reviewers = "Richard Gomes")
public class MakeSchedule implements Cloneable {
	private Calendar calendar_;
	private Date effectiveDate_;
	private Date terminationDate_;
	private Period tenor_;
	private BusinessDayConvention convention_;
	private BusinessDayConvention terminationDateConvention_;
	private DateGeneration.Rule rule_;
	private boolean endOfMonth_;
	private Date firstDate_;
	private Date nextToLastDate_;

	public MakeSchedule(final Date effectiveDate, final Date terminationDate,
			final Period tenor, final Calendar calendar,
			final BusinessDayConvention convention) {
		this.calendar_ = calendar;
		this.effectiveDate_ = effectiveDate;
		this.terminationDate_ = terminationDate;
		this.tenor_ = tenor;
		this.convention_ = convention;
		this.terminationDateConvention_ = convention;
		this.rule_ = DateGeneration.Rule.Backward;
		this.endOfMonth_ = false;
		this.firstDate_ = new Date();
		this.nextToLastDate_ = new Date();
	}

	public MakeSchedule withTerminationDateConvention(
			final BusinessDayConvention conv) {
		terminationDateConvention_ = conv;
		return this.clone();
	}

	public MakeSchedule withRule(final DateGeneration.Rule r) {
		rule_ = r;
		return this.clone();

	}

	public MakeSchedule forwards() {
		rule_ = DateGeneration.Rule.Forward;
		return this.clone();
	}

	public MakeSchedule backwards() {
		rule_ = DateGeneration.Rule.Backward;
		return this.clone();

	}

	public MakeSchedule endOfMonth() {
		return endOfMonth(true);

	}

	public MakeSchedule endOfMonth(final boolean flag) {
		endOfMonth_ = flag;
		return this.clone();
	}

	public MakeSchedule withFirstDate(final Date d) {
		firstDate_ = d;
		return this.clone();
	}

	public MakeSchedule withNextToLastDate(final Date d) {
		nextToLastDate_ = d;
		return this.clone();
	}

	/**
	 * MakeSchedule::operator Schedule() const { return Schedule(effectiveDate_,
	 * terminationDate_, tenor_, calendar_, convention_,
	 * terminationDateConvention_, rule_, endOfMonth_, firstDate_,
	 * nextToLastDate_);
	 * 
	 * @return
	 */
	public Schedule schedule() {
		return new Schedule(effectiveDate_, terminationDate_, tenor_,
				calendar_, convention_, terminationDateConvention_, rule_,
				endOfMonth_, firstDate_, nextToLastDate_);
	}

	@Override
	public MakeSchedule clone() {
		final MakeSchedule clone = new MakeSchedule(effectiveDate_,
				terminationDate_, tenor_, calendar_, convention_);
		clone.calendar_ = calendar_;
		clone.effectiveDate_ = effectiveDate_.clone();
		clone.terminationDate_ = terminationDate_.clone();
		clone.tenor_ = tenor_;
		clone.convention_ = convention_;
		clone.terminationDateConvention_ = terminationDateConvention_;
		clone.rule_ = rule_;
		clone.endOfMonth_ = endOfMonth_;
		clone.firstDate_ = firstDate_.clone();
		clone.nextToLastDate_ = nextToLastDate_.clone();

		return clone;
	}
}
