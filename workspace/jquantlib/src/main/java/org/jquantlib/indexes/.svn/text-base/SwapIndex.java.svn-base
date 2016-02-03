package org.jquantlib.indexes;

import org.jquantlib.currencies.Currency;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.instruments.MakeVanillaSwap;
import org.jquantlib.instruments.VanillaSwap;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;

/**
 * Base class for swap-rate indexes
 *
 * @author Richard Gomes
 */
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public class SwapIndex extends InterestRateIndex {

    //
    // protected fields
    //

    protected Period tenor;
    protected IborIndex iborIndex;
    protected Period fixedLegTenor;
    protected BusinessDayConvention fixedLegConvention;


    //
    // public constructors
    //

    public SwapIndex(
            final String familyName,
            final Period tenor,
            final /*@Natural*/ int settlementDays,
            final Currency currency,
            final Calendar calendar,
            final Period fixedLegTenor,
            final BusinessDayConvention fixedLegConvention,
            final DayCounter fixedLegDayCounter,
            final IborIndex iborIndex) {
        super(familyName, tenor, settlementDays, currency, calendar, fixedLegDayCounter);
        this.tenor = tenor;
        this.iborIndex = iborIndex;
        this.fixedLegTenor = fixedLegTenor;
        this.fixedLegConvention = fixedLegConvention;

        this.iborIndex.addObserver(this);
        //XXX:registerWith
        //registerWith(this.iborIndex);
    }


    //
    // protected methods
    //

    @Override
    protected /*@Rate*/ double forecastFixing(final Date fixingDate) /* @ReadOnly */ {
        return underlyingSwap(fixingDate).fairRate();
    }


    //
    // public methods
    //

    public IborIndex iborIndex() /* @ReadOnly */ {
        return iborIndex;
    }

    public Period fixedLegTenor() /* @ReadOnly */{
        return fixedLegTenor;
    }

    public BusinessDayConvention fixedLegConvention() /* @ReadOnly */ {
        return fixedLegConvention;
    }

    public VanillaSwap underlyingSwap(final Date fixingDate) /* @ReadOnly */ {
        /*@Rate*/ final double fixedRate = 0.0;
        return new MakeVanillaSwap(tenor, iborIndex, fixedRate)
        .withEffectiveDate(valueDate(fixingDate))
        .withFixedLegCalendar(fixingCalendar())
        .withFixedLegDayCount(dayCounter)
        .withFixedLegTenor(fixedLegTenor)
        .withFixedLegConvention(fixedLegConvention)
        .withFixedLegTerminationDateConvention(fixedLegConvention).value();
    }

    @Override
    public Date maturityDate(final Date valueDate) /* @ReadOnly */ {
        final Date fixDate = fixingDate(valueDate);
        return underlyingSwap(fixDate).maturityDate();
    }



    @Override
    public Handle<YieldTermStructure> termStructure() /* @ReadOnly */ {
        return iborIndex.termStructure();
    }

}
