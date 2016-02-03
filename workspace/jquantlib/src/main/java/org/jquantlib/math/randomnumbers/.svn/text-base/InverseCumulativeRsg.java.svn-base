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

package org.jquantlib.math.randomnumbers;

import org.jquantlib.methods.montecarlo.Sample;

/**
 * Inverse cumulative random sequence generator
 * <p>
 * It uses a sequence of uniform deviate in (0, 1) as the source of cumulative distribution values. Then an inverse cumulative
 * distribution is used to calculate the distribution deviate.
 * 
 * The uniform deviate sequence is supplied by USG.
 * 
 * @author Richard Gomes
 */

//TODO: why USG and not RSG? What's the difference between URSG and RSG??
public class InverseCumulativeRsg<USG extends UniformRandomSequenceGenerator, IC extends InverseCumulative>
            implements UniformRandomSequenceGenerator {

    private final /*@NonNegative*/ int  dimension;
    private final USG                   ursg;
    
    private Sample<double[]>            sequence;
    private IC                          ic;
    private double                      weight;
    

    public InverseCumulativeRsg(final USG ursg) {
        if (System.getProperty("EXPERIMENTAL")==null) {
            throw new UnsupportedOperationException("Work in progress");
        }
        this.ursg = ursg;
        this.dimension = this.ursg.dimension();
        this.weight = 1.0;
        this.sequence = new Sample<double[]>(new double[this.dimension], this.weight);
        this.ic = null;
    }

    public InverseCumulativeRsg(final USG ursg, final IC ic) {
        this(ursg);
        this.ic = ic;
    }


    //
    // implements UniformRandomSequenceGenerator
    //

    @Override
    public/*@NonNegative*/int dimension() /* @ReadOnly */{
        return this.dimension;
    }

    @Override
    //FIXME: original QuantLib does not declare this method.
    public long[] nextInt32Sequence() /* @ReadOnly */ {
        throw new UnsupportedOperationException(); //TODO: message
    }
    
    /**
     * @return next sample from the Gaussian distribution
     */
    @Override
    public Sample<double[]> nextSequence() /* @ReadOnly */ {
        if (System.getProperty("EXPERIMENTAL")==null) throw new UnsupportedOperationException("Work in progress");
        
        Sample<double[]> sample = this.ursg.nextSequence();
        double[] v = sample.value();
        this.weight = sample.weight();
        
        double[] d = new double[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            d[i] = this.ic.op(v[i]);
        }

        return new Sample<double[]>(d, weight);
    }

    @Override
    public final Sample<double[]> lastSequence() /* @ReadOnly */ {
        return this.sequence;
    }

}
