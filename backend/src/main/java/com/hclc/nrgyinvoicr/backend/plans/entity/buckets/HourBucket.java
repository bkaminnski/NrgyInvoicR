package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;

class HourBucket extends Bucket {
    private static final int FIRST_HOUR = 0;
    private static final int LAST_HOUR = 23;
    private final int since;
    private final int until;
    private final BigDecimal price;
    private BigDecimal totalUsage = ZERO;

    HourBucket(ExpressionLine bucketStart, List<ExpressionLine> bucketContent) {
        this.since = Integer.valueOf(bucketStart.getRangeStart());
        this.until = Integer.valueOf(bucketStart.getRangeEnd());
        this.price = bucketStart.getPrice();
    }

    @Override
    boolean accept(ReadingValue readingValue) {
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
    public List<Flattened> flatten() {
        return singletonList(new Flattened(this));
    }

    @Override
    Bucket optimized() {
        if (coversFullPeriod()) {
            return new UnconditionalBucket(price, totalUsage);
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

    BigDecimal getPrice() {
        return price;
    }

    BigDecimal getTotalUsage() {
        return totalUsage;
    }

    BigDecimal getTotalPrice() {
        return totalUsage.multiply(price);
    }
}
