/*
 Copyright (C) 2011 Tim Blackler

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

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.Settings;
import org.jquantlib.currencies.Currency;
import org.jquantlib.lang.annotation.Real;
import org.jquantlib.termstructures.InflationTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.Period;
import org.jquantlib.time.calendars.NullCalendar;
import org.jquantlib.util.Observer;
import org.jquantlib.util.Pair;

/**
 *
 * @author Tim Blackler
 *
 */
// TODO: code review :: please verify against QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class InflationIndex extends Index implements Observer {

    protected String familyName;
    protected Region region;
    protected boolean revised;
    protected boolean interpolated;
    protected Frequency frequency;
    protected Period availabilityLag;
    protected Currency currency;
    
    
    public InflationIndex(   final String familyName,
            				 final Region region,
            				 final boolean revised,
            				 final boolean interpolated,
            				 final Frequency frequency,
            				 final Period availabilityLag,
            				 final Currency currency) {
        this.familyName = familyName;
        this.region = region;
        this.revised = revised;
        this.interpolated = interpolated;
        this.availabilityLag = availabilityLag;
        this.currency = currency;

        new Settings().evaluationDate().addObserver(this);
        IndexManager.getInstance().notifier(name()).addObserver(this);
    }
    
    @Override
    public String name() {
        final StringBuilder builder = new StringBuilder(region.name());
        builder.append(" ");
        builder.append(familyName);
        return builder.toString();
    }
    
    @Override
    /** Inflation indices do not have fixing calendars.  An inflation index value is valid for every day (including
     *  weekends) of a calendar period.  I.e. it uses the NullCalendar as its fixing calendar.
     **/
    public Calendar fixingCalendar() {
        return new NullCalendar();
    }

    @Override
    public boolean isValidFixingDate(final Date fixingDate) {
        return true;
    }

    public String familyName() {
        return familyName;
    }

    public Region region() {
        return region;
    }

    public boolean revised() {
        return revised;
    }

    public boolean interpolated() {
        return interpolated;
    }

    
    public Frequency frequency() {
        return frequency;
    }
    
    /** The availability lag describes when the index is
     * <i>available</i>, not how it is used.  Specifically the
     * fixing for, say, January, may only be available in April
     * but the index will always return the index value
     * applicable for January as its January fixing (independent
     * of the lag in availability).
     **/
    public Period availabilityLag() {
        return availabilityLag;
    }
    
    public Currency currency() {
        return currency;
    }
    
    //
    // public methods
    //
    @Override
    /** this method creates all the "fixings" for the relevant
     * period of the index.  E.g. for monthly indices it will put
     * the same value in every calendar day in the month.
     **/
    public void addFixing (final Date fixingDate,
    				       final @Real double fixing,
    				       boolean forceOverwrite) {
    	
    	Pair<Date,Date> lim = InflationTermStructure.inflationPeriod(fixingDate, frequency);
    	
    	int n = (int)(lim.second().inc().sub(lim.first()));
    	
    	List<Date> dates = new ArrayList<Date>();
    	List<Double> rates = new ArrayList<Double>();
    	
    	for (int i = 0; i < n; i++) {
    		dates.add(i, lim.first().add(i));
    		rates.add(i, Double.valueOf(fixing));
    	}
    	
    	super.addFixings(dates.iterator(), rates.iterator(), forceOverwrite);
    }

    @Override
    //XXX::OBS public void update(final Observable o, final Object arg) {
    public void update() {
        //XXX::OBS notifyObservers(arg);
        notifyObservers();
    }


}
