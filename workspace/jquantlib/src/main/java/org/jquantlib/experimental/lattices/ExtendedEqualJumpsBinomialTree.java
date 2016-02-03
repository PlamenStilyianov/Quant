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

import org.jquantlib.processes.StochasticProcess1D;

/**
 * Base class for equal jumps binomial tree.
 *
 * @category lattices
 *
 * @author Richard Gomes
 */
public abstract class ExtendedEqualJumpsBinomialTree extends ExtendedBinomialTree /*<T>*/ {


    //
    // protected fields
    //

    protected double dx;
    protected double pu;
    protected double pd;


    //
    // public methods
    //

    public ExtendedEqualJumpsBinomialTree(
            final StochasticProcess1D process,
            final /* @Time */ double end,
            final int steps) {

        super(process, end, steps);
    }

    @Override
    public double underlying(final int i, final int index) /* @ReadOnly */ {
        final /*@Time*/ double stepTime = i*this.dt;
        final long j = 2*index - i;
        // exploiting equal jump and the x0_ tree centering
        return this.x0*Math.exp(j*dxStep(stepTime));
    }

    @Override
    public double probability(final int i, final int ref, final int branch) /* @ReadOnly */ {
        final /*@Time*/ double stepTime = i*dt;
        final /*@Real*/ double upProb = probUp(stepTime);
        final /*@Real*/ double downProb = 1 - upProb;
        return (branch == 1 ? upProb : downProb);
    }



    //
    // protected abstract methods
    //

    protected abstract double probUp(/* @Time */ double stepTime) /* @ReadOnly */ ;

    /**
     * time dependent term dx
     */
    protected abstract double dxStep(/* @Time */ double stepTime) /* @ReadOnly */ ;

}
