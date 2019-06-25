package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class NightPlanFixture {
    private static final BigDecimal DAY_PRICE = new BigDecimal("0.19288");
    private static final BigDecimal NIGHT_PRICE = new BigDecimal("0.17531");

    static List<ExpressionLine> nightPlan() {
        return Stream.of(
                new ExpressionLine(1, ".01.01-12.31"),
                new ExpressionLine(2, "..1-7"),
                new ExpressionLine(3, "...8-22:0.19288"),
                new ExpressionLine(4, "...23-7:0.17531")
        ).collect(toList());
    }

    static List<ExpectedFlattenedBucket> nightFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattenedBucket("8h - 22h", DAY_PRICE, reference.getDay(), DAY_PRICE.multiply(reference.getDay())),
                new ExpectedFlattenedBucket("23h - 7h", NIGHT_PRICE, reference.getNight(), NIGHT_PRICE.multiply(reference.getNight()))
        ).collect(toList());
    }
}
