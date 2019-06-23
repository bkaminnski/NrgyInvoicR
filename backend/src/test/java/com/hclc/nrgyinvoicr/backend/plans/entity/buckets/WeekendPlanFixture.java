package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class WeekendPlanFixture {

    static List<ExpressionLine> weekendPlan() {
        return Stream.of(
                new ExpressionLine(1, ".01.01-12.31"),
                new ExpressionLine(2, "..1-5"),
                new ExpressionLine(3, "...0-23:0.19131"),
                new ExpressionLine(4, "..6-7"),
                new ExpressionLine(5, "...0-23:0.17617")
        ).collect(toList());
    }

    static List<ExpectedFlattened> weekendFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("01.01 - 12.31, Monday - Friday, 0h - 23h", reference.getWeekDay()),
                new ExpectedFlattened("01.01 - 12.31, Saturday - Sunday, 0h - 23h", reference.getWeekend())
        ).collect(toList());
    }

    static List<ExpectedFlattened> weekendOptimizedFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("Monday - Friday", reference.getWeekDay()),
                new ExpectedFlattened("Saturday - Sunday", reference.getWeekend())
        ).collect(toList());
    }
}
