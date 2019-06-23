package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class WinterPlanFixture {

    static List<ExpressionLine> arrangeWinterPlan() {
        return Stream.of(
                new ExpressionLine(1, ".04.01-10.31"),
                new ExpressionLine(2, "..1-7"),
                new ExpressionLine(3, "...0-23:0.18910"),
                new ExpressionLine(4, ".11.01-03.31"),
                new ExpressionLine(5, "..1-7"),
                new ExpressionLine(6, "...0-23:0.17720")
        ).collect(toList());
    }

    static String[] expectedFlattenedWinterDescriptions() {
        return new String[]{
                "04.01 - 10.31, Monday - Sunday, 0h - 23h",
                "11.01 - 03.31, Monday - Sunday, 0h - 23h"
        };
    }

    static String[] expectedOptimizedFlattenedWinterDescriptions() {
        return new String[]{
                "04.01 - 10.31",
                "11.01 - 03.31"
        };
    }
}
