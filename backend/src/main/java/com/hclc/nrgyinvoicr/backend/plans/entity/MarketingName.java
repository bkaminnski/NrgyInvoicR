package com.hclc.nrgyinvoicr.backend.plans.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class MarketingName {

    @Id
    @SequenceGenerator(name = "marketing_name_seq", sequenceName = "marketing_name_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "marketing_name_seq")
    private Long id;

    @Column(length = 25)
    private String name;

    @Column(length = 2000)
    private String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
