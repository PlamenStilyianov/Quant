/*
 Copyright (C) 2007 Richard Gomes

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
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

package org.jquantlib.math.optimization;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Constraint enforcing both given sub-constraints
 * 
 * @author Richard Gomes
 */
@QualityAssurance(quality=Quality.Q3_DOCUMENTATION, version=Version.V097, reviewers="Richard Gomes")
public class CompositeConstraint extends Constraint {

    //
    // public constructors
    //

    public CompositeConstraint(final Constraint c1, final Constraint c2) {
        super.impl = new Impl(c1, c2);
    }


    //
    // private inner classes
    //

    /**
     * Base class for constraint implementations.
     */
    private class Impl extends Constraint.Impl {

        //
        // private fields
        //

        private final Constraint c1, c2;


        //
        // private constructors
        //

        private Impl(final Constraint c1, final Constraint c2) {
            this.c1 = c1;
            this.c2 = c2;
        }


        //
        // public abstract methods
        //

        /**
         * Tests if params satisfy the constraint.
         */
        @Override
        public boolean test(final Array  params) /* @ReadOnly */ {
            return c1.test(params) && c2.test(params);
        }

    }

}
