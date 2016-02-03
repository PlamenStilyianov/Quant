/*
 Copyright (C) 2010 Richard Gomes

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
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2003 RiskMap srl
 Copyright (C) 2005 Gary Kennedy

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
package org.jquantlib.testsuite.math.statistics;

import static org.junit.Assert.fail;

import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.Real;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.statistics.ConvergenceStatistics;
import org.jquantlib.math.statistics.GenericRiskStatistics;
import org.jquantlib.math.statistics.GenericSequenceStatistics;
import org.jquantlib.math.statistics.IncrementalStatistics;
import org.jquantlib.math.statistics.RiskStatistics;
import org.jquantlib.util.Pair;
import org.junit.Test;

/**
 * Statistics test cases
 * 
 * @author Richard Gomes
 */
public class StatisticsTest {

    private final Array data;
    private final Array weights;
    

    public StatisticsTest() {
        QL.info("Testing volatility model construction...");
        this.data    = new Array(new double[] { 3.0, 4.0, 5.0, 2.0, 3.0, 4.0, 5.0, 6.0, 4.0, 7.0 });
        this.weights = new Array(new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 });
    }

    @Test
    public void testStatistics() {
        QL.info("Testing statistics ...");
        check(new RiskStatistics(), "Statistics");
    }

    @Test
    public void testIncrementalStatistics() {
        QL.info("Testing incremental statistics ...");
        check(new IncrementalStatistics(), "IncrementalStatistics");
    }

    
    @Test
    public void testSequenceStatistics() {
        QL.info("Testing sequence statistics ...");
        checkSequence(new RiskStatistics(), "Statistics", 5);
        checkSequence(new IncrementalStatistics(), "IncrementalStatistics", 5);
    }
    
    
    @Test
    public void testConvergenceStatistics() {
        QL.info("Testing convergence statistics ...");
        checkConvergence(new RiskStatistics(), "Statistics");
        checkConvergence(new IncrementalStatistics(), "IncrementalStatistics");
    }
    
    
    
    private void check(final GenericRiskStatistics s, final String name) {
        for (int i = 0; i<data.size(); i++)
            s.add(data.get(i), weights.get(i));

        double calculated, expected;
        double tolerance;

        if (s.samples()!=data.size())
            fail("wrong number of samples \n" +
                    "calculated: " + s.samples() + "\n" +
                    "expected: " + data.size());
        
        expected = weights.accumulate();
        calculated = s.weightSum();
        if (calculated != expected)
            fail(name  + ": wrong sum of weights\n"
            + "    calculated: " + calculated + "\n"
            + "    expected:   " + expected);

        expected = data.min();
        calculated = s.min();
        if (calculated != expected)
            fail(name + ": wrong minimum value \n" +
                    "calculated: " + calculated + "\n" +
                    "expected: " + expected);

        expected = data.max();
        calculated = s.max();
        if (calculated != expected)
            fail(name + ": wrong maxmimum value \n" +
                    "calculated: " + expected + "\n" +
                    "expected: " + expected);

        expected = 4.3;
        tolerance = 1.0e-9;
        calculated = s.mean();
        if (Math.abs(calculated - expected)>tolerance)
            fail(name + "wrong mean value" + "\n" +
                    "calculated: " + calculated + "\n" +
                    "expected: " + expected);

        expected = 2.23333333333;
        calculated = s.variance();
        if (Math.abs(calculated - expected) > tolerance)
            fail(name + "wrong variance" + "\n" +
                    "calculated: " + calculated + "\n" +
                    "expected: " + expected);

        expected = 1.4944341181;
        calculated = s.standardDeviation();
        if (Math.abs(calculated-expected) > tolerance)
            fail(name + "wrong standard deviation" + "\n" +
                    "calculated: " + calculated + "\n" +
                    "expected: " + expected);

        expected = 0.359543071407;
        calculated = s.skewness();
        if (Math.abs(calculated-expected) > tolerance)
            fail(name + "wrong skewness" + "\n" +
                    "calculated: " + calculated + "\n" +
                    "expected: " + expected);

        expected = -0.151799637209;
        calculated = s.kurtosis();
        if (Math.abs(calculated-expected) > tolerance)
            fail(name + "wrong skewness" + "\n" +
                    "calculated: " + calculated + "\n" +
                    "expected: " + expected);
    }
    
    
    private void checkSequence(final GenericRiskStatistics stat, final String name, int dimension) {

        final GenericSequenceStatistics ss = new GenericSequenceStatistics(dimension);
        
        /*@Size*/ int i;
        for (i = 0; i<data.size(); i++) {
        	Array temp = new Array(dimension);
        	temp.fill(data.get(i));
            ss.add(temp, weights.get(i));
        }

        Array calculated;
        /*@Real*/ double expected, tolerance;

        if (ss.samples() != data.size())
            fail("SequenceStatistics<" + name + ">: "
                       + "wrong number of samples\n"
                       + "    calculated: " + ss.samples() + "\n"
                       + "    expected:   " + data.size());

        expected = weights.accumulate(0.0);
        if (ss.weightSum() != expected)
            fail("SequenceStatistics<" + name + ">: "
                       + "wrong sum of weights\n"
                       + "    calculated: " + ss.weightSum() + "\n"
                       + "    expected:   " + expected);

        expected = data.min();
        calculated = ss.min();
        for (i=0; i<dimension; i++) {
            if (calculated.get(i) != expected)
                fail("SequenceStatistics<" + name + ">: "
                           + (i+1) + " dimension: "
                           + "wrong minimum value\n"
                           + "    calculated: " + calculated.get(i) + "\n"
                           + "    expected:   " + expected);
        }

        expected = data.max();
        calculated = ss.max();
        for (i=0; i<dimension; i++) {
            if (calculated.get(i) != expected)
                fail("SequenceStatistics<" + name + ">: "
                           + (i+1) + " dimension: "
                           + "wrong maximun value\n"
                           + "    calculated: " + calculated.get(i) + "\n"
                           + "    expected:   " + expected);
        }

        expected = 4.3;
        tolerance = 1.0e-9;
        calculated = ss.mean();
        for (i=0; i<dimension; i++) {
            if (Math.abs(calculated.get(i)-expected) > tolerance)
                fail("SequenceStatistics<" + name + ">: "
                           + (i+1) + " dimension: "
                           + "wrong mean value\n"
                           + "    calculated: " + calculated.get(i) + "\n"
                           + "    expected:   " + expected);
        }

        expected = 2.23333333333;
        calculated = ss.variance();
        for (i=0; i<dimension; i++) {
            if (Math.abs(calculated.get(i)-expected) > tolerance)
                fail("SequenceStatistics<" + name + ">: "
                           + (i+1) + " dimension: "
                           + "wrong variance\n"
                           + "    calculated: " + calculated.get(i) + "\n"
                           + "    expected:   " + expected);
        }

        expected = 1.4944341181;
        calculated = ss.standardDeviation();
        for (i=0; i<dimension; i++) {
            if (Math.abs(calculated.get(i)-expected) > tolerance)
                fail("SequenceStatistics<" + name + ">: "
                           + (i+1) + " dimension: "
                           + "wrong standard deviation\n"
                           + "    calculated: " + calculated.get(i) + "\n"
                           + "    expected:   " + expected);
        }

        expected = 0.359543071407;
        calculated = ss.skewness();
        for (i=0; i<dimension; i++) {
            if (Math.abs(calculated.get(i)-expected) > tolerance)
                fail("SequenceStatistics<" + name + ">: "
                           + (i+1) + " dimension: "
                           + "wrong skewness\n"
                           + "    calculated: " + calculated.get(i) + "\n"
                           + "    expected:   " + expected);
        }

        expected = -0.151799637209;
        calculated = ss.kurtosis();
        for (i=0; i<dimension; i++) {
            if (Math.abs(calculated.get(i)-expected) > tolerance)
                fail("SequenceStatistics<" + name + ">: "
                           + (i+1) + " dimension: "
                           + "wrong kurtosis\n"
                           + "    calculated: " + calculated.get(i) + "\n"
                           + "    expected:   " + expected);
        }
    }
    

    
    private void checkConvergence(final GenericRiskStatistics stat, final String name) {

        final ConvergenceStatistics stats = new ConvergenceStatistics();

        stats.add(1.0);
        stats.add(2.0);
        stats.add(3.0);
        stats.add(4.0);
        stats.add(5.0);
        stats.add(6.0);
        stats.add(7.0);
        stats.add(8.0);

       	final /*@Size*/ int expectedSize1 = 3;
        /*@Size*/ int calculatedSize = stats.convergenceTable().size();
        if (calculatedSize != expectedSize1)
            fail("ConvergenceStatistics<" + name + ">: "
                       + "\nwrong convergence-table size"
                       + "\n    calculated: " + calculatedSize
                       + "\n    expected:   " + expectedSize1);
        

        final /*@Real*/ double tolerance = 1.0e-9;
        
        {
        	final /*@Real*/ double expectedValue1 = 4.0;
	        final List<Pair<Integer, Double>> table = stats.convergenceTable();
	        /*@Real*/ double calculatedValue = table.get(table.size()-1).second();
	        if (Math.abs(calculatedValue-expectedValue1) > tolerance)
	            fail("wrong last value in convergence table"
	                       + "\n    calculated: " + calculatedValue
	                       + "\n    expected:   " + expectedValue1);
        }

        {
        	final /*@Size*/ int expectedSampleSize1 = 7;
	        final List<Pair<Integer, Double>> table = stats.convergenceTable();
	        /*@Size*/ int calculatedSamples = table.get(table.size()-1).first();
	        if (calculatedSamples != expectedSampleSize1)
	            fail("wrong number of samples in convergence table"
	                       + "\n    calculated: " + calculatedSamples
	                       + "\n    expected:   " + expectedSampleSize1);
        }

        stats.reset();
        stats.add(1.0);
        stats.add(2.0);
        stats.add(3.0);
        stats.add(4.0);

        
       	final /*@Size*/ int expectedSize2 = 2;
        calculatedSize = stats.convergenceTable().size();
        if (calculatedSize != expectedSize2)
            fail("wrong convergence-table size"
                       + "\n    calculated: " + calculatedSize
                       + "\n    expected:   " + expectedSize2);
        

        {
        	final /*@Real*/ double expectedValue2 = 2.0;
	        final List<Pair<Integer, Double>> table = stats.convergenceTable();
	        /*@Real*/ double calculatedValue = table.get(table.size()-1).second();
	        if (Math.abs(calculatedValue-expectedValue2) > tolerance)
	            fail("wrong last value in convergence table"
	                       + "\n    calculated: " + calculatedValue
	                       + "\n    expected:   " + expectedValue2);
        }

        {
        	final /*@Size*/ int expectedSampleSize2 = 3;
	        final List<Pair<Integer, Double>> table = stats.convergenceTable();
	        /*@Size*/ int calculatedSamples = table.get(table.size()-1).first();
	        if (calculatedSamples != expectedSampleSize2)
	            fail("wrong number of samples in convergence table"
	                       + "\n    calculated: " + calculatedSamples
	                       + "\n    expected:   " + expectedSampleSize2);
        }
    }

}
