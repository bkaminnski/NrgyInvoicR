package com.hclc.nrgyinvoicr.backend.plans.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class PlanVersion {

    @Id
    @SequenceGenerator(name = "plan_version_id_seq", sequenceName = "plan_version_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "plan_version_id_seq")
    private Long id;

    @Column(name = "plan_id")
    private Long planId;

    private ZonedDateTime validSince;

    @Embedded
    private FixedFees fixedFees;

    @Column(length = 2000)
    private String expression;

    @Column(length = 2000)
    private String description;

    public Long getId() {
        return id;
    }

    public ZonedDateTime getValidSince() {
        return validSince;
    }

    public FixedFees getFixedFees() {
        return fixedFees;
    }

    public String getExpression() {
        return expression;
    }

    public String getDescription() {
        return description;
    }
}
