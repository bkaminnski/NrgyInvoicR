package com.hclc.nrgyinvoicr.backend.plans.entity;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.FlattenedBucket;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.LineError;

import java.util.List;

public class ExpressionTestResult {
    private final boolean valid;
    private final Boolean coversWholeYear;
    private final LineError lineError;
    private final List<FlattenedBucket> flattenedBuckets;

    public ExpressionTestResult(LineError lineError) {
        this.lineError = lineError;
        this.valid = false;
        this.coversWholeYear = null;
        this.flattenedBuckets = null;
    }

    public ExpressionTestResult(boolean coversWholeYear, List<FlattenedBucket> flattenedBuckets) {
        this.valid = coversWholeYear;
        this.coversWholeYear = coversWholeYear;
        this.flattenedBuckets = flattenedBuckets;
        this.lineError = null;
    }

    public boolean isValid() {
        return valid;
    }

    public Boolean getCoversWholeYear() {
        return coversWholeYear;
    }

    public LineError getLineError() {
        return lineError;
    }

    public List<FlattenedBucket> getFlattenedBuckets() {
        return flattenedBuckets;
    }
}
