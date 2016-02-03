/*
Copyright (C) 2008 Aaron Roth

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

The code below is an adaptation of the work of Adrian King
(<code>ceroxylon<b> at </b>hotmail.com</code>), which is in turn an adaptation
in Java of the C code, version 1.3, of the orginators of SIMD-oriented
Fast Mersenne Twister (SFMT) algorithm, Mutsuo Saito (Hiroshima University) and
Makoto Matsumoto (Hiroshima University). See <a
href="http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/SFMT/index.html">.

Their original algorithm supports generators of various periods; the code below
currently supports generators of period 2<sup>19937</sup> &minus; 1 only.

<p>
The license (a modified BSD License) for the original C code from which this
code is adapted:

<pre>
Copyright (c) 2006,2007 Mutsuo Saito, Makoto Matsumoto and Hiroshima
University. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following
disclaimer in the documentation and/or other materials provided
with the distribution.
 * Neither the name of the Hiroshima University nor the names of
its contributors may be used to endorse or promote products
derived from this software without specific prior written
permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
</pre>
 */
package org.jquantlib.experimental.math.randomnumbers;


import org.jquantlib.experimental.math.randomnumbers.UniformRng;
import org.jquantlib.math.randomnumbers.SeedGenerator;


/**
 * @author Aaron Roth
 */
public class SFMTUniformRng extends UniformRng<Integer> {
    /**
     * Mersenne Exponent. The period of the sequence is a multiple of
     * 2<sup><code>MEXP</code></sup> &minus; 1. If you adapt this code to
     * support a different exponent, you must change many of the other constants
     * here as well; consult the original C code.
     */
    private static final int MEXP = 19937;
    
    /**
     * The SFMT generator has an internal state array of 128-bit integers, and
     * <code>N</code> is its size.
     */
    private static final int N = MEXP / 128 + 1;
    
    /**
     * <code>N32</code> is the size of internal state array when regarded as an
     * array of 32-bit integers. This algorithm could, for instance, be filled
     * out for longs, i.e. 64-bit integers. (Various other hard-code things,
     * e.g. in the method, doRecursion(), would also need to be suitably
     * modified.
     */
    private static final int N32 = N * 4;
    
    /**
     * The pick up position of the array.
     */
    private static final int POS1 = 122;
    
    /**
     * The parameter of shift left as four 32-bit registers.
     */
    private static final int SL1 = 18;
    
    /**
     * The parameter of shift left as one 128-bit register. The 128-bit integer
     * is shifted by <code>SL2 * 8</code> bits.
     */
    private static final int SL2 = 1;
    
    /**
     * The parameter of shift right as four 32-bit registers.
     */
    private static final int SR1 = 11;
    
    /**
     * The parameter of shift right as one 128-bit register. The 128-bit integer
     * is shifted by <code>SL2 * 8</code> bits.
     */
    private static final int SR2 = 1;
    
    /**
     * Bitmask parameters used in the recursion to break the symmetry of SIMD.
     */
    private static final int MSK1 = 0xdfffffef;
    private static final int MSK2 = 0xddfecb7f;
    private static final int MSK3 = 0xbffaffff;
    private static final int MSK4 = 0xbffffff6;
    
    /**
     * Parts of the 128-bit period certification vector.
     */
    private static final int PARITY1 = 0x00000001;
    private static final int PARITY2 = 0x00000000;
    private static final int PARITY3 = 0x00000000;
    private static final int PARITY4 = 0x13c9e684;

    /**
     * A parity check vector which certifies the period of 2<sup>{@link
     * #MEXP}</sup>.
     */
    private static final int parity[] = {PARITY1, PARITY2, PARITY3, PARITY4};    
    
    
    /**
     * The internal state array. Blocks of four consecutive <code>int</code>s
     * are often treated as a single 128-bit integer that is
     * little-endian&mdash;that is, its low-order bits are at lower indices in
     * the array, and high-order bits at higher indices.
     */
    private final int[] sfmt = new int[N32];
    
    /**
     * Index counter of the next <code>int</code> to return from {@link #next}.
     */
    private int idx = N32;

    
    
    public SFMTUniformRng() {
        long seed = SeedGenerator.getInstance().get();
        seed((int) seed, (int) (seed >>> 32));
    }

    /**
     * Constructor that initializes the internal state array by calling {@link
     * #seedInitialization} with the specified argument.
     *
     * @param seed      initial seed for this generator.
     */
    public SFMTUniformRng(int seed) {
        seed(seed);
    }

    /**
     * Applies the recursion formula, the heart of the block-generation of
     * the pseudorandom numbers.
     *
     * @param r         output array.
     * @param rI        index in <code>r</code>.
     * @param a         state array.
     * @param aI        index in <code>a</code>.
     * @param b         state array.
     * @param bI        index in <code>b</code>.
     * @param c         state array.
     * @param cI        index in <code>c</code>.
     * @param d         state array.
     * @param dI        index in <code>d</code>.
     */
    private void doRecursion(int[] r, int rI, int[] a, int aI, int[] b, int bI, int[] c, int cI, int[] d, int dI) {
        final int lShift = SL2 * 8;

        int a0 = a[aI],
            a1 = a[aI + 1],
            a2 = a[aI + 2],
            a3 = a[aI + 3];

        long    hi = ((long) a3 << 32) | (a2 & (-1L >>> 32)),
                lo = ((long) a1 << 32) | (a0 & (-1L >>> 32)),
             outLo = lo << lShift,
             outHi = (hi << lShift) | (lo >>> (64 - lShift));

        int x0 = (int) outLo,
            x1 = (int) (outLo >>> 32),
            x2 = (int) outHi,
            x3 = (int) (outHi >>> 32);

        final int rShift = SR2 * 8;

        hi = ((long) c[cI + 3] << 32) | (c[cI + 2] & (-1L >>> 32));
        lo = ((long) c[cI + 1] << 32) | (c[cI] & (-1L >>> 32));
        outHi = hi >>> rShift;
        outLo = (lo >>> rShift) | (hi << (64 - rShift));

        int y0 = (int) outLo,
            y1 = (int) (outLo >>> 32),
            y2 = (int) outHi,
            y3 = (int) (outHi >>> 32);

        r[rI] =
            a0 ^ x0 ^ ((b[bI] >>> SR1) & MSK1) ^ y0 ^ (d[dI] << SL1);

        r[rI + 1] =
            a1 ^ x1 ^ ((b[bI + 1] >>> SR1) & MSK2) ^ y1 ^ (d[dI + 1] << SL1);

        r[rI + 2] =
            a2 ^ x2 ^ ((b[bI + 2] >>> SR1) & MSK3) ^ y2 ^ (d[dI + 2] << SL1);

        r[rI + 3] =
            a3 ^ x3 ^ ((b[bI + 3] >>> SR1) & MSK4) ^ y3 ^ (d[dI + 3] << SL1);
    }

    /**
     * Fills the internal state array with pseudorandom integers.
     */
    private void genRandAll() {
        int  i = 0,
            r1 = 4 * (N - 2),
            r2 = 4 * (N - 1);
        
        for (; i < 4 * (N - POS1); i += 4) {
            doRecursion(sfmt, i, sfmt, i, sfmt, i + 4 * POS1, sfmt, r1, sfmt, r2);
            r1 = r2;
            r2 = i;
        }
        
        for (; i < 4 * N; i += 4) {
            doRecursion(sfmt, i, sfmt, i, sfmt, i + 4 * (POS1 - N), sfmt, r1, sfmt, r2);
            r1 = r2;
            r2 = i;
        }
    }

    
    /**
     * Certifies the period of 2<sup>{@link #MEXP}</sup>.
     */
    private void periodCertification () {
        int inner = 0;
        
        for (int i = 0; i < 4; i++)
            inner ^= sfmt[i] & parity[i];
        
        for (int i = 16; i > 0; i >>= 1)
            inner ^= inner >> i;
        
        // check OK, return
        if ((inner & 1) != 0)
            return;
        
        // otherwise, twiddle a couple bits of the internal state
        for (int i = 0; i < 4; i++) {
            int work = 1;
            
            for (int j = 0; j < 32; j++) {
                if ((work & parity[i]) != 0) {
                    sfmt[i] ^= work;
                    return;
                }
                
                work <<= 1;
            }
        }
    }    


    /**
     * Generates and returns the next 32-bit pseudorandom number.
     *
     * @return          next int.
     */
    @Override
    public Integer next() {
        // If we've exahusted the current internal state, generate a new one.
        if (idx >= N32) {
            genRandAll();
            idx = 0;
        }
        
        return sfmt[idx++];
    }
    
    /**
     * Initializes the internal state array with a 32-bit seed.
     * The version of {@link #seedInitialization(int[] seeds)}
     * that takes an array of ints as its seeds is to be preferred.
     *
     * @param seed      32-bit seed.
     */
    public void seed(int seed) {
        sfmt[0] = seed;
        
        for (int i = 1; i < N32; i++) {
            int prev = sfmt[i - 1];
            sfmt[i] = 1812433253 * (prev ^ (prev >>> 30)) + i;
        }
        
        periodCertification();
        idx = N32;
    }
    
    /**
     * Helper function used by {@link #seedInitialization(int[] seeds)}.
     *
     * @param x         32-bit integer.
     * @return          32-bit integer.
     */
    private int func1(int x) {
        return (x ^ (x >>> 27)) * 1664525;
    }

    /**
     * Helper function used by {@link #seedInitialization(int[] seeds)}.
     *
     * @param x         32-bit integer.
     * @return          32-bit integer.
     */
    private int func2(int x) {
        return (x ^ (x >>> 27)) * 1566083941;
    }

    /**
     * Initializes the internal state array with a list of 32-bit integers.
     *
     * @param seeds       array of 32-bit integers, used as seeds.
     */
    @Override
    public void seed(int... seeds) {
        int lag = N32 >= 623 ? 11 : N32 >= 68 ? 7 : N32 >= 39 ? 5 : 3;
        int mid = (N32 - lag) / 2;
        
        for (int i = sfmt.length-1; i >= 0; i--) {
            sfmt[i] = 0x8b8b8b8b;
        }
        
        int count = seeds.length >= N32 ? seeds.length : N32 - 1;
        int r = func1(0x8b8b8b8b);
        
        sfmt[mid] += r;
        r += seeds.length;
        sfmt[mid + lag] += r;
        sfmt[0] = r;
        
        int i = 1, j = 0;
        for (; j < count && j < seeds.length; j++) {
            r = func1(sfmt[i] ^ sfmt[(i+mid) % N32] ^ sfmt[(i+N32-1) % N32]);
            sfmt[(i + mid) % N32] += r;
            r += seeds[j] + i;
            sfmt[(i + mid + lag) % N32] += r;
            sfmt[i] = r;
            i = (i + 1) % N32;
        }
        
        for (; j < count; j++) {
            r = func1(sfmt[i] ^ sfmt[(i+mid) % N32] ^ sfmt[(i+N32-1) % N32]);
            sfmt[(i + mid) % N32] += r;
            r += i;
            sfmt[(i + mid + lag) % N32] += r;
            sfmt[i] = r;
            i = (i + 1) % N32;
        }
        
        for (j = 0; j < N32; j++) {
            r = func2(sfmt[i] + sfmt[(i+mid) % N32] + sfmt[(i+N32-1) % N32]);
            sfmt[(i + mid) % N32] ^= r;
            r -= i;
            sfmt[(i + mid + lag) % N32] ^= r;
            sfmt[i] = r;
            i = (i + 1) % N32;
        }
        
        periodCertification();
        idx = N32;
    }
}
