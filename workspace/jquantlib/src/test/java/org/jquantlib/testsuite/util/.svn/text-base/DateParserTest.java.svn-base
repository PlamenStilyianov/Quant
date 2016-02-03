/*
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

package org.jquantlib.testsuite.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jquantlib.time.Date;
import org.jquantlib.time.DateParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for DateParser
 * 
 * @author Zahid Hussain
 * 
 */
public class DateParserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParseISO() {
        final String dtStr =  "2009-09-05";

        final Date expectedDt = new Date(05,9,2009);
        final Date unExpectedDt = new Date(05,9,2008);

        final Date parsedDt = DateParser.parseISO(dtStr);
        assertTrue(expectedDt.eq(parsedDt));
        assertFalse(unExpectedDt.eq(parsedDt));
    }

    @Test
    public void testParse() {
        final String dtStr =  "2009/09/05";
        final String fmtStr = "yyyy/MM/dd";

        final Date expectedDt = new Date(05,9,2009);
        final Date unExpectedDt = new Date(05,9,2008);

        final Date parsedDt = DateParser.parse(dtStr, fmtStr);
        assertTrue(expectedDt.eq(parsedDt));
        assertFalse(unExpectedDt.eq(parsedDt));

    }

}
