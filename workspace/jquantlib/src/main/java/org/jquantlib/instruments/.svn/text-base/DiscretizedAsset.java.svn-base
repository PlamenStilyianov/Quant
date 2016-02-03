/*
 Copyright (C) 2008 Srinivas Hasti

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
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2004, 2005, 2006 StatPro Italia srl

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

package org.jquantlib.instruments;

import java.util.List;

import org.jquantlib.math.Closeness;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.lattices.Lattice;
import org.jquantlib.time.TimeGrid;

/**
 * Discretized asset class used by numerical methods
 *
 * @author Srinivas Hasti
 */
public abstract class DiscretizedAsset {

    protected double /* @Time */latestPreAdjustment;
    protected double /* @Time */latestPostAdjustment;
    protected double /* @Time */time;
    protected Array values_;

	private Lattice method;


	//
	// public constructors
	//

	public DiscretizedAsset() {
		this.latestPostAdjustment = Double.MAX_VALUE;
		this.latestPreAdjustment = Double.MAX_VALUE;
	}


    //
    // public methods
    //

	public /* @Time */ double time() {
		return time;
	}

	public void setTime(final double t){
		this.time = t;
	}

	public Array values() {
		return values_;
	}

	public Lattice method() {
		return method;
	}


    //////////////////////////////////////////////////////////////////////////////
	//
	// High-level interface
	//
	// Users of discretized assets should use these methods in order to
	// initialize, evolve and take the present value of the assets. They call
	// the corresponding methods in the Lattice interface, to which we refer for
	// documentation.
	//
    //////////////////////////////////////////////////////////////////////////////

	//
	// public methods
	//

	public void initialize(final Lattice method, final /* @Time */ double t) {
		this.method = method;
		method.initialize(this, t);
	}

	public void rollback(final /* @Time */ double to) {
		method.rollback(this, to);
	}

	public void partialRollback(final /* @Time */ double to) {
		method.partialRollback(this, to);
	}

	public /* @Real */ double presentValue() {
		return method.presentValue(this);
	}


	//////////////////////////////////////////////////////////////////////////////
	//
	// Low-level interface
	//
	// These methods (that developers should override when deriving from
	// DiscretizedAsset) are to be used by numerical methods and not directly by
	// users, with the exception of adjustValues(), preAdjustValues() and
	// postAdjustValues() that can be used together with partialRollback().
	//
    //////////////////////////////////////////////////////////////////////////////


	//
	// abstract methods
	//

	/**
	 * This method should initialize the asset values to an Array of the given
	 * size and with values depending on the particular asset.
	 */
	public abstract void reset(/* Size */int size);

    /**
     * This method returns the times at which the numerical method should stop
     * while rolling back the asset. Typical examples include payment times,
     * exercise times and such.
     *
     * @note The returned values are not guaranteed to be sorted.
     */
    public abstract List</* Time */Double> mandatoryTimes();


	//
	// public methods
	//

	/**
	 * This method will be invoked after rollback and before any other asset
	 * (i.e., an option on this one) has any chance to look at the values. For
	 * instance, payments happening at times already spanned by the rollback
	 * will be added here.
	 * <p>
	 * This method is not virtual; derived classes must override the protected
	 * preAdjustValuesImpl() method instead.
	 */
	public void preAdjustValues() {
		if (!Closeness.isCloseEnough(time(), latestPreAdjustment)) {
			preAdjustValuesImpl();
			latestPreAdjustment = time();
		}
	}

	/**
	 * This method will be invoked after rollback and after any other asset
	 * had their chance to look at the values. For instance, payments happening
	 * at the present time (and therefore not included in an option to be
	 * exercised at this time) will be added here.
	 * <p>
	 * This method is not virtual; derived classes must override the protected
	 * postAdjustValuesImpl() method instead.
	 */
	public void postAdjustValues() {
		if (!Closeness.isCloseEnough(time(), latestPostAdjustment)) {
			postAdjustValuesImpl();
			latestPostAdjustment = time();
		}
	}

	/**
	 * This method performs both pre- and post-adjustment
	 */
	public void adjustValues() {
		preAdjustValues();
		postAdjustValues();
	}

	public void setValues(final Array newValues) {
		this.values_ = newValues;
	}


	//
	// protected methods
	//

    protected boolean isOnTime(final /* @Time */ double t) {
        final TimeGrid grid = method().timeGrid();
        return Closeness.isCloseEnough(grid.at(grid.index(t)), time());
    }

    /**
     * This method performs the actual pre-adjustment
     * <p>
     * This method is left to be overridden by extended classes
     */
    protected void preAdjustValuesImpl() {
        // nothing
    }


    /**
     * This method performs the actual post-adjustment
     * <p>
     * This method is left to be overridden by extended classes
     */
    protected void postAdjustValuesImpl() {
        // nothing
    }

}
