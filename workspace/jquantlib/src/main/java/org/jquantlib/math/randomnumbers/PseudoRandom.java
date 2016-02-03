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

package org.jquantlib.math.randomnumbers;

import org.jquantlib.QL;
import org.jquantlib.math.distributions.InverseCumulativeNormal;

/**
 * @author Richard Gomes
 */
public class PseudoRandom /* <RNG extends MersenneTwisterUniformRng, IC extends InverseCumulativeNormal> */
        extends GenericPseudoRandom<MersenneTwisterUniformRng, InverseCumulativeNormal> {

	public PseudoRandom(final Class<? extends UniformRandomSequenceGenerator> classRNG, final Class<? extends InverseCumulative> classIC) {
		super(classRNG, classIC);
	}
	
	
	
    @Override
    public InverseCumulativeRsg<RandomSequenceGenerator<MersenneTwisterUniformRng>, InverseCumulativeNormal> makeSequenceGenerator(
            final /*@NonNegative*/ int dimension, 
            final /*@NonNegative*/ long seed) {

    	QL.validateExperimentalMode();
    	return super.makeSequenceGenerator(dimension, seed);
    }

}