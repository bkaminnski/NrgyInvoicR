package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;
import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

class DateBucket extends Bucket {
    private final String sinceAsString;
    private final String untilAsString;
    private final int since;
    private final int until;

    DateBucket(ExpressionLine bucketStart, List<ExpressionLine> bucketContent) {
        this.sinceAsString = bucketStart.getRangeStart();
        if (dateIsInvalid(this.sinceAsString)) {
            throw new DateBucketRangeStartException(bucketStart.getLineNumber(), this.sinceAsString);
        }
        this.untilAsString = bucketStart.getRangeEnd();
        if (dateIsInvalid(this.untilAsString)) {
            throw new DateBucketRangeEndException(bucketStart.getLineNumber(), this.untilAsString);
        }
        this.since = parseDate(sinceAsString);
        this.until = parseDate(untilAsString);
        if (bucketStart.getUnitPrice() != null) {
            throw new DateBucketRangePriceDeclaredException(bucketStart.getLineNumber(), bucketStart.getUnitPrice());
        }
        List<Bucket> dayOfWeekBuckets = new BucketsCreator(bucketContent, 2, DayOfWeekBucket::new).create();
        if (dayOfWeekBuckets.isEmpty()) {
            throw new DateBucketRangeMissingDayOfWeekRangeException(bucketStart.getLineNumber());
        }
        addToBuckets(dayOfWeekBuckets);
    }

    private boolean dateIsInvalid(String date) {
        return !date.matches("\\d{2}\\.\\d{2}");
    }

    private DateBucket(String sinceAsString, String untilAsString, int since, int until, List<Bucket> buckets) {
        this.sinceAsString = sinceAsString;
        this.untilAsString = untilAsString;
        this.since = since;
        this.until = until;
        addToBuckets(buckets);
    }

    @Override
    public boolean accept(ReadingValue readingValue) {
        int date = parseDate(readingValue.getDate());
        if ((since <= until) && (date < since || until < date)) {
            // |---<date>--<since>/////////<until>----<date>--|----------------------------------------------|
            // 01.01 02.13  05.01           10.31     11.15   12.31
            return false;
        }
        if ((since > until) && (until < date && date < since)) {
            // |///////////////<until>---<date>--<since>//////|///////////////<until>---<date>--<since>//////|
            // 01.01            06.30    09.01    11.01       12.31            06.30    09.01    11.01       01.01
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
        return new DateBucket(sinceAsString, untilAsString, since, until, getOptimizedBuckets());
    }

    private boolean coversFullPeriod() {
        return since == 101 && until == 1231;
    }

    private int parseDate(String date) {
        String[] parts = date.split("\\.");
        int month = Integer.valueOf(parts[0]);
        int day = Integer.valueOf(parts[1]);
        return month * 100 + day;
    }

    private int parseDate(ZonedDateTime zonedDateTime) {
        return zonedDateTime.getMonthValue() * 100 + zonedDateTime.getDayOfMonth();
    }

    String getSinceAsString() {
        return sinceAsString;
    }

    String getUntilAsString() {
        return untilAsString;
    }
}
