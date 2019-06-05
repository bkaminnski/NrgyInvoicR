package com.hclc.nrgyinvoicr.backend.meters.entity;

import org.springframework.data.jpa.domain.Specification;

public class MetersSpecification {
    private static final String SERIAL_NUMBER = "serialNumber";

    public static Specification<Meter> serialNumberLike(String serialNumber) {
        return (meter, query, cb) -> serialNumber == null ? cb.and() : cb.like(meter.get(SERIAL_NUMBER), "%" + serialNumber + "%");
    }
}
