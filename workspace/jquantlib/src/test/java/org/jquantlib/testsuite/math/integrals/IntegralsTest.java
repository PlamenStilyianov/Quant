package org.jquantlib.testsuite.math.integrals;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;
import org.jquantlib.math.distributions.NormalDistribution;
import org.jquantlib.math.functions.Constant;
import org.jquantlib.math.functions.Cos;
import org.jquantlib.math.functions.Identity;
import org.jquantlib.math.functions.Sin;
import org.jquantlib.math.functions.Square;
import org.jquantlib.math.integrals.GaussKronrodAdaptive;
import org.jquantlib.math.integrals.GaussKronrodNonAdaptive;
import org.jquantlib.math.integrals.Integrator;
import org.jquantlib.math.integrals.SegmentIntegral;
import org.jquantlib.math.integrals.SimpsonIntegral;
import org.jquantlib.math.integrals.TrapezoidIntegral;
import org.junit.Test;

public class IntegralsTest {

    final private static double tolerance = 1.0e-6;

    private void testSingle(
            final Integrator integrator,
            final String tag,
            final Ops.DoubleOp f,
            final double xMin, final double xMax, final double expected) {
        final double calculated = integrator.op(f, xMin, xMax);
        if (Math.abs(calculated-expected) > tolerance) {

            final StringBuilder sb = new StringBuilder();
            sb.append("integrating ").append(tag).append('\n');
            sb.append("    calculated: ").append(calculated).append('\n');
            sb.append("    expeceted:  ").append(expected);
            fail(sb.toString());
        }
    }

    private void testSeveral(final Integrator I) {
        testSingle(I, "f(x) = 1",      new Constant(1.0),               0.0, 1.0, 1.0);
        testSingle(I, "f(x) = x",      new Identity(),                  0.0, 1.0, 0.5);
        testSingle(I, "f(x) = x^2",    new Square(),                    0.0, 1.0, 1.0/3.0);
        testSingle(I, "f(x) = sin(x)", new Sin(),                       0.0, Constants.M_PI, 2.0);
        testSingle(I, "f(x) = cos(x)", new Cos(),                       0.0, Constants.M_PI, 0.0);
        testSingle(I, "f(x) = Gaussian(x)", new NormalDistribution(), -10.0, 10.0, 1.0);

//TODO: http://bugs.jquantlib.org/view.php?id=452
//        testSingle(I, "f(x) = Abcd2(x)",
//                AbcdSquared(0.07, 0.07, 0.5, 0.1, 8.0, 10.0),
//                5.0, 6.0,
//                AbcdFunction(0.07, 0.07, 0.5, 0.1).covariance(5.0, 6.0, 8.0, 10.0));
    }


    @Test
    public void testSegment() {
        QL.info("Testing segment integration...");
        testSeveral(new SegmentIntegral(10000));
    }

    @Test
    public void testTrapezoid() {
        QL.info("Testing trapezoid integration...");
        testSeveral(new TrapezoidIntegral<TrapezoidIntegral.Default>(TrapezoidIntegral.Default.class, tolerance, 10000));
    }

    @Test
    public void testMidPointTrapezoid() {
        QL.info("Testing mid-point trapezoid integration...");
        testSeveral(new TrapezoidIntegral<TrapezoidIntegral.MidPoint>(TrapezoidIntegral.MidPoint.class, tolerance, 10000));
    }

    @Test
    public void testSimpson() {
        QL.info("Testing Simpson integration...");
        testSeveral(new SimpsonIntegral(tolerance, 10000));
    }

    @Test
    public void testGaussKronrodAdaptive() {
        QL.info("Testing adaptive Gauss-Kronrod integration...");
        final int maxEvaluations = 1000;
        testSeveral(new GaussKronrodAdaptive(tolerance, maxEvaluations));
    }


//TODO: http://bugs.jquantlib.org/view.php?id=453
//    @Test
//    public void testGaussLobatto() {
//        QL.info("Testing adaptive Gauss-Lobatto integration...");
//        final int maxEvaluations = 1000;
//        testSeveral(new GaussLobattoIntegral(maxEvaluations, tolerance));
//    }

    @Test
    public void testGaussKronrodNonAdaptive() {
        QL.info("Testing non-adaptive Gauss-Kronrod integration...");
        final double precision = tolerance;
        final int maxEvaluations = 100;
        final double relativeAccuracy = tolerance;
        final GaussKronrodNonAdaptive gaussKronrodNonAdaptive = new GaussKronrodNonAdaptive(precision, maxEvaluations, relativeAccuracy);
        testSeveral(gaussKronrodNonAdaptive);
    }

}
