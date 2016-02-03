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

package org.jquantlib.testsuite.patterns;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.util.PolymorphicVisitable;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;
import org.junit.Test;

/**
 *
 * @author Richard Gomes
 */
public class VisitorTest {

	public VisitorTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}

	@Test
	public void testPolymorphicVisitorPattern() {

		final NumberPolymorphicVisitable  numberPolymorphicVisitable  = new NumberPolymorphicVisitable();
		final DoublePolymorphicVisitable  doublePolymorphicVisitable  = new DoublePolymorphicVisitable();
		final IntegerPolymorphicVisitable integerPolymorphicVisitable = new IntegerPolymorphicVisitable();

		final NumberPolymorphicVisitor  numberPolymorphicVisitor  = new NumberPolymorphicVisitor();
		final DoublePolymorphicVisitor  doublePolymorphicVisitor  = new DoublePolymorphicVisitor();
		final IntegerPolymorphicVisitor integerPolymorphicVisitor = new IntegerPolymorphicVisitor();

		// these tests must pass
		numberPolymorphicVisitable.accept(numberPolymorphicVisitor);
		doublePolymorphicVisitable.accept(numberPolymorphicVisitor);
		doublePolymorphicVisitable.accept(doublePolymorphicVisitor);
		integerPolymorphicVisitable.accept(numberPolymorphicVisitor);
		integerPolymorphicVisitable.accept(integerPolymorphicVisitor);

		// the following must fail
		try {
			numberPolymorphicVisitable.accept(doublePolymorphicVisitor);
			fail("numberPolymorphicVisitable.accept(doublePolymorphicVisitor) should fail!");
		} catch (final Exception e) {
			// OK, as expected
		}

		// the following must fail
		try {
			numberPolymorphicVisitable.accept(integerPolymorphicVisitor);
			fail("numberPolymorphicVisitable.accept(integerPolymorphicVisitor) should fail");
		} catch (final Exception e) {
			// OK, as expected
		}

		// the following must fail
		try {
			doublePolymorphicVisitable.accept(integerPolymorphicVisitor);
			fail("doublePolymorphicVisitable.accept(integerPolymorphicVisitor) should fail");
		} catch (final Exception e) {
			// OK, as expected
		}

		// the following must fail
		try {
			integerPolymorphicVisitable.accept(doublePolymorphicVisitor);
			fail("integerPolymorphicVisitable.accept(doublePolymorphicVisitor) should fail");
		} catch (final Exception e) {
			// OK, as expected
		}

	}

	private class NumberPolymorphicVisitable implements PolymorphicVisitable {
		@Override
		public void accept(final PolymorphicVisitor pv) {
			final Visitor<Number> v = (pv!=null) ? pv.visitor(Number.class) : null;
			if (v!=null)
				v.visit(1.0);
			else
				throw new IllegalArgumentException("not a Number visitor");
		}
	}


	private class DoublePolymorphicVisitable extends NumberPolymorphicVisitable  /* implements PolymorphicVisitable<Double> */ {
		private final double data = 5.6;

		@Override
		public void accept(final PolymorphicVisitor pv) {
			final Visitor<Double> v = (pv!=null) ? pv.visitor(Double.class) : null;
			if (v!=null)
				v.visit(data);
			else
				super.accept(pv);
		}
	}


	private class IntegerPolymorphicVisitable extends NumberPolymorphicVisitable  /* implements PolymorphicVisitable<Double> */ {
		private final int data = 3456;

		@Override
		public void accept(final PolymorphicVisitor pv) {
			final Visitor<Integer> v = (pv!=null) ? pv.visitor(Integer.class) : null;
			if (v!=null)
				v.visit(data);
			else
				super.accept(pv);
		}
	}



	//
	// declare PolymorphicVisitors and corresponding Visitors
	//

	private static class NumberPolymorphicVisitor implements PolymorphicVisitor {

		@Override
		public <Number> Visitor<Number> visitor(final Class<? extends Number> klass) {
			return (Visitor<Number>) ((klass==java.lang.Number.class) ? numberVisitor : null);
		}

		//
		// composition pattern to an inner Visitor<Number>
		//

		private final NumberVisitor numberVisitor = new NumberVisitor();

		private static class NumberVisitor implements Visitor<Number> {
			@Override
			public void visit(final Number o) {
				QL.info("Number :: "+o);
			}
		}
	}


	private static class DoublePolymorphicVisitor extends NumberPolymorphicVisitor {

		@Override
		public <Number> Visitor<Number> visitor(final Class<? extends Number> klass) {
			return (Visitor<Number>) ((klass==Double.class) ? doubleVisitor : null);
		}

		//
		// composition pattern to an inner Visitor<Double>
		//

		private final DoubleVisitor doubleVisitor = new DoubleVisitor();

		private static class DoubleVisitor implements Visitor<Number> {
			@Override
			public void visit(final Number o) {
				final double obj = (Double)o;
				QL.info("Double :: "+obj);
			}
		}
	}

	private static class IntegerPolymorphicVisitor extends NumberPolymorphicVisitor {

		@Override
		public <Number> Visitor<Number> visitor(final Class<? extends Number> klass) {
			return (Visitor<Number>) ((klass==Integer.class) ? integerVisitor : null);
		}

		//
		// composition pattern to an inner Visitor<Double>
		//

		private final IntegerVisitor integerVisitor = new IntegerVisitor();

		private static class IntegerVisitor implements Visitor<Number> {
			@Override
			public void visit(final Number o) {
				final Integer obj = (Integer)o;
				QL.info("Integer :: "+obj);
			}
		}
	}

}
