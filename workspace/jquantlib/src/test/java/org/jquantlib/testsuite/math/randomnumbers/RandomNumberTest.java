package org.jquantlib.testsuite.math.randomnumbers;

import org.jquantlib.QL;
import org.junit.Test;

// TODO: code review :: please verify against QL/C++ code
public class RandomNumberTest {

    public RandomNumberTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    @Test
    public void testGaussian(){
        QL.info("Testing Gaussian pseudo-random number generation...");
        QL.info("to be implemented");

    }

    @Test
    public void testDefaultPoisson(){
        QL.info("Testing Poisson pseudo-random number generation...");
        QL.info("to be implemented");
    }

    @Test
    public void testCustomPoisson(){
        QL.info("Testing custom Poisson pseudo-random number generation...");
        QL.info("to be implemented");
    }

}
