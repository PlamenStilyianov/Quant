/*
 Copyright (C) 2008 Daniel Kong
 Copyright (C) 2009 Richard Gomes

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

package org.jquantlib.testsuite.math.interpolations;

import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static org.junit.Assert.assertFalse;

import org.jquantlib.QL;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;
import org.jquantlib.math.integrals.SimpsonIntegral;
import org.jquantlib.math.interpolations.BackwardFlatInterpolation;
import org.jquantlib.math.interpolations.CubicInterpolation;
import org.jquantlib.math.interpolations.ForwardFlatInterpolation;
import org.jquantlib.math.interpolations.Interpolation;
import org.jquantlib.math.interpolations.LinearInterpolation;
import org.jquantlib.math.interpolations.SABRInterpolation;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.EndCriteria;
import org.jquantlib.math.optimization.LevenbergMarquardt;
import org.jquantlib.math.optimization.OptimizationMethod;
import org.jquantlib.math.optimization.Simplex;
import org.jquantlib.math.randomnumbers.SobolRsg;
import org.jquantlib.termstructures.volatilities.Sabr;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Daniel Kong
 * @author Richard Gomes
 **/
public class InterpolationTest {

	public InterpolationTest() {
		QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
	}


    @Test
    public void testAsFunctor() {

        final class NotThrown extends RuntimeException {
            // nothing
        }

        QL.info("Testing use of interpolations as functors...");

        final Array x = new Array(new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 });
        final Array y = new Array(new double[] { 5.0, 4.0, 3.0, 2.0, 1.0 });

        final Interpolation f = new LinearInterpolation(x, y);
        f.update();

        final Array x2 = new Array(new double[] { -2.0, -1.0, 0.0, 1.0, 3.0, 4.0, 5.0, 6.0, 7.0 });
        final int N = x2.size();

        final double tolerance = 1.0e-12;

        // case 1: extrapolation not allowed
        try {
            final Array y2 = x2.clone().transform(f);
            System.out.println(y2);
            throw new NotThrown();
        } catch (final NotThrown ex) {
            throw new LibraryException("failed to throw exception when trying to extrapolate");
        } catch (final Exception ex) {
            // as expected, we are OK.
            System.out.println(ex);
        }

        // case 2: enable extrapolation
        f.enableExtrapolation();
        final Array y2 = x2.clone().transform(f);
        System.out.println(y2);
        for (int i=0; i<N-1; i++) {
            final double expected = 5.0 - x2.get(i);
            final double calculated = y2.get(i);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }
    }


    @Test
    public void testBackwardFlat() {

        QL.info("Testing backward-flat interpolation...");

        final Array x = new Array(new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 });
        final Array y = new Array(new double[] { 5.0, 4.0, 3.0, 2.0, 1.0 });

        final Interpolation f = new BackwardFlatInterpolation(x, y);
        f.update();

        final int N = x.size();
        final double tolerance = 1.0e-12;

        // at original points
        for (int i=0; i<N; i++) {
            final double p = x.get(i);
            final double calculated = f.op(p);
            final double expected = y.get(i);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        // at middle points
        for (int i=0; i<N-1; i++) {
            final double p = (x.get(i)+x.get(i+1))/2;
            final double calculated = f.op(p);
            final double expected = y.get(i+1);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        // outside the original range
        f.enableExtrapolation();

        {
            // this is just a block
            double p = x.get(0) - 0.5;
            double calculated = f.op(p);
            double expected = y.get(0);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);

            p = x.get(N-1) + 0.5;
            calculated = f.op(p);
            expected = y.get(N-1);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);

            // primitive at original points
            calculated = f.primitive(x.get(0));
            expected = 0.0;
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        double sum = 0.0;
        for (int i=1; i<N-1; i++) {
            sum += (x.get(i) - x.get(i-1)) * y.get(i);
            final double calculated = f.primitive(x.get(i));
            final double expected = sum;
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        // primitive at middle points
        sum = 0.0;
        for (int i=0; i<N-1; i++) {
            final double p = (x.get(i) + x.get(i+1))/2;
            sum += (x.get(i+1) - x.get(i)) * y.get(i+1)/2;
            final double calculated = f.primitive(p);
            final double expected = sum;
            sum += (x.get(i+1)-x.get(i))*y.get(i+1)/2;
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

    }




    @Test
    public void testSplineOnGenericValues() {

        QL.info("Testing spline interpolation on generic values...");

        final Array generic_x = new Array(new double[]{ 0.0, 1.0, 3.0, 4.0 });
        final Array generic_y = new Array(new double[]{ 0.0, 0.0, 2.0, 2.0 });
        final Array generic_natural_y2 = new Array(new double[]{ 0.0, 1.5, -1.5, 0.0 });

        double interpolated, error;
        final int n = generic_x.size();
        final double[] x35 = new double[3];

        // Natural spline
        CubicInterpolation f = new CubicInterpolation(
                generic_x, generic_y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.SecondDerivative, generic_natural_y2.first(),
                CubicInterpolation.BoundaryCondition.SecondDerivative, generic_natural_y2.last());
        f.update();

        checkValues("Natural spline", f, generic_x, generic_y);
        // cached second derivative
        for (int i=0; i<n; i++) {
            interpolated = f.secondDerivative(generic_x.get(i));
            error = interpolated - generic_natural_y2.get(i);
            assertFalse("Natural spline interpolation "
                  +"second derivative failed at x="+generic_x.get(i)
                  +"\n interpolated value: "+interpolated
                  +"\n expected value:     "+generic_natural_y2.get(i)
                  +"\n error:              "+error,
                  abs(error) > 3e-16);
        }
        x35[1] = f.op(3.5);


        // Clamped spline
        final double y1a = 0.0;
        final double y1b = 0.0;
        f = new CubicInterpolation(
                generic_x, generic_y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.FirstDerivative, y1a,
                CubicInterpolation.BoundaryCondition.FirstDerivative, y1b);
        f.update();

        checkValues("Clamped spline", f, generic_x, generic_y);
        check1stDerivativeValue("Clamped spline", f, generic_x.first(), 0.0);
        check1stDerivativeValue("Clamped spline", f, generic_x.last(),  0.0);
        x35[0] = f.op(3.5);


        // Not-a-knot spline
        f = new CubicInterpolation(
                generic_x, generic_y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
        f.update();

        checkValues("Not-a-knot spline", f, generic_x, generic_y);
        checkNotAKnotCondition("Not-a-knot spline", f);
        x35[2] = f.op(3.5);

        assertFalse("Spline interpolation failure"
            +"\n at x = "+3.5
            +"\n clamped spline    "+x35[0]
            +"\n natural spline    "+x35[1]
            +"\n not-a-knot spline "+x35[2]
            +"\n values should be in increasing order",
            x35[0]>x35[1] || x35[1]>x35[2]);
    }


    @Test
    public void testSimmetricEndConditions() {

        QL.info("Testing symmetry of spline interpolation end-conditions...");

        final int n = 9;

        final Array x = xRange(-1.8, 1.8, n);
        final Array y = gaussian(x);

        // Not-a-knot spline
        CubicInterpolation f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
        f.update();

        checkValues("Not-a-knot spline", f, x, y);
        checkNotAKnotCondition("Not-a-knot spline", f);
        checkSymmetry("Not-a-knot spline", f, x.first());


        // MC not-a-knot spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
        f.update();

        checkValues("MC not-a-knot spline", f, x, y);
        checkSymmetry("MC not-a-knot spline", f, x.first());
    }


    @Test
    public void testForwardFlat() {

        QL.info("Testing forward-flat interpolation...");

        final Array x = new Array(new double[] { 0.0, 1.0, 2.0, 3.0, 4.0 });
        final Array y = new Array(new double[] { 5.0, 4.0, 3.0, 2.0, 1.0 });

        final Interpolation f = new ForwardFlatInterpolation(x, y);
        f.update();

        final int N = x.size();
        final double tolerance = 1.0e-12;

        // at original points
        for (int i=0; i<N; i++) {
            final double p = x.get(i);
            final double calculated = f.op(p);
            final double expected = y.get(i);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        // at middle points
        for (int i=0; i<N-1; i++) {
            final double p = (x.get(i) + x.get(i+1))/2;
            final double calculated = f.op(p);
            final double expected = y.get(i);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        // outside the original range
        f.enableExtrapolation();

        {
            double p = x.get(0) - 0.5;
            double calculated = f.op(p);
            double expected = y.get(0);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);

            p = x.get(N-1) + 0.5;
            calculated = f.op(p);
            expected = y.get(N-1);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);

            // primitive at original points
            calculated = f.primitive(x.get(0));
            expected = 0.0;
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        double sum = 0.0;
        for (int i=1; i<N; i++) {
            sum += (x.get(i) - x.get(i-1)) * y.get(i-1);
            final double calculated = f.primitive(x.get(i));
            final double expected = sum;
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }

        // primitive at middle points
        sum = 0.0;
        for (int i=0; i<N-1; i++) {
            final double p = (x.get(i) + x.get(i+1))/2;
            sum += (x.get(i+1) - x.get(i)) * y.get(i)/2;
            final double calculated = f.primitive(p);
            final double expected = sum;
            sum += (x.get(i+1) - x.get(i)) * y.get(i)/2;
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+calculated
                    +"\n    error:              "+Math.abs(calculated-expected),
                    Math.abs(calculated - expected) > tolerance);
        }
    }


    @Ignore
    @Test
    public void testMultiSpline() {
        QL.info("Testing N-dimensional cubic spline...");

        final int dim[] = new int[] {6, 5, 5, 6, 4};
        final double offsets[] = new double[] {1.005, 14.0, 33.005, 35.025, 19.025};
        final double args[] = new double[] {
                offsets[0],
                offsets[1],
                offsets[2],
                offsets[3],
                offsets[4],
        };

        double s = args[0];
        double t = args[1];
        double u = args[2];
        double v = args[3];
        double w = args[4];

        //TODO: final SplineGrid grid = new SplineGrid(5);
        final Array grid[] = new Array[5]; //XXX: just to compile

        double r = 0.15;

        for (int i = 0; i < 5; ++i) {
// TODO: translate this code
//            double temp = offsets[i];
//            for (int j = 0; j < dim[i]; temp += r, ++j) {
//                grid[i].push_back(temp);
//            }
            grid[i] = new Array(0); //XXX: just to compile
        }

        r = 0.01;

        //TODO: MultiCubicSpline<5>::data_table y5(dim);
        final double y5[][][][][] = new double [dim[0]] [dim[1]] [dim[2]] [dim[3]] [dim[4]];

        for (int i = 0; i < dim[0]; ++i) {
            for (int j = 0; j < dim[1]; ++j) {
                for (int k = 0; k < dim[2]; ++k) {
                    for (int l = 0; l < dim[3]; ++l) {
                        for (int m = 0; m < dim[4]; ++m) {
//TODO: translate properly
//                            y5[i][j][k][l][m] = multif(grid[0][i], grid[1][j], grid[2][k], grid[3][l], grid[4][m]);

                            //XXX: just to compile
                            y5[i][j][k][l][m] = multif(
                                    grid[0].get(i),
                                    grid[1].get(j),
                                    grid[2].get(k),
                                    grid[3].get(l),
                                    grid[4].get(m));
                            //-----------------------------------------------
                        }
                    }
                }
            }
        }

        //TODO: MultiCubicSpline<5> cs(grid, y5);

        //XXX: just to compile
        class MultiCubicSpline {
            public double op(final double[] args) {
                throw new UnsupportedOperationException();
            }
        };
        final MultiCubicSpline cs = new MultiCubicSpline();
        //----------------------------------


        /* ORIGINAL COMMENT FROM C++ SOURCES:
        It would fail with
        for (i = 0; i < dim[0]; ++i)
            for (j = 0; j < dim[1]; ++j)
                for (k = 0; k < dim[2]; ++k)
                    for (l = 0; l < dim[3]; ++l)
                        for (m = 0; m < dim[4]; ++m) {
        */

        for (int i = 1; i < dim[0]-1; ++i) {
            for (int j = 1; j < dim[1]-1; ++j) {
                for (int k = 1; k < dim[2]-1; ++k) {
                    for (int l = 1; l < dim[3]-1; ++l) {
                        for (int m = 1; m < dim[4]-1; ++m) {
//TODO: translate properly
//                            s = grid[0][i];
//                            t = grid[1][j];
//                            u = grid[2][k];
//                            v = grid[3][l];
//                            w = grid[4][m];
                            //XXX: just to compile
                            s = grid[0].get(i);
                            t = grid[1].get(j);
                            u = grid[2].get(k);
                            v = grid[3].get(l);
                            w = grid[4].get(m);
                            //--------------------------------
                            final double interpolated = cs.op(args);
                            final double expected = y5[i][j][k][l][m];
                            final double error = Math.abs(interpolated-expected);
                            final double tolerance = 1e-16;
                            assertFalse("failed to reproduce expected datum"
                                    +"\n    expected value:   "+expected
                                    +"\n    calculated value: "+interpolated
                                    +"\n    error:              "+error,
                                    error > tolerance);
                        }
                    }
                }
            }
        }


        final long seed = 42;
        final SobolRsg rsg = new SobolRsg(5, seed);

        final double tolerance = 1.7e-4;
        // actually tested up to 2^21-1=2097151 Sobol draws
        for (int i = 0; i < 1023; ++i) {
            final double next[] = rsg.nextSequence().value();
// TODO: translate properly
//            s = grid[0].front() + next[0]*(grid[0].back()-grid[0].front());
//            t = grid[1].front() + next[1]*(grid[1].back()-grid[1].front());
//            u = grid[2].front() + next[2]*(grid[2].back()-grid[2].front());
//            v = grid[3].front() + next[3]*(grid[3].back()-grid[3].front());
//            w = grid[4].front() + next[4]*(grid[4].back()-grid[4].front());
            // XXX: just to compile
            s = grid[0].first() + next[0]*(grid[0].last()-grid[0].first());
            t = grid[1].first() + next[1]*(grid[1].last()-grid[1].first());
            u = grid[2].first() + next[2]*(grid[2].last()-grid[2].first());
            v = grid[3].first() + next[3]*(grid[3].last()-grid[3].first());
            w = grid[4].first() + next[4]*(grid[4].last()-grid[4].first());
            // ------------------------------------------------------------
            final double interpolated = cs.op(args);
            final double expected = multif(s, t, u, v, w);
            final double error = Math.abs(interpolated-expected);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+expected
                    +"\n    calculated value: "+interpolated
                    +"\n    error:              "+error,
                    error > tolerance);
        }
    }



    @Test
    public void testDerivativeEndConditions() {

        QL.info("Testing derivative end-conditions for spline interpolation...");

        final int n = 4;
        final Array x = xRange(-2.0, 2.0, n);
        final Array y = parabolic(x);

        // Not-a-knot spline
        CubicInterpolation f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
        f.update();
        checkValues("Not-a-knot spline", f, x, y);
        check1stDerivativeValue("Not-a-knot spline", f, x.get(0), 4.0);
        check1stDerivativeValue("Not-a-knot spline", f, x.get(n-1), -4.0);
        check2ndDerivativeValue("Not-a-knot spline", f, x.get(0), -2.0);
        check2ndDerivativeValue("Not-a-knot spline", f, x.get(n-1), -2.0);


        // Clamped spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.FirstDerivative,  4.0,
                CubicInterpolation.BoundaryCondition.FirstDerivative, -4.0);
        f.update();
        checkValues("Clamped spline", f, x, y);
        check1stDerivativeValue("Clamped spline", f, x.get(0), 4.0);
        check1stDerivativeValue("Clamped spline", f, x.get(n-1), -4.0);
        check2ndDerivativeValue("Clamped spline", f, x.get(0), -2.0);
        check2ndDerivativeValue("Clamped spline", f, x.get(n-1), -2.0);


        // SecondDerivative spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.SecondDerivative, -2.0,
                CubicInterpolation.BoundaryCondition.SecondDerivative, -2.0);
        f.update();
        checkValues("SecondDerivative spline", f, x, y);
        check1stDerivativeValue("SecondDerivative spline", f, x.get(0), 4.0);
        check1stDerivativeValue("SecondDerivative spline", f, x.get(n-1), -4.0);
        check2ndDerivativeValue("SecondDerivative spline", f, x.get(0), -2.0);
        check2ndDerivativeValue("SecondDerivative spline", f, x.get(n-1), -2.0);

        // MC Not-a-knot spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
        f.update();
        checkValues("MC Not-a-knot spline", f, x, y);
        check1stDerivativeValue("MC Not-a-knot spline", f, x.get(0), 4.0);
        check1stDerivativeValue("MC Not-a-knot spline", f, x.get(n-1), -4.0);
        check2ndDerivativeValue("MC Not-a-knot spline", f, x.get(0), -2.0);
        check2ndDerivativeValue("MC Not-a-knot spline", f, x.get(n-1), -2.0);


        // MC Clamped spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.FirstDerivative,  4.0,
                CubicInterpolation.BoundaryCondition.FirstDerivative, -4.0);
        f.update();
        checkValues("MC Clamped spline", f, x, y);
        check1stDerivativeValue("MC Clamped spline", f, x.get(0), 4.0);
        check1stDerivativeValue("MC Clamped spline", f, x.get(n-1), -4.0);
        check2ndDerivativeValue("MC Clamped spline", f, x.get(0), -2.0);
        check2ndDerivativeValue("MC Clamped spline", f, x.get(n-1), -2.0);


        // MC SecondDerivative spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.SecondDerivative, -2.0,
                CubicInterpolation.BoundaryCondition.SecondDerivative, -2.0);
        f.update();
        checkValues("MC SecondDerivative spline", f, x, y);
        check1stDerivativeValue("MC SecondDerivative spline", f, x.get(0), 4.0);
        check1stDerivativeValue("MC SecondDerivative spline", f, x.get(n-1), -4.0);
        check2ndDerivativeValue("SecondDerivative spline",    f, x.get(0), -2.0);
        check2ndDerivativeValue("MC SecondDerivative spline", f, x.get(n-1), -2.0);
    }


















    @Test
	public void testSplineErrorOnGaussianValues(){
	    //System.setProperty("EXPERIMENTAL", "true");
		QL.info("Testing spline approximation on Gaussian data sets...");

	    final int points[]                 = {      5,      9,     17,     33 };

	    // complete spline data from the original 1983 Hyman paper
	    final double tabulatedErrors[]     = { 3.5e-2, 2.0e-3, 4.0e-5, 1.8e-6 };
	    final double toleranceOnTabErr[]   = { 0.1e-2, 0.1e-3, 0.1e-5, 0.1e-6 };

	    // (complete) MC spline data from the original 1983 Hyman paper
	    // NB: with the improved Hyman filter from the Dougherty, Edelman, and
	    //     Hyman 1989 paper the n=17 nonmonotonicity is not filtered anymore
	    //     so the error agrees with the non MC method.
	    final double tabulatedMCErrors[]   = { 1.7e-2, 2.0e-3, 4.0e-5, 1.8e-6 };
	    final double toleranceOnTabMCErr[] = { 0.1e-2, 0.1e-3, 0.1e-5, 0.1e-6 };

	    final SimpsonIntegral integral = new SimpsonIntegral(1e-12, 10000);

	    // still unexplained scale factor needed to obtain the numerical results from the paper
	    final double scaleFactor = 1.9;

	    for (int i=0; i<points.length; i++) {
	        final int n = points[i];
	        final Array x = xRange(-1.7, 1.9, n);
	        final Array y = gaussian(x);
	        double result;

	        // Not-a-knot
	        CubicInterpolation f = new CubicInterpolation(
	                x, y,
                    CubicInterpolation.DerivativeApprox.Spline, false,
                    CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                    CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
	        f.update();

	        result = Math.sqrt(integral.op(makeErrorFunction(f), -1.7, 1.9));
	        result /= scaleFactor;
            assertFalse("Not-a-knot spline interpolation "
                    +"\n    sample points:      "+n
                    +"\n    norm of difference: "+result
                    +"\n    it should be:       "+tabulatedErrors[i],
                    abs(result-tabulatedErrors[i]) > toleranceOnTabErr[i]);

	        // MC not-a-knot
	        f = new CubicInterpolation(
	                x, y,
                    CubicInterpolation.DerivativeApprox.Spline, true,
                    CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                    CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
	        f.update();

            result = Math.sqrt(integral.op(makeErrorFunction(f), -1.7, 1.9));
	        result /= scaleFactor;
            assertFalse ("MC Not-a-knot spline interpolation "
                    + "\n    sample points:      "
                    + "\n    norm of difference: " + result
                    + "\n    it should be:       " + tabulatedErrors[i],
                    Math.abs(result-tabulatedMCErrors[i]) > toleranceOnTabMCErr[i]);
	    }
	}



	@Test
	public void testSplineOnRPN15AValues(){

		QL.info("Testing Clamped spline interpolation on RPN15A data set...");

		final Array RPN15A_x = new Array(new double[] { 7.99, 8.09, 8.19, 8.7, 9.2, 10.0, 12.0, 15.0, 20.0 });
		final Array RPN15A_y = new Array(new double[] { 0.0, 2.76429e-5, 4.37498e-5, 0.169183, 0.469428, 0.943740, 0.998636, 0.999919, 0.999994 });

		double interpolated;


	    // Natural spline
	    CubicInterpolation f = new CubicInterpolation(
                                RPN15A_x, RPN15A_y,
                                CubicInterpolation.DerivativeApprox.Spline, false,
                                CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
                                CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0);
	    f.update();

	    checkValues("Natural spline", f, RPN15A_x, RPN15A_y);
	    check2ndDerivativeValue("Natural spline", f, RPN15A_x.first(), 0.0);
	    check2ndDerivativeValue("Natural spline", f, RPN15A_x.last(),  0.0);

	    // poor performance
	    final double x_bad = 11.0;
	    interpolated = f.op(x_bad);
        assertFalse("Natural spline interpolation poor performance unverified"
                +"\n    at x = "+x_bad
                +"\n    interpolated value: "+interpolated
                +"\n    expected value > 1.0",
                interpolated<1.0);


	    // Clamped spline
	    f = new CubicInterpolation(
                RPN15A_x, RPN15A_y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.FirstDerivative, 0.0,
                CubicInterpolation.BoundaryCondition.FirstDerivative, 0.0);
	    f.update();

	    checkValues("Clamped spline", f, RPN15A_x, RPN15A_y);
	    check1stDerivativeValue("Clamped spline", f, RPN15A_x.first(), 0.0);
	    check1stDerivativeValue("Clamped spline", f, RPN15A_x.last(),  0.0);

	    // poor performance
	    interpolated = f.op(x_bad);
        assertFalse("Clamped spline interpolation poor performance unverified"
                +"\n    at x = "+x_bad
                +"\n    interpolated value: "+interpolated
                +"\n    expected value > 1.0",
                interpolated<1.0);


	    // Not-a-knot spline
        f = new CubicInterpolation(
                RPN15A_x, RPN15A_y,
                CubicInterpolation.DerivativeApprox.Spline, false,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
	    f.update();

	    checkValues("Not-a-knot spline", f, RPN15A_x, RPN15A_y);
	    checkNotAKnotCondition("Not-a-knot spline", f);

	    // poor performance
	    interpolated = f.op(x_bad);
        assertFalse("Not-a-knot spline interpolation poor performance unverified"
                +"\n    at x = "+x_bad
                +"\n    interpolated value: "+interpolated
                +"\n    expected value > 1.0",
                interpolated<1.0);



	    // MC natural spline values
        f = new CubicInterpolation(
                RPN15A_x, RPN15A_y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0,
                CubicInterpolation.BoundaryCondition.SecondDerivative, 0.0);
	    f.update();

	    checkValues("MC natural spline", f, RPN15A_x, RPN15A_y);

	    // good performance
	    interpolated = f.op(x_bad);
        assertFalse("MC natural spline interpolation good performance unverified"
                +"\n    at x = "+x_bad
                +"\n    interpolated value: "+interpolated
                +"\n    expected value > 1.0",
                interpolated>1.0);


	    // MC clamped spline values
        f = new CubicInterpolation(
                RPN15A_x, RPN15A_y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.FirstDerivative, 0.0,
                CubicInterpolation.BoundaryCondition.FirstDerivative, 0.0);
	    f.update();

	    checkValues("MC clamped spline", f, RPN15A_x, RPN15A_y);
	    check1stDerivativeValue("MC clamped spline", f, RPN15A_x.first(), 0.0);
	    check1stDerivativeValue("MC clamped spline", f, RPN15A_x.last(),  0.0);

	    // good performance
	    interpolated = f.op(x_bad);
        assertFalse("MC clamped spline interpolation good performance unverified"
                +"\n    at x = "+x_bad
                +"\n    interpolated value: "+interpolated
                +"\n    expected value > 1.0",
                interpolated>1.0);


	    // MC not-a-knot spline values
        f = new CubicInterpolation(
                RPN15A_x, RPN15A_y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL,
                CubicInterpolation.BoundaryCondition.NotAKnot, Constants.NULL_REAL);
	    f.update();

	    checkValues("MC not-a-knot spline", f, RPN15A_x, RPN15A_y);

	    // good performance
	    interpolated = f.op(x_bad);
        assertFalse("MC clamped spline interpolation good performance unverified"
                +"\n    at x = "+x_bad
                +"\n    interpolated value: "+interpolated
                +"\n    expected value > 1.0",
                interpolated>1.0);
	}


	@Test
	public void testSplineOnGaussianValues(){

	    QL.info("Testing spline interpolation on a Gaussian data set...");

        double interpolated, interpolated2;
        final int n = 5;

        Array x, y;

        final double x1_bad=-1.7, x2_bad=1.7;

        for (double start = -1.9, j=0; j<2; start+=0.2, j++) {
            x = xRange(start, start+3.6, n);
            y = gaussian(x);

            // Not-a-knot spline
            CubicInterpolation f = new CubicInterpolation(
                    x, y,
                    CubicInterpolation.DerivativeApprox.Spline, false,
                    CubicInterpolation.BoundaryCondition.NotAKnot, 0.0,
                    CubicInterpolation.BoundaryCondition.NotAKnot, 0.0);

            checkValues("Not-a-knot spline", f, x, y);
            checkNotAKnotCondition("Not-a-knot spline", f);
            // bad performance
            interpolated  = f.op(x1_bad);
            interpolated2 = f.op(x2_bad);
            assertFalse("Not-a-knot spline interpolation bad performance unverified"
                    +"\n    at x = "+x1_bad
                    +"\n    interpolated value: "+interpolated
                    +"\n    at x = "+x2_bad
                    +"\n    interpolated value: "+interpolated2
                    +"\n    at least one of them was expected to be < 0.0",
                    interpolated>0.0 && interpolated2>0.0);

            // MC not-a-knot spline
            f = new CubicInterpolation(
                    x, y,
                    CubicInterpolation.DerivativeApprox.Spline, true,
                    CubicInterpolation.BoundaryCondition.NotAKnot, 0.0,
                    CubicInterpolation.BoundaryCondition.NotAKnot, 0.0);
            f.update();

            checkValues("MC not-a-knot spline", f, x, y);

            // bad performance
            interpolated  = f.op(x1_bad);
            interpolated2 = f.op(x2_bad);
            assertFalse("Not-a-knot spline interpolation "
                        + "bad performance unverified"
                        + "\nat x = " + x1_bad
                        + " interpolated value: " + interpolated
                        + "\nat x = " + x2_bad
                        + " interpolated value: " + interpolated
                        + "\n at least one of them was expected to be < 0.0",
                        interpolated<=0.0 || interpolated2<=0.0);
        }


        QL.info("Testing spline interpolation on a Gaussian data set...");

        for (double start = -1.9, j=0; j<2; start+=0.2, j++) {
            x = xRange(start, start+3.6, n);
            y = gaussian(x);

            // MC not-a-knot spline
            final CubicInterpolation interpolation = new CubicInterpolation(
                    x, y,
                    CubicInterpolation.DerivativeApprox.Spline, true,
                    CubicInterpolation.BoundaryCondition.NotAKnot, 0.0,
                    CubicInterpolation.BoundaryCondition.NotAKnot, 0.0);
            interpolation.update();

            checkValues("MC not-a-knot spline", interpolation, x, y);

            // bad performance
            interpolated  = interpolation.op(x1_bad);
            interpolated2 = interpolation.op(x2_bad);
            if (interpolated>0.0 && interpolated2>0.0 ) {
                assertFalse("Not-a-knot spline interpolation "
                            + "bad performance unverified"
                            + "\nat x = " + x1_bad
                            + " interpolated value: " + interpolated
                            + "\nat x = " + x2_bad
                            + " interpolated value: " + interpolated
                            + "\n at least one of them was expected to be < 0.0",
                            interpolated<=0.0 || interpolated2<=0.0);
            }
        }
    }


    /**
     * See R. L. Dougherty, A. Edelman, J. M. Hyman,
     * "Nonnegativity-, Monotonicity-, or Convexity-Preserving CubicSpline and Quintic
     * Hermite Interpolation"
     * Mathematics Of Computation, v. 52, n. 186, April 1989, pp. 471-494.
     */
    @Test
    public void testNonRestrictiveHymanFilter() {
        final int n = 4;

        Array x, y;
        x = xRange(-2.0, 2.0, n);
        y = parabolic(x);
        final double zero=0.0;
        double interpolated;
        final double expected=0.0;

        // MC Not-a-knot spline
        CubicInterpolation f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.NotAKnot, 0.0,
                CubicInterpolation.BoundaryCondition.NotAKnot, 0.0);

        interpolated = f.op(zero);
        assertFalse("MC not-a-knot spline interpolation failed at x = "+zero
                    +"\n    interpolated value: "+interpolated
                    +"\n    expected value:     "+expected
                    +"\n    error:              "+abs(interpolated-expected),
                    abs(interpolated-expected) > 1e-15);


        // MC Clamped spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.FirstDerivative,  4.0,
                CubicInterpolation.BoundaryCondition.FirstDerivative, -4.0);

        interpolated =  f.op(zero);
        assertFalse("MC clamped spline interpolation failed at x = "+zero
                +"\n    interpolated value: "+interpolated
                +"\n    expected value:     "+expected
                +"\n    error:              "+abs(interpolated-expected),
                abs(interpolated-expected) > 1e-15);


        // MC SecondDerivative spline
        f = new CubicInterpolation(
                x, y,
                CubicInterpolation.DerivativeApprox.Spline, true,
                CubicInterpolation.BoundaryCondition.SecondDerivative, -2.0,
                CubicInterpolation.BoundaryCondition.SecondDerivative, -2.0);

        interpolated =  f.op(zero);
        assertFalse("MC SecondDerivative spline interpolation failed at x = "+zero
                +"\n    interpolated value: "+interpolated
                +"\n    expected value:     "+expected
                +"\n    error:              "+abs(interpolated-expected),
                abs(interpolated-expected) > 1e-15);
    }




    @Ignore
    @Test
    public void testSabrInterpolation(){

        QL.info("Testing Sabr interpolation...");

        // Test SABR function against input volatilities
        final double tolerance = 2.0e-13;
        final double strikes[] = new double[31];
        final double volatilities[] = new double[31];
        // input strikes
        strikes[0] = 0.03 ; strikes[1] = 0.032 ; strikes[2] = 0.034 ;
        strikes[3] = 0.036 ; strikes[4] = 0.038 ; strikes[5] = 0.04 ;
        strikes[6] = 0.042 ; strikes[7] = 0.044 ; strikes[8] = 0.046 ;
        strikes[9] = 0.048 ; strikes[10] = 0.05 ; strikes[11] = 0.052 ;
        strikes[12] = 0.054 ; strikes[13] = 0.056 ; strikes[14] = 0.058 ;
        strikes[15] = 0.06 ; strikes[16] = 0.062 ; strikes[17] = 0.064 ;
        strikes[18] = 0.066 ; strikes[19] = 0.068 ; strikes[20] = 0.07 ;
        strikes[21] = 0.072 ; strikes[22] = 0.074 ; strikes[23] = 0.076 ;
        strikes[24] = 0.078 ; strikes[25] = 0.08 ; strikes[26] = 0.082 ;
        strikes[27] = 0.084 ; strikes[28] = 0.086 ; strikes[29] = 0.088;
        strikes[30] = 0.09;
        // input volatilities
        volatilities[0] = 1.16725837321531 ; volatilities[1] = 1.15226075991385 ; volatilities[2] = 1.13829711098834 ;
        volatilities[3] = 1.12524190877505 ; volatilities[4] = 1.11299079244474 ; volatilities[5] = 1.10145609357162 ;
        volatilities[6] = 1.09056348513411 ; volatilities[7] = 1.08024942745106 ; volatilities[8] = 1.07045919457758 ;
        volatilities[9] = 1.06114533019077 ; volatilities[10] = 1.05226642581503 ; volatilities[11] = 1.04378614411707 ;
        volatilities[12] = 1.03567243073732 ; volatilities[13] = 1.0278968727451 ; volatilities[14] = 1.02043417226345 ;
        volatilities[15] = 1.01326171139321 ; volatilities[16] = 1.00635919013311 ; volatilities[17] = 0.999708323124949 ;
        volatilities[18] = 0.993292584155381 ; volatilities[19] = 0.987096989695393 ; volatilities[20] = 0.98110791455717 ;
        volatilities[21] = 0.975312934134512 ; volatilities[22] = 0.969700688771689 ; volatilities[23] = 0.964260766651027;
        volatilities[24] = 0.958983602256592 ; volatilities[25] = 0.953860388001395 ; volatilities[26] = 0.948882997029509 ;
        volatilities[27] = 0.944043915545469 ; volatilities[28] = 0.939336183299237 ; volatilities[29] = 0.934753341079515 ;
        volatilities[30] = 0.930289384251337;

        final /*@Time*/ double expiry = 1.0;
        final double forward = 0.039;
        // input SABR coefficients (corresponding to the vols above)
        final double initialAlpha = 0.3;
        final double initialBeta = 0.6;
        final double initialNu = 0.02;
        final double initialRho = 0.01;
        // calculate SABR vols and compare with input vols
        for (int i=0; i<strikes.length; i++) {
            final double calculatedVol = (new Sabr()).sabrVolatility(strikes[i], forward, expiry,
                                                initialAlpha, initialBeta,
                                                initialNu, initialRho);
            assertFalse("failed to reproduce expected datum"
                    +"\n    expected value:   "+volatilities[i]
                    +"\n    calculated value: "+calculatedVol
                    +"\n    error:              "+Math.abs(calculatedVol-volatilities[i]),
                    Math.abs(volatilities[i]-calculatedVol) > tolerance);
        }

        // Test SABR calibration against input parameters
        // Initial null guesses (uses default values)
        final double alphaGuess = Constants.NULL_REAL;
        final double betaGuess = Constants.NULL_REAL;
        final double nuGuess = Constants.NULL_REAL;
        final double rhoGuess = Constants.NULL_REAL;

        final boolean vegaWeighted[] = {true, false};
        final boolean isAlphaFixed[] = {true, false};
        final boolean isBetaFixed[]  = {true, false};
        final boolean isNuFixed[]    = {true, false};
        final boolean isRhoFixed[]   = {true, false};

        final double calibrationTolerance = 5.0e-8;
        // initialize optimization methods
        final OptimizationMethod methods[] = new OptimizationMethod[2];
        methods[0] = new Simplex(0.01);
        methods[1] = new LevenbergMarquardt(1e-8, 1e-8, 1e-8);
        // Initialize end criteria
        final EndCriteria endCriteria = new EndCriteria(100000, 100, 1e-8, 1e-8, 1e-8);

        // Test looping over all possibilities
        for (int j=0; j<methods.length; ++j) {
          for (int i=0; i<vegaWeighted.length; ++i) {
            for (int k_a=0; k_a<isAlphaFixed.length; ++k_a) {
              for (int k_b=0; k_b<isBetaFixed.length; ++k_b) {
                for (int k_n=0; k_n<isNuFixed.length; ++k_n) {
                  for (int k_r=0; k_r<isRhoFixed.length; ++k_r) {
//FIXME: uncomment
                    final SABRInterpolation sabrInterpolation = new SABRInterpolation(
                            new Array(strikes), new Array(volatilities),
                            expiry, forward,
                            alphaGuess, betaGuess, nuGuess, rhoGuess,
                            isAlphaFixed[k_a], isBetaFixed[k_b],
                            isNuFixed[k_n], isRhoFixed[k_r],
                            vegaWeighted[i],
                            endCriteria, methods[j]);
                    sabrInterpolation.update();

                    // Recover SABR calibration parameters
                    final boolean failed = false;
                    final double calibratedAlpha = sabrInterpolation.alpha();
                    final double calibratedBeta  = sabrInterpolation.beta();
                    final double calibratedNu    = sabrInterpolation.nu();
                    final double calibratedRho   = sabrInterpolation.rho();

                    // TODO: remove these declarations. Added just to make it compile.
//                    final double calibratedAlpha = Double.NaN;
//                    final double calibratedBeta  = Double.NaN;
//                    final double calibratedNu    = Double.NaN;
//                    final double calibratedRho   = Double.NaN;
                    // ---------------------------------------------------------------

                    double error;

                    // compare results: alpha
                    error = Math.abs(initialAlpha-calibratedAlpha);
                    assertFalse("failed to calibrate alpha Sabr parameter"
                            +"\n    expected value:   "+initialAlpha
                            +"\n    calculated value: "+calibratedAlpha
                            +"\n    error:              "+error,
                            Math.abs(error) > calibrationTolerance);
                    // Beta
                    error = Math.abs(initialBeta-calibratedBeta);
                    assertFalse("failed to calibrate beta Sabr parameter"
                            +"\n    expected value:   "+initialBeta
                            +"\n    calculated value: "+calibratedBeta
                            +"\n    error:              "+error,
                            Math.abs(error) > calibrationTolerance);
                    // Nu
                    error = Math.abs(initialNu-calibratedNu);
                    assertFalse("failed to calibrate nu Sabr parameter"
                            +"\n    expected value:   "+initialNu
                            +"\n    calculated value: "+calibratedNu
                            +"\n    error:              "+error,
                            Math.abs(error) > calibrationTolerance);
                    // Rho
                    error = Math.abs(initialRho-calibratedRho);
                    assertFalse("failed to calibrate rho Sabr parameter"
                            +"\n    expected value:   "+initialRho
                            +"\n    calculated value: "+calibratedRho
                            +"\n    error:              "+error,
                            Math.abs(error) > calibrationTolerance);
                  }
                }
              }
            }
          }
        }

    }


    //
    // private methods
    //

    private Array xRange(final double start, final double finish, final int size){
        final double[] x = new double [size];
        final double dx = (finish-start)/(size-1);
        for(int i=0; i<size-1; i++) {
            x[i]=start+i*dx;
        }
        x[size-1] = finish;
        return new Array(x);
    }

    private Array gaussian(final Array x){
        final double[] y = new double[x.size()];
        for(int i=0; i<x.size(); i++) {
            final double value = x.get(i);
            y[i] = exp(-value*value);
        }
        return new Array(y);
    }

    private Array parabolic(final Array x){
        final double[] y = new double[x.size()];
        for(int i=0; i<x.size(); i++) {
            final double value = x.get(i);
            y[i] = -value*value;
        }
        return new Array(y);
    }

    private void checkValues(
            final String type,
            final CubicInterpolation spline,
            final Array x, final Array y){
        final double tolerance = 2.0e-15;
        for(int i=0; i<x.size(); i++) {
            final double xval = x.get(i);
            final double yval = y.get(i);
            final double interpolated = spline.op(xval);
            assertFalse(type+" interpolation failed at x = "+xval
                    +"\n interpolated value: "+interpolated
                    +"\n expected value:     "+yval
                    +"\n error:        "+abs(interpolated-yval),
                    abs(interpolated-yval) > tolerance);
        }
    }

    private void check1stDerivativeValue(
            final String type,
            final CubicInterpolation spline,
            final double x,
            final double value) {
        final double tolerance = 1.0e-14;
        final double interpolated = spline.derivative(x);
        assertFalse(type+" interpolation first derivative failure at x = "+x
                    +"\n interpolated value: "+interpolated
                    +"\n expected value:     "+value
                    +"\n error:        "+abs(interpolated-value),
                    abs(interpolated-value) > tolerance);
    }

    private void check2ndDerivativeValue(
            final String type,
            final CubicInterpolation spline,
            final double x,
            final double value) {
        final double tolerance = 1.0e-13;
        final double interpolated = spline.secondDerivative(x);
        assertFalse(type+" interpolation second derivative failure at x = "+x
                    +"\n interpolated value: "+interpolated
                    +"\n expected value:     "+value
                    +"\n error:        "+abs(interpolated-value),
                    abs(interpolated-value) > tolerance);
    }

    private void checkNotAKnotCondition(
            final String type,
            final CubicInterpolation spline) {
        final double tolerance = 1.0e-14;

        final Array c = spline.cCoefficients();
        assertFalse(type+" interpolation failure"
                    +"\n    cubic coefficient of the first polinomial is "+c.get(0)
                    +"\n    cubic coefficient of the second polinomial is "+c.get(1),
                    abs(c.get(0)-c.get(1)) > tolerance);
        final int n=c.size();
        assertFalse(type+" interpolation failure"
                +"\n    cubic coefficient of the 2nd to last polinomial is "+c.get(n-2)
                +"\n    cubic coefficient of the last polinomial is "+c.get(n-1),
                abs(c.get(n-2)-c.get(n-1)) > tolerance);

    }

    private void checkSymmetry(
            final String type,
            final CubicInterpolation spline,
            final double xMin) {
        final double tolerance = 1.0e-15;
        for (double x = xMin; x < 0.0; x += 0.1){
            final double y1=spline.op(x);
            final double y2=spline.op(-x);
            assertFalse(type+" interpolation not symmetric"
                    +"\n    x = "+x
                    +"\n    g(x)  = "+y1
                    +"\n    g(-x) = "+y2
                    +"\n    error:  "+abs(y1-y2),
                    abs(y1-y2) > tolerance);
        }
    }

    private class ErrorFunction implements Ops.DoubleOp {
        private final Ops.DoubleOp f;

        public ErrorFunction(final Ops.DoubleOp f) {
            this.f = f;
        }

        public double op(final double x) /* @ReadOnly */ {
            final double temp = f.op(x) - Math.exp(-x*x);
            return temp * temp;
        }
    }

    private ErrorFunction makeErrorFunction(final Ops.DoubleOp f) {
        return new ErrorFunction(f);
    }

    private double multif(final double s, final double t, final double u, final double v, final double w) {
        return Math.sqrt(s * Math.sinh(Math.log(t)) + Math.exp(Math.sin(u) * Math.sin(3 * v)) + Math.sinh(Math.log(v * w)));
    }

}
