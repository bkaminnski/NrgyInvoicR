package com.hclc.nrgyinvoicr.backend.readings.control.files;

import java.time.ZonedDateTime;

class ParsedFileName {
    private final String meterId;
    private final ZonedDateTime readingDate;

    ParsedFileName(String meterId, ZonedDateTime readingDate) {
        this.meterId = meterId;
        this.readingDate = readingDate;
    }

    String getMeterId() {
        return meterId;
    }

    ZonedDateTime getReadingDate() {
        return readingDate;
    }
}
