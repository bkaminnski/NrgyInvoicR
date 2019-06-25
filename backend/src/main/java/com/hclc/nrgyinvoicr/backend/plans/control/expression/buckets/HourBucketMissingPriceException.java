package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

public class HourBucketMissingPriceException extends LineException {
    private final int lineNumber;

    HourBucketMissingPriceException(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Price is missing in hour range.");
    }
}
