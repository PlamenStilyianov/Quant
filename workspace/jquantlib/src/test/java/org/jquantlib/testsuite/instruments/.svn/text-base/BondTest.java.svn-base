/*
 Copyright (C) 2008 Richard Gomes
 Copyright (C) 2009 John Nichol

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
 Copyright (C) 2003, 2004 Ferdinando Ametrano
 Copyright (C) 2005 StatPro Italia srl
 Copyright (C) 2005 Joseph Wang

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

/**
 *
 * Ported from
 * <ul>
 * <li>test-suite/americanoption.cpp</li>
 * </ul>
 *
 * @author <Richard Gomes>
 *
 */

package org.jquantlib.testsuite.instruments;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.SavedSettings;
import org.jquantlib.Settings;
import org.jquantlib.cashflow.BlackIborCouponPricer;
import org.jquantlib.cashflow.FixedRateLeg;
import org.jquantlib.cashflow.IborCouponPricer;
import org.jquantlib.cashflow.Leg;
import org.jquantlib.cashflow.PricerSetter;
import org.jquantlib.cashflow.SimpleCashFlow;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.ActualActual;
import org.jquantlib.daycounters.Business252;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.daycounters.Thirty360;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.indexes.ibor.USDLibor;
import org.jquantlib.instruments.Bond;
import org.jquantlib.instruments.bonds.FixedRateBond;
import org.jquantlib.instruments.bonds.FloatingRateBond;
import org.jquantlib.instruments.bonds.ZeroCouponBond;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.bond.DiscountingBondEngine;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.DateGeneration.Rule;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Brazil;
import org.jquantlib.time.calendars.NullCalendar;
import org.jquantlib.time.calendars.Target;
import org.jquantlib.time.calendars.UnitedStates;
import org.junit.Ignore;
import org.junit.Test;

public class BondTest {
    private class CommonVars {
        // common data
        Calendar calendar;
        Date today;
        double /*Real*/ faceAmount;

        // cleanup
        SavedSettings backup = new SavedSettings();

        // setup
        public CommonVars() {
            calendar = new Target();
            today = calendar.adjust(Date.todaysDate());
            new Settings().setEvaluationDate(today);
            faceAmount = 1000000.0;
        }
    }

    public BondTest() {
		QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
	}


	@Ignore @Test
	//FIXME: http://bugs.jquantlib.org/view.php?id=472
	public void testYield() {
		QL.info("Testing consistency of bond price/yield calculation....");

        final Calendar calendar = new org.jquantlib.time.calendars.Target();
        final Date today = calendar.adjust(Date.todaysDate());
        final double faceAmount = 1000000.0;

		final double tolerance = 1.0e-7;
		final int maxEvaluations = 100;

		final int issueMonths[] = { -24, -18, -12, -6, 0, 6, 12, 18, 24 };
		final int lengths[] = { 3, 5, 10, 15, 20 };
		final int settlementDays = 3;
		final double coupons[] = { 0.02, 0.05, 0.08 };
		final Frequency frequencies[] = { Frequency.Semiannual, Frequency.Annual };
		final DayCounter bondDayCount = new Thirty360();
		final BusinessDayConvention accrualConvention = BusinessDayConvention.Unadjusted;
		final BusinessDayConvention paymentConvention = BusinessDayConvention.ModifiedFollowing;
		final double redemption = 100.0;

		final double yields[] = { 0.03, 0.04, 0.05, 0.06, 0.07 };
		final Compounding compounding[] = { Compounding.Compounded, Compounding.Continuous };

		for (int i = 0; i < (issueMonths).length; i++) {
			for (int j = 0; j < (lengths).length; j++) {
				for (int k = 0; k < (coupons).length; k++) {
					for (int l = 0; l < (frequencies).length; l++) {
						for (int n = 0; n < (compounding).length; n++) {
							final Date dated = calendar.advance(today, issueMonths[i], TimeUnit.Months);
							final Date issue = dated;
							final Date maturity = calendar.advance(issue, lengths[j], TimeUnit.Years);

							final Schedule sch = new Schedule(
							        dated,
									maturity,
									new Period(frequencies[l]),
									calendar,
									accrualConvention,
									accrualConvention,
									DateGeneration.Rule.Backward,
									false,
									new Date(),
									new Date());

							final FixedRateBond bond = new FixedRateBond(
							        settlementDays,
							        faceAmount,
							        sch,
									new double[] { coupons[k] }, bondDayCount, paymentConvention, redemption, issue);

							for (int m = 0; m < (yields).length; m++) {
								final double price = bond.cleanPrice(yields[m], bondDayCount, compounding[n], frequencies[l]);
								final double calculated = bond.yield(
								        price,
								        bondDayCount,
								        compounding[n],
								        frequencies[l],
								        new Date(),
								        tolerance,
								        maxEvaluations);

								if (Math.abs(yields[m] - calculated) > tolerance) {
									// the difference might not matter
									final double price2 = bond.cleanPrice(calculated, bondDayCount, compounding[n], frequencies[l]);
									if (Math.abs(price - price2) / price > tolerance) {
			                            fail(
										        "yield recalculation failed:\n" + "    issue:     " + issue + "\n"
												+ "    maturity:  " + maturity + "\n" + "    coupon:    " + coupons[k] + "\n"
												+ "    frequency: " + frequencies[l] + "\n\n" + "    yield:  " + yields[m] + " "
												+ (compounding[n] == Compounding.Continuous ? "compounded" : "continuous") + "\n"
												+ "    price:  " + price + "\n" + "    yield': " + (calculated) + "\n"
												+ "    price': " + price2);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Test
	public void testTheoretical() {
		QL.info("Testing theoretical bond price/yield calculation...");

        final Calendar calendar = new org.jquantlib.time.calendars.Target();
        final Date today = calendar.adjust(Date.todaysDate());
        final double faceAmount = 1000000.0;

		final double tolerance = 1.0e-7;
		final int maxEvaluations = 100;

		final int lengths[] = { 3, 5, 10, 15, 20 };
		final int settlementDays = 3;
		final double coupons[] = { 0.02, 0.05, 0.08 };
		final Frequency frequencies[] = { Frequency.Semiannual, Frequency.Annual };
		final DayCounter bondDayCount = new Actual360();
		final BusinessDayConvention accrualConvention = BusinessDayConvention.Unadjusted;
		final BusinessDayConvention paymentConvention = BusinessDayConvention.ModifiedFollowing;
		final double redemption = 100.0;

		final double yields[] = { 0.03, 0.04, 0.05, 0.06, 0.07 };

		for (final int length : lengths) {
			for (final double coupon : coupons) {
				for (final Frequency frequency : frequencies) {

					final Date dated = today;
					final Date issue = dated;
					final Date maturity = calendar.advance(issue, length, TimeUnit.Years);

					final SimpleQuote rate = new SimpleQuote(0.0);
					final Handle<YieldTermStructure> discountCurve = new Handle<YieldTermStructure>(Utilities.flatRate(today, rate, bondDayCount));

					final Schedule sch = new Schedule(
					        dated, maturity,
							new Period(frequency), calendar,
							accrualConvention, accrualConvention,
							Rule.Backward, false);

					final FixedRateBond bond = new FixedRateBond(
					        settlementDays, faceAmount, sch,
							new double[] { coupon },
							bondDayCount, paymentConvention,
							redemption, issue);

					final PricingEngine bondEngine = new DiscountingBondEngine(discountCurve);
					bond.setPricingEngine(bondEngine);

					for (final double yield : yields) {

						rate.setValue(yield);

						final double price = bond.cleanPrice(yield, bondDayCount, Compounding.Continuous, frequency);
						final double calculatedPrice = bond.cleanPrice();

						if (Math.abs(price-calculatedPrice) > tolerance) {
							fail(
									"price calculation failed:"
									+ "\n    issue:     " + issue
									+ "\n    maturity:  " + maturity
									+ "\n    coupon:    " + coupon
									+ "\n    frequency: " + frequency + "\n"
									+ "\n    yield:  " + yield
									+ "\n    expected:    " + price
									+ "\n    calculated': " + calculatedPrice
									+ "\n    error':      " + (price-calculatedPrice));
						}

						final double calculatedYield = bond.yield(
								bondDayCount, Compounding.Continuous, frequency,
								tolerance, maxEvaluations);
						if (Math.abs(yield-calculatedYield) > tolerance) {
                            fail(
									"yield calculation failed:"
									+ "\n    issue:     " + issue
									+ "\n    maturity:  " + maturity
									+ "\n    coupon:    " + coupon
									+ "\n    frequency: " + frequency + "\n"
									+ "\n    yield:  " + yield
									+ "\n    price:    " + price
									+ "\n    yield': " + calculatedYield);
						}
					}
				}
			}
		}
	}

	@Test
	public void testCached() {

		QL.info("Testing bond price/yield calculation against cached values...");

        //final Calendar calendar = new Target();
        // final Date today = calendar.adjust(Date.todaysDate());
        // final Date today = calendar.adjust(new Date(6,Month.June,2007));
        final Date today = new Date(22,Month.November,2004);
        final Settings settings = new Settings();
        settings.setEvaluationDate(today);

        final double faceAmount = 1000000.0;

        // with implicit settlement calculation:

	    final Calendar bondCalendar = new NullCalendar();
	    final DayCounter bondDayCount = new ActualActual(ActualActual.Convention.ISMA);
	    final int settlementDays = 1;

		final Handle<YieldTermStructure> discountCurve = new Handle<YieldTermStructure>(Utilities.flatRate(today, 0.03, new Actual360()));

	    // actual market values from the evaluation date

	    final Frequency freq = Frequency.Semiannual;
	    final Schedule sch1 = new Schedule(new Date(31, Month.October, 2004),
	                  new Date(31, Month.October, 2006), new Period(freq), bondCalendar,
	                  BusinessDayConvention.Unadjusted, BusinessDayConvention.Unadjusted, DateGeneration.Rule.Backward, false);

	    final FixedRateBond bond1 = new FixedRateBond(settlementDays, faceAmount, sch1,
	                        new double[] {0.025},
	                        bondDayCount, BusinessDayConvention.ModifiedFollowing,
	                        100.0, new Date(1, Month.November, 2004));

		final PricingEngine bondEngine = new DiscountingBondEngine(discountCurve);

	    bond1.setPricingEngine(bondEngine);

	    final double marketPrice1 = 99.203125;
	    final double marketYield1 = 0.02925;

	    final Schedule sch2 = new Schedule(new Date(15, Month.November, 2004),
	    		new Date(15, Month.November, 2009), new Period(freq), bondCalendar,
	    		BusinessDayConvention.Unadjusted, BusinessDayConvention.Unadjusted, DateGeneration.Rule.Backward, false);

	    final FixedRateBond bond2 = new FixedRateBond(settlementDays, faceAmount, sch2,
	                        new double [] {0.035},
	                        bondDayCount, BusinessDayConvention.ModifiedFollowing,
	                        100.0, new Date(15, Month.November, 2004));

	    bond2.setPricingEngine(bondEngine);

	    final double marketPrice2 = 99.6875;
	    final double marketYield2 = 0.03569;

	    // calculated values

	    final double cachedPrice1a = 99.204505, cachedPrice2a = 99.687192;
	    final double cachedPrice1b = 98.943393, cachedPrice2b = 101.986794;
	    final double cachedYield1a = 0.029257,  cachedYield2a = 0.035689;
	    final double cachedYield1b = 0.029045,  cachedYield2b = 0.035375;
	    final double cachedYield1c = 0.030423,  cachedYield2c = 0.030432;

	    // check
	    final double tolerance = 1.0e-6;
	    double price, yield;

	    price = bond1.cleanPrice(marketYield1,
	                             bondDayCount, Compounding.Compounded, freq);
	    if (Math.abs(price-cachedPrice1a) > tolerance) {
	    	fail("failed to reproduce cached price:"
	                   + "\n    calculated: " + price
	                   + "\n    expected:   " + cachedPrice1a
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (price-cachedPrice1a));
	    }

	    price = bond1.cleanPrice();
	    if (Math.abs(price-cachedPrice1b) > tolerance) {
	    	fail("failed to reproduce cached price:"
	                   + "\n    calculated: " + price
	                   + "\n    expected:   " + cachedPrice1b
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (price-cachedPrice1b));
	    }

	    yield = bond1.yield(marketPrice1, bondDayCount, Compounding.Compounded, freq);
	    if (Math.abs(yield-cachedYield1a) > tolerance) {
	    	fail("failed to reproduce cached compounded yield:"
	                   + "\n    calculated: " + yield
	                   + "\n    expected:   " + cachedYield1a
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (yield-cachedYield1a));
	    }

	    yield = bond1.yield(marketPrice1, bondDayCount, Compounding.Continuous, freq);
	    if (Math.abs(yield-cachedYield1b) > tolerance) {
	    	fail("failed to reproduce cached continuous yield:"
	                   + "\n    calculated: " + yield
	                   + "\n    expected:   " + cachedYield1b
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (yield-cachedYield1b));
	    }

	    yield = bond1.yield(bondDayCount, Compounding.Continuous, freq);
	    if (Math.abs(yield-cachedYield1c) > tolerance) {
	    	fail("failed to reproduce cached continuous yield:"
	                   + "\n    calculated: " + yield
	                   + "\n    expected:   " + cachedYield1c
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (yield-cachedYield1c));
	    }


	    price = bond2.cleanPrice(marketYield2, bondDayCount, Compounding.Compounded, freq);
	    if (Math.abs(price-cachedPrice2a) > tolerance) {
	    	fail("failed to reproduce cached price:"
	                   + "\n    calculated: " + price
	                   + "\n    expected:   " + cachedPrice2a
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (price-cachedPrice2a));
	    }

	    price = bond2.cleanPrice();
	    if (Math.abs(price-cachedPrice2b) > tolerance) {
	    	fail("failed to reproduce cached price:"
	                   + "\n    calculated: " + price
	                   + "\n    expected:   " + cachedPrice2b
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (price-cachedPrice2b));
	    }

	    yield = bond2.yield(marketPrice2, bondDayCount, Compounding.Compounded, freq);
	    if (Math.abs(yield-cachedYield2a) > tolerance) {
	    	fail("failed to reproduce cached compounded yield:"
	                   + "\n    calculated: " + yield
	                   + "\n    expected:   " + cachedYield2a
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (yield-cachedYield2a));
	    }

	    yield = bond2.yield(marketPrice2, bondDayCount, Compounding.Continuous, freq);
	    if (Math.abs(yield-cachedYield2b) > tolerance) {
	    	fail("failed to reproduce cached continuous yield:"
	                   + "\n    calculated: " + yield
	                   + "\n    expected:   " + cachedYield2b
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (yield-cachedYield2b));
	    }

	    yield = bond2.yield(bondDayCount, Compounding.Continuous, freq);
	    if (Math.abs(yield-cachedYield2c) > tolerance) {
	    	fail("failed to reproduce cached continuous yield:"
	                   + "\n    calculated: " + yield
	                   + "\n    expected:   " + cachedYield2c
	                   + "\n    tolerance:  " + tolerance
	                   + "\n    error:      " + (yield-cachedYield2c));
	    }

	    // with explicit settlement date:

	    final Schedule sch3 = new Schedule(new Date(30,Month.November,2004),
	                  new Date(30,Month.November,2006), new Period(freq),
	                  new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
	                  BusinessDayConvention.Unadjusted, BusinessDayConvention.Unadjusted, DateGeneration.Rule.Backward, false);

	    final FixedRateBond bond3 = new FixedRateBond(settlementDays, faceAmount, sch3,
	                        new double[] {0.02875},
	                        new ActualActual(ActualActual.Convention.ISMA),
	                        BusinessDayConvention.ModifiedFollowing,
	                        100.0, new Date(30,Month.November,2004));

	    bond3.setPricingEngine(bondEngine);

	    final double marketYield3 = 0.02997;

	    final Date settlementDate = new Date(30,Month.November,2004);
	    final double cachedPrice3 = 99.764874;

	    price = bond3.cleanPrice(marketYield3,
	                             bondDayCount, Compounding.Compounded, freq, settlementDate);
	    if (Math.abs(price-cachedPrice3) > tolerance) {
	    	fail("failed to reproduce cached price:"
	                   + "\n    calculated: " + price + ""
	                   + "\n    expected:   " + cachedPrice3 + ""
	                   + "\n    error:      " + (price-cachedPrice3));
	    }

	    // this should give the same result since the issue date is the
	    // earliest possible settlement date

	    settings.setEvaluationDate(new Date(22,Month.November,2004));

	    price = bond3.cleanPrice(marketYield3, bondDayCount, Compounding.Compounded, freq);
	    if (Math.abs(price-cachedPrice3) > tolerance) {
	    	fail("failed to reproduce cached price:"
	                   + "\n    calculated: " + price + ""
	                   + "\n    expected:   " + cachedPrice3 + ""
	                   + "\n    error:      " + (price-cachedPrice3));
	    }
	}

	@Test
	public void testCachedZero() {

	    QL.info("Testing zero-coupon bond prices against cached values...");

        final Calendar calendar = new Target();
        final Date today = calendar.adjust(Date.todaysDate());
        // final Date today = calendar.adjust(new Date(6,Month.June,2007));
	    // final Date today = new Date(22,Month.November,2004);
	    final Settings settings = new Settings();
	    settings.setEvaluationDate(today);

        final double faceAmount = 1000000.0;

	    final int settlementDays = 1;

		final Handle<YieldTermStructure> discountCurve = new Handle<YieldTermStructure>(Utilities.flatRate(today, 0.03, new Actual360()));

	    final double tolerance = 1.0e-6;

	    // plain

	    final ZeroCouponBond bond1 = new ZeroCouponBond(settlementDays,
	                         new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
	                         faceAmount,
	                         new Date(30,Month.November,2008),
	                         BusinessDayConvention.ModifiedFollowing,
	                         100.0, new Date(30,Month.November,2004));

		final PricingEngine bondEngine = new DiscountingBondEngine(discountCurve);
	    bond1.setPricingEngine(bondEngine);

	    final double cachedPrice1 = 88.551726;

	    double price = bond1.cleanPrice();
	    if (Math.abs(price-cachedPrice1) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice1 + "\n"
	                   + "    error:      " + (price-cachedPrice1));
	    }

	    final ZeroCouponBond bond2 = new ZeroCouponBond(settlementDays,
	                         new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
	                         faceAmount,
	                         new Date(30,Month.November,2007),
	                         BusinessDayConvention.ModifiedFollowing,
	                         100.0, new Date(30,Month.November,2004));

	    bond2.setPricingEngine(bondEngine);

	    final double cachedPrice2 = 91.278949;

	    price = bond2.cleanPrice();
	    if (Math.abs(price-cachedPrice2) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice2 + "\n"
	                   + "    error:      " + (price-cachedPrice2));
	    }

	    final ZeroCouponBond bond3 = new ZeroCouponBond(settlementDays,
	                         new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
	                         faceAmount,
	                         new Date(30,Month.November,2006),
	                         BusinessDayConvention.ModifiedFollowing,
	                         100.0, new Date(30,Month.November,2004));

	    bond3.setPricingEngine(bondEngine);

	    final double cachedPrice3 = 94.098006;

	    price = bond3.cleanPrice();
	    if (Math.abs(price-cachedPrice3) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice3 + "\n"
	                   + "    error:      " + (price-cachedPrice3));
	    }
	}

	@Test
	public void testCachedFixed() {

	    QL.info("Testing fixed-coupon bond prices against cached values...");

        final Calendar calendar = new Target();
        final Date today = calendar.adjust(Date.todaysDate());
        // final Date today = calendar.adjust(new Date(6,Month.June,2007));
        // final Date today = new Date(22,Month.November,2004);
        final Settings settings = new Settings();
        settings.setEvaluationDate(today);

        final double faceAmount = 1000000.0;

	    final int settlementDays = 1;

		final Handle<YieldTermStructure> discountCurve = new Handle<YieldTermStructure>(Utilities.flatRate(today, 0.03, new Actual360()));

	    final double tolerance = 1.0e-6;

	    // plain

	    final Schedule sch = new Schedule(new Date(30,Month.November,2004),
	                 new Date(30,Month.November,2008), new Period(Frequency.Semiannual),
	                 new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
	                 BusinessDayConvention.Unadjusted, BusinessDayConvention.Unadjusted, DateGeneration.Rule.Backward, false);

	    final FixedRateBond bond1 = new FixedRateBond(settlementDays, faceAmount, sch,
	                        new double [] { 0.02875 },
	                        new ActualActual(ActualActual.Convention.ISMA),
	                        BusinessDayConvention.ModifiedFollowing,
	                        100.0, new Date(30,Month.November,2004));

	    final PricingEngine bondEngine = new DiscountingBondEngine(discountCurve);
	    bond1.setPricingEngine(bondEngine);

	    final double cachedPrice1 = 99.298100;

	    double price = bond1.cleanPrice();
	    if (Math.abs(price-cachedPrice1) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice1 + "\n"
	                   + "    error:      " + (price-cachedPrice1));
	    }

	    // varying coupons

	    final double [] couponRates = new double[] { 0.02875, 0.03, 0.03125, 0.0325 };

	    final FixedRateBond bond2 = new FixedRateBond(settlementDays, faceAmount, sch,
	                          couponRates,
	                          new ActualActual(ActualActual.Convention.ISMA),
	                          BusinessDayConvention.ModifiedFollowing,
	                          100.0, new Date(30,Month.November,2004));

	    bond2.setPricingEngine(bondEngine);

	    final double cachedPrice2 = 100.334149;

	    price = bond2.cleanPrice();
	    if (Math.abs(price-cachedPrice2) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice2 + "\n"
	                   + "    error:      " + (price-cachedPrice2));
	    }

	    // stub date

	    final Schedule sch3 = new Schedule(new Date(30,Month.November,2004),
	                  new Date(30,Month.March,2009), new Period(Frequency.Semiannual),
	                  new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
	                  BusinessDayConvention.Unadjusted, BusinessDayConvention.Unadjusted,
	                  DateGeneration.Rule.Backward, false,
	                  new Date(), new Date(30,Month.November,2008));

	    final FixedRateBond bond3 = new FixedRateBond(settlementDays, faceAmount, sch3,
	                          couponRates, new ActualActual(ActualActual.Convention.ISMA),
	                          BusinessDayConvention.ModifiedFollowing,
	                          100.0, new Date(30,Month.November,2004));

	    bond3.setPricingEngine(bondEngine);

	    final double cachedPrice3 = 100.382794;

	    price = bond3.cleanPrice();
	    if (Math.abs(price-cachedPrice3) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice3 + "\n"
	                   + "    error:      " + (price-cachedPrice3));
	    }
	}


	@Test
	public void testCachedFloating() {

	    QL.info("Testing floating-rate bond prices against cached values...");

	    final CommonVars vars = new CommonVars();

	    final Date today = new Date(22,Month.November,2004);
	    final Settings settings = new Settings();
	    settings.setEvaluationDate(today);

	    final int settlementDays = 1;

		final Handle<YieldTermStructure> riskFreeRate = new Handle<YieldTermStructure>(Utilities.flatRate(today, 0.025, new Actual360()));
		final Handle<YieldTermStructure> discountCurve = new Handle<YieldTermStructure>(Utilities.flatRate(today, 0.03, new Actual360()));

		final IborIndex index = new USDLibor(new Period(6,TimeUnit.Months), riskFreeRate);
	    final int fixingDays = 1;

	    final double tolerance = 1.0e-6;

	    final IborCouponPricer pricer = new BlackIborCouponPricer(new Handle<OptionletVolatilityStructure>());

	    // plain

	    final Schedule sch = new Schedule(new Date(30,Month.November,2004),
	    							new Date(30,Month.November,2008),
	    							new Period(Frequency.Semiannual),
	    							new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
	    							BusinessDayConvention.ModifiedFollowing, 
	    							BusinessDayConvention.ModifiedFollowing,
	    							DateGeneration.Rule.Backward, false);

	    final FloatingRateBond bond1 = new FloatingRateBond(settlementDays, vars.faceAmount, sch,
	                           index, new ActualActual(ActualActual.Convention.ISMA),
	                           BusinessDayConvention.ModifiedFollowing, fixingDays,
	                           new Array(0), new Array(0),
	                           new Array(0), new Array(0),
	                           false,
	                           100.0, new Date(30,Month.November,2004));

	    final PricingEngine bondEngine = new DiscountingBondEngine(riskFreeRate);
	    bond1.setPricingEngine(bondEngine);

	    PricerSetter.setCouponPricer(bond1.cashflows(),pricer);

	    final boolean indexedCoupon = new Settings().isUseIndexedCoupon();
	    
	    final double cachedPrice1 = indexedCoupon ? 99.874645 : 99.874646;

	    double price = bond1.cleanPrice();
	    if (Math.abs(price-cachedPrice1) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice1 + "\n"
	                   + "    error:      " + (price-cachedPrice1));
	    }

	    // different risk-free and discount curve

	    final FloatingRateBond bond2 = new FloatingRateBond(settlementDays, vars.faceAmount, sch,
	                           index, new ActualActual(ActualActual.Convention.ISMA),
	                           BusinessDayConvention.ModifiedFollowing, fixingDays,
	                           new Array(0), new Array(0),
	                           new Array(0), new Array(0),
	                           false,
	                           100.0, new Date(30,Month.November,2004));

	    final PricingEngine bondEngine2 = new DiscountingBondEngine(discountCurve);
	    bond2.setPricingEngine(bondEngine2);

	    PricerSetter.setCouponPricer(bond2.cashflows(),pricer);

        final double cachedPrice2 = indexedCoupon ? 97.955904 : 97.955904; // yes, they are the same, according to QuantLib/C++

	    price = bond2.cleanPrice();
	    if (Math.abs(price-cachedPrice2) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice2 + "\n"
	                   + "    error:      " + (price-cachedPrice2));
	    }

	    // varying spread
	    final double [] spreads = new double[] { 0.001, 0.0012, 0.0014, 0.0016 };

	    final FloatingRateBond bond3 = new FloatingRateBond(settlementDays, vars.faceAmount, sch,
	                           index, new ActualActual(ActualActual.Convention.ISMA),
	                           BusinessDayConvention.ModifiedFollowing, fixingDays,
	                           new Array(0), new Array(spreads),
	                           new Array(0), new Array(0),
	                           false,
	                           100.0, new Date(30,Month.November,2004));

	    bond3.setPricingEngine(bondEngine2);

	    PricerSetter.setCouponPricer(bond3.cashflows(),pricer);

        final double cachedPrice3 = indexedCoupon ? 98.495458 : 98.495459;
        
	    price = bond3.cleanPrice();
	    if (Math.abs(price-cachedPrice3) > tolerance) {
	        fail("failed to reproduce cached price:\n"
	                   + "    calculated: " + price + "\n"
	                   + "    expected:   " + cachedPrice3 + "\n"
	                   + "    error:      " + (price-cachedPrice3));
	    }
	}

	@Test
	public void testBrazilianCached() {

	    QL.info("Testing Brazilian public bond prices against cached values...");

        final Calendar calendar = new Target();
        // final Date today = calendar.adjust(Date.todaysDate());
	    final Date today = calendar.adjust(new Date(6,Month.June,2007));
	    final Settings settings = new Settings();
	    settings.setEvaluationDate(today);

        //final double faceAmount = 1000000.0;
        final double faceAmount = 1000.0;
        final int settlementDays = 1;

	    // NTN-F maturity dates
	    final Date [] maturityDates = new Date[6];
	    maturityDates[0] = new Date(1,Month.January,2008);
	    maturityDates[1] = new Date(1,Month.January,2010);
	    maturityDates[2] = new Date(1,Month.July,2010);
	    maturityDates[3] = new Date(1,Month.January,2012);
	    maturityDates[4] = new Date(1,Month.January,2014);
	    maturityDates[5] = new Date(1,Month.January,2017);

	    // NTN-F yields
	    final double [] yields = new double[6];
	    yields[0] = 0.114614;
	    yields[1] = 0.105726;
	    yields[2] = 0.105328;
	    yields[3] = 0.104283;
	    yields[4] = 0.103218;
	    yields[5] = 0.102948;

	    // NTN-F prices
	    final double [] prices = new double[6];
	    prices[0] = 1034.63031372;
	    prices[1] = 1030.09919487;
	    prices[2] = 1029.98307160;
	    prices[3] = 1028.13585068;
	    prices[4] = 1028.33383817;
	    prices[5] = 1026.19716497;


	    // The tolerance is high because Andima truncate yields
	    final double tolerance = 1.0e-4;

	    final InterestRate [] couponRates = new InterestRate[1];
	    couponRates[0] = new InterestRate(0.1,new Thirty360(),Compounding.Compounded,Frequency.Annual);

	    for (int bondIndex = 0; bondIndex < maturityDates.length; bondIndex++) {

	        // plain
	        final InterestRate yield = new InterestRate(yields[bondIndex],
	                           new Business252(new Brazil()),
	                           Compounding.Compounded, Frequency.Annual);

	        final Schedule schedule = new Schedule(new Date(1,Month.January,2007),
	                          maturityDates[bondIndex], new Period(Frequency.Semiannual),
	                          new Brazil(Brazil.Market.SETTLEMENT),
	                          BusinessDayConvention.Unadjusted, BusinessDayConvention.Unadjusted,
	                          DateGeneration.Rule.Backward, false);

	        // fixed coupons
	        final Leg cashflows =
	            new FixedRateLeg(schedule, new Actual360())
	            .withNotionals(faceAmount)
	            .withCouponRates(couponRates)
	            .withPaymentAdjustment(BusinessDayConvention.ModifiedFollowing).Leg();
	        // redemption
	        cashflows.add(new SimpleCashFlow(faceAmount, cashflows.last().date()));

	        final Bond bond = new Bond(settlementDays, new Brazil(Brazil.Market.SETTLEMENT),
	                  faceAmount, cashflows.last().date(),
	                  new Date(1,Month.January,2007), cashflows);

	        final double cachedPrice = prices[bondIndex];

	        final double price = faceAmount*bond.dirtyPrice(yield.rate(),
	                                                     yield.dayCounter(),
	                                                     yield.compounding(),
	                                                     yield.frequency(),
	                                                     today)/100;
	        if (Math.abs(price-cachedPrice) > tolerance) {
	            fail("failed to reproduce cached price:\n"
	                        + "    calculated: " + price + "\n"
	                        + "    expected:   " + cachedPrice + "\n"
	                        + "    error:      " + (price-cachedPrice)  + "\n"
	                        );
	        }
	    }
	}

}
