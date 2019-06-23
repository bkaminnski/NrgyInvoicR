package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import static java.time.ZoneId.systemDefault;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class BucketsTest {

    private static final ZonedDateTime START_OF_2019 = LocalDateTime.parse("2019-01-01T00:00:00.000").atZone(systemDefault());
    private static final ZonedDateTime START_OF_2020 = LocalDateTime.parse("2020-01-01T00:00:00.000").atZone(systemDefault());

    @ParameterizedTest(name = "{index}. for {0}")
    @MethodSource("flattenedParameters")
    void shouldFlattenBuckets(String description, List<ExpressionLine> lines, String[] expectedDescriptions) {
        List<Flattened> flatteneds = Buckets.forExpressionLines(lines).flatten();

        assertThat(flatteneds.stream().map(Flattened::getDescription).collect(toList())).containsExactly(expectedDescriptions);
    }

    private static Stream<Arguments> flattenedParameters() {
        return Stream.of(
                of("Stable Plan", stablePlan(), expectedFlattenedStableDescriptions()),
                of("Night Plan", nightPlan(), expectedFlattenedNightDescriptions()),
                of("Winter Plan", winterPlan(), expectedFlattenedWinterDescriptions()),
                of("Weekend Plan", weekendPlan(), expectedFlattenedWeekendDescriptions()),
                of("Winter/Weekend/Night Plan", winterWeekendNightPlan(), expectedFlattenedWinterWeekendNightDescriptions())
        );
    }

    @ParameterizedTest(name = "{index}. for {0}")
    @MethodSource("optimizedFlattenedParameters")
    void shouldFlattenOptimizedBuckets(String description, List<ExpressionLine> lines, String[] expectedDescriptions) {
        List<Flattened> flatteneds = Buckets.forExpressionLines(lines).optimized().flatten();

        assertThat(flatteneds.stream().map(Flattened::getDescription).collect(toList())).containsExactly(expectedDescriptions);
    }

    private static Stream<Arguments> optimizedFlattenedParameters() {
        return Stream.of(
                of("Stable Plan", stablePlan(), expectedOptimizedFlattenedStableDescriptions()),
                of("Night Plan", nightPlan(), expectedOptimizedFlattenedNightDescriptions()),
                of("Weekend Plan", weekendPlan(), expectedOptimizedFlattenedWeekendDescriptions()),
                of("Winter Plan", winterPlan(), expectedOptimizedFlattenedWinterDescriptions()),
                of("Winter/Weekend/Night Plan", winterWeekendNightPlan(), expectedOptimizedFlattenedWinterWeekendNightDescriptions())
        );
    }

    @ParameterizedTest(name = "{index}. for {0}")
    @MethodSource("plans")
    void shouldCountEachValueInBuckets(String description, List<ExpressionLine> lines) {
        Bucket bucket = Buckets.forExpressionLines(lines);

        BigDecimal expectedTotal = addToBucketAValueForEachQuarterOfAnHourIn2019(bucket);

        assertThat(sumUpAllTotals(bucket)).isEqualByComparingTo(expectedTotal);
    }

    @ParameterizedTest(name = "{index}. for {0}")
    @MethodSource("plans")
    void shouldCountEachValueInOptimizedBuckets(String description, List<ExpressionLine> lines) {
        Bucket bucket = Buckets.forExpressionLines(lines).optimized();

        BigDecimal expectedTotal = addToBucketAValueForEachQuarterOfAnHourIn2019(bucket);

        assertThat(sumUpAllTotals(bucket)).isEqualByComparingTo(expectedTotal);
    }


    private static Stream<Arguments> plans() {
        return Stream.of(
                of("Stable Plan", stablePlan()),
                of("Night Plan", nightPlan()),
                of("Weekend Plan", weekendPlan()),
                of("Winter Plan", winterPlan()),
                of("Winter/Weekend/Night Plan", winterWeekendNightPlan())
        );
    }

    private BigDecimal addToBucketAValueForEachQuarterOfAnHourIn2019(Bucket bucket) {
        BigDecimal expectedTotal = ZERO;
        ZonedDateTime readingDate = START_OF_2019;
        while (readingDate.isBefore(START_OF_2020)) {
            expectedTotal = expectedTotal.add(ONE);
            bucket.accept(new ReadingValue(null, readingDate, ONE));
            readingDate = readingDate.plus(15, MINUTES);
        }
        return expectedTotal;
    }

    private BigDecimal sumUpAllTotals(Bucket bucket) {
        return bucket.flatten().stream().map(Flattened::getTotal).reduce(ZERO, BigDecimal::add);
    }
}