package com.hclc.nrgyinvoicr.systemtesting.stories.clients;

import static java.util.UUID.randomUUID;

public class ClientBuilder {
    private String fistName = randomUUID().toString();
    private String middleName = randomUUID().toString();
    private String lastName = randomUUID().toString();
    private String addressLine1 = randomUUID().toString();
    private String addressLine2 = randomUUID().toString();
    private String postalCode = randomUUID().toString().substring(0, 10);
    private String city = randomUUID().toString();
    private String meterSerialNumber = randomUUID().toString();

    public static ClientBuilder aClient() {
        return new ClientBuilder();
    }

    public ClientBuilder withFistName(String fistName) {
        this.fistName = fistName;
        return this;
    }

    public ClientBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public ClientBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ClientBuilder withAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public ClientBuilder withAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public ClientBuilder withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public ClientBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public ClientBuilder withMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
        return this;
    }

    public Client build() {
        return new Client(fistName, middleName, lastName, addressLine1, addressLine2, postalCode, city, meterSerialNumber);
    }
}