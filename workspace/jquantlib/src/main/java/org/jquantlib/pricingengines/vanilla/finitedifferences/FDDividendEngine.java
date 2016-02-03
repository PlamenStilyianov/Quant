package org.jquantlib.pricingengines.vanilla.finitedifferences;

import org.jquantlib.processes.GeneralizedBlackScholesProcess;

/**
 * This class mimics
 * <pre>
 * typedef FDDividendEngineMerton73 FDDividendEngine;
 * </pre>
 * 
 * @author Richard Gomes
 */
public final class FDDividendEngine extends FDDividendEngineMerton73 {

    public FDDividendEngine(
            final GeneralizedBlackScholesProcess process,
            final int timeSteps,
            final int gridPoints,
            final boolean timeDependent) {
        super(process, timeSteps, gridPoints, timeDependent);
    }

}
