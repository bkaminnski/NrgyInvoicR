package com.softwarewithpassion.nrgyinvoicr.backend.readings.control.files;

class InvalidNumberOfValuesInReadingLine extends ReadingException {

    InvalidNumberOfValuesInReadingLine(int lineNumber, int numberOfValues) {
        super("Line " + lineNumber + " has " + numberOfValues + " value(s). Line should have exactly two values.");
    }
}
