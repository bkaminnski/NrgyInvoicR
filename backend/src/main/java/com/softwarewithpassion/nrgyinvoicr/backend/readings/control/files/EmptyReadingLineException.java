package com.softwarewithpassion.nrgyinvoicr.backend.readings.control.files;

class EmptyReadingLineException extends ReadingException {

    EmptyReadingLineException(int lineNumber) {
        super("Line " + lineNumber + " is empty.");
    }
}
