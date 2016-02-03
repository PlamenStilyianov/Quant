/*
 Copyright (C) 2008 Richard Gomes

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
 Copyright (C) 2002, 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl

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

package org.jquantlib.math.interpolations;

import org.jquantlib.math.Ops;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Interface for 1-D interpolations.
 * <p>
 * Classes implementing from this interface will provide interpolated
 * values from two sequences of equal length, representing
 * discretized values of a variable and a function of the former,
 * respectively.
 *
 * @author Richard Gomes
 */
public interface Interpolation extends Extrapolator, Ops.DoubleOp {

    public boolean empty() /*@ReadOnly*/;

    public double op(final double x, boolean allowExtrapolation) /*@ReadOnly*/;
    public double primitive(final double x, boolean allowExtrapolation) /*@ReadOnly*/;
    public double derivative(final double x, boolean allowExtrapolation) /*@ReadOnly*/;
    public double secondDerivative(final double x, boolean allowExtrapolation) /*@ReadOnly*/;

    public double op(final double x) /*@ReadOnly*/;
    public double primitive(final double x) /*@ReadOnly*/;
    public double derivative(final double x) /*@ReadOnly*/;
    public double secondDerivative(final double x) /*@ReadOnly*/;

    public double xMin() /*@ReadOnly*/;
    public double xMax() /*@ReadOnly*/;

    public boolean isInRange(final double x) /*@ReadOnly*/;

    public void update();


    public interface Interpolator {

        public boolean global() /*@ReadOnly*/;
        public int requiredPoints() /*@ReadOnly*/;

        public Interpolation interpolate(final Array vx, final Array vy);

    }

}