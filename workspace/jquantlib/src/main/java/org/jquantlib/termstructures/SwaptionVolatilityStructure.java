package org.jquantlib.termstructures;

import org.jquantlib.QL;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.termstructures.volatilities.SmileSection;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.util.Pair;

public abstract class SwaptionVolatilityStructure extends AbstractTermStructure {

    private final BusinessDayConvention bdc_;

    public SwaptionVolatilityStructure(final DayCounter dc, final BusinessDayConvention bdc) {
        super(dc);
        this.bdc_ = bdc;
    }

    public SwaptionVolatilityStructure(final Date referenceDate, final Calendar calendar, final DayCounter dc, final BusinessDayConvention bdc) {
        super(referenceDate, calendar, dc);
        this.bdc_ = bdc;
    }

    public SwaptionVolatilityStructure(final int settlementDays, final Calendar calendar, final DayCounter dc, final BusinessDayConvention bdc) {
        super(settlementDays, calendar, dc);
        this.bdc_ = bdc;
    }

    // ! returns the volatility for a given option time and swapLength

    public double volatility(final double optionTime, final double swapLength, final double strike) {
        return volatility(optionTime, swapLength, strike, false);
    }

    // ! returns the Black variance for a given option time and swapLength
    public abstract double blackVariance(double optionTime, double swapLength, double strike, boolean extrapolate);

    public double blackVariance(final double optionTime, final double swapLength, final double strike) {

        return blackVariance(optionTime, swapLength, strike, false);
    }

    //FIXME:.....
    // overloaded (at least) in SwaptionVolCube2
    /*
     * public SmileSection smileSection( Date optionDate, Period swapTenor) {
     *
     * } Pair<Double, Double> p = null;//convertDates(optionDate, swapTenor); return smileSectionImpl(p.first, p.second); }
     */

    // ! returns the volatility for a given option tenor and swap tenor
    public double volatility(final Period optionTenor, final Period swapTenor, final double strike) {
        return volatility(optionTenor, swapTenor, strike, false);
    }

    // ! returns the Black variance for a given option tenor and swap tenor

    public double blackVariance(final Period optionTenor, final Period swapTenor, final double strike) {
        return blackVariance(optionTenor, swapTenor, strike, false);
    }

    // @}
    // ! \name Limits
    // @{
    // ! the largest length for which the term structure can return vols
    public abstract Period maxSwapTenor();

    // ! the largest swapLength for which the term structure can return vols
    // ! the minimum strike for which the term structure can return vols
    public abstract double minStrike();

    // ! the maximum strike for which the term structure can return vols
    public abstract double maxStrike();

    // @}

    // ! the business day convention used for option date calculation
    public abstract BusinessDayConvention businessDayConvention();

    // ! implements the conversion between optionTenors and optionDates
    // public abstract Date optionDateFromTenor( Period optionTenor);

    // ! return smile section
    protected abstract SmileSection smileSectionImpl(double optionTime, double swapLength);

    protected abstract SmileSection smileSectionImpl(Date optionDate, Period swapTenor);

    // ! implements the actual volatility calculation in derived classes
    public abstract double volatilityImpl(double optionTime, double swapLength, double strike);

    protected double volatilityImpl(final Date optionDate, final Period swapTenor, final double strike) {
        final Pair<Double, Double> p = convertDates(optionDate, swapTenor);
        return volatilityImpl(p.first(), p.second(), strike);
    }

    public Date optionDateFromTenor(final Period optionTenor) {
        return calendar().advance(referenceDate(), optionTenor, businessDayConvention());
    }

    public double volatility(final double optionTime, final double swapLength, final double strike, final boolean extrapolate) {
        checkRange(optionTime, swapLength, strike, extrapolate);
        return volatilityImpl(optionTime, swapLength, strike);
    }

    public double blackVariance(final double optionTime, final double swapLength, final double strike, final Boolean extrapolate) {
        checkRange(optionTime, swapLength, strike, extrapolate);
        final double vol = volatilityImpl(optionTime, swapLength, strike);
        return vol * vol * optionTime;
    }

    public double volatility(final Date optionDate, final Period swapTenor, final double strike, final boolean extrapolate) {
        checkRange(optionDate, swapTenor, strike, extrapolate);
        return volatilityImpl(optionDate, swapTenor, strike);
    }

    public double blackVariance(final Date optionDate, final Period swapTenor, final double strike, final boolean extrapolate) {
        final double vol = volatility(optionDate, swapTenor, strike, extrapolate);
        final Pair<Double, Double> p = convertDates(optionDate, swapTenor);
        return vol * vol * p.first();
    }

    public double volatility(final Period optionTenor, final Period swapTenor, final double strike, final boolean extrapolate) {
        final Date optionDate = optionDateFromTenor(optionTenor);
        return volatility(optionDate, swapTenor, strike, extrapolate);
    }

    public double blackVariance(final Period optionTenor, final Period swapTenor, final double strike, final boolean extrapolate) {
        final Date optionDate = optionDateFromTenor(optionTenor);
        final double vol = volatility(optionDate, swapTenor, strike, extrapolate);
        final Pair<Double, Double> p = convertDates(optionDate, swapTenor);
        return vol * vol * p.first();
    }

    public SmileSection smileSection(final Period optionTenor, final Period swapTenor) {
        final Date optionDate = optionDateFromTenor(optionTenor);
        return smileSectionImpl(optionDate, swapTenor);
    }

    public void checkRange(final double optionTime, final double swapLength, final double k, final boolean extrapolate) {
        super.checkRange(optionTime, extrapolate);
        if (swapLength < 0.0) {
            throw new IllegalArgumentException("negative swapLength (" + swapLength + ") given");
        }
        if (!extrapolate && !allowsExtrapolation() && swapLength > maxSwapLength()) {
            throw new IllegalArgumentException("swapLength (" + swapLength + ") is past max curve swapLength (" + maxSwapLength()
                    + ")");
        }
        if (!extrapolate && !allowsExtrapolation() && (k < minStrike() || k > maxStrike())) {
            throw new IllegalArgumentException("strike (" + k + ") is outside the curve domain [" + minStrike() + "," + maxStrike()
                    + "]");
        }
    }

    public double maxSwapLength() {
        return timeFromReference(referenceDate().add(maxSwapTenor()));
    }

    public Pair<Double, Double> convertDates(final Date optionDate, final Period swapTenor) {
        final Date end = optionDate.add(swapTenor);
        // TODO: code review :: please verify against QL/C++ code
        QL.require(end.gt(optionDate) , "negative swap tenorgiven"); // TODO: message
        final double optionTime = timeFromReference(optionDate);
        final double timeLength = dayCounter().yearFraction(optionDate, end);
        return new Pair<Double, Double>(optionTime, timeLength);
    }

    protected void checkRange(final Date optionDate, final Period swapTenor, final double k, final boolean extrapolate) {
        super.checkRange(timeFromReference(optionDate), extrapolate);
        if (swapTenor.length() <= 0) {
            throw new IllegalArgumentException("negative swap tenor (" + swapTenor + ") given");
        }
        if (!extrapolate && !allowsExtrapolation() && swapTenor.gt(maxSwapTenor())) {
            throw new IllegalArgumentException("swap tenor (" + swapTenor + ") is past max tenor (" + maxSwapTenor() + ")");
        }
        //TODO: review
        if (!extrapolate && !allowsExtrapolation() && (k >= minStrike() && k <= maxStrike())) {
            throw new IllegalArgumentException("strike (" + k + ") is outside the curve domain [" + minStrike() + "," + maxStrike()
                    + "]");
        }

    }
}
