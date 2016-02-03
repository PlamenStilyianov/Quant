/*
Copyright (C) 2008 Praneet Tiwari

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
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2006 Ferdinando Ametrano
 Copyright (C) 2007, 2008 StatPro Italia srl

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

package org.jquantlib.instruments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.cashflow.CashFlow;
import org.jquantlib.cashflow.CashFlows;
import org.jquantlib.cashflow.Leg;
import org.jquantlib.math.Constants;
import org.jquantlib.pricingengines.GenericEngine;
import org.jquantlib.pricingengines.PricingEngine;
import org.jquantlib.time.Date;

/**
 * Interest rate swap
 * <p>
 * The cash flows belonging to the first leg are paid; the ones belonging to the second leg are received.
 *
 * @category instruments
 *
 * @author Praneet Tiwari
 */
public class Swap extends Instrument {

    protected List<Leg> legs;
    protected double[] payer;
    protected double[] legNPV;
    protected double[] legBPS;


    //
    // public constructors
    //

    public Swap(final Leg firstLeg, final Leg secondLeg) {

        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }

        this.legs = new ArrayList<Leg>();
        this.payer = new double[2];
        this.legNPV = new double[2];
        this.legBPS = new double[2];
        legs.add(firstLeg);
        legs.add(secondLeg);
        payer[0] = -1.0;
        payer[1] = +1.0;

        for (int i = 0; i < legs.size(); i++) {
            for (final CashFlow item : legs.get(i)) {
                item.addObserver(this);
            }
        }
    }

    public Swap(final List<Leg> legs, final boolean[] payer) {

        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }

        this.legs = legs;
        this.payer = new double[legs.size()];
        Arrays.fill(this.payer, 1.0);
        this.legNPV = new double[legs.size()];
        this.legBPS = new double[legs.size()];

        for (int j = 0; j < this.legs.size(); j++) {
            if (payer[j]) {
                this.payer[j] = -1.0;
            }
            for (int i = 0; i < legs.size(); i++) {
                for (final CashFlow item : legs.get(i)) {
                    item.addObserver(this);
                }
            }
        }
    }


    //
    // protected constructors
    //

    protected Swap(final int legs) {
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }

        this.legs   = new ArrayList<Leg>();
        this.payer  = new double[legs];
        this.legNPV = new double[legs];
        this.legBPS = new double[legs];
    }


    //
    // public methods
    //

    public Date startDate() /* @ReadOnly */ {
        QL.require(legs.size() > 0 , "no legs given"); // TODO: message
        Date d = CashFlows.getInstance().startDate(this.legs.get(0));
        for (int j = 1; j < this.legs.size(); j++) {
            d = Date.min(d, CashFlows.getInstance().startDate(this.legs.get(j)));
        }
        return d;
    }

    public Date maturityDate() /* @ReadOnly */ {
        QL.require(legs.size() > 0 , "no legs given"); // TODO: message
        Date d = CashFlows.getInstance().maturityDate(this.legs.get(0));
        for (int j = 1; j < this.legs.size(); j++) {
            d = Date.max(d, CashFlows.getInstance().maturityDate(this.legs.get(j)));
        }
        return d;
    }


    //
    // overrides Instrument
    //

    @Override
    public boolean isExpired() /* @ReadOnly */ {
        final Date today = new Settings().evaluationDate();
        for (int i = 0; i < legs.size(); i++) {
            for (final CashFlow item : legs.get(i)) {
                if (!item.hasOccurred(today)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void setupExpired() /* @ReadOnly */ {
        super.setupExpired();
        Arrays.fill(legBPS, 0.0);
        Arrays.fill(legNPV, 0.0);
    }

    @Override
    public void setupArguments(final PricingEngine.Arguments arguments) /* @ReadOnly */ {
        final Swap.ArgumentsImpl a = (Swap.ArgumentsImpl)arguments;
        a.legs = legs;
        a.payer = payer;
    }

    @Override
    public void fetchResults(final PricingEngine.Results results) /* @ReadOnly */ {
        super.fetchResults(results);

        final Swap.ResultsImpl r = (Swap.ResultsImpl)results;
        if (r.legNPV.length > 0) {
            QL.require(r.legNPV.length == legNPV.length , "wrong number of leg NPV returned"); // TODO: message
            legNPV = r.legNPV;
        } else {
            Arrays.fill(legNPV, Constants.NULL_REAL);
        }

        if (r.legBPS.length > 0) {
            QL.require(r.legBPS.length == legBPS.length , "wrong number of leg BPS returned"); // TODO: message
            legBPS = r.legBPS;
        } else {
            Arrays.fill(legBPS, Constants.NULL_REAL);
        }
    }


    //
    // public inner interfaces
    //

    /**
     * Basic swap arguments
     *
     * @author Richard Gomes
     */
    public interface Arguments extends Instrument.Arguments { /* marking interface */ }


    /**
     * Basic swap results
     *
     * @author Richard Gomes
     */
    public interface Results extends Instrument.Results { /* marking interface */ }


    //
    // public inner classes
    //

    static public class ArgumentsImpl implements Swap.Arguments {
        public List<Leg> legs;
        public double[] payer;

        @Override
        public void validate() /* @ReadOnly */ {
            QL.require(legs.size() == payer.length , "number of legs and multipliers differ"); // TODO: message
        }
    }


    static public class ResultsImpl extends Instrument.ResultsImpl implements Swap.Results {

        public double[] legNPV;
        public double[] legBPS;

        @Override
        public void reset() {
            super.reset();
            if (legNPV != null) {
                Arrays.fill(legNPV, 0.0);
            }
            if (legBPS != null) {
                Arrays.fill(legBPS, 0.0);
            }
        }
    }

    static public class EngineImpl extends GenericEngine<Swap.Arguments, Swap.Results> {

        protected EngineImpl() {
            super(new ArgumentsImpl(), new ResultsImpl());
        }

        @Override
        public void calculate() /* @ReadOnly */ {
            // nothing
        }
    }

}
