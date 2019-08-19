package com.hclc.nrgyinvoicr.backend.invoices.entity;

import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class InvoicesSpecification {
    private static final String ISSUE_DATE = "issueDate";
    private static final String CLIENT_NUMBER = "number";

    public static Specification<Invoice> issueDateBetween(ZonedDateTime since, ZonedDateTime until) {
        return (invoice, query, cb) -> cb.and(
                since == null ? cb.and() : cb.greaterThanOrEqualTo(invoice.get(ISSUE_DATE), since),
                until == null ? cb.and() : cb.lessThan(invoice.get(ISSUE_DATE), until)
        );
    }

    public static Specification<Invoice> clientNumberLike(String clientNumber) {
        return (invoice, query, cb) -> clientNumber == null
                ? cb.and()
                : cb.like(invoice.join("client").get(CLIENT_NUMBER), "%" + clientNumber + "%");
    }
}
