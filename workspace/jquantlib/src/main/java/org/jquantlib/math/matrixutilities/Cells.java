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

package org.jquantlib.math.matrixutilities;

import java.util.Set;

import org.jquantlib.QL;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.matrixutilities.internal.Address;

/**
 * This class provides efficient basement for matrix operations by mapping matrices onto
 * a linear array, which performs better than bidimensional arrays.
 * <p>
 * In addition, access to elements is calculated by a certain policy which is intended to
 *  Matrix elements need to be translated to the underlying linear
 * storage when they are read or written.
 *
 * @author Richard Gomes
 */
public abstract class Cells<T extends Address> implements Cloneable {

    //
    // private final static fields :: error messages
    //

    private final static String FORTRAN_ADDRESSING_EXPECTED = "variable \"%s\" should be FORTRAN-style addressing";

    //
    // protected final static fields :: error messages
    //

    protected final static String INVALID_ARGUMENTS = "invalid arguments";
    protected final static String WRONG_BUFFER_LENGTH = "wrong buffer length";
    protected final static String MATRIX_IS_INCOMPATIBLE = "matrix is incompatible";
    protected final static String ARRAY_IS_INCOMPATIBLE = "array is incompatible";
    protected final static String ITERATOR_IS_INCOMPATIBLE = "iterator is incompatible";
    protected final static String NOT_ENOUGH_STORAGE = "not enough storage area for operation";
    protected final static String MATRIX_MUST_BE_SQUARE = "matrix must be square";
    protected final static String MATRIX_MUST_BE_SYMMETRIC = "matrix must be symmetric";
    protected final static String MATRIX_IS_SINGULAR = "matrix is singular";
    protected final static String NON_CONTIGUOUS_DATA = "Operation not supported on non-contiguous data";


    //
    // protected fields
    //

    protected final int rows;
    protected final int cols;
    protected final int size;


    //
    // protected fields
    //

    protected T addr;


    //
    // public fields
    //

    /**
     * This is the internal data storage where data is kept in an uni-dimensional <code>double[]</code>
     * <p>
     * <b>The use of this field is highly discouraged by end-user applications.</b>
     * <p>
     * Note: this field is deprecated in order to remind application developers avoid its use.
     * <p>
     * If you cannot avoid access to the underlying data, please remember that it can be non-contiguous, chained or mapped, which
     * means that:
     * <li>you'd better obtain a reference to this internal data structure via method {@link Cells#data()} instead;</li>
     * <li>you must be sure to use methods {@link Array#_(int)} and {@link Matrix#_(int, int)} in order to guarantee you calculate
     * properly addresses of elements in the underlying data structure.</li>
     * <p>
     * This internal data structure is exposed for <b>convenience purposes only</b>, given the fact that algorithms in general
     * handle arrays or matrices directly. It's recommended that application code <b>never calculate indexes directly</b>, but
     * employ Address mappings in order to perform this task.
     * <p>
     * In particular, when employing <a href="http://icl.cs.utk.edu/f2j/">f2j</a> in order to translate algorithms from FORTRAN
     * into Java, the obtained code is 1-based indexed, like it is in FORTRAN, like this:
     * <pre>
     *    for (i = 1; i <= n; i++)
     * </pre>
     * rather than 0-based, like it is in Java, like this:
     * <pre>
     *    for (i = 0; i < n; i++)
     * </pre>
     * For this reason, you'd better let JQuantLib manage indexing. The only thing you need to adjust is direct access to
     * elements of arrays and matrices, converting things like <code>a[i]</code> to <code>a.$[a._(i)]</code> in case of arrays
     * and things like <code>m[i][j]</code> to <code>m.$[m._(i,j)]</code> in case of matrices.
     *
     * @see Array#_(int)
     * @see Matrix#_(int, int)
     */
    @Deprecated
    public double[] $;



    /**
     * Builds a Storage of <code>rows</code> by <code>cols</code>
     *
     * @param rows is the number of rows
     * @param cols is the number of columns
     * @throws IllegalArgumentException if parameters are less than zero
     */
    protected Cells(
            final int rows,
            final int cols,
            final T addr) {
        this.rows = rows;
        this.cols = cols;
        this.addr = addr;
        this.size = rows*cols;
        this.$ = new double[size];
    }


    /**
     * Performs a shallow copy of another <code>Cells</code> instance
     * <p>
     * This constructor accepts a custom <code>Offset</code> implementation, which is responsible for
     * mapping a virtual address map onto its physical representation on an already existing <code>Cells</code>.
     *
     * @param rows is the number of rows
     * @param cols is the number of columns
     * @param data is an existing data buffer
     * @param addr is an Address calculation policy responsible for mapping virtual addresses to physical addresses
     */
    protected Cells(
            final int rows,
            final int cols,
            final double data[],
            final T addr) {
        this.rows = rows;
        this.cols = cols;
        this.$ = data;
        this.addr = addr;
        this.size = rows*cols;
        if (data.length != addr.rows()*addr.cols())
            throw new IllegalArgumentException("declared dimension do not match underlying storage size");
    }


    //
    // public methods
    //

    public final int rows()       { return rows; }
    public final int columns()    { return cols; }
    public final int cols()       { return cols; } // FIXME: remove this
    public final int size()       { return size; }
    public final boolean empty() { return size <= 0; }

    public final Set<Address.Flags> flags() {
        return addr.flags();
    }

    public void requireFlags(final Set<Address.Flags> required, final String variable) {
        if (required.contains(Address.Flags.FORTRAN) != addr.isFortran()) {
            final String name = (variable==null) ? "variable" : (this.getClass().getSimpleName() + " " + variable);
            final String message = String.format(FORTRAN_ADDRESSING_EXPECTED, name);
            QL.error(String.format(FORTRAN_ADDRESSING_EXPECTED, name));
        }
    }



    //
    // Overrides Object
    //

    @Override
    public Cells clone() {
        try {
            return (Cells) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new LibraryException(e);
        }
    }


//TODO: implement hashCode and equals
//
//        @Override
//        public int hashCode() {
//            final int prime = 31;
//            int result = 1;
//            result = prime * result + cols;
//            result = prime * result + rows;
//            return result;
//        }

//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj)
//                return true;
//            if (obj == null)
//                return false;
//            if (getClass() != obj.getClass())
//                return false;
//            Cells other = (Cells) obj;
//            if (cols != other.cols)
//                return false;
//            if (rows != other.rows)
//                return false;
//            return true;
//        }


}
