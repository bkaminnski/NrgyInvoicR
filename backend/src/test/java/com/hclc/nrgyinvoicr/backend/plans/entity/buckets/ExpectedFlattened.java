package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.math.BigDecimal;

class ExpectedFlattened {
    private final String description;
    private final BigDecimal price;
    private final BigDecimal totalUsage;
    private final BigDecimal totalPrice;

    ExpectedFlattened(String description, BigDecimal price, BigDecimal totalUsage, BigDecimal totalPrice) {
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
