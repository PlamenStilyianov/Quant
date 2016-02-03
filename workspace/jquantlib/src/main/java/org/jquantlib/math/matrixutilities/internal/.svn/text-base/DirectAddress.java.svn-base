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
import java.util.Set;

import org.jquantlib.lang.exceptions.LibraryException;


/**
 * This is the base class for all accessors on top of contiguous intervals
 *
 * @author Richard Gomes
 */
public abstract class DirectAddress implements Address, Cloneable {

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

    private final int base;
    private final int last;


    //
    // public methods
    //

    public DirectAddress(
                final double[] data,
                final int row0, final int row1,
                final Address chain,
                final int col0, final int col1,
                final Set<Address.Flags> flags,
                final boolean contiguous,
                final int rows, final int cols) {
        this.data = data; // DO NOT use clone: direct reference on purpose!
        this.chain  = chain;
        this.contiguous = contiguous;
        this.flags  = (flags != null) ? flags : (chain != null) ? chain.flags() : EnumSet.noneOf(Address.Flags.class);

        this.offset = isFortran() ? 1 : 0;
        this.row0 = row0 - offset + ( chain==null ? 0 : chain.row0() );
        this.col0 = col0 - offset + ( chain==null ? 0 : chain.col0() );
        this.row1 = this.row0 + (row1-row0);
        this.col1 = this.col0 + (col1-col0);
        this.rows = (chain==null) ? rows : chain.rows();
        this.cols = (chain==null) ? cols : chain.cols();
        this.base = (row0-offset)*cols + (col0-offset);
        this.last = (row1-offset-1)*cols + (col1-offset-1);
    }


    @Override
    public boolean isContiguous() {
        return contiguous;
    }

    @Override
    public boolean isFortran() {
        return flags.contains(Address.Flags.FORTRAN);
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
    // Overrides Object
    //

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[row0=").append(row0).append(" row1=").append(row1);
        sb.append(" col0=").append(col0).append(" col1=").append(col1);
        sb.append(" flags=").append(flags).append("]");
        return sb.toString();
    }


    //
    // implements Cloneable
    //

    @Override
    public DirectAddress clone() {
        try {
            return (DirectAddress) super.clone();
        } catch (final Exception e) {
            throw new LibraryException(e);
        }
    }


    //
    // protected inner classes
    //

    protected abstract class DirectAddressOffset implements Address.Offset {

        protected int row;
        protected int col;

        protected DirectAddressOffset() {
            // only protected access allowed
        }

    }

}
