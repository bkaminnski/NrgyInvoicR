package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.EnumSet;

import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.BucketsTest.START_OF_2019;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.BucketsTest.START_OF_2020;
import static java.time.DayOfWeek.*;
import static java.time.temporal.ChronoUnit.MINUTES;

class ReferenceNumberOfValues {
    private static final ZonedDateTime START_OF_APRIL_2019 = LocalDateTime.parse("2019-04-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));
    private static final ZonedDateTime START_OF_NOVEMBER_2019 = LocalDateTime.parse("2019-11-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));
    private static final int START_OF_DAY = 8;
    private static final int START_OF_NIGHT = 23;
    private static final EnumSet WEEK_DAYS = EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
    private int all = 0;
    private int day = 0;
    private int night = 0;
    private int weekDay = 0;
    private int weekend = 0;
    private int winter = 0;
    private int summer = 0;
    private int winterWeekDayDay = 0;
    private int winterWeekDayNight = 0;
    private int winterWeekendDay = 0;
    private int winterWeekendNight = 0;
    private int summerWeekDayDay = 0;
    private int summerWeekDayNight = 0;
    private int summerWeekendDay = 0;
    private int summerWeekendNight = 0;

    ReferenceNumberOfValues() {
        ZonedDateTime date = START_OF_2019;
        while (date.isBefore(START_OF_2020)) {
            incrementAll(date);
            date = date.plus(15, MINUTES);
        }
    }

    private void incrementAll(ZonedDateTime date) {
        all++;
        if (itIsWinter(date)) {
            incrementWinter(date);
        } else {
            incrementSummer(date);
        }
    }

    private void incrementWinter(ZonedDateTime date) {
        winter++;
        if (itIsWeekDay(date)) {
            incrementWinterWeekDay(date);
        } else {
            incrementWinterWeekend(date);
        }
    }

    private void incrementWinterWeekDay(ZonedDateTime date) {
        weekDay++;
        if (itIsDay(date)) {
            day++;
            winterWeekDayDay++;
        } else {
            night++;
            winterWeekDayNight++;
        }
    }

    private void incrementWinterWeekend(ZonedDateTime date) {
        weekend++;
        if (itIsDay(date)) {
            day++;
            winterWeekendDay++;
        } else {
            night++;
            winterWeekendNight++;
        }
    }

    private void incrementSummer(ZonedDateTime date) {
        summer++;
        if (itIsWeekDay(date)) {
            incrementSummerWeekDay(date);
        } else {
            incrementSummerWeekend(date);
        }
    }

    private void incrementSummerWeekDay(ZonedDateTime date) {
        weekDay++;
        if (itIsDay(date)) {
            day++;
            summerWeekDayDay++;
        } else {
            night++;
            summerWeekDayNight++;
        }
    }

    private void incrementSummerWeekend(ZonedDateTime date) {
        weekend++;
        if (itIsDay(date)) {
            day++;
            summerWeekendDay++;
        } else {
            night++;
            summerWeekendNight++;
        }
    }

    private boolean itIsWinter(ZonedDateTime date) {
        return date.isBefore(START_OF_APRIL_2019) || !date.isBefore(START_OF_NOVEMBER_2019);
    }

    private boolean itIsWeekDay(ZonedDateTime date) {
        return WEEK_DAYS.contains(date.getDayOfWeek());
    }

    private boolean itIsDay(ZonedDateTime date) {
        return START_OF_DAY <= date.getHour() && date.getHour() < START_OF_NIGHT;
    }

    BigDecimal getAll() {
        return new BigDecimal(all);
    }

    BigDecimal getDay() {
        return new BigDecimal(day);
    }

    BigDecimal getNight() {
        return new BigDecimal(night);
    }

    BigDecimal getWeekDay() {
        return new BigDecimal(weekDay);
    }

    BigDecimal getWeekend() {
        return new BigDecimal(weekend);
    }

    BigDecimal getWinter() {
        return new BigDecimal(winter);
    }

    BigDecimal getSummer() {
        return new BigDecimal(summer);
    }

    BigDecimal getWinterWeekDayDay() {
        return new BigDecimal(winterWeekDayDay);
    }

    BigDecimal getWinterWeekDayNight() {
        return new BigDecimal(winterWeekDayNight);
    }

    BigDecimal getWinterWeekendDay() {
        return new BigDecimal(winterWeekendDay);
    }

    BigDecimal getWinterWeekendNight() {
        return new BigDecimal(winterWeekendNight);
    }

    BigDecimal getSummerWeekDayDay() {
        return new BigDecimal(summerWeekDayDay);
    }

    BigDecimal getSummerWeekDayNight() {
        return new BigDecimal(summerWeekDayNight);
    }

    BigDecimal getSummerWeekendDay() {
        return new BigDecimal(summerWeekendDay);
    }

    BigDecimal getSummerWeekendNight() {
        return new BigDecimal(summerWeekendNight);
    }
}
