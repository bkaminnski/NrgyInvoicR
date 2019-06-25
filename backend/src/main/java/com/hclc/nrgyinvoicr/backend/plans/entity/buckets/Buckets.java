package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Buckets extends Bucket {

    private Buckets() {
    }

    public static Bucket forExpressionLines(List<ExpressionLine> expressionLines) {
        Buckets buckets = new Buckets();
        buckets.addToBuckets(new BucketsCreator(expressionLines, 1, DateBucket::new).create());
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
    public List<Flattened> flatten() {
        return getBuckets().stream()
                .flatMap(b -> b.flatten().stream())
                .collect(toList());
    }

    @Override
    Bucket optimized() {
        return Buckets.forBuckets(getOptimizedBuckets());
    }
}
