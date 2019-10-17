package com.softwarewithpassion.nrgyinvoicr.backend.readings.control.files;

public class MeterNotFoundException extends ReadingException {

    MeterNotFoundException(String serialNumber) {
        super("Meter not found: " + serialNumber);
    }
}
