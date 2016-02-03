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

import junit.framework.Assert;

import org.jquantlib.QL;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.Ops;
import org.jquantlib.math.functions.GreaterThanPredicate;
import org.jquantlib.math.functions.Square;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.math.matrixutilities.internal.Address;
import org.junit.Test;


/**
 *
 * @author Richard Gomes
 */
public class ArrayTest {

    private final Set<Address.Flags> jFlags;
    private final Set<Address.Flags> fFlags;


    public ArrayTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        this.jFlags = EnumSet.noneOf(Address.Flags.class);
        this.fFlags = EnumSet.of(Address.Flags.FORTRAN);
    }

    private Array augmented(final Array array) {
        final int begin = array.begin();
        final int end = array.end();
        final Array result = new Array(array.size()+2, array.flags());
        result.set(begin, Math.random());
        for (int i=begin, j=1+begin; i<end; i++,j++) {
            result.set(j, array.get(i));
        }
        result.set(end+1, Math.random());
        // System.out.println(result);
        return result;
    }

    private Array range(final Array array) {
        final int begin = array.begin();
        final int end = array.end();
        return array.range(begin+1, end-1 );
    }

    public static boolean equals(final Array a, final Array b) {
        if (a.size() != b.size())
            return false;
        final int offsetA = a.flags().contains(Address.Flags.FORTRAN) ? 1 : 0;
        final int offsetB = b.flags().contains(Address.Flags.FORTRAN) ? 1 : 0;
        for (int i=0; i<a.size(); i++) {
            if (a.get(i+offsetA) != b.get(i+offsetB))
                return false;
        }
        return true;
    }


    @Test
    public void testToString() {
        testToString(jFlags);
        testToString(fFlags);
    }

    private void testToString(final Set<Address.Flags> flags) {
        final Array a = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flags);
        testToString(a);
        testToString(range(augmented(a)));
        testToString(range(augmented(range(augmented(a)))));
    }

    private void testToString(final Array a) {
        final String result = a.toString();
        System.out.println(result);
    }


    @Test
    public void toFortran() {
        toFortran(jFlags);
        toFortran(fFlags);
    }

    private void toFortran(final Set<Address.Flags> flags) {
        final Array a = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flags).toFortran();
        toFortran(a);
        toFortran(range(augmented(a)));
        toFortran(range(augmented(range(augmented(a)))));
    }

    private void toFortran(final Array a) {
        final Array result = a.toFortran();
        System.out.println(result);
    }


    @Test
    public void toJava() {
        toJava(jFlags);
        toJava(fFlags);
    }

    private void toJava(final Set<Address.Flags> flags) {
        final Array a = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flags).toJava();
        toJava(a);
        toJava(range(augmented(a)));
        toJava(range(augmented(range(augmented(a)))));
    }

    private void toJava(final Array a) {
        final Array result = a.toJava();
        System.out.println(result);
    }


    @Test
    public void iterator() {
        iterator(jFlags, jFlags);
        iterator(jFlags, fFlags);
        iterator(fFlags, jFlags);
        iterator(fFlags, fFlags);
    }

    private void iterator(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 1.0, -2.0, -3.0, 5.0, -9.0, -11.0, -12.0 }, flagsA);
        final Array aB = new Array(new double[] { 1.0, -2.0, -3.0, 5.0, -9.0, -11.0, -12.0 }, flagsB);
        iterator(aA, aB);
        iterator(range(augmented(aA)), range(augmented(aB)));
        iterator(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void iterator(final Array aA, final Array aB) {
        int pos = aB.begin();
        for (final double d : aA) {
            Assert.assertTrue("iterator failed", d == aB.get(pos));
            pos++;
        }
    }




    @Test
    public void testClone() {
        testClone(jFlags, jFlags);
        testClone(jFlags, fFlags);
        testClone(fFlags, jFlags);
        testClone(fFlags, fFlags);
    }

    private void testClone(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flagsA);
        final Array aB = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flagsB);
        testClone(aA, aB);
        testClone(range(augmented(aA)), range(augmented(aB)));
        testClone(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void testClone(final Array aA, final Array aB) {
        final Array result = aA.clone();
        if (result == aA) {
            fail("'clone' must return a new instance");
        }
        if (result == aB) {
            fail("'clone' must return a new instance");
        }
        if (!equals(result, aB)) {
            fail("'clone' failed");
        }
    }


    @Test
    public void abs() {
        abs(jFlags, jFlags);
        abs(jFlags, fFlags);
        abs(fFlags, jFlags);
        abs(fFlags, fFlags);
    }

    private void abs(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 1.0, -2.0, -3.0, 5.0, -9.0, -11.0, -12.0 }, flagsA);
        final Array aB = new Array(new double[] { 1.0,  2.0,  3.0, 5.0,  9.0,  11.0,  12.0 }, flagsB);
        abs(aA, aB);
        abs(range(augmented(aA)), range(augmented(aB)));
        abs(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void abs(final Array aA, final Array aB) {
        final Array result = aA.abs();
        if (result == aA) {
            fail("'abs' must return a new instance");
        }
        if (result == aB) {
            fail("'abs' must return a new instance");
        }
        if (!equals(result, aB)) {
            fail("'abs' failed");
        }
    }


    @Test
    public void accumulate() {
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0 });
        accumulate(aA);
        accumulate(range(augmented(aA)));
        accumulate(range(augmented(range(augmented(aA)))));
    }

    private void accumulate(final Array aA) {
        if (aA.accumulate() != 45.0) {
            fail("'accumulate' failed");
        }
        if (aA.accumulate(2, 5, -2.0) != 10.0) {
            fail("'accumulate' failed");
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
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flagsA);
        final Array aB = new Array(new double[] { 4.0, 3.0, 2.0, 1.0 }, flagsB);
        add(aA, aB);
        add(range(augmented(aA)), range(augmented(aB)));
        add(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void add(final Array aA, final Array aB) {
        final Array a = aA.add(aB);
        if (a == aA) {
            fail("'add' must return a new instance");
        }
        if (a.size() != aA.size()) {
            fail("'add' failed");
        }

        for (int i=0; i<a.size(); i++) {
            if (a.get(i) != 5) {
                fail("'add' failed");
            }
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
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flagsA);
        final Array aB = new Array(new double[] { 4.0, 3.0, 2.0, 1.0 }, flagsB);
        addAssign(aA, aB);
        addAssign(range(augmented(aA)), range(augmented(aB)));
        addAssign(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void addAssign(final Array aA, final Array aB) {
        final Array clone = aA.clone();
        final Array a = clone.addAssign(aB);
        if (a != clone) {
            fail("addAssign must return <this>");
        }

        final int begin = a.begin();
        final int end = a.end();
        for (int i=begin; i<end; i++) {
            if (a.get(i) != 5) {
                fail("'addAssign' failed");
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
        final Array aA = new Array(new double[] { 9.0, 8.0, 7.0, 6.0 }, flagsA);
        final Array aB = new Array(new double[] { 4.0, 3.0, 2.0, 1.0 }, flagsB);
        sub(aA, aB);
        sub(range(augmented(aA)), range(augmented(aB)));
        sub(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void sub(final Array aA, final Array aB) {
        final Array a = aA.sub(aB);
        if (a == aA) {
            fail("'sub' must return a new instance");
        }
        if (a.size() != aA.size()) {
            fail("'sub' failed");
        }

        for (int i=0; i < a.size(); i++) {
            if (a.get(i) != 5) {
                fail("'sub' failed");
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
        final Array aA = new Array(new double[] { 9.0, 8.0, 7.0, 6.0 }, flagsA);
        final Array aB = new Array(new double[] { 4.0, 3.0, 2.0, 1.0 }, flagsB);
        subAssign(aA, aB);
        subAssign(range(augmented(aA)), range(augmented(aB)));
        subAssign(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void subAssign(final Array aA, final Array aB) {
        final Array clone = aA.clone();
        final Array a = clone.subAssign(aB);
        if (a != clone) {
            fail("subAssign must return <this>");
        }
        if (a.size() != aA.size()) {
            fail("'subAssign' failed");
        }

        final int begin = a.begin();
        final int end = a.end();
        for (int i=begin; i<end; i++) {
            if (a.get(i) != 5) {
                fail("'subAssign' failed");
            }
        }
    }


    @Test
    public void mul() {
        mul(jFlags, jFlags);
        mul(jFlags, fFlags);
        mul(fFlags, jFlags);
        mul(fFlags, fFlags);
    }

    private void mul(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 200.0, 100.0, 250.0, 500.0 }, flagsA);
        final Array aB = new Array(new double[] {   5.0,  10.0,   4.0,   2.0 }, flagsB);
        mul(aA, aB);
        mul(range(augmented(aA)), range(augmented(aB)));
        mul(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void mul(final Array aA, final Array aB) {
        final Array clone = aA.clone();
        final Array a1 = clone.mul(aB);
        if (a1 == clone) {
            fail("'mul' must return a new instance");
        }
        if (a1.size() != aA.size()) {
            fail("'mul' failed");
        }

        for (int i=a1.begin(); i<a1.end(); i++) {
            if (a1.get(i) != 1000) {
                fail("'mul' failed");
            }
        }

        // array multiplied by Matrix

        final Matrix mB = new Matrix(new double[][] {
                { 1.0,  2.0,  0.0 },
                { 2.0,  1.0,  0.0 },
                { 2.0,  1.0,  1.0 },
                { 1.0,  2.0,  0.0 }
        });
        final Array aB2 = new Array(new double[] { 1400.0,  1750.0,   250.0 });

        final Array a2 = clone.mul(mB);

        if (a2 == clone) {
            fail("'mul' must return a new instance");
        }
        if (a2.size() != mB.cols()) {
            fail("'mul' failed");
        }

        for (int i=a2.begin(); i<a2.end(); i++) {
            final double elem = aB2.get(i);
            if (a2.get(i) != elem) {
                fail("'mul' failed");
            }
        }
    }


    @Test
    public void mulAssign() {
        mulAssign(jFlags, jFlags);
        mulAssign(jFlags, fFlags);
        mulAssign(fFlags, jFlags);
        mulAssign(fFlags, fFlags);
    }

    private void mulAssign(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 200.0, 100.0, 250.0, 500.0 }, flagsA);
        final Array aB = new Array(new double[] {   5.0,  10.0,   4.0,   2.0 }, flagsB);
        mulAssign(aA, aB);
        mulAssign(range(augmented(aA)), range(augmented(aB)));
        mulAssign(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void mulAssign(final Array aA, final Array aB) {
        final Array clone = aA.clone();
        final Array a = clone.mulAssign(aB);
        if (a != clone) {
            fail("mulAssign must return <this>");
        }
        if (a.size() != aA.size()) {
            fail("'mulAssign' failed");
        }

        final int begin = a.begin();
        final int end = a.end();
        for (int i=begin; i<end; i++) {
            if (a.get(i) != 1000) {
                fail("'mulAssign' failed");
            }
        }
    }


    @Test
    public void div() {
        div(jFlags, jFlags);
        div(jFlags, fFlags);
        div(fFlags, jFlags);
        div(fFlags, fFlags);
    }

    private void div(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 20.0, 18.0, 16.0, 14.0 }, flagsA);
        final Array aB = new Array(new double[] { 10.0,  9.0,  8.0,  7.0 }, flagsB);
        div(aA, aB);
        div(range(augmented(aA)), range(augmented(aB)));
    }

    private void div(final Array aA, final Array aB) {
        final Array clone = aA.clone();
        final Array a = clone.div(aB);
        if (a == clone) {
            fail("'div' must return a new instance");
        }
        if (a.size() != aA.size()) {
            fail("'div' failed");
        }

        for (int i=0; i<a.size(); i++) {
            if (a.get(i) != 2) {
                fail("'div' failed");
            }
        }
    }


    @Test
    public void divAssign() {
        divAssign(jFlags, jFlags);
        divAssign(jFlags, fFlags);
        divAssign(fFlags, jFlags);
        divAssign(fFlags, fFlags);
    }

    private void divAssign(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 20.0, 18.0, 16.0, 14.0 }, flagsA);
        final Array aB = new Array(new double[] { 10.0,  9.0,  8.0,  7.0 }, flagsB);
        divAssign(aA, aB);
        divAssign(range(augmented(aA)), range(augmented(aB)));
        divAssign(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void divAssign(final Array aA, final Array aB) {
        final Array clone = aA.clone();
        final Array a = clone.divAssign(aB);
        if (a != clone) {
            fail("divAssign must return <this>");
        }
        if (a.size() != aA.size()) {
            fail("'divAssign' failed");
        }

        final int begin = a.begin();
        final int end = a.end();
        for (int i=begin; i<end; i++) {
            if (!Closeness.isClose(a.get(i), 2.0)) {
                fail("'divAssign' failed");
            }
        }
    }


    @Test
    public void dotProduct() {
        dotProduct(jFlags, jFlags);
        dotProduct(jFlags, fFlags);
        dotProduct(fFlags, jFlags);
        dotProduct(fFlags, fFlags);
    }

    private void dotProduct(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 2.0, 1.0, -2.0, 3.0 }, flagsA);
        final Array aB = new Array(new double[] { 3.0, 4.0,  5.0, 1.0 }, flagsB);
        dotProduct(aA, aB);
        dotProduct(range(augmented(aA)), range(augmented(aB)));
        dotProduct(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void dotProduct(final Array aA, final Array aB) {
        if (aA.dotProduct(aB) != 3) {
            fail("'dotProduct' failed");
        }
    }


    @Test
    public void innerProduct() {
        // when working with real numbers, both dotProduct and innerProduct give the same results
        dotProduct();
    }


    @Test
    public void outerProduct() {
        outerProduct(jFlags, jFlags);
        outerProduct(jFlags, fFlags);
        outerProduct(fFlags, jFlags);
        outerProduct(fFlags, fFlags);
    }

    private void outerProduct(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 2.0, 1.0, -2.0, }, flagsA);
        final Array aB = new Array(new double[] { 3.0, 4.0,  5.0, 1.0 }, flagsB);
        outerProduct(aA, aB);
        outerProduct(range(augmented(aA)), range(augmented(aB)));
        outerProduct(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void outerProduct(final Array aA, final Array aB) {
        final Matrix mC = new Matrix( new double[][] {
                {  6.0,  8.0,  10.0,  2.0 },
                {  3.0,  4.0,   5.0,  1.0 },
                { -6.0, -8.0, -10.0, -2.0 }
        });

        final Matrix m = aA.outerProduct(aB);
        if (!MatrixTest.equals(m, mC)) {
            fail("'outerProduct' failed");
        }
    }


    @Test
    public void transform() {
        transform(jFlags, jFlags);
        transform(jFlags, fFlags);
        transform(fFlags, jFlags);
        transform(fFlags, fFlags);
    }

    private void transform(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] {  5.0, 2.0, 3.0,  4.0 }, flagsA);
        final Array aB = new Array(new double[] { 25.0, 4.0, 9.0, 16.0 }, flagsB);
        transform(aA, aB);
        transform(range(augmented(aA)), range(augmented(aB)));
        transform(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void transform(final Array aA, final Array aB) {
        final Array tmp1 = aA.clone();
        Array result = tmp1.transform(new Square());
        if (result != tmp1) {
            fail("'transform' must return this");
        }
        if (!equals(result, aB)) {
            fail("'transform' failed");
        }

        final Array aC = new Array(new double[] { 5.0, 4.0, 9.0,  4.0 });

        final Array tmp2 = aA.clone();
        final int offset = aA.begin();
        result = tmp2.transform(1+offset, 3+offset, new Square());
        if (result != tmp2) {
            fail("'transform' must return this");
        }
        if (!equals(result, aC)) {
            fail("'transform' failed");
        }
    }


    /**
     * @see <a href="http://gcc.gnu.org/viewcvs/trunk/libstdc%2B%2B-v3/testsuite/25_algorithms/lower_bound/">lower_bound test cases</a>
     */
    @Test
    //TODO: test range(augmented(...))
    public void lowerBound_Case1() {
        lowerBound_Case1(jFlags);
        lowerBound_Case1(fFlags);
    }

    private void lowerBound_Case1(final Set<Address.Flags> flags) {

        final String MESSAGE = "lowerBound Case 1 failed";

        final double array[] = {0, 0, 0, 0, 1, 1, 1, 1};

        for (int i = 0; i < 5; ++i) {
            for (int j = 4; j < 7; ++j) {
                final double tmp[] = new double[j-i +1];
                System.arraycopy(array, i, tmp, 0, j-i+1);
                final Array con = new Array(tmp, flags);
                final int offset = con.begin();

                final int pos = con.lowerBound(1);
                if (pos != 4 - i + offset) {
                    fail(MESSAGE);
                }
            }
        }
    }


    /**
     * @see <a href="http://gcc.gnu.org/viewcvs/trunk/libstdc%2B%2B-v3/testsuite/25_algorithms/lower_bound/">lower_bound test cases</a>
     */
    @Test
    //TODO: test range(augmented(...))
    public void lowerBound_Case2() {
        lowerBound_Case2(jFlags, jFlags);
        lowerBound_Case2(jFlags, fFlags);
        lowerBound_Case2(fFlags, jFlags);
        lowerBound_Case2(fFlags, fFlags);
    }

    private void lowerBound_Case2(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array A = new Array(new double[] { 1, 2, 3, 3, 3, 5, 8 }, flagsA);
        final Array C = new Array(new double[] { 8, 5, 3, 3, 3, 2, 1 }, flagsB);
        lowerBound_Case2(A, C);
        lowerBound_Case2(range(augmented(A)), range(augmented(C)));
    }

    private void lowerBound_Case2(final Array A, final Array C) {

        final String MESSAGE = "lowerBound Case 2 failed";

        final int N = A.size();

            final double first = A.first();
            final double last = A.last();
            final int offsetA = A.begin();
            int pos;

            pos = A.lowerBound(3);
            assertTrue(MESSAGE, pos==2+offsetA);

            pos = A.lowerBound(first);
            assertTrue(MESSAGE, pos==0+offsetA);

            pos = A.lowerBound(last);
            assertTrue(MESSAGE, pos==N-1+offsetA);

            pos = A.lowerBound(4);
            assertTrue(MESSAGE, pos==5+offsetA);

            final Ops.BinaryDoublePredicate gt = new GreaterThanPredicate();
            final int offsetC = C.begin();

            pos = C.lowerBound(3, gt);
            assertTrue(MESSAGE, pos==2+offsetC);

            pos = C.lowerBound(first, gt);
            assertTrue(MESSAGE, pos==N-1+offsetC);

            pos = C.lowerBound(last, gt);
            assertTrue(MESSAGE, pos==0+offsetC);

            pos = C.lowerBound(4, gt);
            assertTrue(MESSAGE, pos==2+offsetC);
    }



    /**
     * @see <a href="http://gcc.gnu.org/viewcvs/trunk/libstdc%2B%2B-v3/testsuite/25_algorithms/upper_bound/">upper_bound test cases</a>
     */
    @Test
    //TODO: test range(augmented(...))
    public void upperBound_Case1() {
        upperBound_Case1(jFlags);
        upperBound_Case1(fFlags);
    }

    private void upperBound_Case1(final Set<Address.Flags> flags) {

        final String MESSAGE = "upperBound Case 1 failed";

        final double array[] = {0, 0, 0, 0, 1, 1, 1, 1};

        for (int i = 0; i < 5; ++i) {
            for (int j = 4; j < 7; ++j) {
                final double tmp[] = new double[j-i +1];
                System.arraycopy(array, i, tmp, 0, j-i+1);
                final Array con = new Array(tmp, flags);
                final int offset = con.begin();

                final int pos = con.upperBound(0);
                if (pos != 4 - i + offset) {
                    fail(MESSAGE);
                }
            }
        }
    }


    /**
     * @see <a href="http://gcc.gnu.org/viewcvs/trunk/libstdc%2B%2B-v3/testsuite/25_algorithms/upper_bound/">upper_bound test cases</a>
     */
    @Test
    //TODO: test range(augmented(...))
    public void upperBound_Case2() {
        upperBound_Case2(jFlags, jFlags);
        upperBound_Case2(jFlags, fFlags);
        upperBound_Case2(fFlags, jFlags);
        upperBound_Case2(fFlags, fFlags);
    }

    private void upperBound_Case2(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array A = new Array(new double[]{1, 2, 3, 3, 3, 5, 8}, flagsA);
        final Array C = new Array(new double[]{8, 5, 3, 3, 3, 2, 1}, flagsB);
        upperBound_Case2(A, C);
        upperBound_Case2(range(augmented(A)), range(augmented(C)));
    }

    private void upperBound_Case2(final Array A, final Array C) {

        final String MESSAGE = "upperBound Case 2 failed";

        final int N = A.size();


            final double first = A.first();
            final double  last = A.last();
            final int offsetA = A.begin();

            int pos;

            pos = A.upperBound(3);
            assertTrue(MESSAGE, pos==5+offsetA);

            pos = A.upperBound(first);
            assertTrue(MESSAGE, pos==1+offsetA);

            pos = A.upperBound(last);
            assertTrue(MESSAGE, pos==N+offsetA);

            pos = A.upperBound(4);
            assertTrue(MESSAGE, pos==5+offsetA);

            final Ops.BinaryDoublePredicate gt = new GreaterThanPredicate();
            final int offsetC = C.begin();

            pos = C.upperBound(3, gt);
            assertTrue(MESSAGE, pos==5+offsetC);

            pos = C.upperBound(first, gt);
            assertTrue(MESSAGE, pos==N+offsetC);

            pos = C.upperBound(last, gt);
            assertTrue(MESSAGE, pos==1+offsetC);

            pos = C.upperBound(4, gt);
            assertTrue(MESSAGE, pos==2+offsetC);
    }


    @Test
    public void adjacentDifference() {
        adjacentDifference(jFlags, jFlags);
        adjacentDifference(jFlags, fFlags);
        adjacentDifference(fFlags, jFlags);
        adjacentDifference(fFlags, fFlags);
    }

    private void adjacentDifference(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 5.0, 9.0, 11.0, 12.0 }, flagsA);
        final Array aB = new Array(new double[] { 1.0, 1.0, 1.0, 2.0, 4.0,  2.0,  1.0 }, flagsB);
        adjacentDifference(aA, aB);
        adjacentDifference(range(augmented(aA)), range(augmented(aB)));
        adjacentDifference(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }


    private void adjacentDifference(final Array aA, final Array aB) {
        final Array result = aA.adjacentDifference();
        if (result == aA) {
            fail("'adjacentDifferences' must return a new instance");
        }
        if (result == aB) {
            fail("'adjacentDifferences' must return a new instance");
        }
        if (!equals(result, aB)) {
            fail("'adjacentDifferences' failed");
        }
    }


    @Test
    public void exp() {
        exp(jFlags, jFlags);
        exp(jFlags, fFlags);
        exp(fFlags, jFlags);
        exp(fFlags, fFlags);
    }

    private void exp(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flagsA);
        final Array aB = new Array(new double[] { Math.exp(1), Math.exp(2), Math.exp(3), Math.exp(4) }, flagsB);
        exp(aA, aB);
        exp(range(augmented(aA)), range(augmented(aB)));
        exp(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void exp(final Array aA, final Array aB) {
        final Array result = aA.exp();
        if (result == aA) {
            fail("'exp' must return a new instance");
        }
        if (result == aB) {
            fail("'exp' must return a new instance");
        }
        if (!equals(result, aB)) {
            fail("'exp' failed");
        }
    }


    @Test
    public void fillScalar() {
        fillScalar(jFlags, jFlags);
        fillScalar(jFlags, fFlags);
        fillScalar(fFlags, jFlags);
        fillScalar(fFlags, fFlags);
    }

    private void fillScalar(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(4, flagsA);
        final Array aB = new Array(new double[] { 2.0, 2.0, 2.0, 2.0 }, flagsB);
        fillScalar(aA, aB);
        fillScalar(range(augmented(aA)), range(augmented(aB)));
        fillScalar(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void fillScalar(final Array aA, final Array aB) {
        aA.fill(2.0);
        if (!equals(aA, aB)) {
            fail("'fill' failed");
        }
    }


    @Test
    public void fillArray() {
        fillArray(jFlags, jFlags);
        fillArray(jFlags, fFlags);
        fillArray(fFlags, jFlags);
        fillArray(fFlags, fFlags);
    }

    private void fillArray(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(4, flagsA);
        final Array aB = new Array(new double[] { 2.0, 2.0, 2.0, 2.0 }, flagsB);
        fillArray(aA, aB);
        fillArray(range(augmented(aA)), range(augmented(aB)));
        fillArray(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void fillArray(final Array aA, final Array aB) {
        aA.fill(aB);
        if (!equals(aA, aB)) {
            fail("'fill' failed");
        }
    }






    @Test
    public void first() {
        first(jFlags);
        first(fFlags);
    }

    private void first(final Set<Address.Flags> flags) {
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flags);
        first(aA);
        first(range(augmented(aA)));
        first(range(augmented(range(augmented(aA)))));
    }

    private void first(final Array aA) {
        if (aA.first() != 1.0) {
            fail("'first' failed");
        }
    }

    @Test
    public void last() {
        last(jFlags);
        last(fFlags);
    }

    private void last(final Set<Address.Flags> flags) {
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flags);
        last(aA);
        last(range(augmented(aA)));
        last(range(augmented(range(augmented(aA)))));
    }

    private void last(final Array aA) {
        if (aA.last() != 4.0) {
            fail("'last' failed");
        }
    }


    @Test
    public void log() {
        log(jFlags, jFlags);
        log(jFlags, fFlags);
        log(fFlags, jFlags);
        log(fFlags, fFlags);
    }

    private void log(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { Math.exp(1), Math.exp(2), Math.exp(3), Math.exp(4) }, flagsA);
        final Array aB = new Array(new double[] { 1.0,  2.0,   3.0,    4.0 }, flagsB);
        log(aA, aB);
        log(range(augmented(aA)), range(augmented(aB)));
        log(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void log(final Array aA, final Array aB) {
        final Array result = aA.log();
        if (result == aA) {
            fail("'log' must return a new instance");
        }
        if (result == aB) {
            fail("'log' must return a new instance");
        }
        if (!equals(result, aB)) {
            fail("'log' failed");
        }
    }

    @Test
    public void min() {
        min(jFlags);
        min(fFlags);
    }

    private void min(final Set<Address.Flags> flags) {
        final Array aA = new Array(new double[] { 0.0, 1.0, 2.0, -3.0, 4.0, 0.0, -6.0, 7.0, 8.0, 0.0 }, flags);
        min(aA);
        min(range(augmented(aA)));
        min(range(augmented(range(augmented(aA)))));
    }

    private void min(final Array aA) {
        if (aA.min() != -6.0) {
            fail("'min' failed");
        }
        if (aA.min(3, 6) != -3.0) {
            fail("'min' failed");
        }
    }


    @Test
    public void max() {
        max(jFlags);
        max(fFlags);
    }

    private void max(final Set<Address.Flags> flags) {
        final Array aA = new Array(new double[] { 0.0, 1.0, 2.0, -3.0, 4.0, 0.0, -6.0, 7.0, 8.0, 0.0 }, flags);
        max(aA);
        max(range(augmented(aA)));
        max(range(augmented(range(augmented(aA)))));
    }

    private void max(final Array aA) {
        if (aA.max() != 8.0) {
            fail("'max' failed");
        }
        if (aA.max(2, 6) != 4.0) {
            fail("'max' failed");
        }
    }


    @Test
    public void sort() {
        sort(jFlags, jFlags);
        sort(jFlags, fFlags);
        sort(fFlags, jFlags);
        sort(fFlags, fFlags);
    }

    private void sort(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 9.0, 8.0, 2.0, 3.0, 1.0, 4.0, 8.0, 9.0 }, flagsA);
        final Array aB = new Array(new double[] { 1.0, 2.0, 3.0, 4.0, 8.0, 8.0, 9.0, 9.0 }, flagsB);
        sort(aA, aB);
        sort(range(augmented(aA)), range(augmented(aB)));
        sort(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void sort(final Array aA, final Array aB) {
        final Array result = aA.sort();
        if (result != aA) {
            fail("'sort' must return <this>");
        }
        if (!equals(result, aB)) {
            fail("'sort' failed");
        }
    }


    @Test
    public void sqrt() {
        sqrt(jFlags, jFlags);
        sqrt(jFlags, fFlags);
        sqrt(fFlags, jFlags);
        sqrt(fFlags, fFlags);
    }

    private void sqrt(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 1.0, 4.0, 9.0, 16.0 }, flagsA);
        final Array aB = new Array(new double[] { 1.0, 2.0, 3.0,  4.0 }, flagsB);
        sqrt(aA, aB);
        sqrt(range(augmented(aA)), range(augmented(aB)));
        sqrt(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void sqrt(final Array aA, final Array aB) {
        final Array result = aA.sqrt();
        if (result == aA) {
            fail("'sqrt' must return a new instance");
        }
        if (result == aB) {
            fail("'sqrt' must return a new instance");
        }
        if (!equals(result, aB)) {
            fail("'sqrt' failed");
        }
    }


    @Test
    public void sqr() {
        sqr(jFlags, jFlags);
        sqr(jFlags, fFlags);
        sqr(fFlags, jFlags);
        sqr(fFlags, fFlags);
    }

    private void sqr(final Set<Address.Flags> flagsA, final Set<Address.Flags> flagsB) {
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0,  4.0 }, flagsA);
        final Array aB = new Array(new double[] { 1.0, 4.0, 9.0, 16.0 }, flagsB);
        sqr(aA, aB);
        sqr(range(augmented(aA)), range(augmented(aB)));
        sqr(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void sqr(final Array aA, final Array aB) {
        final Array result = aA.sqr();
        if (result == aA) {
            fail("'sqr' must return a new instance");
        }
        if (result == aB) {
            fail("'sqr' must return a new instance");
        }
        if (!equals(result, aB)) {
            fail("'sqr' failed");
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
        final Array aA = new Array(new double[] { 1.0, 2.0, 3.0, 4.0 }, flagsA);
        final Array aB = new Array(new double[] { 4.0, 3.0, 2.0, 1.0 }, flagsB);
        swap(aA, aB);
        swap(range(augmented(aA)), range(augmented(aB)));
        swap(range(augmented(range(augmented(aA)))), range(augmented(range(augmented(aB)))));
    }

    private void swap(final Array aA, final Array aB) {
        final Array aAclone = aA.clone();
        final Array aBclone = aB.clone();

        aA.swap(aB);
        if (!equals(aA, aBclone)) {
            fail("'swap' failed");
        }
        if (!equals(aB, aAclone)) {
            fail("'swap' failed");
        }
    }

}
