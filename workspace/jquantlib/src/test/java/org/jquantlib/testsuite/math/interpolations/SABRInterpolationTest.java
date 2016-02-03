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


package org.jquantlib.testsuite.math.interpolations;

import static java.lang.Math.abs;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.lang.annotation.Time;
import org.jquantlib.math.Constants;
import org.jquantlib.math.interpolations.SABRInterpolation;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.optimization.EndCriteria;
import org.jquantlib.math.optimization.LevenbergMarquardt;
import org.jquantlib.math.optimization.OptimizationMethod;
import org.jquantlib.math.optimization.Simplex;
import org.jquantlib.termstructures.volatilities.Sabr;
import org.junit.Ignore;
import org.junit.Test;

public class SABRInterpolationTest {

    //TODO: uncomment when Minpack.lmdif becomes available
    @Ignore
    @Test
    public void testSABRInterpolationTest() {
        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");
        QL.info("Testing SABR interpolation...");

        // Test SABR function against input volatilities
        final double tolerance = 2.0e-13;
        final double[] strikes = new double[31];
        final double[] volatilities = new double[31];
        // input strikes
        strikes[0] = 0.03 ; strikes[1] = 0.032 ; strikes[2] = 0.034 ;
        strikes[3] = 0.036 ; strikes[4] = 0.038 ; strikes[5] = 0.04 ;
        strikes[6] = 0.042 ; strikes[7] = 0.044 ; strikes[8] = 0.046 ;
        strikes[9] = 0.048 ; strikes[10] = 0.05 ; strikes[11] = 0.052 ;
        strikes[12] = 0.054 ; strikes[13] = 0.056 ; strikes[14] = 0.058 ;
        strikes[15] = 0.06 ; strikes[16] = 0.062 ; strikes[17] = 0.064 ;
        strikes[18] = 0.066 ; strikes[19] = 0.068 ; strikes[20] = 0.07 ;
        strikes[21] = 0.072 ; strikes[22] = 0.074 ; strikes[23] = 0.076 ;
        strikes[24] = 0.078 ; strikes[25] = 0.08 ; strikes[26] = 0.082 ;
        strikes[27] = 0.084 ; strikes[28] = 0.086 ; strikes[29] = 0.088;
        strikes[30] = 0.09;
        // input volatilities
        volatilities[0] = 1.16725837321531 ; volatilities[1] = 1.15226075991385 ; volatilities[2] = 1.13829711098834 ;
        volatilities[3] = 1.12524190877505 ; volatilities[4] = 1.11299079244474 ; volatilities[5] = 1.10145609357162 ;
        volatilities[6] = 1.09056348513411 ; volatilities[7] = 1.08024942745106 ; volatilities[8] = 1.07045919457758 ;
        volatilities[9] = 1.06114533019077 ; volatilities[10] = 1.05226642581503 ; volatilities[11] = 1.04378614411707 ;
        volatilities[12] = 1.03567243073732 ; volatilities[13] = 1.0278968727451 ; volatilities[14] = 1.02043417226345 ;
        volatilities[15] = 1.01326171139321 ; volatilities[16] = 1.00635919013311 ; volatilities[17] = 0.999708323124949 ;
        volatilities[18] = 0.993292584155381 ; volatilities[19] = 0.987096989695393 ; volatilities[20] = 0.98110791455717 ;
        volatilities[21] = 0.975312934134512 ; volatilities[22] = 0.969700688771689 ; volatilities[23] = 0.964260766651027;
        volatilities[24] = 0.958983602256592 ; volatilities[25] = 0.953860388001395 ; volatilities[26] = 0.948882997029509 ;
        volatilities[27] = 0.944043915545469 ; volatilities[28] = 0.939336183299237 ; volatilities[29] = 0.934753341079515 ;
        volatilities[30] = 0.930289384251337;

        final Array strikeArray = new Array(strikes.length);
        final Array volatilityArray = new Array(volatilities.length);

        for (int i = 0; i < strikes.length; i++) {
            strikeArray.set(i, strikes[i]);
        }
        for (int i = 0; i < volatilities.length; i++) {
            volatilityArray.set(i, volatilities[i]);
        }

        @Time
        final double expiry = 1.0;
        final double forward = 0.039;
        // input SABR coefficients (corresponding to the vols above)
        final double initialAlpha = 0.3;
        final double initialBeta = 0.6;
        final double initialNu = 0.02;
        final double initialRho = 0.01;
        // calculate SABR vols and compare with input vols
        for(int i=0; i < strikes.length; i++){
            final double calculatedVol = (new Sabr()).sabrVolatility(strikes[i], forward, expiry,
                                                				initialAlpha, initialBeta,
                                                				initialNu, initialRho);
	        assertFalse("failed to calculate Sabr function at strike " + strikes[i]
			                + "\n    expected:   " + volatilities[i]
			                + "\n    calculated: " + calculatedVol
			                + "\n    error:      " + abs(calculatedVol-volatilities[i]),
			            abs(volatilities[i]-calculatedVol) > tolerance);
        }

        // Test SABR calibration against input parameters
        // Initial null guesses (uses default values)
        final double alphaGuess = Constants.NULL_REAL;
        final double betaGuess = Constants.NULL_REAL;
        final double nuGuess = Constants.NULL_REAL;
        final double rhoGuess = Constants.NULL_REAL;

        final boolean vegaWeighted[]= {true, false};
        final boolean isAlphaFixed[]= {true, false};
        final boolean isBetaFixed[]= {true, false};
        final boolean isNuFixed[]= {true, false};
        final boolean isRhoFixed[]= {true, false};

        final double calibrationTolerance = 5.0e-8;
        // initialize optimization methods
        final List<OptimizationMethod> methods_ = new ArrayList<OptimizationMethod>();
        methods_.add(new Simplex(0.01));
        methods_.add(new LevenbergMarquardt(1e-8, 1e-8, 1e-8));
        // Initialize end criteria
        final EndCriteria endCriteria = new EndCriteria(100000, 100, 1e-8, 1e-8, 1e-8);
        // Test looping over all possibilities
        for (int j=0; j<methods_.size(); ++j) {
          for (int i=0; i<vegaWeighted.length; ++i) {
            for (int k_a=0; k_a<isAlphaFixed.length; ++k_a) {
              for (int k_b=0; k_b<isBetaFixed.length; ++k_b) {
                for (int k_n=0; k_n<isNuFixed.length; ++k_n) {
                  for (int k_r=0; k_r<isRhoFixed.length; ++k_r) {
                    final SABRInterpolation sabrInterpolation =
                    	new SABRInterpolation(strikeArray, volatilityArray, expiry, forward,
				                                 alphaGuess, betaGuess, nuGuess, rhoGuess,
				                                 isAlphaFixed[k_a], isBetaFixed[k_b],
				                                 isNuFixed[k_n], isRhoFixed[k_r],
				                                 vegaWeighted[i],
				                                 endCriteria, methods_.get(j));
                    sabrInterpolation.update();

                    // Recover SABR calibration parameters
                    final double calibratedAlpha = sabrInterpolation.alpha();
                    final double calibratedBeta = sabrInterpolation.beta();
                    final double calibratedNu = sabrInterpolation.nu();
                    final double calibratedRho = sabrInterpolation.rho();
                    double error;

                    // compare results: alpha
                    error = abs(initialAlpha-calibratedAlpha);
                    assertFalse("\nfailed to calibrate alpha Sabr parameter:" +
                                    "\n    expected:        " + initialAlpha +
                                    "\n    calibrated:      " + calibratedAlpha +
                                    "\n    error:           " + error,
                                error > calibrationTolerance);
                    // Beta
                    error = abs(initialBeta-calibratedBeta);
                    assertFalse("\nfailed to calibrate beta Sabr parameter:" +
                                    "\n    expected:        " + initialBeta +
                                    "\n    calibrated:      " + calibratedBeta +
                                    "\n    error:           " + error,
                                error > calibrationTolerance);
                    // Nu
                    error = abs(initialNu-calibratedNu);
                    assertFalse("\nfailed to calibrate nu Sabr parameter:" +
                                    "\n    expected:        " + initialNu +
                                    "\n    calibrated:      " + calibratedNu +
                                    "\n    error:           " + error,
                                error > calibrationTolerance);
                    // Rho
                    error = abs(initialRho-calibratedRho);
                    assertFalse("\nfailed to calibrate rho Sabr parameter:" +
                                    "\n    expected:        " + initialRho +
                                    "\n    calibrated:      " + calibratedRho +
                                    "\n    error:           " + error,
                                error > calibrationTolerance);
                  }
                }
              }
            }
          }
        }

    }

}

