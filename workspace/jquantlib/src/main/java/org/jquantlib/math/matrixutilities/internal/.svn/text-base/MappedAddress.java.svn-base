/*
JQuantLib is Copyright (c) 2007, Richard Gomes

All rights reserved.

This source code is release under the BSD License.

JQuantLib includes code taken from QuantLib.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    Neither the names of the copyright holders nor the names of the QuantLib
    Group and its contributors may be used to endorse or promote products
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/
package org.jquantlib.math.matrixutilities.internal;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import org.jquantlib.lang.exceptions.LibraryException;

/**
 * This is the base class for all accessors on top of non-contiguous intervals
 *
 * @author Richard Gomes
 */
public abstract class MappedAddress implements Address {

    protected final double[] data;
    protected final int row0;
    protected final int row1;
    protected final Address chain;
    protected final int col0;
    protected final int col1;
    protected final Set<Address.Flags> flags;
    protected final boolean contiguous;
    protected final int rows;
    protected final int cols;
    protected final int offset;

    protected final int ridx[];
    protected final int cidx[];

    private final int base;
    private final int last;


    //
    // public methods
    //

    public MappedAddress(
            final double[] data,
            final int row0, final int row1,
            final Address chain,
            final int[] cidx,
            final Set<Address.Flags> flags,
            final boolean contiguous,
            final int rows, final int cols) {
        this(data, makeIndex(row0, row1), chain, cidx, flags, contiguous, rows, cols);
    }


    public MappedAddress(
            final double[] data,
            final int[] ridx,
            final Address chain,
            final int col0, final int col1,
            final Set<Address.Flags> flags,
            final boolean contiguous,
            final int rows, final int cols) {
        this(data, ridx, chain, makeIndex(col0, col1), flags, contiguous, rows, cols);
    }


    public MappedAddress(
            final double[] data,
            final int[] ridx,
            final Address chain,
            final int[] cidx,
            final Set<Address.Flags> flags,
            final boolean contiguous,
            final int rows, final int cols) {
        this.data = data; // DO NOT use clone: direct reference on purpose!
        this.chain = chain;
        this.offset = isFortran() ? 1 : 0;

        // obtain contiguous flag from chained address mapping
        boolean contiguous_ = contiguous & (chain==null || chain.isContiguous());

        // clone indexes and apply offset
        this.ridx  = ridx.clone();
        for (int i=0; i<this.ridx.length; i++) {
            this.ridx[i] += offset;
        }
        // find limiting rows from ridx
        final int[] rorder = this.ridx.clone();
        Arrays.sort(rorder);
        // find lower index and upper indexes
        final int row0 = rorder[0];
        final int row1 = rorder[rorder.length-1];
        // determine if it is a contiguous interval
        contiguous_ &= contiguous(rorder);

        // clone indexes and apply offset
        this.cidx  = cidx.clone();
        for (int i=0; i<this.cidx.length; i++) {
            this.cidx[i] += offset;
        }
        // find limiting rows from ridx
        final int[] corder = this.cidx.clone();
        Arrays.sort(corder);
        // find lower index and upper indexes
        final int col0 = corder[0];
        final int col1 = corder[corder.length-1];
        // determine if it is a contiguous interval
        contiguous_ &= contiguous(corder);

        // remove Address.Flags.CONTIGUOUS from this Set<Address.Flags> if needed
        this.contiguous = contiguous_;

        this.flags  = (flags != null) ? flags : (chain != null) ? chain.flags() : EnumSet.noneOf(Address.Flags.class);

        if (chain==null) {
            this.row0 = row0;
            this.col0 = col0;
        } else {
            this.row0 = row0 + chain.row0() - (chain.isFortran() ? 1 : 0);
            this.col0 = col0 + chain.col0() - (chain.isFortran() ? 1 : 0);
        }
        this.row1 = this.row0 + (row1-row0);
        this.col1 = this.col0 + (col1-col0);
        this.rows = (chain==null) ? rows : chain.rows();
        this.cols = (chain==null) ? cols : chain.cols();

        this.base = (row0+offset)*cols + (col0+offset);
        this.last = (row1+offset)*cols + (col1+offset);
    }


    //
    // implements Cloneable
    //

    @Override
    public MappedAddress clone() {
        try {
            return (MappedAddress) super.clone();
        } catch (final Exception e) {
            throw new LibraryException(e);
        }
    }



    // implements Address
    //

    @Override
    public boolean isContiguous() {
        return contiguous;
    }

    @Override
    public boolean isFortran() {
        return flags!=null && flags.contains(Address.Flags.FORTRAN);
    }

    @Override
    public Set<Address.Flags> flags() {
        return flags;
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int cols() {
        return cols;
    }

    @Override
    public int row0() {
        return row0;
    }

    @Override
    public int col0() {
        return col0;
    }

    @Override
    public int base() {
        return base;
    }

    @Override
    public int last() {
        return last;
    }

    //
    // private static methods
    //

    private final static int[] makeIndex(final int idx0, final int idx1) {
        final int[] result = new int[idx1-idx0+1];
        for (int i=idx0; i<=idx1; i++) {
            result[i] = i;
        }
        return result;
    }

    private static boolean contiguous(final int array[]) {
        final int[] rorder = array.clone();
        Arrays.sort(rorder);
        // determine if it is a contiguous interval
        boolean result = false;
        int  curr = rorder[0];
        for (final int element : rorder) {
            result = (element == curr);
            if (!result) {
                break;
            }
            curr++;
        }
        return result;
    }


    //
    // protected inner classes
    //

    protected abstract class FastIndexAddressOffset implements Address.Offset {

        protected int row;
        protected int col;

        protected FastIndexAddressOffset() {
            // only protected access allowed
        }

    }


}
