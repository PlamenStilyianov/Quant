/*
 Copyright (C) 2009 Apratim Rajendra

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

package org.jquantlib.samples;

import org.jquantlib.QL;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.math.matrixutilities.Matrix;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.termstructures.BlackVarianceTermStructure;
import org.jquantlib.termstructures.BlackVolTermStructure;
import org.jquantlib.termstructures.BlackVolatilityTermStructure;
import org.jquantlib.termstructures.LocalVolTermStructure;
import org.jquantlib.termstructures.volatilities.BlackConstantVol;
import org.jquantlib.termstructures.volatilities.BlackVarianceCurve;
import org.jquantlib.termstructures.volatilities.BlackVarianceSurface;
import org.jquantlib.termstructures.volatilities.ImpliedVolTermStructure;
import org.jquantlib.termstructures.volatilities.LocalConstantVol;
import org.jquantlib.termstructures.volatilities.LocalVolCurve;
import org.jquantlib.termstructures.volatilities.BlackVarianceSurface.Extrapolation;
import org.jquantlib.time.Date;
import org.jquantlib.time.calendars.UnitedStates;
import org.jquantlib.time.calendars.UnitedStates.Market;

/**
 *
 * This class explores the functionalities provided by volatility termstructures.The different types
 * of volatilities termstructures covered in this class are as shown below-
 * (1)BlackConstantVol
 * (2)BlackVarianceCurve
 * (3)BlackVarianceSurface
 * (4)ImpliedVolTermStructure
 * (5)LocalConstantVol
 * (6)LocalVolCurve
 * (7)LocalVolSurface
 *
 * @author Apratim Rajendra
 *
 */
public class VolatilityTermStructures implements Runnable {

    public static void main(final String[] args) {
        new VolatilityTermStructures().run();
    }

    public void run() {

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();

        final Date today  = Date.todaysDate();
        final Date date10 = today.clone().addAssign(10);
        final Date date12 = today.clone().addAssign(12);
        final Date date15 = today.clone().addAssign(15);
        final Date date16 = today.clone().addAssign(16);
        final Date date17 = today.clone().addAssign(17);
        final Date date20 = today.clone().addAssign(20);
        final Date date22 = today.clone().addAssign(22);
        final Date date25 = today.clone().addAssign(25);
        final Date date26 = today.clone().addAssign(26);
        final Date date27 = today.clone().addAssign(27);
        final Date date30 = today.clone().addAssign(30);
        final Date date32 = today.clone().addAssign(32);
        final Date date35 = today.clone().addAssign(35);
        final Date date37 = today.clone().addAssign(37);
        final Date date40 = today.clone().addAssign(40);
        final Date date50 = today.clone().addAssign(50);


        System.out.println("//===============================BlackConstantVol termstructure==================================");

        //Let's explore BlackConstantVolatility by calculating blackVolatility,blackForwardVolatility,blackVariance and blackForwardVariance
        //Following example explains that when volatility is assumed to be constant BlackConstantVol termstructure can be used to represent
        //such a volatility termstructure.
        final SimpleQuote volatilityQuote = new SimpleQuote(0.3);
        final RelinkableHandle<Quote>  handleToVolatilityQuote = new RelinkableHandle<Quote>(volatilityQuote);
        BlackVolatilityTermStructure constantVolatility = new BlackConstantVol(2,new UnitedStates(Market.NYSE),handleToVolatilityQuote, new Actual365Fixed());

        //Calculating blackVolatility using maturity as 10 days after today and strike as 20
        Double volatility1 = constantVolatility.blackVol(date10.clone(), 20);
        System.out.println("BlackVolatility = "+volatility1);

        //Calculating blackVolatility using maturity as 20 days after today and strike as 30
        Double volatility2 = constantVolatility.blackVol(date20.clone(), 30);
        System.out.println("BlackVolatility = "+volatility2);

        //Calculating blackVolatility using maturity as 30 days after today and strike as 40
        Double volatility3 = constantVolatility.blackVol(date30.clone(), 40);
        System.out.println("BlackVolatility = "+volatility3);

        //The volatilities calculated above are same as it's constant volatility termstructure
        if(volatility1.equals(volatility2) && volatility2.equals(volatility3)){
            System.out.println("All the volatilities calculated above are same and = "+volatility1);
        }

        //Calculating blackForwardVolatility between 10 days after today and 15 days after today with strike as 20
        Double forwardVolatility1 = constantVolatility.blackForwardVol(date10.clone(), date15.clone(), 20, true);
        System.out.println("BlackForwardVolatility = "+forwardVolatility1);

        //Calculating blackForwardVolatility between 20 days after today and 25 days after today with strike as 40
        Double forwardVolatility2 = constantVolatility.blackForwardVol(date10.clone(), date15.clone(), 20, true);
        System.out.println("BlackForwardVolatility = "+forwardVolatility2);

        //Calculating blackForwardVolatility between 27 days after today and 35 days after today with strike as 60
        Double forwardVolatility3 = constantVolatility.blackForwardVol(date27.clone(), date35.clone(), 60, true);
        System.out.println("BlackForwardVolatility = "+forwardVolatility3);

        //The volatilities calculated above are same as it's constant volatility termstructure
        if(forwardVolatility1.equals(forwardVolatility2) && forwardVolatility2.equals(forwardVolatility3)){
            System.out.println("All the forward volatilities calculated above are same and = "+forwardVolatility1);
        }else{
            System.out.println("The forward volatilities may not be constant");
        }

        //Calculating blackVariance
        System.out.println("BlackVariance = "+constantVolatility.blackVariance(date10.clone(), 20));

        //Calculating blackForwardVariance
        System.out.println("BlackForwardVariance = "+constantVolatility.blackForwardVariance(date10.clone(), date15.clone(), 20, true));

        //As BlackConstantVol termstructure has been initialized using relinkable handle so lets change the observable SimpleQuote of this handle
        //and see the change getting reflected to all the calculations done above.
        volatilityQuote.setValue(0.04) ;
        constantVolatility = new BlackConstantVol(2,new UnitedStates(Market.NYSE),handleToVolatilityQuote, new Actual365Fixed());

        //Calculating blackVolatility using maturity as 10 days after today and strike as 20
        volatility1 = constantVolatility.blackVol(date10.clone(), 20);
        System.out.println("BlackVolatility = "+volatility1);

        //Calculating blackVolatility using maturity as 20 days after today and strike as 30
        volatility2 = constantVolatility.blackVol(date20.clone(), 30);
        System.out.println("BlackVolatility = "+volatility2);

        volatility3 = constantVolatility.blackVol(date30.clone(), 40);
        System.out.println("BlackVolatility = "+volatility3);

        //The volatilities calculated above are same as it's constant volatility termstructure
        if(volatility1.equals(volatility2) && volatility2.equals(volatility3)){
            System.out.println("All the volatilities calculated above are same and = "+volatility1);
        }

        //Calculating blackForwardVolatility between 10 days after today and 15 days after today with strike as 20
        forwardVolatility1 = constantVolatility.blackForwardVol(date10.clone(), date15.clone(), 20, true);
        System.out.println("BlackForwardVolatility = "+forwardVolatility1);

        //Calculating blackForwardVolatility between 20 days after today and 25 days after today with strike as 40
        forwardVolatility2 = constantVolatility.blackForwardVol(date10.clone(), date15.clone(), 20, true);
        System.out.println("BlackForwardVolatility = "+forwardVolatility2);

        //Calculating blackForwardVolatility between 27 days after today and 35 days after today with strike as 60
        forwardVolatility3 = constantVolatility.blackForwardVol(date27.clone(), date35.clone(), 60, true);
        System.out.println("BlackForwardVolatility = "+forwardVolatility3);

        //The volatilities calculated above are same as it's constant volatility termstructure
        if(forwardVolatility1.equals(forwardVolatility2) && forwardVolatility2.equals(forwardVolatility3)){
            System.out.println("All the volatilities calculated above are same and = "+forwardVolatility1);
        }else{
            System.out.println("The forward volatilities may not be constant");
        }

        //Calculating blackVariance
        System.out.println("BlackVariance = "+constantVolatility.blackVariance(date10.clone(), 20));

        //Calculating blackForwardVariance
        System.out.println("BlackForwardVariance = "+constantVolatility.blackForwardVariance(date10.clone(), date15.clone(), 20, true));

        System.out.println("//===============================BlackVarianceCurve================================");

        //Let's create black variance curve and calculate volatilities/variances by interpolating on
        //the created curve for a given strike.

        //-- Date today = DateFactory.getFactory().getTodaysDate();

        //Following is the time axis
        final Date[] dates = {date10.clone(), date15.clone(), date20.clone(), date25.clone(), date30.clone(), date40.clone() };

        //Following is the volatility axis
        final double[] volatilities = {0.1,0.2,0.3,0.4,0.5,0.6};

        //Following is the curve
        final BlackVarianceTermStructure varianceCurve = new BlackVarianceCurve(today,dates,volatilities, new Actual365Fixed(), false);
        ((BlackVarianceCurve)varianceCurve).setInterpolation();

        //Calculating blackVolatility using maturity as 12 days after today and strike as 20
        volatility1 = varianceCurve.blackVol(date12.clone(), 20);
        System.out.println("Interpolated BlackVolatility on BlackVarianceCurve = "+volatility1);

        //Calculating blackVolatility using maturity as 22 days after today and strike as 30
        volatility2 = varianceCurve.blackVol(date22.clone(), 30);
        System.out.println("Interpolated BlackVolatility on BlackVarianceCurve = "+volatility2);

        //Calculating blackVolatility using maturity as 32 days after today and strike as 40
        volatility3 = varianceCurve.blackVol(date32.clone(), 40);
        System.out.println("Interpolated BlackVolatility on BlackVarianceCurve = "+volatility3);


        //Calculating blackForwardVolatility between 12 days after today and 16 days after today with strike as 20
        forwardVolatility1 = varianceCurve.blackForwardVol(date12.clone(), date16.clone(), 20, true);
        System.out.println("Interpolated BlackForwardVolatility on BlackVarianceCurve = "+forwardVolatility1);

        //Calculating blackForwardVolatility between 22 days after today and 26 days after today with strike as 40
        forwardVolatility2 = varianceCurve.blackForwardVol(date22.clone(), date26.clone(), 40, true);
        System.out.println("Interpolated BlackForwardVolatility on BlackVarianceCurve = "+forwardVolatility2);

        //Calculating blackForwardVolatility between 27 days after today and 35 days after today with strike as 60
        forwardVolatility3 = varianceCurve.blackForwardVol(date27.clone(), date35.clone(), 60, true);
        System.out.println("Interpolated BlackForwardVolatility on BlackVarianceCurve = "+forwardVolatility3);


        //Calculating blackVariance using maturity as 12 days after today and strike as 20
        System.out.println("Interpolated BlackVariance on BlackVarianceCurve = "+varianceCurve.blackVariance(date12.clone(), 20));

        //Calculating blackForwardVariance between 12 days after today and 16 days after today with strike as 20
        System.out.println("Interpolated BlackForwardVariance on BlackVarianceCurve = "+varianceCurve.blackForwardVariance(date12.clone(), date16.clone(), 20, true));

        System.out.println("//===============================BlackVarianceSurface================================");

        //Let's create black variance surface and calculate volatilities/variances by interpolating on
        //the created curve for a given strike.

        //-- today = DateFactory.getFactory().getTodaysDate();

        //Following is the time axis
        final Date[] datesAxis = {date10.clone(), date15.clone(), date20.clone(), date25.clone(), date30.clone(), date40.clone() };

        final Array strikeAxis = new Array(new double[] {10,20,35,40,56,60});

        //Following is the volatility surface on which interpolations will be done
        final Matrix volatilityMatrix = new Matrix(new double[][] {
                {0.01,0.02,0.03,0.04,0.05,0.06},
                {0.02,0.03,0.04,0.05,0.06,0.07},
                {0.03,0.04,0.05,0.06,0.07,0.08},
                {0.3,0.5,0.6,0.7,0.8,0.9},
                {0.1,0.4,0.6,0.7,0.8,0.9},
                {0.2,0.5,0.6,0.7,0.8,0.9}
        });


        //Following is the variance surface where variance = f(strike,maturity) and f = function
        final BlackVarianceTermStructure varianceSurface = new BlackVarianceSurface(
                today, datesAxis,
                strikeAxis, volatilityMatrix, new Actual365Fixed(),
                Extrapolation.InterpolatorDefaultExtrapolation,
                Extrapolation.InterpolatorDefaultExtrapolation);
        ((BlackVarianceSurface)varianceSurface).setInterpolation(null);

        //As the surface has been set up to do interpolations so let's start calculating the volatilities for strikes
        //and maturities lying between the points as mentioned by strikesAxis and dateAxis.
        //Calculating blackVolatility using maturity as 12 days after today and strike as 18
        volatility1 = varianceSurface.blackVol(date12.clone(), 18);
        System.out.println("Interpolated BlackVolatility on BlackVarianceSurface = "+volatility1);

        //Calculating blackVolatility using maturity as 22 days after today and strike as 33
        volatility2 = varianceSurface.blackVol(date22.clone(), 33);
        System.out.println("Interpolated BlackVolatility on BlackVarianceSurface = "+volatility2);

        //Calculating blackVolatility using maturity as 32 days after today and strike as 45
        volatility3 = varianceSurface.blackVol(date32.clone(), 45);
        System.out.println("Interpolated BlackVolatility on BlackVarianceSurface = "+volatility3);


        //Calculating blackForwardVolatility between 12 days after today and 16 days after today with strike as 20
        forwardVolatility1 = varianceSurface.blackForwardVol(date12.clone(), date16.clone(), 20, true);
        System.out.println("Interpolated BlackForwardVolatility on BlackVarianceSurface = "+forwardVolatility1);

        //Calculating blackForwardVolatility between 22 days after today and 26 days after today with strike as 40
        forwardVolatility2 = varianceSurface.blackForwardVol(date22.clone(), date26.clone(), 40, true);
        System.out.println("Interpolated BlackForwardVolatility on BlackVarianceSurface = "+forwardVolatility2);

        //Calculating blackForwardVolatility between 27 days after today and 35 days after today with strike as 50
        forwardVolatility3 = varianceSurface.blackForwardVol(date27.clone(), date35.clone(), 50, true);
        System.out.println("Interpolated BlackForwardVolatility on BlackVarianceSurface = "+forwardVolatility3);


        //Calculating blackVariance using maturity as 12 days after today and strike as 20
        System.out.println("Interpolated BlackVariance on BlackVarianceSurface = "+varianceSurface.blackVariance(date12.clone(), 20));

        //Calculating blackForwardVariance between 12 days after today and 16 days after today with strike as 20
        System.out.println("Interpolated BlackForwardVariance on BlackVarianceSurface = "+varianceSurface.blackForwardVariance(date12.clone(), date16.clone(), 20, true));

        System.out.println("//================================ImpliedVolTermStructure=============================");

        //As mentioned in the java docs the implied volatility termstructure remains linked to
        //the underlying termstructure and changes to same are linked to ImpliedVolTermStructure
        //as well.

        //Lets use underlying as varianceCurve defined above by creating a relinkable handle as shown below
        final RelinkableHandle<BlackVolTermStructure> varianceCurveHandle = new RelinkableHandle<BlackVolTermStructure>(varianceCurve);
        final BlackVarianceTermStructure impliedVolTermStructure = new ImpliedVolTermStructure(varianceCurveHandle, today);

        //Calculating blackVolatility using maturity as 12 days after today and strike as 20
        volatility1 = varianceCurve.blackVol(date12.clone(), 20);
        final double impliedVolatility1 = impliedVolTermStructure.blackVol(date12.clone(), 20);

        if(volatility1 == impliedVolatility1){
            System.out.println("Interpolated BlackVolatility on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+volatility1);
        }

        //Calculating blackVolatility using maturity as 22 days after today and strike as 30
        volatility2 = varianceCurve.blackVol(date22.clone(), 30);
        final double impliedVolatility2 = impliedVolTermStructure.blackVol(date22.clone(), 30);
        if(volatility2 == impliedVolatility2){
            System.out.println("Interpolated BlackVolatility on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+volatility2);
        }

        //Calculating blackVolatility using maturity as 32 days after today and strike as 40
        volatility3 = varianceCurve.blackVol(date32.clone(), 40);
        final double impliedVolatility3 = impliedVolTermStructure.blackVol(date32.clone(), 40);
        if(volatility3 == impliedVolatility3){
            System.out.println("Interpolated BlackVolatility on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+volatility3);
        }


        //Calculating blackForwardVolatility between 12 days after today and 16 days after today with strike as 20
        forwardVolatility1 = varianceCurve.blackForwardVol(date12.clone(), date16.clone(), 20, true);
        final double impliedForwardVolatility1 = impliedVolTermStructure.blackForwardVol(date12.clone(), date16.clone(), 20, true);
        if(forwardVolatility1 == impliedForwardVolatility1){
            System.out.println("Interpolated BlackForwardVolatility on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+forwardVolatility1);
        }

        //Calculating blackForwardVolatility between 22 days after today and 26 days after today with strike as 40
        forwardVolatility2 = varianceCurve.blackForwardVol(date22.clone(), date26.clone(), 40, true);
        final double impliedForwardVolatility2 = impliedVolTermStructure.blackForwardVol(date22.clone(), date26.clone(), 40, true);
        if(forwardVolatility2 == impliedForwardVolatility2){
            System.out.println("Interpolated BlackForwardVolatility on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+forwardVolatility2);
        }

        //Calculating blackForwardVolatility between 27 days after today and 35 days after today with strike as 60
        forwardVolatility3 = varianceCurve.blackForwardVol(date27.clone(), date35.clone(), 60, true);
        final double impliedForwardVolatility3 = impliedVolTermStructure.blackForwardVol(date27.clone(), date35.clone(), 60, true);
        if(forwardVolatility3 == impliedForwardVolatility3){
            System.out.println("Interpolated BlackForwardVolatility on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+forwardVolatility3);
        }


        //Calculating blackVariance using maturity as 12 days after today and strike as 20
        final double variance = varianceCurve.blackVariance(date12.clone(), 20);
        final double impliedVariance = impliedVolTermStructure.blackVariance(date12.clone(), 20);
        if(variance == impliedVariance){
            System.out.println("Interpolated BlackVariance on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+varianceCurve.blackVariance(date12.clone(), 20));
        }

        //Calculating blackForwardVariance between 12 days after today and 16 days after today with strike as 20
        final double forwardVariance = varianceCurve.blackForwardVariance(date12.clone(), date16.clone(), 20, true);
        final double impliedForwardVariance = impliedVolTermStructure.blackForwardVariance(date12.clone(), date16.clone(), 20, true);
        if(forwardVariance == impliedForwardVariance){
            System.out.println("Interpolated BlackForwardVariance on BlackVarianceCurve is same for varianceCurve and ImpliedVolTermStructure derived on it and = "+varianceCurve.blackForwardVariance(date12.clone(), date16.clone(), 20, true));
        }

        System.out.println("//================================LocalConstantVol=======================================");

        //LocalConstantVolatility is essentially same as BlackConstantVol and is a local volatility version of BlackConstantVol

        //Let's set the quoteValue = 0.05 and use the constantVolatility a BlackConstantVol
        volatilityQuote.setValue(0.05);
        final LocalVolTermStructure localConstantVolatility = new LocalConstantVol(2,new UnitedStates(Market.NYSE),handleToVolatilityQuote, new Actual365Fixed());

        //Calculating blackVolatility using maturity as 10 days after today and strike as 20
        if(constantVolatility.blackVol(date10.clone(), 20) == localConstantVolatility.localVol(date10.clone(), 20,true)){
            System.out.println("BlackVolatility and LocalVolatility are same and are = "+localConstantVolatility.localVol(date10.clone(), 20,true));
        }

        //Calculating blackVolatility using maturity as 20 days after today and strike as 30
        if(constantVolatility.blackVol(date20.clone(), 30) == localConstantVolatility.localVol(date20.clone(), 30,true)){
            System.out.println("BlackVolatility and LocalVolatility are same and are = "+localConstantVolatility.localVol(date20.clone(), 30,true));
        }

        //Calculating blackVolatility using maturity as 30 days after today and strike as 40
        if(constantVolatility.blackVol(date30.clone(), 40) == localConstantVolatility.localVol(date30.clone(), 40,true)){
            System.out.println("BlackVolatility and LocalVolatility are same and are = "+localConstantVolatility.localVol(date30.clone(), 40,true));
        }

        System.out.println("//================================LocalVolCurve==========================================");

        //LocalVolatility curve wraps BlackVarianceCurve and uses it to calculate the interpolated local volatility
        final LocalVolTermStructure localVolatilityCurve = new LocalVolCurve(new RelinkableHandle<BlackVarianceCurve>((BlackVarianceCurve)varianceCurve));

        //Calculating blackVolatility using maturity as 12 days after today and strike as 20
        volatility1 = localVolatilityCurve.localVol(date12.clone(), 20,true);
        System.out.println("Interpolated BlackVolatility on LocalVolCurve = "+volatility1);

        //Calculating blackVolatility using maturity as 22 days after today and strike as 30
        volatility2 = localVolatilityCurve.localVol(date22.clone(), 30,true);
        System.out.println("Interpolated BlackVolatility on LocalVolCurve = "+volatility2);

        //Calculating blackVolatility using maturity as 32 days after today and strike as 40
        volatility3 = localVolatilityCurve.localVol(date32.clone(), 40,true);
        System.out.println("Interpolated BlackVolatility on LocalVolCurve = "+volatility3);


        //TODO
        // System.out.println("//================================LocalVolSurface========================================");

        clock.stopClock();
        clock.log();
    }

}
