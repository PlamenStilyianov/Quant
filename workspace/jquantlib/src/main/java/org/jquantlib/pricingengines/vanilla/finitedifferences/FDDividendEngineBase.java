/*
 Copyright (C) 2008 Srinivas Hasti
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

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.cashflow.Dividend;
import org.jquantlib.cashflow.Event;
import org.jquantlib.instruments.DividendVanillaOption;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.pricingengines.PricingEngine.Arguments;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.time.Date;

/**
 * Abstract base class for dividend engines
 *
 * @todo The dividend class really needs to be made more sophisticated to distinguish between fixed dividends and fractional dividends
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 */
//TODO: The dividend class really needs to be made more sophisticated to distinguish between fixed dividends and fractional dividends
public abstract class FDDividendEngineBase extends FDMultiPeriodEngine {

    //
    // public methods
    //

    public FDDividendEngineBase(
            final GeneralizedBlackScholesProcess process) {
        this(process, 100, 100, false);
    }

    public FDDividendEngineBase(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps) {
        this(process, timeSteps, 100, false);
    }

    public FDDividendEngineBase(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints) {
        this(process, timeSteps, gridPoints, false);
    }

    public FDDividendEngineBase(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints,
            final boolean timeDependent) {
        super(process, timeSteps, gridPoints, timeDependent);
    }


    //
    // protected methods
    //

    @Override
    protected void setupArguments(final Arguments  args) /* @ReadOnly */ {
        QL.require(DividendVanillaOption.ArgumentsImpl.class.isAssignableFrom(args.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
        final DividendVanillaOption.ArgumentsImpl arguments = (DividendVanillaOption.ArgumentsImpl)args;
        final List<Event> events = new ArrayList<Event>(arguments.cashFlow.size());
        for (final Dividend cashFlow : arguments.cashFlow) {
            events.add(cashFlow);
        }
        super.setupArguments(args, events);
    }

    protected double getDividendAmount(final int i) /* @ReadOnly */ {
        final Dividend dividend = (Dividend)(events.get(i));
        if (dividend!=null) {
            return dividend.amount();
        } else {
            return 0.0;
        }
    }

    protected double getDiscountedDividend(final int i) /* @ReadOnly */ {
        final double dividend = getDividendAmount(i);
        final Date date = events.get(i).date();
        final double discount = process.riskFreeRate().currentLink().discount(date)
                              / process.dividendYield().currentLink().discount(date);
        return dividend * discount;
    }


    //
    // protected abstract methods
    //

    @Override
    protected abstract void setGridLimits() /* @ReadOnly */ ;

    @Override
    protected abstract void executeIntermediateStep(int step) /* @ReadOnly */ ;

}
