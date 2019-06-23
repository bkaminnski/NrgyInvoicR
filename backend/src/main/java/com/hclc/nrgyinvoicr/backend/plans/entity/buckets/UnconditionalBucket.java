package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

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
    boolean accept(ReadingValue readingValue) {
        totalUsage = totalUsage.add(readingValue.getValue());
        return true;
    }

    @Override
    public Bucket optimized() {
        return this;
    }

    @Override
    public List<Flattened> flatten() {
        return singletonList(new Flattened(this));
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
