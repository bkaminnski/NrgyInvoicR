package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Iterable<Invoice> findInvoices(InvoicesSearchCriteria invoicesSearchCriteria) throws ParseException {
        Date from = invoicesSearchCriteria.getIssueDateFrom();
        Date to = invoicesSearchCriteria.getIssueDateTo();
        return this.invoicesRepository.findAll(issueDateBetween(from, to));
    }
}
