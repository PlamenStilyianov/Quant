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

/*
 Copyright (C) 2003 RiskMap srl
 Copyright (C) 2006 Piter Dias

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

package org.jquantlib.testsuite.daycounters;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual360;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.ActualActual;
import org.jquantlib.daycounters.Business252;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.daycounters.SimpleDayCounter;
import org.jquantlib.daycounters.Thirty360;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Brazil;
import org.jquantlib.time.calendars.China;
import org.junit.Test;

/**
 * Test Day Counters
 *
 * @author Richard Gomes
 * @author Daniel Kong
 *
 */
public class DayCountersTest {

    public DayCountersTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    private static class SingleCase {
        private final ActualActual.Convention convention;
        private final Date start;
        private final Date end;
        private final Date refStart;
        private final Date refEnd;
        private final /*@Time*/ double  result;

        public SingleCase(
                final ActualActual.Convention convention,
                final Date start,
                final Date end,
                final /*@Time*/ double result) {
            this(convention, start, end, new Date(), new Date(), result);
        }

        public SingleCase(
                final ActualActual.Convention convention,
                final Date start,
                final Date end,
                final Date refStart,
                final Date refEnd,
                final /*@Time*/ double result) {
            this.convention = convention;
            this.start = start;
            this.end = end;
            this.refStart = refStart;
            this.refEnd = refEnd;
            this.result = result;
        }

        private String dumpDate(final Date date) {
            if (date == null || date.isNull())
                return "null";
            else
                return date.isoDate().toString();
        }


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            sb.append(convention).append(", ");
            sb.append(dumpDate(start)).append(", ");
            sb.append(dumpDate(end)).append(", ");
            sb.append(dumpDate(refStart)).append(", ");
            sb.append(dumpDate(refEnd));
            sb.append(" ]");
            return sb.toString();
        }
    }


    @Test
    public void testActualActual() {

        QL.info("Testing actual/actual day counters...");

        final SingleCase testCases[] = new SingleCase[] {
                // first example
                new SingleCase(ActualActual.Convention.ISDA,
                        new Date(1,Month.November,2003), new Date(1,Month.May,2004),
                        0.497724380567),
                        new SingleCase(ActualActual.Convention.ISMA,
                                new Date(1,Month.November,2003), new Date(1,Month.May,2004),
                                new Date(1,Month.November,2003), new Date(1,Month.May,2004),
                                0.500000000000),
                                new SingleCase(ActualActual.Convention.AFB,
                                        new Date(1,Month.November,2003), new Date(1,Month.May,2004),
                                        0.497267759563),
                                        // short first calculation period (first period)
                                        new SingleCase(ActualActual.Convention.ISDA,
                                                new Date(1,Month.February,1999), new Date(1,Month.July,1999),
                                                0.410958904110),
                                                new SingleCase(ActualActual.Convention.ISMA,
                                                        new Date(1,Month.February,1999), new Date(1,Month.July,1999),
                                                        new Date(1,Month.July,1998), new Date(1,Month.July,1999),
                                                        0.410958904110),
                                                        new SingleCase(ActualActual.Convention.AFB,
                                                                new Date(1,Month.February,1999), new Date(1,Month.July,1999),
                                                                0.410958904110),
                                                                // short first calculation period (second period)
                                                                new SingleCase(ActualActual.Convention.ISDA,
                                                                        new Date(1,Month.July,1999), new Date(1,Month.July,2000),
                                                                        1.001377348600),
                                                                        new SingleCase(ActualActual.Convention.ISMA,
                                                                                new Date(1,Month.July,1999), new Date(1,Month.July,2000),
                                                                                new Date(1,Month.July,1999), new Date(1,Month.July,2000),
                                                                                1.000000000000),
                                                                                new SingleCase(ActualActual.Convention.AFB,
                                                                                        new Date(1,Month.July,1999), new Date(1,Month.July,2000),
                                                                                        1.000000000000),
                                                                                        // long first calculation period (first period)
                                                                                        new SingleCase(ActualActual.Convention.ISDA,
                                                                                                new Date(15,Month.August,2002), new Date(15,Month.July,2003),
                                                                                                0.915068493151),
                                                                                                new SingleCase(ActualActual.Convention.ISMA,
                                                                                                        new Date(15,Month.August,2002), new Date(15,Month.July,2003),
                                                                                                        new Date(15,Month.January,2003), new Date(15,Month.July,2003),
                                                                                                        0.915760869565),
                                                                                                        new SingleCase(ActualActual.Convention.AFB,
                                                                                                                new Date(15,Month.August,2002), new Date(15,Month.July,2003),
                                                                                                                0.915068493151),
                                                                                                                // long first calculation period (second period)
                                                                                                                /* Warning: the ISDA case is in disagreement with mktc1198.pdf */
                                                                                                                new SingleCase(ActualActual.Convention.ISDA,
                                                                                                                        new Date(15,Month.July,2003), new Date(15,Month.January,2004),
                                                                                                                        0.504004790778),
                                                                                                                        new SingleCase(ActualActual.Convention.ISMA,
                                                                                                                                new Date(15,Month.July,2003), new Date(15,Month.January,2004),
                                                                                                                                new Date(15,Month.July,2003), new Date(15,Month.January,2004),
                                                                                                                                0.500000000000),
                                                                                                                                new SingleCase(ActualActual.Convention.AFB,
                                                                                                                                        new Date(15,Month.July,2003), new Date(15,Month.January,2004),
                                                                                                                                        0.504109589041),
                                                                                                                                        // short final calculation period (penultimate period)
                                                                                                                                        new SingleCase(ActualActual.Convention.ISDA,
                                                                                                                                                new Date(30,Month.July,1999), new Date(30,Month.January,2000),
                                                                                                                                                0.503892506924),
                                                                                                                                                new SingleCase(ActualActual.Convention.ISMA,
                                                                                                                                                        new Date(30,Month.July,1999), new Date(30,Month.January,2000),
                                                                                                                                                        new Date(30,Month.July,1999), new Date(30,Month.January,2000),
                                                                                                                                                        0.500000000000),
                                                                                                                                                        new SingleCase(ActualActual.Convention.AFB,
                                                                                                                                                                new Date(30,Month.July,1999), new Date(30,Month.January,2000),
                                                                                                                                                                0.504109589041),
                                                                                                                                                                // short final calculation period (final period)
                                                                                                                                                                new SingleCase(ActualActual.Convention.ISDA,
                                                                                                                                                                        new Date(30,Month.January,2000), new Date(30,Month.June,2000),
                                                                                                                                                                        0.415300546448),
                                                                                                                                                                        new SingleCase(ActualActual.Convention.ISMA,
                                                                                                                                                                                new Date(30,Month.January,2000), new Date(30,Month.June,2000),
                                                                                                                                                                                new Date(30,Month.January,2000), new Date(30,Month.July,2000),
                                                                                                                                                                                0.417582417582),
                                                                                                                                                                                new SingleCase(ActualActual.Convention.AFB,
                                                                                                                                                                                        new Date(30,Month.January,2000), new Date(30,Month.June,2000),
                                                                                                                                                                                        0.41530054644)
        };

        for (int i=0; i<testCases.length-1; i++) {
            final ActualActual dayCounter =  new ActualActual(testCases[i].convention);
            final Date d1 = testCases[i].start;
            final Date d2 = testCases[i].end;
            final Date rd1 = testCases[i].refStart;
            final Date rd2 = testCases[i].refEnd;

            QL.info(testCases[i].toString());

            /*@Time*/ final double  calculated = dayCounter.yearFraction(d1, d2, rd1, rd2);

            if (abs(calculated-testCases[i].result) > 1.0e-10) {
                final String period = "period: " + d1 + " to " + d2;
                String refPeriod = "";
                if (testCases[i].convention == ActualActual.Convention.ISMA) {
                    refPeriod = "referencePeriod: " + rd1 + " to " + rd2;
                }
                fail(dayCounter.name() + ":\n"
                        + period + "\n"
                        + refPeriod + "\n"
                        + "    calculated: " + calculated + "\n"
                        + "    expected:   " + testCases[i].result);
            }
        }
    }


    @Test
    public void testSimple() {

        QL.info("Testing simple day counter...");

        final Period p[] = new Period[] { new Period(3, TimeUnit.Months), new Period(6, TimeUnit.Months), new Period(1, TimeUnit.Years) };
        /*@Time*/ final double expected[] = { 0.25, 0.5, 1.0 };

        // 4 years should be enough
        final Date first = new Date(1,Month.January,2002);
        final Date last  = new Date(31,Month.December,2005);
        final DayCounter dayCounter = new SimpleDayCounter();

        for (final Date start = first; start.le(last); start.inc()) {
            for (int i=0; i<expected.length-1; i++) {
                final Date end = start.add(p[i]);
                /*@Time*/ final double  calculated = dayCounter.yearFraction(start, end);

                if (abs(calculated-expected[i]) > 1.0e-12) {
                    fail("from " + start + " to " + end + ":\n"
                            + "    calculated: " + calculated + "\n"
                            + "    expected:   " + expected[i]);
                }
            }
        }
    }

    @Test
    public void testOne() {

        QL.info("Testing 1/1 day counter...");

        final Period p[] = new Period[]{ new Period(3, TimeUnit.Months), new Period(6, TimeUnit.Months), new Period(1, TimeUnit.Years) };
        /*@Time*/ final double expected[] = new double[] { 1.0, 1.0, 1.0 };

        // 1 years should be enough
        final Date first = new Date(1,Month.January,2004);
        final Date last  = new Date(31,Month.December,2004);
        final DayCounter dayCounter = new SimpleDayCounter();

        for (final Date start = first; start.le(last); start.inc()) {
            for (int i=0; i<expected.length-1; i++) {
                final Date end = start.add(p[i]);
                /*@Time*/ final double  calculated = dayCounter.yearFraction(start, end);

                if (abs(calculated-expected[i]) <= 1.0e-12) {
                    fail("from " + start + " to " + end + ":\n"
                            + "    calculated: " + calculated + "\n"
                            + "    expected:   " + expected[i]);
                }
            }
        }
    }

    //TODO: Sounds like this test method from the C++ codes actually test nothing!
    //abs(calculated - expected[i]) <= 1.0e-12? making sense? could always pass. Daniel
    @Test
    public void testBusiness252() {

        QL.info("Testing business/252 day counter...");

        final Date testDates[] = {
                new Date(1,Month.February,2002),
                new Date(4,Month.February,2002),
                new Date(16,Month.May,2003),
                new Date(17,Month.December,2003),
                new Date(17,Month.December,2004),
                new Date(19,Month.December,2005),
                new Date(2,Month.January,2006),
                new Date(13,Month.March,2006),
                new Date(15,Month.May,2006),
                new Date(17,Month.March,2006),
                new Date(15,Month.May,2006),
                new Date(26,Month.July,2006) };

        /*@Time*/ final double expected[] = {
                0.0039682539683,
                1.2738095238095,
                0.6031746031746,
                0.9960317460317,
                1.0000000000000,
                0.0396825396825,
                0.1904761904762,
                0.1666666666667,
                -0.1507936507937,
                0.1507936507937,
                0.2023809523810
        };

        final DayCounter dayCounter = new Business252(new Brazil(Brazil.Market.SETTLEMENT));

        for (int i=1; i<testDates.length-1; i++) {
            final Date start = testDates[i-1];
            final Date end = testDates[i];
            /*@Time*/ final double  calculated = dayCounter.yearFraction(start, end);
            // System.out.println(calculated);
            assertFalse(dayCounter.getClass().getName()
                    +"\n from "+start
                    +"\n to "+end
                    +"\n calculated: "+calculated
                    +"\n expected:   "+expected[i],
                    abs(calculated - expected[i]) <= 1.0e-12);
        }
    }
    
    @Test
    public void testEqualityHashCode() {

        QL.info("Testing Equality and HashCode ...");
        final DayCounter business252Brazil = new Business252(new Brazil(Brazil.Market.SETTLEMENT));
        final DayCounter business252Brazil1 = new Business252(new Brazil(Brazil.Market.SETTLEMENT));

        
        final DayCounter business252China = new Business252(new China(China.Market.SSE));
        final DayCounter simpleDayCounter = new SimpleDayCounter();      
        final DayCounter actual360 = new Actual360();        
        final DayCounter actual365Fixed = new Actual365Fixed();        
        final DayCounter actualActual = new ActualActual();        
        final DayCounter thirty360 = new Thirty360();        
        final DayCounter thirty360_2 = new Thirty360(); 
        
        assertFalse(thirty360.equals(null));
        assertEquals(thirty360, thirty360);
        assertEquals(thirty360, thirty360_2);
        
        assertFalse(simpleDayCounter.equals(business252Brazil));
        assertFalse(business252Brazil.equals(simpleDayCounter));
        assertFalse(actual360.equals(actual365Fixed));
        assertFalse(actual365Fixed.equals(actual360));
        assertFalse(actualActual.equals(thirty360));
        assertFalse(thirty360.equals(actualActual));
        assertFalse(business252Brazil.equals(business252China));
        assertFalse(business252China.equals(business252Brazil));
        assertTrue(business252Brazil.equals(business252Brazil1));
        
        assertTrue(business252Brazil.eq(business252Brazil1));
        assertFalse(business252Brazil.ne(business252Brazil1));
        
        HashSet<DayCounter> testSet = new HashSet<DayCounter>();
        testSet.add(thirty360);
        
        assertTrue(testSet.contains(thirty360));
        assertFalse(testSet.contains(actualActual));
        
    }

}
