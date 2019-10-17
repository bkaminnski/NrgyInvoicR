package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

public class DayOfWeekBucketRangeEndException extends LineException {
    private final int lineNumber;
    private final String rangeEnd;

    DayOfWeekBucketRangeEndException(int lineNumber, String rangeEnd) {
        this.lineNumber = lineNumber;
        this.rangeEnd = rangeEnd;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid end of day of week range: \"" + rangeEnd + "\". The value should be a number between 1 (Monday) and 7 (Sunday).");
    }
}
