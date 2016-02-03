package org.jquantlib.model.marketmodels;

/**
 * 
 * @author Ueli Hofstetter
 *
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: code review :: please verify against QL/C++ code
public abstract class BrownianGenerator {
    
    public BrownianGenerator(){
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }
    
    public abstract double nextStep();
    public abstract double nextPath();
    public abstract int numberOfFactors();
    public abstract int numberOfSteps();

}
