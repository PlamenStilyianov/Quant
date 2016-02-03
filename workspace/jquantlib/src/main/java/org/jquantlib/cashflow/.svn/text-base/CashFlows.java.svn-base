/*
 Copyright (C) 2009 Ueli Hofstetter
 Copyright (C) 2009 Richard Gomes

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

package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;
import org.jquantlib.math.solvers1D.Brent;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Cashflow-analysis functions
 *
 * @author Ueli Hofstetter
 * @author Richard Gomes
 */

//
// =================== W A R N I N G ================
//
// This class requires a total rewrite. See: http://bugs.jquantlib.org/view.php?id=357

// TODO: code review :: please verify against QL/C++ code

public class CashFlows {

    private final String not_enough_information_available = "not enough information available";
    private final String no_cashflows = "no cashflows";
    private final String unsupported_compounding_type = "unsupported compounding type";
    private final String compounded_rate_required = "compounded rate required";
    private final String unsupported_frequency = "unsupported frequency";
    private final String unknown_duration_type = "unsupported duration type";
    private final String infeasible_cashflow = "the given cash flows cannot result in the given market price due to their sign";

    private static double basisPoint_ = 1.0e-4;

    /**
     * Singleton instance for the whole application.
     * <p>
     * In an application server environment, it could be by class loader
     * depending on scope of the JQuantLib library to the module.
     *
     * @see <a
     *      href="http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html">The
     *      "Double-Checked Locking is Broken" Declaration </a>
     */
    private static volatile CashFlows instance = null;


    //
    // private constructors
    //

    private CashFlows() {
        // cannot be directly instantiated
    }

    //
    // public static methods
    //

    public static CashFlows getInstance() {
        if (instance == null) {
            synchronized (CashFlows.class) {
                if (instance == null) {
                    instance = new CashFlows();
                }
            }
        }
        return instance;
    }

    //
    // public methods
    //

    public Date startDate(final Leg cashflows) {
        Date d = Date.maxDate();
        for (int i = 0; i < cashflows.size(); ++i) {
            final Coupon c = (Coupon) cashflows.get(i);
            if (c != null) {
                d = Date.min(c.accrualStartDate(), d);
            }
        }
        // TODO: code review :: please verify against QL/C++ code
        QL.ensure(d.lt(Date.maxDate()) , not_enough_information_available); // QA:[RG]::verified
        return d;
    }

    public Date maturityDate(final Leg cashflows) {
        Date d = Date.minDate();
        for (int i = 0; i < cashflows.size(); i++) {
            d = Date.max(d, cashflows.get(i).date());
        }
        // TODO: code review :: please verify against QL/C++ code
        QL.ensure (d.gt(Date.minDate()), no_cashflows);
        return d;
    }


    public double npv(
            final Leg cashflows,
            final Handle<YieldTermStructure> discountCurve,
            final Date settlementDate,
            final Date npvDate) {
        return npv(cashflows, discountCurve, settlementDate, npvDate, 0);
    }

    /**
     * NPV of the cash flows.
     * <p>
     * The NPV is the sum of the cash flows, each discounted according to the
     * given term structure.
     *
     * @param cashflows
     * @param discountCurve
     * @param settlementDate
     * @param npvDate
     * @param exDividendDays
     * @return
     */
    public double npv(
            final Leg cashflows,
            final Handle<YieldTermStructure> discountCurve,
            final Date settlementDate,
            final Date npvDate,
            final int exDividendDays) {

        Date date = settlementDate;
        if (date.isNull()) {
            date = discountCurve.currentLink().referenceDate();
        }

        double totalNPV = 0.0;
        for (int i = 0; i < cashflows.size(); ++i) {
            if (!cashflows.get(i).hasOccurred(date.add(exDividendDays))) {
                totalNPV += cashflows.get(i).amount() * discountCurve.currentLink().discount(cashflows.get(i).date());
            }
        }

        if (npvDate.isNull())
            return totalNPV;
        else
            return totalNPV / discountCurve.currentLink().discount(npvDate);
    }

    public double npv(
            final Leg leg,
            final Handle<YieldTermStructure> discountCurve) {
        return npv(leg, discountCurve, new Date(), new Date(), 0);
    }

    /**
     * NPV of the cash flows.
     * <p>
     * The NPV is the sum of the cash flows, each discounted according to the
     * given constant interest rate. The result is affected by the choice of the
     * interest-rate compounding and the relative frequency and day counter.
     */
    public double npv(final Leg cashflows, final InterestRate irr, final Date settlementDate) {

        Date date = settlementDate;
        if (date.isNull()) {
            date = new Settings().evaluationDate();
        }

        final YieldTermStructure flatRate = new FlatForward(date, irr.rate(), irr.dayCounter(), irr.compounding(), irr.frequency());
        return npv(cashflows, new Handle<YieldTermStructure>(flatRate), date, date, 0);
    }

    public double npv(final Leg leg, final InterestRate interestRate) {
        return npv(leg, interestRate, new Date());
    }


    /*
     * BPS Functions implied from quantlib default variables
     * since we cannot assign variables to defaults in the parameter lists of functions,
     * we use function chaining to effectively assign a single default at each level.
     */

    public double bps (final Leg cashflows, final Handle <YieldTermStructure> discountCurve)
    {
        // default variable of settlement date
        return bps (cashflows, discountCurve, new Settings().evaluationDate());
    }

    public double bps (final Leg cashflows, final Handle <YieldTermStructure> discountCurve,
                       final Date settlementDate)
    {
        // default variable of npv date
        return bps (cashflows, discountCurve, settlementDate, settlementDate);
    }

    public double bps (final Leg cashflows, final Handle <YieldTermStructure> discountCurve,
                       final Date settlementDate, final Date npvDate)
    {
        // default variable of ex-dividend days
        return bps (cashflows, discountCurve, settlementDate, npvDate, 0);
    }


    /*
     * Acutal BPS Functions ported from quantlib
     */

    /**
     * Basis-point sensitivity of the cash flows.
     * <p>
     * The result is the change in NPV due to a uniform 1-basis-point change in
     * the rate paid by the cash flows. The change for each coupon is discounted
     * according to the given term structure.
     */
    public double bps(final Leg cashflows, final Handle<YieldTermStructure> discountCurve,
                      final Date settlementDate, final Date npvDate, final int exDividendDays) {

        Date date = settlementDate;
        if (date.isNull()) {
            date = discountCurve.currentLink().referenceDate();
        }

        final BPSCalculator calc = new BPSCalculator(discountCurve, npvDate);
        for (int i = 0; i < cashflows.size(); ++i) {
            if (!cashflows.get(i).hasOccurred(date.add(exDividendDays))) {
                cashflows.get(i).accept(calc);
            }
        }
        return basisPoint_ * calc.result();
    }

    /**
     * Basis-point sensitivity of the cash flows.
     * <p>
     * The result is the change in NPV due to a uniform 1-basis-point change in
     * the rate paid by the cash flows. The change for each coupon is discounted
     * according to the given term structure.
     */
    public double bps(final Leg cashflows, final InterestRate irr, Date settlementDate){
        if (settlementDate.isNull())
        {
            settlementDate = new Settings().evaluationDate();
        }
        final YieldTermStructure flatRate = new FlatForward(settlementDate, irr.rate(),
                    irr.dayCounter(), irr.compounding(), irr.frequency());
        return bps(cashflows, new Handle<YieldTermStructure>(flatRate), settlementDate, settlementDate);
     }

    /**
     * At-the-money rate of the cash flows.
     * <p>
     * The result is the fixed rate for which a fixed rate cash flow vector,
     * equivalent to the input vector, has the required NPV according to the
     * given term structure. If the required NPV is not given, the input cash
     * flow vector's NPV is used instead.
     */
    public double atmRate(final Leg leg, final Handle<YieldTermStructure> discountCurve, final Date settlementDate,
            final Date npvDate, final int exDividendDays, double npv) {
        final double bps = bps(leg, discountCurve, settlementDate, npvDate, exDividendDays);
        if (npv == 0) {
            npv = npv(leg, discountCurve, settlementDate, npvDate, exDividendDays);
        }
        return basisPoint_ * npv / bps;
    }

    public double atmRate(final Leg leg, final Handle<YieldTermStructure> discountCurve) {
        return atmRate(leg, discountCurve, new Date(), new Date(), 0, 0);
    }

    /**
     * Internal rate of return.
     * <p>
     * The IRR is the interest rate at which the NPV of the cash flows equals
     * the given market price. The function verifies the theoretical existance
     * of an IRR and numerically establishes the IRR to the desired precision.
     */
    public double irr(final Leg cashflows, final double marketPrice, final DayCounter dayCounter, final Compounding compounding,
            final Frequency frequency, final Date settlementDate, final double tolerance, final int maxIterations,
            final double guess) {

        Date date = settlementDate;
        if (date.isNull()) {
            date = new Settings().evaluationDate();
        }

        // depending on the sign of the market price, check that cash
        // flows of the opposite sign have been specified (otherwise
        // IRR is nonsensical.)

        int lastSign = sign(-marketPrice), signChanges = 0;
        for (int i = 0; i < cashflows.size(); ++i) {
            if (!cashflows.get(i).hasOccurred(date)) {
                final int thisSign = sign(cashflows.get(i).amount());
                if (lastSign * thisSign < 0) {
                    signChanges++;
                }
                if (thisSign != 0) {
                    lastSign = thisSign;
                }
            }
        }

        QL.ensure(signChanges > 0 , infeasible_cashflow); // QA:[RG]::verified

        /*
         * THIS COMMENT COMES UNMODIFIED FROM QL/C++ SOURCES
         *
         * The following is commented out due to the lack of a QL_WARN macro
         *
         * if (signChanges > 1) { // Danger of non-unique solution // Check the
         * aggregate cash flows (Norstrom) Real aggregateCashFlow = marketPrice;
         * signChanges = 0; for (Size i = 0; i < cashflows.size(); ++i) { Real
         * nextAggregateCashFlow = aggregateCashFlow + cashflows[i]->amount();
         * if (aggregateCashFlow * nextAggregateCashFlow < 0.0) signChanges++;
         * aggregateCashFlow = nextAggregateCashFlow; } if (signChanges > 1)
         * QL_WARN( "danger of non-unique solution"); }
         */

        final Brent solver = new Brent();
        solver.setMaxEvaluations(maxIterations);
        return solver.solve(new IRRFinder(cashflows, marketPrice, dayCounter, compounding, frequency, date), tolerance, guess,
                guess / 10.0);
    }

    public double irr(final Leg leg, final double marketPrice, final DayCounter dayCounter, final Compounding compounding) {
        return irr(
                leg, marketPrice, dayCounter, compounding,
                Frequency.NoFrequency, new Date(),
                1.0e-10, 10000, 0.05);
    }

    /**
     * Cash-flow duration.
     * <p>
     * The simple duration of a string of cash flows is defined as
     * {@latex[ D_ \mathrm{simple} = \frac{\sum t_i c_i B(t_i)}{\sum c_i B(t_i)} } where {@latex$ c_i } is the amount of
     * the {@latex$ i }-th cash flow, {@latex$ t_i } is its payment time, and {@latex$ B(t_i) } is the corresponding
     * discount according to the passed yield.
     * <p>
     * The modified duration is defined as {@latex[ D_ \mathrm{modified} = -\frac{1}{P} \frac{\partial P}{\partial y} } where
     * {@latex$ P }is the present value of the cash flows according to the given IRR {@latex$ y }.
     * <p>
     * The Macaulay duration is defined for a compounded IRR as
     * {@latex[ D_ \mathrm{Macaulay} = \left( 1 + \frac{y}{N} \right) D_{\mathrm{modified}} } where
     * {@latex$ y } is the IRR and {@latex$ N } is the number of cash flows per year.
     */
    public double duration(final Leg leg, final InterestRate y, final Duration duration, final Date settlementDate) {

        Date date = settlementDate;
        if (date.isNull()) {
            date = new Settings().evaluationDate();
        }

        switch (duration) {
        case Simple:
            return simpleDuration(leg, y, date);
        case Modified:
            return modifiedDuration(leg, y, date);
        case Macaulay:
            return macaulayDuration(leg, y, date);
        default:
            throw new LibraryException(unknown_duration_type); // QA:[RG]::verified
        }
    }

    public double duration(final Leg leg, final InterestRate y) {
        return duration(leg, y, Duration.Modified, new Date());
    }

    /**
     * Cash-flow convexity
     * <p>
     * The convexity of a string of cash flows is defined as {@latex[ C = \frac{1}{P} \frac{\partial^2 P}{\partial y^2} } where
     * {@latex$ P } is the present value of the cash flows according to the given IRR {@latex$ y }.
     */
    public double convexity(final Leg cashFlows, final InterestRate rate, final Date settlementDate) {

        Date date = settlementDate;
        if (date.isNull()) {
            date = new Settings().evaluationDate();
        }

        final DayCounter dayCounter = rate.dayCounter();

        double P = 0.0;
        double d2Pdy2 = 0.0;
        final double y = rate.rate();
        final int N = rate.frequency().toInteger();

        for (int i = 0; i < cashFlows.size(); ++i) {
            if (!cashFlows.get(i).hasOccurred(date)) {
                final double t = dayCounter.yearFraction(date, cashFlows.get(i).date());
                final double c = cashFlows.get(i).amount();
                final double B = rate.discountFactor(t);

                P += c * B;
                switch (rate.compounding()) {
                case Simple:
                    d2Pdy2 += c * 2.0 * B * B * B * t * t;
                    break;
                case Compounded:
                    d2Pdy2 += c * B * t * (N * t + 1) / (N * (1 + y / N) * (1 + y / N));
                    break;
                case Continuous:
                    d2Pdy2 += c * B * t * t;
                    break;
                case SimpleThenCompounded:
                default:
                    throw new LibraryException(unsupported_compounding_type); // QA:[RG]::verified
                }
            }
        }

        if (P == 0.0)
            return 0.0; // no cashflows
        return d2Pdy2 / P;
    }

    public double convexity(final Leg leg, final InterestRate y) {
        return convexity(leg, y, new Date());
    }








    private double simpleDuration(final Leg cashflows, final InterestRate rate, final Date settlementDate) {

        double P = 0.0;
        double tP = 0.0;

        for (int i = 0; i < cashflows.size(); ++i) {
            if (!cashflows.get(i).hasOccurred(settlementDate)) {
                final double t = rate.dayCounter().yearFraction(settlementDate, cashflows.get(i).date());
                final double c = cashflows.get(i).amount();
                final double B = rate.discountFactor(t);

                P += c * B;
                tP += t * c * B;
            }
        }

        if (P == 0.0)
            // no cashflows
            return 0.0;

        return tP / P;
    }

    private double modifiedDuration(final Leg cashflows, final InterestRate rate, final Date settlementDate) {

        double P = 0.0;
        double dPdy = 0.0;
        final double y = rate.rate();
        final int N = rate.frequency().toInteger();

        for (int i = 0; i < cashflows.size(); ++i) {
            if (!cashflows.get(i).hasOccurred(settlementDate)) {
                final double t = rate.dayCounter().yearFraction(settlementDate, cashflows.get(i).date());
                final double c = cashflows.get(i).amount();
                final double B = rate.discountFactor(t);

                P += c * B;
                switch (rate.compounding()) {
                case Simple:
                    dPdy -= c * B * B * t;
                    break;
                case Compounded:
                    dPdy -= c * B * t / (1 + y / N);
                    break;
                case Continuous:
                    dPdy -= c * B * t;
                    break;
                case SimpleThenCompounded:
                default:
                    throw new LibraryException(unsupported_compounding_type); // QA:[RG]::verified
                }
            }
        }

        if (P == 0.0)
            // no cashflows
            return 0.0;
        return -dPdy / P;
    }

    private double macaulayDuration(final Leg cashflows, final InterestRate rate, final Date settlementDate) {

        final double y = rate.rate();
        final int N = rate.frequency().toInteger();
        QL.require(rate.compounding().equals(Compounding.Compounded), compounded_rate_required);
        QL.require(N>=1, unsupported_frequency);
        return (1 + y / N) * modifiedDuration(cashflows, rate, settlementDate);
    }

    private int sign(final double x) {
        if (x == 0)
            return 0;
        else if (x > 0)
            return 1;
        else
            return -1;
    }

    final public int previousCashFlow(final Leg leg) {
        return previousCashFlow(leg, new Date());
    }

    final public int previousCashFlow(final Leg leg, Date refDate) {
        if (refDate.isNull()) {
            refDate = new Settings().evaluationDate();
        }

        if (!(leg.get(0).hasOccurred(refDate)))
            return leg.size();

        final int i = nextCashFlowIndex(leg, refDate);
        final Date beforeLastPaymentDate = leg.get(i - 1).date();// (*--i)->date()-1;
        return nextCashFlowIndex(leg, beforeLastPaymentDate);
    }

    final public double previousCouponRate(final Leg cashFlows) {
        return previousCouponRate(cashFlows, new Date());
    }

    final public double previousCouponRate(final Leg cashFlows, final Date settlement) {
        final int cf = previousCashFlow(cashFlows, settlement);
        return couponRate(cashFlows, cashFlows, cf);
    }

    final public double nextCouponRate(final Leg leg) {
        return nextCouponRate(leg, new Date());
    }

    final public double nextCouponRate(final Leg cashFlows, final Date settlement) {
        final int cf = nextCashFlowIndex(cashFlows, settlement);
        return couponRate(cashFlows, cashFlows, cf);
    }

    /**
     * NOTE: should return null when no cashflow could be found!
     *
     * @param cashFlows
     * @param settlement
     * @return
     */
    final public CashFlow nextCashFlow(final Leg cashFlows, Date settlement) {
        if (settlement.isNull()) {
            settlement = new Settings().evaluationDate();
        }
        for (int i = 0; i < cashFlows.size(); ++i) {
            // the first coupon paying after d is the one we're after
            if (!cashFlows.get(i).hasOccurred(settlement))
                return cashFlows.get(i);
        }
        return null;// cashFlows.get(cashFlows.size());
    }

    /**
     * NOTE: returns the index! for cashflow.end() the returned index would
     * throw a index out of bounds exception
     *
     * @param cashFlows
     * @param settlement
     * @return
     */
    final public int nextCashFlowIndex(final Leg cashFlows, Date settlement) {
        if (settlement.isNull()) {
            settlement = new Settings().evaluationDate();
        }
        for (int i = 0; i < cashFlows.size(); ++i) {
            // the first coupon paying after d is the one we're after
            if (!cashFlows.get(i).hasOccurred(settlement))
                return i;
        }
        return cashFlows.size();
    }

    final public CashFlow nextCashFlow(final Leg cashFlows) {
        return nextCashFlow(cashFlows, new Date());
    }

    /**
     * Yield value of a basis point The yield value of a one basis point change
     * in price is the derivative of the yield with respect to the price
     * multiplied by 0.01
     *
     * @param leg
     * @param y
     * @param settlmentDate
     * @return
     */
    final public double yieldValueBasisPoint(final Leg leg, final InterestRate y, final Date settlementDate) {
        final double shift = 0.01;

        final double dirtyPrice = npv(leg, y, settlementDate);
        final double modifiedDuration = duration(leg, y, Duration.Modified, settlementDate);

        return (1.0 / (-dirtyPrice * modifiedDuration)) * shift;
    }

    final public double yieldValueBasisPoint(final Leg leg, final InterestRate y) {
        return yieldValueBasisPoint(leg, y, new Date());

    }

    // utility functions
    final public double couponRate(final Leg leg, final Leg iteratorLeg, final int iteratorIndex) {
        if (iteratorLeg.size() <= iteratorIndex)
            return 0.0;

        final Date paymentDate = iteratorLeg.get(iteratorIndex).date();
        boolean firstCouponFound = false;
        /* @Real */double nominal = Constants.NULL_REAL;
        /* @Time */double accrualPeriod = Constants.NULL_TIME;
        DayCounter dc = null;
        /* @Rate */double result = 0.0;

        for (int i = iteratorIndex; i < leg.size(); i++) {
            final CashFlow cf = iteratorLeg.get(i);
            if (cf.date().eq(paymentDate)) {
                if (cf instanceof Coupon) {
                    final Coupon cp = (Coupon) cf;
                    if (firstCouponFound) {
                        QL.require(nominal == cp.nominal() && accrualPeriod == cp.accrualPeriod() && dc == cp.dayCounter() , "cannot aggregate two different coupons");  // TODO: message
                    } else {
                        firstCouponFound = true;
                        nominal = cp.nominal();
                        accrualPeriod = cp.accrualPeriod();
                        dc = cp.dayCounter();
                    }
                    result += cp.rate();
                }
            }
        }
        QL.ensure((firstCouponFound) , "next cashflow (" + paymentDate + ") is not a coupon"); // TODO: message
        return result;
    }


    //
    // private methods
    //


    /**
     * Basis-point value Obtained by setting dy = 0.0001 in the 2nd-order Taylor
     * series expansion.
     *
     * @param leg
     * @param y
     * @param settlementDate
     * @return
     */
    final private double basisPointValue(final Leg leg, final InterestRate y, final Date settlementDate) {
        /* @Real */final double shift = 0.0001;
        /* @Real */final double dirtyPrice = npv(leg, y, settlementDate);
        /* @Real */final double modifiedDuration = duration(leg, y, Duration.Modified, settlementDate);
        /* @Real */final double convexity = convexity(leg, y, settlementDate);

        /* @Real */double delta = -modifiedDuration * dirtyPrice;

        /* @Real */double gamma = (convexity / 100.0) * dirtyPrice;

        delta *= shift;
        gamma *= shift * shift;

        return delta + 0.5 * gamma;
    }

    final private double basisPointValue(final Leg leg, final InterestRate y) {
        return basisPointValue(leg, y, new Date());
    }



    //
    // public Enums
    //

    /**
     * Duration type
     */
    public enum Duration {
        Simple, Macaulay, Modified
    }


    //
    // private inner classes
    //

    private class IRRFinder implements Ops.DoubleOp {

        private final Leg cashflows_;
        private final double marketPrice_;
        private final DayCounter dayCounter_;
        private final Compounding compounding_;
        private final Frequency frequency_;
        private final Date settlementDate_;

        public IRRFinder(final Leg cashflows, final double marketPrice, final DayCounter dayCounter, final Compounding compounding,
                final Frequency frequency, final Date settlementDate) {
            this.cashflows_ = cashflows;
            this.marketPrice_ = marketPrice;
            this.dayCounter_ = dayCounter;
            this.compounding_ = compounding;
            this.frequency_ = frequency;
            this.settlementDate_ = settlementDate;
        }

        @Override
        public double op(final double guess) {
            final InterestRate rate = new InterestRate(guess, dayCounter_, compounding_, frequency_);
            final double NPV = npv(cashflows_, rate, settlementDate_);
            return marketPrice_ - NPV;
        }
    }

    private class BPSCalculator implements PolymorphicVisitor {

        private static final String UNKNOWN_VISITABLE = "unknow visitable object";

        private final Handle<YieldTermStructure> termStructure;
        private final Date npvDate;

        private double result;

        public BPSCalculator(final Handle<YieldTermStructure> termStructure, final Date npvDate) {
            this.termStructure = termStructure;
            this.npvDate = npvDate;
            this.result = 0.0;
        }

        public double result() {
            if (npvDate.isNull())
                return result;
            else
                return result / termStructure.currentLink().discount(npvDate);
        }

        //
        // private inner classes
        //

        private class CashFlowVisitor implements Visitor<CashFlow> {
            @Override
            public void visit(final CashFlow o) {
                // nothing
            }
        }

        private class CouponVisitor implements Visitor<CashFlow> {
            @Override
            public void visit(final CashFlow o) {
                final Coupon c = (Coupon) o;
                result += c.accrualPeriod() * c.nominal() * termStructure.currentLink().discount(c.date());
            }
        }

        //
        // implements PolymorphicVisitor
        //

        @Override
        public <CashFlow> Visitor<CashFlow> visitor(final Class<? extends CashFlow> klass) {

            //FIXME
            // Coupon is a CashFlow, therefore any Coupon types will never get to the CashFlowVisitor.
            // This may be fine for now, but could become problematic if other types are introduced.

            if (Coupon.class.isAssignableFrom (klass))
                return (Visitor<CashFlow>) new CouponVisitor();
            if (org.jquantlib.cashflow.CashFlow.class.isAssignableFrom (klass))
                return (Visitor<CashFlow>) new CashFlowVisitor();
            throw new LibraryException(UNKNOWN_VISITABLE); // QA:[RG]::verified
        }
    }
}
