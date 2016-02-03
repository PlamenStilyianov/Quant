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
Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
Copyright (C) 2003 Ferdinando Ametrano
Copyright (C) 2005 StatPro Italia srl
Copyright (C) 2008 John Maiden

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

package org.jquantlib.experimental.lattices;

import org.jquantlib.QL;
import org.jquantlib.processes.StochasticProcess1D;

/**
 * Extended Joshi4 approach
 *
 * @category lattices
 *
 * @author Richard Gomes
 */
public class ExtendedJoshi4 extends ExtendedBinomialTree /*<T>*/ {


   //
   // protected fields
   //

   private final /*@Time*/ double  end;
   private final /*@Size*/ int oddSteps;
   private final double strike;
   private final double up;
   private final double down;
   private final double pu;
   private final double pd;


   //
   // public methods
   //

   public ExtendedJoshi4(
           final StochasticProcess1D process,
           final /* @Time */ double end,
           final int steps,
           final double strike) {

       super(process, end, (steps%2!=0 ? steps : steps+1));
       this.end = end;
       this.oddSteps = steps%2!=0 ? steps : steps+1;
       this.strike = strike;

        QL.require(strike>0.0, "strike must be positive");
        /*@Real*/ final double variance = process.variance(0.0, x0, end);

        /*@Real*/ final double ermqdt = Math.exp(driftStep(0.0) + 0.5*variance/oddSteps);
        /*@Real*/ final double d2 = (Math.log(x0/strike) + driftStep(0.0)*oddSteps ) / Math.sqrt(variance);

        pu = computeUpProb((oddSteps-1.0)/2.0,d2 );
        pd = 1.0 - pu;
        /*@Real*/ final double pdash = computeUpProb((oddSteps-1.0)/2.0,d2+Math.sqrt(variance));
        up = ermqdt * pdash / pu;
        down = (ermqdt - pu * up) / (1.0 - pu);

       QL.require(pu<=1.0, NEGATIVE_PROBABILITY);
       QL.require(pu>=0.0, NEGATIVE_PROBABILITY);
   }

   @Override
   public double underlying(final int i, final int index) /* @ReadOnly */ {
       final /*@Time*/ double stepTime = i*dt;
       final /*@Real*/ double variance = treeProcess.variance(stepTime, x0, end);
       final /*@Real*/ double ermqdt = Math.exp(driftStep(stepTime) + 0.5*variance/oddSteps);
       final /*@Real*/ double d2 = (Math.log(x0/strike) + driftStep(stepTime)*oddSteps ) / Math.sqrt(variance);

       final /*@Real*/ double pu = computeUpProb((oddSteps-1.0)/2.0,d2 );
       final /*@Real*/ double pdash = computeUpProb((oddSteps-1.0)/2.0,d2+Math.sqrt(variance));
       final /*@Real*/ double up = ermqdt * pdash / pu;
       final /*@Real*/ double down = (ermqdt - pu * up) / (1.0 - pu);

       return x0 * Math.pow(down, i-index) * Math.pow(up, index);
   }

   @Override
   public double probability(final int i, final int ref, final int branch) /* @ReadOnly */ {
       final /*@Time*/ double stepTime = i*dt;
       final /*@Real*/ double variance = treeProcess.variance(stepTime, x0, end);
       final /*@Real*/ double d2 = (Math.log(x0/strike) + driftStep(stepTime)*oddSteps ) / Math.sqrt(variance);

       final /*@Real*/ double pu = computeUpProb((oddSteps-1.0)/2.0,d2 );
       final /*@Real*/ double pd = 1.0 - pu;

       return (branch == 1 ? pu : pd);
   }


   private double computeUpProb(final double k, final double dj) /* @ReadOnly */ {
       final /*@Real*/ double alpha = dj/(Math.sqrt(8.0));
       final /*@Real*/ double alpha2 = alpha*alpha;
       final /*@Real*/ double alpha3 = alpha*alpha2;
       final /*@Real*/ double alpha5 = alpha3*alpha2;
       final /*@Real*/ double alpha7 = alpha5*alpha2;
       final /*@Real*/ double beta = -0.375*alpha-alpha3;
       final /*@Real*/ double gamma = (5.0/6.0)*alpha5 + (13.0/12.0)*alpha3 +(25.0/128.0)*alpha;
       final /*@Real*/ double delta = -0.1025 *alpha- 0.9285 *alpha3 -1.43 *alpha5 -0.5 *alpha7;
       /*@Real*/ double p =0.5;
       final /*@Real*/ double rootk= Math.sqrt(k);
       p+= alpha/rootk;
       p+= beta /(k*rootk);
       p+= gamma/(k*k*rootk);
       // delete next line to get results for j three tree
       p+= delta/(k*k*k*rootk);
       return p;
   }

}
