package com.hclc.nrgyinvoicr.backend.plans.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

public class Plan {

    @Id
    @SequenceGenerator(name = "plan_id_seq", sequenceName = "plan_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "plan_id_seq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "marketing_name_id", referencedColumnName = "id")
    private MarketingName marketingName;

    private ZonedDateTime validSince;

    @OneToMany
    @JoinColumn(name = "plan_id")
    private List<PlanVersion> versions;

    @Column(length = 2000)
    private String description;
}
