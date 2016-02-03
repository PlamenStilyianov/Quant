/*
 Copyright (C) 2010 Zahid Hussain

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
package org.jquantlib.util;

import java.util.List;

import org.jquantlib.lang.exceptions.LibraryException;

/**
 * This class contains utility methods similar to C++ std functions
 * 
 * @author Zahid Hussain
 */
public class Std {
	public static enum OpType {
		Plus, Sub, Div, Mult
	};

	/**
	 * This method is equivalent to std:lower_bound function Returns an index
	 * pointing to the first element in the ordered collection is equal or
	 * greater than passed value
	 * 
	 * @param valueList
	 *            order collection in ascending order
	 * @param value
	 *            Date to be compared
	 * @return index to element which is >= passed value
	 */

	public static <T> int lowerBound(final List<T> valueList, int fromIdx,
			int toIdx, final Comparable<T> value) {
		int len = toIdx - fromIdx + (toIdx > fromIdx ? 1 : 0);
		int from = fromIdx;
		int half;
		int middle;

		while (len > 0) {
			half = len >> 1;
			middle = from;
			middle = middle + half;

			if (value.compareTo(valueList.get(middle)) == 1) { // value > 1
				from = middle;
				from++;
				len = len - half - 1;
			} else {
				len = half;
			}
		}
		return from;
	}

	public static <T> int lowerBound(final List<T> valueList,
			final Comparable<T> value) {
		return Std.lowerBound(valueList, 0, valueList != null
				&& !valueList.isEmpty() ? valueList.size() - 1 : 0, value);
	}

	/**
	 * This method is equivalent to C++ std:upper_bound function Returns an
	 * index pointing to the first element in the ordered collection which is
	 * greater than passed value
	 * 
	 * @param <T>
	 * 
	 * @param valueList
	 *            order collection in ascending order
	 * @param value
	 *            Date to be compared
	 * @return index to element which is > passed value
	 */
	public static <T> int upperBound(final List<T> valueList, int fromIdx,
			int toIdx, final Comparable<T> value) {

		int len = toIdx - fromIdx + (toIdx > fromIdx ? 1 : 0);
		int from = fromIdx;
		int half;
		int middle;

		while (len > 0) {
			half = len >> 1;
			middle = from;
			middle = middle + half;
			if (value.compareTo(valueList.get(middle)) == -1) {
				len = half;
			} else {
				from = middle;
				from++;
				len = len - half - 1;
			}
		}
		return from;
	}

	public static <T> int upperBound(final List<T> valueList,
			final Comparable<T> value) {
		return Std.upperBound(valueList, 0, valueList != null
				&& !valueList.isEmpty() ? valueList.size() - 1 : 0, value);
	}

	/**
	 * This method is equivalent to C++ std:distance function Return distance
	 * between indices (or iterators) Calculates the number of elements between
	 * first and last. If i is a Random Access Iterator, the function uses
	 * operator- to calculate this. Otherwise, the function uses repeatedly the
	 * increase or decrease operator (operator++ or operator--) until this
	 * distance is calculated.
	 */

	public static <T> int distance(final List<T> values, int first, int last) {
		int size = last - first;
		return size;
	}

	/**
	 * std:copy: Copy range of elements Copies the elements in the range
	 * [first,last) into a range beginning at result. Returns an iterator to the
	 * last element in the destination range Parameters first, last Input
	 * iterators to the initial and final positions in a sequence to be copied.
	 * The range used is [first,last), which contains all the elements between
	 * first and last, including the element pointed by first but not the
	 * element pointed by last. result Output iterator to the initial position
	 * in the destination sequence. This shall not point to any element in the
	 * range [first,last).
	 * 
	 * @return
	 */

	public static <T> int copy(final List<T> from, int first, int last,
			List<T> to) {
		int i = first;
		for (; i < last; i++) {
			to.add(from.get(i));
		}
		return i;
	}

	public static <T> int copy(final List<T> from, int first, int last,
			List<T> to, int toFrom) {
		while (first < last) {
			to.set(toFrom++, from.get(first++));
		}
		return toFrom;
	}

	public static <T> void fill(final List<T> array, int from, int last, T value) {
		for (int i = from; i < last; i++) {
			array.set(i, value);
		}
	}


	/**
	 * Test whether the elements in two ranges are equal Compares the elements
	 * in the range [first1,last1) with those in the range beginning at first2,
	 * and returns true if the elements in both ranges are considered equal. The
	 * elements are compared by either applying the == comparison operator to
	 * each pair of corresponding elements, or the template parameter pred (for
	 * the second version)\ The behavior of this function template is equivalent
	 * to
	 * 
	 * template <class InputIterator1, class InputIterator2> bool equal (
	 * InputIterator1 first1, InputIterator1 last1, InputIterator2 first2 ) {
	 * while ( first1!=last1 ) { if (*first1 != *first2) // or: if
	 * (!pred(*first1,*first2)), for pred version return false; ++first1;
	 * ++first2; } return true; }
	 * 
	 * @param array
	 * @param size
	 * @param to
	 * @param i
	 * @return
	 */
	public static <T> boolean equal(List<T> first, int from, int last,
			List<T> second, int secondFrom) {
		while (from < last) {
			if (!first.get(from).equals(second.get(secondFrom)))
				return false;
			from++;
			secondFrom++;
		}
		return true;
	}

	public static <T> boolean equal(List<T> first, List<T> second) {
		if (first.size() != second.size())
			return false;
		return Std.equal(first, 0, first.size(), second, 0);
	}

	public class Operator<T extends Number> {
		protected OpType op;

		public Operator(OpType o) {
			this.op = o;
		}

		public T op(T t1) {
			return t1;
		} // dummy impl

		public T op(T t1, T t2) {
			return t1;
		} // dummy implementation

		public Double op(Double t1) {
			switch (op) {
			case Plus:
				return ++t1;
			case Sub:
				return --t1;
			default:
				throw new LibraryException("Unsupported operation");
			}
		}

		public Double op(Double t1, Double t2) {
			switch (op) {
			case Plus:
				return t1 + t2;
			case Sub:
				return t1 - t2;
			case Div:
				return t1 / t2;
			case Mult:
				return t1 * t2;
			default:
				throw new LibraryException("Unsupported operation");
			}
		}

		public Integer op(Integer t1, Integer t2) {
			switch (op) {
			case Plus:
				return t1 + t2;
			case Sub:
				return t1 - t2;
			case Div:
				return t1 / t2;
			case Mult:
				return t1 * t2;
			default:
				throw new LibraryException("Unsupported operation");
			}
		}

		public Long op(Long t1, Long t2) {
			switch (op) {
			case Plus:
				return t1 + t2;
			case Sub:
				return t1 - t2;
			case Div:
				return t1 / t2;
			case Mult:
				return t1 * t2;
			default:
				throw new LibraryException("Unsupported operation");
			}
		}
	}

	public class BinaryOperator<T extends Number> extends Operator<T> {

		public BinaryOperator(OpType op) {
			super(op);
		}

		public T op(T t1, T t2) {
			return t1; // dummy
		}
	}

	public class UninaryOperator<T extends Number> extends Operator<T> {
		public UninaryOperator(OpType op) {
			super(op);
		}
	}

	public class Plus<T extends Number> extends BinaryOperator<T> {
		public Plus() {
			super(OpType.Plus);
		}
	}

	/*
	 * 
	 * template <class Operation, class T> binder2nd<Operation> bind2nd (const
	 * Operation& op, const T& x); Return function object with second parameter
	 * binded
	 * 
	 * This function constructs an unary function object from the binary
	 * function object op by binding its second parameter to the fixed value x.
	 * The function object returned by bind2nd has its operator() defined such
	 * that it takes only one argument. This argument is used to call binary
	 * function object op with x as the fixed value for the second argument. It
	 * is defined with the same behavior as: template <class Operation, class T>
	 * binder2nd<Operation> bind2nd (const Operation& op, const T& x) { return
	 * binder2nd<Operation>(op, typename Operation::second_argument_type(x)); }
	 */
	public class Bind2nd<T extends Number> {

		public BinaryOperator<T> bind2nd(final T x) {
			// TODO:
			throw new LibraryException("Unsupported operation");
		}
	}

	/*
	 * template < class InputIterator, class OutputIterator, class UnaryOperator
	 * > OutputIterator transform ( InputIterator first1, InputIterator last1,
	 * OutputIterator result, UnaryOperator op ) template < class
	 * InputIterator1, class InputIterator2, class OutputIterator, class
	 * BinaryOperator > OutputIterator transform ( InputIterator1 first1,
	 * InputIterator1 last1, InputIterator2 first2, OutputIterator result,
	 * BinaryOperator binary_op ); { while (first1 != last1)result++ =
	 * op(*first1++); // or: *result++=binary_op(*first1++,*first2++); return
	 * result;
	 * 
	 * }
	 */

	public static <T extends Number> int transform(List<T> list1, int first1,
			int last1, List<T> list2, int first2, List<T> result,
			BinaryOperator<T> binaryOp) {
		int idx = 0;
		while (first1 < last1) {
			T v1 = list1.get(first1);
			T v2 = list2.get(first2);
			T r = binaryOp.op(v1, v2);
			result.set(idx, r);
			first1++;
			first2++;
			idx++;
		}
		return idx;
	}
}
