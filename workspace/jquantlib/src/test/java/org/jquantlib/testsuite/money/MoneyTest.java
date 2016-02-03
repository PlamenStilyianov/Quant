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
package org.jquantlib.testsuite.money;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.currencies.Currency;
import org.jquantlib.currencies.ExchangeRate;
import org.jquantlib.currencies.ExchangeRateManager;
import org.jquantlib.currencies.Money;
import org.jquantlib.currencies.America.USDCurrency;
import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.currencies.Europe.GBPCurrency;
import org.jquantlib.math.Closeness;
import org.jquantlib.math.Rounding;
import org.junit.Ignore;
import org.junit.Test;

//FIXME: http://bugs.jquantlib.org/view.php?id=474
public class MoneyTest {

    public MoneyTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
        QL.info("see testsuite.money.cpp/hpp");
    }

    // FIXME: Remove @Ignore when ExchangeRateManager becomes available (and reviewed!)
    // See: http://bugs.jquantlib.org/view.php?id=466
    @Ignore
    @Test
    public void testBaseCurrency(){
        QL.info("Testing money arithmetic with conversion to base currency...");

        final Currency EUR = new EURCurrency();
        final Currency GBP = new GBPCurrency();
        final Currency USD = new USDCurrency();

        final Money m1 = Money.multiple(50000.0,GBP);
        final Money m2 = Money.multiple(100000.0 , EUR);
        final Money m3 = Money.multiple(500000.0 , USD);


        ExchangeRateManager.getInstance().clear();
        final ExchangeRate eur_usd = new  ExchangeRate(EUR, USD, 1.2042);
        final ExchangeRate eur_gbp = new ExchangeRate(EUR, GBP, 0.6612);
        ExchangeRateManager.getInstance().add(eur_usd);
        ExchangeRateManager.getInstance().add(eur_gbp);


        Money.conversionType = Money.ConversionType.BaseCurrencyConversion;
        Money.baseCurrency = EUR;

        //divided the steps for tracing...
        final Money calculated0 = m1.mul(3.0);
        final Money calculated1 = (m2.mul(2.5));
        final Money calculated2 = m3.div(5.0);

        final Money calculated3 = calculated0.add(calculated1).sub(calculated2);

        QL.info("Calculated value: " + calculated3.value());


        final Rounding round = Money.baseCurrency.rounding();
        /*@Decimal*/final double x = round.operator(m1.value()*3.0/eur_gbp.rate()) + 2.5*m2.value() -
        round.operator(m3.value()/(5.0*eur_usd.rate()));
        QL.info("Expected value: " + x);

        final Money expected = new Money(x, EUR);

        assertTrue(Closeness.isClose(calculated3.value(),expected.value()));
        if(!calculated3.equals(expected)) {
            fail("Wrong result: \n"
                    + "    expected:   " + expected + "\n"
                    + "    calculated: " + calculated3);
        }
        QL.info("testBaseCurrency done!");
    }


    @Ignore
    @Test
    public void testNone() {
        QL.info("Testing money arithmetic without conversions...");
        final Currency EUR = new EURCurrency();
        final Money m1 = Money.multiple( 50000.0, EUR);
        final Money m2 = Money.multiple(100000.0, EUR);
        final Money m3 = Money.multiple(500000.0, EUR);

        Money.conversionType = Money.ConversionType.NoConversion;

        //divided the steps for tracing...
        final Money calculated0 = m1.mul(3.0);
        final Money calculated1 = (m2.mul(2.5));
        final Money calculated2 = m3.div(5.0);

        final Money calculated3 = calculated0.add(calculated1).sub(calculated2);

        QL.info("Calculated value: " + calculated3.value());

        /*Decimal*/final double x =  m1.value()*3.0 + 2.5*m2.value() - m3.value()/5.0;
        QL.info("Expected value: " + x);

        final Money expected = new Money(x, EUR);

        if(!calculated3.equals(expected)){
            fail("Wrong result: \n"
                    + "    expected:   " + expected + "\n"
                    + "    calculated: " + calculated3);
        }
        QL.info("testNone done!");
    }

}
