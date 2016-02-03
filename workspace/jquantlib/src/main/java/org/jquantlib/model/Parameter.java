/*
Copyright (C) 2009 Praneet Tiwari

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
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb

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

package org.jquantlib.model;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.Constraint;
import org.jquantlib.math.optimization.NoConstraint;

/**
 * Base class for model arguments
 *
 * @author Praneet Tiwari
 */
@QualityAssurance(quality = Quality.Q1_TRANSLATION, version = Version.V097, reviewers = { "Richard Gomes" })
public class Parameter {

    //
    // protected fields
    //

    protected Constraint constraint;
    protected Array params;
    protected Impl impl;


    //
    // public constructors
    //

    public Parameter() {
        this.constraint = new NoConstraint();

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }


    //
    // protected constructors
    //

    protected Parameter(final int size, final Impl impl, final Constraint  constraint) {
        this.constraint = constraint;
        this.impl = impl;
        this.params = new Array(size);

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }


    //
    // public methods
    //

    public final Array  params() /* @ReadOnly */ {
        return params;
    }

    public void setParam(final int i, final double x) {
        params.set(i, x);
    }

    public boolean testParams(final Array  params) /* @ReadOnly */ {
        return constraint.test(params);
    }

    // FIXME: evaluate the possibility to rename to Ops.Ops#op
    public double get(final /* @Time */ double t) /* @ReadOnly */ {
        return impl.value(params, t);
    }

    public int size() /* @ReadOnly */ {
        return params.size();
    }

    public final Impl implementation() /* @ReadOnly */ {
        return impl;
    }


    //
    // inner interfaces
    //

    protected interface Impl {
        public abstract double value(final Array  params, final /* @Time */ double t) /* @ReadOnly */;
    }

}
