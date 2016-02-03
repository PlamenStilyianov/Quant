/*
 Copyright (C) 2009 Ueli Hofstetter

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

package org.jquantlib.cashflow;

import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.volatilities.optionlet.OptionletVolatilityStructure;

// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class IborCouponPricer extends FloatingRateCouponPricer {

    public static final String no_adequate_capletVol_given = "no adequate capletVol given";

    private Handle<OptionletVolatilityStructure> capletVol_;

    public IborCouponPricer(final Handle<OptionletVolatilityStructure> capletVol){
        this.capletVol_ = capletVol;
        this.capletVol_.addObserver(this);
        //XXX:registerWith
        //registerWith(this.capletVol_);
    }

    public Handle<OptionletVolatilityStructure> capletVolatility(){
        return capletVol_;
    }

    public void setCapletVolatility(final Handle<OptionletVolatilityStructure> capletVol){
        if (capletVol!=null) {
            capletVol.currentLink().deleteObserver(this);
        }
        this.capletVol_ = capletVol;
        if (this.capletVol_!=null) {
            this.capletVol_.addObserver(this);
        }
        update();
    }

    @Override
    public void update(){
        notifyObservers();
    }
}
