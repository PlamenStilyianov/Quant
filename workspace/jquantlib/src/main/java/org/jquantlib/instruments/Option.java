/*
 Copyright (C) 2007 Richard Gomes

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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl

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

package org.jquantlib.instruments;

import org.jquantlib.QL;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.time.Frequency;

/**
 * Abstract base class for Options
 *
 * @author Richard Gomes
 */
public abstract class Option extends Instrument {

    //
    // protected final fields
    //

    protected final Payoff payoff;
    protected final Exercise exercise;

    //
    // public constructors
    //

    public Option(final Payoff payoff, final Exercise exercise) {
        this.payoff = payoff;
        this.exercise = exercise;
    }

    @Override
    protected void setupArguments(final PricingEngine.Arguments a) /* @ReadOnly */ {
        QL.require(Option.ArgumentsImpl.class.isAssignableFrom(a.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
        final Option.ArgumentsImpl arguments = (Option.ArgumentsImpl)a;
        arguments.payoff = payoff;
        arguments.exercise = exercise;
    }



    //
    // public static inner enums
    //

    /**
     * This enumeration represents options types: CALLs and PUTs.
     */
    public static enum Type {

        Put(-1),
        Call(1);

        private int value;

        private Type(final int type) {
            this.value = type;
        }

        static public final String UNKNOWN_OPTION_TYPE = "unknown option type";

        /**
         * This method returns the <i>mathematical signal</i> associated to an option type.
         *
         * @return 1 for CALLs; -1 for PUTs
         */
        public int toInteger() {
            return value;
        }

        @Override
        public String toString() {
            if (value==1)
                return "Call";
            else if (value==-1)
                return "Put";
            else
                throw new LibraryException(UNKNOWN_OPTION_TYPE);
        }
    }



    //
    // ????? inner interfaces
    //

    /**
     * basic option arguments
     *
     * @author Richard Gomes
     */
    public interface Arguments extends Instrument.Arguments { /* marking interface */ }


    /**
     * additional option results
     *
     * @author Richard Gomes
     */
    public interface Greeks extends Instrument.Results { /* marking interface */ }


    /**
     * more additional option results
     *
     * @author Richard Gomes
     */
    public interface MoreGreeks extends Instrument.Results { /* marking interface */ }



    //
    // static ????? inner classes
    //

    /**
     * Keeps arguments used by {@link PricingEngine}s and necessary for Option valuation
     *
     * @note Public fields as this class works pretty much as Data Transfer Objects
     *
     * @author Richard Gomes
     */
    static public class ArgumentsImpl implements Option.Arguments {

        //
        // public fields
        //

        public Payoff payoff;
        public Exercise exercise;


        //
        // implements Arguments
        //

        @Override
        public void validate() /*@ReadOnly*/ {
            QL.require(payoff != null , "No payoff given"); // TODO: message
            QL.require(exercise != null , "No exercise given"); // TODO: message
        }

    }


    /**
     * This class keeps Greeks and other {@link Results} calculated by a {@link PricingEngine}
     * <p>
     * In mathematical finance, the Greeks are the quantities representing the market sensitivities of derivatives such as options. Each
     * "Greek" measures a different aspect of the risk in an option position, and corresponds to a parameter on which the value of an
     * instrument or portfolio of financial instruments is dependent. The name is used because the parameters are often denoted by Greek
     * letters.
     *
     * @note Public fields as this class works pretty much as Data Transfer Objects
     *
     * @see Results
     * @see Instrument
     * @see PricingEngine
     * @see Arguments
     * @see <a href="http://en.wikipedia.org/wiki/Greeks_(finance)">Greeks</a>
     * @see <a href="http://www.theponytail.net/DOL/DOLnode69.htm">The Derivatives Online Pages</a>
     *
     * @author Richard Gomes
     */
    static public class GreeksImpl implements Option.Greeks {

        //
        // public fields
        //

        public /*@Real*/ double delta;
        public /*@Real*/ double gamma;
        public /*@Real*/ double theta;
        public /*@Real*/ double vega;
        public /*@Real*/ double rho;
        public /*@Real*/ double dividendRho;

        public /*@Real*/ double  blackScholesTheta(
                final GeneralizedBlackScholesProcess p,
                final /*@Real*/ double value, final /*@Real*/ double delta, final /*@Real*/ double gamma) {

            /*@Real*/ final double u = p.stateVariable().currentLink().value();
            //TODO update zeroRate so that we do not need to set frequency and extrapolate
            /*@Rate*/ final double r = p.riskFreeRate().currentLink().zeroRate(0.0, Compounding.Continuous, Frequency.Annual, false).rate();
            /*@Rate*/ final double q = p.dividendYield().currentLink().zeroRate(0.0, Compounding.Continuous, Frequency.Annual, false).rate();
            /*@Volatility*/ final double v = p.localVolatility().currentLink().localVol(0.0, u);

            return r*value -(r-q)*u*delta - 0.5*v*v*u*u*gamma;
        }

        public /*@Real*/ double defaultThetaPerDay(/*@Real*/ final double theta) {
            return theta/365.0;
        }

        //
        // implements Greeks
        //

        @Override
        public void reset() {
            delta = gamma = theta = vega = rho = dividendRho = Double.NaN;
        }

    }



    /**
     * This class keeps additional Greeks and other {@link Results} calculated by a {@link PricingEngine}
     * <p>
     * In mathematical finance, the Greeks are the quantities representing the market sensitivities of derivatives such as options. Each
     * "Greek" measures a different aspect of the risk in an option position, and corresponds to a parameter on which the value of an
     * instrument or portfolio of financial instruments is dependent. The name is used because the parameters are often denoted by Greek
     * letters.
     *
     * @note Public fields as this class works pretty much as Data Transfer Objects
     *
     * @see Greeks
     * @see Results
     * @see Instrument
     * @see PricingEngine
     * @see Arguments
     * @see <a href="http://en.wikipedia.org/wiki/Greeks_(finance)">Greeks</a>
     *
     * @author Richard Gomes
     */
    static public class MoreGreeksImpl implements Option.MoreGreeks {

        public /*@Real*/ double itmCashProbability;
        public /*@Real*/ double deltaForward;
        public /*@Real*/ double elasticity;
        public /*@Real*/ double thetaPerDay;
        public /*@Real*/ double strikeSensitivity;

        //
        // implements MoreGreeks
        //

        @Override
        public void reset() {
            itmCashProbability = deltaForward = elasticity = thetaPerDay = strikeSensitivity = Double.NaN;
        }

    }

}
