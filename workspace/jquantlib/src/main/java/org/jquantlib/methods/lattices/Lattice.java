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
 Copyright (C) 2004, 2005 StatPro Italia srl

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

package org.jquantlib.methods.lattices;

import org.jquantlib.instruments.DiscretizedAsset;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.time.TimeGrid;

/**
 * Lattice (tree, finite-differences) base class
 *
 * @author Srinivas Hasti
 */
public abstract class Lattice {

    protected TimeGrid t;

    //
    // public constructors
    //

    public Lattice(final TimeGrid t) {
        this.t = t;
    }

    //
    // public methods
    //

    public TimeGrid timeGrid() {
        return t;
    }


    ////////////////////////////////////////////////////////////////////////////////////
    //
    // Numerical method interface
    //
    // These methods are to be used by discretized assets and must be overridden
    // by developers implementing numerical methods. Users are advised to use
    // the corresponding methods of DiscretizedAsset instead.
    //
    ////////////////////////////////////////////////////////////////////////////////////

    //
    // abstract methods
    //

    /**
     * initialize an asset at the given time.
     */
    public abstract void initialize(final DiscretizedAsset asset, final /* Time */ double time);

    /**
     * Roll back an asset until the given time, performing any needed adjustment.
     */
    public abstract void rollback(final DiscretizedAsset asset, final /* Time */double to);

    /**
     * Roll back an asset until the given time, but do not perform the final adjustment.
     * <p>
     * @warning In version 0.3.7 and earlier, this method was called rollAlmostBack and performed pre-adjustment.
     * This is no longer true; when migrating your code, you'll have to replace calls such as:
     * <pre>
     * method->rollAlmostBack(asset,t);
     * </pre>
     * with the two statements:
     * <pre>
     * method->partialRollback(asset,t);
     * asset->preAdjustValues();
     * </pre>
     */
    public abstract void partialRollback(final DiscretizedAsset asset, final /* Time */double to);

    /**
     * computes the present value of an asset.
     */
    public abstract/* Real */double presentValue(DiscretizedAsset asset);

    /**
     * This is smelly, but we need it. We'll rethink it later.
     * @param t
     * @return
     */
    public abstract Array grid(double t);
}
