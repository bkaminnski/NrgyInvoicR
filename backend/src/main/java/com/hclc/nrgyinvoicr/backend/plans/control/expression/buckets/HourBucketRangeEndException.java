package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

public class HourBucketRangeEndException extends LineException {
    private final int lineNumber;
    private final String rangeEnd;

    HourBucketRangeEndException(int lineNumber, String rangeEnd) {
        this.lineNumber = lineNumber;
        this.rangeEnd = rangeEnd;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid end of hour range: \"" + rangeEnd + "\". The value should be a number between 0 (inclusive) and 23 (inclusive).");
    }
}
