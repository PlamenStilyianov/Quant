/*
 Copyright (C) 2010 Zahid Hussain

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
package org.jquantlib.testsuite.instruments;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.SavedSettings;
import org.jquantlib.Settings;
import org.jquantlib.cashflow.BlackIborCouponPricer;
import org.jquantlib.cashflow.IborCouponPricer;
import org.jquantlib.cashflow.PricerSetter;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.AmericanExercise;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.indexes.Euribor1Y;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.instruments.CallabilitySchedule;
import org.jquantlib.instruments.DividendSchedule;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.instruments.bonds.ConvertibleFixedCouponBond;
import org.jquantlib.instruments.bonds.ConvertibleFloatingRateBond;
import org.jquantlib.instruments.bonds.ConvertibleZeroCouponBond;
import org.jquantlib.instruments.bonds.FixedRateBond;
import org.jquantlib.instruments.bonds.FloatingRateBond;
import org.jquantlib.instruments.bonds.ZeroCouponBond;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.lattices.CoxRossRubinstein;
import org.jquantlib.pricingengines.BinomialConvertibleEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.bond.DiscountingBondEngine;
import org.jquantlib.pricingengines.vanilla.BinomialVanillaEngine;
import org.jquantlib.processes.BlackScholesMertonProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;
import org.jquantlib.termstructures.yieldcurves.ForwardSpreadedTermStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.MakeSchedule;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;
import org.junit.Test;

/**
 * @author Zahid Hussain
 */

public class ConvertibleBondTest {

    public ConvertibleBondTest() {
		QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
	}

	private class CommonVars {
		// global data
		Date today, issueDate, maturityDate;
		Calendar calendar;
		DayCounter dayCounter;
		Frequency frequency;
		int settlementDays;

		RelinkableHandle<Quote> underlying = new RelinkableHandle<Quote>();
		RelinkableHandle<YieldTermStructure> dividendYield = new RelinkableHandle<YieldTermStructure>();
		RelinkableHandle<YieldTermStructure> riskFreeRate = new RelinkableHandle<YieldTermStructure>();
		RelinkableHandle<BlackVolTermStructure> volatility = new RelinkableHandle<BlackVolTermStructure>();
		BlackScholesMertonProcess process;

		RelinkableHandle<Quote> creditSpread = new RelinkableHandle<Quote>();

		CallabilitySchedule no_callability = new CallabilitySchedule();
		DividendSchedule no_dividends = new DividendSchedule();

		double faceAmount, redemption, conversionRatio;

		// cleanup
		SavedSettings backup = new SavedSettings();

		// setup
		CommonVars() {
			calendar = new Target();

			today = calendar.adjust(Date.todaysDate());
			new Settings().setEvaluationDate(today);

			dayCounter = new Actual360();
			frequency = Frequency.Annual;
			settlementDays = 3;

			issueDate = calendar.advance(today, 2, TimeUnit.Days);
			maturityDate = calendar.advance(issueDate, 10, TimeUnit.Years);
			// reset to avoid inconsistencies as the schedule is backwards
			issueDate = calendar.advance(maturityDate, -10, TimeUnit.Years);

			underlying.linkTo(new SimpleQuote(50.0));
			dividendYield.linkTo(Utilities.flatRate(today, 0.02, dayCounter));
			riskFreeRate.linkTo(Utilities.flatRate(today, 0.05, dayCounter));
			volatility.linkTo(Utilities.flatVol(today, 0.15, dayCounter));

			process = new BlackScholesMertonProcess(underlying, dividendYield,
					riskFreeRate, volatility);

			creditSpread.linkTo(new SimpleQuote(0.005));

			// it fails with 1000000
			// faceAmount = 1000000.0;
			faceAmount = 100.0;
			redemption = 100.0;
			conversionRatio = redemption / underlying.currentLink().value();
		}
	}

	@Test
	public void testBond() {
		System.setProperty("EXPERIMENTAL", "true");
		/*
		 * when deeply out-of-the-money, the value of the convertible bond
		 * should equal that of the underlying plain-vanilla bond.
		 */

		QL.info("Testing out-of-the-money convertible bonds against vanilla bonds...");

		final CommonVars vars = new CommonVars();

		vars.conversionRatio = 1.0e-16;

		final Exercise euExercise = new EuropeanExercise(vars.maturityDate);
		final Exercise amExercise = new AmericanExercise(vars.issueDate,
				vars.maturityDate);

		final int timeSteps = 1001;
		final PricingEngine engine = new BinomialConvertibleEngine<CoxRossRubinstein>(
				CoxRossRubinstein.class,
				vars.process, timeSteps);

		final Handle<YieldTermStructure> discountCurve = new Handle<YieldTermStructure>(
				new ForwardSpreadedTermStructure(vars.riskFreeRate,
						vars.creditSpread));

		// zero-coupon
		Schedule schedule = new MakeSchedule(vars.issueDate, vars.maturityDate,
				new Period(Frequency.Once), vars.calendar,
				BusinessDayConvention.Following).backwards().schedule();

		final ConvertibleZeroCouponBond euZero = new ConvertibleZeroCouponBond(
				euExercise, vars.conversionRatio, vars.no_dividends,
				vars.no_callability, vars.creditSpread, vars.issueDate,
				vars.settlementDays, vars.dayCounter, schedule, vars.redemption);
		euZero.setPricingEngine(engine);

		final ConvertibleZeroCouponBond amZero = new ConvertibleZeroCouponBond(
				amExercise, vars.conversionRatio, vars.no_dividends,
				vars.no_callability, vars.creditSpread, vars.issueDate,
				vars.settlementDays, vars.dayCounter, schedule, vars.redemption);
		amZero.setPricingEngine(engine);

		final ZeroCouponBond zero = new ZeroCouponBond(vars.settlementDays,
				vars.calendar, 100.0, vars.maturityDate,
				BusinessDayConvention.Following, vars.redemption,
				vars.issueDate);

		final PricingEngine bondEngine = new DiscountingBondEngine(discountCurve);
		zero.setPricingEngine(bondEngine);

		double tolerance = 1.0e-2 * (vars.faceAmount / 100.0);

		double error = Math.abs(euZero.NPV() - zero.settlementValue());
		if (error > tolerance) {
			fail("failed to reproduce zero-coupon bond price:"
					+ "\n    calculated: " + euZero.NPV()
					+ "\n    expected:   " + zero.settlementValue()
					+ "\n    error:      " + error);
		}

		error = Math.abs(amZero.NPV() - zero.settlementValue());
		if (error > tolerance) {
			fail("failed to reproduce zero-coupon bond price:"
					+ "\n    calculated: " + amZero.NPV()
					+ "\n    expected:   " + zero.settlementValue()
					+ "\n    error:      " + error);
		}

		// coupon

		final double[] coupons = { 0.05 };

		schedule = new MakeSchedule(vars.issueDate, vars.maturityDate,
				new Period(vars.frequency), vars.calendar,
				BusinessDayConvention.Following).backwards().schedule();

		final ConvertibleFixedCouponBond euFixed = new ConvertibleFixedCouponBond(
				euExercise, vars.conversionRatio, vars.no_dividends,
				vars.no_callability, vars.creditSpread, vars.issueDate,
				vars.settlementDays, coupons, vars.dayCounter, schedule,
				vars.redemption);
		euFixed.setPricingEngine(engine);

		final ConvertibleFixedCouponBond amFixed = new ConvertibleFixedCouponBond(
				amExercise, vars.conversionRatio, vars.no_dividends,
				vars.no_callability, vars.creditSpread, vars.issueDate,
				vars.settlementDays, coupons, vars.dayCounter, schedule,
				vars.redemption);
		amFixed.setPricingEngine(engine);

		final FixedRateBond fixed = new FixedRateBond(vars.settlementDays,
				vars.faceAmount, schedule, coupons, vars.dayCounter,
				BusinessDayConvention.Following, vars.redemption,
				vars.issueDate);

		fixed.setPricingEngine(bondEngine);

		tolerance = 2.0e-2 * (vars.faceAmount / 100.0);

		error = Math.abs(euFixed.NPV() - fixed.settlementValue());
		if (error > tolerance) {
			fail("failed to reproduce fixed-coupon bond price:"
					+ "\n    calculated: " + euFixed.NPV()
					+ "\n    expected:   " + fixed.settlementValue()
					+ "\n    error:      " + error);
		}

		error = Math.abs(amFixed.NPV() - fixed.settlementValue());
		if (error > tolerance) {
			fail("failed to reproduce fixed-coupon bond price:"
					+ "\n    calculated: " + amFixed.NPV()
					+ "\n    expected:   " + fixed.settlementValue()
					+ "\n    error:      " + error);
		}

		// floating-rate

		final IborIndex index = new Euribor1Y(discountCurve);
		final int fixingDays = 2;
		final Array gearings = new Array(1).fill(1.0);
		final Array spreadsArr = new Array(0);
		final double[] spreads = { 0 };

		final ConvertibleFloatingRateBond euFloating = new ConvertibleFloatingRateBond(
				euExercise, vars.conversionRatio, vars.no_dividends,
				vars.no_callability, vars.creditSpread, vars.issueDate,
				vars.settlementDays, index, fixingDays, spreads,
				vars.dayCounter, schedule, vars.redemption);
		euFloating.setPricingEngine(engine);

		final ConvertibleFloatingRateBond amFloating = new ConvertibleFloatingRateBond(
				amExercise, vars.conversionRatio, vars.no_dividends,
				vars.no_callability, vars.creditSpread, vars.issueDate,
				vars.settlementDays, index, fixingDays, spreads,
				vars.dayCounter, schedule, vars.redemption);
		amFloating.setPricingEngine(engine);

		final IborCouponPricer pricer = new BlackIborCouponPricer(
				new Handle<OptionletVolatilityStructure>());

		final Schedule floatSchedule = new Schedule(vars.issueDate,
				vars.maturityDate, new Period(vars.frequency), vars.calendar,
				BusinessDayConvention.Following,
				BusinessDayConvention.Following, DateGeneration.Rule.Backward,
				false);

		final FloatingRateBond floating = new FloatingRateBond(vars.settlementDays,
				vars.faceAmount, floatSchedule, index, vars.dayCounter,
				BusinessDayConvention.Following, fixingDays, gearings,
				spreadsArr, new Array(0), new Array(0), false, vars.redemption,
				vars.issueDate);

		floating.setPricingEngine(bondEngine);
		PricerSetter.setCouponPricer(floating.cashflows(), pricer);

		tolerance = 2.0e-2 * (vars.faceAmount / 100.0);

		error = Math.abs(euFloating.NPV() - floating.settlementValue());
		if (error > tolerance) {
			fail("failed to reproduce floating-rate bond price:"
					+ "\n    calculated: " + euFloating.NPV()
					+ "\n    expected:   " + floating.settlementValue()
					+ "\n    error:      " + error);
		}

		error = Math.abs(amFloating.NPV() - floating.settlementValue());
		if (error > tolerance) {
			fail("failed to reproduce floating-rate bond price:"
					+ "\n    calculated: " + amFloating.NPV()
					+ "\n    expected:   " + floating.settlementValue()
					+ "\n    error:      " + error);
		}
	}

	@Test
	public void testOption() {
		System.setProperty("EXPERIMENTAL", "true");

		/*
		 * a zero-coupon convertible bond with no credit spread is equivalent to
		 * a call option.
		 */

		QL.info("Testing zero-coupon convertible bonds against vanilla option...");

		final CommonVars vars = new CommonVars();

		final Exercise euExercise = new EuropeanExercise(vars.maturityDate);

		vars.settlementDays = 0;

		final int timeSteps = 1001;
		final PricingEngine engine = new BinomialConvertibleEngine<CoxRossRubinstein>(CoxRossRubinstein.class, vars.process, timeSteps);
		final PricingEngine vanillaEngine = new BinomialVanillaEngine<CoxRossRubinstein>(CoxRossRubinstein.class, vars.process, timeSteps);

		vars.creditSpread.linkTo(new SimpleQuote(0.0));

		final double conversionStrike = vars.redemption / vars.conversionRatio;
		final StrikedTypePayoff payoff = new PlainVanillaPayoff(Option.Type.Call,
				conversionStrike);

		final Schedule schedule = new MakeSchedule(vars.issueDate, vars.maturityDate,
				new Period(Frequency.Once), vars.calendar,
				BusinessDayConvention.Following).backwards().schedule();

		final ConvertibleZeroCouponBond euZero = new ConvertibleZeroCouponBond(
				euExercise, vars.conversionRatio, vars.no_dividends,
				vars.no_callability, vars.creditSpread, vars.issueDate,
				vars.settlementDays, vars.dayCounter, schedule, vars.redemption);
		euZero.setPricingEngine(engine);

		final VanillaOption euOption = new VanillaOption(payoff, euExercise);
		euOption.setPricingEngine(vanillaEngine);

		final double tolerance = 5.0e-2 * (vars.faceAmount / 100.0);

		final double expected = vars.faceAmount
				/ 100.0
				* (vars.redemption
						* vars.riskFreeRate.currentLink().discount(
								vars.maturityDate) + vars.conversionRatio
						* euOption.NPV());
		final double error = Math.abs(euZero.NPV() - expected);
		if (error > tolerance) {
			fail("failed to reproduce plain-option price:"
					+ "\n    calculated: " + euZero.NPV()
					+ "\n    expected:   " + expected + "\n    error:      "
					+ error);
		}
	}
}
