package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

public class DateBucketRangeMissingDayOfWeekRangeException extends LineException {
    private final int lineNumber;

    DateBucketRangeMissingDayOfWeekRangeException(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "No day of week range was found for a date range. A day of week range should start with \"..\".");
    }
}
