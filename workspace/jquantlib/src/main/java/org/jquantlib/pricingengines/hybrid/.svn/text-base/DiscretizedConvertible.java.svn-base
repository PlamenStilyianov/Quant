/*
 Copyright (C) 2010 Zahid Hussain

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
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
 Copyright (C) 2005, 2006 Theo Boafo
 Copyright (C) 2006, 2007 StatPro Italia srl

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

package org.jquantlib.pricingengines.hybrid;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.cashflow.Callability;
import org.jquantlib.cashflow.Dividend;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.Exercise;
import org.jquantlib.instruments.DiscretizedAsset;
import org.jquantlib.instruments.bonds.ConvertibleBondOption;
import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.Constants;
import org.jquantlib.math.matrixutilities.Array;
import org.jquantlib.processes.GeneralizedBlackScholesProcess;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.TimeGrid;
import org.jquantlib.util.Std;

/**
 * Discretized Convertible
 * 
 * @author Zahid Hussain
 *
 */
@QualityAssurance(quality=Quality.Q3_DOCUMENTATION, version=Version.V097, reviewers="Richard Gomes")
public class DiscretizedConvertible extends DiscretizedAsset {

    //
    // protected fields
    //
    
    protected Array conversionProbability;
    protected Array spreadAdjustedRate;
    protected Array dividendValues;

    //
    // private fields
    //
    
    private final ConvertibleBondOption.ArgumentsImpl arguments;
    private final GeneralizedBlackScholesProcess process;
    private final List< /* @Time */ Double > stoppingTimes;
    private final List< /* @Time */ Double > callabilityTimes;
    private final List< /* @Time */ Double > couponTimes;
    private final List< /* @Time */ Double > dividendTimes;


    //
    // public constructors
    //
    
    public DiscretizedConvertible(final ConvertibleBondOption.ArgumentsImpl arguments,
                                  final GeneralizedBlackScholesProcess process,
                                  final TimeGrid grid) {
        this.arguments = arguments;
        this.process = process; 

       dividendValues = new Array(this.arguments.dividends.size()).fill(0.0);

       final Date settlementDate = this.process.riskFreeRate().currentLink().referenceDate();
       for (int i=0; i<this.arguments.dividends.size(); i++) {
           if (this.arguments.dividends.get(i).date().ge(settlementDate)) {
               final double value =  this.arguments.dividends.get(i).amount() *
                                this.process.riskFreeRate().currentLink().discount(
                                            this.arguments.dividends.get(i).date());
               dividendValues.set(i, value);
           }
       }

       final DayCounter dayCounter = this.process.riskFreeRate().currentLink().dayCounter();
       final Date bondSettlement = this.arguments.settlementDate;

       stoppingTimes = new ArrayList<Double>(this.arguments.exercise.dates().size());
       for (int i=0; i<this.arguments.exercise.dates().size(); ++i)
           stoppingTimes.add(dayCounter.yearFraction(bondSettlement,
                                       this.arguments.exercise.date(i)));

       callabilityTimes = new ArrayList<Double>(this.arguments.callabilityDates.size());
       for (int i=0; i<this.arguments.callabilityDates.size(); ++i)
           callabilityTimes.add(dayCounter.yearFraction(bondSettlement,
                                       this.arguments.callabilityDates.get(i)));

       couponTimes = new ArrayList<Double>(this.arguments.couponDates.size());
       for (int i=0; i<this.arguments.couponDates.size(); ++i)
           couponTimes.add(dayCounter.yearFraction(bondSettlement,
                                       this.arguments.couponDates.get(i)));

       dividendTimes = new ArrayList<Double>(this.arguments.dividendDates.size());
       for (int i=0; i<this.arguments.dividendDates.size(); ++i)
           dividendTimes.add(dayCounter.yearFraction(bondSettlement,
                                       this.arguments.dividendDates.get(i)));

       if (!grid.empty()) {
           // adjust times to grid
           for (int i=0; i<stoppingTimes.size(); i++)
               stoppingTimes.set(i, grid.closestTime(stoppingTimes.get(i)));
           for (int i=0; i<couponTimes.size(); i++)
               couponTimes.set(i, grid.closestTime(couponTimes.get(i)));
           for (int i=0; i<callabilityTimes.size(); i++)
               callabilityTimes.set(i, grid.closestTime(callabilityTimes.get(i)));
           for (int i=0; i<dividendTimes.size(); i++)
               dividendTimes.set(i,grid.closestTime(dividendTimes.get(i)));
       }
   }


    //
    // public final methods
    //
    
    public final Array conversionProbability()                  { return conversionProbability; }
    public final Array spreadAdjustedRate()                     { return spreadAdjustedRate; }
    public final Array dividendValues()                         { return dividendValues; }
    public final void setConversionProbability(final Array a)   { conversionProbability = a; }
    public final void setSpreadAdjustedRate(final Array a)      { spreadAdjustedRate = a; }
    public final void setDividendValues(final Array a)          { dividendValues = a; }
    
    
    //
    // public methods
    //
    
    @Override
    public List<Double> mandatoryTimes() {
        
        final List<Double> result = new ArrayList<Double>();
        Std.copy(stoppingTimes, 0, stoppingTimes.size(), result);
        Std.copy(callabilityTimes, 0, callabilityTimes.size(),result);
        Std.copy(couponTimes, 0, couponTimes.size(), result);
        return result;
    }

    public void applyConvertibility() {
        final Array grid = adjustedGrid();
        for (int j=0; j<values_.size(); j++) {
            final double payoff = this.arguments.conversionRatio * grid.get(j);
            if (values_.get(j) <= payoff) {
                values_.set(j, payoff);
                conversionProbability.set(j, 1.0);
            }
        }
    }

    public void applyCallability(final int i, final boolean convertible) {
        int  j;
        final Array grid = adjustedGrid();
        final Callability.Type Call = Callability.Type.Call;
        final Callability.Type Put = Callability.Type.Put;
        switch (this.arguments.callabilityTypes.get(i)) {
          case Call:
            if (this.arguments.callabilityTriggers.get(i) != Constants.NULL_REAL) {
                final double conversionValue =
                    this.arguments.redemption/this.arguments.conversionRatio;
                final double trigger =
                    conversionValue*this.arguments.callabilityTriggers.get(i);
                for (j=0; j<values_.size(); j++) {
                    // the callability is conditioned by the trigger...
                    if (grid.get(j) >= trigger) {
                        // ...and might trigger conversion
                        values_.set(j, 
                            Math.min(Math.max(this.arguments.callabilityPrices.get(i),
                                              this.arguments.conversionRatio*grid.get(j)),
                                     values_.get(j)));
                    }
                }
            } else if (convertible) {
                for (j=0; j<values_.size(); j++) {
                    // exercising the callability might trigger conversion
                    values_.set(j, 
                        Math.min(Math.max(this.arguments.callabilityPrices.get(i),
                                          this.arguments.conversionRatio*grid.get(j)),
                                 values_.get(j)));
                }
            } else {
                for (j=0; j<values_.size(); j++) {
                    values_.set(j, Math.min(this.arguments.callabilityPrices.get(j),
                                          values_.get(j)));
                }
            }
            break;
          case Put:
            for (j=0; j<values_.size(); j++) {
                values_.set(j, Math.max(values_.get(j),
                                      this.arguments.callabilityPrices.get(i)));
            }
            break;
          default:
            QL.error("unknown callability type");
        }
    }

    public void addCoupon(final int i) {
        values_.addAssign(this.arguments.couponAmounts.get(i));
    }

    public Array adjustedGrid() {
        final double t = time();
        final Array grid = method().grid(t);
        // add back all dividend amounts in the future
        for (int i=0; i<this.arguments.dividends.size(); i++) {
            final double dividendTime = dividendTimes.get(i);
            if (dividendTime >= t || Closeness.isCloseEnough(dividendTime,t)) {
               final Dividend d = this.arguments.dividends.get(i);
                for (int j=0; j<grid.size(); j++) {
                    double v = grid.get(j);
                    v += d.amount(v);
                    grid.set(j,v);// += d->amount(grid[j]);
                }
            }
        }
        return grid;
    }

    
    //
    // overrides DiscretizedAsset
    //
    
    /**
     * This method should initialize the asset values to an {@link Array} of the
     * given size and with values depending on the particular asset.
     */
    @Override
    public void reset(final int size) {
            // Set to bond redemption values
            values_ = new Array(size).fill(this.arguments.redemption);

            // coupon amounts should be added when adjusting
            // values_ = Array(size, this.arguments.cashFlows.back()->amount());

            conversionProbability = new Array(size).fill(0.0);
            spreadAdjustedRate = new Array(size).fill(0.0);

            final DayCounter rfdc  = this.process.riskFreeRate().currentLink().dayCounter();

            // this takes care of convertibility and conversion probabilities
            adjustValues();

            final double creditSpread = this.arguments.creditSpread.currentLink().value();

            final Date exercise = this.arguments.exercise.lastDate();

            final double riskFreeRate =
                this.process.riskFreeRate().currentLink().zeroRate(exercise, rfdc,
                                                   Compounding.Continuous, Frequency.NoFrequency).rate();

            // Calculate blended discount rate to be used on roll back.
            for (int j=0; j<values_.size(); j++) {
               spreadAdjustedRate.set(j,
                   conversionProbability.get(j) * riskFreeRate +
                   (1-conversionProbability.get(j))*(riskFreeRate + creditSpread));
            }
    }

    @Override
    protected void postAdjustValuesImpl() {
        final Exercise.Type American = Exercise.Type.American;
        final Exercise.Type European = Exercise.Type.European;
        final Exercise.Type Bermudan = Exercise.Type.Bermudan;
        
        boolean convertible = false;
        switch (this.arguments.exercise.type()) {
          case American:
            if (time() <= stoppingTimes.get(1) && time() >= stoppingTimes.get(0))
                convertible = true;
            break;
          case European:
            if (isOnTime(stoppingTimes.get(0)))
                convertible = true;
            break;
          case Bermudan:
            for (int i=0; i<stoppingTimes.size(); ++i) {
                if (isOnTime(stoppingTimes.get(i)))
                    convertible = true;
            }
            break;
          default:
            QL.error("invalid option type");
        }

        for (int i=0; i<callabilityTimes.size(); i++) {
            if (isOnTime(callabilityTimes.get(i)))
                applyCallability(i,convertible);
        }

        for (int i=0; i<couponTimes.size(); i++) {
            if (isOnTime(couponTimes.get(i)))
                addCoupon(i);
        }

        if (convertible)
            applyConvertibility();
    }

}
