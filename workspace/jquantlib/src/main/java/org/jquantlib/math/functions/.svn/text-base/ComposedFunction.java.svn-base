package org.jquantlib.math.functions;

import org.jquantlib.math.Ops;

public class ComposedFunction implements Ops.DoubleOp {

    private final Ops.DoubleOp f;
    private final Ops.DoubleOp g;

    public ComposedFunction(final Ops.DoubleOp f, final Ops.DoubleOp g) {
        this.f = f;
        this.g = g;
    }


    //
    // implements Ops.DoubleOp
    //

    /**
     * @return {@latex$ f(g(x)) }
     */
	@Override
	public double op(final double x) {
		return f.op(g.op(x));
	}
	
}
