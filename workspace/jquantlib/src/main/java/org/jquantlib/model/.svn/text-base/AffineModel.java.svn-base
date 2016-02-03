package org.jquantlib.model;

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


import org.jquantlib.instruments.Option;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.util.Observable;

/**
 * Affine model class
 * <p>
 * Base class for analytically tractable models.
 * 
 * @category shortrate
 * 
 * @author Praneet Tiwari
 */
public interface AffineModel extends Observable {

    /**
     * Implied discount curve
     */
    public /* @DiscountFactor */ double discount(/* @Time */ double t) /* @ReadOnly */;

    public double discountBond(
            /* @Time */ double now,
            /* @Time */ double maturity,
            Array factors) /* @ReadOnly */;

    public double discountBondOption(
            Option.Type type,
            double strike,
            /* @Time */ double maturity,
            /* @Time */ double bondMaturity) /* @ReadOnly */;
}
