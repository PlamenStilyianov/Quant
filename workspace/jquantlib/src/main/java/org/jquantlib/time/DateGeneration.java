/*
 Copyright (C) 2008 Srinivas Hasti
 Copyright (C) 2009 Zahid Hussain

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

package org.jquantlib.time;

import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.lang.exceptions.LibraryException;

/**
 * @author Srinivas Hasti
 * @author Zahid Hussain
 */
@QualityAssurance(quality=Quality.Q3_DOCUMENTATION, version=Version.V097, reviewers="Richard Gomes")
public class DateGeneration {

    public static enum Rule {
        /**
         * Backward from termination date to effective date.
         */
        Backward   (1),

        /**
         * Forward from effective date to termination date.
         */
        Forward   (2),

        /**
         * No intermediate dates between effective date and termination date.
         */
        Zero   (3),

        /**
         * All dates but effective date and termination date are taken to be on the third wednesday of their month (with forward
         * calculation.)
         */
        ThirdWednesday   (4),

        /**
         * All dates but the effective date are taken to be the twentieth of their month (used for CDS schedules in emerging
         * markets.) The termination date is also modified.
         */
        Twentieth   (5),

        /**
         * All dates but the effective date are taken to be the twentieth of an IMM month (used for CDS schedules.) The termination
         * date is also modified.
         */
        TwentiethIMM   (6);


        //
        // private static fields
        //

        private static final String UNKNOWN_DATE_GENERATION_RULE = "unknown date generation rule";


        //
        // private fields
        //

        private final int rule;


        //
        // private constructors
        //

        private Rule(final int rule) {
            this.rule = rule;
        }


        //
        // overrides Object
        //

        @Override
        public String toString() {
            switch (rule) {
            case 1:
                return "Backward";
            case 2:
                return "Forward";
            case 3:
                return "Zero";
            case 4:
                return "ThirdWednesday";
            case 5:
                return "Twentieth";
            case 6:
                return "TwentiethIMM";
            default:
                throw new LibraryException(UNKNOWN_DATE_GENERATION_RULE);
            }
        }

    }

}
