package com.hclc.nrgyinvoicr.backend.readings.control.files;

import java.util.Date;

class ParsedFileName {
    private final String meterId;
    private final Date readingDate;

    ParsedFileName(String meterId, Date readingDate) {
        this.meterId = meterId;
        this.readingDate = readingDate;
    }

    String getMeterId() {
        return meterId;
    }

    Date getReadingDate() {
        return readingDate;
    }
}
