package org.jquantlib.model.marketmodels;

/**
 * 
 * @author Ueli Hofstetter
 *
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: code review :: please verify against QL/C++ code
public abstract class  BrownianGeneratorFactory {

    public BrownianGeneratorFactory() {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }

    public abstract BrownianGenerator create(int factors,int steps) ;

}
