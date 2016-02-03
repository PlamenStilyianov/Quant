/*
Copyright (C) 2007 Richard Gomes

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
package org.jquantlib.time.calendars;


import org.jquantlib.lang.annotation.QualityAssurance;
import org.jquantlib.lang.annotation.QualityAssurance.Quality;
import org.jquantlib.lang.annotation.QualityAssurance.Version;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Date;
import org.jquantlib.time.Month;
import org.jquantlib.time.Weekday;

/**
 * South African calendar
 * <p>
 * Holidays:
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's day, January 1</li>
 * <li>Human Rights Day, March 21</li>
 * <li>Good Friday, Friday before Easter Monday</li>
 * <li>Family Day/Easter Monday</li>
 * <li>Freedom Day, April 27</li>
 * <li>Worker's Day, May 1</li>
 * <li>Youth Day, June 16</li>
 * <li>National Women's Day, August 9</li>
 * <li>Heritage Day/National Braai Day, September 24</li>
 * <li>Day of Reconciliation, December 16</li>
 * <li>Christmas Day, December 25</li>
 * <li>Day of Goodwill, December 26</li>
 * </ul>
 *
 * Any holiday falling on a Sunday moves to the following Monday
 *
 * @category calendars
 *
 * @see <a href="http://en.wikipedia.org/wiki/Public_holidays_in_South_Africa">Hoidays in South Africa</a>
 *
 * @author Bernard Kruger
 * @author Richard Gomes
 */
@QualityAssurance(quality = Quality.Q3_DOCUMENTATION, version = Version.V097, reviewers = {"no reviewers yet"})
public class SouthAfrica extends Calendar {

    //
    // public constructors
    //
    public SouthAfrica() {
        impl = new Impl();

        // once-off holidays
        addHoliday(new Date(31,12,1999));   // Y2K changeover
        addHoliday(new Date(2,1,2000));     // Y2K changeover
        addHoliday(new Date(3,1,2000));     // Y2K changeover
        addHoliday(new Date(2,5,2008));     // Human Rights Day and Good Friday was on same day in 2008
        addHoliday(new Date(22,4,2009));    // 2009 election day
    }

    //
    // private final inner classes
    //
    private final class Impl extends WesternImpl {

        @Override
        public String name() {
            return "SouthAfrica";
        }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                    // New Year's Day, January 1
                    || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                    // Human Rights Day, March 21
                    || ((d == 21 || (d == 22 && w == Weekday.Monday)) && m == Month.March)
                    // Good Friday
                    || (dd == em-3)
                    // Easter Monday/Family Day
                    || (dd == em)
                    // Freedom Day, April 27
                    || ((d == 27 || (d == 28 && w == Weekday.Monday)) && m == Month.April)
                    // Worker's Day, May 1
                    || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.May)
                    // Youth Day, June 16
                    || ((d == 16 || (d == 17 && w == Weekday.Monday)) && m == Month.June)
                    // National Women's Day, August 9
                    || ((d == 9 || (d == 10 && w == Weekday.Monday)) && m == Month.August)
                    // Heritage Day, September 24
                    || ((d == 24 || (d == 25 && w == Weekday.Monday)) && m == Month.September)
                    // Day of Reconciliation, December 16
                    || ((d == 16 || (d == 17 && w == Weekday.Monday)) && m == Month.December)
                    // Christmas Day, December 25
                    || ((d == 25 || (d == 26 && w == Weekday.Monday)) && m == Month.December)
                    // Day of Goodwill, December 26
                    || ((d == 26 || (d == 27 && w == Weekday.Monday)) && m == Month.December)) {
                return false;
            }
            return true;
        }
    }
}

////2009
//        addHoliday(DateParser.parseISO((new LocalDate(2009,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,4,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,4,13)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,4,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,8,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2009,12,25)).toString()));
//
//        //2010
//        addHoliday(DateParser.parseISO((new LocalDate(2010,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,3,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,4,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,4,5)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2010,12,27)).toString()));
//
//        //2011
//        addHoliday(DateParser.parseISO((new LocalDate(2011,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,4,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,4,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,5,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2011,12,26)).toString()));
//
//        //2012
//        addHoliday(DateParser.parseISO((new LocalDate(2012,1,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,4,6)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,4,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,12,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2012,12,26)).toString()));
//
//        //2013
//        addHoliday(DateParser.parseISO((new LocalDate(2013,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,3,29)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,4,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,6,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2013,12,26)).toString()));
//
//        //2014
//        addHoliday(DateParser.parseISO((new LocalDate(2014,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,4,18)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,4,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,4,28)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2014,12,26)).toString()));
//
//        //2015
//        addHoliday(DateParser.parseISO((new LocalDate(2015,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,4,3)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,4,6)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,8,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2015,12,25)).toString()));
//
//        //2016
//        addHoliday(DateParser.parseISO((new LocalDate(2016,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,3,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,3,28)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,5,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2016,12,26)).toString()));
//
//        //2017
//        addHoliday(DateParser.parseISO((new LocalDate(2017,1,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,4,14)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,4,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,9,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2017,12,26)).toString()));
//
//        //2018
//        addHoliday(DateParser.parseISO((new LocalDate(2018,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,3,30)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,4,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,12,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2018,12,26)).toString()));
//
//        //2019
//        addHoliday(DateParser.parseISO((new LocalDate(2019,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,4,19)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,4,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,6,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2019,12,26)).toString()));
//
//        //2020
//        addHoliday(DateParser.parseISO((new LocalDate(2020,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,4,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,4,13)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,8,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2020,12,25)).toString()));
//
//        //2021
//        addHoliday(DateParser.parseISO((new LocalDate(2021,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,3,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,4,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,4,5)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2021,12,27)).toString()));
//
//        //2022
//        addHoliday(DateParser.parseISO((new LocalDate(2022,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,4,15)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,4,18)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,5,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2022,12,26)).toString()));
//
//        //2023
//        addHoliday(DateParser.parseISO((new LocalDate(2023,1,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,4,7)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,4,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,9,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2023,12,26)).toString()));
//
//        //2024
//        addHoliday(DateParser.parseISO((new LocalDate(2024,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,3,29)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,4,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,6,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2024,12,26)).toString()));
//
//        //2025
//        addHoliday(DateParser.parseISO((new LocalDate(2025,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,4,18)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,4,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,4,28)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2025,12,26)).toString()));
//
//        //2026
//        addHoliday(DateParser.parseISO((new LocalDate(2026,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,4,3)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,4,6)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,8,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2026,12,25)).toString()));
//
//        //2027
//        addHoliday(DateParser.parseISO((new LocalDate(2027,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,3,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,3,26)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,3,29)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2027,12,27)).toString()));
//
//        //2028
//        addHoliday(DateParser.parseISO((new LocalDate(2028,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,4,14)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,4,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,9,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2028,12,26)).toString()));
//
//        //2029
//        addHoliday(DateParser.parseISO((new LocalDate(2029,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,3,30)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,4,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,12,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2029,12,26)).toString()));
//
//        //2030
//        addHoliday(DateParser.parseISO((new LocalDate(2030,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,4,19)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,4,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,6,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2030,12,26)).toString()));
//
//        //2031
//        addHoliday(DateParser.parseISO((new LocalDate(2031,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,4,11)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,4,14)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,4,28)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2031,12,26)).toString()));
//
//        //2032
//        addHoliday(DateParser.parseISO((new LocalDate(2032,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,3,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,3,26)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,3,29)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2032,12,27)).toString()));
//
//        //2033
//        addHoliday(DateParser.parseISO((new LocalDate(2033,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,4,15)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,4,18)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,5,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2033,12,26)).toString()));
//
//        //2034
//        addHoliday(DateParser.parseISO((new LocalDate(2034,1,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,4,7)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,4,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,9,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2034,12,26)).toString()));
//
//        //2035
//        addHoliday(DateParser.parseISO((new LocalDate(2035,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,3,23)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,3,26)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,12,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2035,12,26)).toString()));
//
//        //2036
//        addHoliday(DateParser.parseISO((new LocalDate(2036,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,4,11)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,4,14)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,4,28)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2036,12,26)).toString()));
//
//        //2037
//        addHoliday(DateParser.parseISO((new LocalDate(2037,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,4,3)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,4,6)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,8,10)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2037,12,25)).toString()));
//
//        //2038
//        addHoliday(DateParser.parseISO((new LocalDate(2038,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,3,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,4,23)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,4,26)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2038,12,27)).toString()));
//
//        //2039
//        addHoliday(DateParser.parseISO((new LocalDate(2039,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,4,8)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,4,11)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,5,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2039,12,26)).toString()));
//
//        //2040
//        addHoliday(DateParser.parseISO((new LocalDate(2040,1,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,3,30)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,4,2)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,4,27)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,12,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2040,12,26)).toString()));
//
//        //2041
//        addHoliday(DateParser.parseISO((new LocalDate(2041,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,4,19)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,4,22)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,6,17)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,8,9)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2041,12,26)).toString()));
//
//        //2042
//        addHoliday(DateParser.parseISO((new LocalDate(2042,1,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,3,21)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,4,4)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,4,7)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,4,28)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,5,1)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,6,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,9,24)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,12,16)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,12,25)).toString()));
//        addHoliday(DateParser.parseISO((new LocalDate(2042,12,26)).toString()));
