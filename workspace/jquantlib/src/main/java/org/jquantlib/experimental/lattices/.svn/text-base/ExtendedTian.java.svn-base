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
 * Tian tree: third moment matching, multiplicative approach
 *
 * @category lattices
 *
 * @author Richard Gomes
 */
public class ExtendedTian extends ExtendedBinomialTree /*<T>*/ {


   //
   // protected fields
   //

   private final double up_;
   private final double down_;
   private final double pu_;
   private final double pd_;


   //
   // public methods
   //

   public ExtendedTian(
           final StochasticProcess1D process,
           final /* @Time */ double end,
           final int steps) {

       super(process, end, steps);
       final /*@Real*/ double q = Math.exp(process.variance(0.0, x0, dt));
       final /*@Real*/ double r = Math.exp(driftStep(0.0))*Math.sqrt(q);

       this.up_ = 0.5 * r * q * (q + 1 + Math.sqrt(q * q + 2 * q - 3));
       this.down_ = 0.5 * r * q * (q + 1 - Math.sqrt(q * q + 2 * q - 3));

       this.pu_ = (r - down_) / (up_ - down_);
       this.pd_ = 1.0 - pu_;

       // ::::: This comment came from QuantLib/C++ :::::
       // doesn't work
       //            treeCentering_ = (up_+down_)/2.0;
       //            up_ = up_-treeCentering_;

       QL.require(pu_<=1.0, NEGATIVE_PROBABILITY);
       QL.require(pu_>=0.0, NEGATIVE_PROBABILITY);
   }

   @Override
   public double underlying(final int i, final int index) /* @ReadOnly */ {
       /*@Time*/ final double stepTime = i*dt;
       /*@Real*/ final double q = Math.exp(treeProcess.variance(stepTime, x0, dt));
       /*@Real*/ final double r = Math.exp(driftStep(stepTime))*Math.sqrt(q);

       /*@Real*/ final double up = 0.5 * r * q * (q + 1 + Math.sqrt(q * q + 2 * q - 3));
       /*@Real*/ final double down = 0.5 * r * q * (q + 1 - Math.sqrt(q * q + 2 * q - 3));

       return x0 * Math.pow(down, i-index) * Math.pow(up, index);
   }

   @Override
   public double probability(final int i, final int ref, final int branch) /* @ReadOnly */ {
       /*@Time*/ final double stepTime = i*dt;
       /*@Real*/ final double q = Math.exp(treeProcess.variance(stepTime, x0, dt));
       /*@Real*/ final double r = Math.exp(driftStep(stepTime))*Math.sqrt(q);

       /*@Real*/ final double up = 0.5 * r * q * (q + 1 + Math.sqrt(q * q + 2 * q - 3));
       /*@Real*/ final double down = 0.5 * r * q * (q + 1 - Math.sqrt(q * q + 2 * q - 3));

       /*@Real*/ final double pu = (r - down) / (up - down);
       /*@Real*/ final double pd = 1.0 - pu;

       return (branch == 1 ? pu : pd);
   }

}
