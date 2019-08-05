package com.hclc.nrgyinvoicr.backend.plans.control.expression.lines;

import java.math.BigDecimal;

public class ExpressionLine {
    private final int lineNumber;
    private final String line;
    private final String rangeStart;
    private final String rangeEnd;
    private final BigDecimal unitPrice;

    public ExpressionLine(int lineNumber, String line) throws ExpressionLineRangeException {
        this.lineNumber = lineNumber;
        this.line = line;
        String[] rangeAndValue = lineWithoutLeadingDots().split(":");
        try {
            if (rangeAndValue.length == 2) {
                this.unitPrice = new BigDecimal(rangeAndValue[1]);
            } else {
                this.unitPrice = null;
            }
        } catch (NumberFormatException e) {
            throw new ExpressionLineInvalidPriceException(lineNumber, rangeAndValue[1]);
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
