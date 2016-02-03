/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.pricingengines.vanilla;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.instruments.DiscretizedAsset;
import org.jquantlib.instruments.VanillaOption;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.processes.StochasticProcess;
import org.jquantlib.time.TimeGrid;

/**
 * Discretized vanilla option
 *
 * @author Srinivas Hasti
 */
public class DiscretizedVanillaOption extends DiscretizedAsset {

    //
    // private final fields
    //

    final private StochasticProcess process;
    final private VanillaOption.ArgumentsImpl a;
    // final private VanillaOption.ResultsImpl r;
    private final List<Double> stoppingTimes;


    //
    // public constructors
    //

    public DiscretizedVanillaOption(
            final VanillaOption.Arguments arguments,
            final StochasticProcess process) {
        this(arguments, process, new TimeGrid());
    }

    public DiscretizedVanillaOption(
            final VanillaOption.Arguments arguments,
            final StochasticProcess process,
            final TimeGrid grid) {
        this.a = (VanillaOption.ArgumentsImpl) arguments;
        this.process = process;
        final int size = a.exercise.size();
        this.stoppingTimes = new ArrayList<Double>();
        for (int i = 0; i < size; ++i) {
            stoppingTimes.add(i, process.time(a.exercise.date(i)));
            if (!grid.empty()) {
                // adjust to the given grid
                stoppingTimes.add(i, grid.closestTime(stoppingTimes.get(i)));
            }
        }
    }


    //
    // private methods
    //

    private void applySpecificCondition() {
        final Array grid = method().grid(time());
        for (int j = 0; j < values_.size(); j++) {
            values_.set(j, Math.max(values_.get(j), a.payoff.get(grid.get(j))));
        }
    }


    //
    // overrides DiscretizedAsset
    //

    @Override
    public void reset(final int size) {
        values_ = new Array(size);
        adjustValues();
    }

    @Override
    public List<Double> mandatoryTimes() /* @ReadOnly */ {
        return stoppingTimes;
    }

    @Override
    protected void postAdjustValuesImpl() {
        final double now = time();
        switch (a.exercise.type()) {
        case American:
            if (now <= stoppingTimes.get(1) && now >= stoppingTimes.get(0)) {
                applySpecificCondition();
            }
            break;
        case European:
            if (isOnTime(stoppingTimes.get(0))) {
                applySpecificCondition();
            }
            break;
        case Bermudan:
            for (int i = 0; i < stoppingTimes.size(); i++)
                if (isOnTime(stoppingTimes.get(i))) {
                    applySpecificCondition();
                }
            break;
        default:
            throw new LibraryException("invalid option type"); // QA:[RG]::verified
        }
    }

}
