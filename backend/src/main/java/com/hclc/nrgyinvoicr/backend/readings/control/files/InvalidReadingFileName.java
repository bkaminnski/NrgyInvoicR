package com.hclc.nrgyinvoicr.backend.readings.control.files;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;

class InvalidReadingFileName extends ReadingException {

    InvalidReadingFileName(String fileName) {
        super("Invalid file name: " + fileName + ". A file name should match the following pattern: mr_[meter UUID]_[reading date " + ISO_8601_DATE + "]_[sequence number].csv.");
    }
}
