package org.jquantlib.samples;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.termstructures.yieldcurves.ForwardRateStructure;
import org.jquantlib.termstructures.yieldcurves.ForwardSpreadedTermStructure;
import org.jquantlib.termstructures.yieldcurves.ImpliedTermStructure;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.calendars.UnitedStates;
import org.jquantlib.time.calendars.UnitedStates.Market;

/**
 * This class explores the functionalities provided by yield termstructures.The different types
 * of yield termstructures covered in this class are as shown below-
 * (1)FlatForward
 * (2)ForwardSpreadedTermStructure
 * (3)ImpliedTermStructure
 * (4)InterpolatedDiscountCurve
 * (5)InterpolatedForwardCurve
 * (6)InterpolatedZeroCurve
 * (7)PiecewiseYieldCurve
 * (8)PiecewiseYieldDiscountCurve
 * (9)ZeroSpreadedTermStructure
 *
 * @author Apratim Rajendra
 *
 */
public class YieldCurveTermStructures implements Runnable {

    public static void main(final String[] args) {
        new YieldCurveTermStructures().run();
    }

    @Override
    public void run() {

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();

        System.out.println("//==========================================FlatForward termstructure===================");
        final SimpleQuote interestRateQuote = new SimpleQuote(0.3);
        final RelinkableHandle<Quote>  handleToInterestRateQuote = new RelinkableHandle<Quote>(interestRateQuote);
        final YieldTermStructure flatforward = new FlatForward(2,new UnitedStates(Market.NYSE),handleToInterestRateQuote,new Actual365Fixed(), Compounding.Continuous,Frequency.Daily);

        final Date today  = Date.todaysDate();
        final Date date10 = today.clone().addAssign(10);
        final Date date20 = today.clone().addAssign(20);
        final Date date30 = today.clone().addAssign(30);
        final Date date40 = today.clone().addAssign(40);
        final Date date50 = today.clone().addAssign(50);


        //Calculating discount factor
        System.out.println("The discount factor for the date 30 days from today is = "+flatforward.discount(date30.clone()));

        //Calculating forward rate
        System.out.println("The forward rate between the date 30 days from today to 50 days from today is = "+flatforward.forwardRate(date30.clone(), date50.clone(), new Actual365Fixed(), Compounding.Continuous).rate());

        //Calculating parRate for the dates as shown below-
        final Date[] dates = { date10.clone(), date20.clone(), date30.clone(), date40.clone(), date50.clone() };
        System.out.println("The par rate for the bond having coupon dates as shown above is = "+flatforward.parRate(dates, Frequency.Semiannual, false));

        //Calculating zero rate
        System.out.println("The zero rate for the bond having coupon date as 10 days from today = "+flatforward.zeroRate(date10.clone(), new Actual365Fixed(), Compounding.Continuous).rate());

        System.out.println("//==========================================ForwardSpreadedTermStructure==================");
        //As the name suggests this termstructure adds a spread to the forward rates it calculates by getting the spread rate
        //from the spread rate quote
        final SimpleQuote spreadRateQuote = new SimpleQuote(0.2);
        final RelinkableHandle<Quote>  handleToSpreadRateQuote = new RelinkableHandle<Quote>(spreadRateQuote);

        final ForwardRateStructure forwardSpreadedTermStructure = new ForwardSpreadedTermStructure(new RelinkableHandle<YieldTermStructure>(flatforward),handleToSpreadRateQuote);

        //Calculating discount factor.This termstructure adds the spread as specified by the spread quote and then calculates the discount.
        System.out.println("The discount factor for the date 30 days from today is = "+forwardSpreadedTermStructure.discount(date30.clone()));

        //Calculating forward rate.This termstructure adds the spread as specified by the spread quote and then calculates the discount.
        System.out.println("The forward rate between the date 30 days from today to 50 days from today is = "+forwardSpreadedTermStructure.forwardRate(date30.clone(), date50.clone(), new Actual365Fixed(), Compounding.Continuous).rate());

        //Calculating parRate for the dates(as used in the FlatForward case) as shown below-
        System.out.println("The par rate for the bond having coupon dates as shown above is = "+forwardSpreadedTermStructure.parRate(dates, Frequency.Semiannual, false));

        //Calculating zero rate.This termstructure adds the spread as specified by the spread quote and then calculates the discount.
        System.out.println("The zero rate for the bond having coupon date as 10 days from today = "+forwardSpreadedTermStructure.zeroRate(date10.clone(), new Actual365Fixed(), Compounding.Continuous).rate());

        //===========================================ImpliedTermStructure============================

        //As the name suggests the implied termstructure holds a reference to an underlying tremstructure and gives the same calulated values
        //as the underlying termstructure.Here the FlatForward termstructure instantiated right at the top has been taken as an underlying.
        final YieldTermStructure impliedTermStructure = new ImpliedTermStructure(new RelinkableHandle<YieldTermStructure>(flatforward), today);
        //TODO as the code has to be updated for the implied term structure.

        //===========================================InterpolatedDiscountCurve=======================
        //TODO as the code has to be updated for the InterpolatedDiscountCurve

        //===========================================InterpolatedForwardCurve========================
        //TODO as the code has to be updated for the InterpolatedForwardCurve

        //===========================================InterpolatedZeroCurve===========================
        //TODO as the code has to be updated for the InterpolatedZeroCurve

        //===========================================PiecewiseYieldCurve=============================
        //TODO as the code has to be updated for the PiecewiseYieldCurve

        //===========================================PiecewiseYieldDiscountCurve=====================
        //TODO as the code has to be updated for the PiecewiseYieldDiscountCurve

        //===========================================ZeroSpreadedTermStructure=======================
        //TODO as the code has to be updated for the PiecewiseYieldDiscountCurve



        clock.stopClock();
        clock.log();

    }

}
