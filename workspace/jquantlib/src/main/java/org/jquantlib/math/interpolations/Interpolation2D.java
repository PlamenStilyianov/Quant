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
 Copyright (C) 2002, 2003, 2006 Ferdinando Ametrano
 Copyright (C) 2004, 2005, 2006, 2007 StatPro Italia srl

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
import org.jquantlib.math.matrixutilities.Matrix;

/**
 * Interface for 2-D interpolations.
 * <p>
 * Classes implementing this interface will provide interpolated
 * values from two sequences of length {@latex$ N } and {@latex$ M },
 * representing the discretized values of the {@latex$ x } and {@latex$ y }
 * variables, and a {@latex$ N \times M } matrix representing
 * the tabulated function values.
 *
 * @author Richard Gomes
 */
public interface Interpolation2D extends Extrapolator, Ops.BinaryDoubleOp {

    public boolean empty() /*@ReadOnly*/;

    public double op(final double x, double y, boolean allowExtrapolation) /*@ReadOnly*/;
    public double op(final double x, double y) /*@ReadOnly*/;

    public double xMin() /*@ReadOnly*/;
    public double xMax() /*@ReadOnly*/;
    public double yMin() /*@ReadOnly*/;
    public double yMax() /*@ReadOnly*/;

//XXX
//  public Array xValues() /*@ReadOnly*/;
//  public Array yValues() /*@ReadOnly*/;
//  public Matrix zData() /*@ReadOnly*/;

    public int locateX(double x) /*@ReadOnly*/;
    public int locateY(double y) /*@ReadOnly*/;

    public boolean isInRange(final double x, double y) /*@ReadOnly*/;

    public void update();


    public interface Interpolator2D {

        public Interpolation2D interpolate(final Array vx, final Array vy, final Matrix mZ);

    }

}
