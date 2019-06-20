package com.hclc.nrgyinvoicr.backend.readings.control.files;

public class MeterNotFoundException extends ReadingException {

    MeterNotFoundException(String serialNumber) {
        super("Meter not found: " + serialNumber);
    }
}
