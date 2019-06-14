package com.hclc.nrgyinvoicr.backend.clients.entity;

import org.springframework.data.jpa.domain.Specification;

public class ClientsSpecification {
    private static final String NUMBER = "number";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ADDRESS_LINE1 = "address_line1";
    private static final String ADDRESS_LINE2 = "address_line2";
    private static final String POSTAL_CODE = "postal_code";
    private static final String CITY = "city";

    public static Specification<Client> numberLike(String number) {
        return (client, query, cb) -> number == null ? cb.and() : cb.like(client.get(NUMBER), "%" + number + "%");
    }

    public static Specification<Client> firstNameLike(String firstName) {
        return (client, query, cb) -> firstName == null ? cb.and() : cb.like(cb.lower(client.get(FIRST_NAME)), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Client> lastNameLike(String lastName) {
        return (client, query, cb) -> lastName == null ? cb.and() : cb.like(cb.lower(client.get(LAST_NAME)), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Client> addressLike(String address) {
        return (client, query, cb) -> address == null ? cb.and() :
                cb.or(
                        cb.like(cb.lower(client.get(ADDRESS_LINE1)), "%" + address.toLowerCase() + "%"),
                        cb.like(cb.lower(client.get(ADDRESS_LINE2)), "%" + address.toLowerCase() + "%")
                );
    }

    public static Specification<Client> postalCodeLike(String postalCode) {
        return (client, query, cb) -> postalCode == null ? cb.and() : cb.like(client.get(POSTAL_CODE), "%" + postalCode + "%");
    }

    public static Specification<Client> cityLike(String city) {
        return (client, query, cb) -> city == null ? cb.and() : cb.like(cb.lower(client.get(CITY)), "%" + city.toLowerCase() + "%");
    }
}
