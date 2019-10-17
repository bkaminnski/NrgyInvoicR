package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;
import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;

class HourBucket extends Bucket {
    private static final int FIRST_HOUR = 0;
    private static final int LAST_HOUR = 23;
    private final int since;
    private final int until;
    private final BigDecimal unitPrice;
    private BigDecimal totalUsage = ZERO;

    HourBucket(ExpressionLine bucketStart, List<ExpressionLine> bucketContent) {
        try {
            this.since = parseHour(bucketStart.getRangeStart());
        } catch (IllegalArgumentException e) {
            throw new HourBucketRangeStartException(bucketStart.getLineNumber(), bucketStart.getRangeStart());
        }
        try {
            this.until = parseHour(bucketStart.getRangeEnd());
        } catch (IllegalArgumentException e) {
            throw new HourBucketRangeEndException(bucketStart.getLineNumber(), bucketStart.getRangeEnd());
        }
        if (bucketStart.getUnitPrice() == null) {
            throw new HourBucketMissingUnitPriceException(bucketStart.getLineNumber());
        }
        this.unitPrice = bucketStart.getUnitPrice();
    }

    private int parseHour(String hourString) throws IllegalArgumentException {
        int hour = Integer.valueOf(hourString);
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException();
        }
        return hour;
    }

    @Override
    public boolean accept(ReadingValue readingValue) {
        int hour = readingValue.getDate().getHour();
        if ((since <= until) && (hour < since || until < hour)) {
            // |---<hour>--<since>/////////<until>----<hour>--|----------------------------------------------|
            // 0      7       14              19        21    23
            return false;
        }
        if ((since > until) && (until < hour && hour < since)) {
            // |///////////////<until>---<hour>--<since>//////|///////////////<until>---<hour>--<since>//////|
            // 0                 15        17      20         23                15        17      20         23
            return false;
        }
        totalUsage = totalUsage.add(readingValue.getValue());
        return true;
    }

    @Override
    public List<FlattenedBucket> flatten() {
        return singletonList(new FlattenedBucket(this));
    }

    @Override
    Bucket optimized() {
        if (coversFullPeriod()) {
            return new UnconditionalBucket(unitPrice, totalUsage);
        }
        return this;
    }

    private boolean coversFullPeriod() {
        return since == FIRST_HOUR && until == LAST_HOUR;
    }

    int getSince() {
        return since;
    }

    int getUntil() {
        return until;
    }

    BigDecimal getUnitPrice() {
        return unitPrice;
    }

    BigDecimal getTotalUsage() {
        return totalUsage;
    }

    BigDecimal getTotalPrice() {
        return totalUsage.multiply(unitPrice);
    }
}
