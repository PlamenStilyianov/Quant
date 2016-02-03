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
package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.indexes.InterestRateIndex;
import org.jquantlib.instruments.Option;
import org.jquantlib.pricingengines.BlackFormula;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;
import org.jquantlib.time.Date;

/*
 * DONE!
 */


public class BlackIborCouponPricer extends IborCouponPricer {

    private final static String missing_caplet_volatility = "missing caplet volatility";

    public BlackIborCouponPricer(final Handle<OptionletVolatilityStructure> capletVol) {
        super(capletVol);
    }

    private IborCoupon coupon_;
    private double discount_;
    private double gearing_;
    private double spread_;
    private double spreadLegValue_;


    @Override
    public void initialize( final FloatingRateCoupon coupon) {
        coupon_ =  (IborCoupon)coupon;
        gearing_ = coupon_.gearing();
        spread_ = coupon_.spread();
        final Date paymentDate = coupon_.date();
        final InterestRateIndex index = coupon_.index();
        final Handle<YieldTermStructure> rateCurve = index.termStructure();

        final Date today = new Settings().evaluationDate();

        if(paymentDate.gt(today))
            discount_ = rateCurve.currentLink().discount(paymentDate);
        else
            discount_ = 1.0;
        spreadLegValue_ = spread_ * coupon_.accrualPeriod()* discount_;
    }

    @Override
    public double swapletPrice() {
        // past or future fixing is managed in InterestRateIndex::fixing()
        final double swapletPrice = adjustedFixing()* coupon_.accrualPeriod()* discount_;
        return gearing_ * swapletPrice + spreadLegValue_;
    }

    @Override
    public double swapletRate()  {
        return swapletPrice()/(coupon_.accrualPeriod()*discount_);
    }

    @Override
    public double capletPrice(final double effectiveCap)  {
        final double capletPrice = optionletPrice(Option.Type.Call, effectiveCap);
        return gearing_ * capletPrice;
    }

    @Override
    public double capletRate(final double effectiveCap) {
        return capletPrice(effectiveCap)/(coupon_.accrualPeriod()*discount_);
    }

    @Override
    public double floorletPrice(final double effectiveFloor)  {
        final double floorletPrice = optionletPrice(Option.Type.Put, effectiveFloor);
        return gearing_ * floorletPrice;
    }

    @Override
    public double floorletRate(final double effectiveFloor) {
        return floorletPrice(effectiveFloor)/
        (coupon_.accrualPeriod()*discount_);
    }

    public double optionletPrice(final Option.Type optionType, final double effStrike)  {
        final Date fixingDate = coupon_.fixingDate();
        if (fixingDate.le(new Settings().evaluationDate())) {
            // the amount is determined
            double a, b;
            if (optionType==Option.Type.Call) {
                a = coupon_.indexFixing();
                b = effStrike;
            } else {
                a = effStrike;
                b = coupon_.indexFixing();
            }
            return Math.max(a - b, 0.0)* coupon_.accrualPeriod()*discount_;
        } else {
            QL.require(capletVolatility()!=null , missing_caplet_volatility); // TODO: message
            // not yet determined, use Black model
            final double fixing =
                BlackFormula.blackFormula(
                        optionType,
                        effStrike,
                        adjustedFixing(),
                        Math.sqrt(capletVolatility().currentLink().blackVariance(fixingDate,
                                effStrike)));
            return fixing * coupon_.accrualPeriod()*discount_;
        }
    }

    public double adjustedFixing() {

        double adjustement = 0.0;

        final double fixing = coupon_.indexFixing();

        if (!coupon_.isInArrears())
            adjustement = 0.0;
        else {
            // see Hull, 4th ed., page 550
            QL.require(capletVolatility() != null , missing_caplet_volatility); // TODO: message
            final Date d1 = coupon_.fixingDate();
            final Date referenceDate = capletVolatility().currentLink().referenceDate();
            if (d1.le(referenceDate))
                adjustement = 0.0;
            else {
                final Date d2 = coupon_.index().maturityDate(d1);
                final double tau = coupon_.index().dayCounter().yearFraction(d1, d2);
                final double variance = capletVolatility().currentLink().blackVariance(d1, fixing);
                adjustement = fixing*fixing*variance*tau/(1.0+fixing*tau);
            }
        }
        return fixing + adjustement;
    }


}
