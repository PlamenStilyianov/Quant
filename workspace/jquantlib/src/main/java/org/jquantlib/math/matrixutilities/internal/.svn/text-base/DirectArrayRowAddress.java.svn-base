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

import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.jquantlib.lang.exceptions.LibraryException;

/**
 * This accessor provides contiguous addressing on row-arrays
 *
 * @see Array
 *
 * @author Richard Gomes
 */
public class DirectArrayRowAddress extends DirectAddress implements Address.ArrayAddress {

    public DirectArrayRowAddress(
            final double[] data,
            final int row,
            final Address chain,
            final int col0, final int col1,
            final Set<Address.Flags> flags,
            final boolean contiguous,
            final int rows, final int cols) {
        super(data, row, row+1, chain, col0, col1, flags, contiguous, rows, cols);
    }


    //
    // implements ArrayAddress
    //

    @Override
    public ArrayAddress toFortran() {
        return isFortran() ? this :
            new DirectArrayRowAddress(data, row0, this.chain, col0, col1, EnumSet.of(Address.Flags.FORTRAN), contiguous, rows, cols);
    }

    @Override
    public ArrayAddress toJava() {
        return isFortran() ?
            new DirectArrayRowAddress(data, row0+1, this.chain, col0+1, col1+1, EnumSet.noneOf(Address.Flags.class), contiguous, rows, cols)
            : this;
    }

    @Override
    public ArrayOffset offset() {
        return new DirectArrayRowAddressOffset(offset, offset);
    }

    @Override
    public ArrayOffset offset(final int index) {
        return new DirectArrayRowAddressOffset(offset, index);
    }

    @Override
    public int op(final int index) {
        return (row0+offset)*cols + (col0+index);
    }


    //
    // implements Cloneable
    //

    @Override
    public DirectArrayRowAddress clone() {
        try {
            return (DirectArrayRowAddress) super.clone();
        } catch (final Exception e) {
            throw new LibraryException(e);
        }
    }


    //
    // private inner classes
    //

    private class DirectArrayRowAddressOffset extends DirectAddressOffset implements Address.ArrayAddress.ArrayOffset {

        public DirectArrayRowAddressOffset(final int row, final int col) {
            super.row = row0+row;
            super.col = col0+col;
        }


        //
        // implements Offset
        //

        @Override
        public int op() {
            return row*cols + col;
        }


        //
        // implements ArrayOffset
        //

        @Override
        public void setIndex(final int index) {
            super.col = col0+index;
        }


        //
        // implements ListIterator
        //

        @Override
        public void add(final Double e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            return super.col == cols ? cols : super.col++;
        }

        @Override
        public int previousIndex() {
            return super.col == -1 ? -1 : super.col--;
        }

        @Override
        public boolean hasNext() {
            return super.col < col1;
        }

        @Override
        public boolean hasPrevious() {
            return super.col > -1;
        }

        @Override
        public Double next() {
            final int idx = op();
            nextIndex();
            if (idx==col1) throw new NoSuchElementException();
            return data[idx];
        }

        @Override
        public Double previous() {
            final int idx = previousIndex();
            if (idx==-1) throw new NoSuchElementException();
            return data[op()];
        }

        @Override
        public void set(final Double e) {
            final int idx = op();
            if ((idx==-1)||(idx==cols)) throw new IllegalStateException();
            data[idx] = e;
        }




    }

}
