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
 Copyright (C) 2004, 2005, 2006, 2007, 2008 StatPro Italia srl
 Copyright (C) 2004 Decillion Pty(Ltd)

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.Settings;
import org.jquantlib.currencies.America.PEHCurrency;
import org.jquantlib.currencies.America.PEICurrency;
import org.jquantlib.currencies.America.PENCurrency;
import org.jquantlib.currencies.Europe.ATSCurrency;
import org.jquantlib.currencies.Europe.BEFCurrency;
import org.jquantlib.currencies.Europe.DEMCurrency;
import org.jquantlib.currencies.Europe.ESPCurrency;
import org.jquantlib.currencies.Europe.EURCurrency;
import org.jquantlib.currencies.Europe.FIMCurrency;
import org.jquantlib.currencies.Europe.FRFCurrency;
import org.jquantlib.currencies.Europe.GRDCurrency;
import org.jquantlib.currencies.Europe.IEPCurrency;
import org.jquantlib.currencies.Europe.ITLCurrency;
import org.jquantlib.currencies.Europe.LUFCurrency;
import org.jquantlib.currencies.Europe.NLGCurrency;
import org.jquantlib.currencies.Europe.PTECurrency;
import org.jquantlib.currencies.Europe.ROLCurrency;
import org.jquantlib.currencies.Europe.RONCurrency;
import org.jquantlib.currencies.Europe.TRLCurrency;
import org.jquantlib.currencies.Europe.TRYCurrency;
import org.jquantlib.lang.exceptions.LibraryException;
import org.jquantlib.lang.iterators.Iterables;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;

/**
 * Exchange rate Repository.
 */
public class ExchangeRateManager {

    /** Singleton instance of the ExchangeRateManager. */
    private static volatile ExchangeRateManager instance = null;
    /** The HashMape containing all ExchangeRates. */
    private final HashMap<Object, List<Entry>> data_ = new HashMap<Object, List<Entry>>();

    /**
     * Returns a singleton of the ExchangeRateManager.
     *
     * @return The ExchangeRateManager shared by everything loaded with this classloader.
     */
    //FIXME: remove singleton pattern
    public static ExchangeRateManager getInstance() {
        if (instance == null) {
            synchronized (ExchangeRateManager.class) {
                if (instance == null) {
                    instance = new ExchangeRateManager();
                }
            }
        }
        return instance;
    }

    // FIXME: check whether this should be derived from some kind of (generic function)
    /**
     * Helper class to decide whether or a date is in the range of a specific entry.
     */
    public static class Valid_at /* implements Ops.DoublePredicate */{
        Date d;

        public Valid_at(final Date d) {
            this.d = d;
        }

        public boolean operator(final Entry e) {
            return d.ge(e.startDate) && d.le(e.endDate);
        }
        // @Override
        // public boolean op(double a) {
        // // TODO Auto-generated method stub
        // return false;
        // }

    }

    /**
     * Entity to be stored in the repository.
     */
    public static class Entry {
        /** The ExchangeRate of this Entry. */
        public ExchangeRate rate;
        /** Start and end date for this currency (note: they can be present multiple times in the repository) */
        public Date startDate, endDate;

        /**
         * Constructs a new Entry
         *
         * @param rate The ExchangeRate
         * @param start The start date of the period this ExchangeRate should be used (ie. when it should be used)
         * @param end The end date of the period this ExchangeRate should be used (ie. when it should be used)
         */
        public Entry(final ExchangeRate rate, final Date start, final Date end) {
            this.rate = (rate);
            this.startDate = (start);
            this.endDate = (end);
        };
    }

    /**
     * Constructs a new ExchangeRateManager and initialises the most used rates. Note: private; should only be accessed by
     * getInstance().
     */
    private ExchangeRateManager() {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        addKnownRates();
    }

    /**
     * Adds an exchange rate. The given rate is valid between the given dates.
     *
     * Note: If two rates are given between the same currencies and with overlapping date ranges, the latest one added takes
     * precedence during lookup.
     *
     * @param rate The ExchangeRate to be added
     * @param startDate The start date of the period for which the above Exchange rate should be valid.
     * @param endDate The end date of the period for which the above Exchange rate should be valid.
     */
    public void add(final ExchangeRate rate, final Date startDate, final Date endDate) {
        /* @Key */final int k = hash(rate.source(), rate.target());
        if (data_.get(k) == null) {
            data_.put(k, new ArrayList<Entry>());
        }
        data_.get(k).add(0, new Entry(rate, startDate, endDate));
    }

    /**
     * Adds an exchange rate to the repository. The given rate is valid between min and max Date (implementation dependend).
     *
     * @param rate
     */
    public void add(final ExchangeRate rate) {
        add(rate, Date.minDate(), Date.maxDate());
    }

    /**
     * Looks up an ExchangeRate in the repository.
     *
     * @param source The source currency the Exchange rates must have.
     * @param target The source currency the Exchange rates must have.
     * @return
     */
    public ExchangeRate lookup(final Currency source, final Currency target) {
        return lookup(source, target, Date.todaysDate(), ExchangeRate.Type.Derived);
    }

    public ExchangeRate lookup(final Currency source, final Currency target, final Date date) {
        return lookup(source, target, date, ExchangeRate.Type.Derived);
    }

    /**
     * Lookup the exchange rate between two currencies at a given date. If the given type is Direct, only direct exchange rates will
     * be returned if available; if Derived, direct rates are still preferred but derived rates are allowed.
     *
     * Warning: if two or more exchange-rate chains are possible which allow to specify a requested rate, it is unspecified which
     * one is returned.
     *
     * @param source The source currency of the exchange rate to be found. Currency
     * @param target The target currency of the exchange rate to be found. Currency
     * @param date The date when this exchange rate should be valid. Date
     * @param type The type of the exchange rate. ExchangeRate.Type
     * @return The exchange rate fulfilling all these properties. ExchangeRate
     */
    public ExchangeRate lookup(final Currency source, final Currency target, Date date, final ExchangeRate.Type type) {
        if (source.eq(target))
            return new ExchangeRate(source, target, 1.0);

        if (date.isToday()) {
            date = new Settings().evaluationDate();
        }

        if (type == ExchangeRate.Type.Direct)
            return directLookup(source, target, date);
        else if (!source.triangulationCurrency().empty()) {
            final Currency link = source.triangulationCurrency();
            if (link.eq(target))
                return directLookup(source, link, date);
            else
                return ExchangeRate.chain(directLookup(source, link, date), lookup(link, target, date));
        } else if (!target.triangulationCurrency().empty()) {
            final Currency link = target.triangulationCurrency();
            if (source.eq(link))
                return directLookup(link, target, date);
            else
                return ExchangeRate.chain(lookup(source, link, date), directLookup(link, target, date));
        } else
            return smartLookup(source, target, date);
    }

    /**
     * Removes all manually added exchange rates from this ExchangeRateManager.
     */
    public void clear() {
        data_.clear();
        addKnownRates();
    }

    /**
     * Creates a hash for two currencies.
     *
     * @param c1 Currency one. Currency
     * @param c2 Currency two. Currency
     * @return A hash of these to currencies. int
     */
    public int hash(final Currency c1, final Currency c2) {
        return Math.min(c1.numericCode(), c2.numericCode()) * 1000 + Math.max(c1.numericCode(), c2.numericCode());
    }

    /**
     * ???????????????????????????????????????????????
     *
     * @param k
     * @param c
     * @return
     */
    public boolean hashes(/* ExchangeRateManager::Key */final int k, final Currency c) {
        return c.numericCode() == k % 1000 || c.numericCode() == k / 1000;
    }

    /**
     * Adds obsoleted currencies to the repository.
     */
    private void addKnownRates() {
        final Date maxDate = Date.maxDate();
        // currencies obsoleted by Euro
        add(new ExchangeRate(
                new EURCurrency(),
                new ATSCurrency(), 13.7603),
                new Date(1, Month.January,1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new BEFCurrency(), 40.3399),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new DEMCurrency(), 1.95583),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new ESPCurrency(), 166.386),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new FIMCurrency(), 5.94573),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new FRFCurrency(), 6.55957),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new GRDCurrency(), 340.750),
                new Date(1, Month.January, 2001),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new IEPCurrency(), 0.787564),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new ITLCurrency(), 1936.27),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new LUFCurrency(), 40.3399),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new NLGCurrency(), 2.20371),
                new Date(1, Month.January, 1999),
                maxDate);
        add(new ExchangeRate(
                new EURCurrency(),
                new PTECurrency(), 200.482),
                new Date(1, Month.January, 1999),
                maxDate);
        // other obsoleted currencies
        add(new ExchangeRate(
                new TRYCurrency(),
                new TRLCurrency(), 1000000.0),
                new Date(1, Month.January, 2005),
                maxDate);
        add(new ExchangeRate(
                new RONCurrency(),
                new ROLCurrency(), 10000.0),
                new Date(1, Month.July, 2005),
                maxDate);
        add(new ExchangeRate(
                new PENCurrency(),
                new PEICurrency(), 1000000.0),
                new Date(1, Month.July, 1991),
                maxDate);
        add(new ExchangeRate(
                new PEICurrency(),
                new PEHCurrency(), 1000.0),
                new Date(1, Month.February, 1985),
                maxDate);
    }

    /**
     * Fetches a exchange rate from the repository.
     *
     * @param source The source currency of the exchange rate. Currency
     * @param target The target currency of the exchange rate. Currency
     * @param date The date the exchange rate should be valid at. Date
     * @return The found exchange rate. ExchangeRate
     */
    private ExchangeRate directLookup(final Currency source, final Currency target, final Date date) {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");

        ExchangeRate rate = null;
        QL.require(((rate = fetch(source, target, date)) != null) , "no direct conversion available");  // TODO: message

        return rate;
    }

    /**
     * @see #smartLookup(Currency, Currency, Date, int[])
     */
    private ExchangeRate smartLookup(final Currency source, final Currency target, final Date date) {
        return smartLookup(source, target, date, new int[0]);
    }

    /**
     * Looks up an exchange rate in the repository
     *
     * @param source The source currency of the exchange rate.
     * @param target The target currency of the exchange rate.
     * @param date The date when the exchange rate should be valid.
     * @param forbidden The index array of forbidden source currencies.
     * @return The found ExchangeRate
     */
    private ExchangeRate smartLookup(final Currency source, final Currency target, final Date date, int[] forbidden) {
        // direct exchange rates are preferred.
        final ExchangeRate direct = fetch(source, target, date);
        if (direct != null)
            return direct;

        // if none is found, turn to smart lookup. The source currency
        // is forbidden to subsequent lookups in order to avoid cycles.
        final int temp[] = forbidden.clone();
        forbidden = new int[temp.length + 1];
        System.arraycopy(temp, 0, forbidden, 0, temp.length);
        forbidden[forbidden.length - 1] = (source.numericCode());

        for (final Object key : Iterables.unmodifiableIterable(data_.keySet())) {
            // we look for exchange-rate data which involve our source
            // currency...
            if (hashes((Integer) key, source) && !(data_.get(key).isEmpty())) {
                // ...whose other currency is not forbidden...
                final Entry e = data_.get(key).get(0);
                final Currency other =
                    // if
                    (source == e.rate.source()) ?
                            // then
                            e.rate.target()
                            :
                                // else
                                e.rate.source();
                            if (match(forbidden, other.numericCode()) == (forbidden.length - 1)) {
                                // ...and which carries information for the requested date.
                                final ExchangeRate head = fetch(source, other, date);
                                try {
                                    if (head != null) {
                                        final ExchangeRate tail = smartLookup(other, target, date, forbidden);
                                        // ..we're done.
                                        return ExchangeRate.chain(head, tail);
                                    }
                                } catch (final Exception ex) {
                                    // fall through...
                                    // otherwise, we just discard this rate.
                                }
                            }
            }
        }

        // if the loop completed, we have no way to return the requested rate.
        throw new LibraryException("no conversion available"); // TODO: message
    }

    /**
     * Fetches an ExchangeRate from the HashMap.
     *
     * @param source The source currency of the exchange rate.
     * @param target The target currency of the exchange rate.
     * @param date The date when the exchange rate should be valid.
     * @return The found ExchangeRate.
     */
    public ExchangeRate fetch(final Currency source, final Currency target, final Date date) {
        final List<Entry> rates = data_.get(hash(source, target));
        final int i = matchValidateAt(rates, date);
        return i == rates.size() - 1 ? rates.get(i).rate : null;
    }

    /**
     * Returns the index of the first element equals to a specific value-
     *
     * @param list The int array to be examined. int[]
     * @param value The value to be looked for. int
     * @return The first index value is found. int
     */
    private int match(final int[] list, final int value) {
        for (int i = 0; i < list.length; i++) {
            if (value == list[i])
                return i;
        }
        return -1;
    }

    /**
     * Returns the index of the first valid element.
     *
     * @param rates The rates to be checked. List<Entry>
     * @param date The date the rate has to be valid at. Date
     * @return The index of the first valid entry. int
     */
    private int matchValidateAt(final List<Entry> rates, final Date date) {
        final Valid_at va = new Valid_at(date);
        for (int i = 0; i < rates.size(); i++) {
            if (va.operator(rates.get(i)))
                return i;
        }
        return -1;
    }
}
