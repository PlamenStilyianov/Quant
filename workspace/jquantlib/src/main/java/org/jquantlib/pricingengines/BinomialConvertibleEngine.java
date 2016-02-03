/*
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
package org.jquantlib.pricingengines;

import java.lang.reflect.Constructor;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.bonds.ConvertibleBondOption;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.methods.lattices.BinomialTree;
import org.jquantlib.methods.lattices.Lattice;
import org.jquantlib.methods.lattices.TsiveriotisFernandesLattice;
import org.jquantlib.pricingengines.hybrid.DiscretizedConvertible;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.yieldcurves.FlatForward;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.TimeGrid;

/**
 * Binomial Tsiveriotis-Fernandes engine for convertible bonds
 *
 * @category hybridengines
 *
 * @author Richard Gomes
 * @author Zahid HussainS
 * @param <E>
 */
//TODO: work in progress
// Temp hack to pass class of T

public class BinomialConvertibleEngine<T extends BinomialTree> extends ConvertibleBondOption.EngineImpl {
    
    //
    // private fields
    //

    private final int timeSteps_;
    private final GeneralizedBlackScholesProcess process_;
    private final ConvertibleBondOption.ArgumentsImpl a;
    private final ConvertibleBondOption.ResultsImpl   r;

    private final Class<T> typeT;

    //
    // public constructors
    //

    public BinomialConvertibleEngine(final Class<T> typeT, 
    								 final GeneralizedBlackScholesProcess process, 
    								 final int timeSteps) {
    	this.typeT = typeT;
        this.a = this.arguments_;
        this.r = this.results_;
    	this.process_ = process;
        this.timeSteps_ = timeSteps;
        QL.require(timeSteps>0, "timeSteps must be positive, " + timeSteps + " not allowed");
        this.process_.addObserver(this);
    }

    @Override
    public void calculate() {

        final DayCounter rfdc  = process_.riskFreeRate().currentLink().dayCounter();
        final DayCounter divdc = process_.dividendYield().currentLink().dayCounter();
        final DayCounter voldc = process_.blackVolatility().currentLink().dayCounter();
        final Calendar volcal = process_.blackVolatility().currentLink().calendar();

        Double s0 = process_.x0();
        QL.require(s0 > 0.0, "negative or null underlying");
        final double /*Volatility*/ v = process_.blackVolatility().currentLink().blackVol(
                                         this.a.exercise.lastDate(), s0);
        final Date maturityDate = this.a.exercise.lastDate();
        final double /*Rate*/ riskFreeRate = process_.riskFreeRate().currentLink().zeroRate(
                                 maturityDate, rfdc, Compounding.Continuous, Frequency.NoFrequency).rate();
        final double q = process_.dividendYield().currentLink().zeroRate(
                                maturityDate, divdc, Compounding.Continuous, Frequency.NoFrequency).rate();
        final Date referenceDate = process_.riskFreeRate().currentLink().referenceDate();

        // subtract dividends
        int i;
        for (i=0; i<this.a.dividends.size(); i++) {
            if (this.a.dividends.get(i).date().gt(referenceDate))
                s0 -= this.a.dividends.get(i).amount() *
                      process_.riskFreeRate().currentLink().discount(
                                             this.a.dividends.get(i).date());
        }
        QL.require(s0 > 0.0,
                   "negative value after subtracting dividends");

        // binomial trees with constant coefficient
        final Handle<Quote> underlying = new Handle<Quote>(new SimpleQuote(s0));
        final Handle<YieldTermStructure> flatRiskFree = 
        			new Handle<YieldTermStructure>(new FlatForward(referenceDate, riskFreeRate, rfdc));
        final Handle<YieldTermStructure> flatDividends =
        			new Handle<YieldTermStructure>(new FlatForward(referenceDate, q, divdc));
        final Handle<BlackVolTermStructure> flatVol = 
        			new Handle<BlackVolTermStructure>(new BlackConstantVol(referenceDate, volcal, v, voldc));

        final PlainVanillaPayoff payoff = (PlainVanillaPayoff)(this.a.payoff);
        QL.require(payoff != null, "non-plain payoff given");

        final double maturity = rfdc.yearFraction(this.a.settlementDate, maturityDate);

        final GeneralizedBlackScholesProcess bs = new GeneralizedBlackScholesProcess(underlying, flatDividends,
                                                    flatRiskFree, flatVol);
        // final T tree = new T(bs, maturity, timeSteps_, payoff.strike());
        final T tree;
        try {
            final Constructor<T> c = typeT.getConstructor(StochasticProcess1D.class, double.class, int.class, double.class);
            tree = typeT.cast( c.newInstance(bs, maturity, timeSteps_, payoff.strike() ));
        } catch (final Exception e) {
            throw new LibraryException(e); // QA:[RG]::verified
        }

        final double creditSpread = this.a.creditSpread.currentLink().value();

        final Lattice lattice = new TsiveriotisFernandesLattice<T>(tree,riskFreeRate,maturity,
                                                 timeSteps_,creditSpread,v,q);

        final DiscretizedConvertible convertible = 
        				new DiscretizedConvertible(this.a, bs,
                                           			new TimeGrid(maturity, timeSteps_));

        convertible.initialize(lattice, maturity);
        convertible.rollback(0.0);
        this.r.value = convertible.presentValue();
    }

}
