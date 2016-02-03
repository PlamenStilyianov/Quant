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

import org.jquantlib.QL;
import org.jquantlib.processes.StochasticProcess1D;

/**
 * Cox-Ross-Rubinstein (multiplicative) equal jumps binomial tree
 *
 * @category lattices
 *
 * @author Richard Gomes
 */
public class ExtendedCoxRossRubinstein extends ExtendedEqualJumpsBinomialTree /*<ExtendedCoxRossRubinstein> */ {

    //
    // public methods
    //

    public ExtendedCoxRossRubinstein(
            final StochasticProcess1D process,
            final /* @Time */ double end,
            final int steps,
            final double strike) {

        super(process, end, steps);
        this.dx = process.stdDeviation(0.0, x0, dt);
        this.pu = 0.5 + 0.5*driftStep(0.0)/dx;
        this.pd = 1.0 - pu;

        QL.require(pu<=1.0, NEGATIVE_PROBABILITY);
        QL.require(pu>=0.0, NEGATIVE_PROBABILITY);
    }


    //
    // protected methods
    //

    @Override
    protected double dxStep(/* @Time */ final double stepTime) /* @ReadOnly */ {
        return treeProcess.stdDeviation(stepTime, x0, dt);
    }

    @Override
    protected double probUp(/* @Time */ final double stepTime) /* @ReadOnly */ {
        return 0.5 + 0.5*driftStep(stepTime)/dxStep(stepTime);
    }

}
