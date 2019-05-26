package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

class InvoiceSpecifications {
    private static final String ISSUE_DATE = "issueDate";

    static Specification<Invoice> issueDateBetween(Date from, Date to) {
        return (invoice, query, cb) -> cb.and(
                from == null ? cb.and() : cb.greaterThanOrEqualTo(invoice.get(ISSUE_DATE), from),
                to == null ? cb.and() : cb.lessThan(invoice.get(ISSUE_DATE), to)
        );
    }
}
