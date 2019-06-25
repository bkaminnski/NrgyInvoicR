package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.util.List;

import static java.util.stream.Collectors.toList;

class DayOfWeekBucket extends Bucket {
    private final int since;
    private final int until;

    DayOfWeekBucket(ExpressionLine bucketStart, List<ExpressionLine> bucketContent) {
        try {
            this.since = parseDayOfWeek(bucketStart.getRangeStart());
        } catch (IllegalArgumentException e) {
            throw new DayOfWeekBucketRangeStartException(bucketStart.getLineNumber(), bucketStart.getRangeStart());
        }
        try {
            this.until = parseDayOfWeek(bucketStart.getRangeEnd());
        } catch (IllegalArgumentException e) {
            throw new DayOfWeekBucketRangeEndException(bucketStart.getLineNumber(), bucketStart.getRangeEnd());
        }
        if (bucketStart.getPrice() != null) {
            throw new DayOfWeekBucketRangePriceDeclaredException(bucketStart.getLineNumber(), bucketStart.getPrice());
        }
        List<Bucket> hourBuckets = new BucketsCreator(bucketContent, 3, HourBucket::new).create();
        if (hourBuckets.isEmpty()) {
            throw new DayOfWeekBucketRangeMissingHourRangeException(bucketStart.getLineNumber());
        }
        addToBuckets(hourBuckets);
    }

    private int parseDayOfWeek(String dayOfWeekAsString) throws IllegalArgumentException {
        int dayOfWeek = Integer.valueOf(dayOfWeekAsString);
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new IllegalArgumentException();
        }
        return dayOfWeek;
    }

    private DayOfWeekBucket(int since, int until, List<Bucket> buckets) {
        this.since = since;
        this.until = until;
        addToBuckets(buckets);
    }

    @Override
    public boolean accept(ReadingValue readingValue) {
        int dayOfWeek = readingValue.getDate().getDayOfWeek().getValue();
        if ((since <= until) && (dayOfWeek < since || until < dayOfWeek)) {
            // |----<dow>--<since>/////////<until>-----<dow>--|----------------------------------------------|
            // 1      2       3               5          6    7
            return false;
        }
        if ((since > until) && (until < dayOfWeek && dayOfWeek < since)) {
            // |///////////////<until>----<dow>--<since>//////|///////////////<until>----<dow>--<since>//////|
            // 1                  3         5       6         7                  3         5       6         1
            return false;
        }
        return super.accept(readingValue);
    }

    @Override
    public List<FlattenedBucket> flatten() {
        return getBuckets().stream()
                .flatMap(b -> b.flatten().stream())
                .map(f -> f.accept(this))
                .collect(toList());
    }

    @Override
    Bucket optimized() {
        if (this.coversFullPeriod()) {
            return Buckets.forBuckets(getOptimizedBuckets());
        }
        return new DayOfWeekBucket(since, until, getOptimizedBuckets());
    }

    private boolean coversFullPeriod() {
        return since == 1 && until == 7;
    }

    int getSince() {
        return since;
    }

    int getUntil() {
        return until;
    }
}
