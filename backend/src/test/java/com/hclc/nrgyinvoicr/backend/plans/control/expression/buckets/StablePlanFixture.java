package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class StablePlanFixture {
    private static final BigDecimal STABLE_PRICE = new BigDecimal("0.18692");

    static List<ExpressionLine> stablePlan() {
        return Stream.of(
                new ExpressionLine(1, ".01.01-12.31"),
                new ExpressionLine(2, "..1-7"),
                new ExpressionLine(3, "...0-23:0.18692")
        ).collect(toList());
    }

    static List<ExpectedFlattenedBucket> stableFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattenedBucket("Stable", STABLE_PRICE, reference.getAll(), STABLE_PRICE.multiply(reference.getAll()))
        ).collect(toList());
    }
}
