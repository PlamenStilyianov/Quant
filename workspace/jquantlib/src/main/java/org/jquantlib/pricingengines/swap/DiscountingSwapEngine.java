package org.jquantlib.pricingengines.swap;

import org.jquantlib.QL;
import org.jquantlib.cashflow.CashFlows;
import org.jquantlib.instruments.Swap;
import org.jquantlib.math.Constants;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.util.Observer;

// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class DiscountingSwapEngine extends Swap.EngineImpl implements /* Swap.Engine, */ Observer {

    private final Handle<YieldTermStructure> discountCurve;

    public DiscountingSwapEngine(final Handle<YieldTermStructure> discountCurve) /* @ReadOnly */ {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.discountCurve = discountCurve;
        this.discountCurve.addObserver(this);
    }

    @Override
    public void calculate() /* @ReadOnly */ {
        QL.require(!discountCurve.empty() , "no discounting term structure set"); // TODO: message

        final Swap.ArgumentsImpl a = (Swap.ArgumentsImpl)arguments_;
        final Swap.ResultsImpl   r = (Swap.ResultsImpl)results_;
        r.value = 0.0;
        r.errorEstimate = Constants.NULL_REAL;
        r.legNPV = new double[a.legs.size()];
        r.legBPS = new double[a.legs.size()];
        for (int i=0; i<a.legs.size(); ++i) {
            r.legNPV[i] = a.payer[i] * CashFlows.getInstance().npv(a.legs.get(i), discountCurve);
            r.legBPS[i] = a.payer[i] * CashFlows.getInstance().bps(a.legs.get(i), discountCurve);
            r.value += r.legNPV[i];
        }
    }

}
