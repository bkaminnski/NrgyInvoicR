package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.math.BigDecimal;

class ExpressionLine {
    private final int lineNumber;
    private final String line;
    private final String rangeStart;
    private final String rangeEnd;
    private final BigDecimal price;

    ExpressionLine(int lineNumber, String line) {
        this.lineNumber = lineNumber;
        this.line = line;
        String[] rangeAndValue = lineWithoutLeadingDots().split(":");
        if (rangeAndValue.length == 2) {
            this.price = new BigDecimal(rangeAndValue[1]);
        } else {
            this.price = null;
        }
        String[] range = rangeAndValue[0].split("-");
        this.rangeStart = range[0];
        this.rangeEnd = range[1];
    }

    private String lineWithoutLeadingDots() {
        return line.replaceFirst("\\.+", "");
    }

    boolean isLevel(int level) {
        return (line.length() - lineWithoutLeadingDots().length()) == level;
    }

    int getLineNumber() {
        return lineNumber;
    }

    String getRangeStart() {
        return rangeStart;
    }

    String getRangeEnd() {
        return rangeEnd;
    }

    BigDecimal getPrice() {
        return price;
    }
}
