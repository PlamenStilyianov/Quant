/*
 Copyright (C) 2008 Richard Gomes

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
 Copyright (C) 2003, 2004, 2005, 2006 Ferdinando Ametrano
 Copyright (C) 2006 StatPro Italia srl

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
import org.jquantlib.instruments.GapPayoff;
import org.jquantlib.instruments.Option;
import org.jquantlib.instruments.Payoff;
import org.jquantlib.instruments.PlainVanillaPayoff;
import org.jquantlib.instruments.StrikedTypePayoff;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.util.PolymorphicVisitor;
import org.jquantlib.util.Visitor;

/**
 * Black 1976 calculator class
 *
 * @note <b>BUG:</b> When the variance is null, division by zero occur during
 *       the calculation of delta, delta forward, gamma, gamma forward, rho,
 *       dividend rho, vega, and strike sensitivity.
 *
 * @author Richard Gomes
 */
// FIXME When the variance is null, division by zero occur during calculations
// TODO: write test cases, including a situation when variance is zero
public class BlackCalculator {

    //
    // private final fields
    //

    private final /* @Real */ double strike;
    private final /* @Real */ double forward;
    private final /* @StdDev */ double stdDev;
    private final /* @DiscountFactor */ double discount;
    private final /* @Variance */ double variance;
    private final double dX_dS;
    private final double n_d1, cum_d1, n_d2, cum_d2;


    //
    // private fields
    //

    private double D1, D2;
    private double alpha, beta;
    private double dAlpha_dD1, dBeta_dD2;
    private double x;
    private double dx_dStrike;


    //
    // public constructors
    //

    public BlackCalculator(final StrikedTypePayoff payoff, final double forward, final double stdDev) {
        this(payoff, forward, stdDev, 1.0);
    }

    public BlackCalculator(final StrikedTypePayoff payoff, final double forward, final double stdDev, final double discount) {

        this.strike = payoff.strike();
        this.forward = forward;
        this.stdDev = stdDev;
        this.discount = discount;
        this.variance = stdDev * stdDev;

        QL.require(forward > 0.0 , "positive forward value required");
        QL.require(stdDev >= 0.0 , "non-negative standard deviation required");
        QL.require(discount > 0.0 , "positive discount required");

        if (stdDev >= Constants.QL_EPSILON) {
            if (strike == 0.0) {
                n_d1 = 0.0;
                n_d2 = 0.0;
                cum_d1 = 1.0;
                cum_d2 = 1.0;
            } else {
                D1 = Math.log(forward / strike) / stdDev + 0.5 * stdDev;
                D2 = D1 - stdDev;
                final CumulativeNormalDistribution f = new CumulativeNormalDistribution();
                cum_d1 = f.op(D1);
                cum_d2 = f.op(D2);
                n_d1 = f.derivative(D1);
                n_d2 = f.derivative(D2);
            }
        } else {
            if (forward > strike) {
                cum_d1 = 1.0;
                cum_d2 = 1.0;
            } else {
                cum_d1 = 0.0;
                cum_d2 = 0.0;
            }
            n_d1 = 0.0;
            n_d2 = 0.0;
        }

        x = strike;
        dx_dStrike = 1.0;

        // the following one will probably disappear as soon as
        // super-share will be properly handled
        dX_dS = 0.0;

        // this part is always executed.
        // in case of plain-vanilla payoffs, it is also the only part
        // which is executed.
        final Option.Type optionType = payoff.optionType();
        if (optionType == Option.Type.Call) {
            alpha = cum_d1;// N(d1)
            dAlpha_dD1 = n_d1;// n(d1)
            beta = -cum_d2;// -N(d2)
            dBeta_dD2 = -n_d2;// -n(d2)
        } else if (optionType == Option.Type.Put) {
            alpha = -1.0 + cum_d1;// -N(-d1)
            dAlpha_dD1 = n_d1;// n( d1)
            beta = 1.0 - cum_d2;// N(-d2)
            dBeta_dD2 = -n_d2;// -n( d2)
        } else
            throw new LibraryException("invalid option type"); // TODO: message

        // now dispatch on type.

        final Calculator calc = new Calculator(this);
        payoff.accept(calc);
    }


    //
    // public methods
    //

    public/* @Real */double value() /* @ReadOnly */{
        /* @Real */final double result = discount * (forward * alpha + x * beta);
        return result;
    }

    /**
     * Sensitivity to change in the underlying spot price.
     */
    public/* @Real */double delta(final double spot) /* @ReadOnly */{

        QL.require(spot > 0.0 , "positive spot value required");
        final double DforwardDs = forward / spot;
        final double temp = stdDev * spot;
        final double DalphaDs = dAlpha_dD1 / temp;
        final double DbetaDs = dBeta_dD2 / temp;
        final double temp2 = DalphaDs * forward + alpha * DforwardDs + DbetaDs * x + beta * dX_dS;

        return discount * temp2;
    }

    /**
     * Sensitivity to change in the underlying forward price.
     */
    public/* @Real */double deltaForward() /* @ReadOnly */{

        final double temp = stdDev * forward;
        final double DalphaDforward = dAlpha_dD1 / temp;
        final double DbetaDforward = dBeta_dD2 / temp;
        final double temp2 = DalphaDforward * forward + alpha + DbetaDforward * x;
        // DXDforward = 0.0; // commented in the source QuantLib

        return discount * temp2;
    }

    /**
     * Sensitivity in percent to a percent change in the underlying spot price.
     */
    public double elasticity(final double spot) /* @ReadOnly */{
        final double val = value();
        final double del = delta(spot);
        if (val > Constants.QL_EPSILON)
            return del / val * spot;
        else if (Math.abs(del) < Constants.QL_EPSILON)
            return 0.0;
        else if (del > 0.0)
            return Double.MAX_VALUE;
        else
            return Double.MIN_VALUE;
    }

    /**
     * Sensitivity in percent to a percent change in the underlying forward
     * price.
     */
    public double elasticityForward() /* @ReadOnly */{
        final double val = value();
        final double del = deltaForward();
        if (val > Constants.QL_EPSILON)
            return del / val * forward;
        else if (Math.abs(del) < Constants.QL_EPSILON)
            return 0.0;
        else if (del > 0.0)
            return Double.MAX_VALUE;
        else
            return Double.MIN_VALUE;
    }

    /**
     * Second order derivative with respect to change in the underlying spot
     * price.
     */
    public double gamma(final double spot) /* @ReadOnly */{

        QL.require(spot > 0.0 , "positive spot value required");
        final double DforwardDs = forward / spot;
        final double temp = stdDev * spot;
        final double DalphaDs = dAlpha_dD1 / temp;
        final double DbetaDs = dBeta_dD2 / temp;
        final double D2alphaDs2 = -DalphaDs / spot * (1 + D1 / stdDev);
        final double D2betaDs2 = -DbetaDs / spot * (1 + D2 / stdDev);
        final double temp2 = D2alphaDs2 * forward + 2.0 * DalphaDs * DforwardDs + D2betaDs2 * x + 2.0 * DbetaDs * dX_dS;

        return discount * temp2;
    }

    /**
     * Second order derivative with respect to change in the underlying forward
     * price.
     */
    public double gammaForward() /* @ReadOnly */{

        final double temp = stdDev * forward;
        final double DalphaDforward = dAlpha_dD1 / temp;
        final double DbetaDforward = dBeta_dD2 / temp;

        final double D2alphaDforward2 = -DalphaDforward / forward * (1 + D1 / stdDev);
        final double D2betaDforward2 = -DbetaDforward / forward * (1 + D2 / stdDev);

        final double temp2 = D2alphaDforward2 * forward + 2.0 * DalphaDforward + D2betaDforward2 * x;

        // DXDforward = 0.0; // commented in the source QuantLib

        return discount * temp2;
    }

    /**
     * Sensitivity to time to maturity.
     */
    public double theta(final double spot, final/* @Time */double maturity) /* @ReadOnly */{

        QL.require(maturity > 0.0 , "non negative maturity required");
        if (maturity == 0.0) return 0.0;

        // TODO: code review :: please verify against QL/C++ code

        // =====================================================================
        //
        // *** The following code is commented out in QuantLib ***
        //
        // vol          = stdDev_ / std::sqrt(maturity);
        // rate         = -std::log(discount_)/maturity;
        // dividendRate = -std::log(forward_ / spot * discount_)/maturity;
        // return rate*value() - (rate-dividendRate)*spot*delta(spot) - 0.5*vol*vol*spot*spot*gamma(spot);
        // =====================================================================

        return -(Math.log(discount) * value() + Math.log(forward / spot) * spot * delta(spot) + 0.5 * variance * spot * spot * gamma(spot)) / maturity;
    }

    /**
     * Sensitivity to time to maturity per day, assuming 365 day per year.
     */
    public double thetaPerDay(final double spot, /* @Time */final double maturity) /* @ReadOnly */{
        return theta(spot, maturity) / 365.0;
    }

    /**
     * Sensitivity to volatility.
     */
    public double vega(final/* @Time */double maturity) /* @ReadOnly */{
        QL.require(maturity >= 0.0 , "negative maturity not allowed");

        final double temp = Math.log(strike / forward) / variance;
        // actually DalphaDsigma / SQRT(T)
        final double DalphaDsigma = dAlpha_dD1 * (temp + 0.5);
        final double DbetaDsigma = dBeta_dD2 * (temp - 0.5);
        final double temp2 = DalphaDsigma * forward + DbetaDsigma * x;

        return discount * Math.sqrt(maturity) * temp2;
    }

    /**
     * Sensitivity to discounting rate.
     */
    public double rho(final/* @Time */double maturity) /* @ReadOnly */{
        QL.require(maturity >= 0.0 , "negative maturity not allowed");

        // actually DalphaDr / T
        final double DalphaDr = dAlpha_dD1 / stdDev;
        final double DbetaDr = dBeta_dD2 / stdDev;
        final double temp = DalphaDr * forward + alpha * forward + DbetaDr * x;

        return maturity * (discount * temp - value());
    }

    /**
     * Sensitivity to dividend/growth rate.
     */
    public double dividendRho(final/* @Time */double maturity) /* @ReadOnly */{
        QL.require(maturity >= 0.0 , "negative maturity not allowed");

        // actually DalphaDq / T
        final double DalphaDq = -dAlpha_dD1 / stdDev;
        final double DbetaDq = -dBeta_dD2 / stdDev;
        final double temp = DalphaDq * forward - alpha * forward + DbetaDq * x;

        return maturity * discount * temp;
    }

    /**
     * Probability of being in the money in the bond martingale measure, i.e.
     * N(d2).
     *
     * <p>
     * It is a risk-neutral probability, not the real world one.
     */
    public double itmCashProbability() /* @ReadOnly */{
        return cum_d2;
    }

    /**
     * Probability of being in the money in the asset martingale measure, i.e.
     * N(d1).
     *
     * <p>
     * It is a risk-neutral probability, not the real world one.
     */
    public double itmAssetProbability() /* @ReadOnly */{
        return cum_d1;
    }

    /**
     * Sensitivity to strike.
     */
    public double strikeSensitivity() /* @ReadOnly */{
        final double temp = stdDev * strike;
        final double DalphaDstrike = -dAlpha_dD1 / temp;
        final double DbetaDstrike = -dBeta_dD2 / temp;
        final double temp2 = DalphaDstrike * forward + DbetaDstrike * x + beta * dx_dStrike;

        return discount * temp2;
    }

    public double alpha() /* @ReadOnly */{
        return alpha;
    }

    public double beta() /* @ReadOnly */{
        return beta;
    }



    //
    // inner classes
    //

    private static class Calculator implements PolymorphicVisitor {

        // TODO: refactor messages?
        private static final String INVALID_OPTION_TYPE = "invalid option type";
        private static final String INVALID_PAYOFF_TYPE = "invalid payoff type";

        //
        // private fields
        //

        private final BlackCalculator black;


        //
        // public constructors
        //

        public Calculator(final BlackCalculator black) {
            this.black = black;
        }


        //
        // implements PolymorphicVisitor<Payoff>
        //

		@Override
        public <Payoff> Visitor<Payoff> visitor(final Class<? extends Payoff> klass) {
            if (klass==PlainVanillaPayoff.class)
                return (Visitor<Payoff>) plainVanillaPayoffVisitor;
            else if (klass==CashOrNothingPayoff.class)
                return (Visitor<Payoff>) cashOrNothingPayoffVisitor;
            else if (klass==AssetOrNothingPayoff.class)
                return (Visitor<Payoff>) assetOrNothingPayoffVisitor;
            else if (klass==GapPayoff.class)
                return (Visitor<Payoff>) gapPayoffVisitor;
            else
                throw new UnsupportedOperationException(INVALID_PAYOFF_TYPE + klass);
        }


        //
        // implements Visitor<PlainVanillaPayoff>
        //

        private final PlainVanillaPayoffVisitor plainVanillaPayoffVisitor = new PlainVanillaPayoffVisitor();

        private static final class PlainVanillaPayoffVisitor implements Visitor<Payoff> {

            @Override
            public final void visit(final Payoff o) {
                // nothing
            }
        }


        //
        // implements Visitor<CashOrNothingPayoff>
        //

        private final CashOrNothingPayoffVisitor cashOrNothingPayoffVisitor = new CashOrNothingPayoffVisitor();

        private final class CashOrNothingPayoffVisitor implements Visitor<Payoff> {

            @Override
            public final void visit(final Payoff o) {
                final CashOrNothingPayoff payoff = (CashOrNothingPayoff)o;
                black.alpha = black.dAlpha_dD1 = 0.0;
                black.x = payoff.getCashPayoff();
                black.dx_dStrike = 0.0;
                final Option.Type optionType = payoff.optionType();
                if (optionType == Option.Type.Call) {
                    black.beta = black.cum_d2;
                    black.dBeta_dD2 = black.n_d2;
                } else if (optionType == Option.Type.Put) {
                    black.beta = 1.0 - black.cum_d2;
                    black.dBeta_dD2 = -black.n_d2;
                } else
                    throw new IllegalArgumentException(INVALID_OPTION_TYPE);
            }
        }


        //
        // implements Visitor<AssetOrNothingPayoff>
        //

        private final AssetOrNothingPayoffVisitor assetOrNothingPayoffVisitor = new AssetOrNothingPayoffVisitor();

        private final class AssetOrNothingPayoffVisitor implements Visitor<Payoff> {

            @Override
            public void visit(final Payoff o) {
                final AssetOrNothingPayoff payoff = (AssetOrNothingPayoff)o;
                black.beta = black.dBeta_dD2 = 0.0;
                final Option.Type optionType = payoff.optionType();
                if (optionType == Option.Type.Call) {
                    black.alpha = black.cum_d1;
                    black.dAlpha_dD1 = black.n_d1;
                } else if (optionType == Option.Type.Put) {
                    black.alpha = 1.0 - black.cum_d1;
                    black.dAlpha_dD1 = -black.n_d1;
                } else
                    throw new IllegalArgumentException(INVALID_OPTION_TYPE);
            }
        }


        //
        // implements Visitor<GapPayoff>
        //

        private final GapPayoffVisitor gapPayoffVisitor = new GapPayoffVisitor();

        private final class GapPayoffVisitor implements Visitor<Payoff> {

            @Override
            public final void visit(final Payoff o) {
                final GapPayoff payoff = (GapPayoff)o;
                black.x = payoff.getSecondStrike();
                black.dx_dStrike = 0.0;
            }
        }

    }

}
