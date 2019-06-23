package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class StablePlanFixture {

    static List<ExpressionLine> stablePlan() {
        return Stream.of(
                new ExpressionLine(1, ".01.01-12.31"),
                new ExpressionLine(2, "..1-7"),
                new ExpressionLine(3, "...0-23:0.18692")
        ).collect(toList());
    }

    static List<ExpectedFlattened> stableFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("01.01 - 12.31, Monday - Sunday, 0h - 23h", reference.getAll())
        ).collect(toList());
    }

    static List<ExpectedFlattened> stableOptimizedFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("Stable", reference.getAll())
        ).collect(toList());
    }
}
