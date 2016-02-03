/*
Copyright (C) 2009 Praneet Tiwari

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


package org.jquantlib.model.shortrate.onefactormodels;

import org.jquantlib.QL;
import org.jquantlib.instruments.Option;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.math.distributions.NonCentralChiSquaredDistribution;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.methods.lattices.Lattice;
import org.jquantlib.methods.lattices.TrinomialTree;
import org.jquantlib.model.Parameter;
import org.jquantlib.model.TermStructureFittingParameter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.TimeGrid;

/**
 * Extended Cox-Ingersoll-Ross model class.
 * <p>
 * This class implements the extended Cox-Ingersoll-Ross model defined by
 * <p>{@latex[ dr_t = (\theta(t) - \alpha r_t)dt + \sqrt{r_t}\sigma dW_t }
 *
 * @bug this class was not tested enough to guarantee its functionality.
 *
 * @category shortrate
 *
 * @author Praneet Tiwari
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class ExtendedCoxIngersollRoss extends CoxIngersollRoss {

    private static final String STRIKE_MUST_BE_POSITIVE = "strike must be positive";


    private final TermStructureConsistentModelClass termstructureConsistentModel;
    private Parameter phi_;

    public ExtendedCoxIngersollRoss(final Handle<YieldTermStructure> termStructure,
            final double theta, final double k, final double sigma, final double x0){
        super(x0, theta, k, sigma);
        termstructureConsistentModel = new TermStructureConsistentModelClass(termStructure);
        generateArguments();
    }

    @Override
    public OneFactorModel.ShortRateDynamics dynamics(){
        return new Dynamics(phi_, theta(), k(), sigma(), x0());
    }

    @Override
    public void generateArguments(){
        phi_ = new FittingParameter(termstructureConsistentModel.termStructure(), theta(), k(), sigma(), x0());
    }

    @Override
    public double A(final double t, final double s)  {
        final double pt = termstructureConsistentModel.termStructure().currentLink().discount(t);
        final double ps = termstructureConsistentModel.termStructure().currentLink().discount(s);
        final double value = super.A(t,s)*Math.exp(B(t,s)*phi_.get(t))*
        (ps*super.A(0.0,t)*Math.exp(-B(0.0,t)*x0()))/
        (pt*super.A(0.0,s)*Math.exp(-B(0.0,s)*x0()));
        return value;
    }

    @Override
    public double discountBondOption(
            final Option.Type type,
            final double strike,
            final double t,
            final double s) {
        QL.require(strike > 0.0 , STRIKE_MUST_BE_POSITIVE); // TODO: message
        final double discountT = termstructureConsistentModel.termStructure().currentLink().discount(t);
        final double discountS = termstructureConsistentModel.termStructure().currentLink().discount(s);
        if (t<Constants.QL_EPSILON)
            switch (type) {
                case Call:
                    return Math.max(discountS - strike, 0);
                case Put:
                    return Math.max(strike - discountS, 0);
                default:
                    throw new LibraryException(Option.Type.UNKNOWN_OPTION_TYPE);
            }
        final double sigma2 = sigma() * sigma();
        final double h = Math.sqrt(k() * k() + 2 * sigma2);
        final double r0 = termstructureConsistentModel.termStructure().currentLink().forwardRate(0.0, 0.0, Compounding.Continuous,
                Frequency.NoFrequency).rate();
        final double b = B(t, s);

        final double rho = 2.0 * h / (sigma2 * (Math.exp(h * t) - 1.0));
        final double psi = (k() + h) / sigma2;

        final double df = 4.0 * k() * theta() / sigma2;
        final double ncps = 2.0 * rho * rho * (r0 - phi_.get(0.0)) * Math.exp(h * t) / (rho + psi + b);
        final double ncpt = 2.0 * rho * rho * (r0 - phi_.get(0.0)) * Math.exp(h * t) / (rho + psi);

        final NonCentralChiSquaredDistribution chis = new NonCentralChiSquaredDistribution(df, ncps);
        final NonCentralChiSquaredDistribution chit = new NonCentralChiSquaredDistribution(df, ncpt);

        final double z = Math.log(super.A(t, s) / strike) / b;
        final double call = discountS * chis.op(2.0 * z * (rho + psi + b)) - strike * discountT
        * chit.op(2.0 * z * (rho + psi));
        if (type.equals(Option.Type.Call))
            return call;
        else
            return call - discountS + strike * discountT;

    }

    @Override
    public Lattice tree(final TimeGrid grid){
        final TermStructureFittingParameter phi = new TermStructureFittingParameter(termstructureConsistentModel.termStructure());
        final Dynamics numericDynamics =  new Dynamics(phi, theta(), k(), sigma(), x0());
        final TrinomialTree trinominal = new TrinomialTree(numericDynamics.process(), grid, true);
        final TermStructureFittingParameter.NumericalImpl impl = (TermStructureFittingParameter.NumericalImpl)phi.implementation();
        return new OneFactorModel.ShortRateTree(trinominal, numericDynamics, impl, grid);
    }


    //
    // private inner classes
    //

    /**
     * Short-rate dynamics in the extended Cox-Ingersoll-Ross model
     * <p>
     * The short-rate is here
     * <p>{@latex[ r_t = \varphi(t) + y_t^2 }
     * where \f$ \varphi(t) \f$ is the deterministic time-dependent
     * parameter used for term-structure fitting and {@latex$ y_t } is the
     * state variable, the square-root of a standard CIR process.
     *
     * @author Praneet Tiwari
     */
    private class Dynamics extends CoxIngersollRoss.Dynamics{

        private final Parameter phi;

        public Dynamics(final Parameter phi,
                final double theta,
                final double k,
                final double sigma,
                final double x0){
            super(theta, k, sigma, x0);
            this.phi = phi;
        }

        @Override
        public double variable(final double t, final double r){
            return Math.sqrt(r - phi.get(t));
        }

        @Override
        public double shortRate(final double t, final double y){
            return y*y + phi.get(t);
        }
    }

    /**
     * Analytical term-structure fitting parameter {@latex$ \varphi(t) }.
     * <p>
     * {@latex$ \varphi(t) } is analytically defined by
     * <p>{@latex[ \varphi(t) = f(t) -
     *                    \frac{2k\theta(e^{th}-1)}{2h+(k+h)(e^{th}-1)} -
     *                    \frac{4 x_0 h^2 e^{th}}{(2h+(k+h)(e^{th}-1))^1} }
     * where {@latex$ f(t) } is the instantaneous forward rate at {@latex$ t }
     * and {@latex$ h = \sqrt{k^2 + 2\sigma^2} }.
     *
     * @author Praneet Tiwari
     */
    private static class FittingParameter extends TermStructureFittingParameter {

        public FittingParameter(
                final Handle<YieldTermStructure> termStructure,
                final double theta,
                final double k,
                final double sigma,
                final double x0) {
            super(new Impl(termStructure, theta, k, sigma, x0));
        }

        public FittingParameter(final Handle<YieldTermStructure> term) {
            super(term);
        }

        //
        // private inner classes
        //

        private static class Impl implements Parameter.Impl {
            private final Handle<YieldTermStructure> termStructure;
            private final double theta;
            private final double k;
            private final double sigma;
            private final double x0;

            public Impl(
                    final Handle<YieldTermStructure> termStructure,
                    final double theta,
                    final double k,
                    final double sigma,
                    final double x0) {
                this.termStructure = termStructure;
                this.theta = theta;
                this.k = k;
                this.sigma = sigma;
                this.x0 = x0;
            }

            @Override
            public double value(final Array params, final double t) {
                final double forwardRate = termStructure.currentLink().forwardRate(
                        t, t, Compounding.Continuous, Frequency.NoFrequency).rate();
                final double h = Math.sqrt(k*k + 2.0 * sigma * sigma);
                final double expth = Math.exp(t*h);
                final double temp = 2.0*h + (k+h)*(expth - 1.0);
                final double phi = forwardRate - 2.0*k*theta*(expth - 1.0)/temp - x0*4.0*h*h*expth/(temp*temp);
                return phi;
            }

        }
    }

}
