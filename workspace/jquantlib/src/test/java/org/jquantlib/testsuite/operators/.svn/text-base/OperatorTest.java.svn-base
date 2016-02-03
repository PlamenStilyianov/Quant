/*
 Copyright (C) 2008 Richard Gomes

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
 * @Author Tim Swetonic
 */

package org.jquantlib.testsuite.operators;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.math.distributions.NormalDistribution;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.finitedifferences.BSMOperator;
import org.jquantlib.methods.finitedifferences.BSMTermOperator;
import org.jquantlib.methods.finitedifferences.DPlusMinus;
import org.jquantlib.methods.finitedifferences.DZero;
import org.jquantlib.methods.finitedifferences.TridiagonalOperator;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.testsuite.util.Utilities;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.junit.Test;


public class OperatorTest {

    public OperatorTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    @Test
    public void testConsistency() {

        QL.info("Testing differential operators...");

        final double average = 0.0, sigma = 1.0;

        final NormalDistribution normal = new NormalDistribution(average,sigma);
        final CumulativeNormalDistribution cum = new CumulativeNormalDistribution(average,sigma);

        final double xMin = average - 4*sigma,
        xMax = average + 4*sigma;
        final int N = 10001;
        final double h = (xMax-xMin)/(N-1);

        final Array x = new Array(N), y = new Array(N), yi = new Array(N), yd = new Array(N);
        Array temp = new Array(N);
        final Array diff = new Array(N);

        int i;
        for(i=0; i < x.size(); i++) {
            x.set(i, xMin+h*i);
        }

        for(i = 0; i < x.size(); i++) {
            y.set(i, normal.op(x.get(i)));
        }

        for(i = 0; i < x.size(); i++) {
            yi.set(i, cum.op(x.get(i)));
        }

        for (i=0; i < x.size(); i++) {
            yd.set(i, normal.derivative(x.get(i)));
        }

        // define the differential operators
        final DZero D = new DZero(N,h);
        final DPlusMinus D2 = new DPlusMinus(N,h);

        // check that the derivative of cum is Gaussian
        temp = D.applyTo(yi);
        for (i=0; i < y.size(); i++) {
            diff.set(i, y.get(i) - temp.get(i));
        }

        double e = Utilities.norm(diff, h);


        if (e > 1.0e-6) {
            fail("norm of 1st derivative of cum minus Gaussian: " + e + "\ntolerance exceeded");
        }

        // check that the second derivative of cum is normal.derivative
        temp = D2.applyTo(yi);
        for (i=0; i < yd.size(); i++) {
            diff.set(i, yd.get(i) - temp.get(i));
        }
        e = Utilities.norm(diff, h);
        if (e > 1.0e-4) {
            fail("norm of 2nd derivative of cum minus Gaussian derivative: " + e + "\ntolerance exceeded");
        }


    }
    public void dumpArray(final Array arr) {
        for(int i = 0; i < arr.size(); i++) {
            QL.info("**** arr[" + i + "] = " + arr.get(i) );
        }
    }

    public void outputDiagonals(final TridiagonalOperator op) {
        QL.info("\n");

        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("[ ");
        Array data = op.lowerDiagonal();
        for(int i = 0; i < data.size(); i++) {
            sb.append(String.format(" %.4f ", data.get(i)));
        }
        sb.append(" ]");
        QL.info(sb.toString());

        sb = new StringBuilder();
        sb.append("[ ");
        data = op.diagonal();
        for(int i = 0; i < data.size(); i++) {
            sb.append(String.format(" %.4f ", data.get(i)));
        }
        sb.append(" ]");
        QL.info(sb.toString());

        sb = new StringBuilder();
        sb.append("[ ");
        data = op.upperDiagonal();
        for(int i = 0; i < data.size(); i++) {
            sb.append(String.format(" %.4f ", data.get(i)));
        }
        sb.append(" ]");
        QL.info(sb.toString());
    }

    @Test
    public void testBSMOperatorConsistency() throws Exception {
        QL.info("Testing consistency of BSM operators...");

        final Array grid = new Array(10);
        double price = 20.0;
        final double factor = 1.1;
        int i;
        for (i = 0; i < grid.size(); i++) {
            grid.set(i, price);
            price *= factor;
        }

        final double dx = Math.log(factor);
        final double r = 0.05;
        final double q = 0.01;
        final double sigma = 0.5;

        final BSMOperator ref = new BSMOperator(grid.size(), dx, r, q, sigma);
        QL.info("BSMOperator reference diagonals: \n");
        outputDiagonals(ref);

        final DayCounter dc = new Actual360();

        final Date today = Date.todaysDate();

        Date exercise = today.clone();
        exercise = exercise.add(new Period(2,TimeUnit.Years));


        final double residualTime = dc.yearFraction(today, exercise);

        final SimpleQuote spot = new SimpleQuote(0.0);
        final YieldTermStructure qTS = Utilities.flatRate(today, q, dc);
        final YieldTermStructure rTS = Utilities.flatRate(today, r, dc);

        final BlackVolTermStructure volTS = Utilities.flatVol(today, sigma, dc);

        final GeneralizedBlackScholesProcess stochProcess = new GeneralizedBlackScholesProcess(
                new Handle<Quote>(spot),
                new Handle<YieldTermStructure>(qTS),
                new Handle<YieldTermStructure>(rTS),
                new Handle<BlackVolTermStructure>(volTS));

        final BSMOperator op1 = new BSMOperator(grid, stochProcess, residualTime);
        QL.info("BSMOperator diagonals: \n");
        outputDiagonals(op1);

        final BSMTermOperator op2 = new BSMTermOperator(grid, stochProcess, residualTime);

        QL.info("PdeOperator diagonals: \n");
        outputDiagonals(op2);

        final double tolerance = 1.0e-6;

        Array lderror = ref.lowerDiagonal().clone();
        lderror.subAssign(op1.lowerDiagonal());
        Array derror = ref.diagonal().clone();
        derror.subAssign(op1.diagonal());
        Array uderror = ref.upperDiagonal().clone();
        uderror.subAssign(op1.upperDiagonal());


        for (i=2; i<grid.size()-2; i++) {
            QL.info("lderror(" + i + ") = "+ Math.abs(lderror.get(i)) +  " tolerance " + tolerance + " \n");
            QL.info("derror(" + i + ") = "+ Math.abs(derror.get(i)) + " tolerance " + tolerance + " \n");
            QL.info("uderror(" + i + ") = "+ Math.abs(uderror.get(i)) + " tolerance " + tolerance + " \n");

            if (Math.abs(lderror.get(i)) > tolerance ||
                    Math.abs(derror.get(i)) > tolerance ||
                    Math.abs(uderror.get(i)) > tolerance) {

                fail("inconsistency between BSM operators:\n"
                        + Integer.toString(i) +  " row:\n"
                        + "expected:   " + ref.lowerDiagonal().get(i) + ", " + ref.diagonal().get(i) + ", " + ref.upperDiagonal().get(i) + "\n"
                        + "calculated: " + op1.lowerDiagonal().get(i) + ", " + op1.diagonal().get(i) + ", " + op1.upperDiagonal().get(i));
            }
        }

        lderror = ref.lowerDiagonal();
        lderror.subAssign(op2.lowerDiagonal());
        derror = ref.diagonal();
        derror.subAssign(op2.diagonal());
        uderror = ref.upperDiagonal();
        uderror.subAssign(op2.upperDiagonal());


        for (i=2; i<grid.size()-2; i++) {
            if (Math.abs(lderror.get(i)) > tolerance ||
                    Math.abs(derror.get(i)) > tolerance ||
                    Math.abs(uderror.get(i)) > tolerance) {

                fail("inconsistency between BSM operators:\n"
                        + Integer.toString(i) + " row:\n"
                        + "expected:   " + ref.lowerDiagonal().get(i) + ", " + ref.diagonal().get(i) + ", " + ref.upperDiagonal().get(i) + "\n"
                        + "calculated: " + op2.lowerDiagonal().get(i) + ", " + op2.diagonal().get(i) + ", " + op2.upperDiagonal().get(i));
            }
        }
    }

}
