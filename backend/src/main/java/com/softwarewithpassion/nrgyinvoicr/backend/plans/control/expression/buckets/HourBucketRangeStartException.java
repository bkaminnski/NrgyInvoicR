package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

public class HourBucketRangeStartException extends LineException {
    private final int lineNumber;
    private final String rangeStart;

    HourBucketRangeStartException(int lineNumber, String rangeStart) {
        this.lineNumber = lineNumber;
        this.rangeStart = rangeStart;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid start of hour range: \"" + rangeStart + "\". The value should be a number between 0 (inclusive) and 23 (inclusive).");
    }
}
