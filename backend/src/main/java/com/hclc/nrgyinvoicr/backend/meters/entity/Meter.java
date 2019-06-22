package com.hclc.nrgyinvoicr.backend.meters.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hclc.nrgyinvoicr.backend.AuditableEntity;
import com.hclc.nrgyinvoicr.backend.clients.entity.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@NamedEntityGraph(name = "meterWithClient", attributeNodes = @NamedAttributeNode("client"))
public class Meter extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "meter_id_seq", sequenceName = "meter_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "meter_id_seq")
    private Long id;

    @NotNull
    @Column(length = 36)
    private String serialNumber;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Meter withClient(Client client) {
        this.client = client;
        return this;
    }

    public String getClientNumber() {
        if (client != null) {
            return client.getNumber();
        }
        return null;
    }
}
