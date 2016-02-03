package org.jquantlib.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This annotation is intended to add Quality Assurance information to types
 *
 * @author Richard Gomes
 */
@Target( { ElementType.TYPE })
public @interface QualityAssurance {

    Version version();
    Quality quality();
    String[] reviewers();


    //
    // inner enums
    //

    public enum Version {
        /**
         * Not verified against any version
         */
        NONE,


        /**
         * Not based on QuantLib
         */
        OTHER,

        /**
         * QuantLib v0.8.1
         */
        V081,

        /**
         * QuantLib v0.9.7
         */
        V097

    }

    public enum Quality {
        /**
         * Translation not finished. Needs detailed code review.
         */
        Q0_UNFINISHED,

        /**
         * Translation finished, no translation issues, not doubts, no pending items.
         */
        Q1_TRANSLATION,

        /**
         * JQuantLib API resembles QuantLib API close enough, including operator overloading, if any
         */
        Q2_RESEMBLANCE,

        /**
         * License, header comments, class comments, @Override, access modifiers,
         */
        Q3_DOCUMENTATION,

        /**
         * Pass on Unit tests (JUnit4). Calculation accuracy inside tolerance.
         */
        Q4_UNIT,

        /**
         * Pass on integration tests, such as sample code which exercises the functionality
         */
        Q5_INTEGRATION,

        /**
         * Code ready to be released.
         */
        Q6_RELEASED,

        /**
         * Pass code profiling
         */
        Q7_PROFILE,

        /**
         * Performance tests are considered acceptable
         */
        Q8_PERFORMANCE
    }

}
