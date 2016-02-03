/*
 Copyright (C) 2009 Apratim Rajendra

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

package org.jquantlib.samples;

import org.jquantlib.QL;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.Weekday;

/**
 *
 * This class explores the functionalities provided by Date interface.
 *
 * @author Apratim Rajendra
 *
 */

public class Dates implements Runnable {

    public static void main(final String[] args) {
        new Dates().run();
    }

    public void run() {

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();

        //Let's take todays date to explore the date interface
        final Date today = Date.todaysDate();
        System.out.println("Today's date is = "+today);

        //Get the Month enum and the month's integer equivalent of this date
        final Month month = today.month();
        final int integerEquivalentOfMonth = today.month().value();
        System.out.println("The month of today's date is = "+month);
        System.out.println("The integer equivalent of this month as obtained from the date is = "+integerEquivalentOfMonth);
        System.out.println("The integer equivalent of the date as obtained from the Month is also = "+month.value());

        //Get the weekday
        final Weekday weekDayOfThisDate = today.weekday();
        System.out.println("The weekday of this date is = "+weekDayOfThisDate);

        //Get the day of the date for it's month
        System.out.println("The day of the date as a day in this date's month(1-31) is = "+today.dayOfMonth());

        //Get the day of the date for it's year
        System.out.println("The day of the date as day in it's year(1-366) is = "+today.dayOfYear());

        //Check if the date belongs to a leap year
        if (Date.isLeap(today.year())) {
            System.out.println("Today's date belong to leap year");
        }

        //Get the next nextWeekdayDate of this date's month having weekday as TUESDAY
        final Date nextWeekdayDate = Date.nextWeekday(today,Weekday.Tuesday);
        System.out.println("The date of the next weekday is = "+nextWeekdayDate);

        //Get the 4th weekdayDate of this date's month having weekday as TUESDAY
        final Date fourthWeekdayDate = Date.nthWeekday(4, Weekday.Tuesday, today.month(), today.year());
        System.out.println("The fourthWeekdayDate which is TUESDAY is = "+fourthWeekdayDate);

        //Let's try getting the first date of the month to which today's date belong to
        final Date dateEndOfMonth = Date.endOfMonth(today);
        final int dayOfEndOfMonth = dateEndOfMonth.dayOfMonth();
        final Date dateStartOfMonth = dateEndOfMonth.add(-dayOfEndOfMonth+1);
        System.out.println("The first date of the month to which todays date belong to is = "+dateStartOfMonth);

        //Let's try getting the first date of the month to which today's date belong to using Period
        final Period period = new Period(-today.dayOfMonth()+1,TimeUnit.Days);
        Date dateStartOfMonthUsingPeriod = today.add(period);
        System.out.println("The first date of the month to which today's date belong to using period is = "+dateStartOfMonthUsingPeriod);

        //Let's use adjust function to get the same result as obtained above
        dateStartOfMonthUsingPeriod = today.add(period);
        System.out.println("The first date of the month to which today's date belong to using adjustment of period is = "+dateStartOfMonthUsingPeriod);


        //<================Let's do some date comparisons========================>

        //Startdate of the month is less than endDate of this dates month
        if(dateStartOfMonthUsingPeriod.le(dateEndOfMonth)){
            System.out.println("Start date is less than end date?");
        }

        //EndDate of the month is greater than Startdate  of this dates month
        if(dateEndOfMonth.ge(dateStartOfMonthUsingPeriod)){
            System.out.println("End date is greater than start date");
        }

        //Let's increment today's date till the endOfMonth
        Date date = today.clone();
        while (!date.eq(dateEndOfMonth)) {
            date.inc();
        }
        System.out.println("The date variable has been incremented to endOfMonth and is = "+date);

        // Let's decrement the same till beginning of the month
        date = today.clone();
        while (!date.eq(dateStartOfMonth)) {
            date.dec();
        }
        System.out.println("The date variable has been decremented to startOfMonth and is = "+date);

        // Let's update the todaysDate with the date just after it as the date is updatable
        today.addAssign(1);
        System.out.println("Today's date dateToday has been updated to = "+today);

        //Let's change the dateToday to current date
        today.subAssign(1);
        System.out.println("Today's date dateToday has been updated to = "+today);

        clock.stopClock();
        clock.log();

    }

}
