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
 Copyright (C) 2003 Ferdinando Ametrano
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

package org.jquantlib.processes;

import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Date;

/**
 * Merton-76 jump diffusion process
 *
 *
 * @author <Richard Gomes>
 */
public class Merton76Process extends StochasticProcess1D {

    //
    // private fields
    //

    private final GeneralizedBlackScholesProcess blackProcess;
    private final Handle<? extends Quote> jumpIntensity, logMeanJump, logJumpVolatility;


    //
    // public constructors
    //

    public Merton76Process(
            final Handle< ? extends Quote > stateVariable,
            final Handle<YieldTermStructure> dividendTS,
            final Handle<YieldTermStructure> riskFreeTS,
            final Handle<BlackVolTermStructure> blackVolTS,
            final Handle<? extends Quote> jumpInt,
            final Handle<? extends Quote> logJMean,
            final Handle<? extends Quote> logJVol) {

        this.blackProcess = new BlackScholesMertonProcess(stateVariable, dividendTS, riskFreeTS, blackVolTS, new EulerDiscretization());
        this.jumpIntensity = jumpInt;
        this.logJumpVolatility = logJVol;
        this.logMeanJump = logJMean;

        this.blackProcess.addObserver(this);
        this.jumpIntensity.addObserver(this);
        this.logJumpVolatility.addObserver(this);
        this.logMeanJump.addObserver(this);
    }


    //
    // public methods
    //

    public Handle<? extends Quote> stateVariable() {
        return blackProcess.stateVariable();
    }

    public Handle<YieldTermStructure> dividendYield() {
        return blackProcess.dividendYield();
    }

    public Handle<YieldTermStructure> riskFreeRate() {
        return blackProcess.riskFreeRate();
    }

    public Handle<BlackVolTermStructure> blackVolatility() {
        return blackProcess.blackVolatility();
    }

    public Handle<? extends Quote> jumpIntensity() {
        return this.jumpIntensity;
    }

    public Handle<? extends Quote> logMeanJump() {
        return this.logMeanJump;
    }

    public Handle<? extends Quote> logJumpVolatility() {
        return this.logJumpVolatility;
    }


    //
    // Overrides StochasticProcess1D
    //

    @Override
    public double /* @Real */x0() {
        return blackProcess.x0();
    }

    // TODO: code review :: please verify against QL/C++ code

    @Override
    public double /* @Real */drift(final double /* @Time */t, final double /* @Real */x) {
        throw new LibraryException("not implemented"); // TODO: message
    }

    @Override
    public double /* @Real */diffusion(final double /* @Time */t, final double /* @Real */x) {
        throw new LibraryException("not implemented"); // TODO: message
    }

    @Override
    public double /* @Real */apply(final double /* @Real */x, final double /* @Real */y) {
        throw new LibraryException("not implemented"); // TODO: message
    }


    //
    // Overrides StochasticProcess1D
    //

    @Override
    public double /* @Time */time(final Date d) {
        return blackProcess.time(d);
    }

}
