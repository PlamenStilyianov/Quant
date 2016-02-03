/*
 Copyright (C) 2007 Dominik Holenstein

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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006 StatPro Italia srl
 Copyright (C) 2004 Ferdinando Ametrano

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

package org.jquantlib.math;

import org.jquantlib.math.matrixutilities.Array;


/**
 * @author Dominik Holenstein
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: remove statics
public final class Grid {

    private Grid() {
        // avoid creation of this class
    }

    final static public Array CenteredGrid(double center, double dx, int steps) {
        final Array result = new Array(steps + 1);
        for (int i = 0; i < steps + 1; i++) {
            result.set(i, center + (i - steps / 2.0) * dx);
        }
        return result;
    }

    final static public Array BoundedGrid(double xMin, double xMax, int steps) {
        final Array result = new Array(steps + 1);
        final double dx = (xMax - xMin) / steps;
        double x = xMin;
        for (int i = 0; i < steps + 1; i++, x += dx) {
            result.set(i, x);
        }
        return result;
    }

    final static public Array BoundedLogGrid(double xMin, double xMax, int steps) {
        final Array result = new Array(steps + 1);
        final double gridLogSpacing = (Math.log(xMax) - Math.log(xMin)) / (steps);
        final double edx = Math.exp(gridLogSpacing);
        result.set(0, xMin);
        for (int j = 1; j < steps + 1; j++) {
            result.set(j, result.get(j - 1) * edx);
        }
        return result;
    }

}
