/*
 Copyright (C) 2008 Daniel Kong

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

package org.jquantlib.samples;

import static org.jquantlib.time.Month.February;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Weekday;

/**
 * This example prices a few bermudan swaptions using different short-rate models calibrated to market swaptions.
 *
 * @see http://quantlib.org/reference/_bermudan_swaption_8cpp-example.html
 *
 * @author Daniel Kong
 */
// TODO: Work in progress
public class BermudanSwaption implements Runnable {

    public static void main(final String[] args) {
        new BermudanSwaption().run();
    }

    @Override
    public void run() {

        QL.validateExperimentalMode();

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();

        final Date todaysDate = new Date(15, February, 2002);

        // TODO: code review :: please verify against QL/C++ code
        final Calendar calendar = new Calendar() {
            public String getName() {
                return "";
            }

            @Override
            public boolean isWeekend(final Weekday w) {
                throw new UnsupportedOperationException();
            }
        };

        final Date settlementDate = new Date(19, February, 2002);
        new Settings().setEvaluationDate(todaysDate);

        // TODO: code review :: please verify against QL/C++ code

        clock.stopClock();
        clock.log();
    }

}
