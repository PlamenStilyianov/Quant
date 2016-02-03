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
 Copyright (C) 2005 Joseph Wang
 Copyright (C) 2007 StatPro Italia srl

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

package org.jquantlib.pricingengines.vanilla.finitedifferences;

import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * Finite-differences pricing engine for dividend options using escowed dividend model
 * <p>
 * The merton 73 engine is the classic engine described in most derivatives texts. However, Haug, Haug, and Lewis in "Back to
 * Basics: a new approach to the discrete dividend problem" argues that this scheme underprices call options. This is set as the
 * default engine, because it is consistent with the analytic version.
 *
 * @category vanillaengines
 *
 * @author Srinivas Hasti
 */
public class FDDividendEngineMerton73 extends FDDividendEngineBase {

    //
    // public methods
    //

    public FDDividendEngineMerton73(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints,
            final boolean timeDependent) {
        super(process, timeSteps, gridPoints, timeDependent);
    }


    //
    // protected methods
    //

    //
    // The value of the x axis is the NPV of the underlying minus the
    // value of the paid dividends.

    // Note that to get the PDE to work, I have to scale the values
    // and not shift them.  This means that the price curve assumes
    // that the dividends are scaled with the value of the underlying.
    //

    @Override
    protected void setGridLimits() /* @ReadOnly */ {
        double paidDividends = 0.0;
        for (int i=0; i<events.size(); i++)
            if (getDividendTime(i) >= 0.0)
                paidDividends += getDiscountedDividend(i);
        super.setGridLimits(process.stateVariable().currentLink().value()-paidDividends, getResidualTime());
        ensureStrikeInGrid();
    }

    @Override
    // TODO:  Make this work for both fixed and scaled dividends
    protected void executeIntermediateStep(final int step) /* @ReadOnly */ {
        final double scaleFactor = getDiscountedDividend(step) / center + 1.0;
        sMin *= scaleFactor;
        sMax *= scaleFactor;
        center *= scaleFactor;

        intrinsicValues.scaleGrid(scaleFactor);
        initializeInitialCondition();
        prices.scaleGrid(scaleFactor);
        initializeOperator();
        super.initializeModel();

        super.initializeStepCondition();
        stepCondition.applyTo(prices.values(), getDividendTime(step));
    }

}
