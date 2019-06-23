package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.NightPlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.StablePlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.WeekendPlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.WinterPlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.WinterWeekendNightPlanFixture.*;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class BucketsTest {

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("flattenedParameters")
    void shouldFlattenBuckets(String description, List<ExpressionLine> lines, String[] expectedDescriptions) {
        List<Flattened> flatteneds = Buckets.forExpressionLines(lines).flatten();

        assertThat(flatteneds.stream().map(Flattened::getDescription).collect(toList())).containsExactly(expectedDescriptions);
    }

    private static Stream<Arguments> flattenedParameters() {
        return Stream.of(
                of("Stable Plan", arrangeStablePlan(), expectedFlattenedStableDescriptions()),
                of("Night Plan", arrangeNightPlan(), expectedFlattenedNightDescriptions()),
                of("Winter Plan", arrangeWinterPlan(), expectedFlattenedWinterDescriptions()),
                of("Weekend Plan", arrangeWeekendPlan(), expectedFlattenedWeekendDescriptions()),
                of("Winter/Weekend/Night Plan", arrangeWinterWeekendNightPlan(), expectedFlattenedWinterWeekendNightDescriptions())
        );
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("optimizedFlattenedParameters")
    void shouldFlattenOptimizedBuckets(String description, List<ExpressionLine> lines, String[] expectedDescriptions) {
        List<Flattened> flatteneds = Buckets.forExpressionLines(lines).optimized().flatten();

        assertThat(flatteneds.stream().map(Flattened::getDescription).collect(toList())).containsExactly(expectedDescriptions);
    }

    private static Stream<Arguments> optimizedFlattenedParameters() {
        return Stream.of(
                of("Stable Plan", arrangeStablePlan(), expectedOptimizedFlattenedStableDescriptions()),
                of("Night Plan", arrangeNightPlan(), expectedOptimizedFlattenedNightDescriptions()),
                of("Weekend Plan", arrangeWeekendPlan(), expectedOptimizedFlattenedWeekendDescriptions()),
                of("Winter Plan", arrangeWinterPlan(), expectedOptimizedFlattenedWinterDescriptions()),
                of("Winter/Weekend/Night Plan", arrangeWinterWeekendNightPlan(), expectedOptimizedFlattenedWinterWeekendNightDescriptions())
        );
    }
}