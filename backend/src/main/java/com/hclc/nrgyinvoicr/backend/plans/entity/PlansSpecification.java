package com.hclc.nrgyinvoicr.backend.plans.entity;

import org.springframework.data.jpa.domain.Specification;

public class PlansSpecification {
    private static final String NAME = "name";

    public static Specification<Plan> nameLike(String name) {
        return (plan, query, cb) -> name == null ? cb.and() : cb.like(plan.get(NAME), "%" + name + "%");
    }
}
