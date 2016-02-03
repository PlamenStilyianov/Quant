/*
 Copyright (C) 2007 Richard Gomes

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

package org.jquantlib.testsuite.util;

import org.jquantlib.util.Observer;

/**
 * This is a helper class intended to serve as a simple {@link Observer}.
 */
public class Flag implements Observer {

    private boolean	up;

    public Flag() {
        up = false;
    }

    public void raise() {
        up = true;
    }

    public void lower() {
        up = false;
    }

    public boolean isUp() /* @ReadOnly */{
        return up;
    }

    //
    // implements Observer
    //

    //XXX:registerWith
    //	@Override
    //    public void registerWith(final Observable o) {
    //        o.addObserver(this);
    //    }
    //
    //    @Override
    //    public void unregisterWith(final Observable o) {
    //        o.deleteObserver(this);
    //    }

    @Override
    //XXX::OBS public void update(final Observable observable, final Object o) {
    public void update() {
        raise();
    }

}