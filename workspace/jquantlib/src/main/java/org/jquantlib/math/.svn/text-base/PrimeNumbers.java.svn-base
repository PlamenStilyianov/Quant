/*
 Copyright (C) 2008 Richard Gomes
 Copyright (C) 2008 Q. Boiler

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

import java.util.ArrayList;


/**
 * This class creates an ArrayList of prime numbers,
 * stored as long.  This class is 'bootstrapped',
 * and longs are calculated on demand.  Therefore,
 * if a time senstitve aspect of the application requires
 * the quick retrieval of a prime, this array should be
 * pre-fetched and populated before that quick need arises.
 * <BR>
 * <BR>
 * Once a prime has been calculated, it will be stored
 * for the life of the JVM.
 * <BR>
 * <B>ALGO:</B>
 * The algorithm used from QuantLib is not
 * very efficient, because it skips only
 * multiples of 2, and checks each number after that.
 * really, it could also skip multiples of 3,5,7,etc...
 *
 * In ohter words once a prime has been calculated,
 * you can just skip over that multiple too.
 *
 * //  TODO fill in a latex algo for later.
 * {@latex$ Prime_{next}}
 * @author Q.Boiler  email is at Yahoo
 */
public class PrimeNumbers {


    // the first two primes are mandatory for bootstrapping
    // optional additional precomputed primes
    final long[]  firstPrimes = {
            2,  3,
            5,  7, 11, 13, 17, 19, 23, 29,
            31, 37, 41, 43, 47 };


    private final ArrayList<Long> primeNumbers = new ArrayList<Long>();


    //  int was chosen because as a parameter if we are calculating.
    //  more than 2Billion Prime numbers with this algorithm,
    //  we are going to run for a long time.
    public long get(final int absoluteIndex) {
        if (primeNumbers.isEmpty()) {
            for (final long prime : firstPrimes) {
                primeNumbers.add(prime);
            }
        }
        while (primeNumbers.size()<=absoluteIndex) {
            nextPrimeNumber();
        }
        return primeNumbers.get(absoluteIndex);
    }

    // TODO, this was a copy from QuantLib.
    // Not really sure if nextPrimeNumber() should
    // both return a long and mutate the primeNumbers collection.
    public long nextPrimeNumber() {
	final int size = primeNumbers.size();
        long p, n, m = primeNumbers.get(size-1);
        do {
            // skip the even numbers
            m += 2;

	    //  From QuantLib.
            //n = static_cast<BigNatural>(std::sqrt(Real(m)));
	    //  my interpretation.
	    n = (long)(Math.sqrt(m));

            // i=1 since the even numbers have already been skipped
            int i = 1;

            do {
                p = primeNumbers.get(i);
                ++i;
		// if m%p is not zero,
		// keep checking until p<=n.
		//  once p>n, then 2*p is > m,
		//  and 2 is the smallest prime.
            } while ( (m%p!=0) && (p<=n) );
        } while ( p<=n );

        primeNumbers.add(m);
        return m;
    }
}
