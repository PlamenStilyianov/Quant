/*
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

/*
 Copyright (C) 2002, 2003 Ferdinando Ametrano

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

package org.jquantlib.pricingengines;

import org.jquantlib.instruments.Instrument;
import org.jquantlib.model.CalibratedModel;

/**
 * Base class for some pricing engine on a particular model.
 * <p>
 * Derived engines only need to implement the <code>{@link calculate()}</code> method.
 *
 * @author Richard Gomes
 */
public abstract class GenericModelEngine<M extends CalibratedModel,
                                         A extends Instrument.Arguments,
                                         R extends Instrument.Results>
        extends GenericEngine<A, R> {


    //
    // protected fields
    //

    protected M model;


    //
    // public methods
    //

    public GenericModelEngine(final A arguments, final R results) {
        super(arguments, results);
    }

    public GenericModelEngine(final M model, final A arguments, final R results) {
        super(arguments, results);
        this.model = model;
        this.model.addObserver(this);
    }

    public void setModel(final M model) {
        if (this.model!=null)
            this.model.deleteObserver(this);
        this.model = model;
        if (this.model!=null)
            this.model.addObserver(this);
        update();
    }

}
