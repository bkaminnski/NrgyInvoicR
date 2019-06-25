package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class WeekendPlanFixture {
    private static final BigDecimal WEEK_DAY_PRICE = new BigDecimal("0.19131");
    private static final BigDecimal WEEKEND_PRICE = new BigDecimal("0.17617");

    static List<ExpressionLine> weekendPlan() {
        return Stream.of(
                new ExpressionLine(1, ".01.01-12.31"),
                new ExpressionLine(2, "..1-5"),
                new ExpressionLine(3, "...0-23:0.19131"),
                new ExpressionLine(4, "..6-7"),
                new ExpressionLine(5, "...0-23:0.17617")
        ).collect(toList());
    }

    static List<ExpectedFlattenedBucket> weekendFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattenedBucket("Monday - Friday", WEEK_DAY_PRICE, reference.getWeekDay(), WEEK_DAY_PRICE.multiply(reference.getWeekDay())),
                new ExpectedFlattenedBucket("Saturday - Sunday", WEEKEND_PRICE, reference.getWeekend(), WEEKEND_PRICE.multiply(reference.getWeekend()))
        ).collect(toList());
    }
}
