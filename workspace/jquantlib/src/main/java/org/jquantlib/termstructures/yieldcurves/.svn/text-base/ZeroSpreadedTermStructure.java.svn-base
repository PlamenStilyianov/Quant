package org.jquantlib.termstructures.yieldcurves;


import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.InterestRate;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;

public class ZeroSpreadedTermStructure extends ZeroYieldStructure  {

    //
    // private fields
    //

    private final Handle<YieldTermStructure> originalCurve;
    private final Handle<Quote> spread;
    private final Compounding comp;
    private final Frequency freq;


    //
    // public constructors
    //

    public ZeroSpreadedTermStructure(
            final Handle<YieldTermStructure> h,
            final Handle<Quote> spread, final Compounding comp , final Frequency freq,
            final DayCounter dc){
        QL.validateExperimentalMode();

        this.originalCurve = h;
        this.spread = spread;
        this.comp = comp;
        this.freq = freq;

        this.originalCurve.addObserver(this);
        this.spread.addObserver(this);
    }


    //
    // public methods
    //

    public double forwardImpl(final double t){
        return originalCurve.currentLink().
        forwardRate(t, t, comp, freq, true).rate()
        + spread.currentLink().value();
    }


    //
    // overrides ZeroYieldStructure
    //

    @Override
    protected double zeroYieldImpl(final double t) {
        //org.comment: to be fixed: user-defined daycounter should be used
        final InterestRate zeroRate = originalCurve.currentLink().
        zeroRate(t, comp, freq, true);
        final InterestRate spreadedRate =
            new InterestRate(zeroRate.rate() + spread.currentLink().value(),
                    zeroRate.dayCounter(),
                    zeroRate.compounding(),
                    zeroRate.frequency());
        return spreadedRate.equivalentRate(t, Compounding.Continuous, Frequency.NoFrequency).rate();
    }


    //
    // overrides TermStructure
    //

    @Override
    public Calendar calendar() {
        return originalCurve.currentLink().calendar();
    }

    @Override
    public Date referenceDate() {
        return originalCurve.currentLink().referenceDate();
    }

    @Override
    public Date maxDate() {
        return originalCurve.currentLink().maxDate();
    }

    @Override
    public/* @Time */double maxTime() {
        return originalCurve.currentLink().maxTime();
    }

}