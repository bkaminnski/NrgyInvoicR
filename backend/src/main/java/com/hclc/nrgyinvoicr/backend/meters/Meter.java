package com.hclc.nrgyinvoicr.backend.meters;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Meter extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "meter_id_seq", sequenceName = "meter_id_seq")
    @GeneratedValue(strategy = SEQUENCE, generator = "meter_id_seq")
    private Long id;

    @NotNull
    @Column(length = 36)
    private String externalId;

    public Meter() {
    }

    public Meter(@NotNull String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }
}
