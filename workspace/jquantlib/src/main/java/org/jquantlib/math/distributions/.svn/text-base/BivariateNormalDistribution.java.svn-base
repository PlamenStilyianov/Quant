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
 Copyright (C) 2002, 2003 Ferdinando Ametrano
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003 StatPro Italia srl
 Copyright (C) 2005 Gary Kennedy

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

package org.jquantlib.math.distributions;

import org.jquantlib.QL;
import org.jquantlib.math.Constants;
import org.jquantlib.math.Ops;
import org.jquantlib.math.integrals.TabulatedGaussLegendre;



/**
 * Cumulative bivariate normal distibution function (West 2004).
 * <p>
 * The bivariate normal distribution is a distribution of a pair of variables whose
 * conditional distributions are normal and that satisfy certain other technical conditions.
 * <p>
 * The implementation derives from the article <i>"Better Approximations To Cumulative
 * Normal Distibutions", Graeme West, Dec 2004</i> available at www.finmod.co.za.
 * Also available in <i>Wilmott Magazine, 2005, (May), 70-76,</i> The main code is a
 * port of the C++ code at <a href="www.finmod.co.za/cumnorm.zip">www.finmod.co.za/cummnorm.zip</a>.
 * <p>
 * The algorithm is based on the near double-precision algorithm described in
 * <i>"Numerical Computation of Rectangular Bivariate an Trivariate Normal and t
 * Probabilities", Genz (2004), Statistics and Computing 14, 151-160. </i>
 * (available at <a href="http://www.math.wsu.edu/faculty/genz/papers/bvnt/bvnt.html">www.math.wsu.edu/faculty/genz/papers/bvnt/bvnt.html</a>)
 * <p>
 * The standard bivariate normal distribution function is given by
 * {@latex[
 * 	\Phi({ \bf b}, \rho)= \frac{1}{2 \pi \sqrt{1- \rho^{2}}} \int_{- \infty}^{b_1} \int_{- \infty}^{b_2} e^{-(x^{2}-2 \rho xy +y^{2})/(2(1- \rho^{2}))}dydx
 * }
 * <p>
 * This implementation mainly differs from the original code in two regards;
 * <ol>
 * <li>The implementation of the cumulative normal distribution is {@code CumulativeNormalDistribution}</li>
 * <li> The arrays XX and W are zero-based</li>
 * </ol>
 *
 * @author Richard Gomes
 */
//TODO: code review :: seems like we should extend or implement something ?
public class BivariateNormalDistribution implements Ops.BinaryDoubleOp {

    //
    // private fields
    //

    private final double correlation;
    private final static CumulativeNormalDistribution cumnorm = new CumulativeNormalDistribution();


    //
    // public constructor
    //

    /**
     * Constructor of BivariateNormalDistribution to initialize the correlation.
     * The correlation <code>rho</code> must be >=-1.0 and <=1.0.
     * @param rho correlation
     */
    public BivariateNormalDistribution(final double rho) {
        QL.require(rho >= -1.0 && rho <= 1.0 , "rho must be >= -1.0 and <= 1.0"); // TODO: message
        correlation = rho;
    }


    //
    // implements Ops.BinaryDoubleOp
    //

    /**
     * Computes the Bivariate Normal Distribution of the two variables <code>x</code> and <code>y</code>
     * which can be correlated in a particular manner.
     *
     * @param x
     *                First variable
     * @param y
     *                Second variable
     * @return BVN
     */
    @Override
    public double op(final double x, final double y) {
        final TabulatedGaussLegendre gaussLegendreQuad = new TabulatedGaussLegendre(20);

        if (Math.abs(correlation) < 0.3) {
            gaussLegendreQuad.setOrder(6);
        } else if (Math.abs(correlation) < 0.75) {
            gaussLegendreQuad.setOrder(12);
        }

        final double h = -x;
        double k = -y;
        double hk = h * k;
        double bvn = 0.0;

        if (Math.abs(correlation) < 0.925) {
            if (Math.abs(correlation) > 0) {
                final double asr = Math.asin(correlation);
                final Eqn3 f = new Eqn3(h, k, asr);
                bvn = gaussLegendreQuad.evaluate(f);
                bvn *= asr * (0.25 / Math.PI);
            }
            bvn += cumnorm.op(-h) * cumnorm.op(-k);
        } else {
            if (correlation < 0) {
                k *= -1;
                hk *= -1;
            }
            if (Math.abs(correlation) < 1) {
                final double Ass = (1 - correlation) * (1 + correlation);
                double a = Math.sqrt(Ass);
                final double bs = (h - k) * (h - k);
                final double c = (4 - hk) / 8;
                final double d = (12 - hk) / 16;
                final double asr = -(bs / Ass + hk) / 2;
                if (asr > -100) {
                    bvn = a * Math.exp(asr) * (1 - c * (bs - Ass) * (1 - d * bs / 5) / 3 + c * d * Ass * Ass / 5);
                }
                if (-hk < 100) {
                    final double B = Math.sqrt(bs);
                    bvn -= Math.exp(-hk / 2) * Constants.M_SQRT2PI * cumnorm.op(-B / a) * B
                    * (1 - c * bs * (1 - d * bs / 5) / 3);
                }
                a /= 2;
                final Eqn6 f = new Eqn6(a, c, d, bs, hk);
                bvn += gaussLegendreQuad.evaluate(f);
                bvn /= (-2.0 * Math.PI);
            }

            if (correlation > 0) {
                bvn += cumnorm.op(-Math.max(h, k));
            } else {
                bvn *= -1;
                if (k > h) {
                    bvn += cumnorm.op(k) - cumnorm.op(h);
                }
            }
        }
        return bvn;
    }


    /**
     * Relates to equation 3, see references
     *
     * @see <a href="http://www.math.wsu.edu/faculty/genz/papers/bvnt/node4.html#L1P">Genz 2004, The Transformed BVN Problem</a>
     */

    private static class Eqn3 implements Ops.DoubleOp {

        private final double hk, asr, hs;

        /**
         * Equation 3, see Genz 2004.
         *
         * @param h
         * @param k
         * @param asr ASIN of <code>correlation_</code>
         * @return Math.exp((sn * hk_ - hs_) / (1.0 - sn * sn))
         */

        public Eqn3(final double h, final double k, final double asr) {
            this.hk = h * k;
            this.hs = (h * h + k * k) / 2;
            this.asr = asr;
        }


        //
        // Implements Ops.DoubleOp
        //

        /**
         * Computes equation 3, see references
         *
         * @see <a href="http://www.math.wsu.edu/faculty/genz/papers/bvnt/node4.html#L1P">Genz 2004, The Transformed BVN Problem</a>
         *
         * @param h
         * @param k
         * @param asr ASIN of <code>correlation_</code>
         * @return Math.exp((sn * hk_ - hs_) / (1.0 - sn * sn))
         */
        public double op(final double x) {
            final double sn = Math.sin(this.asr * (-x + 1) * 0.5);
            return Math.exp((sn * hk - hs) / (1.0 - sn * sn));
        }
    }


    /**
     * Relates to equation 6, see references
     *
     * @see <a href="http://www.math.wsu.edu/faculty/genz/papers/bvnt/node5.html#L3">Genz 2004, Numerical Integration Results</a>
     *
     */
    private static class Eqn6 implements Ops.DoubleOp {

        private final double a, c, d, bs, hk;

        /**
         * Constructor to initialize a, b, c, d, bs and hk.
         *
         * @param a
         * @param c
         * @param d
         * @param bs
         * @param hk
         */
        public Eqn6(final double a, final double c, final double d, final double bs, final double hk) {
            this.a = a;
            this.c = c;
            this.d = d;
            this.bs = bs;
            this.hk = hk;
        }


        //
        // Implements Ops.DoubleOp
        //

        /**
         * Computes equation 6, see references<br>
         *
         * @see <a href="http://www.math.wsu.edu/faculty/genz/papers/bvnt/node5.html#L3">Genz 2004, Numerical Integration
         *      Results</a>
         *
         * @param x
         * @return <code>if (asr > -100.0) </code> return (a_ * Math.exp(asr) * (Math.exp(-hk_ * (1 - rs) / (2 * (1 + rs))) / rs -
         *         (1 + c_ * xs * (1 + d_ * xs))))<br>
         *         <code>else</code> return 0.00
         *
         */
        public double op(final double x) {
            double xs = a * (-x + 1);
            xs = Math.abs(xs * xs);
            final double rs = Math.sqrt(1 - xs);
            final double asr = -(bs / xs + hk) / 2;
            if (asr > -100.0) {
                return (a * Math.exp(asr) * (Math.exp(-hk * (1 - rs) / (2 * (1 + rs))) / rs - (1 + c * xs * (1 + d * xs))));
            }
            return 0.0;
        }
    }

}
