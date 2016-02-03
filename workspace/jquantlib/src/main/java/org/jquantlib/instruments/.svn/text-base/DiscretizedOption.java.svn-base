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
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2004, 2005, 2006 StatPro Italia srl

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
package org.jquantlib.instruments;

import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.functions.Bind2ndPredicate;
import org.jquantlib.math.functions.FindIf;
import org.jquantlib.math.functions.GreaterEqualPredicate;
import org.jquantlib.math.matrixutilities.Array;

/**
 * Discretized option on a given asset
 * <p>
 * @warning it is advised that derived classes take care of creating and
 * initializing themselves an instance of the underlying.
 *
 * @author Srinivas Hasti
 */
public class DiscretizedOption extends DiscretizedAsset {

    protected Exercise.Type exerciseType;
    protected Array exerciseTimes;
    protected DiscretizedAsset underlying;

    public DiscretizedOption(
            final DiscretizedAsset underlying,
            final Exercise.Type exerciseType,
            final Array exerciseTimes) {
        this.underlying = underlying;
        this.exerciseType = exerciseType;
        this.exerciseTimes = exerciseTimes;
    }

    @Override
    public void reset(final int size) {
        QL.require(method().equals(underlying.method()) , "option and underlying were initialized on different methods");
        values_ = new Array(size);
        adjustValues();
    }

    @Override
    public List</* @Time */Double> mandatoryTimes() {
        final List</* @Time */Double> times = underlying.mandatoryTimes();

        // discard negative times...
        final Array array = new FindIf(exerciseTimes, new Bind2ndPredicate(new GreaterEqualPredicate(), 0.0)).op();
        // and add the positive ones
        for (int i=0; i< array.size(); i++) {
            times.add(array.get(i));
        }
        return times;
    }

    protected void applyExerciseCondition() {
        for (int i = 0; i < values_.size(); i++) {
            values_.set(i, Math.max(underlying.values().get(i), values_.get(i)));
        }
    }

    @Override
    public void postAdjustValuesImpl() {
        /*
         * In the real world, with time flowing forward, first any payment is
         * settled and only after options can be exercised. Here, with time
         * flowing backward, options must be exercised before performing the
         * adjustment.
         */
        underlying.partialRollback(time());
        underlying.preAdjustValues();
        int i;
        switch (exerciseType) {
        case American:
            if (time >= exerciseTimes.get(0) && time <= exerciseTimes.get(1)) {
                applyExerciseCondition();
            }
            break;
        case Bermudan:
        case European:
            for (i = 0; i < exerciseTimes.size(); i++) {
                final /* @Time */ double t = exerciseTimes.get(i);
                if (t >= 0.0 && isOnTime(t)) {
                    applyExerciseCondition();
                }
            }
            break;
        default:
            throw new LibraryException("invalid exercise type"); // TODO: message
        }
        underlying.postAdjustValues();
    }

}
