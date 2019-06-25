package com.hclc.nrgyinvoicr.backend.plans.control.expression.lines;

import java.math.BigDecimal;

public class ExpressionLine {
    private final int lineNumber;
    private final String line;
    private final String rangeStart;
    private final String rangeEnd;
    private final BigDecimal price;

    public ExpressionLine(int lineNumber, String line) throws ExpressionLineRangeException {
        this.lineNumber = lineNumber;
        this.line = line;
        String[] rangeAndValue = lineWithoutLeadingDots().split(":");
        if (rangeAndValue.length == 2) {
            this.price = new BigDecimal(rangeAndValue[1]);
        } else {
            this.price = null;
        }
        try {
            String[] range = rangeAndValue[0].split("-");
            this.rangeStart = range[0];
            this.rangeEnd = range[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ExpressionLineRangeException(lineNumber, rangeAndValue[0]);
        }
    }

    private String lineWithoutLeadingDots() {
        return line.replaceFirst("\\.+", "");
    }

    public boolean isLevel(int level) {
        return (line.length() - lineWithoutLeadingDots().length()) == level;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
