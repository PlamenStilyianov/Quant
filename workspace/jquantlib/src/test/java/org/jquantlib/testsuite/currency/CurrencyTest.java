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
package org.jquantlib.testsuite.currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.jquantlib.QL;
import org.jquantlib.currencies.Currency;
import org.jquantlib.currencies.Europe.CHFCurrency;
import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.math.Rounding;
import org.junit.Ignore;
import org.junit.Test;

//FIXME: http://bugs.jquantlib.org/view.php?id=474
public class CurrencyTest {

    public CurrencyTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }


    @Test
    public void testCurrencies(){
        QL.info("testing currencies...");
        //Sample Currency - CHF
        final CHFCurrency chf = new CHFCurrency();

        QL.info("testing correct initialization...");
        assertTrue(chf.name().equalsIgnoreCase("Swiss franc"));
        assertTrue(chf.code().equalsIgnoreCase("CHF"));
        assertEquals(chf.numericCode(),756);
        assertTrue(chf.symbol().equalsIgnoreCase("SwF"));
        assertTrue(chf.fractionSymbol().equalsIgnoreCase(""));
        assertEquals(chf.fractionsPerUnit(),100);
        assertEquals(chf.rounding().type(), Rounding.Type.None);
        //Note: the initialization of the triangulated currency is a little bit suspicious...
        assertTrue(chf.triangulationCurrency().getClass() == Currency.class);
        assertTrue(chf.triangulationCurrency().empty());
        QL.info("testing overloaded operators....(only class based)");
        final EURCurrency euro = new EURCurrency();
        final CHFCurrency chf2 = new CHFCurrency();
        assertFalse(euro.eq(chf));
        assertTrue(euro.ne(chf));
        assertFalse(chf2.ne(chf));
        assertTrue(chf2.eq(chf));
        
        assertFalse(euro.eq(null));
        assertTrue(chf.eq(chf2));
        assertTrue(chf.eq(chf));
        
        assertTrue(Currency.operatorEquals(chf, chf2));
        assertTrue(Currency.operatorNotEquals(chf, euro));
        
        HashSet<Currency> testSet = new HashSet<Currency>();
        testSet.add(chf);
        
        assertTrue(testSet.contains(chf));
        assertFalse(testSet.contains(euro));
    }


    //Note: the initialization of the triangulated currency is a little bit suspicious...data_ not initialized!!
    @Ignore
    @Test(expected = NullPointerException.class)
    public void testLeakyCurrencyInitialization(){
        final CHFCurrency chf = new CHFCurrency();
        chf.triangulationCurrency().code();
    }



}
