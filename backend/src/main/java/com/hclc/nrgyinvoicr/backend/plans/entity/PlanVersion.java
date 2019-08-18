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

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public ZonedDateTime getValidSince() {
        return validSince;
    }

    public void setValidSince(ZonedDateTime validSince) {
        this.validSince = validSince;
    }

    public FixedFees getFixedFees() {
        return fixedFees;
    }

    public void setFixedFees(FixedFees fixedFees) {
        this.fixedFees = fixedFees;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
