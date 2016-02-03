/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.methods.finitedifferences;

import org.jquantlib.math.matrixutilities.Array;

/**
 * @author Srinivas Hasti
 * @author Tim Swetonic
 */
//TODO: code review :: license, class comments, comments for access modifiers, put "final" everywhere
//TODO: code review:: This class should be parametrized
public class TridiagonalOperator implements Operator {

	protected TimeSetter timeSetter;
	protected Array lowerDiagonal;
	protected Array diagonal;
	protected Array upperDiagonal;


	public TridiagonalOperator(final int size) {
		if (size >= 3) {
			this.lowerDiagonal = new Array(size - 1);
			this.diagonal = new Array(size);
			this.upperDiagonal = new Array(size - 1);
		} else if (size == 0) {
			this.lowerDiagonal = new Array(0);
			this.diagonal = new Array(0);
			this.upperDiagonal = new Array(0);
		} else
            throw new IllegalStateException("Invalid size for Tridiagonal Operator"); // TODO: message

	}

	public TridiagonalOperator(final Array ldiag, final Array diag, final Array udiag) {
		if (ldiag.size() != diag.size() - 1) throw new IllegalStateException("wrong size for lower diagonal");
		if (udiag.size() != diag.size() - 1) throw new IllegalStateException("wrong size for upper diagonal");
		this.lowerDiagonal = ldiag;
		this.diagonal = diag;
		this.upperDiagonal = udiag;
	}

	public TridiagonalOperator(final TridiagonalOperator t) {
		this.diagonal = t.diagonal();
		this.upperDiagonal = t.upperDiagonal();
		this.lowerDiagonal = t.lowerDiagonal();
		this.timeSetter = t.getTimeSetter();
	}

	public void setFirstRow(final double b, final double c) {
		diagonal.set(0, b);
		upperDiagonal.set(0, c);
	}

	public void setMidRow(final int size, final double a, final double b, final double c) {

		if (!(size >= 1 && size <= size() - 2))
			throw new IllegalStateException("out of range in setMidRow");

		lowerDiagonal.set(size - 1, a);
		diagonal.set(size, b);
		upperDiagonal.set(size, c);

	}

	public void setMidRows(final double a, final double b, final double c) {
		for (int i = 1; i <= size() - 2; i++) {
			lowerDiagonal.set(i - 1, a);
			diagonal.set(i, b);
			upperDiagonal.set(i, c);
		}
	}

	public void setLastRow(final double a, final double b) {
		lowerDiagonal.set(size() - 2, a);
		diagonal.set(size() - 1, b);
	}

    public final Array lowerDiagonal() {
        return lowerDiagonal;
    }

    public final Array diagonal() {
        return diagonal;
    }

    public final Array upperDiagonal() {
        return upperDiagonal;
    }

    public TimeSetter getTimeSetter() {
        return this.timeSetter;
    }


	//
	// implements Operator
	//

    @Override
    public int size() {
        return diagonal.size();
    }

    @Override
    public boolean isTimeDependent() {
        return timeSetter != null;
    }

	@Override
	public void setTime(final double t) {
		if (timeSetter != null) {
			timeSetter.setTime(t, this);
		}
	}

    @Override
	public Operator add(final Operator op) {
        final TridiagonalOperator D = (TridiagonalOperator)op;
        final Array low  = lowerDiagonal.add(D.lowerDiagonal);
        final Array mid  = diagonal.add(D.diagonal);
        final Array high = upperDiagonal.add(D.upperDiagonal);
        return new TridiagonalOperator(low, mid, high);
	}

    @Override
	public Operator subtract(final Operator op) {
        final TridiagonalOperator D = (TridiagonalOperator)op;
        final Array low  = lowerDiagonal.sub(D.lowerDiagonal);
        final Array mid  = diagonal.sub(D.diagonal);
        final Array high = upperDiagonal.sub(D.upperDiagonal);
        return new TridiagonalOperator(low, mid, high);
	}

    @Override
    public Operator multiply(final double a) {
        final Array low  = lowerDiagonal.mul(a);
        final Array mid  = diagonal.mul(a);
        final Array high = upperDiagonal.mul(a);
        return new TridiagonalOperator(low, mid, high);
    }

//
//    @Override
//    public Operator add(final Operator op1, final Operator op2) {
//        TridiagonalOperator D1 = (TridiagonalOperator) op1;
//        TridiagonalOperator D2 = (TridiagonalOperator) op2;
//        Array low  = D1.lowerDiagonal.add(D2.lowerDiagonal());
//        Array mid  = D1.diagonal.add(D2.diagonal());
//        Array high = D1.upperDiagonal.add(D2.upperDiagonal());
//        return new TridiagonalOperator(low, mid, high);
//    }
//
//    @Override
//    public Operator subtract(final Operator op1, final Operator op2) {
//        TridiagonalOperator D1 = (TridiagonalOperator) op1;
//        TridiagonalOperator D2 = (TridiagonalOperator) op2;
//        Array low  = D1.lowerDiagonal.sub(D2.lowerDiagonal);
//        Array mid  = D1.diagonal.sub(D2.diagonal);
//        Array high = D1.upperDiagonal.sub(D2.upperDiagonal);
//        return new TridiagonalOperator(low, mid, high);
//    }
//
//    @Override
//    public Operator multiply(final double a, final Operator op) {
//        TridiagonalOperator D = (TridiagonalOperator) op;
//        Array low = D.lowerDiagonal;
//        low.mul(a);
//        Array mid = D.diagonal;
//        mid.mul(a);
//        Array high = D.upperDiagonal;
//        high.mul(a);
//        return new TridiagonalOperator(low, mid, high);
//    }
//
//    @Override
//    public Operator multiply(final Operator D, double a) {
//		return multiply(a, D);
//	}
//
//    @Override
//	public Operator divide(final Operator op, double a) {
//		TridiagonalOperator D = (TridiagonalOperator) op;
//		Array low = D.lowerDiagonal;
//		low.div(a);
//		Array mid = D.diagonal;
//		mid.div(a);
//		Array high = D.upperDiagonal;
//		low.div(a);
//		return new TridiagonalOperator(low, mid, high);
//	}
//
//	/**
//	 * Solve linear system with SOR approach
//	 */
//    @Override
//	public final Array SOR(final Array rhs, int tol) {
//		if (rhs.length != size())
//			throw new IllegalStateException("rhs has the wrong size");
//
//		// initial guess
//		Array result = rhs;
//
//		// solve tridiagonal system with SOR technique
//		int sorIteration, i;
//		double omega = 1.5;
//		double err = 2.0 * tol;
//		double temp;
//		for (sorIteration = 0; err > tol; sorIteration++) {
//			if (sorIteration > 100000) {
//				throw new IllegalStateException("tolerance (" + tol
//						+ ") not reached in " + sorIteration + " iterations. "
//						+ "The error still is " + err);
//			}
//
//			temp = omega * (rhs.first() - upperDiagonal.first() * result.get(1) - diagonal.first() * result.first()) / diagonal.first();
//			err = temp * temp;
//			result.set(0, result.first() + temp);
//
//			for (i = 1; i < size() - 1; i++) {
//				temp = omega
//						* (rhs.get(i) - upperDiagonal.get(i)
//								* result.get(i + 1) - diagonal.get(i)
//								* result.get(i) - lowerDiagonal.get(i - 1)
//								* result.get(i - 1)) / diagonal.get(i);
//				err += temp * temp;
//				result.set(i, result.get(i) + temp);
//			}
//
//			temp = omega * (rhs.get(i) - diagonal.get(i) * result.get(i) - lowerDiagonal.get(i - 1) * result.get(i - 1)) / diagonal.get(i);
//			err += temp * temp;
//			result.set(i, result.get(i) + temp);
//		}
//
//		return result;
//
//	}

	/**
	 * Identity instance
	 */
    @Override
	public TridiagonalOperator identity(final int size) {
		final TridiagonalOperator I = new TridiagonalOperator(
				new Array(size-1),         // lower diagonal
				new Array(size).fill(1.0), // diagonal
				new Array(size-1));        // upper diagonal
		return I;
	}

    @Override
	public void swap(final Operator from) {
		final TridiagonalOperator D = (TridiagonalOperator) from;
		this.diagonal.swap(D.diagonal);
		this.lowerDiagonal.swap(D.lowerDiagonal);
		this.upperDiagonal.swap(D.upperDiagonal);
		// swaps TimeSetter
		final TimeSetter tmpTimeSetter = this.timeSetter; this.timeSetter = D.timeSetter; D.timeSetter = tmpTimeSetter;
	}

//	// CODE REVIEW: This doesn't look right, L1 and temp will be pointing to
//	// same reference ??
//    @Override
//	public void swap(Operator op1, Operator op2) {
//		TridiagonalOperator L1 = (TridiagonalOperator) op1;
//		TridiagonalOperator temp = L1;
//		L1.swap(op2);
//		op2.swap(temp);
//	}

    @Override
	public Array applyTo(final Array v) /*@ReadOnly*/ {
		if (v.size() != size())
			throw new IllegalStateException("vector of the wrong size (" + v.size() + "instead of " + size() + ")");

		// result = diagonal * v
		final Array result = this.diagonal.mul(v);

		// matricial product
		double d = result.get(0) + (this.upperDiagonal.get(0) * v.get(1));
		result.set(0, d);
		for (int j=1; j<=size()-2; j++) {
		    d = result.get(j) + (lowerDiagonal.get(j-1) * v.get(j-1)) + (upperDiagonal.get(j) * v.get(j+1));
			result.set(j, d);
		}
		d = result.get(size()-1) + (lowerDiagonal.get(size()-2) * v.get(size()-2));
		result.set(size()-1, d);

		return result;
	}

	/**
	 * Solve linear system for a given right-hand side
	 */
    @Override
	public final Array solveFor(final Array rhs) {
		final Array result = new Array(size());
		final Array tmp = new Array(size());

		double bet = diagonal.first();
		if (bet == 0.0) throw new IllegalStateException("division by zero");

		result.set(0, rhs.first() / bet);
		int j;
		for (j = 1; j <= size() - 1; j++) {
			tmp.set(j, upperDiagonal.get(j - 1) / bet);
			bet = diagonal.get(j) - lowerDiagonal.get(j - 1) * tmp.get(j);
			if (bet == 0.0) throw new IllegalStateException("division by zero");
			result.set(j, (rhs.get(j) - lowerDiagonal.get(j - 1) * result.get(j - 1)) / bet);
		}

		// cannot be j>=0 with Size j
		for (j = size() - 2; j > 0; --j) {
            result.set(j, result.get(j) - (tmp.get(j + 1) * result.get(j + 1)));
        }

		result.set(0, result.first() - (tmp.get(1) * result.get(1)));
		return result;
	}

    @Override
    public final double[] solveFor(final double[] rhs) {
        final double[] result = new double[size()];
        final double[] tmp = new double[size()];

        double bet = diagonal.first();
        if (bet == 0.0) throw new IllegalStateException("division by zero");

        result[0] = rhs[0] / bet;
        int j;
        for (j = 1; j <= size() - 1; j++) {
            tmp[j] = upperDiagonal.get(j - 1) / bet;
            bet = diagonal.get(j) - lowerDiagonal.get(j - 1) * tmp[j];
            if (bet == 0.0) throw new IllegalStateException("division by zero");
            result[j] = (rhs[j] - lowerDiagonal.get(j - 1) * result[j-1]) / bet;
        }

        // cannot be j>=0 with Size j
        for (j = size() - 2; j > 0; --j) {
            result[j] -= tmp[j + 1] * result[j + 1];
        }

        result[0] = result[0] - (tmp[1] * result[1]);
        return result;
    }



    //TODO : code review against QuantLib/C++
    /*public Operator assign(Operator d) {
		swap(d);
		return this;
	}*/




    //
    // public static inner interfaces
    //
    
    
	// TODO: code review: consider refactor inner interface TimeSetter
    public static interface TimeSetter {
		public void setTime(double t, TridiagonalOperator l);
	}

}
