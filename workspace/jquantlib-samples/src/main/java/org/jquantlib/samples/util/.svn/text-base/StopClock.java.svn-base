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

package org.jquantlib.samples.util;

import org.jquantlib.QL;

public class StopClock {

    public static enum Unit {
        /**
         * milliseconds
         */
        ms,

        /**
         * nonoseconds
         */
        ns;
    }

    private final Unit units;
    private long startTime;
    private long stopTime;

    public StopClock() {
        this(Unit.ms);
    }

    public StopClock(final Unit unit) {
        this.units = unit;
    }

    public void startClock() {
        if (units == Unit.ms)
            startTime = System.currentTimeMillis();
        else
            startTime = System.nanoTime();
        stopTime = startTime;
    }

    public void stopClock() {
        if (units == Unit.ms)
            stopTime = System.currentTimeMillis();
        else
            stopTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return stopTime - startTime;
    }

    public Unit getUnit() {
        return units;
    }

    public void reset() {
        startTime = 0;
        stopTime = 0;
    }

    @Override
    public String toString() {
        return ("Time taken: " + getElapsedTime() + units);
    }

    public void log() {
        QL.info(toString());
    }

}
