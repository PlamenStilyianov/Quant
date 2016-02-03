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
 Copyright (C) 2003, 2004 Neil Firth
 Copyright (C) 2003, 2004 Ferdinando Ametrano
 Copyright (C) 2003, 2004, 2007 StatPro Italia srl

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
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.GenericEngine;
import org.jquantlib.pricingengines.PricingEngine;

/**
 * Barrier option on a single asset.
 * <p>
 * The analytic pricing engine will be used if none if passed.
 *
 * @author <Richard Gomes>
 *
 */
public class BarrierOption extends OneAssetOption {

    //
    // protected fields
    //

    protected BarrierType barrierType;
    protected double barrier;
    protected double rebate;


    //
    // public constructors
    //

    public BarrierOption(
            final BarrierType barrierType,
			final double barrier,
			final double rebate,
			final StrikedTypePayoff payoff,
			final Exercise exercise) {

    	super(payoff, exercise);
    	this.barrierType = barrierType;
    	this.barrier = barrier;
    	this.rebate = rebate;
    }


    //
    // overrides OneAssetStrikedOption
    //

    @Override
    public void setupArguments(final PricingEngine.Arguments arguments) {
        super.setupArguments(arguments);
        QL.require(BarrierOption.Arguments.class.isAssignableFrom(arguments.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
        final BarrierOption.ArgumentsImpl a = (BarrierOption.ArgumentsImpl) arguments;
        a.barrierType = barrierType;
        a.barrier = barrier;
        a.rebate = rebate;
    }


//
//    //
//    // inner interfaces
//    //
//
//    /**
//     * barrier option arguments
//     *
//     * @author Richard Gomes
//     */
//    public interface Arguments extends OneAssetOption.Arguments { /* marking interface */ }
//
//
//    /**
//     * barrier option results
//     *
//     * @author Richard Gomes
//     */
//    public interface Results extends OneAssetOption.Results { /* marking interface */ }
//
//
//    /**
//     * barrier option price engine
//     *
//     * @author Richard Gomes
//     */
//    public interface Engine extends PricingEngine, Observer { /* marking interface */ }


    //
    // inner classes
    //



    /**
     * This class defines validation for option arguments
     *
     * @author <Richard Gomes>
     *
     */
    static public class ArgumentsImpl extends OneAssetOption.ArgumentsImpl implements BarrierOption.Arguments {

        // TODO: refactor messages
        private static final String UNKNOWN_TYPE = "unknown type";

        //
        // public fields
        //

        // FIXME: public fields here is a bad design technique :(
        public BarrierType barrierType;
        public double barrier, rebate;


        //
        // public constructors
        //

        public ArgumentsImpl() {
            this.barrierType = BarrierType.Unknown;
            this.barrier = Constants.NULL_REAL;
            this.rebate = Constants.NULL_REAL;
        }


        //
        // public methods
        //

        /**
         * This method performs additional validation of needed to conform to the barrier type.
         * The validation is done by comparing the underlying price against the barrier type.
         *
         * @see org.jquantlib.pricingengines.arguments.OneAssetStrikedOptionArguments#validate()
         */
        @Override
        public void validate() {
            super.validate();

            switch (barrierType) {
            case DownIn:
            case UpIn:
            case DownOut:
            case UpOut:
              break;
            default:
                throw new LibraryException(UNKNOWN_TYPE); // QA:[RG]::verified
          }

          QL.require(!Double.isNaN(barrier), "no barrier given"); // TODO: message
          QL.require(!Double.isNaN(rebate), "no rebate given"); // TODO: message
        }
    }


    static public class ResultsImpl extends OneAssetOption.ResultsImpl implements BarrierOption.Results { /* marking class */ }


    /**
     * Barrier-option engine base class
     *
     * @author <Richard Gomes>
     */
    static public abstract class EngineImpl
            extends GenericEngine<BarrierOption.Arguments, OneAssetOption.Results> {

        final private BarrierOption.ArgumentsImpl a;

        protected EngineImpl() {
            super(new ArgumentsImpl(), new ResultsImpl());
            this.a = (BarrierOption.ArgumentsImpl)arguments_;
        }

        protected boolean triggered(final /*@Real*/ double underlying) /* @ReadOnly */ {
            switch (a.barrierType) {
              case DownIn:
              case DownOut:
                return underlying < a.barrier;
              case UpIn:
              case UpOut:
                return underlying > a.barrier;
              default:
                throw new LibraryException("Unknown type"); // TODO: message
            }
        }

    }

}
