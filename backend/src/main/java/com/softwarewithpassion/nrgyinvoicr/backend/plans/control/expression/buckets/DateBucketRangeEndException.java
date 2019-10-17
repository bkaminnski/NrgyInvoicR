package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

public class DateBucketRangeEndException extends LineException {
    private final int lineNumber;
    private final String rangeEnd;

    DateBucketRangeEndException(int lineNumber, String rangeEnd) {
        this.lineNumber = lineNumber;
        this.rangeEnd = rangeEnd;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid end of date range: \"" + rangeEnd + "\". Date should match a pattern \"MM.DD\".");
    }
}
