package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

public class DayOfWeekBucketRangeMissingHourRangeException extends LineException {
    private final int lineNumber;

    DayOfWeekBucketRangeMissingHourRangeException(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "No hour range was found for a day of week range. An hour range should start with \"...\".");
    }
}
