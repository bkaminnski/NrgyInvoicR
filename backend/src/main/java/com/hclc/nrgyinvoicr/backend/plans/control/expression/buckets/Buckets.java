package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.lines.ExpressionLine;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Buckets extends Bucket {

    private Buckets() {
    }

    public static Bucket forExpressionLines(List<ExpressionLine> expressionLines) throws LineException {
        Buckets buckets = new Buckets();
        List<Bucket> dateBuckets = new BucketsCreator(expressionLines, 1, DateBucket::new).create();
        if (dateBuckets.isEmpty()) {
            throw new MissingDateBucketRangeException(1);
        }
        buckets.addToBuckets(dateBuckets);
        return buckets.optimized();
    }

    static Bucket forBuckets(List<Bucket> bucketsList) {
        if (bucketsList.size() == 1) {
            return bucketsList.get(0);
        }
        Buckets buckets = new Buckets();
        buckets.addToBuckets(bucketsList);
        return buckets;
    }

    @Override
    public List<FlattenedBucket> flatten() {
        return getBuckets().stream()
                .flatMap(b -> b.flatten().stream())
                .collect(toList());
    }

    @Override
    Bucket optimized() {
        return Buckets.forBuckets(getOptimizedBuckets());
    }
}
