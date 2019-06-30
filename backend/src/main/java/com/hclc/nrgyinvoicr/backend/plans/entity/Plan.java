package com.hclc.nrgyinvoicr.backend.plans.entity;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Plan {

    @Id
    @SequenceGenerator(name = "plan_id_seq", sequenceName = "plan_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "plan_id_seq")
    private Long id;

    @Column(length = 25)
    private String name;

    @Column(length = 2000)
    private String description;

    @OneToMany
    @JoinColumn(name = "plan_id")
    private List<PlanVersion> versions;

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
