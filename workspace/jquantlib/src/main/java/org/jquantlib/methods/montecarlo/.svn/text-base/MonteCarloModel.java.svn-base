/*
 Copyright (C) 2007 Richard Gomes

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
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

package org.jquantlib.methods.montecarlo;

import org.jquantlib.math.randomnumbers.RandomNumberGenerator;
import org.jquantlib.math.statistics.Statistics;

/**
 *
 * General-purpose Monte Carlo model for path samples
 * <p>
 * The template arguments of this class correspond to available policies for the particular model to be instantiated---i.e., whether
 * it is single- or multi-asset, or whether it should use pseudo-random or low-discrepancy numbers for path generation. Such
 * decisions are grouped in trait classes so as to be orthogonal---see mctraits.hpp for examples.
 * <p>
 *
 * The constructor accepts two safe references, i.e. two smart pointers, one to a path generator and the other to a path pricer. In
 * case of control variate technique the user should provide the additional control option, namely the option path pricer and the
 * option value.
 *
 * @category mcarlo
 *
 * @author Richard Gomes
 */
public class MonteCarloModel<MC extends Variate, RNG extends RandomNumberGenerator, S extends Statistics> {

	public MonteCarloModel() {
        if (System.getProperty("EXPERIMENTAL")==null)
            throw new UnsupportedOperationException("Work in progress");
	}

//    private PathGeneratorType pathGenerator_;
//    private PathPricerType pathPricer_;

      private S sampleAccumulator_;
//    private boolean isAntitheticVariate_;
//    private PathPricerType cvPathPricer_;
//    private ResultType cvOptionValue_;
//    private boolean isControlVariate_;

	public void addSamples(final int size){
		//  TODO... we have to work on this a bit.

	}

	public S sampleAccumulator(){
		return sampleAccumulator_;
	}


}


//template <template <class> class MC, class RNG, class S = Statistics>
//class MonteCarloModel {
//  public:
//    typedef MC<RNG> mc_traits;
//    typedef RNG rng_traits;
//    typedef typename MC<RNG>::path_generator_type path_generator_type;
//    typedef typename MC<RNG>::path_pricer_type path_pricer_type;
//    typedef typename path_generator_type::sample_type sample_type;
//    typedef typename path_pricer_type::result_type result_type;
//    typedef S stats_type;
//    // constructor
//    MonteCarloModel(
//              const boost::shared_ptr<path_generator_type>& pathGenerator,
//              const boost::shared_ptr<path_pricer_type>& pathPricer,
//              const stats_type& sampleAccumulator,
//              bool antitheticVariate,
//              const boost::shared_ptr<path_pricer_type>& cvPathPricer
//                    = boost::shared_ptr<path_pricer_type>(),
//              result_type cvOptionValue = result_type())
//    : pathGenerator_(pathGenerator), pathPricer_(pathPricer),
//      sampleAccumulator_(sampleAccumulator),
//      isAntitheticVariate_(antitheticVariate),
//      cvPathPricer_(cvPathPricer), cvOptionValue_(cvOptionValue) {
//        if (!cvPathPricer_)
//            isControlVariate_ = false;
//        else
//            isControlVariate_ = true;
//    }
//    void addSamples(Size samples);
//    const stats_type& sampleAccumulator(void) const;
//  private:
//    boost::shared_ptr<path_generator_type> pathGenerator_;
//    boost::shared_ptr<path_pricer_type> pathPricer_;
//    stats_type sampleAccumulator_;
//    bool isAntitheticVariate_;
//    boost::shared_ptr<path_pricer_type> cvPathPricer_;
//    result_type cvOptionValue_;
//    bool isControlVariate_;
//};
//
//
//// inline definitions
//template <template <class> class MC, class RNG, class S>
//inline void MonteCarloModel<MC,RNG,S>::addSamples(Size samples) {
//    for(Size j = 1; j <= samples; j++) {
//
//        sample_type path = pathGenerator_->next();
//        result_type price = (*pathPricer_)(path.value);
//
//        if (isControlVariate_)
//            price += cvOptionValue_-(*cvPathPricer_)(path.value);
//
//        if (isAntitheticVariate_) {
//            path = pathGenerator_->antithetic();
//            result_type price2 = (*pathPricer_)(path.value);
//            if (isControlVariate_)
//                price2 += cvOptionValue_-(*cvPathPricer_)(path.value);
//            sampleAccumulator_.add((price+price2)/2.0, path.weight);
//        } else {
//            sampleAccumulator_.add(price, path.weight);
//        }
//    }
//}
//
//template <template <class> class MC, class RNG, class S>
//inline const typename MonteCarloModel<MC,RNG,S>::stats_type&
//MonteCarloModel<MC,RNG,S>::sampleAccumulator() const {
//    return sampleAccumulator_;
//}
//
