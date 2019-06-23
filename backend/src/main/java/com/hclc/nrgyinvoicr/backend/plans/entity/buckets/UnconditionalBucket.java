package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.singletonList;

public class UnconditionalBucket extends Bucket {
    private BigDecimal total = ZERO;

    @Override
    boolean accept(ReadingValue readingValue) {
        total = total.add(readingValue.getValue());
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

    BigDecimal getTotal() {
        return total;
    }
}
