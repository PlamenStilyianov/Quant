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

/*
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2005 StatPro Italia srl
 Copyright (C) 2008 John Maiden

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

package org.jquantlib.experimental.lattices;

import org.jquantlib.methods.lattices.Tree;
import org.jquantlib.processes.StochasticProcess1D;

/**
 * Binomial tree base class.
 *
 * @category lattices
 *
 * @author Richard Gomes
 */
public abstract class ExtendedBinomialTree extends Tree /* <T> */ {

    protected static final Branches branches = Branches.BINOMIAL;

    protected static final String NEGATIVE_PROBABILITY = "negative probability";

    //
    // protected fields
    //

    protected double x0;
    protected double driftPerStep;
    protected /* @Time */ double dt;
    protected StochasticProcess1D treeProcess;


    //
    // public methods
    //

    public ExtendedBinomialTree(
            final StochasticProcess1D process,
            final /* @Time */ double end,
            final int steps) {
        super(steps+1);
        this.treeProcess = process;
        this.x0 = process.x0();
        this.dt = end/steps;
        this.driftPerStep = process.drift(0.0, x0) * dt;
    }

    @Override
    public int size(final int i) /* @ReadOnly */ {
        return i+1;
    }

    @Override
    public int descendant(final int ref, final int index, final int branch) /* @ReadOnly */ {
        return index + branch;
    }



    //
    // protected methods
    //

    protected double driftStep(/* @Time */ final double driftTime) /* @ReadOnly */ {
        return treeProcess.drift(driftTime, x0) * dt;
    }

}
