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

package org.jquantlib.math;

import org.jquantlib.math.distributions.GammaFunction;

/**
 * Computes n factorial; <code>n!</code>
 * 
 * @author Richard Gomes
 */
public class Factorial {

    private static final GammaFunction gammaFunction = new GammaFunction();

    private static final double firstFactorials[] = {
        1.0,                                  1.0,
        2.0,                                  6.0,
        24.0,                                 120.0,
        720.0,                                5040.0,
        40320.0,                              362880.0,
        3628800.0,                            39916800.0,
        479001600.0,                          6227020800.0,
        87178291200.0,                        1307674368000.0,
        20922789888000.0,                     355687428096000.0,
        6402373705728000.0,                   121645100408832000.0,
        2432902008176640000.0,                51090942171709440000.0,
        1124000727777607680000.0,             25852016738884976640000.0,
        620448401733239439360000.0,           15511210043330985984000000.0,
        403291461126605635584000000.0,        10888869450418352160768000000.0

    };

    private static final int tabulated = firstFactorials.length-1;

    /**
     * Computes n factorial, <code>n!</code>
     * @param n
     * @return <code> n!</code>
     */
     public double get(final int n) {
         check(n);
         if (n <= tabulated)
             return firstFactorials[n];
         return Math.exp(gammaFunction.logValue(n + 1));
     }


     /**
      * Computes log of n factorial, <code>ln(n!)</code>
      * @param n
      * @return <code>ln(n!)</code>
      */
     public double ln(final int n) {
         check(n);
         if (n <= tabulated)
             return Math.log(firstFactorials[n]);
         return gammaFunction.logValue(n + 1);
     }

     /**
      * Checks if <code>n</code> is >= 0.
      * 
      * @param n
      * @throws Arithmetic Exception if <code>n<0</code>
      */
     private void check(final int n) {
         if (n < 0) throw new ArithmeticException("Factorial: expected n >= 0");
     }
}

