/*
 Copyright (C) 2008 Q Boiler

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


import org.joda.primitives.list.impl.ArrayDoubleList;
import org.jquantlib.time.Date;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListTest {

    private final static Logger logger = LoggerFactory.getLogger(ListTest.class);

    Date startDate = null;

    @Before
    public void init() {
    }

    @Test
    public void testJavaUtilList() {
		final long startNano = System.nanoTime();
		final ArrayDoubleList dal = new ArrayDoubleList();
		final java.util.List list = dal;
		for(double i = 0.0; i<1000 ; i=i+.01){
			list.add(i);
		}

		final long totalTime = System.nanoTime() - startNano;
		logger.info("      java.util.list with  autoboxing : "+ totalTime +" nano seconds");
    }


    @Test
    public void testJQuantList() {
		final long startNano = System.nanoTime();
		final ArrayDoubleList dal = new ArrayDoubleList();
		final org.jquantlib.experimental.StrategyLoadingList list = new org.jquantlib.experimental.StrategyLoadingList(dal);
		for(double i = 0.0; i<1000 ; i=i+.01){
			list.add(i);
		}
		final long totalTime = System.nanoTime() - startNano;

		logger.info("org.jqauntlib.util.List NO autoboxing : "+ totalTime +" nano seconds");
    }

}
