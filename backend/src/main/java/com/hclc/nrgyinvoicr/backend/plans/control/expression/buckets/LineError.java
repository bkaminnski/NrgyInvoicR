package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

public class LineError {
    private final int lineNumber;
    private final String errorMessage;

    public LineError(int lineNumber, String errorMessage) {
        this.lineNumber = lineNumber;
        this.errorMessage = errorMessage;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
