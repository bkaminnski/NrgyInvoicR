package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

abstract class Bucket {
    private final List<Bucket> buckets = new ArrayList<>();

    void addToBuckets(List<Bucket> buckets) {
        this.buckets.addAll(buckets);
    }

    boolean accept(ReadingValue readingValue) {
        for (Bucket bucket : buckets) {
            if (bucket.accept(readingValue)) {
                return true;
            }
        }
        return false;
    }

    public abstract Bucket optimized();

    public abstract List<Flattened> flatten();

    List<Bucket> getBuckets() {
        return buckets;
    }

    List<Bucket> getOptimizedBuckets() {
        return buckets.stream().map(Bucket::optimized).collect(toList());
    }
}
