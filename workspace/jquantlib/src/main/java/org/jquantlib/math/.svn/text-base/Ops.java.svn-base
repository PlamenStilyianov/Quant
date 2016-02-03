package org.jquantlib.math;


/**
 * This is an interim interface which will be replaced in future by an interface of same name from JSR-166y-extra
 *
 * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.Op.html">Op</a>
 * @author Richard Gomes
 */
public interface Ops {


    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     *
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.Op.html">Op</a>
     * @author Richard Gomes
     */
    public interface Op<A,R> {
        public R op(A a);
    }


    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     *
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.DoubleOp.html">DoubleOp</a>
     * @author Richard Gomes
     */
    public interface DoubleOp {

        public double op(double x);
        //TODO: boolean isFailed() // TODO is error handling needed?
    }


    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     *
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.BinaryDoubleOp.html">BinaryDoubleOp</a>
     * @author Richard Gomes
     */
    public interface BinaryDoubleOp {
        public double op(double x, double y);
        //FIXME: boolean isFailed() TODO error handling
    }


    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     *
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.IntToDouble.html">IntToDouble</a>
     * @author Richard Gomes
     */
    //TODO : consider http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.BinaryDoubleOp.html
    public interface IntToDouble {
        public double op(int x);
        //FIXME: boolean isFailed() // TODO is error handling needed?
    }


    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     *
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/ObjectToDouble.html">ObjectToDouble</a>
     * @author Richard Gomes
     */
    public interface ObjectToDouble<A> {
        public double op(A a);
    }



    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     *
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.DoublePredicate.html">DoublePredicate</a>
     * @author Richard Gomes
     */
    public static interface DoublePredicate {
        public boolean op(double a);
    }


    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     *
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.BinaryDoublePredicate.html">BinaryDoublePredicate</a>
     * @author Richard Gomes
     */
    public static interface BinaryDoublePredicate {
        public boolean op(double a, double b);
    }

    /**
     * This is an interim method which will be replaced in future by a method of same name from JSR-166y-extra
     * 
     * @see <a href="http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/extra166y/Ops.DoubleGenerator.html">DoubleGenerator</a>
     * @author Zahid Hussain.
     */
    public static interface DoubleGenerator {
    	public double op();
    }
}
