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

package org.jquantlib.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.CompositeConstraint;
import org.jquantlib.math.optimization.Constraint;
import org.jquantlib.math.optimization.CostFunction;
import org.jquantlib.math.optimization.EndCriteria;
import org.jquantlib.math.optimization.OptimizationMethod;
import org.jquantlib.math.optimization.Problem;
import org.jquantlib.util.DefaultObservable;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;


/**
 * Calibrated model class
 *
 * @author Ueli Hofstetter
 */
@QualityAssurance(quality=Quality.Q3_DOCUMENTATION, version=Version.V097, reviewers="Richard Gomes")
public abstract class CalibratedModel implements Observer, Observable {

    private static final String parameter_array_to_small = "parameter array to small";
    private static final String parameter_array_to_big = "parameter array to big";


    //
    // protected fields
    //

    protected List< Parameter > arguments_;
    protected Constraint constraint_;
    protected EndCriteria.Type shortRateEndCriteria_;


    //
    // public methods
    //

    public CalibratedModel(final int nArguments) {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.arguments_ = new ArrayList<Parameter>(nArguments);
        this.constraint_ = new PrivateConstraint(arguments_);
        this.shortRateEndCriteria_ = EndCriteria.Type.None;
    }

    /**
     * <p>Calibrate to a set of market instruments (caps/swaptions). </p>
     * <p>An additional constraint can be passed which must be satisfied in addition to the constraints of the model. </p>
     */
    public void calibrate(
            final List<CalibrationHelper> instruments,
            final OptimizationMethod method,
            final EndCriteria endCriteria,
            final Constraint additionalConstraint,
            final double[] weights) {

        QL.require(weights==null || weights.length == instruments.size(),
        "mismatch between number of instruments and weights"); // TODO: message

        Constraint c;
        if (additionalConstraint.empty()) {
            c = constraint_;
        } else {
            c = new CompositeConstraint(constraint_, additionalConstraint);
        }

        final double[] w = new double[instruments.size()];
        if (weights==null) {
            Arrays.fill(w, 1.0);
        } else {
            System.arraycopy(weights, 0, w, 0, w.length);
        }

        final CalibrationFunction f = new CalibrationFunction(this, instruments, w);

        final Problem prob = new Problem(f, c, params());
        shortRateEndCriteria_ = method.minimize(prob, endCriteria);
        final Array result = new Array(prob.currentValue());
        setParams(result);
        final Array shortRateProblemValues_ = prob.values(result);

        notifyObservers();
    }

    public double value(final Array  params, final List<CalibrationHelper> instruments) {
        final double[] w = new double[instruments.size()];
        Arrays.fill(w, 1.0);
        final CalibrationFunction f = new CalibrationFunction(this, instruments, w);
        return f.value(params);
    }

    public final Constraint constraint() /* @ReadOnly */ {
        return this.constraint_;
    }

    /**
     * <p>returns end criteria result </p>
     */
    public EndCriteria.Type endCriteria() {
        return this.shortRateEndCriteria_;
    }

    /**
     * <p>Returns array of arguments on which calibration is done. </p>
     */
    public Array params() /* @ReadOnly */ {
        int size = 0;
        for (int i=0; i<arguments_.size(); i++) {
            size += arguments_.get(i).size();
        }
        final Array params = new Array(size);
        int k = 0;
        for (int i=0; i<arguments_.size(); i++) {
            for (int j=0; j<arguments_.get(i).size(); j++, k++) {
                final double value = arguments_.get(i).params().get(j);
                params.set(k, value);
            }
        }
        return params;
    }

    public void setParams(final Array params) {

// original C++ code:
//      Array::const_iterator p = params.begin();
//      for (Size i=0; i<arguments_.size(); ++i) {
//          for (Size j=0; j<arguments_[i].size(); ++j, ++p) {
//              QL_REQUIRE(p!=params.end(),"parameter array too small");
//              arguments_[i].setParam(j, *p);
//          }
//      }

        final double[] from = params.$;
        int pos = 0;
        for (int i=0; i<arguments_.size(); i++) {
            final double[] to = arguments_.get(i).params.$;
            System.arraycopy(from, pos, to, 0, to.length);
            pos += to.length;
        }

        QL.require(pos==params.size(), "parameter array too big"); // TODO: message
        update();
    }


    //
    // protected methods
    //

    protected void generateArguments() {
        // nothing
    }


    //
    // implements Observer
    //


//    @Override
//XXX::OBS    public void update(final Observable o, final Object arg) {
//        generateArguments();
//        notifyObservers();
//    }

    /**
     * This method must be implemented in derived classes.
     * <p>
     * An instance of Observer does not call this method directly: instead, it will be called by
     * the observables the instance registered with when they need to notify any changes.
     */
    @Override
    public void update(){
        generateArguments();
        notifyObservers();
    }



    //
    // implements Observable
    //

    private final DefaultObservable delegatedObservable = new DefaultObservable(this);

    @Override
    public void addObserver(final org.jquantlib.util.Observer observer) {
        delegatedObservable.addObserver(observer);

    }

    @Override
    public int countObservers() {
        return delegatedObservable.countObservers();
    }

    @Override
    public void deleteObserver(final org.jquantlib.util.Observer observer) {
        delegatedObservable.deleteObserver(observer);

    }

    @Override
    public void deleteObservers() {
        delegatedObservable.deleteObservers();
    }

    @Override
    public List<org.jquantlib.util.Observer> getObservers() {
        return delegatedObservable.getObservers();
    }

    @Override
    public void notifyObservers() {
        delegatedObservable.notifyObservers();
    }

    @Override
    public void notifyObservers(final Object arg) {
        delegatedObservable.notifyObservers(arg);

    }


    //
    // private inner classes
    //

    private final class CalibrationFunction extends CostFunction {

        private final CalibratedModel model;
        private final List<CalibrationHelper> instruments;
        private final double[] weights;

        public CalibrationFunction(
                final CalibratedModel model,
                final List<CalibrationHelper> instruments,
                final double[] weights){
            this.model = model;
            this.instruments = instruments;
            this.weights = weights.clone();
        }

        @Override
        public double value(final Array  params) /* @ReadOnly */ {
            model.setParams(params);

            double value = 0.0;
            for (int i=0; i<instruments.size(); i++) {
                final double diff = instruments.get(i).calibrationError();
                value += diff*diff*weights[i];
            }

            return Math.sqrt(value);
        }

        /**
         * <p>method to overload to compute the cost function values in x </p>
         */
        @Override
        public Array values(final Array  params) /* @ReadOnly */ {
            model.setParams(params);

            final Array values = new Array(instruments.size());
            for (int i=0; i<instruments.size(); i++) {
                final double value = instruments.get(i).calibrationError() * Math.sqrt(weights[i]);
                values.set(i, value);
            }
            return values;
        }

        /**
         * Default epsilon for finite difference method:
         */
        @Override
        public double finiteDifferenceEpsilon() /* @ReadOnly */ {
            return 1e-6;
        }
    }


    private final class PrivateConstraint extends Constraint {

        //
        // public constructors
        //

        public PrivateConstraint(final List<Parameter> arguments) {
            super.impl = new Impl(arguments);
        }


        //
        // private inner classes
        //

        /**
         * Base class for constraint implementations.
         */
        private class Impl extends Constraint.Impl {

            //
            // private fields
            //

            private final List<Parameter> arguments;


            //
            // private constructors
            //

            private Impl(final List<Parameter> arguments) {
                this.arguments = arguments;
            }


            //
            // public abstract methods
            //

            /**
             * Tests if params satisfy the constraint.
             */
            @Override
            public boolean test(final Array  params) /* @ReadOnly */ {
                int k = 0;
                for (int i = 0; i < arguments_.size(); i++) {
                    final int size = arguments_.get(i).size();
                    final Array testParams = new Array(size);
                    for (int j = 0; j < size; j++, k++) {
                        testParams.set(j, params.get(k));
                    }
                    if (!arguments_.get(i).testParams(testParams))
                        return false;
                }
                return true;
            }
        }
    }

}
