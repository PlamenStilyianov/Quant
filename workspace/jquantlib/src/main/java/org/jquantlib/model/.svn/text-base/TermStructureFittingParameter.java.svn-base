/*
Copyright (C) 2008 Praneet Tiwari
Copyright (C) 2008 Praneet Richard Gomes

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
package org.jquantlib.model;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.NoConstraint;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;

/**
 * Deterministic time-dependent parameter used for yield-curve fitting.
 *
 * @author Praneet Tiwari
 */
@QualityAssurance(quality=Quality.Q3_DOCUMENTATION, version=Version.V097, reviewers="Richard Gomes")
public class TermStructureFittingParameter extends Parameter {

    //
    // public constructors
    //

    public TermStructureFittingParameter(final Parameter.Impl impl) {
        super(0, impl, new NoConstraint());
    }

    public TermStructureFittingParameter(final Handle <YieldTermStructure> term) {
        super(0, new NumericalImpl(term), new NoConstraint());
    }


    //
    // protected inner classes
    //

    static public class NumericalImpl implements Parameter.Impl {

        //
        // private fields
        //

        private final List<Double> times;
        private final List<Double> values;
        private final Handle<YieldTermStructure> termStructure;


        //
        // public methods
        //

        public NumericalImpl(final Handle<YieldTermStructure> termStructure) {
            this.times  = new ArrayList<Double>();
            this.values = new ArrayList<Double>();
            this.termStructure = termStructure;
        }

        public void set(final /* @Time */ double t, final double x) {
            times.add(t);
            values.add(x);
        }

        public void change(final double x) {
            final int last = values.size()-1;
            values.set(last, x);
        }

        public void reset() {
            times.clear();
            values.clear();
        }

        @Override
        public double value(final Array  ref, /* @Time */ final double t) /* @ReadOnly */ {
            final int index = times.indexOf(t);
            QL.require(index > -1, "fitting parameter not set!"); // TODO: message
            return values.get(index);
        }

        public final Handle<YieldTermStructure> termStructure() /* @ReadOnly */ {
            return termStructure;
        }
    }
}
