/*
 Copyright (C) 2008 Srinivas Hasti

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
/*
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2004, 2005 StatPro Italia srl

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

package org.jquantlib.methods.lattices;

import java.util.Vector;

import org.jquantlib.QL;
import org.jquantlib.instruments.DiscretizedAsset;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.time.TimeGrid;

/**
 * Tree-based lattice-method base class
 * <p>
 * This class defines a lattice method that is able to rollback
 * (with discount) a discretized asset object. It will be based
 * on one or more trees.
 *
 * @category lattices
 *
 * @author Srinivas Hasti
 */
public abstract class TreeLattice extends Lattice {

    private final int n;
    private int statePricesLimit;

    // Arrow-Debrew state prices
    protected Vector<Array> statePrices;


    //
    // public constructors
    //

    public TreeLattice(final TimeGrid t, final int n) {
        super(t);
        this.n = n;
        if (n <= 0)
            throw new IllegalStateException("there is no zeronomial lattice!");
        statePrices = new Vector<Array>();
        statePrices.add(new Array(1).fill(1.0));//ZH: Verified with QL097
        statePricesLimit = 0;
    }


    //
    // public abstract methods
    //

    public abstract double discount(int i, int index);

    public abstract int descendant(int i, int index, int branch);

    public abstract double probability(int i, int index, int branch);

    public abstract int size(int i);


    //
    // protected methods
    //

    protected void computeStatePrices(final int until) {
        for (int i = statePricesLimit; i < until; i++) {
            statePrices.add(new Array(size(i + 1)));
            for (int j = 0; j < size(i); j++) {
                final double disc = discount(i, j);
                final double statePrice = statePrices.get(i).get(j);
                final Array array = statePrices.get(i + 1);
                for (int l = 0; l < n; l++) {
                    final int index = descendant(i, j, l);
                    final double oldValue = array.get(index);
                    array.set(index, oldValue + (statePrice * disc * probability(i, j, l)));
                }
            }
        }
        statePricesLimit = until;
    }


    //
    // public methods
    //

    public Array statePrices(final int i) {
        if (i > statePricesLimit)
            computeStatePrices(i);
        return statePrices.get(i);
    }

    public void stepback(final int i, final Array values, final Array newValues) {
        for (int j = 0; j < size(i); j++) {
            double value = 0.0;
            for (int l = 0; l < n; l++)
                value += probability(i, j, l) * values.get(descendant(i, j, l));
            value *= discount(i, j);
            newValues.set(j, value);
        }
    }


    //
    // overrides Lattice
    //

    /**
     * Computes the present value of an asset using Arrow-Debrew prices
     */
    @Override
    public double presentValue(final DiscretizedAsset asset) {
        final int i = t.index(asset.time());
        return asset.values().dotProduct(statePrices(i));
    }

    @Override
    public void initialize(final DiscretizedAsset asset, final double ti) {
        final int i = t.index(ti);
        asset.setTime(ti);
        asset.reset(size(i));
    }

    @Override
    public void rollback(final DiscretizedAsset asset, final double to) {
        partialRollback(asset, to);
        asset.adjustValues();
    }

    @Override
    public void partialRollback(final DiscretizedAsset asset, final double to) {

        final double from = asset.time();

        if (Closeness.isClose(from, to))
            return;

        QL.require(from > to, "cannot roll the asset"); // TODO: message

        final int iFrom = t.index(from);
        final int iTo = t.index(to);

        for (int i = iFrom - 1; i >= iTo; --i) {
            final Array newValues = new Array(size(i));
            stepback(i, asset.values(), newValues);
            asset.setTime(t.get(i));
            asset.setValues(newValues);
            // skip the very last adjustment
            if (i != iTo)
                asset.adjustValues();
        }
    }

}
