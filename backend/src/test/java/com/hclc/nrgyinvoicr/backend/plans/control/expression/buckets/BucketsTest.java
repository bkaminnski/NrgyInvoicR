package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;
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

import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.NightPlanFixture.nightFlattened;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.NightPlanFixture.nightPlan;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.StablePlanFixture.stableFlattened;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.StablePlanFixture.stablePlan;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.WeekendPlanFixture.weekendFlattened;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.WeekendPlanFixture.weekendPlan;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.WinterPlanFixture.winterFlattened;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.WinterPlanFixture.winterPlan;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.WinterWeekendNightPlanFixture.winterWeekendNightFlattened;
import static com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.WinterWeekendNightPlanFixture.winterWeekendNightPlan;
import static java.math.BigDecimal.ONE;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class BucketsTest {
    static final ZonedDateTime START_OF_2019 = LocalDateTime.parse("2019-01-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));
    static final ZonedDateTime START_OF_2020 = LocalDateTime.parse("2020-01-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));

    @ParameterizedTest(name = "for {0}")
    @MethodSource("parameters")
    void shouldFlattenBuckets(String description, List<ExpressionLine> lines, List<ExpectedFlattenedBucket> expected) {
        Bucket bucket = arrangeBucket(lines);

        List<FlattenedBucket> flattenedBuckets = bucket.flatten();

        assertDescriptionsAreAsExpected(flattenedBuckets, expected);
        assertPricesAreAsExpected(flattenedBuckets, expected);
        assertTotalUsagesAreAsExpected(flattenedBuckets, expected);
        assertTotalPricesAreAsExpected(flattenedBuckets, expected);
    }

    private Bucket arrangeBucket(List<ExpressionLine> lines) {
        Bucket bucket = Buckets.forExpressionLines(lines);
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

    private void assertDescriptionsAreAsExpected(List<FlattenedBucket> flattenedBuckets, List<ExpectedFlattenedBucket> expected) {
        List<String> actualDescriptions = flattenedBuckets.stream().map(FlattenedBucket::getDescription).collect(toList());
        String[] expectedDescriptions = expected.stream().map(ExpectedFlattenedBucket::getDescription).toArray(String[]::new);
        assertThat(actualDescriptions).containsExactly(expectedDescriptions);
    }

    private void assertPricesAreAsExpected(List<FlattenedBucket> flattenedBuckets, List<ExpectedFlattenedBucket> expected) {
        List<BigDecimal> actualPrice = flattenedBuckets.stream().map(FlattenedBucket::getUnitPrice).collect(toList());
        BigDecimal[] expectedPrice = expected.stream().map(ExpectedFlattenedBucket::getPrice).toArray(BigDecimal[]::new);
        assertThat(actualPrice).containsExactly(expectedPrice);
    }

    private void assertTotalUsagesAreAsExpected(List<FlattenedBucket> flattenedBuckets, List<ExpectedFlattenedBucket> expected) {
        List<BigDecimal> actualTotalUsage = flattenedBuckets.stream().map(FlattenedBucket::getTotalUsage).collect(toList());
        BigDecimal[] expectedTotalUsage = expected.stream().map(ExpectedFlattenedBucket::getTotalUsage).toArray(BigDecimal[]::new);
        assertThat(actualTotalUsage).containsExactly(expectedTotalUsage);
    }

    private void assertTotalPricesAreAsExpected(List<FlattenedBucket> flattenedBuckets, List<ExpectedFlattenedBucket> expected) {
        List<BigDecimal> actualTotalPrice = flattenedBuckets.stream().map(FlattenedBucket::getTotalPrice).collect(toList());
        BigDecimal[] expectedTotalPrice = expected.stream().map(ExpectedFlattenedBucket::getTotalPrice).toArray(BigDecimal[]::new);
        assertThat(actualTotalPrice).containsExactly(expectedTotalPrice);
    }

    private static Stream<Arguments> parameters() {
        ReferenceNumberOfValues reference = new ReferenceNumberOfValues();
        return Stream.of(
                of("Stable Plan", stablePlan(), stableFlattened(reference)),
                of("Night Plan", nightPlan(), nightFlattened(reference)),
                of("Winter Plan", winterPlan(), winterFlattened(reference)),
                of("Weekend Plan", weekendPlan(), weekendFlattened(reference)),
                of("Winter/Weekend/Night Plan", winterWeekendNightPlan(), winterWeekendNightFlattened(reference))
        );
    }
}