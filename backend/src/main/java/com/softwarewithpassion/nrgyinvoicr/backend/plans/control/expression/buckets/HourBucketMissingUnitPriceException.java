package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

public class HourBucketMissingUnitPriceException extends LineException {
    private final int lineNumber;

    HourBucketMissingUnitPriceException(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Unit price is missing in hour range.");
    }
}
