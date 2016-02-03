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
 Copyright (C) 2003, 2004, 2005 StatPro Italia srl

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

/**
 * @author Richard Gomes
 */
public class MultiVariate<RNG extends RandomNumberGenerator> implements Variate {
	
	public MultiVariate() {
        if (System.getProperty("EXPERIMENTAL")==null) {
            throw new UnsupportedOperationException("Work in progress");
        }
	}

    // FIXME: decide how to implement
    
}


////! default Monte Carlo traits for multi-variate models
//template <class RNG = PseudoRandom>
//struct MultiVariate {
//    typedef RNG rng_traits;
//    typedef MultiPath path_type;
//    typedef PathPricer<path_type> path_pricer_type;
//    typedef typename RNG::rsg_type rsg_type;
//    typedef MultiPathGenerator<rsg_type> path_generator_type;
//    enum { allowsErrorEstimate = RNG::allowsErrorEstimate };
//};
