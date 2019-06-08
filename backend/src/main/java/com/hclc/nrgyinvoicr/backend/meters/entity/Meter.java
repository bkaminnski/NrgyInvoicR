package com.hclc.nrgyinvoicr.backend.meters.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Meter extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "meter_id_seq", sequenceName = "meter_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "meter_id_seq")
    private Long id;

    @NotNull
    @Column(length = 36)
    private String serialNumber;

    public Meter() {
    }

    public Meter(@NotNull String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
