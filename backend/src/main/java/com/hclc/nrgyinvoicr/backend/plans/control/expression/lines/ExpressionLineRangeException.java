package com.hclc.nrgyinvoicr.backend.plans.control.expression.lines;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.LineError;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.LineException;

public class ExpressionLineRangeException extends LineException {
    private final int lineNumber;
    private final String range;

    ExpressionLineRangeException(int lineNumber, String range) {
        this.lineNumber = lineNumber;
        this.range = range;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid range: \"" + range + "\". Range expression should match a pattern \"<start inclusive>-<end inclusive>\".");
    }
}
