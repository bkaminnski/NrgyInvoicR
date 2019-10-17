package com.softwarewithpassion.nrgyinvoicr.backend.readings.control.files;

class InvalidReadingValueInReadingLine extends ReadingException {

    InvalidReadingValueInReadingLine(int lineNumber, String valueAsString) {
        super("Invalid numeric value in line " + lineNumber + ": " + valueAsString + ".");
    }
}
