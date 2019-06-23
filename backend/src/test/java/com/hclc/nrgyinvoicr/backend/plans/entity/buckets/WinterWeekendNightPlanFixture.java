package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class WinterWeekendNightPlanFixture {

    static List<ExpressionLine> arrangeWinterWeekendNightPlan() {
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

    static String[] expectedFlattenedWinterWeekendNightDescriptions() {
        return new String[]{
                "04.01 - 10.31, Monday - Friday, 8h - 22h",
                "04.01 - 10.31, Monday - Friday, 23h - 7h",
                "04.01 - 10.31, Saturday - Sunday, 8h - 22h",
                "04.01 - 10.31, Saturday - Sunday, 23h - 7h",
                "11.01 - 03.31, Monday - Friday, 8h - 22h",
                "11.01 - 03.31, Monday - Friday, 23h - 7h",
                "11.01 - 03.31, Saturday - Sunday, 8h - 22h",
                "11.01 - 03.31, Saturday - Sunday, 23h - 7h"
        };
    }

    static String[] expectedOptimizedFlattenedWinterWeekendNightDescriptions() {
        return new String[]{
                "04.01 - 10.31, Monday - Friday, 8h - 22h",
                "04.01 - 10.31, Monday - Friday, 23h - 7h",
                "04.01 - 10.31, Saturday - Sunday, 8h - 22h",
                "04.01 - 10.31, Saturday - Sunday, 23h - 7h",
                "11.01 - 03.31, Monday - Friday, 8h - 22h",
                "11.01 - 03.31, Monday - Friday, 23h - 7h",
                "11.01 - 03.31, Saturday - Sunday, 8h - 22h",
                "11.01 - 03.31, Saturday - Sunday, 23h - 7h"
        };
    }
}
