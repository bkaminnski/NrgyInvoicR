package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;

public class UnconditionalBucket extends Bucket {
    private final BigDecimal price;
    private BigDecimal totalUsage;

    public UnconditionalBucket(BigDecimal price, BigDecimal totalUsage) {
        this.price = price;
        this.totalUsage = totalUsage;
    }

    @Override
    public boolean accept(ReadingValue readingValue) {
        totalUsage = totalUsage.add(readingValue.getValue());
        return true;
    }

    @Override
    public List<FlattenedBucket> flatten() {
        return singletonList(new FlattenedBucket(this));
    }

    @Override
    Bucket optimized() {
        return this;
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
