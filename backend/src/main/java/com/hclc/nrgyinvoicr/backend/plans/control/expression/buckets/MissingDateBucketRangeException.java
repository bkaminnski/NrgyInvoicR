package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

public class MissingDateBucketRangeException extends LineException {
    private final int lineNumber;

    MissingDateBucketRangeException(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "No date range was found. A date range should start with \".\".");
    }
}
