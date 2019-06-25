package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

public class DayOfWeekBucketRangeStartException extends LineException {
    private final int lineNumber;
    private final String rangeStart;

    DayOfWeekBucketRangeStartException(int lineNumber, String rangeStart) {
        this.lineNumber = lineNumber;
        this.rangeStart = rangeStart;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid start of day of week range: \"" + rangeStart + "\". The value should be a number between 1 (Monday) and 7 (Sunday).");
    }
}
