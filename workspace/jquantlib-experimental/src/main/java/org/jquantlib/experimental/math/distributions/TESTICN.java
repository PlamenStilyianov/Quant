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

package org.jquantlib.experimental.math.distributions;

/**
 *  CODE FROM  http://home.online.no/~pjacklam/notes/invnorm/impl/karimov/StatUtil.java
 * @author Q. Boiler
 */
public class TESTICN {
/**
 * Class contains the implementation of:
 * - Inverse Normal Cummulative Distribution Function Algorythm
 * - Error Function Algorythm
 * - Complimentary Error Function Algorythm
 *
 * @author Sherali Karimov (sherali.karimov@proxima-tech.com)
 */
/* ********************************************
 * Original algorythm and Perl implementation can
 * be found at:
 * http://www.math.uio.no/~jacklam/notes/invnorm/index.html
 * Author:
 *  Peter J. Acklam
 *  jacklam@math.uio.no
 * ****************************************** */
  private static final double P_LOW  = 0.02425D;
  private static final double P_HIGH = 1.0D - P_LOW;

  // Coefficients in rational approximations.
  private static final double ICDF_A[] =
  { -3.969683028665376e+01,  2.209460984245205e+02,
    -2.759285104469687e+02,  1.383577518672690e+02,
    -3.066479806614716e+01,  2.506628277459239e+00 };

  private static final double ICDF_B[] =
  { -5.447609879822406e+01,  1.615858368580409e+02,
    -1.556989798598866e+02,  6.680131188771972e+01,
    -1.328068155288572e+01 };

  private static final double ICDF_C[] =
  { -7.784894002430293e-03, -3.223964580411365e-01,
    -2.400758277161838e+00, -2.549732539343734e+00,
    4.374664141464968e+00,  2.938163982698783e+00 };

  private static final double ICDF_D[] =
  { 7.784695709041462e-03,  3.224671290700398e-01,
    2.445134137142996e+00,  3.754408661907416e+00 };

  public static double getInvCDF(double d, boolean highPrecision)
  {
    // Define break-points.
    // variable for result
    double z = 0;

    if(d == 0) z = Double.NEGATIVE_INFINITY;
    else if(d == 1) z = Double.POSITIVE_INFINITY;
    else if(Double.isNaN(d) || d < 0 || d > 1) z = Double.NaN;

    // Rational approximation for lower region:
    else if( d < P_LOW )
    {
      double q  = Math.sqrt(-2*Math.log(d));
      z = (((((ICDF_C[0]*q+ICDF_C[1])*q+ICDF_C[2])*q+ICDF_C[3])*q+ICDF_C[4])*q+ICDF_C[5]) / ((((ICDF_D[0]*q+ICDF_D[1])*q+ICDF_D[2])*q+ICDF_D[3])*q+1);
    }

    // Rational approximation for upper region:
    else if ( P_HIGH < d )
    {
      double q  = Math.sqrt(-2*Math.log(1-d));
      z = -(((((ICDF_C[0]*q+ICDF_C[1])*q+ICDF_C[2])*q+ICDF_C[3])*q+ICDF_C[4])*q+ICDF_C[5]) / ((((ICDF_D[0]*q+ICDF_D[1])*q+ICDF_D[2])*q+ICDF_D[3])*q+1);
    }
   // Rational approximation for central region:
    else
    {
      double q = d - 0.5D;
      double r = q * q;
      z = (((((ICDF_A[0]*r+ICDF_A[1])*r+ICDF_A[2])*r+ICDF_A[3])*r+ICDF_A[4])*r+ICDF_A[5])*q / (((((ICDF_B[0]*r+ICDF_B[1])*r+ICDF_B[2])*r+ICDF_B[3])*r+ICDF_B[4])*r+1);
    }
    if(highPrecision) z = refine(z, d);
    return z;
  }

//C------------------------------------------------------------------
//C  Coefficients for approximation to  erf  in first interval
//C------------------------------------------------------------------
  private static final double ERF_A[] =
  { 3.16112374387056560E00, 1.13864154151050156E02,
    3.77485237685302021E02, 3.20937758913846947E03,
    1.85777706184603153E-1 };

  private static final double ERF_B[] =
  { 2.36012909523441209E01, 2.44024637934444173E02,
    1.28261652607737228E03, 2.84423683343917062E03 };

//C------------------------------------------------------------------
//C  Coefficients for approximation to  erfc  in second interval
//C------------------------------------------------------------------
  private static final double ERF_C[] =
  { 5.64188496988670089E-1, 8.88314979438837594E0,
    6.61191906371416295E01, 2.98635138197400131E02,
    8.81952221241769090E02, 1.71204761263407058E03,
    2.05107837782607147E03, 1.23033935479799725E03,
    2.15311535474403846E-8 };

  private static final double ERF_D[] =
  { 1.57449261107098347E01,1.17693950891312499E02,
    5.37181101862009858E02,1.62138957456669019E03,
    3.29079923573345963E03,4.36261909014324716E03,
    3.43936767414372164E03,1.23033935480374942E03 };

//C------------------------------------------------------------------
//C  Coefficients for approximation to  erfc  in third interval
//C------------------------------------------------------------------
  private static final double ERF_P[] =
  { 3.05326634961232344E-1,3.60344899949804439E-1,
    1.25781726111229246E-1,1.60837851487422766E-2,
    6.58749161529837803E-4,1.63153871373020978E-2 };

  private static final double ERF_Q[] =
  { 2.56852019228982242E00,1.87295284992346047E00,
    5.27905102951428412E-1,6.05183413124413191E-2,
    2.33520497626869185E-3 };

  private static final double PI_SQRT = Math.sqrt(Math.PI);
  private static final double THRESHOLD = 0.46875D;

/* **************************************
 * Hardware dependant constants were calculated
 * on Dell "Dimension 4100":
 * - Pentium III 800 MHz
 * running Microsoft Windows 2000
 * ************************************* */
  private static final double X_MIN = Double.MIN_VALUE;
  private static final double X_INF = Double.MAX_VALUE;
  private static final double X_NEG = -9.38241396824444;
  private static final double X_SMALL = 1.110223024625156663E-16;
  private static final double X_BIG = 9.194E0;
  private static final double X_HUGE = 1.0D / (2.0D * Math.sqrt(X_SMALL));
  private static final double X_MAX = Math.min(X_INF, (1 / (Math.sqrt(Math.PI) * X_MIN)));

  private static double calerf(double X, int jint)
  {
/* ******************************************
 * ORIGINAL FORTRAN version can be found at:
 * http://www.netlib.org/specfun/erf
 ********************************************
C------------------------------------------------------------------
C
C THIS PACKET COMPUTES THE ERROR AND COMPLEMENTARY ERROR FUNCTIONS
C   FOR REAL ARGUMENTS  ARG.  IT CONTAINS TWO FUNCTION TYPE
C   SUBPROGRAMS,  ERF  AND  ERFC  (OR  DERF  AND  DERFC),  AND ONE
C   SUBROUTINE TYPE SUBPROGRAM,  CALERF.  THE CALLING STATEMENTS
C   FOR THE PRIMARY ENTRIES ARE
C
C                   Y=ERF(X)     (OR   Y=DERF(X) )
C   AND
C                   Y=ERFC(X)    (OR   Y=DERFC(X) ).
C
C   THE ROUTINE  CALERF  IS INTENDED FOR INTERNAL PACKET USE ONLY,
C   ALL COMPUTATIONS WITHIN THE PACKET BEING CONCENTRATED IN THIS
C   ROUTINE.  THE FUNCTION SUBPROGRAMS INVOKE  CALERF  WITH THE
C   STATEMENT
C          CALL CALERF(ARG,RESULT,JINT)
C   WHERE THE PARAMETER USAGE IS AS FOLLOWS
C
C      FUNCTION                     PARAMETERS FOR CALERF
C       CALL              ARG                  RESULT          JINT
C     ERF(ARG)      ANY REAL ARGUMENT         ERF(ARG)          0
C     ERFC(ARG)     ABS(ARG) .LT. XMAX        ERFC(ARG)         1
C
C   THE MAIN COMPUTATION EVALUATES NEAR MINIMAX APPROXIMATIONS
C   FROM "RATIONAL CHEBYSHEV APPROXIMATIONS FOR THE ERROR FUNCTION"
C   BY W. J. CODY, MATH. COMP., 1969, PP. 631-638.  THIS
C   TRANSPORTABLE PROGRAM USES RATIONAL FUNCTIONS THAT THEORETICALLY
C       APPROXIMATE  ERF(X)  AND  ERFC(X)  TO AT LEAST 18 SIGNIFICANT
C   DECIMAL DIGITS.  THE ACCURACY ACHIEVED DEPENDS ON THE ARITHMETIC
C   SYSTEM, THE COMPILER, THE INTRINSIC FUNCTIONS, AND PROPER
C   SELECTION OF THE MACHINE-DEPENDENT CONSTANTS.
C
C  AUTHOR: W. J. CODY
C          MATHEMATICS AND COMPUTER SCIENCE DIVISION
C          ARGONNE NATIONAL LABORATORY
C          ARGONNE, IL 60439
C
C  LATEST MODIFICATION: JANUARY 8, 1985
C
C------------------------------------------------------------------
*/
    double result = 0;
    double Y = Math.abs(X);
    double Y_SQ, X_NUM, X_DEN;

    if(Y <= THRESHOLD)
    {
      Y_SQ = 0.0D;
      if (Y > X_SMALL) Y_SQ = Y * Y;
      X_NUM = ERF_A[4] * Y_SQ;
      X_DEN = Y_SQ;
      for (int i=0;i<3;i++)
      {
        X_NUM = (X_NUM + ERF_A[i]) * Y_SQ;
        X_DEN = (X_DEN + ERF_B[i]) * Y_SQ;
      }
      result = X * (X_NUM + ERF_A[3]) / (X_DEN + ERF_B[3]);
      if (jint != 0)  result = 1 - result;
      if(jint == 2) result = Math.exp(Y_SQ) * result;
      return result;
    }
    else if (Y <= 4.0D)
    {
      X_NUM = ERF_C[8] * Y;
      X_DEN = Y;
      for (int i=0;i<7;i++)
      {
        X_NUM = (X_NUM + ERF_C[i]) * Y;
        X_DEN = (X_DEN + ERF_D[i]) * Y;
      }
      result = (X_NUM + ERF_C[7]) / (X_DEN + ERF_D[7]);
      if(jint != 2)
      {
        Y_SQ = Math.round(Y*16.0D)/16.0D;
        double del = (Y - Y_SQ) * (Y + Y_SQ);
        result = Math.exp(-Y_SQ * Y_SQ) * Math.exp(-del) * result;
      }
    }
    else
    {
      result = 0.0D;
      if( Y >= X_BIG && (jint != 2 || Y >= X_MAX));
      else if(Y >= X_BIG && Y >= X_HUGE) result = PI_SQRT / Y;
      else
      {
        Y_SQ = 1.0D / (Y * Y);
        X_NUM = ERF_P[5] * Y_SQ;
        X_DEN = Y_SQ;
        for (int i=0;i<4; i++)
        {
          X_NUM = (X_NUM + ERF_P[i]) * Y_SQ;
          X_DEN = (X_DEN + ERF_Q[i]) * Y_SQ;
        }
        result = Y_SQ * (X_NUM + ERF_P[4]) / (X_DEN + ERF_Q[4]);
        result = (PI_SQRT - result) / Y;
        if(jint != 2)
        {
          Y_SQ = Math.round(Y*16.0D)/16.0D;
          double del = (Y - Y_SQ) * (Y + Y_SQ);
          result = Math.exp(-Y_SQ * Y_SQ) * Math.exp(-del) * result;
        }
      }
    }

    if(jint == 0)
    {
      result = (0.5D - result) + 0.5D;
      if(X < 0) result = -result;
    }
    else if(jint == 1)
    {
      if(X < 0) result = 2.0D - result;
    }
    else
    {
      if(X < 0)
      {
        if(X < X_NEG) result = X_INF;
        else
        {
          Y_SQ = Math.round(X*16.0D)/16.0D;
          double del = (X - Y_SQ) * (X + Y_SQ);
          Y = Math.exp(Y_SQ * Y_SQ) * Math.exp(del);
          result = (Y + Y) - result;
        }
      }
    }
    return result;
  }

  public static double erf(double d){ return calerf(d, 0); }
  public static double erfc(double d){ return calerf(d, 1); }
  public static double erfcx(double d){ return calerf(d, 2); }

/* ****************************************************
 * Refining algorytm is based on Halley rational method
 * for finding roots of equations as described at:
 * http://www.math.uio.no/~jacklam/notes/invnorm/index.html
 * by:
 *  Peter J. Acklam
 *  jacklam@math.uio.no
 * ************************************************** */
  public static double refine(double x, double d)
  {
    if( d > 0 && d < 1)
    {
      double e = 0.5D * erfc(-x/Math.sqrt(2.0D)) - d;
      double u = e * Math.sqrt(2.0D*Math.PI) * Math.exp((x*x)/2.0D);
      x = x - u/(1.0D + x*u/2.0D);
    }
    return x;
  }
}
