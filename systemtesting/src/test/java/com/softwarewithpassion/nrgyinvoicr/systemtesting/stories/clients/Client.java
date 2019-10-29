package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients;

public class Client {
    public final String fistName;
    public final String middleName;
    public final String lastName;
    public final String addressLine1;
    public final String addressLine2;
    public final String postalCode;
    public final String city;
    public final String meterSerialNumber;
    public String number;

    Client(String fistName, String middleName, String lastName, String addressLine1, String addressLine2, String postalCode, String city, String meterSerialNumber) {
        this.fistName = fistName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.city = city;
        this.meterSerialNumber = meterSerialNumber;
    }

    public Client withNumber(String number) {
        this.number = number;
        return this;
    }
}
