/*
 Copyright (C) 2008 Srinivas Hasti

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

package org.jquantlib.indexes;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.currencies.Currency;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.math.Constants;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.Observer;

/**
 *
 * @author Srinivas Hasti
 * @author Zahid Hussain
 *
 */
// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class InterestRateIndex extends Index implements Observer {

    protected String familyName;
    protected Period tenor;
    protected /*Natural*/ int fixingDays;
    protected Calendar fixingCalendar;
    protected Currency currency;
    protected DayCounter dayCounter;
    
    public InterestRateIndex(final String familyName,
            				 final Period tenor,
            				 final int fixingDays,
            				 final Currency currency,
            				 final Calendar fixingCalendar,
            				 final DayCounter dayCounter) {
        this.familyName = familyName;
        this.tenor = tenor;
        this.fixingDays = fixingDays;
        this.fixingCalendar = fixingCalendar;
        this.currency = currency;
        this.dayCounter = dayCounter;
        
        this.tenor.normalize();

        new Settings().evaluationDate().addObserver(this);
        IndexManager.getInstance().notifier(name()).addObserver(this);
    }
    
    @Override
    public String name() {
        final StringBuilder builder = new StringBuilder(familyName);
        if (tenor.equals(new Period(1,TimeUnit.Days))) {
            if (fixingDays == 0) {
                builder.append("ON");
            } else if (fixingDays == 1) {
                builder.append("TN");
            } else if (fixingDays == 2) {
                builder.append("SN");
            } else {
                builder.append(tenor.getShortFormat());
            }
        } else {
            builder.append(tenor.getShortFormat());
        }
        builder.append(' ').append(dayCounter.name());
        return builder.toString();
    }
    
    @Override
    public Calendar fixingCalendar() {
        return fixingCalendar;
    }

    @Override
    public boolean isValidFixingDate(final Date fixingDate) {
        return fixingCalendar.isBusinessDay(fixingDate);
    }

    public String familyName() {
        return familyName;
    }

    public Period tenor() {
        return tenor;
    }

    public int fixingDays() {
        return fixingDays;
    }


    public Currency currency() {
        return currency;
    }

    public DayCounter dayCounter() {
        return dayCounter;
    }


    //
    // protected abstract methods
    //

    protected abstract double forecastFixing(Date fixingDate);


    //
    // public abstract methods
    //

    public abstract Handle<YieldTermStructure> termStructure();
    public abstract Date maturityDate(Date valueDate);


    //
    // public methods
    //
    @Override
    public double fixing(final Date fixingDate, 
    					 final boolean forecastTodaysFixing) {
        QL.require(isValidFixingDate(fixingDate) , "Fixing date " + fixingDate.toString() + " is not valid"); // QA:[RG]::verified 
        final Date today = new Settings().evaluationDate();
        final boolean enforceTodaysHistoricFixings = new Settings().isEnforcesTodaysHistoricFixings();

        if (fixingDate.lt(today) || (fixingDate.equals(today) && enforceTodaysHistoricFixings && !forecastTodaysFixing)) {
            // must have been fixed
             double /*Rate*/ pastFixing =
                    IndexManager.getInstance().getHistory(name()).get(fixingDate);
             QL.require(pastFixing != Constants.NULL_REAL,
                          "Missing " + name() + " fixing for " + fixingDate);
            return pastFixing;
        }

        if ((fixingDate.equals(today)) && !forecastTodaysFixing) {
            // might have been fixed
            try {
                double /*Rate*/ pastFixing =
                	IndexManager.getInstance().getHistory(name()).get(fixingDate);
                if (pastFixing != Constants.NULL_REAL)
                    return pastFixing;
                else
                    ;   // fall through and forecast
            } catch (final Exception e) {
                ; // fall through and forecast
            }
        }
        // forecast
        return forecastFixing(fixingDate);
    }

    @Override
    public double fixing(final Date fixingDate) {
        return fixing(fixingDate, false);
    }

    public Date fixingDate(final Date valueDate) {
        final Date fixingDate = fixingCalendar().advance(valueDate, fixingDays, TimeUnit.Days);
        QL.ensure(isValidFixingDate(fixingDate) , "fixing date " + fixingDate + " is not valid"); 
        return fixingDate;
    }

    public Date valueDate(final Date fixingDate) {
        QL.require(isValidFixingDate(fixingDate) , "Fixing date is not valid"); // TODO: message
        return fixingCalendar().advance(fixingDate, fixingDays, TimeUnit.Days);
    }


    //
    // implements Observer
    //

    //XXX:registerWith
    //    @Override
    //    public void registerWith(final Observable o) {
    //        o.addObserver(this);
    //    }
    //
    //    @Override
    //    public void unregisterWith(final Observable o) {
    //        o.deleteObserver(this);
    //    }

    @Override
    //XXX::OBS public void update(final Observable o, final Object arg) {
    public void update() {
        //XXX::OBS notifyObservers(arg);
        notifyObservers();
    }


}
