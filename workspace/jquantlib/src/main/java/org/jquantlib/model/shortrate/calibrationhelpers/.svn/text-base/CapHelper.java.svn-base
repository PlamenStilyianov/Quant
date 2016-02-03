package org.jquantlib.model.shortrate.calibrationhelpers;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.indexes.IborIndex;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.model.CalibrationHelper;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Date;
import org.jquantlib.time.DateGeneration;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.time.Schedule;
import org.jquantlib.util.DefaultObservable;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class CapHelper extends CalibrationHelper {

    public CapHelper(final Period length,
            final Handle<Quote> volatility,
            final IborIndex index,
            // data for ATM swap-rate calculation
            final Frequency fixedLegFrequency,
            final DayCounter fixedLegDayCounter,
            final boolean includeFirstSwaplet,
            final Handle<YieldTermStructure> termStructure){
        this(length, volatility, index, fixedLegFrequency, fixedLegDayCounter,
                includeFirstSwaplet,termStructure, false);
    }

    public CapHelper(final Period length,
            final Handle<Quote> volatility,
            final IborIndex index,
            // data for ATM swap-rate calculation
            final Frequency fixedLegFrequency,
            final DayCounter fixedLegDayCounter,
            final boolean includeFirstSwaplet,
            final Handle<YieldTermStructure> termStructure,
            final boolean calibrateVolatility){
        super(volatility, termStructure, calibrateVolatility);

        final Period indexTenor = index.tenor();
        final double fixedRate = 0.04; //dummy value
        Date startDate, maturity;
        if(includeFirstSwaplet){
            startDate = termStructure.currentLink().referenceDate();
            maturity = termStructure.currentLink().referenceDate().add(length);
        }
        else{
            startDate = termStructure.currentLink().referenceDate().add(indexTenor);
            maturity = termStructure.currentLink().referenceDate().add(length);
        }

        final IborIndex dummyIndex = new IborIndex("dummy",
                indexTenor,
                index.fixingDays(),
                index.currency(),
                index.fixingCalendar(),
                index.businessDayConvention(),
                index.endOfMonth(),
                termStructure.currentLink().dayCounter(),
                termStructure);

        final double [] nominals = {1,1.0};


        // TODO: code review :: please verify against QL/C++ code
        final Schedule fixedSchedule = new Schedule(
                startDate, maturity,
                new Period(fixedLegFrequency), index.fixingCalendar(),
                BusinessDayConvention.Unadjusted, BusinessDayConvention.Unadjusted,
                DateGeneration.Rule.Forward, false);

        //TODO: Code review :: incomplete code
        if (true)
            throw new UnsupportedOperationException("Work in progress");


        /*
        Leg floatingLeg = new IborL

         Leg fixedLeg = FixedRateLeg(nominals,
                                    fixedSchedule,
                                    std::vector<Rate>(1, fixedRate),
                                    fixedLegDayCounter,
                                    index->businessDayConvention());

        boost::shared_ptr<Swap> swap(
            new Swap(termStructure, floatingLeg, fixedLeg));
        Rate fairRate = fixedRate - swap->NPV()/(swap->legBPS(1)/1.0e-4);
        engine_  = boost::shared_ptr<PricingEngine>();
        cap_ = boost::shared_ptr<Cap>(new Cap(floatingLeg,
                                              std::vector<Rate>(1, fairRate),
                                              termStructure, engine_));
        marketValue_ = blackPrice(volatility_->value());*/


    }

    @Override
    public void addTimesTo(final ArrayList<Time> times) {
        // TODO Auto-generated method stub

    }

    @Override
    public double blackPrice(final double volatility) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double modelValue() {
        // TODO Auto-generated method stub
        return 0;
    }


    //
    // implements Observer
    //
    @Override
    public void update() {
        throw new UnsupportedOperationException("work in progress");
    }


    //
    // implements Observable
    //

    private final Observable delegatedObservable = new DefaultObservable(this);

    @Override
    public void addObserver(final Observer observer) {
        delegatedObservable.addObserver(observer);

    }

    @Override
    public int countObservers() {
        return delegatedObservable.countObservers();
    }

    @Override
    public void deleteObserver(final Observer observer) {
        delegatedObservable.deleteObservers();

    }

    @Override
    public void deleteObservers() {
        delegatedObservable.deleteObservers();

    }

    @Override
    public List<Observer> getObservers() {
        return delegatedObservable.getObservers();
    }

    @Override
    public void notifyObservers() {
        delegatedObservable.notifyObservers();

    }

    @Override
    public void notifyObservers(final Object arg) {
        delegatedObservable.notifyObservers(arg);

    }

}
