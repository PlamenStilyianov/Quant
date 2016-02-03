package org.jquantlib.samples;

import org.junit.Ignore;
import org.junit.Test;

public class TestSamples {

    private final AllSamples samples;
    
    public TestSamples() {
        this.samples = new AllSamples();
    }
    
    @Test
    public void testCompleteSamples() {
        samples.runCompleteSamples();
    }
    
    @Test
    public void testIncompleteSamples() {
        samples.runIncompleteSamples();
    }
    
    @Ignore
    @Test
    public void testPendingSamples() {
        samples.runPendingSamples();
    }

}
