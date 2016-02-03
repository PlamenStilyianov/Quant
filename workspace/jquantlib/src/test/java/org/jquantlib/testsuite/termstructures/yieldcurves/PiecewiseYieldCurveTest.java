/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.testsuite.termstructures.yieldcurves;


import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.ActualActual;
import org.jquantlib.daycounters.ActualActual.Convention;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.BMAIndex;
import org.jquantlib.indexes.Euribor;
import org.jquantlib.indexes.Euribor3M;
import org.jquantlib.indexes.Euribor6M;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.indexes.ibor.JPYLibor;
import org.jquantlib.indexes.ibor.USDLibor;
import org.jquantlib.instruments.BMASwap;
import org.jquantlib.instruments.ForwardRateAgreement;
import org.jquantlib.instruments.MakeVanillaSwap;
import org.jquantlib.instruments.Position;
import org.jquantlib.instruments.VanillaSwap;
import org.jquantlib.instruments.bonds.FixedRateBond;
import org.jquantlib.math.interpolations.CubicInterpolation;
import org.jquantlib.math.interpolations.Interpolation.Interpolator;
import org.jquantlib.math.interpolations.factories.BackwardFlat;
import org.jquantlib.math.interpolations.factories.Cubic;
import org.jquantlib.math.interpolations.factories.Linear;
import org.jquantlib.math.interpolations.factories.LogCubic;
import org.jquantlib.math.interpolations.factories.LogLinear;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.bond.DiscountingBondEngine;
import org.jquantlib.pricingengines.swap.DiscountingSwapEngine;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.Bootstrap;
import org.jquantlib.termstructures.IterativeBootstrap;
import org.jquantlib.termstructures.RateHelper;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.yieldcurves.BMASwapRateHelper;
import org.jquantlib.termstructures.yieldcurves.DepositRateHelper;
import org.jquantlib.termstructures.yieldcurves.Discount;
import org.jquantlib.termstructures.yieldcurves.FixedRateBondHelper;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.termstructures.yieldcurves.ForwardRate;
import org.jquantlib.termstructures.yieldcurves.FraRateHelper;
import org.jquantlib.termstructures.yieldcurves.PiecewiseYieldCurve;
import org.jquantlib.termstructures.yieldcurves.SwapRateHelper;
import org.jquantlib.termstructures.yieldcurves.Traits;
import org.jquantlib.termstructures.yieldcurves.ZeroYield;
import org.jquantlib.testsuite.util.Flag;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.MakeSchedule;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.Weekday;
import org.jquantlib.time.calendars.Japan;
import org.jquantlib.time.calendars.JointCalendar;
import org.jquantlib.time.calendars.JointCalendar.JointCalendarRule;
import org.jquantlib.time.calendars.Target;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
public class PiecewiseYieldCurveTest {

	private final Datum depositData[] = new Datum[] {
    	new Datum( 1, TimeUnit.Weeks,  4.559 ),
    	new Datum( 1, TimeUnit.Months, 4.581 ),
    	new Datum( 2, TimeUnit.Months, 4.573 ),
    	new Datum( 3, TimeUnit.Months, 4.557 ),
    	new Datum( 6, TimeUnit.Months, 4.496 ),
    	new Datum( 9, TimeUnit.Months, 4.490 )
    };

    private final Datum fraData[] = {
    	new Datum( 1, TimeUnit.Months, 4.581 ),
    	new Datum( 2, TimeUnit.Months, 4.573 ),
    	new Datum( 3, TimeUnit.Months, 4.557 ),
    	new Datum( 6, TimeUnit.Months, 4.496 ),
    	new Datum( 9, TimeUnit.Months, 4.490 )
    };

    private final Datum swapData[] = {
    	new Datum(  1, TimeUnit.Years, 4.54 ),
    	new Datum(  2, TimeUnit.Years, 4.63 ),
    	new Datum(  3, TimeUnit.Years, 4.75 ),
    	new Datum(  4, TimeUnit.Years, 4.86 ),
    	new Datum(  5, TimeUnit.Years, 4.99 ),
    	new Datum(  6, TimeUnit.Years, 5.11 ),
    	new Datum(  7, TimeUnit.Years, 5.23 ),
    	new Datum(  8, TimeUnit.Years, 5.33 ),
    	new Datum(  9, TimeUnit.Years, 5.41 ),
    	new Datum( 10, TimeUnit.Years, 5.47 ),
    	new Datum( 12, TimeUnit.Years, 5.60 ),
    	new Datum( 15, TimeUnit.Years, 5.75 ),
    	new Datum( 20, TimeUnit.Years, 5.89 ),
    	new Datum( 25, TimeUnit.Years, 5.95 ),
    	new Datum( 30, TimeUnit.Years, 5.96 )
    };

    private final BondDatum bondData[] = {
    	new BondDatum(  6, TimeUnit.Months, 5, Frequency.Semiannual, 4.75, 101.320 ),
    	new BondDatum(  1, TimeUnit.Years,  3, Frequency.Semiannual, 2.75, 100.590 ),
    	new BondDatum(  2, TimeUnit.Years,  5, Frequency.Semiannual, 5.00, 105.650 ),
    	new BondDatum(  5, TimeUnit.Years, 11, Frequency.Semiannual, 5.50, 113.610 ),
    	new BondDatum( 10, TimeUnit.Years, 11, Frequency.Semiannual, 3.75, 104.070 )
    };

    private final Datum bmaData[] = {
    	new Datum(  1, TimeUnit.Years, 67.56 ),
    	new Datum(  2, TimeUnit.Years, 68.00 ),
    	new Datum(  3, TimeUnit.Years, 68.25 ),
    	new Datum(  4, TimeUnit.Years, 68.50 ),
    	new Datum(  5, TimeUnit.Years, 68.81 ),
    	new Datum(  7, TimeUnit.Years, 69.50 ),
    	new Datum( 10, TimeUnit.Years, 70.44 ),
    	new Datum( 15, TimeUnit.Years, 71.69 ),
    	new Datum( 20, TimeUnit.Years, 72.69 ),
    	new Datum( 30, TimeUnit.Years, 73.81 )
    };
    
    
    public PiecewiseYieldCurveTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }
    
    
    //
    // private inner classes
    //
	
	private static class Datum {
        public final int n;
        public final TimeUnit units;
        public final /*@Rate*/ double rate;
        
        public Datum(
        		final int n,
        		final TimeUnit units,
        		final /*@Rate*/ double rate) {
        	this.n = n;
        	this.units = units;
        	this.rate = rate;
        }
    }

	private static class BondDatum {
    	public final int n;
    	public final TimeUnit units;
    	public final int length;
    	public final Frequency frequency;
    	public final /*@Rate*/ double coupon;
    	public final /*@Real*/ double price;
        
        public BondDatum(
        		final int n,
        		final TimeUnit units,
        		final int length,
        		final Frequency frequency,
        		final /*@Rate*/ double coupon,
        		final /*@Real*/ double price) {
        	this.n = n;
        	this.units = units;
        	this.length = length;
        	this.frequency = frequency;
        	this.coupon = coupon;
        	this.price = price;
        }
    }

	private class CommonVars {
		// global variables
		public Calendar calendar;
		public final int settlementDays;
		public Date today;
		public Date settlement;
		public final BusinessDayConvention fixedLegConvention;
		public final Frequency fixedLegFrequency;
		public final DayCounter fixedLegDayCounter;
		public final int bondSettlementDays;
		public final DayCounter bondDayCounter;
		public final BusinessDayConvention bondConvention;
		public final double bondRedemption;
		public final Frequency bmaFrequency;
		public final BusinessDayConvention bmaConvention;
		public final DayCounter bmaDayCounter;

		public final int deposits;
		public final int fras;
		public final int swaps;
		public final int bonds;
		public final int bmas;
		public SimpleQuote[] rates;
		public final SimpleQuote[] fraRates;
		public final SimpleQuote[] prices;
		public final SimpleQuote[] fractions;
		public RateHelper[] instruments;
		public final RateHelper[] fraHelpers;
		public final RateHelper[] bondHelpers;
		public final RateHelper[] bmaHelpers;
		public final Schedule[] schedules;
		

		public YieldTermStructure termStructure;


		//public SavedSettings backup;
		//public IndexHistoryCleaner cleaner;

		
		public CommonVars() {
			// data
			calendar = new Target();
			settlementDays = 2;
			
			today = calendar.adjust(Date.todaysDate());
			new Settings().setEvaluationDate(today);
			
			settlement = calendar.advance(today,settlementDays,TimeUnit.Days);
			fixedLegConvention = BusinessDayConvention.Unadjusted;
			fixedLegFrequency = Frequency.Annual;
			fixedLegDayCounter = new org.jquantlib.daycounters.Thirty360();
			bondSettlementDays = 3;
			bondDayCounter = new ActualActual(Convention.Bond);
			bondConvention = BusinessDayConvention.Following;
			bondRedemption = 100.0;
			bmaFrequency = Frequency.Quarterly;
			bmaConvention = BusinessDayConvention.Following;
			bmaDayCounter = new ActualActual(Convention.Bond);

			deposits = depositData.length;
            fras = fraData.length;
            swaps = swapData.length;
            bonds = bondData.length;
            bmas = bmaData.length;

            
            // market elements
            rates = new SimpleQuote[deposits+swaps];
            fraRates = new SimpleQuote[fras];
            fractions = new SimpleQuote[bmas];
            prices = new SimpleQuote[bonds];
            
            for (int i=0; i<deposits; i++) {
                rates[i] = new SimpleQuote(depositData[i].rate/100);
            }

            for (int i=0; i<swaps; i++) {
            	rates[i+deposits] = new SimpleQuote(swapData[i].rate/100);
            }
            
            for (int i=0; i<fras; i++) {
                fraRates[i] = new SimpleQuote(fraData[i].rate/100);
            }
            
            for (int i=0; i<bonds; i++) {
                prices[i] = new SimpleQuote(bondData[i].price);
            }
            
            for (int i=0; i<bmas; i++) {
                fractions[i] = new SimpleQuote(bmaData[i].rate/100);
            }

            // rate helpers
            instruments = new RateHelper[deposits+swaps];
            fraHelpers  = new RateHelper[fras];
            bondHelpers = new RateHelper[bonds];
            schedules   = new Schedule[bonds];
            bmaHelpers  = new RateHelper[bmas];
            
            final IborIndex euribor6m = new Euribor(new Period(6, TimeUnit.Months), new Handle<YieldTermStructure>());
            for (int i=0; i<deposits; i++) {
                final Handle<Quote> r = new Handle<Quote>(rates[i]);
                instruments[i] = new
                    DepositRateHelper(r, new Period(depositData[i].n,depositData[i].units),
                                      euribor6m.fixingDays(), calendar,
                                      euribor6m.businessDayConvention(),
                                      euribor6m.endOfMonth(),
                                      euribor6m.dayCounter());
            }

            for (int i=0; i<swaps; i++) {
                final Handle<Quote> r = new Handle<Quote>(rates[i+deposits]);
                instruments[i+deposits] = new
                    SwapRateHelper(r, new Period(swapData[i].n, swapData[i].units),
                                   calendar,
                                   fixedLegFrequency, fixedLegConvention,
                                   fixedLegDayCounter, euribor6m);
            }

            final Euribor euribor3m = new Euribor(new Period(3, TimeUnit.Months), new Handle<YieldTermStructure>());
            for (int i=0; i<fras; i++) {
                final Handle<Quote> r = new Handle<Quote>(fraRates[i]);
                fraHelpers[i] = new
                    FraRateHelper(r, fraData[i].n, fraData[i].n + 3,
                                  euribor3m.fixingDays(),
                                  euribor3m.fixingCalendar(),
                                  euribor3m.businessDayConvention(),
                                  euribor3m.endOfMonth(),
                                  euribor3m.dayCounter());
            }

            for (int i=0; i<bonds; i++) {
                final Handle<Quote> p = new Handle<Quote>(prices[i]);
                final Date maturity = calendar.advance(today, bondData[i].n, bondData[i].units);
                final Date issue = calendar.advance(maturity, -bondData[i].length, TimeUnit.Years);
                
                /*@Rate*/ final double[] coupons = new double[1];
                coupons[0] = bondData[i].coupon/100.0;

                schedules[i] = new Schedule(issue, maturity,
                                        new Period(bondData[i].frequency),
                                        calendar,
                                        bondConvention, bondConvention,
                                        DateGeneration.Rule.Backward, false, new Date(), new Date());
                
                bondHelpers[i] = new FixedRateBondHelper(p,
                                        bondSettlementDays,
                                        bondRedemption, schedules[i],
                                        coupons, bondDayCounter,
                                        bondConvention,
                                        bondRedemption, issue);
            }
        }
	}
    
    
    
    private <T extends Traits, I extends Interpolator, B extends Bootstrap> void testCurveConsistency(
    		final Class<T> classT,
    		final Class<I> classI,
    		final Class<B> classB,
    		final CommonVars vars) {
    	I interpolator;
		try {
			interpolator = classI.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
    	testCurveConsistency(classT, classI, classB, vars, interpolator, 1.0e-9);
    }
    private <T extends Traits, I extends Interpolator, B extends Bootstrap> void testCurveConsistency(
    		final Class<T> classT,
    		final Class<I> classI,
    		final Class<B> classB,
    		final CommonVars vars,
            final Interpolator interpolator) {
    	testCurveConsistency(classT, classI, classB, vars, interpolator, 1.0e-9);
    }
    private <T extends Traits, I extends Interpolator, B extends Bootstrap> void testCurveConsistency(
    		final Class<T> classT,
    		final Class<I> classI,
    		final Class<B> classB,
    		final CommonVars vars,
            final Interpolator interpolator,
            /*@Real*/ final double tolerance) {
    	
        vars.termStructure = new PiecewiseYieldCurve<T,I,B>(
										classT, classI, classB,
										vars.settlement, vars.instruments,
										new Actual360(),
										new Handle/*<Quote>*/[0],
										new Date[0],
										1.0e-12,
										interpolator);

        final RelinkableHandle<YieldTermStructure> curveHandle = new RelinkableHandle<YieldTermStructure>();
        curveHandle.linkTo(vars.termStructure);

        // check deposits
        for (int i=0; i<vars.deposits; i++) {
            final Euribor index = new Euribor(new Period(depositData[i].n, depositData[i].units), curveHandle);
            /*@Rate*/ final double expectedRate  = depositData[i].rate/100;
            /*@Rate*/ final double estimatedRate = index.fixing(vars.today);
            if (Math.abs(expectedRate-estimatedRate) > tolerance) {
            	throw new RuntimeException(
	                String.format("%d %s %s %s %f %s %f",
	                    depositData[i].n,
	                    depositData[i].units == TimeUnit.Weeks ? "week(s)" : "month(s)",
	                    " deposit:",
	                    "\n    estimated rate: ", estimatedRate,
	                    "\n    expected rate:  ", expectedRate));
            }
        }

        // check swaps
        final IborIndex euribor6m = new Euribor6M(curveHandle);
        for (int i=0; i<vars.swaps; i++) {
            final Period tenor = new Period(swapData[i].n, swapData[i].units);

            final VanillaSwap swap = new MakeVanillaSwap(tenor, euribor6m, 0.0)
                .withEffectiveDate(vars.settlement)
                .withFixedLegDayCount(vars.fixedLegDayCounter)
                .withFixedLegTenor(new Period(vars.fixedLegFrequency))
                .withFixedLegConvention(vars.fixedLegConvention)
                .withFixedLegTerminationDateConvention(vars.fixedLegConvention)
                .value();

            /*@Rate*/ final double expectedRate  = swapData[i].rate/100;
            /*@Rate*/ final double estimatedRate = swap.fairRate();
            /*@Spread*/ final double error = Math.abs(expectedRate-estimatedRate);
            if (error > tolerance) {
            	throw new RuntimeException(
        			String.format("%d %s %s %f %s %f %s %f %s %f",
	                    swapData[i].n, " year(s) swap:\n",
	                    "\n estimated rate: ", estimatedRate,
	                    "\n expected rate:  ", expectedRate,
	                    "\n error:          ", error,
	                    "\n tolerance:      ", tolerance));
            }
        }

        // check bonds
        vars.termStructure = new PiecewiseYieldCurve<T,I,B>(
									classT, classI, classB,
									vars.settlement, vars.bondHelpers,
									new Actual360(),
									new Handle/*<Quote>*/[0],
									new Date[0],
									1.0e-12,
									interpolator);
        
        curveHandle.linkTo(vars.termStructure);

        for (int i=0; i<vars.bonds; i++) {
            final Date maturity = vars.calendar.advance(vars.today, bondData[i].n, bondData[i].units);
            final Date issue = vars.calendar.advance(maturity, -bondData[i].length, TimeUnit.Years);
            /*@Rate*/ final double[] coupons = new double[1];
            coupons[0] = bondData[i].coupon/100.0;

            final FixedRateBond bond = new FixedRateBond(vars.bondSettlementDays, 100.0,
                               vars.schedules[i], coupons,
                               vars.bondDayCounter, vars.bondConvention,
                               vars.bondRedemption, issue);

            final PricingEngine bondEngine = new DiscountingBondEngine(curveHandle);
            bond.setPricingEngine(bondEngine);

            /*@Real*/ final double expectedPrice = bondData[i].price, estimatedPrice = bond.cleanPrice();
            /*@Real*/ final double error = Math.abs(expectedPrice-estimatedPrice);
            if (error > tolerance) {
            	throw new RuntimeException(
            			String.format("#%d %s %s %f %s %f %s %f",
        					i+1, " bond failure:",
                            "\n  estimated price: ", estimatedPrice,
                            "\n  expected price:  ", expectedPrice,
                            "\n  error:           ", error));
            }
        }

        // check FRA
        vars.termStructure = new PiecewiseYieldCurve<T,I,B>(
        							classT, classI, classB,
        							vars.settlement, vars.fraHelpers,
                                    new Actual360(),
									new Handle/*<Quote>*/[0],
									new Date[0],
                                    1.0e-12,
                                    interpolator);
        curveHandle.linkTo(vars.termStructure);

        final IborIndex euribor3m = new Euribor3M(curveHandle);
        for (int i=0; i<vars.fras; i++) {
            final Date start = vars.calendar.advance(vars.settlement,
		                                       fraData[i].n,
		                                       fraData[i].units,
		                                       euribor3m.businessDayConvention(),
		                                       euribor3m.endOfMonth());
            final Date end = vars.calendar.advance(start, 3, TimeUnit.Months,
                                             euribor3m.businessDayConvention(),
                                             euribor3m.endOfMonth());

            final ForwardRateAgreement fra = new ForwardRateAgreement(start, end, Position.Long,
            													fraData[i].rate/100, 100.0,
            													euribor3m, curveHandle);
            /*@Rate*/ final double expectedRate  = fraData[i].rate/100;
            /*@Rate*/ final double estimatedRate = fra.forwardRate().rate();
            if (Math.abs(expectedRate-estimatedRate) > tolerance) {
            	throw new RuntimeException(
            			String.format("#%d %s %s %f %s %f",
        					i+1, " FRA failure:",
                            "\n  estimated rate: ", estimatedRate,
                            "\n  expected rate:  ", expectedRate));
            }
        }
    }



    private <T extends Traits, I extends Interpolator, B extends Bootstrap> void testBMACurveConsistency(
    		final Class<T> classT,
    		final Class<I> classI,
    		final Class<B> classB,
    		final CommonVars vars) {
    	I interpolator;
		try {
			interpolator = classI.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
    	testCurveConsistency(classT, classI, classB, vars, interpolator, 1.0e-9);
    }
    private <T extends Traits, I extends Interpolator, B extends Bootstrap> void testBMACurveConsistency(
    		final Class<T> classT,
    		final Class<I> classI,
    		final Class<B> classB,
    		final CommonVars vars,
            final Interpolator interpolator) {
    	testCurveConsistency(classT, classI, classB, vars, interpolator, 1.0e-9);
    }
    private <T extends Traits, I extends Interpolator, B extends Bootstrap> void testBMACurveConsistency(
    		final Class<T> classT,
    		final Class<I> classI,
    		final Class<B> classB,
    		final CommonVars vars,
            final Interpolator interpolator,
            /*@Real*/ final double tolerance) {
    	
        // re-adjust settlement
        vars.calendar = new JointCalendar(new BMAIndex().fixingCalendar(),
                                          new USDLibor(new Period(3, TimeUnit.Months)).fixingCalendar(),
                                          JointCalendarRule.JoinHolidays);
        vars.today = vars.calendar.adjust(Date.todaysDate());
        new Settings().setEvaluationDate(vars.today);
        vars.settlement = vars.calendar.advance(vars.today, vars.settlementDays, TimeUnit.Days);


        final Handle<YieldTermStructure> riskFreeCurve = new Handle<YieldTermStructure>(new FlatForward(vars.settlement, 0.04, new Actual360()));

        final BMAIndex bmaIndex = new BMAIndex();
        final IborIndex liborIndex = new USDLibor(new Period(3, TimeUnit.Months), riskFreeCurve);
        for (int i=0; i<vars.bmas; ++i) {
            final Handle<Quote> f = new Handle<Quote>(vars.fractions[i]);
            vars.bmaHelpers[i] = // boost::shared_ptr<RateHelper>(
                      new BMASwapRateHelper(f, new Period(bmaData[i].n, bmaData[i].units),
                                            vars.settlementDays,
                                            vars.calendar,
                                            new Period(vars.bmaFrequency),
                                            vars.bmaConvention,
                                            vars.bmaDayCounter,
                                            bmaIndex,
                                            liborIndex);
        }

        final Weekday w = vars.today.weekday();
        final Date lastWednesday = (w.ordinal() >= 4) ? vars.today.sub(w.ordinal() - 4) : vars.today.add(4 - w.ordinal() - 7);
        final Date lastFixing = bmaIndex.fixingCalendar().adjust(lastWednesday);
        bmaIndex.addFixing(lastFixing, 0.03);

        vars.termStructure = new PiecewiseYieldCurve<T,I,B>(
        							classT, classI, classB,
        							vars.settlement, vars.bmaHelpers,
                                    new Actual360(),
                                    new Handle/*<Quote>*/[0],
                                    new Date[0],
                                    1.0e-12,
                                    interpolator);

        final RelinkableHandle<YieldTermStructure> curveHandle = new RelinkableHandle<YieldTermStructure>();
        curveHandle.linkTo(vars.termStructure);

        // check BMA swaps
        final BMAIndex bma = new BMAIndex(curveHandle);
        final IborIndex libor3m = new USDLibor(new Period(3, TimeUnit.Months), riskFreeCurve);
        for (int i=0; i<vars.bmas; i++) {
            final Period tenor = new Period(bmaData[i].n, bmaData[i].units);

            final Schedule bmaSchedule = new MakeSchedule(vars.settlement,
                                                	vars.settlement.add(tenor),
                                                	new Period(vars.bmaFrequency),
                                                	bma.fixingCalendar(),
                                                	vars.bmaConvention)
            												.backwards()
            												.schedule();
            final Schedule liborSchedule = new MakeSchedule(vars.settlement,
                                                  	  vars.settlement.add(tenor),
                                                  	  libor3m.tenor(),
                                                  	  libor3m.fixingCalendar(),
                                                  	  libor3m.businessDayConvention())
										                	.endOfMonth(libor3m.endOfMonth())
										                	.backwards()
										                	.schedule();


            final BMASwap swap = new BMASwap(BMASwap.Type.Payer, 100.0,
				                       liborSchedule, 0.75, 0.0,
				                       libor3m, libor3m.dayCounter(),
				                       bmaSchedule, bma, vars.bmaDayCounter);
            swap.setPricingEngine(new DiscountingSwapEngine(libor3m.termStructure()));

            /*@Real*/ final double expectedFraction = bmaData[i].rate/100;
            /*@Real*/ final double estimatedFraction = swap.fairLiborFraction();
            /*@Real*/ final double error = Math.abs(expectedFraction-estimatedFraction);
            if (error > tolerance) {
            	throw new RuntimeException(
            			String.format("%d %s %s %f %s %f %s %f %s %f",
                            bmaData[i].n, " year(s) BMA swap:\n",
                            "\n estimated libor fraction: ", estimatedFraction,
                            "\n expected libor fraction:  ", expectedFraction,
                            "\n error:          ", error,
                            "\n tolerance:      ", tolerance));
            }
        }
	  }

    
	@Ignore
	@Test
	public void testLogCubicDiscountConsistency() {

		QL.info("Testing consistency of piecewise-log-cubic discount curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(
	    	Discount.class, LogCubic.class, IterativeBootstrap.class,
	        vars,
	        new LogCubic(CubicInterpolation.DerivativeApprox.Spline, true,
	                     CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
	                     CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0));
//	    testBMACurveConsistency(
//		    Discount.class, LogCubic.class, IterativeBootstrap.class,
//	        vars,
//	        new LogCubic(CubicInterpolation.DerivativeApprox.Spline, true,
//	                     CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
//	                     CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0));
	}

	@Ignore
	@Test
	public void testLogLinearDiscountConsistency() {

	    QL.info("Testing consistency of piecewise-log-linear discount curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(Discount.class, LogLinear.class, IterativeBootstrap.class, vars);
	    testBMACurveConsistency(Discount.class, LogLinear.class, IterativeBootstrap.class, vars);
	}

	@Ignore
	@Test
	public void testLinearDiscountConsistency() {

	    QL.info("Testing consistency of piecewise-linear discount curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(Discount.class, Linear.class, IterativeBootstrap.class, vars);
	    testBMACurveConsistency(Discount.class, Linear.class, IterativeBootstrap.class, vars);
	}

	@Ignore
	@Test
	public void testLogLinearZeroConsistency() {

	    QL.info("Testing consistency of piecewise-log-linear zero-yield curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(ZeroYield.class, LogLinear.class, IterativeBootstrap.class, vars);
	    testBMACurveConsistency(ZeroYield.class, LogLinear.class, IterativeBootstrap.class, vars);
	}

	@Ignore
	@Test
	public void testLinearZeroConsistency() {

	    QL.info("Testing consistency of piecewise-linear zero-yield curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(ZeroYield.class, Linear.class, IterativeBootstrap.class, vars);
	    testBMACurveConsistency(ZeroYield.class, Linear.class, IterativeBootstrap.class, vars);
	}

	@Ignore
	@Test
	public void testSplineZeroConsistency() {

	    QL.info("Testing consistency of piecewise-cubic zero-yield curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(
	    				ZeroYield.class, Cubic.class, IterativeBootstrap.class, 
	                    vars,
	                    new Cubic(CubicInterpolation.DerivativeApprox.Spline, true,
	                              CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
	                              CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0));
	    testBMACurveConsistency(
				ZeroYield.class, Cubic.class, IterativeBootstrap.class, 
	                    vars,
	                    new Cubic(CubicInterpolation.DerivativeApprox.Spline, true,
	                              CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
	                              CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0));
	}

	@Ignore
	@Test
	public void testLinearForwardConsistency() {

	    QL.info("Testing consistency of piecewise-linear forward-rate curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(ForwardRate.class, Linear.class, IterativeBootstrap.class, vars);
	    testBMACurveConsistency(ForwardRate.class, Linear.class, IterativeBootstrap.class, vars);
	}

	@Ignore
	@Test
	public void testFlatForwardConsistency() {

	    QL.info("Testing consistency of piecewise-flat forward-rate curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(ForwardRate.class, BackwardFlat.class, IterativeBootstrap.class, vars);
	    testBMACurveConsistency(ForwardRate.class, BackwardFlat.class, IterativeBootstrap.class, vars);
	}

	@Ignore
	@Test
	public void testSplineForwardConsistency() {

	    QL.info("Testing consistency of piecewise-cubic forward-rate curve...");

	    final CommonVars vars = new CommonVars();

	    testCurveConsistency(
	    				ForwardRate.class, Cubic.class, IterativeBootstrap.class,
	                    vars,
	                    new Cubic(CubicInterpolation.DerivativeApprox.Spline, true,
	                         CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
	                         CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0));
	    testBMACurveConsistency(
				ForwardRate.class, Cubic.class, IterativeBootstrap.class,
                vars,
                new Cubic(CubicInterpolation.DerivativeApprox.Spline, true,
                     CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
                     CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0));
	}

//	@Ignore
//	@Test
//	public void testConvexMonotoneForwardConsistency() {
//	    QL.info("Testing consistency of convex monotone forward-rate curve...");
//
//	    CommonVars vars = new CommonVars();
//	    
//	    testCurveConsistency(ForwardRate.class, ConvexMonotone.class, IterativeBootstrap.class, vars);
//	    testBMACurveConsistency(ForwardRate.class, ConvexMonotone.class, IterativeBootstrap.class, vars);
//	}


//	@Ignore
//	@Test
//	public void testLocalBootstrapConsistency() {
//	    QL.info("Testing consistency of local-bootstrap algorithm...");
//
//	    final CommonVars vars = new CommonVars();
//	    
//	    testCurveConsistency(
//	    		ForwardRate.class, ConvexMonotone.class, LocalBootstrap.class, 
//	            vars, 
//	            new ConvexMonotone(), 1.0e-7);
//	    testBMACurveConsistency(
//	    		ForwardRate.class, ConvexMonotone.class, LocalBootstrap.class, 
//	            vars, 
//	            new ConvexMonotone(), 1.0e-7);
//	}


	@Ignore
	@Test
	public void testObservability() {

	    QL.info("Testing observability of piecewise yield curve...");

	    final CommonVars vars = new CommonVars();

	    vars.termStructure = new PiecewiseYieldCurve(
							    		Discount.class, LogLinear.class, IterativeBootstrap.class,
							    		vars.settlementDays,
							    		vars.calendar,
							            vars.instruments,
							            new Actual360());
	    final Flag f = new Flag();
	    vars.termStructure.addObserver(f);

	    for (int i=0; i<vars.deposits+vars.swaps; i++) {
	        /*@Time*/ final double testTime = new Actual360().yearFraction(vars.settlement, vars.instruments[i].latestDate());
	        /*@DiscountFactor*/ final double discount = vars.termStructure.discount(testTime);
	        f.lower();
	        vars.rates[i].setValue(vars.rates[i].value()*1.01);
	        if (!f.isUp())
	            throw new RuntimeException("Observer was not notified of underlying rate change");
	        if (vars.termStructure.discount(testTime,true) == discount)
	        	throw new RuntimeException("rate change did not trigger recalculation");
	        vars.rates[i].setValue(vars.rates[i].value()/1.01);
	    }

	    f.lower();
	    new Settings().setEvaluationDate(vars.calendar.advance(vars.today, 15, TimeUnit.Days));
	    if (!f.isUp())
	    	throw new RuntimeException("Observer was not notified of date change");
	}


	@Ignore
	@Test
	public void testLiborFixing() {

	    QL.info("Testing use of today's LIBOR fixings in swap curve...");

	    final CommonVars vars = new CommonVars();

	    final RateHelper[] swapHelpers = new RateHelper[vars.swaps];
	    final IborIndex euribor6m = new Euribor6M();

	    for (int i=0; i<vars.swaps; i++) {
	        final Handle<Quote> r = new Handle<Quote>(vars.rates[i+vars.deposits]);
	        swapHelpers[i] = new SwapRateHelper(
	        		           r, new Period(swapData[i].n, swapData[i].units),
	                           vars.calendar,
	                           vars.fixedLegFrequency, vars.fixedLegConvention,
	                           vars.fixedLegDayCounter, euribor6m);
	    }

	    vars.termStructure = new PiecewiseYieldCurve(
			    				Discount.class, LogLinear.class, IterativeBootstrap.class, 
			    				vars.settlement, 
			    				swapHelpers, 
			                    new Actual360());

	    final Handle<YieldTermStructure> curveHandle = new Handle<YieldTermStructure>(vars.termStructure);

	    final IborIndex index = new Euribor6M(curveHandle);
	    for (int i=0; i<vars.swaps; i++) {
	        final Period tenor = new Period(swapData[i].n, swapData[i].units);

	        final VanillaSwap swap = new MakeVanillaSwap(tenor, index, 0.0)
	            .withEffectiveDate(vars.settlement)
	            .withFixedLegDayCount(vars.fixedLegDayCounter)
	            .withFixedLegTenor(new Period(vars.fixedLegFrequency))
	            .withFixedLegConvention(vars.fixedLegConvention)
	            .withFixedLegTerminationDateConvention(vars.fixedLegConvention)
	            		.value();

	        /*@Rate*/ final double expectedRate  = swapData[i].rate/100;
	        /*@Rate*/ final double estimatedRate = swap.fairRate();
	        /*@Real*/ final double tolerance = 1.0e-9;
	        if (Math.abs(expectedRate-estimatedRate) > tolerance) {
	        	throw new RuntimeException(
	        			String.format("%s %d %s %s %f %s %s %f",
	        				"before LIBOR fixing:\n",
	                        swapData[i].n, " year(s) swap:\n",
	                        "    estimated rate: ", estimatedRate, "\n",
	                        "    expected rate:  ", expectedRate));
	        }
	    }

	    final Flag f = new Flag();
	    vars.termStructure.addObserver(f);
	    f.lower();

	    index.addFixing(vars.today, 0.0425);

	    if (!f.isUp())
	        throw new RuntimeException("Observer was not notified of rate fixing");

	    for (int i=0; i<vars.swaps; i++) {
	        final Period tenor = new Period(swapData[i].n, swapData[i].units);

	        final VanillaSwap swap = new MakeVanillaSwap(tenor, index, 0.0)
	            .withEffectiveDate(vars.settlement)
	            .withFixedLegDayCount(vars.fixedLegDayCounter)
	            .withFixedLegTenor(new Period(vars.fixedLegFrequency))
	            .withFixedLegConvention(vars.fixedLegConvention)
	            .withFixedLegTerminationDateConvention(vars.fixedLegConvention)
	            		.value();

	        /*@Rate*/ final double expectedRate  = swapData[i].rate/100;
	        /*@Rate*/ final double estimatedRate = swap.fairRate();
	        /*@Real*/ final double tolerance = 1.0e-9;
	        if (Math.abs(expectedRate-estimatedRate) > tolerance) {
	        	throw new RuntimeException(
	        			String.format("%s %d %s %s %f %s %s %f",
	                        "after LIBOR fixing:\n", 
	                        swapData[i].n, " year(s) swap:\n",
	                        "    estimated rate: ", estimatedRate, "\n",
	                        "    expected rate:  ", expectedRate));
	        }
	    }
	}

	
	@Ignore
	@Test
	public void testJpyLibor() {
	    QL.info("Testing bootstrap over JPY LIBOR swaps...");

	    final CommonVars vars = new CommonVars();

	    vars.today = new Date(4, Month.October, 2007);
	    new Settings().setEvaluationDate(vars.today);

	    vars.calendar = new Japan();
	    vars.settlement = vars.calendar.advance(vars.today,  vars.settlementDays, TimeUnit.Days);

	    // market elements
	    vars.rates = new SimpleQuote[vars.swaps];
	    for (int i=0; i<vars.swaps; i++) {
	        vars.rates[i] = new SimpleQuote(swapData[i].rate/100);
	    }

	    // rate helpers
	    vars.instruments = new RateHelper[vars.swaps];

	    final IborIndex index = new JPYLibor(new Period(6, TimeUnit.Months));
	    for (int i=0; i<vars.swaps; i++) {
	        final Handle<Quote> r = new Handle<Quote>(vars.rates[i]);
	        vars.instruments[i] = new SwapRateHelper(
	        							r, new Period(swapData[i].n, swapData[i].units),
	        							vars.calendar,                         // TODO: code review on this line!!!!
	        							vars.fixedLegFrequency, vars.fixedLegConvention,
			                            vars.fixedLegDayCounter, index);
	    }
	    
	    vars.termStructure = new PiecewiseYieldCurve(
	    								Discount.class, LogLinear.class, IterativeBootstrap.class, 
	                                    vars.settlement, vars.instruments,
	                                    new Actual360(),
										new Handle/*<Quote>*/[0],
										new Date[0],
	                                    1.0e-12);

        final RelinkableHandle<YieldTermStructure> curveHandle = new RelinkableHandle<YieldTermStructure>();
	    curveHandle.linkTo(vars.termStructure);

	    // check swaps
	    final IborIndex jpylibor6m = new JPYLibor(new Period(6, TimeUnit.Months), curveHandle);
	    for (int i=0; i<vars.swaps; i++) {
	        final Period tenor = new Period(swapData[i].n, swapData[i].units);

	        final VanillaSwap swap = new MakeVanillaSwap(tenor, jpylibor6m, 0.0)
	            .withEffectiveDate(vars.settlement)
	            .withFixedLegDayCount(vars.fixedLegDayCounter)
	            .withFixedLegTenor(new Period(vars.fixedLegFrequency))
	            .withFixedLegConvention(vars.fixedLegConvention)
	            .withFixedLegTerminationDateConvention(vars.fixedLegConvention)
	            .withFixedLegCalendar(vars.calendar)
	            .withFloatingLegCalendar(vars.calendar)
	            		.value();

	        /*@Rate*/ final double expectedRate  = swapData[i].rate/100;
	        /*@Rate*/ final double estimatedRate = swap.fairRate();
	        /*@Spread*/ final double error = Math.abs(expectedRate-estimatedRate);
	        /*@Real*/ final double tolerance = 1.0e-9;

	        
	        if (error > tolerance) {
	        	throw new RuntimeException(
	        			String.format("%d %s %s %f %s %f %s %f %s %f",
	        				swapData[i].n, " year(s) swap:\n",
	                        "\n estimated rate: ", estimatedRate,
	                        "\n expected rate:  ", expectedRate,
	                        "\n error:          ", error,
	                        "\n tolerance:      ", tolerance));
	        }
	    }
	}	

}
