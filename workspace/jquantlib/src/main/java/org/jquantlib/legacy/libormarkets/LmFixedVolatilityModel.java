/*
 Copyright (C)
 2009  Ueli Hofstetter

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

package org.jquantlib.legacy.libormarkets;

import org.jquantlib.QL;
import org.jquantlib.math.matrixutilities.Array;

public class LmFixedVolatilityModel extends LmVolatilityModel {

    private final Array volatilities_;
    private final Array startTimes_;

    public LmFixedVolatilityModel(final Array volatilities, final Array startTimes) {
        super(startTimes.size(), 0);

        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        // TODO: code review :: use of clone()
        this.volatilities_ = volatilities;
        this.startTimes_ = startTimes;

        QL.require(startTimes_.size()>1 , "too few dates"); // TODO: message
        QL.require(volatilities_.size() == startTimes_.size() , "volatility array and fixing time array have to have the same size"); // TODO: message

        for (int i = 1; i < startTimes_.size(); i++)
            if(startTimes_.get(i) <= startTimes_.get(i-1))
                throw new IllegalArgumentException("invalid time (" + startTimes_.get(i) + ", vs " + startTimes_.get(i) + ")");
    }

    @Override
    protected void generateArguments() {
        return;
    }

    @Override
    public Array volatility(final double t, final Array x) {
        QL.require((t < startTimes_.first() || t > startTimes_.last()) , "invalid time given for volatility model"); // TODO: message
        final int ti = (int) (startTimes_.upperBound(t) - startTimes_.first() - 1);

        final Array tmp = new Array(size_);

        for (int i = ti; i < size_; ++i)
            tmp.set(i, volatilities_.get(i - ti));
        final Array ret = new Array(tmp.size());//ZH: translation not as QL097
        for (int i = 0; i < tmp.size(); i++)
            ret.set(i, tmp.get(i));
        return ret;
    }

    @Override
    public double /* @Volatility */volatility(final int i, /* @Time */final double t, final Array x) {
        if (t < startTimes_.first() || t > startTimes_.last())
            throw new IllegalArgumentException("invalid time given for volatility model");
        final int ti = (int) (startTimes_.upperBound(t) - startTimes_.first() - 1);

        return volatilities_.get(i - ti);
    }

}
