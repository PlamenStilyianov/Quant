package org.jquantlib.testsuite.helpers;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.daycounters.Actual365Fixed;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.helpers.CRRAmericanDividendOptionHelper;
import org.jquantlib.helpers.CRREuropeanDividendOptionHelper;
import org.jquantlib.instruments.Option;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.Target;
import org.junit.Test;

public class CRRDividendOptionTest implements Runnable {

    final private Calendar calendar;
    final private Date today;
    final private Date settlementDate;
    final private Option.Type type;
    final private double strike;
    final private double underlying;
    final private /*@Rate*/ double riskFreeRate;
    final private /*@Volatility*/ double volatility;
    final private double dividendYield;
    final private Date maturityDate;
    final private DayCounter dc;
    final private List<Date> divDates;
    final private List<Double> divAmounts;


    public static void main(final String[] args) {
        new CRRDividendOptionTest().run();
    }

    private final boolean quiet = false;


    public CRRDividendOptionTest() {
        this.calendar = new Target();
        this.today = new Date(15, Month.May, 1998);
        this.settlementDate = new Date(17, Month.May, 1998);

        this.type = Option.Type.Put;
        this.strike = 40.0;
        this.underlying = 36.0;
        this.riskFreeRate = 0.06;
        this.volatility = 0.2;
        this.dividendYield = 0.00;

        this.maturityDate = new Date(17, Month.May, 1999);
        this.dc = new Actual365Fixed();

        this.divDates = new ArrayList<Date>();
        this.divAmounts = new ArrayList<Double>();
        for (int i=1; i<=3; i++) {
            final Date divDate = today.add(new Period(i*3, TimeUnit.Months)).add(new Period(15, TimeUnit.Days));
            divDates.add(divDate);
            divAmounts.add(2.06);
        }
    }


    public void run() {
        testEuropeanDividendOption();
        testAmericanDividendOption();
    }

    @Test
    public void testEuropeanDividendOption() {
        if (!quiet) {
            QL.info("::::: " + this.getClass().getSimpleName() + " ::::: European Dividend Option :::::");
        }

        new Settings().setEvaluationDate(today);

        final CRREuropeanDividendOptionHelper option = new CRREuropeanDividendOptionHelper(
                type, underlying, strike, riskFreeRate, dividendYield, volatility,
                settlementDate, maturityDate,
                divDates, divAmounts,
                calendar, dc);

        final double value  = option.NPV();
        final double delta  = option.delta();
        final double gamma  = option.gamma();
        final double theta  = option.theta();
        final double vega   = option.vega(); //TODO
        final double rho    = option.rho();  //TODO

        // market price: simply guess something 10% higher than theoretical
        // final double ivol = option.impliedVolatility(value*1.10);

        if (!quiet) {
            QL.info(String.format("value       = %13.9f", value));
            QL.info(String.format("delta       = %13.9f", delta));
            QL.info(String.format("gamma       = %13.9f", gamma));
            QL.info(String.format("theta       = %13.9f", theta));
            QL.info(String.format("vega        = %13.9f", vega));
            QL.info(String.format("rho         = %13.9f", rho));
            // QL.info(String.format("implied vol = %13.9f", ivol));
        }
    }

    @Test
    public void testAmericanDividendOption() {
        if (!quiet) {
            QL.info("::::: " + this.getClass().getSimpleName() + " ::::: American Dividend Option :::::");
        }

        new Settings().setEvaluationDate(today);

        final CRRAmericanDividendOptionHelper option = new CRRAmericanDividendOptionHelper(
                type, underlying, strike, riskFreeRate, dividendYield, volatility,
                settlementDate, maturityDate,
                divDates, divAmounts,
                calendar, dc);

        final double value = option.NPV();
        final double delta = option.delta();
        final double gamma = option.gamma();
        final double theta = option.theta();
        final double vega  = option.vega(); //TODO
        final double rho   = option.rho();  //TODO

        // market price: simply guess something 10% higher than theoretical
        //FIXME final double ivol = option.impliedVolatility(value*1.10);

        if (!quiet) {
            QL.info(String.format("value       = %13.9f", value));
            QL.info(String.format("delta       = %13.9f", delta));
            QL.info(String.format("gamma       = %13.9f", gamma));
            QL.info(String.format("theta       = %13.9f", theta));
            QL.info(String.format("vega        = %13.9f", vega));
            QL.info(String.format("rho         = %13.9f", rho));
            // QL.info(String.format("implied vol = %13.9f", ivol));
        }
    }

}
