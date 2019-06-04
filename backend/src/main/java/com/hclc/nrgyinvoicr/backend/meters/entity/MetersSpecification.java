package com.hclc.nrgyinvoicr.backend.meters.entity;

import org.springframework.data.jpa.domain.Specification;

public class MetersSpecification {
    private static final String EXTERNAL_ID = "externalId";

    public static Specification<Meter> externalIdLike(String externalId) {
        return (meter, query, cb) -> externalId == null ? cb.and() : cb.like(meter.get(EXTERNAL_ID), "%" + externalId + "%");
    }
}
