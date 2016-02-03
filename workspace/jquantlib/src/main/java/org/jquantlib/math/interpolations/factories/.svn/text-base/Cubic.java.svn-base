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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2004 StatPro Italia srl

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

package org.jquantlib.math.interpolations.factories;

import org.jquantlib.math.interpolations.CubicInterpolation;
import org.jquantlib.math.interpolations.Interpolation;
import org.jquantlib.math.interpolations.CubicInterpolation.BoundaryCondition;
import org.jquantlib.math.interpolations.CubicInterpolation.DerivativeApprox;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Cubic spline interpolation factory and traits.
 *
 * @see CubicInterpolation
 *
 * @author Richard Gomes
 * @author Daniel Kong
 */
public class Cubic implements Interpolation.Interpolator {
    private final DerivativeApprox da;
    private final BoundaryCondition leftType;
    private final BoundaryCondition rightType;
    private final double leftValue;
    private final double rightValue;
    private final boolean monotonic;

    public Cubic() {
        this(DerivativeApprox.Kruger, false, BoundaryCondition.SecondDerivative, 0.0, BoundaryCondition.SecondDerivative, 0.0);
    }

    public Cubic(
            final DerivativeApprox da,
            final boolean monotonic,
            final BoundaryCondition leftCondition,
            final double leftConditionValue,
            final BoundaryCondition rightCondition,
            final double rightConditionValue) {
        this.da = da;
        this.monotonic = monotonic;
        this.leftType = leftCondition;
        this.rightType = rightCondition;
        this.leftValue = leftConditionValue;
        this.rightValue = rightConditionValue;
    }

    @Override
    public final boolean global()     { return true; }

    @Override
    public final int requiredPoints() { return 2; }

    @Override
    public final Interpolation interpolate(final Array vx, final Array vy) /* @ReadOnly */ {
        return new CubicInterpolation(vx, vy, da, monotonic, leftType, leftValue, rightType, rightValue);
    }

}
