/*
 Copyright (C) 2009 Ueli Hofstetter

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
 Copyright (C) 2006 Banca Profilo S.p.A.

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
package org.jquantlib.processes;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;

/**
 * Forward-measure 1-D stochastic process
 * <p>
 * 1-D stochastic process whose dynamics are expressed in the forward measure.
 *
 * @category processes
 *
 * @author Ueli Hofstetter
 */
@QualityAssurance(quality=Quality.Q2_RESEMBLANCE, version=Version.V097, reviewers="Richard Gomes")
public abstract class ForwardMeasureProcess1D extends StochasticProcess1D {

    protected double T_;

    public ForwardMeasureProcess1D() {
    }

    protected ForwardMeasureProcess1D(final double T) {
        this.T_ = T;
    }

    protected ForwardMeasureProcess1D(final StochasticProcess1D.Discretization1D discretization) {
        super(discretization);
    }

    public /*@Time*/ double getForwardMeasureTime() /* @ReadOnly */ {
        return T_;
    }

    public void setForwardMeasureTime(final double T) {
        T_ = T;
        notifyObservers();
    }

}
