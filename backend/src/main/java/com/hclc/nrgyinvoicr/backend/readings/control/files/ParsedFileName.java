package com.hclc.nrgyinvoicr.backend.readings.control.files;

import java.time.LocalDate;

class ParsedFileName {
    private final String meterId;
    private final LocalDate readingDate;

    ParsedFileName(String meterId, LocalDate readingDate) {
        this.meterId = meterId;
        this.readingDate = readingDate;
    }

    String getMeterId() {
        return meterId;
    }

    LocalDate getReadingDate() {
        return readingDate;
    }
}
