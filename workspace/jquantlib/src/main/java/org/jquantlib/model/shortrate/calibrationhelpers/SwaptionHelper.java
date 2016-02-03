package org.jquantlib.model.shortrate.calibrationhelpers;

import java.util.ArrayList;

import org.jquantlib.lang.annotation.Time;
import org.jquantlib.model.CalibrationHelper;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.YieldTermStructure;

// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class SwaptionHelper extends CalibrationHelper {

    public SwaptionHelper(final Handle<Quote> volatility, final Handle<YieldTermStructure> termStructure, final boolean calibrateVolatility) {
        super(volatility, termStructure, calibrateVolatility);

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        // TODO Auto-generated constructor stub
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
    public double calibrationError() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double impliedVolatility(final double targetValue, final double accuracy, final int maxEvaluations, final double minVol, final double maxVol) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double modelValue() {
        // TODO Auto-generated method stub
        return 0;
    }

}
