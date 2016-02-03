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

package org.jquantlib.math.distributions;

import org.jquantlib.math.Ops;

/**
 * 
 * @author Dominik Holenstein
 *
 */

// TODO Add test case for MoroInverseCumulativeNormal class.
public class MoroInverseCumulativeNormal extends NormalDistribution implements Ops.DoubleOp {
	
    //
    // static final fields (constants)
	//
	
	static final double a0_ =   2.50662823884;
	static final double a1_ = -18.61500062529;
	static final double a2_ =  41.39119773534;
	static final double a3_ = -25.44106049637;

	static final double b0_ =  -8.47351093090;
	static final double b1_ =  23.08336743743;
	static final double b2_ = -21.06224101826;
	static final double b3_ =   3.13082909833;

	static final double c0_ = 0.3374754822726147;
	static final double c1_ = 0.9761690190917186;
	static final double c2_ = 0.1607979714918209;
	static final double c3_ = 0.0276438810333863;
	static final double c4_ = 0.0038405729373609;
	static final double c5_ = 0.0003951896511919;
	static final double c6_ = 0.0000321767881768;
	static final double c7_ = 0.0000002888167364;
	static final double c8_ = 0.0000003960315187;

	
	//
	// public constructors
	//
	
    public MoroInverseCumulativeNormal() {
    	super();
    }

    public MoroInverseCumulativeNormal(double average, double sigma) {
    	super(average, sigma);
    }    
    
    
    //
    // implements Ops.DoubleOp
    //
        
    @Override
    public double op(final double x) /* Read-only */ {
        final double temp=x-0.5;
        
        // x has to be between 0.00 and 1.00
		if (x <= 0.0) return 0.00;
		if (x >=1.0)  return 1.00;

        double result;
        if (Math.abs(temp) < 0.42) {
            // Beasley and Springer, 1977
            result=temp*temp;
            result=temp*
                (((a3_*result+a2_)*result+a1_)*result+a0_) /
                ((((b3_*result+b2_)*result+b1_)*result+b0_)*result+1.0);
        } else {
            // improved approximation for the tail (Moro 1995)
            if (x<0.5)
                result = x;
            else
                result=1.0-x;
            result = Math.log(-Math.log(result));
            result = c0_+result*(c1_+result*(c2_+result*(c3_+result*
                                   (c4_+result*(c5_+result*(c6_+result*
                                                       (c7_+result*c8_)))))));
            if (x<0.5)
                result=-result;
        }
        return average + result * sigma;
    }

}
