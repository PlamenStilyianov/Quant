/*
 Copyright (C) 2008 Srinivas Hasti

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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl

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

package org.jquantlib.methods.finitedifferences;

import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.Payoff;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Abstract base class which allows step conditions to use both payoff and array functions
 *
 * @author Richard Gomes
 */
//TODO: code review :: license, class comments, comments for access modifiers, put "final" everywhere
public class CurveDependentStepCondition implements StepCondition<Array> {

    public static interface CurveWrapper {
        double getValue(Array a, int i);
    }

    private final CurveWrapper curveItem;

    public CurveDependentStepCondition(final Option.Type type, final double strike) {
        curveItem = new PayoffWrapper(type, strike);
    }

    public CurveDependentStepCondition(final Payoff p) {
        curveItem = new PayoffWrapper(p);
    }

    public CurveDependentStepCondition(final Array a) {
        curveItem = new ArrayWrapper(a);
    }

    protected double applyToValue(final double a, final double b) {
        throw new LibraryException("not yet implemented"); // TODO: message
    }

    @Override
    public void applyTo(final Array a, final double t) {
        for (int i = 0; i < a.size(); i++) {
            a.set(i, applyToValue(a.get(i), getValue(a, i)));
        }
    }

    protected double getValue(final Array a, final int index) {
        return curveItem.getValue(a, index);
    }

    static class ArrayWrapper implements CurveWrapper {
        private final Array values;

        public ArrayWrapper(final Array values) {
            this.values = values;
        }

        public double getValue(final Array a, final int i) {
            return values.get(i);
        }
    };

    static class PayoffWrapper implements CurveWrapper {
        private final Payoff payoff;

        public PayoffWrapper(final Payoff p) {
            this.payoff = p;
        }

        public PayoffWrapper(final Option.Type type, final double strike) {
            payoff = new PlainVanillaPayoff(type, strike);
        }

        public double getValue(final Array a, final int i) {
            return payoff.get(a.get(i));
        }
    };
}