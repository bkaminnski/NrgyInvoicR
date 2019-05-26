package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.hclc.nrgyinvoicr.backend.invoices.InvoiceSpecifications.issueDateBetween;

@Service
class InvoicesService {
    private final InvoicesRepository invoicesRepository;

    InvoicesService(InvoicesRepository invoicesRepository) {
        this.invoicesRepository = invoicesRepository;
    }

    Page<Invoice> findInvoices(InvoicesSearchCriteria criteria) {
        Date from = criteria.getIssueDateFrom();
        Date to = criteria.getIssueDateTo();
        Specification<Invoice> specification = issueDateBetween(from, to);

        return this.invoicesRepository.findAll(specification, criteria.getPageDefinition().asPageRequest());
    }
}
