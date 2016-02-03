package org.jquantlib.samples;
import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.cashflow.BlackIborCouponPricer;
import org.jquantlib.cashflow.IborCouponPricer;
import org.jquantlib.cashflow.PricerSetter;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.ActualActual;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.daycounters.Thirty360;
import org.jquantlib.daycounters.Thirty360.Convention;
import org.jquantlib.indexes.Euribor6M;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.indexes.ibor.USDLibor;
import org.jquantlib.instruments.bonds.FixedRateBond;
import org.jquantlib.instruments.bonds.FloatingRateBond;
import org.jquantlib.instruments.bonds.ZeroCouponBond;
import org.jquantlib.math.interpolations.factories.LogLinear;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.bond.DiscountingBondEngine;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.IterativeBootstrap;
import org.jquantlib.termstructures.RateHelper;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.optionlet.ConstantOptionletVolatility;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;
import org.jquantlib.termstructures.yieldcurves.DepositRateHelper;
import org.jquantlib.termstructures.yieldcurves.Discount;
import org.jquantlib.termstructures.yieldcurves.FixedRateBondHelper;
import org.jquantlib.termstructures.yieldcurves.PiecewiseYieldCurve;
import org.jquantlib.termstructures.yieldcurves.SwapRateHelper;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;
import org.jquantlib.time.calendars.UnitedStates;
/**
 * 
 * @author Zahid Hussain
 *
 */
public class Bonds { //implements Runnable {

    public static void main(final String[] args) {
        new Bonds().run();
    }

    public void run() {

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();
        QL.info("Started calculation at: " + clock.getElapsedTime());
        /*********************
         *** MARKET DATA ***
         *********************/

        final Calendar calendar = new Target();

        // FIXME: outdated...
        Date settlementDate = new Date(18, Month.September, 2008);
        // must be a business day
        settlementDate = calendar.adjust(settlementDate);

        final int fixingDays = 3;
        final int settlementDays = 3;

        final Date todaysDate = calendar.advance(settlementDate, -fixingDays, TimeUnit.Days);
        new Settings().setEvaluationDate(todaysDate);

        QL.info("Evaluation date: " + todaysDate.weekday() + ", " + todaysDate);
        QL.info("Settlement date: " + settlementDate.weekday() + ", " + settlementDate);

        // Building of the bonds discounting yield curve

        /*********************
         *** RATE HELPERS ***
         *********************/

        // RateHelpers are built from the above quotes together with
        // other instrument dependent infos. Quotes are passed in
        // relinkable handles which could be relinked to some other
        // data source later.
        
        // Common data
        
        // ZC rates for the short end
        final double zc3mQuote = 0.0096;
        final double zc6mQuote = 0.0145;
        final double zc1yQuote = 0.0194;

        final Quote zc3mRate = new SimpleQuote(zc3mQuote);
        final Quote zc6mRate = new SimpleQuote(zc6mQuote);
        final Quote zc1yRate = new SimpleQuote(zc1yQuote);

        final DayCounter zcBondsDayCounter = new Actual365Fixed();

        final RateHelper zc3m = new DepositRateHelper(
                new Handle<Quote>(zc3mRate), new Period(3, TimeUnit.Months),
                fixingDays, calendar,
                BusinessDayConvention.ModifiedFollowing, true, zcBondsDayCounter);
        final RateHelper zc6m = new DepositRateHelper(
                new Handle<Quote>(zc6mRate), new Period(6, TimeUnit.Months),
                fixingDays, calendar,
                BusinessDayConvention.ModifiedFollowing, true, zcBondsDayCounter);
        final RateHelper zc1y = new DepositRateHelper(
                new Handle<Quote>(zc1yRate), new Period(1, TimeUnit.Years),
                fixingDays, calendar,
                BusinessDayConvention.ModifiedFollowing, true, zcBondsDayCounter);

        // setup bonds
        final double redemption = 100.0;
        final int numberOfBonds = 5;

        final Date issueDates[] = {
                new Date(15, Month.March,    2005),
                new Date(15, Month.June,     2005),
                new Date(30, Month.June,     2006),
                new Date(15, Month.November, 2002),
                new Date(15, Month.May,      1987) };

        final Date maturities[] = {
                new Date(31, Month.August, 2010),
                new Date(31, Month.August, 2011),
                new Date(31, Month.August, 2013),
                new Date(15, Month.August, 2018),
                new Date(15, Month.May,    2038) };

        final double couponRates[] = {	0.02375, 
        								0.04625, 
        								0.03125, 
        								0.04000, 
        								0.04500 
        								};

        final double marketQuotes[] = { 100.390625, 
        								106.21875, 
        								100.59375, 
        								101.6875, 
        								102.140625 
        								};

        final List<SimpleQuote> quote = new ArrayList<SimpleQuote>(numberOfBonds);
        final List<RelinkableHandle<Quote>> quoteHandle = new ArrayList<RelinkableHandle<Quote>>(numberOfBonds);
        for (int i = 0; i < numberOfBonds; i++) {
        	final SimpleQuote sq = new SimpleQuote(marketQuotes[i]);
        	final RelinkableHandle<Quote> handle = new RelinkableHandle<Quote>(sq);
            quote.add(sq);
            quoteHandle.add(handle);   
        }

        // Definition of the rate helpers
//        final List<FixedRateBondHelper<YieldTermStructure>> bondsHelpers = new ArrayList<FixedRateBondHelper<YieldTermStructure>>();
        final List<FixedRateBondHelper> bondsHelpers = new ArrayList<FixedRateBondHelper>();

        for (int i = 0; i < numberOfBonds; i++) {
            final Schedule schedule = new Schedule(
                    issueDates[i], maturities[i],
                    new Period(Frequency.Semiannual),
                    new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
                    BusinessDayConvention.Unadjusted,
                    BusinessDayConvention.Unadjusted,
                    DateGeneration.Rule.Backward,
                    false);
        		final FixedRateBondHelper bondHelper = new FixedRateBondHelper(
            								quoteHandle.get(i), 
            								settlementDays,
            								100.0, 
            								schedule,
            								new double[]{ couponRates[i] },
            								new ActualActual(ActualActual.Convention.Bond),
            								BusinessDayConvention.Unadjusted,
            								redemption,
            								issueDates[i]);

            bondsHelpers.add(bondHelper);
        }

        /*********************
         ** CURVE BUILDING **
         *********************/

        // Any DayCounter would be fine.
        // ActualActual::ISDA ensures that 30 years is 30.0
        final DayCounter termStructureDayCounter = new ActualActual(ActualActual.Convention.ISDA);

        final double tolerance = 1.0e-15;

        // A depo-bond curve
        final List<RateHelper> bondInstruments = new ArrayList<RateHelper>();

        // Adding the ZC bonds to the curve for the short end
        bondInstruments.add(zc3m);
        bondInstruments.add(zc6m);
        bondInstruments.add(zc1y);

        // Adding the Fixed rate bonds to the curve for the long end
        for (int i = 0; i < numberOfBonds; i++) {
            bondInstruments.add(bondsHelpers.get(i));
        }
        final RateHelper[] instruments1 = new RateHelper[bondInstruments.size()];
        bondInstruments.toArray(instruments1);
        final Handle[] jumps1 = new Handle[0];
        final Date[] jumpDates1 = new Date[0];
        final double tolerance1 = 1.0e-15;
        final LogLinear interpolator = null;
        final IterativeBootstrap bootstrap = null;
        
        final YieldTermStructure  bondDiscountingTermStructur = 
    	 					new PiecewiseYieldCurve<Discount,LogLinear,IterativeBootstrap>(
    	 							Discount.class, LogLinear.class, IterativeBootstrap.class,
    	 							settlementDate, 
    	 							instruments1,
    	 							termStructureDayCounter,
    	 							jumps1,
    	 							jumpDates1,
    	 							tolerance1,
    	 							interpolator,
    	 							bootstrap){/* anonymous */};

        // Building of the Libor forecasting curve
        // deposits
        final double d1wQuote = 0.043375;
        final double d1mQuote = 0.031875;
        final double d3mQuote = 0.0320375;
        final double d6mQuote = 0.03385;
        final double d9mQuote = 0.0338125;
        final double d1yQuote = 0.0335125;
        // swaps
        final double s2yQuote = 0.0295;
        final double s3yQuote = 0.0323;
        final double s5yQuote = 0.0359;
        final double s10yQuote = 0.0412;
        final double s15yQuote = 0.0433;

        /********************
         *** QUOTES ***
         ********************/

        // SimpleQuote stores a value which can be manually changed;
        // other Quote subclasses could read the value from a database
        // or some kind of data feed.

        // deposits
        final Quote d1wRate = (new SimpleQuote(d1wQuote));
        final Quote d1mRate = (new SimpleQuote(d1mQuote));
        final Quote d3mRate = (new SimpleQuote(d3mQuote));
        final Quote d6mRate = (new SimpleQuote(d6mQuote));
        final Quote d9mRate = (new SimpleQuote(d9mQuote));
        final Quote d1yRate = (new SimpleQuote(d1yQuote));
        // swaps
        final Quote s2yRate = (new SimpleQuote(s2yQuote));
        final Quote s3yRate = (new SimpleQuote(s3yQuote));
        final Quote s5yRate = (new SimpleQuote(s5yQuote));
        final Quote s10yRate = (new SimpleQuote(s10yQuote));
        final Quote s15yRate = (new SimpleQuote(s15yQuote));

        /*********************
         *** RATE HELPERS ***
         *********************/

        // RateHelpers are built from the above quotes together with
        // other instrument dependant infos. Quotes are passed in
        // relinkable handles which could be relinked to some other
        // data source later.

        // deposits
        final DayCounter depositDayCounter = new Actual360();

        final RateHelper d1w = new DepositRateHelper(
        					new Handle<Quote>(d1wRate), 
        					new Period(1, TimeUnit.Weeks), 
        					fixingDays, calendar,
        					BusinessDayConvention.ModifiedFollowing, 
        					true, depositDayCounter);
        final RateHelper d1m = new DepositRateHelper(
        					new Handle<Quote>(d1mRate), 
        					new Period(1, TimeUnit.Months), 
        					fixingDays, calendar,
        					BusinessDayConvention.ModifiedFollowing, 
        					true, depositDayCounter);
        final RateHelper d3m = new DepositRateHelper(
        					new Handle<Quote>(d3mRate), 
        					new Period(3, TimeUnit.Months), 
        					fixingDays, calendar,
        					BusinessDayConvention.ModifiedFollowing, 
        					true, depositDayCounter);
        final RateHelper d6m = new DepositRateHelper(
        					new Handle<Quote>(d6mRate), 
        					new Period(6, TimeUnit.Months), 
        					fixingDays, calendar,
        					BusinessDayConvention.ModifiedFollowing, 
        					true, depositDayCounter);
        final RateHelper d9m = new DepositRateHelper(
        				new Handle<Quote>(d9mRate), 
        				new Period(9, TimeUnit.Months), 
        				fixingDays, calendar,
        				BusinessDayConvention.ModifiedFollowing, 
        				true, depositDayCounter);
        final RateHelper d1y = new DepositRateHelper(
        				new Handle<Quote>(d1yRate), 
        				new Period(1, TimeUnit.Years), 
        				fixingDays, calendar,
        				BusinessDayConvention.ModifiedFollowing, 
        				true, depositDayCounter);

        // setup swaps
        final Frequency swFixedLegFrequency = Frequency.Annual;
        final BusinessDayConvention swFixedLegConvention = BusinessDayConvention.Unadjusted;
        final DayCounter swFixedLegDayCounter = new Thirty360(Convention.European);
        final IborIndex  swFloatingLegIndex = new Euribor6M(new Handle<YieldTermStructure>());

        // TODO and FIXME: not sure whether the class stuff works properly
        // final IborIndex swFloatingLegIndex = Euribor.getEuribor6M(new Handle<YieldTermStructure>(YieldTermStructure.class)); //FIXME::RG::Handle
//        final YieldTermStructure nullYieldTermStructure = new AbstractYieldTermStructure() {
//            @Override
//            protected double discountImpl(final double t) {
//                throw new UnsupportedOperationException();
//            }
//            @Override
//            public Date maxDate() {
//                throw new UnsupportedOperationException();
//            }
//        };
//        final IborIndex swFloatingLegIndex = new Euribor6M(new Handle<YieldTermStructure>(nullYieldTermStructure));


        final Period forwardStart = new Period(1, TimeUnit.Days);
        
        final RateHelper s2y = new SwapRateHelper(
        			new Handle<Quote>(s2yRate), 
        			new Period(2, TimeUnit.Years),
        			calendar, 
        			swFixedLegFrequency,
        			swFixedLegConvention, 
        			swFixedLegDayCounter,
        			swFloatingLegIndex, 
        			new Handle<Quote>(),
        			forwardStart);
         final RateHelper s3y = new SwapRateHelper(
        		 		new Handle<Quote>(s3yRate), 
        		 		new Period(3, TimeUnit.Years),
        		 		calendar, 
        		 		swFixedLegFrequency,
        		 		swFixedLegConvention, 
        		 		swFixedLegDayCounter,        			
        		 		swFloatingLegIndex, 
        		 		new Handle<Quote>(),
        		 		forwardStart);
        final RateHelper  s5y = new SwapRateHelper(
        				new Handle<Quote>(s5yRate), 
        				new Period(5, TimeUnit.Years),
        				calendar, 
        				swFixedLegFrequency,
        				swFixedLegConvention, 
        				swFixedLegDayCounter,
        				swFloatingLegIndex, 
        				new Handle<Quote>(),
        				forwardStart);
        final RateHelper s10y = new SwapRateHelper(
        				new Handle<Quote>(s10yRate), 
        				new Period(10, TimeUnit.Years),
        				calendar, 
        				swFixedLegFrequency,
        				swFixedLegConvention, 
        				swFixedLegDayCounter,
        				swFloatingLegIndex, 
        				new Handle<Quote>(),
        				forwardStart);
        final RateHelper  s15y = new SwapRateHelper(
        				new Handle<Quote>(s15yRate), 
        				new Period(15, TimeUnit.Years),
        				calendar, 
        				swFixedLegFrequency,
        				swFixedLegConvention, 
        				swFixedLegDayCounter,
        				swFloatingLegIndex, 
        				new Handle<Quote>(),
        				forwardStart);

         /*********************
         ** CURVE BUILDING **
         *********************/
        
         // Any DayCounter would be fine.
         // ActualActual::ISDA ensures that 30 years is 30.0
        
         // A depo-swap curve
         final List<RateHelper> depoSwapInstruments = new ArrayList<RateHelper>();
         depoSwapInstruments.add(d1w);
         depoSwapInstruments.add(d1m);
         depoSwapInstruments.add(d3m);
         depoSwapInstruments.add(d6m);
         depoSwapInstruments.add(d9m);
         depoSwapInstruments.add(d1y);
         depoSwapInstruments.add(s2y);
         depoSwapInstruments.add(s3y);
         depoSwapInstruments.add(s5y);
         depoSwapInstruments.add(s10y);
         depoSwapInstruments.add(s15y);
         
         final RateHelper[] instruments = new RateHelper[depoSwapInstruments.size()];
         depoSwapInstruments.toArray(instruments);
         final Handle[] jumps= new Handle[0];//]<Quote>[]) new ArrayList<Handle<Quote>>().toArray();
         final Date[] jumpDates = new Date[0];// new ArrayList<Date>().toArray();
         
         final YieldTermStructure  depoSwapTermStructure = 
        	 	new PiecewiseYieldCurve<Discount,LogLinear,IterativeBootstrap>(
        	 			Discount.class, LogLinear.class, IterativeBootstrap.class,
        	 			settlementDate, 
        	 			instruments,
        	 			termStructureDayCounter,
        	 			jumps,
        	 			jumpDates,
        	 			tolerance,
						interpolator,/*Hack*/
 						bootstrap /*Hack*/){/* anonymous */};

         // Term structures that will be used for pricing:
         // the one used for discounting cash flows
         final RelinkableHandle<YieldTermStructure> discountingTermStructure = new RelinkableHandle<YieldTermStructure>();
         // the one used for forward rate forecasting
         final RelinkableHandle<YieldTermStructure> forecastingTermStructure = new RelinkableHandle<YieldTermStructure>();
        
         /*********************
         * BONDS TO BE PRICED *
         **********************/
        
         // Common data
         final double faceAmount = 100;
        
         // Pricing engine
        final PricingEngine  bondEngine = new DiscountingBondEngine(discountingTermStructure);
        
         // Zero coupon bond
         final ZeroCouponBond zeroCouponBond = new ZeroCouponBond(
        		 							settlementDays,
        		 							new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
        		 							faceAmount,
        		 							new Date(15,Month.August,2013),
        		 							BusinessDayConvention.Following,
        		 							116.92,
        		 							new Date(15,Month.August,2003));
        
         zeroCouponBond.setPricingEngine(bondEngine);
        
         // Fixed 4.5% US Treasury Note
         final Schedule fixedBondSchedule = new Schedule(
        		 				new Date(15, Month.May, 2007),
        		 				new Date(15,Month.May,2017), 
        		 				new Period(Frequency.Semiannual),
        		 				new UnitedStates(UnitedStates.Market.GOVERNMENTBOND),
        		 				BusinessDayConvention.Unadjusted, 
        		 				BusinessDayConvention.Unadjusted, 
        		 				DateGeneration.Rule.Backward, false);
        
         final FixedRateBond fixedRateBond = new FixedRateBond(
         						settlementDays,
         						faceAmount,
         						fixedBondSchedule,
         						new double[]{0.045},
         						new ActualActual(ActualActual.Convention.Bond),
         						BusinessDayConvention.ModifiedFollowing,
         						100.0, 
         						new Date(15, Month.May, 2007));
        
         fixedRateBond.setPricingEngine(bondEngine);
        
         // Floating rate bond (3M USD Libor + 0.1%)
         // Should and will be priced on another curve later...
        
         final RelinkableHandle<YieldTermStructure> liborTermStructure = new RelinkableHandle<YieldTermStructure>();
         final IborIndex libor3m =  new USDLibor(new Period(3, TimeUnit.Months), liborTermStructure);
         libor3m.addFixing(new Date(17, Month.July, 2008), 0.0278625);
        
         final Schedule floatingBondSchedule = new Schedule(
        		 				new Date(21, Month.October, 2005),
        		 				new Date(21, Month.October, 2010), new Period(Frequency.Quarterly),
        		 				new UnitedStates(UnitedStates.Market.NYSE),
        		 				BusinessDayConvention.Unadjusted, 
        		 				BusinessDayConvention.Unadjusted, 
        		 				DateGeneration.Rule.Backward, true);
        
         final FloatingRateBond floatingRateBond = new FloatingRateBond(
        		 				settlementDays,
        		 				faceAmount,
        		 				floatingBondSchedule,
        		 				libor3m,
        		 				new Actual360(),
        		 				BusinessDayConvention.ModifiedFollowing,
        		 				2,        		 				
        		 				new Array(1).fill(1.0),  //Gearings        		 				
        		 				new Array(1).fill(0.001),//Spreads
        		 				new Array(0),		     // Caps
        		 				new Array(0),		     // Floors
        		 				true,				     // Fixing in arrears
        		 				100.0,
        		 				new Date(21, Month.October, 2005));
        
         floatingRateBond.setPricingEngine(bondEngine);
               
         // optionLet volatilities
         final double volatility = 0.0;
         final Handle<OptionletVolatilityStructure> vol =
        	 		new Handle<OptionletVolatilityStructure>(
         						new ConstantOptionletVolatility(
         								settlementDays,
         								calendar,
         								BusinessDayConvention.ModifiedFollowing,
         								volatility,
         								new Actual365Fixed()));
        
         // Coupon pricers
         final IborCouponPricer pricer = new BlackIborCouponPricer(vol);
         PricerSetter.setCouponPricer(floatingRateBond.cashflows(),pricer);
        
         // Yield curve bootstrapping
         forecastingTermStructure.linkTo(depoSwapTermStructure);
         discountingTermStructure.linkTo(bondDiscountingTermStructur);
        
         // We are using the depo & swap curve to estimate the future Libor
         // rates
         liborTermStructure.linkTo(depoSwapTermStructure);
        
         /***************
         * BOND PRICING *
         ****************/
        
        QL.info("Results:");
        
         System.out.println("                 "
        		 + "  " +  "ZC"
        		 + "  " +  "Fixed"
        		 + "  " +  "Floating"
        		 );
                
         System.out.println( "Net present value"
        		 	+  "  " + zeroCouponBond.NPV()
        		 	+  "  " + fixedRateBond.NPV()
        		 	+  "  " + floatingRateBond.NPV());
                
        System.out.println("Clean Price      "
        		+ "  " +zeroCouponBond.cleanPrice()
        		+ "  " +fixedRateBond.cleanPrice()
        		+ "  " + floatingRateBond.cleanPrice()
        		);
        
        System.out.println("Dirty price      "
        			+ " " + zeroCouponBond.dirtyPrice()
        			+ " " + fixedRateBond.dirtyPrice()
        			+ " " + floatingRateBond.dirtyPrice()
        			);
        
        System.out.println( "Accrued coupon  "
    			+ " " + zeroCouponBond.accruedAmount()
    			+ " " + fixedRateBond.accruedAmount()
    			+ " " + floatingRateBond.accruedAmount()
        		);

        System.out.println( "Previous coupon "
    			+ " " + "N/A"  //zeroCouponBond
    			+ " " + fixedRateBond.previousCoupon()
    			+ " " + floatingRateBond.previousCoupon()
        		);

        System.out.println( "Next coupon     "
    			+ " " + "N/A"  //zeroCouponBond
    			+ " " + fixedRateBond.nextCoupon()
    			+ " " + floatingRateBond.nextCoupon()
        		);
        System.out.println( "Yield           "
    			+ " " + zeroCouponBond.yield(new Actual360(),Compounding.Compounded, Frequency.Annual)
    			+ " " + fixedRateBond.yield(new Actual360(),Compounding.Compounded, Frequency.Annual)
    			+ " " + floatingRateBond.yield(new Actual360(),Compounding.Compounded, Frequency.Annual)
        		);

         // Other computations
        System.out.println("Sample indirect computations (for the floating rate bond): " );
        System.out.println( "Yield to Clean Price: " +
        		floatingRateBond.cleanPrice(floatingRateBond.yield(new Actual360(),Compounding.Compounded,Frequency.Annual),new Actual360(),Compounding.Compounded,Frequency.Annual,settlementDate)
        );
        
        System.out.println("Clean Price to Yield: " +
         floatingRateBond.yield(floatingRateBond.cleanPrice(),new Actual360(),Compounding.Compounded,Frequency.Annual,settlementDate)
        );
        
         /* "Yield to Price"
         	"Price to Yield" 
         */
        clock.stopClock();
        clock.log();
    }

}
