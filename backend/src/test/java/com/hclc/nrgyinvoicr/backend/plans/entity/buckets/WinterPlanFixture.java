package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class WinterPlanFixture {

    static List<ExpressionLine> winterPlan() {
        return Stream.of(
                new ExpressionLine(1, ".04.01-10.31"),
                new ExpressionLine(2, "..1-7"),
                new ExpressionLine(3, "...0-23:0.18910"),
                new ExpressionLine(4, ".11.01-03.31"),
                new ExpressionLine(5, "..1-7"),
                new ExpressionLine(6, "...0-23:0.17720")
        ).collect(toList());
    }

    static List<ExpectedFlattened> winterFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("04.01 - 10.31, Monday - Sunday, 0h - 23h", reference.getSummer()),
                new ExpectedFlattened("11.01 - 03.31, Monday - Sunday, 0h - 23h", reference.getWinter())
        ).collect(toList());
    }

    static List<ExpectedFlattened> winterOptimizedFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("04.01 - 10.31", reference.getSummer()),
                new ExpectedFlattened("11.01 - 03.31", reference.getWinter())
        ).collect(toList());
    }
}
