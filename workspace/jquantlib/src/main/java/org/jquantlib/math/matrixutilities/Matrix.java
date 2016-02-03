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

/*
Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl
Copyright (C) 2003, 2004 Ferdinando Ametrano

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
import java.util.Set;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.matrixutilities.internal.Address;
import org.jquantlib.math.matrixutilities.internal.DirectArrayColAddress;
import org.jquantlib.math.matrixutilities.internal.DirectArrayRowAddress;
import org.jquantlib.math.matrixutilities.internal.DirectMatrixAddress;
import org.jquantlib.math.matrixutilities.internal.MappedMatrixAddress;
import org.jquantlib.math.matrixutilities.internal.Address.MatrixAddress.MatrixOffset;

/**
 * Bidimensional matrix operations
 * <p>
 * Performance of multidimensional arrays is a big concern in Java. This is because multidimensional arrays are stored as arrays of
 * arrays, spanning this concept to as many depths as necessary. In C++, a multidimensional array is stored internally as a
 * unidimensional array where depths are stacked together one after another. A very simple calculation is needed in order to map
 * multiple dimensional indexes to an unidimensional index.
 * <p>
 * This class provides a C/C++ like approach of internal unidimensional array backing a conceptual bidimensional matrix. This
 * mapping is provided by method <code>op(int row, int col)</code> which is responsible for returning the physical address of
 * the desired tuple <row,col>, given a certain access method.
 * <p>
 * The mentioned access method is provided by a concrete implementation of {@link Address} which is passed to constructors.
 * Doing so, it is possible to access the same underlying unidimensional data storage in various different ways, which allows us
 * obtain another Matrix (see method {@link Matrix#range(int, int, int, int) and alike}) from an existing Matrix without any
 * need of copying the underlying data. Certain operations benefit a lot from such approach, like {@link Matrix#transpose()} which
 * presents constant execution time.
 * <p>
 * The price we have to pay for such flexibility and benefits is that an access method is necessary, which means that a the bytecode
 * may potentially need a dereference to a class which contain the concrete implementation of method <code>op(int row, int col)</code>.
 * In order to keep this cost at minimum, implementation of access method keep indexes at hand whenever possible, in order to
 * being able to calculate the actual unidimensional index as fast as possible. This additional dereference impacts performance
 * more or less in the same ways dereference impacts performance when a bidimensional array (<code>double[][]</code>) is employed.
 * Contrary to the bidimensional implementation, our implementation can potentially benefit from bytecode inlining, which means
 * that calculation of the unidimensional index can be performed potentially faster than calculation of address location when a
 * bidimensional array (<code>double[][]</code>) is used as underlying storage.
 * <p>
 * <p>
 * <b>Assignment operations</b>
 * <pre>
 *   opr method     this    right    result
 *   --- ---------- ------- -------- ------
 *   =   assign     Matrix           Matrix (1)
 *   +=  addAssign  Matrix  Matrix   this
 *   -=  subAssign  Matrix  Matrix   this
 *   *=  mulAssign  Matrix  scalar   this
 *   /=  divAssign  Matrix  scalar   this
 * </pre>
 * <p>
 * <p>
 * <b>Algebraic products</b>
 * <pre>
 *   opr method     this    right    result
 *   --- ---------- ------- -------- ------
 *   +   add        Matrix  Matrix   Matrix
 *   -   sub        Matrix  Matrix   Matrix
 *   -   negative   Matrix           this
 *   *   mul        Matrix  scalar   Matrix
 *   /   div        Matrix  scalar   Matrix
 * </pre>
 * <p>
 * <p>
 * <b>Vectorial products</b>
 * <pre>
 *   method         this    right    result
 *   -------------- ------- -------- ------
 *   mul            Matrix  Array    Array
 *   mul            Matrix  Matrix   Matrix
 * </pre>
 * <p>
 * <p>
 * <b>Decompositions</b>
 * <pre>
 *   method         this    right    result
 *   -------------- ------- -------- ------
 *   lu             Matrix           LUDecomposition
 *   qr             Matrix           QRDecomposition
 *   cholensky      Matrix           CholeskyDecomposition
 *   svd            Matrix           SingularValueDecomposition
 *   eigenvalue     Matrix           EigenvalueDecomposition
 * </pre>
 * <p>
 * <p>
 * <b>Element iterators</b>
 * <pre>
 *   method         this    right    result
 *   ------------   ------- -------- ------
 *   rowIterator    Matrix           RowIterator
 *   columnIterator Matrix           ColumnIterator
 * </pre>
 * <p>
 * <p>
 * <b>Miscellaneous</b>
 * <pre>
 *   method         this    right    result
 *   -------------- ------- -------- ------
 *   transpose      Matrix           Matrix
 *   diagonal       Matrix           Array
 *   determinant    Matrix           double
 *   inverse        Matrix           Matrix
 *   solve          Matrix           Matrix
 *   swap           Matrix  Matrix   this
 *   range          Matrix  Matrix   Matrix
 * </pre>
 * <p>
 * <p>
 * (1): clone()<br/>
 * (2): Unary + is equivalent to: array.clone()<br/>
 * (3): Unary ? is equivalent to: array.clone().mulAssign(-1)
 * <p>
 * @note This class is not thread-safe
 *
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q1_TRANSLATION, version = Version.V097, reviewers = { "Richard Gomes" })
// TODO: better documentation
public class Matrix extends Cells<Address.MatrixAddress> implements Cloneable {

    //
    // public constructors
    //

    /**
     * Default constructor
     * <p>
     * Builds a Matrix with dimensions 1x1
     */
    public Matrix() {
        this(EnumSet.noneOf(Address.Flags.class));
    }

    /**
     * Default constructor
     * <p>
     * Builds a Matrix with dimensions 1x1
     *
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     *
     * @see Address.Flags
     */
    public Matrix(final Set<Address.Flags> flags) {
        super(1, 1, null);
        super.addr = new DirectMatrixAddress(this.$, 0, 1, null, 0, 1, flags, true, 1, 1);
    }


    /**
     * Builds a Matrix of <code>rows</code> by <code>cols</code>
     *
     * @param rows is the number of rows
     * @param cols is the number of columns
     * @throws IllegalArgumentException if parameters are less than zero
     */
    public Matrix(final int rows, final int cols) {
        this(rows, cols, EnumSet.noneOf(Address.Flags.class));
    }

    /**
     * Builds a Matrix of <code>rows</code> by <code>cols</code>
     *
     * @param rows is the number of rows
     * @param cols is the number of columns
     * @param flags is a <code>Set&lt;Address.Flags&gt;</code>
     * @throws IllegalArgumentException if parameters are less than zero
     *
     * @see Address.Flags
     */
    public Matrix(final int rows, final int cols, final Set<Address.Flags> flags) {
        super(rows, cols, null);
        this.addr = new DirectMatrixAddress(this.$, 0, rows, null, 0, cols, flags, true, rows, cols);
    }


    /**
     * Creates a Matrix given a double[][] array
     *
     * @param data
     */
    public Matrix(final double[][] data) {
        this(data, EnumSet.noneOf(Address.Flags.class));
    }

    /**
     * Creates a Matrix given a double[][] array
     *
     * @param data
     */
    public Matrix(final double[][] data, final Set<Address.Flags> flags) {
        super(data.length, data[0].length, null);
        this.addr = new DirectMatrixAddress(this.$, 0, data.length, null, 0, data[0].length, flags, true, data.length, data[0].length);
        for (int row=0; row<data.length; row++) {
            System.arraycopy(data[row], 0, this.$, row*this.cols, this.cols);
        }
    }


    /**
     * copy constructor
     *
     * @param $
     */
    public Matrix(final Matrix m) {
        super(m.rows(), m.cols(), copyData(m), m.addr.clone());
    }


    private static final double[] copyData(final Matrix m) {
        final int size = m.rows()*m.cols();
        final double[] data = new double[size];
        if (m.addr.isContiguous()) {
            System.arraycopy(m.$, 0, data, 0, size);
        } else {
            //FIXME: this code is probably wrong
            final MatrixOffset offset = m.addr.offset();
            final int cols = m.cols();
            for (int row=0; row<m.rows(); row++) {
                System.arraycopy(m.$, offset.op(), data, row*cols, cols);
                offset.nextRow();
            }
        }
        return data;
    }



    //
    // protected constructors
    //

    //FIXME:: protected
    public Matrix(
            final int rows,
            final int cols,
            final double[] data,
            final Address.MatrixAddress addr) {
        super(rows, cols, data, addr);
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
    public int _(final int row, final int col) {
        return addr.op(row, col);
    }



    /**
     * Retrieves an element of <code>this</code> Matrix which identified by <i>(row, col)</i>
     *
     * @param row coordinate
     * @param col coordinate
     * @return the contents of a given cell
     */
    public double get(final int row, final int col) {
        return this.$[addr.op(row, col)];
    }

    /**
     * Stores a value into an element of <code>this</code> Matrix which is identified by <i>(row, col)</i>
     *
     * @param row coordinate
     * @param col coordinate
     */
    public void set(final int row, final int col, final double value) {
        this.$[addr.op(row, col)] = value;
    }



    //
    //	Assignment operations
    //
    //	opr   method     this    right    result
    //	----- ---------- ------- -------- ------
    //	=     assign     Matrix           Matrix (1)
    //	+=    addAssign  Matrix  Matrix   this
    //	-=    subAssign  Matrix  Matrix   this
    //	*=    mulAssign  Matrix  scalar   this
    //	/=    divAssign  Matrix  scalar   this
    //


    /**
     * Returns the result of an addition of <code>this</code> Matrix and <code>another</code> Matrix
     *
     * @param another
     * @return this
     */
    public Matrix addAssign(final Matrix another) {
        QL.require(rows() == another.rows() && cols() == another.cols() ,  MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        if (this.addr.isContiguous() && another.addr.isContiguous()) {
            for (int i=0; i<size(); i++) {
                this.$[i] += another.$[i];
            }
        } else {
            int addr = 0;
            final Address.MatrixAddress.MatrixOffset toff = this.addr.offset();
            final Address.MatrixAddress.MatrixOffset aoff = another.addr.offset();
            for (int row=0; row<rows(); row++) {
                toff.setRow(row);
                aoff.setRow(row);
                for (int col=0; col<cols(); col++) {
                    this.$[toff.op()] += another.$[aoff.op()];
                    addr++;
                    toff.nextCol();
                    aoff.nextCol();
                }
            }
        }
        return this;
    }

    /**
     * Returns the result of a subtraction of <code>this</code> Matrix and <code>another</code> Matrix
     *
     * @param another
     * @return this
     */
    public Matrix subAssign(final Matrix another) {
        QL.require(rows() == another.rows() && cols() == another.cols() ,  MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        if (this.addr.isContiguous() && another.addr.isContiguous()) {
            for (int i=0; i<size(); i++) {
                this.$[i] -= another.$[i];
            }
        } else {
            int addr = 0;
            final Address.MatrixAddress.MatrixOffset toff = this.addr.offset();
            final Address.MatrixAddress.MatrixOffset aoff = another.addr.offset();
            for (int row=0; row<rows(); row++) {
                toff.setRow(row);
                aoff.setRow(row);
                for (int col=0; col<cols(); col++) {
                    this.$[toff.op()] -= another.$[aoff.op()];
                    addr++;
                    toff.nextCol();
                    aoff.nextCol();
                }
            }
        }
        return this;
    }

    /**
     * Returns the result of a multiplication of <code>this</code> Matrix by a <code>scalar</code>
     *
     * @param scalar
     * @return this
     */
    public Matrix mulAssign(final double scalar) {
        if (addr.isContiguous()) {
            for (int addr=0; addr<size(); addr++) {
                $[addr] *= scalar;
            }
        } else {
            final Address.MatrixAddress.MatrixOffset dst = this.addr.offset();
            for (int row = 0; row < rows(); row++) {
                dst.setRow(row);
                for (int col = 0; col < cols(); col++) {
                    $[dst.op()] *= scalar;
                    dst.nextCol();
                }
            }
        }
        return this;
    }

    /**
     * Returns the result of a division of <code>this</code> Matrix by a <code>scalar</code>
     *
     * @param scalar
     * @return this
     */
    public Matrix divAssign(final double scalar) {
        if (addr.isContiguous()) {
            for (int addr=0; addr<size(); addr++) {
                $[addr] /= scalar;
            }
        } else {
            final Address.MatrixAddress.MatrixOffset dst = this.addr.offset();
            for (int row = 0; row < rows(); row++) {
                dst.setRow(row);
                for (int col = 0; col < cols(); col++) {
                    $[dst.op()] /= scalar;
                    dst.nextCol();
                }
            }
        }
        return this;
    }



    //
    //	Algebraic products
    //
    //	opr   method     this    right    result
    //	----- ---------- ------- -------- ------
    //	+     add        Matrix  Matrix    Matrix
    //	-     sub        Matrix  Matrix    Matrix
    //  -     negative   Matrix            this
    //	*     mul        Matrix  scalar    Matrix
    //	/     div        Matrix  scalar    Matrix
    //

    /**
     * Returns the result of addition of <code>this</code> Matrix and <code>another</code> Matrix
     *
     * @param another
     * @return a new instance
     */
    public Matrix add(final Matrix another) {
        QL.require(rows() == another.rows() && cols() == another.cols() ,  MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Matrix result = new Matrix(rows(), cols());
        if (this.addr.isContiguous() && another.addr.isContiguous()) {
            for (int i=0; i<size(); i++) {
                result.$[i] = this.$[i] + another.$[i];
            }
        } else {
            int addr = 0;
            final Address.MatrixAddress.MatrixOffset toff = this.addr.offset();
            final Address.MatrixAddress.MatrixOffset aoff = another.addr.offset();
            for (int row=0; row<rows(); row++) {
                toff.setRow(row);
                aoff.setRow(row);
                for (int col=0; col<cols(); col++) {
                    result.$[addr] = this.$[toff.op()] + another.$[aoff.op()];
                    addr++;
                    toff.nextCol();
                    aoff.nextCol();
                }
            }
        }
        return result;
    }

    /**
     * Returns the result of a subtraction of <code>this</code> Matrix and <code>another</code> Matrix
     *
     * @param another
     * @return a new instance
     */
    public Matrix sub(final Matrix another) {
        QL.require(rows() == another.rows() && cols() == another.cols() ,  MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Matrix result = new Matrix(rows(), cols());
        if (this.addr.isContiguous() && another.addr.isContiguous()) {
            for (int addr=0; addr<size(); addr++) {
                result.$[addr] = this.$[addr] - another.$[addr];
            }
        } else {
            int addr = 0;
            final Address.MatrixAddress.MatrixOffset toff = this.addr.offset();
            final Address.MatrixAddress.MatrixOffset aoff = another.addr.offset();
            for (int row=0; row<rows(); row++) {
                toff.setRow(row);
                aoff.setRow(row);
                for (int col=0; col<cols(); col++) {
                    result.$[addr] = this.$[toff.op()] - another.$[aoff.op()];
                    addr++;
                    toff.nextCol();
                    aoff.nextCol();
                }
            }
        }
        return result;
    }


    /**
     * Returns the negative of <code>this</code> Matrix
     *
     * @return this
     */
    public Matrix negative() {
        return mulAssign(-1);
    }


    /**
     * Returns the result of a multiplication of <code>this</code> Matrix by a <code>scalar</code>
     *
     * @param scalar
     * @return a new instance
     */
    public Matrix mul(final double scalar) {
        final Matrix result = new Matrix(rows(), cols());
        if (addr.isContiguous()) {
            for (int addr=0; addr<size(); addr++) {
                result.$[addr] = this.$[addr] * scalar;
            }
        } else {
            int addr = 0;
            final Address.MatrixAddress.MatrixOffset src = this.addr.offset();
            for (int row = 0; row < rows(); row++) {
                src.setRow(row);
                for (int col = 0; col < cols(); col++) {
                    result.$[addr] = this.$[src.op()] * scalar;
                    addr++;
                    src.nextCol();
                }
            }
        }
        return result;
    }

    /**
     * Returns the result of a division of <code>this</code> Matrix by a <code>scalar</code>
     *
     * @param scalar
     * @return a new instance
     */
    public Matrix div(final double scalar) {
        final Matrix result = new Matrix(rows(), cols());
        if (addr.isContiguous()) {
            for (int addr=0; addr<size(); addr++) {
                result.$[addr] = this.$[addr] / scalar;
            }
        } else {
            int addr = 0;
            final Address.MatrixAddress.MatrixOffset src = this.addr.offset();
            for (int row = 0; row < rows(); row++) {
                src.setRow(row);
                for (int col = 0; col < cols(); col++) {
                    result.$[addr] = this.$[src.op()] / scalar;
                    addr++;
                    src.nextCol();
                }
            }
        }
        return result;
    }


    //
    //	Vectorial products
    //
    //	opr   method     this    right    result
    //	----- ---------- ------- -------- ------
    //	*     mul        Matrix  Array    Array
    //	*     mul        Matrix  Matrix   Matrix
    //

    /**
     * Returns an Array which represents the multiplication of <code>this</code> Matrix by an Array
     *
     * @param array is the input Array which participates in the operation
     * @return a new Array which contains the result
     */
    public Array mul(final Array array) {
        QL.require(cols() == array.size(), ARRAY_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Array result = new Array(rows(), this.flags());
        final Address.MatrixAddress.MatrixOffset toff = this.addr.offset();
        final Address.ArrayAddress.ArrayOffset  aoff = array.addr.offset();
        final int offsetT = this.addr.isFortran() ? 1 : 0;
        final int offsetA = array.addr.isFortran() ? 1 : 0;
        for (int row = offsetT; row < result.size()+offsetT; row++) {
            toff.setRow(row); toff.setCol(offsetT);
            aoff.setIndex(offsetA);
            double sum = 0.0;
            for (int col = offsetT; col < this.cols()+offsetT; col++) {
                final double telem = this.$[toff.op()];
                final double aelem = array.$[aoff.op()];
                sum += telem * aelem;
                toff.nextCol();
                aoff.nextIndex();
            }
            result.$[result._(row)] = sum;
        }
        return result;
    }

    /**
     * Returns a Matrix which represents the multiplication of <code>this</code> Matrix and <code>another</code> Matrix
     *
     * @param another
     * @return a new Matrix which contains the result
     */
    public Matrix mul(final Matrix another) {
        QL.require(cols() == another.rows(),  MATRIX_IS_INCOMPATIBLE); // QA:[RG]::verified
        final Matrix result = new Matrix(rows(), another.cols(), this.flags());
        final Address.MatrixAddress.MatrixOffset toff = this.addr.offset();
        final Address.MatrixAddress.MatrixOffset aoff = another.addr.offset();
        final int offsetT = this.addr.isFortran() ? 1 : 0;
        final int offsetA = another.addr.isFortran() ? 1 : 0;
        for (int col = offsetA; col < another.cols()+offsetA; col++) {
            for (int row = offsetT; row < this.rows()+offsetT; row++) {
                toff.setRow(row); toff.setCol(offsetT);
                aoff.setRow(offsetA); aoff.setCol(col);
                double sum = 0.0;
                for (int i = 0; i < this.cols(); i++) {
                    final double telem = this.$[toff.op()];
                    final double aelem = another.$[aoff.op()];
                    sum += telem * aelem;
                    toff.nextCol();
                    aoff.nextRow();
                }
                result.$[result.addr.op(row, col-offsetA+offsetT)] = sum;
            }
        }
        return result;
    }


    //
    // Decompositions
    //
    //  method       this    right    result
    //  ----------   ------- -------- ------
    //  lu           Matrix           LUDecomposition
    //  qr           Matrix           QRDecomposition
    //  cholensky    Matrix           CholeskyDecomposition
    //  svd          Matrix           SingularValueDecomposition
    //  eigenvalue   Matrix           EigenvalueDecomposition
    //

    /**
     * LU Decomposition
     *
     * @param moreGreeks is a rectangular Matrix
     * @return Structure to access L, U and piv.
     */
    public LUDecomposition lu() {
        return new LUDecomposition(this);
    }

    /**
     * QR Decomposition
     *
     * @return QRDecomposition
     * @see QRDecomposition
     */
    public QRDecomposition qr() {
        return new QRDecomposition(this);
    }

    /**
     * QR Decomposition
     *
     * @return QRDecomposition
     * @see QRDecomposition
     */
    public QRDecomposition qr(final boolean pivot) {
        return new QRDecomposition(this, pivot);
    }

    /**
     * Cholesky Decomposition
     *
     * @return CholeskyDecomposition
     * @see CholeskyDecomposition
     */
    public CholeskyDecomposition cholesky() {
        return new CholeskyDecomposition(this);
    }

    /**
     * Symmetric Schur Decomposition
     *
     * @return SymmetricSchurDecomposition
     * @see SymmetricSchurDecomposition
     */
    public SymmetricSchurDecomposition schur() {
        return new SymmetricSchurDecomposition(this);
    }

    /**
     * Singular Value Decomposition
     *
     * @return SingularValueDecomposition
     * @see SVD
     */
    public SVD svd() {
        return new SVD(this);
    }

    /**
     * Eigenvalue Decomposition
     *
     * @return EigenvalueDecomposition
     * @see EigenvalueDecomposition
     */
    public EigenvalueDecomposition eigenvalue() {
        return new EigenvalueDecomposition(this);
    }


    //
    //	Miscellaneous
    //
    //	method       this    right    result
    //	------------ ------- -------- ------
    //	transpose    Matrix           Matrix
    //  diagonal     Matrix           Array
    //  determinant  Matrix           double
    //	inverse      Matrix           Matrix
    //  solve        Matrix           Matrix
    //  swap         Matrix  Matrix   this

    /**
     * Returns the transpose of <code>this</code> Matrix
     *
     * @return a new instance which contains the result of this operation
     */
    public Matrix transpose() {
        final int offset = addr.isFortran() ? 1 : 0;
        final Matrix result = new Matrix(cols(), rows(), this.flags());
        final Address.MatrixAddress.MatrixOffset src = this.addr.offset(offset, offset);
        final Address.MatrixAddress.MatrixOffset dst = result.addr.offset(offset, offset);
        for (int row=offset; row<rows()+offset; row++) {
            src.setRow(row); src.setCol(offset);
            dst.setRow(offset);   dst.setCol(row);
            for (int col=offset; col<cols()+offset; col++) {
                result.$[dst.op()] = this.$[src.op()];
                src.nextCol();
                dst.nextRow();
            }
        }
        return result;
    }

    /**
     * Returns a diagonal from <code>this</code> Matrix, if it is square
     *
     * @return a new instance which contains the result of this operation
     */
    public Array diagonal() {
        QL.require(rows() == cols(),  MATRIX_MUST_BE_SQUARE); // QA:[RG]::verified
        final Array result = new Array(cols());
        int addr = 0;
        for (int i = 0; i < cols(); i++) {
            result.$[i] = $[addr];
            addr += cols() + 1;
        }
        return result;
    }

    /**
     * Determinant
     *
     * @return determinant of matrix
     * @exception IllegalArgumentException Matrix must be square
     */
    public double determinant() {
        return new LUDecomposition(this).det();
    }

    /**
     * Returns an inverse Matrix from <code>this</code> Matrix
     *
     * @return a new instance which contains the result of this operation
     */
    public Matrix inverse() {
        QL.require(this.rows == this.cols, "matrix is not square");
        return (new LUDecomposition(this)).solve(new Identity(rows()));
    }

//XXX
//    /**
//     * Solve A*X = B
//     *
//     * @param m right hand side
//     * @return solution if A is square, least squares solution otherwise
//     */
//    public Matrix solve (final Matrix m) {
//       return (rows() == cols()
//               ? (new LUDecomposition(this)).solve(m)
//                       : (new QRDecomposition(this, true)).solve(m));
//    }


    //
    //  Range
    //
    //  method       this    right    result
    //  ------------ ------- -------- ------
    //  rangeRow     Matrix           Array
    //  rangeCol     Matrix           Array
    //  range        Matrix           Matrix
    //

    public Array rangeRow(final int row) {
        return rangeRow(row, 0, cols());
    }

    public Array rangeRow(final int row, final int col0) {
        return rangeRow(row, col0, cols());
    }

    public Array rangeRow(final int row, final int col0, final int col1) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(row  >= offset && row  <= rows()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_ROW_INDEX);
        QL.require(col0 >= offset && col0 < cols()+offset && col1 >= offset && col1 <= cols()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_COLUMN_INDEX);
        QL.require(col0 <= col1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        return new RangeRow(row-offset, this.addr, col0-offset, col1-offset, $, rows(), cols());
    }


    public Array rangeCol(final int col) {
        return rangeCol(col, 0, rows());
    }

    public Array rangeCol(final int col, final int row0) {
        return rangeCol(col, row0, rows());
    }

    public Array rangeCol(final int col, final int row0, final int row1) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(col  >= offset && col  <= cols()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_COLUMN_INDEX);
        QL.require(row0 >= offset && row0 < rows()+offset && row1 >= offset && row1 <= rows()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_ROW_INDEX);
        QL.require(row0 <= row1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        return new RangeCol(row0-offset, row1-offset, this.addr, col-offset, $, rows(), cols());
    }


    public Matrix range(final int row0, final int row1, final int col0, final int col1) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(row0 >= offset && row0 < rows()+offset && row1 >= offset && row1 <= rows()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_ROW_INDEX);
        QL.require(row0<=row1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        QL.require(col0 >= offset && col0 < cols()+offset && col1 >= offset && col1 <= cols()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_COLUMN_INDEX);
        QL.require(col0<=col1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        final boolean contiguous = super.cols()==(col1-col0+1);
        return new RangeMatrix(row0-offset, row1-offset, this.addr, col0-offset, col1-offset, contiguous, $, rows(), cols());
    }

    public Matrix range(final int[] ridx, final int col0, final int col1) {
        return new RangeMatrix(ridx, this.addr, col0, col1, $, rows(), cols());
    }

    public Matrix range(final int row0, final int row1, final int[] cidx) {
        return new RangeMatrix(row0, row1, this.addr, cidx, $, rows(), cols());
    }

    public Matrix range(final int[] ridx, final int[] cidx) {
        return new RangeMatrix(ridx, this.addr, cidx, $, rows(), cols());
    }






    public Array constRangeRow(final int row) {
        return constRangeRow(row, 0, cols());
    }

    public Array constRangeRow(final int row, final int col0) {
        return constRangeRow(row, col0, cols());
    }

    public Array constRangeRow(final int row, final int col0, final int col1) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(row  >= offset && row  < rows()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_ROW_INDEX);
        QL.require(col0 >= offset && col0 < cols()+offset && col1 >= offset && col1 <= cols()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_COLUMN_INDEX);
        QL.require(col0<=col1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        return new ConstRangeRow(row, this.addr, col0, col1, $, rows(), cols());
    }


    public Array constRangeCol(final int col) {
        return constRangeCol(col, 0, rows());
    }

    public Array constRangeCol(final int col, final int row0) {
        return constRangeCol(col, row0, rows());
    }

    public Array constRangeCol(final int col, final int row0, final int row1) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(col  >= offset && col  < cols()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_COLUMN_INDEX);
        QL.require(row0 >= offset && row0 < rows()+offset && row1 >= offset && row1 <= rows()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_ROW_INDEX);
        QL.require(row0<=row1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        return new ConstRangeCol(row0, row1, this.addr, col, $, rows(), cols());
    }


    public Matrix constRange(final int row0, final int row1, final int col0, final int col1) {
        final int offset = addr.isFortran() ? 1 : 0;
        QL.require(row0 >= offset && row0 < rows()+offset && row1 >= offset && row1 <= rows()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_ROW_INDEX);
        QL.require(row0<=row1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        QL.require(col0 >= offset && col0 < cols()+offset && col1 >= offset && col1 <= cols()+offset, ArrayIndexOutOfBoundsException.class, Address.INVALID_COLUMN_INDEX);
        QL.require(col0<=col1, ArrayIndexOutOfBoundsException.class, Address.INVALID_BACKWARD_INDEXING);
        final boolean contiguous = super.cols()==(col1-col0+1);
        return new ConstRangeMatrix(row0, row1, this.addr, col0, col1, contiguous, $, rows(), cols());
    }

    public Matrix constRange(final int[] ridx, final int col0, final int col1) {
        return new ConstRangeMatrix(ridx, this.addr, col0, col1, $, rows(), cols());
    }

    public Matrix constRange(final int row0, final int row1, final int[] cidx) {
        return new ConstRangeMatrix(row0, row1, this.addr, cidx, $, rows(), cols());
    }

    public Matrix constRange(final int[] ridx, final int[] cidx) {
        return new ConstRangeMatrix(ridx, this.addr, cidx, $, rows(), cols());
    }

    public Matrix toFortran() {
        return this.addr.isFortran()
            ?  this
            : new Matrix(this.rows, this.cols, this.$, this.addr.toFortran());
    }

    public Matrix toJava() {
        return this.addr.isFortran()
            ?  new Matrix(this.rows, this.cols, this.$, this.addr.toJava())
            : this;
    }













    //
    // TODO: better comments
    //

    public Matrix fill(final double scalar) {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        Arrays.fill($, addr.base(), this.size(), scalar);
        return this;
    }

    public Matrix fill(final Matrix another) {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(another.addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(this.rows()==another.rows() && this.cols()==another.cols() && this.size()==another.size(), WRONG_BUFFER_LENGTH);
        // copies data
        System.arraycopy(another.$, another.addr.base(), this.$, this.addr.base(), this.size());
        return this;
    }

    /**
     * Overwrites contents of a certain row
     *
     * @param row is the requested row to be overwritten
     * @param array contains the elements to be copied
     */
    public void fillRow(final int row, final Array array) {
        QL.require(cols() == array.size() ,  ARRAY_IS_INCOMPATIBLE);
        if (this.addr.isContiguous() && array.addr.isContiguous()) {
            System.arraycopy(array.$, 0, $, addr.op(row, 0), cols());
        } else {
            final Address.ArrayAddress.ArrayOffset src = array.addr.offset();
            final Address.MatrixAddress.MatrixOffset dst = this.addr.offset(row, 0);
            for (int col = 0; col < cols(); col++) {
                $[dst.op()] = array.$[src.op()];
                src.nextIndex();
                dst.nextCol();
            }
        }
    }

    /**
     * Overwrites contents of a certain column
     *
     * @param col is the requested column to be overwritten
     * @param array contains the elements to be copied
     */
    public void fillCol(final int col, final Array array) {
        QL.require(rows() == array.size() ,  ARRAY_IS_INCOMPATIBLE); // QA:[RG]::verified
        if (this.addr.isContiguous() && array.addr.isContiguous() && cols() == 1) {
            System.arraycopy(array.$, 0, $, 0, size());
        } else {
            final Address.ArrayAddress.ArrayOffset src = array.addr.offset();
            final Address.MatrixAddress.MatrixOffset dst = this.addr.offset(0, col);
            for (int row = 0; row < rows(); row++) {
                $[dst.op()] = array.$[src.op()];
                src.nextIndex();
                dst.nextRow();
            }
        }
    }

    public Matrix swap(final Matrix another) {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(another.addr.isContiguous(), NON_CONTIGUOUS_DATA);
        QL.require(this.rows()==another.rows() && this.cols()==another.cols() && this.size()==another.size(), WRONG_BUFFER_LENGTH);
        // swaps data
        final double [] tdata;
        final Address.MatrixAddress taddr;
        tdata = this.$;  this.$ = another.$;  another.$ = tdata;
        taddr = this.addr;  this.addr = another.addr;  another.addr = taddr;
        return this;
    }

    public Matrix sort() {
        QL.require(addr.isContiguous(), NON_CONTIGUOUS_DATA);
        Arrays.sort($, addr.base(), addr.last());
        return this;
    }



    //
    // implements Cloneable
    //

    /**
     * Returns a copy of <code>this</code> Matrix
     */
    @Override
    public Matrix clone() {
        //XXX return new Matrix(this);
        final Matrix clone = (Matrix)super.clone();
        clone.$ = copyData(this);
        clone.addr = new DirectMatrixAddress(clone.$, 0, this.rows, null, 0, this.cols, this.flags(), true, this.rows, this.cols);
        return clone;
    }


    //
    // overrides Object
    //

    @Override
    // TODO: implement equals() with a near-linear approach
    // 1. the chain of Addresses must be equal
    // 2. the base and last memory addresses must be equal in all Addresses
    // *** THIS IDEA NEEDS VALIDATION ***
    public boolean equals(final Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        final int offset = addr.isFortran() ? 1 : 0;
        final StringBuffer sb = new StringBuffer();
        sb.append("[rows=").append(rows()).append(" cols=").append(cols()).append(" addr=").append(addr).append('\n');
        for (int row = offset; row < rows()+offset; row++) {
            sb.append("  [ ");
            sb.append($[addr.op(row, offset)]);
            for (int col = 1+offset; col < cols()+offset; col++) {
                sb.append(", ");
                sb.append($[addr.op(row, col)]);
            }
            sb.append("  ]\n");
        }
        sb.append("]\n");
        return sb.toString();
    }

    public int offset() {
        return addr.isFortran() ? 1 : 0;
     }




//XXX
//  //
//  // static methods
//  //
//
//    /**
//     * sqrt(a^2 + b^2) without under/overflow.
//     */
//    //TODO: verify if it can be replaced by Math.hypot
//    public static double hypot(final double a, final double b) {
//        double r;
//        if (Math.abs(a) > Math.abs(b)) {
//            r = b / a;
//            r = Math.abs(a) * Math.sqrt(1 + r * r); Math.h
//        } else if (b != 0) {
//            r = a / b;
//            r = Math.abs(b) * Math.sqrt(1 + r * r);
//        } else {
//            r = 0.0;
//        }
//        return r;
//    }


    //
    // private inner classes
    //

    private static class RangeRow extends Array {

        public RangeRow(
            final int row,
            final Address.MatrixAddress chain,
            final int col0,
            final int col1,
            final double[] data,
            final int rows,
            final int cols) {
            super(1, col1-col0,
                  data,
                  new DirectArrayRowAddress(data, row, chain, col0, col1, null, true, rows, cols));
        }
    }


    private static class RangeCol extends Array {

        public RangeCol(
                final int row0,
                final int row1,
                final Address.MatrixAddress chain,
                final int col,
                final double[] data,
                final int rows,
                final int cols) {
            super(row1-row0, 1,
                  data,
                  new DirectArrayColAddress(data, row0, row1, chain, col, null, true, rows, cols));
        }
    }


    private static class RangeMatrix extends Matrix {

        public RangeMatrix(
            final int row0,
            final int row1,
            final Address.MatrixAddress chain,
            final int col0,
            final int col1,
            final boolean contiguous,
            final double[] data,
            final int rows,
            final int cols) {
            super(row1-row0, col1-col0,
                  data,
                  new DirectMatrixAddress(data, row0, row1, chain, col0, col1, null, true, rows, cols));
        }

        public RangeMatrix(
            final int[] ridx,
            final Address.MatrixAddress chain,
            final int col0,
            final int col1,
            final double[] data,
            final int rows,
            final int cols) {
            super(ridx.length, col1-col0,
                  data,
                  new MappedMatrixAddress(data, ridx, chain, col0, col1, null, true, rows, cols));
        }

        public RangeMatrix(
            final int row0,
            final int row1,
            final Address.MatrixAddress chain,
            final int[] cidx,
            final double[] data,
            final int rows,
            final int cols) {
            super(row1-row0, cidx.length,
                  data,
                  new MappedMatrixAddress(data, row0, row1, chain, cidx, null, true, rows, cols));
        }

        public RangeMatrix(
            final int[] ridx,
            final Address.MatrixAddress chain,
            final int[] cidx,
            final double[] data,
            final int rows,
            final int cols) {
            super(ridx.length, cidx.length,
                  data,
                  new MappedMatrixAddress(data, ridx, chain, cidx, null, true, rows, cols));
        }
    }


    private static class ConstRangeRow extends RangeRow {

        public ConstRangeRow(
            final int row,
            final Address.MatrixAddress chain,
            final int col0,
            final int col1,
            final double[] data,
            final int rows,
            final int cols) {
            super(row, chain, col0, col1, data, rows, cols);
        }

        @Override
        public void set(final int pos, final double value) {
            throw new UnsupportedOperationException();
        }
    }


    private static class ConstRangeCol extends RangeCol {

        public ConstRangeCol(
                final int row0,
                final int row1,
                final Address.MatrixAddress chain,
                final int col,
                final double[] data,
                final int rows,
                final int cols) {
            super(row0, row1, chain, col, data, rows, cols);
        }

        @Override
        public void set(final int pos, final double value) {
            throw new UnsupportedOperationException();
        }
    }


    private static class ConstRangeMatrix extends RangeMatrix {

        public ConstRangeMatrix(
            final int row0,
            final int row1,
            final Address.MatrixAddress chain,
            final int col0,
            final int col1,
            final boolean contiguous,
            final double[] data,
            final int rows,
            final int cols) {
            super(row0, row1, chain, col0, col1, contiguous, data, rows, cols);
        }

        public ConstRangeMatrix(
            final int[] ridx,
            final Address.MatrixAddress chain,
            final int col0,
            final int col1,
            final double[] data,
            final int rows,
            final int cols) {
            super(ridx, chain, col0, col1, data, rows, cols);
        }

        public ConstRangeMatrix(
            final int row0,
            final int row1,
            final Address.MatrixAddress chain,
            final int[] cidx,
            final double[] data,
            final int rows,
            final int cols) {
            super(row0, row1, chain, cidx, data, rows, cols);
        }

        public ConstRangeMatrix(
            final int[] ridx,
            final Address.MatrixAddress chain,
            final int[] cidx,
            final double[] data,
            final int rows,
            final int cols) {
            super(ridx, chain, cidx, data, rows, cols);
        }

        @Override
        public void set(final int row, final int col, final double value) {
            throw new UnsupportedOperationException();
        }

    }

}
