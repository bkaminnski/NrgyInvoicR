package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.NightPlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.StablePlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.WeekendPlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.WinterPlanFixture.*;
import static com.hclc.nrgyinvoicr.backend.plans.entity.buckets.WinterWeekendNightPlanFixture.*;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class BucketsTest {
    static final ZonedDateTime START_OF_2019 = LocalDateTime.parse("2019-01-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));
    static final ZonedDateTime START_OF_2020 = LocalDateTime.parse("2020-01-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));

    @ParameterizedTest(name = "for {0}")
    @MethodSource("parameters")
    void shouldFlattenBuckets(String description, List<ExpressionLine> lines, boolean optimized, List<ExpectedFlattened> expected) {
        Bucket bucket = arrangeBucket(lines, optimized);

        List<Flattened> flatteneds = bucket.flatten();

        assertDescriptionsAreAsExpected(flatteneds, expected);
        assertTotalsAreAsExpected(flatteneds, expected);
        assertSumOfTotalsIsAsExpected(flatteneds, expected);
    }

    private Bucket arrangeBucket(List<ExpressionLine> lines, boolean optimized) {
        Bucket bucket = Buckets.forExpressionLines(lines);
        bucket = optimized ? bucket.optimized() : bucket;
        addToBucketAValueForEachQuarterOfAnHourIn2019(bucket);
        return bucket;
    }

    private void addToBucketAValueForEachQuarterOfAnHourIn2019(Bucket bucket) {
        ZonedDateTime readingDate = START_OF_2019;
        while (readingDate.isBefore(START_OF_2020)) {
            bucket.accept(new ReadingValue(null, readingDate, ONE));
            readingDate = readingDate.plus(15, MINUTES);
        }
    }

    private void assertDescriptionsAreAsExpected(List<Flattened> flatteneds, List<ExpectedFlattened> expected) {
        List<String> actualDescriptions = flatteneds.stream().map(Flattened::getDescription).collect(toList());
        String[] expectedDescriptions = expected.stream().map(ExpectedFlattened::getDescription).toArray(String[]::new);
        assertThat(actualDescriptions).containsExactly(expectedDescriptions);
    }

    private void assertTotalsAreAsExpected(List<Flattened> flatteneds, List<ExpectedFlattened> expected) {
        List<BigDecimal> actualTotals = flatteneds.stream().map(Flattened::getTotal).collect(toList());
        BigDecimal[] expectedTotals = expected.stream().map(ExpectedFlattened::getTotal).toArray(BigDecimal[]::new);
        assertThat(actualTotals).containsExactly(expectedTotals);
    }

    private void assertSumOfTotalsIsAsExpected(List<Flattened> flatteneds, List<ExpectedFlattened> expected) {
        BigDecimal actualSumOfTotals = flatteneds.stream().map(Flattened::getTotal).reduce(ZERO, BigDecimal::add);
        BigDecimal expectedSumOfTotals = expected.stream().map(ExpectedFlattened::getTotal).reduce(ZERO, BigDecimal::add);
        assertThat(actualSumOfTotals).isEqualByComparingTo(expectedSumOfTotals);
    }

    private static Stream<Arguments> parameters() {
        ReferenceNumberOfValues reference = new ReferenceNumberOfValues();
        return Stream.of(
                of("Stable Plan, unoptimized", stablePlan(), false, stableFlattened(reference)),
                of("Night Plan, unoptimized", nightPlan(), false, nightFlattened(reference)),
                of("Winter Plan, unoptimized", winterPlan(), false, winterFlattened(reference)),
                of("Weekend Plan, unoptimized", weekendPlan(), false, weekendFlattened(reference)),
                of("Winter/Weekend/Night Plan, unoptimized", winterWeekendNightPlan(), false, winterWeekendNightFlattened(reference)),
                of("Stable Plan, optimized", stablePlan(), true, stableOptimizedFlattened(reference)),
                of("Night Plan, optimized", nightPlan(), true, nightOptimizedFlattened(reference)),
                of("Winter Plan, optimized", winterPlan(), true, winterOptimizedFlattened(reference)),
                of("Weekend Plan, optimized", weekendPlan(), true, weekendOptimizedFlattened(reference)),
                of("Winter/Weekend/Night Plan, optimized", winterWeekendNightPlan(), true, winterWeekendNightOptimizedFlattened(reference))
        );
    }
}