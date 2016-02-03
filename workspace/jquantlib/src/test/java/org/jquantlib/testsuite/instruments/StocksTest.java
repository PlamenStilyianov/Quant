package org.jquantlib.testsuite.instruments;

import static org.junit.Assert.fail;

import org.jquantlib.QL;
import org.jquantlib.instruments.Instrument;
import org.jquantlib.instruments.Stock;
import org.jquantlib.quotes.Quote;
import org.jquantlib.quotes.RelinkableHandle;
import org.jquantlib.quotes.SimpleQuote;
import org.jquantlib.testsuite.util.Flag;
import org.junit.Test;

public class StocksTest {

    public StocksTest() {
        QL.info("::::: "+this.getClass().getSimpleName()+" :::::");
    }

    @Test
    public void testStocks() {
        QL.info("Testing price valuation of Stocks...");

        final double iniPrice = 3.56;
        final double newPrice = iniPrice*1.0214; // changed +2.14%

        // define Stock
        final RelinkableHandle<Quote> h = new RelinkableHandle<Quote>(new SimpleQuote(iniPrice));
        final Instrument s = new Stock(h);

        // attach an Observer to Stock
        final Flag priceChange = new Flag();
        s.addObserver(priceChange);

        // verify initial price
        if (iniPrice != s.NPV()) {
            fail("stock quote valuation failed");
        }

        // set a new price
        h.linkTo(new SimpleQuote(newPrice));

        // Observer must detect price change
        if (!priceChange.isUp()) {
            fail("Observer was not notified of instrument change");
        }

        if (newPrice != s.NPV()) {
            fail("stock quote havent changed value");
        }

    }


}
