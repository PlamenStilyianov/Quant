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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006, 2007 StatPro Italia srl

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

package org.jquantlib.quotes;

import org.jquantlib.util.Observable;

/**
 * Relinkable handle to an observable
 * <p>
 * An instance of this class can be relinked so that it points to another
 * observable. The change will be propagated to all handles that were created as
 * copies of such instance.
 *
 * @author Richard Gomes
 */
//FIXME:
public class RelinkableHandle<T extends Observable> extends Handle<T> {

    public RelinkableHandle() {
        super();
    }

	public RelinkableHandle(final T observable) {
    	this(observable, true);
    }

    public RelinkableHandle(final T observable, final boolean isObserver) {
    	super(observable, isObserver);
    }

    @Override
    public final void linkTo(final T observable) {
    	super.internalLinkTo(observable, true);
    }

    @Override
    public final void linkTo(final T observable, final boolean isObserver) {
    	super.internalLinkTo(observable, isObserver);
    }

}
