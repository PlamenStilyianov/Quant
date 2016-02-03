/*
 Copyright (C) 2009 Ueli Hofstetter

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
 Copyright (C) 2004 Decillion Pty(Ltd)
 Copyright (C) 2004, 2005, 2006, 2007 StatPro Italia srl

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
package org.jquantlib.currencies;

import org.jquantlib.math.Rounding;


public class Currency implements Cloneable {

    /** The data object of this currency*/
    protected Data data;
    

    /**
     * Instances built via this constructor have undefined behavior.
     * <p>
     * Such instances can only act as placeholders and must be
     * reassigned to a valid currency before being used.
     */
    public Currency() {
    	// Instances built via this constructor have undefined behavior.
    }

    //
    // public methods
    //
    
    public final String name() {
        return data.name;
    }

    public final String code() {
        return data.code;
    }

    public final int numericCode() {
        return data.numeric;
    }

    public final String symbol() {
        return data.symbol;
    }

    public final String fractionSymbol() {
        return data.fractionSymbol;
    }

    public final int fractionsPerUnit() {
        return data.fractionsPerUnit;
    }

    public final Rounding rounding() {
        return data.rounding;
    }

    public final String format() {
        return data.formatString;
    }

    //is this a usable instance?
    public final boolean empty() {
        return data == null;
    }

    public final Currency triangulationCurrency() {
        return data.triangulated;
    }

    public final boolean eq(final Currency currency) {
        return equals(currency);
    }

    public final boolean ne(final Currency currency) {
        // eating our own dogfood
        return !(eq(currency));
    }

    
    //
    //  public static methods
    //
    
    public static final boolean operatorEquals(final Currency c1, final Currency c2) {
        return c1.equals(c2);
    }

    public static final boolean operatorNotEquals(final Currency c1, final Currency c2) {
        // eating our own dogfood
        return !(Currency.operatorEquals(c1, c2));
    }

    
    //
    // Overrides Object
    //

    @Override
    public String toString() {
        if (!empty())
            return code();
        else
            return "(null currency)";
    }

    @Override
  	public boolean equals(final Object obj) {
    	if (this == obj)
    		return true;
    	if (obj == null)
    		return false;
    	
        return obj instanceof Currency &&
        ((Currency) obj).fEquals(this);
   	
    }	
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((data == null) ? 0 : name().hashCode());
        return result;
    }


    //
    // protected methods
    //

    protected boolean fEquals(Currency other) {
    	if (this.empty() && other.empty())
    		return true;
    	if (this.name().equals(other.name()))
    		return true;
    	return false;  	
    }
    
    
    //
    // Implements Cloneable
    //

    /**
     * Creates a deep copy/clone of the object
     * @return the clones Currency object
     */
    @Override
    protected Currency clone() {
        final Currency currency = new Currency();
        if (data != null) {
            currency.data = data.clone();
        }
        return currency;

    }

    
    //
    // protected inner classes
    //

    /**
     * Inner class containing all information of a specific currency.
     */
    protected static class Data implements Cloneable {
        /**  The currency name, e.g, "U.S. Dollar"*/
        private final String name;
        /**  ISO 4217 three-letter code, e.g, "USD"*/
        private final String code;
        /** ISO 4217 numeric code, e.g, "840" */
        private final int numeric;
        /** Symbol, e.g, "$"*/
        private final String symbol;
        /** Fraction symbol, e.g, "p" */
        private final String  fractionSymbol;
        /** The number of fractionary parts in a unit, e.g, 100 */
        private final int fractionsPerUnit;
        /** The  rounding convention */
        private final Rounding rounding;
        /**
         *  The format will be fed three positional parameters, namely, value, code, and symbol, in this order.
         */
        private final String formatString;
        /** The currency used for triangulated exchange when required*/
        private final Currency triangulated;

        /**
         * Constructs a new Data object with a empty curreny object as triangulation currency
         * @param name The name of the currency String
         * @param code The code of the currency String
         * @param numericCode The numeric code of the currency String
         * @param symbol The symbol of the currency String
         * @param fractionSymbol The fractionSymbol of the currency String
         * @param fractionsPerUnit The fractions per unit of this currency (if any) int
         * @param rounding The rounding scheme used for this currency org.jquantlib.math.Rounding
         * @param formatString The format string used to format the output String
         */
        public Data(final String name, final String code, final int numericCode, final String symbol, final String fractionSymbol,
                final int fractionsPerUnit, final Rounding rounding, final String formatString) {
            this(name, code, numericCode, symbol, fractionSymbol, fractionsPerUnit, rounding, formatString, new Currency());
        }

        /**
         * Constructs a new Data object with a empty curreny object
         * @param name The name of the currency String
         * @param code The code of the currency String
         * @param numericCode The numeric code of the currency String
         * @param symbol The symbol of the currency String
         * @param fractionSymbol The fractionSymbol of the currency String
         * @param fractionsPerUnit The fractions per unit of this currency (if any) int
         * @param rounding The rounding scheme used for this currency org.jquantlib.math.Rounding
         * @param formatString The format string used to format the output String
         * @param triangulationCurrency The used triangulation currency
         */
        public Data(final String name, final String code, final int numericCode, final String symbol, final String fractionSymbol,
                final int fractionsPerUnit, final Rounding rounding, final String formatString, final Currency triangulationCurrency) {
            this.name = (name);
            this.code = (code);
            this.numeric = (numericCode);
            this.symbol = (symbol);
            this.fractionSymbol = (fractionSymbol);
            this.fractionsPerUnit = (fractionsPerUnit);
            this.rounding = (rounding);
            this.triangulated = (triangulationCurrency);
            this.formatString = (formatString);
        }

        /**
         * Creates deep copy/clone of this object.
         * @return The cloned Data object.
         */
        @Override
        public Data clone() {
            final Data data = new Data(name, code, numeric, symbol, fractionSymbol, fractionsPerUnit, rounding, formatString,
                    triangulated.clone());
            return data;
        }

    }
}
