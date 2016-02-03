/*
Copyright (C) 2008 Praneet Tiwari
Copyright (C) 2009 Ueli Hofstetter
Copyright (C) 2009 Richard Gomes

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
package org.jquantlib.model.shortrate.twofactormodels;

import static org.jquantlib.pricingengines.BlackFormula.blackFormula;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.instruments.Option;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.solvers1D.Brent;
import org.jquantlib.model.AffineModel;
import org.jquantlib.model.Parameter;
import org.jquantlib.model.TermStructureFittingParameter;
import org.jquantlib.model.shortrate.onefactormodels.TermStructureConsistentModel;
import org.jquantlib.model.shortrate.onefactormodels.TermStructureConsistentModelClass;
import org.jquantlib.processes.OrnsteinUhlenbeckProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Frequency;

/**
 * Two-additive-factor gaussian model class.
 * <p>
 * This class implements a two-additive-factor model defined by {@latex$ dr_t = \varphi(t) + x_t + y_t }
 * where {@latex$ x_t } and {@latex$ y_t } are defined by
 * {@latex[ dx_t = -a x_t dt + \sigma dW^1_t, x_0 = 0 }
 * {@latex[ dy_t = -b y_t dt + \sigma dW^2_t, y_0 = 0 } and {@latex$ dW^1_t dW^2_t = \rho dt }
 *
 * @note This class was not tested enough to guarantee its functionality.
 *
 * @category shortrate
 *
 * @author Praneet Tiwari
 * @author Richard Gomes
 */
@QualityAssurance(quality=Quality.Q0_UNFINISHED, version=Version.V097, reviewers="Richard Gomes")
public class G2 extends TwoFactorModel implements AffineModel, TermStructureConsistentModel {

    private static final String g2_model_needs_two_factors = "g2 model needs two factors to compute discount bond";

    // need permanent solution for this one
    private final TermStructureConsistentModelClass termStructureConsistentModelClass;

    private final Parameter a_;
    private final Parameter sigma_;
    private final Parameter b_;
    private final Parameter eta_;
    private final Parameter rho_;
    private Parameter phi_;

    public G2(final Handle<YieldTermStructure> termStructure) {
        this(termStructure, 0.1, 0.01, 0.1, 0.01, -0.75);
    }

    public G2(
            final Handle<YieldTermStructure> termStructure,
            final double /* @Real */ a) {
        this(termStructure, a, 0.01, 0.1, 0.01, -0.75);
    }

    public G2(
            final Handle<YieldTermStructure> termStructure,
            final double /* @Real */ a,
            final double /* @Real */ sigma) {
        this(termStructure, a, sigma, 0.1, 0.01, -0.75);
    }

    public G2(
            final Handle<YieldTermStructure> termStructure,
            final double /* @Real */ a,
            final double /* @Real */ sigma,
            final double /* @Real */ b) {
        this(termStructure, a, sigma, b, 0.01, -0.75);
    }

    public G2(
            final Handle<YieldTermStructure> termStructure,
            final double /* @Real */ a,
            final double /* @Real */ sigma,
            final double /* @Real */ b,
            final double /* @Real */ eta) {
        this(termStructure, a, sigma, b, eta, -0.75);
    }

    public G2(
            final Handle<YieldTermStructure> termStructure,
            final double /* @Real */ a,
            final double /* @Real */ sigma,
            final double /* @Real */ b,
            final double /* @Real */ eta,
            final double /* @Real */ rho) {
        super(5);

        //TODO: Code review :: incomplete code
        if (true)
            throw new UnsupportedOperationException("Work in progress");

        termStructureConsistentModelClass = new TermStructureConsistentModelClass(termStructure);
        a_ = (arguments_.get(0) /* [0] */);
        sigma_ = (arguments_.get(1) /* [1] */);
        b_ = (arguments_.get(2) /* []2] */);

        eta_ = (arguments_.get(3) /* []3] */);
        rho_ = (arguments_.get(4) /* []4] */);

        generateArguments();

        // TODO: code review :: please verify against QL/C++ code
        // seems like we should have this.termStructure

        termStructure.addObserver(this);
        //XXX:registerWith
        //registerWith(termStructure);
    }

    public double discountBond(final double t, final double T, final double x, final double y) {
        return A(t, T) * Math.exp(-B(a(), (T - t)) * x - B(b(), (T - t)) * y);
    }

    @Override
    public double /* @Real */discountBondOption(final Option.Type type, final double /* @Real */strike, final double /* @Time */maturity,
            final double /* @Time */bondMaturity) {

        final double /* @Real */v = sigmaP(maturity, bondMaturity);
        final double /* @Real */f = termStructureConsistentModelClass.termStructure().currentLink().discount(bondMaturity);
        final double /* @Real */k = termStructureConsistentModelClass.termStructure().currentLink().discount(maturity) * strike;

        return blackFormula(type, k, f, v);
    }

    @Override
    public TwoFactorModel.ShortRateDynamics dynamics() {
        return (new Dynamics(phi_, a(), sigma(), b(), eta(), rho()));
    }

    public double swaption(final Object object, final double range, final int intervals) {
        throw new UnsupportedOperationException("work in progress");
        /*
         * Real G2::swaption(const Swaption::arguments& arguments, Real range, Size intervals) const {
         *
         * Time start = arguments.floatingResetTimes[0]; Real w = (arguments.type==VanillaSwap::Payer ? 1 : -1 );
         * SwaptionPricingFunction function(a(), sigma(), b(), eta(), rho(), w, start, arguments.fixedPayTimes, arguments.fixedRate,
         * (*this));
         *
         * Real upper = function.mux() + range*function.sigmax(); Real lower = function.mux() - range*function.sigmax();
         * SegmentIntegral integrator(intervals); return arguments.nominal*w*termStructure()->discount(start)* integrator(function,
         * lower, upper); }
         */
    }

    @Override
    public double discount(/* @Time */final double t) {
        return termStructure().currentLink().discount(t);
    }

    @Override
    public void generateArguments() {
        phi_ = new FittingParameter(termStructureConsistentModelClass.termStructure(), a(), sigma(), b(), eta(), rho());
    }

    protected double /* @Real */A(final double /* @Time */t, final double /* @Time */T) {
        return termStructureConsistentModelClass.termStructure().currentLink().discount(T)
        / termStructureConsistentModelClass.termStructure().currentLink().discount(t)
        * Math.exp(0.5 * (V(T - t) - V(T) + V(t)));
    }

    protected double /* @Real */B(final double /* @Real */x, final double /* @Time */t) {
        return (1.0 - Math.exp(-x * t)) / x;
    }

    private double /* @Real */V(final double /* @Time */t) {
        final double /* @Real */expat = Math.exp(-a() * t);
        final double /* @Real */expbt = Math.exp(-b() * t);
        final double /* @Real */cx = sigma() / a();
        final double /* @Real */cy = eta() / b();
        final double /* @Real */valuex = cx * cx * (t + (2.0 * expat - 0.5 * expat * expat - 1.5) / a());
        final double /* @Real */valuey = cy * cy * (t + (2.0 * expbt - 0.5 * expbt * expbt - 1.5) / b());
        final double /* @Real */value = 2.0 * rho() * cx * cy
        * (t + (expat - 1.0) / a() + (expbt - 1.0) / b() - (expat * expbt - 1.0) / (a() + b()));
        return valuex + valuey + value;
    }

    private double /* @Real */a() {
        return a_.get(0.0);
    }

    private double /* @Real */sigma() {
        return sigma_.get(0.0);
    }

    private double /* @Real */b() {
        return b_.get(0.0);
    }

    private double /* @Real */eta() {
        return eta_.get(0.0);
    }

    private double /* @Real */rho() {
        return rho_.get(0.0);
    }

    private double /* @Real */sigmaP(final double /* @Time */t, final double /* @Time */s) {
        final double /* @Real */temp = 1.0 - Math.exp(-(a() + b()) * t);
        final double /* @Real */temp1 = 1.0 - Math.exp(-a() * (s - t));
        final double /* @Real */temp2 = 1.0 - Math.exp(-b() * (s - t));
        final double /* @Real */a3 = a() * a() * a();
        final double /* @Real */b3 = b() * b() * b();
        final double /* @Real */sigma2 = sigma() * sigma();
        final double /* @Real */eta2 = eta() * eta();
        final double /* @Real */value = 0.5 * sigma2 * temp1 * temp1 * (1.0 - Math.exp(-2.0 * a() * t)) / a3 + 0.5 * eta2 * temp2 * temp2
        * (1.0 - Math.exp(-2.0 * b() * t)) / b3 + 2.0 * rho() * sigma() * eta() / (a() * b() * (a() + b())) * temp1 * temp2
        * temp;
        return Math.sqrt(value);
    }


    @Override
    public Handle<YieldTermStructure> termStructure() {
        return termStructureConsistentModelClass.termStructure();
    }

    @Override
    public double discountBond(final double now, final double maturity, final Array factors) {
        throw new UnsupportedOperationException(
        "not sure whether this is a quantlib error - looks like they forgot to overwrite a virtual method");
    }


    //
    // private inner classes
    //

    private class Dynamics extends TwoFactorModel.ShortRateDynamics {
        public Dynamics(final Parameter fitting, final double /* @Real */a, final double /* @Real */sigma, final double /* @Real */b,
                final double /* @Real */eta, final double /* @Real */rho) {
            super(new OrnsteinUhlenbeckProcess(a, sigma, 0.0, 0.0), new OrnsteinUhlenbeckProcess(b, eta, 0.0, 0.0), rho);
            fitting_ = (fitting);
        }

        @Override
        public double /* @Rate */shortRate(final double /* @Time */t, final double /* @Real */x, final double /* @Real */y) {
            return fitting_.get(t) + x + y;
        }

        private final Parameter fitting_;
    } // dynamics



    //TODO: code review
    private class SwaptionPricingFunction {

        public SwaptionPricingFunction(final double /* @Real */a, final double /* @Real */sigma, final double /* @Real */b, final double /* @Real */eta,
                final double /* @Real */rho, final double /* @Real */w, final double /* @Real */start,
                final/* std::vector<Time>& */ArrayList<Double /* @Time */> payTimes, final double /* @Rate */fixedRate, final G2 model) {
            a_ = (a);
            sigma_ = (sigma);
            b_ = (b);
            eta_ = (eta);
            rho_ = (rho);
            w_ = (w);

            T_ = (start);
            t_ = (payTimes);
            rate_ = (fixedRate);
            size_ = (t_.size());

            A_ = new Array(size_);
            Ba_ = new Array(size_);
            Bb_ = new Array(size_);

            sigmax_ = sigma_ * Math.sqrt(0.5 * (1.0 - Math.exp(-2.0 * a_ * T_)) / a_);
            sigmay_ = eta_ * Math.sqrt(0.5 * (1.0 - Math.exp(-2.0 * b_ * T_)) / b_);
            rhoxy_ = rho_ * eta_ * sigma_ * (1.0 - Math.exp(-(a_ + b_) * T_)) / ((a_ + b_) * sigmax_ * sigmay_);

            double /* @Real */temp = sigma_ * sigma_ / (a_ * a_);
            mux_ = -((temp + rho_ * sigma_ * eta_ / (a_ * b_)) * (1.0 - Math.exp(-a * T_)) - 0.5 * temp
                    * (1.0 - Math.exp(-2.0 * a_ * T_)) - rho_ * sigma_ * eta_ / (b_ * (a_ + b_))
                    * (1.0 - Math.exp(-(b_ + a_) * T_)));

            temp = eta_ * eta_ / (b_ * b_);
            muy_ = -((temp + rho_ * sigma_ * eta_ / (a_ * b_)) * (1.0 - Math.exp(-b * T_)) - 0.5 * temp
                    * (1.0 - Math.exp(-2.0 * b_ * T_)) - rho_ * sigma_ * eta_ / (a_ * (a_ + b_))
                    * (1.0 - Math.exp(-(b_ + a_) * T_)));

            for (int /* @Size */i = 0; i < size_; i++) {
                /*
                 * A_[i] = model.A(T_, t_[i]); Ba_[i] = model.B(a_, t_[i]-T_); Bb_[i] = model.B(b_, t_[i]-T_);
                 */
                A_.set(i, model.A(T_, t_.get(i)));
                Ba_.set(i, model.B(a_, t_.get(i) - T_));
                Bb_.set(i, model.B(b_, t_.get(i) - T_));
            }
        }

        double /* @Real */mux() {
            return mux_;
        }

        double /* @Real */sigmax() {
            return sigmax_;
        }

        double /* @Real */getOperatorEq(final double /* @Real */x) {
            final CumulativeNormalDistribution phi = new CumulativeNormalDistribution();
            final double /* @Real */temp = (x - mux_) / sigmax_;
            final double /* @Real */txy = Math.sqrt(1.0 - rhoxy_ * rhoxy_);

            final Array lambda = new Array(size_);
            int /* @Size */i;
            for (i = 0; i < size_; i++) {
                final double /* @Real */tau = (i == 0 ? t_.get(0) - T_ : t_.get(i) - t_.get(i - 1));
                final double /* @Real */c = (i == size_ - 1 ? (1.0 + rate_ * tau) : rate_ * tau);
                lambda.set(i, c * A_.get(i) * Math.exp(-Ba_.get(i) * x));
            }

            final SolvingFunction function = new SolvingFunction(lambda, Bb_);
            final Brent s1d = new Brent();
            s1d.setMaxEvaluations(1000);

            final double /* @Real */yb = s1d.solve(function, 1e-6, 0.00, -100.0, 100.0);
            final double /* @Real */h1 = (yb - muy_) / (sigmay_ * txy) - rhoxy_ * (x - mux_) / (sigmax_ * txy);
            // not sure if evaluate method is equivalent of op overloading -> we have to test it ;-)
            double /* @Real */value = /* phi(-w_*h1) */phi.op(-w_ * h1);

            for (i = 0; i < size_; i++) {
                final double /* @Real */h2 = h1 + Bb_.get(i) * sigmay_ * Math.sqrt(1.0 - rhoxy_ * rhoxy_);
                final double /* @Real */kappa = -Bb_.get(i)
                * (muy_ - 0.5 * txy * txy * sigmay_ * sigmay_ * Bb_.get(i) + rhoxy_ * sigmay_ * (x - mux_) / sigmax_);
                // operator overloading problem again
                value -= lambda.get(i) * Math.exp(kappa) * /* phi(-w_*h2) */phi.op(-w_ * h2);
            }

            return Math.exp(-0.5 * temp * temp) * value / (sigmax_ * Math.sqrt(2.0 * Constants.M_PI));
        }

        private class SolvingFunction implements Ops.DoubleOp {

            public SolvingFunction(final Array lambda, final Array Bb) {
                lambda_ = (lambda);
                Bb_ = (Bb);
            }

            public double /* @Real */op(final double /* @Real */y) {
                double /* @Real */value = 1.0;
                for (int /* @Size */i = 0; i < lambda_.size(); i++)
                    value -= lambda_.get(i) * Math.exp(-Bb_.get(i) * y);
                return value;
            }

            private final Array lambda_;
            private final Array Bb_;
        }

        double /* @Real */a_, sigma_, b_, eta_, rho_, w_;
        double /* @Time */T_;
        /* std::vector<Time> */List<Double /* @Time */> t_;
        double /* @Rate */rate_;
        int /* @Size */size_;
        Array A_, Ba_, Bb_;
        double /* @Real */mux_, muy_, sigmax_, sigmay_, rhoxy_;
    }



    //
    // static private inner classes
    //

    /**
     * Analytical term-structure fitting parameter {@latex$ \varphi(t) }.
     * <p>
     * {@latex$ \varphi(t) } is analytically defined by
     * <p>
     * {@latex[ \varphi(t) =
     *          f(t)
     *          + \frac{1}{2}(\frac{\sigma(1-e^{-at})}{a})^2
     *          + \frac{1}{2}(\frac{\eta(1-e^{-bt})}{b})^2 + \rho\frac{\sigma(1-e^{-at})}{a}\frac{\eta(1-e^{-bt})}{b} },
     * <p>
     * where {@latex$ f(t)} is the instantaneous forward rate at {@latex$ t}.
     */
    static class FittingParameter extends TermStructureFittingParameter {

        public FittingParameter(
                final Handle<YieldTermStructure> termStructure,
                final double /* @Real */a,
                final double /* @Real */sigma,
                final double /* @Real */b,
                final double /* @Real */eta,
                final double /* @Real */rho) {
            super(new Impl(termStructure, a, sigma, b, eta, rho));
        }


        //
        // static private inner classes
        //

        static private class Impl implements Parameter.Impl {

            private final Handle<YieldTermStructure> termStructure_;
            double /* @Real */ a_;
            double /* @Real */ sigma_;
            double /* @Real */ b_;
            double /* @Real */ eta_;
            double /* @Real */ rho_;

            public Impl(
                    final Handle<YieldTermStructure> termStructure,
                    final double /* @Real */ a,
                    final double /* @Real */ sigma,
                    final double /* @Real */ b,
                    final double /* @Real */ eta,
                    final double /* @Real */ rho) {
                this.termStructure_ = (termStructure);
                this.a_ = (a);
                this.sigma_ = (sigma);
                this.b_ = (b);
                this.eta_ = (eta);
                this.rho_ = (rho);
            }

            @Override
            public double /* @Real */value(final Array a, final double /* @Time */t) {
                final double /* @Rate */forward =
                    termStructure_.currentLink().forwardRate(t, t, Compounding.Continuous, Frequency.NoFrequency).rate();

                final double /* @Real */temp1 = sigma_ * (1.0 - Math.exp(-a_ * t)) / a_;
                final double /* @Real */temp2 = eta_ * (1.0 - Math.exp(-b_ * t)) / b_;
                final double /* @Real */value = 0.5 * temp1 * temp1 + 0.5 * temp2 * temp2 + rho_ * temp1 * temp2 + forward;
                return value;
            }

        }

    }

}
