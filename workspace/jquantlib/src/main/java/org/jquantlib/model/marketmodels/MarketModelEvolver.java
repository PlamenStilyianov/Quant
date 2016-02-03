package org.jquantlib.model.marketmodels;

/**
 * Market-model evolver
 * <p>
 * Abstract base class. The evolver does the actual gritty work of evolving the forward rates from one time to the next.
 *
 * @author Ueli Hofstetter
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: code review :: please verify against QL/C++ code
public abstract class MarketModelEvolver {

    abstract int [] numeraires();
    abstract double startNewPath();
    abstract double advanceStep();
    abstract int currentStep();
    abstract CurveState currentState();
    abstract void setInitialState(CurveState curveState);

}
