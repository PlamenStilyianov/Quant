/*
Copyright (C) 2008 Praneet Tiwari

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
package org.jquantlib.model.shortrate.onefactormodels;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.model.AffineModel;

/**
 * Single-factor affine base class
 * <p>
 * Single-factor models with an analytical formula for discount bonds should inherit from this class.
 * They must then implement the functions \f$ A(t,T) \f$ and \f$ B(t,T) \f$ such that
 * \f[ P(t, T, r_t) = A(t,T)e^{ -B(t,T) r_t}. \f]
 * 
 * @category shortrate
 * 
 * @author Praneet Tiwari
 */
public abstract class OneFactorAffineModel extends OneFactorModel implements AffineModel {

    //
    // public methods
    //

    public OneFactorAffineModel(final int nArguments) {
        super(nArguments);
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
    }


    public double discountBond(/* @Time */ final double now, /* @Time */ final double maturity, /* @Rate */ final double rate) /* @ReadOnly */ {
        return A(now, maturity) * Math.exp(-B(now, maturity) * rate);
    }


    //
    // protected abstract methods
    //

    protected abstract double A(/* @Time */ final double t, /* @Time */ final double T) /* @ReadOnly */;

    protected abstract double B(/* @Time */ final double t, /* @Time */ final double T) /* @ReadOnly */;


    //
    // implements AffineModel
    //

    @Override
    public double discountBond(/* @Time */ final double now, /* @Time */ final double maturity, final Array factors) /* @ReadOnly */ {
        return discountBond(now, maturity, factors.first());
    }

    @Override
    public /* @DiscountFactor */ double discount(/* @Time */ final double t) /* @ReadOnly */ {
        final double /* @Real */x0 = dynamics().process().x0();
        final double /* @Rate */r0 = dynamics().shortRate(0.0, x0);
        return discountBond(0.0, t, r0);
    }

}
