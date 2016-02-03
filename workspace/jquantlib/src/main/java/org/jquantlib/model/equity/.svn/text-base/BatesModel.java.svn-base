/*
Copyright (C)
2009 Ueli Hofstetter

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

package org.jquantlib.model.equity;

import org.jquantlib.math.optimization.NoConstraint;
import org.jquantlib.math.optimization.PositiveConstraint;
import org.jquantlib.model.ConstantParameter;
import org.jquantlib.processes.HestonProcess;

/**
 * 
 * @author Ueli Hofstetter
 *
 */
public class BatesModel extends HestonModel {

    public BatesModel(final HestonProcess process, final double lambda, final double nu, final double delta) {
        super(process);
        arguments_.set(5, new ConstantParameter(nu, new NoConstraint()));
        arguments_.set(6, new ConstantParameter(delta, new PositiveConstraint()));
        arguments_.set(7, new ConstantParameter(lambda, new PositiveConstraint()));

        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
    }

    public BatesModel(final HestonProcess process) {
        this(process, 0.1, 0.0, 0.1);
    }


    public double nu() {
        return arguments_.get(5).get(0.0);
    }

    public double delta() {
        return arguments_.get(6).get(0.0);
    }

    public double lambda() {
        return arguments_.get(7).get(0.0);
    }

    private static class BatesDetJumpModel extends BatesModel {

        public BatesDetJumpModel(final HestonProcess process) {
            this(process, 0.1, 0.0, 0.1, 1.0, 0.1);
        }

        public BatesDetJumpModel(final HestonProcess process, final double lambda, final double nu, final double delta, final double kappaLambda,
                final double thetaLambda) {
            super(process);
            arguments_.set(8, new ConstantParameter(kappaLambda, new PositiveConstraint()));
            arguments_.set(9, new ConstantParameter(thetaLambda, new PositiveConstraint()));
        }

        public double kappaLambda() {
            return arguments_.get(8).get(0.0);
        }

        public double thethaLambda() {
            return arguments_.get(9).get(0.0);
        }

    }

}
