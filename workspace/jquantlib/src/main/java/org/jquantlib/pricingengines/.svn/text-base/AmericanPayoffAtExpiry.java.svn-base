/*
 Copyright (C) 2009 Jose Coll

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
 Copyright (C) 2004 Ferdinando Ametrano

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

package org.jquantlib.pricingengines;

import org.jquantlib.QL;
import org.jquantlib.instruments.AssetOrNothingPayoff;
import org.jquantlib.instruments.CashOrNothingPayoff;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.instruments.Option.Type;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;

/**
 * Analytical formulae for american exercise with payoff at expiry
 *
 * @author Jose Coll
 */

//TODO: finish this class
//-- When your code is not complete, please add "TODO" so that it becomes easy to track what is needed to be done

public class AmericanPayoffAtExpiry {

    private final /*@DiscountFactor*/ double discount;
    private final /*@Real*/ double forward;
    private final /*@Volatility*/ double stdDev;
    private final /*@Real*/ double strike;
    private final double log_H_S;
    private final double cum_d1, cum_d2;
    private final double n_d1, n_d2;
    private final double alpha, beta;
    private final double DalphaDd1, DbetaDd2;
    private final boolean inTheMoney;
    private final double x, y;

    private double mu, K, D1, D2;
    private double DKDstrike, DXDstrike, DYDstrike;

    public AmericanPayoffAtExpiry(final double spot, final double discount, final double dividendDiscount, final double variance, final StrikedTypePayoff strikedTypePayoff) {
        super();
        QL.require(spot > 0.0 , "positive spot value required"); // TODO: message
        QL.require(discount > 0.0 , "positive discount required"); // TODO: message
        QL.require(dividendDiscount > 0.0 , "positive dividend discount required"); // TODO: message
        QL.require(variance >= 0.0 , "non-negative variance required"); // TODO: message

        this.discount = discount;
        this.forward = spot * dividendDiscount / discount;
        this.stdDev = Math.sqrt(variance);

        final Option.Type optionType = strikedTypePayoff.optionType();
        strike = strikedTypePayoff.strike();
        mu = Math.log(dividendDiscount / discount) / variance - 0.5;

        // binary cash-or-nothing payoff ?
        if (strikedTypePayoff instanceof CashOrNothingPayoff) {
            final CashOrNothingPayoff coo = (CashOrNothingPayoff) strikedTypePayoff;
            K = coo.getCashPayoff();
            DKDstrike = 0.0;
        }
        // binary asset-or-nothing payoff ?
        else if (strikedTypePayoff instanceof AssetOrNothingPayoff) {
            K = forward;
            DKDstrike = 0.0;
            mu += 1.0;
        }

        log_H_S = Math.log(strike / spot);

        if (variance >= Math.E) {
            D1 = log_H_S / stdDev + mu * stdDev;
            D2 = D1 - 2.0 * mu * stdDev;
            final CumulativeNormalDistribution f = new CumulativeNormalDistribution();
            cum_d1 = f.op(D1);
            cum_d2 = f.op(D2);
            n_d1 = f.derivative(D1);
            n_d2 = f.derivative(D2);
        } else {
            if (log_H_S > 0) {
                cum_d1 = 1.0;
                cum_d2 = 1.0;
            }
            else {
                cum_d1 = 0.0;
                cum_d2 = 0.0;
            }
            n_d1 = 0.0;
            n_d2 = 0.0;
        }

        // up-and-in cash-(at-hit)-or-nothing option
        // a.k.a. american call with cash-or-nothing payoff
        if (optionType.equals(Type.Call)) {
            if (strike > spot) {
                alpha     = 1.0-cum_d2;//  N(-d2)
                DalphaDd1 =    -  n_d2; // -n( d2)
                beta      = 1.0-cum_d1;//  N(-d1)
                DbetaDd2  =    -  n_d1; // -n( d1)
            } else {
                alpha     = 0.5;
                DalphaDd1 = 0.0;
                beta      = 0.5;
                DbetaDd2  = 0.0;
            }
        }
        // down-and-in cash-(at-hit)-or-nothing option
        // a.k.a. american put with cash-or-nothing payoff
        else if (optionType.equals(Type.Put)) {
            if (strike < spot) {
                alpha     =     cum_d2;//  N(d2)
                DalphaDd1 =       n_d2; //  n(d2)
                beta      =     cum_d1;//  N(d1)
                DbetaDd2  =       n_d1; //  n(d1)
            } else {
                alpha     = 0.5;
                DalphaDd1 = 0.0;
                beta      = 0.5;
                DbetaDd2  = 0.0;
            }
        } else
            throw new IllegalArgumentException("invalid option type");

        inTheMoney = (optionType.equals(Type.Call) && strike < spot) ||
        (optionType.equals(Type.Put) && strike > spot);
        if (inTheMoney) {
            y         = 1.0;
            x         = 1.0;
            DYDstrike = 0.0;
            DXDstrike = 0.0;
        } else {
            y = 1.0;
            x = Math.pow(strike / spot, 2.0 * mu);
            // Commented out in original C++ code :: DXDstrike_ = ......;
        }

    }

    public /* @Real */ double value() /* @ReadOnly */ {
        /* @Real */ final double result = discount * K * (y * alpha + x * beta);
        return result;
    }

}
