/*
 Copyright (C) 2008 Srinivas Hasti

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */
/*
 Copyright (C) 2002, 2003, 2004 Ferdinando Ametrano
 Copyright (C) 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2007 StatPro Italia srl
 Copyright (C) 2007 Affine Group Limited

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

package org.jquantlib.pricingengines.vanilla;

import java.lang.reflect.Constructor;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.experimental.lattices.ExtendedTian;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.lattices.BlackScholesLattice;
import org.jquantlib.methods.lattices.Tree;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.processes.StochasticProcess1D;
import org.jquantlib.quotes.Handle;
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
 * Pricing engine for vanilla options using binomial trees
 *
 * @category vanillaengines
 *
 * @test the correctness of the returned values is tested by
 *       checking it against analytic results.
 *
 * @todo Greeks are not overly accurate. They could be improved
 *       by building a tree so that it has three points at the
 *       current time. The value would be fetched from the middle
 *       one, while the two side points would be used for
 *       estimating partial derivatives.
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
//ZH: remove abstract
public class BinomialVanillaEngine<T extends Tree> extends VanillaOption.EngineImpl {

    //
    // private final fields
    //

    final private GeneralizedBlackScholesProcess process;
    final private int timeSteps_;
    final private VanillaOption.ArgumentsImpl a;
    final private VanillaOption.ResultsImpl   r;
    final private Option.GreeksImpl greeks;
    final private Option.MoreGreeksImpl moreGreeks;

    //
    // private fields
    //

    private final Class<? extends Tree> classT;


    //
    // public constructors
    //

    public BinomialVanillaEngine(
    		final Class<? extends Tree> classT,
    		final GeneralizedBlackScholesProcess process, 
    		final int timeSteps) {
        this.classT = classT;
        QL.require(timeSteps > 0 , "timeSteps must be positive"); // TODO: message
        this.timeSteps_ = timeSteps;
        this.a = (VanillaOption.ArgumentsImpl)arguments_;
        this.r = (VanillaOption.ResultsImpl)results_;
        this.greeks = r.greeks();
        this.moreGreeks = r.moreGreeks();
        this.process = process;
        this.process.addObserver(this);
    }


    //
    // private methods
    //

    private Object getTreeInstance(
            final StochasticProcess1D bs,
            final /*@Date*/ double maturity,
            final int timeSteps,
            final /*@Real*/ double strike) {
        try {
            if (this.classT == ExtendedTian.class) {
                final Constructor<T> c = (Constructor<T>) classT.getConstructor(StochasticProcess1D.class, double.class, int.class);
                return classT.cast(c.newInstance(bs, maturity, timeSteps));
            } else {
                final Constructor<T> c = (Constructor<T>) classT.getConstructor(StochasticProcess1D.class, double.class, int.class, double.class);
                return classT.cast(c.newInstance(bs, maturity, timeSteps, strike));
            }
        } catch (final Exception e) {
            throw new LibraryException(e); // QA:[RG]::verified
        }
    }


    //
    // implements PricingEngine
    //

    @Override
    public void calculate() /*@ReadOnly*/ {
        //FIXME: code review: what about BermudanExercise?
        //QL.require(a.exercise.type() == Exercise.Type.European || a.exercise.type() == Exercise.Type.American,
        //           "neither European nor American option"); // TODO: message

        final DayCounter rfdc  = process.riskFreeRate().currentLink().dayCounter();
        final DayCounter divdc = process.dividendYield().currentLink().dayCounter();
        final DayCounter voldc = process.blackVolatility().currentLink().dayCounter();
        final Calendar volcal  = process.blackVolatility().currentLink().calendar();

        final double s0 = process.stateVariable().currentLink().value();
        QL.require(s0 > 0.0 , "negative or null underlying given"); // TODO: message
        final double v = process.blackVolatility().currentLink().blackVol(a.exercise.lastDate(), s0);
        final Date maturityDate = a.exercise.lastDate();

        final double rRate = process.riskFreeRate().currentLink().zeroRate(maturityDate, rfdc, Compounding.Continuous, Frequency.NoFrequency).rate();
        final double qRate = process.dividendYield().currentLink().zeroRate(maturityDate, divdc, Compounding.Continuous, Frequency.NoFrequency).rate();
        final Date referenceDate = process.riskFreeRate().currentLink().referenceDate();

        // binomial trees with constant coefficient
        final Handle<YieldTermStructure> flatRiskFree = new Handle<YieldTermStructure>(new FlatForward(referenceDate, rRate, rfdc));
        final Handle<YieldTermStructure> flatDividends = new Handle<YieldTermStructure>(new FlatForward(referenceDate, qRate, divdc));
        final Handle<BlackVolTermStructure> flatVol = new Handle<BlackVolTermStructure>(new BlackConstantVol(referenceDate, volcal, v, voldc));
        final PlainVanillaPayoff payoff = (PlainVanillaPayoff) a.payoff;
        QL.require(payoff!=null , "non-plain payoff given"); // TODO: message

        final double maturity = rfdc.yearFraction(referenceDate, maturityDate);

        final StochasticProcess1D bs = new GeneralizedBlackScholesProcess(process.stateVariable(), flatDividends, flatRiskFree, flatVol);
        final TimeGrid grid = new TimeGrid(maturity, timeSteps_);
        final Tree tree = (Tree)getTreeInstance(bs, maturity, timeSteps_, payoff.strike());

        final BlackScholesLattice<Tree> lattice = new BlackScholesLattice<Tree>(tree, rRate, maturity, timeSteps_);
        final DiscretizedVanillaOption option = new DiscretizedVanillaOption(a, process, grid);

        option.initialize(lattice, maturity);

        // Partial derivatives calculated from various points in the binomial tree (Odegaard)

        // Rollback to third-last step, and get underlying price (s2) & option values (p2) at this point
        option.rollback(grid.at(2));
        final Array va2 = option.values();
        QL.require(va2.size() == 3 , "expect 3 nodes in grid at second step"); // TODO: message
        final double p2h = va2.get(2); // high-price
        final double s2 = lattice.underlying(2, 2); // high price

        // Rollback to second-last step, and get option value (p1) at this point
        option.rollback(grid.at(1));
        final Array va = option.values();
        QL.require(va.size() == 2, "expect 2 nodes in grid at first step"); // TODO: message
        final double p1 = va.get(1);

        // Finally, rollback to t=0
        option.rollback(0.0);
        final double p0 = option.presentValue();
        final double s1 = lattice.underlying(1, 1);

        // Calculate partial derivatives
        final double delta0 = (p1 - p0) / (s1 - s0); // dp/ds
        final double delta1 = (p2h - p1) / (s2 - s1); // dp/ds

        // Store results
        r.value = p0;
        greeks.delta = delta0;
        greeks.gamma = 2.0 * (delta1 - delta0) / (s2 - s0); // d(delta)/ds
        greeks.theta = greeks.blackScholesTheta(process, r.value, greeks.delta, greeks.gamma);
    }

}
