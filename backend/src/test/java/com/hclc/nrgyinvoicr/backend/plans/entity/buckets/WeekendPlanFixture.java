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

    static String[] expectedFlattenedWeekendDescriptions() {
        return new String[]{
                "01.01 - 12.31, Monday - Friday, 0h - 23h",
                "01.01 - 12.31, Saturday - Sunday, 0h - 23h"
        };
    }

    static String[] expectedOptimizedFlattenedWeekendDescriptions() {
        return new String[]{
                "Monday - Friday",
                "Saturday - Sunday"
        };
    }
}
