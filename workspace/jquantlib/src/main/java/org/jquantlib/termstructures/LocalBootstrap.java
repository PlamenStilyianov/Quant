/*
Copyright (C) 2011 Richard Gomes

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


package org.jquantlib.termstructures;

import java.util.Arrays;

import org.jquantlib.QL;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.Constraint;
import org.jquantlib.math.optimization.CostFunction;
import org.jquantlib.math.optimization.EndCriteria;
import org.jquantlib.math.optimization.LevenbergMarquardt;
import org.jquantlib.math.optimization.NoConstraint;
import org.jquantlib.math.optimization.PositiveConstraint;
import org.jquantlib.termstructures.yieldcurves.PiecewiseYieldCurve;
import org.jquantlib.time.Date;


/**
 * Localised-term-structure bootstrapper for most curve types.
 * <p>
 * This algorithm enables a localised fitting for non-local
    interpolation methods.

    As in the similar class (IterativeBootstrap) the input term
    structure is solved on a number of market instruments which
    are passed as a vector of handles to BootstrapHelper
    instances. Their maturities mark the boundaries of the
    interpolated segments.

    Unlike the IterativeBootstrap class, the solution for each
    interpolated segment is derived using a local
    approximation. This restricts the risk profile s.t.  the risk
    is localised. Therefore, we obtain a local IR risk profile
    whilst using a smoother interpolation method. Particularly
    good for the convex-monotone spline method.
*/


//FIXME: This class needs full code review


public class LocalBootstrap<Curve extends PiecewiseYieldCurve> {

    
    //    typedef typename Curve::traits_type Traits;
    //    typedef typename Curve::interpolator_type Interpolator;


    //
    // private final fields
    //
    
    private Curve ts_;
    private final /*Size*/ int localisation_;
    private final boolean forcePositive_;
    
    
    //
    // private fields
    //
    
    private boolean validCurve_;

    public LocalBootstrap() {
        this(2, true);
    }

    public LocalBootstrap(/*Size*/ final int localisation) {
        this(localisation, true);
    }

    public LocalBootstrap(/*Size*/ final int localisation, final boolean forcePositive) {
        
        QL.validateExperimentalMode();
        
        this.validCurve_ = false;
        this.ts_ = null;
        this.localisation_ = localisation;
        this.forcePositive_ = forcePositive;
    }


    public void setup(final Curve ts) {
        this.ts_ = ts;
        /*Size*/ final int n = ts_.instruments().length;
        
        QL.require(n >= ts.interpolator().requiredPoints(),
                "not enough instruments: %d provided, %d required",
                n, ts.interpolator().requiredPoints());
        QL.require(n > localisation_,
                "not enough instruments: %d provided, %d required.",
                n, localisation_);
        
        for (/*Size*/ int i=0; i<n; ++i) {
            ts_.instruments()[i].addObserver(ts_);
        }
    }


    public void calculate() /*@ReadOnly*/ {
        validCurve_ = false;
        final /*Size*/ int nInsts = ts_.instruments().length;

        // ensure rate helpers are sorted
        Arrays.sort(ts_.instruments(), new BootstrapHelperSorter());

        // check that there is no instruments with the same maturity
        for (/*Size*/ int i=1; i<nInsts; ++i) {
            final Date m1 = ts_.instruments()[i-1].latestDate();
            final Date m2 = ts_.instruments()[i].latestDate();
            QL.require(m1 != m2, "two instruments have the same maturity");
        }

        // check that there is no instruments with invalid quote
        for (/*Size*/ int i=0; i<nInsts; ++i)
            QL.require(ts_.instruments()[i].quoteIsValid(),
                    " instrument #%d (maturity: %s) has infalic quote",
                    i+1, ts_.instruments()[i].latestDate());

        // setup instruments
        for (/*Size*/ int i=0; i<nInsts; ++i) {
            // don't try this at home!
            // This call creates instruments, and removes "const".
            // There is a significant interaction with observability.
            ts_.instruments()[i].setTermStructure(ts_); // const_cast<Curve*>(ts_)
        }
        // set initial guess only if the current curve cannot be used as guess
        if (validCurve_)
            QL.ensure(ts_.data().length == nInsts+1,
                      "dimension mismatch: expected %d, actual %d",
                      nInsts+1, ts_.data().length);
        else {
            final double[] data = new double[nInsts+1];
            data[0] = ts_.traits().initialValue(ts_);
            ts_.setData(data);
        }

        // calculate dates
        {
            final Date[] dates = new Date[nInsts+1];
            dates[0] = ts_.traits().initialDate(ts_);
            ts_.setDates(dates);
        }
        
        // calculate times
        {
            final double[] times = new double[nInsts+1];
            times[0] = ts_.timeFromReference(ts_.dates()[0]);
            ts_.setTimes(times);
        }
        
        for (/*Size*/ int i=0; i<nInsts; ++i) {
            ts_.dates()[i+1] = ts_.instruments()[i].latestDate();
            ts_.times()[i+1] = ts_.timeFromReference(ts_.dates()[i+1]);
            if (!validCurve_)
                ts_.data()[i+1] = ts_.data()[i];
        }
        
        
        
        

        final LevenbergMarquardt solver = new LevenbergMarquardt(ts_.accuracy(), ts_.accuracy(), ts_.accuracy());
        final EndCriteria endCriteria = new EndCriteria(100, 10, 0.00, ts_.accuracy(), 0.00);
        final Constraint solverConstraint = forcePositive_ ? new PositiveConstraint() : new NoConstraint();

        // now start the bootstrapping.
        /*Size*/ final int iInst = localisation_-1;

        
        //=========================================================================
        
        //FIXME: uncomment and review this block
        
        
//        /*Size*/ final int dataAdjust = ts_.interpolator().dataSizeAdjustment;
//
//        do {
//            /*Size*/ final int initialDataPt = iInst+1-localisation_+dataAdjust;
//            final double[] startArray = new double[localisation_+1-dataAdjust];
//            for (/*Size*/ int j = 0; j < startArray.length-1; ++j)
//                startArray[j] = ts_.data()[initialDataPt+j];
//
//            // here we are extending the interpolation a point at a
//            // time... but the local interpolator can make an
//            // approximation for the final localisation period.
//            // e.g. if the localisation is 2, then the first section
//            // of the curve will be solved using the first 2
//            // instruments... with the local interpolator making
//            // suitable boundary conditions.
//
//            
//            ts_.setInterpolation(
//                ts_.interpolator().localInterpolate(
//                                              ts_.times_.begin(),
//                                              ts_.times_.begin()+(iInst + 2),
//                                              ts_.data_.begin(),
//                                              localisation_,
//                                              ts_.interpolation_,
//                                              nInsts+1) );
//
//            if (iInst >= localisation_) {
//                startArray[localisation_-dataAdjust] = ts_.traits().guess(ts_, ts_.dates()[iInst]);
//            } else {
//                startArray[localisation_-dataAdjust] = ts_.data()[0];
//            }
//
//            final PenaltyFunction currentCost = new PenaltyFunction(
//                        ts_,
//                        initialDataPt,
//                        ts_.instruments().begin() + (iInst - localisation_+1),
//                        ts_.instruments().begin() + (iInst+1));
//
//            final Problem toSolve = new Problem(currentCost, solverConstraint, startArray);
//
//            final EndCriteria.Type endType = solver.minimize(toSolve, endCriteria);
//
//            // check the end criteria
//            QL.require(endType == EndCriteria.Type.StationaryFunctionAccuracy ||
//                       endType == EndCriteria.Type.StationaryFunctionValue,
//                       "Unable to strip yieldcurve to required accuracy " );
//
//            ++iInst;
//        } while ( iInst < nInsts );
        //=========================================================================
        
        
        validCurve_ = true;
    }


    //
    // inner classes
    //

    private class PenaltyFunction extends CostFunction {
        private final int initialIndex;
        private final int rateHelpersStart;
        private final int rateHelpersEnd;
        private final int penaltylocalisation;

        private PenaltyFunction(final int initialIndex, final int rateHelpersStart, final int rateHelpersEnd) {
            this.initialIndex = initialIndex;
            this.rateHelpersStart = rateHelpersStart;
            this.rateHelpersEnd = rateHelpersEnd;
            this.penaltylocalisation = rateHelpersEnd - rateHelpersStart;
        }

        @Override
        public double value(final Array x) {
            int i = initialIndex;
            int guessIt = 0;
            for (; guessIt < x.size(); ++guessIt, ++i) {
                ts_.traits().updateGuess(ts_.data(), guessIt, i);
            }

            ts_.interpolation().update();

            double penalty = 0.0;
            int j = rateHelpersStart;
            for (; j != rateHelpersEnd; ++j) {
                penalty += Math.abs(ts_.instruments()[j].quoteError());
            }
            return penalty;
        }

        @Override
        public Array values(final Array x) {
            int guessIt = 0;
            for (int i = initialIndex; guessIt < x.size(); ++guessIt, ++i) {
                ts_.traits().updateGuess(ts_.data(), x.get(guessIt), i);
            }
            ts_.interpolation().update();
            final Array penalties = new Array(penaltylocalisation);
            int instIterator = rateHelpersStart;
            for (int penIt = 0; instIterator != rateHelpersEnd; ++instIterator, ++penIt) {
                penalties.set(penIt, Math.abs(ts_.instruments()[instIterator].quoteError()));
            }
            return penalties;
        }
    }

}
