package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

class BucketsCreator {
    private final List<ExpressionLine> expressionLines;
    private final int level;
    private final BiFunction<ExpressionLine, List<ExpressionLine>, Bucket> creatingFunction;
    private List<Bucket> buckets = null;
    private ExpressionLine bucketStart = null;
    private List<ExpressionLine> bucketContent = null;

    BucketsCreator(List<ExpressionLine> expressionLines, int level, BiFunction<ExpressionLine, List<ExpressionLine>, Bucket> creatingFunction) {
        this.expressionLines = expressionLines;
        this.level = level;
        this.creatingFunction = creatingFunction;
    }

    List<Bucket> create() {
        initiateCreator();
        expressionLines.forEach(this::process);
        createBucket();
        return buckets;
    }

    private void initiateCreator() {
        buckets = new ArrayList<>();
        bucketStart = null;
        bucketContent = null;
    }

    private void process(ExpressionLine expressionLine) {
        if (expressionLine.isLevel(level)) {
            createBucket();
            bucketStart = expressionLine;
            bucketContent = new ArrayList<>();
        } else {
            bucketContent.add(expressionLine);
        }
    }

    private void createBucket() {
        if (bucketStart != null && bucketContent != null) {
            buckets.add(creatingFunction.apply(bucketStart, bucketContent));
        }
    }
}
