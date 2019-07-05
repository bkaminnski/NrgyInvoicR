package com.hclc.nrgyinvoicr.backend.plans.control.expression.lines;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.LineError;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.LineException;

public class ExpressionLineInvalidPriceException extends LineException {
    private final int lineNumber;
    private final String price;

    ExpressionLineInvalidPriceException(int lineNumber, String price) {
        this.lineNumber = lineNumber;
        this.price = price;
    }

    public LineError toLineError() {
        return new LineError(lineNumber, "Invalid price: \"" + price + "\".");
    }
}
