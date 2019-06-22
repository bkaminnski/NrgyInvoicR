package com.hclc.nrgyinvoicr.backend.plans.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import static javax.persistence.GenerationType.SEQUENCE;

public class MarketingName {

    @Id
    @SequenceGenerator(name = "marketing_name_seq", sequenceName = "marketing_name_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "marketing_name_seq")
    private Long id;

    @Column(length = 25)
    private String name;

    @Column(length = 2000)
    private String description;
}
