/*
 Copyright (C) 2007 Richard Gomes

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


package org.jquantlib.time;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.NonNegative;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.lang.iterators.Iterables;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.matrixutilities.Array;


/**
 * TimeGrid class.
 *
 * @author Dominik Holenstein
 */
// TODO: Taken over from QuantLib: What was the rationale for limiting the grid to positive times?
// Investigate and see whether we can use it for negative ones as well.
public class TimeGrid {

    //
    // private fields
    //
    private final Array times;
    private final Array dt;
    private final Array mandatoryTimes;



    //
    // Constructors
    //

    public TimeGrid() {
        this.times = null; //XXX  new Array();
        this.dt = null; //XXX: new Array();
        this.mandatoryTimes = null; //XXX: new Array();
    }

    /**
     * Regularly spaced time-grid
     *
     * @param end
     * @param steps
     */
    public TimeGrid(@Time @NonNegative final double end, @NonNegative final int steps) {

        // THIS COMMENT COMES FROM QuantLib/C++ code
        //
        // We seem to assume that the grid begins at 0.
        // Let's enforce the assumption for the time being
        // (even though I'm not sure that I agree.)
        QL.require(end > 0.0 , "negative times not allowed"); // QA:[RG]::verified // FIXME: message

        /*@Time*/ final double dt = end/steps;
        this.times = new Array(steps+1);
        for (int i=0; i<=steps; i++) {
            times.set(i, dt*i);
        }
        this.mandatoryTimes = new Array(1).fill(end);
        this.dt = new Array(steps).fill(dt);
    }


    /**
     * Time grid with mandatory time points.
     * <p>
     * Mandatory points are guaranteed to belong to the grid. No additional points are added.
     *
     * @note This constructor is not available yet
     *
     * @param list
     */
    public TimeGrid(@Time @NonNegative final Array mandatoryTimes) {

        if (System.getProperty("EXPERIMENTAL")==null)
            throw new UnsupportedOperationException("This constructor is not available yet");

        //XXX this.mandatoryTimes = mandatoryTimes.clone();
        this.mandatoryTimes = mandatoryTimes;
        this.mandatoryTimes.sort();

        // THIS COMMENT COMES FROM QuantLib/C++ code
        //
        // We seem to assume that the grid begins at 0.
        // Let's enforce the assumption for the time being
        // (even though I'm not sure that I agree.)
        QL.require(mandatoryTimes.first() < 0.0 , "negative times not allowed"); // TODO: message

        final List<Double> unique = new ArrayList<Double>();
        double prev = this.mandatoryTimes.get(0);
        unique.add(prev);
        for (int i=1; i<this.mandatoryTimes.size(); i++) {
            final double curr = this.mandatoryTimes.get(i);
            if (! Closeness.isCloseEnough(prev, curr)) {
                unique.add(curr);
            }
            prev = curr;
        }

        this.times = new Array(unique.size());
        int i=0;
        for (final double d : Iterables.unmodifiableIterable(unique.iterator())) {
            this.times.set(i, d);
            i++;
        }
        this.dt = this.times.adjacentDifference();
    }



    /**
     * Time grid with mandatory time points
     * <p>
     * Mandatory points are guaranteed to belong to the grid.
     * Additional points are then added with regular spacing between pairs of mandatory times in order
     * to reach the desired number of steps.
     *
     * @note This constructor is not available yet - fix adjacent_difference before using
     */
    //TODO: needs code review
//    template <class Iterator>
//    TimeGrid(Iterator begin, Iterator end, Size steps)
//    : mandatoryTimes_(begin, end) {
//        std::sort(mandatoryTimes_.begin(),mandatoryTimes_.end());
//        // We seem to assume that the grid begins at 0.
//        // Let's enforce the assumption for the time being
//        // (even though I'm not sure that I agree.)
//        QL_REQUIRE(mandatoryTimes_.front() >= 0.0,
//                   "negative times not allowed");
//        std::vector<Time>::iterator e =
//            std::unique(mandatoryTimes_.begin(),mandatoryTimes_.end(),
//                        std::ptr_fun(close_enough));
//        mandatoryTimes_.resize(e - mandatoryTimes_.begin());
//
//        Time last = mandatoryTimes_.back();
//        Time dtMax;
//        // The resulting timegrid have points at times listed in the input
//        // list. Between these points, there are inner-points which are
//        // regularly spaced.
//        if (steps == 0) {
//            std::vector<Time> diff;
//            std::adjacent_difference(mandatoryTimes_.begin(),
//                                     mandatoryTimes_.end(),
//                                     std::back_inserter(diff));
//            if (diff.front()==0.0)
//                diff.erase(diff.begin());
//            dtMax = *(std::min_element(diff.begin(), diff.end()));
//        } else {
//            dtMax = last/steps;
//        }
//
//        Time periodBegin = 0.0;
//        times_.push_back(periodBegin);
//        for (std::vector<Time>::const_iterator t=mandatoryTimes_.begin();
//                                               t<mandatoryTimes_.end();
//                                               t++) {
//            Time periodEnd = *t;
//            if (periodEnd != 0.0) {
//                // the nearest integer
//                Size nSteps = Size((periodEnd - periodBegin)/dtMax+0.5);
//                // at least one time step!
//                nSteps = (nSteps!=0 ? nSteps : 1);
//                Time dt = (periodEnd - periodBegin)/nSteps;
//                times_.reserve(nSteps);
//                for (Size n=1; n<=nSteps; ++n)
//                    times_.push_back(periodBegin + n*dt);
//            }
//            periodBegin = periodEnd;
//        }
//
//        std::adjacent_difference(times_.begin()+1,times_.end(),
//                                 std::back_inserter(dt_));
//    }


    public @NonNegative int index(@Time @NonNegative final double t) /* @ReadOnly */ {
        final @NonNegative int i = closestIndex(t);
        if (Closeness.isCloseEnough(t, times.get(i)))
            return i;
        else if (t < front())
            throw new IllegalArgumentException(
                    "using inadequate time grid: all nodes are later than the required time t = "
                    + t + " (earliest node is t1 = " + times.first() + ")" );
        else if (t > back())
            throw new IllegalArgumentException(
                    "using inadequate time grid: all nodes are earlier than the required time t = "
                    + t + " (latest node is t1 = " + back() + ")" );
        else {
            /*@NonNegative*/ int j, k;
            if (t > times.get(i)) {
                j = i;
                k = i+1;
            } else {
                j = i-1;
                k = i;
            }
            throw new IllegalArgumentException(
                    "using inadequate time grid: the nodes closest to the required time t = "
                    + t + " are t1 = " + times.get(j) + " and t2 = " + times.get(k) );
        }
    }


    public @NonNegative int closestIndex(@Time @NonNegative final double t) /* @ReadOnly */ {
        final int size = times.size();
        final int result = times.lowerBound(t);

        if (result == 0)
            return 0;
        else if (result == size)
            return size-1;
        else {
            final @Time double dt1 = times.get(result) - t;
            final @Time double dt2 = t - times.get(result-1);
            if (dt1 < dt2)
                return result;
            else
                return result-1;
        }
    }

    /**
     * @return the time on the grid closest to the given t
     */
    public @Time double closestTime (@Time @NonNegative final double t) /*@Readonly*/ {
        return times.get(closestIndex(t));
    }

    public final Array mandatoryTimes() /*@Readonly*/ {
        // TODO: code review :: use of clone()
        return mandatoryTimes.clone();
    }

    public double dt (final int i) /*@Readonly*/ {
        return dt.get(i);
    }

    public double get(final int i) /*@Readonly*/ {
        return times.get(i);
    }

    public double at(final int i) /*@Readonly*/ {
        return times.get(i);
    }

    public int size() /*@Readonly*/ {
        return times.size();
    }

    public boolean empty() /*@Readonly*/ {
        return times.size() == 0;
    }

    public double begin() /*@Readonly*/ {
        return times.first();
    }

    public double end() /*@Readonly*/ {
        return times.last();
    }

    public double front() /*@Readonly*/ {
        return times.first();
    }

    public double back() /*@Readonly*/ {
        return times.last();
    }

}
