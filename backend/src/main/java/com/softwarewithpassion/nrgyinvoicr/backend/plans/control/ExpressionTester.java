package com.softwarewithpassion.nrgyinvoicr.backend.plans.control;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.ExpressionParser;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets.Bucket;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets.FlattenedBucket;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets.LineException;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.ExpressionTestResult;
import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class ExpressionTester {
    private static final ZonedDateTime START_OF_2020 = LocalDateTime.parse("2020-01-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));
    private static final ZonedDateTime START_OF_2021 = LocalDateTime.parse("2021-01-01T00:00:00.000").atZone(ZoneId.of("Europe/Berlin"));

    public ExpressionTestResult test(String expression) throws IOException {
        try {
            Bucket bucket = new ExpressionParser().parse(expression);
            boolean coversFullYear = testYearCoverage(bucket);
            List<FlattenedBucket> flattenedBuckets = bucket.flatten();
            return new ExpressionTestResult(coversFullYear, flattenedBuckets);
        } catch (LineException e) {
            return new ExpressionTestResult(e.toLineError());
        }
    }

    private boolean testYearCoverage(Bucket bucket) {
        ZonedDateTime testDate = START_OF_2020;
        int expectedTotalUsage = 0;
        while (testDate.isBefore(START_OF_2021)) {
            bucket.accept(new ReadingValue(null, testDate, ONE));
            expectedTotalUsage++;
            testDate = testDate.plus(15, MINUTES);
        }
        BigDecimal actualTotalUsage = sumUpTotalUsageInFlattenedBuckets(bucket.flatten());
        return new BigDecimal(expectedTotalUsage).compareTo(actualTotalUsage) == 0;
    }

    private BigDecimal sumUpTotalUsageInFlattenedBuckets(List<FlattenedBucket> flattenedBuckets) {
        return flattenedBuckets.stream().map(FlattenedBucket::getTotalUsage).reduce(ZERO, BigDecimal::add);
    }
}
