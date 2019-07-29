package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class InvoiceRunsService {
    private final InvoiceRunsRepository invoiceRunsRepository;
    private final NewInvoiceRunFactory newInvoiceRunFactory;

    InvoiceRunsService(InvoiceRunsRepository invoiceRunsRepository, NewInvoiceRunFactory newInvoiceRunFactory) {
        this.invoiceRunsRepository = invoiceRunsRepository;
        this.newInvoiceRunFactory = newInvoiceRunFactory;
    }

    public InvoiceRun prepareNewInvoiceRun() {
        return newInvoiceRunFactory.createNewInvoiceRun();
    }

    public InvoiceRun createInvoiceRun(InvoiceRun invoiceRun) {
        invoiceRun.setId(null);
        return invoiceRunsRepository.save(invoiceRun);
    }

    public Page<InvoiceRun> findInvoiceRuns(InvoiceRunsSearchCriteria criteria) {
        return this.invoiceRunsRepository.findAll(criteria.getPageDefinition().asPageRequest());
    }
}
