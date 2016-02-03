/*
 Copyright (C) 2008 Richard Gomes

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

package org.jquantlib.methods.lattices;

import java.util.List;

import org.jquantlib.cashflow.Dividend;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.time.Date;
import org.jquantlib.time.TimeGrid;

/**
 * Simple binomial lattice approximating the Black-Scholes model which supports dividends
 *
 * @category lattices
 *
 * @author Richard Gomes
 */
public class BlackScholesDividendLattice<T extends Tree> extends BlackScholesLattice<T> {

    final private T tree;
    final private int[] map;
    final private double[] list;

    public BlackScholesDividendLattice(
            final T tree,
            final double riskFreeRate,
            final /*@Time*/ double end,
            final int steps,
            final DayCounter dc,
            final TimeGrid grid,
            final Date referenceDate,
            final List<? extends Dividend> cashFlow) {
        super(tree, riskFreeRate, end, steps);
        this.tree = tree;
        // allocate map from TimeGrid to internal list of dividend amounts
        this.map = new int[grid.size()];
        // allocate list of total amount of dividends to be discounted
        this.list = new double[cashFlow.size()+1];
        // initial dividend amount to be discounted is zero.
        list[0] = 0.0;
        // populate map and list
        int lastIdx = 0;
        for (int i=1; i<list.length; i++) {
            // populate one element of total amount of dividends to be discounted
            final double time = dc.yearFraction(referenceDate, cashFlow.get(i-1).date());
            list[i] = list[i-1] + Math.exp(-riskFreeRate * time);
            // obtain grid element which is immediately greater than current dividend time
            final int currIdx = grid.closestIndex(time) + 1;
            // populate elements of map with index to map array
            for (int j=lastIdx; j<currIdx; j++)
                map[j] = i-1;
            lastIdx = currIdx;
        }
        for (int j=lastIdx; j<map.length; j++)
            map[j] = list.length-1;
    }

    @Override
    public double underlying(final int i, final int index) {
        return tree.underlying(i, index) - list[map[index]];
    }

}
