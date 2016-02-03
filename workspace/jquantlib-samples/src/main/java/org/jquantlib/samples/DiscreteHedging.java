/*
 Copyright (C) 2009 Q.Boiler, Ueli Hofstetter

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




/*  This example computes profit and loss of a discrete interval hedging
    strategy and compares with the results of Derman & Kamal's (Goldman Sachs
    Equity Derivatives Research) Research Note: "When You Cannot Hedge
    Continuously: The Corrections to Black-Scholes"
    http://www.ederman.com/emanuelderman/GSQSpapers/when_you_cannot_hedge.pdf

    Suppose an option hedger sells an European option and receives the
    Black-Scholes value as the options premium.
    Then he follows a Black-Scholes hedging strategy, rehedging at discrete,
    evenly spaced time intervals as the underlying stock changes. At
    expiration, the hedger delivers the option payoff to the option holder,
    and unwinds the hedge. We are interested in understanding the final
    profit or loss of this strategy.

    If the hedger had followed the exact Black-Scholes replication strategy,
    re-hedging continuously as the underlying stock evolved towards its final
    value at expiration, then, no matter what path the stock took, the final
    P&L would be exactly zero. When the replication strategy deviates from
    the exact Black-Scholes method, the final P&L may deviate from zero. This
    deviation is called the replication error. When the hedger rebalances at
    discrete rather than continuous intervals, the hedge is imperfect and the
    replication is inexact. The more often hedging occurs, the smaller the
    replication error.

    We examine the range of possibilities, computing the replication error.
 */

package org.jquantlib.samples;

import org.jquantlib.QL;
import org.jquantlib.instruments.Option;
import org.jquantlib.samples.util.ReplicationError;
import org.jquantlib.samples.util.StopClock;


// FIXME :: This class needs code review ::
// http://bugs.jquantlib.org/view.php?id=221
public class DiscreteHedging implements Runnable {

    public static void main(final String[] args) {
        new DiscreteHedging().run();
    }

    @Override
    public void run() {

        QL.validateExperimentalMode();

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

		try {

		    final StopClock clock = new StopClock();
            clock.startClock();

			/* @Time */			final Number maturity = new Double(1.0 / 12.0); // 1 month
			/* @Real */	 	final Number strike = new Double(100);
			/* @Real */	 	final Number underlying = new Double(100);
			/* @Volatility */	final Number volatility = new Double(0.20); // 20%
			/* @Rate */    		final Number riskFreeRate = new Double(0.05); // 5%
			final Option.Type Call = Option.Type.Call;

			final ReplicationError rp = new ReplicationError(Call, maturity, strike, underlying, volatility, riskFreeRate);

			final int scenarios = 50000;
			int hedgesNum;

			hedgesNum = 21;
			rp.compute(hedgesNum, scenarios);

			hedgesNum = 84;
			rp.compute(hedgesNum, scenarios);

			clock.stopClock();
			clock.log();
		}

		catch(final Exception ex){
			ex.printStackTrace();
			//QL.info(ex.getMessage());
		}
	}

}



