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
package org.jquantlib.math.optimization;

import org.jquantlib.math.matrixutilities.Array;

public class LineSearch {

    private final static String CANNOT_UPDATE_LINESEARCH = "cannot update linesearch";

    // current values of the search direction
    protected Array searchDirection_;
    // new x and its gradient
    protected Array xtd_, gradient_;
    // cost function value and gradient norm corresponding to xtd_
    protected double qt_, qpt_;
    // flag to know if linesearch succed
    protected boolean succeed_;

    // Default constructor - there are no default param values in java :-(
    public LineSearch(){
        this(0.0);
    }

    // Default constructor
    public LineSearch(final double init){
        qt_ = init;
        qpt_= init;
        succeed_ = true;

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }

    // return last x value
    public Array lastX(){
        return xtd_;
    }

    // return last cost function value
    public double lastFunctionValue(){
        return qt_;
    }

    // return last gradient
    public Array lastGradient(){
        return gradient_;
    }

    // return square norm of last gradient
    public double lastGradientNormNorm2(){
        return qpt_;
    }

    // current value of the search direction
    public Array searchDirection(){
        return searchDirection_;
    }

    //FIXME: to be reviewed.
    // Perform line search
    public double evaluate(final Problem P, final EndCriteria.Type ecType, final EndCriteria endCriteria, final double t_ini){
        throw new UnsupportedOperationException("Work in progress");
    }

    public double update(final Array params, final Array direction, final double beta, final Constraint constraint){
        double diff = beta;
        //TODO: check whether we implemented overloaded c++ operators correctly
        Array newParams = params.add(direction.mul(diff));
        boolean valid = constraint.test(newParams);
        int icount = 0;
        while(!valid){
            if(icount > 200)
                throw new ArithmeticException("can't update lineSearch");
            diff *= 0.5;
            icount++;
            newParams = params.add(direction.mul(diff));
            valid = constraint.test(newParams);
        }

        params.add(direction.mul(diff));
        return diff;
    }

    public void setSearchDirection(final Array searchDirection){
        this.searchDirection_ = searchDirection;
    }



}
