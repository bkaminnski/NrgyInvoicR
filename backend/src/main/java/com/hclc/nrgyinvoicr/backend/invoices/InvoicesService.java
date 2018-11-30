package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

import static com.hclc.nrgyinvoicr.backend.invoices.InvoiceSpecifications.issueDateBetween;

@Service
public class InvoicesService {

    private final InvoicesRepository invoicesRepository;

    @Autowired
    public InvoicesService(InvoicesRepository invoicesRepository) {
        this.invoicesRepository = invoicesRepository;
    }

    public Page<Invoice> findInvoices(InvoicesSearchCriteria criteria) throws ParseException {
        Date from = criteria.getIssueDateFrom();
        Date to = criteria.getIssueDateTo();
        Specification<Invoice> specification = issueDateBetween(from, to);

        return this.invoicesRepository.findAll(specification, criteria.getPageDefinition().asPageRequest());
    }
}
