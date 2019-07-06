package com.hclc.nrgyinvoicr.backend.clients.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hclc.nrgyinvoicr.backend.plans.entity.Plan;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class ClientPlanAssignment {

    @Id
    @SequenceGenerator(name = "client_plan_assignment_id_seq", sequenceName = "client_plan_assignment_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "client_plan_assignment_id_seq")
    private Long id;

    private ZonedDateTime validSince;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private Plan plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getValidSince() {
        return validSince;
    }

    public void setValidSince(ZonedDateTime validSince) {
        this.validSince = validSince;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
