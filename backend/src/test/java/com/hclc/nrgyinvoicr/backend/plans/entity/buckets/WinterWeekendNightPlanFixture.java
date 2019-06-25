package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class WinterWeekendNightPlanFixture {
    private static final BigDecimal SUMMER_WEEK_DAY_DAY_PRICE = new BigDecimal("0.19111");
    private static final BigDecimal SUMMER_WEEK_DAY_NIGHT_PRICE = new BigDecimal("0.17222");
    private static final BigDecimal SUMMER_WEEKEND_DAY_PRICE = new BigDecimal("0.19333");
    private static final BigDecimal SUMMER_WEEKEND_NIGHT_PRICE = new BigDecimal("0.17444");
    private static final BigDecimal WINTER_WEEK_DAY_DAY_PRICE = new BigDecimal("0.19555");
    private static final BigDecimal WINTER_WEEK_DAY_NIGHT_PRICE = new BigDecimal("0.17666");
    private static final BigDecimal WINTER_WEEKEND_DAY_PRICE = new BigDecimal("0.19777");
    private static final BigDecimal WINTER_WEEKEND_NIGHT_PRICE = new BigDecimal("0.17888");

    static List<ExpressionLine> winterWeekendNightPlan() {
        return Stream.of(
                new ExpressionLine(1, ".04.01-10.31"),
                new ExpressionLine(2, "..1-5"),
                new ExpressionLine(3, "...8-22:0.19111"),
                new ExpressionLine(4, "...23-7:0.17222"),
                new ExpressionLine(5, "..6-7"),
                new ExpressionLine(6, "...8-22:0.19333"),
                new ExpressionLine(7, "...23-7:0.17444"),
                new ExpressionLine(8, ".11.01-03.31"),
                new ExpressionLine(9, "..1-5"),
                new ExpressionLine(10, "...8-22:0.19555"),
                new ExpressionLine(11, "...23-7:0.17666"),
                new ExpressionLine(12, "..6-7"),
                new ExpressionLine(13, "...8-22:0.19777"),
                new ExpressionLine(14, "...23-7:0.17888")
        ).collect(toList());
    }

    static List<ExpectedFlattened> winterWeekendNightFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("04.01 - 10.31, Monday - Friday, 8h - 22h", SUMMER_WEEK_DAY_DAY_PRICE, reference.getSummerWeekDayDay(), SUMMER_WEEK_DAY_DAY_PRICE.multiply(reference.getSummerWeekDayDay())),
                new ExpectedFlattened("04.01 - 10.31, Monday - Friday, 23h - 7h", SUMMER_WEEK_DAY_NIGHT_PRICE, reference.getSummerWeekDayNight(), SUMMER_WEEK_DAY_NIGHT_PRICE.multiply(reference.getSummerWeekDayNight())),
                new ExpectedFlattened("04.01 - 10.31, Saturday - Sunday, 8h - 22h", SUMMER_WEEKEND_DAY_PRICE, reference.getSummerWeekendDay(), SUMMER_WEEKEND_DAY_PRICE.multiply(reference.getSummerWeekendDay())),
                new ExpectedFlattened("04.01 - 10.31, Saturday - Sunday, 23h - 7h", SUMMER_WEEKEND_NIGHT_PRICE, reference.getSummerWeekendNight(), SUMMER_WEEKEND_NIGHT_PRICE.multiply(reference.getSummerWeekendNight())),
                new ExpectedFlattened("11.01 - 03.31, Monday - Friday, 8h - 22h", WINTER_WEEK_DAY_DAY_PRICE, reference.getWinterWeekDayDay(), WINTER_WEEK_DAY_DAY_PRICE.multiply(reference.getWinterWeekDayDay())),
                new ExpectedFlattened("11.01 - 03.31, Monday - Friday, 23h - 7h", WINTER_WEEK_DAY_NIGHT_PRICE, reference.getWinterWeekDayNight(), WINTER_WEEK_DAY_NIGHT_PRICE.multiply(reference.getWinterWeekDayNight())),
                new ExpectedFlattened("11.01 - 03.31, Saturday - Sunday, 8h - 22h", WINTER_WEEKEND_DAY_PRICE, reference.getWinterWeekendDay(), WINTER_WEEKEND_DAY_PRICE.multiply(reference.getWinterWeekendDay())),
                new ExpectedFlattened("11.01 - 03.31, Saturday - Sunday, 23h - 7h", WINTER_WEEKEND_NIGHT_PRICE, reference.getWinterWeekendNight(), WINTER_WEEKEND_NIGHT_PRICE.multiply(reference.getWinterWeekendNight()))
        ).collect(toList());
    }
}
