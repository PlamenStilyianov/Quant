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

package org.jquantlib.processes;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.cashflow.CashFlow;
import org.jquantlib.cashflow.IborCoupon;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.time.Date;

/**
 * Libor-forward-model process
 * <p>
 * Stochastic process of a libor forward model using the rolling forward measure including predictor-corrector step
 * <p>
 * References:
 * <li>Glasserman, Paul, 2004, Monte Carlo Methods in Financial Engineering, Springer, Section 3.7</li>
 * <li>Antoon Pelsser, 2000, Efficient Methods for Valuing Interest Rate Derivatives, Springer, 8</li>
 * <li>Hull, John, White, Alan, 1999, Forward Rate Volatilities, Swap Rate Volatilities and the Implementation of the Libor Market
 * Model<li>
 *
 * @see <a href="http://www.rotman.utoronto.ca/~amackay/fin/libormktmodel2.pdf">FORWARD RATE VOLATILITIES, SWAP RATE VOLATILITIES,
 *      AND THE IMPLEMENTATION OF THE LIBOR MARKET MODEL</a>
 *
 * @category processes
 *
 * @author Ueli Hofstetter
 */
// TODO: license, class comments, access modifiers organization, good formatting
public class LiborForwardModelProcess extends StochasticProcess {

    //Exception messages
    private static final String wrong_number_of_cashflows = "wrong number of cashflows";
    private static final String irregular_coupon_types = "irregular coupon types are not suppported";

    private final int size_;
    private final IborIndex index_;
    private LfmCovarianceParameterization lfmParam_;
    private final Array initialValues_;
    private final List</*@Time*/Double> fixingTimes_;
    private final List</*@Time*/Date> fixingDates_;
    private final List</*@Time*/Double> accrualStartTimes_;
    private final List</*@Time*/Double> accrualEndTimes_;
    private final List</*@Time*/Double> accrualPeriod_;

    private final  Array m1, m2;

    public LiborForwardModelProcess(final int size, final IborIndex  index) {
        super(new EulerDiscretization());

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        this.size_ = size;
        this.index_ = index;
        this.initialValues_ = new Array(size_);
        this.fixingDates_ = new ArrayList<Date>(size_);
        this.fixingTimes_ = new ArrayList<Double>(size_);
        this.accrualStartTimes_ = new ArrayList<Double>(size_);
        this.accrualEndTimes_ = new ArrayList<Double>(size_);
        this.accrualPeriod_ = new ArrayList<Double>(size_);
        this.m1 = new Array(size_);
        this.m2 = new Array(size_);

        final DayCounter dayCounter = index_.dayCounter();
        final List<CashFlow> flows = null /* cashFlows() */; // FIXME: translate cashFlows();

        QL.require(this.size_ == flows.size() , wrong_number_of_cashflows); // TODO: message

        final Date settlement = index_.termStructure().currentLink().referenceDate();
        final Date startDate = ((IborCoupon) flows.get(0)).fixingDate();
        for (int i = 0; i < size_; ++i) {
            final IborCoupon coupon = (IborCoupon) flows.get(i);
            QL.require(coupon.date().eq(coupon.accrualEndDate()) , irregular_coupon_types); // TODO: message

            initialValues_.set(i, coupon.rate());
            accrualPeriod_.set(i, coupon.accrualPeriod());

            fixingDates_.set(i, coupon.fixingDate());
            fixingTimes_.set(i, dayCounter.yearFraction(startDate, coupon.fixingDate()));
            accrualStartTimes_.set(i, dayCounter.yearFraction(settlement, coupon.accrualStartDate()));
            accrualEndTimes_.set(i, dayCounter.yearFraction(settlement, coupon.accrualEndDate()));
        }
    }


    //
    // public methods
    //

    public void setCovarParam(final LfmCovarianceParameterization  param) {
        lfmParam_ = param;
    }

    public LfmCovarianceParameterization covarParam()  {
        return lfmParam_;
    }

    public IborIndex index() {
        return index_;
    }


    //
    // Overrides StochasticProcess
    //

    @Override
    public Array initialValues()  {
        return initialValues_.clone();
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Array drift(/* @Time */final double t, final Array x) {
        final Array f = new Array(size_);
        final Matrix covariance = lfmParam_.covariance(t, x);
        final int m = 0;//NextA
        for (int k = m; k < size_; ++k) {
            m1.set(k, accrualPeriod_.get(k) * x.get(k) / (1 + accrualPeriod_.get(k) * x.get(k)));
            final double value = m1.innerProduct(covariance.constRangeCol(k), m, k+1-m) - 0.5 * covariance.get(k, k);
            f.set(k, value);
        }
        return f;
    }

    @Override
    public Matrix diffusion(/*@Time*/ final double t, final Array x){
        return lfmParam_.diffusion(t, x);
    }

    @Override
    public Matrix covariance(/*@Time*/final double t, final Array x, /*@Time*/ final double dt){
        return lfmParam_.covariance(dt, x).mul(lfmParam_.covariance(dt, x).mulAssign(dt));
    }

    @Override
    public Array apply(final Array x0, final Array dx){
        final Array tmp = new Array(size_);
        for(int k = 0; k<size_; ++k) {
            tmp.set(k, x0.get(k)*Math.exp(dx.get(k)));
        }
        return tmp;
    }

    @Override
    public Array evolve(/*@Time*/ final double t0, final Array x0, /*@Time*/ final double dt, final Array dw)  {

        //FIXME:: code review against QuantLib/C++

        /* predictor-corrector step to reduce discretization errors.

           Short - but slow - solution would be

           Array rnd_0     = stdDeviation(t0, x0, dt)*dw;
           Array drift_0   = discretization_->drift(*this, t0, x0, dt);

           return apply(x0, ( drift_0 + discretization_
                ->drift(*this,t0,apply(x0, drift_0 + rnd_0),dt) )*0.5 + rnd_0);

           The following implementation does the same but is faster.
        */

        if (true)
            throw new UnsupportedOperationException("work in progress");
        final int m   = 0;//nextIndexReset(t0);
        final double sdt = Math.sqrt(dt);

        final Array f = x0.clone();
        final Matrix diff       = lfmParam_.diffusion(t0, x0);
        final Matrix covariance = lfmParam_.covariance(t0, x0);

        // TODO: review iterators
        for (int k=m; k<size_; ++k) {
            final double y = accrualPeriod_.get(k)*x0.get(k);
            m1.set(k,y/(1+y));

            final double d = (m1.innerProduct(covariance.constRangeCol(k), m, k+1-m)-0.5*covariance.get(k, k)) * dt;
            final double r = diff.rangeRow(k).innerProduct(dw)*sdt;
            final double x = y*Math.exp(d + r);
            m2.set(k, x/(1+x));

            final double ip = m2.innerProduct(covariance.constRangeCol(k), m, k+1-m);
            final double value = x0.get(k) * Math.exp(0.5*(d+ip-0.5*covariance.get(k,k))*dt) + r;
            f.set(k, value);
        }

        return f;
    }
//    /*
//    private /*Size*/ int nextIndexReset(/*Time*/ final double t)  {
//        return Std.getInstance().
//                 - fixingTimes_.begin();
//    }


}





/*
    public List<CashFlow> cashFlows{

        final Date refDate = index_.getTermStructure().getLink().referenceDate();

        List<CashFlow> floatingLeg = Leg.FloatingLe, schedule, index, paymentDayCounter, paymentAdj, fixingDays, gearings, spreads, caps, floors, isInArrears)(
                   std::vector<Real>(1, amount),
                   Schedule(refDate,
                            refDate + Period(index_->tenor().length()*size_,
                                             index_->tenor().units()),
                            index_->tenor(), index_->fixingCalendar(),
                            index_->businessDayConvention(),
                            index_->businessDayConvention(), false, false),
                   index_,
                   index_->dayCounter(),
                   index_->businessDayConvention(),
                   index_->fixingDays());
        boost::shared_ptr<IborCouponPricer>
                        fictitiousPricer(new BlackIborCouponPricer(Handle<CapletVolatilityStructure>()));
        setCouponPricer(floatingLeg,fictitiousPricer);
        return floatingLeg;

    }

    Size LiborForwardModelProcess::size() const {
        return size_;
    }

    Size LiborForwardModelProcess::factors() const {
        return lfmParam_->factors();
    }

    const std::vector<Time> & LiborForwardModelProcess::fixingTimes() const {
        return fixingTimes_;
    }

    const std::vector<Date> & LiborForwardModelProcess::fixingDates() const {
        return fixingDates_;
    }

    const std::vector<Time> &
    LiborForwardModelProcess::accrualStartTimes() const {
        return accrualStartTimes_;
    }

    const std::vector<Time> &
    LiborForwardModelProcess::accrualEndTimes() const {
        return accrualEndTimes_;
    }

    Size LiborForwardModelProcess::nextIndexReset(Time t) const {
        return std::upper_bound(fixingTimes_.begin(), fixingTimes_.end(), t)
                 - fixingTimes_.begin();
    }

    std::vector<DiscountFactor> LiborForwardModelProcess::discountBond(
        const std::vector<Rate> & rates) const {

        std::vector<DiscountFactor> discountFactors(size_);
        discountFactors[0] = 1.0/(1.0 + rates[0]*accrualPeriod_[0]);

        for (Size i = 1; i < size_; ++i) {
            discountFactors[i] =
                discountFactors[i-1]/(1.0 + rates[i]*accrualPeriod_[i]);
        }

        return discountFactors;
    }*/

