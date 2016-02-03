/*Copyright (C) 2009 Apratim Rajendra

This source code is release under the BSD License.

This file is part of JQuantLib, a free-software/open-source library
for financial quantitative analysts and developers - http://jquantlib.org/

JQuantLib is free software: you can redistribute it and/or modify it
under the terms of the JQuantLib license. You should have received a
copy of the license along with this program; if not, please email
<jquant-devel@lists.sourceforge.net>. The license is also available online at
<http://www.jquantlib.org/index.php/LICENSE.TXT>.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the license for more details.

JQuantLib is based on QuantLib. http://quantlib.org/
When applicable, the original copyright notice follows this notice.
 */
package org.jquantlib.samples;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.samples.util.StopClock;
import org.jquantlib.time.BusinessDayConvention;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.time.calendars.JointCalendar;
import org.jquantlib.time.calendars.UnitedStates;
import org.jquantlib.time.calendars.JointCalendar.JointCalendarRule;
import org.jquantlib.time.calendars.UnitedStates.Market;

/**
 * This class explores the functionalities provided by Calendar interface.
 *
 * @author Apratim Rajendra
 *
 */

public class Calendars implements Runnable {

    public static void main(final String[] args) {
        new Calendars().run();
    }

    public void run() {

        QL.info("::::: " + this.getClass().getSimpleName() + " :::::");

        final StopClock clock = new StopClock();
        clock.startClock();

        // <=========================Basic Calendar functions==============================>
        //Let's take UnitedStates New-York-Stock-Exchange calendar
        final Calendar unitedStatesCalendar = new UnitedStates(Market.NYSE);
        System.out.println("The name of this calendar is = "+ unitedStatesCalendar.name());
        // Let's get the list of holidays from todays date till the date obtained by advancing today's date by 90 days
        final Date dateToday = Date.todaysDate();
        final Date dateAdvanced = dateToday.add(90);
        final List<Date> holidayList = UnitedStates.holidayList(unitedStatesCalendar,dateToday, dateAdvanced, true);
        System. out.println("The holidays between dateToday = " + dateToday+ " till the date dateAdvanced = " + dateAdvanced+ " are as shown below");
        final StringBuffer bufferToHoldHolidays = new StringBuffer();
        for (final Date date : holidayList) {
            bufferToHoldHolidays.append(date +"::");
        }
        System. out.println("The holidays separated by :: are = "+ bufferToHoldHolidays);

        // Let's get the business days between dateToday and dateAdvanced as obtained above by advancing
        // today's date by 90 days as shown below and checking if the incremented date isBusinessDate
        final List<Date> businessDaysInBetweenUsingIsBusDay = new ArrayList<Date>();
        Date dateTodayTemp = dateToday.clone();
        for (; !dateTodayTemp.eq(dateAdvanced); dateTodayTemp.inc()) {
            if (unitedStatesCalendar.isBusinessDay(dateTodayTemp)) {
                businessDaysInBetweenUsingIsBusDay.add(dateTodayTemp);
            }
        }

        final Long sizeOfBusinessDays = Long.parseLong(Integer.toString((businessDaysInBetweenUsingIsBusDay.size())));
        // Let's get the business days between dateToday and dateAdvanced as obtained above by advancing
        // today's date by 90 days using calendar api businessDaysBetween
        final long sizeOfBusinessDaysUsingCalApi = unitedStatesCalendar.businessDaysBetween(dateToday, dateAdvanced, true, true);
        // Check if the sizes obtained are same as they both represent same set of business days
        if (sizeOfBusinessDays == sizeOfBusinessDaysUsingCalApi) {
            System.out.println("The sizes are same");
        }

        // Let's get the same business days using calendars isHoliday API
        final List<Date> businessDaysInBetweenUsingIsHolDay = new ArrayList<Date>();
        dateTodayTemp = dateToday.clone();
        for (; !dateTodayTemp.eq(dateAdvanced); dateTodayTemp.inc()) {
            if (!unitedStatesCalendar.isHoliday(dateTodayTemp)) {
                businessDaysInBetweenUsingIsHolDay.add(dateTodayTemp);
            }
        }

        //Essentially the lists businessDaysInBetweenUsingIsBusDay and businessDaysInBetweenUsingIsHolDay are same
        if (businessDaysInBetweenUsingIsBusDay.equals(businessDaysInBetweenUsingIsHolDay)) {
            System.out.println("The lists businessDaysInBetweenUsingIsBusDay and businessDaysInBetweenUsingIsHolDay are same");
        }

        // <========================Let's use some Calendar airthmetics==============================>
        // Get the first holiday from today
        final Date firstHolidayDate = dateToday.clone();
        while (!unitedStatesCalendar.isHoliday(firstHolidayDate)) {
            firstHolidayDate.inc();
        }

        // Check to see if firstHoliday is holiday
        if (unitedStatesCalendar.isHoliday(firstHolidayDate)) {
            System.out.println("FirstHolidayDate = " + firstHolidayDate+ " is a holiday date");
        }

        // Advance the first holiday date using calendars's advance(date) API and get the nexBusinessDay
        final Date nextBusinessDay = unitedStatesCalendar.advance(firstHolidayDate, new Period(1, TimeUnit.Days));
        if (unitedStatesCalendar.isBusinessDay(nextBusinessDay)) {
            System.out.println("NextBusinessDayFromFirstHolidayFromToday = "+ nextBusinessDay + " is a business date");
        }

        // Adjust todaysDate using different businessDayConventions

        // Using FOLLOWING as a business day convention
        final Date nextFollowingBusinessDay = unitedStatesCalendar.adjust(dateToday,BusinessDayConvention.Following);
        if (unitedStatesCalendar.isBusinessDay(nextFollowingBusinessDay)) {
            System. out.println("NextFollowingBusinessDate = "+ nextFollowingBusinessDay+ " from today is a business date");
        }

        // Using MODIFIED_FOLLOWING as a business day convention
        final Date nextModified_FollowingBusinessDay = unitedStatesCalendar.adjust(dateToday, BusinessDayConvention.ModifiedFollowing);
        if (unitedStatesCalendar.isBusinessDay(nextModified_FollowingBusinessDay)) {
            System.out.println("NextModified_FollowingBusinessDate = "+ nextModified_FollowingBusinessDay+ " from today is a business date");
        }

        // Using PRECEDING as a business day convention
        final Date nextPrecidingBusinessDay = unitedStatesCalendar.adjust(dateToday,BusinessDayConvention.Preceding);
        if (unitedStatesCalendar.isBusinessDay(nextPrecidingBusinessDay)) {
            System.out.println("NextPrecidingBusinessDay = "+ nextPrecidingBusinessDay+ " from today is a business date");
        }

        // Using MODIFIED_PRECEDING as a business day convention
        final Date nextModified_PrecidingBusinessDay = unitedStatesCalendar.adjust(dateToday, BusinessDayConvention.ModifiedPreceding);
        if (unitedStatesCalendar.isBusinessDay(nextModified_PrecidingBusinessDay)) {
            System.out.println("NextModified_PrecidingBusinessDay = "+ nextModified_PrecidingBusinessDay+ " from today is a business date");
        }

        // Using UNADJUSTED as a business day convention
        final Date nextUnadjustedBusinessDay = unitedStatesCalendar.adjust(dateToday,BusinessDayConvention.Unadjusted);
        if (unitedStatesCalendar.isBusinessDay(nextModified_PrecidingBusinessDay)) {
            System.out.println("NextUnadjustedBusinessDay = "+ nextUnadjustedBusinessDay+ " from today is a business date and is same as today");
        }

        // Advance the current date using calendars's advance(date,n,unit) where n = 10 and unit=DAYS
        Date advancedDate = unitedStatesCalendar.advance(dateToday, 10,TimeUnit.Days);
        System.out.println("Next business date when today's date is advanced by 10 days = "+ advancedDate);

        // Advance the current date using calendars's advance(date,n,unit) where n = 10 and unit=WEEKS
        advancedDate = unitedStatesCalendar.advance(dateToday, 10, TimeUnit.Weeks);
        System.out.println("Next business date when today's date is advanced by 10 weeks = "+ advancedDate);

        // Advance the current date using calendars's advance(date,n,unit) where n = 10 and unit=MONTHS
        advancedDate = unitedStatesCalendar.advance(dateToday, 10, TimeUnit.Months);
        System.out.println("Next business date when today's date is advanced by 10 months = "+ advancedDate);

        // Advance the current date using calendars's advance(date,n,unit) where n = 10 and unit=YEARS
        advancedDate = unitedStatesCalendar.advance(dateToday, 10, TimeUnit.Years);
        System.out.println("Next business date when today's date is advanced by 10 years = "+ advancedDate);

        // Advance the current date using calendars's advance(date,period-->lenth=1,Unit=Days,BusinessDayConvention=FOLLOWING)
        advancedDate = unitedStatesCalendar.advance(dateToday,new Period(1, TimeUnit.Days), BusinessDayConvention.Following);
        System.out.println("Next business date when today's date is advanced 1 day = "+ advancedDate);

        // Advance the current date using calendars's advance(date,period-->lenth=1,Unit=WEEKS,BusinessDayConvention=MODIFIED_FOLLOWING)
        advancedDate = unitedStatesCalendar.advance(dateToday,new Period(1, TimeUnit.Weeks),BusinessDayConvention.ModifiedFollowing);
        System.out.println("Next business date when today's date is advanced 1 week = "+ advancedDate);

        // Advance the current date using calendars's advance(date,period-->lenth=1,Unit=MONTHS,BusinessDayConvention=MODIFIED_PRECEDING)
        advancedDate = unitedStatesCalendar.advance(dateToday,new Period(1, TimeUnit.Months),BusinessDayConvention.ModifiedPreceding);
        System.out.println("Next business date when today's date is advanced 1 month = "+ advancedDate);

        // Advance the current date using calendars's advance(date,period-->lenth=1,Unit=YEARS,BusinessDayConvention=PRECEDING)
        advancedDate = unitedStatesCalendar.advance(dateToday,new Period(1, TimeUnit.Years), BusinessDayConvention.Preceding);
        System.out.println("Next business date when today's date is advanced 1 year = "+ advancedDate);

        //<==================Joining Calendars===============================>
        final Calendar unitedStatesCalendarGvtBondCalendar = new UnitedStates(Market.GOVERNMENTBOND);
        final Calendar jointCalendar = new JointCalendar(unitedStatesCalendar,unitedStatesCalendarGvtBondCalendar,JointCalendarRule.JoinBusinessDays);

        //The above joint calendar has been obtained by joining businessdays of newyork stock exchange and government bond calendar
        //which means a day will be a business day for the joint calendar if it is a business day for both of the calendars used
        //(NYSE,GovtBond) and will be a holiday if the day is a holiday in atleast one of the calendars.

        //Let's get the list of holidays for the joint calendar from the date today to date advanced(obtained by advancing date today by 90 days)
        final List<Date> listOfHoliDays = jointCalendar.holidayList(jointCalendar, dateToday, dateAdvanced, true);

        //Now let's get the same holiday list between dateToday and dateAdvanced using isBusinessDay API
        final List<Date> holidayListObtainedUsingCalAPI = new ArrayList<Date>();
        final Date start = dateToday.clone();
        for (; !start.eq(dateAdvanced); start.inc()) {
            if (jointCalendar.isHoliday(start)){
                holidayListObtainedUsingCalAPI.add(start.clone());
            }
        }

        //Essentially both the lists obtained above are same
        if(listOfHoliDays.equals(holidayListObtainedUsingCalAPI)){
            System.out.println("Lists listOfHoliDays and holidayListObtainedUsingCalAPI of joint calendar are same");
        }

        clock.stopClock();
        clock.log();
    }
}