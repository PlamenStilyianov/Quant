package org.jquantlib.cashflow;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Schedule;

// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class FixedRateLeg extends Leg {

    private final Schedule schedule_;
    private double[] notionals_;
    private InterestRate[] couponRates_;
    private final DayCounter paymentDayCounter_;
    private DayCounter firstPeriodDayCounter_;
    private BusinessDayConvention paymentAdjustment_;

    public FixedRateLeg(final Schedule schedule, final DayCounter paymentDayCounter){
        this.schedule_=(schedule);
        this.paymentDayCounter_=(paymentDayCounter);
        this.paymentAdjustment_ = BusinessDayConvention.Following;
    }

    public FixedRateLeg withNotionals(/* Real */final double notional) {
        this.notionals_ = new double[] {notional};
        return this;
    }

    public FixedRateLeg withNotionals(final double[]/*List<Double>*/ notionals) {
        this.notionals_ = notionals; // TODO: clone() ?
        return this;
    }

    public FixedRateLeg withCouponRates(/* @Rate */final double couponRate) {
        couponRates_ = new InterestRate[]{new InterestRate(couponRate, paymentDayCounter_, Compounding.Simple)};

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        //        couponRates_.clear();
        //        couponRates_.set(0, new InterestRate(couponRate, paymentDayCounter_, Compounding.SIMPLE));
        return this;
    }

    public FixedRateLeg withCouponRates(final InterestRate couponRate) {
        couponRates_ = new InterestRate[]{couponRate};
        return this;
    }

    public FixedRateLeg withCouponRates(/* @Rate */final double [] couponRates) {
        couponRates_ = new InterestRate[couponRates.length];
        for (int i = 0; i<couponRates.length; i++) {
            couponRates_[i] = new InterestRate(couponRates[i], paymentDayCounter_, Compounding.Simple);
        }
        return this;
    }

    public FixedRateLeg withCouponRates(final InterestRate [] couponRates) {
        couponRates_ = couponRates; // TODO: clone() ?
        return this;
    }

    public FixedRateLeg withPaymentAdjustment(final BusinessDayConvention convention) {
        paymentAdjustment_ = convention;
        return this;
    }

    public FixedRateLeg withFirstPeriodDayCounter(final DayCounter dayCounter) {
        firstPeriodDayCounter_ = dayCounter;
        return this;
    }


    public Leg Leg() {
        QL.require(couponRates_ != null && couponRates_.length>0 , "coupon rates not specified"); // TODO: message
        QL.require(notionals_   != null && notionals_.length>0 , "nominals not specified"); // TODO: message

        final Leg leg = new Leg();

        // the following is not always correct
        final Calendar calendar = schedule_.calendar();

        // first period might be short or long
        Date start = schedule_.date(0), end = schedule_.date(1);
        Date paymentDate = calendar.adjust(end, paymentAdjustment_);
        InterestRate rate = couponRates_[0];
        /*@Real*/ double nominal = notionals_[0];
        if (schedule_.isRegular(1)) {
            // TODO: code review :: please verify against QL/C++ code
            QL.require(firstPeriodDayCounter_==null || !firstPeriodDayCounter_.equals(paymentDayCounter_) , "regular first coupon does not allow a first-period day count"); // TODO: message
            leg.add(new FixedRateCoupon(nominal, paymentDate, rate, paymentDayCounter_, start, end, start, end));
        } else {
            Date ref = end.sub(schedule_.tenor());
            ref = calendar.adjust(ref, schedule_.businessDayConvention());
            // FIXME: empty() method on dayCounter missing --> substituted by == null (probably incorrect)
            final DayCounter dc = (firstPeriodDayCounter_ == null) ? paymentDayCounter_ : firstPeriodDayCounter_;
            leg.add(new FixedRateCoupon(nominal, paymentDate, rate, dc, start, end, ref, end));
        }
        // regular periods
        for (int i = 2; i < schedule_.size() - 1; ++i) {
            start = end;
            end = schedule_.date(i);
            paymentDate = calendar.adjust(end, paymentAdjustment_);
            if ((i - 1) < couponRates_.length) {
                rate = couponRates_[i - 1];
            } else {
                rate = couponRates_[couponRates_.length - 1];
            }
            if ((i - 1) < notionals_.length) {
                nominal = notionals_[i - 1];
            } else {
                nominal = notionals_[notionals_.length - 1];
            }
            leg.add(new FixedRateCoupon(nominal, paymentDate, rate, paymentDayCounter_, start, end, start, end));
        }

        if (schedule_.size() > 2) {
            // last period might be short or long
            final int N = schedule_.size();
            start = end;
            end = schedule_.date(N - 1);
            paymentDate = calendar.adjust(end, paymentAdjustment_);
            if ((N - 2) < couponRates_.length) {
                rate = couponRates_[N - 2];
            } else {
                rate = couponRates_[couponRates_.length - 1];
            }
            if ((N - 2) < notionals_.length) {
                nominal = notionals_[N - 2];
            } else {
                nominal = notionals_[notionals_.length - 1];
            }
            if (schedule_.isRegular(N - 1)) {
                leg.add(new FixedRateCoupon(nominal, paymentDate, rate, paymentDayCounter_, start, end, start, end));
            } else {
                Date ref = start.add(schedule_.tenor());
                ref = calendar.adjust(ref, schedule_.businessDayConvention());
                leg.add(new FixedRateCoupon(nominal, paymentDate, rate, paymentDayCounter_, start, end, start, ref));
            }
        }
        return leg;
    }
}
