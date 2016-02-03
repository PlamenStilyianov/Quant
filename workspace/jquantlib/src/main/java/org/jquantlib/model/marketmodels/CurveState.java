package org.jquantlib.model.marketmodels;

import org.jquantlib.QL;


/**
 * Curve state for market-model simulations
 * <p>
 * This class stores the state of the yield curve associated to the fixed calendar times within the simulation. This is the
 * workhorse discounting object associated to the rate times of the simulation. It's important to pass the rates via an object like
 * this to the product rather than directly to make it easier to switch to other engines such as a coterminal swap rate engine. Many
 * products will not need expired rates and others will only require the first rate.
 *
 * @author Ueli Hofstetter
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class CurveState {

    protected int numberOfRates_;
    protected /*@Time*/double [] rateTimes_, rateTaus_;

    // There will n+1 rate times expressing payment and reset times of forward rates.
    //
    //            |-----|-----|-----|-----|-----|      (size = 6)
    //            t0    t1    t2    t3    t4    t5     rateTimes
    //            f0    f1    f2    f3    f4           forwardRates
    //            d0    d1    d2    d3    d4    d5     discountBonds
    //            d0/d0 d1/d0 d2/d0 d3/d0 d4/d0 d5/d0  discountRatios
    //            sr0   sr1   sr2   sr3   sr4          cotSwaps

    public CurveState(final  /*@Time*/ double []  rateTimes){

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        numberOfRates_ = rateTimes==null || rateTimes.length == 0 ? 0 : rateTimes.length-1;
        rateTimes_ = (rateTimes); // TODO: clone() ?
        rateTaus_ = new double[numberOfRates_];
        //checkIncreasingTimesAndCalculateTaus(rateTimes_, rateTaus_);
    }

    public /*@Rate*/ double swapRate(final int begin, final int end)  {
        QL.require(end > begin , "empty range specified"); // TODO: message
        QL.require(end <= numberOfRates_ , "taus/end mismatch"); // TODO: message

        final double sum = 0.0;

        return 0;
//        for (int i=begin; i<end; ++i)
//            sum += rateTaus_[i]*discountRatio(i+1, numberOfRates_);
//
//        return (discountRatio(begin, numberOfRates_)-discountRatio(end, numberOfRates_))/sum;
    }

//    void forwardsFromDiscountRatios(const Size firstValidIndex,
//                                    const std::vector<DiscountFactor>& ds,
//                                    const std::vector<Time>& taus,
//                                    std::vector<Rate>& fwds) {
//        QL_REQUIRE(taus.size()==fwds.size(),
//                   "taus.size()!=fwds.size()");
//        QL_REQUIRE(ds.size()==fwds.size()+1,
//                   "ds.size()!=fwds.size()+1");
//
//        for (Size i=firstValidIndex; i<fwds.size(); ++i)
//            fwds[i] = (ds[i]-ds[i+1])/(ds[i+1]*taus[i]);
//    }
//
//    void coterminalFromDiscountRatios(
//                    const Size firstValidIndex,
//                    const std::vector<DiscountFactor>& discountFactors,
//                    const std::vector<Time>& taus,
//                    std::vector<Rate>& cotSwapRates,
//                    std::vector<Real>& cotSwapAnnuities)
//    {
//        Size nCotSwapRates = cotSwapRates.size();
//        QL_REQUIRE(taus.size()==nCotSwapRates,
//                   "taus.size()!=cotSwapRates.size()");
//        QL_REQUIRE(cotSwapAnnuities.size()==nCotSwapRates,
//                   "cotSwapAnnuities.size()!=cotSwapRates.size()");
//        QL_REQUIRE(discountFactors.size()==nCotSwapRates+1,
//                   "discountFactors.size()!=cotSwapRates.size()+1");
//
//        cotSwapAnnuities[nCotSwapRates-1] =
//            taus[nCotSwapRates-1]*discountFactors[nCotSwapRates];
//        cotSwapRates[nCotSwapRates-1] =
//            (discountFactors[nCotSwapRates-1]-discountFactors[nCotSwapRates])
//                /cotSwapAnnuities[nCotSwapRates-1];
//
//        for (Size i=nCotSwapRates-1; i>firstValidIndex; --i) {
//            cotSwapAnnuities[i-1] = cotSwapAnnuities[i] + taus[i-1] * discountFactors[i];
//            cotSwapRates[i-1] =
//                (discountFactors[i-1]-discountFactors[nCotSwapRates])
//                /cotSwapAnnuities[i-1];
//        }
//    }
//
//
//    void constantMaturityFromDiscountRatios(// Size i, // to be added later
//                                            const Size spanningForwards,
//                                            const Size firstValidIndex,
//                                            const std::vector<DiscountFactor>& ds,
//                                            const std::vector<Time>& taus,
//                                            std::vector<Rate>& constMatSwapRates,
//                                            std::vector<Real>& constMatSwapAnnuities) {
//        Size nConstMatSwapRates = constMatSwapRates.size();
//        QL_REQUIRE(taus.size()==nConstMatSwapRates,
//                   "taus.size()!=nConstMatSwapRates");
//        QL_REQUIRE(constMatSwapAnnuities.size()==nConstMatSwapRates,
//                   "constMatSwapAnnuities.size()!=nConstMatSwapRates");
//        QL_REQUIRE(ds.size()==nConstMatSwapRates+1,
//                   "ds.size()!=nConstMatSwapRates+1");
//        // compute the first cmsrate and cmsannuity
//        constMatSwapAnnuities[firstValidIndex]=0.;
//        Size lastIndex = std::min(firstValidIndex+spanningForwards,nConstMatSwapRates);
//        for (Size i=firstValidIndex; i<lastIndex; ++i) {
//            constMatSwapAnnuities[firstValidIndex]+= taus[i] * ds[i+1];
//        }
//        constMatSwapRates[firstValidIndex] =
//            (ds[firstValidIndex]-ds[lastIndex])/
//                constMatSwapAnnuities[firstValidIndex];
//        Size oldLastIndex = lastIndex;
//
//        // compute all the other cmas rates and cms annuities
//        for (Size i=firstValidIndex+1; i<nConstMatSwapRates; ++i) {
//            Size lastIndex = std::min(i+spanningForwards,nConstMatSwapRates);
//            constMatSwapAnnuities[i] = constMatSwapAnnuities[i-1]
//                                       - taus[i-1] * ds[i];
//            if (lastIndex!=oldLastIndex)
//               constMatSwapAnnuities[i] += taus[lastIndex-1] * ds[lastIndex];
//            constMatSwapRates[i] = (ds[i]-ds[lastIndex])
//                /constMatSwapAnnuities[i];
//            oldLastIndex = lastIndex;
//        }
//    }

}
