package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

public class DateBucketRangeStartException extends LineException {
    private final int lineNumber;
    private final String rangeStart;

    DateBucketRangeStartException(int lineNumber, String rangeStart) {
        this.lineNumber = lineNumber;
        this.rangeStart = rangeStart;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid start of date range: \"" + rangeStart + "\". Date should match a pattern \"MM.DD\".");
    }
}
