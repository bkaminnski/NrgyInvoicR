package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;

class HourBucket extends Bucket {
    private final int since;
    private final int until;
    private BigDecimal total = ZERO;

    HourBucket(ExpressionLine bucketStart, List<ExpressionLine> bucketContent) {
        this.since = Integer.valueOf(bucketStart.getRangeStart());
        this.until = Integer.valueOf(bucketStart.getRangeEnd());
    }

    @Override
    boolean accept(ReadingValue readingValue) {
        int hour = readingValue.getDate().getHour();
        if (since <= hour && hour <= until) {
            total = total.add(readingValue.getValue());
            return true;
        }
        return false;
    }

    @Override
    public Bucket optimized() {
        if (coversFullPeriod()) {
            return new UnconditionalBucket();
        }
        return this;
    }

    private boolean coversFullPeriod() {
        return since == 0 && until == 23;
    }

    @Override
    public List<Flattened> flatten() {
        return singletonList(new Flattened(this));
    }

    int getSince() {
        return since;
    }

    int getUntil() {
        return until;
    }

    BigDecimal getTotal() {
        return total;
    }
}
