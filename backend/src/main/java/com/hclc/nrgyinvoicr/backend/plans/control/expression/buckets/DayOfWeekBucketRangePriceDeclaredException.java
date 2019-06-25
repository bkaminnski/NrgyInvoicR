package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import java.math.BigDecimal;

public class DayOfWeekBucketRangePriceDeclaredException extends LineException {
    private final int lineNumber;
    private final BigDecimal price;

    DayOfWeekBucketRangePriceDeclaredException(int lineNumber, BigDecimal price) {
        this.lineNumber = lineNumber;
        this.price = price;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Price cannot be declared for a day of week range: " + price + ".");
    }
}
