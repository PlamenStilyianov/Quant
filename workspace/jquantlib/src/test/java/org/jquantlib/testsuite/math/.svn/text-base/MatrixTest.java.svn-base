/*
 Copyright (C) 2007 Richard Gomes

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

package org.jquantlib.testsuite.math;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.EnumSet;
import java.util.Set;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.Constants;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Identity;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.math.matrixutilities.QRDecomposition;
import org.jquantlib.math.matrixutilities.SymmetricSchurDecomposition;
import org.jquantlib.math.matrixutilities.internal.Address;
import org.junit.Test;

/**
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q0_UNFINISHED, version = Version.V097, reviewers = { "" })
public class MatrixTest {

    private final Set<Address.Flags> jFlags;
    private final Set<Address.Flags> fFlags;


    public MatrixTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");

        this.jFlags = EnumSet.noneOf(Address.Flags.class);
        this.fFlags = EnumSet.of(Address.Flags.FORTRAN);
    }


    @Test
    public void testToString() {
        testToString(jFlags);
        testToString(fFlags);
    }

    private void testToString(final Set<Address.Flags> flags) {

        final Matrix M1 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.4 },
              { 0.7,  0.4,  1.0 }
        }, flags);

        final Matrix M2 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.3 },
              { 0.7,  0.3,  1.0 }
        }, flags);

        final Matrix I = new Identity(3, flags);

        final Matrix M3 = new Matrix(new double[][] {
              { 1,   2,   3,   4 },
              { 2,   0,   2,   1 },
              { 0,   1,   0,   0 }
        }, flags);

        final Matrix M4 = new Matrix(new double[][] {
              {  1,   2,   400    },
              {  2,   0,     1    },
              { 30,   2,     0    },
              {  2,   0,     1.05 }
        }, flags);

        // from Higham - nearest correlation matrix
        final Matrix M5 = new Matrix(new double[][] {
              {  2,   -1,     0,    0 },
              { -1,    2,    -1,    0 },
              {  0,   -1,     2,   -1 },
              {  0,    0,    -1,    2 },
        }, flags);

        final Matrix matrices[] = { M1, M2, M3, M4, M5 };

//        for (final Matrix m : matrices) {
//            System.out.println(m);
//        }
    }

    @Test
    public void toFortran() {
        toFortran(jFlags);
        toFortran(fFlags);
    }

    private void toFortran(final Set<Address.Flags> flags) {

        final Matrix M1 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.4 },
              { 0.7,  0.4,  1.0 }
        }, flags);

        final Matrix M2 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.3 },
              { 0.7,  0.3,  1.0 }
        }, flags);

        final Matrix I = new Identity(3, flags);

        final Matrix M3 = new Matrix(new double[][] {
              { 1,   2,   3,   4 },
              { 2,   0,   2,   1 },
              { 0,   1,   0,   0 }
        }, flags);

        final Matrix M4 = new Matrix(new double[][] {
              {  1,   2,   400    },
              {  2,   0,     1    },
              { 30,   2,     0    },
              {  2,   0,     1.05 }
        }, flags);

        // from Higham - nearest correlation matrix
        final Matrix M5 = new Matrix(new double[][] {
              {  2,   -1,     0,    0 },
              { -1,    2,    -1,    0 },
              {  0,   -1,     2,   -1 },
              {  0,    0,    -1,    2 },
        }, flags);

        final Matrix matrices[] = { M1, M2, M3, M4, M5 };

//        for (final Matrix m : matrices) {
//            System.out.println(m.toFortran());
//        }
    }


    @Test
    public void toJava() {
        toJava(jFlags);
        toJava(fFlags);
    }

    private void toJava(final Set<Address.Flags> flags) {

        final Matrix M1 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.4 },
              { 0.7,  0.4,  1.0 }
        }, flags);

        final Matrix M2 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.3 },
              { 0.7,  0.3,  1.0 }
        }, flags);

        final Matrix I = new Identity(3, flags);

        final Matrix M3 = new Matrix(new double[][] {
              { 1,   2,   3,   4 },
              { 2,   0,   2,   1 },
              { 0,   1,   0,   0 }
        }, flags);

        final Matrix M4 = new Matrix(new double[][] {
              {  1,   2,   400    },
              {  2,   0,     1    },
              { 30,   2,     0    },
              {  2,   0,     1.05 }
        }, flags);

        // from Higham - nearest correlation matrix
        final Matrix M5 = new Matrix(new double[][] {
              {  2,   -1,     0,    0 },
              { -1,    2,    -1,    0 },
              {  0,   -1,     2,   -1 },
              {  0,    0,    -1,    2 },
        }, flags);

        final Matrix matrices[] = { M1, M2, M3, M4, M5 };

//        for (final Matrix m : matrices) {
//            System.out.println(m.toJava());
//        }
    }





//    @Test
//    public void testEquals() {
//        final Matrix mA = new Matrix(new double[][] {
//                { 1.0, 2.0, 3.0, 4.0 },
//                { 1.0, 2.0, 3.0, 4.0 },
//                { 1.0, 2.0, 3.0, 4.0 },
//        });
//
//        final Matrix mB = new Matrix(new double[][] {
//                { 1.0, 2.0, 3.0, 4.0 },
//                { 1.0, 2.0, 3.0, 4.0 },
//                { 1.0, 2.0, 3.0, 4.0 },
//        });
//
//        if (!mA.equals(mB)) {
//            fail("'equals' failed");
//        }
//    }


//XXX :: not used anymore
//
//    private Matrix augmented(final Matrix matrix) {
//        final Matrix result = new Matrix(matrix.rows()+2, matrix.cols()+2);
//        // set first row with random numbers
//        for (int j=0; j<result.cols(); j++) {
//            result.set(0, j, Math.random());
//        }
//        // copy matrix to inner part of a new matrix
//        for (int i=0, ii=1; i<matrix.size(); i++,ii++) {
//            result.set(ii, 0, Math.random());
//            for (int j=0, jj=1; i<matrix.cols(); j++,jj++) {
//                result.set(ii, jj, matrix.get(i,j));
//            }
//            result.set(ii, result.cols()-1, Math.random());
//        }
//        // set last row with random numbers
//        for (int j=0; j<result.cols(); j++) {
//            result.set(result.rows()-1, j, Math.random());
//        }
//        return result;
//    }
//
//    private Matrix range(final Matrix matrix) {
//        return matrix.range(1, 1, matrix.rows()-2, matrix.cols()-2 );
//    }


    public static boolean equals(final Matrix a, final Matrix b) {
        if (a.size() != b.size())
            return false;
        final int offsetA = a.offset();
        final int offsetB = b.offset();
        for (int i=0; i<a.rows(); i++) {
            for (int j=0; j<a.cols(); j++) {
                if (a.get(i+offsetA, j+offsetA) != b.get(i+offsetB, j+offsetB))
                    return false;
            }
        }
        return true;
    }



    @Test
    public void testClone() {
        testClone(jFlags, jFlags);
        testClone(jFlags, fFlags);
        testClone(fFlags, jFlags);
        testClone(fFlags, fFlags);
    }

    private void testClone(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Matrix mB = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsB);

        final Matrix m = mA.clone();
        if (m == mA) {
            fail("'clone' must return a new instance");
        }
        if (m == mB) {
            fail("'clone' must return a new instance");
        }
        if (!equals(m, mB)) {
            fail("'clone' failed");
        }
    }



    @Test
    public void empty() {
        empty(jFlags);
        empty(fFlags);
    }

    private void empty(final Set<Address.Flags> flags) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flags);

        if (mA.empty()) {
            fail("'empty' failed");
        }
    }



    @Test
    public void fill() {
        fill(jFlags);
        fill(fFlags);
    }

    private void fill(final Set<Address.Flags> flags) {
        final Matrix mA = new Matrix(new double[][] {
                { 2.0, 2.0, 2.0, 2.0 },
                { 2.0, 2.0, 2.0, 2.0 },
                { 2.0, 2.0, 2.0, 2.0 },
        }, flags);

        final Matrix m = new Matrix(3, 4).fill(2.0);
        if (!equals(m, mA)) {
            fail("'fill' failed");
        }
    }


    @Test
    public void addAssign() {
        addAssign(jFlags, jFlags);
        addAssign(jFlags, fFlags);
        addAssign(fFlags, jFlags);
        addAssign(fFlags, fFlags);
    }

    private void addAssign(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Matrix mB = new Matrix(new double[][] {
                { 4.0, 3.0, 2.0, 1.0 },
                { 5.0, 4.0, 3.0, 2.0 },
                { 6.0, 5.0, 4.0, 3.0 },
                { 7.0, 6.0, 5.0, 4.0 },
        }, flagsB);


        final Matrix m = mA.addAssign(mB);
        if (m != mA) {
            fail("addAssign must return <this>");
        }

        final int offset = m.offset();
        for (int row=offset; row<m.rows()+offset; row++) {
            for (int col=offset; col<m.cols()+offset; col++) {
                if (m.get(row, col) != row-offset+5) {
                    fail("addAssign failed");
                }
            }
        }
    }


    @Test
    public void subAssign() {
        subAssign(jFlags, jFlags);
        subAssign(jFlags, fFlags);
        subAssign(fFlags, jFlags);
        subAssign(fFlags, fFlags);
    }

    private void subAssign(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Matrix mB = new Matrix(new double[][] {
                { 4.0, 5.0, 6.0,  7.0 },
                { 5.0, 6.0, 7.0,  8.0 },
                { 6.0, 7.0, 8.0,  9.0 },
                { 7.0, 8.0, 9.0, 10.0 },
        }, flagsB);


        final Matrix m = mB.subAssign(mA);
        if (m != mB) {
            fail("subAssign must return <this>");
        }

        final int offset = m.offset();
        for (int row=offset; row<m.rows()+offset; row++) {
            for (int col=offset; col<m.cols()+offset; col++) {
                if (m.get(row, col) != row-offset+3) {
                    fail("subAssign failed");
                }
            }
        }
    }



    @Test
    public void mulAssign() {
        mulAssign(jFlags);
        mulAssign(fFlags);
    }

    private void mulAssign(final Set<Address.Flags> flags) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flags);

        final Matrix m = mA.mulAssign(2.5);
        if (m != mA) {
            fail("mulAssign must return <this>");
        }

        final int offset = m.offset();
        for (int row=offset; row<m.rows()+offset; row++) {
            for (int col=offset; col<m.cols()+offset; col++) {
                if (!Closeness.isClose(m.get(row, col), (col-offset+1)*2.5)) {
                    fail("mulAssign failed");
                }
            }
        }
    }


    @Test
    public void divAssign() {
        divAssign(jFlags);
        divAssign(fFlags);
    }

    private void divAssign(final Set<Address.Flags> flags) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flags);

        final Matrix m = mA.divAssign(2.5);
        if (m != mA) {
            fail("divAssign must return <this>");
        }

        final int offset = m.offset();
        for (int row=offset; row<m.rows()+offset; row++) {
            for (int col=offset; col<m.cols()+offset; col++) {
                if (!Closeness.isClose(m.get(row, col), (col-offset+1)/2.5)) {
                    fail("divAssign failed");
                }
            }
        }
    }


    @Test
    public void add() {
        add(jFlags, jFlags);
        add(jFlags, fFlags);
        add(fFlags, jFlags);
        add(fFlags, fFlags);
    }

    private void add(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Matrix mB = new Matrix(new double[][] {
                { 4.0, 3.0, 2.0, 1.0 },
                { 5.0, 4.0, 3.0, 2.0 },
                { 6.0, 5.0, 4.0, 3.0 },
                { 7.0, 6.0, 5.0, 4.0 },
        }, flagsB);


        final Matrix m = mA.add(mB);
        if (m == mA) {
            fail("'add' must return a new instance");
        }
        if (m.rows() != mA.rows() || m.cols() != mA.cols()) {
            fail("'add' failed");
        }

        for (int row=0; row<m.rows(); row++) {
            for (int col=0; col<m.cols(); col++) {
                if (m.get(row, col) != row+5) {
                    fail("'add' failed");
                }
            }
        }
    }


    @Test
    public void sub() {
        sub(jFlags, jFlags);
        sub(jFlags, fFlags);
        sub(fFlags, jFlags);
        sub(fFlags, fFlags);
    }

    private void sub(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {

        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Matrix mB = new Matrix(new double[][] {
                { 4.0, 5.0, 6.0,  7.0 },
                { 5.0, 6.0, 7.0,  8.0 },
                { 6.0, 7.0, 8.0,  9.0 },
                { 7.0, 8.0, 9.0, 10.0 },
        }, flagsB);


        final Matrix m = mB.sub(mA);
        if (m == mB) {
            fail("'sub' must return a new instance");
        }
        if (m.rows() != mB.rows() || m.cols() != mB.cols()) {
            fail("'sub' failed");
        }

        for (int row=0; row<m.rows(); row++) {
            for (int col=0; col<m.cols(); col++) {
                if (m.get(row, col) != row+3) {
                    fail("'sub' failed");
                }
            }
        }

    }



    @Test
    public void mulScalar() {
        mulScalar(jFlags);
        mulScalar(fFlags);
    }

    private void mulScalar(final Set<Address.Flags> flags) {

        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flags);

        final Matrix m = mA.mul(2.5);
        if (m == mA) {
            fail("'sub' must return a new instance");
        }
        if (m.rows() != mA.rows() || m.cols() != mA.cols()) {
            fail("'sub' failed");
        }

        for (int row=0; row<m.rows(); row++) {
            for (int col=0; col<m.cols(); col++) {
                if (!Closeness.isClose(m.get(row, col), (col+1)*2.5)) {
                    fail("'mul' failed");
                }
            }
        }
    }


    @Test
    public void mulArray() {
        mulArray(jFlags, jFlags);
        mulArray(jFlags, fFlags);
        mulArray(fFlags, jFlags);
        mulArray(fFlags, fFlags);
    }

    private void mulArray(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {

        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Array aD = new Array(new double[] { 1.0, 1.0, 1.0, 1.0 }, flagsB);

        final Array a = mA.mul(aD);
        if (a.size() != mA.rows()) {
            fail("'mul' failed");
        }

        final int offset = a.flags().contains(Address.Flags.FORTRAN) ? 1 : 0;
        for (int col=offset; col<a.cols()+offset; col++) {
            if (a.get(col) != 10.0) {
                fail("'mul' failed");
            }
        }

    }


    @Test
    public void mulMatrix() {
        mulMatrix(jFlags, jFlags);
        mulMatrix(jFlags, fFlags);
        mulMatrix(fFlags, jFlags);
        mulMatrix(fFlags, fFlags);
    }

    private void mulMatrix(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mI = new Matrix(new double[][] {
                { 1, 0, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 0, 0, 0, 1 },
        }, flagsA);

        final Matrix mA = new Matrix(new double[][] {
                { 4.0, 3.0, 2.0, 1.0 },
                { 5.0, 4.0, 3.0, 2.0 },
                { 6.0, 5.0, 4.0, 3.0 },
                { 7.0, 6.0, 5.0, 4.0 },
        }, flagsB);

        final Matrix m = mI.mul(mA);
        if (m == mI) {
            fail("'mul' must return a new instance");
        }
        if (m == mA) {
            fail("'mul' must return a new instance");
        }
        if (!equals(m,mA)) {
            fail("'mul' failed");
        }
    }

    @Test
    public void divScalar() {
        divScalar(jFlags);
        divScalar(fFlags);
    }

    private void divScalar(final Set<Address.Flags> flags) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        });

        final Matrix m = mA.div(2.5);
        if (m == mA) {
            fail("'div' must return a new instance");
        }
        if (m.rows() != mA.rows() || m.cols() != mA.cols()) {
            fail("'add' failed");
        }

        for (int row=0; row<m.rows(); row++) {
            for (int col=0; col<m.cols(); col++) {
                if (!Closeness.isClose(m.get(row, col), (col+1)/2.5)) {
                    fail("'div' failed");
                }
            }
        }
    }



    @Test
    public void swap() {
        swap(jFlags, jFlags);
        swap(jFlags, fFlags);
        swap(fFlags, jFlags);
        swap(fFlags, fFlags);
    }

    private void swap(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Matrix mB = new Matrix(new double[][] {
                { 4.0, 3.0, 2.0, 1.0 },
                { 5.0, 4.0, 3.0, 2.0 },
                { 6.0, 5.0, 4.0, 3.0 },
                { 7.0, 6.0, 5.0, 4.0 },
        }, flagsB);

        final Matrix mAclone = mA.clone();
        final Matrix mBclone = mB.clone();

        mA.swap(mB);
        if (!equals(mA, mBclone)) {
            fail("'swap' failed");
        }
        if (!equals(mB, mAclone)) {
            fail("'swap' failed");
        }
    }


    @Test
    public void transpose() {
        transpose(jFlags, jFlags);
        transpose(jFlags, fFlags);
        transpose(fFlags, jFlags);
        transpose(fFlags, fFlags);
    }

    private void transpose(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
                { 1.0, 2.0, 3.0, 4.0 },
        }, flagsA);

        final Matrix mB = new Matrix(new double[][] {
                { 1.0, 1.0, 1.0 },
                { 2.0, 2.0, 2.0 },
                { 3.0, 3.0, 3.0 },
                { 4.0, 4.0, 4.0 },
        }, flagsB);

        final Matrix m = mA.transpose();

        if (m == mA) {
            fail("'transpose' must return a new instance");
        }
        if (m == mB) {
            fail("'transpose' must return a new instance");
        }
        if (!equals(m, mB)) {
            fail("'transpose' failed");
        }
    }


    @Test
    public void diagonal() {
        diagonal(jFlags, jFlags);
        diagonal(jFlags, fFlags);
        diagonal(fFlags, jFlags);
        diagonal(fFlags, fFlags);
    }

    private void diagonal(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Matrix mA = new Matrix(new double[][] {
                { 1.0, 9.0, 9.0, 9.0 },
                { 9.0, 2.0, 9.0, 9.0 },
                { 9.0, 9.0, 3.0, 9.0 },
                { 9.0, 9.0, 9.0, 4.0 },
        }, flagsA);

        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flagsB);

        if (!ArrayTest.equals(mA.diagonal(), aA)) {
            fail("'diagonal' failed");
        }
    }


    @Test
    public void inverse() {
        inverse(jFlags);
        inverse(fFlags);
    }

    private void inverse(final Set<Address.Flags> flags) {

        final Matrix M1 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.4 },
              { 0.7,  0.4,  1.0 }
        }, flags);

        final Matrix M2 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.3 },
              { 0.7,  0.3,  1.0 }
        }, flags);

        final Matrix I = new Identity(3, flags);

        // from Higham - nearest correlation matrix
        final Matrix M5 = new Matrix(new double[][] {
              {  2,   -1,     0,    0 },
              { -1,    2,    -1,    0 },
              {  0,   -1,     2,   -1 },
              {  0,    0,    -1,    2 },
        }, flags);

        QL.info("Testing LU inverse calculation...");

        final Matrix matrices[] = { M1, M2, I, M5 };

        for (final Matrix m : matrices) {
            inverse(m);
        }
    }


    private void inverse(final Matrix m) {
        QL.info("Testing LU inverse calculation...");

        final double tol = 1.0e-12;

        final Matrix A = new Matrix(m);
        // System.out.println("A = "+A.toString());

        final Matrix invA = A.inverse();
        // System.out.println("invA = "+invA.toString());

        final Matrix I1 = invA.mul(A);
        // System.out.println("I1 = "+I1.toString());

        final Matrix I2 = A.mul(invA);
        // System.out.println("I2 = "+I2.toString());

        final Matrix eins = new Identity(A.rows());
        // System.out.println("eins = "+eins.toString());

        final double d = norm(I1.sub(eins));
        // System.out.println("d = "+String.valueOf(d));

        if (d > tol) {
            fail("inverse(A)*A does not recover unit matrix");
        }

        if (norm(I2.sub(eins)) > tol) {
            fail("A*inverse(A) does not recover unit matrix");
        }
    }


    static private final String RangeRow_FAILED = "RangeRow failed";
    static private final String RangeCol_FAILED = "RangeCol failed";
    static private final String WRONG_EXCEPTION = "wrong exception was thrown";
    static private final String MISSING_EXCEPTION = "failed to throw an exception";
    static private final String TEST_SUCCEEDED = "**** Exception caught: TEST SUCCEEDED *****";


    @Test
    public void testRangeRow() {

        final Matrix mA = new Matrix(new double[][] {
                { 1.0,  0.9,  0.7 },
                { 0.8,  2.0,  3.2 },
                { 0.6,  3.1,  5.0 }
        });

        testRangeRow(mA);
    }

    private void testRangeRow(final Matrix mA) {
        Array array;

        // test RangeRow

        // { 1.0,  0.9,  0.7 }

        array = mA.rangeRow(0, 0, 1);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==1);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 1.0));

        array = mA.rangeRow(0, 0, 2);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==2);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 1.9));

        array = mA.rangeRow(0, 0, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==3);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 2.6));

        array = mA.rangeRow(0, 1, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==2);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 1.6));

        array = mA.rangeRow(0, 2, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==1);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 0.7));

        // { 0.8,  2.0,  3.2 }

        array = mA.rangeRow(1, 0, 1);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==1);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 0.8));

        array = mA.rangeRow(1, 0, 2);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==2);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 2.8));

        array = mA.rangeRow(1, 0, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==3);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 6.0));

        array = mA.rangeRow(1, 1, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==2);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 5.2));

        array = mA.rangeRow(1, 2, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==1);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 3.2));

        // { 0.6,  3.1,  5.0 }

        array = mA.rangeRow(2, 0, 1);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==1);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 0.6));

        array = mA.rangeRow(2, 0, 2);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==2);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 3.7));

        array = mA.rangeRow(2, 0, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==3);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 8.7));

        array = mA.rangeRow(2, 1, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==2);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 8.1));

        array = mA.rangeRow(2, 2, 3);
        // System.out.println(array.toString());
        assertTrue(RangeRow_FAILED, array.size()==1);
        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 5.0));


//        // test RangeRow backwards
//
//        // { 1.0,  0.9,  0.7 }
//
//        array = mA.rangeRow(0, 0, 0);
//        assertTrue(RangeRow_FAILED, array.size()==1);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 1.0));
//
//        array = mA.rangeRow(0, 1, 0);
//        assertTrue(RangeRow_FAILED, array.size()==2);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 1.9));
//
//        array = mA.rangeRow(0, 2, 0);
//        assertTrue(RangeRow_FAILED, array.size()==3);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 2.6));
//
//        array = mA.rangeRow(0, 2, 1);
//        assertTrue(RangeRow_FAILED, array.size()==2);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 1.6));
//
//        array = mA.rangeRow(0, 2, 2);
//        assertTrue(RangeRow_FAILED, array.size()==1);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 0.7));
//
//        // { 0.8,  2.0,  3.2 }
//
//        array = mA.rangeRow(1, 0, 0);
//        assertTrue(RangeRow_FAILED, array.size()==1);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 0.8));
//
//        array = mA.rangeRow(1, 1, 0);
//        assertTrue(RangeRow_FAILED, array.size()==2);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 2.8));
//
//        array = mA.rangeRow(1, 2, 0);
//        assertTrue(RangeRow_FAILED, array.size()==3);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 6.0));
//
//        array = mA.rangeRow(1, 2, 1);
//        assertTrue(RangeRow_FAILED, array.size()==2);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 5.2));
//
//        array = mA.rangeRow(1, 2, 2);
//        assertTrue(RangeRow_FAILED, array.size()==1);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 3.2));
//
//        // { 0.6,  3.1,  5.0 }
//
//        array = mA.rangeRow(2, 0, 0);
//        assertTrue(RangeRow_FAILED, array.size()==1);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 0.6));
//
//        array = mA.rangeRow(2, 1, 0);
//        assertTrue(RangeRow_FAILED, array.size()==2);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 3.7));
//
//        array = mA.rangeRow(2, 2, 0);
//        assertTrue(RangeRow_FAILED, array.size()==3);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 8.7));
//
//        array = mA.rangeRow(2, 2, 1);
//        assertTrue(RangeRow_FAILED, array.size()==2);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 8.1));
//
//        array = mA.rangeRow(2, 2, 2);
//        assertTrue(RangeRow_FAILED, array.size()==1);
//        assertTrue(RangeRow_FAILED, Closeness.isClose(array.accumulate(), 5.0));

        // test RangeRow raising ArrayIndexOutOfBoundsException

        try {
            array = mA.rangeRow(-1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeRow(0, -1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeRow(0, 0, -1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeRow(0, -1, 0);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeRow(0, -1, -1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeRow(0, 0, 4);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeRow(0, 4, 0);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeRow(0, 4, 4);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

    }


    @Test
    public void testRangeCol() {

        final Matrix mA = new Matrix(new double[][] {
                { 1.0,  0.9,  0.7 },
                { 0.8,  2.0,  3.2 },
                { 0.6,  3.1,  5.0 }
        });

        testRangeCol(mA);
    }

    private void testRangeCol(final Matrix mA) {

        Array array;

        // test RangeCol

        // { 1.0,  0.8,  0.6 }

        array = mA.rangeCol(0, 0, 1);
        assertTrue(RangeCol_FAILED, array.size()==1);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 1.0));

        array = mA.rangeCol(0, 0, 2);
        // System.out.println(array.toString());
        assertTrue(RangeCol_FAILED, array.size()==2);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 1.8));

        array = mA.rangeCol(0, 0, 3);
        assertTrue(RangeCol_FAILED, array.size()==3);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 2.4));

        array = mA.rangeCol(0, 1, 3);
        assertTrue(RangeCol_FAILED, array.size()==2);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 1.4));

        array = mA.rangeCol(0, 2, 3);
        assertTrue(RangeCol_FAILED, array.size()==1);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 0.6));

        // { 0.9,  2.0,  3.1 }

        array = mA.rangeCol(1, 0, 1);
        assertTrue(RangeCol_FAILED, array.size()==1);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 0.9));

        array = mA.rangeCol(1, 0, 2);
        assertTrue(RangeCol_FAILED, array.size()==2);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 2.9));

        array = mA.rangeCol(1, 0, 3);
        assertTrue(RangeCol_FAILED, array.size()==3);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 6.0));

        array = mA.rangeCol(1, 1, 3);
        assertTrue(RangeCol_FAILED, array.size()==2);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 5.1));

        array = mA.rangeCol(1, 2, 3);
        assertTrue(RangeCol_FAILED, array.size()==1);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 3.1));

        // { 0.7,  3.2,  5.0 }

        array = mA.rangeCol(2, 0, 1);
        assertTrue(RangeCol_FAILED, array.size()==1);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 0.7));

        array = mA.rangeCol(2, 0, 2);
        assertTrue(RangeCol_FAILED, array.size()==2);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 3.9));

        array = mA.rangeCol(2, 0, 3);
        assertTrue(RangeCol_FAILED, array.size()==3);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 8.9));

        array = mA.rangeCol(2, 1, 3);
        assertTrue(RangeCol_FAILED, array.size()==2);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 8.2));

        array = mA.rangeCol(2, 2, 3);
        assertTrue(RangeCol_FAILED, array.size()==1);
        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 5.0));


//        // test rangeCol backwards
//
//        // { 1.0,  0.8,  0.6 }
//
//        array = mA.rangeCol(0, 0, 0);
//        assertTrue(RangeCol_FAILED, array.size()==1);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 1.0));
//
//        array = mA.rangeCol(0, 1, 0);
//        assertTrue(RangeCol_FAILED, array.size()==2);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 1.8));
//
//        array = mA.rangeCol(0, 2, 0);
//        assertTrue(RangeCol_FAILED, array.size()==3);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 2.4));
//
//        array = mA.rangeCol(0, 2, 1);
//        assertTrue(RangeCol_FAILED, array.size()==2);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 1.4));
//
//        array = mA.rangeCol(0, 2, 2);
//        assertTrue(RangeCol_FAILED, array.size()==1);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 0.6));
//
//        // { 0.9,  2.0,  3.1 }
//
//        array = mA.rangeCol(1, 0, 0);
//        assertTrue(RangeCol_FAILED, array.size()==1);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 0.9));
//
//        array = mA.rangeCol(1, 1, 0);
//        assertTrue(RangeCol_FAILED, array.size()==2);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 2.9));
//
//        array = mA.rangeCol(1, 2, 0);
//        assertTrue(RangeCol_FAILED, array.size()==3);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 6.0));
//
//        array = mA.rangeCol(1, 2, 1);
//        assertTrue(RangeCol_FAILED, array.size()==2);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 5.1));
//
//        array = mA.rangeCol(1, 2, 2);
//        assertTrue(RangeCol_FAILED, array.size()==1);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 3.1));
//
//        // { 0.7,  3.2,  5.0 }
//
//        array = mA.rangeCol(2, 0, 0);
//        assertTrue(RangeCol_FAILED, array.size()==1);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 0.7));
//
//        array = mA.rangeCol(2, 1, 0);
//        assertTrue(RangeCol_FAILED, array.size()==2);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 3.9));
//
//        array = mA.rangeCol(2, 2, 0);
//        assertTrue(RangeCol_FAILED, array.size()==3);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 8.9));
//
//        array = mA.rangeCol(2, 2, 1);
//        assertTrue(RangeCol_FAILED, array.size()==2);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 8.2));
//
//        array = mA.rangeCol(2, 2, 2);
//        assertTrue(RangeCol_FAILED, array.size()==1);
//        assertTrue(RangeCol_FAILED, Closeness.isClose(array.accumulate(), 5.0));

        // test rangeCol raising ArrayIndexOutOfBoundsException

        try {
            array = mA.rangeCol(-1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeCol(0, -1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeCol(0, 0, -1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeCol(0, -1, 0);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeCol(0, -1, -1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeCol(0, 0, 4);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeCol(0, 4, 0);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            array = mA.rangeCol(0, 4, 4);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

    }


    @Test
    public void testRange() {

        final Matrix mA = new Matrix(new double[][] {
                { -1.0,      1.1,  1.1,  1.1,      -9.0 },
                //--------------------------------------
                { -1.0, /**/ 1.0,  0.9,  0.7, /**/ -9.0 },
                { -2.5, /**/ 0.8,  2.0,  3.2, /**/ -6.5 },
                { -4.0, /**/ 0.6,  3.1,  5.0, /**/ -4.0 },
                //--------------------------------------
                { -4.0,      9.9,  9.9,  9.9,      -4.0 },
        });

        final Matrix mB = new Matrix(new double[][] {
                { -1.0,      1.1,  1.1,  1.1,      -9.0 },
                //--------------------------------------
                { -1.0, /**/ 5.0,  3.1,  0.6, /**/ -9.0 },
                { -2.5, /**/ 3.2,  2.0,  0.8, /**/ -6.5 },
                { -4.0, /**/ 0.7,  0.9,  1.0, /**/ -4.0 },
                //--------------------------------------
                { -4.0,      9.9,  9.9,  9.9,      -4.0 },
        });

        Matrix matrix;

        matrix = mA.range(1, 4, 1, 4);
        // System.out.println(matrix.toString());
        testRangeRow(matrix);

// test range() backwards
//        matrix = mA.range(3, 1, 3, 1);
//        testRangeRow(matrix);


        try {
            matrix = mA.range(-1, 3, 1, 3);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            matrix = mA.range(1, -1, 1, 3);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            matrix = mA.range(1, 3, -1, 3);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            matrix = mA.range(1, 3, 1, -1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            matrix = mA.range(1, 6, 1, 4);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            matrix = mA.range(1, 4, 4, 1);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

        try {
            matrix = mA.range(4, 1, 1, 4);
            fail(MISSING_EXCEPTION);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println(TEST_SUCCEEDED);
        } catch (final Exception e) {
            fail(WRONG_EXCEPTION);
        }

    }


    @Test
    public void testEigenvectors() {
        testEigenvectors(jFlags);
        testEigenvectors(fFlags);
    }

    private void testEigenvectors(final Set<Address.Flags> flags) {
    	QL.info("Testing eigenvalues and eigenvectors calculation...");

        final Matrix M1 = new Matrix(new double[][] {
                { 1.0,  0.9,  0.7 },
                { 0.9,  1.0,  0.4 },
                { 0.7,  0.4,  1.0 }
        }, flags);

        final Matrix M2 = new Matrix(new double[][] {
                { 1.0,  0.9,  0.7 },
                { 0.9,  1.0,  0.3 },
                { 0.7,  0.3,  1.0 }
        }, flags);

        final Matrix testMatrices[] = { M1, M2 };


        for (final Matrix M : testMatrices) {

    		final SymmetricSchurDecomposition schur = M.schur();
    		final Array eigenValues = schur.eigenvalues();
    		final Matrix eigenVectors = schur.eigenvectors();
    		final double minHolder = Constants.QL_MAX_REAL;

    		final int N = M.cols();
    		final int offset = M.offset();

    		for (int i=offset; i<N+offset; i++) {
    			final Array v = new Array(N, M.flags());
    			for (int j=offset; j<N+offset; j++) {
                    v.set( j, eigenVectors.get(j, i) );
                }
    			// check definition
    			final Array a = M.mul(v);
    			final Array b = v.mul(eigenValues.get(i));
    			final double tol = norm(a.sub(b));
    			if (tol > 1.0e-15) {
                    fail("Eigenvector definition not satisfied");
                }
    		}

    		// check normalization
    		final Matrix m = eigenVectors.mul(eigenVectors.transpose());
    		final Identity ID = new Identity(N);
    		final double tol = norm(m.sub(ID));
    		if (tol > 1.0e-15) {
                fail("Eigenvector not normalized");
            }
    	}
    }


    //    @Test
    //    public void testSqrt() {
    //
    //        fail("not implemented yet");
    //
    //        BOOST_MESSAGE("Testing matricial square root...");
    //
    //        setup();
    //
    //        final Matrix m = pseudoSqrt(M1, SalvagingAlgorithm::None);
    //        final Matrix temp = m*transpose(m);
    //        final Real error = norm(temp - M1);
    //        final Real tolerance = 1.0e-12;
    //        if (error>tolerance) {
    //            BOOST_FAIL("Matrix square root calculation failed\n"
    //                       << "original matrix:\n" << M1
    //                       << "pseudoSqrt:\n" << m
    //                       << "pseudoSqrt*pseudoSqrt:\n" << temp
    //                       << "\nerror:     " << error
    //                       << "\ntolerance: " << tolerance);
    //        }
    //    }


    //    @Test
    //    public void testHighamSqrt() {
    //
    //        fail("not implemented yet");
    //
    //        BOOST_MESSAGE("Testing Higham matricial square root...");
    //
    //        setup();
    //
    //        final Matrix tempSqrt = pseudoSqrt(M5, SalvagingAlgorithm::Higham);
    //        final Matrix ansSqrt = pseudoSqrt(M6, SalvagingAlgorithm::None);
    //        final Real error = norm(ansSqrt - tempSqrt);
    //        final Real tolerance = 1.0e-4;
    //        if (error>tolerance) {
    //            BOOST_FAIL("Higham matrix correction failed\n"
    //                       << "original matrix:\n" << M5
    //                       << "pseudoSqrt:\n" << tempSqrt
    //                       << "should be:\n" << ansSqrt
    //                       << "\nerror:     " << error
    //                       << "\ntolerance: " << tolerance);
    //        }
    //    }


    //    @Test
    //    public void testSVD() {
    //
    //        QL.info("Testing singular value decomposition...");
    //
    //        final double tol = 1.0e-12;
    //        final Matrix testMatrices[] = { M1, M2, M3, M4 };
    //
    //        for (final Matrix A : testMatrices) {
    //            final SVD svd = A.svd();
    //            // U is m x n
    //            final Matrix U = svd.U();
    //            // s is n long
    //            final Array s = svd.singularValues();
    //            // S is n x n
    //            final Matrix S = svd.S();
    //            // V is n x n
    //            final Matrix V = svd.V();
    //
    //            for (int i=0; i < S.rows(); i++) {
    //                if (S.get(i,i) != s.get(i))
    //                    fail("S not consistent with s");
    //            }
    //
    //            Identity ID;
    //
    //            // tests
    //            final Matrix U_Utranspose = U.transpose().mul(U);
    //            ID = new Identity(U_Utranspose.cols());
    //            if (norm(U_Utranspose.sub(ID)) > tol)
    //                fail("U not orthogonal");
    //
    //            final Matrix V_Vtranspose = V.transpose().mul(V);
    //            ID = new Identity(V_Vtranspose.cols());
    //            if (norm(V_Vtranspose.sub(ID)) > tol)
    //                fail("V not orthogonal");
    //
    //            final Matrix A_reconstructed = U.mul(S).mul(V.transpose());
    //            if (norm(A_reconstructed.sub(A)) > tol)
    //                fail("Product does not recover A");
    //        }
    //    }


    @Test
    public void testQRDecomposition() {
        testQRDecomposition(jFlags);
        testQRDecomposition(fFlags);
    }

    private void testQRDecomposition(final Set<Address.Flags> flags) {


        final Matrix M1 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.4 },
              { 0.7,  0.4,  1.0 }
        }, flags);

        final Matrix M2 = new Matrix(new double[][] {
              { 1.0,  0.9,  0.7 },
              { 0.9,  1.0,  0.3 },
              { 0.7,  0.3,  1.0 }
        }, flags);

        final Matrix I = new Identity(3, flags);

        final Matrix M3 = new Matrix(new double[][] {
              { 1,   2,   3,   4 },
              { 2,   0,   2,   1 },
              { 0,   1,   0,   0 }
        }, flags);

        final Matrix M4 = new Matrix(new double[][] {
              {  1,   2,   400    },
              {  2,   0,     1    },
              { 30,   2,     0    },
              {  2,   0,     1.05 }
        }, flags);

        // from Higham - nearest correlation matrix
        final Matrix M5 = new Matrix(new double[][] {
              {  2,   -1,     0,    0 },
              { -1,    2,    -1,    0 },
              {  0,   -1,     2,   -1 },
              {  0,    0,    -1,    2 },
        }, flags);

//        // from Higham - nearest correlation matrix to M5
//        Matrix M6 = new Matrix(new double[][] {
//              {  1,              -0.8084124981,    0.1915875019,    0.106775049  },
//              { -0.8084124981,    1,              -0.6562326948,    0.1915875019 },
//              {  0.1915875019,   -0.6562326948,    1,              -0.8084124981 },
//              {  0.106775049,     0.1915875019,   -0.8084124981,    1            }
//        });
//
//        Matrix M7 = M1.clone();
//        M7.set(0, 1, 0.3); M7.set(0, 2, 0.2); M7.set(2, 1, 1.2);


        QL.info("Testing QR decomposition...");

        final double tolerance = 1.0e-12;

        final Matrix testMatrices[] = { M1, M2, I, M3, M3.transpose(), M4, M4.transpose(), M5 };

        for (final Matrix A : testMatrices) {

            QRDecomposition qr;
            Matrix Q;
            Matrix R;
            final Matrix P;
            final Matrix mT;
            Matrix mul1;
            final Matrix mul2;
            double norm;

            // System.out.println("///////////////////////////////////////////////");

            // System.out.println("Matrix A = "+A.toString());

            // System.out.println("// QR decomposition with column pivoting");
            qr = new Matrix(A).qr(true);
            R = qr.R();
            Q = qr.Q();
            P = qr.P();

            // norm(Q*R - A*P)
            mul1 = Q.mul(R);
            mul2 = A.mul(P);
            norm = norm( mul1.sub(mul2) );
            if (norm > tolerance) {
                fail("Q*R (pivot=true) does not match matrix A*P :: norm = "+String.valueOf(norm));
            }



            // System.out.println("// QR decomposition without column pivoting");
            qr = new Matrix(A).qr();
            // norm(Q*R - A)
            R = qr.R();
            Q = qr.Q();

            mul1 = Q.mul(R);
            norm = norm(mul1.sub(A));
            if (norm > tolerance) {
                fail("Q*R (pivot=false) does not match matrix A :: norm = "+String.valueOf(norm));
            }


        }
    }

    //    @Test
    //    public void testQRSolve() {
    //
    //        QL.info("Testing QR solve...");
    //
    //        final double tol = 1.0e-12;
    //        final MersenneTwisterUniformRng rng = new MersenneTwisterUniformRng(1234);
    //        final Matrix bigM = new Matrix(50, 100);
    //        for (int i=0; i < Math.min(bigM.rows(), bigM.cols()); i++) {
    //            bigM.set(i, i, i+1.0);
    //        }
    //
    ////        final Matrix testMatrices[] = { M1, M2, M3, M3.transpose(), M4, M4.transpose(), M5, I, M7, bigM, bigM.transpose() };
    //
    //      final Matrix T1 = new Matrix(new double[][] {
    //              {1, 1, -1},
    //              {1, 2,  1},
    //              {1, 2, -1}
    //      });
    //      final Matrix testMatrices[] = { T1 };
    //
    //
    //
    //        for (final Matrix A : testMatrices) {
    //            final Array b = new Array(A.rows());
    //
    //            for (int k=0; k < 10; k++) {
    //                for (final Iterator it = b.iterator(); it.hasNext(); ) {
    //                    it.forward();
    //                    final double value = rng.next().value();
    //                    it.set(value);
    //                }
    //
    //                final QRDecomposition qr = A.qr(true);
    //                final Array x = qr.solve(b);
    //                final Matrix QT = qr.QT();
    //                final Matrix Q  = qr.Q();
    //                final Matrix R  = qr.R();
    //                final Matrix H  = qr.H();
    //                final Matrix P  = qr.P();
    //
    //                if (A.cols() >= A.rows()) {
    //                    final double t = norm(A.mul(x).sub(b));
    //                    if (t > tol)
    //                        fail("A*x does not match vector b");
    //                } else {
    //                    // use the SVD to calculate the reference values
    //                    final int n = A.cols();
    //                    final Array xr = new Array(n);
    //
    //                    final SVD svd = A.svd();
    //                    final Matrix V = svd.V();
    //                    final Matrix U = svd.U();
    //                    final Array  w = svd.singularValues();
    //                    final double threshold = n*Constants.QL_EPSILON;
    //
    //                    for (int i=0; i<n; ++i) {
    //                        if (w.get(i) > threshold) {
    //                            // final double u = std::inner_product(U.column_begin(i), U.column_end(i), b.begin(), 0.0) / w[i];
    //                            final double u = U.constColumnIterator(i).innerProduct(b.constIterator()) / w.get(i);
    //                            for (int z=0; z<n; z++) {
    //                                //-- xr[z]  +=u*V[z][i];
    //                                final double value = xr.get(z) + u * V.get(z, i);
    //                                xr.set(z, value);
    //                            }
    //                        }
    //                    }
    //
    //                    final double t = norm(xr.sub(x));
    //                    if (t > tol) {
    //                        fail("least square solution does not match");
    //
    //                    }
    //                }
    //            }
    //        }
    //    }


    //    @Test
    //    public void testDeterminant() {
    //
    //        fail("not implemented yet");
    //
    //        BOOST_MESSAGE("Testing LU determinant calculation...");
    //
    //        setup();
    //        final Real tol = 1e-10;
    //
    //        final Matrix testMatrices[] = {M1, M2, M5, M6, I};
    //        // expected results calculated with octave
    //        final Real expected[] = { 0.044, -0.012, 5.0, 5.7621e-11, 1.0};
    //
    //        for (final Size i=0; i < LENGTH(testMatrices); ++i) {
    //            const final Real calculated = determinant(testMatrices[i]);
    //            if (std::fabs(expected[i] - calculated) > tol)
    //                BOOST_FAIL("determinant calculation failed "
    //                           << "\n matrix     :\n" << testMatrices[i]
    //                           << "\n calculated : " << calculated
    //                           << "\n expected   : " << expected[i]);
    //        }
    //
    //        MersenneTwisterUniformRng rng(1234);
    //        for (Size i=0; i < 100; ++i) {
    //            Matrix m(3,3, 0.0);
    //            for (Matrix::iterator iter = final m.begin(); iter != m.end(); ++iter)
    //                *iter = rng.next().value;
    //
    //            if (!(i%3)) {
    //                // every third matrix is a singular matrix
    //                Size row = Size(3*rng.next().value);
    //                std::fill(m.row_begin(row), m.row_end(row), 0.0);
    //            }
    //
    //            const Real& a=m[0][0];
    //            const Real& b=m[0][1];
    //            const Real& c=m[0][2];
    //            const Real& d=m[1][0];
    //            const Real& e=m[1][1];
    //            const Real& f=m[1][2];
    //            const Real& g=m[2][0];
    //            const Real& h=m[2][1];
    //            const Real& i=m[2][2];
    //
    //            const Real expected = a*e*i+b*f*g+c*d*h-(g*e*c+h*f*a+i*d*b);
    //            const Real calculated = determinant(m);
    //
    //            if (std::fabs(expected-calculated) > tol)
    //                BOOST_FAIL("determinant calculation failed "
    //                           << "\n matrix     :\n" << m
    //                           << "\n calculated : " << calculated
    //                           << "\n expected   : " << expected);
    //        }
    //    }


    //    @Test
    //    public void testOrthogonalProjection() {
    //
    //        fail("not implemented yet");
    //
    //        BOOST_MESSAGE("Testing orthogonal projections...");
    //
    //        Size dimension = 1000;
    //        Size numberVectors = 50;
    //        Real multiplier = 100;
    //        Real tolerance = 1e-6;
    //        unsigned long seed = 1;
    //
    //        Real errorAcceptable = 1E-11;
    //
    //        Matrix test(numberVectors,dimension);
    //
    //        MersenneTwisterUniformRng rng(seed);
    //
    //        for (Size i=0; i < numberVectors; ++i)
    //            for (Size j=0; j < dimension; ++j)
    //                test[i][j] = rng.next().value;
    //
    //        OrthogonalProjections projector(final test,
    //                                        multiplier,
    //                                        tolerance  );
    //
    //        Size numberFailures =0;
    //        Size failuresTwo=0;
    //
    //        for (Size i=0; i < numberVectors; ++i)
    //        {
    //            // check output vector i is orthogonal to all other vectors
    //
    //            if (projector.validVectors()[i])
    //            {
    //                for (Size j=0; j < numberVectors; ++j)
    //                      if (projector.validVectors()[j] && i != j)
    //                      {
    //                          Real dotProduct=0.0;
    //                          for (Size k=0; k < dimension; ++k)
    //                              dotProduct += test[j][k]*projector.GetVector(i)[k];
    //
    //                          if (fabs(dotProduct) > errorAcceptable)
    //                              ++numberFailures;
    //
    //                      }
    //
    //               Real innerProductWithOriginal =0.0;
    //               Real normSq =0.0;
    //
    //               for (Size j=0; j < dimension; ++j)
    //               {
    //                    innerProductWithOriginal +=   projector.GetVector(i)[j]*test[i][j];
    //                    normSq += test[i][j]*test[i][j];
    //               }
    //
    //               if (fabs(innerProductWithOriginal-normSq) > errorAcceptable)
    //                   ++failuresTwo;
    // - eins
    //            }
    //
    //        }
    //
    //        if (numberFailures > 0 || failuresTwo >0)
    //            BOOST_FAIL("OrthogonalProjections test failed with " << numberFailures << " failures  of orthogonality and "
    //                        << failuresTwo << " failures of projection size.");
    //
    //    }





    // private methods
    //

    private double norm(final Array v) {
        final double result = Math.sqrt(v.dotProduct(v));
        return result;
    }

    private double norm(final Matrix m) {
        double sum = 0.0;
        for (int i=0; i<m.rows(); i++) {
            for (int j=0; j<m.cols(); j++) {
                sum += m.get(i, j) * m.get(i, j);
            }
        }

        final double result = Math.sqrt(sum);
        return result;
    }

}
