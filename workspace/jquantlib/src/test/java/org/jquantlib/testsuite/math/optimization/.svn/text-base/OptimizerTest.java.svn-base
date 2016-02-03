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

package org.jquantlib.testsuite.math.optimization;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.Constraint;
import org.jquantlib.math.optimization.CostFunction;
import org.jquantlib.math.optimization.EndCriteria;
import org.jquantlib.math.optimization.LevenbergMarquardt;
import org.jquantlib.math.optimization.NoConstraint;
import org.jquantlib.math.optimization.OptimizationMethod;
import org.jquantlib.math.optimization.Problem;
import org.jquantlib.math.optimization.Simplex;
import org.junit.Ignore;
import org.junit.Test;

public class OptimizerTest {

    private enum OptimizationMethodType {
        simplex, levenbergMarquardt, conjugateGradient, steepestDescent
    };


    @Ignore
    @Test
    public void testOptimizers() {

        System.out.println("::::: " + this.getClass().getSimpleName() + " :::::");
        System.out.println("Testing optimizers... ");

        // following block moved inside this method body
        final List<CostFunction> costFunctions_ = new ArrayList<CostFunction>();
        final List<Constraint> constraints_ = new ArrayList<Constraint>();
        final List<Array> initialValues_ = new ArrayList<Array>();
        final List<Integer> maxIterations_ = new ArrayList<Integer>();
        final List<Integer> maxStationaryStateIterations_ = new ArrayList<Integer>();
        final List<Double> rootEpsilons_ = new ArrayList<Double>();
        final List<Double> functionEpsilons_ = new ArrayList<Double>();
        final List<Double> gradientNormEpsilons_ = new ArrayList<Double>();
        final List<EndCriteria> endCriterias_ = new ArrayList<EndCriteria>();
        final List<List<OptimizationMethod>> optimizationMethods_ = new ArrayList<List<OptimizationMethod>>();
        final double [] xMinExpected = new double[1];
        final double [] yMinExpected = new double[1];

        // following block moved from setup() to here
        // keep stuff local... (move here from setup())
        // Cost function n. 1: 1D polynomial of degree 2 (parabolic function y=a*x^2+b*x+c)
        final double a = 1; // require a>0
        final double b = 1;
        final double c = 1;

        final List<Double> coefficients = new ArrayList<Double>();
        coefficients.add(c);
        coefficients.add(b);
        coefficients.add( a);
        xMinExpected[0] = -b/(2.0*a);
        yMinExpected[0] = -(b*b-4.0*a*c)/(4.0*a);


        //List<Array> yMinExpected_ = new ArrayList<Array>();
        final List<Double> xMinExpected_ = new ArrayList<Double>();
        final List<Double> yMinExpected_ = new ArrayList<Double>();
        xMinExpected_.add(xMinExpected[xMinExpected.length-1]);
        yMinExpected_.add(xMinExpected[yMinExpected.length-1]);
        costFunctions_.add(new OneDimensionalPolynomDegreeN(coefficients));
        // Set Constraint for optimizers: unconstrained problem
        constraints_.add(new NoConstraint());
        // Set initial guess for optimizer
        final Array initialValue = new Array(0);
        initialValue.add(-100.0);
        initialValues_.add(initialValue);
        // Set end criteria for optimizer
        maxIterations_.add(1000); // maxIterations
        maxStationaryStateIterations_.add(100); // MaxStationaryStateIterations
        rootEpsilons_.add(1e-8); // rootEpsilon
        functionEpsilons_.add(1e-16); // functionEpsilon
        gradientNormEpsilons_.add(1e-8); // gradientNormEpsilon
        endCriterias_.add(new EndCriteria(maxIterations_.get(maxIterations_.size() - 1), maxStationaryStateIterations_
                .get(maxStationaryStateIterations_.size() - 1), rootEpsilons_.get(rootEpsilons_.size() - 1), functionEpsilons_
                .get(functionEpsilons_.size() - 1), gradientNormEpsilons_.get(gradientNormEpsilons_.size() - 1)));
        // Set optimization methods for optimizer
        final OptimizationMethodType optimizationMethodTypes[] = { OptimizationMethodType.simplex };/* OptimizationMethodType.levenbergMarquardt};*/
        final double simplexLambda = 0.1;
        final double levenbergMarquardtEpsfcn = Math.pow(10, -0.8); //FIXME: how to write this as 1e-0.8???
        final double levenbergMarquardtXtol = Math.pow(10, -0.8); //FIXME: how to write this as 1e-0.8???
        final double levenbergMarquardtGtol = Math.pow(10, -0.8); //FIXME: how to write this as 1e-0.8???

        optimizationMethods_.add(makeOptimizationMethods(optimizationMethodTypes, simplexLambda, levenbergMarquardtEpsfcn, levenbergMarquardtXtol, levenbergMarquardtGtol));
        // Set expected results for optimizer

        for (int i=0; i<costFunctions_.size(); ++i) {
            final Problem problem = new Problem(costFunctions_.get(i), constraints_.get(i), initialValues_.get(i));
            for (int j=0; j<(optimizationMethods_.get(i)).size(); ++j) {
                final EndCriteria.Type endCriteriaResult = optimizationMethods_.get(i).get(j).minimize(problem, endCriterias_.get(i));
            final Array xMinCalculated = problem.currentValue();
            final Array yMinCalculated = problem.values(xMinCalculated);
            // Check optimizatin results vs known solution
            for (int k=0; k < xMinCalculated.size(); ++k) {
                //if(Math.abs(yMinExpected_.get(k)- yMinCalculated.get(k))> functionEpsilons_.get(i)){
                //if (std::fabs(yMinExpected_[k]- yMinCalculated[k]) > functionEpsilons_[i]) {
                if (true) {
                    System.out.println(
                            "costFunction = " + String.valueOf(i) + "\n"
                          + "optimizer =  " +  j + "\n"
                          + "    x expected:    " +  xMinExpected_.get(k) + "\n"
                          + "    x calculated:  " +  xMinCalculated.get(k) + "\n"
                          + "    x difference:  " +  ((Double)xMinExpected_.get(k)- xMinCalculated.get(k)) + "\n"
                          + "    rootEpsilon:   " +  rootEpsilons_.get(i) + "\n"
                          + "    y expected:    " +  yMinExpected_.get(k) + "\n"
                          + "    y calculated:  " +  yMinCalculated.get(k) + "\n"
                          + "    y difference:  " +  ((Double)yMinExpected_.get(k)- yMinCalculated.get(k)) + "\n"
                          + "    functionEpsilon:   " +  functionEpsilons_.get(i) + "\n"
                          + "    endCriteriaResult:  " + endCriteriaResult);
                    }

                }
            }
        }
    }


    private List<OptimizationMethod> makeOptimizationMethods(final OptimizationMethodType optimizationMethodTypes [],
            final double simplexLambda,
            final double levenbergMarquardtEpsfcn,
            final double levenbergMarquardtXtol,
            final double levenbergMarquardtGtol){
        final List<OptimizationMethod> results = new ArrayList<OptimizationMethod>();
        for(int i=0; i<optimizationMethodTypes.length; ++i) {
            results.add(makeOptimizationMethod(optimizationMethodTypes[i],
                    simplexLambda,
                    levenbergMarquardtEpsfcn,
                    levenbergMarquardtXtol,
                    levenbergMarquardtGtol));
        }
        return results;
    }

    private OptimizationMethod makeOptimizationMethod(final OptimizationMethodType optimizationMethodType,
            final double simplexLambda,
            final double levenbergMarquardtEpsfcn,
            final double levenbergMarquardtXtol,
            final double levenbergMarquardtGtol)
    {
        switch(optimizationMethodType){
        case simplex:
            return new Simplex(simplexLambda);
        case levenbergMarquardt:
            return new LevenbergMarquardt(levenbergMarquardtEpsfcn,
                    levenbergMarquardtXtol,
                    levenbergMarquardtXtol);
        default:
            throw new IllegalArgumentException("unknown Optimization Method type");
        }
    }


    private class OneDimensionalPolynomDegreeN extends CostFunction {

        private final List<Double> coefficients_;
        private final int polynominalDegree_;

        public OneDimensionalPolynomDegreeN(final List<Double> coefficients) {
            this.coefficients_ = coefficients;
            this.polynominalDegree_ = coefficients.size() - 1;
        }

        @Override
        public double value(final Array x) {
            if (x.size() != 1)
                throw new IllegalArgumentException("Independent variable must be 1 dimensional");
            double y = 0;
            for (int i = 0; i <= polynominalDegree_; ++i) {
                y += coefficients_.get(i) * Math.pow(x.first(), i);
            }
            return y;
        }

        @Override
        public Array values(final Array x) {
            if (x.size() != 1)
                throw new IllegalArgumentException("Independent variable must be 1 dimensional");
            final Array y = new Array(1);
            y.set(0, value(x));
            return y;
        }
    }
}
