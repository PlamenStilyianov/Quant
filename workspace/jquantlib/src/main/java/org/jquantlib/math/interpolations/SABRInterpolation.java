/*
 Copyright (C) 2010 Selene Makarios

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
 Copyright (C) 2006 Ferdinando Ametrano
 Copyright (C) 2007 Marco Bianchetti
 Copyright (C) 2007 Francois du Vignaud
 Copyright (C) 2007 Giorgio Facchinetti
 Copyright (C) 2006 Mario Pucci
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

package org.jquantlib.math.interpolations;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.CostFunction;
import org.jquantlib.math.optimization.EndCriteria;
import org.jquantlib.math.optimization.NoConstraint;
import org.jquantlib.math.optimization.OptimizationMethod;
import org.jquantlib.math.optimization.ParametersTransformation;
import org.jquantlib.math.optimization.Problem;
import org.jquantlib.math.optimization.ProjectedCostFunction;
import org.jquantlib.math.optimization.Simplex;
import org.jquantlib.pricingengines.BlackFormula;
import org.jquantlib.termstructures.volatilities.Sabr;

/**
 * SABR smile interpolation between discrete volatility points.
 *
 * @author Selene Makarios
 */
public class SABRInterpolation extends AbstractInterpolation {

    private final SABRCoeffHolder coeffs_;

	public SABRInterpolation(
			final Array vx, // x = strikes
			final Array vy, // y = volatilities
			@Time final double t, // option expiry
			final double forward,
			final double alpha,
			final double beta,
			final double nu,
			final double rho,
			final boolean alphaIsFixed,
			final boolean betaIsFixed,
			final boolean nuIsFixed,
			final boolean rhoIsFixed,
			final boolean vegaWeighted,
			final EndCriteria endCriteria,
			final OptimizationMethod optMethod) {

		impl = new SABRInterpolationImpl(vx, vy, t, forward, alpha, beta, nu, rho, alphaIsFixed, betaIsFixed, nuIsFixed, rhoIsFixed, vegaWeighted,
				endCriteria, optMethod);
		coeffs_ = ((SABRInterpolationImpl) impl).itsCoeffs;
	}

	public double expiry() {
		return coeffs_.t_;
	}

	public double forward() {
		return coeffs_.forward_;
	}

	public double alpha() {
		return coeffs_.alpha_;
	}

	public double beta() {
		return coeffs_.beta_;
	}

	public double nu() {
		return coeffs_.nu_;
	}

	public double rho() {
		return coeffs_.rho_;
	}

	public double rmsError() {
		return coeffs_.error_;
	}

	public double maxError() {
		return coeffs_.maxError_;
	}

	public Array interpolationWeights() {
		return coeffs_.weights_;
	}

	public EndCriteria.Type endCriteria() {
		return coeffs_.SABREndCriteria_;
	}

	private class SABRCoeffHolder {

		public SABRCoeffHolder(
		        final @Time double t,
		        final double forward,
		        final double alpha,
		        final double beta,
		        final double nu,
		        final double rho,
		        final boolean alphaIsFixed,
				final boolean betaIsFixed,
				final boolean nuIsFixed,
				final boolean rhoIsFixed) {
			t_ = t;
			forward_ = forward;
			alpha_ = alpha;
			beta_ = beta;
			nu_ = nu;
			rho_ = rho;
			alphaIsFixed_ = false;
			betaIsFixed_ = false;
			nuIsFixed_ = false;
			rhoIsFixed_ = false;
			weights_ = new Array(0);//ZH:TBD:verify with QL097, it is vector<Real> in QL097
			error_ = Constants.NULL_REAL;
			maxError_ = Constants.NULL_REAL;
			SABREndCriteria_ = EndCriteria.Type.None;

			QL.require(t > 0.0, "expiry time must be positive: " + t + " not allowed");
			if (!Double.isNaN(alpha_)) {
                alphaIsFixed_ = alphaIsFixed;
            } else {
                alpha_ = Math.sqrt(0.2);
            }
			if (!Double.isNaN(beta_)) {
                betaIsFixed_ = betaIsFixed;
            } else {
                beta_ = 0.5;
            }
			if (!Double.isNaN(nu_)) {
                nuIsFixed_ = nuIsFixed;
            } else {
                nu_ = Math.sqrt(0.4);
            }
			if (!Double.isNaN(rho_)) {
                rhoIsFixed_ = rhoIsFixed;
            } else {
                rho_ = 0.0;
            }
			(new Sabr()).validateSabrParameters(alpha_, beta_, nu_, rho_);
		}

		/* ! Option expiry */
		public double t_;
		/* ! */
		public double forward_;
		/* ! Sabr parameters */
		public double alpha_, beta_, nu_, rho_;
		public boolean alphaIsFixed_, betaIsFixed_, nuIsFixed_, rhoIsFixed_;
		public Array weights_;
		/* ! Sabr interpolation results */
		public double error_, maxError_;
		public EndCriteria.Type SABREndCriteria_;
	}

	private class SABRInterpolationImpl extends AbstractInterpolation.Impl {

        EndCriteria endCriteria_;
        OptimizationMethod optMethod_;
        double forward_;
        boolean vegaWeighted_;
        ParametersTransformation transformation_;
        NoConstraint constraint_;

        public SABRCoeffHolder itsCoeffs;

        public SABRInterpolationImpl(
                final Array vx,
                final Array vy,
                final @Time double t,
                final double forward,
                final double alpha,
                final double beta,
                final double nu,
                final double rho,
                final boolean alphaIsFixed,
                final boolean betaIsFixed,
                final boolean nuIsFixed,
                final boolean rhoIsFixed,
                final boolean vegaWeighted,
                final EndCriteria endCriteria,
                final OptimizationMethod optMethod) {
			super(vx, vy);
			itsCoeffs = new SABRCoeffHolder(t, forward, alpha, beta, nu, rho, alphaIsFixed, betaIsFixed, nuIsFixed, rhoIsFixed);
			endCriteria_ = endCriteria;
			optMethod_ = optMethod;
			forward_ = forward;
			vegaWeighted_ = vegaWeighted;

			// if no optimization method or endCriteria is provided, we provide
			// one
			if (optMethod_ != null) {
                // optMethod_ = boost::shared_ptr<OptimizationMethod>(new
				// LevenbergMarquardt(1e-8, 1e-8, 1e-8));
				optMethod_ = new Simplex(0.01);
            }
			if (endCriteria_ != null) {
				endCriteria_ = new EndCriteria(60000, 100, 1e-8, 1e-8, 1e-8);
			}
			itsCoeffs.weights_ = new Array(vx.size());
			for (int i = 0; i < itsCoeffs.weights_.size(); i++) {
                itsCoeffs.weights_.set(i, 1.0 / vx.size());
            }
		}

		@Override
        public void update() {
			// forward_ might have changed
			QL.require(forward_ > 0.0, "at the money forward rate must be " + "positive: " + forward_ + " not allowed");

			// we should also check that y contains positive values only

			// we must update weights if it is vegaWeighted
			if (vegaWeighted_) {
				// itsCoeffs.weights_.clear();
				double weightsSum = 0.0;
				for (int i = 0; i < vx.size(); i++) {
					final double x = vx.get(i);
					final double y = vy.get(i);
					final double stdDev = Math.sqrt(y * y * itsCoeffs.t_);
					itsCoeffs.weights_.set(i, BlackFormula.blackFormulaStdDevDerivative(x, forward_, stdDev));
					weightsSum += itsCoeffs.weights_.get(i);
				}
				// weight normalization
				for (int i = 0; i < itsCoeffs.weights_.size(); i++) {
                    itsCoeffs.weights_.set(i, itsCoeffs.weights_.get(i) / weightsSum);
                }
			}

			// there is nothing to optimize
			if (itsCoeffs.alphaIsFixed_ && itsCoeffs.betaIsFixed_ && itsCoeffs.nuIsFixed_ && itsCoeffs.rhoIsFixed_) {
				itsCoeffs.error_ = interpolationError();
				itsCoeffs.maxError_ = interpolationMaxError();
				itsCoeffs.SABREndCriteria_ = EndCriteria.Type.None;
				return;

			} else {

				final SABRError costFunction = new SABRError(this);
				transformation_ = new SabrParametersTransformation();

				final Array guess = new Array(4);
				guess.set(0, itsCoeffs.alpha_);
				guess.set(1, itsCoeffs.beta_);
				guess.set(2, itsCoeffs.nu_);
				guess.set(3, itsCoeffs.rho_);

				final boolean[] parameterAreFixed = new boolean[4];
				parameterAreFixed[0] = itsCoeffs.alphaIsFixed_;
				parameterAreFixed[1] = itsCoeffs.betaIsFixed_;
				parameterAreFixed[2] = itsCoeffs.nuIsFixed_;
				parameterAreFixed[3] = itsCoeffs.rhoIsFixed_;

				final Array inversedTransformatedGuess = new Array(transformation_.inverse(guess));

				final ProjectedCostFunction constrainedSABRError = new ProjectedCostFunction(costFunction, inversedTransformatedGuess, parameterAreFixed);

				final Array projectedGuess = new Array(constrainedSABRError.project(inversedTransformatedGuess));

				final NoConstraint constraint = new NoConstraint();
				final Problem problem = new Problem(constrainedSABRError, constraint, projectedGuess);
				itsCoeffs.SABREndCriteria_ = optMethod_.minimize(problem, endCriteria_);
				final Array projectedResult = new Array(problem.currentValue());
				final Array transfResult = new Array(constrainedSABRError.include(projectedResult));

				final Array result = transformation_.direct(transfResult);
				itsCoeffs.alpha_ = result.get(0);
				itsCoeffs.beta_ = result.get(1);
				itsCoeffs.nu_ = result.get(2);
				itsCoeffs.rho_ = result.get(3);

			}
			itsCoeffs.error_ = interpolationError();
			itsCoeffs.maxError_ = interpolationMaxError();

		}

		@Override
        public double op(final double x) {
			QL.require(x > 0.0, "strike must be positive: " + x + " not allowed");
			return (new Sabr()).sabrVolatility(x, forward_, itsCoeffs.t_, itsCoeffs.alpha_, itsCoeffs.beta_, itsCoeffs.nu_, itsCoeffs.rho_);
		}

		@Override
        public double primitive(final double x) {
			throw new LibraryException("SABR primitive not implemented");
		}

		@Override
        public double derivative(final double x) {
			throw new LibraryException("SABR derivative not implemented");
		}

		@Override
        public double secondDerivative(final double x) {
			throw new LibraryException("SABR secondDerivative not implemented");
		}

		// calculate total squared weighted difference (L2 norm)
		public double interpolationSquaredError() {
			double error, totalError = 0.0;
			int ix = vx.begin();
			int iy = vy.begin();
			int iw = itsCoeffs.weights_.begin();
			while (ix < vx.end()) {
				final double x = vx.get(ix);
				final double y = vy.get(iy);
				final double w = itsCoeffs.weights_.get(iw);
				error = (op(x) - y);
				totalError += error * error * w;
				ix++;
				iy++;
				iw++;
			}
			return totalError;
		}

		// calculate weighted differences
		public Array interpolationErrors(final Array not_used) {
			final Array results = new Array(vx.size());
			int ix = vx.begin();
			int iy = vy.begin();
			int iw = itsCoeffs.weights_.begin();
			int ir = results.begin();
			while (ix < vx.end()) {
				final double x = vx.get(ix);
				final double y = vy.get(iy);
				final double w = itsCoeffs.weights_.get(iw);
				results.set(ir, (op(x) - y) * Math.sqrt(w));
				ix++;
				iy++;
				iw++;
				ir++;
			}
			return results;
		}

		public double interpolationError() {
			final int n = vx.size();
			final double squaredError = interpolationSquaredError();
			return Math.sqrt(n * squaredError / (n - 1));
		}

		public double interpolationMaxError() {
			double error, maxError = Constants.DBL_MIN;
			for (int i = 0; i < vx.size(); i++) {
				final double x = vx.get(i);
				final double y = vy.get(i);
				error = Math.abs(op(x) - y);
				maxError = Math.max(maxError, error);
			}
			return maxError;
		}


		private class SabrParametersTransformation implements ParametersTransformation {
			Array y_;
			final double eps1_, eps2_;//, dilationFactor_;

			public SabrParametersTransformation() {
				y_ = new Array(4);
				eps1_ = .0000001;
				eps2_ = .9999;
				//dilationFactor_ = 0.001;
			}

			public Array direct(final Array x) {
				y_.set(0, x.get(0) * x.get(0) + eps1_);
				// y_(1) = std::atan(dilationFactor_*x(1))/M_PI + 0.5;
				y_.set(1, Math.exp(-(x.get(1) * x.get(1))));
				y_.set(2, x.get(2) * x.get(2) + eps1_);
				y_.set(3, eps2_ * Math.sin(x.get(3)));
				return y_;
			}

			public Array inverse(final Array x) {
				y_.set(0, Math.sqrt(x.get(0) - eps1_));
				// y_(1) = std::tan(M_PI*(x(1) - 0.5))/dilationFactor_;
				y_.set(1, Math.sqrt(-Math.log(x.get(1))));
				y_.set(2, Math.sqrt(x.get(2) - eps1_));
				y_.set(3, Math.asin(x.get(3) / eps2_));

				return y_;
			}
		}


		private class SABRError extends CostFunction {

			public SABRError(final SABRInterpolationImpl sabr) {
				this.sabr_ = sabr;
			}

			@Override
            public double value(final Array x) {
				final Array y = sabr_.transformation_.direct(x);
				sabr_.itsCoeffs.alpha_ = y.get(0);
				sabr_.itsCoeffs.beta_ = y.get(1);
				sabr_.itsCoeffs.nu_ = y.get(2);
				sabr_.itsCoeffs.rho_ = y.get(3);
				return sabr_.interpolationSquaredError();
			}

			@Override
            public Array values(final Array x) {
				final Array y = sabr_.transformation_.direct(x);
				sabr_.itsCoeffs.alpha_ = y.get(0);
				sabr_.itsCoeffs.beta_ = y.get(1);
				sabr_.itsCoeffs.nu_ = y.get(2);
				sabr_.itsCoeffs.rho_ = y.get(3);
				return sabr_.interpolationErrors(x);
			}

			private final SABRInterpolationImpl sabr_;
		}

	}

}
