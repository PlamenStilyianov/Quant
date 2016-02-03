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
 Copyright (C) 2003, 2004 Ferdinando Ametrano
 Copyright (C) 2004, 2007 StatPro Italia srl

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

/*! \file asianoption.hpp
 \brief Asian option on a single asset
 */

package org.jquantlib.instruments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.GenericEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.time.Date;

/**
 * Discrete-averaging Asian option
 *
 * @category instruments
 *
 * @author <Richard Gomes>
 */
public class DiscreteAveragingAsianOption extends OneAssetOption {

    protected final AverageType averageType;
    protected final /* @Real */ double runningAccumulator;
    protected final /* @Size */ int pastFixings;
    protected final List<Date> fixingDates;

    public DiscreteAveragingAsianOption(
            final AverageType averageType,
            final /* @Real */ double runningAccumulator,
            final /* @Size */ int pastFixings,
            final List<Date> fixingDates,
            final StrikedTypePayoff payoff,
            final Exercise exercise) {
        super(payoff, exercise);
        this.averageType = averageType;
        this.runningAccumulator = runningAccumulator;
        this.pastFixings = pastFixings;
        this.fixingDates = new ArrayList<Date>(fixingDates);
        Collections.sort(this.fixingDates);
    }

    @Override
    public void setupArguments(final PricingEngine.Arguments arguments) /* @ReadOnly */ {
        super.setupArguments(arguments);
        QL.require(DiscreteAveragingAsianOption.Arguments.class.isAssignableFrom(arguments.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
        final DiscreteAveragingAsianOption.ArgumentsImpl a = (DiscreteAveragingAsianOption.ArgumentsImpl) arguments;
        a.averageType = averageType;
        a.runningAccumulator = runningAccumulator;
        a.pastFixings = pastFixings;
        a.fixingDates = fixingDates;
    }


    //
    // public inner classes
    //


    /**
     * Description of the terms and conditions of a discrete average out fixed strike option.
     *
     * @author <Richard Gomes>
     */
    static public class ArgumentsImpl extends OneAssetOption.ArgumentsImpl implements DiscreteAveragingAsianOption.Arguments {

        public AverageType averageType;
        public /*@Real*/ double runningAccumulator;
        public /*@Size*/ int pastFixings;
        public List<Date> fixingDates;


        //
        // public constructors
        //

        public ArgumentsImpl() {
            averageType = null;
            runningAccumulator = Constants.NULL_REAL; //FIXME is there central values?
            pastFixings = Constants.NULL_INTEGER; //FIXME is there central values?
            fixingDates = new ArrayList<Date>();
        }


        //
        // public methods
        //

        @Override
        public void validate() /*@ReadOnly*/{
            super.validate();
            QL.require(averageType!=null , "unspecified average type"); // TODO: message
            QL.require(pastFixings!=Constants.NULL_INTEGER, "null past-fixing number"); // TODO: message
            QL.require(!Double.isNaN(runningAccumulator), "null running product"); // TODO: message

            // TODO: code review :: please verify against QL/C++ code
            switch (averageType) {
            case Arithmetic:
                QL.require(runningAccumulator >= 0.0 , "non negative running sum required: not allowed"); // TODO: message
                break;
            case Geometric:
                QL.require(runningAccumulator > 0.0 , "positive running product required: not allowed"); // TODO: message
                break;
            default:
                throw new LibraryException("invalid average type"); // TODO: message
            }
        }

    }


    static public class ResultsImpl
            extends OneAssetOption.ResultsImpl
            implements DiscreteAveragingAsianOption.Results { /* marking interface */ }


    /**
     * Asian option on a single asset
     * <p>
     * Description of the terms and conditions of a discrete average out fixed strike option.
     *
     * @author <Richard Gomes>
     */
    static public abstract class EngineImpl
            extends GenericEngine<DiscreteAveragingAsianOption.Arguments, DiscreteAveragingAsianOption.Results>
            implements DiscreteAveragingAsianOption.Results {

        /**
         * Extra arguments for single-asset discrete-average Asian option
         */
        protected EngineImpl() {
            super(new DiscreteAveragingAsianOption.ArgumentsImpl(), new DiscreteAveragingAsianOption.ResultsImpl());
        }

    }



}
