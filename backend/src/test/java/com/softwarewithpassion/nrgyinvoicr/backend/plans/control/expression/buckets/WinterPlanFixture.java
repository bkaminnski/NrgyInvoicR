package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class WinterPlanFixture {
    private static final BigDecimal SUMMER_PRICE = new BigDecimal("0.18910");
    private static final BigDecimal WINTER_PRICE = new BigDecimal("0.17720");

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

    static List<ExpectedFlattenedBucket> winterFlattened(ReferenceNumberOfValues reference) {
        return Stream.of(
                new ExpectedFlattenedBucket("04.01 - 10.31", SUMMER_PRICE, reference.getSummer(), SUMMER_PRICE.multiply(reference.getSummer())),
                new ExpectedFlattenedBucket("11.01 - 03.31", WINTER_PRICE, reference.getWinter(), WINTER_PRICE.multiply(reference.getWinter()))
        ).collect(toList());
    }
}
