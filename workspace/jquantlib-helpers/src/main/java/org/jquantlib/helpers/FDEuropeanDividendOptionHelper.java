package org.jquantlib.helpers;

import java.util.List;

import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.exercise.EuropeanExercise;
import org.jquantlib.instruments.Option;
import org.jquantlib.pricingengines.vanilla.finitedifferences.FDDividendEuropeanEngine;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;

/**
 * Helper class for European Dividend Options using the finite differences engine
 *
 * @see FDDividendOptionHelper
 *
 * @author Richard Gomes
 */
public class FDEuropeanDividendOptionHelper extends FDDividendOptionHelper<FDDividendEuropeanEngine> {

    /**
     * Constructor for European Dividend Options helper class using the finite differences engine
     *
     * @param type is the option call type (Call/Put)
     * @param underlying is the price of the underlying asset
     * @param strike is the strike price at expiration
     * @param r is the risk free rate
     * @param q is the yield rate
     * @param vol is the volatility
     * @param settlementDate is the settlement date
     * @param expirationDate is the expiration date
     * @param dates is a list of dates when dividends are expected to be paid
     * @param dividends is a list of dividends amounts (as a pure value) expected to be paid
     */
    public FDEuropeanDividendOptionHelper(
            final Option.Type type,
            final /*@Real*/ double underlying,
            final /*@Real*/ double strike,
            final /*@Rate*/ double r,
            final /*@Rate*/ double q,
            final /*@Volatility*/ double vol,
            final Date settlementDate,
            final Date expirationDate,
            final List<Date> dates,
            final List<Double> dividends) {

        super(FDDividendEuropeanEngine.class,
              type, underlying, strike, r, q, vol,
              settlementDate, new EuropeanExercise(expirationDate),
              dates, dividends);
    }


    /**
     * Constructor for European Dividend Options helper class using the finite differences engine
     *
     * @param type is the option call type (Call/Put)
     * @param underlying is the price of the underlying asset
     * @param strike is the strike price at expiration
     * @param r is the risk free rate
     * @param q is the yield rate
     * @param vol is the volatility
     * @param settlementDate is the settlement date
     * @param expirationDate is the expiration date
     * @param dates is a list of dates when dividends are expected to be paid
     * @param dividends is a list of dividends amounts (as a pure value) expected to be paid
     * @param cal is {@link Calendar} to be employed
     */
    public FDEuropeanDividendOptionHelper(
            final Option.Type type,
            final /*@Real*/ double underlying,
            final /*@Real*/ double strike,
            final /*@Rate*/ double r,
            final /*@Rate*/ double q,
            final /*@Volatility*/ double vol,
            final Date settlementDate,
            final Date expirationDate,
            final List<Date> dates,
            final List<Double> dividends,
            final Calendar cal) {

        super(FDDividendEuropeanEngine.class,
              type, underlying, strike, r, q, vol,
              settlementDate, new EuropeanExercise(expirationDate),
              dates, dividends, cal);
    }


    /**
     * Constructor for European Dividend Options helper class using the finite differences engine
     *
     * @param type is the option call type (Call/Put)
     * @param underlying is the price of the underlying asset
     * @param strike is the strike price at expiration
     * @param r is the risk free rate
     * @param q is the yield rate
     * @param vol is the volatility
     * @param settlementDate is the settlement date
     * @param expirationDate is the expiration date
     * @param dates is a list of dates when dividends are expected to be paid
     * @param dividends is a list of dividends amounts (as a pure value) expected to be paid
     * @param cal is {@link Calendar} to be employed
     * @param dc is a {@link DayCounter}
     */
    public FDEuropeanDividendOptionHelper(
            final Option.Type type,
            final /*@Real*/ double underlying,
            final /*@Real*/ double strike,
            final /*@Rate*/ double r,
            final /*@Rate*/ double q,
            final /*@Volatility*/ double vol,
            final Date settlementDate,
            final Date expirationDate,
            final List<Date> dates,
            final List<Double> dividends,
            final Calendar cal,
            final DayCounter dc) {

        super(FDDividendEuropeanEngine.class,
              type, underlying, strike, r, q, vol,
              settlementDate, new EuropeanExercise(expirationDate),
              dates, dividends, cal, dc);
    }

}
