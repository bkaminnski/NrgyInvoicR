package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class NightPlanFixture {

    static List<ExpressionLine> nightPlan() {
        return Stream.of(
                new ExpressionLine(1, ".01.01-12.31"),
                new ExpressionLine(2, "..1-7"),
                new ExpressionLine(3, "...8-22:0.19288"),
                new ExpressionLine(4, "...23-7:0.17531")
        ).collect(toList());
    }

    static List<ExpectedFlattened> nightFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("01.01 - 12.31, Monday - Sunday, 8h - 22h", reference.getDay()),
                new ExpectedFlattened("01.01 - 12.31, Monday - Sunday, 23h - 7h", reference.getNight())
        ).collect(toList());
    }

    static List<ExpectedFlattened> nightOptimizedFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattened("8h - 22h", reference.getDay()),
                new ExpectedFlattened("23h - 7h", reference.getNight())
        ).collect(toList());
    }
}
