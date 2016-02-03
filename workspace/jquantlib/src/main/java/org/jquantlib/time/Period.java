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
 Copyright (C) 2004, 2005, 2006 Ferdinando Ametrano
 Copyright (C) 2006 Katiuscia Manzoni
 Copyright (C) 2000, 2001, 2002, 2003 RiskMap srl
 Copyright (C) 2003, 2004, 2005, 2006, 2007 StatPro Italia srl

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

package org.jquantlib.time;

import java.util.Formatter;
import java.util.Locale;

import org.jquantlib.QL;
import org.jquantlib.lang.exceptions.LibraryException;

/**
 * This class provides a {@link Period} (length + TimeUnit) class and
 * implements a limited algebra.
 *
 * @author Srinivas Hasti
 * @author Richard Gomes
 * @author Mufaddal Karachiwala
 */
public class Period implements Cloneable {

    private static final String UNKNOWN_FREQUENCY = "unknown frequency";
    private static final String UNKNOWN_TIME_UNIT = "unknown time unit";
    private static final String INCOMPATIBLE_TIME_UNIT = "incompatible time unit";
    private static final String UNDECIDABLE_COMPARISON = "undecidable comparison";
    private static final String DIVISION_BY_ZERO_ERROR = "cannot be divided by zero";


    /**
     * Constant that can be used to represent one year period forward
     */
    public static final Period ONE_YEAR_FORWARD = new Period(1, TimeUnit.Years);

    /**
     * Constant that can be used to represent one year period in the past
     */
    public static final Period ONE_YEAR_BACKWARD = new Period(-1, TimeUnit.Years);

    /**
     * Constant that can be used to represent one year period forward
     */
    public static final Period ONE_MONTH_FORWARD = new Period(1, TimeUnit.Months);

    /**
     * Constant that can be used to represent one year period in the past
     */
    public static final Period ONE_MONTH_BACKWARD = new Period(-1, TimeUnit.Months);

    /**
     * Constant that can be used to represent one year period forward
     */
    public static final Period ONE_DAY_FORWARD = new Period(1, TimeUnit.Days);

    /**
     * Constant that can be used to represent one year period in the past
     */
    public static final Period ONE_DAY_BACKWARD = new Period(-1, TimeUnit.Days);


    /**
     * Length of the period
     */
    private int length;

    /**
     * Units representing the period
     */
    private TimeUnit units;


    //
    // public constructors
    //

    /**
     * Default constructor. Defaults to period of 0 days
     */
    public Period() {
        this.length = 0;
        this.units = TimeUnit.Days;
    }

    /**
     * To construct period representing the specified length and units
     *
     * @param length
     * @param units
     */
    public Period(final int length, final TimeUnit units) {
        this.length = length;
        this.units = units;
    }

    /**
     * To create a period by Frequency
     *
     * @param f
     */
    public Period(final Frequency f) {
        switch (f) {
        case Once:
        case NoFrequency:
            // same as Period()
            units = TimeUnit.Days;
            length = 0;
            break;
        case Annual:
            units = TimeUnit.Years;
            length = 1;
            break;
        case Semiannual:
        case EveryFourthMonth:
        case Quarterly:
        case Bimonthly:
        case Monthly:
            units = TimeUnit.Months;
            length = 12 / f.toInteger();
            break;
        case EveryFourthWeek:
        case Biweekly:
        case Weekly:
            units = TimeUnit.Weeks;
            length = 52 / f.toInteger();
            break;
        case Daily:
            units = TimeUnit.Days;
            length = 1;
            break;
        default:
            throw new LibraryException(UNKNOWN_FREQUENCY); // QA:[RG]::verified
        }
    }


    //
    // implements Cloneable
    //

    @Override
    protected Period clone() {
        return new Period(this.length, this.units);
    }


    //
    // unary operators
    //

    public Period negative() {
        return new Period(-this.length(), this.units());
    }


    //
    // binary operators
    //

    /**
     * Returns <code>this</code> Period added to <code>another</code> Period
     *
     * @param another
     * @return this
     */
    public Period addAssign(final Period another) {
        if (this.length() == 0) {
            this.length = another.length();
            this.units = another.units();
        } else if (this.units == another.units())
            this.length += another.length();
        else
            switch (this.units) {
                case Years:
                    switch (another.units()) {
                        case Months:
                            this.units = another.units();
                            this.length = this.length*12 + another.length();
                            break;
                        case Weeks:
                        case Days:
                            throw new IllegalArgumentException(INCOMPATIBLE_TIME_UNIT);
                        default:
                            throw new LibraryException(UNKNOWN_TIME_UNIT);
                    }
                    break;
                case Months:
                    switch (another.units()) {
                        case Years:
                            this.length += another.length() * 12;
                            break;
                        case Weeks:
                        case Days:
                            throw new IllegalArgumentException(INCOMPATIBLE_TIME_UNIT);
                        default:
                            throw new LibraryException(UNKNOWN_TIME_UNIT);
                    }
                    break;
                case Weeks:
                    switch (another.units()) {
                        case Days:
                            this.units = another.units();
                            this.length = this.length*7 + another.length();
                            break;
                        case Years:
                        case Months:
                            throw new IllegalArgumentException(INCOMPATIBLE_TIME_UNIT);
                        default:
                            throw new LibraryException(UNKNOWN_TIME_UNIT);
                    }
                    break;
                case Days:
                    switch (another.units()) {
                        case Weeks:
                            this.length += another.length()*7;
                            break;
                        case Years:
                        case Months:
                            throw new IllegalArgumentException(INCOMPATIBLE_TIME_UNIT);
                        default:
                            throw new LibraryException(UNKNOWN_TIME_UNIT);
                    }
                    break;
                default:
                    throw new LibraryException(UNKNOWN_TIME_UNIT);
            }
        return this;
    }

    /**
     * Returns <code>this</code> Period subtracted <code>another</code> Period
     *
     * @param another
     * @return this
     */
    public Period subAssign(final Period another) {
        return this.addAssign(another.clone().negative());
    }

    /**
     * Returns <code>this</code> Period divided by a scalar
     *
     * @param scalar
     * @return this
     */
    public Period divAssign(final int scalar) {
        if (scalar == 0)
            throw new ArithmeticException(DIVISION_BY_ZERO_ERROR);

        if (this.length % scalar == 0)
            // keep the original units. If the user created a
            // 24-months period, he'll probably want a 12-months one
            // when he halves it.
            this.length /= scalar;
        else
            switch (this.units) {
                case Years:
                    this.units = TimeUnit.Months;
                    this.length *= 12;
                    break;
                case Weeks:
                    this.units = TimeUnit.Days;
                    this.length *= 7;
                    break;
            }

        if (this.length % scalar == 0)
            this.length = this.length/scalar;
        else
            throw new LibraryException("cannot be divided by " + scalar);

        return this;
    }

    /**
     * Returns <code>this</code> Period multiplied by a scalar
     *
     * @param scalar
     * @return a new instance
     */
    public Period mul(final int scalar) {
        return new Period(scalar * this.length, this.units);
    }

    /**
     * Returns <code>this</code> Period added <code>another</code> Period
     *
     * @param another
     * @return a new instance
     */
    public Period add(final Period another) {
        return this.clone().addAssign(another);
    }

    /**
     * Returns <code>this</code> Period subtracted <code>another</code> Period
     *
     * @param another
     * @return a new instance
     */
    public Period sub(final Period another) {
        return this.clone().subAssign(another);
    }

    /**
     * Returns <code>this</code> Period divided by a scalar
     *
     * @param another
     * @return a new instance
     */
    public Period div(final int scalar) {
        return this.clone().divAssign(scalar);
    }

    /**
     * Compares <code>this</code> to <code>another</code> Period
     *
     * @param another
     * @return <code>true</code> if <code>this</code> is equal to <code>another</code>, <code>false</code> otherwise
     */
    public boolean eq(final Period another) {
        return this.equals(another);
    }

    /**
     * Compares <code>this</code> to <code>another</code> Period
     *
     * @param another
     * @return <code>true</code> if <code>this</code> is not equal to <code>another</code>, <code>false</code> otherwise
     */
    public boolean neq(final Period another) {
        return !this.equals(another);
    }

    /**
     * Compares <code>this</code> to <code>another</code> Period
     *
     * @param another
     * @return <code>true</code> if <code>this</code> is greater than <code>another</code>, <code>false</code> otherwise
     */
    public boolean gt(final Period another) {
        return another.lt(this);
    }

    /**
     * Compares <code>this</code> to <code>another</code> Period
     *
     * @param another
     * @return <code>true</code> if <code>this</code> is less than or equal to <code>another</code>, <code>false</code> otherwise
     */
    public boolean le(final Period another) {
        return this.lt(another) || this.eq(another);
    }

    /**
     * Compares <code>this</code> to <code>another</code> Period
     *
     * @param another
     * @return <code>true</code> if <code>this</code> is greater or equal to <code>another</code>, <code>false</code> otherwise
     */
    public boolean ge(final Period another) {
        return another.le(this);
    }

    /**
     * Compares <code>this</code> to <code>another</code> Period
     *
     * @param another
     * @return <code>true</code> if <code>this</code> is less than <code>another</code>, <code>false</code> otherwise
     */
    public boolean lt(final Period another) {
        if (this.length == 0)
            return (another.length > 0);
        if (another.length == 0)
            return (this.length < 0);

        // exact comparisons
        if (this.units() == another.units())
            return this.length() < another.length();
        if (this.units() == TimeUnit.Months && another.units() == TimeUnit.Years)
            return this.length() < 12 * another.length();
        if (this.units() == TimeUnit.Years && another.units() == TimeUnit.Months)
            return 12 * this.length() < another.length();
        if (this.units() == TimeUnit.Days && another.units() == TimeUnit.Weeks)
            return this.length() < 7 * another.length();
        if (this.units() == TimeUnit.Weeks && another.units() == TimeUnit.Days)
            return 7 * this.length() < another.length();

        // Inexact comparison
        final int period1MinDays = this.getMinDays();
        final int period1MaxDays = this.getMaxDays();
        final int period2MinDays = another.getMinDays();
        final int period2MaxDays = another.getMaxDays();

        if (period1MaxDays < period2MinDays)
            return true;
        else if (period1MinDays > period2MaxDays)
            return false;
        else
            throw new LibraryException(UNDECIDABLE_COMPARISON);
    }


   //
   // public methods
   //

   /**
    * @return length of the period
    */
   public final int length() {
       return this.length;
   }

   /**
    * Time units represented by the period
    *
    * @return time units of the period
    */
   public final TimeUnit units() {
       return this.units;
   }

   /**
    * To get at Frequency represented by the period
    *
    * @return
    */
   public final Frequency frequency() {
       // unsigned version
       final int length = Math.abs(this.length);

       if (length == 0)
           return Frequency.NoFrequency;

       switch (units) {
       case Years:
           if (length == 1)
               return Frequency.Annual;
           else
               return Frequency.OtherFrequency;
         case Months:
           if (12%length == 0 && length <= 12)
               return Frequency.valueOf(12 / length);
           else
               return Frequency.OtherFrequency;
         case Weeks:
           if (length==1)
               return Frequency.Weekly;
           else if (length==2)
               return Frequency.Biweekly;
           else if (length==4)
               return Frequency.EveryFourthWeek;
           else
               return Frequency.OtherFrequency;
         case Days:
           if (length==1)
               return Frequency.Daily;
           else
               return Frequency.OtherFrequency;
       default:
           throw new LibraryException(UNKNOWN_TIME_UNIT); // QA:[RG]::verified
       }
   }



    public double years(final Period p) {
    	if(p.length() == 0) return 0.0;

    	switch (p.units()) {
	    	case Days:
	    	case Weeks:
	    		throw new IllegalArgumentException(UNDECIDABLE_COMPARISON);
	    	case Months:
	    		return p.length()/12.0;
	    	case Years:
	    		return p.length();
	    	default:
	    		throw new LibraryException(UNKNOWN_TIME_UNIT);
    	}
    }

    public double months(final Period p) {
    	if(p.length() == 0) return 0.0;

    	switch(p.units()) {
	    	case Days:
	    	case Weeks:
	    		throw new IllegalArgumentException(UNDECIDABLE_COMPARISON);
	    	case Months:
	    		return p.length();
	    	case Years:
	    		return p.length()*12.0;
	    	default:
	    		throw new LibraryException(UNKNOWN_TIME_UNIT);
    	}
    }

    public double weeks(final Period p) {
    	if(p.length() == 0) return 0.0;

    	switch(p.units()) {
	    	case Days:
	    		return p.length()/7.0;
	    	case Weeks:
	    		return p.length();
	    	case Months:
	    	case Years:
	    		throw new IllegalArgumentException(UNDECIDABLE_COMPARISON);
	    	default:
	    		throw new LibraryException(UNKNOWN_TIME_UNIT);
    	}
    }

    public double days(final Period p) {
    	if(p.length() == 0) return 0.0;

    	switch(p.units()) {
	    	case Days:
	    		return p.length();
	    	case Weeks:
	    		return p.length()*7.0;
	    	case Months:
	    	case Years:
	    		throw new IllegalArgumentException(UNDECIDABLE_COMPARISON);
	    	default:
	    		throw new LibraryException(UNKNOWN_TIME_UNIT);
    	}
    }




    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + length;
        result = prime * result + ((units == null) ? 0 : units.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
    	
        return obj instanceof Period &&
        ((Period) obj).fEquals(this);
    }


    //
    // protected methods
    //

    protected boolean fEquals(Period other) {
        if (length != other.length)
            return false;
        if (units == null) {
            if (other.units != null)
                return false;
        } else if (!units.equals(other.units))
            return false;
        return true;	
    }






    /**
     * Converts Period to days
     * @param p
     * @return minimum days
     */
    private int getMinDays() {
    	switch (this.units()) {
    		case Years:
    			return this.length()*365;
    		case Months:
    			return this.length()*28;
    		case Weeks:
    			return this.length()*7;
    		case Days:
    			return this.length();
    		default:
    			throw new LibraryException(UNKNOWN_TIME_UNIT);
    	}
    }

    /**
     * Converts Period to days
     * @param p
     * @return maximum days
     */
    private int getMaxDays() {
    	switch (this.units()) {
		case Years:
			return this.length()*366;
		case Months:
			return this.length()*31;
		case Weeks:
			return this.length()*7;
		case Days:
			return this.length();
		default:
			throw new LibraryException(UNKNOWN_TIME_UNIT);
	}

    }

    @Override
    public String toString() {
        return getLongFormat();
    }

    /**
     * Returns the name of period in long format
     *
     * @return the name of period in long format
     */
    public String getLongFormat() {
        return getInternalLongFormat();
    }

    /**
     * Returns the name of period in short format (3 letters)
     *
     * @return the name of period in short format (3 letters)
     */
    public String getShortFormat() {
        return getInternalShortFormat();
    }

    /**
     * Returns periods in long format (e.g. "2 weeks")
     */
    private String getInternalLongFormat() {
        String suffix;
        if (this.length == 1)
            suffix = "";
        else
            suffix = "s";
        final StringBuilder sb = new StringBuilder();
        final Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("%d %s%s", this.length, this.units.getLongFormat(),
                suffix);
        return sb.toString();
    }

    /**
     * Output periods in short format (e.g. "2w")
     */
    private String getInternalShortFormat() {
        final StringBuilder sb = new StringBuilder();
        final Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("%d%s", this.length, this.units.getShortFormat());
        return sb.toString();
    }

    public void normalize() {
        if (length!=0)
            switch (units) {
            case Days:
                if (!(length%7!=0)) {
                    length/=7;
                    units = TimeUnit.Weeks;
                }
                break;
            case Months:
                if (!(length%12!=0)) {
                    length/=12;
                    units = TimeUnit.Years;
                }
                break;
            case Weeks:
            case Years:
                break;
            default:
                QL.require(false , UNKNOWN_TIME_UNIT); // QA:[RG]::verified
            }
    }

}
