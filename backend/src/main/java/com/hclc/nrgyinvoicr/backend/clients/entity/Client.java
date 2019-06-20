package com.hclc.nrgyinvoicr.backend.clients.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;
import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@NamedEntityGraph(name = "clientWithMeter", attributeNodes = @NamedAttributeNode("meter"))
public class Client extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "client_id_seq", sequenceName = "client_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "client_id_seq")
    private Long id;

    @NotNull
    @Column(length = 36)
    private String number;

    @NotNull
    @Column(length = 36)
    private String firstName;

    @Column(length = 36)
    private String middleName;

    @NotNull
    @Column(length = 36)
    private String lastName;

    @NotNull
    @Column(length = 50)
    private String addressLine1;

    @Column(length = 50)
    private String addressLine2;

    @NotNull
    @Column(length = 10)
    private String postalCode;

    @NotNull
    @Column(length = 10)
    private String city;

    @OneToOne(mappedBy = "client")
    private Meter meter;

    public Client() {
    }

    public Client(@NotNull String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client withId(Long id) {
        setId(id);
        return this;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client withNumber(String number) {
        setNumber(number);
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public Client withMeter(Meter meter) {
        this.setMeter(meter);
        return this;
    }
}
