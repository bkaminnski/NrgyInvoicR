package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets.Bucket;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets.Buckets;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets.LineException;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {

    public Bucket parse(String expression) throws IOException, LineException {
        List<ExpressionLine> expressionLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(expression == null ? "" : expression))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                expressionLines.add(new ExpressionLine(lineNumber, line));
                lineNumber++;
            }
        }
        return Buckets.forExpressionLines(expressionLines);
    }
}
