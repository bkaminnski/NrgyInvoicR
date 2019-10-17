package com.softwarewithpassion.nrgyinvoicr.backend.clients.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.PageDefinition;

public class ClientsSearchCriteria {
    private String number;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private PageDefinition pageDefinition;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
