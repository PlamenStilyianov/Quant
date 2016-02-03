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
 Copyright (C) 2003, 2004, 2005, 2006, 2007 StatPro Italia srl

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

import java.util.HashMap;
import java.util.Map;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.util.LazyObject;


/**
 * This is an abstract {@link Instrument} class which is able to use a {@link PricingEngine} implemented
 * internally or externally to it.
 *
 * @see PricingEngine
 * @see <a href="http://quantlib.org/reference/group__instruments.html">QuantLib: Financial Instruments</a>
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = { "Femi Anthony" })
public abstract class Instrument extends LazyObject {

    //
    // private static final fields
    //

    private static final String SHOULD_DEFINE_PRICING_ENGINE = "Should define pricing engine";
    private static final String SETUP_ARGUMENTS_NOT_IMPLEMENTED = "Instrument#setupArguments() not implemented";


    //
    // protected fields
    //

    /**
     * The value of this attribute and any other that derived classes might declare must be set during calculation.
     *
     * @see PricingEngine
     */
    protected PricingEngine engine;

    /**
     * Represents the net present value of the instrument.
     */
    protected /*@Real*/ double NPV;

    /**
     * Represents the error estimate on the NPV when available.
     */
    protected /*@Real*/ double errorEstimate;


    //
    // public abstract methods
    //

    /**
     * @return <code>true</code> if the instrument is still tradeable.
     */
    public abstract boolean isExpired();

    /**
     * Passes arguments to be used by a {@link PricingEngine}.
     * When a derived argument structure is defined for an instrument, this method should be overridden to fill it.
     *
     * @param arguments keeps values to be used by the external {@link PricingEngine}
     *
     * @see Arguments
     * @see PricingEngine
     */
    protected void setupArguments(final PricingEngine.Arguments a) /* @ReadOnly */ {
        throw new LibraryException(SETUP_ARGUMENTS_NOT_IMPLEMENTED);
    }



    //
    // protected constructors
    //

    protected Instrument() {
        this.NPV = Double.NaN;
        this.errorEstimate = 0.0;
    }


    //
    // public final methods
    //

    /**
     * This method defines an external {@link PricingEngine} to be used for a <i>new-style</i> {@link Instrument}.
     *
     * @param engine is the external {@link PricingEngine} to be used
     *
     * @see PricingEngine
     */
    public final void setPricingEngine(final PricingEngine engine) {
        if (this.engine != null)
            this.engine.deleteObserver(this);
        this.engine = engine;
        if (this.engine != null)
            this.engine.addObserver(this);
        //XXX:OBS update(this, null);
        update();
    }

    /**
     * returns the net present value of the instrument.
     */
    public final/*@Real*/double NPV() /*@ReadOnly*/{
        calculate();
        QL.require(!Double.isNaN(this.NPV) , "NPV not provided");  // TODO: message
        return NPV;
    }

    /**
     * returns the error estimate on the NPV when available.
     */
    public final/*@Real*/double errorEstimate() /*@ReadOnly*/{
        calculate();
        QL.require(!Double.isNaN(this.errorEstimate) , "error estimate not provided"); // TODO: message
        return errorEstimate;
    }


    //
    // protected methods
    //

    /**
     * Obtains the {@link Results} populated by a {@link PricingEngine}.
     * When a derived result structure is defined for an instrument, this method should be overridden to read from it.
     *
     * @param results contains the {@link Results} object populated by a {@link PricingEngine}
     *
     * @see Results
     * @see PricingEngine
     */
    protected void fetchResults(final PricingEngine.Results r) /* @ReadOnly */ {
        QL.require(PricingEngine.Results.class.isAssignableFrom(r.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
        final Instrument.ResultsImpl results = (Instrument.ResultsImpl)r;
        NPV = results.value;
        errorEstimate = results.errorEstimate;
    }


    /**
     * This method must leave the instrument in a consistent
     * state when the expiration condition is met.
     */
    protected void setupExpired() /*@ReadOnly*/{
        NPV = 0.0;
        errorEstimate = 0.0;
    }


    //
    // overrides LazyObject
    //

    /**
     * This method performs the actual calculations and set any needed results.
     *
     * @see LazyObject#performCalculations
     */
    @Override
    protected void performCalculations() /*@ReadOnly*/ {
        QL.require(engine != null, SHOULD_DEFINE_PRICING_ENGINE); // QA:[RG]::verified
        engine.reset();
        setupArguments(engine.getArguments());
        engine.getArguments().validate();
        engine.calculate();
        fetchResults(engine.getResults());
    }

    @Override
    protected void calculate() /*@ReadOnly*/ {
        if (isExpired()) {
            setupExpired();
            calculated = true;
        } else
            super.calculate();
    }


    //
    // ????? inner interfaces
    //

    /**
     * basic instrument arguments
     *
     * @author Richard Gomes
     */
    public interface Arguments extends PricingEngine.Arguments { /* marking interface */ }

    /**
     * Results from instrument calculation
     *
     * @author Richard Gomes
     */
    public interface Results extends PricingEngine.Results { /* marking interface */ }



    //
    // ????? inner classes
    //


    /**
     * Results are used by {@link PricingEngine}s in order to store results of calculations
     * relative to <i>new-style</i> {@link Instrument}s
     *
     * @note Public fields as this class works pretty much as Data Transfer Objects
     *
     * @see Instrument
     * @see PricingEngine
     * @see Arguments
     *
     * @author Richard Gomes
     */
    static public class ResultsImpl implements Instrument.Results {

        //
        // public fields
        //

        /**
         * Represents the calculated value of an {@link Instrument}
         *
         * @see Instrument
         */
        public /*@Real*/ double value;

        /**
         * Contains the estimated error due to floating point error
         */
        public /*@Real*/ double errorEstimate;

        //TODO: Code review
        private final Map<String, Object> additionalResults = new HashMap<String, Object>();


        //
        // public methods
        //

        /**
         * returns any additional result returned by the pricing engine.
         */
        public Object result(final String key) /* @ReadOnly */ {
            return this.additionalResults.get(key);
        }

        /**
         * returns all additional result returned by the pricing engine.
         */
        public Map<String, Object> additionalResults() /* @ReadOnly */ {
            return this.additionalResults;
        }


        //
        // Overrides PriceEngine.Results
        //

        /**
         * Clean up results of calculations
         * <p>
         * Notice that values are <b>undefined</b> after reset.
         */
        @Override
        public void reset() {
            value = errorEstimate = Double.NaN;
            additionalResults.clear();
        }

    }

}
