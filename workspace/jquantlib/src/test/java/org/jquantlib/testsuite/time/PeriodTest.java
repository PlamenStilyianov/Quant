package org.jquantlib.testsuite.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.jquantlib.QL;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.junit.Test;


public class PeriodTest {
	
	   @Test
	    public void testEqualsandHashCode() {

	        QL.info("Testing equals and hashcode");
	        
	        final Period oneDay = new Period(1, TimeUnit.Days);
	        final Period oneYear = new Period(1, TimeUnit.Years);
	        final Period oneDayAgo = new Period(-1, TimeUnit.Days);
	        final Period oneDayAgain = new Period(1, TimeUnit.Days);
	        final Period oneNull = new Period(1, null);
	        final Period oneNullAgain = new Period(1, null);
	        final Period twoNull = new Period(1, null);
	        
	        assertFalse(oneDay.equals(twoNull));
	        assertEquals(oneNull, oneNullAgain);        
	        
	        assertFalse(oneDay.equals(null));
	        assertFalse(oneDay.equals(oneNull));
	        assertEquals(oneDay, oneDay);
	        assertFalse(oneDay.equals(oneDayAgo));
	        assertFalse(oneDay.equals(oneYear));   
	        assertEquals(oneDay, oneDayAgain);
	        
	        HashSet<Period> testSet = new HashSet<Period>();
	        testSet.add(oneDay);
	        testSet.add(oneDayAgo);
	               
	        assertTrue(testSet.contains(oneDay));
	        assertFalse(testSet.contains(oneYear));      

	    }
}
