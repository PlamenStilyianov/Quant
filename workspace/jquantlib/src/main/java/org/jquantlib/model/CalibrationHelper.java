/*
Copyright (C)
2008 Praneet Tiwari
2009 Ueli Hofstetter

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
package org.jquantlib.model;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.lang.annotation.Time;
import org.jquantlib.math.Ops;
import org.jquantlib.math.solvers1D.Brent;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * Liquid market instrument used during calibration
 * @author Praneet Tiwari
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: code review :: please verify against QL/C++ code
public abstract class CalibrationHelper implements Observer, Observable {

    protected double/* @Real */marketValue;
    protected Handle<Quote> volatility_;
    protected Handle<YieldTermStructure> termStructure_;
    protected PricingEngine engine_;
    private boolean calibrateVolatility_ = false;

    public CalibrationHelper(
            final Handle<Quote> volatility,
            final Handle<YieldTermStructure> termStructure,
            final boolean calibrateVolatility) {

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        this.volatility_ = volatility;
        this.termStructure_ = termStructure;
        this.calibrateVolatility_ = calibrateVolatility;

        this.volatility_.addObserver(this);
        this.termStructure_.addObserver(this);
        //XXX:registerWith
        //registerWith(termStructure_);
        //registerWith(volatility_);
    }

    //TODO: code review
    // abstract double blackPrice (double /*@Volatility*/ volatility);

    // ! returns the actual price of the instrument (from volatility)
    double/* @Real */marketValue() {
        return marketValue;
    }

    // ! returns the price of the instrument according to the model
    public abstract double /* @Real */modelValue();

    // ! returns the error resulting from the model valuation
    public double /* @Real */calibrationError(){
        if(calibrateVolatility_){
            final double lowerPrice = blackPrice(0.001);
            final double upperPrice = blackPrice(10);
            final double modelPrice = modelValue();

            double implied;
            if(modelPrice <= lowerPrice)
                implied = 0.001;
            else if(modelPrice >= upperPrice)
                implied = 10.0;
            else
                implied = impliedVolatility(modelPrice, 1e-12, 5000, 0.001, 10);
            return implied - volatility_.currentLink().value();
        } else
            return Math.abs(marketValue() - modelValue())/marketValue();

    }

    // ! Black volatility implied by the model
    public double /* @Volatility */impliedVolatility(final double /* @Real */targetValue, final double /* @Real */accuracy,
            final int /* @Size */maxEvaluations, final double /* @Volatility */minVol, final double /* @Volatility */maxVol){
        final ImpliedVolatilityHelper f = new ImpliedVolatilityHelper(this, targetValue);
        final Brent solver = new Brent();
        solver.setMaxEvaluations(maxEvaluations);
        return solver.solve(f, accuracy, volatility_.currentLink().value(), minVol, maxVol);
    }

    public void setPricingEngine(final PricingEngine engine) {
        this.engine_ = engine;
    }


    //
    // public abstract methods
    //

    // TODO: code review :: please verify against QL/C++ code
    public abstract void addTimesTo(ArrayList<Time> times);

    // CalibrationHelper! Black price given a volatility
    public abstract double /* @Real */blackPrice(double /* @Volatility */volatility);



    //
    // implements Observer
    //

    //XXX:registerWith
    //    @Override
    //    public void registerWith(final Observable o) {
    //        o.addObserver(this);
    //    }
    //
    //    @Override
    //    public void unregisterWith(final Observable o) {
    //        o.deleteObserver(this);
    //    }

    @Override
    public void update() {
        marketValue = blackPrice(volatility_.currentLink().value());
        notifyObservers();
    }



    //
    // implements Observable
    //

    @Override
    public void addObserver(final Observer observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public int countObservers() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void deleteObserver(final Observer observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteObservers() {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Observer> getObservers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void notifyObservers() {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyObservers(final Object arg) {
        // TODO Auto-generated method stub

    }


    //
    // private inner classes
    //

    private class ImpliedVolatilityHelper implements Ops.DoubleOp {
        public ImpliedVolatilityHelper(final CalibrationHelper helper, final double value){
            this.helper_ = helper;
            this.value_ = value;
        }

        public double op(final double x) {
            return value_ - helper_.blackPrice(x);
        }

        private final CalibrationHelper helper_;
        private final double value_;

    }
}
