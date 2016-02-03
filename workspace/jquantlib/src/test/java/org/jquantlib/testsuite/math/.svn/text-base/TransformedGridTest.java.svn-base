package org.jquantlib.testsuite.math;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.math.Grid;
import org.jquantlib.math.Ops;
import org.jquantlib.math.TransformedGrid;
import org.jquantlib.math.matrixutilities.Array;
import org.junit.Test;

public class TransformedGridTest {

    public TransformedGridTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    @Test
    public void testConstruction() {

        QL.info("Testing transformed grid construction...");

        final PlusOne p1 = new PlusOne();
        final Array grid = Grid.BoundedGrid(0, 100, 100);
        final TransformedGrid tg = new TransformedGrid(grid, p1);
        if (Math.abs(tg.grid(0) - 0.0) > 1e-5) {
            fail("grid creation failed");
        }

        if (Math.abs(tg.transformedGrid(0) - 1.0) > 1e-5) {
            fail("grid transformation failed");
        }
    }


    private final class PlusOne implements Ops.DoubleOp {

        //
        // implements Ops.DoubleOp
        //

        @Override
        public double op(final double a) {
            return a + 1.0;
        }

    }

}
