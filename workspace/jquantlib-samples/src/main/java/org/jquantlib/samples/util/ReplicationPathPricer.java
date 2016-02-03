/*
 Copyright (C) 2009 Q.Boiler, Ueli Hofstetter
 
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
package org.jquantlib.samples.util;

import org.jquantlib.instruments.Option;
import org.jquantlib.methods.montecarlo.Path;
import org.jquantlib.methods.montecarlo.PathPricer;

public class ReplicationPathPricer extends PathPricer<Path> {

	ReplicationPathPricer(final Option.Type type,
			double /* @Real @NonNegative */strike, double /*
															 * @Rate
															 * @NonNegative
															 */r,
			double /* @Time @NonNegative */maturity, double /*
															 * @Volatility
															 * @NonNegative
															 */sigma) {
		type_ = type;
		strike_ = strike;
		r_ = r;
		maturity_ = maturity;
		sigma_ = sigma;

		// XXX: These tests can be substituted by [future] JSR-308 annotation
		// @NonNegative
		assert (strike_ > 0.0);
		assert (r_ > 0.0);
		assert (maturity_ > 0.0);
		assert (sigma_ > 0.0);

	}

	// The value() method encapsulates the pricing code

	private Number operator(Path path) {
		if (System.getProperty("EXPERIMENTAL") == null) {
			throw new UnsupportedOperationException("Work in progress");
		}
		return null;
	}

	private Option.Type type_;
	private/* @Real */double strike_;
	private/* @Rate */double r_;
	private/* @Time */double maturity_;
	private/* @Volatility */double sigma_;

	@Override
	public Double op(Path path) {
		if (System.getProperty("EXPERIMENTAL") == null) {
			throw new UnsupportedOperationException("Work in progress");
		}
		return null;
	}

}

