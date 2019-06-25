package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import java.math.BigDecimal;

public class DateBucketRangePriceDeclaredException extends LineException {
    private final int lineNumber;
    private final BigDecimal price;

    DateBucketRangePriceDeclaredException(int lineNumber, BigDecimal price) {
        this.lineNumber = lineNumber;
        this.price = price;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Price cannot be declared for a date range: " + price + ".");
    }
}
