package com.hclc.nrgyinvoicr.backend.readings.control.files;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;

class InvalidReadingDateInFileName extends ReadingException {

    InvalidReadingDateInFileName(String dateAsString) {
        super("Invalid reading date in the file name: " + dateAsString + ". A date should match the following pattern: " + ISO_8601_DATE + ".");
    }
}
