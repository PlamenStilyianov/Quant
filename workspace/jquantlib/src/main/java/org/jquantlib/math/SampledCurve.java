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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2004 StatPro Italia srl

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 */

package org.jquantlib.math;

import org.jquantlib.QL;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.functions.Identity;
import org.jquantlib.math.interpolations.CubicInterpolation;
import org.jquantlib.math.interpolations.NaturalCubicInterpolation;
import org.jquantlib.math.matrixutilities.Array;

/**
 * This class contains a sampled curve.
 * <p>
 * Initially the class will contain one indexed curve
 *
 * @author Dominik Holenstein
 */
public class SampledCurve implements Cloneable {

    //
    // private fields
    //

    private Array grid;
    private Array values;

    //
    // Constructors
    //

    public SampledCurve(final int gridSize) {
        this.grid   = new Array(gridSize);
        this.values = new Array(gridSize);
    }

    public SampledCurve(final Array grid) {
        // TODO: code review :: use of clone()
        this.grid   = grid;
        this.values = new Array(this.grid.size());
    }

    /**
     * Copy constructor
     *
     * @param that
     */
    public SampledCurve(final SampledCurve that) {
        // TODO: code review :: use of clone()
        this.grid   = that.grid.clone();
        this.values = that.values.clone();
    }


    //
    // public methods
    //

    @Override
    public SampledCurve clone() {
        //XXX final SampledCurve result = new SampledCurve(this);
        try {
            return (SampledCurve) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new LibraryException(e);
        }
    }

    public int size() {
        return grid.size();
    }

    public SampledCurve swap(final SampledCurve another) {
        this.grid.swap(another.grid);
        this.values.swap(another.values);
        return this;
    }

    public Array grid() /* @Readonly */{
        return grid;
    }

    public Array values() /* @Readonly */{
        return values;
    }

    public double gridValue(final int i) {
        return grid.get(i);
    }

    public double value(final int i) {
        return values.get(i);
    }

    private boolean empty() /* @Readonly */{
        return this.grid.empty();
    }

    public void setGrid(final Array g) {
        // TODO: RICHARD:: code review :: use of clone()
        this.grid = g;
    }

    public void setValues(final Array array) {
        // TODO: RICHARD:: code review :: use of clone()
        this.values = array;
    }

    public void setLogGrid(final double min, final double max) {
        setGrid(Grid.BoundedLogGrid(min, max, size() - 1));
    }

    public <T extends Ops.DoubleOp> void sample(final T func) {
        for (int i = 0; i < this.grid.size(); i++) {
            final double v = func.op(grid.get(i));
            this.values.set(i, v);
        }
    }

    public void shiftGrid(final double s) {
        this.grid.addAssign(s);
    }

    public void scaleGrid(final double s) {
        this.grid.mulAssign(s);
    }


    public double valueAtCenter() /* @Readonly */{
        QL.require(!empty() , "empty sampled curve"); // TODO: message
        final int jmid = size() / 2;
        if (size() % 2 != 0)
            return values.get(jmid);
        else
            return (values.get(jmid) + values.get(jmid - 1)) / 2.0;
    }

    public double firstDerivativeAtCenter() /* @Readonly */{
        QL.require(size() >= 3 , "the size of the curve must be at least 3"); // TODO: message
        final int jmid = size() / 2;
        if (size() % 2 != 0)
            return (values.get(jmid + 1) - values.get(jmid - 1)) / (grid.get(jmid + 1) - grid.get(jmid - 1));
        else
            return (values.get(jmid) - values.get(jmid - 1)) / (grid.get(jmid) - grid.get(jmid - 1));
    }

    public double secondDerivativeAtCenter() /* @Readonly */{
        QL.require(size() >= 4 , "the size of the curve must be at least 4"); // TODO: message
        final int jmid = size() / 2;
        if (size() % 2 != 0) {
            final double deltaPlus = (values.get(jmid + 1) - values.get(jmid)) / ((grid.get(jmid + 1) - grid.get(jmid)));
            final double deltaMinus = (values.get(jmid) - values.get(jmid - 1)) / ((grid.get(jmid) - grid.get(jmid - 1)));
            final double dS = (grid.get(jmid + 1) - grid.get(jmid - 1)) / 2.0;
            return (deltaPlus - deltaMinus) / dS;
        } else {
            final double deltaPlus = (values.get(jmid + 1) - values.get(jmid - 1)) / ((grid.get(jmid + 1) - grid.get(jmid - 1)));
            final double deltaMinus = (values.get(jmid) - values.get(jmid - 2)) / (grid.get(jmid) - grid.get(jmid - 2));
            return (deltaPlus - deltaMinus) / (grid.get(jmid) - grid.get(jmid - 1));
        }
    }

    public void regrid(final Array newGrid) {
        regrid(newGrid, new Identity());
    }

    /**
     * @note This method modifies contents of parameter newGrid
     *
     * @param newGrid
     * @param f
     */
    public void regrid(final Array newGrid, final Ops.DoubleOp f) {
        final Array transformed;
        final Array newValues;

        if (f instanceof Identity) {
            transformed = this.grid;
            newValues = newGrid.clone();
        } else {
            transformed = this.grid.clone().transform(f);
            newValues = newGrid.clone().transform(f);
        }

        final CubicInterpolation priceSpline = new NaturalCubicInterpolation(transformed, values);

        priceSpline.update();
        for (int i=0; i<newValues.size(); i++) {
            newValues.set(i, priceSpline.op(newValues.get(i), true) );
        }

        this.grid.swap(newGrid);
        this.values.swap(newValues);
    }

}
