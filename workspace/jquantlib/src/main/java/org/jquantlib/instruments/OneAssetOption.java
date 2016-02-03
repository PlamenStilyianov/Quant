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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
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

package org.jquantlib.instruments;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.GenericEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.util.Observer;

/**
 * Base class for options on a single asset
 *
 * @author Richard Gomes
 * @author Zahid Hussain
 */
public class OneAssetOption extends Option {

    private double delta_;
    private double deltaForward_;
    private double elasticity_;
    private double gamma_;
    private double theta_;
    private double thetaPerDay_;
    private double vega_;
    private double rho_;
    private double dividendRho_;
    private double strikeSensitivity_;
    private double itmCashProbability_;

    public OneAssetOption(final Payoff payoff,
            			  final Exercise exercise) {
        super(payoff, exercise);
    }
    
    @Override
	public boolean isExpired() /* @ReadOnly */ {
        return exercise.lastDate().lt(new Settings().evaluationDate() );
    }
    
    public double delta() {
        calculate();
        QL.require(delta_ != Constants.NULL_REAL, "delta not provided");
        return delta_;
    }
    
    public double deltaForward() /* @ReadOnly */ {
        calculate();
        QL.require(deltaForward_ != Constants.NULL_REAL , "forward delta not provided");
        return deltaForward_;
    }

    public double elasticity() {
        calculate();
        QL.require(elasticity_ != Constants.NULL_REAL, "elasticity not provided");
        return elasticity_;
    }
    
    public double gamma(){
        calculate();
        QL.require(gamma_ != Constants.NULL_REAL, "gamma not provided");
        return gamma_;
    }
    
    public double theta() {
        calculate();
        QL.require(theta_ != Constants.NULL_REAL, "theta not provided");
        return theta_;
    }

    public double thetaPerDay() {
        calculate();
        QL.require(thetaPerDay_ != Constants.NULL_REAL, "theta per-day not provided");
        return thetaPerDay_;
    }
    public double vega() {
        calculate();
        QL.require(vega_ != Constants.NULL_REAL, "vega not provided");
        return vega_;
    }
    
    public double rho() {
        calculate();
        QL.require(rho_ != Constants.NULL_REAL, "rho not provided");
        return rho_;
    }
    
    public double dividendRho() {
        calculate();
        QL.require(dividendRho_ != Constants.NULL_REAL, "dividend rho not provided");
        return dividendRho_;
    }
    
    public double strikeSensitivity() {
        calculate();
        QL.require(strikeSensitivity_ != Constants.NULL_REAL,
                   "strike sensitivity not provided");
        return strikeSensitivity_;
    }

    public double itmCashProbability() {
        calculate();
        QL.require(itmCashProbability_ != Constants.NULL_REAL,
                   "in-the-money cash probability not provided");
        return itmCashProbability_;
    }
    
    @Override
	public void setupExpired() {
        super.setupExpired();
        delta_ = deltaForward_ = elasticity_ = gamma_ = theta_ =
            thetaPerDay_ = vega_ = rho_ = dividendRho_ =
            strikeSensitivity_ = itmCashProbability_ = 0.0;
    }

    @Override
	public void fetchResults(final PricingEngine.Results r) {
        super.fetchResults(r);
        
        QL.require(OneAssetOption.Results.class.isAssignableFrom(r.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
        //final Greeks results = (Greeks)(r);

        final OneAssetOption.ResultsImpl ri = (OneAssetOption.ResultsImpl)r;
        final GreeksImpl     results = ri.greeks();
        
        QL.ensure(results != null,
                  "no greeks returned from pricing engine");
        /* no check on null values - just copy.
           this allows:
           a) to decide in derived options what to do when null
           results are returned (throw? numerical calculation?)
           b) to implement slim engines which only calculate the
           value---of course care must be taken not to call
           the greeks methods when using these.
        */
        delta_          = results.delta;
        gamma_          = results.gamma;
        theta_          = results.theta;
        vega_           = results.vega;
        rho_            = results.rho;
        dividendRho_    = results.dividendRho;

        final MoreGreeksImpl moreResults = ri.moreGreeks();
        QL.ensure(moreResults != null,
                  "no more greeks returned from pricing engine");
        /* no check on null values - just copy.
           this allows:
           a) to decide in derived options what to do when null
           results are returned (throw? numerical calculation?)
           b) to implement slim engines which only calculate the
           value---of course care must be taken not to call
           the greeks methods when using these.
        */
        deltaForward_       = moreResults.deltaForward;
        elasticity_         = moreResults.elasticity;
        thetaPerDay_        = moreResults.thetaPerDay;
        strikeSensitivity_  = moreResults.strikeSensitivity;
        itmCashProbability_ = moreResults.itmCashProbability;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Obtains {@link Greeks} and {@link MoreGreeks} calculated by a {@link PricingEngine}
     *
     * @see Greeks
     * @see MoreGreeks
     * @see PricingEngine
     */
//    @Override
//    public void fetchResults(final PricingEngine.Results results) /* @ReadOnly */ {
//        super.fetchResults(results);
//
//        // bind a Results interface to specific classes
//        QL.require(OneAssetOption.Results.class.isAssignableFrom(results.getClass()), ReflectConstants.WRONG_ARGUMENT_TYPE); // QA:[RG]::verified
//        final OneAssetOption.ResultsImpl r = (OneAssetOption.ResultsImpl)results;
//        final GreeksImpl     greeks = r.greeks();
//        final MoreGreeksImpl moreGreeks = r.moreGreeks();
//
//        //
//        // No check on Double.NaN values - just copy. this allows:
//        // a) To decide in derived options what to do when null results are returned
//        //    (throw numerical calculation?)
//        // b) To implement slim engines which only calculate the value.
//        //    Of course care must be taken not to call the greeks methods when using these.
//        //
//        delta          = greeks.delta;
//        gamma          = greeks.gamma;
//        theta          = greeks.theta;
//        vega           = greeks.vega;
//        rho            = greeks.rho;
//        dividendRho    = greeks.dividendRho;
//
//        //
//        // No check on Double.NaN values - just copy. this allows:
//        // a) To decide in derived options what to do when null results are returned
//        //    (throw numerical calculation?)
//        // b) To implement slim engines which only calculate the value.
//        //    Of course care must be taken not to call the greeks methods when using these.
//        //
//        deltaForward       = moreGreeks.deltaForward;
//        elasticity         = moreGreeks.elasticity;
//        thetaPerDay        = moreGreeks.thetaPerDay;
//        itmCashProbability = moreGreeks.itmCashProbability;
//    }


    //
    // public inner interfaces
    //
    //! %Results from single-asset option calculation
//    class OneAssetOption::results : public Instrument::results,
//                                    public Greeks,
//                                    public MoreGreeks {
//      public:
//        void reset() {
//            Instrument::results::reset();
//            Greeks::reset();
//            MoreGreeks::reset();
//        }
//    };
//
//    class OneAssetOption::engine :
//        public GenericEngine<OneAssetOption::arguments,
//                             OneAssetOption::results> {};

    /**
     * basic option arguments
     *
     * @author Richard Gomes
     */
    public interface Arguments extends Option.Arguments { /* marking interface */ }

    /**
     * Results from single-asset option calculation
     *
     * @author Richard Gomes
     */
    public interface Results extends Instrument.Results, Option.Greeks, Option.MoreGreeks { /* marking interface */ }


    public interface Engine extends PricingEngine, Observer { /* marking interface */ }



    //
    // public inner classes
    //

    static public class ArgumentsImpl extends Option.ArgumentsImpl implements OneAssetOption.Arguments { /* marking interface */ }


    /**
     * Results from single-asset option calculation
     *
     * @author Richard Gomes
     */
    static public class ResultsImpl extends Instrument.ResultsImpl implements OneAssetOption.Results {

        private final Option.GreeksImpl       greeks;
        private final Option.MoreGreeksImpl   moreGreeks;

        public ResultsImpl() {
            greeks = new Option.GreeksImpl();
            moreGreeks = new Option.MoreGreeksImpl();
        }

        final public Option.GreeksImpl greeks() {
            return greeks;
        }

        final public Option.MoreGreeksImpl moreGreeks() {
            return moreGreeks;
        }

        //
        // implements Results
        //

        @Override
        public void reset() /* @ReadOnly */ {
            super.reset();
            greeks.reset();
            moreGreeks.reset();
        }

    }


    /**
     * The pricing engine for one-asset options
     *
     * @author Richard Gomes
     */
    static abstract public class EngineImpl
            extends GenericEngine<OneAssetOption.Arguments, OneAssetOption.Results>
            implements OneAssetOption.Engine {

        public EngineImpl() {
            super(new ArgumentsImpl(), new ResultsImpl());
        }

        public EngineImpl(final OneAssetOption.Arguments arguments, final OneAssetOption.Results results) {
            super(arguments, results);
        }

    }

}
