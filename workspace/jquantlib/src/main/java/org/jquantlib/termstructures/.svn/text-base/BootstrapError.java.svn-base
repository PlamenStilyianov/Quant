
/*
Copyright (C) 2009 John Martin

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


package org.jquantlib.termstructures;

import org.jquantlib.QL;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.lang.reflect.ReflectConstants;
import org.jquantlib.math.Ops;
import org.jquantlib.termstructures.yieldcurves.PiecewiseCurve;
import org.jquantlib.termstructures.yieldcurves.Traits;

// FIXME: http://bugs.jquantlib.org/view.php?id=463
public class BootstrapError<T extends Traits> implements Ops.DoubleOp {

    private final PiecewiseCurve    curve;
    private final Traits            traits;
    private final RateHelper        helper;
    private final int               segment;


    public BootstrapError(
            final Class<?> klass,
            final PiecewiseCurve curve,
            final RateHelper helper,
            final int segment) {
        this(constructTraits(klass), curve, helper, segment);
    }
    public BootstrapError(
            final Traits traits,
            final PiecewiseCurve c,
            final RateHelper helper,
            final int segment) {
        QL.validateExperimentalMode();

        if (!Traits.class.isAssignableFrom(traits.getClass())) {
            throw new LibraryException(ReflectConstants.WRONG_ARGUMENT_TYPE);
        }

        this.traits = traits;
        this.curve = c;
        this.segment = segment;
        this.helper = helper;
    }


    //
    // public methods
    //

    @Override
	public double op (final double guess) {
        //FIXME: find a way to solve this! :: ifndef DOXYGEN
        traits.updateGuess (curve.data(), guess, segment);
        curve.interpolation().update();
        return helper.quoteError();
    }


    //
    // static private methods
    //

    static private Traits constructTraits(final Class<?> klass) {
        if (klass==null) {
            throw new LibraryException("null Traits"); // TODO: message
        }
        if (klass!=Traits.class) {
            throw new LibraryException(ReflectConstants.WRONG_ARGUMENT_TYPE);
        }

        try {
            return (Traits) klass.newInstance();
        } catch (final Exception e) {
            throw new LibraryException("cannot create Traits", e); // TODO: message
        }
    }

}