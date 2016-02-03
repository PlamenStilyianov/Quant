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

package org.jquantlib.math.matrixutilities;

import org.jquantlib.math.Ops;
import org.jquantlib.math.Ops.DoubleOp;


/**
 * This interface defines algebric operators
 *
 * @author Richard Gomes
 *
 * @param <T>
 */
public interface Algebra<T> {

    //
    //    Algebraic products :: returning 'this'
    //
    //    opr   method     right    result
    //    ----- ---------- -------- ------
    //    +     add        scalar    <T>
    //    +     add        <T>       <T>
    //    -     sub        scalar    <T>
    //    -     sub        <T>       <T>
    //    *     mul        scalar    <T>
    //    *     mul        <T>       <T>
    //    *     mul        Matrix    <T>
    //    /     div        scalar    <T>
    //    /     div        <T>       <T>
    //

    /**
     * Adds a <code>scalar</code> to every element of <code>this</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param scalar
     * @return <code>this</code>
     */
    public T addAssign(final double scalar);

    /**
     * Adds <code>this</code> instance and <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return this
     */
    public T addAssign(final T another);

    /**
     * Subtracts a <code>scalar</code> to every element of <code>this</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param scalar
     * @return <code>this</code>
     */
    public T subAssign(final double scalar);

    /**
     * Subtracts <code>this</code> instance by <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return this
     */
    public T subAssign(final T another);

    /**
     * Multiplies every element of <code>this</code> instance of <code>&lt;T&gt;</code> by a <code>scalar</code>.
     *
     * @param scalar
     * @return <code>this</code>
     */
    public T mulAssign(final double scalar);

    /**
     * Multiplies <code>this</code> instance by <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return this
     */
    public T mulAssign(final T another);

    /**
     * Divides every element of <code>this</code> instance of <code>&lt;T&gt;</code> by a <code>scalar</code>.
     *
     * @param scalar
     * @return <code>this</code>
     */
    public T divAssign(final double scalar);

    /**
     * Divides <code>this</code> instance by <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return this
     */
    public T divAssign(final T another);


    //
    //    Algebraic products :: returning a new instance
    //
    //    opr   method     right    result
    //    ----- ---------- -------- ------
    //    +     add        scalar    <T>
    //    +     add        <T>       <T>
    //    -     sub        scalar    <T>
    //    -     sub        <T>       <T>
    //    -     negative             <T>
    //    *     mul        scalar    <T>
    //    *     mul        <T>       <T>
    //    *     mul        Matrix    <T>
    //    /     div        scalar    <T>
    //    /     div        <T>       <T>
    //

    /**
     * Adds a <code>scalar</code> to every element of <code>this</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param scalar
     * @return a new instance
     */
    public T add(final double scalar);

    /**
     * Adds <code>this</code> instance and <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return a new instance
     */
    public T add(final T another);

    /**
     * Subtracts a <code>scalar</code> to every element of <code>this</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param scalar
     * @return a new instance
     */
    public T sub(final double scalar);

    /**
     * Subtracts <code>this</code> instance by <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return a new instance
     */
    public T sub(final T another);

    /**
     * Multiplies every element of <code>this</code> instance of <code>&lt;T&gt;</code> by a <code>scalar</code>.
     *
     * @param scalar
     * @return a new instance
     */
    public T mul(final double scalar);

    /**
     * Multiplies <code>this</code> instance by <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return a new instance
     */
    public T mul(final T another);

    /**
     * Divides every element of <code>this</code> instance of <code>&lt;T&gt;</code> by a <code>scalar</code>.
     *
     * @param scalar
     * @return a new instance
     */
    public T div(final double scalar);

    /**
     * Divides <code>this</code> instance by <code>another</code> instance of <code>&lt;T&gt;</code>.
     *
     * @param another
     * @return a new instance
     */
    public T div(final T another);


    /**
     * Unary negative function applied to every element of <code>this</code> instance
     *
     * @return a new instance
     */
    public T negative();


    //
    //    Vectorial operations :: return a new instance or scalar
    //
    //    method             right    result
    //    ------------------ -------- ------
    //    mul                Matrix   <T>
    //    dotProduct         <T>      double
    //    innerProduct       <T>      double
    //    outerProduct       <T>      Matrix
    //    adjacentDifference          <T>

    /**
     * Vectorial multiplication of <code>this</code> instance by a {@link Matrix}.
     *
     * @param another
     * @return a new instance
     */
    public T mul(final Matrix matrix);

    /**
     * The inner product generalizes the dot product to abstract vector spaces and is normally denoted by {@latex$ <a , b>}.
     * <p>
     * As we are working in space {@latex$ \Re} (real numbers), it's sufficient to understand that both <b>inner product</b> and
     * <b>dot operator</b> give equivalent results.
     *
     * @param another Iterator
     * @return the inner product between this Matrix and another Matrix
     *
     * @see <a href="http://en.wikipedia.org/wiki/Inner_product">Inner Product</a>
     */
    public abstract double dotProduct(final T another);

    /**
     * The inner product generalizes the dot product to abstract vector spaces and is normally denoted by {@latex$ <a , b>}.
     * <p>
     * As we are working in space {@latex$ \Re} (real numbers), it's sufficient to understand that both <b>inner product</b> and
     * <b>dot operator</b> give equivalent results.
     *
     * @param another Iterator
     * @param from is the start element, inclusive
     * @param to is the end element, exclusive
     * @return the inner product between this Matrix and another Matrix
     *
     * @see <a href="http://en.wikipedia.org/wiki/Inner_product">Inner Product</a>
     */
    public abstract double dotProduct(final T another, final int from, final int to);

    /**
     * The inner product generalizes the dot product to abstract vector spaces and is normally denoted by {@latex$ <a , b>}.
     * <p>
     * As we are working in space {@latex$ \Re} (real numbers), it's sufficient to understand that both <b>inner product</b> and
     * <b>dot operator</b> give equivalent results.
     *
     * @param another Iterator
     * @return the inner product between this Matrix and another Matrix
     *
     * @see <a href="http://en.wikipedia.org/wiki/Inner_product">Inner Product</a>
     */
    public abstract double innerProduct(final T another);

    /**
     * The inner product generalizes the dot product to abstract vector spaces and is normally denoted by {@latex$ <a , b>}.
     * <p>
     * As we are working in space {@latex$ \Re} (real numbers), it's sufficient to understand that both <b>inner product</b> and
     * <b>dot operator</b> give equivalent results.
     *
     * @param another Iterator
     * @param from is the start element, inclusive
     * @param to is the end element, exclusive
     * @return the inner product between this Matrix and another Matrix
     *
     * @see <a href="http://en.wikipedia.org/wiki/Inner_product">Inner Product</a>
     */
    public abstract double innerProduct(final T another, final int from, final int to);


    /**
     * In linear algebra, the outer product typically refers to the tensor product of two vectors. The result of applying the outer
     * product to a pair of vectors is a matrix. The name contrasts with the inner product, which takes as input a pair of vectors
     * and produces a scalar.
     * <p>
     * Given a vector {@latex$ \mathbf{u} = (u_1, u_2, \dots, u_m) } with ''m'' elements and a vector {@latex$ \mathbf{v} = (v_1,
     * v_2, \dots, v_n) } with ''n'' elements, their outer product {@latex$ \mathbf{u} \otimes \mathbf{v} } is defined as the
     * {@latex$ m \times n } matrix {@latex$ \mathbf{A} } obtained by multiplying each element of {@latex$ \mathbf{u} } by each
     * element of {@latex$ \mathbf{v} }:
     * <p>
     * {@latex[ \mathbf{u} \otimes \mathbf{v} = \mathbf{A} =
     * \begin{bmatrix}u_1v_1 & u_1v_2 & \dots & u_1v_n \\
     *                u_2v_1 & u_2v_2 & \dots & u_2v_n \\
     *                \vdots & \vdots & \ddots & \vdots\\
     *                u_mv_1 & u_mv_2 & \dots & u_mv_n \end{bmatrix} }
     * <p>
     * Note that {@latex$ \mathbf{A} \mathbf{v} = \mathbf{u} \Vert v \Vert ^2 }.
     *
     * @param another Iterator
     * @return the outer product between this Matrix and another Matrix
     *
     * @see <a href="http://en.wikipedia.org/wiki/Outer_product">Outer Product</a>
     */
    public abstract Matrix outerProduct(final T another);

    /**
     * In linear algebra, the outer product typically refers to the tensor product of two vectors. The result of applying the outer
     * product to a pair of vectors is a matrix. The name contrasts with the inner product, which takes as input a pair of vectors
     * and produces a scalar.
     * <p>
     * Given a vector {@latex$ \mathbf{u} = (u_1, u_2, \dots, u_m) } with ''m'' elements and a vector {@latex$ \mathbf{v} = (v_1,
     * v_2, \dots, v_n) } with ''n'' elements, their outer product {@latex$ \mathbf{u} \otimes \mathbf{v} } is defined as the
     * {@latex$ m \times n } matrix {@latex$ \mathbf{A} } obtained by multiplying each element of {@latex$ \mathbf{u} } by each
     * element of {@latex$ \mathbf{v} }:
     * <p>
     * {@latex[ \mathbf{u} \otimes \mathbf{v} = \mathbf{A} =
     * \begin{bmatrix}u_1v_1 & u_1v_2 & \dots & u_1v_n \\
     *                u_2v_1 & u_2v_2 & \dots & u_2v_n \\
     *                \vdots & \vdots & \ddots & \vdots\\
     *                u_mv_1 & u_mv_2 & \dots & u_mv_n \end{bmatrix} }
     * <p>
     * Note that {@latex$ \mathbf{A} \mathbf{v} = \mathbf{u} \Vert v \Vert ^2 }.
     *
     * @param another Iterator
     * @param from is the start element, inclusive
     * @param to is the end element, exclusive
     * @return the outer product between this Matrix and another Matrix
     *
     * @see <a href="http://en.wikipedia.org/wiki/Outer_product">Outer Product</a>
     */
    public abstract Matrix outerProduct(final T another, final int from, final int to);

    /**
     * Return differences between adjacent values.
     * <p>
     * Computes the difference between adjacent values in the range [first,last) using subtraction to compute differences
     * and returns the results as an Array.
     *
     * @return an array which contains the computed adjacent differences.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a00969.html#d7df62eaf265ba5c859998b1673fd427">std::adjacent_difference</a>
     */
    public T adjacentDifference();

    /**
     * Return differences between adjacent values.
     * <p>
     * Computes the difference between adjacent values in the range [first,last) using subtraction to compute differences
     * and returns the results as an Array.
     *
     * @param first is the start of range
     * @param last is the end of range
     *
     * @return an array which contains the computed adjacent differences.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a00969.html#d7df62eaf265ba5c859998b1673fd427">std::adjacent_difference</a>
     */
    public T adjacentDifference(final int from, final int to);

    /**
     * Return differences between adjacent values.
     * <p>
     * Computes the difference between adjacent values in the range [begin(),end()+1) using a function for computing the differences
     * and returns the results as an Array.
     *
     * @param f is a function
     *
     * @return an array which contains the computed adjacent differences.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a00969.html#d7df62eaf265ba5c859998b1673fd427">std::adjacent_difference</a>
     */
    public T adjacentDifference(final Ops.BinaryDoubleOp f);

    /**
     * Return differences between adjacent values.
     * <p>
     * Computes the difference between adjacent values in the range [first,last) using a function for computing the differences
     * and returns the results as an Array.
     *
     * @param first is the start of range
     * @param last is the end of range
     * @param f is a function
     *
     * @return an array which contains the computed adjacent differences.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a00969.html#d7df62eaf265ba5c859998b1673fd427">std::adjacent_difference</a>
     */
    public T adjacentDifference(final int from, final int to, final Ops.BinaryDoubleOp f);


    //
    //    Math functions
    //
    //    method          right    result
    //    --------------- -------- ------
    //    min                      scalar
    //    max                      scalar
    //    abs                      <T>
    //    sqrt                     <T>
    //    log                      <T>
    //    exp                      <T>
    //    accumulate               scalar
    //    transform                this


    /**
     * Return the minimum element in a range using comparison functor.
     *
     * @note Mimics std::min_element
     *
     * @return the minimum value computed
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01014.html#g09af772609c56f01dd33891d51340baf">std::min_element</a>
     */
    public double min();

    /**
     * Return the minimum element in a range using comparison functor.
     *
     * @note Mimics std::min_element
     *
     * @param from is the initial index, inclusive
     * @param to is the final index, exclusive
     * @return the minimum value computed
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01014.html#g09af772609c56f01dd33891d51340baf">std::min_element</a>
     */
    public double min(final int from, final int to);

    /**
     * Return the maximum element in a range using comparison functor.
     *
     * @note Mimics std::max_element
     *
     * @return the maximum value computed
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01014.html#g595f12feaa16ea8aac6e5bd51782e123">std::max_element</a>
     */
    public double max();

    /**
     * Return the maximum element in a range using comparison functor.
     *
     * @note Mimics std::max_element
     *
     * @param from is the initial index, inclusive
     * @param to is the final index, exclusive
     * @return the maximum value computed
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01014.html#g595f12feaa16ea8aac6e5bd51782e123">std::max_element</a>
     */
    public double max(final int from, final int to);

    /**
     * Applies {@link Math#abs(double)} to every element of <code>this</code> instance.
     *
     * @return a new instance
     */
    public T abs();

    /**
     * Applies {@link Math#sqr(double)} to every element of <code>this</code> instance.
     *
     * @return a new instance
     */
    public T sqr();

    /**
     * Applies {@link Math#sqrt(double)} to every element of <code>this</code> instance.
     *
     * @return a new instance
     */
    public T sqrt();

    /**
     * Applies {@link Math#log(double)} to every element of <code>this</code> instance.
     *
     * @return a new instance
     */
    public T log();

    /**
     * Applies {@link Math#exp(double)} to every element of <code>this</code> instance.
     *
     * @return a new instance
     */
    public T exp();



    /**
     * Accumulate values in a range.
     * <p>
     * Accumulates the values in the range [begin,end+1).
     * The initial value is zero. The values are processed in order.
     *
     * @return the final sum
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a00969.html#3e6040dba097b64311fce39fa87d1b29">std::accumulate</a>
     */
    public double accumulate();

    /**
     * Accumulate values in a range.
     * <p>
     * Accumulates the values in the range [begin(),end()+1).
     * The initial value is init. The values are processed in order.
     *
     * @param init is the starting value to add other values to.
     *
     * @return the final sum
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a00969.html#3e6040dba097b64311fce39fa87d1b29">std::accumulate</a>
     */
    public double accumulate(final double init);

    /**
     * Accumulate values in a range.
     * <p>
     * Accumulates the values in the range [first,last).
     * The initial value is init. The values are processed in order.
     *
     * @param first is the start of range, inclusive
     * @param last is the end of range, exclusive
     * @param init is the starting value to add other values to.
     *
     * @return the final sum
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a00969.html#3e6040dba097b64311fce39fa87d1b29">std::accumulate</a>
     */
    public double accumulate(final int from, final int to, final double init);

    /**
     * Perform an operation on a sequence in a range [first,last)
     * <p>
     * Applies the operator to each element in the input range and assigns the results to itself.
     * Evaluates input[first+N] = unary_op(array[first+N]) for each <code>N</code> in the range [0, end()-begin() ).
     *
     * @param f is an unary function to be applied
     *
     * @return <code>this</code>, i.e: performs the transformation in place
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01161.html#gaaf771a08ae2322b42640bb14fc342c5d">std::transform</a>
     */
    public T transform(final DoubleOp func);

    /**
     * Perform an operation on a sequence in a range [first,last)
     * <p>
     * Applies the operator to each element in the input range and assigns the results to itself.
     * Evaluates input[first+N] = unary_op(array[first+N]) for each <code>N</code> in the range [0, last-first).
     *
     * @param first is the start of range, inclusive
     * @param last is the end of range, exclusive
     * @param f is an unary function to be applied
     *
     * @return <code>this</code>, i.e: performs the transformation in place
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01161.html#gaaf771a08ae2322b42640bb14fc342c5d">std::transform</a>
     */
    public T transform(final int first, final int last, final Ops.DoubleOp f);


//
// TO BE IMPLEMENTED
//
//    /**
//     * Perform an operation on a sequence in a range [first,last)
//     * <p>
//     * Applies the operator to each element in the input range and assigns the results to successive elements of
//     * the output sequence. Evaluates input[first+N] = unary_op(array[to+N]) for each <code>N</code> in the range [0, last-first).
//     *
//     * @param first is the start of range, inclusive
//     * @param last is the end of range, exclusive
//     * @param result is the output sequence
//     * @param to is the first element of the output sequence
//     * @param f is an unary function to be applied
//     *
//     * @return the result sequence received as input
//     *
//     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01161.html#gaaf771a08ae2322b42640bb14fc342c5d">std::transform</a>
//     */
//    public T transform(final int first, final int last, final T result, final int to, final Ops.DoubleOp f);
//
//    public T transform(final T another, final Ops.BinaryDoubleOp f);
//
//    public T transform(final int first1, final int last1, final T another, final int first2, final Ops.BinaryDoubleOp f);
//
//    public T transform(final int first1, final int last1, final T another, final int first2, final T result, final int to, final Ops.BinaryDoubleOp f);


    //
    //    Miscellaneous
    //
    //    method          right    result
    //    ------------    -------- ------
    //    lowerBound               int
    //    upperBound               int

    /**
     * Finds the first position in which val could be inserted without changing the ordering.
     *
     * @param value to be searched
     *
     * @return an index pointing to the first element "not less than" <code>val</code>, or end() if every element is
     * less than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01165.html#ga0ff3b53e875d75731ff8361958fac68f">std::lower_bound</a>
     */
    public abstract int lowerBound(final double val);

    /**
     * Finds the first position in which val could be inserted without changing the ordering.
     *
     * @param first element, inclusive
     * @param last element, <b>inclusive</b>
     * @param value to be searched
     *
     * @return an index pointing to the first element "not less than" <code>val</code>, or end() if every element is
     * less than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01165.html#ga0ff3b53e875d75731ff8361958fac68f">std::lower_bound</a>
     */
    public abstract int lowerBound(int from, final int to, final double val);

    /**
     * Finds the first position in which val could be inserted without changing the ordering.
     *
     * @param value to be searched
     * @param f is a BinaryDoublePredicate which defines the comparison function
     *
     * @return an index pointing to the first element "not less than" <code>val</code>, or end() if every element is
     * less than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01165.html#ga0ff3b53e875d75731ff8361958fac68f">std::lower_bound</a>
     */
    public abstract int lowerBound(final double val, final Ops.BinaryDoublePredicate f);

    /**
     * Finds the first position in which val could be inserted without changing the ordering.
     *
     * @param first element, inclusive
     * @param last element, <b>inclusive</b>
     * @param value to be searched
     * @param f is a BinaryDoublePredicate which defines the comparison function
     *
     * @return an index pointing to the first element "not less than" <code>val</code>, or end() if every element is
     * less than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01165.html#ga0ff3b53e875d75731ff8361958fac68f">std::lower_bound</a>
     */
    public abstract int lowerBound(int from, final int to, final double val, final Ops.BinaryDoublePredicate f);

    /**
     * Finds the last position in which val could be inserted in a sequence [begin(), end()] without changing the ordering.
     *
     * @param value to be searched
     *
     * @return an index pointing to the first element greater than <code>val</code>, or
     * end() if no elements are greater than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01016.html#g9bf525d5276b91ff6441e27386034a75">std::upper_bound</a>
     */
    public abstract int upperBound(final double val);


    /**
     * Finds the last position in which val could be inserted in a sequence [first,last] without changing the ordering.
     *
     * @param first element, inclusive
     * @param last element, <b>inclusive</b>
     * @param value to be searched
     *
     * @return an index pointing to the first element greater than <code>val</code>, or
     * end() if no elements are greater than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01016.html#g9bf525d5276b91ff6441e27386034a75">std::upper_bound</a>
     */
    public abstract int upperBound(int first, final int last, final double val);


    /**
     * Finds the last position in which val could be inserted in a sequence [begin(), end()] without changing the ordering.
     *
     * @param value to be searched
     * @param f is a BinaryDoublePredicate which defines the comparison function
     *
     * @return an index pointing to the first element greater than <code>val</code>, or
     * end() if no elements are greater than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01016.html#g9bf525d5276b91ff6441e27386034a75">std::upper_bound</a>
     */
    public abstract int upperBound(final double val, final Ops.BinaryDoublePredicate f);


    /**
     * Finds the last position in which val could be inserted in a sequence [first,last] without changing the ordering.
     *
     * @param first element, inclusive
     * @param last element, <b>inclusive</b>
     * @param value to be searched
     * @param f is a BinaryDoublePredicate which defines the comparison function
     *
     * @return an index pointing to the first element greater than <code>val</code>, or
     * end() if no elements are greater than <code>val</code>.
     *
     * @see <a href="http://gcc.gnu.org/onlinedocs/libstdc++/latest-doxygen/a01016.html#g9bf525d5276b91ff6441e27386034a75">std::upper_bound</a>
     */
    public abstract int upperBound(int first, final int last, final double val, final Ops.BinaryDoublePredicate f);

}