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

/*
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl
 Copyright (C) 2004 Ferdinando Ametrano

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

package org.jquantlib.math.matrixutilities;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Ops;
import org.jquantlib.math.Ops.BinaryDoubleOp;
import org.jquantlib.math.Ops.DoubleOp;
import org.jquantlib.math.functions.LessThanPredicate;
import org.jquantlib.math.matrixutilities.internal.Address;
import org.jquantlib.math.matrixutilities.internal.DirectArrayRowAddress;



/**
 * 1-D array used in linear algebra.
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q2_RESEMBLANCE, version = Version.V097, reviewers = { "Richard Gomes" })
public class Array extends Cells<Address.ArrayAddress> implements Cloneable, Iterable<Double>, Algebra<Array> {

    //
    // public constructors
    //

    /**
     * Default constructor
     * <p>
     * Builds an Array which contains only one element.
     */
    public Array() {
        this(0, EnumSet.noneOf(Address.Flags.class));
    }


    /**
     * Default constructor
     * <p>
     * Builds an Array which contains only one element.
     *
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     *
     * @see Address.Flags
     */
    public Array(final Set<Address.Flags> flags) {
        super(1, 1, null);
        this.addr = new DirectArrayRowAddress(this.$, 0, null, 0, 0, flags, true, 1, 1);
    }


    /**
     * Builds an Array of <code>size</code>
     *
     * @param size is the size of <code>this</code> Array
     * @throws IllegalArgumentException if size are less than zero
     */
    public Array(final int size) {
        this(size, EnumSet.noneOf(Address.Flags.class));
    }

    /**
     * Builds an Array of <code>size</code>
     *
     * @param size is the size of <code>this</code> Array
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     * @throws IllegalArgumentException if size are less than zero
     *
     * @see Address.Flags
     */
    public Array(final int size, final Set<Address.Flags> flags) {
        super(1, size, null);
        this.addr = new DirectArrayRowAddress(this.$, 0, null, 0, size-1, flags, true, 1, size);
    }

    /**
     * Creates an Array given a double[] array
     *
     * @param $ is a unidimensional array
     */
    public Array(final double[] array) {
        this(array, EnumSet.noneOf(Address.Flags.class));
    }

    /**
     * Creates an Array given a double[] array
     *
     * @param $ is a unidimensional array
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     *
     * @see Address.Flags
     */
    public Array(final double[] array, final Set<Address.Flags> flags) {
        super(1, array.length, null);
        this.addr = new DirectArrayRowAddress(this.$, 0, null, 0, array.length-1, flags, true, 1, array.length);
        System.arraycopy(array, 0, $, 0, this.size());
    }

    /**
     * Creates an Array given a double[] array and the desired number of elements
     *
     * @param $ is a unidimensional array
     * @param size is the desired number of elements to be taken, counted from the first position
     */
    public Array(final double[] array, final int size) {
        this(array, size, EnumSet.noneOf(Address.Flags.class));
    }

    /**
     * Creates an Array given a double[] array and the desired number of elements
     *
     * @param $ is a unidimensional array
     * @param size is the desired number of elements to be taken, counted from the first position
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     *
     * @see Address.Flags
     */
    public Array(final double[] array, final int size, final Set<Address.Flags> flags) {
        super(1, size, null);
        this.addr = new DirectArrayRowAddress(this.$, 0, null, 0, size-1, flags, true, 1, size);
        System.arraycopy(array, 0, $, 0, this.size());
    }

    /**
     * Creates a Matrix given a double[][] array
     *
     * @param $
     */
    public Array(final Array array) {
        this(array, EnumSet.noneOf(Address.Flags.class));
    }

    /**
     * Creates a Matrix given a double[][] array
     *
     * @param $
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     *
     * @see Address.Flags
     */
    public Array(final Array array, final Set<Address.Flags> flags) {
        super(1, array.size(), null);
        this.addr = new DirectArrayRowAddress(this.$, 0, null, 0, array.size(), array.flags(), true, 1, array.size());
        if (array.addr.isContiguous()) {
            final int begin = array.addr.col0()+(addr.isFortran() ? 1 : 0);
            System.arraycopy(array.$, begin, $, 0, this.size());
        } else {
            for (int i=0; i<array.size(); i++) {
                this.$[i] = array.get(i);
            }
        }
    }


    //
    // protected constructors
    //

    protected Array(
            final int rows,
            final int cols,
            final double[] data,
            final Address.ArrayAddress addr) {
        super(rows, cols, data, addr);
    }


    //
    // implements Cloneable
    //

    @Override
    public Array clone() {
        //XXX return new Array(this, this.flags());
        final Array clone = (Array) super.clone();
        clone.$ = new double[this.size()];
        clone.addr = new DirectArrayRowAddress(clone.$, 0, null, 0, this.size(), this.flags(), true, 1, this.size());
        if (this.addr.isContiguous()) {
            final int begin = this.addr.col0()+(addr.isFortran() ? 1 : 0);
            System.arraycopy(this.$, begin, clone.$, 0, this.size());
        } else {
            for (int i=0; i<this.size(); i++) {
                clone.$[i] = this.get(i);
            }
        }
        return clone;
    }


    //
    // public methods
    //

    /**
     * This is a convenience method intended to return the physical address of an element.
     * <p>
     * <b>The use of this method is highly discouraged</b>
     *
     * @param index is a logical address of an element
     *
     * @see Cells#$
     *
     * @return the physical address to an element
     */
    @Deprecated
    public int _(final int index) {
        return addr.op(index);
    }

    public int begin() {
        return addr.isFortran() ? 1 : 0;
    }

    public int end() {
        return size() + (addr.isFortran() ? 1 : 0);
    }

    public double first() {
        return $[_(addr.isFortran() ? 1 : 0)];
    }

    public double last() {
        return $[_(end() - 1)];
    }

    /**
     * Retrieves an element of <code>this</code> Matrix
     * <p>
     * This method is provided for performance reasons. See methods {@link #getAddress(int)} and {@link #getAddress(int, int)} for
     * more details
     *
     * @param dim coordinate
     * @param col coordinate
     * @return the contents of a given cell
     *
     * @see #getAddress(int)
     * @see #getAddress(int, int)
     */
    public double get(final int pos) {
        return $[addr.op(pos)];
    }

    /**
     * Stores a value into an element of <code>this</code> Matrix
     * <p>
     * This method is provided for performance reasons. See methods {@link #getAddress(int)} and {@link #getAddress(int, int)} for
     * more details
     *
     * @param dim coordinate
     * @param col coordinate
     *
     * @see #getAddress(int)
     * @see #getAddress(int, int)
     */
    public void set(final int pos, final double value) {
        $[addr.op(pos)] = value;
    }



    ////////////////////////////////////////////////////////////
    //
    // implements Algebra<Array>
    //
    ////////////////////////////////////////////////////////////


    //
    //    Assignment operations
    //
    //    opr   method     this    right    result
    //    ----- ---------- ------- -------- ------
    //    +=    addAssign  Array   scalar   this
    //    +=    addAssign  Array   Array    this
    //    -=    subAssign  Array   scalar   this
    //    -=    subAssign  Array   Array    this
    //    *=    mulAssign  Array   scalar   this
    //    *=    mulAssign  Array   Array    this
    //    /=    divAssign  Array   scalar   this
    //    /=    divAssign  Array   Array    this
    //

    @Override
    public Array addAssign(final double scalar) {
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<size(); i++) {
            $[src.op()] += scalar;
            src.nextIndex();
        }
        return this;
    }

    @Override
    public Array subAssign(final double scalar) {
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<size(); i++) {
            $[src.op()] -= scalar;
            src.nextIndex();
        }
        return this;
    }

    /**
     * Returns the result of a subtraction of <code>this</code> Array and <code>another</code> Array
     *
     * @param another
     * @return this
     */
    @Override
    public Array subAssign(final Array another) {
        QL.require(this.size() == another.size(), ARRAY_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int i=0; i<size(); i++) {
            $[toff.op()] -= another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return this;
    }

    @Override
    public Array mulAssign(final double scalar) {
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<size(); i++) {
            $[src.op()] *= scalar;
            src.nextIndex();
        }
        return this;
    }

    @Override
    public Array divAssign(final double scalar) {
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<size(); i++) {
            $[src.op()] /= scalar;
            src.nextIndex();
        }
        return this;
    }

    @Override
    public Array addAssign(final Array another) {
        QL.require(this.size() == another.size(), ARRAY_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int i=0; i<size(); i++) {
            $[toff.op()] += another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return this;
    }

    @Override
    public Array mulAssign(final Array another) {
        QL.require(this.size() == another.size(), ARRAY_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int i=0; i<size(); i++) {
            $[toff.op()] *= another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return this;
    }

    @Override
    public Array divAssign(final Array another) {
        QL.require(this.size() == another.size(), ARRAY_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int i=0; i<size(); i++) {
            this.$[toff.op()] /= another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return this;
    }


    //
    //    Algebraic products
    //
    //    opr   method     this    right    result
    //    ----- ---------- ------- -------- ------
    //    +     positive   Array             Array  (2)
    //    +     add        Array   scalar    Array
    //    +     add        Array   Array     Array
    //    -     negative   Array             Array  (3)
    //    -     sub        Array   scalar    Array
    //    -     sub        Array   Array     Array
    //    *     mul        Array   scalar    Array
    //    *     mul        Array   Array     Array
    //    *     mul        Array   Matrix    Array
    //    /     div        Array   scalar    Array
    //    /     div        Array   Array     Array
    //

    @Override
    public Array add(final double scalar) {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[src.op()] + scalar;
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array sub(final double scalar) {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[src.op()] - scalar;
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array mul(final double scalar) {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[src.op()] * scalar;
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array negative() {
        return mul(-1);
    }

    @Override
    public Array div(final double scalar) {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[src.op()] / scalar;
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array add(final Array another) {
        QL.require(this.size() == another.size(), MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[toff.op()] + another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return result;
    }

    @Override
    public Array sub(final Array another) {
        QL.require(this.size() == another.size(), MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[toff.op()] - another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return result;
    }

    @Override
    public Array mul(final Array another) {
        QL.require(this.size() == another.size(), MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[toff.op()] * another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return result;
    }

    @Override
    public Array div(final Array another) {
        QL.require(this.size() == another.size(), MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset();
        for (int col=0; col<size(); col++) {
            result.$[col] = $[toff.op()] / another.$[aoff.op()];
            toff.nextIndex();
            aoff.nextIndex();
        }
        return result;
    }

    @Override
    public Array mul(final Matrix matrix) {
        QL.require(this.size() == matrix.rows(), MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Array result = new Array(matrix.cols());
        final Address.ArrayAddress.ArrayOffset  toff = this.addr.offset();
        final Address.MatrixAddress.MatrixOffset moff = matrix.addr.offset();
        final int offsetA = this.addr.isFortran() ? 1 : 0;
        final int offsetM = matrix.addr.isFortran() ? 1 : 0;
        for (int col=0; col<matrix.cols(); col++) {
            toff.setIndex(offsetA);
            moff.setRow(offsetM); moff.setCol(col+offsetM);
            double sum = 0.0;
            for (int row=0; row<matrix.rows(); row++) {
                final double telem = this.$[toff.op()];
                final double aelem = matrix.$[moff.op()];
                sum += telem * aelem;
                toff.nextIndex();
                moff.nextRow();
            }
            result.$[col] = sum;
        }
        return result;
    }




    //
    //    Math functions
    //
    //    opr   method     this    right    result
    //    ----- ---------- ------- -------- ------
    //    min   min        Array            scalar
    //    max   max        Array            scalar
    //    abs   abs        Array            Array
    //    sqrt  sqrt       Array            Array
    //    log   log        Array            Array
    //    exp   exp        Array            Array

    @Override
    public double min() {
        return min(0, this.size());
    }

    @Override
    public double min(final int from, final int to) {
        QL.require(from >= 0 && to > from && to <= size(),  INVALID_ARGUMENTS); // QA:[RG]::verified
        final int offset = addr.isFortran() ? 1 : 0;
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset(from+offset);
        double result = $[src.op()];
        for (int i=0; i<(to-from); i++) {
            final double tmp = $[src.op()];
            src.nextIndex();
            if (tmp < result) {
                result = tmp;
            }
        }
        return result;
    }

    @Override
    public double max() {
        return max(0, this.size());
    }

    @Override
    public double max(final int from, final int to) {
        QL.require(from >= 0 && to > from && to <= size(),  INVALID_ARGUMENTS); // QA:[RG]::verified
        final int offset = addr.isFortran() ? 1 : 0;
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset(from+offset);
        double result = $[src.op()];
        for (int i=0; i<(to-from); i++) {
            final double tmp = $[src.op()];
            src.nextIndex();
            if (tmp > result) {
                result = tmp;
            }
        }
        return result;
    }

    @Override
    public Array abs() {
        final Array result = new Array(this.size(), this.addr.flags());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        final int offset = addr.isFortran() ? 1 : 0;
        for (int i=offset; i<this.size()+offset; i++) {
            result.$[result._(i)] = Math.abs($[src.op()]);
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array sqr() {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<this.size(); i++) {
            final double a = $[src.op()];
            result.$[i] = a*a;
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array sqrt() {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<this.size(); i++) {
            result.$[i] = Math.sqrt($[src.op()]);
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array log() {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<this.size(); i++) {
            result.$[i] = Math.log($[src.op()]);
            src.nextIndex();
        }
        return result;
    }

    @Override
    public Array exp() {
        final Array result = new Array(this.size());
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset();
        for (int i=0; i<this.size(); i++) {
            result.$[i] = Math.exp($[src.op()]);
            src.nextIndex();
        }
        return result;
    }



    //
    //    Miscellaneous
    //
    //    method       this    right    result
    //    ------------ ------- -------- ------
    //    outerProduct Array   Array    Matrix
    //    dotProduct   Array   Array    double
    //

    @Override
    public double dotProduct(final Array another) {
        final int offset = another.addr.isFortran() ? 1 : 0;
        return dotProduct(another, offset, another.size()+offset);
    }

    @Override
    public double dotProduct(final Array another, final int from, final int to) {
        final int offset = another.addr.isFortran() ? 1 : 0;
        QL.require(from >= offset && to >= from && to <= another.size()+offset, INVALID_ARGUMENTS); // QA:[RG]::verified
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset(from);
        double sum = 0.0;
        for (int i=0; i<to-from; i++) {
            final double telem = this.$[toff.op()];
            final double aelem = another.$[aoff.op()];
            sum += telem * aelem;
            toff.nextIndex();
            aoff.nextIndex();
        }
        return sum;
    }

    @Override
    public double innerProduct(final Array another) {
        // when working with real numbers, both dotProduct and innerProduct give the same results
        return dotProduct(another);
    }

    @Override
    public double innerProduct(final Array another, final int from, final int to) {
        // when working with real numbers, both dotProduct and innerProduct give the same results
        return dotProduct(another, from, to);
    }

    @Override
    public Matrix outerProduct(final Array another) {
        final int offset = another.addr.isFortran() ? 1 : 0;
        return outerProduct(another, offset, another.size()+offset);
    }

    @Override
    public Matrix outerProduct(final Array another, final int from, final int to) {
        final int offset = another.addr.isFortran() ? 1 : 0;
        QL.require(from >= offset && to >= from && to <= another.size()+offset, INVALID_ARGUMENTS); // QA:[RG]::verified
        final Matrix result = new Matrix(this.size(), to-from);
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset();
        int addr = 0;
        for (int i=0; i<this.size(); i++) {
            final Address.ArrayAddress.ArrayOffset aoff = another.addr.offset(from);
            for (int j=from; j < to; j++) {
                result.$[addr] = this.$[toff.op()] * another.$[aoff.op()];
                addr++;
                aoff.nextIndex();
            }
            toff.nextIndex();
        }
        return result;
    }


    //
    // Routines ported from stdlibc++
    //


    @Override
    public double accumulate() {
        return accumulate(0, this.size(), 0.0);
    }

    @Override
    public double accumulate(final double init) {
        return accumulate(0, this.size(), init);
    }

    @Override
    public double accumulate(final int first, final int last, final double init) {
        QL.require(first>=0 && last>first && last<=size(),  INVALID_ARGUMENTS); // QA:[RG]::verified
        double sum = init;
        final Address.ArrayAddress.ArrayOffset src = this.addr.offset(first);
        for (int i=0; i<last-first; i++) {
            final double elem = this.$[src.op()];
            sum += elem;
            src.nextIndex();
        }
        return sum;
    }

    @Override
    public final Array adjacentDifference() {
        final int offset = addr.isFortran() ? 1 : 0;
        return adjacentDifference(offset, size()+offset);
    }

    @Override
    public final Array adjacentDifference(final int from, final int to) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(from >= offset && to >= from && to <= this.size()+offset, INVALID_ARGUMENTS); // QA:[RG]::verified
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset(from);
        final Array diff = new Array(to-from, this.flags());
        // obtain first element and advance pointer
        double prev = this.$[toff.op()]; toff.nextIndex();
        // fill in first difference
        diff.$[diff._(offset)] = prev;
        // fill in remaining differences
        for (int i=1+offset; i<to-from+offset; i++) {
            final double curr = this.$[toff.op()]; toff.nextIndex();
            diff.$[diff._(i)] = curr - prev;
            prev = curr;
        }
        return diff;
    }

    @Override
    public Array adjacentDifference(final BinaryDoubleOp f) {
        final int offset = addr.isFortran() ? 1 : 0;
        return adjacentDifference(offset, size()+offset, f);
    }

    @Override
    public Array adjacentDifference(final int from, final int to, final BinaryDoubleOp f) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(from >= offset && to >= from && to <= this.size()+offset, INVALID_ARGUMENTS); // QA:[RG]::verified
        final Address.ArrayAddress.ArrayOffset toff = this.addr.offset(from);
        final Array diff = new Array(to-from, this.flags());
        // obtain first element and advance pointer
        double prev = this.$[toff.op()]; toff.nextIndex();
        // fill in first difference
        diff.$[diff._(offset)] = prev;
        // fill in remaining differences
        for (int i=1+offset; i<to-from+offset; i++) {
            final double curr = this.$[toff.op()]; toff.nextIndex();
            diff.$[diff._(i)] = f.op(curr, prev);
            prev = curr;
        }
        return diff;
    }

    @Override
    public Array transform(final DoubleOp f) {
        final int offset = addr.isFortran() ? 1 : 0;
        return transform(offset, this.size()+offset, f);
    }

    @Override
    public Array transform(final int from, final int to, final Ops.DoubleOp f) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(from >= offset && to >= from && to <= this.size()+offset && f!=null, INVALID_ARGUMENTS); // QA:[RG]::verified
        for (int i=from; i<to; i++) {
            final int idx = this._(i);
            this.$[idx] = f.op(this.$[idx]);
        }
        return this;
    }


    @Override
    public int lowerBound(final double val) {
        final int offset = addr.isFortran() ? 1 : 0;
        return lowerBound(offset, size()+offset, val, new LessThanPredicate());
    }

    @Override
    public int lowerBound(final int from, final int to, final double val) {
        return lowerBound(from, to, val, new LessThanPredicate());
    }

    @Override
    public int lowerBound(final double val, final Ops.BinaryDoublePredicate f) {
        final int offset = addr.isFortran() ? 1 : 0;
        return lowerBound(offset, size()+offset, val, f);
    }

    @Override
    public int lowerBound(int from, final int to, final double val, final Ops.BinaryDoublePredicate f) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(from>=offset && from<=to && to<=size()+offset, INVALID_ARGUMENTS); // QA:[RG]::verified
        int len = to - from;
        while (len > 0) {
            final int half = len >> 1;
            final int middle = from-offset + half;
            if (f.op($[addr.op(middle+offset)], val)) {
                from = middle+offset + 1;
                len -= half + 1;
            } else {
                len = half;
            }
        }
        return from;
    }

    @Override
    public int upperBound(final double val) {
        final int offset = addr.isFortran() ? 1 : 0;
        return upperBound(offset, size()+offset, val);
    }

    @Override
    public int upperBound(final int from, final int to, final double val) {
        return upperBound(from, to, val, new LessThanPredicate());
    }

    @Override
    public int upperBound(final double val, final Ops.BinaryDoublePredicate f) {
        final int offset = addr.isFortran() ? 1 : 0;
        return upperBound(offset, size()+offset, val, f);
    }

    @Override
    public int upperBound(int from, final int to, final double val, final Ops.BinaryDoublePredicate f) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(from>=offset && from<=to && to<=size()+offset, INVALID_ARGUMENTS); // QA:[RG]::verified
        int len = to - from;
        while (len > 0) {
            final int half = len >> 1;
            final int middle = from-offset + half;
            if (f.op(val, $[addr.op(middle+offset)])) {
                len = half;
            } else {
                from = middle+offset + 1;
                len -= half + 1;
            }
        }
        return from;
    }



//    @Override
//    public int upperBound(int from, final int to, final double val) {
//
//        QL.require(first>=0 && first<=last && last<=size(), INVALID_ARGUMENTS); // QA:[RG]::verified
//        int len = last - first;
//        while (len > 0) {
//            final int half = len >> 1;
//            final int middle = first + half;
//            if (val < $[addr.op(middle)]) {
//                len = half;
//            } else {
//                first = middle + 1;
//                len -= half + 1;
//            }
//        }
//        return first;
//    }
//
//    @Override
//    public int upperBound(final double val, final Ops.BinaryDoublePredicate f) {
//        return upperBound(0, size(), val, f);
//    }
//
//    @Override
//    public int upperBound(int first, final int last, final double val, final Ops.BinaryDoublePredicate f) {
//        QL.require(first>=0 && first<=last && last<=size(), INVALID_ARGUMENTS); // QA:[RG]::verified
//        int len = last - first;
//        while (len > 0) {
//            final int half = len >> 1;
//            final int middle = first + half;
//            if (f.op(val, $[addr.op(middle)])) {
//                len = half;
//            } else {
//                first = middle + 1;
//                len -= half + 1;
//            }
//        }
//        return first;
//    }


    //
    //  Range
    //
    //  method       this    right    result
    //  ------------ ------- -------- ------
    //  range        Array            Array
    //


    public Array range(final int col0) {
        return range(col0, cols());
    }

    public Array range(final int col0, final int col1) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(col0 >= offset && col0 < cols()+offset && col1 >= offset && col1 <= cols()+offset, Address.INVALID_COLUMN_INDEX);
        return new Range(offset, this.addr, $, col0, col1, rows(), cols());
    }

    public Array toFortran() {
        return this.addr.isFortran()
            ?  this
            : new Array(this.rows, this.cols, this.$, this.addr.toFortran());
    }

    public Array toJava() {
        return this.addr.isFortran()
            ?  new Array(this.rows, this.cols, this.$, this.addr.toJava())
            : this;
    }






    //TODO: better comments
    //
    // methods moved from Cells
    //

    public Array fill(final double scalar) {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        final int offset = addr.isFortran() ? 1 : 0;
        Arrays.fill($, begin()-offset, end()-offset, scalar);
        return this;
    }

    public Array fill(final Array another) {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(another.addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(this.rows()==another.rows() && this.cols()==another.cols() && this.size()==another.size(), WRONG_BUFFER_LENGTH);
        // copies data
        final int offsetT = this.addr.isFortran() ? 1 : 0;
        final int offsetA = another.addr.isFortran() ? 1 : 0;
        System.arraycopy(another.$, another.begin()-offsetA, this.$, this.begin()-offsetT, another.size());
        return this;
    }

    public Array swap(final Array another) {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(another.addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(this.rows()==another.rows() && this.cols()==another.cols() && this.size()==another.size(), WRONG_BUFFER_LENGTH);
        // swaps data
        final double [] tdata;
        final Address.ArrayAddress taddr;
        tdata = this.$;  this.$ = another.$;  another.$ = tdata;
        taddr = this.addr;  this.addr = another.addr;  another.addr = taddr;
        return this;
    }

    public Array sort() {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        final int offset = addr.isFortran() ? 1 : 0;
        Arrays.sort($, begin()-offset, end()-offset);
        return this;
    }


    //
    // Overrides Object
    //


    @Override
    public String toString() {
        final int offset = addr.isFortran() ? 1 : 0;
        final StringBuffer sb = new StringBuffer();
        sb.append("[rows=").append(rows()).append(" cols=").append(cols()).append(" addr=").append(addr).append('\n');
        sb.append("  [ ");
        sb.append(this.$[this.addr.op(offset)]);
        for (int pos = 1+offset; pos < size()+offset; pos++) {
            sb.append(", ");
            sb.append($[addr.op(pos)]);
        }
        sb.append("  ]\n");
        sb.append("]\n");
        return sb.toString();
    }


    //
    // implements Iterable<Double>
    //

    @Override
    public Iterator<Double> iterator() {
        return this.addr.offset();
    }


    //
    // private inner classes
    //

    private class Range extends Array {

        public Range(
            final int row0,
            final Address.ArrayAddress chain,
            final double[] data,
            final int col0,
            final int col1,
            final int rows, final int cols) {
            super(1,
                  col1-col0,
                  data,
                  new DirectArrayRowAddress(data, row0, chain, col0, col1, null, true, rows, cols));
        }
    }

}
