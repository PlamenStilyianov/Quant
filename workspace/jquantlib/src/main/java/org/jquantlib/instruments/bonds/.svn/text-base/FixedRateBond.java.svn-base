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
 Copyright (C) 2007, 2008 Ferdinando Ametrano
 Copyright (C) 2004 Jeff Yu
 Copyright (C) 2004 M-Dimension Consulting Inc.
 Copyright (C) 2005 StatPro Italia srl

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

package org.jquantlib.instruments.bonds;

import org.jquantlib.QL;
import org.jquantlib.cashflow.FixedRateLeg;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.Bond;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;

/**
 * Fixed-rate bond
 *
 * @category instruments
 *
 * @author Ueli Hofstetter
 *
*/
public class FixedRateBond extends Bond {

	protected Frequency frequency_;
    protected DayCounter dayCounter_;

    /**
     *
     * @param settlementDays
     * @param faceAmount
     * @param schedule
     * @param coupons
     * @param accrualDayCounter
     * @param paymentConvention default: Following
     * @param redemption default: 100
     * @param issueDate default: new Date()
     */
    public FixedRateBond(/*@Natural*/final int settlementDays,
            /*@Real*/final double faceAmount,
            final Schedule schedule,
            final double[] coupons,
            final DayCounter accrualDayCounter,
            final BusinessDayConvention paymentConvention,
            /*Real*/final double redemption,
            final Date  issueDate){
    	
        super(settlementDays, schedule.calendar(), issueDate);
        
        frequency_ = schedule.tenor().frequency();
        dayCounter_ = accrualDayCounter;
        maturityDate_ = schedule.endDate().clone();
        
        cashflows_ = new FixedRateLeg(schedule, accrualDayCounter)
        				.withNotionals(faceAmount)
        				.withCouponRates(coupons)
        				.withPaymentAdjustment(paymentConvention)
        				.Leg();

        addRedemptionsToCashflows(new double[]{redemption});

        QL.ensure(!cashflows().isEmpty(), "bond with no cashflows!");
        QL.ensure(redemptions_.size() == 1, "multiple redemptions created");
    }

    /**
     * C'tor with default:
     *  issueDate = new Date()
     */
    public FixedRateBond(/*@Natural*/final int settlementDays,
            /*@Real*/final double faceAmount,
            final Schedule schedule,
            final double[] coupons,
            final DayCounter accrualDayCounter,
            final BusinessDayConvention paymentConvention,
            /*Real*/final double redemption){
    	
    	this(settlementDays,
             faceAmount,
             schedule,
             coupons,
             accrualDayCounter,
             paymentConvention,
             redemption,
             new Date());
    }

    /**
     * C'tor with default:
     *  redemption = 100
     *  issueDate  = new Date()
     */
    public FixedRateBond(/*@Natural*/final int settlementDays,
            /*@Real*/final double faceAmount,
            final Schedule schedule,
            final double[] coupons,
            final DayCounter accrualDayCounter,
            final BusinessDayConvention paymentConvention) {
    	
    	this(settlementDays,
                faceAmount,
                schedule,
                coupons,
                accrualDayCounter,
                paymentConvention,
                100.0,
                new Date());
    }

    /**
     * C'tor with default:
     *  paymentConvention = Following
     *  redemption = 100
     * 	issueDate  = new Date()
     */
    public FixedRateBond(/*@Natural*/final int settlementDays,
            /*@Real*/final double faceAmount,
            final Schedule schedule,
            final double[] coupons,
            final DayCounter accrualDayCounter) {
    	
    	this(settlementDays,
                faceAmount,
                schedule,
                coupons,
                accrualDayCounter,
                BusinessDayConvention.Following,
                100.0,
                new Date());
    }


    
    /**
     *
     * @param settlementDays
     * @param calendar
     * @param faceAmount
     * @param startDate
     * @param maturityDate
     * @param tenor
     * @param coupons
     * @param accrualDayCounter
     * @param accrualConvention default: Following
     * @param paymentConvention default: Following
     * @param redemption default: 100
     * @param issueDate default: Date()
     * @param stubDate default: Date()
     * @param rule default: Backward
     * @param endOfMonth default: false
     */
    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter,
            final BusinessDayConvention accrualConvention,
            final BusinessDayConvention paymentConvention,
            /*@Real*/ final double redemption,
            final Date  issueDate ,
            final Date  stubDate ,
            final DateGeneration.Rule  rule  ,
            final boolean endOfMonth){

    	super(settlementDays, calendar, issueDate);
        
    	frequency_= tenor.frequency();
        dayCounter_= accrualDayCounter;
        maturityDate_ = maturityDate.clone();

        Date firstDate = new Date();
        Date nextToLastDate = new Date();
        switch (rule) {
        case Backward:
            firstDate = new Date();
            nextToLastDate = stubDate.clone();
            break;
        case Forward:
            firstDate = stubDate.clone();
            nextToLastDate = new Date();
            break;
        case Zero:
        case ThirdWednesday:
        case  Twentieth:
        case  TwentiethIMM:
            throw new LibraryException(reportFalseDateGenerationRule(stubDate, rule));
        default:
            throw new LibraryException("unknown DateGeneration.Rule"); // TODO: message
        }

        final Schedule schedule = new Schedule(startDate, maturityDate_, tenor,
                calendar_, accrualConvention, accrualConvention,
                rule, endOfMonth,
                firstDate, nextToLastDate);

        cashflows_ = new FixedRateLeg(schedule, accrualDayCounter)
        .withNotionals(faceAmount)
        .withCouponRates(coupons)
        .withPaymentAdjustment(paymentConvention);

        addRedemptionsToCashflows(new double[]{redemption});

        QL.ensure(!cashflows().isEmpty(), "bond with no cashflows!");
        QL.ensure(redemptions_.size() == 1, "multiple redemptions created");
    }


    /* C'tor with default:
     * endOfMonth = false
    */
    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter,
            final BusinessDayConvention accrualConvention,
            final BusinessDayConvention paymentConvention,
            /*@Real*/ final double redemption,
            final Date  issueDate ,
            final Date  stubDate ,
            final DateGeneration.Rule  rule) {

    	this(settlementDays,
             calendar,
             faceAmount,
             startDate,
             maturityDate,
             tenor,
             coupons,
             accrualDayCounter,
             accrualConvention,
             paymentConvention,
             redemption,
             issueDate ,
             stubDate ,
             rule, 
             false);
    }
    /* C'tor with default:
     * DateGeneration::Rule rule = DateGeneration::Backward,
     * endOfMonth = false
     */

    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter,
            final BusinessDayConvention accrualConvention,
            final BusinessDayConvention paymentConvention,
            /*@Real*/ final double redemption,
            final Date  issueDate ,
            final Date  stubDate) {

    	this(settlementDays,
             calendar,
             faceAmount,
             startDate,
             maturityDate,
             tenor,
             coupons,
             accrualDayCounter,
             accrualConvention,
             paymentConvention,
             redemption,
             issueDate ,
             stubDate ,
             DateGeneration.Rule.Backward, 
             false);
    }

    /* C'tor with default:
     * 	stubDate = Date()
     * 	rule = DateGeneration.Rule.Backward,
     * 	endOfMonth = false
     */

    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter,
            final BusinessDayConvention accrualConvention,
            final BusinessDayConvention paymentConvention,
            /*@Real*/ final double redemption,
            final Date  issueDate) {

    	this(settlementDays,
             calendar,
             faceAmount,
             startDate,
             maturityDate,
             tenor,
             coupons,
             accrualDayCounter,
             accrualConvention,
             paymentConvention,
             redemption,
             issueDate ,
             new Date(),
             DateGeneration.Rule.Backward, 
             false);
    }
    /* C'tor with default:
     * 	issueDate = new Date()
     * 	stubDate = new Date()
     * 	rule = DateGeneration.Rule.Backward,
     * 	endOfMonth = false
     */

    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter,
            final BusinessDayConvention accrualConvention,
            final BusinessDayConvention paymentConvention,
            /*@Real*/ final double redemption) {

    	this(settlementDays,
             calendar,
             faceAmount,
             startDate,
             maturityDate,
             tenor,
             coupons,
             accrualDayCounter,
             accrualConvention,
             paymentConvention,
             redemption,
             new Date(),
             new Date(),
             DateGeneration.Rule.Backward, 
             false);
    }
    
    /** C'tor with default:
     * 		redemption = 100.0,
     * 		issueDate = new Date()
     * 		stubDate = new Date()
     * 		rule = DateGeneration.Rule.Backward,
     * 		endOfMonth = false
     */

    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter,
            final BusinessDayConvention accrualConvention,
            final BusinessDayConvention paymentConvention) {

    	this(settlementDays,
             calendar,
             faceAmount,
             startDate,
             maturityDate,
             tenor,
             coupons,
             accrualDayCounter,
             accrualConvention,
             paymentConvention,
             100.0,
             new Date(),
             new Date(),
             DateGeneration.Rule.Backward, 
             false);
    }

    /* C'tor with default:
     * 	paymentConvention = BusinessDayConvention.Following,
     * 	redemption = 100.0,
     * 	issueDate = new Date()
     * 	stubDate = new Date()
     * 	rule = DateGeneration.Rule.Backward,
     * 	endOfMonth = false
     */

    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter,
            final BusinessDayConvention accrualConvention) {

    	this(settlementDays,
             calendar,
             faceAmount,
             startDate,
             maturityDate,
             tenor,
             coupons,
             accrualDayCounter,
             accrualConvention,
             BusinessDayConvention.Following,
             100.0,
             new Date(),
             new Date(),
             DateGeneration.Rule.Backward, 
             false);
    }

    /* C'tor with default:
     * 	accrualConvention = BusinessDayConvention.Following,
     * 	paymentConvention = BusinessDayConvention.Following,
     * 	redemption = 100.0,
     * 	issueDate = new Date()
     * 	stubDate = new Date()
     * 	rule = DateGeneration.Rule.Backward,
     * 	endOfMonth = false
     */

    public FixedRateBond(/*@Natural*/final int settlementDays,
            final Calendar  calendar,
            /*@Real*/ final double faceAmount,
            final Date  startDate,
            final Date  maturityDate,
            final Period  tenor,
            final double[] coupons,
            final DayCounter  accrualDayCounter) {

    	this(settlementDays,
             calendar,
             faceAmount,
             startDate,
             maturityDate,
             tenor,
             coupons,
             accrualDayCounter,
             BusinessDayConvention.Following,
             BusinessDayConvention.Following,
             100.0,
             new Date(),
             new Date(),
             DateGeneration.Rule.Backward, 
             false);
    }

    public Frequency frequency(){
        return frequency_;
    }

    public DayCounter dayCounter(){
        return dayCounter_;
    }

    private String reportFalseDateGenerationRule(final Date stubDate, final DateGeneration.Rule rule) {
        final StringBuilder sb = new StringBuilder();
        sb.append("stub date (").append(stubDate).append(") not allowed with DateGeneration.Rule (").append(rule).append(")");
        return sb.toString();
    }

}
