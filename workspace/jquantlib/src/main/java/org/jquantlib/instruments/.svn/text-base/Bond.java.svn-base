/*
 Copyright (C) 2008 Srinivas Hasti, Daniel Kong

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
 Copyright (C) 2004 Jeff Yu
 Copyright (C) 2004 M-Dimension Consulting Inc.
 Copyright (C) 2005, 2006, 2007 StatPro Italia srl
 Copyright (C) 2007, 2008 Ferdinando Ametrano
 Copyright (C) 2007 Chiara Fornarola
 Copyright (C) 2008 Simon Ibbotson

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

package org.jquantlib.instruments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.cashflow.CashFlow;
import org.jquantlib.cashflow.CashFlows;
import org.jquantlib.cashflow.Coupon;
import org.jquantlib.cashflow.Leg;
import org.jquantlib.cashflow.SimpleCashFlow;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.iterators.Iterables;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops.DoubleOp;
import org.jquantlib.math.solvers1D.Brent;
import org.jquantlib.pricingengines.GenericEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.pricingengines.bond.DiscountingBondEngine;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.yieldcurves.ZeroSpreadedTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.Observer;

/**
 * Base bond class
 *
 * Derived classes must fill the uninitialized data members. Warning: Most
 * methods assume that the cash flows are stored sorted by date, the
 * redemption(s) being after any cash flow at the same date. In particular, if
 * there's one single redemption, it must be the last cash flow,
 *
 * @category instruments
 *
 *           Tests: @see ...... //FIXME: where are the testcases?!?! -
 *           price/yield calculations are cross-checked for consistency. -
 *           price/yield calculations are checked against known good values.
 *
 * @author Srinivas Hasti
 * @author Daniel Kong
 * @author Ueli Hofstetter
 * @author Zahid Hussain
 *
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class Bond extends Instrument {

    protected /*Natural*/ int settlementDays_;
    protected Calendar calendar_;
    protected List<Date> notionalSchedule_;
    protected List<Double> notionals_;
    protected Leg cashflows_; // all cashflows
    protected Leg redemptions_; // the redemptions

    protected Date maturityDate_;
    protected Date issueDate_;
    protected /*Real*/ double settlementValue_;

    

    /**
     * Constructor for amortizing or non-amortizing bonds. Redemptions and
     * maturity are calculated from the coupon data, if available. Therefore,
     * redemptions must not be included in the passed cash flows.
     *
     * @param settlementDays
     * @param calendar
     * @param issueDate
     * @param coupons
     */
    protected Bond(final /* @Natural */int settlementDays,
            	   final Calendar calendar,
            	   final Date issueDate,
            	   final Leg coupons) {

        this.settlementDays_ = settlementDays;
        this.calendar_ = calendar;
        this.cashflows_ = coupons;
        this.issueDate_ = issueDate.clone();
        this.notionals_ = new ArrayList<Double>();
        this.notionalSchedule_ = new ArrayList<Date>();
        this.redemptions_ = new Leg();

        if (!coupons.isEmpty()) {
            Collections.sort(cashflows_, new EarlierThanCashFlowComparator());
            maturityDate_ = coupons.last().date();
            addRedemptionsToCashflows();
        }

        //TODO:Review translation of Settings.java. QL097 has singleton or session based instances of Settings.
        // Current implementation of Settings appears to be thread based
        final Date evaluationDate = new Settings().evaluationDate();
        evaluationDate.addObserver(this);
    }

    protected Bond(final /* @Natural */int settlementDays,
            	   final Calendar calendar) {
        this(settlementDays, calendar, new Date(), new Leg());
    }

    protected Bond(final /* @Natural */int settlementDays,
            	   final Calendar calendar,
            	   final Date issueDate) {
        this(settlementDays, calendar, issueDate, new Leg());
    }

    /**
     * Old constructor for non amortizing bonds. /* Warning: The last passed
     * cash flow must be the bond redemption. No other cash flow can have a date
     * later than the redemption date.
     *
     * @param settlementDays
     * @param calendar
     * @param faceAmount
     * @param maturityDate
     * @param issueDate
     * @param cashflows
     */    
    public Bond(final /* @Natural */int settlementDays,
            	final Calendar calendar,
            	final /* @Real */double faceAmount,
            	final Date maturityDate,
            	final Date issueDate,
            	final Leg cashflows) {

        this.settlementDays_ = settlementDays;
        this.calendar_ = calendar;
        this.cashflows_ = cashflows;
        this.maturityDate_ = maturityDate.clone();
        this.issueDate_ = issueDate.clone();
        
        this.notionalSchedule_ = new ArrayList<Date>();
        this.notionals_ = new ArrayList<Double>();
        this.redemptions_ = new Leg();

        if (!cashflows.isEmpty()) {
            notionalSchedule_.add(new Date());
            notionals_.add(faceAmount);

            notionalSchedule_.add(maturityDate.clone());
            notionals_.add(0.0);

            final CashFlow last = cashflows.last();
            redemptions_.add(last);

            // sort cashflows, except last one
            cashflows.remove(last);
            Collections.sort(cashflows, new EarlierThanCashFlowComparator());

            //now add last cashflow back to the last position
            cashflows.add(last);
        }

        final Date evaluationDate = new Settings().evaluationDate();
        evaluationDate.addObserver(this);
    }

    protected Bond(final /* @Natural */int settlementDays,
            	   final Calendar calendar,
            	   final /* @Real */double faceAmount,
            	   final Date maturityDate) {
        this(settlementDays, calendar, faceAmount, maturityDate, new Date(), new Leg());
    }

    protected Bond(final /* @Natural */int settlementDays,
            	   final Calendar calendar,
            	   final /* @Real */double faceAmount,
            	   final Date maturityDate,
            	   final Date issueDate) {
        this(settlementDays, calendar, faceAmount, maturityDate, issueDate, new Leg());
    }

    // inline definitions

    public  /*Natural*/ int settlementDays() {
        return settlementDays_;
    }

    public Calendar calendar() {
        return calendar_;
    }

    public /*Real*/ double faceAmount() {
        return notionals_.get(0);
    }

    public List<Double> notionals() {
        return notionals_;
    }

    public Leg cashflows() {
        return cashflows_;
    }

    public Leg redemptions() {
        return redemptions_;
    }

    public Date maturityDate() {
    	return !maturityDate_.isNull() ? maturityDate_ : cashflows_.last().date();
    	
//        if (!maturityDate_.isNull() )
//            return maturityDate_;
//        else
//            return cashflows_.last().date();
    }

    public Date issueDate() {
        return issueDate_;
    }

    /** Note: new Date() as default argument. */
    public /* @Real */double notional(){
         return notional(new Date());
    }

    public/* @Real */double notional(Date date){
        if (date.isNull()){
            date = settlementDate();
        }
        if (date.gt(notionalSchedule_.get(notionalSchedule_.size()-1)))
            // after maturity
            return 0.0;

        // After the check above, d is between the schedule
        // boundaries.  We search starting from the second notional
        // date, since the first is null.  After the call to
        // lower_bound, *i is the earliest date which is greater or
        // equal than d.  Its index is greater or equal to 1.

        // FIXME:: code review !!!
        //int i = StdUtils.lowerBound(notionalSchedule_, 1, notionalSchedule_.size()-1, date);
        //int index = StdUtils.distance(notionalSchedule_,0, i);
        //TODO: get reviewed by Richard
        int index = Collections.binarySearch(notionalSchedule_, date);
        if (index < 0) {
            index = - (index + 1);
        }

        if (date.le(notionalSchedule_.get(index)))
            // no doubt about what to return
            return notionals_.get(index-1);
        else {
        	// d is equal to a redemption date.
            if (new Settings().isTodaysPayments())
                // We consider today's payment as pending; the bond still
                // has the previous notional
                return notionals_.get(index-1);
            else
                // today's payment has occurred; the bond already changed
                // notional.
            	return notionals_.get(index);
        }
    }
    
    public CashFlow redemption() {
        QL.require(redemptions_.size() == 1,
                   "multiple redemption cash flows given");
        return redemptions_.last();
    }

    public Date settlementDate() {
        return settlementDate(new Date());
    }

    public Date settlementDate(final Date date) {
        final Date d = date.isNull() ? new Settings().evaluationDate() : date;

        // usually, the settlement is at T+n...
        final Date settlement = calendar_.advance(d, settlementDays_, TimeUnit.Days);
        
        // ...but the bond won't be traded until the issue date (if given.)
        return issueDate_.isNull() ? settlement : Date.max(settlement, issueDate_.clone());
    }


    /**
     * Theoretical clean price.
     * The default bond settlement is used for calculation. 
     * Warning: the theoretical price calculated from a flat term
     * structure might differ slightly from the price calculated from the
     * corresponding yield by means of the other overload of this function. If
     * the price from a constant yield is desired, it is advisable to use such
     * other overload.
     *
     * @return The clean price of this bond.
     */
    public double cleanPrice() {
        return dirtyPrice() - accruedAmount(settlementDate());
    }

    /**
     * Theoretical dirty price.
     * The default bond settlement is used for calculation. 
     * Warning: the theoretical price calculated from a flat term
     * structure might differ slightly from the price calculated from the
     * corresponding yield by means of the other overload of this function. If
     * the price from a constant yield is desired, it is advisable to use such
     * other overload.
     *
     * @return The dirty price of this bond.
     */
    public double dirtyPrice() {
        return settlementValue()/notional(settlementDate())*100.0;
    }

    /**
     * Theoretical settlement value.
     * The default bond settlement date is used for
     * calculation.
     *
     * @return The theoretical settlement value.
     */
    public /* @Real */double settlementValue() {
        calculate();
        QL.require(settlementValue_ != Constants.NULL_REAL,
        	                   "settlement value not provided");
        return settlementValue_;
    }
    /**
     * Settlement value as a function of the clean price.The default bond
     * settlement date is used for calculation.
     *
     * @param cleanPrice
     * @return settlementValue
     */
    public/* @Real */double settlementValue(final /* @Real */double cleanPrice) {
    	
        final double dirtyPrice = cleanPrice + accruedAmount(settlementDate());
        return dirtyPrice/100.0 * notional(settlementDate());
    }

    /**
     * Theoretical bond yield /* The default bond settlement and theoretical
     * price are used for calculation.
     *
     * @param dc
     * @param comp
     * @param freq
     */
    public/* @Rate */double yield(final DayCounter dc, 
    		                      final Compounding comp,
    		                      final Frequency freq,
    		                      final /* Real */double accuracy,
    		                      final /* Size */int maxEvaluations) {
    	
        final Brent solver = new Brent();
        solver.setMaxEvaluations(maxEvaluations);
        final YieldFinder objective = new YieldFinder(notional(settlementDate()), cashflows_,
                dirtyPrice(),
                dc, comp, freq,
                settlementDate());
        return solver.solve(objective, accuracy, 0.02, 0.0, 1.0);
    }

    public/* @Rate */double yield(final DayCounter dc, 
    							  final Compounding comp,
    							  final Frequency freq) {
    	
        return yield(dc, comp, freq, 1.0e-8, 100);
    }
    
    /**
     * Clean price given a yield and settlement date
     * The default bond settlement is used if no date is given.
     *
     * @param yield
     * @param dc
     * @param comp
     * @param freq
     * @param settlementDate
     * @return clean price
     */
    public/* @Real */double cleanPrice(final /* @Rate */double yield,
            						   final DayCounter dc, 
            						   final Compounding comp, 
            						   final Frequency freq,
            						   Date settlementDate) {
        if (settlementDate.isNull()) {
            settlementDate = settlementDate();
        }
        return dirtyPrice(yield, dc, comp, freq, settlementDate) - accruedAmount(settlementDate);
    }

    public/* @Real */double cleanPrice(final /* @Rate */double yield,
            						   final DayCounter dc, 
            						   final Compounding comp, 
            						   final Frequency freq) {
        return cleanPrice(yield, dc, comp, freq, new Date());
    }
    
    /**
     * Dirty price given a yield and settlement date 
     * The default bond settlement is used if no date is given.
     *
     * @param yield
     * @param dc
     * @param comp
     * @param freq
     * @param settlementDate
     * @return dirty price
     */    
    public/* @Real */double dirtyPrice(final /* @Rate */double yield,
            						   final DayCounter dc, 
            						   final Compounding comp, 
            						   final Frequency freq,
            						   Date settlementDate) {
        if (settlementDate.isNull()){
            settlementDate = settlementDate();
        }
        return dirtyPriceFromYield(notional(settlementDate), 
        							this.cashflows_, yield,	dc, comp, 
        							freq, settlementDate);
    }

    /**
     * see {@link #dirtyPrice()} Default null settlement date is used.
     *
     * @param yield
     * @param dc
     * @param comp
     * @param freq
     * @return dirty price
     */
    public/* @Real */double dirtyPrice(final /* @Rate */double yield,
            						   final DayCounter dc, 
            						   final Compounding comp, 
            						   final Frequency freq) {
        return dirtyPrice(yield, dc, comp, freq, new Date());
    }

    /**
     * Yield given a (clean) price and settlement date
     * The default bond settlement is used if no date is given.
     *
     * @param cleanPrice
     * @param dc
     * @param comp
     * @param freq
     * @param settlementDate
     * @param accuracy
     * @param maxEvaluations
     * @return
     */
    public/* @Real */double yield(final /* @Real */ double cleanPrice,
            					  final DayCounter dc,
            					  final Compounding comp,
            					  final Frequency freq,
            					  Date settlementDate,
            					  final /* @Real */ double accuracy,
            					  final /* @Size */ int maxEvaluations) {
        if (settlementDate.isNull()) {
            settlementDate = settlementDate();
        }

        final Brent solver = new Brent();
        solver.setMaxEvaluations(maxEvaluations);
        final double dirtyPrice = cleanPrice + accruedAmount(settlementDate);
        final YieldFinder objective = new YieldFinder(notional(settlementDate),
                							this.cashflows_,dirtyPrice,
                							dc, comp, freq,	settlementDate);
        return solver.solve(objective, accuracy, 0.02, 0.0, 1.0);
    }

    /**
     * @see #yield(double, DayCounter, Compounding, Frequency, Date, double,
     *      int) using settlementDate = today, accuracy = 1.0e-8 and
     *      maxEvaluation = 100.
     * @param cleanPrice
     * @param dc
     * @param comp
     * @param freq
     * @return
     */
    public/* @Real */double yield(final /* @Real */double cleanPrice,
            					  final DayCounter dc, 
            					  final Compounding comp, 
            					  final Frequency freq) {
    	
        return yield(cleanPrice, dc, comp, freq, new Date(), 1.0e-8, 100);
    }

    /**
     * @see #yield(double, DayCounter, Compounding, Frequency, Date, double,
     *      int) using accuracy = 1.0e-8 and maxEvaluation = 100.
     * @param cleanPrice
     * @param dc
     * @param comp
     * @param freq
     * @param settlementDate
     * @return
     */
    public/* @Real */double yield(final /* @Real */double cleanPrice,
            					  final DayCounter dc, 
            					  final Compounding comp, 
            					  final Frequency freq,
            					  final Date settlementDate) {
    	
        return yield(cleanPrice, dc, comp, freq, settlementDate, 1.0e-8, 100);
    }

    /**
     * @see #yield(double, DayCounter, Compounding, Frequency, Date, double,
     *      int) using maxEvaluation = 100.
     * @param cleanPrice
     * @param dc
     * @param comp
     * @param freq
     * @param settlementDate
     * @param accuracy
     * @return
     */
    public/* @Real */double yield(final /* @Real */double cleanPrice,
            					  final DayCounter dc, 
            					  final Compounding comp, 
            					  final Frequency freq,
            					  final Date settlementDate,
            					  final /* @Real */double accuracy) {
    	
        return yield(cleanPrice, dc, comp, freq, settlementDate, accuracy, 100);
    }

    /**
     * Clean price given Z-spread 
     * Z-spread compounding, frequency, daycount are taken into account 
     * The default bond settlement is used if no date is given. 
     * For details on Z-spread refer to: "Credit Spreads Explained",
     * Lehman Brothers European Fixed Income Research - March 2004, D. O'Kane
     *
     * @param zSpread
     * @param dc
     * @param comp
     * @param freq
     * @param settlementDate
     * @return
     */

    public/* @Real */double cleanPriceFromZSpread(final /* @Spread */double zSpread,
            									  final DayCounter dc, 
            									  final Compounding comp, 
            									  final Frequency freq,
            									  final Date settlementDate) {
    	
        final /*@Real*/double p = dirtyPriceFromZSpread(zSpread, dc, comp, freq, settlementDate);
        return p - accruedAmount(settlementDate);
    }

    /**
     * @see Bond#cleanPriceFromZSpread(double, DayCounter, Compounding,
     *      Frequency, Date) using default(null) settlement date.
     * @param zSpread
     * @param dc
     * @param comp
     * @param freq
     * @return
     */
    public/* @Real */double cleanPriceFromZSpread(final /* @Spread */double zSpread,
            									  final DayCounter dc, 
            									  final Compounding comp, 
            									  final Frequency freq) {
        return cleanPriceFromZSpread(zSpread, dc, comp, freq, new Date());
    }

    /**
     * Dirty price given Z-spread Z-spread compounding, frequency, daycount are
     * taken into account The default bond settlement is used if no date is
     * given. For details on Z-spread refer to: "Credit Spreads Explained",
     * Lehman Brothers European Fixed Income Research - March 2004, D. O'Kane
     *
     * @param zSpread
     * @param dc
     * @param comp
     * @param freq
     * @param settlementDate
     * @return
     */
    public/* @Real */double dirtyPriceFromZSpread(
            	final /* @Spread */double zSpread,
            	final DayCounter dc,
            	final Compounding comp,
            	final Frequency freq,
            	Date settlement) {
    	
        if (settlement.isNull()){
            settlement = settlementDate();
        }
        QL.require(engine != null, "null pricing engine");

        //FIXME: DiscontingBondEngine
        QL.require(DiscountingBondEngine.class.isAssignableFrom(engine.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
        final DiscountingBondEngine discountingBondEngine = (DiscountingBondEngine)engine;

        return dirtyPriceFromZSpreadFunction(notional(settlement), cashflows_,
                zSpread, dc, comp, freq, settlement,
                discountingBondEngine.discountCurve());
    }

    /**
     * @see Bond#dirtyPriceFromZSpread(double, DayCounter, Compounding,
     *      Frequency, Date) using today's date as settlement date.
     * @param zSpread
     * @param dc
     * @param comp
     * @param freq
     * @return
     */
    public/* @Real */double dirtyPriceFromZSpread(final /* @Spread */double zSpread,
            									  final DayCounter dc, 
            									  final Compounding comp, 
            									  final Frequency freq) {
        return dirtyPriceFromZSpread(zSpread, dc, comp, freq, new Date());
    }


    /**
     * Accrued amount at a given date /* The default bond settlement is used if
     * no date is given.
     *
     * @return The accrued amount. double
     */
    public double accruedAmount() {
        return accruedAmount(new Date());
    }

    public double accruedAmount(Date settlement) {
        if (settlement.isNull()){
            settlement = settlementDate();
        }
        final CashFlow cf = CashFlows.getInstance().nextCashFlow(cashflows_, settlement);

        if (cf==null)
            return 0.0;

        final Date paymentDate = cf.date();
        boolean firstCouponFound = false;
        /*@Real*/double nominal = Constants.NULL_REAL;
        /*@Time*/double accrualPeriod = Constants.NULL_REAL;
        DayCounter dc = null;

        /*@Rate*/double result = 0.0;

        // QL starts at the next cashflow and only continues to the one after to
        // check that it isn't the same date. Also stop at the penultimate flow. The last flow
        // is not a Coupon
        final int startIndex = cashflows_.indexOf(cf);
        for (final CashFlow flow : Iterables.unmodifiableIterable(cashflows_.listIterator(startIndex))) {
            if (flow.date().ne(paymentDate)) {
                //TODO: Check with Richard, with break, code does not handle multiple CFs on paymentDate.
                continue; //break; 
            }
            final Coupon cp = Coupon.class.isAssignableFrom(flow.getClass()) ? (Coupon)flow : null;
            if (cp != null) {
                if (firstCouponFound) {
                    QL.require(nominal == cp.nominal() &&
                            accrualPeriod == cp.accrualPeriod() &&
                            dc.equals(cp.dayCounter()),
                                "cannot aggregate accrued amount of two " +
                                "different coupons on " + paymentDate.toString());
                } else {
                    firstCouponFound = true;
                    nominal = cp.nominal();
                    accrualPeriod = cp.accrualPeriod();
                    dc = cp.dayCounter();
                }
                result += cp.accruedAmount(settlement);
            }
        }
        return result/notional(settlement)*100.0;
    }

    @Override
	public boolean isExpired() {
        return cashflows_.last().hasOccurred(settlementDate());
    }

    /**
     * Expected next coupon: depending on (the bond and) the given date the
     * coupon can be historic, deterministic or expected in a stochastic sense.
     * When the bond settlement date is used the coupon is the already-fixed
     * not-yet-paid one. The current bond settlement is used if no date is
     * given.
     *
     * @return
     */
    public /* @Rate */double nextCoupon(Date settlement){
        if (settlement.isNull()){
            settlement = settlementDate();
        }
        return CashFlows.getInstance().nextCouponRate(cashflows_, settlement);
    }
    
    public  /* @Rate */double nextCoupon(){
        return nextCoupon(new Date());
    }


    /**
     * Previous coupon already paid at a given date /* Expected previous coupon:
     * depending on (the bond and) the given date the coupon can be historic,
     * deterministic or expected in a stochastic sense. When the bond settlement
     * date is used the coupon is the last paid one. The current bond settlement
     * is used if no date is given.
     *
     * @return
     */
    public /* @Rate */double previousCoupon(Date settlement){
        if (settlement.isNull()){
            settlement = settlementDate();
        }
        return CashFlows.getInstance().previousCouponRate(cashflows_, settlement);
    }
    public /* @Rate */double previousCoupon(){
        return previousCoupon(new Date());
    }


    @Override
    protected void setupExpired() {
        super.setupExpired();
        this.settlementValue_ = 0.0;
    }

    @Override
    protected void setupArguments(final PricingEngine.Arguments args) {
    	QL.require(args != null, "Null arguments passed");
        QL.require(Bond.ArgumentsImpl.class.isAssignableFrom(args.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE);
        final Bond.ArgumentsImpl arguments = (Bond.ArgumentsImpl)args;

        arguments.settlementDate = settlementDate();
        arguments.cashflows = (Leg)cashflows_.clone();
        arguments.calendar = calendar_;
    }
    
    @Override
    protected void fetchResults(final PricingEngine.Results r) {
    	QL.require(r != null, "Null arguments passed");
        super.fetchResults(r);

        QL.require(Bond.ResultsImpl.class.isAssignableFrom(r.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); 
        final Bond.ResultsImpl results = (Bond.ResultsImpl)r;
        
        settlementValue_ = results.settlementValue;
    }

    /**
     * This method can be called by derived classes in order to build redemption
     * payments from the existing cash flows. It must be called after setting up
     * the cashflows_ vector and will fill the notionalSchedule_, notionals_,
     * and redemptions_ data members. If given, the elements of the redemptions
     * vector will multiply the amount of the redemption cash flow. The elements
     * will be taken in base 100, i.e., a redemption equal to 100 does not
     * modify the amount. The cashflows_ vector must contain at least one coupon
     * and must be sorted by date.
     *
     * @param redemptions
     */
    protected void addRedemptionsToCashflows(final List<Double> redemptions) {
        // First, we gather the notional information from the cashflows
        calculateNotionalsFromCashflows();
        // Then, we create the redemptions based on the notional
        // information and we add them to the cashflows vector after
        // the coupons.
        redemptions_.clear();
        for (int i=1; i<notionalSchedule_.size(); ++i) {
            final double R = i < redemptions.size() ? redemptions.get(i) :
            			!redemptions.isEmpty() ? redemptions.get(redemptions.size()-1) : 100.0;
            final double amount = (R/100.0)*(notionals_.get(i-1)-notionals_.get(i));
            final CashFlow  redemption = new SimpleCashFlow(amount, notionalSchedule_.get(i));
            cashflows_.add(redemption);
            redemptions_.add(redemption);
        }
        // stable_sort now moves the redemptions to the right places
        // while ensuring that they follow coupons with the same date.
        //FIXME: Note: this should be a stable sort. according to the the java documentation Collections.sort algorithms have to be stable!
        //It has to be checked whether this is similar to std::stable_sort
        Collections.sort(cashflows_, new EarlierThanCashFlowComparator());
    }
    /*
     * Util methods
      @deprecated
     */
    protected void addRedemptionsToCashflows(final double redemption) {
		final List<Double> redemptions = new ArrayList<Double>();
		redemptions.add(redemption);
		addRedemptionsToCashflows(redemptions);
    }
    protected void addRedemptionsToCashflows(final double[] redemptionsArr) {
		final List<Double> redemptions = new ArrayList<Double>();
		for ( final double d : redemptionsArr)
			redemptions.add(new Double(d));
		
		addRedemptionsToCashflows(redemptions);
    }

    protected void addRedemptionsToCashflows() {
        addRedemptionsToCashflows(new ArrayList<Double>());
    }

    protected void calculateNotionalsFromCashflows() {
        notionalSchedule_.clear();
        notionals_.clear();
        Date lastPaymentDate = new Date();
        notionalSchedule_.add(new Date());
        for (int i=0; i<cashflows_.size(); ++i) {
        	final Object cfObj = cashflows_.get(i);
        	final Coupon coupon = Coupon.class.isAssignableFrom(cfObj.getClass()) ? (Coupon)cfObj : null;
            if (coupon==null){
                continue;
            }
            final double notional = coupon.nominal();
            // we add the notional only if it is the first one...
            if (notionals_.isEmpty()) {
                notionals_.add(coupon.nominal());
                lastPaymentDate = coupon.date().clone();
            } else if (!Closeness.isClose(notional, notionals_.get(notionals_.size() -1 ))) {
                // ...or if it has changed.
                QL.require(notional < notionals_.get(notionals_.size()-1), "increasing coupon notionals");
                notionals_.add(coupon.nominal());
                // in this case, we also add the last valid date for
                // the previous one...
                notionalSchedule_.add(lastPaymentDate);
                // ...and store the candidate for this one.
                lastPaymentDate = coupon.date().clone();
            } else {
                // otherwise, we just extend the valid range of dates
                // for the current notional.
                lastPaymentDate = coupon.date().clone();
            }
        }
        QL.require(!notionals_.isEmpty(), "no coupons provided");
        notionals_.add(0.0);
        notionalSchedule_.add(lastPaymentDate);
    }

    /**
     * This method can be called by derived classes in order to build a bond
     * with a single redemption payment. It will fill the notionalSchedule_,
     * notionals_, and redemptions_ data members.
     *
     * @param notional
     * @param redemption
     * @param date
     */
    protected void setSingleRedemption(final /*Real */double notional,
            						   final /*Real */double redemption, 
            						   final Date date) {
        redemptions_.clear();

        notionalSchedule_.add(new Date());
        notionals_.add( notional);

        notionalSchedule_.add(date);
        notionals_.add(0.0);

        final CashFlow redemptionCashflow =  new SimpleCashFlow(notional*redemption/100.0, date);
        cashflows_.add(redemptionCashflow);
        redemptions_.add(redemptionCashflow);
    }


    public static /* @Real */double dirtyPriceFromYield(final /*Real*/ double faceAmount, 
            											final Leg cashflows,
            											final /* @Rate */ double yield, 
            											final DayCounter dayCounter,
            											final Compounding compounding, 
            											Frequency frequency, 
            											final Date settlement) {
        if (frequency == Frequency.NoFrequency || frequency == Frequency.Once) {
            frequency = Frequency.Annual;
        }

        final InterestRate y = new InterestRate(yield, dayCounter, compounding, frequency);

        /* @Real */double price = 0.0;
        /* @DiscountFactor */double discount = 1.0;
        Date lastDate = new Date();

        for (int i = 0; i < cashflows.size(); ++i) {
            if (cashflows.get(i).hasOccurred(settlement)) {
                continue;
            }

            final Date couponDate = cashflows.get(i).date();
            /* @Real */final double amount = cashflows.get(i).amount();

            if (lastDate.isNull()) {
                // first not-expired coupon
                if (i > 0) {
                    lastDate = cashflows.get(i - 1).date().clone();
                } else {
                    final Object cpnObj = cashflows.get(i);
                	final Coupon coupon = Coupon.class.isAssignableFrom(cpnObj.getClass()) ? (Coupon)cpnObj : null;
                    if (coupon != null) {
                        lastDate = coupon.accrualStartDate().clone();
                    } else {
                        lastDate = couponDate.sub(new Period(1, TimeUnit.Years));
                    }
                }
                discount *= y.discountFactor(settlement, couponDate, lastDate, couponDate);
            } else {
                discount *= y.discountFactor(lastDate, couponDate);
            }
            lastDate = couponDate.clone();

            price += amount * discount;
        }
        return price / faceAmount * 100.0;
    }

    /*
     *         class YieldFinder {
          public:
            YieldFinder(
                   Real faceAmount,
                   const Leg& cashflows,
                   Real dirtyPrice,
                   const DayCounter& dayCounter,
                   Compounding compounding,
                   Frequency frequency,
                   const Date& settlement)
            : faceAmount_(faceAmount), cashflows_(cashflows),
              dirtyPrice_(dirtyPrice),compounding_(compounding),
              dayCounter_(dayCounter), frequency_(frequency),
              settlement_(settlement) {}
            Real operator()(Real yield) const {
                return dirtyPrice_ - dirtyPriceFromYield(faceAmount_,
                                                         cashflows_,
                                                         yield,
                                                         dayCounter_,
                                                         compounding_,
                                                         frequency_,
                                                         settlement_);
            }
          private:
            Real faceAmount_;
            Leg cashflows_;
            Real dirtyPrice_;
            Compounding compounding_;
            DayCounter dayCounter_;
            Frequency frequency_;
            Date settlement_;
        };

     */
    public static class YieldFinder implements DoubleOp {
        private final/* @Real */double faceAmount_;
        private final Leg cashflows_;
        private final/* @Real */double dirtyPrice_;
        private final Compounding compounding_;
        private final DayCounter dayCounter_;
        private final Frequency frequency_;
        private final Date settlement_;

        public YieldFinder(
                final /* @Real */ double faceAmount, 
                final Leg cashflows,
                final /* @Real */ double dirtyPrice, 
                final DayCounter dayCounter,
                final Compounding compounding, 
                final Frequency frequency,
                final Date settlement) {
            this.faceAmount_ = faceAmount;
            this.cashflows_ = cashflows;
            this.dirtyPrice_ = dirtyPrice;
            this.compounding_ = compounding;
            this.dayCounter_ = dayCounter;
            this.frequency_ = frequency;
            this.settlement_ = settlement.clone();
        }

        /**
         * This is equivalent to C++ assignment operator:
         *   double operator()(double yield)
         */
        @Override
        public double op(final double yield) {
            return dirtyPrice_
            - dirtyPriceFromYield(faceAmount_, 
            					  cashflows_, 
            					  yield,
            					  dayCounter_, 
            					  compounding_, 
            					  frequency_, 
            					  settlement_);
        }

    }

    static/* @Real */double dirtyPriceFromZSpreadFunction(
            final /* @Real */double faceAmount, 
            final Leg cashflows,
            final /* @Spread */ double zSpread, 
            final DayCounter dc, 
            final Compounding comp,
            final Frequency freq, 
            final Date settlement,
            final Handle<YieldTermStructure> discountCurve) {

        assert(freq!=Frequency.NoFrequency && freq != Frequency.Once):"invalid frequency:" + freq.toString();

        final Handle<Quote> zSpreadQuoteHandle = new Handle<Quote>(new SimpleQuote(
                zSpread));

        final ZeroSpreadedTermStructure spreadedCurve = new ZeroSpreadedTermStructure(
                discountCurve, zSpreadQuoteHandle, comp, freq, dc);
        /* @Real */double price = 0.0;
        for (int i = 0; i < cashflows.size(); ++i) {
            if (cashflows.get(i).hasOccurred(settlement)) {
                continue;
            }
            final Date couponDate = cashflows.get(i).date();
            /* @Real */final double amount = cashflows.get(i).amount();
            price += amount * spreadedCurve.discount(couponDate);
        }
        price /= spreadedCurve.discount(settlement);
        return price / faceAmount * 100.0;
    }



    //
    // inner interfaces
    //

    /**
     * basic bond arguments
     *
     * @author Richard Gomes
     */
    public interface Arguments extends Instrument.Arguments { /* marking interface */ }


    /**
     * basic bond results
     *
     * @author Richard Gomes
     */
    public interface Results extends Instrument.Results { /* marking interface */ }


    /**
     * basic bond price engine
     *
     * @author Richard Gomes
     */
    public interface Engine extends PricingEngine, Observer { /* marking interface */ }


    //
    // inner classes
    //
    static public class ArgumentsImpl implements Bond.Arguments {

        public Date settlementDate;
        public Leg cashflows;
        public Calendar calendar;

        @Override
        public void validate() {
            QL.require(!settlementDate.isNull(), "no settlement date provided"); // QA:[RG]::verified 
            QL.require(!cashflows.isEmpty(), "no cash flow provided");
            for (final CashFlow cf: cashflows) {
                QL.require(cf != null, "null cash flow provided");
            }
        }
    }

    static public class ResultsImpl extends Instrument.ResultsImpl implements Bond.Results {

        public /*@Real*/double settlementValue;

        @Override
        public void reset() {
            settlementValue = Constants.NULL_REAL;
            super.reset();
        }
    }

    /**
     * The pricing engine for bonds
     *
     * @author Richard Gomes
     */
    static public abstract class EngineImpl
            extends GenericEngine<Bond.Arguments, Bond.Results>
            implements Bond.Engine {

        protected EngineImpl() {
            super(new ArgumentsImpl(), new ResultsImpl());
        }

    }

}
