package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.math.BigDecimal;

class ExpectedFlattened {
    private final String description;
    private final BigDecimal total;

    ExpectedFlattened(String description, BigDecimal total) {
        this.description = description;
        this.total = total;
    }

    String getDescription() {
        return description;
    }

    BigDecimal getTotal() {
        return total;
    }
}
