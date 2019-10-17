package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

import java.math.BigDecimal;

class ExpectedFlattenedBucket {
    private final String description;
    private final BigDecimal price;
    private final BigDecimal totalUsage;
    private final BigDecimal totalPrice;

    ExpectedFlattenedBucket(String description, BigDecimal price, BigDecimal totalUsage, BigDecimal totalPrice) {
        this.description = description;
        this.price = price;
        this.totalUsage = totalUsage;
        this.totalPrice = totalPrice;
    }

    String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    BigDecimal getTotalUsage() {
        return totalUsage;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
