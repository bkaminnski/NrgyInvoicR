package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class Bucket {
    private final List<Bucket> buckets = new ArrayList<>();

    public boolean accept(ReadingValue readingValue) {
        for (Bucket bucket : buckets) {
            if (bucket.accept(readingValue)) {
                return true;
            }
        }
        return false;
    }

    public abstract List<FlattenedBucket> flatten();

    void addToBuckets(List<Bucket> buckets) {
        this.buckets.addAll(buckets);
    }

    abstract Bucket optimized();

    List<Bucket> getBuckets() {
        return buckets;
    }

    List<Bucket> getOptimizedBuckets() {
        return buckets.stream().map(Bucket::optimized).collect(toList());
    }
}
