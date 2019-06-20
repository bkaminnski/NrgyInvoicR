package com.hclc.nrgyinvoicr.backend.readings.control.files;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_OFFSET_DATE_TIME_LESS_PRECISION;

class InvalidDateInReadingLine extends ReadingException {

    InvalidDateInReadingLine(int lineNumber, String dateAsString) {
        super("Invalid date in line " + lineNumber + ": " + dateAsString + ". A date should match the following pattern: " + ISO_8601_OFFSET_DATE_TIME_LESS_PRECISION + ".");
    }
}
